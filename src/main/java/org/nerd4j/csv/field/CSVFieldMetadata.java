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
public final class CSVFieldMetadata<S,T>
{

    /** Object that describes the field-model mapping. */
    private final CSVMappingDescriptor mappingDescriptor;
    
    /** The field used to represent the column. */
    private final CSVField<S,T> field;

    
    /**
     * Constructor with parameters.
     * 
     * @param mappingDescriptor the field-model mapping (mandatory).
     * @param field             the field used to represent the column (mandatory).
     * 
     */
    public CSVFieldMetadata( final CSVMappingDescriptor mappingDescriptor, final CSVField<S,T> field )
    {
        
        super();
        
        if( mappingDescriptor == null )
            throw new CSVConfigurationException( "The mapping descriptor is mandatory. Check the configuration" );
        
        if( field == null )
            throw new CSVConfigurationException( "The field is mandatory. Check the configuration" );
        
        this.field = field;
        this.mappingDescriptor = mappingDescriptor;
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */

    
    public CSVMappingDescriptor getMappingDescriptor()
    {
        return mappingDescriptor;
    }
    
    public CSVField<S,T> getField()
    {
        return field;
    }
    
}