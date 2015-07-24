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
package org.nerd4j.csv.reader;

import java.io.Closeable;
import java.io.IOException;

import org.nerd4j.csv.exception.CSVProcessException;
import org.nerd4j.csv.exception.CSVToModelBindingException;


/**
 * Represents a CSV data source reader.
 * 
 * <p>
 *  Reads a record in the CSV source and creates an object of
 *  type <e>M</e> that represents such record. 
 * </p>
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @author Nerd4J Team
 */
public interface CSVReader<M> extends Closeable
{

	/**
	 * Returns the CSV header if any, otherwise returns <code>null</code>.
	 * 
	 * @return the CSV header if any, otherwise returns <code>null</code>.
	 */
	public String[] getHeader();
	
    /**
     * Tells if the end of the CSV source has been reached.
     * 
     * @return <code>true</code> if the end of the CSV source has been reached.
     */
    public boolean isEndOfData();
    
    /**
     * Reads a record in the CSV source and returns
     * the corresponding data model.
     * <p>
     *  This method has a cleaner interface but handles
     *  processing errors by throwing {@link CSVProcessException}
     *  therefore it may be slow. If performance matters
     *  use {@link CSVReader#read()} instead.
     * </p>
     * 
     * @return the outcome of the reading process.
     * @throws IOException if an error occurs reading the CSV source.
     * @throws CSVToModelBindingException if an error occurs during model binding.
     * @throws CSVProcessException if an error occurs during data processing.
     */
    public M readModel() throws IOException, CSVToModelBindingException, CSVProcessException;

	/**
	 * Reads a record in the CSV source and returns
	 * a {@link CSVReadOutcome} containing the read
	 * data and the read execution context.
	 * <p>
     *  <b>IMPORTANT</b> for performance reasons there is only one
     *                   instance of the read outcome.
     *                   So the internal values of this object may
     *                   vary during execution.
     * </p>
	 * 
	 * @return the outcome of the reading process.
	 * @throws IOException if an error occurs reading the CSV source.
	 * @throws CSVToModelBindingException if an error occurs during model binding.
	 */
	public CSVReadOutcome<M> read() throws IOException, CSVToModelBindingException;
	
}