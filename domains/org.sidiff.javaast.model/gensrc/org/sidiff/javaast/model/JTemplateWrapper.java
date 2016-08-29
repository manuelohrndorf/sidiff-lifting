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
 * A representation of the model object '<em><b>JTemplate Wrapper</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JTemplateWrapper#getBindings <em>Bindings</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJTemplateWrapper()
 * @model
 * @generated
 */
public interface JTemplateWrapper extends JIdentifiableElement, JNamedElement, JType
{
  /**
	 * Returns the value of the '<em><b>Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JTemplateBinding}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Bindings</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Bindings</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJTemplateWrapper_Bindings()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JTemplateBinding> getBindings();

} // JTemplateWrapper
