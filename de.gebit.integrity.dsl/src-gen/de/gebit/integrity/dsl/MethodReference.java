/**
 */
package de.gebit.integrity.dsl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.gebit.integrity.dsl.MethodReference#getType <em>Type</em>}</li>
 *   <li>{@link de.gebit.integrity.dsl.MethodReference#getMethod <em>Method</em>}</li>
 * </ul>
 *
 * @see de.gebit.integrity.dsl.DslPackage#getMethodReference()
 * @model
 * @generated
 */
public interface MethodReference extends EObject
{
  /**
   * Returns the value of the '<em><b>Type</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' reference.
   * @see #setType(JvmType)
   * @see de.gebit.integrity.dsl.DslPackage#getMethodReference_Type()
   * @model
   * @generated
   */
  JvmType getType();

  /**
   * Sets the value of the '{@link de.gebit.integrity.dsl.MethodReference#getType <em>Type</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' reference.
   * @see #getType()
   * @generated
   */
  void setType(JvmType value);

  /**
   * Returns the value of the '<em><b>Method</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Method</em>' reference.
   * @see #setMethod(JvmOperation)
   * @see de.gebit.integrity.dsl.DslPackage#getMethodReference_Method()
   * @model
   * @generated
   */
  JvmOperation getMethod();

  /**
   * Sets the value of the '{@link de.gebit.integrity.dsl.MethodReference#getMethod <em>Method</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Method</em>' reference.
   * @see #getMethod()
   * @generated
   */
  void setMethod(JvmOperation value);

} // MethodReference
