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
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.exception.CSVUnrecoverableWriteException;
import org.nerd4j.csv.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Test for the class CSVReadOutcomeIterator.
 * 
 * @author Nerd4j Team
 */
public class CSVStreamDrainerTest
{
    
    private static final Logger logger = LoggerFactory.getLogger( CSVStreamDrainerTest.class );
    
    private static final Product valid   = getValidModel();
    private static final Product invalid = getEmptyModel();
    
    
	/* ************** */
	/*  TEST METHODS  */
	/* ************** */
	
	
    @Test
    public void testEmptyStream() throws Exception
    {
    	
    	final Stream<Product> stream = Stream.empty();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
	    drain( stream, writer, 0, 0, 0 );
	    
    }
    
    @Test
    public void testEmptySpliterator() throws Exception
    {
    	
    	final Spliterator<Product> spliterator = Spliterators.emptySpliterator();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( spliterator, writer, 0, 0, 0 );
    	
    }
    
    @Test
    public void testEmptyIterator() throws Exception
    {
    	
    	final Iterator<Product> iterator = Collections.emptyIterator();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( iterator, writer, 0, 0, 0 );
    	
    }
    
    @Test
    public void testOneValidElementStream() throws Exception
    {
    	
    	final Stream<Product> stream = Stream.of( valid );
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( stream, writer, 1, 0, 1 );
    	
    }
    
    @Test
    public void testOneValidElementSpliterator() throws Exception
    {
    	
    	final Spliterator<Product> spliterator = Arrays.asList( valid ).spliterator();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( spliterator, writer, 1, 0, 1 );
    	
    }
    
    @Test
    public void testOneValidElementIterator() throws Exception
    {
    	
    	final Iterator<Product> iterator = Arrays.asList( valid ).iterator();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( iterator, writer, 1, 0, 1 );
    	
    }
    
    @Test
    public void testOneInvalidElementStream() throws Exception
    {
    	
    	final Stream<Product> stream = Stream.of( invalid );
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( stream, writer, 0, 1, 1 );
    	
    }
    
    @Test
    public void testOneInvalidElementSpliterator() throws Exception
    {
    	
    	final Spliterator<Product> spliterator = Arrays.asList( invalid ).spliterator();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( spliterator, writer, 0, 1, 1 );
    	
    }
    
    @Test
    public void testOneInvalidElementIterator() throws Exception
    {
    	
    	final Iterator<Product> iterator = Arrays.asList( invalid ).iterator();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( iterator, writer, 0, 1, 1 );
    	
    }   
    
    @Test
    public void testMoreValidElementsStream() throws Exception
    {
    	
    	final Stream<Product> stream = Stream.of( valid, valid, valid );
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( stream, writer, 3, 0, 3 );
    	
    }
    
    @Test
    public void testMoreValidElementsSpliterator() throws Exception
    {
    	
    	final Spliterator<Product> spliterator = Arrays.asList( valid, valid, valid ).spliterator();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( spliterator, writer, 3, 0, 3 );
    	
    }
    
    @Test
    public void testMoreValidElementsIterator() throws Exception
    {
    	
    	final Iterator<Product> iterator = Arrays.asList( valid, valid, valid ).iterator();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( iterator, writer, 3, 0, 3 );
    	
    }
    
    @Test
    public void testMoreElementsOneInvalidStream() throws Exception
    {
    	
    	final Stream<Product> stream = Stream.of( valid, invalid, valid );
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( stream, writer, 2, 1, 3 );
    	
    }
    
    @Test
    public void testMoreElementsOneInvalidSpliterator() throws Exception
    {
    	
    	final Spliterator<Product> spliterator = Arrays.asList( valid, invalid, valid ).spliterator();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( spliterator, writer, 2, 1, 3 );
    	
    }
    
    @Test
    public void testMoreElementsOneInvalidIterator() throws Exception
    {
    	
    	final Iterator<Product> iterator = Arrays.asList( valid, invalid, valid ).iterator();
    	
    	final CSVWriter<Product> writer = getCSVWriter( false ); 
    	drain( iterator, writer, 2, 1, 3 );
    	
    }
    
    @Test
    public void testBrokenWriterWithEmptySream() throws Exception
    {
    	
    	final Stream<Product> stream = Stream.empty();
    	final CSVWriter<Product> writer = getCSVWriter( true ); 
    	
    	drain( stream, writer, 0, 0, 0 );
    	
    }
    
    @Test
    public void testBrokenWriterWithOneInvalidElementSream() throws Exception
    {
    	
    	final Stream<Product> stream = Stream.of( invalid );
    	final CSVWriter<Product> writer = getCSVWriter( true ); 
    	
    	drain( stream, writer, 0, 1, 1 );
    	
    }
    
    @Test
    public void testBrokenWriterWithOneValidElementSream() throws Exception
    {
    	
    	final Stream<Product> stream = Stream.of( valid );
    	final CSVWriter<Product> writer = getCSVWriter( true ); 
    	
    	drainWithError( stream, writer, 1, 0, 1 );
    	
    }
    
    @Test
    public void testBrokenWriterWithMoreValidElementsSream() throws Exception
    {
    	
    	final Stream<Product> stream = Stream.of( valid, valid, valid );
    	final CSVWriter<Product> writer = getCSVWriter( true ); 
    	
    	drainWithError( stream, writer, 3, 0, 3 );
    	
    }
    
        
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * Returns an empty and therefore invalid data model.
     * 
     * @return an empty data model.
     */
    private static Product getEmptyModel()
    {
        
        return new Product();
                
    }
    
    /**
     * Returns a valid data model to be written.
     * 
     * @return a valid data model to be written.
     */
    private static Product getValidModel()
    {
    	
    	final Product model = new Product();
    	model.setName( "name" );
    	model.setDescription( "description" );
    	model.setUpc( new Long(System.currentTimeMillis()) );
    	model.setCurrency( Product.Currency.EUR );
    	model.setPrice( new Float(33.33) );
    	model.setInStock( Boolean.FALSE );
    	model.setLastUpdate( new Date() );
    	
    	return model;
    	
    }
    
    /**
     * Returns a well configured CSVWriter.
     * <p>
     * To simulate a broken CSVWriter we close
     * the underlying writer so every write or flush
     * produces an IOException.
     * 
     * 
     * @param broken tells if the CSV writer has to be broken.
     * @return a CSVWriter.
     */
    public <T> CSVWriter<T> getCSVWriter( boolean broken ) throws Exception
    {
        
        final CSVWriterMetadataFactory<T> metadataFactory = CSVWriterConfigurator.getBeanToCSVWriterMetadataFactory();
        final CSVWriterFactory<T> writerFactory = new CSVWriterFactoryImpl<T>( metadataFactory );
        
        final Writer writer = broken ? new BrokenWriter() : new StringWriter( 300 );
        final CSVWriter<T> csvWriter = writerFactory.getCSVWriter( writer );
        
        return csvWriter;
                
    }
        
    /**
     * Drains the given stream and checks the operation statistics.
     * 
     * @param stream the stream to drain.
     * @param writer the CSV writer.
     * @param success number of expected success.
     * @param errors number of expected errors.
     * @param total  number of expected writes.
     */
    private <T> void drain( Stream<T> stream, CSVWriter<T> writer, int success, int errors, int total )
    {
    	
    	final Stats stats = new Stats();
    	try{
    	
    		writer.drain( stream ).afterEach( write ->
        	{
        		write.success( model -> stats.success.incrementAndGet() );
        		write.error( error -> stats.error.incrementAndGet() );
        		write.then( context -> stats.total.incrementAndGet() );
        	})
        	.intoCSV();
    		
    	}catch( CSVUnrecoverableWriteException ex )
    	{
    		logger.error( "Unexpected error", ex );
    		Assert.fail( "Unexpected error" );
    	}
    	
    	Assert.assertEquals( success, stats.success.get() );
    	Assert.assertEquals( errors, stats.error.get() );
    	Assert.assertEquals( total, stats.total.get() );
    	
    }
    
    /**
     * Drains the given spliterator and checks the operation statistics.
     * 
     * @param spliterator the spliterator to drain.
     * @param writer the CSV writer.
     * @param success number of expected success.
     * @param errors number of expected errors.
     * @param total  number of expected writes.
     */
    private <T> void drain( Spliterator<T> spliterator, CSVWriter<T> writer, int success, int errors, int total )
    {
    	
    	final Stats stats = new Stats();
    	try{
    	
    		writer.drain( spliterator ).afterEach( write ->
        	{
        		write.success( model -> stats.success.incrementAndGet() );
        		write.error( error -> stats.error.incrementAndGet() );
        		write.then( context -> stats.total.incrementAndGet() );
        	})
        	.intoCSV();
    		
    	}catch( CSVUnrecoverableWriteException ex )
    	{
    		logger.error( "Unexpected error", ex );
    		Assert.fail( "Unexpected error" );
    	}
    	
    	Assert.assertEquals( success, stats.success.get() );
    	Assert.assertEquals( errors, stats.error.get() );
    	Assert.assertEquals( total, stats.total.get() );
    	
    }
    
    /**
     * Drains the given iterator and checks the operation statistics.
     * 
     * @param iterator the iterator to drain.
     * @param writer the CSV writer.
     * @param success number of expected success.
     * @param errors number of expected errors.
     * @param total  number of expected writes.
     */
    private <T> void drain( Iterator<T> iterator, CSVWriter<T> writer, int success, int errors, int total )
    {
    	
    	final Stats stats = new Stats();
    	try{
    	
    		writer.drain( iterator ).afterEach( write ->
    		{
    			write.success( model -> stats.success.incrementAndGet() );
    			write.error( error -> stats.error.incrementAndGet() );
    			write.then( context -> stats.total.incrementAndGet() );
    		})
    		.intoCSV();
    		
    	}catch( CSVUnrecoverableWriteException ex )
    	{
    		logger.error( "Unexpected error", ex );
    		Assert.fail( "Unexpected error" );
    	}
    	
    	Assert.assertEquals( success, stats.success.get() );
    	Assert.assertEquals( errors, stats.error.get() );
    	Assert.assertEquals( total, stats.total.get() );
    	
    }
    
    
    /**
     * Tries to drain the given stream expecting ad error to occur
     * and checks the partial operation statistics.
     * 
     * @param stream the stream to drain.
     * @param writer the CSV writer.
     * @param success number of expected success.
     * @param errors number of expected errors.
     * @param total  number of expected writes.
     */
    private <T> void drainWithError( Stream<T> stream, CSVWriter<T> writer, int success, int errors, int total )
    {
    	
    	final Stats stats = new Stats();
    	try{
    	
    		writer.drain( stream ).afterEach( write ->
        	{
        		write.success( model -> stats.success.incrementAndGet() );
        		write.error( error -> stats.error.incrementAndGet() );
        		write.then( context -> stats.total.incrementAndGet() );
        	})
        	.intoCSV();
    		
    		Assert.fail( "A CSVUnrecoverableWriteException was expected" );
    		
    	}catch( CSVUnrecoverableWriteException ex )
    	{
    		
    		Assert.assertEquals( success, stats.success.get() );
    		Assert.assertEquals( errors, stats.error.get() );
    		Assert.assertEquals( total, stats.total.get() );
    		
    	}catch( Exception ex )
    	{
    		Assert.fail( "A CSVUnrecoverableWriteException was expected" );
    	}
    	
    }
    
    
    /* *************** */
    /*  INNER CLASSES  */
    /* *************** */

    
    /**
     * Write operations statistics.
     * 
     * @author Nerd4j Team
     */
    private class Stats
    {
    	
    	public AtomicInteger success = new AtomicInteger();
    	public AtomicInteger error = new AtomicInteger();
    	public AtomicInteger total = new AtomicInteger();

    }
    
    
    /**
     * Stub Writer that simulates a broken writer.
     * 
     * @author Nerd4j Team
     */
    private class BrokenWriter extends Writer
    {
    	
    	/**
    	 * Default constructor.
    	 * 
    	 */
    	public BrokenWriter()
    	{
    		
    		super();
    		
    	}
    	
    	
    	/* ******************* */
    	/*  INTERFACE METHODS  */
    	/* ******************* */

    	
    	/**
    	 * {@inheritDoc}
    	 */
		@Override
		public void write( char[] cbuf, int off, int len ) throws IOException
		{
			throw new IOException( "BrokenWriter Stub" );
		}

		/**
    	 * {@inheritDoc}
    	 */
		@Override
		public void flush() throws IOException
		{
			throw new IOException( "BrokenWriter Stub" );
		}

		/**
    	 * {@inheritDoc}
    	 */
		@Override
		public void close() throws IOException
		{
			throw new IOException( "BrokenWriter Stub" );
		}
    	
    }
    
}