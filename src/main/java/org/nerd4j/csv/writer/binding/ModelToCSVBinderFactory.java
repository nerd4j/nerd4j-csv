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
package org.nerd4j.csv.writer.binding;

import org.nerd4j.csv.exception.ModelToCSVBindingException;
import org.nerd4j.csv.registry.CSVRegistryEntry;
import org.nerd4j.csv.writer.CSVWriterMetadata;


/**
 * Represents a {@code Factory} able to build and configure
 * {@link ModelToCSVBinder}s which reads data models and provides
 * CSV record fields in the right order.
 * 
 * @param <Model> type of the model parsed by the binder.
 * 
 * @author Nerd4j Team
 */
public interface ModelToCSVBinderFactory<Model> extends CSVRegistryEntry
{
    
    /**
     * Returns a new CSV model binder which parses the data
     * model and returns the CSV fields in the required order.
     * 
     * @param configuration used to configure the binding.
     * @param columnIds     which columns to use and in which order.
     * @return a new CSV model binder.
     * @throws ModelToCSVBindingException if binding configuration is inconsistent.
     */
    public ModelToCSVBinder<Model> getModelToCSVBinder( final CSVWriterMetadata<Model> configuration,
    		                                            final String[] columnIds )
    throws ModelToCSVBindingException;
    
}