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
 * Common representation of a character set configuration.
 * 
 * @author Nerd4j Team
 */

public class CSVCharSetConf implements Cloneable
{
    
    /** Name used to identify the char set. */
    private String name;

    /** Character used to quote fields (the double quote ["] is used by default). */
    private Character quoteChar;
    
    /** Character used to escape other characters (by default this field is empty). */
    private Character escapeChar;
    
    /** Character used to separate fields (the comma [,] is used by default). */
    private Character fieldSeparator;
    
    /** Characters used to separate records. */ 
    private char[] recordSeparator;
    

    /**
     * Default constructor.
     * 
     */
    public CSVCharSetConf()
    {

        super();
    
        this.name = null;
        this.fieldSeparator   = null;
        this.recordSeparator  = null;
        
        this.escapeChar = null;
        this.quoteChar  = null;
        
    }
    
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the registry key.
     * 
     * @return the registry key.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Sets the registry key.
     * 
     * @param name value to set.
     */
    public void setName( String name )
    {
    	this.name = name;
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
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVCharSetConf clone() throws CloneNotSupportedException
    {
        return (CSVCharSetConf) super.clone();
    }
   
}