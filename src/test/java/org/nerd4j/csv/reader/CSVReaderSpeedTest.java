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

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.nerd4j.csv.parser.CSVParser;
import org.nerd4j.csv.parser.CSVParserFactory;
import org.nerd4j.csv.parser.CSVToken;
import org.nerd4j.csv.writer.CSVWriterSpeedTest;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

//@Ignore
public class CSVReaderSpeedTest
{
    
    @Rule
	public TestRule benchmarkRun = new BenchmarkRule();

    @Test
    @BenchmarkOptions(benchmarkRounds = 5, warmupRounds = 5)
    public void csvToArrayReaderSpeedTest() throws Exception
    {
        
        final Reader reader = getCSVSourceReader();
        final CSVReaderMetadataFactory<Object[]> metadataFactory = CSVReaderConfigurator.getCSVToArrayReaderMetadataFactory( false );
        final CSVReaderFactory<Object[]> readerFactory = new CSVReaderFactoryImpl<Object[]>( metadataFactory );
        
        final CSVReader<Object[]> csvReader = readerFactory.getCSVReader( reader );
        printModel( csvReader );
        
    }

    @Test
    @BenchmarkOptions(benchmarkRounds = 5, warmupRounds = 5)
    public void parserSpeedTest() throws Exception
    {
        
        final Reader reader = getCSVSourceReader();
        final CSVParserFactory parserFactory = new CSVParserFactory();
        final CSVParser parser = parserFactory.create( reader );
        
        final long start = System.currentTimeMillis();

        int count = 0;
        while( parser.read() != CSVToken.END_OF_DATA  )
            ++count;
        
        final long end = System.currentTimeMillis();
                
        System.out.println( "NERD4J-CSV Read " + --count + " fields in " + (end - start) + "ms." );
        
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    private Reader getCSVSourceReader() throws Exception
    {
        
        final File file = new File( System.getProperty("java.io.tmpdir") + "/speed_test.csv" );
        
        if( ! file.exists() )
            new CSVWriterSpeedTest().testArrayToCSVWriterSpeed();
        
        return new FileReader( file );
        
    }
    
    
    private void printModel( CSVReader<Object[]> csvReader ) throws Exception
    {
        
        final long start = System.currentTimeMillis();
        
        int success = 0;
        int failure = 0;
        CSVReadOutcome<Object[]> outcome;
        while( ! csvReader.isEndOfData() )
        {
            
            outcome = csvReader.read();            
            if( outcome.getCSVReadingContext().isError() )
                ++ failure;
            else
                ++ success;
            
        }
        
        final long end = System.currentTimeMillis();
                
        System.out.println( "NERD4J-CSV Read " + --success + " recornds, failed " + failure + " in " + (end - start) + "ms." );
        
    }
    
}
