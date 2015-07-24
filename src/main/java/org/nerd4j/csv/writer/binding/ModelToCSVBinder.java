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


/**
 * Represents an object able to parse the data model
 * and provide the fields of the CSV record in the
 * right order.
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @author Nerd4j Team
 */
public interface ModelToCSVBinder<M>
{

    /**
     * Sets the next model instance to be parsed.
     *  
     * @param model the next model instance to be parsed.
     */
    public void setModel( M model );
    
    /**
     * Returns the size of the CSV record to write.
     *  
     * @return the size of the CSV record to write.
     */
    public int getRecordSize();
    
    /**
     * Returns the model value corresponding to the given CSV column.
     * 
     * @param  column related column.
     * @return the model value to use to fill the column.
     * @throws ModelToCSVBindingException if the operation fails for some reason.
     */
    public Object getValue( int column ) throws ModelToCSVBindingException;
    
}