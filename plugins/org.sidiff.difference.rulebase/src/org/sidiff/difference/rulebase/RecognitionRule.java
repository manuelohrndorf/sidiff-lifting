/**
 */
package org.sidiff.difference.rulebase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.editrule.rulebase.EditRuleAttachment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Recognition Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.RecognitionRule#getRecognitionMainUnit <em>Recognition Main Unit</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RecognitionRule#getTracesB <em>Traces B</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RecognitionRule#getTracesA <em>Traces A</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RecognitionRule#getRecognitionModule <em>Recognition Module</em>}</li>
 * </ul>
 *
 * @see org.sidiff.difference.rulebase.LiftingRulebasePackage#getRecognitionRule()
 * @model
 * @generated
 */
public interface RecognitionRule extends EditRuleAttachment {
	/**
	 * Returns the value of the '<em><b>Recognition Main Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recognition Main Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recognition Main Unit</em>' reference.
	 * @see org.sidiff.difference.rulebase.LiftingRulebasePackage#getRecognitionRule_RecognitionMainUnit()
	 * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Rule getRecognitionMainUnit();

	/**
	 * Returns the value of the '<em><b>Traces B</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.rulebase.Trace}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Traces B</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traces B</em>' containment reference list.
	 * @see org.sidiff.difference.rulebase.LiftingRulebasePackage#getRecognitionRule_TracesB()
	 * @model containment="true"
	 * @generated
	 */
	EList<Trace> getTracesB();

	/**
	 * Returns the value of the '<em><b>Traces A</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.rulebase.Trace}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Traces A</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traces A</em>' containment reference list.
	 * @see org.sidiff.difference.rulebase.LiftingRulebasePackage#getRecognitionRule_TracesA()
	 * @model containment="true"
	 * @generated
	 */
	EList<Trace> getTracesA();

	/**
	 * Returns the value of the '<em><b>Recognition Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recognition Module</em>' reference.
	 * @see #setRecognitionModule(org.eclipse.emf.henshin.model.Module)
	 * @see org.sidiff.difference.rulebase.LiftingRulebasePackage#getRecognitionRule_RecognitionModule()
	 * @model required="true"
	 * @generated
	 */
	org.eclipse.emf.henshin.model.Module getRecognitionModule();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RecognitionRule#getRecognitionModule <em>Recognition Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recognition Module</em>' reference.
	 * @see #getRecognitionModule()
	 * @generated
	 */
	void setRecognitionModule(org.eclipse.emf.henshin.model.Module value);

} // RecognitionRule
