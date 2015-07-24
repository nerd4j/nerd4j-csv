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
 * Represents the configuration for registry entry providers.
 * 
 * @author Nerd4j Team
 */
public class CSVRegisterTypesConf implements Cloneable
{
    
    /** Map of CSV validator providers. */
    private HashMap<String,CSVRegisterProviderConf> validatorProviders;
    
    /** Map of CSV converter providers. */
    private HashMap<String,CSVRegisterProviderConf> converterProviders;

    /** Map of CSV to Model providers. */
    private HashMap<String,CSVRegisterProviderConf> csvToModelProviders;
    
    /** Map of Model to CSV providers. */
    private HashMap<String,CSVRegisterProviderConf> modelToCSVProviders;
    
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVRegisterTypesConf()
    {

        super();
        
        this.validatorProviders = new HashMap<String,CSVRegisterProviderConf>();
        this.converterProviders = new HashMap<String,CSVRegisterProviderConf>();
        this.csvToModelProviders = new HashMap<String,CSVRegisterProviderConf>();
        this.modelToCSVProviders = new HashMap<String,CSVRegisterProviderConf>();
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    public Map<String,CSVRegisterProviderConf> getValidatorProviders()
    {
        return validatorProviders;
    }

    public Map<String,CSVRegisterProviderConf> getConverterProviders()
    {
        return converterProviders;
    }
    
    public Map<String,CSVRegisterProviderConf> getCsvToModelProviders()
    {
        return csvToModelProviders;
    }

    public Map<String,CSVRegisterProviderConf> getModelToCSVProviders()
    {
        return modelToCSVProviders;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVRegisterTypesConf clone() throws CloneNotSupportedException
    {
        
        final CSVRegisterTypesConf clone = (CSVRegisterTypesConf) super.clone();
        
        clone.validatorProviders  = deepClone( this.validatorProviders );
        clone.converterProviders  = deepClone( this.converterProviders );
        clone.csvToModelProviders = deepClone( this.csvToModelProviders );
        clone.modelToCSVProviders = deepClone( this.modelToCSVProviders );
        
        return clone;
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * Creates a deep clone of the given map ie each map value will be cloned.
     * 
     * @param source the map to clone.
     * @return the cloned map.
     * @throws CloneNotSupportedException
     */
    public HashMap<String,CSVRegisterProviderConf> deepClone( HashMap<String,CSVRegisterProviderConf> source )
    throws CloneNotSupportedException
    {
        
        final HashMap<String,CSVRegisterProviderConf> target = new HashMap<String,CSVRegisterProviderConf>( source.size() );
        for( Map.Entry<String,CSVRegisterProviderConf> entry : source.entrySet() )
            target.put( entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null );
        
        return target;
                
    }
    
}