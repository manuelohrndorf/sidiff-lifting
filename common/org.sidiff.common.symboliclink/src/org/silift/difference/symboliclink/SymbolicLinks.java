/**
 */
package org.silift.difference.symboliclink;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbolic Links</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinks#getLinkObjects <em>Link Objects</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinks#getDocType <em>Doc Type</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinks#getLinkReferences <em>Link References</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinks()
 * @model
 * @generated
 */
public interface SymbolicLinks extends EObject {
	/**
	 * Returns the value of the '<em><b>Link Objects</b></em>' containment reference list.
	 * The list contents are of type {@link org.silift.difference.symboliclink.SymbolicLinkObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link Objects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link Objects</em>' containment reference list.
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinks_LinkObjects()
	 * @model containment="true"
	 * @generated
	 */
	EList<SymbolicLinkObject> getLinkObjects();

	/**
	 * Returns the value of the '<em><b>Doc Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Doc Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Doc Type</em>' attribute.
	 * @see #setDocType(String)
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinks_DocType()
	 * @model
	 * @generated
	 */
	String getDocType();

	/**
	 * Sets the value of the '{@link org.silift.difference.symboliclink.SymbolicLinks#getDocType <em>Doc Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Doc Type</em>' attribute.
	 * @see #getDocType()
	 * @generated
	 */
	void setDocType(String value);

	/**
	 * Returns the value of the '<em><b>Link References</b></em>' containment reference list.
	 * The list contents are of type {@link org.silift.difference.symboliclink.SymbolicLinkReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link References</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link References</em>' containment reference list.
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinks_LinkReferences()
	 * @model containment="true"
	 * @generated
	 */
	EList<SymbolicLinkReference> getLinkReferences();

} // SymbolicLinks
