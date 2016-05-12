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
package org.nerd4j.csv.formatter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import org.nerd4j.csv.RemarkableASCII;



/**
 * Factory of {@link CSVFormatter}.
 * 
 * <p>
 *  Handles correct formatters configuration and creation.
 * </p>
 * 
 * @author Nerd4j Team
 */
public final class CSVFormatterFactory
{
    
    /** Contains the actions to do for each configured character. */
    private final int[] actions;
	
    /** The record separator character sequence. */
    private final char[] recordSeparators;
    
    /** The field separator character. */
    private final char fieldSeparator;
    
    /** The escape character. */
    private final char escapeChar;
    
    /** The quoting character. */
    private final char quoteChar;
    
	
	/**
	 * Default constructor.
	 * 
	 */
	public CSVFormatterFactory()
	{
		
	    this( new CSVFormatterMetadata() );
	    
	}

	
	/**
	 * Constructor with parameters.
	 * 
	 * @param configuration the configuration to use to build the formatters.
	 */
	public CSVFormatterFactory( final CSVFormatterMetadata configuration )
	{
	    
	    super();
	    
	    if( configuration == null )
	        throw new NullPointerException( "The configuration is mandatory and can't be null" );
	    
	    this.recordSeparators = createRecordSeparators( configuration );
	    this.actions = createActions( configuration );
	    
	    this.fieldSeparator = configuration.getFieldSeparator();
	    this.escapeChar = configuration.getEscapeChar();
	    this.quoteChar = configuration.getQuoteChar();
	    
	}
		
	
	/* ******************* */
	/*  INTERFACE METHODS  */
	/* ******************* */
	
	
	/**
	 * Create a new {@link CSVFormatter} to write data to the given {@link Writer}.
	 * 
	 * @param writer CSV data destination.
	 */
	public CSVFormatter create( final Writer writer )
	{
	    
		return new CSVFormatterImpl( writer, false );
		
	}
	
	/**
	 * Create a new {@link CSVFormatter} to write data to the given {@link Writer}.
	 * 
	 * @param writer   CSV data destination.
	 * @param quoteAll tells if all the field should be quoted or not.
	 */
	public CSVFormatter create( final Writer writer, final boolean quoteAll )
	{
	    
	    return new CSVFormatterImpl( writer, quoteAll );
	    
	}
	
	
	/* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
	
	
	/**
	 * This method reads the configurations and builds the actions
	 * needed by the {@link DefaultCSVFormatter} to format the fields.
	 * 
	 * @param configuration the configuration to parse.
	 * @return the formatter actions.
	 */
	private int[] createActions( final CSVFormatterMetadata configuration )
    {
        
        final int[] actions = new int[RemarkableASCII.ASCII_TABLE_SIZE];
        
        /* We check if there are characters that need to be escaped. */
        final char[] charsToEscape = configuration.getCharsToEscape();
        for( char c : charsToEscape )
            if( c < RemarkableASCII.ASCII_TABLE_SIZE )
                actions[c] = FieldAction.DO_ESCAPE;
            
        /*
         * We check if there are characters that force the whole filed to be quoted.
         * If those characters are also to be escaped we perform both actions.
         */
        final char[] forceQuoteChars = configuration.getCharsThatForceQuoting();
        for( char c : forceQuoteChars )
            if( c < RemarkableASCII.ASCII_TABLE_SIZE )
                actions[c] |= FieldAction.FORCE_QUOTE;
        
        /*
         * A field separator can be escaped, quoted or both.
         * No other action is accepted for this character. 
         */
        final char fieldSeparator = configuration.getFieldSeparator();
        if( fieldSeparator == RemarkableASCII.NOT_AN_ASCII )
            throw new IllegalArgumentException( "The character used to separate fields is mandatory" );
                
        if( actions[fieldSeparator] != FieldAction.DO_ESCAPE && actions[fieldSeparator] != FieldAction.QUOTE_AND_ESCAPE )
            actions[fieldSeparator] = FieldAction.FORCE_QUOTE;

        /*
         * A record separator can be escaped, quoted or both.
         * No other action is accepted for this character. 
         */
        final char[] recordSeparator = configuration.getRecordSeparator();
        for( char c : recordSeparator )
        {
        	if( actions[c] != FieldAction.DO_ESCAPE && actions[c] != FieldAction.QUOTE_AND_ESCAPE )
            actions[c] = FieldAction.FORCE_QUOTE;
        }
        
        /*
         * The escape character if any must be escaped itself.
         * The way to escape the escape character is to write it twice. 
         */
        final char escapeChar = configuration.getEscapeChar();                
        if( escapeChar == RemarkableASCII.NOT_AN_ASCII )
        {
            if( charsToEscape.length > 0 )
                throw new IllegalArgumentException( "There are characters to be escaped but no escape character is defined" );
        }
        else
        {
            if( actions[escapeChar] == FieldAction.FORCE_QUOTE ||
                actions[escapeChar] == FieldAction.QUOTE_AND_ESCAPE )
                actions[escapeChar] = FieldAction.QUOTE_AND_ESCAPE;
            else
                actions[escapeChar] = FieldAction.DO_ESCAPE;
        }
        
        /*
         * The default behavior for the quote character is to be double quoted.
         * But the user can escape the quote character with the escape character.
         * In any case the quote character must be escaped.
         */
        final char quoteChar = configuration.getQuoteChar();
        if( quoteChar == RemarkableASCII.NOT_AN_ASCII )
            throw new IllegalArgumentException( "The character used to quote is mandatory" );
        else
            if( actions[quoteChar] != FieldAction.DO_ESCAPE &&
                actions[quoteChar] != FieldAction.QUOTE_AND_ESCAPE )
                actions[quoteChar] = FieldAction.DOUBLE_QUOTE;
        
        return actions;
        
    }
	
	
	/**
     * Creates a record separation sequence from given configuration.
     * 
     * @param configuration the configuration to parse.
     * @return the record separation sequence.
     */
    private char[] createRecordSeparators( final CSVFormatterMetadata configuration )
    {
       
    	final char[] recordSeparator = configuration.getRecordSeparator();
    	if( recordSeparator == null || recordSeparator.length == 0 )
    		throw new IllegalArgumentException( "Record separators are mandatory and can't be null" );
    	
    	for( char c : recordSeparator )
    		if( c == RemarkableASCII.NOT_AN_ASCII )
    			throw new IllegalArgumentException( "Record separators are mandatory and can't be null" );
    	
    	return recordSeparator;
        
    }
    
    
    /* *************** */
    /*  INNER CLASSES  */
    /* *************** */
    
    
    /**
     * Enumerates all possible field formatting actions (state machine).
     * Previously was an enum but we discovered that switching on constants
     * is actually faster.
     */
    private interface FieldAction
    {
        
        /** Writes a normal character. */
        public static final int WRITE               = 0;
        
        /** Writes the escape character before the one to write. */
        public static final int DO_ESCAPE           = 1;
        
        /** Forces the whole field to be quoted and writes the character. */
        public static final int FORCE_QUOTE         = 2;
        
        /** Forces the whole field to be quoted and escapes the next character. */
        public static final int QUOTE_AND_ESCAPE    = 3;
        
        /**
         * Escapes the quote character writing it twice
         * and forced the whole field to be quoted as expected
         * by the CSV standard. */
        public static final int DOUBLE_QUOTE        = 4;
        
    }
    
    
    /**
     * <tt>CSVParser</tt> reads character data from a {@link String}, parsing and
     * quoting if needed and writing into a {@link Writer}.
     * <p>
     * After use it should be closed to permit resource release.
     * </p>
     * 
     * @author Nerd4j Team
     */
    public class CSVFormatterImpl implements CSVFormatter
    {
        

        /** CSV data destination writer. */
        private final Writer writer;
        
        /** Tells to quote all the fields even if not needed. */
        private final boolean quoteAll;
        
        /** Size of writer char buffer, 8MB. Different from fieldBuffer size. */
        private final int BUFFER_SIZE = 1024 * 1024 * 8;
        
        /** Character buffer used to build fields. */
        private char[] fieldBuffer;

        /** Currently writing field index. */
        private int filedCount;
        
        
        /**
         * Constructor with parameters.
         * 
         * @param writer   CSV data destination
         * @param quoteAll force quoting on all fields
         */
        private CSVFormatterImpl( final Writer writer, final boolean quoteAll )
        {
            
            super();
            
            this.filedCount = 0;
            this.writer     = new BufferedWriter( writer, BUFFER_SIZE );
            this.quoteAll   = quoteAll;
            
            this.fieldBuffer     = new char[1024];
            this.fieldBuffer[0]  = quoteChar;
            
        }
        
        
        /* ******************* */
        /*  INTERFACE METHODS  */
        /* ******************* */
        
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void writeField( final String source ) throws IOException
        {
            
            writeField( source, false );
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void writeField( final String source, final boolean quote ) throws IOException
        {
            
            /* If we are not writing the first field we add a field separator. */
            if( ++filedCount > 1 ) writer.write( fieldSeparator );
            
            /* Need to be quoted if forced or requested. */
            boolean doQuote = quoteAll | quote;
            
            /* Length of the data to write. */
            final int length = source != null ? source.length() : 0;
            
            /*
             * If the field to write is greater than the buffer
             * we resize the buffer to be four times bigger than
             * the field. So the buffer is big enough to contain
             * all the field even if each character has to be
             * escaped and probably doesn't need to be resized
             * any sooner.
             */
            if( fieldBuffer.length < length << 1 )
            {
                fieldBuffer = new char[ length << 2 ];
                fieldBuffer[0] = quoteChar;
            }
            
            /* Loop current char index (even surrogate). */
            int index = -1;
            
            /*
             * The position in the buffer to write the next character.
             * We keep empty the first position in the buffer so we
             * can add the quote character if needed.
             * (the first position is -1 because the value of pos is
             * always pre-incremented. )
             */
            int pos = 0;
            
            /* Loop read char. */
            char currentChar;
            
            /* Loop read char type. */
            int currentAction;
            
            /* Evaluate every character (avoiding to copy char array). */
            while( ++index < length )
            {
                
                /* Extract current character. */
                currentChar = source.charAt( index );
                
                /* Find the current character class. */
                if( currentChar < RemarkableASCII.ASCII_TABLE_SIZE )
                    currentAction = actions[ currentChar ];
                else
                    currentAction = FieldAction.WRITE;
                
                switch( currentAction )
                {
                
                /*
                 * A normal action for the formatter is
                 * to write the character to the field builder.
                 */
                case FieldAction.WRITE:                    
                    fieldBuffer[++pos] = currentChar;
                    break;
                    
                /*
                 * Escaping a character means writing the escape
                 * character before the one to write.
                 */
                case FieldAction.DO_ESCAPE:
                    fieldBuffer[++pos] = escapeChar;
                    fieldBuffer[++pos] = currentChar;
                    break;
                    
                /*
                 * By forcing quotes we tell the formatter to
                 * enclose the formatted field into quotes. 
                 */
                case FieldAction.FORCE_QUOTE:
                    fieldBuffer[++pos] = currentChar;
                    doQuote = true;
                    break;
                    
                /*
                 * In this case we both enclose the formatted
                 * field into quotes and escape the current
                 * character.
                 */
                case FieldAction.QUOTE_AND_ESCAPE:
                    fieldBuffer[++pos] = escapeChar;
                    fieldBuffer[++pos] = currentChar;
                    doQuote = true;
                    break;
                   
                /*  
                 * The standard behavior for escaping the quote character
                 * is to write it twice. This case should occur only if
                 * currentChar == quoteChar. If they are different we have
                 * a bug evidence.
                 */
                case FieldAction.DOUBLE_QUOTE:
                    if( currentChar != quoteChar )
                        throw new IllegalStateException( "The character: " + currentChar + " should not be handled as DOUBLE QUOTE. This is a bug evidence." );
                    
                    fieldBuffer[++pos] = quoteChar;
                    fieldBuffer[++pos] = currentChar;
                    doQuote = true;
                    break;
                                                    
                default:
                    throw new IllegalStateException( "Unknown action: " + currentAction + ". This is a bug evidence." );
                    
                }
                
            }
            
            if( doQuote )
            {
                
                fieldBuffer[++pos] = quoteChar;
                writer.write( fieldBuffer, 0, ++pos );
                
            }
            else
            {
                
                writer.write( fieldBuffer, 1, pos );
                
            }
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void writeEOR() throws IOException
        {
            
            filedCount = 0;
            writer.write( recordSeparators );
                    
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void writeEOD() throws IOException
        {
            
            filedCount = 0;
            
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() throws IOException
        {
            
            writer.close();
            
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void flush() throws IOException
        {
            
            writer.flush();
            
        }
              
    }
	
}