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
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.nerd4j.csv.CSVProcessOutcome;
import org.nerd4j.csv.exception.CSVProcessException;
import org.nerd4j.csv.exception.CSVSingleUseViolationException;
import org.nerd4j.csv.exception.CSVToModelBindingException;


/**
 * Represents a CSV data source reader.
 * 
 * <p>
 * Reads a record in the CSV source and creates an object of
 * type {@code M} that represents such record. 
 * 
 * <h3>Synchronization</h3>
 * <p>
 * CSV readers are not synchronized.
 * It is recommended to create separate CSV reader instances for each thread.
 * If multiple threads access a CSV reader concurrently, it must be synchronized
 * externally.
 * 
 * <p>
 * Since version {@code 1.2.0} support has been added for the new {@code Java 8 Stream API}.
 * Now it's possible to use the {@link CSVReader} as the target of the {@code "for-each-loop"}
 * statement and even to get a {@link Stream} of {@link CSVProcessOutcome}s.
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @since 1.2.0 extends {@link Iterable}
 * 
 * @author Nerd4J Team
 */
public interface CSVReader<M> extends Closeable, Iterable<CSVProcessOutcome<M>>
{
	
	/**
	 * Returns the CSV header if any, otherwise returns {@code null}.
	 * 
	 * @return the CSV header if any, otherwise returns {@code null}.
	 */
	public String[] getHeader();
	
    /**
     * Tells if the end of the CSV source has been reached.
     * 
     * @return {@code true} if the end of the CSV source has been reached.
     */
    public boolean isEndOfData();
    
    /**
     * Reads a record in the CSV source and returns
     * the corresponding data model.
     * <p>
     * This method has a cleaner interface but handles
     * processing errors by throwing {@link CSVProcessException}
     * therefore it may be slow. If performance matters
     * use {@link CSVReader#read()} instead.
     * 
     * @return the outcome of the reading process.
     * @throws IOException if an error occurs reading the CSV source.
     * @throws CSVToModelBindingException if an error occurs during model binding.
     * @throws CSVProcessException if an error occurs during data processing.
     */
    public M readModel() throws IOException, CSVToModelBindingException, CSVProcessException;

	/**
	 * Reads a record in the CSV source and returns
	 * a {@link CSVProcessOutcome} containing the read
	 * data and the read execution context.
	 * <p>
     * <b>IMPORTANT</b> for performance reasons there is only one
     *                  instance of the read outcome.
     *                  So the internal values of this object may
     *                  vary during execution.
	 * 
	 * @return the outcome of the reading process.
	 * @throws IOException if an error occurs reading the CSV source.
	 * @throws CSVToModelBindingException if an error occurs during model binding.
	 */
	public CSVProcessOutcome<M> read() throws IOException, CSVToModelBindingException;
	
	/**
	 * Creates a {@link Spliterator} over the outcomes returned by this {@link CSVReader}.
	 * <p>
	 * The {@link Spliterator} created by this method is:
	 * <ul>
	 *  <li>{@code IMMUTABLE}: signifying that the element source cannot be structurally modified.</li>
	 *  <li>{@code NONNULL}: signifying that the source guarantees that encountered elements will not be {@code null}.</li>
	 *  <li>{@code ORDERED}: ignifying that an encounter order is defined for elements.</li>
	 * </ul>
	 * 
	 * @return a {@link Spliterator} over the outcomes returned by this {@link CSVReader}.
	 * @since 1.2.0
	 */
	@Override
	default Spliterator<CSVProcessOutcome<M>> spliterator()
	throws CSVSingleUseViolationException
	{
		final int characteristics =  Spliterator.IMMUTABLE | Spliterator.NONNULL | Spliterator.ORDERED;
		return Spliterators.spliteratorUnknownSize( iterator(), characteristics );
	}
	
    /**
     * Returns a sequential {@code Stream} of {@link CSVProcessOutcome}s.
     *
     * @return a sequential {@code Stream} of {@link CSVProcessOutcome}s.
     * @since 1.2.0
     */
    default Stream<CSVProcessOutcome<M>> stream()
    throws CSVSingleUseViolationException
    {
    	return StreamSupport.stream( spliterator(), false );
    }
	
}