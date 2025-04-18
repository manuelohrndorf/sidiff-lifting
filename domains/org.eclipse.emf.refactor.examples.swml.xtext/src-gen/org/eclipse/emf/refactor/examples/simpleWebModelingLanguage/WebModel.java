/**
 * generated by Xtext 2.14.0-SNAPSHOT
 */
package org.eclipse.emf.refactor.examples.simpleWebModelingLanguage;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Web Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.WebModel#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.WebModel#getDataLayer <em>Data Layer</em>}</li>
 *   <li>{@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.WebModel#getHypertextLayer <em>Hypertext Layer</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage#getWebModel()
 * @model
 * @generated
 */
public interface WebModel extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage#getWebModel_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.WebModel#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Data Layer</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Data Layer</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Data Layer</em>' containment reference.
   * @see #setDataLayer(DataLayer)
   * @see org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage#getWebModel_DataLayer()
   * @model containment="true"
   * @generated
   */
  DataLayer getDataLayer();

  /**
   * Sets the value of the '{@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.WebModel#getDataLayer <em>Data Layer</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Data Layer</em>' containment reference.
   * @see #getDataLayer()
   * @generated
   */
  void setDataLayer(DataLayer value);

  /**
   * Returns the value of the '<em><b>Hypertext Layer</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Hypertext Layer</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Hypertext Layer</em>' containment reference.
   * @see #setHypertextLayer(HypertextLayer)
   * @see org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage#getWebModel_HypertextLayer()
   * @model containment="true"
   * @generated
   */
  HypertextLayer getHypertextLayer();

  /**
   * Sets the value of the '{@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.WebModel#getHypertextLayer <em>Hypertext Layer</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Hypertext Layer</em>' containment reference.
   * @see #getHypertextLayer()
   * @generated
   */
  void setHypertextLayer(HypertextLayer value);

} // WebModel
