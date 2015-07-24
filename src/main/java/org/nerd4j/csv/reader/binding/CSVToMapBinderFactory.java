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
package org.nerd4j.csv.reader.binding;

import java.util.HashMap;
import java.util.Map;

import org.nerd4j.csv.exception.CSVToModelBindingException;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents a <code>Factory</code> able to build and configure
 * {@link CSVToModelBinder}s which populates {@link Map}s that
 * associates to column names the related values.
 * 
 * @author Nerd4j Team
 */
public final class CSVToMapBinderFactory extends AbstractCSVToModelBinderFactory<Map<String,Object>,String>
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVToMapBinderFactory.class );
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVToMapBinderFactory() throws CSVToModelBindingException
    {
        
        super( String.class );
        
    }
    
    
    /* ***************** */
    /*  EXTENSION HOOKS  */
    /* ***************** */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String getMapping( final String mappingDescriptor )
    {
        
        return mappingDescriptor;
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected CSVToModelBinder<Map<String,Object>> getBinder( final CSVFieldMetadata<?,?>[] fieldConfs, final String[] fieldMapping )
    {
        
        final int arraySize = fieldConfs == null ? 0 : fieldConfs.length;
        return new CSVToMapBinder( arraySize, fieldMapping );
        
    }
    
    
    /* *************** */
    /*  INNER CLASSES  */
    /* *************** */
    
    
    /**
     * Represents an implementation of {@link CSVToModelBinder} that
     * uses a {@link Map} as CSV record model.
     * 
     * @author Nerd4j Team
     */
    private class CSVToMapBinder implements CSVToModelBinder<Map<String,Object>>
    {
        
        /** The size of the map representing the CSV record. */
        private final int mapSize;
        
        /**
         * This array is intended to contain a mapping that associates
         * each input column index into the related output map key.
         */
        private final String[] columnMapping;
        
        /** The internal instance of the data model. */
        private Map<String,Object> model;

        
        /**
         * Constructor with parameters.
         * 
         * @param mapSize size of the map representing the CSV record.
         * @param columnMapping mapping of the columns.
         */
        public CSVToMapBinder( final int mapSize, final String[] columnMapping )
        {
            
            super();
            
            this.model = null;
            this.mapSize = mapSize;
            this.columnMapping = columnMapping;
            
        }
        
        
        /* ******************* */
        /*  INTERFACE METHODS  */
        /* ******************* */
        
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void initModel()
        {
            
            logger.debug( "Creating new empty map." );
            this.model = new HashMap<String,Object>( mapSize );
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void fill( int column, Object value )
        throws CSVToModelBindingException
        {

            final String key = column < columnMapping.length
                             ? columnMapping[column]
                             : null;

            try{
                            
                if( key != null )
                {
                    if( logger.isDebugEnabled() )
                        logger.debug( "Fill value {} for column {} into map using key {}.", value, column, key );
                        
                    model.put( key, value );
                }
                else
                {
                    if( logger.isTraceEnabled() )
                        logger.trace( "There is no valid key for column {} unable to fill value.", column );
                }
                
            }catch( NullPointerException ex )
            {
                
                logger.error( "Try to put an entry into a unexisting map, the model needs to be initialized before filling.", ex );
                throw new CSVToModelBindingException( "Try to fill a model without initialization", ex );

            }
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Map<String,Object> getModel()
        {
            
            return model;
            
        }
        
    }

}
