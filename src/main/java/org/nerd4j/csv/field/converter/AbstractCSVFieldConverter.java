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
package org.nerd4j.csv.field.converter;

import org.nerd4j.csv.field.CSVFieldConverter;
import org.nerd4j.csv.field.CSVFieldProcessContext;


/**
 * Abstract implementation of the {@link CSVFieldConverter} interface
 * that performs the expected behavior and modifies the {@link CSVFieldProcessContext}
 * accordingly with the framework execution policy.
 * 
 * <p>
 * Any custom implementation of the {@link CSVFieldConverter} interface
 * should extend this class. 
 *
 * @param <S> type of the source field format.
 * @param <T> type of the target field format.
 * 
 * @author Nerd4j Team
 */
public abstract class AbstractCSVFieldConverter<S,T> implements CSVFieldConverter<S,T>
{
	
	/** The source type accepted by this converter. */
	private Class<S> sourceType;
	
	/** The target type produced by this converter. */
	private Class<T> targetType;
    
    /** The error message pattern to set into the context in case of failure. */
    private String errorMessagePattern;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param sourceType source type accepted by this converter. 
     * @param targetType target type produced by this converter.
     * @param errorMessagePattern error message pattern to set into the context in case of failure.
     */
    public AbstractCSVFieldConverter( final Class<S> sourceType, final Class<T> targetType, final String errorMessagePattern )
    {
        
        super();
        
        if( sourceType == null )
            throw new IllegalArgumentException( "The source type is mandatory cannot be null" );

        if( targetType == null )
        	throw new IllegalArgumentException( "The target type is mandatory cannot be null" );
        
        if( errorMessagePattern == null || errorMessagePattern.isEmpty() )
        	throw new IllegalArgumentException( "The error message pattern is mandatory cannot be null or empty" );
        
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.errorMessagePattern = errorMessagePattern;
        
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */

    
	/**
	 * {@inheritDoc}
	 */
    @Override
	public Class<S> getSourceType()
	{
    	
    	return sourceType;
    	
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override
	public Class<T> getTargetType()
	{
    	
    	return targetType;
    	
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessagePattern()
    {
        return errorMessagePattern;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public T convert( final S source, final CSVFieldProcessContext context )
    {
        
        try{
        
            final T target = performConversion( source );        
            if( target != null )
            {
                context.setProcessedValue( target );
                return target;
            }
        
        }catch( Exception ex ) {}
        
        context.operationFailed( this );
        return null;
        
    }

    
    /* **************** */
    /*  EXTENSION HOOKS */
    /* **************** */

    
    /**
     * Performs the conversion over the provided source value.
     * <p>
     * This method can assume to receive a not null and not
     * empty value.
     * 
     * @param source the not null and not empty value to convert.
     * @return the converted value.
     * @throws Exception if the conversion process fails.
     */
    protected abstract T performConversion( final S source ) throws Exception;
    
}
