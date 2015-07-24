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
 * Represents the configuration of the CSVRegister.
 * 
 * @author Nerd4j Team
 */
public class CSVRegisterConf implements Cloneable
{
    
    /** Configuration to use to register custom types. */
    private CSVRegisterTypesConf types;
    
    /** Map of registered CSV validators. */
    private Map<String,CSVRegisterValidatorConf> validators;
    
    /** Map of registered CSV converters. */
    private Map<String,CSVRegisterConverterConf> converters;
    
    /** Map of registered CSV processors. */
    private Map<String,CSVRegisterProcessorConf> processors;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVRegisterConf()
    {

        super();
        
        this.types = new CSVRegisterTypesConf();
        
        this.validators = new HashMap<String,CSVRegisterValidatorConf>();
        this.converters = new HashMap<String,CSVRegisterConverterConf>();
        this.processors = new HashMap<String,CSVRegisterProcessorConf>();
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    public CSVRegisterTypesConf getTypes()
    {
        return types;
    }
    
    public Map<String,CSVRegisterValidatorConf> getValidators()
    {
        return validators;
    }

    public Map<String,CSVRegisterConverterConf> getConverters()
    {
        return converters;
    }

    public Map<String,CSVRegisterProcessorConf> getProcessors()
    {
        return processors;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVRegisterConf clone() throws CloneNotSupportedException
    {
        
        final CSVRegisterConf clone = (CSVRegisterConf) super.clone();
        
        clone.types = this.types != null ? this.types.clone() : null;
        
        clone.validators = new HashMap<String,CSVRegisterValidatorConf>( this.validators.size() );
        for( Map.Entry<String,CSVRegisterValidatorConf> entry : this.validators.entrySet() )
            clone.validators.put( entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null );
        
        clone.converters = new HashMap<String,CSVRegisterConverterConf>( this.converters.size() );
        for( Map.Entry<String,CSVRegisterConverterConf> entry : this.converters.entrySet() )
            clone.converters.put( entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null );
                        
        clone.processors = new HashMap<String,CSVRegisterProcessorConf>( this.processors.size() );
        for( Map.Entry<String,CSVRegisterProcessorConf> entry : this.processors.entrySet() )
            clone.processors.put( entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null );
                        
        return clone;
        
    }
    
}