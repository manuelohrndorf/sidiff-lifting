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
 * A representation of the model object '<em><b>JType</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JType#isIsExternal <em>Is External</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJType()
 * @model abstract="true"
 * @generated
 */
public interface JType extends EObject
{
  /**
	 * Returns the value of the '<em><b>Is External</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is External</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Is External</em>' attribute.
	 * @see #setIsExternal(boolean)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJType_IsExternal()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsExternal();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JType#isIsExternal <em>Is External</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is External</em>' attribute.
	 * @see #isIsExternal()
	 * @generated
	 */
  void setIsExternal(boolean value);

} // JType
