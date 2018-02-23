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
package org.nerd4j.csv.reader;

import org.nerd4j.csv.CSVProcessContext;
import org.nerd4j.csv.CSVProcessOutcome;


/**
 * Represents the outcome of a CSV read operation performed
 * by the {@link CSVReader#read()} method.
 * 
 * <p>
 * This object contains the following elements:
 * <ol>
 *  <li>The data model corresponding to the CSV record read;</li>
 *  <li>The execution context of the reading operation.</li>
 * </ol>
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @author Nerd4j Team
 */
public interface CSVReadOutcome<M> extends CSVProcessOutcome<M>
{
    
    /**
     * Returns the reading execution context.
     * <p>
     * This object contains some information about the progress
     * of the reading.
     * <p>
     * <b>IMPORTANT</b> for performance reasons there is only one
     *                  instance of the execution context. So the
     *                  internal values of this object may vary
     *                  during execution.
     *                  
     * @return the reading execution context.
     * @deprecated use {@link CSVProcessOutcome#getCSVProcessContext()} instead.
     */
	@Deprecated
    public CSVProcessContext getCSVReadingContext();
           
}
