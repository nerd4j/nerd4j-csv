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
package org.nerd4j.csv.field.validator;

import org.nerd4j.csv.field.CSVFieldProcessContext;
import org.nerd4j.csv.field.CSVFieldValidator;


/**
 * Abstract implementation of the {@link CSVFieldValidator} interface
 * that performs the expected behavior and modifies the {@link CSVFieldProcessContext}
 * accordingly with the framework execution policy.
 * 
 * <p>
 * Any custom implementation of the {@link CSVFieldValidator} interface
 * should extend this class. 
 *
 * @param <V> type of the value to check.
 * 
 * @author Nerd4j Team
 */
public abstract class AbstractCSVFieldValidator<V> implements CSVFieldValidator<V>
{
    
    /** The error message pattern to set into the context in case of failure. */
    private final String errorMessagePattern;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param errorMessagePattern message pattern to set into the context in case of failure.
     */
    public AbstractCSVFieldValidator( final String errorMessagePattern )
    {
        
        super();
        
        if( errorMessagePattern == null || errorMessagePattern.isEmpty() )
            throw new IllegalArgumentException( "The error message is mandatory" );
        
        this.errorMessagePattern = errorMessagePattern;
        
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */

    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessagePattern()
    {
        return errorMessagePattern;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void apply( final V value, final CSVFieldProcessContext context )
    {
        
        if( ! performValidation(value) )
            context.operationFailed( this );
        
    }

    
    /* **************** */
    /*  EXTENSION HOOKS */
    /* **************** */

    
    /**
     * Performs the validation, and returns {@code true}
     * if the validation succeeds and {@code false} otherwise.
     * 
     * @param value value to check.
     * @return {@code true} if the validation succeeds, {@code false} otherwise.
     */
    protected abstract boolean performValidation( final V value );
    
}
