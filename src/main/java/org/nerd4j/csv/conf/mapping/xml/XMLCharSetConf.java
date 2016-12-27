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
package org.nerd4j.csv.conf.mapping.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.nerd4j.csv.exception.CSVConfigurationException;



/**
 * Abstract common representation of an XML configuration
 * for objects that configures characters.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="char-set")
public class XMLCharSetConf
{
    
    private static final String QUOTE = "quote";
    private static final String ESCAPE = "escape";
    private static final String FIELD_SEPARATOR = "field-sep";
    private static final String RECORD_SEPARATOR = "record-sep";
    

    /** Name used to identify the parser. */
    @XmlAttribute(name="name",required=false)
    private String name;

    /** Character used to quote fields (the double quote ["] is used by default). */
    @XmlAttribute(name=QUOTE,required=true)
    private String quoteCharString;
    
    /** Character used to escape other characters (by default this field is empty). */
    @XmlAttribute(name=ESCAPE,required=false)
    private String escapeCharString;
    
    /** Character used to separate fields (the comma [,] is used by default). */
    @XmlAttribute(name=FIELD_SEPARATOR,required=true)
    private String fieldSeparatorString;
    
    /**
     * Characters used to separate records.
     * By default the operating system line
     * separator will be used.
     */
    @XmlAttribute(name=RECORD_SEPARATOR,required=false)
    private String recordSeparatorString;
    

    /**
     * Default constructor.
     * 
     */
    public XMLCharSetConf()
    {

        super();
    
        this.name = null;
        this.fieldSeparatorString   = null;
        this.recordSeparatorString = null;
        
        this.escapeCharString = null;
        this.quoteCharString  = null;
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    /**
     * Parses the configured string and returns the represented
     * character if any. If the string doesn't represent a single
     * char an exception will be thrown.
     * 
     * @param value the value to parse.
     * @param field the related field.
     * @return the represented character, or {@code null}.
     * @throws CSVConfigurationException if the given value is not a character.
     */
    private Character parseChar( String value, String field )
    {
        
        if( value == null || value.isEmpty() )
            return null;
        
        if( value.length() != 1 )
            throw new CSVConfigurationException( "The configured value '" + value + "' for the field '" + field + "' is not a character" );
        
        return value.charAt( 0 );
        
    }
    
    /**
     * Parses the configured string and returns the represented
     * character set. If the string doesn't represent a space
     * separated list of characters an exception will be thrown.
     * 
     * @param value the value to parse.
     * @return the represented character set.
     * @throws CSVConfigurationException if the given value is not a character.
     */
    protected char[] parseCharSet( String value )
    {
        
       return value != null ? value.toCharArray() : null;
        
    }

    /**
     * Formats the given character set into a string
     * representing a space separated list.
     * 
     * @param value the value to format.
     * @return the string representation of the character set.
     */
    protected String formatCharSet(  char[] value )
    {
        
        return value != null && value.length > 0 ? new String(value) : "";
        
    }
    
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the registry key.
     * 
     * @return the registry key.
     */
    @XmlTransient
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the character used as field separator in the CSV.
     * 
     * @return the character used as field separator in the CSV.
     */
    @XmlTransient
    public Character getFieldSeparator()
    {
        return parseChar( fieldSeparatorString, FIELD_SEPARATOR );
    }
    
    /**
     * Sets the character used as field separator in the CSV.
     * 
     * @param fieldSeparator value to set.
     */
    public void setFieldSeparator( Character fieldSeparator )
    {
        this.fieldSeparatorString = String.valueOf( fieldSeparator );
    }
    
    /**
     * Returns the character sequence used as record separator in the CSV.
     * 
     * @return the character sequence used as record separator in the CSV.
     */
    @XmlTransient
    public char[] getRecordSeparator()
    {
        return parseCharSet( recordSeparatorString );
    }
    
    /**
     * Sets the character sequence used as record separator in the CSV.
     * 
     * @param recordSeparator value to set.
     */
    public void setRecordSeparator( char[] recordSeparator )
    {
        this.recordSeparatorString = formatCharSet( recordSeparator );
    }

    /**
     * Returns the character used to escape control characters in the CSV.
     * 
     * @return the character used to escape control characters in the CSV.
     */
    @XmlTransient
    public Character getEscapeChar()
    {
        return parseChar( escapeCharString, ESCAPE );
    }
    
    /**
     * Sets the character used to escape control characters in the CSV.
     * 
     * @param escapeChar value to set.
     */
    public void setEscapeChar( Character escapeChar )
    {
        this.escapeCharString = String.valueOf( escapeChar );
    }
    
    /**
     * Returns the character used to quote fields in the CSV.
     * 
     * @return the character used to quote fields in the CSV.
     */
    @XmlTransient
    public Character getQuoteChar()
    {
        return parseChar( quoteCharString, QUOTE );
    }
    
    /**
     * Sets the character used to quote fields in the CSV.
     * 
     * @param quoteChar value to set.
     */
    public void setQuoteChar( Character quoteChar )
    {
        this.quoteCharString = String.valueOf( quoteChar );
    }
   
}