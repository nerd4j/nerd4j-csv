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
package org.nerd4j.csv.reader.binding;

import org.nerd4j.csv.exception.CSVToModelBindingException;
import org.nerd4j.csv.reader.CSVReaderMetadata;




/**
 * Represents a <code>Factory</code> able to build and configure
 * {@link CSVToModelBinder}s which populates data models of the given type.
 * 
 * @param <Model> type of the model returned by the builder.
 * 
 * @author Nerd4j Team
 */
public interface CSVToModelBinderFactory<Model>
{
    
    /**
     * Returns a new CSV model binder which builds
     * and fills the data model objects of the
     * specified type.
     * 
     * @param configuration used to configure the binding.
     * @param columnMapping used to map columns with model.
     * @return a new CSV model binder.
     * @throws CSVToModelBindingException if binding configuration is inconsistent.
     */
    public CSVToModelBinder<Model> getCSVToModelBinder( final CSVReaderMetadata<Model> configuration,
                                                        final Integer[] columnMapping )
    throws CSVToModelBindingException;
    
}