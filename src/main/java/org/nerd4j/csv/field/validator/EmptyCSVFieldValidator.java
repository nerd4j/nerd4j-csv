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
 * Represents an empty validator that never fails.
 * 
 * @author Nerd4j Team
 */
public final class EmptyCSVFieldValidator implements CSVFieldValidator<Object>
{
    
    
    /**
     * Default constructor.
     * 
     */
    public EmptyCSVFieldValidator()
    {
        
        super();
        
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
        return "This validator never fails!";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void apply( final Object value, final CSVFieldProcessContext context ) {}

}