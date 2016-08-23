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
 * A representation of the model object '<em><b>JClassifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JClassifier#getInnerClassifiers <em>Inner Classifiers</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJClassifier()
 * @model abstract="true"
 * @generated
 */
public interface JClassifier extends JNamedElement
{
  /**
	 * Returns the value of the '<em><b>Inner Classifiers</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JClassifier}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Inner Classifiers</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Inner Classifiers</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJClassifier_InnerClassifiers()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JClassifier> getInnerClassifiers();

} // JClassifier
