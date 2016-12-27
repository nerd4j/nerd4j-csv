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
package org.nerd4j.csv.field;

import org.nerd4j.csv.CSVProcessContext;
import org.nerd4j.csv.CSVProcessError;
import org.nerd4j.csv.CSVProcessOperation;


/**
 * Represents the execution context of the during
 * the process of validating and converting fields.
 * 
 * <p>
 * This context is used both during reading of a
 * CSV source and during writing to a CSV target.
 * 
 * <p>
 * This context keeps some information like:
 * <ul>
 *  <li>The current row and column handled.</li>
 *  <li>The original value of the field.</li>
 *  <li>The value of the field after conversion.</li>
 * </ul>
 * 
 * @author Nerd4j Team
 */
public final class CSVFieldProcessContext implements CSVProcessContext
{

    /** Currently processed row index (0 based). */
    private int rowIndex;
    
    /** Currently processed column index (0 based). */
    private int columnIndex;
    
    /*
     * Avoid to use generic types for value field
     * because id makes the context uselessly
     * complex to use.
     */
    
    /** Value before processing. */
    private Object originalValue;
    
    /** Value after processing. */
    private Object processedValue;
    
    /** In case of failure contains the operator that has failed. */
    private CSVProcessOperation failedOperation;
    
    /** The CSV header containing the columns names. */ 
    private String[] header;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param header the CSV header containing the columns names.
     */
    public CSVFieldProcessContext( String[] header )
    {
        
        super();
        
        this.header = header;
        
        this.columnIndex = -1;
        
        /* If the header has been read the row index starts at the second row. */
        this.rowIndex = header == null ? -1 : 0;
        
        this.originalValue   = null;
        this.processedValue  = null;
        this.failedOperation = null;
        
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */

    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowIndex()
    {
        return rowIndex;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnIndex()
    {
        return columnIndex;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isError()
    {
        return failedOperation != null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVProcessError getError()
    {
        if( failedOperation != null )
            return new CSVFieldProcessError( failedOperation, this );
        else
            return null;
    }
    
    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * Returns the original value before processing.
     * 
     * @return the original value before processing.
     */
    public Object getOriginalValue()
    {
        return originalValue;
    }
    
    /**
     * Sets the original value before processing.
     * 
     * @param originalValue value to set.
     */
    public void setOriginalValue( final Object originalValue )
    {
        this.originalValue = originalValue;
    }
    
    /**
     * Returns the value after processing.
     * 
     * @return the value after processing.
     */
    public Object getProcessedValue()
    {
        return processedValue;
    }
    
    /**
     * Sets the value after processing.
     * 
     * @param processedValue value to set.
     */
    public void setProcessedValue( final Object processedValue )
    {
        this.processedValue = processedValue;
    }
    
    
    /* ***************** */
    /*  UTILITY METHODS  */
    /* ***************** */
    
    
    /**
     * Performs the steps needed to start a new row:
     * <ol>
     *  <li>Increments the row count;</li>
     *  <li>Clears the column count;</li>
     *  <li>Clears previous values;</li>
     *  <li>Clears previous errors:</li>
     * </ol>
     * 
     */
    public void newRow()
    {
        
        ++ this.rowIndex;
        this.columnIndex = -1;
        
    }
    
    /**
     * Performs the steps needed to process a new column:
     * <ol>
     *  <li>Increments the column count;</li>
     *  <li>Clears previous values;</li>
     *  <li>Clears previous errors:</li>
     * </ol>
     * 
     */
    public void newColumn()
    {
        
        ++ this.columnIndex;
        
    }
    
    /**
     * Returns the column name if exists otherwise
     * returns the column index in string format.
     * 
     * @return the column name.
     */
    public String getColumnName()
    {
        
        String columnName = null;
        if( header != null && columnIndex < header.length )
            columnName = header[columnIndex];
        
        return columnName != null ? columnName : String.valueOf( columnIndex );
        
    }
    
    /**
     * Tells the context that the given operation
     * has failed. This causes the {@link #isError()}
     * method to return {@code true}.
     * 
     * @param failedOperation the operation that has failed.
     */
    public void operationFailed( final CSVProcessOperation failedOperation )
    {
        this.failedOperation = failedOperation;
    }
   
    /**
     * Clears the context errors.
     *  
     */
    public void clear()
    {
        
        this.originalValue   = null;
        this.processedValue  = null;
        this.failedOperation = null;        
        
    }
    
}