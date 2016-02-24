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
 * A representation of the model object '<em><b>JProject</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JProject#getPackages <em>Packages</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JProject#getSimpleTypes <em>Simple Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJProject()
 * @model
 * @generated
 */
public interface JProject extends JIdentifiableElement, JNamedElement
{
  /**
	 * Returns the value of the '<em><b>Packages</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JPackage}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Packages</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Packages</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJProject_Packages()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JPackage> getPackages();

  /**
	 * Returns the value of the '<em><b>Simple Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JType}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Simple Types</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Simple Types</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJProject_SimpleTypes()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JType> getSimpleTypes();

} // JProject
