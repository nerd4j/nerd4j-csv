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
package org.nerd4j.csv.field;

import org.nerd4j.csv.CSVProcessOperation;



/**
 * Represents a converter able to convert the CSV field
 * in {@link String} into is object oriented representation
 * and vice versa.
 * 
 * @param <S> type of the source field format.
 * @param <T> type of the target field format.
 * 
 * @author Nerd4j Team
 */
public interface CSVFieldConverter<S,T> extends CSVProcessOperation
{
	
	/**
	 * Returns the source type accepted by this converter.
	 * 
	 * @return source type accepted by this converter.
	 */
	public Class<S> getSourceType();
	
	/**
	 * Returns the target type produced by this converter.
	 * 
	 * @return target type produced by this converter.
	 */
	public Class<T> getTargetType();

    /**
     * Applies the conversion of the field in source
     * format and returns the field in target format.
     * <p>
     *  If the conversion fails it modifies the context
     *  accordingly with the framework policies.
     * </p>
     * 
     * @param source the CSV field to convert.
     * @param context the field process execution context.
     * @return the CSV field in converted format.
     */
    public T convert( S source, CSVFieldProcessContext context );
    
}
