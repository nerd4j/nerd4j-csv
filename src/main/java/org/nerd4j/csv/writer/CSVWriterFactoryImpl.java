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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.nerd4j.csv.exception.CSVInvalidHeaderException;
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
    public CSVWriter<Model> getCSVWriter( File file, String[] header )
    throws FileNotFoundException, IOException, ModelToCSVBindingException, CSVInvalidHeaderException
    {
    	
    	return getCSVWriter( new FileWriter(file), header );
    	
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
    public CSVWriter<Model> getCSVWriter( OutputStream os, String[] header )
    throws IOException, ModelToCSVBindingException, CSVInvalidHeaderException
    {
    	
    	return getCSVWriter( new OutputStreamWriter(os), header );
    	
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
        
        /* As second step we create the CSV header. */
        final String[] header = buildHeader( metadata );
        
        /* Finally we create the rewuested CSV writer. */
        return buildCSVWriter( metadata, writer, header );
        
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVWriter<Model> getCSVWriter( Writer writer, String[] header )
    throws IOException, ModelToCSVBindingException, CSVInvalidHeaderException
    {
    	
    	/*
    	 * First of all we create a new meta-data model.
    	 * If the framework was not well configured the
    	 * system can break ad this point.
    	 */
    	final CSVWriterMetadata<Model> metadata = metadataFactory.getCSVWriterMetadata();
        
        /* As second step we check if the provided CSV header is valid. */
        checkHeader( header, metadata );
        
        /* Finally we create the rewuested CSV writer. */
        return buildCSVWriter( metadata, writer, header );
    	
    }
    
    
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    
    /**
     * Creates a new {@link CSVWriter} able to write the given data model
     * of type M into the provided CSV destination.
     * <p>
     * The provided header is used to determine which columns and in which
     * order should be written.
     * 
     * @param metadata the meta-data model to read configuration from.
     * @param writer   the CSV destination writer.
     * @param header   the header to be written.
     * @return a related {@link CSVWriter}.
     * @throws IOException if fails to write the destination.
     * @throws ModelToCSVBindingException if binding configuration is inconsistent.
     */
    private CSVWriter<Model> buildCSVWriter( CSVWriterMetadata<Model> metadata,
    		                                 Writer writer, String[] header )
    throws IOException, ModelToCSVBindingException
    {
    	
    	/* We create the formatter able to format the given data source. */
    	final CSVFormatter csvFormatter = metadata.getFormatterFactory().create( writer );
    	
    	/* As second step we write the CSV header if requested. */
    	if( metadata.isWriteHeader() )
    		writeHeader( csvFormatter, header );
    	else
    		logger.trace( "The 'writeHeader' flag in configuration is false, no header will be written." );
    	
    	/* Than we create the fields needed to elaborate the data model. */
    	final CSVField<?,String>[] fields = buildFields( metadata, header );
    	
    	/* We create the model binder needed to bind the model data to the output CSV. */
    	final ModelToCSVBinder<Model> modelBinder = metadata.getModelBinderFactory().getModelToCSVBinder( metadata, header );
    	
    	/* Finally we create the CSV writer. */
    	final CSVWriter<Model> csvWriter = new CSVWriterImpl<Model>( csvFormatter, header, fields, modelBinder );
    	
    	return csvWriter;
    	
    }
    
    
    /**
     * Creates a new header retrieving the column names from
     * the internal configuration.
     * 
     * @param metadata  the meta-data model to read configuration from.
     * @return the CSV destination header.
     */
    private String[] buildHeader( CSVWriterMetadata<Model> metadata )
    throws IOException
    {
                        
        final CSVFieldMetadata<?,String>[] fieldConfs = metadata.getFieldConfigurations();
        final String[] header = new String[fieldConfs.length];
        
        String columnId;
        for( int i = 0; i < fieldConfs.length; ++i )
        {
            columnId = fieldConfs[i].getMappingDescriptor().getColumnId();
            header[i] = columnId;
        }
        
        return header;
        
    }
    
    
    /**
     * Checks if the given header is valid related to the given configuration.
     * 
     * @param header    the header to check.
     * @param metadata  the meta-data model to read configuration from.
     * @throws CSVInvalidHeaderException if the header is not valid.
     */
    private void checkHeader( String[] header, CSVWriterMetadata<Model> metadata )
    throws CSVInvalidHeaderException
    {
    	
    	final CSVFieldMetadata<?,String>[] fieldConfs = metadata.getFieldConfigurations();
    	final Set<String> columnIds = new HashSet<String>( fieldConfs.length );
    	
    	for( CSVFieldMetadata<?,String> field : fieldConfs )
    		columnIds.add( field.getMappingDescriptor().getColumnId() );
    	
    	for( String columnId : header )
    		if( ! columnIds.contains(columnId) )
    			throw new CSVInvalidHeaderException( columnId );
    	    	
    }
    
    
    /**
     * Writes the given header as the first row in the CSV destination.
     * 
     * @param csvFormatter the CSV formatter.
     * @param header the header to write.
     * @throws IOException if the CSV formatter fails to write.
     */
    private void writeHeader( CSVFormatter csvFormatter, String[] header )
    throws IOException
    {
    	
    	for( String columnName : header )
    		csvFormatter.writeField( columnName, false );
    	
    	csvFormatter.writeEOR();
    	
    	if( logger.isDebugEnabled() )
    	{
    		logger.debug( "The 'writeHeader' flag in configuration is true, the following header has been writen." );
    		if( header.length < 1 )
    		{
    			logger.debug( "The header is empty." );
    			return;
    		}
    		
    		final StringBuilder sb = new StringBuilder( 300 );
    		sb.append( header[0] );
    		for( int i = 1; i < header.length; ++i )
    			sb.append( ", " ).append( header[i] );
    		
    		logger.debug( sb.toString() );
    	}
    	    	
    }
    
    
    /**
     * Builds the fields needed to elaborate the CSV data model.
     * <p>
     * The fields are returned in the same order defined by
     * the given header.
     * 
     * @param metadata  the meta-data model to read configuration from.
     * @param header    used to determine which columns to write and in which order.
     * @return the field needed to elaborate the CSV data model.
     */
    @SuppressWarnings("unchecked")
    private CSVField<?,String>[] buildFields( CSVWriterMetadata<Model> metadata,
    		                                  String[] header )
    {
        
        logger.debug( "Going to get field processors from the configuration." );
        
        final CSVField<?,String>[] fields = new CSVField[header.length];
        final CSVFieldMetadata<?,String>[] fieldConfs = metadata.getFieldConfigurations();
        final Map<String,CSVField<?,String>> fieldMap = new HashMap<>( fieldConfs.length );
        
        /* We create a map that associates to each field the related column identifier. */
        for( CSVFieldMetadata<?,String> conf : fieldConfs )
        	fieldMap.put( conf.getMappingDescriptor().getColumnId(), conf.getField() );
        	
        /* Then we return the fields in the order provided by the header. */
        for( int i = 0; i < header.length; ++i )
            fields[i] = fieldMap.get( header[i] );
        
        return fields;
        
    }
   
}