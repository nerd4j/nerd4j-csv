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

import java.util.Locale;

import org.nerd4j.csv.field.CSVFieldProcessor;
import org.nerd4j.csv.field.CSVFieldValidator;
import org.nerd4j.csv.field.converter.StringToNumber;


/**
 * Represents a CSV field processor able to parse
 * a given {@link String} and return a {@link Number}.
 * 
 * @param <N> type of the {@link Number} to be returned.
 * 
 * @author Nerd4j Team
 */
public final class ParseNumber<N extends Number> extends CSVFieldProcessor<String,N>
{
    
    /**
     * Constructor with parameters.
     * 
     * @param numberType one of the accepted implementations of the {@link Number}.
     */
    public ParseNumber( final Class<N> numberType )
    {
        
        super( null, new StringToNumber<N>(numberType), null );
        
    }
    
    
    /**
     * Constructor with parameters.
     * 
     * @param numberType one of the accepted implementations of the {@link Number}.
     * @param numberPattern the pattern that describes the number format.
     */
    public ParseNumber( final Class<N> numberType, final String numberPattern )
    {
        
        super( null, new StringToNumber<N>(numberType, numberPattern), null );
        
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param numberType one of the accepted implementations of the {@link Number}.
     * @param numberPattern the pattern that describes the number format.
     * @param numberLocale locale for formatter symbols, ignored if no pattern
     */
    public ParseNumber( final Class<N> numberType, final String numberPattern, final Locale numberLocale )
    {
        
        super( null, new StringToNumber<N>(numberType, numberPattern, numberLocale), null );
        
    }
    
    
    /**
     * Constructor with parameters.
     * 
     * @param numberType    one of the accepted implementations of the {@link Number}.
     * @param precondition  condition to satisfy before conversion.
     * @param postcondition condition to satisfy after conversion.
     */
    public ParseNumber( final Class<N> numberType,
                        final CSVFieldValidator<String> precondition,
                        final CSVFieldValidator<N> postcondition )
    {
        
        super( precondition, new StringToNumber<N>(numberType), postcondition );
        
    }
        
    /**
     * Constructor with parameters.
     * 
     * @param numberType    one of the accepted implementations of the {@link Number}.
     * @param numberPattern the pattern that describes the number format.
     * @param precondition  condition to satisfy before conversion.
     * @param postcondition condition to satisfy after conversion.
     */
    public ParseNumber( final Class<N> numberType,
                        final String numberPattern,
                        final CSVFieldValidator<String> precondition,
                        final CSVFieldValidator<N> postcondition )
    {
        
        super( precondition, new StringToNumber<N>(numberType, numberPattern), postcondition );
        
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param numberType    one of the accepted implementations of the {@link Number}.
     * @param numberPattern the pattern that describes the number format.
     * @param numberLocale locale for formatter symbols, ignored if no pattern
     * @param precondition  condition to satisfy before conversion.
     * @param postcondition condition to satisfy after conversion.
     */
    public ParseNumber( final Class<N> numberType,
                        final String numberPattern,
                        final Locale numberLocale,
                        final CSVFieldValidator<String> precondition,
                        final CSVFieldValidator<N> postcondition )
    {
        
        super( precondition, new StringToNumber<N>(numberType, numberPattern, numberLocale), postcondition );
        
    }
    
}