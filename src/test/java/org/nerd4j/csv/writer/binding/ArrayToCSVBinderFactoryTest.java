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

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.exception.ModelToCSVBindingException;
import org.nerd4j.csv.field.CSVField;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.csv.field.CSVMappingDescriptor;
import org.nerd4j.csv.field.processor.EmptyCSVFieldProcessor;
import org.nerd4j.csv.formatter.CSVFormatterMetadata;
import org.nerd4j.csv.writer.CSVWriterMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ArrayToCSVBinderFactoryTest
{
    
    private static final Logger logger = LoggerFactory.getLogger( ArrayToCSVBinderFactoryTest.class );

    private static final boolean USE_HEADER = false;
    
    
    
    @Test
    public void testFailNoSet() throws Exception
    {
        
        final ModelToCSVBinder<Object[]> binder = getBinder();
        
        try{
            
            for( int i = 0; i < 10; ++i )
                logger.debug( String.valueOf(binder.getValue(i)) );
            
            Assert.fail( "An exception was expected but not thrown." );
            
        }catch( ModelToCSVBindingException ex )
        {
            logger.info( "Expected exception has been thrown: {}", ex.getMessage() );
        }
        
    }
    
    @Test
    public void testModelBiggerThanMapping() throws Exception
    {
        
        final ModelToCSVBinder<Object[]> binder = getBinder();
        
        binder.setModel( new Object[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 } );
        for( int i = 0; i < binder.getRecordSize(); ++i )
            logger.debug( String.valueOf(binder.getValue(i)) );
                
    }
    
    @Test
    public void testModelSmallerThanMapping() throws Exception
    {
        
        final ModelToCSVBinder<Object[]> binder = getBinder();
        
        try{
            
            binder.setModel( new Object[] { 1, 2, 3, 4 } );
            for( int i = 0; i < binder.getRecordSize(); ++i )
                logger.debug( String.valueOf(binder.getValue(i)) );
        
            Assert.fail( "An exception was expected but not thrown." );
            
        }catch( ModelToCSVBindingException ex )
        {
            logger.info( "Expected exception has been thrown: {}", ex.getMessage() );
        }
        
    }

    @Test
    public void testIndexOutOfMapping() throws Exception
    {
        
        final ModelToCSVBinder<Object[]> binder = getBinder();
        
        try{
            
            binder.setModel( new Object[] { 1, 2, 3, 4 } );
            logger.debug( String.valueOf(binder.getValue(binder.getRecordSize() + 1)) );
            
            Assert.fail( "An exception was expected but not thrown." );
            
        }catch( ModelToCSVBindingException ex )
        {
            logger.info( "Expected exception has been thrown: {}", ex.getMessage() );
        }
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private CSVWriterMetadata<Object[]> getModelToCSVWriterConfiguration()
    throws ModelToCSVBindingException
    {
                       
        final CSVField field = new CSVField( new EmptyCSVFieldProcessor(String.class), false );
        final CSVFieldMetadata<Object,String>[] fieldConfs = new CSVFieldMetadata[3];

        fieldConfs[0] = new CSVFieldMetadata<Object,String>( new CSVMappingDescriptor("COL-1","0",String.class), field, 1 );
        fieldConfs[1] = new CSVFieldMetadata<Object,String>( new CSVMappingDescriptor("COL-2","2",String.class), field, 2 );
        fieldConfs[2] = new CSVFieldMetadata<Object,String>( new CSVMappingDescriptor("COL-3","4",String.class), field, 3 );
        
        final CSVFormatterMetadata formatterConfiguration = new CSVFormatterMetadata();
        final ModelToCSVBinderFactory<Object[]> binderFactory = new ArrayToCSVBinderFactory();
        
        return new CSVWriterMetadata<Object[]>( formatterConfiguration, binderFactory, fieldConfs, USE_HEADER );
                            
    }
    
    private ModelToCSVBinder<Object[]> getBinder() throws ModelToCSVBindingException
    {
        
        final ArrayToCSVBinderFactory binderFactory = new ArrayToCSVBinderFactory();
        final CSVWriterMetadata<Object[]> configuration = getModelToCSVWriterConfiguration();
        
        final CSVFieldMetadata<?,String>[] fieldConf = configuration.getFieldConfigurations();
        final String[] columnIds = new String[fieldConf.length];
        for( int i = 0; i < fieldConf.length; ++i )
        	columnIds[i] = fieldConf[i].getMappingDescriptor().getColumnId();
        
        return binderFactory.getModelToCSVBinder( configuration, columnIds );
        
    }
    
}
