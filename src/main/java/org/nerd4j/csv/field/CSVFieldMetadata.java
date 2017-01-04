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

import org.nerd4j.csv.exception.CSVConfigurationException;


/**
 * Represents the meta-data that describes how to handle
 * a single field in the CSV process.
 * 
 * @param <S> type of the source field format.
 * @param <T> type of the target field format.
 * 
 * @author Nerd4J Team
 */
public final class CSVFieldMetadata<S,T> implements Comparable<CSVFieldMetadata<S,T>>
{

    /** Object that describes the field-model mapping. */
    private final CSVMappingDescriptor mappingDescriptor;
    
    /** The field used to represent the column. */
    private final CSVField<S,T> field;
    
    /** The order in which the field should be written. */
    private final int order;

    
    /**
     * Constructor with parameters.
     * 
     * @param mappingDescriptor the field-model mapping (mandatory).
     * @param field             the field used to represent the column (mandatory).
     * @param order             the order in which the field should be written.
     * 
     */
    public CSVFieldMetadata( final CSVMappingDescriptor mappingDescriptor,
    		                 final CSVField<S,T> field, final int order )
    {
        
        super();
        
        if( mappingDescriptor == null )
            throw new CSVConfigurationException( "The mapping descriptor is mandatory. Check the configuration" );
        
        if( field == null )
            throw new CSVConfigurationException( "The field is mandatory. Check the configuration" );
        
        this.field = field;
        this.order = order;
        this.mappingDescriptor = mappingDescriptor;
        
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */

    
    /**
     * {@inheritDoc}
     */
	@Override
	public int compareTo( CSVFieldMetadata<S, T> o )
	{
		
		return this.order - o.order;
		
	}
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the field mapping descriptor.
     * 
     * @return the field mapping descriptor.
     */
    public CSVMappingDescriptor getMappingDescriptor()
    {
        return mappingDescriptor;
    }
    
    /**
     * Returns the field representation.
     * 
     * @return the field representation.
     */
    public CSVField<S,T> getField()
    {
        return field;
    }
    
    /**
     * Returns the order in which the field should be written.
     * 
     * @return the order in which the field should be written.
     */
    public int getOrder()
    {
    	return order;
    }
    
}