/*
 * #%L
 * Nerd4j CSV
 * %%
 * Copyright (C) 2013 - 2016 Nerd4j
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
package org.nerd4j.csv.reader.binding;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.nerd4j.util.DataConsistency;


/**
 * Abstract class representing an object able to write
 * the field of a {@code JavaBean} either invoking the
 * related {@code setter} or writing directly into the
 * field using reflection.
 * 
 * @author Nerd4j Team
 */
public abstract class CSVToBeanFieldWriter
{
	
	
	/**
	 * Default constructor.
	 */
	public CSVToBeanFieldWriter()
	{
		
		super();
		
	}
	
	
	/* **************** */
	/*  PUBLIC METHODS  */
	/* **************** */

	
	/**
	 * Returns the name of the field writer.
	 * 
	 * @return name of the field writer.
	 */
	public abstract String getName();
	
	
	/**
	 * Writes the given value into the field,
	 * of the given object, handled by this writer.
	 * 
	 * @param value the value to write.
	 * @param bean  the bean to alter.
	 * @throws IllegalAccessException if the setter is not accessible.
	 * @throws IllegalArgumentException if the bean has not the expected class.
	 * @throws InvocationTargetException if the setter does not exist. 
	 */
	public abstract void write( Object value, Object bean )
	throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	
	/* **************** */
	/*  STATIC METHODS  */
	/* **************** */

	
	/**
	 * Returns a {@link CSVToBeanFieldWriter} that uses the given
	 * {@link java.lang.reflect.Field} object to set the field value.
	 * 
	 * @param field the {@link java.lang.reflect.Field} to use.
	 * @return a new {@link CSVToBeanFieldWriter}.
	 */
	public static CSVToBeanFieldWriter getWriter( Field field )
	{
		
		return new WriteByField( field );
		
	}
	
	/**
	 * Returns a {@link CSVToBeanFieldWriter} that uses the given
	 * {@link java.lang.reflect.Method} object to set the field value.
	 * 
	 * @param setter the {@link java.lang.reflect.Method} to invoke.
	 * @return a new {@link CSVToBeanFieldWriter}.
	 */
	public static CSVToBeanFieldWriter getWriter( Method setter )
	{
		
		return new WriteBySetter( setter );
		
	}
	
	
	/* *************** */
	/*  INNER CLASSES  */
	/* *************** */

	
	/**
	 * Implementation of {@link CSVToBeanFieldWriter} that
	 * use the {@link java.lang.reflect.Field} class to
	 * update the field.
	 * 
	 * @author Nerd4j Team
	 */
	private static class WriteByField extends CSVToBeanFieldWriter
	{

		/** The field to be written. */
		private Field field;
		
		
		/**
		 * Constructor with parameters.
		 * 
		 * @param field the field to write.
		 */
		public WriteByField( Field field )
		{
			
			super();
			
			DataConsistency.checkIfNotNull( "field", field );
			this.field = field;
			
			if( ! field.isAccessible() )
				field.setAccessible( true );
			
		}
		
		
		/* ***************** */
		/*  EXTENSION HOOKS  */
		/* ***************** */
		
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName()
		{
			
			return field.getName();
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write( Object value, Object bean )
		throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
		{
			
			field.set( bean, value );
			
		}
		
	}
	
	
	/**
	 * Implementation of {@link CSVToBeanFieldWriter} that
	 * use the {@link java.lang.reflect.Method} class to
	 * update the field.
	 * 
	 * @author Nerd4j Team
	 */
	private static class WriteBySetter extends CSVToBeanFieldWriter
	{
		
		/** The field to be written. */
		private Method setter;
		
		
		/**
		 * Constructor with parameters.
		 * 
		 * @param setter the setter method to use.
		 */
		public WriteBySetter( Method setter )
		{
			
			super();
			
			DataConsistency.checkIfNotNull( "setter", setter );
			DataConsistency.checkIfTrue( "setter.getParameterTypes().length == 1", setter.getParameterTypes().length == 1 );
			DataConsistency.checkIfTrue( "setter.getName().startsWith( \"set\" )", setter.getName().startsWith( "set" ) );
			
			this.setter = setter;
			
		}
		
		
		/* ***************** */
		/*  EXTENSION HOOKS  */
		/* ***************** */
		
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getName()
		{
			
			return setter.getName();
			
		}
		
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write( Object value, Object bean )
		throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
		{
			
			setter.invoke( bean, value );
			
		}
		
	}
	
}
