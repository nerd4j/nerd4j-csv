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

import org.nerd4j.csv.formatter.CSVFormatter;


/**
 * Represents the configuration of the {@link CSVFormatter}.
 * 
 * @author Nerd4j Team
 */
public class CSVFormatterConf extends CSVCharSetConf implements Cloneable
{
    
    /**
     * Set of characters to be escaped.
     * (by default this set is empty).
     */
    private char[] charsToEscape;
    
    /**
     * Set of characters that forces the whole field to be quoted.
     * (by default this set is: {[ ],[,],[\t],[\n]}).
     */
    private char[] charsThatForceQuoting;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVFormatterConf()
    {

        super();
    
        this.charsToEscape         = null;
        this.charsThatForceQuoting = null;
        
    }
    
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    public char[] getCharsToEscape()
    {
        return charsToEscape;
    }
    
    public void setCharsToEscape( char[] charsToEscape )
    {
        this.charsToEscape = charsToEscape;
    }
    
    public char[] getCharsThatForceQuoting()
    {
        return charsThatForceQuoting;
    }
    
    public void setCharsThatForceQuoting( char[] charsThatForceQuoting )
    {
        this.charsThatForceQuoting = charsThatForceQuoting;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVFormatterConf clone() throws CloneNotSupportedException
    {
        
        final CSVFormatterConf clone = (CSVFormatterConf) super.clone();
        
        if( this.charsToEscape != null )
            clone.charsToEscape = this.charsToEscape.clone();
        
        if( this.charsThatForceQuoting != null )
            clone.charsThatForceQuoting = this.charsThatForceQuoting.clone();
        
        return clone;
        
    }
    
}