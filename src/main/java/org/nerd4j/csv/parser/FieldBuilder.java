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

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Mimic {@link StringBuilder} functionality adding mark and reset capabilities
 * similar to {@link Reader} ones. See {@link #mark()} javadoc for more details.
 * 
 * @author Nerd4j Team
 */
interface FieldBuilder
{
	
	/**
	 * Append a new character at the end of the content.
	 * 
	 * @param character new character to append.
	 */
	public void append( char character );
	
	/**
	 * Append a new character at the end of the content.
	 * 
	 * @param character new character to append.
	 */
	public void append( int character );
	
	/**
	 * Returns the length of current content
	 * 
	 * @return length of current content.
	 */
	public int length();
	
	/** Clear current content. */
	public void clear();
	
	/**
	 * Returns the marked position if any.
	 *  
	 * @return the current marked position or {@code -1}.
	 */
	public int getMarkedPosition();
	
	/**
	 * Mark current position.
	 * <p>
	 * After a {@link #reset()} call the {@link FieldBuilder} will return at
	 * the same position. Appending ({@link #append(char)}) more than a
	 * character implicitly clear the marker an no return will be possibile
	 * anymore (similar to {@link Reader#mark(int)} with value 1 supported
	 * only).
	 * </p>
	 * <p>
	 * Consecutive {@link #mark()} calls will extends marker space; they
	 * won't reset it. For example the next {@link FieldBuilder} will
	 * contain the "A" string at procedure end:
	 * 
	 * <pre>
	 * builder.append('A');
	 * builder.mark();
	 * builder.append('B');
	 * builder.mark();
	 * builder.append('C');
	 * builder.reset();
	 * </pre>
	 * 
	 * Differently next one will contain "ABC":
	 * 
	 * <pre>
	 * builder.append('A');
	 * builder.mark();
	 * builder.append('B');
	 * builder.append('C');
	 * builder.reset();
	 * </pre>
	 * 
	 * </p>
	 */
	public void mark();
	
	/**
	 * Reset content state to last mark if possible;
	 * see {@link #mark()} javadoc for details.
	 */
	public void rollbackToMark();
	
	/**
	 * Returns current {@link FieldBuilder} content.
	 * 
	 * @return the content.
	 */
	@Override
	public String toString();
	
	/**
	 * Returns a portion of current {@link FieldBuilder} content.
	 * 
	 * @param offset position of first wanted character.
	 * @param length number of character needed.
	 * @return a portion of the content.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if offset or length parameters are outside builder current
	 *             limits.
	 */
	public String toString( int offset, int length );
	
	/**
	 * Write current {@link FieldBuilder} content on given {@link Writer}.
	 * 
	 * @param writer writer where write content.
	 * 
	 * @throws IOException if any error occur while writing.
	 */
	public void writeTo( Writer writer ) throws IOException;
	
	/**
	 * Write a portion of current {@link FieldBuilder} content on given
	 * {@link Writer}.
	 * 
	 * @param writer writer where write content.
	 * @param offset position of first wanted character.
	 * @param length number of character needed.
	 * 
	 * @throws IOException if any error occur while writing.
	 */
	public void writeTo( Writer writer, int offset, int length ) throws IOException;
	
}