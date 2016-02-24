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
 * A representation of the model object '<em><b>JPackage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JPackage#getSubPackages <em>Sub Packages</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JPackage#getClasses <em>Classes</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JPackage#getInterfaces <em>Interfaces</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJPackage()
 * @model
 * @generated
 */
public interface JPackage extends JIdentifiableElement, JNamedElement
{
  /**
	 * Returns the value of the '<em><b>Sub Packages</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JPackage}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sub Packages</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Packages</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJPackage_SubPackages()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JPackage> getSubPackages();

  /**
	 * Returns the value of the '<em><b>Classes</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JClass}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Classes</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Classes</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJPackage_Classes()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JClass> getClasses();

  /**
	 * Returns the value of the '<em><b>Interfaces</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JInterface}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Interfaces</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Interfaces</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJPackage_Interfaces()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JInterface> getInterfaces();

} // JPackage
