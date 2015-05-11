/**
 */
package org.sidiff.difference.symmetricprofiled;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Applied Stereotype</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype#getStereoType <em>Stereo Type</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype#getBaseObject <em>Base Object</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getAppliedStereotype()
 * @model
 * @generated
 */
public interface AppliedStereotype extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getAppliedStereotype_Name()
	 * @model default="" transient="true" changeable="false" derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Base Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Object</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Object</em>' reference.
	 * @see #setBaseObject(EObject)
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getAppliedStereotype_BaseObject()
	 * @model required="true"
	 * @generated
	 */
	EObject getBaseObject();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype#getBaseObject <em>Base Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Object</em>' reference.
	 * @see #getBaseObject()
	 * @generated
	 */
	void setBaseObject(EObject value);

	/**
	 * Returns the value of the '<em><b>Stereo Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stereo Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stereo Type</em>' reference.
	 * @see #setStereoType(EObject)
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getAppliedStereotype_StereoType()
	 * @model required="true"
	 * @generated
	 */
	EObject getStereoType();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype#getStereoType <em>Stereo Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stereo Type</em>' reference.
	 * @see #getStereoType()
	 * @generated
	 */
	void setStereoType(EObject value);

} // AppliedStereotype
