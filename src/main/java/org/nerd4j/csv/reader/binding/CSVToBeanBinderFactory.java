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

import java.lang.reflect.Method;

import org.nerd4j.csv.exception.CSVToModelBindingException;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents a <code>Factory</code> able to build and configure
 * {@link CSVToModelBinder}s which populates data objects (beans)
 * using the fields of a related CSV record.
 * 
 * @param <B> type of the data bean to build.
 * 
 * @author Nerd4j Team
 */
public final class CSVToBeanBinderFactory<B> extends AbstractCSVToModelBinderFactory<B,Method>
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVToBeanBinderFactory.class );
    
    
    /** The {@link Class} representing the data bean. */
    private Class<B> beanClass;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param beanClass the type of the data bean.
     */
    public CSVToBeanBinderFactory( final Class<B> beanClass )
    throws CSVToModelBindingException
    {
        
        super( Method.class );
        
        if( beanClass == null )
            throw new CSVToModelBindingException( "The bean type is mandatory" );

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
        
        return ReflectionUtil.findPublicSetter( mappingDescriptor, beanClass );
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected CSVToModelBinder<B> getBinder( final CSVFieldMetadata<?,?>[] fieldConfs, final Method[] fieldMapping )
    
    {
        
        return new CSVToBeanBinder( fieldMapping );
        
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
    private class CSVToBeanBinder implements CSVToModelBinder<B>
    {
        
        /** The internal instance of the data model. */
        private B model;
        
        /**
         * This array is intended to contain a mapping that associates
         * each input column index into the related output bean property.
         */
        private final Method[] columnMapping;

        
        /**
         * Constructor with parameters.
         * 
         * @param columnMapping mapping of the columns.
         */
        public CSVToBeanBinder( final Method[] columnMapping )
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
        public void initModel() throws CSVToModelBindingException
        {
            
            try{
            
                logger.debug( "Creating new bean of type {}.", beanClass );
                this.model = beanClass.newInstance();
            
            }catch( Exception ex )
            {
                
                logger.error( "Unable to instantiate bean of type " + beanClass, ex );
                throw new CSVToModelBindingException( ex );
                
            }
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void fill( int column, Object value ) throws CSVToModelBindingException
        {
                
            final Method setter = column < columnMapping.length
                                ? columnMapping[column]
                                : null;

            try{
                                
                
                if( setter != null )
                {
                    if( logger.isDebugEnabled() )
                        logger.debug( "Setting value {} for column {} using method {}.", value, column, setter.getName() );
                    
                    setter.invoke( model, value );
                }
                else
                {
                    if( logger.isTraceEnabled() )
                        logger.trace( "There is no valid setter for column {} unable to fill value.", column );
                }
            
            }catch( NullPointerException ex )
            {
                
                logger.error( "Try to invoke a method on a null object, the model needs to be initialized before filling.", ex );
                throw new CSVToModelBindingException( "Try to fill a model without initialization", ex );

            }catch( Exception ex )
            {
                
                logger.error( "Unable to invoke method " + setter, ex );
                throw new CSVToModelBindingException( ex );
                
            }
            
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public B getModel()
        {
            
            return model;
            
        }
        
    }

}
