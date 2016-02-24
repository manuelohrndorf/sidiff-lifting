/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Doc</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.JavaDoc#getText <em>Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.javaast.model.JavaModelPackage#getJavaDoc()
 * @model
 * @generated
 */
public interface JavaDoc extends JIdentifiableElement
{
  /**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Text</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see org.sidiff.javaast.model.JavaModelPackage#getJavaDoc_Text()
	 * @model required="true" ordered="false"
	 * @generated
	 */
  String getText();

  /**
	 * Sets the value of the '{@link org.sidiff.javaast.model.JavaDoc#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
  void setText(String value);

} // JavaDoc
