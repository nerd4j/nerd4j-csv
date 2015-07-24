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
import org.nerd4j.csv.exception.ModelToCSVBindingException;
import org.nerd4j.csv.writer.binding.ArrayToCSVBinderFactory;
import org.nerd4j.csv.writer.binding.BeanToCSVBinderFactory;
import org.nerd4j.csv.writer.binding.MapToCSVBinderFactory;
import org.nerd4j.csv.writer.binding.ModelToCSVBinder;
import org.nerd4j.csv.writer.binding.ModelToCSVBinderFactory;


/**
 * Represents a registry of {@link ModelToCSVBinderFactory} instances.
 * 
 * <p>
 *  This registry is internally used to refer {@link ModelToCSVBinderFactory}
 *  by type name. It is possible to register custom binder factories.
 * </p>
 * 
 * <p>
 *  The following {@link ModelToCSVBinder} factories are registered by default:
 *  <ul>
 *    <li>array : binds an {@link Object[]} to a CSV record</li>
 *    <li>bean  : binds a Java Bean to a CSV record</li>
 *    <li>map   : binds a {@link Map<String,Object>} to a CSV record</li>
 *  </ul>
 * </p>
 * 
 * @author Nerd4j Team
 */
final class ModelToCSVBinderFactoryRegistry extends CSVAbstractRegistry<ModelToCSVBinderFactory<?>>
{
    
    
    /**
     * Default constructor.
     * 
     */
    public ModelToCSVBinderFactoryRegistry()
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
        setProvider( "array", new CSVRegistryEntryProvider<ModelToCSVBinderFactory<?>>()
        {
            @Override
            public ModelToCSVBinderFactory<?> get( Map<String,String> params )
            {
                try{
                    
                    return new ArrayToCSVBinderFactory();
                    
                }catch( ModelToCSVBindingException ex )
                {
                    throw new CSVConfigurationException( "Unable to build binder of type 'array'", ex );
                }
            }
        });
        
        /*  CSV to bean binder factory provider. */
        setProvider( "bean", new CSVRegistryEntryProvider<ModelToCSVBinderFactory<?>>()
        {
            @Override
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public ModelToCSVBinderFactory<?> get( Map<String,String> params )
            {
                    
                final String beanType = params.get( "bean-class" );
                if( beanType == null )
                    throw new CSVConfigurationException( "The bean-class is mandatory to build BeanToCSVBinderFactory" );
                    
                try{
                    
                    final Class<?> beanClass = Class.forName( beanType );
                    return new BeanToCSVBinderFactory( beanClass );
                    
                }catch( ClassNotFoundException ex )
                {
                    throw new CSVConfigurationException( "The value bean-class do not represent a canonical class name", ex );
                }catch( ModelToCSVBindingException ex )
                {
                    throw new CSVConfigurationException( "Unable to build binder of type 'array'", ex );
                }
            }
        }); 
        
        /*  CSV to map binder factory provider. */
        setProvider( "map", new CSVRegistryEntryProvider<ModelToCSVBinderFactory<?>>()
        {
            @Override
            public ModelToCSVBinderFactory<?> get( Map<String,String> params )
            {
                try{
                    
                    return new MapToCSVBinderFactory();
                    
                }catch( ModelToCSVBindingException ex )
                {
                    throw new CSVConfigurationException( "Unable to build binder of type 'map'", ex );
                }
            }
        }); 
        
    }
}