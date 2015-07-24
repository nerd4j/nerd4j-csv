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
package org.nerd4j.csv.conf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.nerd4j.csv.conf.mapping.CSVConfiguration;
import org.nerd4j.csv.conf.mapping.ann.AnnotatedConfigurationFactory;
import org.nerd4j.csv.conf.mapping.xml.XMLConfiguration;
import org.nerd4j.csv.conf.mapping.xml.XMLConfigurationFactory;
import org.nerd4j.csv.exception.CSVConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents the factory able to create the configurations
 * parsing the given Java Beans of XML sources.
 * 
 * @author Nerd4j Team
 */
public final class CSVConfigurationFactory
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVConfigurationFactory.class );

    
    /**
     * Default constructor.
     * 
     */
    public CSVConfigurationFactory()
    {
        
        super();
        
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * Returns the {@link CSVConfiguration} parsed from the given
     * annotation sources.
     * 
     * @param annSources the classes containing the annotations to parse.
     * @return a new {@link CSVConfiguration}.
     */
    public CSVConfiguration getCSVConfiguration( Class<?>... annSources )
    {
        
    	if ( annSources == null || annSources.length < 1 )
            throw new NullPointerException( "At least one annotated bean is required" );
    	
        try {
        
            final CSVConfiguration csvConf = loadFromAnnotations( annSources );
            return csvConf;
        
        } catch( Exception ex )
        {
            throw new CSVConfigurationException( "Unable to build the CSV configuration due to ", ex );
        }
        
    }
    
    /**
     * Returns the {@link CSVConfiguration} parsed from the given XML
     * and annotation sources.
     * 
     * @param xmlSource the XML source to parse.
     * @param annSources the classes containing the annotations to parse.
     * @return a new {@link CSVConfiguration}.
     */
    public CSVConfiguration getCSVConfiguration( Reader xmlSource, Class<?>... annSources )
    {
        
        if ( xmlSource == null )
            throw new NullPointerException( "Invalid null configuration source" );
        
        try {
            
            final CSVConfiguration csvConf = loadFromAnnotations( annSources );
            final XMLConfiguration xmlConf = XMLConfigurationFactory.load( xmlSource );
            XMLConfigurationFactory.merge( xmlConf, csvConf );
            
            return csvConf;
            
        } catch( Exception ex )
        {
            throw new CSVConfigurationException( "Unable to build the CSV configuration due to ", ex );
        }
        
    }
    
    /**
     * Returns the {@link CSVConfiguration} parsed from the given source.
     * 
     * @param xmlSource    the source to parse.
     * @param charset      character set for read the configuration source.
     * @param annSources   the classes containing the annotations to parse.
     * @return a new {@link CSVConfiguration}.
     */
    public CSVConfiguration getCSVConfiguration( InputStream xmlSource, Charset charset, Class<?>... annSources )
    {
        
    	if ( xmlSource == null )
            throw new NullPointerException( "Invalid null configuration source" );
    	
        if ( charset == null )
            throw new NullPointerException( "Invalid null configuration character set" );
        
        final InputStreamReader reader = new InputStreamReader( xmlSource, charset );
        
        return getCSVConfiguration( reader, annSources );
        
    }
    
    /**
     * Returns the {@link CSVConfiguration} parsed from the given source.
     * 
     * @param xmlSource  the source to parse.
     * @param annSources the classes containing the annotations to parse.
     * @return a new {@link CSVConfiguration}.
     */
    public CSVConfiguration getCSVConfiguration( InputStream xmlSource, Class<?>... annSources )
    {
    	
    	return getCSVConfiguration( xmlSource, Charset.defaultCharset(), annSources );
    	
    }
    
    /**
     * Returns the {@link CSVConfiguration} parsed from the given source.
     * 
     * @param xmlSource    the source to parse.
     * @param charset      character set for read the configuration source.
     * @param annSources   the classes containing the annotations to parse.
     * @return a new {@link CSVConfiguration}.
     * 
     * @throws FileNotFoundException if configuation source cannot be read.
     */
    public CSVConfiguration getCSVConfiguration( File xmlSource, Charset charset, Class<?>... annSources )
    throws FileNotFoundException
    {
        
    	if ( xmlSource == null )
            throw new NullPointerException( "Invalid null configuration source" );
    	
        if ( charset == null )
            throw new NullPointerException( "Provided a null character set" );
        
        logger.info( "Generating a new configuration from config file {}", xmlSource.getName() );
        
        /*
         * Buffer and resource closing are handled only here. For InputStream
         * and Reader implementations will be user task to close/buffer his own
         * resources.
         */
        
        final FileInputStream stream = new FileInputStream( xmlSource );
        final BufferedInputStream buffered = new BufferedInputStream( stream );
        
        try
        {
            
            return getCSVConfiguration( buffered, charset, annSources );
            
        } finally
        {
            
            try { buffered.close(); }
            catch ( Exception e ) { /* Soak */ }
            
        }
        
    }
    
    /**
     * Returns the {@link CSVConfiguration} parsed from the given source.
     * 
     * @param xmlSource  the source to parse.
     * @param annSources the classes containing the annotations to parse.
     * @return a new {@link CSVConfiguration}.
     * 
     * @throws FileNotFoundException if configuation source cannot be read.
     */
    public CSVConfiguration getCSVConfiguration( File xmlSource, Class<?>... annSources )
    throws FileNotFoundException
    {
    	
    	return getCSVConfiguration( xmlSource, Charset.defaultCharset(), annSources );
    	
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * Parses each given class, loads all defined annotations
     * and merges all the configurations into the returned
     * {@link CSVConfiguration}.
     * 
     * @param configSources the source classes to parse.
     * @return the resulting merged configuration.
     */
    private CSVConfiguration loadFromAnnotations( Class<?>[] configSources )
    {
        
        final CSVConfiguration configTarget = new CSVConfiguration();
        for( Class<?> configSource : configSources )
            AnnotatedConfigurationFactory.merge( configSource, configTarget );
                
        return configTarget;
                
    }
    
}