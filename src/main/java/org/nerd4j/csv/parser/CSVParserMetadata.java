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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    
    /**
     * The first of at most two record separator characters.
     * This character is mandatory, the second one is optional.
     *(the [\n] character is used by default). 
     */
    private Character recordSeparator1;
    
    /**
     * The second of at most two record separator characters.
     * This character is optional, the first one is mandatory.
     *(by default this field is empty). 
     */
    private Character recordSeparator2;
    
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
     * Default constructor.
     * 
     */
    public CSVParserMetadata()
    {

        super();
    
        this.lazyQuotes = false;
        
        this.fieldSeparator   = RemarkableASCII.COMMA;
        
        this.recordSeparator1 = RemarkableASCII.LF;
        this.recordSeparator2 = null;
        
        this.escapeChar = null;
        this.quoteChar  = RemarkableASCII.DOUBLE_QUOTE;
        
        this.charsToIgnore   = null;
        this.charsToIgnoreAroundFields = new HashSet<Character>();
        this.charsToIgnoreAroundFields.add( RemarkableASCII.SPACE );
        this.charsToIgnoreAroundFields.add( RemarkableASCII.HT );
        this.charsToIgnoreAroundFields.add( RemarkableASCII.LF );
        
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

    public Character getRecordSeparator1()
    {
        return recordSeparator1;
    }
    
    public void setRecordSeparator1( Character recordSeparator1 )
    {
        this.recordSeparator1 = recordSeparator1;
    }
    
    public Character getRecordSeparator2()
    {
        return recordSeparator2;
    }
    
    public void setRecordSeparator2( Character recordSeparator2 )
    {
        this.recordSeparator2 = recordSeparator2;
    }
    
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
    
    public Set<Character> getCharsToIgnore()
    {
        if( charsToIgnore == null )
            return Collections.emptySet();
        else
            return charsToIgnore;
    }
    
    public void setCharsToIgnore( Set<Character> charsToIgnore )
    {
        this.charsToIgnore = charsToIgnore;
    }
    
    public Set<Character> getCharsToIgnoreAroundFields()
    {
        if( charsToIgnoreAroundFields == null )
            return Collections.emptySet();
        else
            return charsToIgnoreAroundFields; 
    }
    
    public void setCharsToIgnoreAroundFields( Set<Character> charsToIgnoreAroundFields )
    {
        this.charsToIgnoreAroundFields = charsToIgnoreAroundFields; 
    }

}
