/**
 */
package de.gebit.integrity.dsl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Time Difference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.gebit.integrity.dsl.TimeDifference#getDirection <em>Direction</em>}</li>
 *   <li>{@link de.gebit.integrity.dsl.TimeDifference#getFixedValues <em>Fixed Values</em>}</li>
 *   <li>{@link de.gebit.integrity.dsl.TimeDifference#getCalculatedValue <em>Calculated Value</em>}</li>
 * </ul>
 *
 * @see de.gebit.integrity.dsl.DslPackage#getTimeDifference()
 * @model
 * @generated
 */
public interface TimeDifference extends EObject
{
  /**
   * Returns the value of the '<em><b>Direction</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Direction</em>' attribute.
   * @see #setDirection(String)
   * @see de.gebit.integrity.dsl.DslPackage#getTimeDifference_Direction()
   * @model
   * @generated
   */
  String getDirection();

  /**
   * Sets the value of the '{@link de.gebit.integrity.dsl.TimeDifference#getDirection <em>Direction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Direction</em>' attribute.
   * @see #getDirection()
   * @generated
   */
  void setDirection(String value);

  /**
   * Returns the value of the '<em><b>Fixed Values</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fixed Values</em>' attribute list.
   * @see de.gebit.integrity.dsl.DslPackage#getTimeDifference_FixedValues()
   * @model unique="false"
   * @generated
   */
  EList<String> getFixedValues();

  /**
   * Returns the value of the '<em><b>Calculated Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Calculated Value</em>' containment reference.
   * @see #setCalculatedValue(ValueOrEnumValueOrOperation)
   * @see de.gebit.integrity.dsl.DslPackage#getTimeDifference_CalculatedValue()
   * @model containment="true"
   * @generated
   */
  ValueOrEnumValueOrOperation getCalculatedValue();

  /**
   * Sets the value of the '{@link de.gebit.integrity.dsl.TimeDifference#getCalculatedValue <em>Calculated Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Calculated Value</em>' containment reference.
   * @see #getCalculatedValue()
   * @generated
   */
  void setCalculatedValue(ValueOrEnumValueOrOperation value);

} // TimeDifference
