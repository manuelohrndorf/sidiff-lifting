/**
 */
package org.silift.difference.uuidsymboliclink;

import org.silift.difference.symboliclink.SymbolicLink;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>UUID Symbolic Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.silift.difference.uuidsymboliclink.UUIDSymbolicLink#getUuid <em>Uuid</em>}</li>
 *   <li>{@link org.silift.difference.uuidsymboliclink.UUIDSymbolicLink#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.silift.difference.uuidsymboliclink.UuidsymboliclinkPackage#getUUIDSymbolicLink()
 * @model
 * @generated
 */
public interface UUIDSymbolicLink extends SymbolicLink {
	/**
	 * Returns the value of the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uuid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uuid</em>' attribute.
	 * @see #setUuid(String)
	 * @see org.silift.difference.uuidsymboliclink.UuidsymboliclinkPackage#getUUIDSymbolicLink_Uuid()
	 * @model required="true"
	 * @generated
	 */
	String getUuid();

	/**
	 * Sets the value of the '{@link org.silift.difference.uuidsymboliclink.UUIDSymbolicLink#getUuid <em>Uuid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uuid</em>' attribute.
	 * @see #getUuid()
	 * @generated
	 */
	void setUuid(String value);

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
	 * @see org.silift.difference.uuidsymboliclink.UuidsymboliclinkPackage#getUUIDSymbolicLink_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.silift.difference.uuidsymboliclink.UUIDSymbolicLink#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // UUIDSymbolicLink
