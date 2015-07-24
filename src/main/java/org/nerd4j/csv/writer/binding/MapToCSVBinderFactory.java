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
package org.nerd4j.csv.writer.binding;

import java.util.Map;

import org.nerd4j.csv.exception.ModelToCSVBindingException;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents a <code>Factory</code> able to build and configure
 * {@link ModelToCSVBinder}s which reads data models and provides
 * CSV record fields in the right order.
 *  
 * @author Nerd4j Team
 */
public final class MapToCSVBinderFactory extends AbstractModelToCSVBinderFactory<Map<String,Object>,String>
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( MapToCSVBinderFactory.class );
    
    
    /**
     * Default constructor.
     * 
     */
    public MapToCSVBinderFactory() throws ModelToCSVBindingException
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
    protected ModelToCSVBinder<Map<String,Object>> getBinder( final CSVFieldMetadata<?,?>[] fieldConfs, final String[] fieldMapping )
    {
        
        return new MapToCSVBinder( fieldMapping );
        
    }
    
    
    /* *************** */
    /*  INNER CLASSES  */
    /* *************** */
    
    
    /**
     * Represents an implementation of {@link ModelToCSVBinder} that
     * uses an array of {@link Object}s as CSV record model.
     * 
     * @author Nerd4j Team
     */
    private class MapToCSVBinder implements ModelToCSVBinder<Map<String,Object>>
    {
        
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
         * @param columnMapping mapping of the columns.
         */
        public MapToCSVBinder( final String[] columnMapping )
        {
            
            super();
            
            this.model = null;
            this.columnMapping = columnMapping;
            
        }
        
        
        /* ******************* */
        /*  INTERFACE METHODS  */
        /* ******************* */
        
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void setModel( Map<String,Object> model )
        {
            
            this.model = model;
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int getRecordSize()
        {
            
            return columnMapping.length;
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Object getValue( int column ) throws ModelToCSVBindingException
        {
            
            final String key = column < columnMapping.length
                    ? columnMapping[column]
                    : null;
            
            try{
                
                if( key != null && model.containsKey(key) )
                {
                    
                    final Object value = model.get( key );
                    
                    if( logger.isDebugEnabled() )
                        logger.debug( "Get value {} for column {} from map using key {}.", value, column, key );
                       
                    return value;
                    
                }
                else
                {

                    if( logger.isTraceEnabled() )
                        logger.trace( "There is no valid key for column {} unable to get value.", column );
                    
                    throw new IndexOutOfBoundsException();
                }
                               
           }catch( NullPointerException ex )
           {
               
               logger.error( "Try to get an entry from an unexisting map, the model needs to be set before reading.", ex );
               throw new ModelToCSVBindingException( "Try to read a model without initialization", ex );
        
           }catch( IndexOutOfBoundsException ex )
           {
               
               logger.error( "Try to get a value from a invalid position, the internal mapping is inconsistent.", ex );
               throw new ModelToCSVBindingException( "Invalid column mapping " + key + " for column " + column, ex );
               
           }
            
        }
        
    }

}
