/**
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.henshin.model.Edge;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Potential Edge Conflict</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialEdgeConflict#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialEdgeConflict#getEdges <em>Edges</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialEdgeConflict()
 * @model
 * @generated
 */
public interface PotentialEdgeConflict extends PotentialConflict {
	/**
	 * Returns the value of the '<em><b>Rule Base</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialEdgeConflicts <em>Potential Edge Conflicts</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Base</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Base</em>' container reference.
	 * @see #setRuleBase(RuleBase)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialEdgeConflict_RuleBase()
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialEdgeConflicts
	 * @model opposite="potentialEdgeConflicts" transient="false"
	 * @generated
	 */
	RuleBase getRuleBase();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialEdgeConflict#getRuleBase <em>Rule Base</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Base</em>' container reference.
	 * @see #getRuleBase()
	 * @generated
	 */
	void setRuleBase(RuleBase value);

	/**
	 * Returns the value of the '<em><b>Edges</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Edge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edges</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edges</em>' reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialEdgeConflict_Edges()
	 * @model lower="2" upper="2"
	 * @generated
	 */
	EList<Edge> getEdges();

} // PotentialEdgeConflict
