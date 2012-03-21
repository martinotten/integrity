/**
 * 
 */
package de.gebit.integrity.ui.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;

import de.gebit.integrity.dsl.ValueOrEnumValue;
import de.gebit.integrity.fixtures.ArbitraryParameterEnumerator;
import de.gebit.integrity.fixtures.ArbitraryParameterEnumerator.ArbitraryParameterDefinition;
import de.gebit.integrity.fixtures.ArbitraryParameterEnumerator.ArbitraryParameterFixtureLink;
import de.gebit.integrity.fixtures.ArbitraryParameterFixture;
import de.gebit.integrity.fixtures.CustomProposalFixture;
import de.gebit.integrity.fixtures.CustomProposalProvider;
import de.gebit.integrity.fixtures.CustomProposalProvider.CustomProposalFixtureLink;
import de.gebit.integrity.fixtures.FixtureParameter;
import de.gebit.integrity.utils.ParameterUtil;

/**
 * Wrapper used in a similar fashion as the {@link de.gebit.integrity.fixtures.FixtureWrapper}. It encapsulates an IType
 * object and provides methods required when accessing the type during autocompletion etc.
 * 
 * @author Rene Schneider
 * 
 */
public class FixtureTypeWrapper {

	/**
	 * The encapsulated type.
	 */
	private IType fixtureType;

	/**
	 * Creates a new instance.
	 * 
	 * @param aFixtureType
	 *            the type to encapsulate
	 */
	public FixtureTypeWrapper(IType aFixtureType) {
		fixtureType = aFixtureType;
	}

	/**
	 * Finds a method with a specific name in the encapsulated type.
	 * 
	 * @param aMethodName
	 *            the method name to find
	 * @return the method if found, null if not
	 * @throws JavaModelException
	 */
	protected IMethod findMethod(String aMethodName) throws JavaModelException {
		for (IMethod tempPossibleMethod : fixtureType.getMethods()) {
			if (aMethodName.equals(tempPossibleMethod.getElementName())) {
				return tempPossibleMethod;
			}
		}

		return null;
	}

	/**
	 * Replaces all values in the given parameter map with converted versions that match the types that are expected by
	 * the given fixture method.
	 * 
	 * @param aFixtureMethodName
	 *            the method name
	 * @param aParameterMap
	 *            the parameter map
	 * @param anIncludeArbitraryParametersFlag
	 *            whether arbitrary parameters shall be included
	 * @throws JavaModelException
	 */
	public void convertParameterValuesToFixtureDefinedTypes(String aFixtureMethodName,
			Map<String, Object> aParameterMap, boolean anIncludeArbitraryParametersFlag) throws JavaModelException {
		IMethod tempMethod = findMethod(aFixtureMethodName);

		Map<String, Object> tempFixedParamsMap = new HashMap<String, Object>();
		for (ILocalVariable tempParam : tempMethod.getParameters()) {
			String tempParamTypeName = IntegrityDSLUIUtil
					.getResolvedTypeName(tempParam.getTypeSignature(), fixtureType);
			if (tempParamTypeName == null) {
				continue;
			}

			if (tempParamTypeName.startsWith(Map.class.getName())) {
				// ignore the arbitrary parameter parameter
			} else {
				IAnnotation tempAnnotation = tempParam.getAnnotation(FixtureParameter.class.getSimpleName());

				if (tempAnnotation != null) {
					String tempName = null;
					for (IMemberValuePair tempPair : tempAnnotation.getMemberValuePairs()) {
						if ("name".equals(tempPair.getMemberName())) {
							tempName = (String) tempPair.getValue();
						}
					}

					if (tempName != null) {
						Object tempValue = aParameterMap.get(tempName);
						if (tempValue != null) {
							Class<?> tempExpectedType;
							try {
								tempExpectedType = getClass().getClassLoader().loadClass(tempParamTypeName);
							} catch (ClassNotFoundException exc) {
								// we'll skip this param
								continue;
							}

							Object tempConvertedValue;
							if (tempValue instanceof Object[]) {
								if (!tempExpectedType.isArray()) {
									throw new IllegalArgumentException("The parameter '" + tempName + "' of method '"
											+ aFixtureMethodName + "' in fixture '"
											+ fixtureType.getFullyQualifiedName()
											+ "' is not an array type, thus you cannot put multiple values into it!");
								}
								Object tempConvertedValueArray = Array.newInstance(tempExpectedType.getComponentType(),
										((Object[]) tempValue).length);
								for (int k = 0; k < ((Object[]) tempValue).length; k++) {
									Object tempSingleValue = ((Object[]) tempValue)[k];
									if (tempSingleValue instanceof ValueOrEnumValue) {
										Array.set(tempConvertedValueArray, k, ParameterUtil
												.convertEncapsulatedValueToParamType(
														tempExpectedType.getComponentType(),
														(ValueOrEnumValue) tempSingleValue, null));
									} else {
										Array.set(
												tempConvertedValueArray,
												k,
												ParameterUtil.convertValueToParamType(
														tempExpectedType.getComponentType(), tempSingleValue));
									}
								}
								tempConvertedValue = tempConvertedValueArray;
							} else {
								// if the expected type is an array, we don't want to convert to that array, but to the
								// component type, of course
								Class<?> tempConversionTargetType = tempExpectedType.isArray() ? tempExpectedType
										.getComponentType() : tempExpectedType;
								if (tempValue instanceof ValueOrEnumValue) {
									tempConvertedValue = ParameterUtil.convertEncapsulatedValueToParamType(
											tempConversionTargetType, (ValueOrEnumValue) tempValue, null);
								} else {
									tempConvertedValue = ParameterUtil.convertValueToParamType(
											tempConversionTargetType, tempValue);
								}
								if (tempExpectedType.isArray()) {
									// ...and if the expected type is an array, now we create one
									Object tempNewArray = Array.newInstance(tempExpectedType.getComponentType(), 1);
									Array.set(tempNewArray, 0, tempConvertedValue);
									tempConvertedValue = tempNewArray;
								}
							}
							aParameterMap.put(tempName, tempConvertedValue);
							tempFixedParamsMap.put(tempName, tempConvertedValue);
						}
					}
				}
			}
		}

		if (anIncludeArbitraryParametersFlag && isArbitraryParameterFixtureClass()) {
			ArbitraryParameterEnumerator tempArbitraryParameterEnumerator = instantiateArbitraryParameterEnumerator();

			List<ArbitraryParameterDefinition> tempArbitraryParameters = tempArbitraryParameterEnumerator
					.defineArbitraryParameters(aFixtureMethodName, tempFixedParamsMap);
			for (ArbitraryParameterDefinition tempArbitraryParameter : tempArbitraryParameters) {
				String tempName = tempArbitraryParameter.getName();
				Object tempValue = aParameterMap.remove(tempName);
				Class<?> tempExpectedType = tempArbitraryParameter.getType();
				if (tempValue != null) {
					Object tempConvertedValue;
					if (tempValue instanceof Object[]) {
						if (!tempExpectedType.isArray()) {
							throw new IllegalArgumentException("The parameter '" + tempName + "' of method '"
									+ aFixtureMethodName + "' in fixture '" + fixtureType.getFullyQualifiedName()
									+ "' is not an array type, thus you cannot put multiple values into it!");
						}
						Object tempConvertedValueArray = Array.newInstance(tempExpectedType.getComponentType(),
								((Object[]) tempValue).length);
						for (int k = 0; k < ((Object[]) tempValue).length; k++) {
							Object tempSingleValue = ((Object[]) tempValue)[k];
							if (tempSingleValue instanceof ValueOrEnumValue) {
								Array.set(tempConvertedValueArray, k, ParameterUtil
										.convertEncapsulatedValueToParamType(tempExpectedType.getComponentType(),
												(ValueOrEnumValue) tempSingleValue, null));
							} else {
								Array.set(tempConvertedValueArray, k, ParameterUtil.convertValueToParamType(
										tempExpectedType.getComponentType(), tempSingleValue));
							}
						}
						tempConvertedValue = tempConvertedValueArray;
					} else {
						if (tempValue instanceof ValueOrEnumValue) {
							tempConvertedValue = ParameterUtil.convertEncapsulatedValueToParamType(tempExpectedType,
									(ValueOrEnumValue) tempValue, null);
						} else {
							tempConvertedValue = ParameterUtil.convertValueToParamType(tempExpectedType, tempValue);
						}
						if (tempExpectedType.isArray()) {
							// The target type may still be an array, even though just one parameter value was given
							Object tempNewArray = Array.newInstance(tempExpectedType.getComponentType(), 1);
							Array.set(tempNewArray, 0, tempConvertedValue);
							tempConvertedValue = tempNewArray;
						}
					}
					aParameterMap.put(tempName, tempConvertedValue);
				}
			}
		}
	}

	/**
	 * Checks whether the encapsulated type is an arbitrary parameter fixture class.
	 * 
	 * @return
	 */
	public boolean isArbitraryParameterFixtureClass() {
		try {
			for (IType tempInterface : fixtureType.newSupertypeHierarchy(null).getAllSuperInterfaces(fixtureType)) {
				if (ArbitraryParameterFixture.class.getName().equals(tempInterface.getFullyQualifiedName())) {
					return true;
				}
			}
		} catch (JavaModelException exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Determines the {@link ArbitraryParameterEnumerator} class to use for the encapsulated fixture type and
	 * instantiates it.
	 * 
	 * @return the enumerator or null if the class isn't an arbitrary fixture class or if there is no matching
	 *         enumerator class found in the workspace
	 */
	public ArbitraryParameterEnumerator instantiateArbitraryParameterEnumerator() {
		if (!isArbitraryParameterFixtureClass()) {
			return null;
		}
		LinkedProviderFinder<ArbitraryParameterEnumerator> tempFinder = new LinkedProviderFinder<ArbitraryParameterEnumerator>(
				ArbitraryParameterFixtureLink.class);
		try {
			Class<? extends ArbitraryParameterEnumerator> tempClass = tempFinder.findProviderForFixtureType(fixtureType
					.getFullyQualifiedName());
			if (tempClass != null) {
				return tempClass.newInstance();
			}
		} catch (ClassNotFoundException exc) {
			exc.printStackTrace();
		} catch (InstantiationException exc) {
			exc.printStackTrace();
		} catch (IllegalAccessException exc) {
			exc.printStackTrace();
		}

		return null;
	}

	/**
	 * Checks whether the encapsulated type is a custom proposal fixture class.
	 * 
	 * @return
	 */
	public boolean isCustomProposalFixtureClass() {
		try {
			for (IType tempInterface : fixtureType.newSupertypeHierarchy(null).getAllSuperInterfaces(fixtureType)) {
				if (CustomProposalFixture.class.getName().equals(tempInterface.getFullyQualifiedName())) {
					return true;
				}
			}
		} catch (JavaModelException exc) {
			exc.printStackTrace();
		}
		return false;
	}

	/**
	 * Determines the {@link CustomProposalProvider} class to use for the encapsulated fixture type and instantiates it.
	 * 
	 * @return the custom proposal provider
	 */
	public CustomProposalProvider instantiateCustomProposalProvider() {
		if (!isCustomProposalFixtureClass()) {
			return null;
		}
		LinkedProviderFinder<CustomProposalProvider> tempFinder = new LinkedProviderFinder<CustomProposalProvider>(
				CustomProposalFixtureLink.class);
		try {
			Class<? extends CustomProposalProvider> tempClass = tempFinder.findProviderForFixtureType(fixtureType
					.getFullyQualifiedName());
			if (tempClass != null) {
				return tempClass.newInstance();
			}
		} catch (ClassNotFoundException exc) {
			exc.printStackTrace();
		} catch (InstantiationException exc) {
			exc.printStackTrace();
		} catch (IllegalAccessException exc) {
			exc.printStackTrace();
		}

		return null;
	}

	/**
	 * Finds providers linked to fixtures using link annotations.
	 * 
	 * 
	 * @author Rene Schneider
	 * 
	 * @param <Provider>
	 */
	private final class LinkedProviderFinder<Provider> {

		/**
		 * The link annotation class.
		 */
		private Class<? extends Annotation> linkAnnotationClass;

		private LinkedProviderFinder(Class<? extends Annotation> aLinkAnnotationClass) {
			linkAnnotationClass = aLinkAnnotationClass;
		}

		/**
		 * The search result.
		 */
		private IType searchResult;

		@SuppressWarnings("unchecked")
		private Class<? extends Provider> findProviderForFixtureType(final String aFullyQualifiedName)
				throws ClassNotFoundException, InstantiationException, IllegalAccessException {
			SearchPattern tempInheritPattern = SearchPattern.createPattern(
					linkAnnotationClass.getName().replace('$', '.'), IJavaSearchConstants.ANNOTATION_TYPE,
					IJavaSearchConstants.REFERENCES | IJavaSearchConstants.ANNOTATION_TYPE_REFERENCE,
					SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE);
			IJavaSearchScope tempScope = SearchEngine.createWorkspaceScope();
			SearchRequestor tempRequestor = new SearchRequestor() {

				@Override
				public void acceptSearchMatch(SearchMatch aMatch) throws CoreException {
					IType tempType = (IType) aMatch.getElement();

					IAnnotation tempAnnotation = tempType.getAnnotation(linkAnnotationClass.getSimpleName());

					if (tempAnnotation != null) {
						IMemberValuePair[] tempPairs = tempAnnotation.getMemberValuePairs();
						if (tempPairs.length == 1) {
							String tempLinkedClassName = (String) tempPairs[0].getValue();
							if (aFullyQualifiedName.equals(tempLinkedClassName)) {
								// this is a match
								searchResult = tempType;
							} else {
								// no match, but that could be because the linked class name is not fully qualified
								if (aFullyQualifiedName.endsWith(tempLinkedClassName)) {
									// okay, we have to check that
									String[][] tempPossibleMatches = tempType.resolveType(tempLinkedClassName);
									for (String[] tempMatch : tempPossibleMatches) {
										// we can ignore the default package here as that case would have already
										// matched before
										String tempFullMatch = tempMatch[0] + "." + tempMatch[1];
										if (aFullyQualifiedName.equals(tempFullMatch)) {
											// yeah, a match
											searchResult = tempType;
										}
									}
								}
							}
						}
					}
				}
			};

			SearchEngine tempSearchEngine = new SearchEngine();
			try {
				tempSearchEngine.search(tempInheritPattern,
						new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, tempScope,
						tempRequestor, null);
			} catch (CoreException exc) {
				exc.printStackTrace();
			}

			// at this point the search should have found a result and stored it, otherwise we can stop here
			if (searchResult == null) {
				return null;
			}

			return (Class<? extends Provider>) ClassLoadingUtil.loadClassFromWorkspace(
					searchResult.getFullyQualifiedName(), searchResult.getJavaProject());
		}
	}

}
