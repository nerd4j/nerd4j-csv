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
import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Maps a JavaBean property to a CSV column.
 * 
 * <h3>Usage</h3>
 * <p>
 * The {@link CSVColumn} annotation can be used with the
 * following program elements: 
 * <ul> 
 *  <li> JavaBean property (getter);</li>
 *  <li> JavaBean field.</li>
 * </ul>
 * 
 * <p>
 * This annotation is equivalent to the XML configuration <tt>csv:column</tt>.
 * 
 * @author Nerd4j Team
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface CSVColumn
{

	/**
	 * The name of the CSV column reported
	 * in the CSV source header if any, or
	 * the column index if no header is defined.
	 * 
	 * @return the column name in the header or the column index.
	 */
	public String name() default "";
	
	/**
	 * Tells if the this column is optional or mandatory
	 * in the output data model.
	 * <p>
	 * An optional column means that the related data model
	 * accepts {@code null} values.
	 * <p>
	 * The default value for this field is {@code null}.
	 * 
	 * @return {@code true} if the column value  is optional.
	 */
	public boolean optional() default false;
	
	/**
	 * This configuration is used only by the {@link CSVWriter}
	 * and tells the order in which the columns should be written.
	 * <p>
	 * If two or more columns have the same order value the framework
	 * chooses the related order depending on the implementation.
	 * 
	 * @return the order of the column.
	 */
	public int order() default Integer.MAX_VALUE;
	
	/**
	 * The name used to refer to a previous configured field processor
	 * and use it during reading process.
	 * 
	 * @return name of the referenced processor.
	 */
	public String readProcessorRef() default "";
	

	/**
	 * Definition of the processor to use to perform the conversion
	 * between the CSV column value and the data model field
	 * during reading process.
	 *  
	 * @return CSV field processor definition.
	 */
	public CSVFieldProcessor[] readProcessor() default {};

	/**
	 * The name used to refer to a previous configured field processor
	 * and use it during writing process.
	 * 
	 * @return name of the referenced processor.
	 */
	public String writeProcessorRef() default "";
	
	
	/**
	 * Definition of the processor to use to perform the conversion
	 * between the CSV column value and the data model field
	 * during writing process.
	 *  
	 * @return CSV field processor definition.
	 */
	public CSVFieldProcessor[] writeProcessor() default {};
	
}
