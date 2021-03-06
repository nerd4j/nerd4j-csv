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
