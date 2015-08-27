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

    private static final boolean USE_HEADER = false;    
    private static final boolean INCOMPLETE_RECORDS = true;
    
    
    @Test
    public void testCSVToArrayReader() throws Exception
    {
               
        final Reader reader = getCSVSourceReader();
        final CSVReaderMetadataFactory<Object[]> metadataFactory = CSVReaderConfigurator.getCSVToArrayReaderMetadataFactory( INCOMPLETE_RECORDS );
        final CSVReaderFactory<Object[]> readerFactory = new CSVReaderFactoryImpl<Object[]>( metadataFactory );       
                        
        final CSVReader<Object[]> csvReader = readerFactory.getCSVReader( reader );
        printModel( csvReader );
        
    }
    
    @Test
    public void testCSVToBeanReader() throws Exception
    {
        
        final Reader reader = getCSVSourceReader();
        final CSVReaderMetadataFactory<Product> metadataFactory = CSVReaderConfigurator.getCSVToBeanReaderMetadataFactory( INCOMPLETE_RECORDS );  
        final CSVReaderFactory<Product> readerFactory = new CSVReaderFactoryImpl<Product>( metadataFactory );       
                        
        final CSVReader<Product> csvReader = readerFactory.getCSVReader( reader );
        printModel( csvReader );
        
    }
    
    @Test
    public void testCSVToMapReader() throws Exception
    {
        
        final Reader reader = getCSVSourceReader();
        final CSVReaderMetadataFactory<Map<String,Object>> metadataFactory = CSVReaderConfigurator.getCSVToMapReaderMetadataFactory( INCOMPLETE_RECORDS );
        final CSVReaderFactory<Map<String,Object>> readerFactory = new CSVReaderFactoryImpl<Map<String,Object>>( metadataFactory );       
                        
        final CSVReader<Map<String,Object>> csvReader = readerFactory.getCSVReader( reader );
        printModel( csvReader );
        
    }
    
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    private Reader getCSVSourceReader()
    {
        
        String source;
        if( USE_HEADER )
        	source = "\"NAME\",\"DESCRIPTION\",\"UPC\",\"CURRENCY\",\"PRICE\",\"IN-STOCK\",\"LAST-UPDATE\"\n";
        else
        	source = "";
        	
        if( INCOMPLETE_RECORDS )
        {
        	source += "1188896.8,EUR,\"Description: 1413475342304\",16-10-14,true,1413475342304,\"Name: 1413475342304\"\n"
                   +  "1188896.8,EUR,\n";
        }
        else
        {
            source += "1188896.8,EUR,\"Description: 1413475342304\",16-10-14,true,1413475342304,\"Name: 1413475342304\"\n"
                   +  "1188896.8,EUR,\"Description: 1413475342304\",16-10-14,true,1413475342304,\"Name: 1413475342304\"\n";
        }

        return new StringReader( source );
        
    }
    
    
    private <T> void printModel( CSVReader<T> csvReader ) throws Exception
    {
        
        T model;
        CSVReadOutcome<T> outcome;
        int attempts = 0;
        do{
            
            outcome = csvReader.read();
            model = outcome.getModel();
            if( outcome.getCSVReadingContext().isError() )
                logger.warn( outcome.getCSVReadingContext().getError().getMessage() );
            
            else if( model != null )
                logger.info( String.valueOf(model) );
                
        }while( ++ attempts < 5 );
                
    }
    
}