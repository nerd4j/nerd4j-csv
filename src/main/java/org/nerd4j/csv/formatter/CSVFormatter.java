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
package org.nerd4j.csv.formatter;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;


/**
 * <tt>CSVFormatter</tt> writes character data from a {@link String}, parsing and
 * quoting if needed and writing into a {@link Writer}.
 * 
 * <p>
 *  After use it should be closed to permit resource release.
 * </p>
 * 
 * <h3>Synchronization</h3>
 *
 * <p>
 *  CSV formatters are not synchronized.
 *  It is recommended to create separate CSV formatter instances for each thread.
 *  If multiple threads access a CSV formatter concurrently, it must be synchronized
 *  externally.
 * </p>
 * 
 * @author Nerd4j Team
 */
public interface CSVFormatter extends Closeable, Flushable
{
	
	/**
	 * Writes a field.
	 * 
	 * @param source  data source
	 * @throws IOException if any error occur while writing.
	 */
	public void writeField( String source ) throws IOException;

	/**
	 * Writes a field.
	 * 
	 * @param source data source
	 * @param quote  tells to quote the field even if not needed.
	 * @throws IOException if any error occur while writing.
	 */
	public void writeField( String source, boolean quote ) throws IOException;
	
	/**
	 * Write an end of record.
	 * 
	 * @throws IOException if any error occur while writing.
	 */
	public void writeEOR() throws IOException;
	
	/**
	 * Write an end of data.
	 * 
	 * @throws IOException if any error occur while writing.
	 */
	public void writeEOD() throws IOException;
	
}