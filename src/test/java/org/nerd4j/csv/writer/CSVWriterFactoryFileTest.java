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
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nerd4j.csv.model.Product;


public class CSVWriterFactoryFileTest
{
	
	private static final String[] customHeader = new String[] { "NAME", "DESCRIPTION", "PRICE", "CURRENCY" };
    
    @Test
    public void testArrayToCSVWriter() throws Exception
    {
        
        final Writer writer = getCSVDestinationWriter();
        final CSVWriterMetadataFactory<Object[]> metadataFactory = CSVWriterConfigurator.getArrayToCSVWriterMetadataFactory();        
        
        final CSVWriterFactory<Object[]> writerFactory = new CSVWriterFactoryImpl<Object[]>( metadataFactory );       
        final CSVWriter<Object[]> csvWriter = writerFactory.getCSVWriter( writer );
        
        Object[] model;
        for( int i = 0; i < 100; ++i )
        {
            model = getNewArray();
            csvWriter.write( model );
        }
        
        csvWriter.close();
        
    }
    
    @Test
    public void testCustomHeaderArrayToCSVWriter() throws Exception
    {
    	
    	final Writer writer = getCSVDestinationWriter();
    	final CSVWriterMetadataFactory<Object[]> metadataFactory = CSVWriterConfigurator.getArrayToCSVWriterMetadataFactory();        
    	
    	final CSVWriterFactory<Object[]> writerFactory = new CSVWriterFactoryImpl<Object[]>( metadataFactory );       
    	final CSVWriter<Object[]> csvWriter = writerFactory.getCSVWriter( writer, customHeader );
    	
    	Object[] model;
    	for( int i = 0; i < 100; ++i )
    	{
    		model = getNewArray();
    		csvWriter.write( model );
    	}
    	
    	csvWriter.close();
    	
    }
    
    @Test
    public void testBeanToCSVWriter() throws Exception
    {
        
        final Writer writer = getCSVDestinationWriter();
        final CSVWriterMetadataFactory<Product> metadataFactory = CSVWriterConfigurator.getBeanToCSVWriterMetadataFactory();
        
        final CSVWriterFactory<Product> writerFactory = new CSVWriterFactoryImpl<Product>( metadataFactory ); 
        final CSVWriter<Product> csvWriter = writerFactory.getCSVWriter( writer );
        
        Product model;
        for( int i = 0; i < 100; ++i )
        {
            model = getNewBean();
            csvWriter.write( model );
        }
        
        csvWriter.close();
        
    }
    
    @Test
    public void testCustomHeaderBeanToCSVWriter() throws Exception
    {
    	
    	final Writer writer = getCSVDestinationWriter();
    	final CSVWriterMetadataFactory<Product> metadataFactory = CSVWriterConfigurator.getBeanToCSVWriterMetadataFactory();
    	
    	final CSVWriterFactory<Product> writerFactory = new CSVWriterFactoryImpl<Product>( metadataFactory ); 
    	final CSVWriter<Product> csvWriter = writerFactory.getCSVWriter( writer, customHeader );
    	
    	Product model;
    	for( int i = 0; i < 100; ++i )
    	{
    		model = getNewBean();
    		csvWriter.write( model );
    	}
    	
    	csvWriter.close();
    	
    }
    
    @Test
    public void testMapToCSVWriter() throws Exception
    {
        
        final Writer writer = getCSVDestinationWriter();
        final CSVWriterMetadataFactory<Map<String,Object>> metadataFactory = CSVWriterConfigurator.getMapToCSVWriterMetadataFactory();
        
        final CSVWriterFactory<Map<String,Object>> writerFactory = new CSVWriterFactoryImpl<Map<String,Object>>( metadataFactory );       
        final CSVWriter<Map<String,Object>> csvWriter = writerFactory.getCSVWriter( writer );
        
        Map<String,Object> model;
        for( int i = 0; i < 100; ++i )
        {
            model = getNewMap();
            csvWriter.write( model );
        }
        
        csvWriter.close();
        
    }
    
    @Test
    public void testCustomHeaderMapToCSVWriter() throws Exception
    {
    	
    	final Writer writer = getCSVDestinationWriter();
    	final CSVWriterMetadataFactory<Map<String,Object>> metadataFactory = CSVWriterConfigurator.getMapToCSVWriterMetadataFactory();
    	
    	final CSVWriterFactory<Map<String,Object>> writerFactory = new CSVWriterFactoryImpl<Map<String,Object>>( metadataFactory );       
    	final CSVWriter<Map<String,Object>> csvWriter = writerFactory.getCSVWriter( writer, customHeader );
    	
    	Map<String,Object> model;
    	for( int i = 0; i < 100; ++i )
    	{
    		model = getNewMap();
    		csvWriter.write( model );
    	}
    	
    	csvWriter.close();
    	
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

    private Product getNewBean()
    {
        
        final long ts = System.currentTimeMillis();
        
        final Product model = new Product();
        
        model.setUpc( ts );
        model.setName( "Name: " + ts );
        model.setInStock( ts % 2 == 0 );
        model.setLastUpdate( new Date(ts) );
        model.setPrice( (float) Math.sqrt(ts) );
        model.setCurrency( Product.Currency.EUR );
        model.setDescription( "Description: " + ts );
        
        return model;
        
    }

    private Map<String,Object> getNewMap()
    {
        
        final long ts = System.currentTimeMillis();
        
        final Map<String,Object> model = new HashMap<String,Object>();
        
        model.put( "Upc", ts );
        model.put( "Name", "Name: " + ts );
        model.put( "InStock", ts % 2 == 0 );
        model.put( "LastUpdate", new Date(ts) );
        model.put( "Price", (float) Math.sqrt(ts) );
        model.put( "Currency", Product.Currency.EUR );
        model.put( "Description", "Description: " + ts );
        
        return model;
        
    }
    
    
    private Writer getCSVDestinationWriter() throws IOException
    {
        
        final File dest = new File( System.getProperty("java.io.tmpdir") + "/file_test.csv" );
        return new FileWriter( dest );
        
    }
    
}