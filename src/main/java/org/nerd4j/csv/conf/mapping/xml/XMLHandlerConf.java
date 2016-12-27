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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.nerd4j.dependency.DependentBean;



/**
 * Abstract common representation of an XML configuration
 * for objects able to handle CSV.
 * 
 * This class implements the {@link DependentBean} interface
 * that allows dependencies to be automatically resolved.
 * 
 * @author Nerd4j Team
 */
@XmlType(name="handler")
public class XMLHandlerConf implements DependentBean
{

    /** The name of the reader instance. */
    private String name;
    
    /** The name of the configuration to inherit from. */
    private String inherit;
    
    /** The configuration which the current depends from. */ 
    private XMLHandlerConf dependency;

    /** List of configured columns. */
    private List<XMLColumnConf> columns;

    /** The model binder configuration. */
    private XMLModelBinderConf modelBinder;
    
    
    /**
     * Default constructor.
     * 
     */
    public XMLHandlerConf()
    {

        super();
    
        this.name = null;
        this.inherit = null;
        this.dependency = null;
        this.modelBinder = null;
        this.columns = new ArrayList<XMLColumnConf>();
        
    }
    
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the name related to the configuration.
     * 
     * @return the name related to the configuration.
     */
    @XmlAttribute(name="name",required=true)
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
     * Returns the key of the parent configuration inherited by the current one.
     * 
     * @return the key of the parent configuration inherited by the current one.
     */
    @XmlAttribute(name="inherit",required=false)
    public String getInherit()
    {
        return inherit;
    }
    
    /**
     * Sets the key of the parent configuration inherited by the current one.
     * 
     * @param inherit value to set.
     */
    public void setInherit( String inherit )
    {
        this.inherit = inherit;
    }
    
    /**
     * Returns the configurations related to the CSV columns.
     * 
     * @return the configurations related to the CSV columns.
     */
    @XmlElement(name="column",required=true)
    @XmlElementWrapper(name="columns",required=true)
    public List<XMLColumnConf> getColumns()
    {
        return columns;
    }
    
    /**
     * Sets the configurations related to the CSV columns.
     * 
     * @param columns value to set.
     */
    public void setColumns( List<XMLColumnConf> columns )
    {
        this.columns = columns;
    }

    /**
     * Returns the configuration related to the model binder.
     * 
     * @return the configuration related to the model binder.
     */
    @XmlElement(name="model-binder",required=false)
    public XMLModelBinderConf getModelBinder()
    {
        return modelBinder;
    }
    
    /**
     * Sets the configuration related to the model binder.
     * 
     * @param modelBinder value to set.
     */
    public void setModelBinder( XMLModelBinderConf modelBinder )
    {
        this.modelBinder = modelBinder;
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<XMLHandlerConf> getDependencies()
    {
        if( this.dependency != null )
            return Collections.singleton( this.dependency );
        else
            return Collections.emptySet();
    }

    /**
	 * Sets the current dependency set.
	 * 
	 * @param dependency value to set.
	 */
    public void setDependency( XMLHandlerConf dependency )
    {
        this.dependency = dependency;
    }

}