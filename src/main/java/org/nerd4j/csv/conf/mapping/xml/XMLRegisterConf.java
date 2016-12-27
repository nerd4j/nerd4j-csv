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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;


/**
 * Backing bean that represents the XML configuration of the CSVRegister.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="register")
public class XMLRegisterConf
{
    
    /** Configuration to use to register custom types. */
    private XMLRegisterTypesConf types;
    
    /** List of registered CSV validators. */
    private List<XMLRegisterValidatorConf> validators;
    
    /** List of registered CSV converters. */
    private List<XMLRegisterConverterConf> converters;
    
    /** List of registered CSV processors. */
    private List<XMLRegisterProcessorConf> processors;
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLRegisterConf()
    {

        super();
        
        this.types = null;
        
        this.validators = null;
        this.converters = null;
        this.processors = null;
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the custom types configurations.
     *  
     * @return the custom types configurations.
     */
    @XmlElement(name="types")
    public XMLRegisterTypesConf getTypes()
    {
        return types;
    }
    
    /**
     * Sets the custom types configurations.
     * 
     * @param types value to set.
     */
    public void setTypes( XMLRegisterTypesConf types )
    {
        this.types = types;
    }
    
    /**
     * Returns the validators configurations.
     * 
     * @return the validators configurations.
     */
    @XmlElement(name="validator")
    @XmlElementWrapper(name="validators")
    public List<XMLRegisterValidatorConf> getValidators()
    {
        return validators;
    }

    /**
     * Sets the validators configurations.
     * 
     * @param validators value to set.
     */
    public void setValidators( List<XMLRegisterValidatorConf> validators )
    {
        this.validators = validators;
    }

    /**
     * Returns the converters configurations.
     * 
     * @return the converters configurations.
     */
    @XmlElement(name="converter")
    @XmlElementWrapper(name="converters")
    public List<XMLRegisterConverterConf> getConverters()
    {
        return converters;
    }
    
    /**
     * Sets the converters configurations.
     * 
     * @param converters value to set.
     */
    public void setConverters( List<XMLRegisterConverterConf> converters )
    {
        this.converters = converters;
    }

    /**
     * Returns the processors configurations.
     * 
     * @return the processors configurations.
     */
    @XmlElement(name="processor")
    @XmlElementWrapper(name="processors")
    public List<XMLRegisterProcessorConf> getProcessors()
    {
        return processors;
    }
    
    /**
     * Sets the processors configurations.
     * 
     * @param processors value to set.
     */
    public void setProcessors( List<XMLRegisterProcessorConf> processors )
    {
        this.processors = processors;
    }

}