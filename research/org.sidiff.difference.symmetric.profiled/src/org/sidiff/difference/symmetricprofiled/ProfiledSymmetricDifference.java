/**
 */
package org.sidiff.difference.symmetricprofiled;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Profiled Symmetric Difference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference#getProfiledSemanticChangeSets <em>Profiled Semantic Change Sets</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference#getSymmetricDifference <em>Symmetric Difference</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSymmetricDifference()
 * @model
 * @generated
 */
public interface ProfiledSymmetricDifference extends EObject {
	/**
	 * Returns the value of the '<em><b>Profiled Semantic Change Sets</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Profiled Semantic Change Sets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Profiled Semantic Change Sets</em>' containment reference list.
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSymmetricDifference_ProfiledSemanticChangeSets()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProfiledSemanticChangeSet> getProfiledSemanticChangeSets();

	/**
	 * Returns the value of the '<em><b>Symmetric Difference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symmetric Difference</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symmetric Difference</em>' containment reference.
	 * @see #setSymmetricDifference(SymmetricDifference)
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSymmetricDifference_SymmetricDifference()
	 * @model containment="true" required="true"
	 * @generated
	 */
	SymmetricDifference getSymmetricDifference();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference#getSymmetricDifference <em>Symmetric Difference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symmetric Difference</em>' containment reference.
	 * @see #getSymmetricDifference()
	 * @generated
	 */
	void setSymmetricDifference(SymmetricDifference value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void derive(SymmetricDifference symmetricDifference);

} // ProfiledSymmetricDifference
