/**
 */
package org.sidiff.difference.asymmetric;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.AttributeDependency#getObject <em>Object</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.AttributeDependency#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAttributeDependency()
 * @model
 * @generated
 */
public interface AttributeDependency extends Dependency {
	/**
	 * Returns the value of the '<em><b>Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object</em>' reference.
	 * @see #setObject(EObject)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAttributeDependency_Object()
	 * @model
	 * @generated
	 */
	EObject getObject();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.AttributeDependency#getObject <em>Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object</em>' reference.
	 * @see #getObject()
	 * @generated
	 */
	void setObject(EObject value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EAttribute)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAttributeDependency_Type()
	 * @model
	 * @generated
	 */
	EAttribute getType();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.AttributeDependency#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EAttribute value);

} // AttributeDependency
