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
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.formatter.CSVFormatter;
import org.nerd4j.csv.formatter.CSVFormatterFactory;
import org.nerd4j.csv.formatter.CSVFormatterMetadata;
import org.nerd4j.test.BaseTest;

/**
 * {@link CSVParser} unit tests.
 * 
 * @author Nerd4j Team
 */
public class CSVFormatterTest extends BaseTest
{
    
	/*  TEST GENERAL BEHAVIOR  */
    
	@Test
	public void generalNoQuote() throws IOException
	{
			    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( null, null, null );
	    final CSVFormatter formatter = factory.create( writer, false );
		
		/* First record. */
		
		formatter.writeField( "abcdefgh", false );
		formatter.flush();
		Assert.assertEquals( "abcdefgh", writer.toString() );
		
		formatter.writeField( "12345", false );
		formatter.flush();
		Assert.assertEquals( "abcdefgh,12345", writer.toString() );

		formatter.writeField( "", false );
		formatter.flush();
		Assert.assertEquals( "abcdefgh,12345,", writer.toString() );
		
		formatter.writeField( "abcdefgh", false );
		formatter.flush();
		Assert.assertEquals( "abcdefgh,12345,,abcdefgh", writer.toString() );

		formatter.writeField( "*+", false );
		formatter.flush();
		Assert.assertEquals( "abcdefgh,12345,,abcdefgh,*+", writer.toString() );

		formatter.writeEOR();
		formatter.flush();
		Assert.assertEquals( "abcdefgh,12345,,abcdefgh,*+\n", writer.toString() );
		
	}
	
	@Test
	public void generalQuoteAll() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( null, null, null );
	    final CSVFormatter formatter = factory.create( writer, true );
	    
	    /* First record. */
	    
	    formatter.writeField( "abcdefgh", false );
	    formatter.flush();
	    Assert.assertEquals( "\"abcdefgh\"", writer.toString() );
	    
	    formatter.writeField( "12345", false );
	    formatter.flush();
	    Assert.assertEquals( "\"abcdefgh\",\"12345\"", writer.toString() );
	    
	    formatter.writeField( "", false );
	    formatter.flush();
	    Assert.assertEquals( "\"abcdefgh\",\"12345\",\"\"", writer.toString() );
	    
	    formatter.writeField( "abcdefgh", false );
	    formatter.flush();
	    Assert.assertEquals( "\"abcdefgh\",\"12345\",\"\",\"abcdefgh\"", writer.toString() );
	    
	    formatter.writeField( "*+", false );
	    formatter.flush();
	    Assert.assertEquals( "\"abcdefgh\",\"12345\",\"\",\"abcdefgh\",\"*+\"", writer.toString() );
	    
	    formatter.writeEOR();
	    formatter.flush();
	    Assert.assertEquals( "\"abcdefgh\",\"12345\",\"\",\"abcdefgh\",\"*+\"\n", writer.toString() );
	    
	}
	
	
	/*  TEST QUOTE HANDLING  */
	
	@Test
	public void escapeQuote() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( '\\', new char[] {'"'}, null );
        final CSVFormatter formatter = factory.create( writer );
        
        formatter.writeField( "abc\"def\"gh", false );
        formatter.close();
        
        Assert.assertEquals( "abc\\\"def\\\"gh", writer.toString() );
        
    }

	@Test
	public void doubleQuote() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( null, null, new char[] {'"'} );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    formatter.writeField( "abc\"def\"gh", false );
	    formatter.close();
	    
	    Assert.assertEquals( "\"abc\"\"def\"\"gh\"", writer.toString() );
	    
	}

	@Test
	public void quoteAndEscapeQuote() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( '\\', new char[] {'"'}, new char[] {'"'} );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    formatter.writeField( "abc\"def\"gh", false );
	    formatter.close();
	    
	    Assert.assertEquals( "\"abc\\\"def\\\"gh\"", writer.toString() );
	    
	}

	
	/*  TEST ESCAPE HANDLING  */
	
	@Test
	public void doubleEscape() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( '\\', new char[] {'\\'}, null  );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    formatter.writeField( "abc\\def\\gh", false );
	    formatter.close();
	    
	    Assert.assertEquals( "abc\\\\def\\\\gh", writer.toString() );
	    
	}
	
	@Test
	public void quoteEscape() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( null, null, new char[] {'\\'} );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    formatter.writeField( "abc\\def\\gh", false );
	    formatter.close();
	    
	    Assert.assertEquals( "\"abc\\def\\gh\"", writer.toString() );
	    
	}
	
	
	/*  TEST ESCAPE HANDLING  */
	
	@Test
	public void escapeFieldSeparator() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( '\\', new char[] {','}, null  );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    formatter.writeField( "abcd,efgh", false );
	    formatter.close();
	    
	    Assert.assertEquals( "abcd\\,efgh", writer.toString() );
	    
	}
	
	@Test
	public void quoteFieldSeparator() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( null, null, new char[] {','} );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    formatter.writeField( "abcd,efgh", false );
	    formatter.close();
	    
	    Assert.assertEquals( "\"abcd,efgh\"", writer.toString() );
	    
	}

	@Test
	public void quoteAndEscapeFieldSeparator() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( '\\', new char[] {','}, new char[] {','} );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    formatter.writeField( "abcd,efgh", false );
	    formatter.close();
	    
	    Assert.assertEquals( "\"abcd\\,efgh\"", writer.toString() );
	    
	}
	
	
	/*  TEST ESCAPE HANDLING  */
	
	@Test
	public void escapeRecordSeparator() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( '\\', new char[] {'\n'}, null  );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    formatter.writeField( "abcd\nefgh", false );
	    formatter.close();
	    
	    Assert.assertEquals( "abcd\\\nefgh", writer.toString() );
	    
	}
	
	@Test
	public void quoteRecordSeparator() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( null, null, new char[] {'\n'} );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    formatter.writeField( "abcd\nefgh", false );
	    formatter.close();
	    
	    Assert.assertEquals( "\"abcd\nefgh\"", writer.toString() );
	    
	}
	
	@Test
	public void quoteAndEscapeRecordSeparator() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( '\\', new char[] {'\n'}, new char[] {'\n'} );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    formatter.writeField( "abcd\nefgh", false );
	    formatter.close();
	    
	    Assert.assertEquals( "\"abcd\\\nefgh\"", writer.toString() );
	    
	}
	
	
	/*  TEST UNICODE CHARACTHERS  */
	
	@Test
    public void handleUnicodeNotAsciiChars() throws IOException
    {
        
        final StringWriter writer = new StringWriter( 300 );
        final CSVFormatterFactory factory = getCSVFormatterFactory( '\\', new char[] {'\n'}, new char[] {'\n'} );
        final CSVFormatter formatter = factory.create( writer );
        
        formatter.writeField( "\uD834\uDD1E\uD834\uDD21\uD834\uDD22", false );
        formatter.close();
        
        Assert.assertEquals( "\uD834\uDD1E\uD834\uDD21\uD834\uDD22", writer.toString() );
        
    }
	
	@Test
	public void handleUnicodeNotAsciiCommaSeparatedChars() throws IOException
	{
	    
	    final StringWriter writer = new StringWriter( 300 );
	    final CSVFormatterFactory factory = getCSVFormatterFactory( '\\', new char[] {'\n'}, new char[] {'\n'} );
	    final CSVFormatter formatter = factory.create( writer );
	    
	    final String source = "\uD834\uDD1E,\uD834\uDD21,\uD834\uDD22";
	    
	    formatter.writeField( source, false );
	    formatter.close();
	    
	    Assert.assertEquals( "\"\uD834\uDD1E,\uD834\uDD21,\uD834\uDD22\"", writer.toString() );
	    
	    System.out.println( "ORIGINAL FIELD: " + source );
	    System.out.println( "WRITTEN FIELD: " + writer.toString() );
	}
	
	
	/* ***************** */
	/*  PRIVATE METHODS  */
	/* ***************** */
	
	
	/**
	 * Creates a new {@link CSVFormatterFactory} with the given properties.
	 * 
	 * @param escapeChar escape character.
	 * @param toEscape   characters to be escaped.
	 * @param forceQuote characters that forces quoting.
	 * @return the {@link CSVFormatterFactory}.
	 */
	private CSVFormatterFactory getCSVFormatterFactory( final Character escapeChar, final char[] toEscape, final char[] forceQuote )
	{
	    	    
	    final CSVFormatterMetadata configuration = new CSVFormatterMetadata();
	    configuration.setCharsThatForceQuoting( forceQuote );
	    configuration.setCharsToEscape( toEscape );
	    
	    if( escapeChar != null )
	        configuration.setEscapeChar( escapeChar );
	    	    
        return new CSVFormatterFactory( configuration );
        
	}
		
}