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
 * A representation of the model object '<em><b>JMethod</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JMethod#isIsAbstract <em>Is Abstract</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JMethod#isIsFinal <em>Is Final</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JMethod#isIsStatic <em>Is Static</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JMethod#isIsConstructor <em>Is Constructor</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JMethod#isIsSynchronized <em>Is Synchronized</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JMethod#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JMethod#getCalls <em>Calls</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JMethod#getCalledBy <em>Called By</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JMethod#getRaisedException <em>Raised Exception</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JMethod#getBody <em>Body</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JMethod#getAccesses <em>Accesses</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod()
 * @model
 * @generated
 */
public interface JMethod extends JIdentifiableElement, JDocumentableElement, JNamedElement, JTemplate, JTypedElement, JVisibleElement
{
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
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_IsAbstract()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsAbstract();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JMethod#isIsAbstract <em>Is Abstract</em>}' attribute.
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
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_IsFinal()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsFinal();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JMethod#isIsFinal <em>Is Final</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Final</em>' attribute.
	 * @see #isIsFinal()
	 * @generated
	 */
  void setIsFinal(boolean value);

  /**
	 * Returns the value of the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is Static</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Static</em>' attribute.
	 * @see #setIsStatic(boolean)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_IsStatic()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsStatic();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JMethod#isIsStatic <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Static</em>' attribute.
	 * @see #isIsStatic()
	 * @generated
	 */
  void setIsStatic(boolean value);

  /**
	 * Returns the value of the '<em><b>Is Constructor</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is Constructor</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Constructor</em>' attribute.
	 * @see #setIsConstructor(boolean)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_IsConstructor()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsConstructor();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JMethod#isIsConstructor <em>Is Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Constructor</em>' attribute.
	 * @see #isIsConstructor()
	 * @generated
	 */
  void setIsConstructor(boolean value);

  /**
	 * Returns the value of the '<em><b>Is Synchronized</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is Synchronized</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Synchronized</em>' attribute.
	 * @see #setIsSynchronized(boolean)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_IsSynchronized()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsSynchronized();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JMethod#isIsSynchronized <em>Is Synchronized</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Synchronized</em>' attribute.
	 * @see #isIsSynchronized()
	 * @generated
	 */
  void setIsSynchronized(boolean value);

  /**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JParameter}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_Parameters()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  EList<JParameter> getParameters();

  /**
	 * Returns the value of the '<em><b>Calls</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JMethod}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.javaast.model.JMethod#getCalledBy <em>Called By</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Calls</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Calls</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_Calls()
	 * @see org.sidiff.javaast.model.JMethod#getCalledBy
	 * @model opposite="calledBy" ordered="false"
	 * @generated
	 */
  EList<JMethod> getCalls();

  /**
	 * Returns the value of the '<em><b>Called By</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JMethod}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.javaast.model.JMethod#getCalls <em>Calls</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Called By</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Called By</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_CalledBy()
	 * @see org.sidiff.javaast.model.JMethod#getCalls
	 * @model opposite="calls" ordered="false"
	 * @generated
	 */
  EList<JMethod> getCalledBy();

  /**
	 * Returns the value of the '<em><b>Raised Exception</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Raised Exception</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Raised Exception</em>' reference.
	 * @see #setRaisedException(JClass)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_RaisedException()
	 * @model ordered="false"
	 * @generated
	 */
  JClass getRaisedException();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JMethod#getRaisedException <em>Raised Exception</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Raised Exception</em>' reference.
	 * @see #getRaisedException()
	 * @generated
	 */
  void setRaisedException(JClass value);

  /**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Body</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(JCodeBlock)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_Body()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  JCodeBlock getBody();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JMethod#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
  void setBody(JCodeBlock value);

  /**
	 * Returns the value of the '<em><b>Accesses</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JField}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.javaast.model.JField#getAccessedBy <em>Accessed By</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Accesses</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Accesses</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJMethod_Accesses()
	 * @see org.sidiff.javaast.model.JField#getAccessedBy
	 * @model opposite="accessedBy" ordered="false"
	 * @generated
	 */
  EList<JField> getAccesses();

} // JMethod
