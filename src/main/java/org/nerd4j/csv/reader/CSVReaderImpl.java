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

import java.io.IOException;

import org.nerd4j.csv.CSVProcessContext;
import org.nerd4j.csv.exception.CSVProcessException;
import org.nerd4j.csv.exception.CSVToModelBindingException;
import org.nerd4j.csv.exception.MalformedCSVException;
import org.nerd4j.csv.field.CSVField;
import org.nerd4j.csv.field.CSVFieldProcessContext;
import org.nerd4j.csv.parser.CSVParser;
import org.nerd4j.csv.parser.CSVToken;
import org.nerd4j.csv.reader.binding.CSVToModelBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Reference implementation of the {@link CSVReader} interface.
 * 
 * <p>
 *  Reads a record in the CSV source and creates an object of
 *  type {@code M} that represents such record. 
 * </p>
 * 
 * <h3>Synchronization</h3>
 *
 * <p>
 *  CSV readers are not synchronized.
 *  It is recommended to create separate CSV reader instances for each thread.
 *  If multiple threads access a CSV reader concurrently, it must be synchronized
 *  externally.
 * </p>
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @author Nerd4J Team
 */
final class CSVReaderImpl<M> implements CSVReader<M>
{
    
    /** SLF4J Logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVReaderImpl.class );

    /** Object able to parse a CSV source. */
    private final CSVParser parser;

    /** Object able to build and fill the data model related to the CSV record. */
    private final CSVToModelBinder<M> modelBinder;

    /** Represents the CSV header, can be <code>null</code> if the CSV has no header. */
    private final String[] header;
    
    /** Contains the processors used to manipulate data. */
    private final CSVField<String,?>[] fields;
    
    /** The process execution context. */ 
    private final CSVFieldProcessContext context;

    /** The reading process outcome. */ 
    private final CSVReadOutcomeImpl outcome;
    
    /**
     * In the CSV standard each record must have the same number of cells.
     * But it is possible to face non standard CSV files where records
     * have different lengths.In this case we need to know the index of
     * the last mandatory field to know if we can return the partially
     * populated model or we need to return an error.
     */
    private final int lastMandatoryField;
    
    /** Tells that the end of the CSV source has been reached. */
    private boolean endOfData;
    
    /**
     * Constructor with parameters.
     * 
     * @param parser the understanding CSV parser.
     * @param header the CSV header if exists.
     * @param processors the CSV field processors.
     * @param modelBinder the CSV model builder.
     * @param acceptIncompleteRecords tells if to accept non standard CSV with incomplete records.
     */
    public CSVReaderImpl( final CSVParser parser, final String[] header,
                          final CSVField<String,?>[] fields,
                          final CSVToModelBinder<M> modelBinder,
                          final boolean acceptIncompleteRecords )
    {
        
        super();
        
        if( parser == null )
            throw new NullPointerException( "The CSV parser is mandatory and can't be null." );

        if( modelBinder == null )
            throw new NullPointerException( "The CSV model binder is mandatory and can't be null." );

        if( fields == null )
            throw new NullPointerException( "The CSV fields are mandatory and can't be null." );

        /*
         * If no processors are configured the reader
         * doesn't know how to handle the CSV data.
         */
        if( fields.length <= 0 )
            throw new IllegalArgumentException( "Fields configuration cannot be empty, at least one column must be configured." );
        
        this.parser = parser;
        this.header = header;
        this.fields = fields;
        this.modelBinder = modelBinder;
        
        this.endOfData = false;
        this.outcome   = new CSVReadOutcomeImpl();
        this.context   = new CSVFieldProcessContext( header );
        
        /*
         * If we need to accept incomplete records than we have to know
         * the index of the last mandatory field, otherwise all fields
         * are mandatory so we use lastIndex + 1 as last mandatory field.
         */
        this.lastMandatoryField = acceptIncompleteRecords
        		                ? getLastMandatoryField( fields )
        		                : fields.length;
        
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getHeader()
    {
        
        return header;
        
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEndOfData()
    {
        
        return endOfData;
        
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public M readModel() throws IOException, CSVToModelBindingException, CSVProcessException
    {
        
        read();
        if( context.isError() )
            throw new CSVProcessException( context.getError() );
            
        return outcome.getModel();
        
    }
    
    
	/**
	 * {@inheritDoc}
	 */
    @Override
	public CSVReadOutcome<M> read() throws IOException, CSVToModelBindingException
	{
        
        /* First of all we clear the reading context and outcome. */
        context.clear();
        outcome.clear();
        
        /* If the end of the data has been reached we don't need to do anything. */
        if( endOfData ) return outcome;
        
        /* Otherwise we tell the context that we start processing a new row. */
        context.newRow();
        
        /* 
         * The size of the fields array is granted to be greater than 0
         * (see the constructor for more details).
         */
        CSVField<String,?> field = fields[0];
        
        /*
         * First of all we skip any empty row.
         * It shouldn't be possible to find empty
         * rows in a well formed CSV, but just in
         * case we perform the check.
         */
        CSVToken currentToken = null;
        do{
        
            /*
             * If we need the first field we read it
             * else we skip it.
             */
            currentToken = field != null
                         ? parser.read()
                         : parser.skip();
            
        }while( currentToken == CSVToken.END_OF_RECORD );
        
        
        /*
         * If the CSV source is empty we set the 'endOfData' flag
         * and return an empty model.
         */
        if( currentToken == CSVToken.END_OF_DATA )
        {
            logger.debug( "CSV source is empty, no more records to read." );
            this.endOfData = true;
            
            return outcome;
        }
        
        /*
         * If we reach this point it means that we read
         * a token of type FIELD, so we start to parse
         * the record.
         */
        logger.debug( "Proceeding to read a new CSV record" );
        
        /* We create a new empty model. */
        modelBinder.initModel();
        
        /* We handle the current column if needed. */
        if( ! processColumn(0, field) )
        {            
            /* 
             * If an error has occurred during the process
             * we return the outcome with the related error.
             */
            handleErrorInFieldProcess();
            return outcome;
        }
              
        /* Now we handle the rest of the columns. */
        for( int i = 1; i < fields.length; ++i )
        {
            
            field = fields[i];
            currentToken = field != null ? parser.read() : parser.skip();
            
            /*
             * If we reach the end of the record before to complete
             * all the mandatory fields we throw an exception.
             * Otherwise we return the partially populated outcome.
             */
            if( currentToken != CSVToken.FIELD )
            {
            	if( i > lastMandatoryField )
            	{
                    /*
                     * We reached the end of the record and all the mandatory
                     * fields have been processed so we are in a consistent
                     * state and we can return the related data model. 
                     */
                    outcome.model = modelBinder.getModel();
                    return outcome;
            	}
            	else
            		throw new MalformedCSVException( "The record ended before all the mandatory fields have been processed." );
            }
            
            /* We handle the current column if needed. */
            if( ! processColumn(i, field) )
            {
                /* 
                 * If an error has occurred during the process
                 * we return the outcome with the related error.
                 */
                handleErrorInFieldProcess();
                return outcome;
            }
            
        }
        
        /*
         * We can be not interested in processing all the fields if the CSV source.
         * In particular the size of the processor array can be smaller than the
         * number of fields in the CSV source record. In this case we skip all the
         * remaining fields. 
         */
        do{
            
            currentToken = parser.skip();
            
        }while( currentToken == CSVToken.FIELD );
        
        /*
         * Now we are in a consistent position (the end of the CSV source record)
         * and we can return the related data model. 
         */
        outcome.model = modelBinder.getModel();
        return outcome;
        
	}
    
    
    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this 
     * method has no effect. 
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException
    {
        
        this.parser.close();
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */

    
    /**
     * Returns the index of the last mandatory field.
     * This value is used in the case of incomplete records.
     * 
     * @param fields the fields to check.
     * @return the index of the last mandatory field if any, -1 otherwise.
     */
    private int getLastMandatoryField( CSVField<String,?>[] fields )
    {
    	
    	for( int i = fields.length-1; i >= 0; --i )
    		if( fields[i] != null && ! fields[i].isOptional() )
    			return i;
    	
    	return -1;
    }
    
    
    /**
     * Executes the work needed to process the field
     * related to the given column index and bind it 
     * to the data model.
     * <p>
     *  This method returns <code>true</code> if the
     *  field has been processed successfully and
     *  <code>false</code> if an error occurred during
     *  processing.
     * </p>
     * <p>
     *  In an error occurs the context is accordingly updated.
     * </p>
     * 
     * @param index index of the column to process.
     * @param field the field to process.
     * @return <code>true</code> if the fiend has been processed successfully.
     * @throws CSVModelBuilderException if an error occurs during model binding.
     */
    private boolean processColumn( final int index, final CSVField<String,?> field )
    throws CSVToModelBindingException
    {

        /* Before processing the new column we tell the context about. */
        context.newColumn();
        
        /* If the field is null we don't need to do any work. */
        if( field == null ) return true;
        
        /*
         * If the parser reads a field the value of such
         * field is returned by the getCurrentValue() method.
         */
        final String originalValue = parser.getCurrentValue();
        context.setOriginalValue( originalValue );
            
        /* We process such value to obtain the expected processed value. */            
        final Object processedValue = field.process( originalValue, context );

        /* If an error occurs during the processing of the field we fail the process. */
        if( context.isError() )return false;
        
        /*
         * Otherwise we bind the processed value
         * to the related position in the data model.
         */
        modelBinder.fill( index, processedValue );
        
        return true;
    
    }
    
    
    /**
     * This method handles the behavior to keep
     * in case an error occurs during the process
     * of a field.
     * <p>
     *  This method execution may depend on the
     *  strategy requested by the user.
     * </p>
     * <p>
     *  The default strategy defines the 
     * </p>
     * 
     * @throws IOException if an error occurs during the CSV source parsing.
     */
    private void handleErrorInFieldProcess() throws IOException
    {
        
        CSVToken currentToken;
        do{
            
            currentToken = parser.skip();
            
        }while( currentToken == CSVToken.FIELD );
        
    }
    
    
    /* *************** */
    /*  INNER CLASSES  */
    /* *************** */

    
    /**
     * Reference implementation of the {@link CSVReadOutcome} interface
     * for this CSV reader.
     * 
     * @author Nerd4J Team
     */
    private class CSVReadOutcomeImpl implements CSVReadOutcome<M>
    {
        
        /** The data model corresponding to the CSV record read. */
        private M model;
        
        
        /**
         * Default constructor.
         * 
         */
        public CSVReadOutcomeImpl()
        {
            
            super();
            
            this.model = null;
            
        }

        
        /* ******************* */
        /*  INTERFACE METHODS  */       
        /* ******************* */

        
        /**
         * {@inheritDoc}
         */
        @Override
        public M getModel()
        {
            return model;
        }


        /**
         * {@inheritDoc}
         */
        @Override
        public CSVProcessContext getCSVReadingContext()
        {
            return context;
        }
        
        
        /* ***************** */
        /*  UTILITY METHODS  */       
        /* ***************** */
        
        
        /**
         * Clears the internal values of the outcome.
         * 
         */
        private void clear()
        {
            this.model = null;
        }
        
    }
}