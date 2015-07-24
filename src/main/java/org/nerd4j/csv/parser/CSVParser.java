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
package org.nerd4j.csv.parser;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

/**
 * <tt>CSVParser</tt> reads character data from a {@link Reader}, parsing and
 * tokenizing them into {@link CSVToken}.
 * 
 * <p>
 *  After usage it should be closed to permit resource release.
 * </p>
 * 
 * @author Nerd4j Team
 */
public interface CSVParser extends Closeable
{

    
	/**
	 * Returns last read {@link CSVToken}. If {@link #read()} wasn't called
	 * before {@code null} will be returned.
	 * 
	 * @return last read {@link CSVToken}.
	 */
	public CSVToken getCurrentToken();
	
	/**
	 * Returns last read field value. If {@link #read()} wasn't called before or
	 * {@link #getCurrentToken()} isn't {@link CSVToken#FIELD} {@code null} will
	 * be returned.
	 * 
	 * @return last read field value.
	 */
	public String getCurrentValue();
	
	/**
	 * Read the next {@link CSVToken} and returns it. The result
	 * {@link CSVToken} will be returned from {@link #getCurrentToken()} too.
	 * 
	 * @return next read {@link CSVToken}.
	 * @throws IOException if an error occurs while parsing data.
	 */
	public CSVToken read() throws IOException;
	
	/**
	 * Read the next {@link CSVToken} and returns it. The result
	 * {@link CSVToken} will be returned from {@link #getCurrentToken()} too.
	 * <p>
	 * The method {@link #getCurrentValue()} won't return any value.
	 * </p>
	 * 
	 * @return next read {@link CSVToken}.
	 * @throws IOException if an error occurs while parsing data.
	 */
	public CSVToken skip() throws IOException;
	
}