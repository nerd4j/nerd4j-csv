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
package org.nerd4j.csv.writer;

import org.nerd4j.csv.CSVProcessContext;


/**
 * Represents the outcome of a CSV write operation performed
 * by the {@link CSVWriter#write(Object)} method.
 * 
 * <p>
 *  This object contains the following elements:
 *  <ol>
 *   <li>The execution context of the writing operation.</li>
 *  </ol>
 * </p>
 * 
 * @author Nerd4j Team
 */
public interface CSVWriteOutcome
{
    
    /**
     * Returns the writing execution context.
     * <p>
     *  This object contains some information about the progress
     *  of the writing.
     * </p>
     * <p>
     *  <b>IMPORTANT</b> for performance reasons there is only one
     *                   instance of the execution context. So the
     *                   internal values of this object may vary
     *                   during execution.
     * </p>
     * @return the writing execution context.
     */
    public CSVProcessContext getCSVWritingContext();
    
}
