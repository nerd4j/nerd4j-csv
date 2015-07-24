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

import org.junit.Test;
import org.junit.Assert;

import org.nerd4j.test.BaseTest;

import org.nerd4j.csv.conf.mapping.CSVConfiguration;
import org.nerd4j.csv.conf.mapping.ann.AnnotatedConfigurationFactory;
import org.nerd4j.csv.model.Product;

/**
 * {@link AnnotatedConfigurationFactory} unit tests.
 * 
 * @author Nerd4j Team
 */
public class AnnotatedConfigurationFactoryTest extends BaseTest
{
    
    @Test
	public void getCSVReaderConfiguration()
	{
                     
        final CSVConfiguration configuration = new CSVConfiguration(); 
        AnnotatedConfigurationFactory.merge( Product.class, configuration );
	    
	}
    
    @Test
    public void testClone() throws CloneNotSupportedException
    {
        
        final CSVConfiguration original = new CSVConfiguration(); 
        AnnotatedConfigurationFactory.merge( Product.class, original );
        
        final CSVConfiguration clone = original.clone();
        
        Assert.assertNotSame( original, clone );
        
    }

}