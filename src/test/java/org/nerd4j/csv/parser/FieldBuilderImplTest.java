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
package org.nerd4j.csv.parser;
import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.test.BaseTest;

/**
 * {@link DefaultFieldBuilder} unit tests.
 * 
 * @author Nerd4j Team
 */
public class FieldBuilderImplTest extends BaseTest
{

	@Test
	public void simple()
	{
		
		final FieldBuilder builder = new FieldBuilderImpl();
		
		builder.append( '0' );
		builder.append( '1' );
		builder.append( '2' );
		builder.append( '3' );
		
		Assert.assertEquals( "0123" , builder.toString() );
		Assert.assertEquals( 4 , builder.length() );
		
	}
	
	@Test
	public void markPositionValid()
	{
		
	    final FieldBuilder builder = new FieldBuilderImpl();
		
		builder.append( '0' );
		builder.append( '1' );
		builder.append( '2' );
		
		builder.mark();		
		builder.append( '3' );
		
		Assert.assertEquals( 3 , builder.getMarkedPosition() );
		Assert.assertEquals( 4 , builder.length() );
		
	}
	
	@Test
	public void markPositionInvalid()
	{
		
	    final FieldBuilder builder = new FieldBuilderImpl();
		
		builder.append( '0' );
		builder.append( '1' );
		
		builder.mark();
		builder.append( '2' );
		
		builder.append( '3' );
		
		Assert.assertEquals( -1 , builder.getMarkedPosition() );
		Assert.assertEquals( 4 , builder.length() );
		
	}
	
	@Test
	public void markPositionReset()
	{
		
	    final FieldBuilder builder = new FieldBuilderImpl();
		
		builder.append( '0' );
		builder.append( '1' );		
		builder.append( '2' );
		
		builder.mark();
		builder.append( '3' );
		
		builder.rollbackToMark();
		
		Assert.assertEquals( -1 , builder.getMarkedPosition() );
		Assert.assertEquals( 3 , builder.length() );
		
	}
	
	@Test
	public void markPositionNoMark()
	{
		
	    final FieldBuilder builder = new FieldBuilderImpl();
		
		builder.append( '0' );
		builder.append( '1' );
		builder.append( '2' );
		builder.append( '3' );
		
		Assert.assertEquals( -1 , builder.getMarkedPosition() );
		Assert.assertEquals( 4 , builder.length() );
		
	}
	
	@Test
	public void mark()
	{
		
	    final FieldBuilder builder = new FieldBuilderImpl();
		
		builder.mark();
		builder.append( '0' );
		
		builder.mark();
		builder.append( '1' );
		
		builder.append( '2' );
		builder.append( '3' );
		
		builder.rollbackToMark();
		
		Assert.assertEquals( "0123" , builder.toString() );
		Assert.assertEquals( 4 , builder.length() );
		
	}
	
	@Test
	public void markAll()
	{
		
	    final FieldBuilder builder = new FieldBuilderImpl();
		
		builder.mark();
		builder.append( '0' );
		
		builder.mark();
		builder.append( '1' );
		
		builder.mark();
		builder.append( '2' );
		
		builder.mark();
		builder.append( '3' );
		
		builder.rollbackToMark();
		
		Assert.assertEquals( "012" , builder.toString() );
		Assert.assertEquals( 3 , builder.length() );
		
	}

	@Test
	public void extendMark()
	{
		
		final FieldBuilder builder = new FieldBuilderImpl();
		
		builder.mark();
		builder.append( '0' );
		
		builder.extendMark();
		builder.append( '1' );
		
		builder.extendMark();
		builder.append( '2' );
		
		builder.extendMark();
		builder.append( '3' );
		
		builder.rollbackToMark();
		
		Assert.assertEquals( null , builder.toString() );
		Assert.assertEquals( 0 , builder.length() );
		
	}

	@Test
	public void clear()
	{
		
	    final FieldBuilder builder = new FieldBuilderImpl();
		
		builder.append( '0' );
		builder.append( '1' );
		builder.append( '2' );
		builder.append( '3' );
		
		builder.clear();
		
		Assert.assertEquals( null , builder.toString() );
		Assert.assertEquals( 0 , builder.length() );
		
	}
	
}
