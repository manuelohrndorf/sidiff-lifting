/**
 */
package org.silift.difference.namedelementsymboliclink;

import org.silift.difference.symboliclink.SymbolicLink;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named Element Symbolic Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink#getName <em>Name</em>}</li>
 *   <li>{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink#getQualifiedName <em>Qualified Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage#getNamedElementSymbolicLink()
 * @model
 * @generated
 */
public interface NamedElementSymbolicLink extends SymbolicLink {
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
	 * @see org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage#getNamedElementSymbolicLink_Name()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink#getName <em>Name</em>}' attribute.
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
	 * @see org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage#getNamedElementSymbolicLink_QualifiedName()
	 * @model
	 * @generated
	 */
	String getQualifiedName();

	/**
	 * Sets the value of the '{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink#getQualifiedName <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
	void setQualifiedName(String value);

} // NamedElementSymbolicLink
