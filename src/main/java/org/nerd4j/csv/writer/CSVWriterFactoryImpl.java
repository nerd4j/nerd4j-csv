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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.nerd4j.csv.exception.ModelToCSVBindingException;
import org.nerd4j.csv.field.CSVField;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.csv.formatter.CSVFormatter;
import org.nerd4j.csv.writer.binding.ModelToCSVBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Reference implementation for the {@link CSVWriterFactory} interface.
 * 
 * @param <Model> type of the data model accepted by the writer.
 * 
 * @author Nerd4j Team
 */
public final class CSVWriterFactoryImpl<Model> implements CSVWriterFactory<Model>
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVWriterFactoryImpl.class );
    
    /** The {@code Factory} for the reader meta-data model. */
    private final CSVWriterMetadataFactory<Model> metadataFactory;
    
//    /** The configuration to use for creating and configuring the {@link CSVWriter}s. */
//    private CSVWriterMetadata<Model> configuration;
//    
//    /** Factory used to build CSV formatters. */
//    private CSVFormatterFactory formatterFactory;
//    
//    /** Factory used to create builders which know how to populate the returned data model. */
//    private ModelToCSVBinderFactory<Model> binderFactory;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param metadataFactory factory to get meta-data required to map the CSV fields properly.
     */
    public CSVWriterFactoryImpl( CSVWriterMetadataFactory<Model> metadataFactory )
    {
        
        super();
        
        if( metadataFactory == null )
            throw new NullPointerException( "The CSV writer configuration is mandatory" );
        
        this.metadataFactory = metadataFactory;
        
//        this.configuration = configuration;
//        this.binderFactory = configuration.getModelBinderFactory();
//        this.formatterFactory = new CSVFormatterFactory( configuration.getFormatterConfiguration() );
        
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVWriter<Model> getCSVWriter( File file )
    throws FileNotFoundException, IOException, ModelToCSVBindingException
    {
        
        return getCSVWriter( new FileWriter(file) );
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVWriter<Model> getCSVWriter( OutputStream os )
    throws IOException, ModelToCSVBindingException
    {
        
        return getCSVWriter( new OutputStreamWriter(os) );
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVWriter<Model> getCSVWriter( Writer writer )
    throws IOException, ModelToCSVBindingException
    {
    	
    	/*
    	 * First of all we create a new meta-data model.
    	 * If the framework was not well configured the
    	 * system can break ad this point.
    	 */
    	final CSVWriterMetadata<Model> metadata = metadataFactory.getCSVWriterMetadata();
        
        /* We create the formatter able to format the given data source. */
        final CSVFormatter csvFormatter = metadata.getFormatterFactory().create( writer );
        
        /* As second step we write the CSV header if requested. */
        final String[] csvHeader = writeHeaderIfNeeded( csvFormatter, metadata );
        
        /* Than we create the fields needed to elaborate the data model. */
        final CSVField<?,String>[] fields = buildFields( metadata );
        
        /* We create the model binder needed to bind the model data to the output CSV. */
        final ModelToCSVBinder<Model> modelBinder = metadata.getModelBinderFactory().getModelToCSVBinder( metadata );
        
        /* Finally we create the CSV writer. */
        final CSVWriter<Model> csvWriter = new CSVWriterImpl<Model>( csvFormatter, csvHeader, fields, modelBinder );
        
        return csvWriter;
        
    }
   
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * If the flag "writeHeader" in the configuration is <code>true</code>
     * and the configured header is not empty, writes the given header as
     * the first row in the CSV destination.
     * 
     * @param csvFormatter the CSV formatter.
     * @param metadata  the meta-data model to read configuration from.
     * @return the CSV destination header.
     * @throws IOException if the CSV formatter fails to write.
     */
    private String[] writeHeaderIfNeeded( CSVFormatter csvFormatter, CSVWriterMetadata<Model> metadata )
    throws IOException
    {
        
        if( ! metadata.isWriteHeader() )
        {
            logger.trace( "The 'writeHeader' flag in configuration is false, no header will be written." );
            return null;
        }
        
        final CSVFieldMetadata<?,String>[] fieldConfs = metadata.getFieldConfigurations();
        final String[] header = new String[fieldConfs.length];
        
        String columnName;
        for( int i = 0; i < fieldConfs.length; ++i )
        {
            columnName = fieldConfs[i].getColumnName();
            
            csvFormatter.writeField( columnName, false );
            header[i] = columnName;
        }
        
        csvFormatter.writeEOR();
        
        if( logger.isDebugEnabled() )
        {
            logger.debug( "The 'writeHeader' flag in configuration is true, the following header has been writen." );
            if( header.length < 1 )
            {
                logger.debug( "The header is empty." );
                return header;
            }
            
            final StringBuilder sb = new StringBuilder( 300 );
            sb.append( header[0] );
            for( int i = 1; i < header.length; ++i )
                sb.append( ", " ).append( header[i] );
            
            logger.debug( sb.toString() );
        }
        
        return header;
        
    }
    
    
    /**
     * Builds the fields needed to elaborate the CSV data model.
     * 
     * @param metadata  the meta-data model to read configuration from.
     * @return the field needed to elaborate the CSV data model.
     */
    @SuppressWarnings("unchecked")
    private CSVField<?,String>[] buildFields( CSVWriterMetadata<Model> metadata )
    {
        
        logger.debug( "Going to get field processors from the configuration." );
        
        final CSVFieldMetadata<?,String>[] fieldConfs = metadata.getFieldConfigurations();
        final CSVField<?,String>[] fields = new CSVField[fieldConfs.length];
        
        for( int i = 0; i < fieldConfs.length; ++i )
            fields[i] = fieldConfs[i].getField();
        
        return fields;
        
    }
   
}