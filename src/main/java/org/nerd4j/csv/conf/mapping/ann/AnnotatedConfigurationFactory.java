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
package org.nerd4j.csv.conf.mapping.ann;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.nerd4j.csv.RemarkableASCII;
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
import org.nerd4j.csv.exception.CSVConfigurationException;
import org.nerd4j.lang.Couple;
import org.nerd4j.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Creates a {@link CSVConfiguration} reading the related
 * CSV annotation from a given bean.
 * 
 * <p>
 *  All the configurations produced by this factory
 *  are implicitly related to a model binder of type 'bean'.
 * </p>
 * 
 * @author Nerd4j Team
 */
public class AnnotatedConfigurationFactory
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( AnnotatedConfigurationFactory.class );

    /** Costante per identificare un elemento di tipo reader. */
    private static final int READER = 1;

    /** Costante per identificare un elemento di tipo writer. */
    private static final int WRITER = 2;
    
    
    
    /**
     * Default constructor.
     * 
     */
    public AnnotatedConfigurationFactory()
    {
        
        super();
        
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * Merges the given annotatedBean that represents the configuration source
     * into the given {@link CSVConfiguration}.
     * 
     * @param configSource the source to parse.
     * @param configTarget the target to merge.
     */
    public static void merge( Class<?> configSource, CSVConfiguration configTarget )
    {
        
        try{
        
            if( configSource == null )
            {
                logger.error( "Invalid configuration source null." );
                throw new NullPointerException( "Invalid null configuration source" );
            }
            
            mergeReader( configSource, configTarget );
            mergeWriter( configSource, configTarget );
        
        }catch( Exception ex )
        {
            logger.error( "An error occurred during configuration loading.", ex );
            throw new CSVConfigurationException( "Unable to load the annotated configuration due to ", ex );
        }
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * Merges the given reader annotation into the given CSV configuration.
     * 
     * @param annConf the source annotate bean.
     * @param csvConf the target CSV configuration.
     */
    private static void mergeReader( final Class<?> annConf, final CSVConfiguration csvConf )
    {
        
        final CSVReader reader = annConf.getAnnotation( CSVReader.class );
        if( reader == null ) return;
                
        final String beanClassName = annConf.getCanonicalName();
        final CSVReaderConf readerConf = new CSVReaderConf();

        csvConf.getReaders().put( beanClassName, readerConf );
        
        readerConf.setName( beanClassName );
        merge( reader, readerConf );
        
        final CSVModelBinderConf modelBinder = readerConf.getModelBinder();
        modelBinder.getParams().put( "bean-class", beanClassName );
        modelBinder.setType( "bean" );
        
        mergeColumns( annConf, readerConf.getColumns(), AnnotatedConfigurationFactory.READER );
        
    }
    
    /**
     * Merges the given reader annotation into the given CSV configuration.
     * 
     * @param annConf the source annotate bean.
     * @param csvConf the target CSV configuration.
     */
    private static void mergeWriter( final Class<?> annConf, final CSVConfiguration csvConf )
    {
        
        final CSVWriter writer = annConf.getAnnotation( CSVWriter.class );
        if( writer == null ) return;
        
        final String beanClassName = annConf.getCanonicalName();
        final CSVWriterConf writerConf = new CSVWriterConf();
        
        csvConf.getWriters().put( beanClassName, writerConf );
        
        writerConf.setName( beanClassName );
        merge( writer, writerConf );
        
        final CSVModelBinderConf modelBinder = writerConf.getModelBinder();
        modelBinder.getParams().put( "bean-class", beanClassName );
        modelBinder.setType( "bean" );
        
        mergeColumns( annConf, writerConf.getColumns(), AnnotatedConfigurationFactory.WRITER );
        
    }
        
    /**
     * Converts the given annotation into a CSV configuration.
     * 
     * @param annConf the source annotation.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final CSVReader annConf, final CSVReaderConf csvConf )
    {
        
        if( checkNullConsistence(isValued(annConf), csvConf, "reader") ) return;
        
        csvConf.setReadHeader( annConf.readHeader() );        
        csvConf.setUseColumnNames( annConf.useColumnNames() );
        csvConf.setAcceptIncompleteRecords( annConf.acceptIncompleteRecords() );
                
        if( isValued(annConf.parser()) )
        {
            csvConf.setParser( new CSVParserConf() );
            overwrite( annConf.parser()[0], csvConf.getParser() );
        }
        
    }
    
    /**
     * Converts the given annotation into a CSV configuration.
     * 
     * @param annConf the source annotation.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final CSVWriter annConf, final CSVWriterConf csvConf )
    {
        
        if( checkNullConsistence(isValued(annConf), csvConf, "writer") ) return;
        
        csvConf.setWriteHeader( annConf.writeHeader() );        
        
        if( isValued(annConf.formatter()) )
        {
            csvConf.setFormatter( new CSVFormatterConf() );
            overwrite( annConf.formatter()[0], csvConf.getFormatter() );
        }
        
    }
    
    /**
     * Overwrites the given CSV configuration using the given annotation. 
     * 
     * @param annConf the source annotation.
     * @param csvConf the target CSV configuration.
     */
    private static void overwrite( final CSVParser annConf, final CSVParserConf csvConf )
    {
        
        if( checkNullConsistence(isValued(annConf), csvConf, "parser") ) return;
        
        csvConf.setLazyQuotes( annConf.lazyQuotes() );
        csvConf.setMatchRecordSeparatorExactSequence( annConf.recordSepMatchExactSequence() );
        
        if( isValued(annConf.quote()) ) csvConf.setQuoteChar( annConf.quote() );
        if( isValued(annConf.escape()) ) csvConf.setEscapeChar( annConf.escape() );
        if( isValued(annConf.fieldSep()) ) csvConf.setFieldSeparator( annConf.fieldSep() );                
        if( isValued(annConf.recordSep()) ) csvConf.setRecordSeparator( annConf.recordSep() );
        if( isValued(annConf.charsToIgnore()) ) csvConf.setCharsToIgnore( annConf.charsToIgnore() );
        if( isValued(annConf.charsToIgnoreAroundFields()) ) csvConf.setCharsToIgnoreAroundFields( annConf.charsToIgnoreAroundFields() );
//        if( isValued(annConf.recordSep1()) ) csvConf.setRecordSeparator1( annConf.recordSep1() );
//        if( isValued(annConf.recordSep2()) ) csvConf.setRecordSeparator2( annConf.recordSep2() );
//        overwriteCharSets( annConf.charsToIgnore(), csvConf.getCharsToIgnore(), "chars-to-ignore" );
//        overwriteCharSets( annConf.charsToIgnoreAroundFields(), csvConf.getCharsToIgnoreAroundFields(), "chars-to-ignore-aroud-fields" );
        
    }
    
    /**
     * Overwrites the given CSV configuration using the given annotation.
     * 
     * @param annConf the source annotation.
     * @param csvConf the target CSV configuration.
     */
    private static void overwrite( final CSVFormatter annConf, final CSVFormatterConf csvConf )
    {
        
        if( checkNullConsistence(isValued(annConf), csvConf, "formatter") ) return;
        
        if( isValued(annConf.quote()) ) csvConf.setQuoteChar( annConf.quote() );
        if( isValued(annConf.escape()) ) csvConf.setEscapeChar( annConf.escape() );
        if( isValued(annConf.fieldSep()) ) csvConf.setFieldSeparator( annConf.fieldSep() );
//        if( isValued(annConf.recordSep1()) ) csvConf.setRecordSeparator1( annConf.recordSep1() );
//        if( isValued(annConf.recordSep2()) ) csvConf.setRecordSeparator2( annConf.recordSep2() );
        if( isValued(annConf.charsToEscape()) ) csvConf.setCharsToEscape( annConf.charsToEscape() );
        if( isValued(annConf.charsThatForceQuoting()) ) csvConf.setCharsThatForceQuoting( annConf.charsThatForceQuoting() );
//        overwriteCharSets( annConf.charsToEscape(), csvConf.getCharsToEscape(), "chars-to-escape" );
//        overwriteCharSets( annConf.charsThatForceQuoting(), csvConf.getCharsThatForceQuoting(), "chars-that-force-quoting" );
        
    }

    /**
     * Merges the given XML configuration into the given CSV configuration.
     * 
     * @param annConf    the source annotation configuration.
     * @param csvConfMap the target CSV configuration.
     * @param confType   can be reader or writer.
     */
    private static void mergeColumns( final Class<?> annConf,
                                      final Map<String,CSVColumnConf> csvConfMap,
                                      final int confType )
    {
                
        final Map<String,Couple<CSVColumn,Class<?>>> columnMap = getColumnAnnotations( annConf );
        
        String property;
        CSVColumn column;
        String columnName;
        CSVColumnConf csvColumn;
        Couple<CSVColumn,Class<?>> couple;
        for( Map.Entry<String,Couple<CSVColumn,Class<?>>> entry : columnMap.entrySet() )
        {
        
            property = entry.getKey();
            couple = entry.getValue();
            column = couple.getFirst();
            
//            if( ! isValued(column.name()) )
//                throw new NullPointerException( "Invalid configuration, column name is not defined" );
            
            if( ! isValued(column.name()) )
            	logger.warn( "Column name not defined going to use property name {}", property );
                
            columnName = isValued(column.name()) ? column.name() : property;
            csvColumn = csvConfMap.get( columnName );
            if( csvColumn == null )
            {
                csvColumn = new CSVColumnConf();
                csvColumn.setName( columnName );
                csvConfMap.put( columnName, csvColumn );
            }
            
            merge( couple, csvColumn, confType );
            csvColumn.setMapping( property );
            
        }
        
        
    }
    
        
    /**
     * Reads the given bean to search for {@link CSVColumn} annotations.
     * The annotations are searched both on the methods and on the fields.
     * If the getter and the field of a given property are both annotated
     * with {@link CSVColumn} an exception will be thrown.
     * 
     * @param annConf    the source annotation configuration.
     * @return a map relating each property of the bean with the corresponding annotation.
     */
    private static Map<String,Couple<CSVColumn,Class<?>>> getColumnAnnotations( final Class<?> annConf )
    {
        
        final Map<Field,CSVColumn> fieldMap = ReflectionUtil.findAnnotatedFields( CSVColumn.class, annConf );
        final Map<Method,CSVColumn> methodMap = ReflectionUtil.findAnnotatedMethods( CSVColumn.class, annConf );
        final Map<String,Couple<CSVColumn,Class<?>>> propertyMap = new HashMap<String,Couple<CSVColumn,Class<?>>>( fieldMap.size() + methodMap.size() );
        
        Method method;
        String property;
        for( Map.Entry<Method,CSVColumn> entry : methodMap.entrySet() )
        {
            
            method = entry.getKey();
            if( ! ReflectionUtil.isGetter(method) )
                throw new IllegalArgumentException( "The annotated method " + method + " is not a getter." );
            
            property = ReflectionUtil.propertyFromGetter( entry.getKey() );
            propertyMap.put( property, new Couple<CSVColumn,Class<?>>(entry.getValue(),method.getReturnType()) );
            
        }
        
        Field field;
        for( Map.Entry<Field,CSVColumn> entry : fieldMap.entrySet() )
        {
            
            field = entry.getKey();
            property = field.getName();
            if( propertyMap.containsKey(property) )
                throw new IllegalStateException( "Duplicated configuration for the property " + property );
                
            propertyMap.put( property, new Couple<CSVColumn,Class<?>>(entry.getValue(),field.getType()) );
        }
        
        return propertyMap;
        
    }
    
    
    /**
     * Merges the given annotation into the given CSV configuration.
     * 
     * @param annConf  the source XML configuration.
     * @param csvConf  the target CSV configuration.
     * @param confType can be reader or writer.
     */
    private static void merge( final Couple<CSVColumn,Class<?>> annConf, final CSVColumnConf csvConf, int confType )
    {
        
        if( checkNullConsistence(annConf != null, csvConf, "column") ) return;
        
        final CSVColumn annotation = annConf.getFirst();
        final Class<?> type = annConf.getSecond();
        
        if( annotation == null || type == null )
            throw new NullPointerException( "The " + confType + " configuration must be not null" );

        csvConf.setOptional( annotation.optional() );
        
        switch( confType )
        {
        
        case AnnotatedConfigurationFactory.READER:
            
            /*
             * If the processor is defined it may be incomplete so we first try
             * to auto detect the configuration, otherwise if neither the processor
             * nor the processorRef is defined that we need the auto detect.
             */
            if( csvConf.getProcessor() != null || ! isValued(csvConf.getProcessorRef()) )
                autodetectProcessor( csvConf, type, "parse" );
            
            if( isValued(annotation.readProcessor()) )
            {
                csvConf.setProcessorRef( null );
                if( csvConf.getProcessor() == null ) csvConf.setProcessor( new CSVFieldProcessorConf() );
                merge( annotation.readProcessor()[0], csvConf.getProcessor() );
            }
            
            else if( isValued(annotation.readProcessorRef()) )
            {
                csvConf.setProcessor( null );
                csvConf.setProcessorRef( annotation.readProcessorRef() );
            }
            
            break;
            
        case AnnotatedConfigurationFactory.WRITER:

            /*
             * If the processor is defined it may be incomplete so we first try
             * to auto detect the configuration, otherwise if neither the processor
             * nor the processorRef is defined that we need the auto detect.
             */
            if( csvConf.getProcessor() != null || ! isValued(csvConf.getProcessorRef()) )
                autodetectProcessor( csvConf, type, "format" );
            
            if( isValued(annotation.writeProcessor()) )
            {
                csvConf.setProcessorRef( null );
                if( csvConf.getProcessor() == null ) csvConf.setProcessor( new CSVFieldProcessorConf() );
                merge( annotation.writeProcessor()[0], csvConf.getProcessor() );
            }
            
            if( isValued(annotation.writeProcessorRef()) )
            {
                csvConf.setProcessor( null );
                csvConf.setProcessorRef( annotation.writeProcessorRef() );
            }
            
            break;
            
        }
        
    }
    
    
    /**
     * Tries to detect the processor that fits the given property type.
     * If the given type is an instance of one of the registered types
     * it creates the related processor. 
     * 
     * @param csvConf configuration to populate.
     * @param type    type to use for auto detection.
     * @param prefix  the prefix to use, can be 'parse' or 'format'.
     */
    private static void autodetectProcessor( CSVColumnConf csvConf, Class<?> type, String prefix )
    {
        
        if( String.class.isAssignableFrom(type) ) return;
        
        final CSVFieldProcessorConf processor = new CSVFieldProcessorConf();
        final CSVFieldConverterConf converter = new CSVFieldConverterConf();
        
        if( Enum.class.isAssignableFrom(type) )
        {
            converter.setType( prefix + "Enum" );
            converter.getParams().put( "enum-type", type.getName() );
        }
        else
        {
            converter.setType( prefix + type.getSimpleName() );
        }
        
        processor.setConverter( converter );
        csvConf.setProcessor( processor );
        
    }
    
    
    /**
     * Merges the given annotation into the given CSV configuration.
     * 
     * @param annConf the source annotation.
     * @param csvConf the target CSV configuration.
     */
    private static void merge( final CSVFieldProcessor annConf, final CSVFieldProcessorConf csvConf )
    {
        
        if( checkNullConsistence(isValued(annConf), csvConf, "processor") ) return;
        
        if( isValued(annConf.converterRef()) )
        {
            csvConf.setConverter( null );
            csvConf.setConverterRef( annConf.converterRef() );
        }
        
        if( isValued(annConf.converter()) )
        {
            csvConf.setConverterRef( null );
            if( csvConf.getConverter() == null ) csvConf.setConverter( new CSVFieldConverterConf() );  
            overwrite( annConf.converter()[0], csvConf.getConverter() );
        }
        
        if( isValued(annConf.preconditionRef()) )
        {
            csvConf.setPrecondition( null );
            csvConf.setPreconditionRef( annConf.preconditionRef() );
        }
        
        if( isValued(annConf.precondition()) )
        {
            csvConf.setPreconditionRef( null );
            if( csvConf.getPrecondition() == null ) csvConf.setPrecondition( new CSVFieldValidatorConf() );  
            overwrite( annConf.precondition()[0], csvConf.getPrecondition() );
        }
        
        if( isValued(annConf.postconditionRef()) )
        {
            csvConf.setPostcondition( null );
            csvConf.setPostconditionRef( annConf.postconditionRef() );
        }
            
        if( isValued(annConf.postcondition()) )
        {
            csvConf.setPostconditionRef( null );
            if( csvConf.getPostcondition() == null ) csvConf.setPostcondition( new CSVFieldValidatorConf() );  
            overwrite( annConf.postcondition()[0], csvConf.getPostcondition() );
        }
        
    }
    
    /**
     * Overwrites the given CSV configuration with the given annotation.
     * 
     * @param annConf the source annotation.
     * @param csvConf the target CSV configuration.
     */
    private static void overwrite( final CSVFieldValidator annConf, final CSVFieldValidatorConf csvConf )
    {

        if( checkNullConsistence(isValued(annConf), csvConf, "validator") ) return;
        
        csvConf.setType( annConf.type() );
        overwriteParams( annConf.params(), csvConf.getParams(), "validator" );
               
    }

    /**
     * Overwrites the given CSV configuration with the given annotation.
     * 
     * @param annConf the source annotation.
     * @param csvConf the target CSV configuration.
     */
    private static void overwrite( final CSVFieldConverter annConf, final CSVFieldConverterConf csvConf )
    {
        
        if( checkNullConsistence(isValued(annConf), csvConf, "converter") ) return;
        
        if( isValued(annConf.type()) ) csvConf.setType( annConf.type() );
        overwriteParams( annConf.params(), csvConf.getParams(), "converter" );
                
    }
    
//    /**
//     * Overwrites the given CSV configuration with the given annotation.
//     * 
//     * @param annConf the source annotation.
//     * @param csvConf the target CSV configuration.
//     * @param charSetType the type of char set to merge.
//     */
//    private static void overwriteCharSets( final char[] annConf, final Set<Character> csvConf, final String charSetType )
//    {
//        
//        if( ! isValued(annConf) ) return;
//        
//        if( csvConf == null )
//            throw new NullPointerException( "The character set for type " + charSetType + " must be not null" );
//        
//        csvConf.clear();
//        for( char c : annConf )
//            csvConf.add( c );
//        
//    }
    
    /**
     * Overwrites the given CSV configuration with the given annotation.
     * 
     * @param annConf the source annotation.
     * @param csvConf the target CSV configuration.
     * @param paramsType the type of parameters to merge.
     */
    private static void overwriteParams( final CSVParam[] annConf, final Map<String,String> csvConf, final String paramsType )
    {
        
        // can be empty
        if( annConf == null ) return;
        
        if( csvConf == null )
            throw new NullPointerException( "The parameter map for type " + paramsType + " must be not null" );
        
        csvConf.clear();
        for( CSVParam param : annConf )
            csvConf.put( param.type(), param.value() );
        
    }
    
    
    /* ***************** */
    /*  UTILITY METHODS  */
    /* ***************** */
    
    
    /**
     * Returns <code>true</code> if the annotation is not valued.
     * Otherwise checks the csvConf, if it is <code>null</code>
     * throws a NullPointerException.
     * 
     * @param annConf the annotation to check.
     * @param csvConf the CSV configuration to check.
     * @param confType the configuration type.
     * @return <code>true</code> if the annConf is <code>null</code>.
     * @throws NullPointerException if csvConf is <code>null</code>.
     */
    private static boolean checkNullConsistence( final boolean isValuedAnnConf, final Object csvConf, final String confType )
    {
        
        if( isValuedAnnConf && csvConf == null )
            throw new NullPointerException( "The " + confType + " configuration must be not null" );

        return ! isValuedAnnConf;
        
    }
    
    /**
     * Tells if the given character is NOT the {@code null} constant
     * {@link RemarkableASCII#NULL}.
     * 
     * @param value the character to check.
     * @return {@code true} if the given character is NOT the {@code null} constant.
     */
    private static boolean isValued( char value )
    {
        
        return value != RemarkableASCII.NOT_AN_ASCII;
        
    }
    
    /**
     * Tells if the given char array is NOT {@code null} AND NOT empty.
     * 
     * @param value the char array to check.
     * @return {@code true} if the given char array is NOT {@code null} AND NOT empty.
     */
    private static boolean isValued( char[] value )
    {
        
        return value != null && value.length > 0 && isValued( value[0] );
        
    }
    
    /**
     * Tells if the given annotation is NOT {@code null}.
     * 
     * @param value the annotation to check.
     * @return {@code true} if the given annotation is NOT {@code null}.
     */
    private static boolean isValued( Annotation value )
    {
        
        return value != null;
        
    }
    
    /**
     * Tells if the given annotation array is NOT {@code null} AND NOT empty.
     * 
     * @param value the annotation array to check.
     * @return {@code true} if the given annotation array is NOT {@code null} AND NOT empty.
     */
    private static boolean isValued( Annotation[] value )
    {
        
        return value != null && value.length > 0;
        
    }
    
    /**
     * Tells if the given string is NOT {@code null} AND NOT empty.
     * 
     * @param value the string to check.
     * @return {@code true} if the given string is NOT {@code null} AND NOT empty.
     */
    private static boolean isValued( String value )
    {
        
        return value != null && ! value.isEmpty();
        
    }
    
    /**
     * Tells if the given {@link CSVFieldConverter} is NOT {@code null} AND NOT empty.
     * 
     * @param value the {@link CSVFieldConverter} to check.
     * @return {@code true} if the given {@link CSVFieldConverter} is NOT {@code null} AND NOT empty.
     */
    private static boolean isValued( CSVFieldConverter value )
    {
        
        return value != null;
        
    }
    
    /**
     * Tells if the given {@link CSVFieldValidator} is NOT {@code null} AND NOT empty.
     * 
     * @param value the {@link CSVFieldValidator} to check.
     * @return {@code true} if the given {@link CSVFieldValidator} is NOT {@code null} AND NOT empty.
     */
    private static boolean isValued( CSVFieldValidator value )
    {
        
        return value != null;
        
    }
    
}