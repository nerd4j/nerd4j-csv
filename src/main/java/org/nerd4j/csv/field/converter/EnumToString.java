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
 * that converts {@link Enum}s into {@link String}s.
 * 
 * 
 * @param <E> type of the {@link Enum} to be returned.
 * 
 * @author Nerd4j Team
 */
public final class EnumToString<E extends Enum<E>> extends AbstractCSVFieldConverter<E,String>
{

    
    /**
     * Default constructor.
     * 
     */
    public EnumToString()
    {

        super( "Unable to convert {1} into String" );
        
    }

    
    /* ***************** */
    /*  EXTENSION HOOKS  */
    /* ***************** */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String performConversion( final E source ) throws Exception
    {

        return source.toString();
        
    }
       
}