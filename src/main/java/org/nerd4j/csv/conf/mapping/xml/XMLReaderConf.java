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
@XmlType(name="reader")
public class XMLReaderConf extends XMLHandlerConf
{

    /** Tells if to read the CSV source first row as header. */
    private Boolean readHeader;
    
    /** Tells if to use column names during configuration. */
    private Boolean useColumnNames;
    
    /**
     * In the CSV standard each record must have the same number of cells.
     * But it is possible to face non standard CSV files where records
     * have different lengths.
     * <p>
     *  This flag tells the reader to accept such non standard files.
     *  In this case the reader may return incomplete data models.
     * </p>
     */
    private Boolean acceptIncompleteRecords;
    
    /** The name to reference the parser configuration to use. */
    private String parserRef;
    
    /** The parser configuration embedded into the reader. */
    private XMLParserConf parser;
    
    /**
     * Default constructor.
     * 
     */
    public XMLReaderConf()
    {

        super();
    
        this.parser = null;
        this.parserRef = null;
        this.readHeader = null;
        this.useColumnNames = null;
        this.acceptIncompleteRecords = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    

    @XmlAttribute(name="read-header",required=false)
    public Boolean getReadHeader()
    {
        return readHeader;
    }
    
    public void setReadHeader( Boolean readHeader )
    {
        this.readHeader = readHeader;
    }
    
    @XmlAttribute(name="use-column-names",required=false)
    public Boolean getUseColumnNames()
    {
        return useColumnNames;
    }
    
    public void setUseColumnNames( Boolean useColumnNames )
    {
        this.useColumnNames = useColumnNames;
    }
    
    @XmlAttribute(name="accept-incomplete-records",required=false)
    public Boolean getAcceptIncompleteRecords()
    {
    	return acceptIncompleteRecords;
    }
    
    public void setAcceptIncompleteRecords( Boolean acceptIncompleteRecords )
    {
    	this.acceptIncompleteRecords = acceptIncompleteRecords;
    }

    @XmlAttribute(name="parser-ref",required=false)
    public String getParserRef()
    {
        return parserRef;
    }
    
    public void setParserRef( String parserRef )
    {
        this.parserRef = parserRef;
    }

    @XmlElement(name="parser",required=false)
    public XMLParserConf getParser()
    {
        return parser;
    }

    public void setParser( XMLParserConf parser )
    {
        this.parser = parser;
    }

}