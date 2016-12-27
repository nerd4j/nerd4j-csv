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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.nerd4j.csv.exception.CSVConfigurationException;
import org.nerd4j.csv.exception.CSVToModelBindingException;
import org.nerd4j.csv.exception.MalformedCSVException;
import org.nerd4j.csv.field.CSVField;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.csv.parser.CSVParser;
import org.nerd4j.csv.reader.binding.CSVToModelBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Reference implementation for the {@link CSVReaderFactory} interface.
 * 
 * @param <Model> type of the data model returned by the reader.
 * 
 * @author Nerd4j Team
 */
public final class CSVReaderFactoryImpl<Model> implements CSVReaderFactory<Model>
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVReaderFactoryImpl.class );

    /** The {@code Factory} for the reader meta-data model. */
    private final CSVReaderMetadataFactory<Model> metadataFactory;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param metadataFactory the {@code Factory} of meta-data that describes how to build the CSV readers.
     */
    public CSVReaderFactoryImpl( final CSVReaderMetadataFactory<Model> metadataFactory )
    {
        
        super();
        
        if( metadataFactory == null )
            throw new CSVConfigurationException( "The CSV reader configuration is mandatory" );
        
        this.metadataFactory = metadataFactory;
        
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVReader<Model> getCSVReader( File file )
    throws FileNotFoundException, IOException, CSVToModelBindingException
    {
        
        return getCSVReader( new FileReader(file) );
        
    }
  
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVReader<Model> getCSVReader( InputStream is )
    throws IOException, CSVToModelBindingException
    {
        
        return getCSVReader( new InputStreamReader(is) );
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVReader<Model> getCSVReader( Reader reader )
    throws IOException, CSVToModelBindingException
    {
    	
    	/*
    	 * First of all we create a new meta-data model.
    	 * If the framework was not well configured the
    	 * system can break ad this point.
    	 */
    	final CSVReaderMetadata<Model> metadata = metadataFactory.getCSVReaderMetadata();
        
        /* We create the parser able to parse the given CSV source. */
        final CSVParser csvParser = metadata.getParserFactory().create( reader );
        
        /* As second step we read the CSV source header if requested. */
        final String[] csvHeader = readHeaderIfNeeded( csvParser, metadata );
        
        /* Than we create the column mapping needed to elaborate the CSV source fields. */
        final Integer[] columnMapping = buildColumnMapping( csvHeader, metadata );
        
        /* We create the fields needed to elaborate the CSV source columns. */
        final CSVField<String,?>[] fields = buildFields( columnMapping, metadata );
        
        /* We create the model binder needed to bind the source data to the output model. */
        final CSVToModelBinder<Model> modelBinder = metadata.getModelBinderFactory().getCSVToModelBinder( metadata, columnMapping );
        
        /* Finally we create the CSV reader. */
        final CSVReader<Model> csvReader =
          new CSVReaderImpl<Model>( csvParser, csvHeader, fields, modelBinder, metadata.isAcceptIncompleteRecords() );
        
        return csvReader;
        
    }
   
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * If the flag "readHeader" in the configuration is {@code true}
     * reads the first row and returns an array of {@link String}s
     * representing the CSV source header.
     * 
     * @param csvParser the CSV source parser.
     * @param metadata  the meta-data model to read configuration from.
     * @return the CSV source header or {@code null}.
     * @throws IOException if the CSV parser fails to read the source.
     */
    private String[] readHeaderIfNeeded( CSVParser csvParser, CSVReaderMetadata<Model> metadata )
    throws IOException
    {
        
        if( metadata.isReadHeader() )
        {
            final CSVHeaderReader csvHeaderReader = new CSVHeaderReader( csvParser );
            final String[] header = csvHeaderReader.readHeader();
            
            if( header == null || header.length < 1 )
            {
                logger.error( "Inconsistent CSV format, header expected but not present." );
                throw new MalformedCSVException( "The CSV source was expected to have an header but was empty." );
            }
            
            return header;
        }
        
        return null;
        
    }
    
    
    /**
     * Builds the fields needed to elaborate the CSV source fields.
     * 
     * @param columnMapping the mapping between the CSV source and the configuration.
     * @param metadata  the meta-data model to read configuration from.
     * @return the fields needed to elaborate the CSV source fields.
     */
    @SuppressWarnings("unchecked")
    private CSVField<String,?>[] buildFields( Integer[] columnMapping, CSVReaderMetadata<Model> metadata )
    {
        
        logger.debug( "Going to get field processors from the configuration." );
        
        if( columnMapping.length < 1 )
            /* The array of fields has always at least one element. */
            return new CSVField[1];
        
        final CSVField<String,?>[] fields = new CSVField[ columnMapping.length ];
        final CSVFieldMetadata<String,?>[] fieldConfs = metadata.getFieldConfigurations();
        for( int i = 0; i < fields.length; ++i )
            if( columnMapping[i] != null )
                fields[i] = fieldConfs[columnMapping[i]].getField();
        
        return fields;
        
    }
    
    
    /**
     * Returns an array that associates each column of the source
     * CSV with the corresponding index in the configuration.
     * <p>
     * This process is performed by reading the CSV source header.
     * If the header is {@code null} all the information
     * will be taken from the configuration.  
     * <p>
     *  If the configuration is smaller than the source CSV some
     *  entries in the array may be {@code null}.
     * <p>
     *  If the configuration is greater than the source CSV the
     *  inconsistent configurations will be ignored.
     * 
     * @param header the CSV source header if any.
     * @param metadata  the meta-data model to read configuration from.
     * @return the column index re-mapping.
     */
    private Integer[] buildColumnMapping( String[] header, CSVReaderMetadata<Model> metadata )
    {
        
        logger.debug( "Going to get the column mapping by parsing the configuration." );
        
        final CSVFieldMetadata<String,?>[] fieldConfs = metadata.getFieldConfigurations();
        if( fieldConfs == null || fieldConfs.length < 1 )
            return new Integer[0];
        
        /* We check the consistency of the configuration and the source CSV. */
        final boolean useColumnNames = metadata.isUseColumnNames();
        if( useColumnNames && header == null )
        {
              
            if( metadata.isReadHeader() )
                logger.error( "The flag 'useColumnNames' is true but there is no header to match. Check the CSV source to be not empty" );
            else
                logger.error( "The configuration 'useColumnNames=true' and 'readHeader=false' are inconsistent. It is not possible to use column names without any matching header." );
              
            throw new CSVConfigurationException( "Unable to use column names without any matching header" );
              
        }
        
        if( metadata.isUseColumnNames() )
        {
            /*
             * If the flag 'readHeader' in the configuration is true
             * we expect the CSV source to have an header and we need
             * to match the information in the configuration with the
             * one in the header. 
             */
            return buildColumnMappingUsingColumnNames( fieldConfs, header );
        }
        else
        {
            /*
             * If the flag 'readHeader' in the configuration is false
             * we the only available information is the one in the
             * configuration so we use it to build the processors.
             */            
            return buildColumnMappingUsingIndexes( fieldConfs );
        }
        
    }

    
    /**
     * Builds the column mapping needed to elaborate the CSV source
     * using the column indexes to identify the fields.
     * 
     * @param fieldConfs array of field configurations.
     * @return the column mapping needed to elaborate the CSV source fields.
     */
    private Integer[] buildColumnMappingUsingIndexes( final CSVFieldMetadata<String,?>[] fieldConfs )
    {
        
        int[] indexes = new int[fieldConfs.length];
        for( int i = 0; i < fieldConfs.length; ++i )
        {
            try{
                
                indexes[i] = Integer.parseInt( fieldConfs[i].getMappingDescriptor().getColumnKey() );
                
            }catch( Exception ex )
            {
                logger.error( "Unable to get column index", ex );
                throw new CSVConfigurationException( fieldConfs[i].getMappingDescriptor().getColumnKey() + " is not a valid column index", ex );
            }
        }
        
        int maxIndex = -1;
        for( int i = 0; i < indexes.length; ++i )
            if( indexes[i] > maxIndex )
                maxIndex = indexes[i];
        
        final Integer[] columnMapping = new Integer[maxIndex + 1];
                
        int columnIndex;
        for( int i = 0; i < fieldConfs.length; ++i )
        {
            columnIndex = indexes[i];
            
            if( columnMapping[columnIndex] != null )
                throw new CSVConfigurationException( "Multiple configuration for the same column " + columnIndex );
            
            columnMapping[columnIndex] = i;
        }
        
        return columnMapping;
        
    }
   
    /**
     * Builds the column  mapping needed to elaborate the CSV source
     * using the column names to identify the fields.
     * 
     * @param fieldConfs array of field configurations.
     * @param header     the CSV source header.
     * @return the column mapping needed to elaborate the CSV source fields.
     */
    private Integer[] buildColumnMappingUsingColumnNames( final CSVFieldMetadata<String,?>[] fieldConfs, final String[] header )
    {
        
        /* We create a map that allows to find the processor by column name. */
        String columnName = null;
        final Map<String,Integer> namedColumnMap = new HashMap<String,Integer>();
        for( int i = 0; i < fieldConfs.length; ++i )
        {
            columnName = fieldConfs[i].getMappingDescriptor().getColumnKey();
            if( namedColumnMap.containsKey(columnName) )
                throw new CSVConfigurationException( "Multiple configuration for the same column " + columnName );
            
            namedColumnMap.put( columnName, i );
        }
        
        final Integer[] columnMapping = new Integer[header.length];

        /* We fill the processors by name. */
        for( int i = 0; i < columnMapping.length; ++i )
            columnMapping[i] = namedColumnMap.get( header[i] );
        
        return columnMapping;
                
    }
    
}