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
 * Represents an empty converter that doesn't perform
 * any real conversion, it returns the given object.
 * 
 * @param <V> type of the data to convert.
 * 
 * @author Nerd4j Team
 */
public final class EmptyCSVFieldConverter<V> implements CSVFieldConverter<V,V>
{

	
	/** The source type accepted by this converter. */
	private Class<V> type;
	
    
    /**
     * Constructor with parameters.
     * 
     * @param the data type handled by this empty converter.
     */
    public EmptyCSVFieldConverter( final Class<V> type )
    {
        
        super();
        
        if( type == null )
            throw new IllegalArgumentException( "The data type is mandatory cannot be null" );
       
        this.type = type;
        
    }
    

    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
	 * {@inheritDoc}
	 */
    @Override
	public Class<V> getSourceType()
	{
    	
    	return type;
    	
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override
	public Class<V> getTargetType()
	{
    	
    	return type;
    	
	}

    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessagePattern()
    {
        return "This converter never fails!";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public V convert( final V source, final CSVFieldProcessContext context )
    {
        
        context.setProcessedValue( source );
        return source;
                
    }

}
