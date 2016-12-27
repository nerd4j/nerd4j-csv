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


/**
 * Represents an object able to build and fill
 * a data model using the fields of a related
 * CSV record.
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @author Nerd4j Team
 */
public interface CSVToModelBinder<M>
{

    /**
     * Creates a new empty model to be filled
     * with the given data by the method
     * {@link CSVToModelBinder#fill(int,Object)}.
     * <p>
     * If a model already exists will be overridden
     * with a new empty instance.
     * 
     * @throws CSVToModelBindingException if the initialization of the model fails.
     */
    public void initModel() throws CSVToModelBindingException;
    
    /**
     * Fills the model with the given value related
     * to the given column.
     * 
     * @param column related column.
     * @param value  value to fill with.
     * @throws CSVToModelBindingException if the operation fails for some reason.
     */
    public void fill( int column, Object value ) throws CSVToModelBindingException;
    
    /**
     * Returns the internal model instance.
     * <p>
     * If the model has never been initialized
     * returns {@code null}.
     * 
     * @return the internal model instance.
     */
    public M getModel();
    
}