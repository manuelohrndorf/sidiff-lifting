/**
 */
package sample.mm.samplemm;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sample Metamodel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sample.mm.samplemm.SampleMetamodel#getItems <em>Items</em>}</li>
 *   <li>{@link sample.mm.samplemm.SampleMetamodel#getNcLists <em>Nc Lists</em>}</li>
 *   <li>{@link sample.mm.samplemm.SampleMetamodel#getCLists <em>CLists</em>}</li>
 * </ul>
 * </p>
 *
 * @see sample.mm.samplemm.SamplemmPackage#getSampleMetamodel()
 * @model
 * @generated
 */
public interface SampleMetamodel extends EObject {
	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference list.
	 * The list contents are of type {@link sample.mm.samplemm.Item}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' containment reference list.
	 * @see sample.mm.samplemm.SamplemmPackage#getSampleMetamodel_Items()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Item> getItems();

	/**
	 * Returns the value of the '<em><b>Nc Lists</b></em>' containment reference list.
	 * The list contents are of type {@link sample.mm.samplemm.NCList}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nc Lists</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nc Lists</em>' containment reference list.
	 * @see sample.mm.samplemm.SamplemmPackage#getSampleMetamodel_NcLists()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<NCList> getNcLists();

	/**
	 * Returns the value of the '<em><b>CLists</b></em>' containment reference list.
	 * The list contents are of type {@link sample.mm.samplemm.CList}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>CLists</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>CLists</em>' containment reference list.
	 * @see sample.mm.samplemm.SamplemmPackage#getSampleMetamodel_CLists()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<CList> getCLists();

} // SampleMetamodel
