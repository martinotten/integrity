/**
 * A default Integrity conversion. 
 */
package de.gebit.integrity.parameter.conversion.conversions.java.numbers;

import java.math.BigDecimal;

import de.gebit.integrity.parameter.conversion.ConversionFailedException;
import de.gebit.integrity.parameter.conversion.Conversion;
import de.gebit.integrity.parameter.conversion.UnresolvableVariableHandling;

/**
 * A default Integrity conversion.
 * 
 * @author Rene Schneider
 * 
 */
public class ByteToBigDecimal extends Conversion<Byte, BigDecimal> {

	@Override
	public BigDecimal convert(Byte aSource, Class<? extends BigDecimal> aTargetType,
			UnresolvableVariableHandling anUnresolvableVariableHandlingPolicy) throws ConversionFailedException {
		return new BigDecimal(aSource);
	}

}