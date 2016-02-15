/**
 */
package org.silift.difference.namedelementsymboliclink;

import org.silift.difference.symboliclink.SymbolicLinkObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named Element Symbolic Link Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject#getName <em>Name</em>}</li>
 *   <li>{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject#getQualifiedName <em>Qualified Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage#getNamedElementSymbolicLinkObject()
 * @model
 * @generated
 */
public interface NamedElementSymbolicLinkObject extends SymbolicLinkObject {
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
	 * @see org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage#getNamedElementSymbolicLinkObject_Name()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualified Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see #setQualifiedName(String)
	 * @see org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage#getNamedElementSymbolicLinkObject_QualifiedName()
	 * @model
	 * @generated
	 */
	String getQualifiedName();

	/**
	 * Sets the value of the '{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject#getQualifiedName <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
	void setQualifiedName(String value);

} // NamedElementSymbolicLinkObject
