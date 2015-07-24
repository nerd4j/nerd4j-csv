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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Backing bean that represents the XML global configuration.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="configuration")
@XmlRootElement(name="configuration")
public class XMLConfiguration
{
    
    /** Configuration about registering items. */
    private XMLRegisterConf register;

    /** List of configured readers. */
    private List<XMLParserConf> parsers;
    
    /** List of configured writers. */
    private List<XMLFormatterConf> formatters;
    
    /** List of configured readers. */
    private List<XMLReaderConf> readers;
    
    /** List of configured writers. */
    private List<XMLWriterConf> writers;
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLConfiguration()
    {

        super();
    
        this.register   = null;
        
        this.readers    = null;
        this.writers    = null;
        
        this.parsers    = null;
        this.formatters = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    @XmlElement(name="register",required=false)
    public XMLRegisterConf getRegister()
    {
        return register;
    }

    public void setRegister( XMLRegisterConf register )
    {
        this.register = register;
    }

    @XmlElement(name="reader",required=false)
    public List<XMLReaderConf> getReaders()
    {
        return readers;
    }
    
    public void setReaders( List<XMLReaderConf> readers )
    {
        this.readers = readers;
    }
   
    @XmlElement(name="writer",required=false)
    public List<XMLWriterConf> getWriters()
    {
        return writers;
    }
    
    public void setWriters( List<XMLWriterConf> writers )
    {
        this.writers = writers;
    }
    
    @XmlElement(name="parser",required=false)
    public List<XMLParserConf> getParsers()
    {
        return parsers;
    }
    
    public void setParsers( List<XMLParserConf> parsers )
    {
        this.parsers = parsers;
    }
    
    @XmlElement(name="formatter",required=false)
    public List<XMLFormatterConf> getFormatters()
    {
        return formatters;
    }
    
    public void setFormatters( List<XMLFormatterConf> formatters )
    {
        this.formatters = formatters;
    }
    
}