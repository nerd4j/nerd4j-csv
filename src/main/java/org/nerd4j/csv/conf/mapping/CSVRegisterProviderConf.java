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



/**
 * Represents the configuration of a registry entry provider.
 * 
 * @author Nerd4j Team
 */
public class CSVRegisterProviderConf implements Cloneable
{
    
    /** The name of the type to use to bind the provider in the registry. */
    private String typeName;
    
    /** The full qualified name of the provider class. */
    private String providerClass;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVRegisterProviderConf()
    {

        super();
        
        this.typeName = null;
        this.providerClass = null;
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */

    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName( String typeName )
    {
        this.typeName = typeName;
    }

    public String getProviderClass()
    {
        return providerClass;
    }
    
    public void setProviderClass( String providerClass )
    {
        this.providerClass = providerClass;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVRegisterProviderConf clone() throws CloneNotSupportedException
    {
        return (CSVRegisterProviderConf) super.clone();
    }
    
}