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
package org.nerd4j.csv.reader;

import org.nerd4j.csv.conf.CSVMetadataRegister;
import org.nerd4j.csv.conf.mapping.CSVColumnConf;
import org.nerd4j.csv.conf.mapping.CSVConfiguration;
import org.nerd4j.csv.conf.mapping.CSVFieldConverterConf;
import org.nerd4j.csv.conf.mapping.CSVFieldProcessorConf;
import org.nerd4j.csv.conf.mapping.CSVReaderConf;
import org.nerd4j.csv.model.Product;
import org.nerd4j.csv.registry.CSVRegistry;


public class CSVReaderConfigurator
{
    
    public static <M> CSVReaderMetadataFactory<M> getCSVToArrayReaderMetadataFactory( boolean acceptIncompleteRecords ) throws Exception
    {
    	
    	final CSVReaderConf readerConf = new CSVReaderConf();
    	readerConf.setAcceptIncompleteRecords( acceptIncompleteRecords );
    	readerConf.getModelBinder().setType( "array" );
        readerConf.setName( "reader" );
    	
    	CSVColumnConf columnConf;        

        columnConf = new CSVColumnConf();
        columnConf.setName( "NAME" );
        columnConf.setMapping( "0" );
        columnConf.setProcessor( null );
        columnConf.setOptional( false );
        readerConf.getColumns().put( columnConf.getName(), columnConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "DESCRIPTION" );
        columnConf.setMapping( "1" );
        columnConf.setProcessor( null );
        columnConf.setOptional( false );
        readerConf.getColumns().put( columnConf.getName(), columnConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "UPC" );
        columnConf.setMapping( "2" );
        columnConf.setProcessorRef( "parseLong" );
        columnConf.setOptional( false );
        readerConf.getColumns().put( columnConf.getName(), columnConf );
        
        final CSVFieldConverterConf enumConverterConf = new CSVFieldConverterConf();
        enumConverterConf.setType( "parseEnum" );
        enumConverterConf.getParams().put( "enum-type", "org.nerd4j.csv.model.Product$Currency" );
        
        final CSVFieldProcessorConf enumProcessorConf = new CSVFieldProcessorConf();
        enumProcessorConf.setConverter( enumConverterConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "CURRENCY" );
        columnConf.setMapping( "3" );
        columnConf.setProcessor( enumProcessorConf );
        columnConf.setOptional( false );
        readerConf.getColumns().put( columnConf.getName(), columnConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "PRICE" );
        columnConf.setMapping( "4" );
        columnConf.setProcessorRef( "parseFloat" );
        columnConf.setOptional( false );
        readerConf.getColumns().put( columnConf.getName(), columnConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "IN-STOCK" );
        columnConf.setMapping( "5" );
        columnConf.setProcessorRef( "parseBoolean" );
        columnConf.setOptional( true );
        readerConf.getColumns().put( columnConf.getName(), columnConf );
        
        final CSVFieldConverterConf dateConverterConf = new CSVFieldConverterConf();
        dateConverterConf.setType( "parseDate" );
        dateConverterConf.getParams().put( "pattern", "dd-MM-yy" );
        
        final CSVFieldProcessorConf dateProcessorConf = new CSVFieldProcessorConf();
        dateProcessorConf.setConverter( dateConverterConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "LAST-UPDATE" );
        columnConf.setMapping( "6" );
        columnConf.setProcessor( dateProcessorConf );
        columnConf.setOptional( true );
        readerConf.getColumns().put( columnConf.getName(), columnConf );
        
        final CSVConfiguration configuration = new CSVConfiguration();
        configuration.getReaders().put( "reader", readerConf );
        
        final CSVRegistry registry = new CSVRegistry();
        CSVMetadataRegister.register( configuration.getRegister(), registry );
        
        return new CSVReaderMetadataFactory<M>( readerConf, configuration, registry );
                            
    }

    public static <M> CSVReaderMetadataFactory<M> getCSVToBeanReaderMetadataFactory( boolean acceptIncompleteRecords ) throws Exception
    {
    	
    	final CSVReaderConf readerConf = new CSVReaderConf();
    	readerConf.setAcceptIncompleteRecords( acceptIncompleteRecords );
    	readerConf.setName( "reader" );
    	
    	readerConf.getModelBinder().setType( "bean" );
   		readerConf.getModelBinder().getParams().put( "bean-class", Product.class.getName() );
    	
    	CSVColumnConf columnConf;        
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "NAME" );
    	columnConf.setMapping( "name" );
    	columnConf.setProcessor( null );
    	columnConf.setOptional( false );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "DESCRIPTION" );
    	columnConf.setMapping( "description" );
    	columnConf.setProcessor( null );
    	columnConf.setOptional( false );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "UPC" );
    	columnConf.setMapping( "upc" );
    	columnConf.setProcessorRef( "parseLong" );
    	columnConf.setOptional( false );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVFieldConverterConf enumConverterConf = new CSVFieldConverterConf();
    	enumConverterConf.setType( "parseEnum" );
    	enumConverterConf.getParams().put( "enum-type", "org.nerd4j.csv.model.Product$Currency" );
    	
    	final CSVFieldProcessorConf enumProcessorConf = new CSVFieldProcessorConf();
    	enumProcessorConf.setConverter( enumConverterConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "CURRENCY" );
    	columnConf.setMapping( "currency" );
    	columnConf.setProcessor( enumProcessorConf );
    	columnConf.setOptional( false );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "PRICE" );
    	columnConf.setMapping( "price" );
    	columnConf.setProcessorRef( "parseFloat" );
    	columnConf.setOptional( false );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "IN-STOCK" );
    	columnConf.setMapping( "inStock" );
    	columnConf.setProcessorRef( "parseBoolean" );
    	columnConf.setOptional( true );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVFieldConverterConf dateConverterConf = new CSVFieldConverterConf();
    	dateConverterConf.setType( "parseDate" );
    	dateConverterConf.getParams().put( "pattern", "dd-MM-yy" );
    	
    	final CSVFieldProcessorConf dateProcessorConf = new CSVFieldProcessorConf();
    	dateProcessorConf.setConverter( dateConverterConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "LAST-UPDATE" );
    	columnConf.setMapping( "lastUpdate" );
    	columnConf.setProcessor( dateProcessorConf );
    	columnConf.setOptional( true );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVConfiguration configuration = new CSVConfiguration();
    	configuration.getReaders().put( "reader", readerConf );
    	
    	final CSVRegistry registry = new CSVRegistry();
    	CSVMetadataRegister.register( configuration.getRegister(), registry );
    	
    	return new CSVReaderMetadataFactory<M>( readerConf, configuration, registry );
    	
    }
    
    public static <M> CSVReaderMetadataFactory<M> getCSVToMapReaderMetadataFactory( boolean acceptIncompleteRecords ) throws Exception
    {
    	
    	final CSVReaderConf readerConf = new CSVReaderConf();
    	readerConf.setAcceptIncompleteRecords( acceptIncompleteRecords );
    	readerConf.getModelBinder().setType( "map" );
    	readerConf.setName( "reader" );
    	
    	CSVColumnConf columnConf;        
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "NAME" );
    	columnConf.setMapping( "Name" );
    	columnConf.setProcessor( null );
    	columnConf.setOptional( false );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "DESCRIPTION" );
    	columnConf.setMapping( "Description" );
    	columnConf.setProcessor( null );
    	columnConf.setOptional( false );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "UPC" );
    	columnConf.setMapping( "Upc" );
    	columnConf.setProcessorRef( "parseLong" );
    	columnConf.setOptional( false );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVFieldConverterConf enumConverterConf = new CSVFieldConverterConf();
    	enumConverterConf.setType( "parseEnum" );
    	enumConverterConf.getParams().put( "enum-type", "org.nerd4j.csv.model.Product$Currency" );
    	
    	final CSVFieldProcessorConf enumProcessorConf = new CSVFieldProcessorConf();
    	enumProcessorConf.setConverter( enumConverterConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "CURRENCY" );
    	columnConf.setMapping( "Currency" );
    	columnConf.setProcessor( enumProcessorConf );
    	columnConf.setOptional( false );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "PRICE" );
    	columnConf.setMapping( "Price" );
    	columnConf.setProcessorRef( "parseFloat" );
    	columnConf.setOptional( false );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "IN-STOCK" );
    	columnConf.setMapping( "InStock" );
    	columnConf.setProcessorRef( "parseBoolean" );
    	columnConf.setOptional( true );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVFieldConverterConf dateConverterConf = new CSVFieldConverterConf();
    	dateConverterConf.setType( "parseDate" );
    	dateConverterConf.getParams().put( "pattern", "dd-MM-yy" );
    	
    	final CSVFieldProcessorConf dateProcessorConf = new CSVFieldProcessorConf();
    	dateProcessorConf.setConverter( dateConverterConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "LAST-UPDATE" );
    	columnConf.setMapping( "LastUpdate" );
    	columnConf.setProcessor( dateProcessorConf );
    	columnConf.setOptional( true );
    	readerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVConfiguration configuration = new CSVConfiguration();
    	configuration.getReaders().put( "reader", readerConf );
    	
    	final CSVRegistry registry = new CSVRegistry();
    	CSVMetadataRegister.register( configuration.getRegister(), registry );
    	
    	return new CSVReaderMetadataFactory<M>( readerConf, configuration, registry );
    	
    }
    
}