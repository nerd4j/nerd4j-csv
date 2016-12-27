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
 * Represents the configuration obtained parsing
 * annotated beans and XML files.
 * 
 * @author Nerd4j Team
 */
public final class CSVConfiguration implements Cloneable
{
    
    /** The register items configuration. */
    private CSVRegisterConf register;

    /** Map of configured readers. */
    private Map<String,CSVParserConf> parsers;
    
    /** Map of configured writers. */
    private Map<String,CSVFormatterConf> formatters;

    /** Map of configured readers. */
    private Map<String,CSVReaderConf> readers;
    
    /** Map of configured writers. */
    private Map<String,CSVWriterConf> writers;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVConfiguration()
    {

        super();
        
        this.register = new CSVRegisterConf();
    
        this.readers = new HashMap<String,CSVReaderConf>();
        this.writers = new HashMap<String,CSVWriterConf>();
        
        this.parsers = new HashMap<String,CSVParserConf>();
        this.formatters = new HashMap<String,CSVFormatterConf>();
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */


    /**
     * Returns the register configuration.
     * 
     * @return the register configuration.
     */
    public CSVRegisterConf getRegister()
    {
        return register;
    }

    /**
     * Returns the configurations related to the readers.
     * 
     * @return the configurations related to the readers.
     */
    public Map<String,CSVReaderConf> getReaders()
    {
        return readers;
    }

    /**
     * Returns the configurations related to the writers.
     * 
     * @return the configurations related to the writers.
     */
    public Map<String,CSVWriterConf> getWriters()
    {
        return writers;
    }
    
    /**
     * Returns the configurations related to the parsers.
     * 
     * @return the configurations related to the parsers.
     */
    public Map<String,CSVParserConf> getParsers()
    {
        return parsers;
    }

    /**
     * Returns the configurations related to the formatters.
     * 
     * @return the configurations related to the formatters.
     */
    public Map<String,CSVFormatterConf> getFormatters()
    {
        return formatters;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVConfiguration clone() throws CloneNotSupportedException
    {
        
        final CSVConfiguration clone = (CSVConfiguration) super.clone();
        
        clone.register = this.register != null ? this.register.clone() : null;
        
        if( readers != null )
        {
            clone.readers = new HashMap<String,CSVReaderConf>( this.readers.size() );
            for( Map.Entry<String,CSVReaderConf> entry : this.readers.entrySet() )
                clone.readers.put( entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null );
        }

        if( writers != null )
        {
            clone.writers = new HashMap<String,CSVWriterConf>( this.writers.size() );
            for( Map.Entry<String,CSVWriterConf> entry : this.writers.entrySet() )
                clone.writers.put( entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null );
        }
                        
        if( parsers != null )
        {
            clone.parsers = new HashMap<String,CSVParserConf>( this.parsers.size() );
            for( Map.Entry<String,CSVParserConf> entry : this.parsers.entrySet() )
                clone.parsers.put( entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null );
        }
                
        if( formatters != null )
        {
            clone.formatters = new HashMap<String,CSVFormatterConf>( this.formatters.size() );
            for( Map.Entry<String,CSVFormatterConf> entry : this.formatters.entrySet() )
                clone.formatters.put( entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null );
        }
        
        return clone;
        
    }
    
}