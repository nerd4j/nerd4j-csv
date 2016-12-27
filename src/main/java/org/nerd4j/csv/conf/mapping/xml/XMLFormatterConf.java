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
package org.nerd4j.csv.conf.mapping.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;



/**
 * Backing bean that represents the XML configuration of the CSVParser.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="formatter")
public class XMLFormatterConf extends XMLCharSetConf
{
    
    private static final String CHARS_TO_ESCAPE = "chars-to-escape";
    private static final String CHARS_THAT_FORCE_QUOTING = "chars-that-force-quoting";
    
    
    /** String representation of the charsToIgnore field. */
    @XmlAttribute(name=CHARS_TO_ESCAPE,required=false)
    private String charsToEscapeString;

    /** String representation of the charsToIgnoreAroundFields field. */
    @XmlAttribute(name=CHARS_THAT_FORCE_QUOTING,required=false)
    private String charsThatForceQuotingString;
    
    /**
     * Characters to be escaped.
     * (by default this set is empty).
     */
    private char[] charsToEscape;
    
    /**
     * Characters that forces the whole field to be quoted.
     * (by default this set is: {[ ],[,],[\t],[\n]}).
     */
    private char[] charsThatForceQuoting;
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLFormatterConf()
    {

        super();
    
        this.charsToEscapeString = null;
        this.charsThatForceQuotingString = null;
        
        this.charsToEscape = null;
        this.charsThatForceQuoting = null;
        
    }
    
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the characters to escape during formatting.
     * 
     * @return the characters to escape during formatting.
     */
    @XmlTransient
    public char[] getCharsToEscape()
    {

        if( charsToEscape == null )
            charsToEscape = parseCharSet( charsToEscapeString );
        
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
        this.charsToEscapeString = formatCharSet( charsToEscape );
    }
    
    /**
     * Returns the characters that force the field to be quoted.
     * 
     * @return the characters that force the field to be quoted.
     */
    @XmlTransient
    public char[] getCharsThatForceQuoting()
    {
        
        if( charsThatForceQuoting == null )
            charsThatForceQuoting = parseCharSet( charsThatForceQuotingString );
        
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
        this.charsThatForceQuotingString = formatCharSet( charsThatForceQuoting );
    }
    
}