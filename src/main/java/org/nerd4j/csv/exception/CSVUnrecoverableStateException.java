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
 * Represents a {@link CSVException} occurred during the processing
 * of a CSV that leaves the {@link org.nerd4j.csv.reader.CSVReader CSVReader}
 * or the {@link org.nerd4j.csv.writer.CSVWriter CSVWriter} in an inconsistent
 * and unrecoverable state.
 * 
 * <h5>reading</h5>
 * <p>
 * This kind of exception will be thrown if the CSV source is malformed
 * in such a way that the parser is unable to recover or if there is an
 * unexpected end of data.
 * 
 * <h5>writing</h5>
 * <p>
 * This kind of exception will be thrown if the CSV target is not writable
 * or close unexpectedly.
 * 
 * <p>
 * After this kind of exception occurs, it is not advisable to continue
 * with the processing.
 *  
 * @since 1.2.0
 * 
 * @author Nerd4j Team
 */
public class CSVUnrecoverableStateException extends RuntimeException
{

    /** Serial Version UID. */
    private static final long serialVersionUID = 1L;

    
	/**
	 * Constructs a new {@link CSVUnrecoverableStateException} with
	 * {@code null} as its detail message. The cause is not initialized,
	 * and may subsequently be initialized by a call to {@link #initCause}.
	 */
    public CSVUnrecoverableStateException()
    {
    
        super();
        
    }

	/**
	 * Constructs a new {@link CSVUnrecoverableStateException} with the specified
	 * detail message. The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 */
    public CSVUnrecoverableStateException( String message )
    {
    
        super(message);
        
    }

	/**
	 * Constructs a new {@link CSVUnrecoverableStateException} with the specified
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
    public CSVUnrecoverableStateException( Throwable cause )
    {
        
        super( cause );
        
    }

	/**
	 * Constructs a new {@link CSVUnrecoverableStateException} with the specified
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
    public CSVUnrecoverableStateException( String message, Throwable cause )
    {
        
        super( message, cause );
        
    }

}