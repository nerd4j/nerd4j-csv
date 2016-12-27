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

import java.io.Reader;

import org.nerd4j.csv.RemarkableASCII;
import org.nerd4j.csv.exception.CSVConfigurationException;


/**
 * Factory of {@link CSVParser}.
 * 
 * <p>
 * Handles correct parsers configuration and creation.
 * 
 * <p>
 * Many useful notable characters are provided as public constants.
 * 
 * @author Nerd4j Team
 */
public final class CSVParserFactory
{
	
	/**
	 * Defines for each character in the ASCII space
	 * the related {@link CharacterClass} used while
	 * paring.
	 */
    private final int[] asciiCharClasses;
	
	/**
	 * Accept more lazy quotes: in unquoted fields are handled as
	 * {@link CharacterClass#NORMAL} without being escaped.
	 */
	private final boolean lazyQuotes;
	
	/** Sequence of characters that represents a record separator. */
	private final char[] recordSeparatorSequence;
	
	
	/* ******************** */
	/* *** CONSTRUCTORS *** */
	/* ******************** */
	
	
	/**
	 * Default constructor.
	 * 
	 */
	public CSVParserFactory()
	{
		
	    this( new CSVParserMetadata() );
		
	}
	
	
	/**
     * Constructor with parameters.
     * 
     * @param configuration the configuration to use to build the parsers.
     */
	public CSVParserFactory( CSVParserMetadata configuration )
	{
	    
		this.lazyQuotes = configuration.isLazyQuotes();
	    this.asciiCharClasses = new int[ RemarkableASCII.ASCII_TABLE_SIZE ];

	    for( char toIgnoreAround : configuration.getCharsToIgnoreAroundFields() )
	        addCharClass( toIgnoreAround, CharacterClass.TO_IGNORE_AROUND_FIELDS, "TO IGNORE AROUND FIELDS", false );
	    
	    for( char toIgnore : configuration.getCharsToIgnore() )
	    	addCharClass( toIgnore, CharacterClass.TO_IGNORE, "TO IGNORE", false );
	    
	    final char[] recordSeparators = configuration.getRecordSeparator();
	    if( recordSeparators == null || recordSeparators.length == 0 )
	    	throw new CSVConfigurationException( "The RECORD SEPARATOR characters are mandatory, at least one must be defined, check the configuration" );
	    	
	    for( char recordSeparator : recordSeparators )
	    	addCharClass( recordSeparator, CharacterClass.RECORD_SEPARATOR, "RECORD SEPARATOR", true );
	    
	    this.recordSeparatorSequence = configuration.isMatchRecordSeparatorExactSequence() ? recordSeparators : null;
	    
	    addCharClass( configuration.getQuoteChar(), CharacterClass.QUOTE, "QUOTE", true );
	    addCharClass( configuration.getEscapeChar(), CharacterClass.ESCAPE, "ESCAPE", false );
	    addCharClass( configuration.getFieldSeparator(), CharacterClass.FIELD_SEPARATOR, "FIELD SEPARATOR", true );
	    
	}
	
	
	
	/* ************************ */
	/* *** PUBLIC INTERFACE *** */
	/* ************************ */
	
	
	/**
	 * Create a new {@link CSVParser} to read data from given {@link Reader}.
	 * 
	 * @param reader CSV data source.
	 * @return the new created CSV parser.
	 */
	public CSVParser create( final Reader reader )
	{
		
		/*
		 * We don't need a buffered reader, the parser
		 * already handles his reading buffer.
		 */
		return new CSVParserImpl( reader, asciiCharClasses, recordSeparatorSequence, lazyQuotes );
		
	}
	
	
	/* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
	
	
	/**
	 * Checks the given character and throws an exception if the value is inconsistent.
	 * 
	 * @param character character to check.
	 * @param charClass character class to add.
	 * @param className character class name.
	 * @param mandatory tells if suck character is mandatory.
	 */
	private void addCharClass( final Character character, final int charClass, final String className, final boolean mandatory )
	{
	   
	    if( character == null )
	    {
	        if( mandatory)
	            throw new CSVConfigurationException( "The " + className + " character is mandatory and cant be null, check the configuration" );
	        else
	            return;
	    }
	    
	    final char charValue = character.charValue();
	    
	    /* 0: ASCII null character to */
        if( charValue < 0 || charValue >= RemarkableASCII.ASCII_TABLE_SIZE )
            throw new CSVConfigurationException( "Invalid " + className + " character, it must belong to ASCII space" );
     
        asciiCharClasses[charValue] = charClass;
        
	}
	
	
	/* *************** */
    /*  INNER CLASSES  */
    /* *************** */
    
    
    /**
     * Enumerates all character classes accepted by the parser (state machine).
     * Previously was an enum but we discovered that switching on constants
     * is actually faster.
     */
    static interface CharacterClass
    {
        
        /** Simple normal character without special meanings. */
        public static final int NORMAL                  = 0;
        
        /** Quoting character, used to enclose field into quotes if needed. */
        public static final int QUOTE                   = 2;
        
        /** Escape character, working like Java escape character '\'. */
        public static final int ESCAPE                  = 1;
        
        /** Character used to separate fields. */
        public static final int FIELD_SEPARATOR         = 3;
        
        /** Character used to separate records. */
        public static final int RECORD_SEPARATOR        = 4;
        
        /** Character to be ignored during parsing. */
        public static final int TO_IGNORE               = 7;
        
        /** Character to be ignored only if on heading or trailing of a field. */
        public static final int TO_IGNORE_AROUND_FIELDS = 8;
        
        
    }
	
}