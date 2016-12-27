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
 * Performs validation over the values of a CSV column
 * or the related data model field.
 * 
 * <h3>Usage</h3>
 * <p>
 * The {@link CSVFieldValidator} annotation is intended
 * to be used inside a {@link CSVFieldProcessor} annotation.
 * 
 * <p>
 * This annotation is equivalent to the XML configuration <tt>csv:validator</tt>.
 * 
 * <p>
 * The built in validations types are:
 * <ul>
 *  <li>checkStringLength  : length   = the actual length of the string (mandatory)</li>
 *  <li>checkStringLength  : min, max = the minimum and maximum length of the string (mandatory)</li>
 *  <li>checkRegEx         : pattern  = the regular expression pattern to match (mandatory)</li>
 * </ul>
 * 
 * @author Nerd4j Team
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface CSVFieldValidator
{

	/**
	 * The name used to identify the validation type to use.
	 * 
	 * @return name of the validation type to use.
	 */
	public String type() default "";
	
	/**
	 * Array of parameters used to configure the validator.
	 * 
	 * @return array of parameters used to configure the validator.
	 */
	public CSVParam[] params() default {};
	
}
