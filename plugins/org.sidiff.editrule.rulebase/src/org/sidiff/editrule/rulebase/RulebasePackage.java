/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.editrule.rulebase;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.sidiff.editrule.rulebase.RulebaseFactory
 * @model kind="package"
 * @generated
 */
public interface RulebasePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "rulebase";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sidiff.org/editrule/rulebase/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "rulebase";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulebasePackage eINSTANCE = org.sidiff.editrule.rulebase.impl.RulebasePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.PotentialDependencyImpl <em>Potential Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.PotentialDependencyImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialDependency()
	 * @generated
	 */
	int POTENTIAL_DEPENDENCY = 3;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.PotentialAttributeDependencyImpl <em>Potential Attribute Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.PotentialAttributeDependencyImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialAttributeDependency()
	 * @generated
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY = 6;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl <em>Rule Base</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.RuleBaseImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getRuleBase()
	 * @generated
	 */
	int RULE_BASE = 0;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__KEY = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__ITEMS = 2;

	/**
	 * The feature id for the '<em><b>Edit Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__EDIT_RULES = 3;

	/**
	 * The feature id for the '<em><b>Potential Node Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__POTENTIAL_NODE_DEPENDENCIES = 4;

	/**
	 * The feature id for the '<em><b>Potential Edge Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES = 5;

	/**
	 * The feature id for the '<em><b>Potential Attribute Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES = 6;

	/**
	 * The feature id for the '<em><b>Document Types</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__DOCUMENT_TYPES = 7;

	/**
	 * The feature id for the '<em><b>Potential Node Conflicts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__POTENTIAL_NODE_CONFLICTS = 8;

	/**
	 * The feature id for the '<em><b>Potential Edge Conflicts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__POTENTIAL_EDGE_CONFLICTS = 9;

	/**
	 * The feature id for the '<em><b>Potential Attribute Conflicts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS = 10;

	/**
	 * The feature id for the '<em><b>Potential Dangling Edge Conflicts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__POTENTIAL_DANGLING_EDGE_CONFLICTS = 11;

	/**
	 * The number of structural features of the '<em>Rule Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_FEATURE_COUNT = 12;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.EditRuleImpl <em>Edit Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.EditRuleImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getEditRule()
	 * @generated
	 */
	int EDIT_RULE = 1;

	/**
	 * The feature id for the '<em><b>Execute Main Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__EXECUTE_MAIN_UNIT = 0;

	/**
	 * The feature id for the '<em><b>Rule Base Item</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__RULE_BASE_ITEM = 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__PARAMETERS = 2;

	/**
	 * The feature id for the '<em><b>Use Derived Features</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__USE_DERIVED_FEATURES = 3;

	/**
	 * The feature id for the '<em><b>Inverse</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__INVERSE = 4;

	/**
	 * The feature id for the '<em><b>Classification</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__CLASSIFICATION = 5;

	/**
	 * The feature id for the '<em><b>Execute Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__EXECUTE_MODULE = 6;

	/**
	 * The number of structural features of the '<em>Edit Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.RuleBaseItemImpl <em>Rule Base Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.RuleBaseItemImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getRuleBaseItem()
	 * @generated
	 */
	int RULE_BASE_ITEM = 2;

	/**
	 * The feature id for the '<em><b>Edit Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM__EDIT_RULE = 0;

	/**
	 * The feature id for the '<em><b>Rule Base</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM__RULE_BASE = 1;

	/**
	 * The feature id for the '<em><b>Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM__ACTIVE = 2;

	/**
	 * The feature id for the '<em><b>Edit Rule Attachments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS = 3;

	/**
	 * The number of structural features of the '<em>Rule Base Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DEPENDENCY__KIND = 0;

	/**
	 * The feature id for the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DEPENDENCY__SOURCE_RULE = 1;

	/**
	 * The feature id for the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DEPENDENCY__TARGET_RULE = 2;

	/**
	 * The feature id for the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DEPENDENCY__TRANSIENT = 3;

	/**
	 * The number of structural features of the '<em>Potential Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DEPENDENCY_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.PotentialNodeDependencyImpl <em>Potential Node Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.PotentialNodeDependencyImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialNodeDependency()
	 * @generated
	 */
	int POTENTIAL_NODE_DEPENDENCY = 4;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_DEPENDENCY__KIND = POTENTIAL_DEPENDENCY__KIND;

	/**
	 * The feature id for the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_DEPENDENCY__SOURCE_RULE = POTENTIAL_DEPENDENCY__SOURCE_RULE;

	/**
	 * The feature id for the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_DEPENDENCY__TARGET_RULE = POTENTIAL_DEPENDENCY__TARGET_RULE;

	/**
	 * The feature id for the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_DEPENDENCY__TRANSIENT = POTENTIAL_DEPENDENCY__TRANSIENT;

	/**
	 * The feature id for the '<em><b>Rule Base</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_DEPENDENCY__RULE_BASE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_DEPENDENCY__SOURCE_NODE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_DEPENDENCY__TARGET_NODE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Potential Node Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_DEPENDENCY_FEATURE_COUNT = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.PotentialEdgeDependencyImpl <em>Potential Edge Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.PotentialEdgeDependencyImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialEdgeDependency()
	 * @generated
	 */
	int POTENTIAL_EDGE_DEPENDENCY = 5;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_DEPENDENCY__KIND = POTENTIAL_DEPENDENCY__KIND;

	/**
	 * The feature id for the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_DEPENDENCY__SOURCE_RULE = POTENTIAL_DEPENDENCY__SOURCE_RULE;

	/**
	 * The feature id for the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_DEPENDENCY__TARGET_RULE = POTENTIAL_DEPENDENCY__TARGET_RULE;

	/**
	 * The feature id for the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_DEPENDENCY__TRANSIENT = POTENTIAL_DEPENDENCY__TRANSIENT;

	/**
	 * The feature id for the '<em><b>Rule Base</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_DEPENDENCY__RULE_BASE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_DEPENDENCY__SOURCE_EDGE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_DEPENDENCY__TARGET_EDGE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Potential Edge Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_DEPENDENCY_FEATURE_COUNT = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY__KIND = POTENTIAL_DEPENDENCY__KIND;

	/**
	 * The feature id for the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY__SOURCE_RULE = POTENTIAL_DEPENDENCY__SOURCE_RULE;

	/**
	 * The feature id for the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY__TARGET_RULE = POTENTIAL_DEPENDENCY__TARGET_RULE;

	/**
	 * The feature id for the '<em><b>Transient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY__TRANSIENT = POTENTIAL_DEPENDENCY__TRANSIENT;

	/**
	 * The feature id for the '<em><b>Rule Base</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY__RULE_BASE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY__SOURCE_ATTRIBUTE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY__TARGET_ATTRIBUTE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Source Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY__SOURCE_NODE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Target Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY__TARGET_NODE = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Potential Attribute Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY_FEATURE_COUNT = POTENTIAL_DEPENDENCY_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.ParameterImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__DIRECTION = 1;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__KIND = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__TYPE = 3;

	/**
	 * The feature id for the '<em><b>Multi</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__MULTI = 4;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.ClassificationImpl <em>Classification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.ClassificationImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getClassification()
	 * @generated
	 */
	int CLASSIFICATION = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Classificator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION__CLASSIFICATOR = 1;

	/**
	 * The number of structural features of the '<em>Classification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFICATION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.EditRuleAttachment <em>Edit Rule Attachment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.EditRuleAttachment
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getEditRuleAttachment()
	 * @generated
	 */
	int EDIT_RULE_ATTACHMENT = 9;

	/**
	 * The feature id for the '<em><b>Rule Base Item</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE_ATTACHMENT__RULE_BASE_ITEM = 0;

	/**
	 * The feature id for the '<em><b>Edit Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE_ATTACHMENT__EDIT_RULE = 1;

	/**
	 * The number of structural features of the '<em>Edit Rule Attachment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE_ATTACHMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl <em>Potential Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.PotentialConflictImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialConflict()
	 * @generated
	 */
	int POTENTIAL_CONFLICT = 10;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_CONFLICT__KIND = 0;

	/**
	 * The feature id for the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_CONFLICT__SOURCE_RULE = 1;

	/**
	 * The feature id for the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_CONFLICT__TARGET_RULE = 2;

	/**
	 * The number of structural features of the '<em>Potential Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_CONFLICT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.PotentialNodeConflictImpl <em>Potential Node Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.PotentialNodeConflictImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialNodeConflict()
	 * @generated
	 */
	int POTENTIAL_NODE_CONFLICT = 11;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_CONFLICT__KIND = POTENTIAL_CONFLICT__KIND;

	/**
	 * The feature id for the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_CONFLICT__SOURCE_RULE = POTENTIAL_CONFLICT__SOURCE_RULE;

	/**
	 * The feature id for the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_CONFLICT__TARGET_RULE = POTENTIAL_CONFLICT__TARGET_RULE;

	/**
	 * The feature id for the '<em><b>Rule Base</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_CONFLICT__RULE_BASE = POTENTIAL_CONFLICT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_CONFLICT__SOURCE_NODE = POTENTIAL_CONFLICT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_CONFLICT__TARGET_NODE = POTENTIAL_CONFLICT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Potential Node Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_NODE_CONFLICT_FEATURE_COUNT = POTENTIAL_CONFLICT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.PotentialEdgeConflictImpl <em>Potential Edge Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.PotentialEdgeConflictImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialEdgeConflict()
	 * @generated
	 */
	int POTENTIAL_EDGE_CONFLICT = 12;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_CONFLICT__KIND = POTENTIAL_CONFLICT__KIND;

	/**
	 * The feature id for the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_CONFLICT__SOURCE_RULE = POTENTIAL_CONFLICT__SOURCE_RULE;

	/**
	 * The feature id for the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_CONFLICT__TARGET_RULE = POTENTIAL_CONFLICT__TARGET_RULE;

	/**
	 * The feature id for the '<em><b>Rule Base</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_CONFLICT__RULE_BASE = POTENTIAL_CONFLICT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_CONFLICT__SOURCE_EDGE = POTENTIAL_CONFLICT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_CONFLICT__TARGET_EDGE = POTENTIAL_CONFLICT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Potential Edge Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_EDGE_CONFLICT_FEATURE_COUNT = POTENTIAL_CONFLICT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.PotentialAttributeConflictImpl <em>Potential Attribute Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.PotentialAttributeConflictImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialAttributeConflict()
	 * @generated
	 */
	int POTENTIAL_ATTRIBUTE_CONFLICT = 13;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_CONFLICT__KIND = POTENTIAL_CONFLICT__KIND;

	/**
	 * The feature id for the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_RULE = POTENTIAL_CONFLICT__SOURCE_RULE;

	/**
	 * The feature id for the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_RULE = POTENTIAL_CONFLICT__TARGET_RULE;

	/**
	 * The feature id for the '<em><b>Rule Base</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE = POTENTIAL_CONFLICT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_ATTRIBUTE = POTENTIAL_CONFLICT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_ATTRIBUTE = POTENTIAL_CONFLICT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Source Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_NODE = POTENTIAL_CONFLICT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Target Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_NODE = POTENTIAL_CONFLICT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Potential Attribute Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_ATTRIBUTE_CONFLICT_FEATURE_COUNT = POTENTIAL_CONFLICT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.impl.PotentialDanglingEdgeConflictImpl <em>Potential Dangling Edge Conflict</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.impl.PotentialDanglingEdgeConflictImpl
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialDanglingEdgeConflict()
	 * @generated
	 */
	int POTENTIAL_DANGLING_EDGE_CONFLICT = 14;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DANGLING_EDGE_CONFLICT__KIND = POTENTIAL_CONFLICT__KIND;

	/**
	 * The feature id for the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DANGLING_EDGE_CONFLICT__SOURCE_RULE = POTENTIAL_CONFLICT__SOURCE_RULE;

	/**
	 * The feature id for the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DANGLING_EDGE_CONFLICT__TARGET_RULE = POTENTIAL_CONFLICT__TARGET_RULE;

	/**
	 * The feature id for the '<em><b>Rule Base</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE = POTENTIAL_CONFLICT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deletion Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DANGLING_EDGE_CONFLICT__DELETION_NODE = POTENTIAL_CONFLICT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Creation Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DANGLING_EDGE_CONFLICT__CREATION_EDGE = POTENTIAL_CONFLICT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Potential Dangling Edge Conflict</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DANGLING_EDGE_CONFLICT_FEATURE_COUNT = POTENTIAL_CONFLICT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.PotentialDependencyKind <em>Potential Dependency Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.PotentialDependencyKind
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialDependencyKind()
	 * @generated
	 */
	int POTENTIAL_DEPENDENCY_KIND = 15;


	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.ParameterDirection <em>Parameter Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.ParameterDirection
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getParameterDirection()
	 * @generated
	 */
	int PARAMETER_DIRECTION = 16;

	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.ParameterKind <em>Parameter Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.ParameterKind
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getParameterKind()
	 * @generated
	 */
	int PARAMETER_KIND = 17;


	/**
	 * The meta object id for the '{@link org.sidiff.editrule.rulebase.PotentialConflictKind <em>Potential Conflict Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.editrule.rulebase.PotentialConflictKind
	 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialConflictKind()
	 * @generated
	 */
	int POTENTIAL_CONFLICT_KIND = 18;


	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.RuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Base</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase
	 * @generated
	 */
	EClass getRuleBase();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.RuleBase#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getKey()
	 * @see #getRuleBase()
	 * @generated
	 */
	EAttribute getRuleBase_Key();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.RuleBase#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getName()
	 * @see #getRuleBase()
	 * @generated
	 */
	EAttribute getRuleBase_Name();

	/**
	 * Returns the meta object for the attribute list '{@link org.sidiff.editrule.rulebase.RuleBase#getDocumentTypes <em>Document Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Document Types</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getDocumentTypes()
	 * @see #getRuleBase()
	 * @generated
	 */
	EAttribute getRuleBase_DocumentTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialNodeConflicts <em>Potential Node Conflicts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Potential Node Conflicts</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialNodeConflicts()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_PotentialNodeConflicts();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialEdgeConflicts <em>Potential Edge Conflicts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Potential Edge Conflicts</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialEdgeConflicts()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_PotentialEdgeConflicts();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialAttributeConflicts <em>Potential Attribute Conflicts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Potential Attribute Conflicts</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialAttributeConflicts()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_PotentialAttributeConflicts();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialDanglingEdgeConflicts <em>Potential Dangling Edge Conflicts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Potential Dangling Edge Conflicts</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialDanglingEdgeConflicts()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_PotentialDanglingEdgeConflicts();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.RuleBase#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getItems()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_Items();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.editrule.rulebase.RuleBase#getEditRules <em>Edit Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Edit Rules</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getEditRules()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_EditRules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialNodeDependencies <em>Potential Node Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Potential Node Dependencies</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialNodeDependencies()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_PotentialNodeDependencies();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialEdgeDependencies <em>Potential Edge Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Potential Edge Dependencies</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialEdgeDependencies()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_PotentialEdgeDependencies();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.RuleBase#getPotentialAttributeDependencies <em>Potential Attribute Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Potential Attribute Dependencies</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBase#getPotentialAttributeDependencies()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_PotentialAttributeDependencies();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.EditRule <em>Edit Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edit Rule</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRule
	 * @generated
	 */
	EClass getEditRule();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.EditRule#getExecuteMainUnit <em>Execute Main Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Execute Main Unit</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRule#getExecuteMainUnit()
	 * @see #getEditRule()
	 * @generated
	 */
	EReference getEditRule_ExecuteMainUnit();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.editrule.rulebase.EditRule#getRuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base Item</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRule#getRuleBaseItem()
	 * @see #getEditRule()
	 * @generated
	 */
	EReference getEditRule_RuleBaseItem();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.EditRule#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRule#getParameters()
	 * @see #getEditRule()
	 * @generated
	 */
	EReference getEditRule_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.EditRule#isUseDerivedFeatures <em>Use Derived Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Derived Features</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRule#isUseDerivedFeatures()
	 * @see #getEditRule()
	 * @generated
	 */
	EAttribute getEditRule_UseDerivedFeatures();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.EditRule#getInverse <em>Inverse</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Inverse</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRule#getInverse()
	 * @see #getEditRule()
	 * @generated
	 */
	EReference getEditRule_Inverse();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.EditRule#getClassification <em>Classification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Classification</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRule#getClassification()
	 * @see #getEditRule()
	 * @generated
	 */
	EReference getEditRule_Classification();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.EditRule#getExecuteModule <em>Execute Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Execute Module</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRule#getExecuteModule()
	 * @see #getEditRule()
	 * @generated
	 */
	EReference getEditRule_ExecuteModule();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.RuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Base Item</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBaseItem
	 * @generated
	 */
	EClass getRuleBaseItem();

	/**
	 * Returns the meta object for the containment reference '{@link org.sidiff.editrule.rulebase.RuleBaseItem#getEditRule <em>Edit Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Edit Rule</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBaseItem#getEditRule()
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	EReference getRuleBaseItem_EditRule();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.editrule.rulebase.RuleBaseItem#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBaseItem#getRuleBase()
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	EReference getRuleBaseItem_RuleBase();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.RuleBaseItem#isActive <em>Active</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Active</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBaseItem#isActive()
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	EAttribute getRuleBaseItem_Active();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.editrule.rulebase.RuleBaseItem#getEditRuleAttachments <em>Edit Rule Attachments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edit Rule Attachments</em>'.
	 * @see org.sidiff.editrule.rulebase.RuleBaseItem#getEditRuleAttachments()
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	EReference getRuleBaseItem_EditRuleAttachments();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.PotentialDependency <em>Potential Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Dependency</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialDependency
	 * @generated
	 */
	EClass getPotentialDependency();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialDependency#getSourceRule <em>Source Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Rule</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialDependency#getSourceRule()
	 * @see #getPotentialDependency()
	 * @generated
	 */
	EReference getPotentialDependency_SourceRule();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialDependency#getTargetRule <em>Target Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Rule</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialDependency#getTargetRule()
	 * @see #getPotentialDependency()
	 * @generated
	 */
	EReference getPotentialDependency_TargetRule();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.PotentialDependency#isTransient <em>Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transient</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialDependency#isTransient()
	 * @see #getPotentialDependency()
	 * @generated
	 */
	EAttribute getPotentialDependency_Transient();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.PotentialDependency#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialDependency#getKind()
	 * @see #getPotentialDependency()
	 * @generated
	 */
	EAttribute getPotentialDependency_Kind();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.PotentialNodeDependency <em>Potential Node Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Node Dependency</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialNodeDependency
	 * @generated
	 */
	EClass getPotentialNodeDependency();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.editrule.rulebase.PotentialNodeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialNodeDependency#getRuleBase()
	 * @see #getPotentialNodeDependency()
	 * @generated
	 */
	EReference getPotentialNodeDependency_RuleBase();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialNodeDependency#getSourceNode <em>Source Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Node</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialNodeDependency#getSourceNode()
	 * @see #getPotentialNodeDependency()
	 * @generated
	 */
	EReference getPotentialNodeDependency_SourceNode();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialNodeDependency#getTargetNode <em>Target Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Node</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialNodeDependency#getTargetNode()
	 * @see #getPotentialNodeDependency()
	 * @generated
	 */
	EReference getPotentialNodeDependency_TargetNode();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency <em>Potential Edge Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Edge Dependency</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeDependency
	 * @generated
	 */
	EClass getPotentialEdgeDependency();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeDependency#getRuleBase()
	 * @see #getPotentialEdgeDependency()
	 * @generated
	 */
	EReference getPotentialEdgeDependency_RuleBase();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency#getSourceEdge <em>Source Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Edge</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeDependency#getSourceEdge()
	 * @see #getPotentialEdgeDependency()
	 * @generated
	 */
	EReference getPotentialEdgeDependency_SourceEdge();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialEdgeDependency#getTargetEdge <em>Target Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Edge</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeDependency#getTargetEdge()
	 * @see #getPotentialEdgeDependency()
	 * @generated
	 */
	EReference getPotentialEdgeDependency_TargetEdge();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.PotentialAttributeDependency <em>Potential Attribute Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Attribute Dependency</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeDependency
	 * @generated
	 */
	EClass getPotentialAttributeDependency();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.editrule.rulebase.PotentialAttributeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeDependency#getRuleBase()
	 * @see #getPotentialAttributeDependency()
	 * @generated
	 */
	EReference getPotentialAttributeDependency_RuleBase();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialAttributeDependency#getSourceAttribute <em>Source Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Attribute</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeDependency#getSourceAttribute()
	 * @see #getPotentialAttributeDependency()
	 * @generated
	 */
	EReference getPotentialAttributeDependency_SourceAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialAttributeDependency#getTargetAttribute <em>Target Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Attribute</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeDependency#getTargetAttribute()
	 * @see #getPotentialAttributeDependency()
	 * @generated
	 */
	EReference getPotentialAttributeDependency_TargetAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialAttributeDependency#getSourceNode <em>Source Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Node</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeDependency#getSourceNode()
	 * @see #getPotentialAttributeDependency()
	 * @generated
	 */
	EReference getPotentialAttributeDependency_SourceNode();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialAttributeDependency#getTargetNode <em>Target Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Node</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeDependency#getTargetNode()
	 * @see #getPotentialAttributeDependency()
	 * @generated
	 */
	EReference getPotentialAttributeDependency_TargetNode();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.sidiff.editrule.rulebase.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.editrule.rulebase.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.Parameter#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see org.sidiff.editrule.rulebase.Parameter#getDirection()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Direction();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.Parameter#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.sidiff.editrule.rulebase.Parameter#getKind()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Kind();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.Parameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.editrule.rulebase.Parameter#getType()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.Parameter#isMulti <em>Multi</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multi</em>'.
	 * @see org.sidiff.editrule.rulebase.Parameter#isMulti()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Multi();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.Classification <em>Classification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Classification</em>'.
	 * @see org.sidiff.editrule.rulebase.Classification
	 * @generated
	 */
	EClass getClassification();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.Classification#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.editrule.rulebase.Classification#getName()
	 * @see #getClassification()
	 * @generated
	 */
	EAttribute getClassification_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.Classification#getClassificator <em>Classificator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Classificator</em>'.
	 * @see org.sidiff.editrule.rulebase.Classification#getClassificator()
	 * @see #getClassification()
	 * @generated
	 */
	EAttribute getClassification_Classificator();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.EditRuleAttachment <em>Edit Rule Attachment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edit Rule Attachment</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRuleAttachment
	 * @generated
	 */
	EClass getEditRuleAttachment();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.editrule.rulebase.EditRuleAttachment#getRuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base Item</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRuleAttachment#getRuleBaseItem()
	 * @see #getEditRuleAttachment()
	 * @generated
	 */
	EReference getEditRuleAttachment_RuleBaseItem();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.EditRuleAttachment#getEditRule <em>Edit Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Edit Rule</em>'.
	 * @see org.sidiff.editrule.rulebase.EditRuleAttachment#getEditRule()
	 * @see #getEditRuleAttachment()
	 * @generated
	 */
	EReference getEditRuleAttachment_EditRule();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.PotentialConflict <em>Potential Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Conflict</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialConflict
	 * @generated
	 */
	EClass getPotentialConflict();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.editrule.rulebase.PotentialConflict#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialConflict#getKind()
	 * @see #getPotentialConflict()
	 * @generated
	 */
	EAttribute getPotentialConflict_Kind();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialConflict#getSourceRule <em>Source Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Rule</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialConflict#getSourceRule()
	 * @see #getPotentialConflict()
	 * @generated
	 */
	EReference getPotentialConflict_SourceRule();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialConflict#getTargetRule <em>Target Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Rule</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialConflict#getTargetRule()
	 * @see #getPotentialConflict()
	 * @generated
	 */
	EReference getPotentialConflict_TargetRule();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.PotentialNodeConflict <em>Potential Node Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Node Conflict</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialNodeConflict
	 * @generated
	 */
	EClass getPotentialNodeConflict();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.editrule.rulebase.PotentialNodeConflict#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialNodeConflict#getRuleBase()
	 * @see #getPotentialNodeConflict()
	 * @generated
	 */
	EReference getPotentialNodeConflict_RuleBase();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialNodeConflict#getSourceNode <em>Source Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Node</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialNodeConflict#getSourceNode()
	 * @see #getPotentialNodeConflict()
	 * @generated
	 */
	EReference getPotentialNodeConflict_SourceNode();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialNodeConflict#getTargetNode <em>Target Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Node</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialNodeConflict#getTargetNode()
	 * @see #getPotentialNodeConflict()
	 * @generated
	 */
	EReference getPotentialNodeConflict_TargetNode();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.PotentialEdgeConflict <em>Potential Edge Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Edge Conflict</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeConflict
	 * @generated
	 */
	EClass getPotentialEdgeConflict();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.editrule.rulebase.PotentialEdgeConflict#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeConflict#getRuleBase()
	 * @see #getPotentialEdgeConflict()
	 * @generated
	 */
	EReference getPotentialEdgeConflict_RuleBase();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialEdgeConflict#getSourceEdge <em>Source Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Edge</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeConflict#getSourceEdge()
	 * @see #getPotentialEdgeConflict()
	 * @generated
	 */
	EReference getPotentialEdgeConflict_SourceEdge();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialEdgeConflict#getTargetEdge <em>Target Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Edge</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialEdgeConflict#getTargetEdge()
	 * @see #getPotentialEdgeConflict()
	 * @generated
	 */
	EReference getPotentialEdgeConflict_TargetEdge();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict <em>Potential Attribute Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Attribute Conflict</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeConflict
	 * @generated
	 */
	EClass getPotentialAttributeConflict();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeConflict#getRuleBase()
	 * @see #getPotentialAttributeConflict()
	 * @generated
	 */
	EReference getPotentialAttributeConflict_RuleBase();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getSourceAttribute <em>Source Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Attribute</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeConflict#getSourceAttribute()
	 * @see #getPotentialAttributeConflict()
	 * @generated
	 */
	EReference getPotentialAttributeConflict_SourceAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getTargetAttribute <em>Target Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Attribute</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeConflict#getTargetAttribute()
	 * @see #getPotentialAttributeConflict()
	 * @generated
	 */
	EReference getPotentialAttributeConflict_TargetAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getSourceNode <em>Source Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Node</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeConflict#getSourceNode()
	 * @see #getPotentialAttributeConflict()
	 * @generated
	 */
	EReference getPotentialAttributeConflict_SourceNode();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialAttributeConflict#getTargetNode <em>Target Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Node</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialAttributeConflict#getTargetNode()
	 * @see #getPotentialAttributeConflict()
	 * @generated
	 */
	EReference getPotentialAttributeConflict_TargetNode();

	/**
	 * Returns the meta object for class '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict <em>Potential Dangling Edge Conflict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Dangling Edge Conflict</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict
	 * @generated
	 */
	EClass getPotentialDanglingEdgeConflict();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getRuleBase()
	 * @see #getPotentialDanglingEdgeConflict()
	 * @generated
	 */
	EReference getPotentialDanglingEdgeConflict_RuleBase();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getDeletionNode <em>Deletion Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Deletion Node</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getDeletionNode()
	 * @see #getPotentialDanglingEdgeConflict()
	 * @generated
	 */
	EReference getPotentialDanglingEdgeConflict_DeletionNode();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getCreationEdge <em>Creation Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Creation Edge</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict#getCreationEdge()
	 * @see #getPotentialDanglingEdgeConflict()
	 * @generated
	 */
	EReference getPotentialDanglingEdgeConflict_CreationEdge();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.editrule.rulebase.PotentialDependencyKind <em>Potential Dependency Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Potential Dependency Kind</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialDependencyKind
	 * @generated
	 */
	EEnum getPotentialDependencyKind();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.editrule.rulebase.ParameterDirection <em>Parameter Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Parameter Direction</em>'.
	 * @see org.sidiff.editrule.rulebase.ParameterDirection
	 * @generated
	 */
	EEnum getParameterDirection();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.editrule.rulebase.ParameterKind <em>Parameter Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Parameter Kind</em>'.
	 * @see org.sidiff.editrule.rulebase.ParameterKind
	 * @generated
	 */
	EEnum getParameterKind();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.editrule.rulebase.PotentialConflictKind <em>Potential Conflict Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Potential Conflict Kind</em>'.
	 * @see org.sidiff.editrule.rulebase.PotentialConflictKind
	 * @generated
	 */
	EEnum getPotentialConflictKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RulebaseFactory getRulebaseFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl <em>Rule Base</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.RuleBaseImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getRuleBase()
		 * @generated
		 */
		EClass RULE_BASE = eINSTANCE.getRuleBase();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_BASE__KEY = eINSTANCE.getRuleBase_Key();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_BASE__NAME = eINSTANCE.getRuleBase_Name();

		/**
		 * The meta object literal for the '<em><b>Document Types</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_BASE__DOCUMENT_TYPES = eINSTANCE.getRuleBase_DocumentTypes();

		/**
		 * The meta object literal for the '<em><b>Potential Node Conflicts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE__POTENTIAL_NODE_CONFLICTS = eINSTANCE.getRuleBase_PotentialNodeConflicts();

		/**
		 * The meta object literal for the '<em><b>Potential Edge Conflicts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE__POTENTIAL_EDGE_CONFLICTS = eINSTANCE.getRuleBase_PotentialEdgeConflicts();

		/**
		 * The meta object literal for the '<em><b>Potential Attribute Conflicts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS = eINSTANCE.getRuleBase_PotentialAttributeConflicts();

		/**
		 * The meta object literal for the '<em><b>Potential Dangling Edge Conflicts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE__POTENTIAL_DANGLING_EDGE_CONFLICTS = eINSTANCE.getRuleBase_PotentialDanglingEdgeConflicts();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE__ITEMS = eINSTANCE.getRuleBase_Items();

		/**
		 * The meta object literal for the '<em><b>Edit Rules</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE__EDIT_RULES = eINSTANCE.getRuleBase_EditRules();

		/**
		 * The meta object literal for the '<em><b>Potential Node Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE__POTENTIAL_NODE_DEPENDENCIES = eINSTANCE.getRuleBase_PotentialNodeDependencies();

		/**
		 * The meta object literal for the '<em><b>Potential Edge Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES = eINSTANCE.getRuleBase_PotentialEdgeDependencies();

		/**
		 * The meta object literal for the '<em><b>Potential Attribute Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES = eINSTANCE.getRuleBase_PotentialAttributeDependencies();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.EditRuleImpl <em>Edit Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.EditRuleImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getEditRule()
		 * @generated
		 */
		EClass EDIT_RULE = eINSTANCE.getEditRule();

		/**
		 * The meta object literal for the '<em><b>Execute Main Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDIT_RULE__EXECUTE_MAIN_UNIT = eINSTANCE.getEditRule_ExecuteMainUnit();

		/**
		 * The meta object literal for the '<em><b>Rule Base Item</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDIT_RULE__RULE_BASE_ITEM = eINSTANCE.getEditRule_RuleBaseItem();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDIT_RULE__PARAMETERS = eINSTANCE.getEditRule_Parameters();

		/**
		 * The meta object literal for the '<em><b>Use Derived Features</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EDIT_RULE__USE_DERIVED_FEATURES = eINSTANCE.getEditRule_UseDerivedFeatures();

		/**
		 * The meta object literal for the '<em><b>Inverse</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDIT_RULE__INVERSE = eINSTANCE.getEditRule_Inverse();

		/**
		 * The meta object literal for the '<em><b>Classification</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDIT_RULE__CLASSIFICATION = eINSTANCE.getEditRule_Classification();

		/**
		 * The meta object literal for the '<em><b>Execute Module</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDIT_RULE__EXECUTE_MODULE = eINSTANCE.getEditRule_ExecuteModule();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.RuleBaseItemImpl <em>Rule Base Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.RuleBaseItemImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getRuleBaseItem()
		 * @generated
		 */
		EClass RULE_BASE_ITEM = eINSTANCE.getRuleBaseItem();

		/**
		 * The meta object literal for the '<em><b>Edit Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE_ITEM__EDIT_RULE = eINSTANCE.getRuleBaseItem_EditRule();

		/**
		 * The meta object literal for the '<em><b>Rule Base</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE_ITEM__RULE_BASE = eINSTANCE.getRuleBaseItem_RuleBase();

		/**
		 * The meta object literal for the '<em><b>Active</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_BASE_ITEM__ACTIVE = eINSTANCE.getRuleBaseItem_Active();

		/**
		 * The meta object literal for the '<em><b>Edit Rule Attachments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS = eINSTANCE.getRuleBaseItem_EditRuleAttachments();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.PotentialDependencyImpl <em>Potential Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.PotentialDependencyImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialDependency()
		 * @generated
		 */
		EClass POTENTIAL_DEPENDENCY = eINSTANCE.getPotentialDependency();

		/**
		 * The meta object literal for the '<em><b>Source Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_DEPENDENCY__SOURCE_RULE = eINSTANCE.getPotentialDependency_SourceRule();

		/**
		 * The meta object literal for the '<em><b>Target Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_DEPENDENCY__TARGET_RULE = eINSTANCE.getPotentialDependency_TargetRule();

		/**
		 * The meta object literal for the '<em><b>Transient</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POTENTIAL_DEPENDENCY__TRANSIENT = eINSTANCE.getPotentialDependency_Transient();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POTENTIAL_DEPENDENCY__KIND = eINSTANCE.getPotentialDependency_Kind();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.PotentialNodeDependencyImpl <em>Potential Node Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.PotentialNodeDependencyImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialNodeDependency()
		 * @generated
		 */
		EClass POTENTIAL_NODE_DEPENDENCY = eINSTANCE.getPotentialNodeDependency();

		/**
		 * The meta object literal for the '<em><b>Rule Base</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_NODE_DEPENDENCY__RULE_BASE = eINSTANCE.getPotentialNodeDependency_RuleBase();

		/**
		 * The meta object literal for the '<em><b>Source Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_NODE_DEPENDENCY__SOURCE_NODE = eINSTANCE.getPotentialNodeDependency_SourceNode();

		/**
		 * The meta object literal for the '<em><b>Target Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_NODE_DEPENDENCY__TARGET_NODE = eINSTANCE.getPotentialNodeDependency_TargetNode();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.PotentialEdgeDependencyImpl <em>Potential Edge Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.PotentialEdgeDependencyImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialEdgeDependency()
		 * @generated
		 */
		EClass POTENTIAL_EDGE_DEPENDENCY = eINSTANCE.getPotentialEdgeDependency();

		/**
		 * The meta object literal for the '<em><b>Rule Base</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_EDGE_DEPENDENCY__RULE_BASE = eINSTANCE.getPotentialEdgeDependency_RuleBase();

		/**
		 * The meta object literal for the '<em><b>Source Edge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_EDGE_DEPENDENCY__SOURCE_EDGE = eINSTANCE.getPotentialEdgeDependency_SourceEdge();

		/**
		 * The meta object literal for the '<em><b>Target Edge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_EDGE_DEPENDENCY__TARGET_EDGE = eINSTANCE.getPotentialEdgeDependency_TargetEdge();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.PotentialAttributeDependencyImpl <em>Potential Attribute Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.PotentialAttributeDependencyImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialAttributeDependency()
		 * @generated
		 */
		EClass POTENTIAL_ATTRIBUTE_DEPENDENCY = eINSTANCE.getPotentialAttributeDependency();

		/**
		 * The meta object literal for the '<em><b>Rule Base</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_ATTRIBUTE_DEPENDENCY__RULE_BASE = eINSTANCE.getPotentialAttributeDependency_RuleBase();

		/**
		 * The meta object literal for the '<em><b>Source Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_ATTRIBUTE_DEPENDENCY__SOURCE_ATTRIBUTE = eINSTANCE.getPotentialAttributeDependency_SourceAttribute();

		/**
		 * The meta object literal for the '<em><b>Target Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_ATTRIBUTE_DEPENDENCY__TARGET_ATTRIBUTE = eINSTANCE.getPotentialAttributeDependency_TargetAttribute();

		/**
		 * The meta object literal for the '<em><b>Source Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_ATTRIBUTE_DEPENDENCY__SOURCE_NODE = eINSTANCE.getPotentialAttributeDependency_SourceNode();

		/**
		 * The meta object literal for the '<em><b>Target Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_ATTRIBUTE_DEPENDENCY__TARGET_NODE = eINSTANCE.getPotentialAttributeDependency_TargetNode();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.ParameterImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__DIRECTION = eINSTANCE.getParameter_Direction();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__KIND = eINSTANCE.getParameter_Kind();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__TYPE = eINSTANCE.getParameter_Type();

		/**
		 * The meta object literal for the '<em><b>Multi</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__MULTI = eINSTANCE.getParameter_Multi();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.ClassificationImpl <em>Classification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.ClassificationImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getClassification()
		 * @generated
		 */
		EClass CLASSIFICATION = eINSTANCE.getClassification();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASSIFICATION__NAME = eINSTANCE.getClassification_Name();

		/**
		 * The meta object literal for the '<em><b>Classificator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASSIFICATION__CLASSIFICATOR = eINSTANCE.getClassification_Classificator();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.EditRuleAttachment <em>Edit Rule Attachment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.EditRuleAttachment
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getEditRuleAttachment()
		 * @generated
		 */
		EClass EDIT_RULE_ATTACHMENT = eINSTANCE.getEditRuleAttachment();

		/**
		 * The meta object literal for the '<em><b>Rule Base Item</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDIT_RULE_ATTACHMENT__RULE_BASE_ITEM = eINSTANCE.getEditRuleAttachment_RuleBaseItem();

		/**
		 * The meta object literal for the '<em><b>Edit Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDIT_RULE_ATTACHMENT__EDIT_RULE = eINSTANCE.getEditRuleAttachment_EditRule();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl <em>Potential Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.PotentialConflictImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialConflict()
		 * @generated
		 */
		EClass POTENTIAL_CONFLICT = eINSTANCE.getPotentialConflict();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POTENTIAL_CONFLICT__KIND = eINSTANCE.getPotentialConflict_Kind();

		/**
		 * The meta object literal for the '<em><b>Source Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_CONFLICT__SOURCE_RULE = eINSTANCE.getPotentialConflict_SourceRule();

		/**
		 * The meta object literal for the '<em><b>Target Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_CONFLICT__TARGET_RULE = eINSTANCE.getPotentialConflict_TargetRule();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.PotentialNodeConflictImpl <em>Potential Node Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.PotentialNodeConflictImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialNodeConflict()
		 * @generated
		 */
		EClass POTENTIAL_NODE_CONFLICT = eINSTANCE.getPotentialNodeConflict();

		/**
		 * The meta object literal for the '<em><b>Rule Base</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_NODE_CONFLICT__RULE_BASE = eINSTANCE.getPotentialNodeConflict_RuleBase();

		/**
		 * The meta object literal for the '<em><b>Source Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_NODE_CONFLICT__SOURCE_NODE = eINSTANCE.getPotentialNodeConflict_SourceNode();

		/**
		 * The meta object literal for the '<em><b>Target Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_NODE_CONFLICT__TARGET_NODE = eINSTANCE.getPotentialNodeConflict_TargetNode();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.PotentialEdgeConflictImpl <em>Potential Edge Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.PotentialEdgeConflictImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialEdgeConflict()
		 * @generated
		 */
		EClass POTENTIAL_EDGE_CONFLICT = eINSTANCE.getPotentialEdgeConflict();

		/**
		 * The meta object literal for the '<em><b>Rule Base</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_EDGE_CONFLICT__RULE_BASE = eINSTANCE.getPotentialEdgeConflict_RuleBase();

		/**
		 * The meta object literal for the '<em><b>Source Edge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_EDGE_CONFLICT__SOURCE_EDGE = eINSTANCE.getPotentialEdgeConflict_SourceEdge();

		/**
		 * The meta object literal for the '<em><b>Target Edge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_EDGE_CONFLICT__TARGET_EDGE = eINSTANCE.getPotentialEdgeConflict_TargetEdge();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.PotentialAttributeConflictImpl <em>Potential Attribute Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.PotentialAttributeConflictImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialAttributeConflict()
		 * @generated
		 */
		EClass POTENTIAL_ATTRIBUTE_CONFLICT = eINSTANCE.getPotentialAttributeConflict();

		/**
		 * The meta object literal for the '<em><b>Rule Base</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE = eINSTANCE.getPotentialAttributeConflict_RuleBase();

		/**
		 * The meta object literal for the '<em><b>Source Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_ATTRIBUTE = eINSTANCE.getPotentialAttributeConflict_SourceAttribute();

		/**
		 * The meta object literal for the '<em><b>Target Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_ATTRIBUTE = eINSTANCE.getPotentialAttributeConflict_TargetAttribute();

		/**
		 * The meta object literal for the '<em><b>Source Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_NODE = eINSTANCE.getPotentialAttributeConflict_SourceNode();

		/**
		 * The meta object literal for the '<em><b>Target Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_NODE = eINSTANCE.getPotentialAttributeConflict_TargetNode();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.impl.PotentialDanglingEdgeConflictImpl <em>Potential Dangling Edge Conflict</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.impl.PotentialDanglingEdgeConflictImpl
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialDanglingEdgeConflict()
		 * @generated
		 */
		EClass POTENTIAL_DANGLING_EDGE_CONFLICT = eINSTANCE.getPotentialDanglingEdgeConflict();

		/**
		 * The meta object literal for the '<em><b>Rule Base</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE = eINSTANCE.getPotentialDanglingEdgeConflict_RuleBase();

		/**
		 * The meta object literal for the '<em><b>Deletion Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_DANGLING_EDGE_CONFLICT__DELETION_NODE = eINSTANCE.getPotentialDanglingEdgeConflict_DeletionNode();

		/**
		 * The meta object literal for the '<em><b>Creation Edge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POTENTIAL_DANGLING_EDGE_CONFLICT__CREATION_EDGE = eINSTANCE.getPotentialDanglingEdgeConflict_CreationEdge();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.PotentialDependencyKind <em>Potential Dependency Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.PotentialDependencyKind
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialDependencyKind()
		 * @generated
		 */
		EEnum POTENTIAL_DEPENDENCY_KIND = eINSTANCE.getPotentialDependencyKind();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.ParameterDirection <em>Parameter Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.ParameterDirection
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getParameterDirection()
		 * @generated
		 */
		EEnum PARAMETER_DIRECTION = eINSTANCE.getParameterDirection();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.ParameterKind <em>Parameter Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.ParameterKind
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getParameterKind()
		 * @generated
		 */
		EEnum PARAMETER_KIND = eINSTANCE.getParameterKind();

		/**
		 * The meta object literal for the '{@link org.sidiff.editrule.rulebase.PotentialConflictKind <em>Potential Conflict Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.editrule.rulebase.PotentialConflictKind
		 * @see org.sidiff.editrule.rulebase.impl.RulebasePackageImpl#getPotentialConflictKind()
		 * @generated
		 */
		EEnum POTENTIAL_CONFLICT_KIND = eINSTANCE.getPotentialConflictKind();

	}

} //RulebasePackage
