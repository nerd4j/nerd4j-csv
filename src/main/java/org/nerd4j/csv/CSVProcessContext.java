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
package org.nerd4j.csv;


/**
 * Represents the CSV processing execution context.
 * 
 * <p>
 * This context is used both during reading of a
 * CSV source and during writing to a CSV target.
 * 
 * <p>
 * This context keeps some information like:
 * <ul>
 *  <li>The index of the row currently processed.</li>
 *  <li>The index of the column currently processed.</li>
 *  <li>If an error has occurred processing the current position.</li>
 * </ul>
 * 
 * @author Nerd4j Team
 */
public interface CSVProcessContext
{

    /**
     * Returns the index of the row currently processed.
     * 
     * @return the index of the row currently processed.
     */
    public int getRowIndex();
    
    /**
     * Returns the index of the column currently processed.
     * 
     * @return the index of the column currently processed.
     */
    public int getColumnIndex();
    
    /**
     * Tells if an error has occurred during execution.
     *  
     * @return {@code true} if an error has occurred.
     */
    public boolean isError();
    
    /**
     * Returns the related error if the method {@link #isError()}
     *  
     * @return the error if occurred, {@code null} otherwise.
     */
    public CSVProcessError getError();
    
}