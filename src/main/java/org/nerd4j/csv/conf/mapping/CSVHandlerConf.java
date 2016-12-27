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



/**
 * Representation of a configuration
 * for objects able to handle CSV.
 * 
 * @author Nerd4j Team
 */
public class CSVHandlerConf implements Cloneable
{

    /** The name of the reader instance. */
    private String name;
    
    /** The model binder configuration. */
    private CSVModelBinderConf modelBinder;
    
    /** Map of configured columns. */
    private Map<String,CSVColumnConf> columns;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVHandlerConf()
    {

        super();
    
        this.name = null;
        this.modelBinder = new CSVModelBinderConf();
        this.columns     = new HashMap<String,CSVColumnConf>();
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the name related to the configuration.
     * 
     * @return the name related to the configuration.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets  the name related to the configuration.
     * 
     * @param name value to set.
     */
    public void setName( String name )
    {
        this.name = name;
    }
    
    /**
     * Returns the configurations related to the CSV columns.
     * 
     * @return the configurations related to the CSV columns.
     */
    public Map<String,CSVColumnConf> getColumns()
    {
        return columns;
    }
    
    /**
     * Returns the configuration related to the model binder.
     * 
     * @return the configuration related to the model binder.
     */
    public CSVModelBinderConf getModelBinder()
    {
        return modelBinder;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVHandlerConf clone() throws CloneNotSupportedException
    {
        
        final CSVHandlerConf clone = (CSVHandlerConf) super.clone();
        
        clone.modelBinder = this.modelBinder != null ? this.modelBinder.clone() : null;
        
        if( this.columns != null )
        {
            clone.columns = new HashMap<String,CSVColumnConf>( this.columns.size() );
            for( Map.Entry<String,CSVColumnConf> entry : this.columns.entrySet() )
                clone.columns.put( entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null );
        }
        
        return clone;
        
    }

}