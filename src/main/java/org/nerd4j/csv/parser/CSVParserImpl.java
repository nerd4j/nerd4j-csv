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
import java.util.LinkedList;
import java.util.Queue;

import org.nerd4j.csv.RemarkableASCII;
import org.nerd4j.csv.exception.MalformedCSVException;
import org.nerd4j.csv.parser.CSVParserFactory.CharacterClass;

/**
 * <tt>CSVParser</tt> reads character data from a {@link Reader}, parsing and
 * tokenizing them into {@link CSVToken}.
 * 
 * <p>
 *  After usage it should be closed to permit resource release.
 * </p>
 * 
 * @author Nerd4j Team
 */
final class CSVParserImpl implements CSVParser
{
	
	/** Size of {@link #buffer reading char buffer}, 8MB. */
	private static final int BUFFER_SIZE = 1024 * 1024 * 8;
	
	/** {@link CharacterClass} mappings from {@link #characterConfiguration}. */
	private final int[] types;
	
	/**
	 * Accept more lazy quotes: in unquoted fields ({@link FieldState#NORMAL}
	 * and {@link FieldState#NORMAL_END}) are handled as
	 * {@link CharacterClass#NORMAL} without being escaped
	 */
	private final boolean lazyQuotes;
	
	/** CSV data source reader. */
	private final Reader reader;
	
	/** Shared field builder. */
	private final FieldBuilder builder;
	
	/** Buffer from which read characters. Populated from {@link #reader} */
	private final char[] buffer;
	
	/** The strategy to use in case a record separator character is read. */
	private final RecordSeparatorStrategy recordSeparatorStrategy;
	
	/** Next character to be read from the {@link #buffer reading char buffer}. */
	private int bufferIndex;
	
	/**
	 * Number of characters currently hold by the {@link #buffer reading char
	 * buffer}. Normally equals to {@link #BUFFER_SIZE} but the last buffer read
	 * before completely emptying the {@link #reader}.
	 */
	private int bufferElements;
	
	/** Last read field termination reason. */
	private FieldEndReason previousFieldEndReason;
	
	/** Current read token. */
	private CSVToken token;
	
	/**
	 * Current token value, if any. As string to avoid to invoke
	 * {@link FieldBuilder#toString()} multiple times.
	 */
	private String value;

	/** Counts the actually read characters. */
	private int charCount;
	
	/**
	 * Next read tokens, if any. Needed when a field terminates with a record
	 * separator or a end of data.
	 */
	private Queue<CSVToken> nexts;
	
	
	/* ******************** */
	/* *** CONSTRUCTORS *** */
	/* ******************** */
	
	/**
	 * Create a new {@link CSVParserImpl} with the given character configuration.
	 * 
	 * @param reader csv data source.
	 * @param types  character classes configuration.
	 * @param recordSeparator the record separator sequence.
	 * @param lazyQuotes tells if quotes inside text are admitted.
	 */
	CSVParserImpl( final Reader reader, final int[] types, final char[] recordSeparator, boolean lazyQuotes )
	{
		
		this.reader = reader;
		
		this.types = types;
		this.lazyQuotes = lazyQuotes ;
		
		this.nexts = new LinkedList<CSVToken>();

		this.bufferElements  = 0;
		this.bufferIndex = BUFFER_SIZE;
		this.buffer = new char[ BUFFER_SIZE ];
		
		this.token = null;
		this.value = null;
		
		this.charCount = 0;		
		this.builder = new FieldBuilderImpl( 1024 );
		this.previousFieldEndReason = FieldEndReason.UNKNOWN;
		
		this.recordSeparatorStrategy = recordSeparator != null && recordSeparator.length > 0
				                     ? new MatchSeparatorSequenceStrategy( recordSeparator )
									 : new MatchAnySeparatorCharStrategy();
		
	}
	
	
	/* ************************ */
	/* *** PUBLIC INTERFACE *** */
	/* ************************ */
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CSVToken getCurrentToken()
	{
		
		return token;
		
	}
	
	/**
     * {@inheritDoc}
     */
    @Override
	public String getCurrentValue()
	{
		
		return value;
		
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
	public CSVToken read() throws IOException
	{
		
		/* Evaluate and return current token. */
		return token = readField( true );
		
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
	public CSVToken skip() throws IOException
	{
		
		/* Evaluate and return current token (dummy field builder). */
		return token = readField( false );
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException
	{
		
		reader.close();
		nexts.clear();
		
		token = null;
		value = null;
		
	}
	
	
	/* ****************************** */
	/* *** PRIVATE IMPLEMENTATION *** */
	/* ****************************** */
	
	
	/**
	 * Enumerates all possible field parsing state (state machine). Previously
	 * was an enum but we discovered that switching on constants is really
	 * faster.
	 */
	private interface FieldState
	{
		
		/** Starting state, only to be ignore chars has been read. */
		public static final int INITIAL       = 0 << 15;
		
		/** Reading a not quoted field. */
		public static final int NORMAL        = 1 << 15;
		
		/** Reading escape on a not quoted field. */
		public static final int NORMAL_ESCAPE = 2 << 15;
		
		/** Trying to read a not quoted field termination. */
		public static final int NORMAL_END    = 3 << 15;
		
		/** Reading a quoted field. */
		public static final int QUOTED        = 4 << 15;
		
		/** Reading escape on a quoted field. */
		public static final int QUOTED_ESCAPE = 5 << 15;
		
		/** Trying to read a quoted field termination. */
		public static final int QUOTED_END    = 6 << 15;
		
		/** Reading double quote on a quoted field. */
		public static final int DOUBLE_QUOTE  = 7 << 15;
		
	}
	
	/**
	 * Enumerates <i>end of field</i> reasons (all possible ways for a field to
	 * terminate).
	 */
	private enum FieldEndReason
	{
	    
	    /** Before ending to read any field the end reason in unknown. */
	    UNKNOWN,
		
		/** Found a field separator: {@link CharacterClass#FIELD_SEPARATOR}. */
		FIELD_SEPARATOR,
		
		/**
		 * Found a record separator
		 * {@link CharacterClass#RECORD_SEPARATOR},
		 * {@link CharacterClass#RECORD_SEPARATOR_1},
		 * {@link CharacterClass#RECORD_SEPARATOR_2}.
		 */
		RECORD_SEPARATOR,
		
		/** Reached data reader end. */
		DATA_END;
		
	}
	
	/**
	 * Read the next {@link CSVToken} and returns it.
	 * <p>
	 * Given {@link FieldBuilder} will be used as support while reading
	 * data and cleaned ({@link FieldBuilder#clear()}) before returning.
	 * </p>
	 * 
	 * @param read tells if to actually read the field or to skip it.
	 * @return next read {@link CSVToken}.
	 * @throws IOException if an error occurs while parsing data.
	 */
	private CSVToken readField( final boolean read ) throws IOException
	{
		
		/* Check if already reached data end. */
		if( token == CSVToken.END_OF_DATA )
			return CSVToken.END_OF_DATA;
		
		/* Clear previous iteration data. */
		value = null;
		charCount = 0;
		
		/* Currently evaluated token. */
		CSVToken token;
		
		/* Check if there is a next token to returns instead of reading another one. */
		if( ! nexts.isEmpty() )
		{
			
			/* Next can't be a field. */
			token = nexts.poll();
			return token;
			
		}
		
		/* Parse a new field and get the reason why the read ended. */
		final FieldEndReason currentFieldEndReason = parseField( read );
		
	    /* Setup read value (if any) and clear field builder. */
		if( read )
		{
		    value = builder.toString();
		    builder.clear();
		}
		
		/* Manipulate FieldEndReason to generate CSVTokens. */
		switch ( currentFieldEndReason )
		{
		
		    /* The end of the stream has been read. */
			case DATA_END:
				
				switch( previousFieldEndReason )
				{
				
				    /* 
				     * If the previous end reason in unknown it means that
				     * the CSV source is empty.
				     */
				    case UNKNOWN:
				        
				        token = CSVToken.END_OF_DATA;
				        break;

				        
				    /*
				     * If the previous read ended with a record separator
				     * we can have two situations:
				     * 
				     * 1. The standard case of a CSV source that ends after
				     *    the last record has ended properly.
				     *    
				     * 2. The case of a single column CSV where the last
				     *    row ends without a proper record separator.
				     */
                    case RECORD_SEPARATOR:
                        
                        /*
                         * In the case of a standard well formed CSV (first case)
                         * the last value read is empty. In this case we just
                         * notify an END_OF_DATA.
                         */
                        if( charCount < 2 )
                        {                            
                            token = CSVToken.END_OF_DATA;
                            break;
                        }
                        
				        /*
				         * In the case of a single column CSV that ends
				         * in a non standard way we behave in the same
				         * way as when we read a FIELD_SEPARATOR.
				         */
				        
					case FIELD_SEPARATOR:
						
						/*
						 * In this case we can end with an empty
						 * field or with a valued field, in both
						 * cases we notify that we have read a 
						 * FIELD than we and the reading.
						 */
						token = CSVToken.FIELD;
						
						nexts.add( CSVToken.END_OF_RECORD );
						nexts.add( CSVToken.END_OF_DATA );
						
						break;
						

					/*
					 * This case can never be reached due to the
					 * check at the beginning of the method.
					 * But just in case we behave as expected.
					 */
					case DATA_END:
						token = CSVToken.END_OF_DATA;
						break;
						
					default:
						throw new IllegalStateException( "Unknown reason: " + currentFieldEndReason + ". This is a bug evidence." );
						
				}
				
				break;

			
			/*
			 * A record separator has been read.
			 * In this case we always consider to read
			 * at least one FIELD as well. Even if the
			 * RECORD_SEPARATOR is at the beginning of
			 * the CSV source it means that we read an
			 * empty field.
			 */
			case RECORD_SEPARATOR:
			    /*
			     * In this case we store the fact that
			     * an END_OF_RECORD has been read for
			     * future use and behave in the same
                 * way as when we read a FIELD_SEPARATOR.
			     */
				nexts.add( CSVToken.END_OF_RECORD );				
				
			/* A field separator has been read. */
			case FIELD_SEPARATOR:
			    /*
			     * In this case we just notify that
			     * a FIELD has been read.
			     */
			    token = CSVToken.FIELD;
			    break;
				
			default:
				throw new IllegalStateException( "Unknown reason: " + currentFieldEndReason + ". This is a bug evidence." );
				
		}
		
		/* Save current stop reason for the next iteration. */
		previousFieldEndReason = currentFieldEndReason;
		
		/* Return evaluated token. */
		return token;
		
	}
	
	/**
	 * Read and parse a field from {@link #reader} and write it on a
	 * {@link FieldBuilder}.
	 * 
	 * @param read tells if to actually read the field or to skip it.
	 * @return reason of field end.
	 * 
	 * @throws IOException if an error occurs while reading characters.
	 */
	private FieldEndReason parseField( final boolean read ) throws IOException
	{
		
		/*
		 * Note that using only ASCII special character no special character is
		 * outside Unicode BMP (thus is only 2 byte long).
		 * 
		 * Characters outside BMP will be handled as non special chars and
		 * simply written (so a multi-char character won't be corrupted).
		 */
		
		/* Read character as int. */
		char current;
		
		/* Read character type. */
		int type;
		
		/* Current field state. */
		int state = FieldState.INITIAL;
		
		/* Loop on characters until an exit case is found. */
		while ( true )
		{
			
			/*
			 * Check if buffer need to be refilled. Checking on BUFFER_SIZE
			 * instead of bufferElements because is faster on a constant value
			 * AND if bufferElements is less than BUFFER_SIZE it can't be
			 * refilled (data already completely read into buffer)
			 */
			if ( bufferIndex >= BUFFER_SIZE )
			{
				
				bufferElements = reader.read( buffer, 0 , BUFFER_SIZE );
				bufferIndex = 0;
				
			}
			
			if( bufferElements <= 0 || bufferIndex >= bufferElements )
				/* Got an end of data. */
				switch ( state )
				{
				
				    case FieldState.NORMAL_END:
				    case FieldState.QUOTED_END:
				        if( read ) builder.rollbackToMark();
				    
				    case FieldState.NORMAL:
					case FieldState.INITIAL:
					case FieldState.DOUBLE_QUOTE:
						/* Normal field termination. */
						return FieldEndReason.DATA_END;
						
					case FieldState.NORMAL_ESCAPE:
						/* Current field has an unterminated escape. */
						throw new MalformedCSVException( "Solitary escape at end of data." );
						
					case FieldState.QUOTED:
						/* Current field has an unclosed quote. */
						throw new MalformedCSVException( "Unclosed quoted field at end of data." );
						
					case FieldState.QUOTED_ESCAPE:
						/* Current field has an unclosed quote and an unterminated escape. */
						throw new MalformedCSVException( "Unclosed quoted field and solitary escape at end of data." );
					
					default:
						throw new IllegalStateException( "Unknown state: " + state + ". This is a bug evidence." );
						
				}
			
			/* Read current character. */
			current = buffer[bufferIndex++];
			
			/* Find the current character class. */
			if( current < RemarkableASCII.ASCII_TABLE_SIZE )
				type =  types[ current ];
			else
				type = CharacterClass.NORMAL;
			
			/*
			 * We count also the characters to ignore 
			 * to know if the field is actually empty. 
			 */
			++ charCount;

			
			/* Handle current character depending on his class. */
			switch ( state ^ type )
			{
			
				/* ************************** */
				/* *** FieldState.INITIAL *** */
				/* ************************** */
				
				case FieldState.INITIAL ^ CharacterClass.NORMAL:
				    if( read ) builder.append( current );
				    state = FieldState.NORMAL;
					break;
					
				case FieldState.INITIAL ^ CharacterClass.TO_IGNORE:
					break;
					
				case FieldState.INITIAL ^ CharacterClass.TO_IGNORE_AROUND_FIELDS:
					break;
					
				case FieldState.INITIAL ^ CharacterClass.QUOTE:
					state = FieldState.QUOTED;
					break;
					
				case FieldState.INITIAL ^ CharacterClass.ESCAPE:
					state = FieldState.NORMAL_ESCAPE;
					break;
					
				case FieldState.INITIAL ^ CharacterClass.FIELD_SEPARATOR:
					return FieldEndReason.FIELD_SEPARATOR;
					
				case FieldState.INITIAL ^ CharacterClass.RECORD_SEPARATOR:
					if ( recordSeparatorStrategy.apply(read) )
						return FieldEndReason.RECORD_SEPARATOR;
						
					/* Handle as CharacterClass.NORMAL */
					state = FieldState.NORMAL;
					break;
					
//				case FieldState.INITIAL ^ CharacterClass.RECORD_SEPARATOR_1:
//					if ( checkRecordSequence( current ) )
//						return FieldEndReason.RECORD_SEPARATOR;
						
//					/* Handle as CharacterClass.NORMAL */
//					if( read ) builder.append( current );
//					state = FieldState.NORMAL;
//					break;
					
//				case FieldState.INITIAL ^ CharacterClass.RECORD_SEPARATOR_2:
//					/* Handle as CharacterClass.NORMAL */
//				    if( read ) builder.append( current );
//				    state = FieldState.NORMAL;
//					break;
					
				/* ************************* */
				/* *** FieldState.NORMAL *** */
				/* ************************* */
				
				case FieldState.NORMAL ^ CharacterClass.NORMAL:
				    if( read ) builder.append( current );
					break;
					
				case FieldState.NORMAL ^ CharacterClass.TO_IGNORE:
					break;
					
				case FieldState.NORMAL ^ CharacterClass.TO_IGNORE_AROUND_FIELDS:
					state = FieldState.NORMAL_END;
					
					if( read ) 
					{
					    builder.mark();
					    builder.append( current );
					}
					break;
					
				case FieldState.NORMAL ^ CharacterClass.QUOTE:
					/* Current field is unquoted but contains a not escaped quote. */
					
					/* If quotes have to be handled less strictly */
					if ( lazyQuotes )
					{
						/* Handle as CharacterClass.NORMAL */
						if( read ) builder.append( current );
						break;
					}
					
					throw new MalformedCSVException( "Encountered an unescaped quote in a unquoted field." );
					
				case FieldState.NORMAL ^ CharacterClass.ESCAPE:
					state = FieldState.NORMAL_ESCAPE;
					break;
					
				case FieldState.NORMAL ^ CharacterClass.FIELD_SEPARATOR:
					return FieldEndReason.FIELD_SEPARATOR;
					
				case FieldState.NORMAL ^ CharacterClass.RECORD_SEPARATOR:
					if ( recordSeparatorStrategy.apply(read) )
						return FieldEndReason.RECORD_SEPARATOR;
						
					/* Handle as CharacterClass.NORMAL */
					state = FieldState.NORMAL;
					break;
					
//				case FieldState.NORMAL ^ CharacterClass.RECORD_SEPARATOR_1:
//					if ( checkRecordSequence( current ) )
//						return FieldEndReason.RECORD_SEPARATOR;
//						
//					/* Handle as CharacterClass.NORMAL */
//					if( read ) builder.append( current );
//					break;
//					
//				case FieldState.NORMAL ^ CharacterClass.RECORD_SEPARATOR_2:
//					/* Handle as CharacterClass.NORMAL */
//				    if( read ) builder.append( current );
//					break;
				
				/* ******************************** */
				/* *** FieldState.NORMAL_ESCAPE *** */
				/* ******************************** */
				
				case FieldState.NORMAL_ESCAPE ^ CharacterClass.NORMAL:
					state = FieldState.NORMAL;
					if( read ) builder.append( current );
					break;
					
				case FieldState.NORMAL_ESCAPE ^ CharacterClass.TO_IGNORE:
					state = FieldState.NORMAL;
					if( read ) builder.append( current );
					break;
					
				case FieldState.NORMAL_ESCAPE ^ CharacterClass.TO_IGNORE_AROUND_FIELDS:
					state = FieldState.NORMAL;
					if( read ) builder.append( current );
					break;
					
				case FieldState.NORMAL_ESCAPE ^ CharacterClass.QUOTE:
					state = FieldState.NORMAL;
					if( read ) builder.append( current );
					break;
					
				case FieldState.NORMAL_ESCAPE ^ CharacterClass.ESCAPE:
					state = FieldState.NORMAL;
					if( read ) builder.append( current );
					break;
					
				case FieldState.NORMAL_ESCAPE ^ CharacterClass.FIELD_SEPARATOR:
					state = FieldState.NORMAL;
					if( read ) builder.append( current );
					break;
					
				case FieldState.NORMAL_ESCAPE ^ CharacterClass.RECORD_SEPARATOR:
					state = FieldState.NORMAL;
					if( read ) builder.append( current );
					break;
					
//				case FieldState.NORMAL_ESCAPE ^ CharacterClass.RECORD_SEPARATOR_1:
//					state = FieldState.NORMAL;
//					if( read ) builder.append( current );
//					break;
//					
//				case FieldState.NORMAL_ESCAPE ^ CharacterClass.RECORD_SEPARATOR_2:
//					/* Handle as CharacterClass.NORMAL */
//					state = FieldState.NORMAL;
//					if( read ) builder.append( current );
//					break;
					
				/* ***************************** */
				/* *** FieldState.NORMAL_END *** */
				/* ***************************** */
				
				case FieldState.NORMAL_END ^ CharacterClass.NORMAL:
					state = FieldState.NORMAL;
					if( read ) builder.append( current );
					break;
					
				case FieldState.NORMAL_END ^ CharacterClass.TO_IGNORE:
					break;
					
				case FieldState.NORMAL_END ^ CharacterClass.TO_IGNORE_AROUND_FIELDS:
				    if( read ) 
				    {
				        builder.mark();
				        builder.append( current );
				    }
					break;
					
				case FieldState.NORMAL_END ^ CharacterClass.QUOTE:
					/* Current field is unquoted but contains a not escaped quote. */
					
					/* If quotes have to be handled less strictly */
					if ( lazyQuotes )
					{
						/* Handle as CharacterClass.NORMAL */
						state = FieldState.NORMAL;
						if( read ) builder.append( current );
						break;
					}
						
				    if( read ) builder.rollbackToMark();
					throw new MalformedCSVException( "Encountered an unescaped quote in a unquoted field." );
					
				case FieldState.NORMAL_END ^ CharacterClass.ESCAPE:
					state = FieldState.NORMAL_ESCAPE;
					break;
					
				case FieldState.NORMAL_END ^ CharacterClass.FIELD_SEPARATOR:
				    if( read ) builder.rollbackToMark();
					return FieldEndReason.FIELD_SEPARATOR;
					
				case FieldState.NORMAL_END ^ CharacterClass.RECORD_SEPARATOR:
				    if( recordSeparatorStrategy.apply(read) )
				    {					
				    	if( read ) builder.rollbackToMark();
				    	return FieldEndReason.RECORD_SEPARATOR;
				    }
					
				    /* Handle as CharacterClass.NORMAL */
					state = FieldState.NORMAL;
					break;
					
//				case FieldState.NORMAL_END ^ CharacterClass.RECORD_SEPARATOR_1:
//					if ( checkRecordSequence( current ) )
//						return FieldEndReason.RECORD_SEPARATOR;
//						
//					/* Handle as CharacterClass.NORMAL */
//					state = FieldState.NORMAL;
//					if( read ) builder.append( current );
//					break;
//					
//				case FieldState.NORMAL_END ^ CharacterClass.RECORD_SEPARATOR_2:
//					/* Handle as CharacterClass.NORMAL */
//					state = FieldState.NORMAL;
//					if( read ) builder.append( current );
//					break;
					
				/* ************************* */
				/* *** FieldState.QUOTED *** */
				/* ************************* */
				
				case FieldState.QUOTED ^ CharacterClass.NORMAL:
				    if( read ) builder.append( current );
					break;
					
				case FieldState.QUOTED ^ CharacterClass.TO_IGNORE:
					break;
					
				case FieldState.QUOTED ^ CharacterClass.TO_IGNORE_AROUND_FIELDS:
				    if( read ) builder.append( current );
					break;
					
				case FieldState.QUOTED ^ CharacterClass.QUOTE:
					state = FieldState.DOUBLE_QUOTE;
					break;
					
				case FieldState.QUOTED ^ CharacterClass.ESCAPE:
					state = FieldState.QUOTED_ESCAPE;
					break;
					
				case FieldState.QUOTED ^ CharacterClass.FIELD_SEPARATOR:
				    if( read ) builder.append( current );
					break;
					/*
					 * character is considered ad RECORD_SEPARATOR_1
					 */
				case FieldState.QUOTED ^ CharacterClass.RECORD_SEPARATOR:
				    if( read ) builder.append( current );
					break;
					
//				case FieldState.QUOTED ^ CharacterClass.RECORD_SEPARATOR_1:
//				    if( read ) builder.append( current );
//					break;
//					
//				case FieldState.QUOTED ^ CharacterClass.RECORD_SEPARATOR_2:
//					/* Handle as CharacterClass.NORMAL */
//				    if( read ) builder.append( current );
//					break;
				
				/* ******************************** */
				/* *** FieldState.QUOTED_ESCAPE *** */
				/* ******************************** */
				
				case FieldState.QUOTED_ESCAPE ^ CharacterClass.NORMAL:
					state = FieldState.QUOTED;
					if( read ) builder.append( current );
					break;
					
				case FieldState.QUOTED_ESCAPE ^ CharacterClass.TO_IGNORE:
					state = FieldState.QUOTED;
					if( read ) builder.append( current );
					break;
					
				case FieldState.QUOTED_ESCAPE ^ CharacterClass.TO_IGNORE_AROUND_FIELDS:
					state = FieldState.QUOTED;
					if( read ) builder.append( current );
					break;
					
				case FieldState.QUOTED_ESCAPE ^ CharacterClass.QUOTE:
					state = FieldState.QUOTED;
					if( read ) builder.append( current );
					break;
					
				case FieldState.QUOTED_ESCAPE ^ CharacterClass.ESCAPE:
					state = FieldState.QUOTED;
					if( read ) builder.append( current );
					break;
					
				case FieldState.QUOTED_ESCAPE ^ CharacterClass.FIELD_SEPARATOR:
					state = FieldState.QUOTED;
					if( read ) builder.append( current );
					break;
					
				case FieldState.QUOTED_ESCAPE ^ CharacterClass.RECORD_SEPARATOR:
					state = FieldState.QUOTED;
					if( read ) builder.append( current );
					break;
					
//				case FieldState.QUOTED_ESCAPE ^ CharacterClass.RECORD_SEPARATOR_1:
//					state = FieldState.QUOTED;
//					if( read ) builder.append( current );
//					break;
//					
//				case FieldState.QUOTED_ESCAPE ^ CharacterClass.RECORD_SEPARATOR_2:
//					/* Handle as CharacterClass.NORMAL */
//					state = FieldState.QUOTED;
//					if( read ) builder.append( current );
//					break;
				
				/* ***************************** */
				/* *** FieldState.QUOTED_END *** */
				/* ***************************** */
				
				case FieldState.QUOTED_END ^ CharacterClass.NORMAL:
					/* Normal character outside a quoted field. */
					throw new MalformedCSVException( "Encountered a normal character outside a quoted field." );
					
				case FieldState.QUOTED_END ^ CharacterClass.TO_IGNORE:
					break;
					
				case FieldState.QUOTED_END ^ CharacterClass.TO_IGNORE_AROUND_FIELDS:
					break;
					
				case FieldState.QUOTED_END ^ CharacterClass.QUOTE:
					/* Quote character outside a quoted field. */
					throw new MalformedCSVException( "Encountered a quote character outside a quoted field." );
					
				case FieldState.QUOTED_END ^ CharacterClass.ESCAPE:
					/* Escape character outside a quoted field. */
					throw new MalformedCSVException( "Encountered an escape character outside a quoted field." );
					
				case FieldState.QUOTED_END ^ CharacterClass.FIELD_SEPARATOR:
					return FieldEndReason.FIELD_SEPARATOR;
					
				case FieldState.QUOTED_END ^ CharacterClass.RECORD_SEPARATOR:
					if ( recordSeparatorStrategy.apply(read) )
						return FieldEndReason.RECORD_SEPARATOR;
						
					/* Handle as CharacterClass.NORMAL */
					/* Normal character outside a quoted field. */
					throw new MalformedCSVException( "Invalid record separator sequence." );
					
//				case FieldState.QUOTED_END ^ CharacterClass.RECORD_SEPARATOR_1:
//					if ( checkRecordSequence( current ) )
//						return FieldEndReason.RECORD_SEPARATOR;
//						
//					/* Handle as CharacterClass.NORMAL */
//					/* Normal character outside a quoted field. */
//					throw new MalformedCSVException( "Invalid record separator sequence." );
//					
//				case FieldState.QUOTED_END ^ CharacterClass.RECORD_SEPARATOR_2:
//					/* Handle as CharacterClass.NORMAL */
//					/* Normal character outside a quoted field. */
//					throw new MalformedCSVException( "Invalid record separator sequence." );
				
				/* ******************************* */
				/* *** FieldState.DOUBLE_QUOTE *** */
				/* ******************************* */
				
				case FieldState.DOUBLE_QUOTE ^ CharacterClass.NORMAL:
					/* Normal character outside a quoted field. *//*
					 * character è considerato RECORD_SEPARATOR_1
					 */
					throw new MalformedCSVException( "Encountered a normal character outside a quoted field." );
					
				case FieldState.DOUBLE_QUOTE ^ CharacterClass.TO_IGNORE:
					state = FieldState.QUOTED_END;
					break;
					
				case FieldState.DOUBLE_QUOTE ^ CharacterClass.TO_IGNORE_AROUND_FIELDS:
					state = FieldState.QUOTED_END;
					break;
					
				case FieldState.DOUBLE_QUOTE ^ CharacterClass.QUOTE:
					state = FieldState.QUOTED;
					if( read ) builder.append(current);
					break;
					
				case FieldState.DOUBLE_QUOTE ^ CharacterClass.ESCAPE:
					/* Escape character outside a quoted field. */
					throw new MalformedCSVException( "Encountered an escape character outside a quoted field." );
					
				case FieldState.DOUBLE_QUOTE ^ CharacterClass.FIELD_SEPARATOR:
					return FieldEndReason.FIELD_SEPARATOR;
					/*
					 * character is considered as RECORD_SEPARATOR_1
					 */
				case FieldState.DOUBLE_QUOTE ^ CharacterClass.RECORD_SEPARATOR:
					if ( recordSeparatorStrategy.apply(read) )
						return FieldEndReason.RECORD_SEPARATOR;
						
					/* Handle as CharacterClass.NORMAL */
					/* Normal character outside a quoted field. */
					throw new MalformedCSVException( "Invalid record separator sequence." );
					
//				case FieldState.DOUBLE_QUOTE ^ CharacterClass.RECORD_SEPARATOR_1:
//					if ( checkRecordSequence( current ) )
//						return FieldEndReason.RECORD_SEPARATOR;
//						
//					/* Handle as CharacterClass.NORMAL */
//					/* Normal character outside a quoted field. */
//					throw new MalformedCSVException( "Invalid record separator sequence." );
//					
//				case FieldState.DOUBLE_QUOTE ^ CharacterClass.RECORD_SEPARATOR_2:
//					/* Handle as CharacterClass.NORMAL */
//					/* Normal character outside a quoted field. */
//					throw new MalformedCSVException( "Invalid record separator sequence." );
						
				default:
					throw new IllegalStateException( "Unknown couple FieldState " + state + " CharacterClass " + type + ". This is a bug evidence." );
					
			}
			
		}
		
	}
	
	
	/**
	 * Represents the selected strategy to apply when
	 * a record separator character has been read.
	 * 
	 * @author Nerd4j Team
	 */
	private static interface RecordSeparatorStrategy
	{
		
		/**
		 * Applies the related strategy to handle record
		 * separator and returns {@code true} if the
		 * current position represents a record end.
		 * 
		 * @param read tells if to actually read the field or to skip it.
		 * @return {@code true} if it's a valid record separator.
		 * @throws IOException if there are problems in reading the buffer.
		 */
		public boolean apply( boolean read ) throws IOException;
		
	}

	
	/**
	 * Implementation of the {@link RecordSeparatorStrategy} that
	 * matches the exact character sequence. If the sequence does
	 * not match the characters are treated as normal characters.
	 * 
	 * @author Nerd4j Team
	 */
	private class MatchSeparatorSequenceStrategy implements RecordSeparatorStrategy
	{
		
		/** The character sequence that represents the record separator. */
		private final char[] sequence;
		
		
		/**
		 * Constructor with parameters.
		 * 
		 * @param sequence record separator character sequence.
		 */
		public MatchSeparatorSequenceStrategy( char[] sequence )
		{
			
			super();
			
			this.sequence = sequence;
			
		}
		
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean apply( boolean read ) throws IOException
		{
			
			/*
			 * We need to step back to catch the first character
			 * in the sequence. Due to the fact that we just
			 * read a character 'bufferIndex' can be at least 0.
			 */
			--bufferIndex;
			
			for( int i = 0; i < sequence.length; ++i )
			{
			
				/*
				 * Check if buffer need to be refilled. Checking on BUFFER_SIZE
				 * instead of bufferElements because is faster on a constant value
				 * AND if bufferElements is less than BUFFER_SIZE it can't be
				 * refilled (data already completely read into buffer)
				 */
				if ( bufferIndex >= BUFFER_SIZE )
				{
				
					bufferElements = reader.read( buffer, 0 , BUFFER_SIZE );
					bufferIndex = 0;
				
				}
			
				/* No more elements can be read. */
				if( bufferIndex >= bufferElements || bufferElements <= 0 )
				{
					if( read ) readPartialSequence( i );
					return false;
				}
			
				/* Read next character. To be used as types array index. */
				final char next = buffer[bufferIndex++];
				
				/* 
				 * If the next character is the expected character in the sequence
				 * we step forward otherwise we return false.
				 */
				if( next != sequence[i] )
				{
					
					/*
					 * If we failed reading the first character in the sequence
					 * we need to handle it. We already know that it is of type
					 * "record separator".
					 */
					if( i == 0 )
					{
						if( read ) builder.append( next );
					}
					else
					{
						/* 
						 * Otherwise we write the partial sequence read before
						 * and we step back the last character so that can be
						 * handled by the standard process.
						 */
						if( read ) readPartialSequence( i );
						--bufferIndex;
					}
					
					return false;
					
				}
				
			}

			return true;
			
		}
			
		/**
		 * This method is called when the record separator sequence
		 * has not been recognized. It writes the portion of the sequence
		 * actually read to the field builder.
		 * 
		 * @param length length of the sequence portion to read.
		 */
		private void readPartialSequence( int length )
		{
			
			for( int i = 0; i < length; ++i )
				builder.append( sequence[i] );
			
		}
		
	}
	
	/**
	 * Implementation of the {@link RecordSeparatorStrategy} that
	 * matches any character of type "record separator". It will
	 * skip any following character of type "record separator".
	 * 
	 * @author Nerd4j Team
	 */
	private class MatchAnySeparatorCharStrategy implements RecordSeparatorStrategy
	{
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean apply( boolean read ) throws IOException
		{
			
			/*
			 * Read the following characters and skip them until
			 * it finds a character that is not a record separator.
			 */		
			int type;					
			do{
			
				/*
				 * Check if buffer need to be refilled. Checking on BUFFER_SIZE
				 * instead of bufferElements because is faster on a constant value
				 * AND if bufferElements is less than BUFFER_SIZE it can't be
				 * refilled (data already completely read into buffer)
				 */
				if ( bufferIndex >= BUFFER_SIZE )
				{
					
					bufferElements = reader.read( buffer, 0 , BUFFER_SIZE );
					bufferIndex = 0;
					
				}
			
				/* No more elements can be read. */
				if( bufferIndex >= bufferElements || bufferElements <= 0 )
					return true;
			
				/* Read next character. To be used as types array index. */
				final char next = buffer[bufferIndex++];
				
				type = next < RemarkableASCII.ASCII_TABLE_SIZE ? types[next] : CharacterClass.NORMAL;		
			
			}while( type == CharacterClass.RECORD_SEPARATOR );
			
			/*
			 * If we reach this point the last character is not a record separator
			 * so we step back and let it be handled by the standard process.
			 */
			--bufferIndex;
			
			return true;
			
		}
		
	}
	
	
//	/**
//	 * Check if there is a correct record separator sequence following a
//	 * character of type {@link CharacterClass.RECORD_SEPARATOR_1}.
//	 * <p>
//	 * Provided character is assumed a
//	 * {@link CharacterClass.RECORD_SEPARATOR_1} and no further checks
//	 * will be done on his type.
//	 * 
//	 * </p>
//	 * 
//	 * @param character first character of a record separator sequence
//	 * @return {@code true} if a complete record separator sequence has been
//	 *         read.
//	 * @throws IOException if an error occurs while parsing data.
//	 */
//	private void skipRecordSeparators() throws IOException
//	{
//		
//		/*
//		 * Read the following characters and skip them until
//		 * it finds a character that is not a record separator.
//		 */		
//		int type;
//		
//		/*
//		 * We do a step back so we don't need to check if 
//		 * type == CharacterClass.RECORD_SEPARATOR inside
//		 * the loop.
//		 */
//		--bufferIndex;
//		
//		do{
//		
//			/*
//			 * Check if buffer need to be refilled. Checking on BUFFER_SIZE
//			 * instead of bufferElements because is faster on a constant value
//			 * AND if bufferElements is less than BUFFER_SIZE it can't be
//			 * refilled (data already completely read into buffer)
//			 */
//			if ( bufferIndex >= BUFFER_SIZE )
//			{
//				
//				bufferElements = reader.read( buffer, 0 , BUFFER_SIZE );
//				bufferIndex = 0;
//				
//			}
//		
//			/* No more elements can be read. */
//			if( bufferElements == -1 || bufferIndex >= bufferElements )
//				return;
//		
//			/* Read next character. To be used as types array index. */
//			final char next = buffer[++bufferIndex];
//			
//			type = next < RemarkableASCII.ASCII_TABLE_SIZE ? types[next] : CharacterClass.NORMAL;		
//		
//		}while( type == CharacterClass.RECORD_SEPARATOR );
//		
//	}
	
	
//	/**
//	 * Check if there is a correct record separator sequence following a
//	 * character of type {@link CharacterClass.RECORD_SEPARATOR_1}.
//	 * <p>
//	 * Provided character is assumed a
//	 * {@link CharacterClass.RECORD_SEPARATOR_1} and no further checks
//	 * will be done on his type.
//	 * 
//	 * </p>
//	 * 
//	 * @param character first character of a record separator sequence
//	 * @return {@code true} if a complete record separator sequence has been
//	 *         read.
//	 * @throws IOException if an error occurs while parsing data.
//	 */
//	private boolean checkRecordSequence( final int character ) throws IOException
//	{
//		
//		/*
//		 * Read next character to discover if is a second character record
//		 * separator.
//		 */
//		
//		/*
//		 * Check if buffer need to be refilled. Checking on BUFFER_SIZE
//		 * instead of bufferElements because is faster on a constant value
//		 * AND if bufferElements is less than BUFFER_SIZE it can't be
//		 * refilled (data already completely read into buffer)
//		 */
//		if ( bufferIndex >= BUFFER_SIZE )
//		{
//			
//			bufferElements = reader.read( buffer, 0 , BUFFER_SIZE );
//			bufferIndex = 0;
//			
//		}
//		
//		/* No more elements can be read. */
//		if( bufferElements == -1 || bufferIndex >= bufferElements )
//			return false;
//		
//		/* Read next character. To be used as types array index. */
//		final char next = buffer[ bufferIndex ];
//		
//		final int type;
//		
//		/* Find the next character class. */
//		if( next < RemarkableASCII.ASCII_TABLE_SIZE )
//			type =  types[ next ];
//		else
//			type = CharacterClass.NORMAL;
//		
//		if ( type == CharacterClass.RECORD_SEPARATOR_2 )
//		{
//			/* Found a complete record separator. */
//			++bufferIndex;
//			return true;
//		}
//		
//		/* Not a record separator. */
//		return false;
//		
//	}
	
}
