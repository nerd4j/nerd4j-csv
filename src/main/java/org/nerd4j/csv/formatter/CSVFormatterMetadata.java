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
package org.nerd4j.csv.formatter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.nerd4j.csv.RemarkableASCII;


/**
 * Contains the meta-data needed to properly build
 * a new {@link CSVFormatter}.  This configuration is
 * intended to be used with the {@link CSVFormatterFactory}.
 * 
 * @author Nerd4j Team
 */
public final class CSVFormatterMetadata
{

    /** Character used to quote fields (the double quote ["] is used by default). */
    private char quoteChar;
    
    /** Character used to escape other characters (by default this field is empty). */ 
    private char escapeChar;
    
    /** Character used to separate fields (the comma [,] is used by default). */ 
    private char fieldSeparator;
    
    /**
     * The first of at most two record separator characters.
     * This character is mandatory, the second one is optional.
     *(the [\n] character is used by default). 
     */
    private char recordSeparator1;
    
    /**
     * The second of at most two record separator characters.
     * This character is optional, the first one is mandatory
     *(by default this field is empty). 
     */
    private char recordSeparator2;
    
    /**
     * Set of characters (in the ASCII space) that has to be escaped
     * using the {@link #escapeChar} (by default this set is empty).
     */
    private Set<Character> charsToEscape;
    
    /**
     * Set of characters (in the ASCII space) that forces the whole
     * field to be enclosed by {@link #quoteChar}s
     * (by default this set is: {[,],[ ],[\t],[\n]}).
     */
    private Set<Character> charsThatForceQuoting;

    
    /**
     * Default constructor.
     * 
     */
	public CSVFormatterMetadata()
    {

        super();
    
        this.fieldSeparator   = RemarkableASCII.COMMA;
        
        this.recordSeparator1 = RemarkableASCII.LF;
        this.recordSeparator2 = RemarkableASCII.NOT_AN_ASCII;
        
        this.escapeChar = RemarkableASCII.NOT_AN_ASCII;
        this.quoteChar  = RemarkableASCII.DOUBLE_QUOTE;
        
        this.charsToEscape   = null;
        this.charsThatForceQuoting = new HashSet<Character>();
        this.charsThatForceQuoting.add( RemarkableASCII.COMMA );
        this.charsThatForceQuoting.add( RemarkableASCII.SPACE );
        this.charsThatForceQuoting.add( RemarkableASCII.HT );
        this.charsThatForceQuoting.add( RemarkableASCII.LF );
        
    }

	
	/* ******************* */
	/*  GETTERS & SETTERS  */
	/* ******************* */
	
	
    public char getFieldSeparator()
    {
        return fieldSeparator;
    }
    
    public void setFieldSeparator( char fieldSeparator )
    {
        this.fieldSeparator = fieldSeparator;
    }

    public char getRecordSeparator1()
    {
        return recordSeparator1;
    }
    
    public void setRecordSeparator1( char recordSeparator1 )
    {
        this.recordSeparator1 = recordSeparator1;
    }
    
    public char getRecordSeparator2()
    {
        return recordSeparator2;
    }
    
    public void setRecordSeparator2( char recordSeparator2 )
    {
        this.recordSeparator2 = recordSeparator2;
    }
    
    public char getEscapeChar()
    {
        return escapeChar;
    }
    
    public void setEscapeChar( char escapeChar )
    {
        this.escapeChar = escapeChar;
    }
    
    public char getQuoteChar()
    {
        return quoteChar;
    }
    
    public void setQuoteChar( char quoteChar )
    {
        this.quoteChar = quoteChar;
    }
    
    public Set<Character> getCharsThatForceQuoting()
    {
        if( charsThatForceQuoting == null )
            return Collections.emptySet();
        else
            return charsThatForceQuoting;
    }
    
    public void setCharsThatForceQuoting( Set<Character> charsThatForceQuoting )
    {
        this.charsThatForceQuoting = charsThatForceQuoting;
    }
    
    public Set<Character> getCharsToEscape()
    {
        if( charsToEscape == null )
            return Collections.emptySet();
        else
            return charsToEscape; 
    }
    
    public void setCharsToEscape( Set<Character> charsToEscape )
    {
        this.charsToEscape = charsToEscape; 
    }

}