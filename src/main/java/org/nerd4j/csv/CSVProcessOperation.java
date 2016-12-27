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
 * Represents an object able to perform an atomic
 * operation over a CSV field.
 * 
 * <p>
 * This interface is intended to be of help
 * for the {@link CSVProcessError}.
 * 
 * <p>
 * The error pattern returned by this interface
 * method must be according with the use made
 * by the {@link CSVProcessError}.
 * 
 * @author Nerd4j Team
 */
public interface CSVProcessOperation
{
    
    /**
     * Returns a message pattern as described in
     * the {@link java.text.MessageFormat} class.
     * <p>
     * The placeholders in this pattern coincide
     * with fields of the {@link org.nerd4j.csv.field.CSVFieldProcessContext CSVFieldProcessContext}
     * as follows:
     * <ul>
     *  <li>{0} = columnName;</li>
     *  <li>{1} = originalValue;</li>
     *  <li>{2} = convertedValue.</li>
     * </ul> 
     * 
     * @return the pattern to use to build the error message.
     */
    public String getErrorMessagePattern();

}
