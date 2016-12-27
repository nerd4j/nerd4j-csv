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
package org.nerd4j.csv.registry;

import org.nerd4j.csv.field.CSVFieldConverter;
import org.nerd4j.csv.field.CSVFieldProcessor;
import org.nerd4j.csv.field.processor.CSVFieldProcessorFactory;
import org.nerd4j.csv.field.processor.EmptyCSVFieldProcessor;


/**
 * Represents a registry of {@link CSVFieldProcessor}s.
 * 
 * <p>
 * This registry is internally used to refer {@link CSVFieldProcessor}s by name.
 * It is possible to register custom processors.
 * 
 * <p>
 * The following processors are registered by default:
 * <ul>
 *  <li>parseByte</li>
 *  <li>parseShort</li>
 *  <li>parseInteger</li>
 *  <li>parseLong</li>
 *  <li>parseFloat</li>
 *  <li>parseDouble</li>
 *  <li>parseBigInteger</li>
 *  <li>parseBigDecimal</li>
 *  <li>parseAtomicInteger</li>
 *  <li>parseAtomicLong</li>
 *  <br>
 *  <li>formatByte</li>
 *  <li>formatShort</li>
 *  <li>formatInteger</li>
 *  <li>formatLong</li>
 *  <li>formatFloat</li>
 *  <li>formatDouble</li>
 *  <li>formatBigInteger</li>
 *  <li>formatBigDecimal</li>
 *  <li>formatAtomicInteger</li>
 *  <li>formatAtomicLong</li>
 *  <br>
 *  <li>parseBoolean</li>
 *  <li>formatBoolean</li>
 * </ul>
 * 
 * @author Nerd4j Team
 */
final class CSVFieldProcessorRegistry extends CSVAbstractRegistry<CSVFieldProcessor<?,?>>
{
	
	
    /**
     * Constructor with parameters.
     * 
     * @param converterRegistry registry of the {@link CSVFieldConverter}s to use
     *                          to build the related {@link CSVFieldProcessor}s.
     */
    public CSVFieldProcessorRegistry( CSVFieldConverterRegistry converterRegistry )
    {
        
        super();
                
        this.registerDefaults( converterRegistry );
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    /**
     * Creates and registers the default entries and builders.
     * 
     * @param converterRegistry registry of the {@link CSVFieldConverter}s to use
     *                          to build the related {@link CSVFieldProcessor}s. 
     */
    private void registerDefaults( CSVFieldConverterRegistry converterRegistry )
    {

    	/* The empty processor to use in case of missing configuration. */
        setFactory( "default", new CSVRegistryEntryFactory<CSVFieldProcessor<?,?>>()
        {
        	/** Singleton instance of the empty converter. */
        	private final CSVFieldProcessor<?,?> emptyProcessor = new EmptyCSVFieldProcessor<String>( String.class );
        	
        	/**
        	 * {@inheritDoc}
        	 */
			@Override
			public CSVFieldProcessor<?,?> create()
			{
				return emptyProcessor;
			}
        });
        
        /* Simple Boolean Processors. */
        setFactory( "parseBoolean",        converterRegistry );
        setFactory( "formatBoolean",       converterRegistry );
        
        /* Simple String to Number Processors. */
        setFactory( "parseByte",           converterRegistry );
        setFactory( "parseShort",          converterRegistry );
        setFactory( "parseInteger",        converterRegistry );
        setFactory( "parseLong",           converterRegistry );
        
        setFactory( "parseFloat",          converterRegistry );
        setFactory( "parseDouble",         converterRegistry );
        
        setFactory( "parseBigInteger",     converterRegistry );
        setFactory( "parseBigDecimal",     converterRegistry );
        setFactory( "parseAtomicInteger",  converterRegistry );
        setFactory( "parseAtomicLong",     converterRegistry );
        
        /* Simple Number to String Processors. */
        setFactory( "formatByte",          converterRegistry );
        setFactory( "formatShort",         converterRegistry );
        setFactory( "formatInteger",       converterRegistry );
        setFactory( "formatLong",          converterRegistry );
        
        setFactory( "formatFloat",         converterRegistry );
        setFactory( "formatDouble",        converterRegistry );
        
        setFactory( "formatBigInteger",    converterRegistry );
        setFactory( "formatBigDecimal",    converterRegistry );
        setFactory( "formatAtomicInteger", converterRegistry );
        setFactory( "formatAtomicLong",    converterRegistry );
        
    }
    
    
    /**
     * Uses the given {@link CSVFieldConverterRegistry} to get the related converter factory
     * and sets a {@link CSVFieldProcessorFactory} with the given name with no precondition
     * and postcondition.
     * 
     * @param name              the name related to the {@link CSVFieldProcessorFactory}.
     * @param converterRegistry the converter registry to use to get the related converter.
     */
    private <S,T> void setFactory( String name, CSVFieldConverterRegistry converterRegistry )
    {
    	
    	@SuppressWarnings("unchecked")
    	final CSVRegistryEntryFactory<CSVFieldConverter<S,T>> converterFactory =
			(CSVRegistryEntryFactory<CSVFieldConverter<S,T>>) (CSVRegistryEntryFactory<?>)
			converterRegistry.provideFactory( name, null );
    	
    	final CSVFieldProcessorFactory<S,T> processorFactory = new CSVFieldProcessorFactory<S,T>( null, converterFactory, null );
    	
    	setFactory( name, processorFactory );
    	
    }
    
}