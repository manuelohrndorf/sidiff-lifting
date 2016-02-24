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
 * A representation of the model object '<em><b>JDocumentable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JDocumentableElement#getJavaDoc <em>Java Doc</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJDocumentableElement()
 * @model abstract="true"
 * @generated
 */
public interface JDocumentableElement extends EObject
{
  /**
	 * Returns the value of the '<em><b>Java Doc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Java Doc</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Doc</em>' containment reference.
	 * @see #setJavaDoc(JavaDoc)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJDocumentableElement_JavaDoc()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  JavaDoc getJavaDoc();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JDocumentableElement#getJavaDoc <em>Java Doc</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Doc</em>' containment reference.
	 * @see #getJavaDoc()
	 * @generated
	 */
  void setJavaDoc(JavaDoc value);

} // JDocumentableElement
