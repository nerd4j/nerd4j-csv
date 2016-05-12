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

import java.util.Collection;
import java.util.Map;

import org.nerd4j.csv.conf.mapping.CSVColumnConf;
import org.nerd4j.csv.conf.mapping.CSVConfiguration;
import org.nerd4j.csv.conf.mapping.CSVFieldConverterConf;
import org.nerd4j.csv.conf.mapping.CSVFieldProcessorConf;
import org.nerd4j.csv.conf.mapping.CSVFieldValidatorConf;
import org.nerd4j.csv.conf.mapping.CSVFormatterConf;
import org.nerd4j.csv.conf.mapping.CSVModelBinderConf;
import org.nerd4j.csv.conf.mapping.CSVParserConf;
import org.nerd4j.csv.conf.mapping.CSVReaderConf;
import org.nerd4j.csv.conf.mapping.CSVWriterConf;
import org.nerd4j.csv.field.CSVField;
import org.nerd4j.csv.field.CSVFieldConverter;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.csv.field.CSVFieldProcessor;
import org.nerd4j.csv.field.CSVFieldValidator;
import org.nerd4j.csv.field.CSVMappingDescriptor;
import org.nerd4j.csv.formatter.CSVFormatterMetadata;
import org.nerd4j.csv.parser.CSVParserMetadata;
import org.nerd4j.csv.reader.CSVReaderMetadata;
import org.nerd4j.csv.reader.binding.CSVToModelBinderFactory;
import org.nerd4j.csv.registry.CSVAbstractRegistry;
import org.nerd4j.csv.registry.CSVRegistry;
import org.nerd4j.csv.writer.CSVWriterMetadata;
import org.nerd4j.csv.writer.binding.ModelToCSVBinderFactory;


/**
 * Utility class to build the meta-data
 * given the related configurations.
 * 
 * @author Nerd4j Team
 */
public final class CSVMetadataBuilder
{
    
    /**
     * Builds the object related to the given configuration.
     * 
     * @param configuration configuration to parse.
     * @return related object to build.
     */
    public static CSVParserMetadata build( CSVParserConf configuration )
    {
        
        final CSVParserMetadata metadata = new CSVParserMetadata();

        if( configuration == null ) return metadata;
        CSVConfChecker.check( configuration );
        
        if( configuration.getQuoteChar() != null )
            metadata.setQuoteChar( configuration.getQuoteChar() );
        
        if( configuration.getEscapeChar() != null )
            metadata.setEscapeChar( configuration.getEscapeChar() );
        
        if( configuration.getFieldSeparator() != null )
            metadata.setFieldSeparator( configuration.getFieldSeparator() );

        if( configuration.getRecordSeparator() != null )
        	metadata.setRecordSeparator( configuration.getRecordSeparator() );
        
        if( configuration.isMatchRecordSeparatorExactSequence() != null )
        	metadata.setMatchRecordSeparatorExactSequence( configuration.isMatchRecordSeparatorExactSequence() );
        
        if( configuration.getCharsToIgnore() != null )
            metadata.setCharsToIgnore( configuration.getCharsToIgnore() );
        
        if( configuration.getCharsToIgnoreAroundFields() != null )
            metadata.setCharsToIgnoreAroundFields( configuration.getCharsToIgnoreAroundFields() );
        
        if( configuration.isLazyQuotes() != null )
            metadata.setLazyQuotes( configuration.isLazyQuotes() );
        
        return metadata;
        
    }
    
    /**
     * Builds the object related to the given configuration.
     * 
     * @param configuration configuration to parse.
     * @return related object to build.
     */
    public static CSVFormatterMetadata build( CSVFormatterConf configuration )
    {
        
        final CSVFormatterMetadata metadata = new CSVFormatterMetadata();

        if( configuration == null ) return metadata;
        CSVConfChecker.check( configuration );
        
        if( configuration.getQuoteChar() != null )
            metadata.setQuoteChar( configuration.getQuoteChar() );
        
        if( configuration.getEscapeChar() != null )
            metadata.setEscapeChar( configuration.getEscapeChar() );
        
        if( configuration.getFieldSeparator() != null )
            metadata.setFieldSeparator( configuration.getFieldSeparator() );
        
        if( configuration.getRecordSeparator() != null )
            metadata.setRecordSeparator( configuration.getRecordSeparator() );
        
        if( configuration.getCharsToEscape() != null )
            metadata.setCharsToEscape( configuration.getCharsToEscape() );
        
        if( configuration.getCharsThatForceQuoting() != null )
            metadata.setCharsThatForceQuoting( configuration.getCharsThatForceQuoting() );
        
        return metadata;
        
    }
    
    
    /**
     * Builds the object related to the given configuration.
     * 
     * @param readerConf    configuration to parse.
     * @param configuration the global configuration.
     * @param registry      the builder registry to use.
     * @return related object to build.
     */
    @SuppressWarnings({"unchecked","rawtypes"})
    public static <M> CSVReaderMetadata<M> build( CSVReaderConf readerConf,
    		                                      CSVConfiguration configuration,
                                                  CSVRegistry registry )
    {
        
        CSVConfChecker.check( readerConf );
        
        final Collection<CSVColumnConf> columns = readerConf.getColumns().values();
        final CSVFieldMetadata<?,?>[] fieldConfs = new CSVFieldMetadata[columns.size()];
        
        int i = -1;
        for( CSVColumnConf column : columns )
            fieldConfs[++i] = CSVMetadataBuilder.build( column, registry );
        
        final String parserRef = readerConf.getParserRef();
        final boolean readHeader = readerConf.getReadHeader() != null ? readerConf.getReadHeader() : true;
        final boolean useColumnNames = readerConf.getUseColumnNames() != null ? readerConf.getUseColumnNames() : true;        
        
        final boolean acceptIncompleteRecords = readerConf.getAcceptIncompleteRecords() != null
        		                              ? readerConf.getAcceptIncompleteRecords()
        		                              : false;        
        
        final CSVParserConf parserConf = readerConf.getParser() != null
                                       ? readerConf.getParser()
                                       : configuration.getParsers().get( parserRef );
        
        final CSVParserMetadata parserConfiguration = build( parserConf );
        
        final CSVModelBinderConf binderConf = readerConf.getModelBinder();
        final CSVToModelBinderFactory<?> binderFactory = buildCSVToModelBinderFactory( binderConf, registry );
        
        final CSVReaderMetadata<M> metadata = new CSVReaderMetadata( parserConfiguration, binderFactory,
                                                                     fieldConfs, readHeader, useColumnNames,
                                                                     acceptIncompleteRecords );
        
        return metadata;
        
    }
    
    /**
     * Builds the object related to the given configuration.
     * 
     * @param writerConf    configuration to parse.
     * @param configuration the global configuration.
     * @param registry      the builder registry to use.
     * @return related object to build.
     */
    @SuppressWarnings({"unchecked","rawtypes"})
    public static <M> CSVWriterMetadata<M> build( CSVWriterConf writerConf, CSVConfiguration configuration,
                                                  CSVRegistry registry )
    {
        
        CSVConfChecker.check( writerConf );
                
        final Collection<CSVColumnConf> columns = writerConf.getColumns().values();
        final CSVFieldMetadata<?,?>[] fieldConfs = new CSVFieldMetadata<?,?>[columns.size()];
        
        int i = -1;
        for( CSVColumnConf column : columns )
            fieldConfs[++i] = CSVMetadataBuilder.build( column, registry );
        
        final boolean writeHeader = writerConf.getWriteHeader() != null ? writerConf.getWriteHeader() : true;
        final String formatterRef = writerConf.getFormatterRef();
        
        final CSVFormatterConf formatterConf = writerConf.getFormatter() != null
                                             ? writerConf.getFormatter()
                                             : configuration.getFormatters().get( formatterRef );
        
        final CSVFormatterMetadata formatterConfiguration = CSVMetadataBuilder.build( formatterConf );
        
        final CSVModelBinderConf binderConf = writerConf.getModelBinder();
        final ModelToCSVBinderFactory<?> binderFactory = buildModelToCSVBinderFactory( binderConf, registry );
        
        final CSVWriterMetadata<M> metadata = new CSVWriterMetadata( formatterConfiguration, binderFactory,
                                                                     fieldConfs, writeHeader );
        return metadata;
        
    }
    
    
    
    /**
     * Builds the object related to the given configuration.
     * 
     * @param configuration configuration to parse.
     * @param registry      the builder registry to use.
     * 
     * @return related object to build.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static CSVFieldMetadata<?,?> build( CSVColumnConf configuration, CSVRegistry registry )
    {
        
        CSVConfChecker.check( configuration );
        
        final String processorRef = configuration.getProcessorRef();
        final CSVFieldProcessorConf processorConf = configuration.getProcessor();
        final CSVFieldProcessor<?,?> processor = processorRef != null
                                               ? registry.getProcessorRegistry().getEntry( processorRef )
                                               : build( processorConf, registry );
        
        final boolean optional = configuration.getOptional() != null ? configuration.getOptional() : false;
        final CSVField<?,?> field = new CSVField(processor, optional );
        
        final String name = configuration.getName();
        final String mapping = configuration.getMapping();
        final CSVMappingDescriptor mappingDescriptor = new CSVMappingDescriptor( name, mapping,  processor.getTargetType() );
        
        return new CSVFieldMetadata( mappingDescriptor, field );
        
    }
    
    
    /**
     * Returns the {@link CSVFieldProcessor} described by the given configuration.
     *  
     * @param configuration the configuration to parse.
     * @param registry      the builder registry to use.
     * @return the {@link CSVFieldProcessor} described by the given configuration.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static CSVFieldProcessor<?,?> build( CSVFieldProcessorConf configuration, CSVRegistry registry )
    {
       
        final CSVAbstractRegistry<CSVFieldProcessor<?,?>> processorRegistry = registry.getProcessorRegistry();

        if( configuration == null )
            return processorRegistry.getEntry( "default" );
        
        CSVConfChecker.check( configuration );
        
        final String preconditionRef  = configuration.getPreconditionRef();
        final CSVFieldValidatorConf preconditionConf  = configuration.getPrecondition();
        final CSVFieldValidator<?> precondition = preconditionRef != null
                                                ? registry.getValidatorRegistry().getEntry( preconditionRef )
                                                : build( preconditionConf, registry );
        
        final String converterRef  = configuration.getConverterRef();
        final CSVFieldConverterConf converterConf = configuration.getConverter();
        final CSVFieldConverter<?,?> converter = converterRef != null
                                               ? registry.getConverterRegistry().getEntry( converterRef )
                                               : build( converterConf, registry );
                
        final String postconditionRef  = configuration.getPostconditionRef();
        final CSVFieldValidatorConf postconditionConf = configuration.getPostcondition();
        final CSVFieldValidator<?> postcondition = postconditionRef != null
                                                 ? registry.getValidatorRegistry().getEntry( postconditionRef )
                                                 : build( postconditionConf, registry );
                
        return new CSVFieldProcessor( precondition, converter, postcondition );
        
    }
    
    
    /**
     * Returns the {@link CSVFieldValidator} described by the given configuration.
     *  
     * @param configuration the configuration to parse.
     * @param registry      the builder registry to use.
     * @return the {@link CSVFieldValidator} described by the given configuration.
     */
    public static CSVFieldValidator<?> build( CSVFieldValidatorConf configuration, CSVRegistry registry )
    {
        
        if( configuration == null ) return null;
        
        final String type = configuration.getType();
        final Map<String,String> params = configuration.getParams();
        final CSVAbstractRegistry<CSVFieldValidator<?>> validatorRegistry = registry.getValidatorRegistry();
        
        return validatorRegistry.provideEntry( type, params );
                                
    }
    
    /**
     * Returns the {@link CSVFieldConverter} described by the given configuration.
     *  
     * @param configuration the configuration to parse.
     * @param registry      the builder registry to use.
     * @return the {@link CSVFieldConverter} described by the given configuration.
     */
    public static CSVFieldConverter<?,?> build( CSVFieldConverterConf configuration, CSVRegistry registry )
    {
        
        final CSVAbstractRegistry<CSVFieldConverter<?,?>> converterRegistry = registry.getConverterRegistry();

        if( configuration == null )
            return converterRegistry.getEntry( "default" );
        
        final String type = configuration.getType();
        final Map<String,String> params = configuration.getParams();
        
        return converterRegistry.provideEntry( type, params );
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * Builds the object related to the given configuration.
     * 
     * @param binderConf configuration of the binder to use.
     * @param registry      the builder registry to use.
     * @return related object to build.
     */
    private static CSVToModelBinderFactory<?> buildCSVToModelBinderFactory( CSVModelBinderConf binderConf, CSVRegistry registry )
    {
        
        final String type = binderConf.getType();
        final Map<String,String> params = binderConf.getParams();
        final CSVAbstractRegistry<CSVToModelBinderFactory<?>> modelBinderRegistry = registry.getCsvToModelBinderFactoryRegistry();
        
        return (CSVToModelBinderFactory<?>) modelBinderRegistry.provideEntry( type, params );
        
    }
    
    /**
     * Builds the object related to the given configuration.
     * 
     * @param binderConf configuration of the binder to use.
     * @param registry      the builder registry to use.
     * @return related object to build.
     */
    private static ModelToCSVBinderFactory<?> buildModelToCSVBinderFactory( CSVModelBinderConf binderConf, CSVRegistry registry )
    {
        
    	final String type = binderConf.getType();
        final Map<String,String> params = binderConf.getParams();
        final CSVAbstractRegistry<ModelToCSVBinderFactory<?>> modelBinderRegistry = registry.getModelToCSVBinderFactoryRegistry();
        
        return (ModelToCSVBinderFactory<?>) modelBinderRegistry.provideEntry( type, params );
        
    }
    
}