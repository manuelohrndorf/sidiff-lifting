/**
 */
package simpleWebModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hypertext Layer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link simpleWebModel.HypertextLayer#getPages <em>Pages</em>}</li>
 *   <li>{@link simpleWebModel.HypertextLayer#getStartPage <em>Start Page</em>}</li>
 *   <li>{@link simpleWebModel.HypertextLayer#getDataLayer <em>Data Layer</em>}</li>
 * </ul>
 *
 * @see simpleWebModel.SimpleWebModelPackage#getHypertextLayer()
 * @model
 * @generated
 */
public interface HypertextLayer extends EObject {
	/**
	 * Returns the value of the '<em><b>Pages</b></em>' containment reference list.
	 * The list contents are of type {@link simpleWebModel.Page}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pages</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pages</em>' containment reference list.
	 * @see simpleWebModel.SimpleWebModelPackage#getHypertextLayer_Pages()
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
	 * @see simpleWebModel.SimpleWebModelPackage#getHypertextLayer_StartPage()
	 * @model
	 * @generated
	 */
	StaticPage getStartPage();

	/**
	 * Sets the value of the '{@link simpleWebModel.HypertextLayer#getStartPage <em>Start Page</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Page</em>' reference.
	 * @see #getStartPage()
	 * @generated
	 */
	void setStartPage(StaticPage value);

	/**
	 * Returns the value of the '<em><b>Data Layer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Layer</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Layer</em>' reference.
	 * @see #setDataLayer(DataLayer)
	 * @see simpleWebModel.SimpleWebModelPackage#getHypertextLayer_DataLayer()
	 * @model
	 * @generated
	 */
	DataLayer getDataLayer();

	/**
	 * Sets the value of the '{@link simpleWebModel.HypertextLayer#getDataLayer <em>Data Layer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Layer</em>' reference.
	 * @see #getDataLayer()
	 * @generated
	 */
	void setDataLayer(DataLayer value);

} // HypertextLayer
