/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JTyped Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JTypedElement#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJTypedElement()
 * @model abstract="true"
 * @generated
 */
public interface JTypedElement extends EObject
{
  /**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(JType)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJTypedElement_Type()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  JType getType();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JTypedElement#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
  void setType(JType value);

} // JTypedElement
