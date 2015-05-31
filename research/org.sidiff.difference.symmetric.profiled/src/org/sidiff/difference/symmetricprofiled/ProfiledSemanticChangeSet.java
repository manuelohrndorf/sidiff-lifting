/**
 */
package org.sidiff.difference.symmetricprofiled;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.sidiff.difference.symmetric.SemanticChangeSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Profiled Semantic Change Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#getSemanticChangeSet <em>Semantic Change Set</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#getAppliedStereotypes <em>Applied Stereotypes</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSemanticChangeSet()
 * @model
 * @generated
 */
public interface ProfiledSemanticChangeSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Semantic Change Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Semantic Change Set</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Semantic Change Set</em>' reference.
	 * @see #setSemanticChangeSet(SemanticChangeSet)
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSemanticChangeSet_SemanticChangeSet()
	 * @model required="true"
	 * @generated
	 */
	SemanticChangeSet getSemanticChangeSet();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet#getSemanticChangeSet <em>Semantic Change Set</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Semantic Change Set</em>' reference.
	 * @see #getSemanticChangeSet()
	 * @generated
	 */
	void setSemanticChangeSet(SemanticChangeSet value);

	/**
	 * Returns the value of the '<em><b>Applied Stereotypes</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.symmetricprofiled.AppliedStereotype}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Applied Stereotypes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Applied Stereotypes</em>' containment reference list.
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSemanticChangeSet_AppliedStereotypes()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<AppliedStereotype> getAppliedStereotypes();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#getProfiledSemanticChangeSet_Name()
	 * @model transient="true" changeable="false" derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addAppliedStereotype(AppliedStereotype appliedStereotype);

} // ProfiledSemanticChangeSet
