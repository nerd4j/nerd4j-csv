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

import org.nerd4j.csv.field.CSVFieldValidator;


/**
 * Represents the configuration of the {@link CSVFieldValidator}.
 * 
 * @author Nerd4j Team
 */
public class CSVRegisterValidatorConf extends CSVFieldValidatorConf implements Cloneable
{
    
    /** Name to use to reference the validator in the registry. */
    private String name;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVRegisterValidatorConf()
    {

        super();
    
        this.name = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    public String getName()
    {
        return name;
    }

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
    public CSVRegisterValidatorConf clone() throws CloneNotSupportedException
    {
        return (CSVRegisterValidatorConf) super.clone();
    }
    
}