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
package org.nerd4j.csv.writer;

import org.nerd4j.csv.exception.CSVConfigurationException;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.csv.formatter.CSVFormatterFactory;
import org.nerd4j.csv.formatter.CSVFormatterMetadata;
import org.nerd4j.csv.writer.binding.ModelToCSVBinderFactory;


/**
 * Represents the meta-data used to tell a {@link CSVWriterFactoryImpl}
 * how to build and configure the related {@link CSVWriter}s.
 * 
 * @param <Model> type of the field mapping descriptor.
 * 
 * @author Nerd4j Team
 */
public final class CSVWriterMetadata<Model>
{
    
    /**
     * Tells the {@link CSVWriter} to write the
     * given header into the first row of the CSV destination.
     * <p>
     * The default value for this flag is {@code true}.
     */
    private final boolean writeHeader;
    
    /**
     * Each index in the array corresponds to a column
     * in the CSV and provides configurations about
     * how to handle the related fields.
     * <p>
     * This array cannot contain {@code null} values.
     */
    private final CSVFieldMetadata<?,String>[] fieldConfigurations;
    
    /** The factory able to create {@link CSVFormatter}s. */
    private final CSVFormatterFactory formatterFactory;
    
    /** The factory able to create {@link ModelBinder}s. */
    private final ModelToCSVBinderFactory<Model> modelBinderFactory;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param formatterMetadata    the configuration of the formatter to use.
     * @param modelBinderFactory   the factory able to create {@link org.nerd4j.csv.writer.binding.ModelToCSVBinder ModelToCSVBinder}s.
     * @param fieldConfigurations  the configurations related to the single fields.
     * @param writeHeader          tells if the header should be written.
     */
    public CSVWriterMetadata( final CSVFormatterMetadata formatterMetadata,
                              final ModelToCSVBinderFactory<Model> modelBinderFactory,
                              final CSVFieldMetadata<?,String>[] fieldConfigurations,
                              final boolean writeHeader )
    {
        
        super();
        
        if( fieldConfigurations == null || fieldConfigurations.length < 1 )
            throw new CSVConfigurationException( "The field configurations are mandatory. Check the configuration" );

        this.writeHeader = writeHeader;
        this.modelBinderFactory = modelBinderFactory;
        this.fieldConfigurations = fieldConfigurations;
        this.formatterFactory = new CSVFormatterFactory( formatterMetadata );
        
        this.checkConsistency();
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the {@code write-header} flag. 
     * 
     * @return the {@code write-header} flag.
     */
    public boolean isWriteHeader()
    {
        return writeHeader;
    }
    
    /**
     * Returns the configurations related to the fields.
     * 
     * @return the configurations related to the fields.
     */
    public CSVFieldMetadata<?,String>[] getFieldConfigurations()
    {
        return fieldConfigurations;
    }
    
    /**
     * Returns the model binder factory.
     * 
     * @return the model binder factory.
     */
    public ModelToCSVBinderFactory<Model> getModelBinderFactory()
    {
        return modelBinderFactory;
    }
    
    /**
     * Returns the formatter factory.
     * 
     * @return the formatter factory.
     */
    public CSVFormatterFactory getFormatterFactory()
    {
        return formatterFactory;
    }

        
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * Checks the consistency of the provided field configurations.
     * 
     */
    private void checkConsistency()
    {
        
        if( modelBinderFactory == null )
            throw new CSVConfigurationException( "The CSV Model Builder Factory is mandatory" );
        
        if( formatterFactory == null )
            throw new CSVConfigurationException( "The CSV Formatter Configuration is mandatory" );
        
        for( CSVFieldMetadata<?,String> conf : fieldConfigurations )
            if( conf == null )
                throw new CSVConfigurationException( "The field configuration array cannot contain null entries. Check the configuration" );
                
        if( writeHeader )
            for( int i = 0; i < fieldConfigurations.length; ++i )
            {
            	final String columnId = fieldConfigurations[i].getMappingDescriptor().getColumnId(); 
                if( columnId == null || columnId.isEmpty() )
                    throw new CSVConfigurationException( "The flag 'writeHeader' is true but there is no identifier for column " + i + ". Check the configuration" );
            }
        
    }

}