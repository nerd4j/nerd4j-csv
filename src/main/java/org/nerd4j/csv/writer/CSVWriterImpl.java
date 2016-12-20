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

import java.io.IOException;

import org.nerd4j.csv.CSVProcessContext;
import org.nerd4j.csv.exception.CSVProcessException;
import org.nerd4j.csv.exception.ModelToCSVBindingException;
import org.nerd4j.csv.field.CSVField;
import org.nerd4j.csv.field.CSVFieldProcessContext;
import org.nerd4j.csv.formatter.CSVFormatter;
import org.nerd4j.csv.writer.binding.ModelToCSVBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Reference implementation if the {@link CSVWriter} interface.
 * 
 * <p>
 *  Reads an object of type <e>M</e> that represents the
 *  data model and writes the corresponding CSV record
 *  to the given destination. 
 * </p>
 * 
 * <h3>Synchronization</h3>
 *
 * <p>
 *  CSV writers are not synchronized.
 *  It is recommended to create separate CSV writer instances for each thread.
 *  If multiple threads access a CSV writer concurrently, it must be synchronized
 *  externally.
 * </p>
 * 
 * @param <M> type of the data model representing the CSV record.
 * 
 * @author Nerd4J Team
 */
final class CSVWriterImpl<M> implements CSVWriter<M>
{
    
    /** SLF4J Logging system. */
    private static final Logger logger = LoggerFactory.getLogger( CSVWriterImpl.class );

    /** Object able to format a source data into a CSV field. */
    private final CSVFormatter formatter;

    /** Object able to read the data model related to the CSV record. */
    private final ModelToCSVBinder<M> modelBinder;

    /** Contains the fields used to manipulate data. */
    private final CSVField<Object,String>[] fields;

    /** Contains the values do be written as output in the CSV destination. */
    private final String[] outputRecord;
    
    /** The process execution context. */ 
    private final CSVFieldProcessContext context;

    /** The writing process outcome. */ 
    private final CSVWriteOutcomeImpl outcome;
    
    
    /**
     * Constructor with parameters.
     * 
     * @param formatter  the understanding CSV formatter.
     * @param header     the CSV header if exists.
     * @param fields     the CSV fields.
     * @param modelBinder the CSV model builder.
     */
    @SuppressWarnings("unchecked")
    public CSVWriterImpl( final CSVFormatter formatter, final String[] header,
                          final CSVField<?,String>[] fields,
                          final ModelToCSVBinder<M> modelBinder )
    {
        
        super();
        
        if( formatter == null )
            throw new NullPointerException( "The CSV formatter is mandatory and can't be null." );

        if( modelBinder == null )
            throw new NullPointerException( "The CSV model binder is mandatory and can't be null." );

        if( fields == null )
            throw new NullPointerException( "The CSV fields are mandatory and can't be null." );

        /*
         * If no fields are configured the reader
         * doesn't know how to handle the CSV data.
         */
        if( fields.length <= 0 )
            throw new IllegalArgumentException( "Fields configuration cannot be empty, at least one column must be configured." );
        
        this.formatter = formatter;
        this.modelBinder = modelBinder;
        this.outputRecord = new String[modelBinder.getRecordSize()];
        this.fields = (CSVField<Object,String>[]) fields;
        
        this.outcome   = new CSVWriteOutcomeImpl();
        this.context   = new CSVFieldProcessContext( header );
        
    }

    
    /* ******************* */
    /*  INTERFACE METHODS  */
    /* ******************* */
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void writeModel( M model ) throws IOException, ModelToCSVBindingException, CSVProcessException
    {
        
        write( model );
        if( context.isError() )
            throw new CSVProcessException( context.getError() );
        
    }
    
    
	/**
	 * {@inheritDoc}
	 */
    @Override
	public CSVWriteOutcome write( M model ) throws IOException, ModelToCSVBindingException
	{
        
        /* First of all we clear the reading context and outcome. */
        context.clear();
        
        /* Otherwise we tell the context that we start processing a new row. */
        context.newRow();
        
        /* If the model is null there is nothing to do. */
        if( model == null ) return outcome;
        
        /*
         * If the data model is not null we proceed to write the
         * new model into the CSV destination.
         */
        logger.debug( "Proceeding to write a new CSV record" );
        
        /* We create a new empty model. */
        modelBinder.setModel( model );
        
        
        /* Now we handle the CSV target columns. */
        for( int i = 0; i < modelBinder.getRecordSize(); ++i )
        {
            
            /* Before processing the new column we tell the context about. */
            context.newColumn();
            
            /* We retrieve the next value to write form the model binder. */
            final Object originalValue = modelBinder.getValue( i );
            context.setOriginalValue( originalValue );
                
            /* We process such value to obtain the expected processed value. */            
            final String processedValue = fields[i].process( originalValue, context );

            /* If an error occurs during the processing of the field we fail the process. */
            if( context.isError() ) return outcome;
            
            /*
             * Otherwise we write the processed value
             * to the CSV record.
             */
            outputRecord[i] = processedValue;
            
        }
        
        /*
         * If the whole record has been processed successfully
         * we write it to the CSV destination.
         */
        for( int i = 0; i < outputRecord.length; ++i )
        {
            /*
             * Otherwise we write the processed value
             * to the CSV destination.
             */
            formatter.writeField( outputRecord[i], false );
        }
        
        /*
         * If the record has been written successfully we write
         * a record separator.
         */
        formatter.writeEOR();
        
        /*
         * Now we are in a consistent position (the end of the CSV source record)
         * and we can return the related data model. 
         */
        return outcome;
        
	}
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException
    {
        
        this.formatter.writeEOD();
        this.formatter.close();
        
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException
    {
        
        this.formatter.flush();
        
    }
    
    
    /* ***************** */
    /*  PRIVATE METHODS  */
    /* ***************** */
	
    
    
    /* *************** */
    /*  INNER CLASSES  */
    /* *************** */

    
    /**
     * Reference implementation of the {@link CSVWriteOutcome} interface
     * for this CSV writer.
     * 
     * @author Nerd4J Team
     */
    private class CSVWriteOutcomeImpl implements CSVWriteOutcome
    {

        
        /**
         * Default constructor.
         * 
         */
        public CSVWriteOutcomeImpl()
        {
            
            super();
                        
        }

        
        /* ******************* */
        /*  INTERFACE METHODS  */       
        /* ******************* */

        
        /**
         * {@inheritDoc}
         */
        @Override
        public CSVProcessContext getCSVWritingContext()
        {
            return context;
        }
        
    }
}