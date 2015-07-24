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
import javax.xml.bind.annotation.XmlType;


/**
 * Backing bean that represents the XML configuration.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="register-types")
public class XMLRegisterTypesConf
{
    
    /** List of CSV validator providers. */
    private List<XMLRegisterProviderConf> validatorProviders;
    
    /** List of CSV converter providers. */
    private List<XMLRegisterProviderConf> converterProviders;

    /** List of CSV to Model providers. */
    private List<XMLRegisterProviderConf> csvToModelProviders;
    
    /** List of Model to CSV providers. */
    private List<XMLRegisterProviderConf> modelToCSVProviders;
    
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLRegisterTypesConf()
    {

        super();
        
        this.validatorProviders = null;
        this.converterProviders = null;
        this.csvToModelProviders = null;
        this.modelToCSVProviders = null;
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    @XmlElement(name="validator")
    public List<XMLRegisterProviderConf> getValidatorProviders()
    {
        return validatorProviders;
    }

    public void setValidatorProviders( List<XMLRegisterProviderConf> validatorProviders )
    {
        this.validatorProviders = validatorProviders;
    }

    @XmlElement(name="converter")
    public List<XMLRegisterProviderConf> getConverterProviders()
    {
        return converterProviders;
    }
    
    public void setConverterProviders( List<XMLRegisterProviderConf> converterProviders )
    {
        this.converterProviders = converterProviders;
    }

    @XmlElement(name="csv-to-model")
    public List<XMLRegisterProviderConf> getCsvToModelProviders()
    {
        return csvToModelProviders;
    }

    public void setCsvToModelProviders( List<XMLRegisterProviderConf> csvToModelProviders )
    {
        this.csvToModelProviders = csvToModelProviders;
    }

    @XmlElement(name="model-to-csv")
    public List<XMLRegisterProviderConf> getModelToCSVProviders()
    {
        return modelToCSVProviders;
    }

    public void setModelToCSVProviders( List<XMLRegisterProviderConf> modelToCSVProviders )
    {
        this.modelToCSVProviders = modelToCSVProviders;
    }
    
}