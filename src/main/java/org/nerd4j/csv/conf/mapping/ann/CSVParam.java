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

import org.nerd4j.csv.field.CSVFieldConverter;


/**
 * Represents a custom parameter in the configuration
 * of a CSV handling element.
 * 
 * <p> <b>Usage</b> </p>
 * <p>
 *  The {@link CSVParam} annotation is intended
 *  to be used inside the following annotations:
 *  <ul>
 *   <li>{@link CSVFieldValidator};</ul>
 *   <li>{@link CSVFieldConverter};</ul>
 *  </ul>
 * </p>
 * 
 * @author Nerd4j Team
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface CSVParam
{

	/**
	 * The name the name of the parameter.
	 * 
	 * @return the name of the parameter.
	 */
	public String type();
	
	/**
	 * The value of the parameter.
	 * 
	 * @return the value of the parameter.
	 */
	public String value();
	
}
