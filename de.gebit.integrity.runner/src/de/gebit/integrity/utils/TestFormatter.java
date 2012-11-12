package de.gebit.integrity.utils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.gebit.integrity.dsl.Call;
import de.gebit.integrity.dsl.MethodReference;
import de.gebit.integrity.dsl.TableTest;
import de.gebit.integrity.dsl.TableTestRow;
import de.gebit.integrity.dsl.Test;
import de.gebit.integrity.dsl.VariableEntity;
import de.gebit.integrity.fixtures.FixtureMethod;
import de.gebit.integrity.fixtures.FixtureWrapper;
import de.gebit.integrity.operations.OperationWrapper.UnexecutableException;
import de.gebit.integrity.parameter.conversion.ValueConverter;

/**
 * The {@link TestFormatter} is responsible for creating human-readable strings out of various test-related entities.
 * This is usually done by using the descriptions attached to fixtures.
 * 
 * 
 * @author Rene Schneider
 * 
 */
public class TestFormatter {

	/**
	 * Escape pattern for parameters in descriptions.
	 */
	private static final Pattern PARAMETER_PATTERN = Pattern.compile("^(.*)\\$(.*)\\$(.*)$");

	/**
	 * The classloader to use.
	 */
	private ClassLoader classloader = getClass().getClassLoader();

	/**
	 * The value converter to use.
	 */
	private ValueConverter valueConverter;

	/**
	 * Creates a new instance.
	 * 
	 * @param aClassloader
	 *            the classloader to use
	 */
	public TestFormatter(ClassLoader aClassloader, ValueConverter aValueConverter) {
		super();
		classloader = aClassloader;
		valueConverter = aValueConverter;
	}

	public void setClassloader(ClassLoader aClassloader) {
		classloader = aClassloader;
	}

	/**
	 * Creates a human-readable string for a test.
	 * 
	 * @param aTest
	 *            the test
	 * @param aVariableMap
	 *            the variables
	 * @return the human-readable test description
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws UnexecutableException
	 */
	public String testToHumanReadableString(Test aTest, Map<VariableEntity, Object> aVariableMap)
			throws ClassNotFoundException, UnexecutableException, InstantiationException {
		return fixtureMethodToHumanReadableString(aTest.getDefinition().getFixtureMethod(),
				IntegrityDSLUtil.createParameterMap(aTest, aVariableMap, classloader, valueConverter, true, false),
				false);
	}

	/**
	 * Creates a human-readable string for a tabletest.
	 * 
	 * @param aTest
	 *            the test
	 * @param aRow
	 *            the row (may be null if the string shall be for the whole test)
	 * @param aVariableMap
	 *            the variable map
	 * @return the human-readable description
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws UnexecutableException
	 */
	public String tableTestRowToHumanReadableString(TableTest aTest, TableTestRow aRow,
			Map<VariableEntity, Object> aVariableMap) throws ClassNotFoundException, UnexecutableException,
			InstantiationException {
		return fixtureMethodToHumanReadableString(aTest.getDefinition().getFixtureMethod(),
				IntegrityDSLUtil
						.createParameterMap(aTest, aRow, aVariableMap, classloader, valueConverter, true, false), false);
	}

	/**
	 * Creates a human-readable string for a tabletest.
	 * 
	 * @param aTest
	 *            the test
	 * @param aVariableMap
	 *            the variable map
	 * @return the human-readable string
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws UnexecutableException
	 */
	public String tableTestToHumanReadableString(TableTest aTest, Map<VariableEntity, Object> aVariableMap)
			throws ClassNotFoundException, UnexecutableException, InstantiationException {
		return fixtureMethodToHumanReadableString(aTest.getDefinition().getFixtureMethod(),
				IntegrityDSLUtil.createParameterMap(aTest.getParameters(), aVariableMap, classloader, valueConverter,
						true, false), true);
	}

	/**
	 * Creates a human-readable string for a call.
	 * 
	 * @param aCall
	 *            the call
	 * @param aVariableMap
	 *            the variable map
	 * @return the human-readable string
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws UnexecutableException
	 */
	public String callToHumanReadableString(Call aCall, Map<VariableEntity, Object> aVariableMap)
			throws ClassNotFoundException, UnexecutableException, InstantiationException {
		return fixtureMethodToHumanReadableString(aCall.getDefinition().getFixtureMethod(),
				IntegrityDSLUtil.createParameterMap(aCall, aVariableMap, classloader, valueConverter, true, false),
				false);
	}

	/**
	 * Creates a human-readable string for a fixture method.
	 * 
	 * @param aFixtureMethod
	 *            the fixture method
	 * @param someParameters
	 *            a map of parameters used for the test
	 * @param anExpectUnspecifiedParametersFlag
	 *            whether unspecified parameters shall result in "???" replacements
	 * @return the human-readable string
	 * @throws ClassNotFoundException
	 */
	public String fixtureMethodToHumanReadableString(MethodReference aFixtureMethod,
			Map<String, Object> someParameters, boolean anExpectUnspecifiedParametersFlag)
			throws ClassNotFoundException {
		String tempFixtureMethodName = aFixtureMethod.getMethod().getSimpleName();
		String tempFixtureClassName = aFixtureMethod.getType().getQualifiedName();
		Class<?> tempFixtureClass = classloader.loadClass(tempFixtureClassName);
		Method tempMethod = FixtureWrapper.findFixtureMethodByName(tempFixtureClass, tempFixtureMethodName);
		if (tempMethod == null) {
			return null;
		}

		FixtureMethod tempAnnotation = tempMethod.getAnnotation(FixtureMethod.class);
		if (tempAnnotation == null) {
			return null;
		}

		String tempText = null;
		if (tempAnnotation.description() != null && tempAnnotation.description().length() > 0) {
			tempText = tempAnnotation.description();
		} else {
			tempText = tempFixtureMethodName;
		}

		Matcher tempMatcher = PARAMETER_PATTERN.matcher(tempText);
		while (tempMatcher.matches()) {
			// classloader and variable maps are not supplied here because the parameters are already expected to be
			// resolved
			String tempValue = valueConverter.convertValueToString(someParameters.get(tempMatcher.group(2)), null,
					null, anExpectUnspecifiedParametersFlag);
			if (tempValue == null) {
				tempValue = "???";
			}

			tempText = tempMatcher.group(1) + tempValue + tempMatcher.group(3);
			tempMatcher = PARAMETER_PATTERN.matcher(tempText);
		}

		return tempText;
	}
}
