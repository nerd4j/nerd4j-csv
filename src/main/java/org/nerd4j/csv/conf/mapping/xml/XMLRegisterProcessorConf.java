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
import javax.xml.bind.annotation.XmlType;

import org.nerd4j.csv.field.CSVFieldProcessor;
import org.nerd4j.format.Formatted;


/**
 * Backing bean that represents the XML configuration of the {@link CSVFieldProcessor}.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="register-processor")
public class XMLRegisterProcessorConf extends XMLFieldProcessorConf
{
    
    /** Name to use to reference the converter in the registry. */
    @Formatted
    private String name;
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLRegisterProcessorConf()
    {

        super();
    
        this.name = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    @XmlAttribute(name="name",required=true)
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
    
}