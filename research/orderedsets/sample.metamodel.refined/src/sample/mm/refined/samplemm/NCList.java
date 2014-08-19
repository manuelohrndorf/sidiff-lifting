/**
 */
package sample.mm.refined.samplemm;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>NC List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sample.mm.refined.samplemm.NCList#getItems <em>Items</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.NCList#getName <em>Name</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.NCList#getItem_links <em>Item links</em>}</li>
 * </ul>
 * </p>
 *
 * @see sample.mm.refined.samplemm.SamplemmPackage#getNCList()
 * @model
 * @generated
 */
public interface NCList extends EObject {
	/**
	 * Returns the value of the '<em><b>Items</b></em>' reference list.
	 * The list contents are of type {@link sample.mm.refined.samplemm.Item}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Items</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' reference list.
	 * @see sample.mm.refined.samplemm.SamplemmPackage#getNCList_Items()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Item> getItems();

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
	 * @see sample.mm.refined.samplemm.SamplemmPackage#getNCList_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link sample.mm.refined.samplemm.NCList#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Item links</b></em>' containment reference list.
	 * The list contents are of type {@link sample.mm.refined.samplemm.Item_Link}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Item links</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Item links</em>' containment reference list.
	 * @see sample.mm.refined.samplemm.SamplemmPackage#getNCList_Item_links()
	 * @model containment="true"
	 * @generated
	 */
	EList<Item_Link> getItem_links();

} // NCList
