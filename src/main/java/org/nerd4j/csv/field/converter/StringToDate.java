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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.nerd4j.csv.field.CSVFieldConverter;


/**
 * Implementation of the {@link CSVFieldConverter} interface
 * that converts {@link String}s into {@link Date}s.
 * 
 * <p>
 *  This converter uses a {@link DateFormat} to perform the
 *  conversion. Therefore it requires the pattern to use
 *  during the parsing. All the informations on how to
 *  write the date pattern can be found in the javadoc of
 *  the {@link DateFormat} class.
 * </p>
 * 
 * @author Nerd4j Team
 */
public final class StringToDate extends AbstractCSVFieldConverter<String,Date>
{

    /** The date format to use for parsing dates. */
    private final DateFormat dateFormat;
    
    
    /**
     * Constructor with parameter.
     * 
     * @param datePattern the pattern that describes the date format.
     */
    public StringToDate( final String datePattern )
    {

        this( datePattern, null, null );
        
    }
    
    /**
     * Constructor with parameter.
     * 
     * @param datePattern the pattern that describes the date format.
     * @param timeZone    (Optional) date time zone different from default one.
     */
    public StringToDate( final String datePattern, final TimeZone timeZone )
    {

        this( datePattern, timeZone, null );
        
    }

    /**
     * Constructor with parameter.
     * 
     * @param datePattern the pattern that describes the date format.
     * @param timeZone    (Optional) date time zone different from default one.
     * @param dateLocale  locale for formatter symbols, ignored if no pattern
     */
    public StringToDate( final String datePattern, final TimeZone timeZone, final Locale dateLocale )
    {
    	
    	super( String.class, Date.class, "Unable to convert {1} into Date" );
    	
    	if( datePattern == null || datePattern.isEmpty() )
    	{
    		this.dateFormat = new SimpleDateFormat();
    	}
    	else
    	{
    		if ( dateLocale == null )
    			this.dateFormat = new SimpleDateFormat( datePattern );
    	    else
    	    	this.dateFormat = new SimpleDateFormat( datePattern, dateLocale );
        }
    			
    	/* Change formatter default timezone if requested. */
    	if ( timeZone != null ) dateFormat.setTimeZone( timeZone );
    			
    }

    
    /* ***************** */
    /*  EXTENSION HOOKS  */
    /* ***************** */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Date performConversion( final String source ) throws Exception
    {
        
        return dateFormat.parse( source );
        
    }

}