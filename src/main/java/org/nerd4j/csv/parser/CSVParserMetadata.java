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
import org.nerd4j.csv.parser.CSVParserFactory.CharacterClass;


/**
 * Contains the meta-data needed to properly build
 * a new {@link CSVParser}.  This configuration is
 * intended to be used with the {@link CSVParserFactory}.
 * 
 * <p>
 *  Unicode characters outside ASCII space won't be considered
 *  special at all and cannot have special functions.
 * </p>
 * 
 * @author Nerd4j Team
 */
public final class CSVParserMetadata
{
	
	/**
	 * Accept more lazy quotes: in unquoted fields are handled as
	 * {@link CharacterClass#NORMAL} without beeing escaped.
	 * (by default this value is: {@code false}).
	 */
	private boolean lazyQuotes;
	
    /** Character used to quote fields (the double quote ["] is used by default). */
    private Character quoteChar;
    
    /** Character used to escape other characters (by default this field is empty). */ 
    private Character escapeChar;
    
    /** Character used to separate fields (the comma [,] is used by default). */ 
    private Character fieldSeparator;
    
//    /**
//     * The first of at most two record separator characters.
//     * This character is mandatory, the second one is optional.
//     *(the [\n] character is used by default). 
//     */
//    private Character recordSeparator1;
//    
//    /**
//     * The second of at most two record separator characters.
//     * This character is optional, the first one is mandatory.
//     *(by default this field is empty). 
//     */
//    private Character recordSeparator2;
    
    /** Characters used to separate records (by default this set is: {[\r],[\n]}). */
    private char[] recordSeparator;
    
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
     * Characters to be completely ignored while parsing.
     * (by default this set is empty).
     */
    char[] charsToIgnore;
//    private Set<Character> charsToIgnore;
    
    /**
     * Characters to be ignored if found
     * on heading or trailing of a field.
     * (by default this set is: {[ ],[\t],[\n]}).
     */
    private char[] charsToIgnoreAroundFields;
//    private Set<Character> charsToIgnoreAroundFields;

    /**
     * Default constructor.
     * 
     */
    public CSVParserMetadata()
    {

        super();
    
        this.lazyQuotes = false;
        
        this.fieldSeparator   = RemarkableASCII.COMMA;
        
//        this.recordSeparator1 = RemarkableASCII.LF;
//        this.recordSeparator2 = null;
        
        this.escapeChar = null;
        this.quoteChar  = RemarkableASCII.DOUBLE_QUOTE;
        
        this.charsToIgnore   = null;
        this.charsToIgnoreAroundFields = new char[] { RemarkableASCII.SPACE, RemarkableASCII.HT };
//        this.charsToIgnoreAroundFields = new HashSet<Character>();
//        this.charsToIgnoreAroundFields.add( RemarkableASCII.SPACE );
//        this.charsToIgnoreAroundFields.add( RemarkableASCII.HT );
        
        this.matchRecordSeparatorExactSequence = false;
        this.recordSeparator = new char[] { RemarkableASCII.CR, RemarkableASCII.LF };
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    
    public boolean isLazyQuotes()
    {
    	return lazyQuotes;
    }
    
    public void setLazyQuotes( boolean lazyQuotes )
    {
    	this.lazyQuotes = lazyQuotes;
    }
    
    public Character getFieldSeparator()
    {
        return fieldSeparator;
    }

	public void setFieldSeparator( Character fieldSeparator )
    {
        this.fieldSeparator = fieldSeparator;
    }

//    public Character getRecordSeparator1()
//    {
//        return recordSeparator1;
//    }
//    
//    public void setRecordSeparator1( Character recordSeparator1 )
//    {
//        this.recordSeparator1 = recordSeparator1;
//    }
//    
//    public Character getRecordSeparator2()
//    {
//        return recordSeparator2;
//    }
//    
//    public void setRecordSeparator2( Character recordSeparator2 )
//    {
//        this.recordSeparator2 = recordSeparator2;
//    }
    
    public Character getEscapeChar()
    {
        return escapeChar;
    }
    
    public void setEscapeChar( Character escapeChar )
    {
        this.escapeChar = escapeChar;
    }
    
    public Character getQuoteChar()
    {
        return quoteChar;
    }
    
    public void setQuoteChar( Character quoteChar )
    {
        this.quoteChar = quoteChar;
    }
    
    public char[] getCharsToIgnore()
    {
        if( charsToIgnore == null )
            return new char[0];
        else
            return charsToIgnore;
    }
    
    public void setCharsToIgnore( char[] charsToIgnore )
    {
        this.charsToIgnore = charsToIgnore;
    }
    
    public char[] getCharsToIgnoreAroundFields()
    {
        if( charsToIgnoreAroundFields == null )
            return new char[0];
        else
            return charsToIgnoreAroundFields; 
    }
    
    public void setCharsToIgnoreAroundFields( char[] charsToIgnoreAroundFields )
    {
        this.charsToIgnoreAroundFields = charsToIgnoreAroundFields; 
    }

    public char[] getRecordSeparator()
    {
    	if( recordSeparator == null )
    		return new char[0];
    	else
    		return recordSeparator; 
    }
    
    public void setRecordSeparator( char[] recordSeparator )
    {
    	this.recordSeparator = recordSeparator; 
    }

	public boolean isMatchRecordSeparatorExactSequence()
	{
		return matchRecordSeparatorExactSequence;
	}

	public void setMatchRecordSeparatorExactSequence( boolean matchRecordSeparatorExactSequence )
	{
		this.matchRecordSeparatorExactSequence = matchRecordSeparatorExactSequence;
	}
    
//    public Set<Character> getCharsToIgnore()
//    {
//    	if( charsToIgnore == null )
//    		return Collections.emptySet();
//    	else
//    		return charsToIgnore;
//    }
//    
//    public void setCharsToIgnore( Set<Character> charsToIgnore )
//    {
//    	this.charsToIgnore = charsToIgnore;
//    }
//    
//    public Set<Character> getCharsToIgnoreAroundFields()
//    {
//    	if( charsToIgnoreAroundFields == null )
//    		return Collections.emptySet();
//    	else
//    		return charsToIgnoreAroundFields; 
//    }
//    
//    public void setCharsToIgnoreAroundFields( Set<Character> charsToIgnoreAroundFields )
//    {
//    	this.charsToIgnoreAroundFields = charsToIgnoreAroundFields; 
//    }
//    
//    public Set<Character> getRecordSeparators()
//    {
//    	if( recordSeparators == null )
//    		return Collections.emptySet();
//    	else
//    		return recordSeparators; 
//    }
//    
//    public void setRecordSeparators( Set<Character> recordSeparators )
//    {
//    	this.recordSeparators = recordSeparators; 
//    }

}
