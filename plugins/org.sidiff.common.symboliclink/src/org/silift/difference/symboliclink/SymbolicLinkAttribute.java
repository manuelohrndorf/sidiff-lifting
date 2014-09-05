/**
 */
package org.silift.difference.symboliclink;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbolic Link Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkAttribute#getValue <em>Value</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.SymbolicLinkAttribute#getKind <em>Kind</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkAttribute()
 * @model
 * @generated
 */
public interface SymbolicLinkAttribute extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkAttribute_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.silift.difference.symboliclink.SymbolicLinkAttribute#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' reference.
	 * @see #setKind(EAttribute)
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#getSymbolicLinkAttribute_Kind()
	 * @model required="true"
	 * @generated
	 */
	EAttribute getKind();

	/**
	 * Sets the value of the '{@link org.silift.difference.symboliclink.SymbolicLinkAttribute#getKind <em>Kind</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' reference.
	 * @see #getKind()
	 * @generated
	 */
	void setKind(EAttribute value);

} // SymbolicLinkAttribute
