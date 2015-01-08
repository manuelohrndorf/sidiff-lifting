/**
 */
package org.sidiff.difference.symmetricprofiled;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Profiled SD</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.ProfiledSD#getProfiledscss <em>Profiledscss</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.ProfiledSD#getSd <em>Sd</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.ProfiledSD#getUnprofiledscss <em>Unprofiledscss</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSD()
 * @model
 * @generated
 */
public interface ProfiledSD extends EObject {
	/**
	 * Returns the value of the '<em><b>Profiledscss</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.symmetricprofiled.ProfiledSCS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Profiledscss</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Profiledscss</em>' containment reference list.
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSD_Profiledscss()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProfiledSCS> getProfiledscss();

	/**
	 * Returns the value of the '<em><b>Sd</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sd</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sd</em>' reference.
	 * @see #setSd(SymmetricDifference)
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSD_Sd()
	 * @model required="true"
	 * @generated
	 */
	SymmetricDifference getSd();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.symmetricprofiled.ProfiledSD#getSd <em>Sd</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sd</em>' reference.
	 * @see #getSd()
	 * @generated
	 */
	void setSd(SymmetricDifference value);

	/**
	 * Returns the value of the '<em><b>Unprofiledscss</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.difference.symmetric.SemanticChangeSet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unprofiledscss</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unprofiledscss</em>' reference list.
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSD_Unprofiledscss()
	 * @model
	 * @generated
	 */
	EList<SemanticChangeSet> getUnprofiledscss();

} // ProfiledSD
