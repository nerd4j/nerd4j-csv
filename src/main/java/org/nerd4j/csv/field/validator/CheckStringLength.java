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

import org.nerd4j.csv.field.CSVFieldValidator;


/**
 * Implementation of the {@link CSVFieldValidator} interface
 * that checks the given {@link String} length to be within
 * the requested interval.
 *
 * @author Nerd4j Team
 */
public final class CheckStringLength extends AbstractCSVFieldValidator<String>
{
    
    /** The string min length. */
    private final int min;
    
    /** The string max length. */
    private final int max;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param length the exact length that the string must have.
     */
    public CheckStringLength( final int length )
    {
        
        super( "The length of {1} is not " + length );

        if( length < 0 ) throw new IllegalArgumentException( "The length cannot be negative." );
        
        this.min = length;
        this.max = length;
        
    }

    /**
     * Constructor with parameters.
     * 
     * @param min the minimum length that the string must have.
     * @param max the maximum length that the string must have.
     */
    public CheckStringLength( final int min, final int max )
    {
        
        super( "The length of {1} is not in the interval [" + min + "," + max + "]." );
        
        if( min < 0 ) throw new IllegalArgumentException( "The min constraint cannot be negative." );
        if( max < min ) throw new IllegalArgumentException( "The max constraint cannot be smaller than the min constraint." );
        
        this.min = min;
        this.max = max;
        
    }

    
    /* **************** */
    /*  EXTENSION HOOKS */
    /* **************** */

    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean performValidation( final String value )
    {
        
        final int length = value.length();
        return length >= min && length <= max;
        
    }
    
}
