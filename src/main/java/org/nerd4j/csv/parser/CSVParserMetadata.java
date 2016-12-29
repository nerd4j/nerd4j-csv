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
package org.nerd4j.csv.parser;

import org.nerd4j.csv.RemarkableASCII;


/**
 * Contains the meta-data needed to properly build
 * a new {@link CSVParser}.  This configuration is
 * intended to be used with the {@link CSVParserFactory}.
 * 
 * <p>
 * Unicode characters outside ASCII space won't be considered
 * special at all and cannot have special functions.
 * 
 * @author Nerd4j Team
 */
public final class CSVParserMetadata
{
	
	/**
	 * Accept only strict quotes: quotes inside fields
	 * MUST be escaped.
	 * (by default this value is: {@code false}).
	 */
	private boolean strictQuotes;
	
    /** Character used to quote fields (the double quote ["] is used by default). */
    private Character quoteChar;
    
    /** Character used to escape other characters (by default this field is empty). */ 
    private Character escapeChar;
    
    /** Character used to separate fields (the comma [,] is used by default). */ 
    private Character fieldSeparator;
    
    /** Characters used to separate records (by default this set is: {[\r],[\n]}). */
    private char[] recordSeparator;
    
    /**
     * Tells the strategy to use for match a record separator.
     * If {@code true} matches the exact character sequence
     * provided in the {@link CSVParserMetadata#recordSeparator}
     * field. By default it will match a record separator as
     * soon as it finds any record separator character (this is
     * the behavior implemented by Microsoft Excel and OpenOffice
     * Calc).
     */
    private boolean matchRecordSeparatorExactSequence;
    
    /**
     * Characters to be completely ignored while parsing.
     * (by default this set is empty).
     */
    char[] charsToIgnore;
    
    /**
     * Characters to be ignored if found
     * on heading or trailing of a field.
     * (by default this set is: {[ ],[\t],[\n]}).
     */
    private char[] charsToIgnoreAroundFields;

    /**
     * Default constructor.
     * 
     */
    public CSVParserMetadata()
    {

        super();
    
        this.strictQuotes = false;
        
        this.fieldSeparator   = RemarkableASCII.COMMA;
        
        this.escapeChar = null;
        this.quoteChar  = RemarkableASCII.DOUBLE_QUOTE;
        
        this.charsToIgnore   = null;
        this.charsToIgnoreAroundFields = new char[] { RemarkableASCII.SPACE, RemarkableASCII.HT };
        
        this.matchRecordSeparatorExactSequence = false;
        this.recordSeparator = new char[] { RemarkableASCII.CR, RemarkableASCII.LF };
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the flag {@code strict-quotes}.
     * 
     * @return the flag {@code strict-quotes}.
     */
    public boolean isStrictQuotes()
    {
    	return strictQuotes;
    }
    
    /**
     * Sets the flag {@code strict-quotes}.
     * 
     * @param strictQuotes value to set.
     */
    public void setStrictQuotes( boolean strictQuotes )
    {
    	this.strictQuotes = strictQuotes;
    }
    
    /**
     * Returns the character used as field separator in the CSV.
     * 
     * @return the character used as field separator in the CSV.
     */
    public Character getFieldSeparator()
    {
        return fieldSeparator;
    }

    /**
     * Sets the character used as field separator in the CSV.
     * 
     * @param fieldSeparator value to set.
     */
	public void setFieldSeparator( Character fieldSeparator )
    {
        this.fieldSeparator = fieldSeparator;
    }

	/**
     * Returns the character used to escape control characters in the CSV.
     * 
     * @return the character used to escape control characters in the CSV.
     */
    public Character getEscapeChar()
    {
        return escapeChar;
    }
    
    /**
     * Sets the character used to escape control characters in the CSV.
     * 
     * @param escapeChar value to set.
     */
    public void setEscapeChar( Character escapeChar )
    {
        this.escapeChar = escapeChar;
    }
    
    /**
     * Returns the character used to quote fields in the CSV.
     * 
     * @return the character used to quote fields in the CSV.
     */
    public Character getQuoteChar()
    {
        return quoteChar;
    }
    
    /**
     * Sets the character used to quote fields in the CSV.
     * 
     * @param quoteChar value to set.
     */
    public void setQuoteChar( Character quoteChar )
    {
        this.quoteChar = quoteChar;
    }
    
    /**
     * Returns the characters to ignore during parsing.
     * 
     * @return the characters to ignore during parsing.
     */
    public char[] getCharsToIgnore()
    {
        if( charsToIgnore == null )
            return new char[0];
        else
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
        if( charsToIgnoreAroundFields == null )
            return new char[0];
        else
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
     * Returns the character sequence used as record separator in the CSV.
     * 
     * @return the character sequence used as record separator in the CSV.
     */
    public char[] getRecordSeparator()
    {
    	if( recordSeparator == null )
    		return new char[0];
    	else
    		return recordSeparator; 
    }
    
    /**
     * Sets the character sequence used as record separator in the CSV.
     * 
     * @param recordSeparator value to set.
     */
    public void setRecordSeparator( char[] recordSeparator )
    {
    	this.recordSeparator = recordSeparator; 
    }

    /**
     * Returns the flag {@code match-record-separator-exact-sequence}.
     * 
     * @return the flag {@code match-record-separator-exact-sequence}.
     */
	public boolean isMatchRecordSeparatorExactSequence()
	{
		return matchRecordSeparatorExactSequence;
	}

	/**
     * Sets the flag {@code match-record-separator-exact-sequence}.
     * 
     * @param matchRecordSeparatorExactSequence value to set.
     */
	public void setMatchRecordSeparatorExactSequence( boolean matchRecordSeparatorExactSequence )
	{
		this.matchRecordSeparatorExactSequence = matchRecordSeparatorExactSequence;
	}

}
