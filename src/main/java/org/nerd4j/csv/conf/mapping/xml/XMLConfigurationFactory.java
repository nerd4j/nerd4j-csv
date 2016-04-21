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
package org.nerd4j.csv.conf.mapping.xml;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.nerd4j.csv.conf.mapping.CSVCharSetConf;
import org.nerd4j.csv.conf.mapping.CSVColumnConf;
import org.nerd4j.csv.conf.mapping.CSVConfiguration;
import org.nerd4j.csv.conf.mapping.CSVFieldConverterConf;
import org.nerd4j.csv.conf.mapping.CSVFieldProcessorConf;
import org.nerd4j.csv.conf.mapping.CSVFieldValidatorConf;
import org.nerd4j.csv.conf.mapping.CSVFormatterConf;
import org.nerd4j.csv.conf.mapping.CSVModelBinderConf;
import org.nerd4j.csv.conf.mapping.CSVParserConf;
import org.nerd4j.csv.conf.mapping.CSVReaderConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterConverterConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterProcessorConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterProviderConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterTypesConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterValidatorConf;
import org.nerd4j.csv.conf.mapping.CSVWriterConf;
import org.nerd4j.csv.exception.CSVConfigurationException;
import org.nerd4j.dependency.DefaultDependencyResolver;
import org.nerd4j.dependency.DependencyException;
import org.nerd4j.dependency.DependencyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The aim of this factory is to load an {@link XMLConfiguration}
 * from a given source and merge a given {@link XMLConfiguration}
 * into a given {@link CSVConfiguration}.
 * 
 * @author Nerd4j Team
 */
public class XMLConfigurationFactory
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( XMLConfigurationFactory.class );

    
    /**
     * Default constructor.
     * 
     */
    public XMLConfigurationFactory()
    {
        
        super();
        
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * Returns the {@link XMLConfiguration} parsed from the given source.
     * 
     * @param configSource the source to parse.
     * @return a new {@link XMLConfiguration}.
     */
    public static XMLConfiguration load( final Reader configSource )
    {
        
        try{
        
        	if( configSource == null )
        	{
        		logger.error( "Invalid configuration source null." );
                throw new NullPointerException( "Invalid null configuration source" );
        	}
            
            final JAXBContext jaxBContext = JAXBContext.newInstance( XMLConfiguration.class );
            final Unmarshaller unmarshaller = jaxBContext.createUnmarshaller();
            
            return (XMLConfiguration) unmarshaller.unmarshal( configSource );
        
        }catch( Exception ex )
        {
        	logger.error( "An error occurred during configuration loading.", ex );
            throw new CSVConfigurationException( "Unable to load the XML configuration due to ", ex );
        }
        
    }
    
    
    /**
     * Merges the given {@link XMLConfiguration} into the given {@link CSVConfiguration}.
     * 
     * @param xmlConfiguration the source XML configuration.
     * @param csvConfiguration the target CSV configuration.
     */
    public static void merge( final XMLConfiguration xmlConfiguration, final CSVConfiguration csvConfiguration )
    {
    	
    	if( xmlConfiguration == null )
    		throw new NullPointerException( "The provided XML configuration can't be null" );
    	
    	if( csvConfiguration == null )
    		throw new NullPointerException( "The provided CSV configuration can't be null" );
    	
    	try{
    	
       		merge( xmlConfiguration.getRegister(), csvConfiguration.getRegister() );
       		
       		mergeParsers( xmlConfiguration.getParsers(), csvConfiguration.getParsers() );
       		mergeFormatters( xmlConfiguration.getFormatters(), csvConfiguration.getFormatters() );
        	
       		mergeReaders( xmlConfiguration.getReaders(), csvConfiguration.getReaders() );
       		mergeWriters( xmlConfiguration.getWriters(), csvConfiguration.getWriters() );
   		
    	}catch( Exception ex )
    	{
    	    logger.error( "An error occurred during configuration merge.", ex );
    	    throw new CSVConfigurationException( "Unable to merge the XML configuration into the CSV due to ", ex );
    	}
   		
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void mergeReaders( final List<XMLReaderConf> xmlConfList, final Map<String,CSVReaderConf> csvConfMap )
    throws CloneNotSupportedException
    {
    	
    	if( checkNullConsistence(xmlConfList, csvConfMap, "readers") ) return;  
    	
    	final List<XMLReaderConf> dependencySafeList = resolveDependencies( xmlConfList, "reader" );
    	
    	String inherit;
    	String xmlConfName;
    	CSVReaderConf csvConf;
    	for( XMLReaderConf xmlConf : dependencySafeList )
    	{
    	    
    	    xmlConfName = xmlConf.getName();
            if( csvConfMap.containsKey(xmlConfName) )
                throw new IllegalStateException( "There are two reader configurations with name " + xmlConfName );

            /* We reset the csvConf variable. */
            csvConf = null;
            
            inherit = xmlConf.getInherit();
            if( inherit != null && ! inherit.isEmpty() )
            {
                
                final CSVReaderConf dependency = csvConfMap.get( inherit );
                if( dependency == null )
                  throw new DependencyException( "The reader with name " + xmlConfName + " inherits from the unexisting configuration " + inherit );
                
                csvConf = dependency.clone();
                
            }
            
            if( csvConf == null )
                csvConf = new CSVReaderConf();
            
            csvConf.setName( xmlConfName );         
            csvConfMap.put( xmlConfName, csvConf );
            
            merge( xmlConf, csvConf );    
    	}
    	
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void mergeWriters( final List<XMLWriterConf> xmlConfList, final Map<String,CSVWriterConf> csvConfMap )
    throws CloneNotSupportedException
    {
        
        if( checkNullConsistence(xmlConfList, csvConfMap, "writers") ) return;  
        
        final List<XMLWriterConf> dependencySafeList = resolveDependencies( xmlConfList, "writer" );
        
        String inherit;
        String xmlConfName;
        CSVWriterConf csvConf;
        for( XMLWriterConf xmlConf : dependencySafeList )
        {
            
            xmlConfName = xmlConf.getName();
            if( csvConfMap.containsKey(xmlConfName) )
                throw new IllegalStateException( "There are two writer configurations with name " + xmlConfName );

            /* We reset the csvConf variable. */
            csvConf = null;
            
            inherit = xmlConf.getInherit();
            if( inherit != null && ! inherit.isEmpty() )
            {
                
                final CSVWriterConf dependency = csvConfMap.get( inherit );
                if( dependency == null )
                  throw new DependencyException( "The writer with name " + xmlConfName + " inherits from the unexisting configuration " + inherit );
                
                csvConf = dependency.clone();
                
            }
            
            if( csvConf == null )
                csvConf = new CSVWriterConf();
            
            csvConf.setName( xmlConfName );         
            csvConfMap.put( xmlConfName, csvConf );
            
            merge( xmlConf, csvConf );    
        }    
        
    }
        
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final XMLReaderConf xmlConf, final CSVReaderConf csvConf )
    {
    	
    	if( checkNullConsistence(xmlConf, csvConf, "reader") ) return;

    	if( xmlConf.getReadHeader() != null ) csvConf.setReadHeader( xmlConf.getReadHeader() );
    	if( xmlConf.getUseColumnNames() != null ) csvConf.setUseColumnNames( xmlConf.getUseColumnNames() );
    	if( xmlConf.getAcceptIncompleteRecords() != null ) csvConf.setAcceptIncompleteRecords( xmlConf.getAcceptIncompleteRecords() );
    	
   	    if( xmlConf.getParserRef() != null ) 
        {
            csvConf.setParser( null );
            csvConf.setParserRef( xmlConf.getParserRef() );
        }

        if( xmlConf.getParser() != null )
        {
            csvConf.setParserRef( null );
            csvConf.setParser( new CSVParserConf() );
            merge( xmlConf.getParser(), csvConf.getParser() );
        }
    	
    	overwrite( xmlConf.getModelBinder(), csvConf.getModelBinder() );
    	mergeColumns( xmlConf.getColumns(), csvConf.getColumns() );
        
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final XMLWriterConf xmlConf, final CSVWriterConf csvConf )
    {
        
        if( checkNullConsistence(xmlConf, csvConf, "writer") ) return;
        
        if( xmlConf.getWriteHeader() != null ) csvConf.setWriteHeader( xmlConf.getWriteHeader() );
        
        if( xmlConf.getFormatterRef() != null ) 
        {
            csvConf.setFormatter( null );
            csvConf.setFormatterRef( xmlConf.getFormatterRef() );
        }

        if( xmlConf.getFormatter() != null )
        {
            csvConf.setFormatterRef( null );
            csvConf.setFormatter( new CSVFormatterConf() );
            merge( xmlConf.getFormatter(), csvConf.getFormatter() );
        }
        
        overwrite( xmlConf.getModelBinder(), csvConf.getModelBinder() );
        mergeColumns( xmlConf.getColumns(), csvConf.getColumns() );
        
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void mergeParsers( final List<XMLParserConf> xmlConfList, final Map<String,CSVParserConf> csvConfMap )
    {
    	
    	if( checkNullConsistence(xmlConfList, csvConfMap, "parsers") ) return;  
    	
    	String xmlConfName;
    	CSVParserConf csvConf;
    	for( XMLParserConf xmlConf : xmlConfList )
    	{
    		
    		if( xmlConf == null ) continue;
    		
    		xmlConfName = xmlConf.getName();
    		if( xmlConfName == null ) continue;
    		
    		csvConf = csvConfMap.get( xmlConfName );
    		if( csvConf == null )
    		{
    			csvConf = new CSVParserConf();
    			csvConf.setName( xmlConfName );
    			csvConfMap.put( xmlConfName, csvConf );
    		}
    		else if( ! xmlConfName.equals(csvConf.getName()) )
    			throwIllegalStateException( xmlConfName, csvConf.getName() );
    	
    		merge( xmlConf, csvConf );
    		
    	}
    	
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void mergeFormatters( final List<XMLFormatterConf> xmlConfList, final Map<String,CSVFormatterConf> csvConfMap )
    {
    	
    	if( checkNullConsistence(xmlConfList, csvConfMap, "formatters") ) return;  
    	
    	String xmlConfName;
    	CSVFormatterConf csvConf;
    	for( XMLFormatterConf xmlConf : xmlConfList )
    	{
    		
    		if( xmlConf == null ) continue;
    		
    		xmlConfName = xmlConf.getName();
    		if( xmlConfName == null ) continue;
    		
    		csvConf = csvConfMap.get( xmlConfName );
    		if( csvConf == null )
    		{
    			csvConf = new CSVFormatterConf();
    			csvConf.setName( xmlConfName );
    			csvConfMap.put( xmlConfName, csvConf );
    		}
    		else if( ! xmlConfName.equals(csvConf.getName()) )
    			throwIllegalStateException( xmlConfName, csvConf.getName() );
    		
    		merge( xmlConf, csvConf );
    		
    	}
    	
    }
    
    /**
     * Merges the given CSV configuration with the data in the given XML configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final XMLParserConf xmlConf, final CSVParserConf csvConf )
    {
        
    	merge( (XMLCharSetConf) xmlConf, (CSVCharSetConf) csvConf, "parser" );
        
    	if( xmlConf.isLazyQuotes() != null ) csvConf.setLazyQuotes( xmlConf.isLazyQuotes() );
    	if( xmlConf.getCharsToIgnore() != null ) csvConf.setCharsToIgnore( xmlConf.getCharsToIgnore() );
    	if( xmlConf.getCharsToIgnoreAroundFields() != null ) csvConf.setCharsToIgnoreAroundFields( xmlConf.getCharsToIgnoreAroundFields() );
    	if( xmlConf.isMatchRecordSeparatorExactSequence() != null ) csvConf.setMatchRecordSeparatorExactSequence( xmlConf.isMatchRecordSeparatorExactSequence() );
        
    }

    /**
     * Merges the given CSV configuration with the data in the given XML configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final XMLFormatterConf xmlConf, final CSVFormatterConf csvConf )
    {
        
    	merge( (XMLCharSetConf) xmlConf, (CSVCharSetConf) csvConf, "formatter" );
        
    	if( xmlConf.getCharsToEscape() != null ) csvConf.setCharsToEscape( xmlConf.getCharsToEscape() );
    	if( xmlConf.getCharsThatForceQuoting() != null ) csvConf.setCharsThatForceQuoting( xmlConf.getCharsThatForceQuoting() );
        
    }
    
    /**
     * Merges the given CSV configuration with the data in the given XML configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     * @param confType the configuration type.
     */
    private static void merge( final XMLCharSetConf xmlConf, final CSVCharSetConf csvConf, String confType )
    {
        
        if( checkNullConsistence(xmlConf, csvConf, confType) ) return;
        
        if( xmlConf.getQuoteChar() != null ) csvConf.setQuoteChar( xmlConf.getQuoteChar() );
        if( xmlConf.getEscapeChar() != null ) csvConf.setEscapeChar( xmlConf.getEscapeChar() );
        if( xmlConf.getFieldSeparator() != null ) csvConf.setFieldSeparator( xmlConf.getFieldSeparator() );
        if( xmlConf.getRecordSeparator() != null ) csvConf.setRecordSeparator( xmlConf.getRecordSeparator() );
//        if( xmlConf.getRecordSeparator1() != null ) csvConf.setRecordSeparator1( xmlConf.getRecordSeparator1() );
//        if( xmlConf.getRecordSeparator2() != null ) csvConf.setRecordSeparator2( xmlConf.getRecordSeparator2() );
        
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final XMLRegisterConf xmlConf, final CSVRegisterConf csvConf )
    {
    	
    	if( checkNullConsistence(xmlConf, csvConf, "register") ) return;
    	
    	merge( xmlConf.getTypes(), csvConf.getTypes() );
    	mergeValidators( xmlConf.getValidators(), csvConf.getValidators() );
    	mergeConverters(xmlConf.getConverters(), csvConf.getConverters() );
    	mergeProcessors( xmlConf.getProcessors(), csvConf.getProcessors() );
    	
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final XMLRegisterTypesConf xmlConf, final CSVRegisterTypesConf csvConf )
    {
    	
        if( checkNullConsistence(xmlConf, csvConf, "register types") ) return;
        
    	mergeProviders( xmlConf.getValidatorProviders(), csvConf.getValidatorProviders(), "validato" );
    	mergeProviders( xmlConf.getConverterProviders(), csvConf.getConverterProviders(), "converter" );
    	mergeProviders( xmlConf.getCsvToModelProviders(), csvConf.getCsvToModelProviders(), "csv to model" );
    	mergeProviders( xmlConf.getModelToCSVProviders(), csvConf.getModelToCSVProviders(), "model to csv" );
    	
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     * @param providerType the type of provider to merge.
     */
    private static void mergeProviders( final List<XMLRegisterProviderConf> xmlConfList,
    		                            final Map<String,CSVRegisterProviderConf> csvConfMap,
    		                            final String providerType )
    {
    	
    	if( checkNullConsistence(xmlConfList, csvConfMap, providerType + " providers") ) return;  
    	
    	String xmlConfName;
    	CSVRegisterProviderConf csvConf;
    	for( XMLRegisterProviderConf xmlConf : xmlConfList )
    	{
    		
    		if( xmlConf == null ) continue;
    		
    		xmlConfName = xmlConf.getTypeName();
    		if( xmlConfName == null ) continue;
    		
    		csvConf = csvConfMap.get( xmlConfName );
    		if( csvConf == null )
    		{
    			csvConf = new CSVRegisterProviderConf();
    			csvConf.setTypeName( xmlConfName );
    			csvConfMap.put( xmlConfName, csvConf );
    		}
    		else if( ! xmlConfName.equals(csvConf.getTypeName()) )
    			throwIllegalStateException( xmlConfName, csvConf.getTypeName() );
    	
    		csvConf.setProviderClass( xmlConf.getProviderClass() );
    		
    	}
    	
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlColumnList the source XML columns configuration.
     * @param csvNamedColumnMap the target CSV named columns configuration.
     * @param csvIndexedColumnMap the target CSV indexed columns configuration.
     */
    private static void mergeColumns( final List<XMLColumnConf> xmlConfList,
                                     final Map<String,CSVColumnConf> csvConfMap )
    {
    	
        if( checkNullConsistence(xmlConfList, csvConfMap, "columns") ) return;
        
    	String  xmlColumnName;
    	CSVColumnConf csvColumn;
    	for( XMLColumnConf xmlColumn : xmlConfList )
    	{
    		
    		if( xmlColumn == null ) continue;
       				
       		xmlColumnName = xmlColumn.getName();
       		
       		if( xmlColumnName == null || xmlColumnName.isEmpty() )
       		    throw new NullPointerException( "Invalid configuration, column name is not defined" );
       		
   			csvColumn = csvConfMap.get( xmlColumnName );
       		if( csvColumn == null )
       		{
       			csvColumn = new CSVColumnConf();
       			csvColumn.setName( xmlColumnName );
       			csvConfMap.put( xmlColumnName, csvColumn );
       		}
       		
       		merge( xmlColumn, csvColumn );
               	    		
    	}
    	
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void mergeValidators( final List<XMLRegisterValidatorConf> xmlConfList, final Map<String,CSVRegisterValidatorConf> csvConfMap )
    {
    	
    	if( checkNullConsistence(xmlConfList, csvConfMap, " register validators") ) return;  	
    	
    	String xmlConfName;
    	CSVRegisterValidatorConf csvConf;
    	for( XMLRegisterValidatorConf xmlConf : xmlConfList )
    	{
    		
    		if( xmlConf == null ) continue;
    		
    		xmlConfName = xmlConf.getName();
    		if( xmlConfName == null ) continue;
    		
    		csvConf = csvConfMap.get( xmlConfName );
    		if( csvConf == null )
    		{
    			csvConf = new CSVRegisterValidatorConf();
    			csvConf.setName( xmlConfName );    			
    			csvConfMap.put( xmlConfName, csvConf );
    		}
    		else if( ! xmlConfName.equals(csvConf.getName()) )
    			throwIllegalStateException( xmlConfName, csvConf.getName() );
    		
    		overwrite( xmlConf, csvConf );
    		
    	}
    	
    }

    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void mergeConverters( final List<XMLRegisterConverterConf> xmlConfList, final Map<String,CSVRegisterConverterConf> csvConfMap )
    {
    	
    	if( checkNullConsistence(xmlConfList, csvConfMap, " register converters") ) return;
    	
    	String xmlConfName;
    	CSVRegisterConverterConf csvConf;
    	for( XMLRegisterConverterConf xmlConf : xmlConfList )
    	{
    		
    		if( xmlConf == null ) continue;
    		
    		xmlConfName = xmlConf.getName();
    		if( xmlConfName == null ) continue;
    		
    		csvConf = csvConfMap.get( xmlConfName );
    		if( csvConf == null )
    		{
    			csvConf = new CSVRegisterConverterConf();
    			csvConf.setName( xmlConfName );    			
    			csvConfMap.put( xmlConfName, csvConf );
    		}
    		else if( ! xmlConfName.equals(csvConf.getName()) )
    			throwIllegalStateException( xmlConfName, csvConf.getName() );
    		
    		overwrite( xmlConf, csvConf );
    		
    	}
    	
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void mergeProcessors( final List<XMLRegisterProcessorConf> xmlConfList, final Map<String,CSVRegisterProcessorConf> csvConfMap )
    {
    	
    	if( checkNullConsistence(xmlConfList, csvConfMap, " register processors") ) return;
    	
    	String xmlConfName;
    	CSVRegisterProcessorConf csvConf;
    	for( XMLRegisterProcessorConf xmlConf : xmlConfList )
    	{
    		
    		if( xmlConf == null ) continue;
    		
    		xmlConfName = xmlConf.getName();
    		if( xmlConfName == null ) continue;
    		
    		csvConf = csvConfMap.get( xmlConfName );
    		if( csvConf == null )
    		{
    			csvConf = new CSVRegisterProcessorConf();
    			csvConf.setName( xmlConfName );    			
    			csvConfMap.put( xmlConfName, csvConf );
    		}
    		else if( ! xmlConfName.equals(csvConf.getName()) )
    			throwIllegalStateException( xmlConfName, csvConf.getName() );
    		
    		merge( xmlConf, csvConf );
    		
    	}
    	
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final XMLColumnConf xmlConf, final CSVColumnConf csvConf )
    {
    	
    	if( checkNullConsistence(xmlConf, csvConf, "column") ) return;

    	if( xmlConf.getMapping() != null ) csvConf.setMapping( xmlConf.getMapping() );
    	if( xmlConf.getOptional() != null ) csvConf.setOptional( xmlConf.getOptional() );
    	
    	if( xmlConf.getProcessorRef() != null )
    	{
    	    csvConf.setProcessor( null );
    	    csvConf.setProcessorRef( xmlConf.getProcessorRef() );
    	}

    	if( xmlConf.getProcessor() != null )
    	{
    	    csvConf.setProcessorRef( null );
    		if( csvConf.getProcessor() == null ) csvConf.setProcessor( new CSVFieldProcessorConf() );
    		merge( xmlConf.getProcessor(), csvConf.getProcessor() );
    	}
    	
    }
    
    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final XMLFieldProcessorConf xmlConf, final CSVFieldProcessorConf csvConf )
    {
    	
    	if( checkNullConsistence(xmlConf, csvConf, " processor") ) return;
    	
    	if( xmlConf.getConverterRef() != null )
    	{
    	    csvConf.setConverter( null );
    	    csvConf.setConverterRef( xmlConf.getConverterRef() );
    	}
    	
    	if( xmlConf.getPreconditionRef() != null )
    	{
    	    csvConf.setPrecondition( null );
    	    csvConf.setPreconditionRef( xmlConf.getPreconditionRef() );
    	}
    	
    	if( xmlConf.getPostconditionRef() != null )
    	{
    	    csvConf.setPostcondition( null );
            csvConf.setPostconditionRef( xmlConf.getPostconditionRef() );
        }
    		
    	if( xmlConf.getConverter() != null )
    	{
    	    csvConf.setConverterRef( null );
    		csvConf.setConverter( new CSVFieldConverterConf() );  
    		overwrite( xmlConf.getConverter(), csvConf.getConverter() );
    	}
    		
    	if( xmlConf.getPrecondition() != null )
    	{
    	    csvConf.setPreconditionRef( null );
    		csvConf.setPrecondition( new CSVFieldValidatorConf() );  
    		overwrite( xmlConf.getPrecondition(), csvConf.getPrecondition() );
    	}
    	
    	if( xmlConf.getPostcondition() != null )
    	{
    	    csvConf.setPostconditionRef( null );
    		csvConf.setPostcondition( new CSVFieldValidatorConf() );  
    		overwrite( xmlConf.getPostcondition(), csvConf.getPostcondition() );
    	}
    	
    }
    
    /**
     * Overwrites the given CSV configuration with the given XML configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void overwrite( final XMLFieldValidatorConf xmlConf, final CSVFieldValidatorConf csvConf )
    {

    	if( checkNullConsistence(xmlConf, csvConf, "validator") ) return;
    	
    	csvConf.setType( xmlConf.getType() );
    	overwriteParams( xmlConf.getParams(), csvConf.getParams(), "validator" );
    	
    }

    /**
     * Overwrites the given CSV configuration with the given XML configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void overwrite( final XMLFieldConverterConf xmlConf, final CSVFieldConverterConf csvConf )
    {
    	
    	if( checkNullConsistence(xmlConf, csvConf, "converter") ) return;
    	
    	csvConf.setType( xmlConf.getType() );
    	overwriteParams( xmlConf.getParams(), csvConf.getParams(), "converter" );
    	
    }
    
    /**
     * Overwrites the given CSV configuration with the given XML configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     */
    private static void overwrite( final XMLModelBinderConf xmlConf, final CSVModelBinderConf csvConf )
    {
    	
    	if( checkNullConsistence(xmlConf, csvConf, "model binder") ) return;
    	
    	csvConf.setType( xmlConf.getType() );
    	overwriteParams( xmlConf.getParams(), csvConf.getParams(), "model binder" );
    	
    }
    
    /**
     * Overwrites the given CSV configuration with the given XML configuration.
     * 
     * @param xmlConf the source XML configuration.
     * @param csvConf the target CSV configuration.
     * @param paramsType the type of parameters to merge.
     */
    private static void overwriteParams( final Map<QName,String> xmlConf, final Map<String,String> csvConf, final String paramsType )
    {
    	
        if( csvConf == null )
            throw new NullPointerException( "The parameter map for type " + paramsType + " must be not null" );
    	
        csvConf.clear();
    	
    	if( xmlConf != null )
    	    for( Map.Entry<QName,String> entry : xmlConf.entrySet() )
    	        csvConf.put( entry.getKey().getLocalPart(), entry.getValue() );
    	
    }
    
    
    /* ***************** */
    /*  UTILITY METHODS  */
    /* ***************** */
    
    
    /**
     * Returns <code>true</code> if the xmlConf is <code>null</code>.
     * Otherwise checks if the csvConf, if it is <code>null</code>
     * throws a NullPointerException.
     * 
     * @param xmlConf the XML configuration to check.
     * @param csvConf the CSV configuration to check.
     * @param confType the configuration type.
     * @return <code>true</code> if the xmlConf is <code>null</code>.
     * @throws NullPointerException if csvConf is <code>null</code>.
     */
    private static boolean checkNullConsistence( final Object xmlConf, final Object csvConf, final String confType )
    {
    	
    	if( xmlConf == null ) return true;
    	
    	if( csvConf == null )
    		throw new NullPointerException( "The " + confType + " configuration must be not null" );

    	return false;
    	
    }

    /**
     * Returns <code>true</code> if the xmlConfList is <code>null</code> or empty.
     * Otherwise checks if the csvConfMap, if it is <code>null</code>
     * throws a NullPointerException.
     * 
     * @param xmlConfList the XML configuration to check.
     * @param csvConfMap the CSV configuration to check.
     * @param confType the configuration type.
     * @return <code>true</code> if the xmlConfList is <code>null</code> or empty.
     * @throws NullPointerException if csvConfMap is <code>null</code>.
     */
    private static boolean checkNullConsistence( final List<?> xmlConfList, final Map<String,?> csvConfMap, final String confType )
    {
    	
    	if( xmlConfList == null || xmlConfList.isEmpty() ) return true;
    	
    	if( csvConfMap == null )
    		throw new NullPointerException( "The " + confType + " map must be not null" );
    	
    	return false;
    	
    }
    
    /**
     * Throws an IllegalStateException related to the fact that
     * the XML configuration key doesn't match the CSV configuration key.
     * 
     * @param xmlConfKey the XML configuration key.
     * @param csvConfKey the CSV configuration key.
     */
    private static void throwIllegalStateException( final String xmlConfKey, final String csvConfKey )
    {
    	
    	throw new IllegalStateException( "Invalid configuration: unable to merge XML comf " + xmlConfKey
    			+ " into CSV conf " + csvConfKey );
    }
    
    
    /**
     * Resolves any dependency created by the use of the tag {@code inherit} and returns
     * a list of handlers ordered in a dependency safe mode.
     *  
     * @param handlerList list of handlers to order.
     * @param handlerType type of the handlers to order.
     * @return dependency safe order.
     * @throws NullPointerException if the dependency informations are not complete.
     * @throws DependencyException if there are errors in dependency definition.
     */
    private static <H extends XMLHandlerConf> List<H> resolveDependencies( List<H> handlerList, String handlerType )    
    {
        
        final Map<String,XMLHandlerConf> handlerMap = new HashMap<String,XMLHandlerConf>( handlerList.size() );
        
        String handlerName;
        for( XMLHandlerConf handler : handlerList )
        {
            
            handlerName = handler.getName();
            if( handlerName == null || handlerName.isEmpty() )
                throw new NullPointerException( "The name is mandatory for the configuration of type " + handlerName );
            
            handlerMap.put( handlerName, handler );
            
        }
          
        String inherit;
        XMLHandlerConf dependency;
        for( XMLHandlerConf handler : handlerList )
        {
            
            inherit = handler.getInherit();
            if( inherit != null && ! inherit.isEmpty() )
            {
                dependency = handlerMap.get( inherit );
                if( dependency != null )
                    handler.setDependency( dependency );
            }
            
        }
        
        final DependencyResolver resolver = DefaultDependencyResolver.getInstance();
        final List<H> result = resolver.resolve( handlerList );
        
        return result;
        
    }
    
}