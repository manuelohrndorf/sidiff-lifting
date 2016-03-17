/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Base Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBaseItem#getEditRule <em>Edit Rule</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBaseItem#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBaseItem#isActive <em>Active</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBaseItem#getEditRuleAttachments <em>Edit Rule Attachments</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBaseItem()
 * @model
 * @generated
 */
public interface RuleBaseItem extends EObject {
	
	/**
	 * @param type
	 *            The co-rule type.
	 * @return The co-rule with the specified type.
	 * 
	 * @generated NOT
	 */
	<T extends EditRuleAttachment> T getEditRuleAttachment(Class<T> type);
	
	/**
	 * Returns the value of the '<em><b>Edit Rule</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.EditRule#getRuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Rule</em>' containment reference.
	 * @see #setEditRule(EditRule)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBaseItem_EditRule()
	 * @see org.sidiff.editrule.rulebase.EditRule#getRuleBaseItem
	 * @model opposite="ruleBaseItem" containment="true"
	 * @generated
	 */
	EditRule getEditRule();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.RuleBaseItem#getEditRule <em>Edit Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Rule</em>' containment reference.
	 * @see #getEditRule()
	 * @generated
	 */
	void setEditRule(EditRule value);

	/**
	 * Returns the value of the '<em><b>Rule Base</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.RuleBase#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Base</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Base</em>' container reference.
	 * @see #setRuleBase(RuleBase)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBaseItem_RuleBase()
	 * @see org.sidiff.editrule.rulebase.RuleBase#getItems
	 * @model opposite="items" transient="false"
	 * @generated
	 */
	RuleBase getRuleBase();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.RuleBaseItem#getRuleBase <em>Rule Base</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Base</em>' container reference.
	 * @see #getRuleBase()
	 * @generated
	 */
	void setRuleBase(RuleBase value);

	/**
	 * Returns the value of the '<em><b>Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Active</em>' attribute.
	 * @see #setActive(boolean)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBaseItem_Active()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	boolean isActive();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.RuleBaseItem#isActive <em>Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Active</em>' attribute.
	 * @see #isActive()
	 * @generated
	 */
	void setActive(boolean value);

	/**
	 * Returns the value of the '<em><b>Edit Rule Attachments</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.editrule.rulebase.EditRuleAttachment}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.EditRuleAttachment#getRuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Rule Attachments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Rule Attachments</em>' containment reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBaseItem_EditRuleAttachments()
	 * @see org.sidiff.editrule.rulebase.EditRuleAttachment#getRuleBaseItem
	 * @model opposite="ruleBaseItem" containment="true"
	 * @generated
	 */
	EList<EditRuleAttachment> getEditRuleAttachments();

} // RuleBaseItem
