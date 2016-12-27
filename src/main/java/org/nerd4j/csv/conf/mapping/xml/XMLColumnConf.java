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


/**
 * Backing bean that represents the XML configuration of the CSV column.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="column")
public class XMLColumnConf
{

    /** The name of the column in the header. */
    private String name;

    /** The element of the model related to the column. */
    private String mapping;
    
    /** Tells if the column can have {@code null} values. */
    private Boolean optional;
    
    /** The name to reference the processor to use to process the column data. */
    private String processorRef;

    /** The processor to use to process the column data. */
    private XMLFieldProcessorConf processor;
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLColumnConf()
    {

        super();
    
        this.name = null;
        this.mapping = null;
        this.optional = null;
        this.processor = null;
        this.processorRef = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the column name.
     * 
     * @return the column name.
     */
    @XmlAttribute(name="name",required=true)
    public String getName()
    {
        return name;
    }

    /**
     * Sets the column name.
     * 
     * @param name value to set.
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * Returns the related model mapping.
     * 
     * @return the related model mapping.
     */
    @XmlAttribute(name="mapping",required=false)
    public String getMapping()
    {
        return mapping;
    }
    
    /**
     * Sets the related model mapping.
     * 
     * @param mapping value to set.
     */
    public void setMapping( String mapping )
    {
        this.mapping = mapping;
    }

    /**
     * Returns the flag {@code optional}.
     * 
     * @return the flag {@code optional}.
     */
    @XmlAttribute(name="optional",required=false)
    public Boolean getOptional()
    {
        return optional;
    }
    
    /**
     * Sets the flag {@code optional}.
     * 
     * @param optional value to set.
     */
    public void setOptional( Boolean optional )
    {
        this.optional = optional;
    }

    /**
     * Returns the column processor reference.
     * 
     * @return the column processor reference.
     */
    @XmlAttribute(name="processor-ref",required=false)
    public String getProcessorRef()
    {
        return processorRef;
    }
    
    /**
     * Sets the column processor reference.
     * 
     * @param processorRef value to set.
     */
    public void setProcessorRef( String processorRef )
    {
        this.processorRef = processorRef;
    }
    
    /**
     * Returns the column processor configuration.
     * 
     * @return the column processor configuration.
     */
    @XmlElement(name="processor",required=false)
    public XMLFieldProcessorConf getProcessor()
    {
        return processor;
    }
    
    /**
     * Sets the column processor configuration.
     * 
     * @param processor value to set.
     */
    public void setProcessor( XMLFieldProcessorConf processor )
    {
        this.processor = processor;
    }
    
}