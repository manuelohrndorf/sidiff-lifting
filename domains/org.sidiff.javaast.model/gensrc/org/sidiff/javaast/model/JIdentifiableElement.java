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
 * A representation of the model object '<em><b>JIdentifiable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JIdentifiableElement#getId <em>Id</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JIdentifiableElement#isCompileable <em>Compileable</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJIdentifiableElement()
 * @model abstract="true"
 * @generated
 */
public interface JIdentifiableElement extends EObject
{
  /**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJIdentifiableElement_Id()
	 * @model default="" id="true" required="true" ordered="false"
	 * @generated
	 */
  String getId();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JIdentifiableElement#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
  void setId(String value);

  /**
	 * Returns the value of the '<em><b>Compileable</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Compileable</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Compileable</em>' attribute.
	 * @see #setCompileable(boolean)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJIdentifiableElement_Compileable()
	 * @model default="true" required="true" ordered="false"
	 * @generated
	 */
  boolean isCompileable();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JIdentifiableElement#isCompileable <em>Compileable</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compileable</em>' attribute.
	 * @see #isCompileable()
	 * @generated
	 */
  void setCompileable(boolean value);

} // JIdentifiableElement
