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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.nerd4j.csv.exception.ModelToCSVBindingException;
import org.nerd4j.csv.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CSVWriterFactoryMockTest
{
    
    private static final Logger logger = LoggerFactory.getLogger( CSVWriterFactoryMockTest.class );
    
    
    @Test
    public void testArrayToCSVWriter() throws Exception
    {
        
        final Object[] model = getArrayModel();
        final CSVWriterMetadataFactory<Object[]> metadataFactory = CSVWriterConfigurator.getArrayToCSVWriterMetadataFactory();
        final CSVWriterFactory<Object[]> writerFactory = new CSVWriterFactoryImpl<Object[]>( metadataFactory );

        final StringWriter writer = new StringWriter( 300 );
        final CSVWriter<Object[]> csvWriter = writerFactory.getCSVWriter( writer );
        
        performWrite( writer, csvWriter, model );
        
    }
    
    @Test
    public void testMapToCSVWriter() throws Exception
    {
        
        final Map<String,Object> model = getMapModel();
        final CSVWriterMetadataFactory<Map<String,Object>> metadataFactory = CSVWriterConfigurator.getMapToCSVWriterMetadataFactory();
        final CSVWriterFactory<Map<String,Object>> writerFactory = new CSVWriterFactoryImpl<Map<String,Object>>( metadataFactory );
        
        final StringWriter writer = new StringWriter( 300 );
        final CSVWriter<Map<String,Object>> csvWriter = writerFactory.getCSVWriter( writer );
        
        performWrite( writer, csvWriter, model );
        
    }
    
    @Test
    public void testBeanToCSVWriter() throws Exception
    {
        
        final Product model = getBeanModel();
        final CSVWriterMetadataFactory<Product> metadataFactory = CSVWriterConfigurator.getBeanToCSVWriterMetadataFactory();
        final CSVWriterFactory<Product> writerFactory = new CSVWriterFactoryImpl<Product>( metadataFactory );
        
        final StringWriter writer = new StringWriter( 300 );
        final CSVWriter<Product> csvWriter = writerFactory.getCSVWriter( writer );
        
        performWrite( writer, csvWriter, model );
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
    
    
    private Object[] getArrayModel()
    {
        return new Object[] { "name", "description", new Long(System.currentTimeMillis()), Product.Currency.EUR, new Float(33.33), Boolean.FALSE, new Date() };
        
    }
    
    private Map<String,Object> getMapModel()
    {
        
        final Map<String,Object> model = new HashMap<String,Object>();
        model.put( "Name", "name" );
        model.put( "Description", "description" );
        model.put( "Upc", new Long(System.currentTimeMillis()) );
        model.put( "Currency", Product.Currency.EUR );
        model.put( "Price", new Float(33.33) );
        model.put( "InStock", Boolean.FALSE );
        model.put( "LastUpdate", new Date() );
        
        return model;
        
    }
    
    
    private Product getBeanModel()
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
    
    
    private <M> void performWrite( StringWriter writer, CSVWriter<M> csvWriter, M model )
    {
        
        try{
        
            csvWriter.write( model );        
            logger.info( writer.toString() );
            
        }catch( IOException ex )
        {
            
            logger.error( "Error in writing", ex );
            
        }catch( ModelToCSVBindingException ex )
        {
            
            logger.error( "Error in writing", ex );
            
        }
        
    }
    
}