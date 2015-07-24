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

import org.nerd4j.csv.CSVProcessOperation;


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
public final class CSVField<S,T>
{
    
    /** Dummy operator to notify mandatory constraint violation. */
    private static final Mandatory MANDATORY = new Mandatory();
    
    /** Performs the operations needed to process the field. */
    private final CSVFieldProcessor<S,T> processor;
    
    /** Tells if the value of this field can be <code>null</code>. */
    private final boolean optional;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param processor     processor that changes the field type. 
     * @param optional      tells if the value of this field can be <code>null</code>. 
     */
    public CSVField( final CSVFieldProcessor<S,T> processor, final boolean optional )
    {
        
        super();
        
        if( processor == null )
            throw new NullPointerException( "The processor is mandatory and can't be null" );
        
        this.optional = optional;
        this.processor = processor;
        
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    

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
        
        /*
         * If the source is null and it is expected to be
         * optional a null value will be returned, otherwise
         * an exception will be thrown.
         */
        if( source == null )
        {
            
            if( ! optional )
                context.operationFailed( MANDATORY );

            return null;
            
        }
        
        /* If the source is not null we proceed with the manipulation. */
        return processor.process( source, context );
        
    }
    
    
    /**
     * Tells if the current field can be empty.
     * 
     * @return {@code true} if the field is optional.
     */
    public boolean isOptional()
    {
    	return optional;
    }
    
    
    /* *************** */
    /*  INNER CLASSES  */
    /* *************** */
    
    
    /**
     * Dummy implementation of the {@link CSVFieldOperator}
     * used to notify a null value on a not null field.
     * 
     * @author Nerd4j Team
     */
    private static class Mandatory implements CSVProcessOperation
    {
        
        /**
         * Default constructor.
         * 
         */
        public Mandatory()
        {
            
            super();
            
        }

        
        /* ******************* */
        /*  INTERFACE METHODS  */
        /* ******************* */

        
        /**
         * {@inheritDoc}
         */
        @Override
        public String getErrorMessagePattern()
        {
            return "Field {0} is mandatory but null value was found";
        }
        
    }
    
}