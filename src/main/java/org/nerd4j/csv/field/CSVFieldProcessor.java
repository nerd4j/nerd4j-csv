/*
 * #%L
 * Nerd4j CSV
 * %%
 * Copyright (C) 2013 Nerd4j
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.nerd4j.csv.field;


/**
 * This class performs the process needed to manipulate
 * a CSV field from its {@link String} form to its
 * {@link Object} representation and vice versa.
 * 
 * @param <S> type of the source field format.
 * @param <T> type of the target field format.
 * 
 * @author Nerd4j Team
 */
public class CSVFieldProcessor<S,T>
{
    
    /** Represents the condition that the field must satisfy before to be converted. */
    private final CSVFieldValidator<S> precondition;

    /** Performs the conversion of the field. */
    private final CSVFieldConverter<S,T> converter;
    
    /** Represents the condition that the field must satisfy after the conversion. */
    private final CSVFieldValidator<T> postcondition;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param precondition  condition to satisfy before conversion.
     * @param converter     converter that changes the field type. 
     * @param postcondition condition to satisfy after conversion.
     */
    public CSVFieldProcessor( final CSVFieldValidator<S> precondition,
                              final CSVFieldConverter<S,T> converter,
                              final CSVFieldValidator<T> postcondition )
    {
        
        super();
        
        if( converter == null )
            throw new NullPointerException( "The converter is mandatory and can't be null" );
        
        this.converter = converter;
        this.precondition = precondition;
        this.postcondition = postcondition;
        
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
	 * Returns the source type accepted by this processor.
	 * 
	 * @return source type accepted by this processor.
	 */
	public Class<S> getSourceType()
	{
		
		return converter.getSourceType();
		
	}
	
	/**
	 * Returns the target type produced by this processor.
	 * 
	 * @return target type produced by this processor.
	 */
	public Class<T> getTargetType()
	{
		
		return converter.getTargetType();
		
	}

    /**
     * Applies the steps needed to process a CSV
     * filed from its {@link String} form to its
     * {@link Object} representation and vice versa.
     * 
     * @param source the CSV field to process.
     * @param context the field process execution context.
     * @return the CSV field after the manipulation.
     */
    public T process( final S source, final CSVFieldProcessContext context )
    {
        
        /* If the source is not null we proceed with the manipulation. */
        
        /* First we apply the precondition if any. */
        if( precondition != null )
        {
            /* If the validation fails we exit with an error context. */
            precondition.apply( source, context );
            if( context.isError() ) return null;
        }
        
        /* Then we convert the field to the expected type. */
        final T target = converter.convert( source, context );
        if( context.isError() ) return null;
        
        /*
         * Last we apply the postcondition if any.
         * In any case we exit with the current context.
         */
        if( postcondition != null )
            postcondition.apply( target, context );
        
        return target;
        
    }
    
}