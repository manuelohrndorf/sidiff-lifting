/**
 */
package org.silift.difference.symboliclink;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbolic Link Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkReference#getType <em>Type</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkReference#getSource <em>Source</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkReference#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkReference()
 * @model
 * @generated
 */
public interface SymbolicLinkReference extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EReference)
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkReference_Type()
	 * @model required="true"
	 * @generated
	 */
	EReference getType();

	/**
	 * Sets the value of the '{@link org.silift.difference.symboliclink.SymbolicLinkReference#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EReference value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.silift.difference.symboliclink.SymbolicLinkObject#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(SymbolicLinkObject)
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkReference_Source()
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject#getOutgoing
	 * @model opposite="outgoing" required="true"
	 * @generated
	 */
	SymbolicLinkObject getSource();

	/**
	 * Sets the value of the '{@link org.silift.difference.symboliclink.SymbolicLinkReference#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(SymbolicLinkObject value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.silift.difference.symboliclink.SymbolicLinkObject#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(SymbolicLinkObject)
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkReference_Target()
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject#getIncoming
	 * @model opposite="incoming" required="true"
	 * @generated
	 */
	SymbolicLinkObject getTarget();

	/**
	 * Sets the value of the '{@link org.silift.difference.symboliclink.SymbolicLinkReference#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(SymbolicLinkObject value);

} // SymbolicLinkReference
