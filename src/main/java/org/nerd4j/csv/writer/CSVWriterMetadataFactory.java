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
package org.nerd4j.csv.writer;

import org.nerd4j.csv.conf.CSVMetadataBuilder;
import org.nerd4j.csv.conf.mapping.CSVConfiguration;
import org.nerd4j.csv.conf.mapping.CSVWriterConf;
import org.nerd4j.csv.registry.CSVRegistry;


/**
 * Represents a {@code Factory} able to build {@link CSVWriterMetadata}.
 * 
 * @param <Model> type of the field mapping descriptor.
 * 
 * @author Nerd4j Team
 */
public final class CSVWriterMetadataFactory<Model>
{
	
	/** Configuration to be parsed to create the meta-data model. */
	private CSVWriterConf writerConf;
	
	/** The global CSV configuration. */
	private CSVConfiguration configuration;
	
	/** The configuration registry to be used if needed. */
	private CSVRegistry registry;
	
	
	/**
     * Constructor with parameters.
     * 
     * @param writerConf    configuration to parse.
     * @param configuration the global configuration.
     * @param registry      the builder registry to use.
     */
    public CSVWriterMetadataFactory( CSVWriterConf writerConf,
    		                         CSVConfiguration configuration,
                                     CSVRegistry registry )
    {
    	
    	super();
    	
    	this.registry = registry;
    	this.writerConf = writerConf;
    	this.configuration = configuration;
    	
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * Returns a new instance of the reader meta-data model.
     * 
     * @return new instance of the reader meta-data model.
     */
    public CSVWriterMetadata<Model> getCSVWriterMetadata()
    {
    	
        return CSVMetadataBuilder.build( writerConf, configuration, registry );
        
    }
    
}