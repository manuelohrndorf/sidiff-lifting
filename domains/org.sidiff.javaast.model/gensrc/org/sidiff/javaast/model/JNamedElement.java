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
 * A representation of the model object '<em><b>JNamed Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JNamedElement#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JNamedElement#getQualifiedName <em>Qualified Name</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJNamedElement()
 * @model abstract="true"
 * @generated
 */
public interface JNamedElement extends EObject
{
  /**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJNamedElement_Name()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  String getName();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JNamedElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
  void setName(String value);

		/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualified Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJNamedElement_QualifiedName()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getQualifiedName();

} // JNamedElement
