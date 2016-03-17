/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.henshin.model.Edge;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Potential Edge Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency#getSourceEdge <em>Source Edge</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency#getTargetEdge <em>Target Edge</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialEdgeDependency()
 * @model
 * @generated
 */
public interface PotentialEdgeDependency extends PotentialDependency {
	/**
	 * Returns the value of the '<em><b>Rule Base</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialEdgeDependencies <em>Potential Edge Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Base</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Base</em>' container reference.
	 * @see #setRuleBase(RuleBase)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialEdgeDependency_RuleBase()
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialEdgeDependencies
	 * @model opposite="potentialEdgeDependencies" transient="false"
	 * @generated
	 */
	RuleBase getRuleBase();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency#getRuleBase <em>Rule Base</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Base</em>' container reference.
	 * @see #getRuleBase()
	 * @generated
	 */
	void setRuleBase(RuleBase value);

	/**
	 * Returns the value of the '<em><b>Source Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Edge</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Edge</em>' reference.
	 * @see #setSourceEdge(Edge)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialEdgeDependency_SourceEdge()
	 * @model
	 * @generated
	 */
	Edge getSourceEdge();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency#getSourceEdge <em>Source Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Edge</em>' reference.
	 * @see #getSourceEdge()
	 * @generated
	 */
	void setSourceEdge(Edge value);

	/**
	 * Returns the value of the '<em><b>Target Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Edge</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Edge</em>' reference.
	 * @see #setTargetEdge(Edge)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialEdgeDependency_TargetEdge()
	 * @model
	 * @generated
	 */
	Edge getTargetEdge();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency#getTargetEdge <em>Target Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Edge</em>' reference.
	 * @see #getTargetEdge()
	 * @generated
	 */
	void setTargetEdge(Edge value);

} // PotentialEdgeDependency
