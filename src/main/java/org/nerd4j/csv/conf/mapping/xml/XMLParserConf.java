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

import org.nerd4j.csv.parser.CSVParserMetadata;



/**
 * Backing bean that represents the XML configuration of the CSVParser.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="parser")
public class XMLParserConf extends XMLCharSetConf
{
    
	private static final String LAZY_QUOTES = "lazy-quotes";
    private static final String CHARS_TO_IGNORE = "chars-to-ignore";
    private static final String CHARS_TO_IGNORE_AROUND_FIELDS = "chars-to-ignore-around-fields";
    private static final String MATCH_RECORD_SEP_EXACT_SEQUENCE = "record-sep-match-exact-sequence";
    
    
    /** String representation of the charsToIgnore field. */
    @XmlAttribute(name=CHARS_TO_IGNORE,required=false)
    private String charsToIgnoreString;

    /** String representation of the charsToIgnoreAroundFields field. */
    @XmlAttribute(name=CHARS_TO_IGNORE_AROUND_FIELDS,required=false)
    private String charsToIgnoreAroundFieldsString;
    
    /**
     * Characters to be completely ignored while parsing.
     * (by default this set is empty).
     */
    private char[] charsToIgnore;
    
    /**
     * Characters to be ignored if found
     * on heading or trailing of a field.
     * (by default this set is: {[ ], [\t],[\n]}).
     */
    private char[] charsToIgnoreAroundFields;
    
    /**
     * Tells if to treat CSV quotes less strictly.
     * More precisely tells the parser not to fail
     * in case a not escaped quote is found
     * into a field.
     */
    private Boolean lazyQuotes;
    
    /**
     * Tells the strategy to use for match a record separator.
     * If {@code true} matches the exact character sequence
     * provided in the {@link CSVParserMetadata#recordSeparator}
     * field. By default it will match a record separator as
     * soon as it finds any record separator character (this is
     * the behaviour implemented by Mucrosoft Excel and OpenOffice
     * Calc).
     */
    private boolean matchRecordSeparatorExactSequence;
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLParserConf()
    {

        super();
        
        this.lazyQuotes = null;
        
        this.charsToIgnoreString = null;
        this.charsToIgnoreAroundFieldsString = null;
        
        this.charsToIgnore = null;
        this.charsToIgnoreAroundFields = null;
        
        this.matchRecordSeparatorExactSequence = false;
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    @XmlTransient
    public char[] getCharsToIgnore()
    {

        if( charsToIgnore == null )
            charsToIgnore = parseCharSet( charsToIgnoreString );
        
        return charsToIgnore;
        
    }
    
    public void setCharsToIgnore( char[] charsToIgnore )
    {
        this.charsToIgnore = charsToIgnore;
        this.charsToIgnoreString = formatCharSet( charsToIgnore );
    }
    
    @XmlTransient
    public  char[] getCharsToIgnoreAroundFields()
    {
        
        if( charsToIgnoreAroundFields == null )
            charsToIgnoreAroundFields = parseCharSet( charsToIgnoreAroundFieldsString );
        
        return charsToIgnoreAroundFields;
        
    }
    
    public void setCharsToIgnoreAroundFields(  char[] charsToIgnoreAroundFields )
    {
        this.charsToIgnoreAroundFields = charsToIgnoreAroundFields;
        this.charsToIgnoreAroundFieldsString = formatCharSet( charsToIgnoreAroundFields );
    }
    
    @XmlAttribute(name=MATCH_RECORD_SEP_EXACT_SEQUENCE,required=false)
	public Boolean isMatchRecordSeparatorExactSequence()
	{
		return matchRecordSeparatorExactSequence;
	}
    
	public void setMatchRecordSeparatorExactSequence( Boolean matchRecordSeparatorExactSequence )
	{
		this.matchRecordSeparatorExactSequence = matchRecordSeparatorExactSequence;
	}
    
	@XmlAttribute(name=LAZY_QUOTES,required=false)
	public Boolean isLazyQuotes()
	{
		return lazyQuotes;
	}
	
	public void setLazyQuotes( Boolean lazyQuotes )
	{
		this.lazyQuotes = lazyQuotes;
	}
	
}