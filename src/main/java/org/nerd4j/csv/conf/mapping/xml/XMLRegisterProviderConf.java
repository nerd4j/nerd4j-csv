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


/**
 * Backing bean that represents the XML configuration
 * for the registry entry providers.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="register-provider")
public class XMLRegisterProviderConf
{
    
    /** The name of the type to use to bind the provider in the registry. */
    private String typeName;
    
    /** The full qualified name of the provider class. */
    private String providerClass;
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLRegisterProviderConf()
    {

        super();
        
        this.typeName = null;
        this.providerClass = null;
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */

    
    /**
     * Returns the registry name.
     * 
     * @return the registry name.
     */
    @XmlAttribute(name="type-name",required=true)
    public String getTypeName()
    {
        return typeName;
    }
    
    /**
     * Sets the registry name.
     * 
     * @param typeName value to set.
     */
    public void setTypeName( String typeName )
    {
        this.typeName = typeName;
    }

    /**
     * Returns the name of the class that implements the provider.
     * 
     * @return full qualified name.
     */
    @XmlAttribute(name="provider-class",required=true)
    public String getProviderClass()
    {
        return providerClass;
    }
    
    /**
     * Sets the name of the class that implements the provider.
     * 
     * @param providerClass value to set.
     */
    public void setProviderClass( String providerClass )
    {
        this.providerClass = providerClass;
    }
    
}