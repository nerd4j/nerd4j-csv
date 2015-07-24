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
 * Represents the processor able to perform the validations
 * and conversions needed to translate a CSV column value
 * into the related data model field and vice versa.
 * 
 * <p> <b>Usage</b> </p>
 * <p>
 *  The {@link CSVFieldProcessor} annotation is intended
 *  to be used inside a {@link CSVColumn} annotation.
 * </p>
 * 
 * <p>
 *   This annotation is equivalent to the XML configuration <tt>csv:processor</tt>.
 * </p>
 * 
 * @author Nerd4j Team
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface CSVFieldProcessor
{

	/**
	 * The name used to register a previous configured field validator
	 * used to check a specific condition before to perform conversion.
	 * 
	 * @return name of the referenced precondition validator.
	 */
	public String preconditionRef() default "";
	
	/**
	 * The name used to register a previous configured field converter
	 * used to actually perform the conversion.
	 * 
	 * @return name of the referenced converter.
	 */
	public String converterRef() default "";
	
	/**
	 * The name used to register a previous configured field validator
	 * used to check a specific condition after the conversion occurs.
	 * 
	 * @return name of the referenced postcondition validator.
	 */
	public String postconditionRef() default "";
	
	/**
	 * The configuration of the validator
	 * used to check a specific condition
	 * before to perform conversion.
	 * 
	 * @return configuration of the precondition validator.
	 */
	public CSVFieldValidator[] precondition() default {};
	
	/**
	 * Configuration of the converter
	 * used to actually perform the conversion.
	 * 
	 * @return configuration of the converter.
	 */
	public CSVFieldConverter[] converter() default {};
	
	/**
	 * The configuration of the validator
	 * used to check a specific condition
	 * after the conversion occurs.
	 * 
	 * @return configuration of the postcondition validator.
	 */
	public CSVFieldValidator[] postcondition() default {};
	
}