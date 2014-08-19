/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.Trace#getEditRuleTrace <em>Edit Rule Trace</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.Trace#getRecognitionRuleTrace <em>Recognition Rule Trace</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.rulebase.RulebasePackage#getTrace()
 * @model
 * @generated
 */
public interface Trace extends EObject {
	/**
	 * Returns the value of the '<em><b>Edit Rule Trace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Rule Trace</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Rule Trace</em>' reference.
	 * @see #setEditRuleTrace(Node)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getTrace_EditRuleTrace()
	 * @model
	 * @generated
	 */
	Node getEditRuleTrace();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.Trace#getEditRuleTrace <em>Edit Rule Trace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Rule Trace</em>' reference.
	 * @see #getEditRuleTrace()
	 * @generated
	 */
	void setEditRuleTrace(Node value);

	/**
	 * Returns the value of the '<em><b>Recognition Rule Trace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recognition Rule Trace</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recognition Rule Trace</em>' reference.
	 * @see #setRecognitionRuleTrace(Node)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getTrace_RecognitionRuleTrace()
	 * @model
	 * @generated
	 */
	Node getRecognitionRuleTrace();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.Trace#getRecognitionRuleTrace <em>Recognition Rule Trace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recognition Rule Trace</em>' reference.
	 * @see #getRecognitionRuleTrace()
	 * @generated
	 */
	void setRecognitionRuleTrace(Node value);

} // Trace
