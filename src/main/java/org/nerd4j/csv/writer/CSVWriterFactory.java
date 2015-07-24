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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.nerd4j.csv.exception.ModelToCSVBindingException;


/**
 * Represents a <code>Factory</code> able to build and configure {@link CSVWriter}s.
 * 
 * @param <Model> type of the data model accepted by the writer.
 * 
 * @author Nerd4j Team
 */
public interface CSVWriterFactory<Model>
{
    
    /**
     * Creates the {@link CSVWriter} able to write the given
     * data model into the provided CSV destination file.
     * 
     * @param file the CSV destination file.
     * @return a related {@link CSVWriter}.
     * @throws FileNotFoundException if fails to find the destination file.
     * @throws IOException if fails to write the destination file.
     * @throws ModelToCSVBindingException if binding configuration is inconsistent.
     */
    public CSVWriter<Model> getCSVWriter( File file )
    throws FileNotFoundException, IOException, ModelToCSVBindingException;
    
    /**
     * Creates the {@link CSVWriter} able to write the given
     * data model into the provided CSV destination output stream.
     * 
     * @param os the CSV destination output stream.
     * @return a related {@link CSVWriter}.
     * @throws IOException if fails to write the destination.
     * @throws ModelToCSVBindingException if binding configuration is inconsistent.
     */
    public CSVWriter<Model> getCSVWriter( OutputStream os )
    throws IOException, ModelToCSVBindingException;
    
    /**
     * Creates the {@link CSVWriter} able to write the given
     * data model of type <code>M</code> into the provided
     * CSV destination.
     * 
     * @param writer the CSV destination writer.
     * @return a related {@link CSVWriter}.
     * @throws IOException if fails to write the destination.
     * @throws ModelToCSVBindingException if binding configuration is inconsistent.
     */
    public CSVWriter<Model> getCSVWriter( Writer writer )
    throws IOException, ModelToCSVBindingException;

}
