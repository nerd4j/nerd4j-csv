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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.nerd4j.csv.field.CSVFieldProcessor;
import org.nerd4j.csv.field.processor.EmptyCSVFieldProcessor;
import org.nerd4j.csv.field.processor.FormatBoolean;
import org.nerd4j.csv.field.processor.FormatNumber;
import org.nerd4j.csv.field.processor.ParseBoolean;
import org.nerd4j.csv.field.processor.ParseNumber;


/**
 * Represents a registry of {@link CSVFieldProcessor}s.
 * 
 * <p>
 *  This registry is internally used to refer {@link CSVFieldProcessor}s by name.
 *  It is possible to register custom processors.
 * </p>
 * 
 * <p>
 *  The following processors are registered by default:
 *  <ul>
 *    <li>parseByte</li>
 *    <li>parseShort</li>
 *    <li>parseInteger</li>
 *    <li>parseLong</li>
 *    <li>parseFloat</li>
 *    <li>parseDouble</li>
 *    <li>parseBigInteger</li>
 *    <li>parseBigDecimal</li>
 *    <li>parseAtomicInteger</li>
 *    <li>parseAtomicLong</li>
 *    <br />
 *    <li>formatByte</li>
 *    <li>formatShort</li>
 *    <li>formatInteger</li>
 *    <li>formatLong</li>
 *    <li>formatFloat</li>
 *    <li>formatDouble</li>
 *    <li>formatBigInteger</li>
 *    <li>formatBigDecimal</li>
 *    <li>formatAtomicInteger</li>
 *    <li>formatAtomicLong</li>
 *    <br />
 *    <li>parseBoolean</li>
 *    <li>formatBoolean</li>
 *  </ul>
 * </p>
 * 
 * @author Nerd4j Team
 */
final class CSVFieldProcessorRegistry extends CSVAbstractRegistry<CSVFieldProcessor<?,?>>
{

    
    /**
     * Default constructor.
     * 
     */
    public CSVFieldProcessorRegistry()
    {
        
        super();
        
        this.registerDefaults();
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    /**
     * Creates and registers the default entries and builders.
     * 
     */
    @SuppressWarnings("rawtypes")
    private void registerDefaults()
    {

        setEntry( "default", new EmptyCSVFieldProcessor() );
        
        /* Simple Boolean Processors. */
        setEntry( "parseBoolean",        new ParseBoolean() );
        setEntry( "formatBoolean",       new FormatBoolean() );
        
        /* Simple String to Number Processors. */
        setEntry( "parseByte",           new ParseNumber<Byte>( Byte.class ) );
        setEntry( "parseShort",          new ParseNumber<Short>( Short.class ) );
        setEntry( "parseInteger",        new ParseNumber<Integer>( Integer.class ) );
        setEntry( "parseLong",           new ParseNumber<Long>( Long.class ) );
        
        setEntry( "parseFloat",          new ParseNumber<Float>( Float.class ) );
        setEntry( "parseDouble",         new ParseNumber<Double>( Double.class ) );
        
        setEntry( "parseBigInteger",     new ParseNumber<BigInteger>( BigInteger.class ) );
        setEntry( "parseBigDecimal",     new ParseNumber<BigDecimal>( BigDecimal.class ) );
        setEntry( "parseAtomicInteger",  new ParseNumber<AtomicInteger>( AtomicInteger.class ) );
        setEntry( "parseAtomicLong",     new ParseNumber<AtomicLong>( AtomicLong.class ) );
        
        /* Simple Number to String Processors. */
        setEntry( "formatByte",          new FormatNumber<Byte>() );
        setEntry( "formatShort",         new FormatNumber<Short>() );
        setEntry( "formatInteger",       new FormatNumber<Integer>() );
        setEntry( "formatLong",          new FormatNumber<Long>() );
        
        setEntry( "formatFloat",         new FormatNumber<Float>() );
        setEntry( "formatDouble",        new FormatNumber<Double>() );
        
        setEntry( "formatBigInteger",    new FormatNumber<BigInteger>() );
        setEntry( "formatBigDecimal",    new FormatNumber<BigDecimal>() );
        setEntry( "formatAtomicInteger", new FormatNumber<AtomicInteger>() );
        setEntry( "formatAtomicLong",    new FormatNumber<AtomicLong>() );
        
    }
}