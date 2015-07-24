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
package org.nerd4j.csv.writer;

import org.nerd4j.csv.conf.CSVMetadataRegister;
import org.nerd4j.csv.conf.mapping.CSVColumnConf;
import org.nerd4j.csv.conf.mapping.CSVConfiguration;
import org.nerd4j.csv.conf.mapping.CSVFieldConverterConf;
import org.nerd4j.csv.conf.mapping.CSVFieldProcessorConf;
import org.nerd4j.csv.conf.mapping.CSVWriterConf;
import org.nerd4j.csv.model.Product;
import org.nerd4j.csv.registry.CSVRegistry;


public class CSVWriterConfigurator
{
    
    public static <M> CSVWriterMetadataFactory<M> getArrayToCSVWriterMetadataFactory() throws Exception
    {
    	
    	final CSVWriterConf writerConf = new CSVWriterConf();
    	writerConf.getModelBinder().setType( "array" );
        writerConf.setName( "writer" );
    	
    	CSVColumnConf columnConf;        

        columnConf = new CSVColumnConf();
        columnConf.setName( "NAME" );
        columnConf.setMapping( "0" );
        columnConf.setProcessor( null );
        columnConf.setOptional( false );
        writerConf.getColumns().put( columnConf.getName(), columnConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "DESCRIPTION" );
        columnConf.setMapping( "1" );
        columnConf.setProcessor( null );
        columnConf.setOptional( false );
        writerConf.getColumns().put( columnConf.getName(), columnConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "UPC" );
        columnConf.setMapping( "2" );
        columnConf.setProcessorRef( "formatLong" );
        columnConf.setOptional( false );
        writerConf.getColumns().put( columnConf.getName(), columnConf );
        
        final CSVFieldConverterConf enumConverterConf = new CSVFieldConverterConf();
        enumConverterConf.setType( "formatEnum" );
        enumConverterConf.getParams().put( "enum-type", "org.nerd4j.csv.model.Product$Currency" );
        
        final CSVFieldProcessorConf enumProcessorConf = new CSVFieldProcessorConf();
        enumProcessorConf.setConverter( enumConverterConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "CURRENCY" );
        columnConf.setMapping( "3" );
        columnConf.setProcessor( enumProcessorConf );
        columnConf.setOptional( false );
        writerConf.getColumns().put( columnConf.getName(), columnConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "PRICE" );
        columnConf.setMapping( "4" );
        columnConf.setProcessorRef( "formatFloat" );
        columnConf.setOptional( false );
        writerConf.getColumns().put( columnConf.getName(), columnConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "IN-STOCK" );
        columnConf.setMapping( "5" );
        columnConf.setProcessorRef( "formatBoolean" );
        columnConf.setOptional( false );
        writerConf.getColumns().put( columnConf.getName(), columnConf );
        
        final CSVFieldConverterConf dateConverterConf = new CSVFieldConverterConf();
        dateConverterConf.setType( "formatDate" );
        dateConverterConf.getParams().put( "pattern", "dd-MM-yy" );
        
        final CSVFieldProcessorConf dateProcessorConf = new CSVFieldProcessorConf();
        dateProcessorConf.setConverter( dateConverterConf );
        
        columnConf = new CSVColumnConf();
        columnConf.setName( "LAST-UPDATE" );
        columnConf.setMapping( "6" );
        columnConf.setProcessor( dateProcessorConf );
        columnConf.setOptional( false );
        writerConf.getColumns().put( columnConf.getName(), columnConf );
        
        final CSVConfiguration configuration = new CSVConfiguration();
        configuration.getWriters().put( "writer", writerConf );
        
        final CSVRegistry registry = new CSVRegistry();
        CSVMetadataRegister.register( configuration.getRegister(), registry );
        
        return new CSVWriterMetadataFactory<M>( writerConf, configuration, registry );
                            
    }

    public static <M> CSVWriterMetadataFactory<M> getBeanToCSVWriterMetadataFactory() throws Exception
    {
    	
    	final CSVWriterConf writerConf = new CSVWriterConf();
    	writerConf.setName( "writer" );
    	
    	writerConf.getModelBinder().setType( "bean" );
   		writerConf.getModelBinder().getParams().put( "bean-class", Product.class.getName() );
    	
    	CSVColumnConf columnConf;        
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "NAME" );
    	columnConf.setMapping( "name" );
    	columnConf.setProcessor( null );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "DESCRIPTION" );
    	columnConf.setMapping( "description" );
    	columnConf.setProcessor( null );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "UPC" );
    	columnConf.setMapping( "upc" );
    	columnConf.setProcessorRef( "formatLong" );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVFieldConverterConf enumConverterConf = new CSVFieldConverterConf();
    	enumConverterConf.setType( "formatEnum" );
    	enumConverterConf.getParams().put( "enum-type", "org.nerd4j.csv.model.Product$Currency" );
    	
    	final CSVFieldProcessorConf enumProcessorConf = new CSVFieldProcessorConf();
    	enumProcessorConf.setConverter( enumConverterConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "CURRENCY" );
    	columnConf.setMapping( "currency" );
    	columnConf.setProcessor( enumProcessorConf );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "PRICE" );
    	columnConf.setMapping( "price" );
    	columnConf.setProcessorRef( "formatFloat" );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "IN-STOCK" );
    	columnConf.setMapping( "inStock" );
    	columnConf.setProcessorRef( "formatBoolean" );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVFieldConverterConf dateConverterConf = new CSVFieldConverterConf();
    	dateConverterConf.setType( "formatDate" );
    	dateConverterConf.getParams().put( "pattern", "dd-MM-yy" );
    	
    	final CSVFieldProcessorConf dateProcessorConf = new CSVFieldProcessorConf();
    	dateProcessorConf.setConverter( dateConverterConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "LAST-UPDATE" );
    	columnConf.setMapping( "lastUpdate" );
    	columnConf.setProcessor( dateProcessorConf );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVConfiguration configuration = new CSVConfiguration();
    	configuration.getWriters().put( "writer", writerConf );
    	
    	final CSVRegistry registry = new CSVRegistry();
    	CSVMetadataRegister.register( configuration.getRegister(), registry );
    	
    	return new CSVWriterMetadataFactory<M>( writerConf, configuration, registry );
    	
    }
    
    public static <M> CSVWriterMetadataFactory<M> getMapToCSVWriterMetadataFactory() throws Exception
    {
    	
    	final CSVWriterConf writerConf = new CSVWriterConf();
    	writerConf.setName( "writer" );
    	
    	writerConf.getModelBinder().setType( "map" );
    	
    	CSVColumnConf columnConf;        
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "NAME" );
    	columnConf.setMapping( "Name" );
    	columnConf.setProcessor( null );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "DESCRIPTION" );
    	columnConf.setMapping( "Description" );
    	columnConf.setProcessor( null );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "UPC" );
    	columnConf.setMapping( "Upc" );
    	columnConf.setProcessorRef( "formatLong" );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVFieldConverterConf enumConverterConf = new CSVFieldConverterConf();
    	enumConverterConf.setType( "formatEnum" );
    	enumConverterConf.getParams().put( "enum-type", "org.nerd4j.csv.model.Product$Currency" );
    	
    	final CSVFieldProcessorConf enumProcessorConf = new CSVFieldProcessorConf();
    	enumProcessorConf.setConverter( enumConverterConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "CURRENCY" );
    	columnConf.setMapping( "Currency" );
    	columnConf.setProcessor( enumProcessorConf );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "PRICE" );
    	columnConf.setMapping( "Price" );
    	columnConf.setProcessorRef( "formatFloat" );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "IN-STOCK" );
    	columnConf.setMapping( "InStock" );
    	columnConf.setProcessorRef( "formatBoolean" );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVFieldConverterConf dateConverterConf = new CSVFieldConverterConf();
    	dateConverterConf.setType( "formatDate" );
    	dateConverterConf.getParams().put( "pattern", "dd-MM-yy" );
    	
    	final CSVFieldProcessorConf dateProcessorConf = new CSVFieldProcessorConf();
    	dateProcessorConf.setConverter( dateConverterConf );
    	
    	columnConf = new CSVColumnConf();
    	columnConf.setName( "LAST-UPDATE" );
    	columnConf.setMapping( "LastUpdate" );
    	columnConf.setProcessor( dateProcessorConf );
    	columnConf.setOptional( false );
    	writerConf.getColumns().put( columnConf.getName(), columnConf );
    	
    	final CSVConfiguration configuration = new CSVConfiguration();
    	configuration.getWriters().put( "writer", writerConf );
    	
    	final CSVRegistry registry = new CSVRegistry();
    	CSVMetadataRegister.register( configuration.getRegister(), registry );
    	
    	return new CSVWriterMetadataFactory<M>( writerConf, configuration, registry );
    	
    }
    
}