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
package org.nerd4j.csv.field.processor;

import org.nerd4j.csv.field.CSVFieldConverter;
import org.nerd4j.csv.field.CSVFieldProcessor;
import org.nerd4j.csv.field.CSVFieldValidator;
import org.nerd4j.csv.registry.CSVRegistryEntryFactory;

/**
 * Factory able to build instances of {@link CSVFieldProcessor}.
 * 
 * <p>
 *  This class keeps an internal reference to the related
 *  instances of {@link CSVFieldValidatorFactory} and
 *  {@link CSVFieldConverterFactory}.
 * </p>
 * 
 * @param <S> type of the source field format.
 * @param <T> type of the target field format.
 * 
 * @author Nerd4j Team
 */
public class CSVFieldProcessorFactory<S,T> implements CSVRegistryEntryFactory<CSVFieldProcessor<?,?>>
{
    
    /** Factory for the condition that the field must satisfy before to be converted. */
    private final CSVRegistryEntryFactory<CSVFieldValidator<S>> preconditionFactory;

    /**  Factory for the conversion of the field. */
    private final CSVRegistryEntryFactory<CSVFieldConverter<S,T>> converterFactory;
    
    /**  Factory for the condition that the field must satisfy after the conversion. */
    private final CSVRegistryEntryFactory<CSVFieldValidator<T>> postconditionFactory;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param preconditionFactory  factory of the condition to satisfy before conversion.
     * @param converterFactory     factory of the converter that changes the field type. 
     * @param postconditionFactory factory of the condition to satisfy after conversion.
     */
    public CSVFieldProcessorFactory( final CSVRegistryEntryFactory<CSVFieldValidator<S>>   preconditionFactory,
                              		 final CSVRegistryEntryFactory<CSVFieldConverter<S,T>> converterFactory,
                              		 final CSVRegistryEntryFactory<CSVFieldValidator<T>>   postconditionFactory )
    {
        
        super();
        
        if( converterFactory == null )
            throw new NullPointerException( "The converter is mandatory and can't be null" );
        
        this.converterFactory = converterFactory;
        this.preconditionFactory = preconditionFactory;
        this.postconditionFactory = postconditionFactory;
        
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
	public CSVFieldProcessor<S, T> create()
    {
		
    	final CSVFieldConverter<S,T> converter = converterFactory.create();
    	final CSVFieldValidator<S> precondition = preconditionFactory != null ? preconditionFactory.create() : null;
    	final CSVFieldValidator<T> postcondition = postconditionFactory != null ? postconditionFactory.create() : null;
    	
    	return new CSVFieldProcessor<>( precondition, converter, postcondition );
    			
	}    
    
}