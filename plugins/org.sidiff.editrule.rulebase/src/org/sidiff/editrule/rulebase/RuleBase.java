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
 * A representation of the model object '<em><b>Rule Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBase#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBase#getItems <em>Items</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBase#getEditRules <em>Edit Rules</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialNodeDependencies <em>Potential Node Dependencies</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialEdgeDependencies <em>Potential Edge Dependencies</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialAttributeDependencies <em>Potential Attribute Dependencies</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBase#getDocumentTypes <em>Document Types</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialNodeConflicts <em>Potential Node Conflicts</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialEdgeConflicts <em>Potential Edge Conflicts</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialAttributeConflicts <em>Potential Attribute Conflicts</em>}</li>
 * </ul>
 *
 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase()
 * @model
 * @generated
 */
public interface RuleBase extends EObject {
	
	/**
	 * @param type
	 *            The entry type.
	 * @return All entries of this rulebase with the specified type.
	 * 
	 * @generated NOT
	 */
	<T extends EditRuleAttachment> EList<T> getEditRuleAttachments(Class<T> type);
	
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
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sidiff.editrule.rulebase.RuleBase#getName <em>Name</em>}' attribute.
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
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase_DocumentTypes()
	 * @model
	 * @generated
	 */
	EList<String> getDocumentTypes();

	/**
	 * Returns the value of the '<em><b>Potential Node Conflicts</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.editrule.rulebase.PotentialNodeConflict}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.PotentialNodeConflict#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Potential Node Conflicts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Potential Node Conflicts</em>' containment reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase_PotentialNodeConflicts()
	 * @see org.sidiff.editrule.rulebase.PotentialNodeConflict#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<PotentialNodeConflict> getPotentialNodeConflicts();

	/**
	 * Returns the value of the '<em><b>Potential Edge Conflicts</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.editrule.rulebase.PotentialEdgeConflict}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.PotentialEdgeConflict#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Potential Edge Conflicts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Potential Edge Conflicts</em>' containment reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase_PotentialEdgeConflicts()
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeConflict#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<PotentialEdgeConflict> getPotentialEdgeConflicts();

	/**
	 * Returns the value of the '<em><b>Potential Attribute Conflicts</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.editrule.rulebase.PotentialAttributeConflict}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Potential Attribute Conflicts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Potential Attribute Conflicts</em>' containment reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase_PotentialAttributeConflicts()
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeConflict#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<PotentialAttributeConflict> getPotentialAttributeConflicts();

	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.editrule.rulebase.RuleBaseItem}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.RuleBaseItem#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' containment reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase_Items()
	 * @see org.sidiff.editrule.rulebase.RuleBaseItem#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<RuleBaseItem> getItems();

	/**
	 * Returns the value of the '<em><b>Edit Rules</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.editrule.rulebase.EditRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Rules</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Rules</em>' reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase_EditRules()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EList<EditRule> getEditRules();

	/**
	 * Returns the value of the '<em><b>Potential Node Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.editrule.rulebase.PotentialNodeDependency}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.PotentialNodeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Potential Node Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Potential Node Dependencies</em>' containment reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase_PotentialNodeDependencies()
	 * @see org.sidiff.editrule.rulebase.PotentialNodeDependency#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<PotentialNodeDependency> getPotentialNodeDependencies();

	/**
	 * Returns the value of the '<em><b>Potential Edge Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.editrule.rulebase.PotentialEdgeDependency}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Potential Edge Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Potential Edge Dependencies</em>' containment reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase_PotentialEdgeDependencies()
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeDependency#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<PotentialEdgeDependency> getPotentialEdgeDependencies();

	/**
	 * Returns the value of the '<em><b>Potential Attribute Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.editrule.rulebase.PotentialAttributeDependency}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.editrule.rulebase.PotentialAttributeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Potential Attribute Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Potential Attribute Dependencies</em>' containment reference list.
	 * @see org.sidiff.editrule.rulebase.RulebasePackage#getRuleBase_PotentialAttributeDependencies()
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeDependency#getRuleBase
	 * @model opposite="ruleBase" containment="true"
	 * @generated
	 */
	EList<PotentialAttributeDependency> getPotentialAttributeDependencies();

} // RuleBase
