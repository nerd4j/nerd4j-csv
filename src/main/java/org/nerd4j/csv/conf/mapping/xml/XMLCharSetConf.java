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

import java.util.HashSet;
import java.util.Set;

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
    private static final String RECORD_SEPARATOR_1 = "record-sep-1";
    private static final String RECORD_SEPARATOR_2 = "record-sep-2";
    

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
     * The first of at most two record separator characters.
     * This character is mandatory, the second one is optional.
     *(the [\n] character is used by default). 
     */
    @XmlAttribute(name=RECORD_SEPARATOR_1,required=true)
    private String recordSeparator1String;
    
    /**
     * The second of at most two record separator characters.
     * This character is optional, the first one is mandatory.
     *(by default this field is empty). 
     */
    @XmlAttribute(name=RECORD_SEPARATOR_2,required=false)
    private String recordSeparator2String;
    

    /**
     * Default constructor.
     * 
     */
    public XMLCharSetConf()
    {

        super();
    
        this.name = null;
        this.fieldSeparatorString   = null;
        
        this.recordSeparator1String = null;
        this.recordSeparator2String = null;
        
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
     * @return the represented character, or <code>null</code>.
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
     * @param field the related field.
     * @return the represented character set.
     * @throws CSVConfigurationException if the given value is not a character.
     */
    protected Set<Character> parseCharSet( String value, String field )
    {
        
        if( value == null ) return null;
        
        final Set<Character> charSet = new HashSet<Character>();
        if( ! value.isEmpty() )
        {
            for( int i = 0; i < value.length(); ++i )
                charSet.add( value.charAt(i) );
        }
        
        return charSet;
        
    }

    /**
     * Formats the given character set into a string
     * representing a space separated list.
     * 
     * @param value the value to format.
     * @return the string representation of the character set.
     */
    protected String formatCharSet( Set<Character> value )
    {
        
        if( value == null || value.isEmpty() )
            return "";
        
        final StringBuilder sb = new StringBuilder(value.size());
        for( Character c : value )
            sb.append( c );
        
        return sb.toString();
        
    }
    
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    @XmlTransient
    public String getName()
    {
        return name;
    }
    
    @XmlTransient
    public Character getFieldSeparator()
    {
        return parseChar( fieldSeparatorString, FIELD_SEPARATOR );
    }
    
    public void setFieldSeparator( Character fieldSeparator )
    {
        this.fieldSeparatorString = String.valueOf( fieldSeparator );
    }
    
    @XmlTransient
    public Character getRecordSeparator1()
    {
        return parseChar( recordSeparator1String, RECORD_SEPARATOR_1 );
    }
    
    public void setRecordSeparator1( Character recordSeparator1 )
    {
        this.recordSeparator1String = String.valueOf( recordSeparator1 );
    }
    
    @XmlTransient
    public Character getRecordSeparator2()
    {
        return parseChar( recordSeparator2String, RECORD_SEPARATOR_2 );
    }
    
    public void setRecordSeparator2( Character recordSeparator2 )
    {
        this.recordSeparator2String = String.valueOf( recordSeparator2 );
    }
    
    @XmlTransient
    public Character getEscapeChar()
    {
        return parseChar( escapeCharString, ESCAPE );
    }
    
    public void setEscapeChar( Character escapeChar )
    {
        this.escapeCharString = String.valueOf( escapeChar );
    }
    
    @XmlTransient
    public Character getQuoteChar()
    {
        return parseChar( quoteCharString, QUOTE );
    }
    
    public void setQuoteChar( Character quoteChar )
    {
        this.quoteCharString = String.valueOf( quoteChar );
    }
   
}