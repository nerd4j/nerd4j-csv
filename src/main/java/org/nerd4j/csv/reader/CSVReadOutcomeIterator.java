/*
 * #%L
 * Nerd4j CSV
 * %%
 * Copyright (C) 2013 - 2018 Nerd4j
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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.nerd4j.csv.exception.CSVUnrecoverableReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class wraps a {@link CSVReader} and implements the {@link Iterator} interface
 * allowing the read outcomes to be used into a "for-each loop" or in the new
 * {@code Java 8 Stream API}.
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @since 1.2.0
 * 
 * @author Nerd4J Team
 */
class CSVReadOutcomeIterator<M> implements Iterator<CSVReadOutcome<M>>
{
	
	/** SLF4J Logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVReaderImpl.class );
    
	
	/** The bean source for this iterator. */
	private CSVReader<M> source;
	
	/** The element to be returned by method {@link #next()}. */
	private CSVReadOutcome<M> nextElement;
	
	/**
	 * An unrecoverable exception occurred during a read operation.
	 * If this kind of exception occurs the iterator is not able to
	 * keep reading elements and must stop.
	 */  
	private CSVUnrecoverableReadException exception;
	
	
	/**
	 * Constructor with parameters.
	 * 
	 * @param source the bean source for this iterator.
	 * @throws IOException if an error occurs reading the CSV source.
	 * @throws CSVToModelBindingException if an error occurs during model binding.
	 */
	public CSVReadOutcomeIterator( CSVReader<M> source )
	{
		
		super();
		
		this.source = Objects.requireNonNull( source, "The bean source for this iterator is mandatory" );
	
		this.exception = null;
		this.nextElement = null;
		
	}
	
	
	/* ******************* */
	/*  INTERFACE METHODS  */
	/* ******************* */

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext()
	{
		
		/*
		 * Since an error has occurred it makes non sense
		 * for this method to return either true or false.
		 */
		if( this.exception != null )
			throw this.exception;
		
		/*
		 * We perform the read operation if needed and
		 * return whether the CSV source is not empty.
		 */
		performReadIfNeeded();
		return ! source.isEndOfData();
		
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CSVReadOutcome<M> next()
	{
		
		/*
		 * According to the Iterator interface we return
		 * a NoSuchElementException if there are no more
		 * elements.
		 */
		if( ! hasNext() )
			throw new NoSuchElementException();
		
		/* We save the current element in a local variable. */
		final CSVReadOutcome<M> toBeReturned = this.nextElement;
		
		/* We clear the nextElement so a new read operation can be triggered. */
		this.nextElement = null;
		
		/* We return the stored element. */
		return toBeReturned;
		
	}
	
	
	/* ***************** */
	/*  PRIVATE METHODS  */
	/* ***************** */
	
	
	/**
	 * Performs a read operation in the CSV source
	 * if the next element has been consumed by the
	 * invocation of the method {@link #next()}
	 */
	private void performReadIfNeeded()
	{
		
		/*
		 * If the next element has not been
		 * consumed yet by the method next()
		 * nothing needs to be done.
		 */
		if( this.nextElement == null )
		{
			try{
				
				/*
				 * We need to read the source to know if it's empty or not.
				 * So we read the next element and store it in a variable
				 * to be returned when next() is invoked. 
				 */
				this.nextElement = source.read();
			
			}catch( Exception ex )
			{
				
				logger.error( "Unable to read the CSV source", ex );
				this.exception = new CSVUnrecoverableReadException( ex );
				throw this.exception;
				
			}
		}
		
	}

}
