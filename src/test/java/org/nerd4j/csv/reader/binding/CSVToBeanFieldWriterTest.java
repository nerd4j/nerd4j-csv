/*
 * #%L
 * Nerd4j CSV
 * %%
 * Copyright (C) 2013 - 2016 Nerd4j
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
package org.nerd4j.csv.reader.binding;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.nerd4j.csv.model.Product;
import org.nerd4j.util.ReflectionUtil;

public class CSVToBeanFieldWriterTest
{

	@Test
	public void writeBySetter()
	{
	
		final Product product = new Product();
		assertNull( product.getName() );
		
		final Method setter = ReflectionUtil.findPublicSetter( "name", Product.class );
		assertNotNull( setter );
		
		final CSVToBeanFieldWriter writer = CSVToBeanFieldWriter.getWriter( setter );
		assertNotNull( writer );
		
		System.out.println( "Found writer: " + writer.getName() );
		
		try{
		
			writer.write( "Name", product );
			assertEquals( "Name", product.getName() );
			
		}catch( Exception ex )
		{
			fail();
		}
		
	}
	
	@Test
	public void writeByField()
	{
		
		final Product product = new Product();
		assertNull( product.getName() );
		
		final Field field = ReflectionUtil.findField( Void.class, "name", Product.class );
		assertNotNull( field );
		
		final CSVToBeanFieldWriter writer = CSVToBeanFieldWriter.getWriter( field );
		assertNotNull( writer );
		
		System.out.println( "Found writer: " + writer.getName() );
		
		try{
			
			writer.write( "Name", product );
			assertEquals( "Name", product.getName() );
			
		}catch( Exception ex )
		{
			fail();
		}
		
	}

}
