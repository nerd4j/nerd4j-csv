/*
 * #%L
 * Nerd4j CSV
 * %%
 * Copyright (C) 2013 - 2016 Nerd4j
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

import java.util.Collections;
import java.util.Map;

import org.nerd4j.csv.exception.CSVConfigurationException;

/**
 * Factory able to properly build a new
 * {@link CSVRegistryEntry} of the given
 * type, using the related entry provider.
 * 
 * <p>
 * This class keeps a reference to a registry entry provider
 * and to the related parameters and is able to create a
 * new {@link CSVRegistryEntry} when needed.
 * 
 * @param <Entry> type of the entry to represent.
 * 
 * @author Nerd4j Team
 */
public class CSVRegistryEntryProviderBasedFactory<Entry extends CSVRegistryEntry>
       implements CSVRegistryEntryFactory<Entry>
{
	

	/** Provider of the {@link CSVRegistryEntry} to build. */
	private final CSVRegistryEntryProvider<Entry> provider;
	
	/** Map of parameters needed by the provider. */ 
	private final Map<String,String> params;
	
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param provider a provider able to build a objects of type {@link Entry}.
	 * @param params   map of parameters needed by the provider (optional).
	 */
	public CSVRegistryEntryProviderBasedFactory( CSVRegistryEntryProvider<Entry> provider, Map<String,String> params )
	{
		
		super();
		
		if( provider == null )
			throw new CSVConfigurationException( "The CSV field converter provider is mandatory." );
		
		this.provider = provider;
		
		if( params != null )
			this.params = params;
		else
			this.params = Collections.emptyMap();
		
		/* Calls the validation method of the provider. */
		this.validate();
				
	}
	
	
	/* *************************** */
	/* ***  INTERFACE METHODS  *** */
	/* *************************** */
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Entry create()
	{
		
		return provider.get( params );
		
	}
	
	
	/* ************************* */
	/* ***  PRIVATE METHODS  *** */
	/* ************************* */
	
	
	/**
	 * Calls the validation method of the {@link CSVRegistryEntryProvider}
	 * class to validate the related parameters.
	 * 
	 * @throws CSVConfigurationException if the parameters are not valid.
	 */
	private void validate()
	{
		
		provider.validate( params );
		
	}
	
}
