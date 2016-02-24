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
 * A representation of the model object '<em><b>JEnumeration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JEnumeration#getLiterals <em>Literals</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJEnumeration()
 * @model
 * @generated
 */
public interface JEnumeration extends JClass
{
  /**
	 * Returns the value of the '<em><b>Literals</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JEnumerationLiteral}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Literals</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Literals</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJEnumeration_Literals()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JEnumerationLiteral> getLiterals();

} // JEnumeration
