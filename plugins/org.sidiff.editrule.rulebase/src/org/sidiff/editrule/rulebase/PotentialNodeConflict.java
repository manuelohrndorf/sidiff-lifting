/**
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Potential Node Conflict</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialNodeConflict#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialNodeConflict#getSourceNode <em>Source Node</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialNodeConflict#getTargetNode <em>Target Node</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialNodeConflict()
 * @model
 * @generated
 */
public interface PotentialNodeConflict extends PotentialConflict {
	/**
	 * Returns the value of the '<em><b>Rule Base</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialNodeConflicts <em>Potential Node Conflicts</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Base</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Base</em>' container reference.
	 * @see #setRuleBase(RuleBase)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialNodeConflict_RuleBase()
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialNodeConflicts
	 * @model opposite="potentialNodeConflicts" transient="false"
	 * @generated
	 */
	RuleBase getRuleBase();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialNodeConflict#getRuleBase <em>Rule Base</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Base</em>' container reference.
	 * @see #getRuleBase()
	 * @generated
	 */
	void setRuleBase(RuleBase value);

	/**
	 * Returns the value of the '<em><b>Source Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Node</em>' reference.
	 * @see #setSourceNode(Node)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialNodeConflict_SourceNode()
	 * @model
	 * @generated
	 */
	Node getSourceNode();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialNodeConflict#getSourceNode <em>Source Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Node</em>' reference.
	 * @see #getSourceNode()
	 * @generated
	 */
	void setSourceNode(Node value);

	/**
	 * Returns the value of the '<em><b>Target Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Node</em>' reference.
	 * @see #setTargetNode(Node)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialNodeConflict_TargetNode()
	 * @model
	 * @generated
	 */
	Node getTargetNode();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialNodeConflict#getTargetNode <em>Target Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Node</em>' reference.
	 * @see #getTargetNode()
	 * @generated
	 */
	void setTargetNode(Node value);

} // PotentialNodeConflict
