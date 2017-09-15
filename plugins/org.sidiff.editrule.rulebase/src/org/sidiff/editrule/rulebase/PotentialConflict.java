/**
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Potential Conflict</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialConflict#getPotentialConflictKind <em>Potential Conflict Kind</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialConflict#isResolvable <em>Resolvable</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialConflict#getSourceRule <em>Source Rule</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialConflict#getTargetRule <em>Target Rule</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialConflict#getConflictResolution <em>Conflict Resolution</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialConflict()
 * @model abstract="true"
 * @generated
 */
public interface PotentialConflict extends EObject {
	/**
	 * Returns the value of the '<em><b>Potential Conflict Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.sidiff.editrule.rulebase.PotentialConflictKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Potential Conflict Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Potential Conflict Kind</em>' attribute.
	 * @see org.sidiff.editrule.rulebase.PotentialConflictKind
	 * @see #setPotentialConflictKind(PotentialConflictKind)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialConflict_PotentialConflictKind()
	 * @model required="true"
	 * @generated
	 */
	PotentialConflictKind getPotentialConflictKind();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialConflict#getPotentialConflictKind <em>Potential Conflict Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Potential Conflict Kind</em>' attribute.
	 * @see org.sidiff.editrule.rulebase.PotentialConflictKind
	 * @see #getPotentialConflictKind()
	 * @generated
	 */
	void setPotentialConflictKind(PotentialConflictKind value);

	/**
	 * Returns the value of the '<em><b>Resolvable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resolvable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resolvable</em>' attribute.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialConflict_Resolvable()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	boolean isResolvable();

	/**
	 * Returns the value of the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Rule</em>' reference.
	 * @see #setSourceRule(EditRule)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialConflict_SourceRule()
	 * @model required="true"
	 * @generated
	 */
	EditRule getSourceRule();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialConflict#getSourceRule <em>Source Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Rule</em>' reference.
	 * @see #getSourceRule()
	 * @generated
	 */
	void setSourceRule(EditRule value);

	/**
	 * Returns the value of the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Rule</em>' reference.
	 * @see #setTargetRule(EditRule)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialConflict_TargetRule()
	 * @model required="true"
	 * @generated
	 */
	EditRule getTargetRule();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialConflict#getTargetRule <em>Target Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Rule</em>' reference.
	 * @see #getTargetRule()
	 * @generated
	 */
	void setTargetRule(EditRule value);

	/**
	 * Returns the value of the '<em><b>Conflict Resolution</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conflict Resolution</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conflict Resolution</em>' reference.
	 * @see #setConflictResolution(PotentialDependency)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialConflict_ConflictResolution()
	 * @model
	 * @generated
	 */
	PotentialDependency getConflictResolution();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialConflict#getConflictResolution <em>Conflict Resolution</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conflict Resolution</em>' reference.
	 * @see #getConflictResolution()
	 * @generated
	 */
	void setConflictResolution(PotentialDependency value);

} // PotentialConflict
