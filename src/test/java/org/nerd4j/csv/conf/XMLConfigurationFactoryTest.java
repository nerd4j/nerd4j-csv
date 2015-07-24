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
package org.nerd4j.csv.conf;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.conf.mapping.xml.XMLConfiguration;
import org.nerd4j.csv.conf.mapping.xml.XMLConfigurationFactory;
import org.nerd4j.test.BaseTest;

/**
 * {@link XMLConfigurationFactory} unit tests.
 * 
 * @author Nerd4j Team
 */
public class XMLConfigurationFactoryTest extends BaseTest
{
    
    @Test
	public void getCSVReaderConfiguration()
	{
        
        final String xml = "<csv:configuration xmlns:csv=\"http://www.nerd4j.org/csv\">" +
                              "<csv:reader name=\"reader\" binder=\"array\" model-binder=\"array\">" +
                                "<csv:columns>" +
                                  "<csv:column name=\"col-1\" index=\"1\" mapping=\"0\" optional=\"true\">" +
                                    "<csv:processor>" +
                                      "<csv:precondition type=\"checkStringLength\" min=\"2\" max=\"3\" />" +
                                      "<csv:converter type=\"parseEnum\" enum-type=\"org.nerd4j.csv.model.Product$Currency\" />" +
                                    "</csv:processor>" +
                                  "</csv:column>" +
                                "</csv:columns>" +
                              "</csv:reader>" +
                            "</csv:configuration>";

        final StringReader reader = new StringReader( xml );
        
        final XMLConfiguration configuration = XMLConfigurationFactory.load( reader );
        Assert.assertNotNull( configuration );
	    
	}
    
}