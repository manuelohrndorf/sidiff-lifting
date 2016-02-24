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
 * A representation of the model object '<em><b>JInterface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JInterface#getConstants <em>Constants</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JInterface#getMethodSignatures <em>Method Signatures</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JInterface#getSuperInterfaces <em>Super Interfaces</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JInterface#getSubInterfaces <em>Sub Interfaces</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JInterface#getImplementingClasses <em>Implementing Classes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJInterface()
 * @model
 * @generated
 */
public interface JInterface extends JClassifier, JIdentifiableElement, JDocumentableElement, JTemplate, JType, JVisibleElement
{
  /**
	 * Returns the value of the '<em><b>Constants</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JConstant}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Constants</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Constants</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJInterface_Constants()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JConstant> getConstants();

  /**
	 * Returns the value of the '<em><b>Method Signatures</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JMethod}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Method Signatures</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Method Signatures</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJInterface_MethodSignatures()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JMethod> getMethodSignatures();

  /**
	 * Returns the value of the '<em><b>Super Interfaces</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JInterface}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.javaast.model.JInterface#getSubInterfaces <em>Sub Interfaces</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Super Interfaces</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Interfaces</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJInterface_SuperInterfaces()
	 * @see org.sidiff.javaast.model.JInterface#getSubInterfaces
	 * @model opposite="subInterfaces" ordered="false"
	 * @generated
	 */
  EList<JInterface> getSuperInterfaces();

  /**
	 * Returns the value of the '<em><b>Sub Interfaces</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JInterface}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.javaast.model.JInterface#getSuperInterfaces <em>Super Interfaces</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sub Interfaces</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Interfaces</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJInterface_SubInterfaces()
	 * @see org.sidiff.javaast.model.JInterface#getSuperInterfaces
	 * @model opposite="superInterfaces" ordered="false"
	 * @generated
	 */
  EList<JInterface> getSubInterfaces();

  /**
	 * Returns the value of the '<em><b>Implementing Classes</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JClass}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.javaast.model.JClass#getImplementedInterfaces <em>Implemented Interfaces</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Implementing Classes</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementing Classes</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJInterface_ImplementingClasses()
	 * @see org.sidiff.javaast.model.JClass#getImplementedInterfaces
	 * @model opposite="implementedInterfaces" ordered="false"
	 * @generated
	 */
  EList<JClass> getImplementingClasses();

} // JInterface
