/*
 * #%L
 * Nerd4j CSV
 * %%
 * Copyright (C) 2013 - 2016 Nerd4j
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
 * Represents the mapping between a column in the CSV
 * and a field in the data model.
 * 
 * @author Nerd4j Team
 */
public class CSVMappingDescriptor
{

	/**
	 * The identifier of the column.
	 * <p>
	 * Can be the name of the CSV column reported
	 * in the CSV source header if any, or
	 * the column index if no header is defined.
	 */
	private String columnId;
	
	/**
	 * The identifier of the destination location
	 * in the data model.
	 * <p>
	 * For example the name of the bean property
	 * or the key of the map or the index of the
	 * array.
	 */
	private String modelId;
	
	/**
	 * The data type into which convert
	 * the column value.
	 */
	private Class<?> modelType;
	
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param columnId identifier of the CSV column.
	 * @param modelId  identifier of the data model location.
	 * @param modelType type into which convert the column value.
	 */
	public CSVMappingDescriptor( String columnId, String modelId, Class<?> modelType )
	{
		
		super();
		
		if( columnId == null || columnId.isEmpty() )
            throw new CSVConfigurationException( "The provided column identifier must be not null or empty. Check the configuration" );

        
        if( modelId == null || modelId.isEmpty() )
            throw new CSVConfigurationException( "The provided model identifier must be not null or empty. Check the configuration" );
        
        if( modelType == null )
            throw new CSVConfigurationException( "The model type is mandatory. Check the configuration" );
        
        this.modelId = modelId;
        this.columnId = columnId;
        this.modelType = modelType;
        
	}

	
	/* ******************* */
	/*  GETTERS & SETTERS  */
	/* ******************* */

	
	/**
	 * Returns the identifier of the CSV column.
	 * 
	 * @return the identifier of the CSV column.
	 */
	public String getColumnId()
	{
		return columnId;
	}

	/**
	 * Returns the identifier of the destination location in the data model.
	 * 
	 * @return the identifier of the destination location in the data model.
	 */
	public String getModelId()
	{
		return modelId;
	}

	/**
	 * Returns the data type into which convert the column value.
	 * 
	 * @return the data type into which convert the column value.
	 */
	public Class<?> getModelType()
	{
		return modelType;
	}
	
}
