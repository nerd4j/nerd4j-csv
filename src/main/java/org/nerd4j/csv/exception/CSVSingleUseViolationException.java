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
package org.nerd4j.csv.exception;


/**
 * This {@link CSVException} will occur if the method
 * {@link org.nerd4j.csv.reader.CSVReader#iterator() CSVReader#iterator()},
 * {@link org.nerd4j.csv.reader.CSVReader#spliterator() CSVReader#spliterator()} or
 * {@link org.nerd4j.csv.reader.CSVReader#stream() CSVReader#stream()}
 * will be invoked more than once on the same {@link org.nerd4j.csv.reader.CSVReader CSVReader}.
 * 
 * <p>
 * Since the CSV source can be anything: a string, a file but also a remote service response,
 * the {@link org.nerd4j.csv.reader.CSVReader CSVReader} is a single-use object and so are also
 * the related {@code Iterator} and {@code Stream}.
 * 
 * <p>
 * If the method {@link org.nerd4j.csv.reader.CSVReader#stream() CSVReader#stream()} is invoked
 * twice the second time will return an empty {@link java.util.stream.Stream}. This behavior is
 * not expected and can lead to issues tricky to be found. Therefore this exception will be throw
 * if those methods are invoked more than once.
 *  
 * @since 1.2.0
 * 
 * @author Nerd4j Team
 */
public class CSVSingleUseViolationException extends RuntimeException
{

    /** Serial Version UID. */
    private static final long serialVersionUID = 1L;

    
	/**
	 * Constructs a new {@link CSVSingleUseViolationException} with
	 * {@code null} as its detail message. The cause is not initialized,
	 * and may subsequently be initialized by a call to {@link #initCause}.
	 */
    public CSVSingleUseViolationException()
    {
    
        super( "This method can be invoked only once" );
        
    }

	/**
	 * Constructs a new {@link CSVSingleUseViolationException} with the specified
	 * detail message. The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 */
    public CSVSingleUseViolationException( String message )
    {
    
        super(message);
        
    }

	/**
	 * Constructs a new {@link CSVSingleUseViolationException} with the specified
	 * cause and a detail message of
	 * <tt>(cause==null ? null : cause.toString())</tt> (which typically
	 * contains the class and detail message of <tt>cause</tt>). This
	 * constructor is useful for exceptions that are little more than wrappers
	 * for other throwables (for example, {@link java.security.PrivilegedActionException}).
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
    public CSVSingleUseViolationException( Throwable cause )
    {
        
        super( cause );
        
    }

	/**
	 * Constructs a new {@link CSVSingleUseViolationException} with the specified
	 * detail message and cause.
	 * <p>
	 * Note that the detail message associated with {@code cause} is
	 * <i>not</i> automatically incorporated in this exception's detail message.
	 * 
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            {@link #getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
    public CSVSingleUseViolationException( String message, Throwable cause )
    {
        
        super( message, cause );
        
    }

}