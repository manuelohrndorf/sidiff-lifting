/**
 */
package org.sidiff.coevolution.example.swml.crossreferences.datalayer;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity#getReferences <em>References</em>}</li>
 *   <li>{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity#getAttributes <em>Attributes</em>}</li>
 * </ul>
 *
 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage#getEntity()
 * @model
 * @generated
 */
public interface Entity extends EObject {
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
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage#getEntity_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Entity#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>References</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>References</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>References</em>' containment reference list.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage#getEntity_References()
	 * @model containment="true"
	 * @generated
	 */
	EList<Reference> getReferences();

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage#getEntity_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Attribute> getAttributes();

} // Entity
