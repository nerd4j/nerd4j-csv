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
package org.nerd4j.csv.conf.mapping;

/**
 * Represents the configuration of the
 * {@link org.nerd4j.csv.reader.CSVReader CSVReader}.
 * 
 * @author Nerd4j Team
 */
public class CSVReaderConf extends CSVHandlerConf implements Cloneable
{

    /** Tells if to read the CSV source first row as header. */
    private Boolean readHeader;
    
    /** Tells if to use column names during configuration. */
    private Boolean useColumnNames;
    
    /**
     * In the CSV standard each record must have the same number of cells.
     * But it is possible to face non standard CSV files where records
     * have different lengths.
     * <p>
     * This flag tells the reader to accept such non standard files.
     * In this case the reader may return incomplete data models.
     */
    private Boolean acceptIncompleteRecords;
    
    /** The name to reference the parser configuration to use. */
    private String parserRef;
    
    /** The parser configuration embedded into the reader. */
    private CSVParserConf parser;
    
    /**
     * Default constructor.
     * 
     */
    public CSVReaderConf()
    {

        super();
    
        this.parser = null;
        this.parserRef = null;
        
        this.readHeader = null;
        this.useColumnNames = null;
        this.acceptIncompleteRecords = null;
        
    }
   
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    

    /**
     * Returns the flag {@code read-header}.
     * 
     * @return the flag {@code read-header}.
     */
    public Boolean getReadHeader()
    {
        return readHeader;
    }
    
    /**
     * Sets the flag {@code read-header}.
     * 
     * @param readHeader value to set.
     */
    public void setReadHeader( Boolean readHeader )
    {
        this.readHeader = readHeader;
    }
    
    /**
     * Returns the flag {@code use-column-names}.
     * 
     * @return the flag {@code use-column-names}.
     */
    public Boolean getUseColumnNames()
    {
        return useColumnNames;
    }
    
    /**
     * Sets the flag {@code use-column-names}.
     * 
     * @param useColumnNames value to set.
     */
    public void setUseColumnNames( Boolean useColumnNames )
    {
        this.useColumnNames = useColumnNames;
    }

    /**
     * Returns the flag {@code accept-incomplete-records}.
     * 
     * @return the flag {@code accept-incomplete-records}.
     */
    public Boolean getAcceptIncompleteRecords()
    {
    	return acceptIncompleteRecords;
    }
    
    /**
     * Sets the flag {@code accept-incomplete-records}.
     * 
     * @param acceptIncompleteRecords value to set.
     */
    public void setAcceptIncompleteRecords( Boolean acceptIncompleteRecords )
    {
    	this.acceptIncompleteRecords = acceptIncompleteRecords;
    }

    /**
     * Returns the parser reference.
     * 
     * @return the parser reference.
     */
    public String getParserRef()
    {
        return parserRef;
    }
    
    /**
     * Sets the parser reference.
     * 
     * @param parserRef value to set.
     */
    public void setParserRef( String parserRef )
    {
        this.parserRef = parserRef;
    }

    /**
     * Returns the parser configuration.
     * 
     * @return the parser configuration.
     */
    public CSVParserConf getParser()
    {
        return parser;
    }

    /**
     * Sets the parser configuration.
     * 
     * @param parser value to set.
     */
    public void setParser( CSVParserConf parser )
    {
        this.parser = parser;
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVReaderConf clone() throws CloneNotSupportedException
    {
        
        final CSVReaderConf clone = (CSVReaderConf) super.clone();
        
        clone.parser = this.parser != null ? this.parser.clone() : null;
        
        return clone;
        
    }

}