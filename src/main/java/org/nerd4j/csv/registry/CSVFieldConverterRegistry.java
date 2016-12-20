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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.nerd4j.csv.exception.CSVConfigurationException;
import org.nerd4j.csv.field.CSVFieldConverter;
import org.nerd4j.csv.field.converter.BooleanToString;
import org.nerd4j.csv.field.converter.DateToString;
import org.nerd4j.csv.field.converter.EmptyCSVFieldConverter;
import org.nerd4j.csv.field.converter.EnumToString;
import org.nerd4j.csv.field.converter.NumberToString;
import org.nerd4j.csv.field.converter.StringToBoolean;
import org.nerd4j.csv.field.converter.StringToDate;
import org.nerd4j.csv.field.converter.StringToEnum;
import org.nerd4j.csv.field.converter.StringToNumber;
import org.nerd4j.i18n.LocaleUtil;


/**
 * Represents a registry of {@link CSVFieldConverter}s.
 * 
 * <p>
 *  This registry is internally used to refer {@link CSVFieldConverter}s by name.
 *  It is possible to register custom providers able to provide custom converters.
 * </p>
 * 
 * <p>
 *  The following converters are registered by default:
 *  <ul>
 *    <li>parseByte          : pattern = the number parse pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>parseShort         : pattern = the number parse pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>parseInteger       : pattern = the number parse pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>parseLong          : pattern = the number parse pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>parseFloat         : pattern = the number parse pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>parseDouble        : pattern = the number parse pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>parseBigInteger    : pattern = the number parse pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>parseBigDecimal    : pattern = the number parse pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>parseAtomicInteger : pattern = the number parse pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>parseAtomicLong    : pattern = the number parse pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <br />
 *    <li>formatByte         : pattern = the number format pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>formatShort        : pattern = the number format pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>formatInteger      : pattern = the number format pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>formatLong         : pattern = the number format pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>formatFloat        : pattern = the number format pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>formatDouble       : pattern = the number format pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>formatBigInteger   : pattern = the number format pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>formatBigDecimal   : pattern = the number format pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>formatAtomicInteger: pattern = the number format pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>formatAtomicLong   : pattern = the number format pattern (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <br />
 *    <li>parseBoolean       : no parameters</li>
 *    <li>formatBoolean      : no parameters</li>
 *    <br />
 *    <li>parseDate          : pattern   = the date format pattern (mandatory)
 *                             time-zone = input date timezone (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <li>formatDate         : pattern = the date format pattern (mandatory)
 *                             time-zone = output date timezone (optional)
 *                             locale  = the pattern locale for symbols (optional)</li>
 *    <br />
 *    <li>parseEnum          : enum-type = the fully qualified enum class name (mandatory)</li>
 *    <li>formatEnum         : enum-type = the fully qualified enum class name (mandatory)</li>
 *  </ul>
 * </p>
 * 
 * @author Nerd4j Team
 */
final class CSVFieldConverterRegistry extends CSVAbstractRegistry<CSVFieldConverter<?,?>>
{


    /**
     * Default constructor.
     * 
     */
    public CSVFieldConverterRegistry()
    {
        
        super();
        
        this.registerDefaults();
        
    }
    
    
    /* *************** */
    /*  INNER CLASSES  */
    /* *************** */

    
    /**
     * Represents a provider able to create {@link String} to {@link Number} converters.
     * 
     * @param <N> type of the {@link Number} to be returned.
     *
     * @author Nerd4j Team
     */
    private static final class StringToNumberProvider<N extends Number> implements CSVRegistryEntryProvider<CSVFieldConverter<?,?>>
    {
        
        /** The type of the {@link Number} to be returned. */
        private final Class<N> numberType;

        /**
         * Constructor with parameters.
         * 
         * @param numberType one of the accepted implementations of the {@link Number}.
         */
        public StringToNumberProvider( final Class<N> numberType )
        {
            
            super();
            
            this.numberType = numberType;
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public CSVFieldConverter<String,N> get( Map<String,String> params )
        {
            final String pattern = params.get("pattern");
            final String nlocale = params.get("locale");
            
            final Locale locale = nlocale == null ? null : LocaleUtil.getLocale(nlocale);
            
            return new StringToNumber<N>( numberType, pattern, locale );
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void validate( Map<String,String> params )
        {
        	if( params == null || params.isEmpty() ) return;
        	
        	try{
        	
        		final String pattern = params.get( "pattern" );
        		if( pattern != null && ! pattern.isEmpty() )
        			new DecimalFormat( pattern );
        	
        		final String locale = params.get( "locale" );
        		if( locale != null && ! locale.isEmpty() )
        			LocaleUtil.getLocale( locale );
        	
        	}catch( Exception ex )
        	{
        		throw new CSVConfigurationException( ex );        		
        	}
        }
        
    }
    
    /**
     * Represents a provider able to create {@link Number} to {@link String} converters.
     * 
     * @param <N> type of the {@link Number} to be returned.
     *
     * @author Nerd4j Team
     */
    private static final class NumberToStringProvider<N extends Number> implements CSVRegistryEntryProvider<CSVFieldConverter<?,?>>
    {
    	
    	/** The type of the {@link Number} to be provided. */
        private final Class<N> numberType;
        
        /**
         * Constructor with parameters.
         * 
         * @param numberType one of the accepted implementations of the {@link Number}.
         */
        public NumberToStringProvider( final Class<N> numberType )
        {
            
            super();
            
            this.numberType = numberType;
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public CSVFieldConverter<N,String> get( Map<String,String> params )
        {
            final String pattern = params.get("pattern");
            final String nlocale = params.get("locale");
            
            final Locale locale = nlocale == null ? null : LocaleUtil.getLocale(nlocale);
            
            return new NumberToString<N>( numberType, pattern, locale );
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void validate( Map<String,String> params )
        {
        	if( params == null || params.isEmpty() ) return;
        	
        	try{
        	
        		final String pattern = params.get( "pattern" );
        		if( pattern != null && ! pattern.isEmpty() )
        			new DecimalFormat( pattern );
        	
        		final String locale = params.get( "locale" );
        		if( locale != null && ! locale.isEmpty() )
        			LocaleUtil.getLocale( locale );
        	
        	}catch( Exception ex )
        	{
        		throw new CSVConfigurationException( ex );        		
        	}
        }
        
    }
    
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    /**
     * Creates and registers the default entries and providers.
     * 
     */
    @SuppressWarnings("rawtypes")
    private void registerDefaults()
    {
        
        /* The empty converter to use in case of missing configuration. */
        setFactory( "default", new CSVRegistryEntryFactory<CSVFieldConverter<?,?>>()
        {
        	/** Singleton instance of the empty converter. */
        	private final CSVFieldConverter<?,?> emptyConverter = new EmptyCSVFieldConverter<String>( String.class );
        	
        	/**
        	 * {@inheritDoc}
        	 */
			@Override
			public CSVFieldConverter<?,?> create()
			{
				return emptyConverter;
			}
        });
        
     
        /* String to Number Providers. */
        setProvider( "parseByte",           new StringToNumberProvider<Byte>(Byte.class) );
        setProvider( "parseShort",          new StringToNumberProvider<Short>(Short.class) );
        setProvider( "parseInteger",        new StringToNumberProvider<Integer>(Integer.class) );
        setProvider( "parseLong",           new StringToNumberProvider<Long>(Long.class) );
                                            
        setProvider( "parseFloat",          new StringToNumberProvider<Float>(Float.class) );
        setProvider( "parseDouble",         new StringToNumberProvider<Double>(Double.class) );
                                            
        setProvider( "parseBigInteger",     new StringToNumberProvider<BigInteger>(BigInteger.class) );
        setProvider( "parseBigDecimal",     new StringToNumberProvider<BigDecimal>(BigDecimal.class) );
                                            
        setProvider( "parseAtomicInteger",  new StringToNumberProvider<AtomicInteger>(AtomicInteger.class) );
        setProvider( "parseAtomicLong",     new StringToNumberProvider<AtomicLong>(AtomicLong.class) );
                                            
                                            
        /* Number to String Providers. */   
        setProvider( "formatByte",          new NumberToStringProvider<Byte>(Byte.class) );
        setProvider( "formatShort",         new NumberToStringProvider<Short>(Short.class) );
        setProvider( "formatInteger",       new NumberToStringProvider<Integer>(Integer.class) );
        setProvider( "formatLong",          new NumberToStringProvider<Long>(Long.class) );
                                            
        setProvider( "formatFloat",         new NumberToStringProvider<Float>(Float.class) );
        setProvider( "formatDouble",        new NumberToStringProvider<Double>(Double.class) );
                                            
        setProvider( "formatBigInteger",    new NumberToStringProvider<BigInteger>(BigInteger.class) );
        setProvider( "formatBigDecimal",    new NumberToStringProvider<BigDecimal>(BigDecimal.class) );
        
        setProvider( "formatAtomicInteger", new NumberToStringProvider<AtomicInteger>(AtomicInteger.class) );
        setProvider( "formatAtomicLong",    new NumberToStringProvider<AtomicLong>(AtomicLong.class) );

        
        /* String to Boolean Provider. */
        setProvider( "parseBoolean", new CSVRegistryEntryProvider<CSVFieldConverter<?,?>>()
        {
        	/**
        	 * {@inheritDoc}
        	 */
            @Override
            public CSVFieldConverter<String,Boolean> get( Map<String,String> params )
            {
                return new StringToBoolean();
            }
            /**
        	 * {@inheritDoc}
        	 */
            @Override
            public void validate( Map<String,String> params ) {}
        });
        
        /* Boolean to String Provider. */
        setProvider( "formatBoolean", new CSVRegistryEntryProvider<CSVFieldConverter<?,?>>()
        {
        	/**
        	 * {@inheritDoc}
        	 */
            @Override
            public CSVFieldConverter<Boolean,String> get( Map<String,String> params )
            {
                return new BooleanToString();
            }
            /**
        	 * {@inheritDoc}
        	 */
            @Override
            public void validate( Map<String,String> params ) {}
         });

        /* String to Date Provider. */
        setProvider( "parseDate", new CSVRegistryEntryProvider<CSVFieldConverter<?,?>>()
        {
        	/**
        	 * {@inheritDoc}
        	 */
            @Override
            public CSVFieldConverter<String,Date> get( Map<String,String> params )
            {
            	final String pattern    = params.get( "pattern"   );
            	final String timeZoneID = params.get( "time-zone" );
            	final String dlocale    = params.get( "locale"    );
                
            	final TimeZone timeZone;
            	if( timeZoneID == null )
            	{
            		timeZone = TimeZone.getDefault();
            	}
            	else
            	{
            		
					/*
					 * A nasty trick to walk around implicit GMT timezone got
					 * from TimeZone.getTimeZone(timeZoneID ) if timeZoneId
					 * cannot be converted to any useful value.
					 */
	            	timeZone = TimeZone.getTimeZone( timeZoneID );
	
	                /* Not nullable, is at least GMT */
	                final String tzID = timeZone.getID();
	
					/*
					 * Check if got timezone is a GMT default o real value.
					 * If is a default throw exception.
					 */
	                if ( tzID.equals( "GMT" ) && !tzID.equals( timeZoneID ) ) 
	                	throw new CSVConfigurationException( "The value time-zone (" +
	                			timeZoneID + ") do not represent a TimeZone identifier" );
	                
                }
                
            	final Locale locale = dlocale == null ? null : LocaleUtil.getLocale(dlocale);
                return new StringToDate( pattern, timeZone, locale ); 
            }
            /**
        	 * {@inheritDoc}
        	 */
            @Override
            public void validate( Map<String,String> params )
            {            	
            	if( params == null || params.isEmpty() ) return;
            	
            	try{
                	
            		final String pattern = params.get( "pattern" );
            		if( pattern != null && ! pattern.isEmpty() )
            			new SimpleDateFormat( pattern );
            	
            		final String locale = params.get( "locale" );
            		if( locale != null && ! locale.isEmpty() )
            			LocaleUtil.getLocale( locale );
            		
            		final String timezone = params.get( "time-zone" );
            		if( timezone != null && ! timezone.isEmpty() )
            			TimeZone.getTimeZone( timezone );
            	
            	}catch( Exception ex )
            	{
            		throw new CSVConfigurationException( ex );        		
            	}
            }
        });
        
        /* Date to String Provider. */
        setProvider( "formatDate", new CSVRegistryEntryProvider<CSVFieldConverter<?,?>>()
        {
        	/**
        	 * {@inheritDoc}
        	 */
            @Override
            public CSVFieldConverter<Date,String> get( Map<String,String> params )
            {
            	final String pattern = params.get( "pattern" );
            	final String timeZoneID = params.get( "time-zone" );
            	final String dlocale    = params.get( "locale"    );
            	
            	final TimeZone timeZone;            	
            	if( timeZoneID == null )
            	{
            		timeZone = TimeZone.getDefault();
            	}
            	else
            	{
            		
					/*
					 * A nasty trick to walk around implicit GMT timezone got
					 * from TimeZone.getTimeZone(timeZoneID ) if timeZoneId
					 * cannot be converted to any useful value.
					 */
	            	timeZone = TimeZone.getTimeZone( timeZoneID );
	
	                /* Not nullable, is at least GMT */
	                final String tzID = timeZone.getID();
	
					/*
					 * Check if got timezone is a GMT default o real value.
					 * If is a default throw exception.
					 */
	                if ( tzID.equals( "GMT" ) && !tzID.equals( timeZoneID ) ) 
	                	throw new CSVConfigurationException( "The value time-zone (" +
	                			timeZoneID + ") do not represent a TimeZone identifier" );
	                
                }
            	
            	final Locale locale = dlocale == null ? null : LocaleUtil.getLocale(dlocale);
            	return new DateToString( pattern, timeZone, locale ); 
            }
            /**
        	 * {@inheritDoc}
        	 */
            @Override
            public void validate( Map<String,String> params )
            {            	
            	if( params == null || params.isEmpty() ) return;
            	
            	try{
                	
            		final String pattern = params.get( "pattern" );
            		if( pattern != null && ! pattern.isEmpty() )
            			new SimpleDateFormat( pattern );
            	
            		final String locale = params.get( "locale" );
            		if( locale != null && ! locale.isEmpty() )
            			LocaleUtil.getLocale( locale );
            		
            		final String timezone = params.get( "time-zone" );
            		if( timezone != null && ! timezone.isEmpty() )
            			TimeZone.getTimeZone( timezone );
            	
            	}catch( Exception ex )
            	{
            		throw new CSVConfigurationException( ex );        		
            	}
            }
        });
        
        /* String to Enum Provider. */
        setProvider( "parseEnum", new CSVRegistryEntryProvider<CSVFieldConverter<?,?>>()
        {
        	/**
        	 * {@inheritDoc}
        	 */
            @Override
            @SuppressWarnings({ "unchecked" })
            public CSVFieldConverter<String,Enum<?>> get( Map<String,String> params )
            {                
                final String enumType = params.get( "enum-type" );
                if( enumType == null )
                    throw new CSVConfigurationException( "The enum-type is mandatory to build parseEnum" );
                
                try{

                    final Class<?> enumClass = Class.forName( enumType );
                    
                    if( ! enumClass.isEnum() )
                        throw new CSVConfigurationException( "The value enum-type to not represent an enum" );
                    
                    return new StringToEnum( enumClass );
                    
                }catch( ClassNotFoundException ex )
                {
                    throw new CSVConfigurationException( "The value enum-type do not represent a canonical class name", ex );
                }
            }
            /**
        	 * {@inheritDoc}
        	 */
            @Override
            public void validate( Map<String,String> params )
            {            	
            	if( params == null || params.isEmpty() )
            		throw new CSVConfigurationException( "The parameter enum-type is mandatory" );
            	
            	final String enumType = params.get( "enum-type" );
                if( enumType == null )
                    throw new CSVConfigurationException( "The parameter enum-type is mandatory." );
                
                try{

                    final Class<?> enumClass = Class.forName( enumType );
                    
                    if( ! enumClass.isEnum() )
                        throw new CSVConfigurationException( "The value enum-type to not represent an enum" );
                    
                }catch( ClassNotFoundException ex )
                {
                    throw new CSVConfigurationException( "The value enum-type do not represent a canonical class name", ex );
                }
            }
        });
        
        /* Enum to String Provider. */
        setProvider( "formatEnum", new CSVRegistryEntryProvider<CSVFieldConverter<?,?>>()
        {
        	/**
        	 * {@inheritDoc}
        	 */
        	@Override
            @SuppressWarnings({ "unchecked" })
            public CSVFieldConverter<Enum<?>,String> get( Map<String,String> params )
            {
                final String enumType = params.get( "enum-type" );
                if( enumType == null )
                    throw new CSVConfigurationException( "The enum-type is mandatory to build formatEnum" );
                
                try{

                    final Class<?> enumClass = Class.forName( enumType );
                    
                    if( ! enumClass.isEnum() )
                        throw new CSVConfigurationException( "The value enum-type do not represent an enum" );
                    
                    return new EnumToString( enumClass );
                    
                }catch( ClassNotFoundException ex )
                {
                    throw new CSVConfigurationException( "The value enum-type do not represent a canonical class name", ex );
                }
            }
        	/**
        	 * {@inheritDoc}
        	 */
            @Override
            public void validate( Map<String,String> params )
            {            	
            	if( params == null || params.isEmpty() )
            		throw new CSVConfigurationException( "The parameter enum-type is mandatory" );
            	
            	final String enumType = params.get( "enum-type" );
                if( enumType == null )
                    throw new CSVConfigurationException( "The parameter enum-type is mandatory." );
                
                try{

                    final Class<?> enumClass = Class.forName( enumType );
                    
                    if( ! enumClass.isEnum() )
                        throw new CSVConfigurationException( "The value enum-type to not represent an enum" );
                    
                }catch( ClassNotFoundException ex )
                {
                    throw new CSVConfigurationException( "The value enum-type do not represent a canonical class name", ex );
                }
            }
        });
        
    }
    
}