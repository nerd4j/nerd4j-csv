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
 * Represents the configuration of the
 * {@link org.nerd4j.csv.writer.CSVWriter CSVWriter}.
 * 
 * @author Nerd4j Team
 */
public class CSVWriterConf extends CSVHandlerConf implements Cloneable
{

    /** Tells if to write the CSV target first row as header. */
    private Boolean writeHeader;
    
    /** The name to reference the formatter configuration to use. */
    private String formatterRef;
    
    /** The formatter configuration embedded into the reader. */
    private CSVFormatterConf formatter;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVWriterConf()
    {

        super();
    
        this.writeHeader = null;
        
        this.formatter = null;
        this.formatterRef = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the {@code write-header} flag. 
     * 
     * @return the {@code write-header} flag.
     */
    public Boolean getWriteHeader()
    {
        return writeHeader;
    }
    
    /**
     * Sets the {@code write-header} flag.
     * 
     * @param writeHeader value to set.
     */
    public void setWriteHeader( Boolean writeHeader )
    {
        this.writeHeader = writeHeader;
    }

    /**
     * Returns the formatter reference.
     * 
     * @return the formatter reference.
     */
    public String getFormatterRef()
    {
        return formatterRef;
    }
    
    /**
     * Sets the formatter reference.
     * 
     * @param formatterRef value to set.
     */
    public void setFormatterRef( String formatterRef )
    {
        this.formatterRef = formatterRef;
    }

    /**
     * Returns the formatter configuration.
     * 
     * @return the formatter configuration.
     */
    public CSVFormatterConf getFormatter()
    {
        return formatter;
    }
    
    /**
     * Sets the formatter configuration.
     * 
     * @param formatter value to set.
     */
    public void setFormatter( CSVFormatterConf formatter )
    {
        this.formatter = formatter;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVWriterConf clone() throws CloneNotSupportedException
    {
        
        final CSVWriterConf clone = (CSVWriterConf) super.clone();
        
        clone.formatter = this.formatter != null ? this.formatter.clone() : null;
        
        return clone;
        
    }
    
}