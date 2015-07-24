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

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.nerd4j.csv.field.CSVFieldValidator;



/**
 * Backing bean that represents the XML configuration of the {@link CSVFieldValidator}.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="validator")
public class XMLFieldValidatorConf
{
    
    /** Type to use to reference the validator in the registry. */
    private String type;
    
    /** The parameters to use to configure the validator. */
    private Map<QName,String> params;
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLFieldValidatorConf()
    {

        super();
    
        this.type = null;
        this.params = new HashMap<QName,String>();
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    @XmlAttribute(name="type",required=true)
    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }


    @XmlAnyAttribute
    public Map<QName,String> getParams()
    {
        return params;
    }
    
    public void setParams( Map<QName,String> params )
    {
        this.params = params;
    }
    
}