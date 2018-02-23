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

import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.model.Product;


/**
 * Test for the class CSVReadOutcome.
 * 
 * @author Nerd4j Team
 */
public class CSVReadOutcomeTest
{
    
	private static final String header = "\"NAME\",\"DESCRIPTION\",\"UPC\",\"CURRENCY\",\"PRICE\",\"IN-STOCK\",\"LAST-UPDATE\"\n";

	private static final String valid_record = "\"Name: 1413475342304\",\"Description: 1413475342304\",1413475342304,EUR,1188896.8,true,16-10-14\n";

	private static final String invalid_record = "\"Name: 1413475342304\",\"Description: 1413475342304\",1413475342304,1188896.8,EUR,true,16-10-14\n";
	
    
	/* ************** */
	/*  TEST METHODS  */
	/* ************** */
	
        
    @Test
    public void testValidForEachLoop() throws Exception
    {
    	    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_VALID_RECORDS );
    	
    	final AtomicInteger success = new AtomicInteger();
    	final AtomicInteger error = new AtomicInteger();
    	final AtomicInteger total = new AtomicInteger();
    	
    	for( CSVReadOutcome<Product> read : reader )
    		read.success( model -> success.incrementAndGet() )
    			.error( context -> error.incrementAndGet() )
    			.then( context -> total.incrementAndGet() );
    		
    	Assert.assertEquals( 3, success.get() );
    	Assert.assertEquals( 0, error.get() );
    	Assert.assertEquals( 3, total.get() );
    	    	
    }
    
    @Test
    public void testInvalidForEachLoop() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_RECORDS_ONE_INVALID );
    	
    	final AtomicInteger success = new AtomicInteger();
    	final AtomicInteger error = new AtomicInteger();
    	final AtomicInteger total = new AtomicInteger();
    	
    	for( CSVReadOutcome<Product> read : reader )
    		read.success( model -> success.incrementAndGet() )
    		    .error( context -> error.incrementAndGet() )
    		    .then( context -> total.incrementAndGet() );
    	
    	Assert.assertEquals( 2, success.get() );
    	Assert.assertEquals( 1, error.get() );
    	Assert.assertEquals( 3, total.get() );
    	
    }
    
    @Test
    public void testValidForEach() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_VALID_RECORDS );
    	
    	final AtomicInteger success = new AtomicInteger();
    	final AtomicInteger error = new AtomicInteger();
    	final AtomicInteger total = new AtomicInteger();
    	
    	reader.forEach( read ->
    		read.error( context -> error.incrementAndGet() )
    			.success( model -> success.incrementAndGet() )
    			.then( context -> total.incrementAndGet() )
    	);
    		
    	Assert.assertEquals( 3, success.get() );
    	Assert.assertEquals( 0, error.get() );
    	Assert.assertEquals( 3, total.get() );
    	
    }
    
    @Test
    public void testInvalidForEach() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_RECORDS_ONE_INVALID );
    	
    	final AtomicInteger success = new AtomicInteger();
    	final AtomicInteger error = new AtomicInteger();
    	final AtomicInteger total = new AtomicInteger();
    	
    	reader.forEach( read ->
			read.error( context -> error.incrementAndGet() )
				.success( model -> success.incrementAndGet() )
				.then( context -> total.incrementAndGet() )
    	);
    	
    	Assert.assertEquals( 2, success.get() );
    	Assert.assertEquals( 1, error.get() );
    	
    }
    
    
    @Test
    public void testValidStream() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_VALID_RECORDS );
    	
    	final AtomicInteger success = new AtomicInteger();
    	final AtomicInteger error = new AtomicInteger();
    	final AtomicInteger total = new AtomicInteger();
    	
    	final long count = reader.stream()
    	.peek( read ->
    		read.success( model -> success.incrementAndGet() )
    		    .error( context -> error.incrementAndGet() )
    		    .then( context -> total.incrementAndGet() )
    	).count();
    	
    	Assert.assertEquals( 3, success.get() );
    	Assert.assertEquals( 0, error.get() );
    	Assert.assertEquals( 3, total.get() );
    	Assert.assertEquals( 3, count );
    	
    }
    
    @Test
    public void testInvalidStream() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_RECORDS_ONE_INVALID );
    	
    	final AtomicInteger success = new AtomicInteger();
    	final AtomicInteger error = new AtomicInteger();
    	final AtomicInteger total = new AtomicInteger();
    	
    	final long count = reader.stream()
    	.peek( read ->
    		read.success( model -> success.incrementAndGet() )
    			.error( context -> error.incrementAndGet() )
    			.then( context -> total.incrementAndGet() )
    	).count();
    	
    	Assert.assertEquals( 2, success.get() );
    	Assert.assertEquals( 1, error.get() );
    	Assert.assertEquals( 3,  total.get() );
    	Assert.assertEquals( 3,  count );
    	
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * Returns a CSVReader for the given CSVSource.
     * 
     * @param source CSV source.
     * @return a CSVReader for the given CSVSource.
     */
    private <T> CSVReader<T> getCSVReader( Source source ) throws Exception
    {
    	
    	final CSVReaderMetadataFactory<T> metadataFactory = CSVReaderConfigurator.getCSVToBeanReaderMetadataFactory( false );
        final CSVReaderFactory<T> readerFactory = new CSVReaderFactoryImpl<>( metadataFactory );       
        
        final Reader reader =  new StringReader( source.content );
    	final CSVReader<T> csvReader = readerFactory.getCSVReader( reader );
    	
    	return csvReader;
    	
    }
    
    
    /**
     * Enumerates the type of possible CSV sources.
     * 
     * @author Nerd4j Team
     */
    private static enum Source
    {
    	
    	ONE_VALID_RECORD( header + valid_record ),
    	ONE_INVALID_RECORD( header + invalid_record ),
    	THREE_VALID_RECORDS( header + valid_record + valid_record + valid_record ),
    	THREE_RECORDS_ONE_INVALID( header + valid_record + invalid_record + valid_record );
    	
    	
    	/** The content of the source. */
    	public final String content;
    	
    	/**
    	 * Constructor with parameters.
    	 * 
    	 * @param content the content of the source.
    	 */
    	private Source( String content )
    	{
    		
    		this.content = content;
    		
    	}
    	
    }
    
}