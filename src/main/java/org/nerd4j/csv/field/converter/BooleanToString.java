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
package org.nerd4j.csv.field.converter;

import org.nerd4j.csv.field.CSVFieldConverter;

/**
 * Implementation of the {@link CSVFieldConverter} interface
 * that converts {@link Boolean}s into {@link String}s.
 * 
 * <p>
 *  This converter accepts custom {@link String}s to use
 *  to represent the boolean values.
 * </p>
 * 
 * <p>
 *  By default the boolean values are converted into
 *  the {@link String}s "true" and "false".
 * </p>
 * 
 * @author Nerd4j Team
 */
public final class BooleanToString extends AbstractCSVFieldConverter<Boolean,String>
{

    /** The {@link String} representation of the boolean "true". */
    private final String trueValue;
    
    /** The {@link String} representation of the boolean "false". */
    private final String falseValue;
    
    
    /**
     * Default constructor.
     * 
     */
    public BooleanToString()
    {

        this( "true", "false" );
        
    }

    
    /**
     * Constructor with parameters.
     * 
     * @param trueValue  representation of the boolean "true".
     * @param falseValue representation of the boolean "false".
     */
    public BooleanToString( final String trueValue, String falseValue )
    {
        
        super( Boolean.class, String.class, "Unable to convert {1} into String" );
        
        if( trueValue == null || trueValue.isEmpty() )
            throw new NullPointerException( "The representation of the value 'true' is mandatory and cannot be null or empty" );

        if( trueValue == null || trueValue.isEmpty() )
            throw new NullPointerException( "The representation of the value 'false' is mandatory and cannot be null or empty" );
        
        this.trueValue = trueValue;
        this.falseValue = falseValue;
        
    }

    
    /* ***************** */
    /*  EXTENSION HOOKS  */
    /* ***************** */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String performConversion( final Boolean source ) throws Exception
    {
        
        return source.booleanValue() ? trueValue : falseValue;
        
    }
    
}