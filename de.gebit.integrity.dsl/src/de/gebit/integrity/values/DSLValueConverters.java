/*******************************************************************************
 * Copyright (c) 2013 Rene Schneider, GEBIT Solutions GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.gebit.integrity.values;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

/**
 * This class is autogenerated by XText and hasn't been extended yet.
 * 
 * @author Rene Schneider - initial API and implementation
 * 
 */
public class DSLValueConverters extends DefaultTerminalConverters {

	// SUPPRESS CHECKSTYLE Javadoc
	@ValueConverter(rule = "INTEGER")
	// SUPPRESS CHECKSTYLE MethodName
	public IValueConverter<BigInteger> INTEGER() {
		return new IValueConverter<BigInteger>() {

			@Override
			public BigInteger toValue(String aString, INode aNode) throws ValueConverterException {
				return new BigInteger(aString);
			}

			@Override
			public String toString(BigInteger aValue) throws ValueConverterException {
				return aValue.toString();
			}

		};
	}

	// SUPPRESS CHECKSTYLE Javadoc
	@ValueConverter(rule = "DECIMAL")
	// SUPPRESS CHECKSTYLE MethodName
	public IValueConverter<BigDecimal> DECIMAL() {
		return new IValueConverter<BigDecimal>() {

			@Override
			public BigDecimal toValue(String aString, INode aNode) throws ValueConverterException {
				return new BigDecimal(aString);
			}

			@Override
			public String toString(BigDecimal aValue) throws ValueConverterException {
				return aValue.toString();
			}

		};
	}

}
