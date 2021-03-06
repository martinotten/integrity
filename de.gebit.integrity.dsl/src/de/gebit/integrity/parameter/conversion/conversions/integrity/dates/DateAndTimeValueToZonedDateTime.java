/*******************************************************************************
 * Copyright (c) 2013 Rene Schneider, GEBIT Solutions GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.gebit.integrity.parameter.conversion.conversions.integrity.dates;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

import de.gebit.integrity.dsl.DateAndTimeValue;
import de.gebit.integrity.parameter.conversion.Conversion;
import de.gebit.integrity.parameter.conversion.ConversionContext;
import de.gebit.integrity.parameter.conversion.ConversionFailedException;
import de.gebit.integrity.utils.DateUtil;

/**
 * A default Integrity conversion.
 * 
 * @author Rene Schneider - initial API and implementation
 * 
 */
public class DateAndTimeValueToZonedDateTime extends Conversion<DateAndTimeValue, ZonedDateTime> {

	@Override
	public ZonedDateTime convert(DateAndTimeValue aSource, Class<? extends ZonedDateTime> aTargetType,
			ConversionContext aConversionContext) throws ConversionFailedException {
		try {
			return ((GregorianCalendar) DateUtil.convertDateAndTimeValue(aSource)).toZonedDateTime();
		} catch (ParseException exc) {
			throw new ConversionFailedException(DateAndTimeValue.class, Date.class, null, exc);
		}
	}

}
