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
     * Default constructor.
     * 
     */
    public CSVCharSetConf()
    {

        super();
    
        this.name = null;
        this.fieldSeparator   = null;
        
        this.recordSeparator1 = null;
        this.recordSeparator2 = null;
        
        this.escapeChar = null;
        this.quoteChar  = null;
        
    }
    
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    public String getName()
    {
        return name;
    }
    
    public void setName( String name )
    {
    	this.name = name;
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