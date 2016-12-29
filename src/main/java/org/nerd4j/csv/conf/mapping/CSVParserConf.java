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

/**
 * Represents the configuration of the
 * {@link org.nerd4j.csv.parser.CSVParser CSVParser}.
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
     * Tells to treat CSV quotes strictly.
     * More precisely tells the parser to
     * fail in case a not escaped quote is
     * found into a field.
     */
    private Boolean strictQuotes;
    
    /**
     * Tells the strategy to use for match a record separator.
     * If {@code true} matches the exact character sequence
     * provided in the {@link CSVParserMetadata#recordSeparator}
     * field. By default it will match a record separator as
     * soon as it finds any record separator character (this is
     * the behavior implemented by Microsoft Excel and OpenOffice
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
        
        this.strictQuotes                      = null;
        this.charsToIgnore                     = null;
        this.charsToIgnoreAroundFields         = null;
        this.matchRecordSeparatorExactSequence = null;
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the characters to ignore during parsing.
     * 
     * @return the characters to ignore during parsing.
     */
    public char[] getCharsToIgnore()
    {
        return charsToIgnore;
    }
    
    /**
     * Sets the characters to ignore during parsing.
     * 
     * @param charsToIgnore value to set.
     */
    public void setCharsToIgnore( char[] charsToIgnore )
    {
        this.charsToIgnore = charsToIgnore;
    }
    
    /**
     * Returns the characters to ignore if not inside a field.
     * 
     * @return the characters to ignore if not inside a field.
     */
    public char[] getCharsToIgnoreAroundFields()
    {
        return charsToIgnoreAroundFields;
    }

    /**
     * Sets the characters to ignore if not inside a field.
     * 
     * @param charsToIgnoreAroundFields value to set.
     */
    public void setCharsToIgnoreAroundFields( char[] charsToIgnoreAroundFields )
    {
        this.charsToIgnoreAroundFields = charsToIgnoreAroundFields;
    }
    
    /**
     * Returns the flag {@code strict-quotes}.
     * 
     * @return the flag {@code strict-quotes}.
     */
    public Boolean isStrictQuotes()
    {
    	return strictQuotes;
    }
    
    /**
     * Sets the flag {@code strict-quotes}.
     * 
     * @param strictQuotes value to set.
     */
    public void setStrictQuotes( Boolean strictQuotes )
    {
    	this.strictQuotes = strictQuotes;
    }

    /**
     * Returns the flag {@code match-record-separator-exact-sequence}.
     * 
     * @return the flag {@code match-record-separator-exact-sequence}.
     */
    public Boolean isMatchRecordSeparatorExactSequence()
    {
    	return matchRecordSeparatorExactSequence;
    }
    
    /**
     * Sets the flag {@code match-record-separator-exact-sequence}.
     * 
     * @param matchRecordSeparatorExactSequence value to set.
     */
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