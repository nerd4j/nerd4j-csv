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
import org.nerd4j.csv.field.processor.EmptyCSVFieldProcessor;
import org.nerd4j.csv.formatter.CSVFormatterMetadata;
import org.nerd4j.csv.model.TestBean;
import org.nerd4j.csv.writer.CSVWriterMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BeanToCSVBinderFactoryTest
{
    
    private static final Logger logger = LoggerFactory.getLogger( BeanToCSVBinderFactoryTest.class );

    private static final boolean USE_HEADER = false;
    
    
    
    @Test
    public void testFailNoSet() throws Exception
    {
        
        final ModelToCSVBinder<TestBean> binder = getBinder();
        
        try{
            
            for( int i = 0; i < 10; ++i )
                logger.debug( String.valueOf(binder.getValue(i)) );
            
            Assert.fail( "An exception was expected but not thrown." );
            
        }catch( ModelToCSVBindingException ex )
        {
            logger.info( "Expected exception has been thrown", ex.getMessage() );
        }
        
    }
    
    @Test
    public void testModelBiggerThanMapping() throws Exception
    {
        
        final ModelToCSVBinder<TestBean> binder = getBinder();
        
        final TestBean model = new TestBean();
        model.setValue1( 1 );
        model.setValue2( 2 );
        model.setValue3( 3 );
        model.setValue4( 4 );
        model.setValue5( 5 );
        model.setValue6( 6 );
        model.setValue7( 7 );
        model.setValue8( 8 );
        model.setValue9( 9 );
        
        binder.setModel( model );
        for( int i = 0; i < binder.getRecordSize(); ++i )
            logger.debug( String.valueOf(binder.getValue(i)) );
                
    }
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testModelSmallerThanMapping() throws Exception
    {
        
        try{
            
            final CSVField field = new CSVField( new EmptyCSVFieldProcessor(), false );
            final CSVFieldMetadata<Object,String>[] fieldConfs = new CSVFieldMetadata[3];
            
            fieldConfs[0] = new CSVFieldMetadata<Object,String>( "COL-1", "value10", field );
        
            final CSVFormatterMetadata formatterConfiguration = new CSVFormatterMetadata();
            final ModelToCSVBinderFactory<TestBean> binderFactory = new BeanToCSVBinderFactory<TestBean>( TestBean.class );
            
            final CSVWriterMetadata<TestBean> configuration = new CSVWriterMetadata<TestBean>( formatterConfiguration, binderFactory, fieldConfs, USE_HEADER );
            binderFactory.getModelToCSVBinder( configuration );
            
            Assert.fail( "An exception was expected but not thrown." );
            
        }catch( Exception ex )
        {
            logger.info( "Expected exception has been thrown", ex.getMessage() );
        }
        
    }

    @Test
    public void testIndexOutOfMapping() throws Exception
    {
        
        final ModelToCSVBinder<TestBean> binder = getBinder();
        
        try{
            
            final TestBean model = new TestBean();
            model.setValue1( 1 );
            model.setValue2( 2 );
            model.setValue3( 3 );
            model.setValue4( 4 );
            
            logger.debug( String.valueOf(binder.getValue(binder.getRecordSize() + 1)) );
            
            Assert.fail( "An exception was expected but not thrown." );
            
        }catch( ModelToCSVBindingException ex )
        {
            logger.info( "Expected exception has been thrown", ex.getMessage() );
        }
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private CSVWriterMetadata<TestBean> getModelToCSVWriterConfiguration()
    throws ModelToCSVBindingException
    {
                       
        final CSVField field = new CSVField( new EmptyCSVFieldProcessor(), false );
        final CSVFieldMetadata<Object,String>[] fieldConfs = new CSVFieldMetadata[3];
        
        fieldConfs[0] = new CSVFieldMetadata<Object,String>( "COL-1", "value1", field );
        fieldConfs[1] = new CSVFieldMetadata<Object,String>( "COL-2", "value3", field );
        fieldConfs[2] = new CSVFieldMetadata<Object,String>( "COL-3", "value5", field );
        
        final CSVFormatterMetadata formatterConfiguration = new CSVFormatterMetadata();
        final ModelToCSVBinderFactory<TestBean> binderFactory = new BeanToCSVBinderFactory<TestBean>( TestBean.class );
        
        return new CSVWriterMetadata<TestBean>( formatterConfiguration, binderFactory, fieldConfs, USE_HEADER );
                            
    }
    
    private ModelToCSVBinder<TestBean> getBinder() throws ModelToCSVBindingException
    {
        
        final CSVWriterMetadata<TestBean> configuration = getModelToCSVWriterConfiguration();
        final BeanToCSVBinderFactory<TestBean> binderFactory = new BeanToCSVBinderFactory<TestBean>( TestBean.class );
        
        return binderFactory.getModelToCSVBinder( configuration );
        
    }
    
}