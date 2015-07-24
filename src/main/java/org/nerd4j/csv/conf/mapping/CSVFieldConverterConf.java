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

import java.util.HashMap;
import java.util.Map;

import org.nerd4j.csv.field.CSVFieldConverter;


/**
 * Represents the configuration of the {@link CSVFieldConverter}.
 * 
 * @author Nerd4j Team
 */
public class CSVFieldConverterConf implements Cloneable
{
    
    /** Type to use to reference the converter in the registry. */
    private String type;
    
    /** The parameters to use to configure the converter. */
    private HashMap<String,String> params;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVFieldConverterConf()
    {

        super();
    
        this.type = null;
        this.params = new HashMap<String,String>();
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public Map<String,String> getParams()
    {
        return params;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public CSVFieldConverterConf clone() throws CloneNotSupportedException
    {
        
        final CSVFieldConverterConf clone = (CSVFieldConverterConf) super.clone();
        
        if( this.params != null )
            clone.params = (HashMap<String,String>) this.params.clone();
        
        return clone;
        
    }
    
}