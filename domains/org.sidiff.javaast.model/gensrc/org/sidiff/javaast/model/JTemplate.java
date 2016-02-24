/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JTemplate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JTemplate#getGenericTypes <em>Generic Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJTemplate()
 * @model abstract="true"
 * @generated
 */
public interface JTemplate extends EObject
{
  /**
	 * Returns the value of the '<em><b>Generic Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JGenericType}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Generic Types</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Generic Types</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJTemplate_GenericTypes()
	 * @model containment="true"
	 * @generated
	 */
  EList<JGenericType> getGenericTypes();

} // JTemplate
