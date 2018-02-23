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

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.nerd4j.csv.CSVProcessOutcome;
import org.nerd4j.csv.exception.CSVUnrecoverableWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The aim of this class i to avoid the boilerplate code needed
 * to write all elements of an {@link java.util.Iterable} collection
 * or a {@link Stream} to a CSV destination.
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @author Nerd4J Team
 */
final class CSVStreamDrainer<M>
{
    
    /** SLF4J Logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVStreamDrainer.class );

    
    /** The target destination where to write the CSV. */
    private final CSVWriter<M> target;
    
    /** The stream to be drained. */
    private final Stream<M> stream;
    
    /** A {@link FunctionalInterface} to be called after each write operation. */
    private Consumer<CSVProcessOutcome<M>> callback;

    
    /**
     * Constructor with parameters.
     * 
     * @param stream  the stream to be drained.
     */
    public CSVStreamDrainer( final Stream<M> stream, CSVWriter<M> target )
    {
        
        super();
                
        this.stream = Objects.requireNonNull( stream, "The stream to be drained is mandatory" );
        this.target = Objects.requireNonNull( target, "The target CSV writer is mandatory" );
        this.callback = outcome -> { /* do nothing */ };
        
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@link FunctionalInterface} to be called after each write operation.
     * 
     * @param callback {@link FunctionalInterface} that consumes the write outcome.
     * @return the current object for concatenation.
     */
    public CSVStreamDrainer<M> afterEach( Consumer<CSVProcessOutcome<M>> callback )
    {
        
    	this.callback = Objects.requireNonNull( callback );
    	return this;
    	
    }
    
    
    /**
     * Actually performs the operation of draining the {@link Stream}
     * and writing the elements of the {@link Stream} into the CSV destination.
     * <p>
     * If a {@link FunctionalInterface} has been provided by method
     * {@link #afterEach(Consumer)} it will be called after each write operation.
     * 
     */
    public void intoCSV()
    {
    	
    	final AtomicLong countSuccess = new AtomicLong();
    	this.stream.forEach( model ->
    	{
    		try{
    		    			
    			/* For each element in the stream we perform a CSV write. */
    			final CSVWriteOutcome<M> write = target.write( model );
    			
    			/* If the write is successful we improve the count. */
    			write.success( m -> countSuccess.incrementAndGet() );
    			
    			/* Finally we apply the provided callback to the write outcome. */
    			callback.accept( write );
    			
    		}catch( Exception ex )
    		{
    			
    			/* If an unrecoverable error occurs an exception will be thrown. */
    			logger.error( "Unable to write to CSV target", ex );
				throw new CSVUnrecoverableWriteException( ex );
				
    		}
    		
    	});
    	
    	/*
    	 * If there was at least one successful write
    	 * we force the writer to flush the operations.
    	 */
    	if( countSuccess.get() > 0L )
    		flushWriter();
    	
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    
    /**
     * Forces the CSV destination writer to flush all writes.
     * 
     */
    private void flushWriter()
    {
    	
    	try{
        	
    		/* We force the writes to be flushed. */
    		target.flush();
    		
    	}catch( IOException ex )
    	{
    		
    		/*
    		 * If an exception occurs we need to notify that
    		 * the writing operation is in an inconsistent state.
    		 */
    		logger.error( "Exception occurred during flush", ex );
			throw new CSVUnrecoverableWriteException( ex );
			
    	}
    	
    }

}