/**
 * generated by Xtext 2.14.0-SNAPSHOT
 */
package org.eclipse.emf.refactor.examples.simpleWebModelingLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Layer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.DataLayer#getEntities <em>Entities</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage#getDataLayer()
 * @model
 * @generated
 */
public interface DataLayer extends EObject
{
  /**
   * Returns the value of the '<em><b>Entities</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.Entity}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Entities</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Entities</em>' containment reference list.
   * @see org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage#getDataLayer_Entities()
   * @model containment="true"
   * @generated
   */
  EList<Entity> getEntities();

} // DataLayer
