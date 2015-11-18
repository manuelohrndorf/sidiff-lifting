/**
 */
package org.silift.difference.symboliclink;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Symbolic Link Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.silift.difference.symboliclink.ExternalSymbolicLinkObject#getEObject <em>EObject</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.ExternalSymbolicLinkObject#getFrom <em>From</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getExternalSymbolicLinkObject()
 * @model
 * @generated
 */
public interface ExternalSymbolicLinkObject extends SymbolicLinkObject {
	/**
	 * Returns the value of the '<em><b>EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EObject</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EObject</em>' reference.
	 * @see #setEObject(EObject)
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getExternalSymbolicLinkObject_EObject()
	 * @model required="true"
	 * @generated
	 */
	EObject getEObject();

	/**
	 * Sets the value of the '{@link org.silift.difference.symboliclink.ExternalSymbolicLinkObject#getEObject <em>EObject</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EObject</em>' reference.
	 * @see #getEObject()
	 * @generated
	 */
	void setEObject(EObject value);

	/**
	 * Returns the value of the '<em><b>From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' attribute.
	 * @see #setFrom(String)
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getExternalSymbolicLinkObject_From()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getFrom();

	/**
	 * Sets the value of the '{@link org.silift.difference.symboliclink.ExternalSymbolicLinkObject#getFrom <em>From</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' attribute.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(String value);

} // ExternalSymbolicLinkObject
