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

import org.nerd4j.csv.field.CSVFieldProcessor;


/**
 * Backing bean that represents the XML configuration of the {@link CSVFieldProcessor}.
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
    
    
    @XmlAttribute(name="precondition-ref",required=false)
    public String getPreconditionRef()
    {
        return preconditionRef;
    }
        
    public void setPreconditionRef( String preconditionRef )
    {
        this.preconditionRef = preconditionRef;
    }
    
    @XmlAttribute(name="converter-ref",required=false)
    public String getConverterRef()
    {
        return converterRef;
    }
    
    public void setConverterRef( String converterRef )
    {
        this.converterRef = converterRef;
    }
    
    @XmlAttribute(name="postcondition-ref",required=false)
    public String getPostconditionRef()
    {
        return postconditionRef;
    }
    
    public void setPostconditionRef( String postconditionRef )
    {
        this.postconditionRef = postconditionRef;
    }
    
    @XmlElement(name="precondition",required=false)
    public XMLFieldValidatorConf getPrecondition()
    {
        return precondition;
    }
    
    public void setPrecondition( XMLFieldValidatorConf precondition )
    {
        this.precondition = precondition;
    }
    
    @XmlElement(name="converter",required=false)
    public XMLFieldConverterConf getConverter()
    {
        return converter;
    }
    
    public void setConverter( XMLFieldConverterConf converter )
    {
        this.converter = converter;
    }
    
    @XmlElement(name="postcondition",required=false)
    public XMLFieldValidatorConf getPostcondition()
    {
        return postcondition;
    }
    
    public void setPostcondition( XMLFieldValidatorConf postcondition )
    {
        this.postcondition = postcondition;
    }
    
}