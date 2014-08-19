/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edit Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.EditRule#getExecuteMainUnit <em>Execute Main Unit</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.EditRule#getRecognitionRule <em>Recognition Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.EditRule#getRuleBaseItem <em>Rule Base Item</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.EditRule#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.EditRule#isUseDerivedFeatures <em>Use Derived Features</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.EditRule#getFileHashMD5 <em>File Hash MD5</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.rulebase.RulebasePackage#getEditRule()
 * @model
 * @generated
 */
public interface EditRule extends EObject {
	/**
	 * Returns the value of the '<em><b>Execute Main Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Execute Main Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Execute Main Unit</em>' reference.
	 * @see #setExecuteMainUnit(Unit)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getEditRule_ExecuteMainUnit()
	 * @model
	 * @generated
	 */
	Unit getExecuteMainUnit();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.EditRule#getExecuteMainUnit <em>Execute Main Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execute Main Unit</em>' reference.
	 * @see #getExecuteMainUnit()
	 * @generated
	 */
	void setExecuteMainUnit(Unit value);

	/**
	 * Returns the value of the '<em><b>Recognition Rule</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.RecognitionRule#getEditRule <em>Edit Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recognition Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recognition Rule</em>' reference.
	 * @see #setRecognitionRule(RecognitionRule)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getEditRule_RecognitionRule()
	 * @see org.sidiff.difference.rulebase.RecognitionRule#getEditRule
	 * @model opposite="editRule" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	RecognitionRule getRecognitionRule();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.EditRule#getRecognitionRule <em>Recognition Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recognition Rule</em>' reference.
	 * @see #getRecognitionRule()
	 * @generated
	 */
	void setRecognitionRule(RecognitionRule value);

	/**
	 * Returns the value of the '<em><b>Rule Base Item</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.RuleBaseItem#getEditRule <em>Edit Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Base Item</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Base Item</em>' container reference.
	 * @see #setRuleBaseItem(RuleBaseItem)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getEditRule_RuleBaseItem()
	 * @see org.sidiff.difference.rulebase.RuleBaseItem#getEditRule
	 * @model opposite="editRule" transient="false"
	 * @generated
	 */
	RuleBaseItem getRuleBaseItem();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.EditRule#getRuleBaseItem <em>Rule Base Item</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Base Item</em>' container reference.
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	void setRuleBaseItem(RuleBaseItem value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.rulebase.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getEditRule_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Use Derived Features</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Use Derived Features</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Use Derived Features</em>' attribute.
	 * @see #setUseDerivedFeatures(boolean)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getEditRule_UseDerivedFeatures()
	 * @model
	 * @generated
	 */
	boolean isUseDerivedFeatures();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.EditRule#isUseDerivedFeatures <em>Use Derived Features</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Derived Features</em>' attribute.
	 * @see #isUseDerivedFeatures()
	 * @generated
	 */
	void setUseDerivedFeatures(boolean value);

	/**
	 * Returns the value of the '<em><b>File Hash MD5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Hash MD5</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Hash MD5</em>' attribute.
	 * @see #setFileHashMD5(byte[])
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getEditRule_FileHashMD5()
	 * @model
	 * @generated
	 */
	byte[] getFileHashMD5();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.EditRule#getFileHashMD5 <em>File Hash MD5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Hash MD5</em>' attribute.
	 * @see #getFileHashMD5()
	 * @generated
	 */
	void setFileHashMD5(byte[] value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Module getExecuteModule();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nameRequired="true"
	 * @generated
	 */
	Parameter getParameterByName(String name);

} // EditRule
