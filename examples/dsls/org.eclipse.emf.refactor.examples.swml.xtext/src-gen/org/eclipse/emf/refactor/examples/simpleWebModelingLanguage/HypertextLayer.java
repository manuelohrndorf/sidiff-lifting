/**
 */
package org.eclipse.emf.refactor.examples.simpleWebModelingLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hypertext Layer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.HypertextLayer#getPages <em>Pages</em>}</li>
 *   <li>{@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.HypertextLayer#getStartPage <em>Start Page</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage#getHypertextLayer()
 * @model
 * @generated
 */
public interface HypertextLayer extends EObject
{
  /**
   * Returns the value of the '<em><b>Pages</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.Page}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pages</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Pages</em>' containment reference list.
   * @see org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage#getHypertextLayer_Pages()
   * @model containment="true"
   * @generated
   */
  EList<Page> getPages();

  /**
   * Returns the value of the '<em><b>Start Page</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Start Page</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Start Page</em>' reference.
   * @see #setStartPage(StaticPage)
   * @see org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage#getHypertextLayer_StartPage()
   * @model
   * @generated
   */
  StaticPage getStartPage();

  /**
   * Sets the value of the '{@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.HypertextLayer#getStartPage <em>Start Page</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Start Page</em>' reference.
   * @see #getStartPage()
   * @generated
   */
  void setStartPage(StaticPage value);

} // HypertextLayer
