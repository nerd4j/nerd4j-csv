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

/**
 * Implementation of the {@link org.nerd4j.csv.field.CSVFieldConverter CSVFieldConverter}
 * interface that converts {@link String}s into {@link Boolean}s.
 * 
 * <p>
 * This converter accepts the following representations
 * of a boolean value (the parsing ignores the character case):
 * <ul>
 *  <li>1, 0</li>
 *  <li>y, n; Y, N;</li>
 *  <li>t, f; T, F</li>
 *  <li>Yes, No; YES, NO; etc.</li>
 *  <li>true, false; True, False; TRUE, FALSE; etc.</li>   
 * </ul>
 * 
 * @author Nerd4j Team
 */
public final class StringToBoolean extends AbstractCSVFieldConverter<String,Boolean>
{

    
    /**
     * Default constructor.
     * 
     */
    public StringToBoolean()
    {

        super( String.class, Boolean.class, "Unable to convert {1} into a Boolean value" );
        
    }

    
    /* ***************** */
    /*  EXTENSION HOOKS  */
    /* ***************** */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Boolean performConversion( final String source ) throws Exception
    {
        
        switch( source.length() )
        {
        case 1: return parseCharacter( source.charAt(0) );
        case 2: return "no".equalsIgnoreCase(source) ? Boolean.FALSE : null;
        case 3: return "yes".equalsIgnoreCase(source) ? Boolean.TRUE : null;
        case 4: return "true".equalsIgnoreCase(source) ? Boolean.TRUE : null;
        case 5: return "false".equalsIgnoreCase(source) ? Boolean.FALSE : null;
        
        default: return null;
        }
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    /**
     * If the source value is made of a single character
     * returns the boolean value related to such character.
     * 
     * @param source the value to parse.
     * @return the corresponding boolean or {@code null}.
     */
    private Boolean parseCharacter( final char source )
    {
               
        switch( source )
        {
        
        case '1': return Boolean.TRUE;
        case 't': return Boolean.TRUE;
        case 'T': return Boolean.TRUE;
        case 'y': return Boolean.TRUE;
        case 'Y': return Boolean.TRUE;

        case '0': return Boolean.FALSE;
        case 'f': return Boolean.FALSE;
        case 'F': return Boolean.FALSE;
        case 'n': return Boolean.FALSE;
        case 'N': return Boolean.FALSE;
        
        default: return null;
        
        }
        
    }
    
}