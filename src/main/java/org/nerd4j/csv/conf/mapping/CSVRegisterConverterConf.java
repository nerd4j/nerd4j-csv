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
 * Represents the configuration of the
 * {@link org.nerd4j.csv.field.CSVFieldConverter CSVFieldConverter}.
 * 
 * @author Nerd4j Team
 */
public class CSVRegisterConverterConf extends CSVFieldConverterConf implements Cloneable
{
    
    /** Name to use to reference the converter in the registry. */
    private String name;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVRegisterConverterConf()
    {

        super();
    
        this.name = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the registry name.
     * 
     * @return the registry name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets  the registry name.
     * 
     * @param name value to set.
     */
    public void setName( String name )
    {
        this.name = name;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVRegisterConverterConf clone() throws CloneNotSupportedException
    {
        return (CSVRegisterConverterConf) super.clone();
    }
    
}