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

import java.util.regex.Pattern;


/**
 * Implementation of the {@link org.nerd4j.csv.field.CSVFieldValidator CSVFieldValidator}
 * interface that checks the given {@link String} matches the given regular expression.
 *
 * @author Nerd4j Team
 */
public final class CheckRegEx extends AbstractCSVFieldValidator<String>
{
    
    /** The regular expression pattern to match. */
    private final Pattern pattern;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param regEx the regular expression pattern to match.
     */
    public CheckRegEx( final String regEx )
    {
        
        super( "The value {1} do not match the regular expression '" + regEx + "'" );

        if( regEx == null || regEx.isEmpty() )
            throw new IllegalArgumentException( "The regular expression pattern is mandatory." );
        
        this.pattern = Pattern.compile( regEx );
        
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
        
        return pattern.matcher( value ).matches();
        
    }
    
}
