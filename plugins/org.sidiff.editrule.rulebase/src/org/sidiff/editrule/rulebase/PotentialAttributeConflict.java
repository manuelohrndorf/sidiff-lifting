/**
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;

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
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getSourceAttribute <em>Source Attribute</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getTargetAttribute <em>Target Attribute</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getSourceNode <em>Source Node</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getTargetNode <em>Target Node</em>}</li>
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
	 * Returns the value of the '<em><b>Source Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Attribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Attribute</em>' reference.
	 * @see #setSourceAttribute(Attribute)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialAttributeConflict_SourceAttribute()
	 * @model required="true"
	 * @generated
	 */
	Attribute getSourceAttribute();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getSourceAttribute <em>Source Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Attribute</em>' reference.
	 * @see #getSourceAttribute()
	 * @generated
	 */
	void setSourceAttribute(Attribute value);

	/**
	 * Returns the value of the '<em><b>Target Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Attribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Attribute</em>' reference.
	 * @see #setTargetAttribute(Attribute)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialAttributeConflict_TargetAttribute()
	 * @model required="true"
	 * @generated
	 */
	Attribute getTargetAttribute();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getTargetAttribute <em>Target Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Attribute</em>' reference.
	 * @see #getTargetAttribute()
	 * @generated
	 */
	void setTargetAttribute(Attribute value);

	/**
	 * Returns the value of the '<em><b>Source Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Node</em>' reference.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialAttributeConflict_SourceNode()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Node getSourceNode();

	/**
	 * Returns the value of the '<em><b>Target Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Node</em>' reference.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialAttributeConflict_TargetNode()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Node getTargetNode();

} // PotentialAttributeConflict
