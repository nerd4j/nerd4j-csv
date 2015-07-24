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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.nerd4j.csv.field.CSVFieldValidator;


/**
 * Implementation of the {@link CSVFieldValidator} interface that checks the
 * given {@link Number} value to be within the requested interval.
 * <p>
 * Due to {@link Comparable} needing not every {@link Number} can be handled (
 * {@link AtomicInteger} and {@link AtomicLong} don't implement
 * {@link Comparable}).
 * </p>
 * 
 * @param <N> type of the {@link Number} to be validated.
 * 
 * @author Nerd4j Team
 */
public final class CheckNumberRange<N extends Number & Comparable<N>> extends AbstractCSVFieldValidator<N>
{
    
    /** The number min value. */
    private final N min;
    
    /** The number max value. */
    private final N max;

    /** Minimum value has been defined? */
    private final boolean hasMin;
    
    /** Maximum value has been defined? */
    private final boolean hasMax;
    
    /**
     * Constructor with parameters.
     * <p>
     * Null values can be uses as substitute of +-infinite (no check).
     * </p>
     * 
     * @param min the minimum value that the number must have.
     * @param max the maximum value that the number must have.
     */
    public CheckNumberRange( final N min, final N max )
    {
        
        super( "The value of {1} is not in the interval [" +
               (min == null ? "-inf" : min) + "," +
               (max == null ? "+inf" : max) + "]." );
        
        this.hasMin = min != null;
        this.hasMax = max != null;
        
        if ( hasMin && hasMax && min.compareTo( max ) > 0 )
        	throw new IllegalArgumentException( "The max constraint cannot be smaller than the min constraint." );
        
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
    protected boolean performValidation( final N value )
    {
        
    	return !( (hasMin && min.compareTo(value) > 0) || (hasMax && max.compareTo(value) < 0) );
        
    }
    
}
