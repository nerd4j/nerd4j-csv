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
package org.nerd4j.csv;


import org.nerd4j.csv.conf.CSVMetadataBuilder;
import org.nerd4j.csv.conf.CSVMetadataRegister;
import org.nerd4j.csv.conf.mapping.CSVConfiguration;
import org.nerd4j.csv.conf.mapping.CSVFormatterConf;
import org.nerd4j.csv.conf.mapping.CSVParserConf;
import org.nerd4j.csv.conf.mapping.CSVReaderConf;
import org.nerd4j.csv.conf.mapping.CSVWriterConf;
import org.nerd4j.csv.formatter.CSVFormatterFactory;
import org.nerd4j.csv.formatter.CSVFormatterMetadata;
import org.nerd4j.csv.parser.CSVParserFactory;
import org.nerd4j.csv.parser.CSVParserMetadata;
import org.nerd4j.csv.reader.CSVReader;
import org.nerd4j.csv.reader.CSVReaderFactory;
import org.nerd4j.csv.reader.CSVReaderFactoryImpl;
import org.nerd4j.csv.reader.CSVReaderMetadataFactory;
import org.nerd4j.csv.registry.CSVRegistry;
import org.nerd4j.csv.writer.CSVWriter;
import org.nerd4j.csv.writer.CSVWriterFactory;
import org.nerd4j.csv.writer.CSVWriterFactoryImpl;
import org.nerd4j.csv.writer.CSVWriterMetadataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents the factory able to create the
 * {@link CSVReaderFactory}s and {@link CSVWriterFactory}s
 * needed to build the actual {@link CSVReader}s and
 * {@link CSVWriter}s.
 * 
 * @author Nerd4j Team
 */
public final class CSVFactory
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVFactory.class );
    
    /** The registry used to retrieve the components used to build the CSV handlers. */
    private CSVRegistry registry;
    
    /** The CSV configuration to use. */
    private final CSVConfiguration configuration;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param configuration the CSV configuration to load.
     */
    public CSVFactory( CSVConfiguration configuration )
    {
        
        super();
        
        this.registry = new CSVRegistry();
        this.configuration = configuration;
        
        /* First of all we register all the specified items. */
        logger.debug( "Proceed to register all the specified items" );
        CSVMetadataRegister.register( configuration.getRegister(), registry );
        
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * Returns the {@link CSVParserFactory} related to the given name.
     * 
     * @param name the name of the requested bean.
     * @return a new {@link CSVParserFactory}.
     */
    public CSVParserFactory getCSVParserFactory( String name )
    {
        
        if( name == null || name.isEmpty() )
            throw new NullPointerException( "Unable to locate the parser factory without the related name" );

        final CSVParserConf parserConf = configuration.getParsers().get( name );
        if( parserConf == null )
            throw new NullPointerException( "There is no csv parser factory configured with the name " + name );
        
        final CSVParserMetadata parserConfiguration =  CSVMetadataBuilder.build( parserConf );
        return new CSVParserFactory( parserConfiguration );
        
    }
    
    /**
     * Returns the {@link CSVFormatterFactory} related to the given name.
     * 
     * @param name the name of the requested bean.
     * @return a new {@link CSVFormatterFactory}.
     */
    public CSVFormatterFactory getCSVFormatterFactory( String name )
    {
        
        if( name == null || name.isEmpty() )
            throw new NullPointerException( "Unable to locate the formatter factory without the related name" );
        
        final CSVFormatterConf formatterConf = configuration.getFormatters().get( name );
        if( formatterConf == null )
            throw new NullPointerException( "There is no csv formatter factory configured with the name " + name );
        
        final CSVFormatterMetadata formatterConfiguration =  CSVMetadataBuilder.build( formatterConf );
        return new CSVFormatterFactory( formatterConfiguration );
        
    }
    
    /**
     * Returns the {@link CSVReaderFactory} related to the given name.
     * 
     * @param name the name of the requested bean.
     * @return a new {@link CSVReaderFactory}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <M> CSVReaderFactory<M> getCSVReaderFactory( String name )
    {
        
        if( name == null || name.isEmpty() )
            throw new NullPointerException( "Unable to locate the reader factory without the related name" );
        
        final CSVReaderConf readerConf = configuration.getReaders().get( name );
        if( readerConf == null )
            throw new NullPointerException( "There is no csv reader factory configured with the name " + name );
        
        final CSVReaderMetadataFactory readerMetadataFactory =  new CSVReaderMetadataFactory( readerConf, configuration, registry );
        return new CSVReaderFactoryImpl( readerMetadataFactory );
        
    }
    
    
    /**
     * Returns the {@link CSVWriterFactory} related to the given name.
     * 
     * @param name the name of the requested bean.
     * @return a new {@link CSVWriterFactory}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <M> CSVWriterFactory<M> getCSVWriterFactory( String name )
    {
        
        if( name == null || name.isEmpty() )
            throw new NullPointerException( "Unable to locate the writer factory without the related name" );
        
        final CSVWriterConf writerConf = configuration.getWriters().get( name );
        if( writerConf == null )
            throw new NullPointerException( "There is no csv writer factory configured with the name " + name );
        
        final CSVWriterMetadataFactory writerMetadataFactory =  new CSVWriterMetadataFactory( writerConf, configuration, registry );
        return new CSVWriterFactoryImpl( writerMetadataFactory );
        
    }
    
}