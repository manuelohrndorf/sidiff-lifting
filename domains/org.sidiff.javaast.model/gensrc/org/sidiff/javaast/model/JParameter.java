/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JParameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JParameter#isIsFinal <em>Is Final</em>}</li>
 * </ul>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJParameter()
 * @model
 * @generated
 */
public interface JParameter extends JIdentifiableElement, JNamedElement, JTypedElement
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
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJParameter_IsFinal()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  boolean isIsFinal();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JParameter#isIsFinal <em>Is Final</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Final</em>' attribute.
	 * @see #isIsFinal()
	 * @generated
	 */
  void setIsFinal(boolean value);

} // JParameter
