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
 * Creates a {@link org.nerd4j.csv.writer.CSVWriter CSVWriter} using the
 * annotated JavaBean to retrieve the required configurations.
 * 
 * <p> <b>Usage</b> </p>
 * <p>
 *  The {@link CSVWriter} annotation can be used with the
 *  following program elements: 
 * <ul> 
 *   <li> JavaBean class.</li>
 * </ul>
 * </p>
 * 
 * <p>
 *   This annotation is equivalent to the XML configuration <tt>csv:reader</tt>.
 * </p>
 * 
 * @author Nerd4j Team
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVWriter
{
	
	/**
	 * Tells the {@link org.nerd4j.csv.writer.CSVWriter CSVWriter}
	 * to write the first row of the CSV destination as a CSV header.
	 * 
	 * @return <code>true</code> if the CSV header has to be written.
	 */
	public boolean writeHeader() default true;

	/**
	 * Configures a custom {@link org.nerd4j.csv.formatter.CSVFormatter CSVFormatter}
	 * for the current {@link org.nerd4j.csv.writer.CSVWriter CSVWriter}.
	 * 
	 * @return custom {@link org.nerd4j.csv.formatter.CSVFormatter CSVFormatter} configuration.
	 */
	public CSVFormatter[] formatter() default {};
	
}