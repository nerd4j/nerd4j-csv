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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.nerd4j.csv.formatter.CSVFormatter;
import org.nerd4j.csv.formatter.CSVFormatterFactory;
import org.nerd4j.csv.model.Product;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;


@Ignore
public class CSVWriterSpeedTest
{
	
    @Rule
	public TestRule benchmarkRun = new BenchmarkRule();
    
    @Test
    @BenchmarkOptions(benchmarkRounds = 5, warmupRounds = 5)
    public void testArrayToCSVWriterSpeed() throws Exception
    {
        
        final Writer writer = getCSVDestinationWriter();
        final CSVWriterMetadataFactory<Object[]> metadataFactory = CSVWriterConfigurator.getArrayToCSVWriterMetadataFactory();        
        
        final CSVWriterFactory<Object[]> writerFactory = new CSVWriterFactoryImpl<Object[]>( metadataFactory );       
        final CSVWriter<Object[]> csvWriter = writerFactory.getCSVWriter( writer );
        
        final Object[] model = getNewArray();
        
        final long start = System.currentTimeMillis();
        
        int i = 0;
        do{
            
            csvWriter.write( model );
            
        }while( ++i < 2000000 );
        
        csvWriter.close();
        
        final long end = System.currentTimeMillis();
        
        System.out.println( "NERD4J-CSV witten " + i + " records in " + (end - start) + "ms" );
        
    }

    
    @Test
    @BenchmarkOptions(benchmarkRounds = 5, warmupRounds = 5)
    public void testCSVFormatterSpeed() throws Exception
    {
        
        final Writer writer = getCSVDestinationWriter();
        final CSVFormatterFactory formatterFactory = new CSVFormatterFactory();
        final CSVFormatter formatter = formatterFactory.create( writer );
        
        final String[] record = new String[] { "Name: 1374160306665","Description: 1374160306665","1374160306665","EUR","1172245.9","false","18/07/2013" };
        
        final long start = System.currentTimeMillis();
        
        int i = 0;
        do{
            
            for( int j = 0; j < record.length; ++j )
                formatter.writeField( record[j] );
            
            formatter.writeEOR();
            
        }while( ++i < 2000000 );
        
        formatter.writeEOD();
        formatter.close();
        
        final long end = System.currentTimeMillis();
        
        System.out.println( "NERD4J-CSV witten " + i + " records in " + (end - start) + "ms" );
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    private Object[] getNewArray()
    {
        
        final long ts = System.currentTimeMillis();
        
        final Object[] model = new Object[7];
        
        model[0] = "Name: " + ts;
        model[1] = "Description: " + ts;
        model[2] = ts;
        model[3] = Product.Currency.EUR;
        model[4] = (float) Math.sqrt(ts);
        model[5] = ts % 2 == 0;
        model[6] = new Date(ts);
        
        return model;
        
    }

    
    private Writer getCSVDestinationWriter() throws IOException
    {
        
        final File dest = new File( System.getProperty("java.io.tmpdir") + "/speed_test.csv" );
        return new FileWriter( dest );
        
    }
    
}