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

import java.util.Map;


/**
 * Represents a provider able to create an instance
 * of a CSV component registry entry.
 * 
 * <p>
 *  This class is intended to be used during configuration parsing.
 *  Configuration are expressed by string values, so the provider
 *  has the aim to properly convert the string parameters into the
 *  requested values.
 * </p>
 * 
 * @param <Entry> type of the entry to provide.
 * 
 * @author Nerd4j Team
 */
public interface CSVRegistryEntryProvider<Entry>
{

    /**
     * Creates the entries returned by the registry.
     *  
     * @param params map of parameters to use to provide the entry.
     * @return a new registry entry instance.
     */
    public Entry get( Map<String,String> params );
    
}
