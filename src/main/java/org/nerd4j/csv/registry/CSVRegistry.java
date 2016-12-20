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
package org.nerd4j.csv.registry;

import org.nerd4j.csv.field.CSVFieldConverter;
import org.nerd4j.csv.field.CSVFieldProcessor;
import org.nerd4j.csv.field.CSVFieldValidator;
import org.nerd4j.csv.reader.binding.CSVToModelBinderFactory;
import org.nerd4j.csv.writer.binding.ModelToCSVBinderFactory;



/**
 * Represents a registry containing the provider able
 * to understand the configured values and provide the
 * related objects.
 * 
 * <p>
 *  This object represents a facade to access the
 *  following actual registry:
 *  <ul>
 *    <li>{@link CSVFieldValidatorRegistry}</li>
 *    <li>{@link CSVFieldConverterRegistry}</li>
 *    <li>{@link CSVFieldProcessorRegistry}</li>
 *    <li>{@link CSVToModelBinderFactoryRegistry}</li>
 *    <li>{@link ModelToCSVBinderFactoryRegistry}</li>
 *  </ul>
 * </p>
 * 
 * @author Nerd4j Team
 */
public final class CSVRegistry
{

    /** The registry used to retrieve validators. */
    private final CSVFieldValidatorRegistry validatorRegistry;
    
    /** The registry used to retrieve converters. */
    private final CSVFieldConverterRegistry converterRegistry;
    
    /** The registry used to retrieve converters. */
    private final CSVFieldProcessorRegistry processorRegistry;
    
    /** The registry used to retrieve CSV to model binder factories. */
    private final CSVToModelBinderFactoryRegistry csvToModelBinderFactoryRegistry;

    /** The registry used to retrieve model to CSV binder factories. */
    private final ModelToCSVBinderFactoryRegistry modelToCSVBinderFactoryRegistry;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVRegistry()
    {
        
        super();
        
        this.validatorRegistry = new CSVFieldValidatorRegistry();
        this.converterRegistry = new CSVFieldConverterRegistry();
        this.processorRegistry = new CSVFieldProcessorRegistry( converterRegistry );
        
        this.csvToModelBinderFactoryRegistry = new CSVToModelBinderFactoryRegistry();
        this.modelToCSVBinderFactoryRegistry = new ModelToCSVBinderFactoryRegistry();
        
    }
    
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */

    
    public CSVAbstractRegistry<CSVFieldValidator<?>> getValidatorRegistry()
    {
        return validatorRegistry;
    }
    
    public CSVAbstractRegistry<CSVFieldConverter<?,?>> getConverterRegistry()
    {
        return converterRegistry;
    }
    
    public CSVAbstractRegistry<CSVFieldProcessor<?,?>> getProcessorRegistry()
    {
        return processorRegistry;
    }
    
    public CSVAbstractRegistry<CSVToModelBinderFactory<?>> getCsvToModelBinderFactoryRegistry()
    {
        return csvToModelBinderFactoryRegistry;
    }
    
    public CSVAbstractRegistry<ModelToCSVBinderFactory<?>> getModelToCSVBinderFactoryRegistry()
    {
        return modelToCSVBinderFactoryRegistry;
    }
    
}