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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * Implementation of the {@link org.nerd4j.csv.field.CSVFieldConverter CSVFieldConverter}
 * interface that converts {@link Number}s into {@link String}s.
 * 
 * <p>
 * This converter accepts all the Java standard implementations
 * of the {@link java.lang.Number} interface i.e.:
 * <ul>
 *  <li>{@link java.lang.Byte}</li>
 *  <li>{@link java.lang.Short}</li>
 *  <li>{@link java.lang.Integer}</li>
 *  <li>{@link java.lang.Long}</li>
 *   
 *  <li>{@link java.lang.Float}</li>
 *  <li>{@link java.lang.Double}</li>
 *   
 *  <li>{@link java.math.BigInteger}</li>
 *  <li>{@link java.math.BigDecimal}</li>
 *   
 *  <li>{@link java.util.concurrent.atomic.AtomicInteger}</li>
 *  <li>{@link java.util.concurrent.atomic.AtomicLong}</li>
 * </ul>
 * 
 * @param <N> type of the {@link Number} to convert.
 * 
 * @author Nerd4j Team
 */
public final class NumberToString<N extends Number> extends AbstractCSVFieldConverter<N,String>
{

    /** The number format to use to format the number. */
    private final NumberFormat numberFormat;
    

    /**
     * Constructor with parameters.
     * 
     * @param numberType one of the accepted implementations of the {@link Number}.
     */
    public NumberToString( final Class<N> numberType )
    {

        this( numberType, null, null );
        
    }

    
    /**
     * Constructor with parameters.
     * 
     * @param numberType one of the accepted implementations of the {@link Number}.
     * @param numberPattern the pattern that describes the number format.
     */
    public NumberToString( final Class<N> numberType, final String numberPattern )
    {
        
    	this( numberType, numberPattern, null );
        
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param numberType one of the accepted implementations of the {@link Number}.
     * @param numberPattern the pattern that describes the number format.
     * @param numberLocale locale for formatter symbols, ignored if no pattern
     */
    public NumberToString( final Class<N> numberType, final String numberPattern, final Locale numberLocale )
    {
        
        super( numberType, String.class, "Unable to convert {1} into String" );
        
        if( numberPattern == null || numberPattern.isEmpty() )
            this.numberFormat = null;
        else
        {
        	if ( numberLocale == null )
        		this.numberFormat = new DecimalFormat( numberPattern );
        	else
        		this.numberFormat = new DecimalFormat( numberPattern,
        				DecimalFormatSymbols.getInstance( numberLocale ) );
        }
        
    }

    
    /* ***************** */
    /*  EXTENSION HOOKS  */
    /* ***************** */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String performConversion( final N source ) throws Exception
    {

        if( numberFormat != null )
            return numberFormat.format( source );
        else
            return source.toString();
        
    }
    
}