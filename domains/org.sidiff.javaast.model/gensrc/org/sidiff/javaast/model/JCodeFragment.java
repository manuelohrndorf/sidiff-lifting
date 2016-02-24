/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JCode Fragment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JCodeFragment#getUsedTypes <em>Used Types</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JCodeFragment#getCalledMethods <em>Called Methods</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JCodeFragment#getCode <em>Code</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JCodeFragment#getUsedFields <em>Used Fields</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JCodeFragment#getUsedParameters <em>Used Parameters</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JCodeFragment#getUsedLocalVariables <em>Used Local Variables</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeFragment()
 * @model
 * @generated
 */
public interface JCodeFragment extends JAdressableFragment, JIdentifiableElement
{
  /**
	 * Returns the value of the '<em><b>Used Types</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JType}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Used Types</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Used Types</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeFragment_UsedTypes()
	 * @model ordered="false"
	 * @generated
	 */
  EList<JType> getUsedTypes();

  /**
	 * Returns the value of the '<em><b>Called Methods</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JMethod}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Called Methods</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Called Methods</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeFragment_CalledMethods()
	 * @model ordered="false"
	 * @generated
	 */
  EList<JMethod> getCalledMethods();

  /**
	 * Returns the value of the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Code</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Code</em>' attribute.
	 * @see #setCode(String)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeFragment_Code()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  String getCode();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JCodeFragment#getCode <em>Code</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code</em>' attribute.
	 * @see #getCode()
	 * @generated
	 */
  void setCode(String value);

  /**
	 * Returns the value of the '<em><b>Used Fields</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JField}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Used Fields</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Used Fields</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeFragment_UsedFields()
	 * @model ordered="false"
	 * @generated
	 */
  EList<JField> getUsedFields();

  /**
	 * Returns the value of the '<em><b>Used Parameters</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JParameter}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Used Parameters</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Used Parameters</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeFragment_UsedParameters()
	 * @model ordered="false"
	 * @generated
	 */
  EList<JParameter> getUsedParameters();

  /**
	 * Returns the value of the '<em><b>Used Local Variables</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JLocalVariable}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Used Local Variables</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Used Local Variables</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJCodeFragment_UsedLocalVariables()
	 * @model ordered="false"
	 * @generated
	 */
  EList<JLocalVariable> getUsedLocalVariables();

} // JCodeFragment
