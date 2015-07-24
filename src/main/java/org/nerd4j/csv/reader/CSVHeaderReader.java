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
package org.nerd4j.csv.reader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.nerd4j.csv.parser.CSVParser;
import org.nerd4j.csv.parser.CSVToken;


/**
 * Represents a CSV data source reader that has the only
 * aim to read the CSV header.
 * 
 * <p>
 *  This class is intended for internal use only.
 *  The aim of this class is to read the CSV header
 *  if needed.
 * </p>
 * 
 * @author Nerd4J Team
 */
final class CSVHeaderReader
{
    
    /** Object able to parse a CSV source. */
    private CSVParser parser;

    
    /**
     * Constructor with parameters.
     * 
     * @param parser the understanding CSV parser.
     */
    public CSVHeaderReader( CSVParser parser )
    {
        
        super();
        
        this.parser = parser;
        
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */

    
	/**
	 * Reads the first record in the CSV source and returns
	 * a list of {@link String}s that represents the header.
	 * 
	 * @return the CSV header as a list of {@link String}s.
	 * @throws IOException if an error occours reading the CSV source.
	 */
	public String[] readHeader() throws IOException
	{
        
	    final List<String> header = new LinkedList<String>();
	    while( parser.read() == CSVToken.FIELD )
	        header.add( parser.getCurrentValue() );
	    
        return header.toArray( new String[header.size()] );
        
	}
	
}
