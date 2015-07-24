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

import java.text.MessageFormat;

import org.nerd4j.csv.CSVProcessError;
import org.nerd4j.csv.CSVProcessOperation;


/**
 * Represents an error occurred during the process
 * of validating or converting fields.
 * 
 * <p>
 *  This object contains the {@link CSVFieldOperator}
 *  which caused the error and the related
 *  {@link CSVFieldProcessContext}.  
 * </p>
 * 
 * <p>
 *  This context keeps some information like:
 *  <ul>
 *   <li>The current row and column handled.</li>
 *   <li>The original value of the field.</li>
 *   <li>The value of the field after conversion.</li>
 *  </ul>
 * </p>
 * 
 * @author Nerd4j Team
 */
final class CSVFieldProcessError implements CSVProcessError
{

    /** The operation that failed causing the error. */
    private final CSVProcessOperation operation;
    
    /** The execution context related to the failure. */
    private final CSVFieldProcessContext context;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param operation the operation that failed causing the error.
     * @param context   the execution context related to the failure.
     */
    public CSVFieldProcessError( final CSVProcessOperation operation, final CSVFieldProcessContext context )
    {
        
        super();
        
        this.context   = context;
        this.operation = operation;
        
    }

    
    /* ******************* */
    /*  GETTERS & SETTERS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVProcessOperation getOperation()
    {
        return operation;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage()
    {
        return MessageFormat.format( operation.getErrorMessagePattern(),
                                     context.getColumnName(),
                                     context.getOriginalValue(),
                                     context.getProcessedValue() );
    }

}