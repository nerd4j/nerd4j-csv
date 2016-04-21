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
package org.nerd4j.csv.conf.mapping;

import org.nerd4j.csv.parser.CSVParser;
import org.nerd4j.csv.parser.CSVParserMetadata;


/**
 * Represents the configuration of the {@link CSVParser}.
 * 
 * @author Nerd4j Team
 */
public class CSVParserConf extends CSVCharSetConf implements Cloneable
{
    
    /**
     * Set of characters to be completely ignored while parsing.
     * (by default this set is empty).
     */
    private char[] charsToIgnore;
    
    /**
     * Set of characters to be ignored if found
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
    private Boolean matchRecordSeparatorExactSequence;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVParserConf()
    {

        super();
        
        this.charsToIgnore                     = null;
        this.charsToIgnoreAroundFields         = null;
        this.lazyQuotes                        = null;
        this.matchRecordSeparatorExactSequence = null;
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    public char[] getCharsToIgnore()
    {
        return charsToIgnore;
    }
    
    public void setCharsToIgnore( char[] charsToIgnore )
    {
        this.charsToIgnore = charsToIgnore;
    }
    
    public char[] getCharsToIgnoreAroundFields()
    {
        return charsToIgnoreAroundFields;
    }

    public void setCharsToIgnoreAroundFields( char[] charsToIgnoreAroundFields )
    {
        this.charsToIgnoreAroundFields = charsToIgnoreAroundFields;
    }
    
    public Boolean isLazyQuotes()
    {
    	return lazyQuotes;
    }
    
    public void setLazyQuotes( Boolean lazyQuotes )
    {
    	this.lazyQuotes = lazyQuotes;
    }

    public Boolean isMatchRecordSeparatorExactSequence()
    {
    	return matchRecordSeparatorExactSequence;
    }
    
    public void setMatchRecordSeparatorExactSequence( Boolean matchRecordSeparatorExactSequence )
    {
    	this.matchRecordSeparatorExactSequence = matchRecordSeparatorExactSequence;
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */


	/**
     * {@inheritDoc}
     */
    @Override
    public CSVParserConf clone() throws CloneNotSupportedException
    {
        
        final CSVParserConf clone = (CSVParserConf) super.clone();
        
        if( this.charsToIgnore != null )
            clone.charsToIgnore = this.charsToIgnore.clone();
        
        if( this.charsToIgnoreAroundFields != null )
            clone.charsToIgnoreAroundFields = this.charsToIgnoreAroundFields.clone();
        
        return clone;
        
    }
    
}