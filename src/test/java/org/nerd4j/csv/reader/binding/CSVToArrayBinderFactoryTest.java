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

import org.junit.Assert;
import org.junit.Test;
import org.nerd4j.csv.exception.CSVToModelBindingException;
import org.nerd4j.csv.field.CSVField;
import org.nerd4j.csv.field.CSVFieldMetadata;
import org.nerd4j.csv.field.CSVMappingDescriptor;
import org.nerd4j.csv.field.processor.EmptyCSVFieldProcessor;
import org.nerd4j.csv.parser.CSVParserMetadata;
import org.nerd4j.csv.reader.CSVReaderMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CSVToArrayBinderFactoryTest
{
    
    private static final Logger logger = LoggerFactory.getLogger( CSVToArrayBinderFactoryTest.class );

    
    @Test
    public void testFailNoInit() throws Exception
    {
        
        final CSVToModelBinder<Object[]> binder = getBinder();
                        
        try{

            binder.fill( 1, "VALUE" );
            
            Assert.fail( "An exception was expected but not thrown." );
            
        }catch( Exception ex )
        {
            
        }
        
    }
    
    @Test
    public void testModelBiggerThanMapping() throws Exception
    {
        
        final CSVToModelBinder<Object[]> binder = getBinder();
        
        binder.initModel();
        for( int i = 0; i < 10; ++i )
            binder.fill( i, "VALUE-" + i );
        
        final Object[] array = binder.getModel();
        for( Object o : array )
            logger.info( String.valueOf(o) );
                
    }
    
    @Test
    public void testModelSmallerThanMapping() throws Exception
    {
        
        final CSVToModelBinder<Object[]> binder = getBinder();
        
        binder.initModel();
        for( int i = 0; i < 4; ++i )
            binder.fill( i, "VALUE-" + i );
        
        final Object[] array = binder.getModel();
        for( Object o : array )
            logger.info( String.valueOf(o) );
                
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private CSVToModelBinder<Object[]> getBinder() throws CSVToModelBindingException
    {
        
        final CSVFieldMetadata<String,?>[] fieldConfs = new CSVFieldMetadata[3];
        final CSVField field = new CSVField( new EmptyCSVFieldProcessor(String.class), true );
        
        fieldConfs[0] = new CSVFieldMetadata( new CSVMappingDescriptor("1","0",String.class), field );
        fieldConfs[1] = new CSVFieldMetadata( new CSVMappingDescriptor("2","1",String.class), field );
        fieldConfs[2] = new CSVFieldMetadata( new CSVMappingDescriptor("4","2",String.class), field );
        
        final CSVParserMetadata parserConfiguration = new CSVParserMetadata();
        final CSVToArrayBinderFactory binderFactory = new CSVToArrayBinderFactory();
        
        final CSVReaderMetadata<Object[]> configuration =
          new CSVReaderMetadata<Object[]>( parserConfiguration, binderFactory, fieldConfs, true, false, false );
        
        return binderFactory.getCSVToModelBinder( configuration, new Integer[] { null, 0, 1, null, 2 } );
        
    }
    
}
