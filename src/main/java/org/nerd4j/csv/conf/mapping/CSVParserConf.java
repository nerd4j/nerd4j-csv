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

import java.util.HashSet;
import java.util.Set;

import org.nerd4j.csv.parser.CSVParser;


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
    private Set<Character> charsToIgnore;
    
    /**
     * Set of characters to be ignored if found
     * on heading or trailing of a field.
     * (by default this set is: {[ ], [\t],[\n]}).
     */
    private Set<Character> charsToIgnoreAroundFields;
    
    /**
     * Tells if to treat CSV quotes less strictly.
     * More precisely tells the parser not to fail
     * in case a not escaped quote is found
     * into a field.
     */
    private Boolean lazyQuotes;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVParserConf()
    {

        super();
        
        this.charsToIgnore             = null;
        this.charsToIgnoreAroundFields = null;
        this.lazyQuotes                = null;
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    public Set<Character> getCharsToIgnore()
    {
        return charsToIgnore;
    }
    
    public void setCharsToIgnore( Set<Character> charsToIgnore )
    {
        this.charsToIgnore = charsToIgnore;
    }
    
    public Set<Character> getCharsToIgnoreAroundFields()
    {
        return charsToIgnoreAroundFields;
    }

    public void setCharsToIgnoreAroundFields( Set<Character> charsToIgnoreAroundFields )
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
            clone.charsToIgnore = new HashSet<Character>( this.charsToIgnore );
        
        if( this.charsToIgnoreAroundFields != null )
            clone.charsToIgnoreAroundFields = new HashSet<Character>( this.charsToIgnoreAroundFields );
        
        return clone;
        
    }
    
}