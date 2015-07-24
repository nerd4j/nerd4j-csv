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
import java.util.Map;

import org.nerd4j.csv.exception.CSVConfigurationException;
import org.nerd4j.csv.field.CSVFieldValidator;
import org.nerd4j.csv.field.validator.CheckNumberRange;
import org.nerd4j.csv.field.validator.CheckRegEx;
import org.nerd4j.csv.field.validator.CheckStringLength;


/**
 * Represents a registry of {@link CSVFieldValidator}s.
 * 
 * <p>
 *  This registry is internally used to refer {@link CSVFieldValidator}s by name.
 *  It is possible to register custom providers able to provide custom validators.
 * </p>
 * 
 * <p>
 *  The following validators are registered by default:
 *  <ul>
 *    <li>checkStringLength    : length   = the actual length of the string (mandatory)</li>
 *    <li>checkStringLength    : min, max = the minimum and maximum length of the string (mandatory)</li>
 *    <br />
 *    <li>checkRegEx           : pattern  = the regular expression pattern to match (mandatory)</li>
 *    <br />
 *    <li>checkByteRange       : min, max = the minimum and maximum value (at least one mandatory)</li>
 *    <li>checkShortRange      : min, max = the minimum and maximum value (at least one mandatory)</li>
 *    <li>checkIntegerRange    : min, max = the minimum and maximum value (at least one mandatory)</li>
 *    <li>checkLongRange       : min, max = the minimum and maximum value (at least one mandatory)</li>
 *    <li>checkFloatRange      : min, max = the minimum and maximum value (at least one mandatory)</li>
 *    <li>checkDoubleRange     : min, max = the minimum and maximum value (at least one mandatory)</li>
 *    <li>checkBigIntegerRange : min, max = the minimum and maximum value (at least one mandatory)</li>
 *    <li>checkBigDecimalRange : min, max = the minimum and maximum value (at least one mandatory)</li>
 *  </ul>
 * </p>
 * 
 * @author Nerd4j Team
 */
final class CSVFieldValidatorRegistry extends CSVAbstractRegistry<CSVFieldValidator<?>>
{

    
    /**
     * Default constructor.
     * 
     */
    public CSVFieldValidatorRegistry()
    {
        
        super();
        
        this.registerDefaults();
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    /**
     * Creates and registers the default entries and converters.
     * 
     */
    private void registerDefaults()
    {

        
        /* Validator that checks the length of a given string */
        setProvider( "checkStringLength", new CSVRegistryEntryProvider<CSVFieldValidator<?>>()
        {
            @Override
            public CSVFieldValidator<?> get( Map<String,String> params )
            {
                final String length = params.get( "length" );
                if( length != null && ! length.isEmpty() )
                {
                    final int lengthVal = Integer.parseInt( length );
                    return new CheckStringLength( lengthVal );
                }
                
                final String min = params.get( "min" );
                final String max = params.get( "max" );
                if( min == null || min.isEmpty() ||
                    max == null || max.isEmpty() )
                    throw new CSVConfigurationException( "Unable to build validator, neither 'lenght' nor 'min','max' parameters are available" );
                
                final int minVal = Integer.parseInt( min );
                final int maxVal = Integer.parseInt( max );
                
                return new CheckStringLength( minVal, maxVal );
                
            }
        });

        /* Validator that checks if the given string matches the regular expression */
        setProvider( "checkRegEx", new CSVRegistryEntryProvider<CSVFieldValidator<?>>()
        {
            @Override
            public CSVFieldValidator<?> get( Map<String,String> params )
            {
                final String regEx = params.get( "pattern" );
                if( regEx == null || regEx.isEmpty() )
                    throw new CSVConfigurationException( "Unable to build validator, regular expression 'pattern' unavailable" );
                
                return new CheckRegEx( regEx );
                
            }
        });
        
        
        /* Validators that checks minimum and maximum value of a given number */
        setProvider( "checkByteRange", new AbstractCheckNumberRangeProvider<Byte>()
        {

			@Override
			protected Byte parseNumber( String number )
			{
				return Byte.parseByte( number );
			}
            
        });
        
        setProvider( "checkShortRange", new AbstractCheckNumberRangeProvider<Short>()
        {

			@Override
			protected Short parseNumber( String number )
			{
				return Short.parseShort( number );
			}
            
        });
        
        setProvider( "checkIntegerRange", new AbstractCheckNumberRangeProvider<Integer>()
        {

			@Override
			protected Integer parseNumber( String number )
			{
				return Integer.parseInt( number );
			}
            
        });
        
        setProvider( "checkLongRange", new AbstractCheckNumberRangeProvider<Long>()
        {

			@Override
			protected Long parseNumber( String number )
			{
				return Long.parseLong( number );
			}
            
        });
        
        setProvider( "checkFloatRange", new AbstractCheckNumberRangeProvider<Float>()
        {

			@Override
			protected Float parseNumber( String number )
			{
				return Float.parseFloat( number );
			}
            
        });
        
        setProvider( "checkDoubleRange", new AbstractCheckNumberRangeProvider<Double>()
        {

			@Override
			protected Double parseNumber( String number )
			{
				return Double.parseDouble( number );
			}
            
        });
        
        setProvider( "checkBigIntegerRange", new AbstractCheckNumberRangeProvider<BigInteger>()
        {

			@Override
			protected BigInteger parseNumber( String number )
			{
				return new BigInteger( number );
			}
            
        });
        
        setProvider( "checkBigDecimalRange", new AbstractCheckNumberRangeProvider<BigDecimal>()
        {

			@Override
			protected BigDecimal parseNumber( String number )
			{
				return new BigDecimal( number );
			}
            
        });
        
    }
    
    /**
     * Represents an abstract provider able to create {@link CheckNumberRange} validators.
     * 
     * @param <N> type of the {@link Number} to be validated.
     *
     * @author Nerd4j Team
     */
    private abstract static class AbstractCheckNumberRangeProvider<N extends Number & Comparable<N>> implements CSVRegistryEntryProvider<CSVFieldValidator<?>>
    {
        
        /**
         * {@inheritDoc}
         */
        @Override
        public CSVFieldValidator<N> get( Map<String,String> params )
        {
        	
            final String min = params.get( "min" );
            final String max = params.get( "max" );
            
            final boolean emptyMin = (min == null || min.isEmpty());
            final boolean emptyMax = (max == null || max.isEmpty());
            
            if ( emptyMin && emptyMax )
            	throw new CSVConfigurationException( "Unable to build validator, neither 'min' nor 'max' parameters are available" );
            
            final N cmin = emptyMin ? null : parseNumber( min );
            final N cmax = emptyMax ? null : parseNumber( max );
            
            return new CheckNumberRange<N>( cmin, cmax );
            
        }
        
        /**
         * Parse a number from its <tt>String</tt> representation.
         * <p>
         * Given number parameter is ensured not null and not empty.
         * </p>
         * 
         * @param number string representation to be parsed.
         * @return parsed number
         */
        protected abstract N parseNumber( String number );
        
    }
    
}