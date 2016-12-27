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

import java.util.HashMap;
import java.util.Map;

import org.nerd4j.csv.exception.CSVConfigurationException;


/**
 * Abstract implementation of a registry containing
 * the providers able to understand the configured
 * values and provide the related objects.
 * 
 * <p>
 * Currently the available implementations are:
 * <ul>
 *  <li>{@link CSVFieldValidatorRegistry}</li>
 *  <li>{@link CSVFieldConverterRegistry}</li>
 *  <li>{@link CSVFieldProcessorRegistry}</li>
 *  <li>{@link CSVToModelBinderFactoryRegistry}</li>
 *  <li>{@link ModelToCSVBinderFactoryRegistry}</li>
 * </ul>
 * 
 * @param <Entry> type of the entry to build.
 * 
 * @author Nerd4j Team
 */
public abstract class CSVAbstractRegistry<Entry extends CSVRegistryEntry>
{

	/**
	 * The internal map used to register the factories
	 * able to build register entries. */
	private final Map<String,CSVRegistryEntryFactory<Entry>> factoryRegistry;
	
    /** The internal map used to register the entry providers. */
    private final Map<String,CSVRegistryEntryProvider<Entry>> providerRegistry;
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVAbstractRegistry()
    {
        
        super();
        
        this.factoryRegistry = new HashMap<>();
        this.providerRegistry = new HashMap<>();
        
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */

    
    /**
     * Returns the factory associated to the given key
     * if any, otherwise returns {@code null}.
     * 
     * @param name name to use to reference the factory.
     * @return the related register entry factory.
     */
    public CSVRegistryEntryFactory<Entry> getFactory( String name )
    {
        
        final CSVRegistryEntryFactory<Entry> factory = factoryRegistry.get( name );
        if( factory == null )
            throw new CSVConfigurationException( "There is no entry factory registred with name " + name );
        
        return factory;
        
    }
    
    /**
     * Sets the given factory in relation to the given name.
     * If the name already exists the related factory will be
     * overwritten.
     * 
     * @param name     name to use to reference the factory.
     * @param factory  the actual factory.
     */
    public void setFactory( String name, CSVRegistryEntryFactory<Entry> factory )
    {
    	
    	factoryRegistry.put( name, factory );
    	
    }
    
    /**
     * Merges the internal registry with the given map.
     * If some keys already exist the related factories
     * will be overwritten.
     * 
     * @param factories the factories to set.
     */
    public void setFactories( Map<String, CSVRegistryEntryFactory<Entry>> factories )
    {
    	
    	factoryRegistry.putAll( factories );
    	
    }
    
    /**
     * Returns the provider associated to the given key
     * if any, otherwise returns {@code null}.
     * 
     * @param name    name to use to reference the provider.
     * @return the provider associated to the given key.
     */
    public CSVRegistryEntryProvider<Entry> getProvider( String name )
    {
        
        final CSVRegistryEntryProvider<Entry> provider = providerRegistry.get( name );
        if( provider == null )
            throw new CSVConfigurationException( "There is no provider registred with name " + name );
        
        return provider;
        
    }
    
    /**
     * Sets the given provider in relation to the given name.
     * If the name already exists the related provider will be
     * overwritten.
     * 
     * @param name    name to use to reference the provider.
     * @param provider the actual provider.
     */
    public void setProvider( String name, CSVRegistryEntryProvider<Entry> provider )
    {
        
    	providerRegistry.put( name, provider );
        
    }
    
    /**
     * Merges the internal registry with the given map.
     * If some keys already exist the related providers
     * will be overwritten.
     * 
     * @param providers the providers to set.
     */
    public void setProviders( Map<String,CSVRegistryEntryProvider<Entry>> providers )
    {
        
    	providerRegistry.putAll( providers );
        
    }
    
    /**
     * Searches in the registry the provider with the given name and
     * returns a new {@link CSVRegistryEntryFactory} configured with
     * the given provider and parameters.
     * 
     * @param name   name of the provider.
     * @param params requested parameters.
     * @return the related {@link CSVRegistryEntryFactory} .
     * @throws CSVConfigurationException if no provider are registered
     *                                   with the given name.
     */
    public CSVRegistryEntryFactory<Entry> provideFactory( String name, Map<String,String> params )
    throws CSVConfigurationException
    {
    	
    	return new CSVRegistryEntryProviderBasedFactory<Entry>( getProvider(name), params );
    	
    }
    
}