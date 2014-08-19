/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Recognition Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.RecognitionRule#getRecognitionMainUnit <em>Recognition Main Unit</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RecognitionRule#getEditRule <em>Edit Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RecognitionRule#getRuleBaseItem <em>Rule Base Item</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.rulebase.RulebasePackage#getRecognitionRule()
 * @model
 * @generated
 */
public interface RecognitionRule extends EObject {
	/**
	 * Returns the value of the '<em><b>Recognition Main Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recognition Main Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recognition Main Unit</em>' reference.
	 * @see #setRecognitionMainUnit(Rule)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRecognitionRule_RecognitionMainUnit()
	 * @model
	 * @generated
	 */
	Rule getRecognitionMainUnit();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RecognitionRule#getRecognitionMainUnit <em>Recognition Main Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recognition Main Unit</em>' reference.
	 * @see #getRecognitionMainUnit()
	 * @generated
	 */
	void setRecognitionMainUnit(Rule value);

	/**
	 * Returns the value of the '<em><b>Edit Rule</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.EditRule#getRecognitionRule <em>Recognition Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Rule</em>' reference.
	 * @see #setEditRule(EditRule)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRecognitionRule_EditRule()
	 * @see org.sidiff.difference.rulebase.EditRule#getRecognitionRule
	 * @model opposite="recognitionRule" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	EditRule getEditRule();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RecognitionRule#getEditRule <em>Edit Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Rule</em>' reference.
	 * @see #getEditRule()
	 * @generated
	 */
	void setEditRule(EditRule value);

	/**
	 * Returns the value of the '<em><b>Rule Base Item</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.RuleBaseItem#getRecognitionRule <em>Recognition Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Base Item</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Base Item</em>' container reference.
	 * @see #setRuleBaseItem(RuleBaseItem)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRecognitionRule_RuleBaseItem()
	 * @see org.sidiff.difference.rulebase.RuleBaseItem#getRecognitionRule
	 * @model opposite="recognitionRule" transient="false"
	 * @generated
	 */
	RuleBaseItem getRuleBaseItem();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RecognitionRule#getRuleBaseItem <em>Rule Base Item</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Base Item</em>' container reference.
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	void setRuleBaseItem(RuleBaseItem value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Module getRecognitionModule();

} // RecognitionRule
