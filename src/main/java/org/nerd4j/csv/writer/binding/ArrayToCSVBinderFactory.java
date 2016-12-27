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

import org.nerd4j.csv.exception.ModelToCSVBindingException;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents a {@code Factory} able to build and configure
 * {@link ModelToCSVBinder}s which reads data models and provides
 * CSV record fields in the right order.
 * 
 * @author Nerd4j Team
 */
public final class ArrayToCSVBinderFactory extends AbstractModelToCSVBinderFactory<Object[],Integer>
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( ArrayToCSVBinderFactory.class );
    
    
    /**
     * Default constructor.
     * 
     * @throws ModelToCSVBindingException if the creation fails.
     */
    public ArrayToCSVBinderFactory() throws ModelToCSVBindingException
    {
        
        super( Integer.class );
        
    }
    
    
    /* ***************** */
    /*  EXTENSION HOOKS  */
    /* ***************** */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getMapping( final String mappingDescriptor )
    {
        
        return Integer.parseInt( mappingDescriptor );
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelToCSVBinder<Object[]> getBinder( final CSVFieldMetadata<?,?>[] fieldConfs, final Integer[] fieldMapping )
    {
        
        return new ArrayToCSVBinder( fieldMapping );
        
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
    private class ArrayToCSVBinder implements ModelToCSVBinder<Object[]>
    {
        
        /**
         * This array is intended to contain a mapping that associates
         * each input column index into the related output column index.
         */
        private final Integer[] columnMapping;
        
        /** The internal instance of the data model. */
        private Object[] model;

        
        /**
         * Constructor with parameters.
         * 
         * @param columnMapping mapping of the columns.
         */
        public ArrayToCSVBinder( final Integer[] columnMapping )
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
        public void setModel( Object[] model )
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
            
            final Integer mapping;
            if( column < columnMapping.length )
                mapping = columnMapping[column];
            else
                mapping = column;
            
            final int index = mapping != null ? mapping : -1;
            
            try{
                
                if( logger.isDebugEnabled() )
                    logger.debug( "Got value {} for data model at column {}.", model[index], column );
                        
                return model[index];
                
            }catch( NullPointerException ex )
            {
                
                logger.error( "Try to get a value from a unexisting array, the model needs to be set before reading.", ex );
                throw new ModelToCSVBindingException( "Try to read a model without initialization", ex );

            }catch( IndexOutOfBoundsException ex )
            {
                
                logger.error( "Try to get a value from a invalid position, the internal mapping is inconsistent.", ex );
                throw new ModelToCSVBindingException( "Invalid column mapping " + index + " for column " + column, ex );
                
            }
            
        }
        
    }

}
