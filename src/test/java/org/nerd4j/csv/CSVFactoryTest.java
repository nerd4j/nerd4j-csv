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
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.conf.CSVConfigurationFactory;
import org.nerd4j.csv.conf.mapping.CSVConfiguration;
import org.nerd4j.csv.conf.mapping.xml.XMLConfiguration;
import org.nerd4j.csv.model.Product;
import org.nerd4j.csv.reader.CSVReaderFactory;
import org.nerd4j.test.BaseTest;

/**
 * {@link CSVFieldConverterFactory} unit tests.
 * 
 * @author Nerd4j Team
 */
public class CSVFactoryTest extends BaseTest
{
    
    @Test
	public void getCSVReaderFactory()
	{
        
        final String xml = "<csv:configuration xmlns:csv=\"http://www.nerd4j.org/csv\">" +
                             "<csv:reader name=\"reader\">" +
                               "<csv:model-binder type=\"array\" />" +
                               "<csv:columns>" +
                                 "<csv:column name=\"col-1\" mapping=\"0\" optional=\"true\">" +
                                   "<csv:processor>" +
                                     "<csv:precondition type=\"checkStringLength\" min=\"2\" max=\"3\" />" +
                                     "<csv:converter type=\"parseEnum\" enum-type=\"org.nerd4j.csv.model.Product$Currency\" />" +
                                   "</csv:processor>" +
                                 "</csv:column>" +
                               "</csv:columns>" +
                             "</csv:reader>" +
                           "</csv:configuration>";

        final StringReader reader = new StringReader( xml );
        
        final CSVConfiguration configuration = new CSVConfigurationFactory().getCSVConfiguration( reader );
        final CSVReaderFactory<?> readerFactory = new CSVFactory( configuration ).getCSVReaderFactory( "reader" );
        Assert.assertNotNull( readerFactory );
	    
	}
    
    
    @Test
    public void testInheritance()
    {
        
        final String xml = 
                "<csv:configuration xmlns:csv=\"http://www.nerd4j.org/csv\">" +
                  "<csv:reader name=\"reader1\" inherit=\"reader\">" +
                    "<csv:columns>" +
                      "<csv:column name=\"NAME\">" +
                        "<csv:processor>" +
                          "<csv:precondition type=\"checkStringLength\" min=\"2\" max=\"3\" />" +
                        "</csv:processor>" +
                      "</csv:column>" +
                    "</csv:columns>" +
                  "</csv:reader>" +
                  "<csv:reader name=\"reader\" inherit=\"org.nerd4j.csv.model.Product\">" +
                    "<csv:columns>" +
                      "<csv:column name=\"PRICE\">" +
                        "<csv:processor>" +
                          "<csv:precondition type=\"checkStringLength\" min=\"2\" max=\"3\" />" +
                        "</csv:processor>" +
                      "</csv:column>" +
                    "</csv:columns>" +
                  "</csv:reader>" +
                "</csv:configuration>";
        
        final StringReader reader = new StringReader( xml );
        
        final CSVConfiguration configuration = new CSVConfigurationFactory().getCSVConfiguration( reader, Product.class );
        final CSVReaderFactory<?> readerFactory = new CSVFactory( configuration ).getCSVReaderFactory( "reader" );
        Assert.assertNotNull( readerFactory );
        
    }
    
    
//    @Test
    public void generateXMLSchema() throws JAXBException, IOException
    {
    
        final JAXBContext jaxbContext = JAXBContext.newInstance( XMLConfiguration.class );
        final SchemaOutputResolver sor = new MySchemaOutputResolver();
        
        jaxbContext.generateSchema( sor );
        
    }


    private static class MySchemaOutputResolver extends SchemaOutputResolver
    {

        @Override
        public Result createOutput( String namespaceURI, String suggestedFileName )
        throws IOException
        {
            
            final File file = new File( suggestedFileName );
            final StreamResult result = new StreamResult( file );
            
            result.setSystemId( file.toURI().toURL().toString() );
            return result;
            
        }

    }
    
}