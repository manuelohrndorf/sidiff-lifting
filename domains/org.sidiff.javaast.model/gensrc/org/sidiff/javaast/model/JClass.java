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
 * A representation of the model object '<em><b>JClass</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JClass#getFields <em>Fields</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JClass#isIsAbstract <em>Is Abstract</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JClass#isIsFinal <em>Is Final</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JClass#getMethods <em>Methods</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JClass#getSuperClass <em>Super Class</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JClass#getSubClasses <em>Sub Classes</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JClass#getImplementedInterfaces <em>Implemented Interfaces</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JClass#getStaticInitializationBlock <em>Static Initialization Block</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJClass()
 * @model
 * @generated
 */
public interface JClass extends JClassifier, JIdentifiableElement, JDocumentableElement, JTemplate, JType, JVisibleElement
{
  /**
	 * Returns the value of the '<em><b>Fields</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JField}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fields</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Fields</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJClass_Fields()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JField> getFields();

  /**
	 * Returns the value of the '<em><b>Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is Abstract</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Abstract</em>' attribute.
	 * @see #setIsAbstract(boolean)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJClass_IsAbstract()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsAbstract();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JClass#isIsAbstract <em>Is Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Abstract</em>' attribute.
	 * @see #isIsAbstract()
	 * @generated
	 */
  void setIsAbstract(boolean value);

  /**
	 * Returns the value of the '<em><b>Is Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is Final</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Final</em>' attribute.
	 * @see #setIsFinal(boolean)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJClass_IsFinal()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsFinal();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JClass#isIsFinal <em>Is Final</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Final</em>' attribute.
	 * @see #isIsFinal()
	 * @generated
	 */
  void setIsFinal(boolean value);

  /**
	 * Returns the value of the '<em><b>Methods</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JMethod}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Methods</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Methods</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJClass_Methods()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JMethod> getMethods();

  /**
	 * Returns the value of the '<em><b>Super Class</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.javaast.model.JClass#getSubClasses <em>Sub Classes</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Super Class</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Class</em>' reference.
	 * @see #setSuperClass(JClass)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJClass_SuperClass()
	 * @see org.sidiff.javaast.model.JClass#getSubClasses
	 * @model opposite="subClasses" ordered="false"
	 * @generated
	 */
  JClass getSuperClass();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JClass#getSuperClass <em>Super Class</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Class</em>' reference.
	 * @see #getSuperClass()
	 * @generated
	 */
  void setSuperClass(JClass value);

  /**
	 * Returns the value of the '<em><b>Sub Classes</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JClass}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.javaast.model.JClass#getSuperClass <em>Super Class</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sub Classes</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Classes</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJClass_SubClasses()
	 * @see org.sidiff.javaast.model.JClass#getSuperClass
	 * @model opposite="superClass" ordered="false"
	 * @generated
	 */
  EList<JClass> getSubClasses();

  /**
	 * Returns the value of the '<em><b>Implemented Interfaces</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JInterface}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.javaast.model.JInterface#getImplementingClasses <em>Implementing Classes</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Implemented Interfaces</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Implemented Interfaces</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJClass_ImplementedInterfaces()
	 * @see org.sidiff.javaast.model.JInterface#getImplementingClasses
	 * @model opposite="implementingClasses" ordered="false"
	 * @generated
	 */
  EList<JInterface> getImplementedInterfaces();

  /**
	 * Returns the value of the '<em><b>Static Initialization Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Static Initialization Block</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Static Initialization Block</em>' containment reference.
	 * @see #setStaticInitializationBlock(JCodeBlock)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJClass_StaticInitializationBlock()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  JCodeBlock getStaticInitializationBlock();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JClass#getStaticInitializationBlock <em>Static Initialization Block</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Static Initialization Block</em>' containment reference.
	 * @see #getStaticInitializationBlock()
	 * @generated
	 */
  void setStaticInitializationBlock(JCodeBlock value);

} // JClass
