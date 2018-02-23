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
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.exception.CSVUnrecoverableReadException;
import org.nerd4j.csv.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Test for the class CSVReadOutcomeIterator.
 * 
 * @author Nerd4j Team
 */
public class CSVReadOutcomeIteratorTest
{
    
    private static final Logger logger = LoggerFactory.getLogger( CSVReadOutcomeIteratorTest.class );
    
	private static final String header = "\"NAME\",\"DESCRIPTION\",\"UPC\",\"CURRENCY\",\"PRICE\",\"IN-STOCK\",\"LAST-UPDATE\"\n";

	private static final String valid_record = "\"Name: 1413475342304\",\"Description: 1413475342304\",1413475342304,EUR,1188896.8,true,16-10-14\n";

	private static final String invalid_record = "\"Name: 1413475342304\",\"Description: 1413475342304\",1413475342304,1188896.8,EUR,true,16-10-14\n";
	
	private static final String incomplete_record = "\"Name: 1413475342304\",\"Description: 1413475342304\",1413475342304,EUR,1188896.8,\n";
    
    
	/* ************** */
	/*  TEST METHODS  */
	/* ************** */
	
	
    @Test
    public void testEmptyCSV() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.EMPTY_CSV ); 
	    final Iterator<CSVReadOutcome<Product>> iterator = reader.iterator();
	    
	    hasNoElements( iterator );
        
    }
    
    
    @Test
    public void testOneValidRecordCSV() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.ONE_VALID_RECORD ); 
    	final Iterator<CSVReadOutcome<Product>> iterator = reader.iterator();
    	
    	hasValidElement( iterator );
    	hasNoElements( iterator );
    	
    }
    
    @Test
    public void testOneInvalidRecordCSV() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.ONE_INVALID_RECORD ); 
    	final Iterator<CSVReadOutcome<Product>> iterator = reader.iterator();
    	
    	hasInvalidElement( iterator );
    	hasNoElements( iterator );
    	
    }
    
    @Test
    public void testOneIncompleteRecordCSV() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.ONE_INCOMPLETE_RECORD ); 
    	final Iterator<CSVReadOutcome<Product>> iterator = reader.iterator();
    	
    	hasUnrecoverabeError( iterator );
    	hasUnrecoverabeError( iterator );
    	
    }
    
    @Test
    public void testMoreValidRecordsCSV() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_VALID_RECORDS ); 
    	final Iterator<CSVReadOutcome<Product>> iterator = reader.iterator();
    	
    	hasValidElement( iterator );
    	hasValidElement( iterator );
    	hasValidElement( iterator );
    	hasNoElements( iterator );
    	
    }
    
    @Test
    public void testMoreRecordsOneInvalidCSV() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_RECORDS_ONE_INVALID ); 
    	final Iterator<CSVReadOutcome<Product>> iterator = reader.iterator();
    	
    	hasValidElement( iterator );
    	hasInvalidElement( iterator );
    	hasValidElement( iterator );
    	hasNoElements( iterator );
    	
    }
    
    @Test
    public void testMoreRecordsOneIncompleteCSV() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_RECORDS_ONE_INCOMPLETE ); 
    	final Iterator<CSVReadOutcome<Product>> iterator = reader.iterator();
    	
    	hasValidElement( iterator );
    	hasUnrecoverabeError( iterator );
    	hasUnrecoverabeError( iterator );
    	
    }
    
    @Test
    public void testHasNextIdempotence() throws Exception
    {
    	
    	/*
    	 * Until next() is invoked hasNext() returns
    	 * the same value.
    	 */
    	final CSVReader<Product> reader = getCSVReader( Source.ONE_VALID_RECORD ); 
    	final Iterator<CSVReadOutcome<Product>> iterator = reader.iterator();
    	
    	for( int i = 0; i < 10; ++i )
    		Assert.assertTrue( iterator.hasNext() );
    	
    	Assert.assertNotNull( iterator.next() );
    	
    	for( int i = 0; i < 10; ++i )
    		Assert.assertFalse( iterator.hasNext() );
    	
    }
    
    @Test
    public void testNextStandalone() throws Exception
    {
    	
    	/*
    	 * next() works without invoking hasNext().
    	 */
    	final CSVReader<Product> reader = getCSVReader( Source.ONE_VALID_RECORD ); 
    	final Iterator<CSVReadOutcome<Product>> iterator = reader.iterator();
    	
    	final CSVReadOutcome<Product> element = iterator.next();
    	Assert.assertNotNull( "Iterator output is always not null", element );
    	Assert.assertFalse( "No errors expected", element.getCSVReadingContext().isError() );
    	Assert.assertNotNull( "Data model expected", element.getModel() );

    	try{
    	    
	    	iterator.next();
	    	Assert.fail( "A NoSuchElementException was expected" );
	    	
	    }catch( NoSuchElementException ex )
	    {
	    	logger.debug( "Test success" );
	    }catch( Exception ex )
	    {
	    	Assert.fail( "A NoSuchElementException was expected" );
	    }

    }
    
    @Test
    public void testValidForEachLoop() throws Exception
    {
    	    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_VALID_RECORDS );
    	
    	for( CSVReadOutcome<Product> outcome : reader )
    		Assert.assertTrue( check(outcome) );
    	    	
    }
    
    @Test
    public void testBrokenForEachLoop() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_RECORDS_ONE_INCOMPLETE );
    	
    	int count = 0;
    	try{
    		
    		for( CSVReadOutcome<Product> outcome : reader )
    		{
    			Assert.assertTrue( check(outcome) );
    			++count;
    		}
    		
    		Assert.fail( "A CSVUnrecoverableReadException was expected" );
    		
    	}catch( CSVUnrecoverableReadException ex )
    	{
    		Assert.assertEquals( 1, count );
    	}catch( Exception ex )
    	{
    		Assert.fail( "A CSVUnrecoverableReadException was expected" );
    	}
    	
    }
    
    @Test
    public void testValidForEach() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_VALID_RECORDS );
    	
    	reader.forEach( outcome ->
	    	{
	    		try{
	    		
	    			Assert.assertTrue( check(outcome) );
	    			
		    	}catch( Exception ex )
		    	{
		    		Assert.fail( "No errors expected" );
		    	}
	    	}
    	);
    	
    }
    
    @Test
    public void testBrokenForEach() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_RECORDS_ONE_INCOMPLETE );
    	
    	final AtomicInteger count = new AtomicInteger();
    	try{
    		
    		reader.forEach( outcome -> count.incrementAndGet() );
    		
    		Assert.fail( "A CSVUnrecoverableReadException was expected" );
    		
    	}catch( CSVUnrecoverableReadException ex )
    	{
    		Assert.assertEquals( 1, count.get() );
    	}catch( Exception ex )
    	{
    		Assert.fail( "A CSVUnrecoverableReadException was expected" );
    	}
    	
    }
    
    @Test
    public void testValidStream() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_VALID_RECORDS );
    	
    	final long count = reader.stream()
    		.peek( outcome ->
	    	{
	    		try{
	    		
	    			Assert.assertTrue( check(outcome) );
	    			
		    	}catch( Exception ex )
		    	{
		    		Assert.fail( "No errors expected." );
		    	}
	    	})
    		.count();
    	
    	Assert.assertEquals( 3L, count );
    	
    }
    
    @Test
    public void testBrokenStream() throws Exception
    {
    	
    	final CSVReader<Product> reader = getCSVReader( Source.THREE_RECORDS_ONE_INCOMPLETE );
    	
    	final AtomicInteger count = new AtomicInteger();
    	try{
    		
    		reader.stream()
    		.peek( outcome -> count.incrementAndGet() )
	    	.count();
    		
    		Assert.fail( "A CSVUnrecoverableReadException was expected" );
    		
    	}catch( CSVUnrecoverableReadException ex )
    	{
    		Assert.assertEquals( 1, count.get() );
    	}catch( Exception ex )
    	{
    		Assert.fail( "A CSVUnrecoverableReadException was expected" );
    	}
    	
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
     * Prints the model read from the CSV source.
     * 
     * @param csvReader the reader to test.
     * @return {@code true} if the outcome has no errors.
     */
    private <T> boolean check( CSVReadOutcome<T> outcome ) throws Exception
    {
        
        if( outcome == null || outcome.getCSVReadingContext().isError() )
        	return false;
        
        logger.info( String.valueOf(outcome.getModel()) );
        return true;
                
    }
    
    /**
     * Checks if there are no more elements.
     * 
     * @param iterator the iterator to check.
     */
    private void hasNoElements( Iterator<CSVReadOutcome<Product>> iterator )
    {
    	
    	Assert.assertFalse( "No elements expected", iterator.hasNext() );
    	
    	try{
    	    
	    	iterator.next();
	    	Assert.fail( "A NoSuchElementException was expected" );
	    	
	    }catch( NoSuchElementException ex )
	    {
	    	logger.debug( "Test success" );
	    }catch( Exception ex )
	    {
	    	Assert.fail( "A NoSuchElementException was expected" );
	    }
    	
    }
    
    /**
     * Checks if next element is present and is valid.
     * 
     * @param iterator the iterator to check.
     */
    private void hasValidElement( Iterator<CSVReadOutcome<Product>> iterator )
    {
    	    	    	
    	Assert.assertTrue( "Element must be present", iterator.hasNext() );
    	
    	try{
    		
    		Assert.assertTrue( "Element must be valid", check(iterator.next()) );
    		
    	}catch( NoSuchElementException ex )
    	{
    		Assert.fail( "Element must be present" );
    	}catch( Exception ex )
    	{
    		Assert.fail( "No errors was expected" );
    	}
    	
    }
    
    /**
     * Checks if next element is present and is invalid.
     * 
     * @param iterator the iterator to check.
     */
    private void hasInvalidElement( Iterator<CSVReadOutcome<Product>> iterator )
    {
    	
    	Assert.assertTrue( "Element must be present", iterator.hasNext() );
    	
    	try{
    		
    		Assert.assertFalse( "Element must not be valid", check(iterator.next()) );
    		
    	}catch( NoSuchElementException ex )
    	{
    		Assert.fail( "Element must be present" );
    	}catch( Exception ex )
    	{
    		Assert.fail( "No errors was expected" );
    	}
    	
    }
    
    /**
     * Checks if next element is present and is invalid.
     * 
     * @param iterator the iterator to check.
     */
    private void hasUnrecoverabeError( Iterator<CSVReadOutcome<Product>> iterator )
    {
    	
    	try{
    		
    		iterator.hasNext();
    		Assert.fail( "A CSVUnrecoverableReadException was expected" );
    		
    	}catch( CSVUnrecoverableReadException ex )
    	{
    		logger.debug( "Test success" );
    	}catch( Exception ex )
    	{
    		Assert.fail( "A CSVUnrecoverableReadException was expected" );
    	}
    	
    	try{
    		
    		iterator.next();
    		Assert.fail( "A CSVUnrecoverableReadException was expected" );
    		
    	}catch( CSVUnrecoverableReadException ex )
    	{
    		logger.debug( "Test success" );
    	}catch( Exception ex )
    	{
    		Assert.fail( "A CSVUnrecoverableReadException was expected" );
    	}
    	
    }
    
    
    /**
     * Enumerates the type of possible CSV sources.
     * 
     * @author Nerd4j Team
     */
    private static enum Source
    {
    	
    	EMPTY_CSV( header ),    	
    	ONE_VALID_RECORD( header + valid_record ),
    	ONE_INVALID_RECORD( header + invalid_record ),
    	ONE_INCOMPLETE_RECORD( header + incomplete_record ),
    	THREE_VALID_RECORDS( header + valid_record + valid_record + valid_record ),
    	THREE_RECORDS_ONE_INVALID( header + valid_record + invalid_record + valid_record ),
    	THREE_RECORDS_ONE_INCOMPLETE( header + valid_record + incomplete_record + valid_record );
    	
    	
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