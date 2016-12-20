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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.field.CSVFieldConverter;
import org.nerd4j.csv.field.CSVFieldProcessContext;
import org.nerd4j.csv.model.Product;
import org.nerd4j.csv.registry.CSVRegistry;
import org.nerd4j.csv.registry.CSVRegistryEntryFactory;
import org.nerd4j.test.BaseTest;

/**
 * {@link CSVFieldConverterFactory} unit tests.
 * 
 * @author Nerd4j Team
 */
public class CSVFieldConverterRegisterTest extends BaseTest
{

    /** The object to test. */
    private final CSVRegistry registry = new CSVRegistry();
    
    private final CSVFieldProcessContext context = new CSVFieldProcessContext( null );
    
    
    @Test
	public void testParseValue()
	{
	    
        final Map<String,String> params = new HashMap<String,String>();
        
	    final Byte byteValue = parse( "parseByte", "100", params );
	    Assert.assertEquals( Byte.valueOf((byte)100), byteValue );

	    final Short shortValue = parse( "parseShort", "10000", params );
	    Assert.assertEquals( Short.valueOf((short)10000), shortValue );

	    final Integer integerValue = parse( "parseInteger", "1000000", params );
	    Assert.assertEquals( Integer.valueOf(1000000), integerValue );

	    final Long longValue = parse( "parseLong", "1000000000000", params );
	    Assert.assertEquals( Long.valueOf(1000000000000L), longValue );
	    
	    final Float floatValue = parse( "parseFloat", "1000.01", params );
	    Assert.assertEquals( Float.valueOf(1000.01f), floatValue );
	    
	    final Double doubleValue = parse( "parseDouble", "1000.01", params );
	    Assert.assertEquals( Double.valueOf(1000.01d), doubleValue );
	    
        final String bigIntVal = "311917102708983781990730508";
        final BigInteger bigIntegerValue = parse( "parseBigInteger", bigIntVal, params );
        Assert.assertEquals( new BigInteger( new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 } ), bigIntegerValue );

        final String bigDecVal = "3119171027089837819907.30508";
        final BigDecimal bigDecimalValue = parse( "parseBigDecimal", bigDecVal, params );
        Assert.assertEquals( new BigDecimal( bigIntegerValue, 5 ), bigDecimalValue );
	    
	    final AtomicInteger atomicIntegerValue = parse( "parseAtomicInteger", "123456789", params );
        Assert.assertEquals( 123456789, atomicIntegerValue.get() );

        final AtomicLong atomicLongValue = parse( "parseAtomicLong", "12345678912345", params );
        Assert.assertEquals( 12345678912345L, atomicLongValue.get() );
        
        final Boolean booleanValue = parse( "parseBoolean", "yes", params );
        Assert.assertEquals( Boolean.TRUE, booleanValue );
        
        params.put( "enum-type", "org.nerd4j.csv.model.Product$Currency" );
        final Product.Currency enumValue = parse( "parseEnum", "EUR",params );
        Assert.assertEquals( Product.Currency.EUR, enumValue );

        params.put( "pattern", "dd/MM/yyyy" );        
        final Date dateValue = parse( "parseDate", "12/04/1976",params );
        Assert.assertEquals( 198111600000L, dateValue.getTime() );
        
	}
    
    @Test
    public void testFormatValue()
    {
        
        final Map<String,String> params = new HashMap<String,String>();
        
        final String byteValue = format( "formatByte", Byte.valueOf((byte)12), params );
        Assert.assertEquals( "12", byteValue );
        
        final String shortValue = format( "formatShort", Short.valueOf((short)12345), params );
        Assert.assertEquals( "12345", shortValue );
        
        final String integerValue = format( "formatInteger", Integer.valueOf(123456789), params );
        Assert.assertEquals( "123456789", integerValue );
        
        final String longValue = format( "formatLong", Long.valueOf(12345678912345L), params );
        Assert.assertEquals( "12345678912345", longValue );
        
        final String floatValue = format( "formatFloat", Float.valueOf(12345.01f), params );
        Assert.assertEquals( "12345.01", floatValue );
        
        final String doubleValue = format( "formatDouble", Double.valueOf(12345.01d), params );
        Assert.assertEquals( "12345.01", doubleValue );
        
        final BigInteger bigIntVal = new BigInteger( new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 } );
        final String bigIntegerValue = format( "formatBigInteger", bigIntVal, params );
        Assert.assertEquals( "311917102708983781990730508", bigIntegerValue );

        final BigDecimal bigDecVal = new BigDecimal( bigIntVal, 5 );
        final String bigDecimalValue = format( "formatBigInteger", bigDecVal, params );
        Assert.assertEquals( "3119171027089837819907.30508", bigDecimalValue );

        final String atomicIntegerValue = format( "formatAtomicInteger", new AtomicInteger(123456789), params );
        Assert.assertEquals( "123456789", atomicIntegerValue );
        
        final String atomicLongValue = format( "formatAtomicLong", new AtomicLong(12345678912345L), params );
        Assert.assertEquals( "12345678912345", atomicLongValue );
        
        final String booleanValue = format( "formatBoolean", Boolean.TRUE, params );
        Assert.assertEquals( "true", booleanValue );
        
        params.put( "enum-type", "org.nerd4j.csv.model.Product$Currency" );
        final String enumValue = format( "formatEnum", Product.Currency.EUR, params );
        Assert.assertEquals( "EUR", enumValue );
        
        params.put( "pattern", "dd/MM/yyyy" );
        params.put( "locale", "IT_it" );
        final String dateValue = format( "formatDate", new Date(198111600000L), params );
        Assert.assertEquals( "12/04/1976", dateValue );
        
    }
    
    @SuppressWarnings("unchecked")
    private <T> T parse( String name, String source, Map<String,String> params )
    {
        
        final CSVRegistryEntryFactory<CSVFieldConverter<?, ?>> factory = registry.getConverterRegistry().provideFactory( name, params );
        final CSVFieldConverter<String,T> converter = (CSVFieldConverter<String,T>) factory.create();
        return converter.convert( source, context );
        
    }

    @SuppressWarnings("unchecked")
    private <T> String format( String name, T source, Map<String,String> params )
    {
        
    	final CSVRegistryEntryFactory<CSVFieldConverter<?, ?>> factory = registry.getConverterRegistry().provideFactory( name, params );
        final CSVFieldConverter<T,String> converter = (CSVFieldConverter<T,String>) factory.create();
        return converter.convert( source, context );
        
    }
    
}