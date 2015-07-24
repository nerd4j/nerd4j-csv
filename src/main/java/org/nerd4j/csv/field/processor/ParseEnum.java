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
package org.nerd4j.csv.field.processor;

import org.nerd4j.csv.field.CSVFieldProcessor;
import org.nerd4j.csv.field.CSVFieldValidator;
import org.nerd4j.csv.field.converter.StringToEnum;


/**
 * Represents a CSV field processor able to parse
 * a given {@link String} and return a {@link Enum}.
 * 
 * @param <E> type of the {@link Enum} to be returned.
 * 
 * @author Nerd4j Team
 */
public final class ParseEnum<E extends Enum<E>> extends CSVFieldProcessor<String,E>
{
    
    /**
     * Constructor with parameters.
     * 
     * @param enumType an extension of the {@link Enum} class.
     */
    public ParseEnum( final Class<E> enumType )
    {
        
        super( null, new StringToEnum<E>( enumType ), null );
        
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param enumType an extension of the {@link Enum} class.
     * @param precondition  condition to satisfy before conversion.
     * @param postcondition condition to satisfy after conversion.
     */
    public ParseEnum( final Class<E> enumType,
                      final CSVFieldValidator<String> precondition,
                      final CSVFieldValidator<E> postcondition )
    {
        
        super( precondition, new StringToEnum<E>( enumType ), postcondition );
        
    }

}