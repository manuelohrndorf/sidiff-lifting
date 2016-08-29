/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JArray Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JArrayType#getArrayDimensions <em>Array Dimensions</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJArrayType()
 * @model
 * @generated
 */
public interface JArrayType extends JClass, JTypedElement
{
  /**
	 * Returns the value of the '<em><b>Array Dimensions</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Array Dimensions</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Dimensions</em>' attribute.
	 * @see #setArrayDimensions(int)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJArrayType_ArrayDimensions()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  int getArrayDimensions();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JArrayType#getArrayDimensions <em>Array Dimensions</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array Dimensions</em>' attribute.
	 * @see #getArrayDimensions()
	 * @generated
	 */
  void setArrayDimensions(int value);

} // JArrayType
