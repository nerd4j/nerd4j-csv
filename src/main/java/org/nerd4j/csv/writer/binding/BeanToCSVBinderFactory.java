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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.nerd4j.csv.exception.ModelToCSVBindingException;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents a {@code Factory} able to build and configure
 * {@link ModelToCSVBinder}s which reads data models and provides
 * CSV record fields in the right order.
 * 
 * @param <B> type of the bean representing the model.
 * 
 * @author Nerd4j Team
 */
public final class BeanToCSVBinderFactory<B> extends AbstractModelToCSVBinderFactory<B,Method>
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( BeanToCSVBinderFactory.class );
    
    
    /** The {@link Class} representing the data bean. */
    private Class<B> beanClass;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param beanClass the type of the data bean.
     * @throws ModelToCSVBindingException if the creation fails.
     */
    public BeanToCSVBinderFactory( final Class<B> beanClass )
    throws ModelToCSVBindingException
    {
        
        super( Method.class );
        
        if( beanClass == null )
            throw new ModelToCSVBindingException( "The bean type is mandatory" );

        this.beanClass = beanClass;
        
    }
    
    
    /* ***************** */
    /*  EXTENSION HOOKS  */
    /* ***************** */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Method getMapping( final String mappingDescriptor )
    {
        
        return ReflectionUtil.findPublicGetter( mappingDescriptor, beanClass );
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelToCSVBinder<B> getBinder( final CSVFieldMetadata<?,?>[] fieldConfs, final Method[] fieldMapping )
    {
        
        return new BeanToCSVBinder( fieldMapping );
        
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
    private class BeanToCSVBinder implements ModelToCSVBinder<B>
    {
        
        /**
         * This array is intended to contain a mapping that associates
         * each input column index into the related bean setter.
         */
        private Method[] columnMapping;
        
        /** The internal instance of the data model. */
        private B model;

        
        /**
         * Constructor with parameters.
         * 
         * @param columnMapping mapping of the columns.
         */
        public BeanToCSVBinder( final Method[] columnMapping )
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
        public void setModel( B model )
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
            
            final Method getter = column < columnMapping.length
                                ? columnMapping[column]
                                : null;
            
            try{
                                    
                if( model == null )
                {
                    logger.error( "Try to read an unexisting bean, the model needs to be set before reading." );
                    throw new ModelToCSVBindingException( "Try to read a model without initialization" );
                }
                    
                    
                if( getter == null )
                {                    
                    logger.error( "There is no valid getter for column {} unable to get value.", column  );
                    throw new ModelToCSVBindingException( "Try to get a value from a invalid position, the internal mapping is inconsistent." );
                }
                    
                final Object value = getter.invoke( model );
                    
                if( logger.isDebugEnabled() )
                    logger.debug( "Got value {} for column {} using method {}.", value, column, getter.getName() );
                    
                return value;
                                
            }catch( InvocationTargetException ex )
            {
                                    
                logger.error( "Unable to invoke method " + getter, ex );
                throw new ModelToCSVBindingException( ex );
                                    
            }catch( IllegalAccessException ex )
            {
                                    
                logger.error( "Unable to invoke method " + getter, ex );
                throw new ModelToCSVBindingException( ex );
                                    
            }
            
        }
        
    }

}
