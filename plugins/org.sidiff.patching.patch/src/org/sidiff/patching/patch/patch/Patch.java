/**
 */
package org.sidiff.patching.patch.patch;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.rulebase.EditRule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Patch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.patching.patch.patch.Patch#getAsymmetricDifference <em>Asymmetric Difference</em>}</li>
 *   <li>{@link org.sidiff.patching.patch.patch.Patch#getSettings <em>Settings</em>}</li>
 *   <li>{@link org.sidiff.patching.patch.patch.Patch#getEditRules <em>Edit Rules</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.patching.patch.patch.PatchPackage#getPatch()
 * @model
 * @generated
 */
public interface Patch extends EObject {
	/**
	 * Returns the value of the '<em><b>Asymmetric Difference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Asymmetric Difference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Asymmetric Difference</em>' reference.
	 * @see #setAsymmetricDifference(AsymmetricDifference)
	 * @see org.sidiff.patching.patch.patch.PatchPackage#getPatch_AsymmetricDifference()
	 * @model
	 * @generated
	 */
	AsymmetricDifference getAsymmetricDifference();

	/**
	 * Sets the value of the '{@link org.sidiff.patching.patch.patch.Patch#getAsymmetricDifference <em>Asymmetric Difference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Asymmetric Difference</em>' reference.
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	void setAsymmetricDifference(AsymmetricDifference value);

	/**
	 * Returns the value of the '<em><b>Settings</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Settings</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Settings</em>' map.
	 * @see org.sidiff.patching.patch.patch.PatchPackage#getPatch_Settings()
	 * @model mapType="org.sidiff.patching.patch.patch.Setting<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getSettings();

	/**
	 * Returns the value of the '<em><b>Edit Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.rulebase.EditRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Rules</em>' containment reference list.
	 * @see org.sidiff.patching.patch.patch.PatchPackage#getPatch_EditRules()
	 * @model containment="true"
	 * @generated
	 */
	EList<EditRule> getEditRules();

} // Patch
