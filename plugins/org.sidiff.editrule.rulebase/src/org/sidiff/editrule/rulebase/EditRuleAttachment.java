/**
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edit Rule Attachment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.EditRuleAttachment#getRuleBaseItem <em>Rule Base Item</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.EditRuleAttachment#getEditRule <em>Edit Rule</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getEditRuleAttachment()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface EditRuleAttachment extends EObject {
	/**
	 * Returns the value of the '<em><b>Rule Base Item</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.RuleBaseItem#getEditRuleAttachments <em>Edit Rule Attachments</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Base Item</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Base Item</em>' container reference.
	 * @see #setRuleBaseItem(RuleBaseItem)
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getEditRuleAttachment_RuleBaseItem()
	 * @see org.sidiff.editrule.rulebase.RuleBaseItem#getEditRuleAttachments
	 * @model opposite="editRuleAttachments" transient="false"
	 * @generated
	 */
	RuleBaseItem getRuleBaseItem();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.EditRuleAttachment#getRuleBaseItem <em>Rule Base Item</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Base Item</em>' container reference.
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	void setRuleBaseItem(RuleBaseItem value);

	/**
	 * Returns the value of the '<em><b>Edit Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Rule</em>' reference.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getEditRuleAttachment_EditRule()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EditRule getEditRule();

} // EditRuleAttachment
