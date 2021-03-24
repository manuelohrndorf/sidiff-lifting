/**
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Potential Dangling Edge Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeDependency#getDeletionNode <em>Deletion Node</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeDependency#getDeletionEdge <em>Deletion Edge</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDanglingEdgeDependency()
 * @model
 * @generated
 */
public interface PotentialDanglingEdgeDependency extends PotentialDependency {
	/**
	 * Returns the value of the '<em><b>Deletion Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deletion Node</em>' reference.
	 * @see #setDeletionNode(Node)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDanglingEdgeDependency_DeletionNode()
	 * @model
	 * @generated
	 */
	Node getDeletionNode();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeDependency#getDeletionNode <em>Deletion Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deletion Node</em>' reference.
	 * @see #getDeletionNode()
	 * @generated
	 */
	void setDeletionNode(Node value);

	/**
	 * Returns the value of the '<em><b>Deletion Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deletion Edge</em>' reference.
	 * @see #setDeletionEdge(Edge)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getPotentialDanglingEdgeDependency_DeletionEdge()
	 * @model
	 * @generated
	 */
	Edge getDeletionEdge();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeDependency#getDeletionEdge <em>Deletion Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deletion Edge</em>' reference.
	 * @see #getDeletionEdge()
	 * @generated
	 */
	void setDeletionEdge(Edge value);

} // PotentialDanglingEdgeDependency
