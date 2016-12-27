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
package org.nerd4j.csv.conf.mapping.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Creates a {@link org.nerd4j.csv.reader.CSVReader CSVReader} using the
 * annotated JavaBean to retrieve the required configurations.
 * 
 * <h3>Usage</h3>
 * <p>
 * The {@link CSVReader} annotation can be used with the
 * following program elements: 
 * <ul> 
 *  <li> JavaBean class.</li>
 * </ul>
 * 
 * <p>
 *  This annotation is equivalent to the XML configuration <tt>csv:reader</tt>.
 * 
 * @author Nerd4j Team
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVReader
{
	
	/**
	 * Tells the {@link org.nerd4j.csv.reader.CSVReader CSVReader} to read
	 * the first row of the CSV source
	 * and use it as a CSV header.
	 * 
	 * @return {@code true} if the CSV header has to be read.
	 */
	public boolean readHeader() default true;
	
	/**
	 * Tells the {@link org.nerd4j.csv.reader.CSVReader CSVReader} to use
	 * the read CSV header to link each
	 * column to the configured name.
	 * <p>
	 *  This parameter can be {@code true}
	 *  only if the 'readHeader' is also {@code true},
	 *  otherwise the configuration is inconsistent.
	 * 
	 * @return {@code true} if the column names
	 *         has to be used instead of the column indexes.
	 */
	public boolean useColumnNames() default true;
	
    /**
     * In the CSV standard each record must have the same number of cells.
     * But it is possible to face non standard CSV files where records
     * have different lengths.
     * <p>
     * This flag tells the {@link org.nerd4j.csv.reader.CSVReader CSVReader}
     * to accept such non standard files. In this case the reader may return
     * incomplete data models.
     * 
     * @return {@code true} if the reader accepts incomplete records.
     */
	public boolean acceptIncompleteRecords() default false;

	/**
	 * Configures a custom {@link org.nerd4j.csv.parser.CSVParser CSVParser}
	 * for the current {@link org.nerd4j.csv.reader.CSVReader CSVReader}.
	 * 
	 * @return custom {@link org.nerd4j.csv.parser.CSVParser CSVParser} configuration.
	 */
	public CSVParser[] parser() default {};
	
}