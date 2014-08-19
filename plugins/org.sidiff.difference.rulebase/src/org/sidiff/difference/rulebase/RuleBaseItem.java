/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Base Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBaseItem#isActive <em>Active</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBaseItem#getEditRule <em>Edit Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBaseItem#getRecognitionRule <em>Recognition Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBaseItem#getTracesB <em>Traces B</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBaseItem#getTracesA <em>Traces A</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBaseItem#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBaseItem#getVersion <em>Version</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBaseItem#isValid <em>Valid</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBaseItem()
 * @model
 * @generated
 */
public interface RuleBaseItem extends EObject {
	/**
	 * Returns the value of the '<em><b>Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Active</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Active</em>' attribute.
	 * @see #setActive(boolean)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBaseItem_Active()
	 * @model
	 * @generated
	 */
	boolean isActive();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RuleBaseItem#isActive <em>Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Active</em>' attribute.
	 * @see #isActive()
	 * @generated
	 */
	void setActive(boolean value);

	/**
	 * Returns the value of the '<em><b>Edit Rule</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.EditRule#getRuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Rule</em>' containment reference.
	 * @see #setEditRule(EditRule)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBaseItem_EditRule()
	 * @see org.sidiff.difference.rulebase.EditRule#getRuleBaseItem
	 * @model opposite="ruleBaseItem" containment="true"
	 * @generated
	 */
	EditRule getEditRule();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RuleBaseItem#getEditRule <em>Edit Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Rule</em>' containment reference.
	 * @see #getEditRule()
	 * @generated
	 */
	void setEditRule(EditRule value);

	/**
	 * Returns the value of the '<em><b>Recognition Rule</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.RecognitionRule#getRuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recognition Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recognition Rule</em>' containment reference.
	 * @see #setRecognitionRule(RecognitionRule)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBaseItem_RecognitionRule()
	 * @see org.sidiff.difference.rulebase.RecognitionRule#getRuleBaseItem
	 * @model opposite="ruleBaseItem" containment="true"
	 * @generated
	 */
	RecognitionRule getRecognitionRule();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RuleBaseItem#getRecognitionRule <em>Recognition Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recognition Rule</em>' containment reference.
	 * @see #getRecognitionRule()
	 * @generated
	 */
	void setRecognitionRule(RecognitionRule value);

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
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBaseItem_TracesB()
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
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBaseItem_TracesA()
	 * @model containment="true"
	 * @generated
	 */
	EList<Trace> getTracesA();

	/**
	 * Returns the value of the '<em><b>Rule Base</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.RuleBase#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Base</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Base</em>' container reference.
	 * @see #setRuleBase(RuleBase)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBaseItem_RuleBase()
	 * @see org.sidiff.difference.rulebase.RuleBase#getItems
	 * @model opposite="items" transient="false"
	 * @generated
	 */
	RuleBase getRuleBase();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RuleBaseItem#getRuleBase <em>Rule Base</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Base</em>' container reference.
	 * @see #getRuleBase()
	 * @generated
	 */
	void setRuleBase(RuleBase value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBaseItem_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RuleBaseItem#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Valid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Valid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Valid</em>' attribute.
	 * @see #setValid(boolean)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBaseItem_Valid()
	 * @model
	 * @generated
	 */
	boolean isValid();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RuleBaseItem#isValid <em>Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valid</em>' attribute.
	 * @see #isValid()
	 * @generated
	 */
	void setValid(boolean value);

} // RuleBaseItem
