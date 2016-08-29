/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JTemplate Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JTemplateBinding#getType <em>Type</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JTemplateBinding#getGenericType <em>Generic Type</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJTemplateBinding()
 * @model
 * @generated
 */
public interface JTemplateBinding extends JIdentifiableElement
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
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJTemplateBinding_Type()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  JType getType();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JTemplateBinding#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
  void setType(JType value);

  /**
	 * Returns the value of the '<em><b>Generic Type</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Generic Type</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Generic Type</em>' reference.
	 * @see #setGenericType(JGenericType)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJTemplateBinding_GenericType()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  JGenericType getGenericType();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JTemplateBinding#getGenericType <em>Generic Type</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generic Type</em>' reference.
	 * @see #getGenericType()
	 * @generated
	 */
  void setGenericType(JGenericType value);

} // JTemplateBinding
