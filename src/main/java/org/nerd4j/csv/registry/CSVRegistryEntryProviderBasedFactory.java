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
 *  This class keeps a reference to a registry entry provider
 *  and to the related parameters and is able to create a
 *  new {@link CSVRegistryEntry} when needed.
 * </p>
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
