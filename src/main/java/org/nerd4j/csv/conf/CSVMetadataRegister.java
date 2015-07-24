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
package org.nerd4j.csv.conf;

import java.util.Collection;

import org.nerd4j.csv.conf.mapping.CSVRegisterConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterConverterConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterProcessorConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterProviderConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterTypesConf;
import org.nerd4j.csv.conf.mapping.CSVRegisterValidatorConf;
import org.nerd4j.csv.exception.CSVConfigurationException;
import org.nerd4j.csv.field.CSVFieldConverter;
import org.nerd4j.csv.field.CSVFieldProcessor;
import org.nerd4j.csv.field.CSVFieldValidator;
import org.nerd4j.csv.registry.CSVAbstractRegistry;
import org.nerd4j.csv.registry.CSVRegistry;
import org.nerd4j.csv.registry.CSVRegistryEntryProvider;


/**
 * Utility class to register configurations
 * in order to use them during meta-data build.
 * 
 * @author Nerd4j Team
 */
public final class CSVMetadataRegister
{
    
    /**
     * Registers into the given registry the object obtained from the given configuration.
     * 
     * @param configuration configuration to parse.
     * @param registry      registry to compile.
     */
    public static void register( final CSVRegisterConf configuration, final CSVRegistry registry )
    {
        
        if( configuration == null ) return;
        
        CSVConfChecker.check( configuration );
        register( configuration.getTypes(), registry );
        
        final Collection<CSVRegisterValidatorConf> validators = configuration.getValidators().values();
        if( validators != null && ! validators.isEmpty() )
            for( CSVRegisterValidatorConf validator : validators )
                register( validator, registry );
        
        final Collection<CSVRegisterConverterConf> converters = configuration.getConverters().values();
        if( converters != null && ! converters.isEmpty() )
            for( CSVRegisterConverterConf converter : converters )
                register( converter, registry );
                                
        final Collection<CSVRegisterProcessorConf> processors = configuration.getProcessors().values();
        if( processors != null && ! processors.isEmpty() )
            for( CSVRegisterProcessorConf processor : processors )
                register( processor, registry );
        
    }
    
    
    /**
     * Registers into the given registry the object obtained from the given configuration.
     * 
     * @param configuration configuration to parse.
     * @param registry      registry to compile.
     */
    public static void register( final CSVRegisterTypesConf configuration, final CSVRegistry registry )
    {
        
        if( configuration == null ) return;
        
        final Collection<CSVRegisterProviderConf> validatorProviders = configuration.getValidatorProviders().values();
        if( validatorProviders != null && ! validatorProviders.isEmpty() )
            for( CSVRegisterProviderConf validatorProvider : validatorProviders )
                register( validatorProvider, registry.getValidatorRegistry() );
                    
        final Collection<CSVRegisterProviderConf> converterProviders = configuration.getConverterProviders().values();
        if( converterProviders != null && ! converterProviders.isEmpty() )
            for( CSVRegisterProviderConf converterProvider : converterProviders )
                register( converterProvider, registry.getConverterRegistry() );
                                
        final Collection<CSVRegisterProviderConf> csvToModelProviders = configuration.getCsvToModelProviders().values();
        if( csvToModelProviders != null && ! csvToModelProviders.isEmpty() )
            for( CSVRegisterProviderConf csvToModelProvider : csvToModelProviders )
                register( csvToModelProvider, registry.getCsvToModelBinderFactoryRegistry() );
                                
        final Collection<CSVRegisterProviderConf> modelToCSVProviders = configuration.getModelToCSVProviders().values();
        if( modelToCSVProviders != null && ! modelToCSVProviders.isEmpty() )
            for( CSVRegisterProviderConf modelToCSVProvider : modelToCSVProviders )
                register( modelToCSVProvider, registry.getModelToCSVBinderFactoryRegistry() );
                                            
    }
    
    
    /**
     * Registers into the given registry the object obtained from the given configuration.
     * 
     * @param configuration configuration to parse.
     * @param registry      registry to compile.
     */
    public static void register( final CSVRegisterValidatorConf configuration, final CSVRegistry registry )
    {
        
        if( configuration == null ) return;
        
        final String name = configuration.getName();
        if( name == null || name.isEmpty() ) return;
        
        final CSVFieldValidator<?> vadilator = CSVMetadataBuilder.build( configuration, registry );
        
        if( vadilator != null )
            registry.getValidatorRegistry().setEntry( name, vadilator );
        
    }
    
    /**
     * Registers into the given registry the object obtained from the given configuration.
     * 
     * @param configuration configuration to parse.
     * @param registry      registry to compile.
     */
    public static void register( final CSVRegisterConverterConf configuration, final CSVRegistry registry )
    {
        
        if( configuration == null ) return;
        
        final String name = configuration.getName();
        if( name == null || name.isEmpty() ) return;
        
        final CSVFieldConverter<?,?> converter = CSVMetadataBuilder.build( configuration, registry );
        
        if( converter != null )
            registry.getConverterRegistry().setEntry( name, converter );
        
    }
    
    /**
     * Registers into the given registry the object obtained from the given configuration.
     * 
     * @param configuration configuration to parse.
     * @param registry      registry to compile.
     */
    public static void register( final CSVRegisterProcessorConf configuration, final CSVRegistry registry )
    {
        
        if( configuration == null ) return;
        
        final String name = configuration.getName();
        if( name == null || name.isEmpty() ) return;
        
        final CSVFieldProcessor<?,?> processor = CSVMetadataBuilder.build( configuration, registry );
        
        if( processor != null )
            registry.getProcessorRegistry().setEntry( name, processor );
        
    }
    
    
    /**
     * Registers into the given registry the object obtained from the given configuration.
     * 
     * @param configuration configuration to parse.
     * @param registry      registry to compile.
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void register( final CSVRegisterProviderConf configuration, final CSVAbstractRegistry<?> registry )
    {
        
        if( configuration == null ) return;
        
        final String typeName = configuration.getTypeName();
        final String className = configuration.getProviderClass();
        
        try{

            final Class<?> providerClass = Class.forName( className );
            if( ! CSVRegistryEntryProvider.class.isAssignableFrom(providerClass) )
                throw new CSVConfigurationException( "The 'provider-class' must be an instance of " + CSVRegistryEntryProvider.class );
            
            final CSVRegistryEntryProvider provider = (CSVRegistryEntryProvider) providerClass.newInstance();
            registry.setProvider( typeName, provider );
            
        }catch( ClassNotFoundException ex )
        {
            
            throw new CSVConfigurationException( "The value of 'provider-class' do not represent a canonical class name", ex );
            
        }catch( InstantiationException ex )
        {
            
            throw new CSVConfigurationException( "Unable to instantiate the provider", ex );
            
        }catch( IllegalAccessException ex )
        {
            
            throw new CSVConfigurationException( "Unable to instantiate the provider", ex );
            
        }
        
    }
    
}