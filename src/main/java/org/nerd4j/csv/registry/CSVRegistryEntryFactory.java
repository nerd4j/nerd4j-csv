package org.nerd4j.csv.registry;


/**
 * Represents a factory able to build
 * entries which configuration can be
 * defined and stored into the
 * {@link CSVRegistry}.
 * 
 * @author Nerd4j Team
 */
public interface CSVRegistryEntryFactory<Entry extends CSVRegistryEntry>
{
	
	/**
	 * The entry of the {@link CSVRegistry} which configuration
	 * is defined into the factory.
	 * 
	 * @return a properly configured {@link CSVRegistry} entry.
	 */
	public Entry create();

}
