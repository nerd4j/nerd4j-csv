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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.nerd4j.csv.exception.CSVToModelBindingException;


/**
 * Represents a <code>Factory</code> able to build and configure {@link CSVReader}s.
 * 
 * @param <Model> type of the data model returned by the reader.
 * 
 * @author Nerd4j Team
 */
public interface CSVReaderFactory<Model>
{

    /**
     * Creates the {@link CSVReader} able to read the given
     * CSV source file and produce a related data model.
     * 
     * @param file the CSV source file.
     * @return a related {@link CSVReader}.
     * @throws FileNotFoundException if fails to find the source.
     * @throws IOException if fails to read the source.
     * @throws CSVToModelBindingException if binding configuration is inconsistent.
     */
    public CSVReader<Model> getCSVReader( File file )
    throws FileNotFoundException, IOException, CSVToModelBindingException;
  
  /**
   * Creates the {@link CSVReader} able to read the given
   * CSV source input stream and produce a related data model.
   * 
   * @param is the CSV source stream.
   * @return a related {@link CSVReader}.
   * @throws IOException if fails to read the source.
   * @throws CSVToModelBindingException if binding configuration is inconsistent.
   */
    public CSVReader<Model> getCSVReader( InputStream is )
    throws IOException, CSVToModelBindingException;
    
    /**
     * Creates the {@link CSVReader} able to read the given
     * CSV source and produce a related data model.
     * 
     * @param reader the CSV source reader.
     * @return a related {@link CSVReader}.
     * @throws IOException if fails to read the source.
     * @throws CSVToModelBindingException if binding configuration is inconsistent.
     */
    public CSVReader<Model> getCSVReader( Reader reader )
    throws IOException, CSVToModelBindingException;
    
}
