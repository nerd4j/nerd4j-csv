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
package org.nerd4j.csv.reader;

import org.nerd4j.csv.exception.CSVConfigurationException;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.csv.parser.CSVParserFactory;
import org.nerd4j.csv.parser.CSVParserMetadata;
import org.nerd4j.csv.reader.binding.CSVToModelBinderFactory;


/**
 * Represents the meta-data used to tell a {@link CSVReaderFactoryImpl}
 * how to build and configure the related {@link CSVReader}s.
 * 
 * @param <Model> type of the field mapping descriptor.
 * 
 * @author Nerd4j Team
 */
public final class CSVReaderMetadata<Model>
{

    /**
     * Tells the {@link CSVReader} to read the
     * first row of the CSV source as a header.
     * <p>
     * The default value for this flag is {@code true}.
     */
    private final boolean readHeader;
    
    /**
     * This value tells the factory to use the header values
     * to identify the columns by name.
     * <p>
     * If this flag is {@code true}, the first row of
     * the CSV source is read as the header and the related
     * strings are matched in the configuration to identify
     * the corresponding columns.
     * <p>
     * This option can be used only if the flag "readHeader"
     * is {@code true}.
     * <p>
     * The default value for this flag is {@code true}.
     */
    private final boolean useColumnNames;
    
    /**
     * In the CSV standard each record must have the same number of cells.
     * But it is possible to face non standard CSV files where records
     * have different lengths.
     * <p>
     * This flag tells the reader to accept such non standard files.
     * In this case the reader may return incomplete data models.
     */
    private final boolean acceptIncompleteRecords;
    

    /**
     * Each index in the array corresponds to a column
     * in the CSV and provides configurations about
     * how to handle the related fields.
     * <p>
     * This array cannot contain {@code null} values.
     */
    private final CSVFieldMetadata<String,?>[] fieldConfigurations;
    
    /** The factory able to create {@link CSVParser}s. */
    private final CSVParserFactory parserFactory;

    /** The factory able to create {@link ModelBinder}s. */
    private final CSVToModelBinderFactory<Model> modelBinderFactory;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param parserMetadata      the parser meta-data to use.
     * @param modelBinderFactory  the factory able to create {@link org.nerd4j.csv.reader.binding.CSVToModelBinder CSVToModelBinder}s.
     * @param fieldConfigurations the configurations related to the single fields.
     * @param readHeader          tells if the header should be read.
     * @param useColumnNames      tells to use the column names in the header to perform column mapping.
     * @param acceptIncompleteRecords tells to accept CSV where not all records have the same number of fields.
     */
    public CSVReaderMetadata( final CSVParserMetadata parserMetadata,
                              final CSVToModelBinderFactory<Model> modelBinderFactory,
                              final CSVFieldMetadata<String,?>[] fieldConfigurations,
                              final boolean readHeader,
                              final boolean useColumnNames,
                              final boolean acceptIncompleteRecords )
    {
        
        super();
        
        this.readHeader = readHeader;
        this.useColumnNames = useColumnNames;
        this.modelBinderFactory = modelBinderFactory;
        this.fieldConfigurations = fieldConfigurations;
        this.acceptIncompleteRecords = acceptIncompleteRecords;
        
        this.parserFactory = new CSVParserFactory( parserMetadata );
        
        this.checkConsistency();
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    public boolean isReadHeader()
    {
        return readHeader;
    }
    
    public CSVFieldMetadata<String,?>[] getFieldConfigurations()
    {
        return fieldConfigurations;
    }

    public CSVParserFactory getParserFactory()
    {
        return parserFactory;
    }
    
    public CSVToModelBinderFactory<Model> getModelBinderFactory()
    {
        return modelBinderFactory;
    }
    
    public boolean isUseColumnNames()
    {
        return useColumnNames;
    }
    
    public boolean isAcceptIncompleteRecords()
    {
		return acceptIncompleteRecords;
	}
    
    
    /* ***************** */
    /*  UTILITY METHODS  */
    /* ***************** */

    
	/**
     * Checks the consistency of the configuration.
     * 
     */
    public void checkConsistency()
    {
        
        if( modelBinderFactory == null )
            throw new CSVConfigurationException( "The CSV Model Builder Factory is mandatory" );
        
        if( parserFactory == null )
            throw new CSVConfigurationException( "The CSV Parser Factory is mandatory" );
        
        if( fieldConfigurations == null || fieldConfigurations.length < 1 )
            throw new CSVConfigurationException( "The field configurations are mandatory. Check the configuration" );

        
        for( CSVFieldMetadata<String,?> conf : fieldConfigurations )
            if( conf == null )
                throw new CSVConfigurationException( "The field configuration array cannot contain null entries. Check the configuration" );
        
    }

}