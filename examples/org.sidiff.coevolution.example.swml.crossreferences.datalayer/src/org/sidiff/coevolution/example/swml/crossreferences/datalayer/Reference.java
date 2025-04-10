/**
 */
package org.sidiff.coevolution.example.swml.crossreferences.datalayer;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage#getReference()
 * @model
 * @generated
 */
public interface Reference extends EObject {
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
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage#getReference_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Entity)
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage#getReference_Type()
	 * @model
	 * @generated
	 */
	Entity getType();

	/**
	 * Sets the value of the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Reference#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Entity value);

} // Reference
