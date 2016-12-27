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
package org.nerd4j.csv.writer;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

import org.nerd4j.csv.exception.CSVProcessException;
import org.nerd4j.csv.exception.ModelToCSVBindingException;


/**
 * Represents a CSV destination writer.
 * 
 * <p>
 * Reads an object of type {@code M} that represents the
 * data model and writes the corresponding CSV record
 * to the given destination. 
 * 
 * <h3>Synchronization</h3>
 * <p>
 * CSV writers are not synchronized.
 * It is recommended to create separate CSV writer instances for each thread.
 * If multiple threads access a CSV writer concurrently, it must be synchronized
 * externally.
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @author Nerd4J Team
 */
public interface CSVWriter<M> extends Closeable, Flushable
{

	/**
	 * Writes the given data model, formatted like
	 * a CSV record into the configured CSV destination.
	 * <p>
     * This method has a cleaner interface but handles
     * processing errors by throwing
     * {@link org.nerd4j.csv.exception.CSVProcessException CSVProcessException}
     * therefore it may be slow. If performance matters
     * use {@link CSVWriter#write(Object)} instead.
	 * 
	 * @param model the data model to write.
	 * @throws IOException if an error occurs reading the CSV source.
	 * @throws ModelToCSVBindingException if an error occurs during model binding.
	 * @throws CSVProcessException if an error occurs during data processing.
	 */
	public void writeModel( M model ) throws IOException, ModelToCSVBindingException, CSVProcessException;
	
	/**
	 * Writes the given data model, formatted like
	 * a CSV record into the configured CSV destination.
	 * <p>
	 * <b>IMPORTANT</b> for performance reasons there is only one
	 *                  instance of the read outcome.
	 *                  So the internal values of this object may
	 *                  vary during execution.
	 * 
	 * @param model the data model to write.
	 * @return the outcome of the writing process.
	 * @throws IOException if an error occurs reading the CSV source.
	 * @throws ModelToCSVBindingException if an error occurs during model binding.
	 */
	public CSVWriteOutcome write( M model ) throws IOException, ModelToCSVBindingException;
	
}
