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
 * A representation of the model object '<em><b>JAdressable Fragment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JAdressableFragment#getStartByte <em>Start Byte</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JAdressableFragment#getByteLength <em>Byte Length</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJAdressableFragment()
 * @model abstract="true"
 * @generated
 */
public interface JAdressableFragment extends EObject
{
  /**
	 * Returns the value of the '<em><b>Start Byte</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Start Byte</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Byte</em>' attribute.
	 * @see #setStartByte(int)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJAdressableFragment_StartByte()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  int getStartByte();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JAdressableFragment#getStartByte <em>Start Byte</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Byte</em>' attribute.
	 * @see #getStartByte()
	 * @generated
	 */
  void setStartByte(int value);

  /**
	 * Returns the value of the '<em><b>Byte Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Byte Length</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Byte Length</em>' attribute.
	 * @see #setByteLength(int)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJAdressableFragment_ByteLength()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  int getByteLength();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JAdressableFragment#getByteLength <em>Byte Length</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Byte Length</em>' attribute.
	 * @see #getByteLength()
	 * @generated
	 */
  void setByteLength(int value);

} // JAdressableFragment
