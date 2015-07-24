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

import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;



/**
 * Backing bean that represents the XML configuration of the CSVParser.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="parser")
public class XMLParserConf extends XMLCharSetConf
{
    
    private static final String CHARS_TO_IGNORE = "chars-to-ignore";
    private static final String CHARS_TO_IGNORE_AROUND_FIELDS = "chars-to-ignore-around-fields";
    private static final String LAZY_QUOTES = "lazy-quotes";
    
    
    /** String representation of the charsToIgnore field. */
    @XmlAttribute(name=CHARS_TO_IGNORE,required=false)
    private String charsToIgnoreString;

    /** String representation of the charsToIgnoreAroundFields field. */
    @XmlAttribute(name=CHARS_TO_IGNORE_AROUND_FIELDS,required=false)
    private String charsToIgnoreAroundFieldsString;
    
    /**
     * Set of characters to be completely ignored while parsing.
     * (by default this set is empty).
     */
    private Set<Character> charsToIgnore;
    
    /**
     * Set of characters to be ignored if found
     * on heading or trailing of a field.
     * (by default this set is: {[ ], [\t],[\n]}).
     */
    private Set<Character> charsToIgnoreAroundFields;
    
    /**
     * Tells if to treat CSV quotes less strictly.
     * More precisely tells the parser not to fail
     * in case a not escaped quote is found
     * into a field.
     */
    private Boolean lazyQuotes;
    
    
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
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    @XmlTransient
    public Set<Character> getCharsToIgnore()
    {

        if( charsToIgnore == null )
            charsToIgnore = parseCharSet( charsToIgnoreString, CHARS_TO_IGNORE );
        
        return charsToIgnore;
        
    }
    
    public void setCharsToIgnore( Set<Character> charsToIgnore )
    {
        this.charsToIgnore = charsToIgnore;
        this.charsToIgnoreString = formatCharSet( charsToIgnore );
    }
    
    @XmlTransient
    public Set<Character> getCharsToIgnoreAroundFields()
    {
        
        if( charsToIgnoreAroundFields == null )
            charsToIgnoreAroundFields = parseCharSet( charsToIgnoreAroundFieldsString, CHARS_TO_IGNORE_AROUND_FIELDS );
        
        return charsToIgnoreAroundFields;
        
    }
    
    public void setCharsToIgnoreAroundFields( Set<Character> charsToIgnoreAroundFields )
    {
        this.charsToIgnoreAroundFields = charsToIgnoreAroundFields;
        this.charsToIgnoreAroundFieldsString = formatCharSet( charsToIgnoreAroundFields );
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