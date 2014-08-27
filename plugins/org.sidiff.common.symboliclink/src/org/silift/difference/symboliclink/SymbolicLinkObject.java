/**
 */
package org.silift.difference.symboliclink;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbolic Link Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkObject#getReliability <em>Reliability</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkObject()
 * @model abstract="true"
 * @generated
 */
public interface SymbolicLinkObject extends EObject {
	/**
	 * Returns the value of the '<em><b>Reliability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reliability</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reliability</em>' attribute.
	 * @see #setReliability(float)
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkObject_Reliability()
	 * @model
	 * @generated
	 */
	float getReliability();

	/**
	 * Sets the value of the '{@link org.silift.difference.symboliclink.SymbolicLinkObject#getReliability <em>Reliability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reliability</em>' attribute.
	 * @see #getReliability()
	 * @generated
	 */
	void setReliability(float value);

} // SymbolicLinkObject
