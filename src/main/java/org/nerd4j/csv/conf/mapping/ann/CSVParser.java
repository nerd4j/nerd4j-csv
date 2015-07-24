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
 * Creates a {@link CSVParser} using the annotated
 * JavaBean to retrieve the required configurations.
 * 
 * <p> <b>Usage</b> </p>
 * <p>
 *  The {@link CSVParser} annotation is intended
 *  to be used inside the following annotations:
 *  <ul>
 *   <li>{@link CSVReader}.</ul>
 *  </ul>
 * </p>
 * 
 * <p>
 *   This annotation is equivalent to the XML configuration <tt>csv:parser</tt>.
 * </p>
 * 
 * @author Nerd4j Team
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface CSVParser
{
	
	/**
	 * Character used to quote fields.
	 * <p>
	 *  The character used by default is the double quote ["].
	 * </p>
	 * @return character used to quote fields.
	 */
	public char quote();
	
	/**
     * Tells if to treat CSV quotes less strictly.
     * More precisely tells the parser not to fail
     * in case a not escaped quote is found
     * into a field.
     */
    public boolean lazyQuotes() default false;

	/**
	 * Character used to escape other characters.
	 * <p>
	 *  There is no default values for this feature.
	 * </p>
	 * @return character used to escape other characters.
	 */
	public char escape() default RemarkableASCII.NOT_AN_ASCII;

	/**
	 * Character used to separate fields in the CSV source.
	 * <p>
	 *  The character used by default is the comma [,].
	 * </p>
	 * @return character used to separate fields.
	 */
	public char fieldSep();
	
	/**
	 * The first of at most two record separator characters.
     * This character is mandatory, the second one is optional.
	 * <p>
	 *  The character used by default is the new line feed [\n].
	 * </p>
	 * @return first character used to separate records.
	 */
	public char recordSep1();

	/**
	 * The second of at most two record separator characters.
     * This character is optional, the first one is mandatory.
	 * <p>
	 *  There is no default values for this feature.
	 * </p>
	 * @return second character used to separate records.
	 */
	public char recordSep2() default RemarkableASCII.NOT_AN_ASCII;
	
	/**
	 * Set of characters to be completely ignored while parsing.
	 * <p>
	 *  There is no default values for this feature.
	 * </p>
	 * @return set of characters to ignore.
	 */
	public char[] charsToIgnore() default { RemarkableASCII.NOT_AN_ASCII };
	
	/**
	 * Set of characters to be ignored if found
     * on heading or trailing of a field.
	 * <p>
	 *  The characters ignored by default are:
	 *  <ul>
	 *   <li>space [ ];</li>
	 *   <li>horizontal tab [\t];</li>
	 *   <li>new line feed [\n].</li>
	 *  </ul>
	 * </p>
	 * @return set of characters to ignore around fields.
	 */
	public char[] charsToIgnoreAroundFields() default { RemarkableASCII.NOT_AN_ASCII };
	
}