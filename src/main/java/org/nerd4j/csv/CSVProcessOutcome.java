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
package org.nerd4j.csv;

import java.util.function.Consumer;


/**
 * Represents the outcome of a CSV operation performed
 * by either {@link CSVReader} or {@link CSVWriter}.
 * 
 * <p>
 * This interface provides the following elements:
 * <ol>
 *  <li>The execution context of the operation.</li>
 *  <li>Some callkbacks called after the operation occurs.</li>  
 * </ol>
 * 
 * @since version {@code 1.2.0} support has been added for {@code Java 8} lambdas.
 * There are two new  methods called on operation success and operation error.
// * There are three new methods called on operation success, operation error and
// * after the operation regardless the result.
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @author Nerd4j Team
 */
public interface CSVProcessOutcome<M>
{
	
    /**
     * Returns the data model corresponding to the CSV record processed.
     * 
     * @return the data model corresponding to the CSV record processed.
     */
    public M getModel();
    
    /**
     * Returns the operation execution context.
     * <p>
     * This object contains some information about the progress
     * of the operation.
     * <p>
     * <b>IMPORTANT</b> for performance reasons there is only one
     *                  instance of the execution context. So the
     *                  internal values of this object may vary
     *                  during execution.
     *                  
     * @return the operation execution context.
     */
    public CSVProcessContext getCSVProcessContext();
   
    /**
     * This method calls the provided {@link FunctionalInterface}
     * if the operation has been successful and provides the
     * data model to be consumed.
     *    
     * @param consumer {@link FunctionalInterface} that consumes the data model.
     * @return this instance for concatenation. 
     * @since 1.2.0 
     */
    default CSVProcessOutcome<M> success( Consumer<M> consumer )
    {
    	
    	if( ! getCSVProcessContext().isError() )
    		consumer.accept( getModel() );
    	
    	return this;
    	
    }
    
    /**
     * This method calls the provided {@link FunctionalInterface}
     * if an error occurred during the operation and provides
     * the error to be consumed.
     *    
     * @param consumer {@link FunctionalInterface} that consumes the error.
     * @return this instance for concatenation. 
     * @since 1.2.0
     */
    default CSVProcessOutcome<M> error( Consumer<CSVProcessError> consumer )
    {
    	
    	final CSVProcessContext context = getCSVProcessContext();
    	if( context.isError() )
    		consumer.accept( context.getError() );
    	
    	return this;
    	
    }
    
    /**
     * This method calls the provided {@link FunctionalInterface}
     * after the operation has been executed regardless the result
     * and provides the related process context to be consumed.
     *    
     * @param consumer {@link FunctionalInterface} that consumes the process context.
     * @return this instance for concatenation. 
     * @since 1.2.0
     */
    default CSVProcessOutcome<M> then( Consumer<CSVProcessContext> consumer )
    {
    	
   		consumer.accept( getCSVProcessContext() );
    	return this;
    	
    }
        
}
