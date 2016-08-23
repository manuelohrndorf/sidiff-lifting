/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JSimple Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JSimpleType#getKind <em>Kind</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JSimpleType#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJSimpleType()
 * @model
 * @generated
 */
public interface JSimpleType extends JIdentifiableElement, JType
{
  /**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.sidiff.javaast.model.JSimpleTypeKind}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Kind</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.sidiff.javaast.model.JSimpleTypeKind
	 * @see #setKind(JSimpleTypeKind)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJSimpleType_Kind()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  JSimpleTypeKind getKind();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JSimpleType#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.sidiff.javaast.model.JSimpleTypeKind
	 * @see #getKind()
	 * @generated
	 */
  void setKind(JSimpleTypeKind value);

		/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJSimpleType_Name()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getName();

} // JSimpleType
