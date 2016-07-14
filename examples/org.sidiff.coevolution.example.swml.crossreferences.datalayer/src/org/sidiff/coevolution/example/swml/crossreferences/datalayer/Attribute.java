/**
 */
package org.sidiff.coevolution.example.swml.crossreferences.datalayer;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends EObject {
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
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage#getAttribute_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.SimpleType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.SimpleType
	 * @see #setType(SimpleType)
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage#getAttribute_Type()
	 * @model
	 * @generated
	 */
	SimpleType getType();

	/**
	 * Sets the value of the '{@link org.sidiff.coevolution.example.swml.crossreferences.datalayer.Attribute#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.datalayer.SimpleType
	 * @see #getType()
	 * @generated
	 */
	void setType(SimpleType value);

} // Attribute
