/**
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.henshin.model.Attribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Potential Attribute Conflict</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getAttributes <em>Attributes</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialAttributeConflict()
 * @model
 * @generated
 */
public interface PotentialAttributeConflict extends PotentialConflict {
	/**
	 * Returns the value of the '<em><b>Rule Base</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialAttributeConflicts <em>Potential Attribute Conflicts</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Base</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Base</em>' container reference.
	 * @see #setRuleBase(RuleBase)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialAttributeConflict_RuleBase()
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialAttributeConflicts
	 * @model opposite="potentialAttributeConflicts" transient="false"
	 * @generated
	 */
	RuleBase getRuleBase();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getRuleBase <em>Rule Base</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Base</em>' container reference.
	 * @see #getRuleBase()
	 * @generated
	 */
	void setRuleBase(RuleBase value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Attribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialAttributeConflict_Attributes()
	 * @model lower="2" upper="2"
	 * @generated
	 */
	EList<Attribute> getAttributes();

} // PotentialAttributeConflict
