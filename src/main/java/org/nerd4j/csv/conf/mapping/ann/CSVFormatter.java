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

import org.nerd4j.csv.RemarkableASCII;


/**
 * Creates a {@link CSVFormatter} using the annotated
 * JavaBean to retrieve the required configurations.
 * 
 * <h3>Usage</h3>
 * <p>
 * The {@link CSVFormatter} annotation is intended
 * to be used inside the following annotations:
 * <ul>
 *  <li>{@link CSVWriter}.</li>
 * </ul>
 * 
 * <p>
 * This annotation is equivalent to the XML configuration <tt>csv:formatter</tt>.
 * 
 * @author Nerd4j Team
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface CSVFormatter
{
	
	/**
	 * Character used to quote fields.
	 * <p>
	 * The character used by default is the double quote ["].
	 * 
	 * @return character used to quote fields.
	 */
	public char quote();

	/**
	 * Character used to escape other characters.
	 * <p>
	 * There is no default values for this feature.
	 * 
	 * @return character used to escape other characters.
	 */
	public char escape() default RemarkableASCII.NOT_AN_ASCII;

	/**
	 * Character used to separate fields in the CSV source.
	 * <p>
	 * The character used by default is the comma [,].
	 * 
	 * @return character used to separate fields.
	 */
	public char fieldSep();
	
	/**
     * Characters used to separate records in the CSV source.
     * <p>
     * By default the operating system line separator will be used.
     * 
     * @return the character used as record separator.
     */
    public char[] recordSep() default { RemarkableASCII.NOT_AN_ASCII };
    
	/**
	 * Set of characters to be escaped during formatting.
	 * <p>
	 * There is no default values for this feature.
	 *  
	 * @return set of characters to be escaped.
	 */
	public char[] charsToEscape() default { RemarkableASCII.NOT_AN_ASCII };
	
	/**
	 * Set of characters that forces the whole field to be quoted.
	 * <p>
	 * The characters configured by default are:
	 * <ul>
	 *  <li>space [ ];</li>
	 *  <li>comma [,];</li>
	 *  <li>horizontal tab [\t];</li>
	 *  <li>new line feed [\n].</li>
	 * </ul>
	 * 
	 * @return set of characters to ignore around fields.
	 */
	public char[] charsThatForceQuoting() default { RemarkableASCII.NOT_AN_ASCII };
	
}