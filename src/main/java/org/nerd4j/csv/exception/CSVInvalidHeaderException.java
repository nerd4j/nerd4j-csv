/*
 * #%L
 * Nerd4j CSV
 * %%
 * Copyright (C) 2013 - 2017 Nerd4j
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
 * Represents an error occurred during the creation of a new
 * {@link org.nerd4j.csv.writer.CSVWriter CSVWriter} caused by
 * providing an inconsistent header.
 * 
 * <p>
 * The {@link org.nerd4j.csv.writer.CSVWriterFactory CSVWriterFactory}
 * allows to provide a custom header to create a CSV writer that
 * uses only the columns defined in the header but in such case
 * each column identifier in the custom header must match one in
 * the configuration. 
 * 
 * @author Nerd4j Team
 */
public class CSVInvalidHeaderException extends CSVConfigurationException
{

	
	/** Generated Serial Version UID. */
	private static final long serialVersionUID = 1L;
	
	
	/** Column identifier that does not match the configuration. */
	private String unmatchableColumnId;

	
	/**
	 * Constructs a new exception with the specified column identifier.
	 * <p>
	 * The message is build using the given column identifier.
	 * <p>
	 * The cause is not initialized, and may subsequently be initialized
	 * by a call to {@link #initCause}.
	 * 
	 * @param unmatchableColumnId column identifier that does not match the configuration.
	 */
	public CSVInvalidHeaderException( String unmatchableColumnId )
	{
		
		super( "The column identifier \"" + unmatchableColumnId + "\" is not configured" );
		
		this.unmatchableColumnId = unmatchableColumnId;
		
	}
	
	
	/* ******************* */
	/*  GETTERS & SETTERS  */
	/* ******************* */
	
	
	/**
	 * Returns the column identifier that does not match the configuration.
	 * 
	 * @return the column identifier that does not match the configuration.
	 */
	public String getUnmatchableColumnId()
	{
		return unmatchableColumnId;
	}
	
}
