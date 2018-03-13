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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.nerd4j.csv.exception.CSVToModelBindingException;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.csv.field.CSVMappingDescriptor;
import org.nerd4j.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents a {@code Factory} able to build and configure
 * {@link CSVToModelBinder}s which populates data objects (beans)
 * using the fields of a related CSV record.
 * 
 * @param <B> type of the data bean to build.
 * 
 * @author Nerd4j Team
 */
public final class CSVToBeanBinderFactory<B> extends AbstractCSVToModelBinderFactory<B,CSVToBeanFieldWriter>
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVToBeanBinderFactory.class );
    
    
    /** The {@link Class} representing the data bean. */
    private Class<B> beanClass;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param beanClass the type of the data bean.
     * @throws CSVToModelBindingException if the creation fails.
     */
    public CSVToBeanBinderFactory( final Class<B> beanClass )
    throws CSVToModelBindingException
    {
        
        super( CSVToBeanFieldWriter.class );
        
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
    protected CSVToBeanFieldWriter getMapping( final CSVMappingDescriptor mappingDescriptor )
    {
        
        final Method setter = ReflectionUtil.findPublicSetter( mappingDescriptor.getModelId(), beanClass );
        if( setter != null )return CSVToBeanFieldWriter.getWriter( setter );
        
        final Field field = ReflectionUtil.findField( mappingDescriptor.getModelType(), mappingDescriptor.getModelId(), beanClass );
        if( field != null ) return CSVToBeanFieldWriter.getWriter( field );
        	
        throw new NullPointerException( "There isn't a valid setter or field related to " + mappingDescriptor );
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected CSVToModelBinder<B> getBinder( final CSVFieldMetadata<?,?>[] fieldConfs, final CSVToBeanFieldWriter[] fieldMapping )
    throws CSVToModelBindingException
    {
        
    	try{
        
    		return new CSVToBeanBinder( fieldMapping );
    		
    	}catch( NoSuchMethodException ex )
    	{
    		
    		throw new CSVToModelBindingException( ex );
    		
		}
        
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
        
        /** Default constructor for bean of the given type. */
        private final Constructor<B> constructor;
        
        /**
         * This array is intended to contain a mapping that associates
         * each input column index into the related output bean property.
         */
        private final CSVToBeanFieldWriter[] columnMapping;

        
        /**
         * Constructor with parameters.
         * 
         * @param columnMapping mapping of the columns.
         */
        public CSVToBeanBinder( final CSVToBeanFieldWriter[] columnMapping )
        throws NoSuchMethodException
        {
            
            super();
            
            this.model = null;
            this.columnMapping = columnMapping;
            this.constructor = beanClass.getDeclaredConstructor();
            
            /*
             * We make the constructor accessible regardless
             * of the visibility defined in the bean class.
             */
           constructor.setAccessible( true );
            
            
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
                this.model = constructor.newInstance();
                            
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
                
            final CSVToBeanFieldWriter writer = column < columnMapping.length
                                              ? columnMapping[column]
                                              : null;

            try{
                                
                
                if( writer != null )
                {
                    if( logger.isDebugEnabled() )
                        logger.debug( "Setting value {} for column {} using writer {}.", value, column, writer.getName() );
                    
                    writer.write( value, model );
                }
                else
                {
                    if( logger.isTraceEnabled() )
                        logger.trace( "There is no valid field writer for column {} unable to fill value.", column );
                }
            
            }catch( NullPointerException ex )
            {
                
                logger.error( "Try to invoke a field writer on a null object, the model needs to be initialized before filling.", ex );
                throw new CSVToModelBindingException( "Try to fill a model without initialization", ex );

            }catch( Exception ex )
            {
                
                logger.error( "Unable to invoke field writer " + writer.getName(), ex );
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
