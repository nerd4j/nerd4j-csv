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
package org.nerd4j.csv;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.conf.CSVConfigurationFactory;
import org.nerd4j.csv.conf.mapping.CSVConfiguration;
import org.nerd4j.csv.model.Product;
import org.nerd4j.csv.reader.CSVReader;
import org.nerd4j.csv.reader.CSVReaderFactory;


public class CSVReaderTest
{


    @Test
    public void testCSVReaderFromXML()
    {
        
        
        final InputStream is = getClass().getResourceAsStream( "/test-conf.xml" );
        final Reader confReader = new InputStreamReader( is );
        
        final CSVConfiguration configuration = new CSVConfigurationFactory().getCSVConfiguration( confReader );
        final CSVFactory csvFactory = new CSVFactory( configuration );
        
        final CSVReaderFactory<?> readerFactory = csvFactory.getCSVReaderFactory( "csvReader" );
        Assert.assertNotNull( readerFactory );
        
    }
    
    @Test
    public void testCSVReaderFromAnnotation() throws Exception
    {
        
        final CSVConfiguration configuration = new CSVConfigurationFactory().getCSVConfiguration( Product.class );
        final CSVFactory csvFactory = new CSVFactory( configuration );
        
        final CSVReaderFactory<Product> readerFactory = csvFactory.getCSVReaderFactory( Product.class.getName() );
        final CSVReader<Product> reader = readerFactory.getCSVReader( new StringReader("NAME,DESCRIPTION,UPC,CURRENCY,PRICE,IN-STOCK,LAST-UPDATE") );
        
        Assert.assertNotNull( reader );
        
    }
        
}
