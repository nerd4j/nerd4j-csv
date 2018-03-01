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

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

import org.junit.Test;
import org.nerd4j.csv.CSVProcessContext;
import org.nerd4j.csv.CSVProcessOutcome;
import org.nerd4j.csv.model.Product;
import org.nerd4j.csv.writer.CSVWriterFactoryFileTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Test per la classe CSVReaderFactory.
 * 
 * @author Nerd4j Team
 */
public class CSVReaderFactoryFileTest
{
    
    private static final Logger logger = LoggerFactory.getLogger( CSVReaderFactoryFileTest.class );

    private static final boolean printModelSuccess = true;
    
    
    @Test
    public void testCSVToArrayReader() throws Exception
    {
        
        final Reader reader = getCSVSourceReader();
        final CSVReaderMetadataFactory<Object[]> metadataFactory = CSVReaderConfigurator.getCSVToArrayReaderMetadataFactory( false );
        
        final CSVReaderFactory<Object[]> readerFactory = new CSVReaderFactoryImpl<Object[]>( metadataFactory );       
        final CSVReader<Object[]> csvReader = readerFactory.getCSVReader( reader );
        
        printModel( csvReader );
        
    }
    
    @Test
    public void testCSVToBeanReader() throws Exception
    {
        
        final Reader reader = getCSVSourceReader();
        final CSVReaderMetadataFactory<Product> metadataFactory = CSVReaderConfigurator.getCSVToBeanReaderMetadataFactory( false );  
        
        final CSVReaderFactory<Product> readerFactory = new CSVReaderFactoryImpl<Product>( metadataFactory );
        final CSVReader<Product> csvReader = readerFactory.getCSVReader( reader );
        
        printModel( csvReader );
        
    }
    
    @Test
    public void testCSVToMapReader() throws Exception
    {
        
        final Reader reader = getCSVSourceReader();
        final CSVReaderMetadataFactory<Map<String,Object>> metadataFactory = CSVReaderConfigurator.getCSVToMapReaderMetadataFactory( false );
        
        final CSVReaderFactory<Map<String,Object>> readerFactory = new CSVReaderFactoryImpl<Map<String,Object>>( metadataFactory );       
        final CSVReader<Map<String,Object>> csvReader = readerFactory.getCSVReader( reader );
        
        printModel( csvReader );
        
    }
    
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    private Reader getCSVSourceReader() throws Exception
    {
        
        final File file = new File( System.getProperty("java.io.tmpdir") + "/file_test.csv" );
        if( ! file.exists() )
            new CSVWriterFactoryFileTest().testArrayToCSVWriter();
        
        return new FileReader( file );
        
    }

    private <T> void printModel( CSVReader<T> csvReader ) throws Exception
    {
        
        T model;
        CSVProcessOutcome<T> outcome;
        CSVProcessContext context;
        do{
            
            outcome = csvReader.read();
            model = outcome.getModel();
            context = outcome.getCSVProcessContext();
            
            final StringBuilder sb = new StringBuilder( 300 );
            sb.append( "At [" ).append( context.getRowIndex() )
            .append( "," ).append( context.getColumnIndex() )
            .append( "]: " );
            if( outcome.getCSVProcessContext().isError() )
            {
                sb.append( context.getError().getMessage() );                
                logger.warn( sb.toString() );
            }
            
            else if( model != null )
            {
            	if( printModelSuccess )
            	{
	                sb.append( model );
	                logger.info( sb.toString() );
            	}
            }
            
            else
            {
                sb.append( "EMPTY ROW" );
                logger.warn( sb.toString() );
            }
                
        }while( ! csvReader.isEndOfData() );
                
    }
  
}