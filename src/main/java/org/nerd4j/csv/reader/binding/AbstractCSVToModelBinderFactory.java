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

import java.lang.reflect.Array;

import org.nerd4j.csv.exception.CSVToModelBindingException;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.csv.field.CSVMappingDescriptor;
import org.nerd4j.csv.reader.CSVReaderMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Abstract implementation of the {@link CSVToModelBinderFactory}
 * that contains the code common to all the concrete implementations.
 * 
 * @param <Model> type of the model returned by the builder.
 * @param <Mapping> type of the column mapping descriptor.
 * 
 * @author Nerd4j Team
 */
public abstract class AbstractCSVToModelBinderFactory<Model,Mapping> implements CSVToModelBinderFactory<Model>
{
    
    /** Internal logging system. */
    private static final Logger logger = LoggerFactory.getLogger( AbstractCSVToModelBinderFactory.class );
    
    /** Type of the object used to map the columns to the model. */
    private final Class<Mapping> mappingType;
    
    /**
     * Constructor with parameters.
     * 
     * @param mappingType type of the object used to map the columns to the model.
     */
    public AbstractCSVToModelBinderFactory( Class<Mapping> mappingType )
    throws CSVToModelBindingException
    {
        
        super();
        
        if( mappingType == null )
            throw new CSVToModelBindingException( "The column mapping type is mandatory" );
        
        this.mappingType = mappingType;
        
    }
    
    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CSVToModelBinder<Model> getCSVToModelBinder( final CSVReaderMetadata<Model> configuration,
    		final Integer[] columnMapping )
    				throws CSVToModelBindingException
    {
    	
    	if( configuration == null )
    		throw new CSVToModelBindingException( "The CSV reader configuration is mandatory" );
    	
    	if( columnMapping == null )
    		throw new CSVToModelBindingException( "The CSV column mapping is mandatory" );
    	
    	return createBinder( configuration, columnMapping );
    	
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    /**
     * Creates a new model binder using the provided configuration.
     * 
     * @param configuration used to configure the column mapping for binding.
     * @param columnMapping mapping of the CSV source columns.
     */
    @SuppressWarnings("unchecked")
    private CSVToModelBinder<Model> createBinder( final CSVReaderMetadata<Model> configuration,
                                                  final Integer[] columnMapping )
    throws CSVToModelBindingException
    {
        
        try{
        
            final CSVFieldMetadata<?,?>[] fieldConfs = configuration.getFieldConfigurations();
            
            /* If the mapping is empty we return an empty binder. */
            if( columnMapping == null || fieldConfs == null || fieldConfs.length < 1 )
            {
                final Mapping[] fieldMapping = (Mapping[]) Array.newInstance( mappingType, 0 );
                return getBinder( fieldConfs, fieldMapping );
            }
            
            /* Otherwise we create the internal structure. */
            final Mapping[] fieldMapping = (Mapping[]) Array.newInstance( mappingType, columnMapping.length );
                         
            for( int i = 0; i < columnMapping.length; ++i )
            {
                fieldMapping[i] = columnMapping[i] != null
                                ? getMapping( fieldConfs[columnMapping[i]].getMappingDescriptor() )
                                : null;
            }
            
            return getBinder( fieldConfs, fieldMapping );
        
        }catch( Exception ex )
        {
            
            logger.error( "Unexpected error during model binder creation.", ex );
            throw new CSVToModelBindingException( ex );
            
        }
        
    }
    
    
    /* ***************** */
    /*  EXTENSION HOOKS  */
    /* ***************** */
    
    
    /**
     * Given the mapping descriptor in {@link String} format
     * returns the actual mapping object.
     * 
     * @param mappingDescriptor the mapping descriptor.
     * @return the actual mapping object.
     */
    protected abstract Mapping getMapping( final CSVMappingDescriptor mappingDescriptor );
    
    /**
     * Given the original field configurations and the computed column mapping
     * returns an instance of {@link CSVToModelBinder}.
     * 
     * @param fieldConfs   original fields configurations.
     * @param fieldMapping computed column mapping.
     * @return the actual {@link CSVToModelBinder}.
     */
    protected abstract CSVToModelBinder<Model> getBinder( final CSVFieldMetadata<?,?>[] fieldConfs, final Mapping[] fieldMapping );
    
}
