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
import java.io.Writer;
import java.util.Arrays;


/**
 * Default implementation for the {@link FieldBuilder} interface.
 * 
 * @author Nerd4j Team
 */
final class FieldBuilderImpl implements FieldBuilder
{
	
	/** Last issued mark position or <tt>-1</tt>. */
	private int markPos;
	
	/** Last issued mark length. */
	private int markLen;
	
	/** Current valid data length (pointer to next appendable position). */
    protected int length;
    
    /** Character array data. */
    private char[] data;
	
    
    
    /**
     * Default constructor.
     * 
     */
    public FieldBuilderImpl()
    {
        
        this( 16 );
        
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param size initial field builder size.
     */
    public FieldBuilderImpl( int size )
    {
        
        super();
        
        this.clear();
        this.data = new char[ size ];
        
    }
	
	
	/* ******************* */
	/*  INTERFACE METHODS  */
	/* ******************* */
	
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void append( char character )
    {
        
        /*
         * If the array size is not enough to keep all
         * the characters we double the array capacity.
         */
        if( ++length > data.length )
            data = Arrays.copyOf( data, data.length << 1 );
        
        data[length-1] = character;
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void append( int character )
    {
        
        /*
         * If the array size is not enough to keep all
         * the characters we double the array capacity.
         */
        if( ++length > data.length )
            data = Arrays.copyOf( data, data.length << 1 );
        
        data[length-1] = (char) character;
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void mark()
    {
        
    	/*
    	 * We set the mark at the current position.
    	 * and the mark space size to 1.
    	 */
        this.markPos = length;
        this.markLen = 1;
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void extendMark()
    {
    	/*
    	 * If the mark has been already set
    	 * we increment the mark space size.
    	 */
    	if( isMarked() ) markLen++;
    	
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollbackToMark()
    {
        
        /*
         * If the writing length (next char position)
         * is within the mark area (markPos + markLen)
         * then it has to be rolled back to the marked
         * position.
         */
        if( markPos < length && length <= markPos + markLen )
            length = markPos;
        
        markLen =  0;
        markPos = -1;
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getMarkedPosition()
    {
        
        if( markPos > -1 && length > markPos + markLen )
        {
            markLen =  0;
            markPos = -1;
        }
        
        return markPos;
        
    }
	
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMarked()
    {
    	
    	return getMarkedPosition() != -1;
    	
    }
    
	/**
     * {@inheritDoc}
     */
    @Override
    public int length()
    {
        
        return length;
        
    }
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Handles mark clearings, implementations should handle their resource
	 * clearing too.
	 * </p>
	 */
	@Override
	public void clear()
	{
		
	    this.length = 0;
	    this.markLen = 0;
	    this.markPos = -1;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		
		return length <= 0 ? null : new String( data, 0, length );
		
	}
	
	@Override
    public String toString( int offset, int length )
    {

        throw new UnsupportedOperationException();
        
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeTo( Writer writer ) throws IOException
	{
		
		if( length > 0 )
		    writer.write( data, 0, length );
		
	}
	
	/**
     * {@inheritDoc}
     */
    @Override
    public void writeTo( Writer writer, int offset, int length ) throws IOException
    {
        
        throw new UnsupportedOperationException();
        
    }
	
}