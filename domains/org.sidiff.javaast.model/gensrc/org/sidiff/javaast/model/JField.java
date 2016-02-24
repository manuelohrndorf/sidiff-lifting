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
 * A representation of the model object '<em><b>JField</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JField#isIsFinal <em>Is Final</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JField#isIsStatic <em>Is Static</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JField#getAccessedBy <em>Accessed By</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JField#isIsTransient <em>Is Transient</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.JField#getInitialization <em>Initialization</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJField()
 * @model
 * @generated
 */
public interface JField extends JIdentifiableElement, JDocumentableElement, JNamedElement, JTypedElement, JVisibleElement
{
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
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJField_IsFinal()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsFinal();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JField#isIsFinal <em>Is Final</em>}' attribute.
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
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJField_IsStatic()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsStatic();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JField#isIsStatic <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Static</em>' attribute.
	 * @see #isIsStatic()
	 * @generated
	 */
  void setIsStatic(boolean value);

  /**
	 * Returns the value of the '<em><b>Accessed By</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.javaast.model.JMethod}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.javaast.model.JMethod#getAccesses <em>Accesses</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Accessed By</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Accessed By</em>' reference list.
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJField_AccessedBy()
	 * @see org.sidiff.javaast.model.JMethod#getAccesses
	 * @model opposite="accesses" ordered="false"
	 * @generated
	 */
  EList<JMethod> getAccessedBy();

  /**
	 * Returns the value of the '<em><b>Is Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is Transient</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Transient</em>' attribute.
	 * @see #setIsTransient(boolean)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJField_IsTransient()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsTransient();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JField#isIsTransient <em>Is Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Transient</em>' attribute.
	 * @see #isIsTransient()
	 * @generated
	 */
  void setIsTransient(boolean value);

  /**
	 * Returns the value of the '<em><b>Initialization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Initialization</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Initialization</em>' containment reference.
	 * @see #setInitialization(JCodeBlock)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJField_Initialization()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
  JCodeBlock getInitialization();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JField#getInitialization <em>Initialization</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initialization</em>' containment reference.
	 * @see #getInitialization()
	 * @generated
	 */
  void setInitialization(JCodeBlock value);

} // JField
