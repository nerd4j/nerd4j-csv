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
import java.io.Reader;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.exception.MalformedCSVException;
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
		
		CSVParserMetadata metadata = new CSVParserMetadata();
		metadata.setRecordSeparator( new char[] {'\n'} );
		metadata.setMatchRecordSeparatorExactSequence( true );
		
		CSVParserFactory factory = new CSVParserFactory( metadata );
		
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
	
	/**
	 * Test a well formed quoted field.
	 */
	@Test
	public void quotedSuccess() throws IOException
	{
		
		final String string = "\"abc\",\"x\"\"yz\"";
		final StringReader reader = new StringReader( string );
		
		final CSVParserFactory factory = new CSVParserFactory();
		final CSVParser parser = factory.create( reader );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "x\"yz" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Test a well formed lazy-quoted field.
	 */
	@Test
	public void lazyQuotedSuccess() throws IOException
	{
		
		final String string = "\"abc\",\"x\"\"yz\"";
		final StringReader reader = new StringReader( string );
		final CSVParser parser = getLazyQuotedParser( reader );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "x\"yz" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	
	/**
	 * Test the presence of an unescaped quote in quoted field.
	 */
	@Test
	public void quotedUnescapedQuote() throws IOException
	{
		
		final String string = "\"abc\",\"x\"yz\"";
		final StringReader reader = new StringReader( string );
		
		final CSVParserFactory factory = new CSVParserFactory();
		final CSVParser parser = factory.create( reader );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		try{

			parser.read();
			Assert.fail( "Expected a MalformedCSVException" );
		
		}catch( MalformedCSVException ex )
		{}
		
	}
	
	
	/**
	 * Test the presence of an unescaped quote in lazy-quoted field.
	 */
	@Test
	public void lazyQuotedUnescapedQuote() throws IOException
	{
		
		final String string = "\"abc\",\"x\"yz\"";
		final StringReader reader = new StringReader( string );
		final CSVParser parser = getLazyQuotedParser( reader );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "abc" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "x\"yz" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	
	/**
	 * Test the presence of spaces after a quote in a quoted field.
	 */
	@Test
	public void quotedSpacesAfterQuote() throws IOException
	{
		
		final String string = "\"x\"\"  yz\"  ";
		final StringReader reader = new StringReader( string );
		
		final CSVParserFactory factory = new CSVParserFactory();
		final CSVParser parser = factory.create( reader );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "x\"  yz" , parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	
	/**
	 * Test the presence of spaces after a quote in a quoted field.
	 */
	@Test
	public void lazyQuotedSpacesAfterQuote() throws IOException
	{
		
		final String string = "\"x\"  yz\"  ";
		final StringReader reader = new StringReader( string );
		final CSVParser parser = getLazyQuotedParser( reader );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "x\"  yz", parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	

	/**
	 * Test presence of triple quotes in a quoted field.
	 */
	@Test
	public void quotedTripleQuote() throws IOException
	{
		
		final String string = "\"\"\"xyz\"\"\"";
		final StringReader reader = new StringReader( string );
		
		final CSVParserFactory factory = new CSVParserFactory();
		final CSVParser parser = factory.create( reader );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "\"xyz\"", parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Test presence of triple quotes in a quoted field.
	 */
	@Test
	public void lazyQuotedTripleQuote() throws IOException
	{
		
		final String string = "\"\"\"xyz\"\"\"";
		final StringReader reader = new StringReader( string );
		
		final CSVParserFactory factory = new CSVParserFactory();
		final CSVParser parser = factory.create( reader );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "\"xyz\"", parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Test a field made of only quoting characters.
	 */
	@Test
	public void quotedOnlyQuotes() throws IOException
	{
		
		final String fiveQuotes = "\"\"\"\"\"";
		final String sixQuotes = "\"\"\"\"\"\"";
		
		final StringReader fiveQuotesReader = new StringReader( fiveQuotes );
		final StringReader sixQuotesReader = new StringReader( sixQuotes );
		
		final CSVParserFactory factory = new CSVParserFactory();

		final CSVParser fiveQuotesParser = factory.create( fiveQuotesReader );
		final CSVParser sixQuotesParser = factory.create( sixQuotesReader );
		
		try{
		
			fiveQuotesParser.read();
			Assert.fail( "MalformedCSVException expected." );;

		}catch( MalformedCSVException ex )
		{}
		
		sixQuotesParser.read();
		Assert.assertEquals( CSVToken.FIELD, sixQuotesParser.getCurrentToken() );
		Assert.assertEquals( "\"\"", sixQuotesParser.getCurrentValue() );
		
		sixQuotesParser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , sixQuotesParser.getCurrentToken() );
		
		sixQuotesParser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , sixQuotesParser.getCurrentToken() );
		
		sixQuotesParser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , sixQuotesParser.getCurrentToken() );
		
	}
	
	/**
	 * Test a field made of only quoting characters.
	 */
	@Test
	public void lazyQuotedOnlyQuotes() throws IOException
	{
		
		final String fiveQuotes = "\"\"\"\"\"";
		final String sixQuotes = "\"\"\"\"\"\"";
		
		final StringReader fiveQuotesReader = new StringReader( fiveQuotes );
		final StringReader sixQuotesReader = new StringReader( sixQuotes );
		
		final CSVParser fiveQuotesParser = getLazyQuotedParser( fiveQuotesReader );
		final CSVParser sixQuotesParser = getLazyQuotedParser( sixQuotesReader );
		
		try{
			
			fiveQuotesParser.read();
			Assert.fail( "MalformedCSVException expected." );;
			
		}catch( MalformedCSVException ex )
		{}
		
		sixQuotesParser.read();
		Assert.assertEquals( CSVToken.FIELD, sixQuotesParser.getCurrentToken() );
		Assert.assertEquals( "\"\"", sixQuotesParser.getCurrentValue() );
		
		sixQuotesParser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , sixQuotesParser.getCurrentToken() );
		
		sixQuotesParser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , sixQuotesParser.getCurrentToken() );
		
		sixQuotesParser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , sixQuotesParser.getCurrentToken() );
		
	}
	
	/**
	 * Test presence of text after a quote (this version should throw ad exception).
	 */
	@Test
	public void quotedTextAfterAQuote() throws IOException
	{
		
		final String string = "\"15\" A\"";
		final StringReader reader = new StringReader( string );
		
		final CSVParserFactory factory = new CSVParserFactory();
		final CSVParser parser = factory.create( reader );
		
		try{
			
			parser.read();
			Assert.fail( "MalformedCSVException expected." );;
			
		}catch( MalformedCSVException ex )
		{}
		
	}
	
	/**
	 * Test presence of text after a quote (this version should treat the quote as normal text).
	 */
	@Test
	public void lazyQuotedTextAfterAQuote() throws IOException
	{
		
		final String string = "\"15\" A\",15\" A";
		final StringReader reader = new StringReader( string );
		final CSVParser parser = getLazyQuotedParser( reader );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "15\" A", parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.FIELD, parser.getCurrentToken() );
		Assert.assertEquals( "15\" A", parser.getCurrentValue() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_RECORD , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
		parser.read();
		Assert.assertEquals( CSVToken.END_OF_DATA , parser.getCurrentToken() );
		
	}
	
	/**
	 * Returns a parser with lazy quotes enabled.
	 */
	private CSVParser getLazyQuotedParser( Reader reader )
	{
		
		final CSVParserMetadata metadata = new CSVParserMetadata();
		metadata.setLazyQuotes( true );
		
		final CSVParserFactory factory = new CSVParserFactory( metadata );
		return factory.create( reader );
		
	}
	
}
