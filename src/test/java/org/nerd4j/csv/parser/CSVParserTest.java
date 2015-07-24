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
import java.io.IOException;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.test.BaseTest;

/**
 * {@link CSVParser} unit tests.
 * 
 * @author Nerd4j Team
 */
public class CSVParserTest extends BaseTest
{

	/**
	 * Simple test that read through a complete simple CSV.
	 */
	@Test
	public void general() throws IOException
	{
		
		CSVParserFactory factory = new CSVParserFactory();
		
		String string = 
				"abcdefgh,12345,,abcdefgh,*+\n"
				+ "abcdefgh,12345,,abcdefgh,*+\n"
				;
		
//		String string = "";
		
		StringReader reader = new StringReader( string );
		
		CSVParser parser = factory.create( reader );
		
		/* First record. */
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abcdefgh" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "12345" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( null , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abcdefgh" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "*+" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		
		/* Second record. */
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abcdefgh" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "12345" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( null , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abcdefgh" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "*+" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Test line:
	 *    Field FieldSeparator Field RecordSeparator EOF
	 */
	@Test
	public void fieldFSfieldRSEOF() throws IOException
	{
		
		CSVParserFactory factory = new CSVParserFactory();
		
		String string = "abc,xyz\n";
		
		StringReader reader = new StringReader( string );
		
		CSVParser parser = factory.create(reader);
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "xyz" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Test line:
	 *    Field FieldSeparator Field(empty) RecordSeparator EOF
	 */
	@Test
	public void fieldFSfieldEmptyRSEOF() throws IOException
	{
		
		CSVParserFactory factory = new CSVParserFactory();
		
		String string = "abc,\n";
		
		StringReader reader = new StringReader( string );
		
		CSVParser parser = factory.create(reader);
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( null , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Test line:
	 *    Field FieldSeparator Field EOF
	 */
	@Test
	public void fieldFSfieldEOF() throws IOException
	{
		
		CSVParserFactory factory = new CSVParserFactory();
		
		String string = "abc,xyz";
		
		StringReader reader = new StringReader( string );
		
		CSVParser parser = factory.create(reader);
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "xyz" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Test line:
	 *    Field FieldSeparator Field(empty) EOF
	 */
	@Test
	public void fieldFSfieldEmptyEOF() throws IOException
	{
		
		CSVParserFactory factory = new CSVParserFactory();
		
		String string = "abc,";
		
		StringReader reader = new StringReader( string );
		
		CSVParser parser = factory.create(reader);
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( null , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Test line:
	 *    Field RecordSeparator Field RecordSeparator EOF
	 */
	@Test
	public void fieldRSfieldRSEOF() throws IOException
	{
		
		CSVParserFactory factory = new CSVParserFactory();
		
		String string = "abc\nxyz\n";
		
		StringReader reader = new StringReader( string );
		
		CSVParser parser = factory.create(reader);
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "xyz" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Test line:
	 *    Field RecordSeparator Field(empty) RecordSeparator EOF
	 */
	@Test
	public void fieldRSfieldEmptyRSEOF() throws IOException
	{
		
		CSVParserFactory factory = new CSVParserFactory();
		
		String string = "abc\n\n";
		
		StringReader reader = new StringReader( string );
		
		CSVParser parser = factory.create(reader);
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( null , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	
	/**
	 * Test line:
	 *    Field RecordSeparator Field EOF
	 */
	@Test
	public void fieldRSfieldEOF() throws IOException
	{
		
		CSVParserFactory factory = new CSVParserFactory();
		
		String string = "abc\nxyz";
		
		StringReader reader = new StringReader( string );
		
		CSVParser parser = factory.create(reader);
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( "xyz" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Test line:
	 *    Field RecordSeparator Field EOF
	 */
	@Test
	public void skipFieldRSfieldEOF() throws IOException
	{
		
		CSVParserFactory factory = new CSVParserFactory();
		
		String string = "abc\nxyz";
		
		StringReader reader = new StringReader( string );
		
		CSVParser parser = factory.create(reader);
		
		parser.skip();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( null , parser.getCurrentValue() );
		
		parser.skip();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.skip();
		Assert.assertEquals( CSVToken.FIELD , parser.getCurrentToken() );
		Assert.assertEquals( null , parser.getCurrentValue() );
		
		parser.skip();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.skip();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.skip();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
}
