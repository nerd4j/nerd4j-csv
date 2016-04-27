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

import org.nerd4j.csv.exception.CSVToModelBindingException;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.csv.field.CSVMappingDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents a <code>Factory</code> able to build and configure
 * {@link CSVToModelBinder}s which populates an array of {@link Object}s
 * using the fields of a related CSV record.
 * 
 * @author Nerd4j Team
 */
public final class CSVToArrayBinderFactory extends AbstractCSVToModelBinderFactory<Object[],Integer>
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVToArrayBinderFactory.class );
    
    
    /**
     * Default constructor.
     * 
     */
    public CSVToArrayBinderFactory() throws CSVToModelBindingException
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
    protected Integer getMapping( final CSVMappingDescriptor mappingDescriptor )
    {
        
        return Integer.parseInt( mappingDescriptor.getModelKey() );
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected CSVToModelBinder<Object[]> getBinder( final CSVFieldMetadata<?,?>[] fieldConfs, final Integer[] fieldMapping )
    
    {
        
        final int arraySize = fieldConfs == null ? 0 : fieldConfs.length;
        return new CSVToArrayBinder( arraySize, fieldMapping );
        
    }
    
    
    /* *************** */
    /*  INNER CLASSES  */
    /* *************** */
    
    
    /**
     * Represents an implementation of {@link CSVToModelBinder} that
     * uses an array of {@link String}s as CSV record model.
     * 
     * @author Nerd4j Team
     */
    private class CSVToArrayBinder implements CSVToModelBinder<Object[]>
    {
        
        /** The size of the array representing the CSV record. */
        private final int arraySize;
        
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
         * @param arraySize size of the array representing the CSV record.
         * @param columnMapping mapping of the columns.
         */
        public CSVToArrayBinder( final int arraySize, final Integer[] columnMapping )
        {
            
            super();
            
            this.model = null;
            this.arraySize = arraySize;
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
            
            logger.debug( "Creating new empty array of length {}.", arraySize );
            this.model = new Object[arraySize];
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void fill( int column, Object value )
        throws CSVToModelBindingException
        {

            final Integer mapping;
            if( column < columnMapping.length )
                mapping = columnMapping[column];
            else
                mapping = column;
                                  
            final int index = mapping != null ? mapping : -1;

            try{
            
                if( index >= 0 && index < arraySize )
                {
                    if( logger.isDebugEnabled() )
                        logger.debug( "Put value {} for column {} into the array at position {}.", value, column, index );
                        
                    model[index] = value;
                }
                else
                {
                    if( logger.isTraceEnabled() )
                        logger.trace( "There is no valid array position for column {} unable to fill value.", column );
                }
                
            }catch( NullPointerException ex )
            {
                
                logger.error( "Try to put a value into a unexisting array, the model needs to be initialized before filling.", ex );
                throw new CSVToModelBindingException( "Try to fill a model without initialization", ex );

            }catch( IndexOutOfBoundsException ex )
            {
                
                logger.error( "Try to put a value into a invalid position, the internal mapping is inconsistent.", ex );
                throw new CSVToModelBindingException( "Invalid column mapping " + index + " for column " + column, ex );
                
            }
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] getModel()
        {
            return model;
        }
        
    }

}
