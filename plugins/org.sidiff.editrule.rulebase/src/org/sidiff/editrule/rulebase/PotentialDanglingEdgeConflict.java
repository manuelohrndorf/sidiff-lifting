/**
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Potential Dangling Edge Conflict</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getDeletionNode <em>Deletion Node</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getCreationEdge <em>Creation Edge</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDanglingEdgeConflict()
 * @model
 * @generated
 */
public interface PotentialDanglingEdgeConflict extends PotentialConflict {
	/**
	 * Returns the value of the '<em><b>Rule Base</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialDanglingEdgeConflicts <em>Potential Dangling Edge Conflicts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Base</em>' container reference.
	 * @see #setRuleBase(RuleBase)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDanglingEdgeConflict_RuleBase()
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialDanglingEdgeConflicts
	 * @model opposite="potentialDanglingEdgeConflicts" transient="false"
	 * @generated
	 */
	RuleBase getRuleBase();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getRuleBase <em>Rule Base</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Base</em>' container reference.
	 * @see #getRuleBase()
	 * @generated
	 */
	void setRuleBase(RuleBase value);

	/**
	 * Returns the value of the '<em><b>Deletion Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deletion Node</em>' reference.
	 * @see #setDeletionNode(Node)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDanglingEdgeConflict_DeletionNode()
	 * @model
	 * @generated
	 */
	Node getDeletionNode();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getDeletionNode <em>Deletion Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deletion Node</em>' reference.
	 * @see #getDeletionNode()
	 * @generated
	 */
	void setDeletionNode(Node value);

	/**
	 * Returns the value of the '<em><b>Creation Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creation Edge</em>' reference.
	 * @see #setCreationEdge(Edge)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDanglingEdgeConflict_CreationEdge()
	 * @model
	 * @generated
	 */
	Edge getCreationEdge();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getCreationEdge <em>Creation Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Edge</em>' reference.
	 * @see #getCreationEdge()
	 * @generated
	 */
	void setCreationEdge(Edge value);

} // PotentialDanglingEdgeConflict
