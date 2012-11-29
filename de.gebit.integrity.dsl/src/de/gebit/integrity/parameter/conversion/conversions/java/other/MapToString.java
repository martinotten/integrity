/**
 * A default Integrity conversion. 
 */
package de.gebit.integrity.parameter.conversion.conversions.java.other;

import java.util.Map;
import java.util.Map.Entry;

import de.gebit.integrity.parameter.conversion.Conversion;
import de.gebit.integrity.parameter.conversion.ConversionFailedException;
import de.gebit.integrity.parameter.conversion.UnresolvableVariableHandling;

/**
 * A default Integrity conversion.
 * 
 * @author Rene Schneider
 * 
 */
@SuppressWarnings("rawtypes")
@de.gebit.integrity.parameter.conversion.Conversion.Priority(0)
public class MapToString extends Conversion<Map, String> {

	@Override
	public String convert(Map aSource, Class<? extends String> aTargetType,
			UnresolvableVariableHandling anUnresolvableVariableHandlingPolicy) throws ConversionFailedException {
		StringBuilder tempBuilder = new StringBuilder();

		for (Entry<?, ?> tempEntry : ((Map<?, ?>) aSource).entrySet()) {
			String[] tempConvertedValues = convertValueToStringArrayRecursive(tempEntry.getValue(),
					anUnresolvableVariableHandlingPolicy);

			if (tempBuilder.length() > 0) {
				tempBuilder.append(", ");
			}

			StringBuilder tempInnerBuilder = new StringBuilder();
			if (tempConvertedValues.length == 1) {
				tempInnerBuilder.append(tempConvertedValues[0]);
			} else {
				tempInnerBuilder.append("[");
				for (int i = 0; i < tempConvertedValues.length; i++) {
					if (i > 0) {
						tempInnerBuilder.append(", ");
					}
					tempInnerBuilder.append(tempConvertedValues[i]);
				}
				tempInnerBuilder.append("]");
			}

			tempBuilder.append(tempEntry.getKey() + "=" + tempInnerBuilder.toString());
		}

		return "{" + tempBuilder.toString() + "}";
	}

}