/*******************************************************************************
 * Copyright (c) 2013 Rene Schneider, GEBIT Solutions GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.gebit.integrity.parameter.conversion.conversions.integrity.dates;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

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
public class DateAndTimeValueToLocalTime extends Conversion<DateAndTimeValue, LocalTime> {

	@Override
	public LocalTime convert(DateAndTimeValue aSource, Class<? extends LocalTime> aTargetType,
			ConversionContext aConversionContext) throws ConversionFailedException {
		try {
			return LocalDateTime
					.from(DateUtil.convertDateAndTimeValue(aSource).toInstant().atZone(ZoneId.systemDefault()))
					.toLocalTime();
		} catch (ParseException exc) {
			throw new ConversionFailedException(DateAndTimeValue.class, Date.class, null, exc);
		}
	}

}
