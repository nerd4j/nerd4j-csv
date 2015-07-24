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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.nerd4j.csv.reader.CSVReader;


/**
 * Backing bean that represents the XML configuration of the {@link CSVReader}.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="writer")
public class XMLWriterConf extends XMLHandlerConf
{

    /** Tells if to write the CSV target first row as header. */
    private Boolean writeHeader;
    
    /** The name to reference the formatter configuration to use. */
    private String formatterRef;
    
    /** The formatter configuration embedded into the reader. */
    private XMLFormatterConf formatter;
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLWriterConf()
    {

        super();
        
        this.formatter    = null;
        this.formatterRef = null;
        this.writeHeader  = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    

    @XmlAttribute(name="write-header",required=false)
    public Boolean getWriteHeader()
    {
        return writeHeader;
    }
    
    public void setWriteHeader( Boolean writeHeader )
    {
        this.writeHeader = writeHeader;
    }

    @XmlAttribute(name="formatter-ref",required=false)
    public String getFormatterRef()
    {
        return formatterRef;
    }
    
    public void setFormatterRef( String formatterRef )
    {
        this.formatterRef = formatterRef;
    }

    @XmlElement(name="formatter",required=false)
    public XMLFormatterConf getFormatter()
    {
        return formatter;
    }
    
    public void setFormatter( XMLFormatterConf formatter )
    {
        this.formatter = formatter;
    }
    
}