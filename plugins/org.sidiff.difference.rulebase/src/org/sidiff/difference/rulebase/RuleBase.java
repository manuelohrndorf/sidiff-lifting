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
 * A representation of the model object '<em><b>Rule Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getItems <em>Items</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getEditRules <em>Edit Rules</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getRecognitionRules <em>Recognition Rules</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getPotentialNodeDependencies <em>Potential Node Dependencies</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getPotentialEdgeDependencies <em>Potential Edge Dependencies</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getPotentialAttributeDependencies <em>Potential Attribute Dependencies</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getDocumentTypes <em>Document Types</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getCharacteristicDocumentType <em>Characteristic Document Type</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getEditRuleFolder <em>Edit Rule Folder</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.RuleBase#getRecognitionRuleFolder <em>Recognition Rule Folder</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase()
 * @model
 * @generated
 */
public interface RuleBase extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RuleBase#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Document Types</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Document Types</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Document Types</em>' attribute list.
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_DocumentTypes()
	 * @model
	 * @generated
	 */
	EList<String> getDocumentTypes();

	/**
	 * Returns the value of the '<em><b>Characteristic Document Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Characteristic Document Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Characteristic Document Type</em>' attribute.
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_CharacteristicDocumentType()
	 * @model default="" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getCharacteristicDocumentType();

	/**
	 * Returns the value of the '<em><b>Edit Rule Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Rule Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Rule Folder</em>' attribute.
	 * @see #setEditRuleFolder(String)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_EditRuleFolder()
	 * @model
	 * @generated
	 */
	String getEditRuleFolder();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RuleBase#getEditRuleFolder <em>Edit Rule Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Rule Folder</em>' attribute.
	 * @see #getEditRuleFolder()
	 * @generated
	 */
	void setEditRuleFolder(String value);

	/**
	 * Returns the value of the '<em><b>Recognition Rule Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recognition Rule Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recognition Rule Folder</em>' attribute.
	 * @see #setRecognitionRuleFolder(String)
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_RecognitionRuleFolder()
	 * @model
	 * @generated
	 */
	String getRecognitionRuleFolder();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.rulebase.RuleBase#getRecognitionRuleFolder <em>Recognition Rule Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recognition Rule Folder</em>' attribute.
	 * @see #getRecognitionRuleFolder()
	 * @generated
	 */
	void setRecognitionRuleFolder(String value);

	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.rulebase.RuleBaseItem}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.RuleBaseItem#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' containment reference list.
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_Items()
	 * @see org.sidiff.difference.rulebase.RuleBaseItem#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<RuleBaseItem> getItems();

	/**
	 * Returns the value of the '<em><b>Edit Rules</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.difference.rulebase.EditRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Rules</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Rules</em>' reference list.
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_EditRules()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EList<EditRule> getEditRules();

	/**
	 * Returns the value of the '<em><b>Recognition Rules</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.difference.rulebase.RecognitionRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recognition Rules</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recognition Rules</em>' reference list.
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_RecognitionRules()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EList<RecognitionRule> getRecognitionRules();

	/**
	 * Returns the value of the '<em><b>Potential Node Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.rulebase.PotentialNodeDependency}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.PotentialNodeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Potential Node Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Potential Node Dependencies</em>' containment reference list.
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_PotentialNodeDependencies()
	 * @see org.sidiff.difference.rulebase.PotentialNodeDependency#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<PotentialNodeDependency> getPotentialNodeDependencies();

	/**
	 * Returns the value of the '<em><b>Potential Edge Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.rulebase.PotentialEdgeDependency}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.PotentialEdgeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Potential Edge Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Potential Edge Dependencies</em>' containment reference list.
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_PotentialEdgeDependencies()
	 * @see org.sidiff.difference.rulebase.PotentialEdgeDependency#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<PotentialEdgeDependency> getPotentialEdgeDependencies();

	/**
	 * Returns the value of the '<em><b>Potential Attribute Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.rulebase.PotentialAttributeDependency}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.rulebase.PotentialAttributeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Potential Attribute Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Potential Attribute Dependencies</em>' containment reference list.
	 * @see org.sidiff.difference.rulebase.RulebasePackage#getRuleBase_PotentialAttributeDependencies()
	 * @see org.sidiff.difference.rulebase.PotentialAttributeDependency#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<PotentialAttributeDependency> getPotentialAttributeDependencies();

} // RuleBase
