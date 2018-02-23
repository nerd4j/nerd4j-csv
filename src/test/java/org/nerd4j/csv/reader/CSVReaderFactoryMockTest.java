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
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Test for the class CSVReaderFactory.
 * 
 * @author Nerd4j Team
 */
public class CSVReaderFactoryMockTest
{
    
    private static final Logger logger = LoggerFactory.getLogger( CSVReaderFactoryMockTest.class );
    
	private static final String header = "\"NAME\",\"DESCRIPTION\",\"UPC\",\"CURRENCY\",\"PRICE\",\"IN-STOCK\",\"LAST-UPDATE\"\n";

	private static final String complete_records = "\"Name: 1413475342304\",\"Description: 1413475342304\",1413475342304,EUR,1188896.8,true,16-10-14\n"
                                                 + "\"Name: 1413475342304\",\"Description: 1413475342304\",1413475342304,EUR,1188896.8,true,16-10-14\n";
	
	private static final String incomplete_records = "\"Name: 1413475342304\",\"Description: 1413475342304\",1413475342304,EUR,1188896.8,true,16-10-14\n"
			                                       + "\"Name: 1413475342304\",\"Description: 1413475342304\",1413475342304,EUR,1188896.8,\n";
    
    
    @Test
    public void testCSVToArrayReader() throws Exception
    {
    	
    	for( Source source : Source.values() )
    	{
               
	        final CSVReaderMetadataFactory<Object[]> completeMetadataFactory = CSVReaderConfigurator.getCSVToArrayReaderMetadataFactory( false );
	        final CSVReaderMetadataFactory<Object[]> incompleteMetadataFactory = CSVReaderConfigurator.getCSVToArrayReaderMetadataFactory( true );
	        
	        final CSVReaderFactory<Object[]> completeReaderFactory = new CSVReaderFactoryImpl<>( completeMetadataFactory );       
	        final CSVReaderFactory<Object[]> incompleteReaderFactory = new CSVReaderFactoryImpl<>( incompleteMetadataFactory );       
	        
	        checkCompleteReader( completeReaderFactory, source);
	        checkIncompleteReader( incompleteReaderFactory, source);
        
    	}
        
    }
    
    @Test
    public void testCSVToBeanReader() throws Exception
    {
        
        for( Source source : Source.values() )
    	{
               
	        final CSVReaderMetadataFactory<Product> completeMetadataFactory = CSVReaderConfigurator.getCSVToBeanReaderMetadataFactory( false );
	        final CSVReaderMetadataFactory<Product> incompleteMetadataFactory = CSVReaderConfigurator.getCSVToBeanReaderMetadataFactory( true );
	        
	        final CSVReaderFactory<Product> completeReaderFactory = new CSVReaderFactoryImpl<>( completeMetadataFactory );       
	        final CSVReaderFactory<Product> incompleteReaderFactory = new CSVReaderFactoryImpl<>( incompleteMetadataFactory );       
	        
	        checkCompleteReader( completeReaderFactory, source);
	        checkIncompleteReader( incompleteReaderFactory, source);
        
    	}
        
    }
    
    @Test
    public void testCSVToMapReader() throws Exception
    {
        
        for( Source source : Source.values() )
    	{
               
	        final CSVReaderMetadataFactory<Map<String,Object>> completeMetadataFactory = CSVReaderConfigurator.getCSVToMapReaderMetadataFactory( false );
	        final CSVReaderMetadataFactory<Map<String,Object>> incompleteMetadataFactory = CSVReaderConfigurator.getCSVToMapReaderMetadataFactory( true );
	        
	        final CSVReaderFactory<Map<String,Object>> completeReaderFactory = new CSVReaderFactoryImpl<>( completeMetadataFactory );       
	        final CSVReaderFactory<Map<String,Object>> incompleteReaderFactory = new CSVReaderFactoryImpl<>( incompleteMetadataFactory );       
	        
	        checkCompleteReader( completeReaderFactory, source);
	        checkIncompleteReader( incompleteReaderFactory, source);
        
    	}
        
    }
    
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    /**
     * Checks expected behavior for a reader configured to read only complete records.
     * 
     * @param factory CSV reader factory.
     * @param source  CSV source.
     */
    private <T> void checkCompleteReader( CSVReaderFactory<T> factory, Source source )
    {
    	
    	try{
	        
    		final Reader reader =  new StringReader( source.content );
        	final CSVReader<T> csvReader = factory.getCSVReader( reader );
        	printModel( csvReader );

        	if( source.malformed )
        		Assert.fail( "CSV " + source + " is malformed, the reader should throw an exception" );
        	
        	if( source.incomplete )
        		Assert.fail( "CSV " + source + "  records are incomplete, the reader should throw an exception" );
        	
        }catch( Exception ex )
        {
        	if( ! source.malformed && ! source.incomplete )
        		Assert.fail( "CSV " + source + " is well formed should be parsed without errors" );
        }
    	
    }
    
    /**
     * Checks expected behavior for a reader configured to read only complete records.
     * 
     * @param factory CSV reader factory.
     * @param source  CSV source.
     */
    private <T> void checkIncompleteReader( CSVReaderFactory<T> factory, Source source )
    {
    	
    	try{
    		
    		final Reader reader =  new StringReader( source.content );
    		final CSVReader<T> csvReader = factory.getCSVReader( reader );
    		printModel( csvReader );
    		
    		if( source.malformed )
    			Assert.fail( "CSV " + source + " is malformed, the reader should throw an exception" );
    		
    	}catch( Exception ex )
    	{
    		if( ! source.malformed )
    			Assert.fail( "CSV " + source + " is well formed should be parsed without errors" );
    	}
    	
    }
    
    
    /**
     * Prints the model read from the CSV source.
     * 
     * @param csvReader the reader to test.
     * @throws Exception if read operations fails.
     */
    private <T> void printModel( CSVReader<T> csvReader ) throws Exception
    {
        
        T model;
        CSVReadOutcome<T> outcome;
        int attempts = 0;
        do{
            
            outcome = csvReader.read();
            model = outcome.getModel();
            if( outcome.getCSVReadingContext().isError() )
            	throw new Exception( outcome.getCSVReadingContext().getError().getMessage() );
            
            else if( model != null )
                logger.info( String.valueOf(model) );
                
        }while( ++ attempts < 5 );
                
    }
    
    
    /**
     * Enumerates the type of possible CSV sources.
     * 
     * @author Nerd4j Team
     */
    private static enum Source
    {
    	
    	EMPTY_SOURCE( "", true, false ),
    	HEADER_ONLY( header, false, false ),
    	
    	COMPLETE_RECORDS_ONLY( complete_records, false, false ),
    	INCOMPLETE_RECORDS_ONLY( incomplete_records, false, true ),
    	
    	HEADER_AND_COMPLETE_RECORDS( header + complete_records, false, false ),
    	HEADER_AND_INCOMPLETE_RECORDS( header + incomplete_records, false, true );
    	
    	
    	/** The content of the source. */
    	public final String content;
    	
    	/** Tells if the source content is malformed. */
    	public final boolean malformed;
    	
    	/** Tells if the source records are incomplete. */
    	public final boolean incomplete;
    	
    	
    	/**
    	 * Constructor with parameters.
    	 * 
    	 * @param content the content of the source.
    	 * @param malformed the source content is malformed.
    	 * @param incomplete the source records are incomplete.
    	 */
    	private Source( String content, boolean malformed, boolean incomplete )
    	{
    		
    		this.content = content;
    		this.malformed = malformed;
    		this.incomplete = incomplete;
    		
    	}
    	
    }
    
}