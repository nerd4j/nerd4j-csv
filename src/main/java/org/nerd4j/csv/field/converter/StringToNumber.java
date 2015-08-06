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
package org.nerd4j.csv.field.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.nerd4j.csv.field.CSVFieldConverter;


/**
 * Implementation of the {@link CSVFieldConverter} interface
 * that converts {@link String}s into {@link Number}s.
 * 
 * <p>
 *  This converter accepts all the Java standard implementations
 *  of the {@link java.lang.Number} interface i.e.:
 *  <ul>
 *   <li>{@link java.lang.Byte}</li>
 *   <li>{@link java.lang.Short}</li>
 *   <li>{@link java.lang.Integer}</li>
 *   <li>{@link java.lang.Long}</li>
 *   
 *   <li>{@link java.lang.Float}</li>
 *   <li>{@link java.lang.Double}</li>
 *   
 *   <li>{@link java.math.BigInteger}</li>
 *   <li>{@link java.math.BigDecimal}</li>
 *   
 *   <li>{@link java.util.concurrent.atomic.AtomicInteger}</li>
 *   <li>{@link java.util.concurrent.atomic.AtomicLong}</li>
 *  </ul>
 * </p>
 * 
 * @param <N> type of the {@link Number} to be returned.
 * 
 * @author Nerd4j Team
 */
public final class StringToNumber<N extends Number> extends AbstractCSVFieldConverter<String,N>
{

    /** The type of the {@link Number} to be returned. */
    private final NumberType numberType;
    
    /** The number format to use to format the number. */
    private final DecimalFormat numberFormat;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param numberType one of the accepted implementations of the {@link Number}.
     */
    public StringToNumber( final Class<N> numberType )
    {

        this( numberType, null, null );
        
    }

    /**
     * Constructor with parameters.
     * 
     * @param numberType one of the accepted implementations of the {@link Number}.
     * @param numberPattern the pattern that describes the number format.
     */
    public StringToNumber( final Class<N> numberType, final String numberPattern )
    {
        this ( numberType, numberPattern, null );
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param numberType one of the accepted implementations of the {@link Number}.
     * @param numberPattern the pattern that describes the number format.
     * @param numberLocale locale for formatter symbols, ignored if no pattern
     */
    public StringToNumber( final Class<N> numberType, final String numberPattern, final Locale numberLocale )
    {
        
        super( "Unable to convert {1} into " + numberType );
        
        if( numberType == null )
            throw new NullPointerException( "The number type is mandatory cannot be null" );
        
        this.numberType = getNumberType( numberType );
        
        if( numberPattern == null || numberPattern.isEmpty() )
            this.numberFormat = null;
        else
        {
        	if ( numberLocale == null )
        		this.numberFormat = new DecimalFormat( numberPattern );
        	else
        		this.numberFormat = new DecimalFormat( numberPattern,
        				DecimalFormatSymbols.getInstance( numberLocale ) );
            
            /* Enable BigDecimal format return if needed */
            switch ( this.numberType )
            {
            
	            case BIG_DECIMAL:
	            case BIG_INTEGER:
	            	this.numberFormat.setParseBigDecimal( true );
	            	break;
			
	            default: break;
            }
        }

    }

    
    /* ***************** */
    /*  EXTENSION HOOKS  */
    /* ***************** */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected N performConversion( final String source ) throws Exception
    {

        if( numberFormat == null )
            return (N) valueOf( source );
        else
            return (N) parseNumber( source );
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    /**
     * Returns the {@link NumberType} corresponding to the
     * {@link Number} implementation described by the given
     * {@link Class}.
     * 
     * @param numberType the number type to be returned.
     * @return the {@link NumberType} found or <code>null</code>.
     */
    private NumberType getNumberType( final Class<N> numberType )
    {
               
        if( numberType.equals(Byte.class) )         return NumberType.BYTE;       
        else if( numberType.equals(Short.class) )   return NumberType.SHORT;        
        else if( numberType.equals(Integer.class) ) return NumberType.INTEGER;
        else if( numberType.equals(Long.class) )    return NumberType.LONG;
        
        else if( numberType.equals(Float.class) )   return NumberType.FLOAT;
        else if( numberType.equals(Double.class) )  return NumberType.DOUBLE;
        
        else if( numberType.equals(BigInteger.class) ) return NumberType.BIG_INTEGER;
        else if( numberType.equals(BigDecimal.class) ) return NumberType.BIG_DECIMAL;
        
        else if( numberType.equals(AtomicInteger.class) ) return NumberType.ATOMIC_INTEGER;
        else if( numberType.equals(AtomicLong.class) )    return NumberType.ATOMIC_LONG;
        
        else return null;
        
    }
    
    
    /**
     * Performs the parsing of the given String and returns
     * the represented number accordingly with the numberType.
     * 
     * @param source string to parse.
     * @return the represented number.
     */
    private Number valueOf( final String source )
    {
        
        switch( numberType )
        {
        
        case BYTE:    return Byte.valueOf( source );
        case SHORT:   return Short.valueOf( source );
        case INTEGER: return Integer.valueOf( source );
        case LONG:    return Long.valueOf( source );
        
        case FLOAT:   return Float.valueOf( source );
        case DOUBLE:  return Double.valueOf( source );

        case BIG_INTEGER: return new BigInteger( source );
        case BIG_DECIMAL: return new BigDecimal( source );

        case ATOMIC_INTEGER: return new AtomicInteger( Integer.parseInt(source) );
        case ATOMIC_LONG:    return new AtomicLong( Long.parseLong(source) );
        
        default: return null;
        
        }
       
    }
    
    /**
     * Performs the parsing of the given String and returns
     * the represented number accordingly with the numberType.
     * 
     * @param source string to parse.
     * @return the represented number.
     */
    private Number parseNumber( final String source )
    throws ParseException
    {
        
        final Number number = numberFormat.parse( source );
        
        switch( numberType )
        {
        
        case BYTE:    return number.byteValue();
        case SHORT:   return number.shortValue();
        case INTEGER: return number.intValue();
        case LONG:    return number.longValue();
        
        case FLOAT:   return number.floatValue();
        case DOUBLE:  return number.doubleValue();
        
        /* In this case the formatter returns big decimal */
        case BIG_INTEGER: return ((BigDecimal) number).toBigInteger();
        
        /* In this case the formatter returns big decimal */
        case BIG_DECIMAL: return (BigDecimal) number;
        
        case ATOMIC_INTEGER: return new AtomicInteger( number.intValue() );
        case ATOMIC_LONG:    return new AtomicLong( number.longValue() );
        
        default: return null;
        
        }
        
    }
    
    
    /**
     * Enumerates the number types handled by this converter.
     * 
     * @author Nerd4j Team
     */
    private static enum NumberType
    {
        
        BYTE,
        SHORT,
        INTEGER,
        LONG,
        
        FLOAT,
        DOUBLE,
        
        BIG_INTEGER,
        BIG_DECIMAL,
        
        ATOMIC_INTEGER,
        ATOMIC_LONG;
        
    }
    
}
