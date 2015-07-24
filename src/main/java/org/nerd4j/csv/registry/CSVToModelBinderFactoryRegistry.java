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
package org.nerd4j.csv.registry;

import java.util.Map;

import org.nerd4j.csv.exception.CSVConfigurationException;
import org.nerd4j.csv.exception.CSVToModelBindingException;
import org.nerd4j.csv.reader.binding.CSVToArrayBinderFactory;
import org.nerd4j.csv.reader.binding.CSVToBeanBinderFactory;
import org.nerd4j.csv.reader.binding.CSVToMapBinderFactory;
import org.nerd4j.csv.reader.binding.CSVToModelBinder;
import org.nerd4j.csv.reader.binding.CSVToModelBinderFactory;


/**
 * Represents a registry of {@link CSVToModelBinderFactory} instances.
 * 
 * <p>
 *  This registry is internally used to refer {@link CSVToModelBinderFactory}
 *  by type name. It is possible to register custom binder factories.
 * </p>
 * 
 * <p>
 *  The following {@link CSVToModelBinder} factories are registered by default:
 *  <ul>
 *    <li>array : binds a CSV record to an {@link Object[]}</li>
 *    <li>bean  : binds a CSV record to a Java Bean</li>
 *    <li>map   : binds a CSV record to a {@link Map<String,Object>}</li>
 *  </ul>
 * </p>
 * 
 * @author Nerd4j Team
 */
final class CSVToModelBinderFactoryRegistry extends CSVAbstractRegistry<CSVToModelBinderFactory<?>>
{
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVToModelBinderFactoryRegistry()
    {
        
        super();
        
        this.registerDefaults();
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    /**
     * Creates and registers the default entries and binders.
     * 
     */
    private void registerDefaults()
    {
        
        /*  CSV to array binder factory provider. */
        setProvider( "array", new CSVRegistryEntryProvider<CSVToModelBinderFactory<?>>()
        {
            @Override
            public CSVToModelBinderFactory<?> get( Map<String,String> params )
            {
                try{
                
                    return new CSVToArrayBinderFactory();
                    
                }catch( CSVToModelBindingException ex )
                {
                    throw new CSVConfigurationException( "Unable to build binder of type 'array'", ex );
                }
            }
        });
        
        /*  CSV to bean binder factory provider. */
        setProvider( "bean", new CSVRegistryEntryProvider<CSVToModelBinderFactory<?>>()
        {
            @Override
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public CSVToModelBinderFactory<?> get( Map<String,String> params )
            {
                
                final String beanType = params.get( "bean-class" );
                if( beanType == null )
                    throw new CSVConfigurationException( "The bean-class is mandatory to build CSVToBeanBinderFactory" );
                
                try{

                    final Class<?> beanClass = Class.forName( beanType );
                    return new CSVToBeanBinderFactory( beanClass );
                    
                }catch( ClassNotFoundException ex )
                {
                    throw new CSVConfigurationException( "The value bean-class do not represent a canonical class name", ex );
                }catch( CSVToModelBindingException ex )
                {
                    throw new CSVConfigurationException( "Unable to build binder of type 'bean'", ex );
                }
            }
        }); 
        
        /*  CSV to map binder factory provider. */
        setProvider( "map", new CSVRegistryEntryProvider<CSVToModelBinderFactory<?>>()
        {
            @Override
            public CSVToModelBinderFactory<?> get( Map<String,String> params )
            {
                try{
                
                    return new CSVToMapBinderFactory();
                    
                }catch( CSVToModelBindingException ex )
                {
                    throw new CSVConfigurationException( "Unable to build binder of type 'map'", ex );
                }
            }
        }); 
        
    }
}