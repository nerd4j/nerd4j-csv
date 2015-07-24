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
package org.nerd4j.csv.model;

import java.util.Date;

import org.junit.Ignore;
import org.nerd4j.csv.conf.mapping.ann.CSVColumn;
import org.nerd4j.csv.conf.mapping.ann.CSVFieldConverter;
import org.nerd4j.csv.conf.mapping.ann.CSVFieldProcessor;
import org.nerd4j.csv.conf.mapping.ann.CSVFieldValidator;
import org.nerd4j.csv.conf.mapping.ann.CSVParam;
import org.nerd4j.csv.conf.mapping.ann.CSVReader;
import org.nerd4j.csv.conf.mapping.ann.CSVWriter;
import org.nerd4j.format.AnnotatedFormattedBean;
import org.nerd4j.format.Formatted;


/**
 * Test object used to test operation on beans.
 * 
 * @author Nerd4j Team
 */
@Ignore

@CSVReader
@CSVWriter
public class Product extends AnnotatedFormattedBean
{

    private static final long serialVersionUID = 1L;

    @Formatted
    private Long upc;
    @Formatted
    private Float price;
    @Formatted
    private String name;
    @Formatted
    private String description;
    @Formatted
    private Currency currency;
    @Formatted
    private Boolean inStock;
 
    @Formatted
    @CSVColumn(name="LAST-UPDATE",
               readProcessor=@CSVFieldProcessor(
                       converter=@CSVFieldConverter(
                               params=@CSVParam(type="pattern",value="yy/MM/dd")
                       )
               )
    )
    private Date lastUpdate;

    
    public Product()
    {

        super();

        this.upc = null;
        this.name = null;
        this.price = null;
        this.inStock = null;
        this.currency = null;
        this.lastUpdate = null;
        this.description = null;

    }
    

    @CSVColumn(name="UPC")
    public Long getUpc()
    {
        return upc;
    }

    public void setUpc( Long upc )
    {
        this.upc = upc;
    }

    @CSVColumn(name="PRICE")
    public Float getPrice()
    {
        return price;
    }

    public void setPrice( Float price )
    {
        this.price = price;
    }

    @CSVColumn(name="NAME")
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
    @CSVColumn(name="DESCRIPTION",
               readProcessor=@CSVFieldProcessor(
                 precondition=@CSVFieldValidator(type="checkStringLength",
                   params={@CSVParam(type="min",value="2"),@CSVParam(type="max",value="30")}
                 )
               )
    )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @CSVColumn(name="CURRENCY")
    public Currency getCurrency()
    {
        return currency;
    }

    public void setCurrency( Currency currency )
    {
        this.currency = currency;
    }

    @CSVColumn(name="IN-STOCK")
    public Boolean getInStock()
    {
        return inStock;
    }

    public void setInStock( Boolean inStock )
    {
        this.inStock = inStock;
    }

    public Date getLastUpdate()
    {
        return lastUpdate;
    }

    public void setLastUpdate( Date lastUpdate )
    {
        this.lastUpdate = lastUpdate;
    }
    
    
    public static enum Currency
    {
        
        EUR,USD,GBP;
        
    }

}