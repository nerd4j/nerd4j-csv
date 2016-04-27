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
	 * The name of the CSV column reported
	 * in the CSV source header if any, or
	 * the column index if no header is defined.
	 */
	private String columnKey;
	
	/**
	 * The key of the destination location in
	 * the data model, for example the name
	 * of the bean property or the key of
	 * the map or the index of the array.
	 */
	private String modelKey;
	
	/**
	 * The data type into which convert
	 * the column value.
	 */
	private Class<?> modelType;
	
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param columnKey name or index of the CSV column.
	 * @param modelKey  name or index of the data model location.
	 * @param modelType type into which convert the column value.
	 */
	public CSVMappingDescriptor( String columnKey, String modelKey, Class<?> modelType )
	{
		
		super();
		
		if( columnKey == null || columnKey.isEmpty() )
            throw new CSVConfigurationException( "The provided column key must be not null or empty. Check the configuration" );

        
        if( modelKey == null || modelKey.isEmpty() )
            throw new CSVConfigurationException( "The provided model key must be not null or empty. Check the configuration" );
        
        if( modelType == null )
            throw new CSVConfigurationException( "The model type is mandatory. Check the configuration" );
        
        this.modelKey = modelKey;
        this.modelType = modelType;
        this.columnKey = columnKey;
        
	}

	
	/* ******************* */
	/*  GETTERS & SETTERS  */
	/* ******************* */

	
	public String getColumnKey()
	{
		return columnKey;
	}

	public String getModelKey()
	{
		return modelKey;
	}

	public Class<?> getModelType()
	{
		return modelType;
	}
	
}
