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

/**
 * Represents the configuration of the CSV column.
 * 
 * @author Nerd4j Team
 */
public class CSVColumnConf implements Cloneable
{

    /** The name of the column in the header. */
    private String name;

    /** The element of the model related to the column. */
    private String mapping;
    
    /** Tells if the column can have {@code null} values. */
    private Boolean optional;
    
    /** The order in which the column should be written. */
    private Integer order;
    
    /** The name to reference the processor to use to process the column data. */
    private String processorRef;

    /** The processor to use to process the column data. */
    private CSVFieldProcessorConf processor;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVColumnConf()
    {

        super();
    
        this.name = null;
        this.order = null;
        this.mapping = null;
        this.optional = null;
        this.processor = null;
        this.processorRef = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the column name.
     * 
     * @return the column name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the column name.
     * 
     * @param name value to set.
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * Returns the related model mapping.
     * 
     * @return the related model mapping.
     */
    public String getMapping()
    {
        return mapping;
    }
    
    /**
     * Sets the related model mapping.
     * 
     * @param mapping value to set.
     */
    public void setMapping( String mapping )
    {
        this.mapping = mapping;
    }

    /**
     * Returns the flag {@code optional}.
     * 
     * @return the flag {@code optional}.
     */
    public Boolean getOptional()
    {
        return optional;
    }
    
    /**
     * Sets the flag {@code optional}.
     * 
     * @param optional value to set.
     */
    public void setOptional( Boolean optional )
    {
        this.optional = optional;
    }
    
    /**
     * Returns the {@code order} value.
     * 
     * @return the {@code order} value.
     */
    public Integer getOrder()
    {
    	return order;
    }
    
    /**
     * Sets the {@code order} value.
     * 
     * @param order value to set.
     */
    public void setOrder( Integer order )
    {
    	this.order = order;
    }

    /**
     * Returns the column processor reference.
     * 
     * @return the column processor reference.
     */
    public String getProcessorRef()
    {
        return processorRef;
    }
    
    /**
     * Sets the column processor reference.
     * 
     * @param processorRef value to set.
     */
    public void setProcessorRef( String processorRef )
    {
        this.processorRef = processorRef;
    }
    
    /**
     * Returns the column processor configuration.
     * 
     * @return the column processor configuration.
     */
    public CSVFieldProcessorConf getProcessor()
    {
        return processor;
    }
    
    /**
     * Sets the column processor configuration.
     * 
     * @param processor value to set.
     */
    public void setProcessor( CSVFieldProcessorConf processor )
    {
        this.processor = processor;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVColumnConf clone() throws CloneNotSupportedException
    {
        
        final CSVColumnConf clone = (CSVColumnConf) super.clone();
        
        clone.processor = this.processor != null ? this.processor.clone() : null;
        
        return clone;
        
    }
    
}