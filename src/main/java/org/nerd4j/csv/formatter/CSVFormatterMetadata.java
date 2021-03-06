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
     * Characters used to separate records.
     * By default the operating system line
     * separator will be used.
     */
    private char[] recordSeparator;
    
    /**
     * Characters (in the ASCII space) that has to be escaped
     * using the {@link #escapeChar} (by default this set is empty).
     */
    private char[] charsToEscape;
    
    /**
     * Characters (in the ASCII space) that forces the whole
     * field to be enclosed by {@link #quoteChar}s
     * (by default this set is: {[ ],[\t],[\n],[\r]}).
     */
    private char[] charsThatForceQuoting;

    
    /**
     * Default constructor.
     * 
     */
	public CSVFormatterMetadata()
    {

        super();
    
        this.fieldSeparator   = RemarkableASCII.COMMA;
        this.recordSeparator = System.getProperty( "line.separator" ).toCharArray();
        
        this.escapeChar = RemarkableASCII.NOT_AN_ASCII;
        this.quoteChar  = RemarkableASCII.DOUBLE_QUOTE;
        
        this.charsToEscape   = null;
        this.charsThatForceQuoting = new char[] { RemarkableASCII.SPACE, RemarkableASCII.HT, RemarkableASCII.LF, RemarkableASCII.CR };
        
    }

	
	/* ******************* */
	/*  GETTERS & SETTERS  */
	/* ******************* */
	
	
	/**
     * Returns the character used as field separator in the CSV.
     * 
     * @return the character used as field separator in the CSV.
     */
    public char getFieldSeparator()
    {
        return fieldSeparator;
    }
    
    /**
     * Sets the character used as field separator in the CSV.
     * 
     * @param fieldSeparator value to set.
     */
    public void setFieldSeparator( char fieldSeparator )
    {
        this.fieldSeparator = fieldSeparator;
    }

    /**
     * Returns the character sequence used as record separator in the CSV.
     * 
     * @return the character sequence used as record separator in the CSV.
     */
    public char[] getRecordSeparator()
    {
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
     * Returns the character used to escape control characters in the CSV.
     * 
     * @return the character used to escape control characters in the CSV.
     */
    public char getEscapeChar()
    {
        return escapeChar;
    }
    
    /**
     * Sets the character used to escape control characters in the CSV.
     * 
     * @param escapeChar value to set.
     */
    public void setEscapeChar( char escapeChar )
    {
        this.escapeChar = escapeChar;
    }
    
    /**
     * Returns the character used to quote fields in the CSV.
     * 
     * @return the character used to quote fields in the CSV.
     */
    public char getQuoteChar()
    {
        return quoteChar;
    }
    
    /**
     * Sets the character used to quote fields in the CSV.
     * 
     * @param quoteChar value to set.
     */
    public void setQuoteChar( char quoteChar )
    {
        this.quoteChar = quoteChar;
    }
    
    /**
     * Returns the characters that force the field to be quoted.
     * 
     * @return the characters that force the field to be quoted.
     */
    public char[] getCharsThatForceQuoting()
    {
        if( charsThatForceQuoting == null )
            return new char[0];
        else
            return charsThatForceQuoting;
    }
    
    /**
     * Sets the characters that force the field to be quoted.
     * 
     * @param charsThatForceQuoting value to set.
     */
    public void setCharsThatForceQuoting( char[] charsThatForceQuoting )
    {
        this.charsThatForceQuoting = charsThatForceQuoting;
    }
    
    /**
     * Returns the characters to escape during formatting.
     * 
     * @return the characters to escape during formatting.
     */
    public char[] getCharsToEscape()
    {
        if( charsToEscape == null )
            return new char[0];
        else
            return charsToEscape; 
    }
    
    /**
     * Sets  the characters to escape during formatting.
     * 
     * @param charsToEscape value to set.
     */
    public void setCharsToEscape( char[] charsToEscape )
    {
        this.charsToEscape = charsToEscape; 
    }

}