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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Backing bean that represents the XML configuration of the
 * {@link org.nerd4j.csv.field.CSVFieldProcessor CSVFieldProcessor}.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="processor")
public class XMLFieldProcessorConf
{
    
    /** The name to reference the configuration of the related precondition. */
    private String preconditionRef;
    
    /** The name to reference the configuration of the related converter. */
    private String converterRef;
    
    /** The name to reference the configuration of the related postcondition. */
    private String postconditionRef;

    /** The in-line configuration of the related precondition. */
    private XMLFieldValidatorConf precondition;
    
    /** The in-line configuration of the related converter. */
    private XMLFieldConverterConf converter;
    
    /** The in-line configuration of the related postcondition. */
    private XMLFieldValidatorConf postcondition;
        
    
    /**
     * Default constructor.
     * 
     */
    public XMLFieldProcessorConf()
    {

        super();
    
        this.converter = null;
        this.precondition = null;
        this.postcondition = null;
        
        this.converterRef = null;
        this.preconditionRef = null;
        this.postconditionRef = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the precondition validator reference.
     * 
     * @return the precondition validator reference.
     */
    @XmlAttribute(name="precondition-ref",required=false)
    public String getPreconditionRef()
    {
        return preconditionRef;
    }
    
    /**
     * Sets the precondition validator reference.
     * 
     * @param preconditionRef value to set.
     */
    public void setPreconditionRef( String preconditionRef )
    {
        this.preconditionRef = preconditionRef;
    }
    
    /**
     * Returns the converter reference.
     * 
     * @return the converter reference.
     */
    @XmlAttribute(name="converter-ref",required=false)
    public String getConverterRef()
    {
        return converterRef;
    }
    
    /**
     * Sets the converter reference.
     * 
     * @param converterRef value to set.
     */
    public void setConverterRef( String converterRef )
    {
        this.converterRef = converterRef;
    }
    
    /**
     * Returns the postcondition validator reference.
     * 
     * @return the postcondition validator reference.
     */
    @XmlAttribute(name="postcondition-ref",required=false)
    public String getPostconditionRef()
    {
        return postconditionRef;
    }
    
    /**
     * Sets the postcondition validator reference.
     * 
     * @param postconditionRef value to set.
     */
    public void setPostconditionRef( String postconditionRef )
    {
        this.postconditionRef = postconditionRef;
    }
    
    /**
     * Returns the precondition validator configuration.
     * 
     * @return the precondition validator configuration.
     */
    @XmlElement(name="precondition",required=false)
    public XMLFieldValidatorConf getPrecondition()
    {
        return precondition;
    }
    
    /**
     * Sets the precondition validator configuration.
     * 
     * @param precondition value to set.
     */
    public void setPrecondition( XMLFieldValidatorConf precondition )
    {
        this.precondition = precondition;
    }
    
    /**
     * Returns the converter configuration.
     * 
     * @return the converter configuration.
     */
    @XmlElement(name="converter",required=false)
    public XMLFieldConverterConf getConverter()
    {
        return converter;
    }
    
    /**
     * Sets the converter configuration.
     * 
     * @param converter value to set.
     */
    public void setConverter( XMLFieldConverterConf converter )
    {
        this.converter = converter;
    }
    
    /**
     * Returns the postcondition validator configuration.
     * 
     * @return the postcondition validator configuration.
     */
    @XmlElement(name="postcondition",required=false)
    public XMLFieldValidatorConf getPostcondition()
    {
        return postcondition;
    }
    
    /**
     * Sets the postcondition validator configuration.
     * 
     * @param postcondition value to set.
     */
    public void setPostcondition( XMLFieldValidatorConf postcondition )
    {
        this.postcondition = postcondition;
    }
    
}