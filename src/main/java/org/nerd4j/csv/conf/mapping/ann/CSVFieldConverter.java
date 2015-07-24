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
 * Performs type conversion between the value
 * of a CSV column and the related data model
 * field and vice versa.
 * 
 * <p> <b>Usage</b> </p>
 * <p>
 *  The {@link CSVFieldConverter} annotation is intended
 *  to be used inside a {@link CSVFieldProcessor} annotation.
 * </p>
 * 
 * <p>
 *   This annotation is equivalent to the XML configuration <tt>csv:converter</tt>.
 * </p>
 * 
 * <p>
 *  The built in validations types are:
 *  <ul>
 *    <li>parseByte          : no parameters</li>
 *    <li>parseShort         : no parameters</li>
 *    <li>parseInteger       : no parameters</li>
 *    <li>parseLong          : no parameters</li>
 *    <li>parseFloat         : no parameters</li>
 *    <li>parseDouble        : no parameters</li>
 *    <li>parseBigInteger    : no parameters</li>
 *    <li>parseBigDecimal    : no parameters</li>
 *    <li>parseAtomicInteger : no parameters</li>
 *    <li>parseAtomicLong    : no parameters</li>
 *    <br />
 *    <li>formatByte         : patter = the number format pattern (optional)</li>
 *    <li>formatShort        : patter = the number format pattern (optional)</li>
 *    <li>formatInteger      : patter = the number format pattern (optional)</li>
 *    <li>formatLong         : patter = the number format pattern (optional)</li>
 *    <li>formatFloat        : patter = the number format pattern (optional)</li>
 *    <li>formatDouble       : patter = the number format pattern (optional)</li>
 *    <li>formatBigInteger   : patter = the number format pattern (optional)</li>
 *    <li>formatBigDecimal   : patter = the number format pattern (optional)</li>
 *    <li>formatAtomicInteger: patter = the number format pattern (optional)</li>
 *    <li>formatAtomicLong   : patter = the number format pattern (optional)</li>
 *    <br />
 *    <li>parseBoolean       : no parameters</li>
 *    <li>formatBoolean      : no parameters</li>
 *    <br />
 *    <li>parseDate          : patter = the date format pattern (mandatory)</li>
 *    <li>formatDate         : patter = the date format pattern (mandatory)</li>
 *    <br />
 *    <li>parseEnum          : enum-type = the full qualified enum class name (mandatory)</li>
 *    <li>formatEnum         : enum-type = the full qualified enum class name (mandatory)</li>
 *  </ul>
 * </p>
 * 
 * @author Nerd4j Team
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface CSVFieldConverter
{

	/**
	 * The name used to identify the conversion type to use.
	 * 
	 * @return name of the conversion type to use.
	 */
	public String type() default "";
	
	/**
	 * Array of parameters used to configure the converter.
	 * 
	 * @return array of parameters used to configure the converter.
	 */
	public CSVParam[] params() default {};
	
}
