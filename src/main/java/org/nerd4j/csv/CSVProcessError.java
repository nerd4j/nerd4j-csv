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
 * Represents an error occurred during the execution
 * of a CSV processing.
 * 
 * <p>
 * This error is used both during reading of a
 * CSV source and during writing to a CSV target.
 * 
 * <p>
 * This error keeps some information like:
 * <ul>
 *  <li>The error message.</li>
 *  <li>The operation that caused the error.</li>
 * </ul>
 * 
 * @author Nerd4j Team
 */
public interface CSVProcessError
{
    
    /**
     * Returns the error message obtained filling the message pattern
     * with the required information.
     * 
     * @return the actual error message.
     */
    public String getMessage();
    
    /**
     * Returns the operation that caused the error.
     * 
     * @return the operation that caused the error.
     */
    public CSVProcessOperation getOperation();
    
}