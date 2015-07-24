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

import java.util.Map;

import org.nerd4j.csv.conf.mapping.CSVColumnConf;
import org.nerd4j.csv.conf.mapping.CSVFieldConverterConf;
import org.nerd4j.csv.conf.mapping.CSVFieldProcessorConf;
import org.nerd4j.csv.conf.mapping.CSVFieldValidatorConf;
import org.nerd4j.csv.conf.mapping.CSVFormatterConf;
import org.nerd4j.csv.conf.mapping.CSVHandlerConf;
import org.nerd4j.csv.conf.mapping.CSVModelBinderConf;
import org.nerd4j.csv.conf.mapping.CSVParserConf;
import org.nerd4j.csv.conf.mapping.CSVReaderConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterConverterConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterProviderConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterTypesConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterValidatorConf;
import org.nerd4j.csv.conf.mapping.CSVWriterConf;
import org.nerd4j.csv.exception.CSVConfigurationException;


/**
 * Utility class to perform checks about the consistency
 * of the CSV configurations.
 * 
 * @author Nerd4j Team
 */
public final class CSVConfChecker
{

    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    public static void check( CSVRegisterConf configuration )
    throws CSVConfigurationException
    {
        
        if( configuration.getTypes() != null )
            check( configuration.getTypes() );
        
        if( configuration.getValidators() != null )
            for( CSVRegisterValidatorConf validatorConf : configuration.getValidators().values() )
                check( validatorConf, "validator" );
                    
        if( configuration.getConverters() != null )
            for( CSVRegisterConverterConf converterConf : configuration.getConverters().values() )
                check( converterConf, "converter" );
                    
        if( configuration.getProcessors() != null )
            for( CSVFieldProcessorConf processorConf : configuration.getProcessors().values() )
                check( processorConf );
        
    }
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    public static void check( CSVRegisterTypesConf configuration )
    throws CSVConfigurationException
    {
        
        if( configuration.getValidatorProviders() != null )
            for( CSVRegisterProviderConf validatorProviderConf : configuration.getValidatorProviders().values() )
                check( validatorProviderConf, "validator" );
                    
        if( configuration.getConverterProviders() != null )
            for( CSVRegisterProviderConf converterProviderConf : configuration.getConverterProviders().values() )
                check( converterProviderConf, "converter" );
                    
        if( configuration.getCsvToModelProviders() != null )
            for( CSVRegisterProviderConf csvToModelProviderConf : configuration.getCsvToModelProviders().values() )
                check( csvToModelProviderConf, "csv-to-model" );
                                            
        if( configuration.getModelToCSVProviders() != null )
            for( CSVRegisterProviderConf modelToCSVProviderConf : configuration.getModelToCSVProviders().values() )
                check( modelToCSVProviderConf, "model-to-csv" );
                                
    }
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    public static void check( CSVParserConf configuration )
    throws CSVConfigurationException
    {
        
        if( configuration.getFieldSeparator() == null )
            throw new CSVConfigurationException( "The 'field-sep' is mandatory for the 'csv-parser'" );
        
        if( configuration.getRecordSeparator1() == null )
            throw new CSVConfigurationException( "The 'record-sep-1' is mandatory for the 'csv-parser'" );
        
        if( configuration.getQuoteChar() == null )
            throw new CSVConfigurationException( "The 'quote' is mandatory for the 'csv-parser'" );
        
    }
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    public static void check( CSVFormatterConf configuration )
    throws CSVConfigurationException
    {
        
        if( configuration.getFieldSeparator() == null )
            throw new CSVConfigurationException( "The 'field-sep' is mandatory for the 'csv-formatter'" );
        
        if( configuration.getRecordSeparator1() == null )
            throw new CSVConfigurationException( "The 'record-sep-1' is mandatory for the 'csv-formatter'" );
        
        if( configuration.getQuoteChar() == null )
            throw new CSVConfigurationException( "The 'quote' is mandatory for the 'csv-formatter'" );
        
    }
    
    /**
     * 
     * 
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    public static void check( CSVReaderConf configuration )
    throws CSVConfigurationException
    {
        
        final String name = configuration.getName();
        if( isNullOrEmpty(name) )
            throw new CSVConfigurationException( "The name is mandatory for the 'csv-reader'" );
        
        checkDoubleConf( configuration.getParserRef(), configuration.getParser(), "csv-parser", "csv-reader" );
        check( (CSVHandlerConf) configuration, "csv-reader" );
        
    }
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    public static void check( CSVWriterConf configuration )
    throws CSVConfigurationException
    {
        
        final String name = configuration.getName();
        if( isNullOrEmpty(name) )
            throw new CSVConfigurationException( "The name is mandatory for the 'csv-writer'" );
        
        checkDoubleConf( configuration.getFormatterRef(), configuration.getFormatter(), "csv-formatter", "csv-writer" );
        check( (CSVHandlerConf) configuration, "csv-writer" );
        
    }
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @param handlerType   type of the CSV handler.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    public static void check( CSVHandlerConf configuration, String handlerType )
    throws CSVConfigurationException
    {
        
        final Map<String,CSVColumnConf> namedColumns = configuration.getColumns();
        if( namedColumns == null )
            throw new CSVConfigurationException( "The columns definifition are mandatory for '" + handlerType + "'" );
                
        final CSVModelBinderConf modelBinder = configuration.getModelBinder();
        if( modelBinder == null )
        	throw new CSVConfigurationException( "The 'model-binder' must be specified in the " + handlerType );
                
    }
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    public static void check( CSVColumnConf configuration )
    throws CSVConfigurationException
    {
        
        if( isNullOrEmpty(configuration.getName()) )
            throw new CSVConfigurationException( "Invalid configuration, the 'name' is mandatory for a 'column'" );
        
        if( isNullOrEmpty(configuration.getMapping()) )
            throw new CSVConfigurationException( "Invalid configuration, the 'mapping' is mandatory for 'column' " + configuration.getName() );
        
        checkDoubleConf( configuration.getProcessorRef(), configuration.getProcessor(), "processor", "column" );
        
    }

    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    public static void check( final CSVFieldProcessorConf configuration )
    throws CSVConfigurationException
    {
        
        final CSVFieldConverterConf converterConf = configuration.getConverter();
        checkDoubleConf( configuration.getConverterRef(), converterConf, "converter", "processor" );
        check( converterConf, "converter" );

        final CSVFieldValidatorConf preconditionConf = configuration.getPrecondition();
        checkDoubleConf( configuration.getPreconditionRef(), preconditionConf, "precondition", "processor" );
        check( preconditionConf, "precondition" );
        
        final CSVFieldValidatorConf postconditionConf = configuration.getPostcondition();
        checkDoubleConf( configuration.getPostconditionRef(), postconditionConf, "postcondition", "processor" );
        check( postconditionConf, "postcondition" );

    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    public static void check( CSVRegisterProviderConf configuration, String type )
    throws CSVConfigurationException
    {
        
        if( isNullOrEmpty(configuration.getTypeName()) )
            throw new CSVConfigurationException( "Invalid configuration, the 'type-name' is mandatory for '" + type + "'" );
        
        if( isNullOrEmpty(configuration.getProviderClass()) )
            throw new CSVConfigurationException( "Invalid configuration, the 'provider-class' is mandatory for '" + type + "'" );
        
    }
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @param fieldName     the name of the field related to the configuration.     
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    private static void check( final CSVFieldValidatorConf configuration, final String fieldName )
    throws CSVConfigurationException
    {
        
        if( configuration != null && isNullOrEmpty(configuration.getType()) )
            throw new CSVConfigurationException( "Invalid configuration, the 'type' is mandatory for '" + fieldName + "'" );
        
    }
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @param fieldName     the name of the field related to the configuration.     
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    private static void check( CSVFieldConverterConf configuration, String fieldName )
    throws CSVConfigurationException
    {
        
        if( configuration != null && isNullOrEmpty(configuration.getType()) )
            throw new CSVConfigurationException( "Invalid configuration, the 'type' is mandatory for '" + fieldName + "'" );
        
    }
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @param fieldName     the name of the field related to the configuration.     
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    private static void check( final CSVRegisterValidatorConf configuration, final String fieldName )
    throws CSVConfigurationException
    {
        
        check( (CSVFieldValidatorConf) configuration, fieldName );
        if( configuration != null && isNullOrEmpty(configuration.getName()) )
            throw new CSVConfigurationException( "Invalid configuration, the 'name' is mandatory for '" + fieldName + "'" );
        
    }
    
    /**
     * Checks the consistency of the information contained in the given configuration.
     * 
     * @param configuration the configuration to check.
     * @param fieldName     the name of the field related to the configuration.     
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    private static void check( final CSVRegisterConverterConf configuration, final String fieldName )
    throws CSVConfigurationException
    {
        
        check( (CSVFieldConverterConf) configuration, fieldName );
        if( configuration != null && isNullOrEmpty(configuration.getName()) )
            throw new CSVConfigurationException( "Invalid configuration, the 'name' is mandatory for '" + fieldName + "'" );
        
    }
    
    /**
     * Checks if a configuration that can be expressed both as an attribute
     * and as an element is consistent. It means that only one of the two
     * possible representation should be used.
     *  
     * @param attribute   the attribute representation.
     * @param element     the element representation.
     * @param name        the name of the configuration.
     * @param handlerType type of CSV handler.
     * @throws CSVConfigurationException if the configuration is inconsistent.
     */
    private static void checkDoubleConf( String attribute, Object element, String name, String handlerType )
    throws CSVConfigurationException
    {
        
        if( ! isNullOrEmpty(attribute) && element != null )
            throw new CSVConfigurationException( "Invalid configuration, the '" + name + "' has been specified twice in the " + handlerType );
        
    }
    
    /**
     * Checks if the given {@link String} is <code>null</code> or empty.
     * 
     * @param value string to check.
     * @return <code>true</code> if the given {@link String} is <code>null</code> or empty.
     */
    private static boolean isNullOrEmpty( String value )
    {
        return value == null || value.isEmpty();
    }
    
}