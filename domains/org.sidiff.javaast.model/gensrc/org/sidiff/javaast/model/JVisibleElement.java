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
 * A representation of the model object '<em><b>JVisible Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JVisibleElement#getVisibilityKind <em>Visibility Kind</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJVisibleElement()
 * @model abstract="true"
 * @generated
 */
public interface JVisibleElement extends EObject
{
  /**
	 * Returns the value of the '<em><b>Visibility Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.sidiff.javaast.model.JVisibilityKind}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Visibility Kind</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Visibility Kind</em>' attribute.
	 * @see org.sidiff.javaast.model.JVisibilityKind
	 * @see #setVisibilityKind(JVisibilityKind)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJVisibleElement_VisibilityKind()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  JVisibilityKind getVisibilityKind();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JVisibleElement#getVisibilityKind <em>Visibility Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visibility Kind</em>' attribute.
	 * @see org.sidiff.javaast.model.JVisibilityKind
	 * @see #getVisibilityKind()
	 * @generated
	 */
  void setVisibilityKind(JVisibilityKind value);

} // JVisibleElement
