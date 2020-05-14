/**
 */
package org.silift.difference.symboliclink;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbolic Link Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkObject#getReliability <em>Reliability</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkObject#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkObject#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkObject#getLinkAttributes <em>Link Attributes</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkObject#getType <em>Type</em>}</li>
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

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link org.silift.difference.symboliclink.SymbolicLinkReference}.
	 * It is bidirectional and its opposite is '{@link org.silift.difference.symboliclink.SymbolicLinkReference#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkObject_Outgoing()
	 * @see org.silift.difference.symboliclink.SymbolicLinkReference#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<SymbolicLinkReference> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link org.silift.difference.symboliclink.SymbolicLinkReference}.
	 * It is bidirectional and its opposite is '{@link org.silift.difference.symboliclink.SymbolicLinkReference#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkObject_Incoming()
	 * @see org.silift.difference.symboliclink.SymbolicLinkReference#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<SymbolicLinkReference> getIncoming();

	/**
	 * Returns the value of the '<em><b>Link Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.silift.difference.symboliclink.SymbolicLinkAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link Attributes</em>' containment reference list.
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkObject_LinkAttributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<SymbolicLinkAttribute> getLinkAttributes();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EObject)
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkObject_Type()
	 * @model required="true"
	 * @generated
	 */
	EObject getType();

	/**
	 * Sets the value of the '{@link org.silift.difference.symboliclink.SymbolicLinkObject#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EObject value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model many="false"
	 * @generated NOT
	 */
	EList<SymbolicLinkReference> getOutgoings(EReference type);

	@Override
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	int hashCode();

	@Override
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean equals(Object o);

} // SymbolicLinkObject
