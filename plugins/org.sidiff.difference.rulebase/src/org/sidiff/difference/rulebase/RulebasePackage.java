/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase;

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
 * @see org.sidiff.difference.rulebase.RulebaseFactory
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
	String eNS_URI = "http://www.sidiff.org/difference/rulebase/1.0";

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
	RulebasePackage eINSTANCE = org.sidiff.difference.rulebase.impl.RulebasePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.PotentialDependencyImpl <em>Potential Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.PotentialDependencyImpl
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getPotentialDependency()
	 * @generated
	 */
	int POTENTIAL_DEPENDENCY = 5;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.PotentialAttributeDependencyImpl <em>Potential Attribute Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.PotentialAttributeDependencyImpl
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getPotentialAttributeDependency()
	 * @generated
	 */
	int POTENTIAL_ATTRIBUTE_DEPENDENCY = 8;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl <em>Rule Base</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.RuleBaseImpl
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getRuleBase()
	 * @generated
	 */
	int RULE_BASE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Document Types</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__DOCUMENT_TYPES = 1;

	/**
	 * The feature id for the '<em><b>Characteristic Document Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__CHARACTERISTIC_DOCUMENT_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__ITEMS = 3;

	/**
	 * The feature id for the '<em><b>Edit Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__EDIT_RULES = 4;

	/**
	 * The feature id for the '<em><b>Recognition Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__RECOGNITION_RULES = 5;

	/**
	 * The feature id for the '<em><b>Potential Node Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__POTENTIAL_NODE_DEPENDENCIES = 6;

	/**
	 * The feature id for the '<em><b>Potential Edge Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES = 7;

	/**
	 * The feature id for the '<em><b>Potential Attribute Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES = 8;

	/**
	 * The number of structural features of the '<em>Rule Base</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.EditRuleImpl <em>Edit Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.EditRuleImpl
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getEditRule()
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
	 * The feature id for the '<em><b>Recognition Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__RECOGNITION_RULE = 1;

	/**
	 * The feature id for the '<em><b>Rule Base Item</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__RULE_BASE_ITEM = 2;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__PARAMETERS = 3;

	/**
	 * The feature id for the '<em><b>Use Derived Features</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE__USE_DERIVED_FEATURES = 4;

	/**
	 * The number of structural features of the '<em>Edit Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDIT_RULE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl <em>Recognition Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.RecognitionRuleImpl
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getRecognitionRule()
	 * @generated
	 */
	int RECOGNITION_RULE = 2;

	/**
	 * The feature id for the '<em><b>Recognition Main Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOGNITION_RULE__RECOGNITION_MAIN_UNIT = 0;

	/**
	 * The feature id for the '<em><b>Edit Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOGNITION_RULE__EDIT_RULE = 1;

	/**
	 * The feature id for the '<em><b>Rule Base Item</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOGNITION_RULE__RULE_BASE_ITEM = 2;

	/**
	 * The number of structural features of the '<em>Recognition Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOGNITION_RULE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.TraceImpl <em>Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.TraceImpl
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getTrace()
	 * @generated
	 */
	int TRACE = 3;

	/**
	 * The feature id for the '<em><b>Edit Rule Trace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__EDIT_RULE_TRACE = 0;

	/**
	 * The feature id for the '<em><b>Recognition Rule Trace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__RECOGNITION_RULE_TRACE = 1;

	/**
	 * The number of structural features of the '<em>Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.RuleBaseItemImpl <em>Rule Base Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.RuleBaseItemImpl
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getRuleBaseItem()
	 * @generated
	 */
	int RULE_BASE_ITEM = 4;

	/**
	 * The feature id for the '<em><b>Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM__ACTIVE = 0;

	/**
	 * The feature id for the '<em><b>Edit Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM__EDIT_RULE = 1;

	/**
	 * The feature id for the '<em><b>Recognition Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM__RECOGNITION_RULE = 2;

	/**
	 * The feature id for the '<em><b>Traces B</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM__TRACES_B = 3;

	/**
	 * The feature id for the '<em><b>Traces A</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM__TRACES_A = 4;

	/**
	 * The feature id for the '<em><b>Rule Base</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM__RULE_BASE = 5;

	/**
	 * The number of structural features of the '<em>Rule Base Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASE_ITEM_FEATURE_COUNT = 6;

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
	 * The number of structural features of the '<em>Potential Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POTENTIAL_DEPENDENCY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.PotentialNodeDependencyImpl <em>Potential Node Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.PotentialNodeDependencyImpl
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getPotentialNodeDependency()
	 * @generated
	 */
	int POTENTIAL_NODE_DEPENDENCY = 6;

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
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.PotentialEdgeDependencyImpl <em>Potential Edge Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.PotentialEdgeDependencyImpl
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getPotentialEdgeDependency()
	 * @generated
	 */
	int POTENTIAL_EDGE_DEPENDENCY = 7;

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
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.ParameterImpl
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 9;

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
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.PotentialDependencyKind <em>Potential Dependency Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.PotentialDependencyKind
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getPotentialDependencyKind()
	 * @generated
	 */
	int POTENTIAL_DEPENDENCY_KIND = 10;


	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.ParameterDirection <em>Parameter Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.ParameterDirection
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getParameterDirection()
	 * @generated
	 */
	int PARAMETER_DIRECTION = 11;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.ParameterKind <em>Parameter Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.ParameterKind
	 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getParameterKind()
	 * @generated
	 */
	int PARAMETER_KIND = 12;


	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.rulebase.RuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Base</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBase
	 * @generated
	 */
	EClass getRuleBase();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.rulebase.RuleBase#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBase#getName()
	 * @see #getRuleBase()
	 * @generated
	 */
	EAttribute getRuleBase_Name();

	/**
	 * Returns the meta object for the attribute list '{@link org.sidiff.difference.rulebase.RuleBase#getDocumentTypes <em>Document Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Document Types</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBase#getDocumentTypes()
	 * @see #getRuleBase()
	 * @generated
	 */
	EAttribute getRuleBase_DocumentTypes();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.rulebase.RuleBase#getCharacteristicDocumentType <em>Characteristic Document Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Characteristic Document Type</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBase#getCharacteristicDocumentType()
	 * @see #getRuleBase()
	 * @generated
	 */
	EAttribute getRuleBase_CharacteristicDocumentType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.rulebase.RuleBase#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBase#getItems()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_Items();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.difference.rulebase.RuleBase#getEditRules <em>Edit Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Edit Rules</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBase#getEditRules()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_EditRules();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.difference.rulebase.RuleBase#getRecognitionRules <em>Recognition Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Recognition Rules</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBase#getRecognitionRules()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_RecognitionRules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.rulebase.RuleBase#getPotentialNodeDependencies <em>Potential Node Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Potential Node Dependencies</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBase#getPotentialNodeDependencies()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_PotentialNodeDependencies();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.rulebase.RuleBase#getPotentialEdgeDependencies <em>Potential Edge Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Potential Edge Dependencies</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBase#getPotentialEdgeDependencies()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_PotentialEdgeDependencies();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.rulebase.RuleBase#getPotentialAttributeDependencies <em>Potential Attribute Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Potential Attribute Dependencies</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBase#getPotentialAttributeDependencies()
	 * @see #getRuleBase()
	 * @generated
	 */
	EReference getRuleBase_PotentialAttributeDependencies();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.rulebase.EditRule <em>Edit Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edit Rule</em>'.
	 * @see org.sidiff.difference.rulebase.EditRule
	 * @generated
	 */
	EClass getEditRule();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.EditRule#getExecuteMainUnit <em>Execute Main Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Execute Main Unit</em>'.
	 * @see org.sidiff.difference.rulebase.EditRule#getExecuteMainUnit()
	 * @see #getEditRule()
	 * @generated
	 */
	EReference getEditRule_ExecuteMainUnit();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.EditRule#getRecognitionRule <em>Recognition Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Recognition Rule</em>'.
	 * @see org.sidiff.difference.rulebase.EditRule#getRecognitionRule()
	 * @see #getEditRule()
	 * @generated
	 */
	EReference getEditRule_RecognitionRule();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.difference.rulebase.EditRule#getRuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base Item</em>'.
	 * @see org.sidiff.difference.rulebase.EditRule#getRuleBaseItem()
	 * @see #getEditRule()
	 * @generated
	 */
	EReference getEditRule_RuleBaseItem();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.rulebase.EditRule#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.sidiff.difference.rulebase.EditRule#getParameters()
	 * @see #getEditRule()
	 * @generated
	 */
	EReference getEditRule_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.rulebase.EditRule#isUseDerivedFeatures <em>Use Derived Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Derived Features</em>'.
	 * @see org.sidiff.difference.rulebase.EditRule#isUseDerivedFeatures()
	 * @see #getEditRule()
	 * @generated
	 */
	EAttribute getEditRule_UseDerivedFeatures();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.rulebase.RecognitionRule <em>Recognition Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Recognition Rule</em>'.
	 * @see org.sidiff.difference.rulebase.RecognitionRule
	 * @generated
	 */
	EClass getRecognitionRule();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.RecognitionRule#getRecognitionMainUnit <em>Recognition Main Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Recognition Main Unit</em>'.
	 * @see org.sidiff.difference.rulebase.RecognitionRule#getRecognitionMainUnit()
	 * @see #getRecognitionRule()
	 * @generated
	 */
	EReference getRecognitionRule_RecognitionMainUnit();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.RecognitionRule#getEditRule <em>Edit Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Edit Rule</em>'.
	 * @see org.sidiff.difference.rulebase.RecognitionRule#getEditRule()
	 * @see #getRecognitionRule()
	 * @generated
	 */
	EReference getRecognitionRule_EditRule();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.difference.rulebase.RecognitionRule#getRuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base Item</em>'.
	 * @see org.sidiff.difference.rulebase.RecognitionRule#getRuleBaseItem()
	 * @see #getRecognitionRule()
	 * @generated
	 */
	EReference getRecognitionRule_RuleBaseItem();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.rulebase.Trace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trace</em>'.
	 * @see org.sidiff.difference.rulebase.Trace
	 * @generated
	 */
	EClass getTrace();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.Trace#getEditRuleTrace <em>Edit Rule Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Edit Rule Trace</em>'.
	 * @see org.sidiff.difference.rulebase.Trace#getEditRuleTrace()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_EditRuleTrace();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.Trace#getRecognitionRuleTrace <em>Recognition Rule Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Recognition Rule Trace</em>'.
	 * @see org.sidiff.difference.rulebase.Trace#getRecognitionRuleTrace()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_RecognitionRuleTrace();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.rulebase.RuleBaseItem <em>Rule Base Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Base Item</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBaseItem
	 * @generated
	 */
	EClass getRuleBaseItem();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.rulebase.RuleBaseItem#isActive <em>Active</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Active</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBaseItem#isActive()
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	EAttribute getRuleBaseItem_Active();

	/**
	 * Returns the meta object for the containment reference '{@link org.sidiff.difference.rulebase.RuleBaseItem#getEditRule <em>Edit Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Edit Rule</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBaseItem#getEditRule()
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	EReference getRuleBaseItem_EditRule();

	/**
	 * Returns the meta object for the containment reference '{@link org.sidiff.difference.rulebase.RuleBaseItem#getRecognitionRule <em>Recognition Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Recognition Rule</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBaseItem#getRecognitionRule()
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	EReference getRuleBaseItem_RecognitionRule();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.rulebase.RuleBaseItem#getTracesB <em>Traces B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Traces B</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBaseItem#getTracesB()
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	EReference getRuleBaseItem_TracesB();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.rulebase.RuleBaseItem#getTracesA <em>Traces A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Traces A</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBaseItem#getTracesA()
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	EReference getRuleBaseItem_TracesA();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.difference.rulebase.RuleBaseItem#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.difference.rulebase.RuleBaseItem#getRuleBase()
	 * @see #getRuleBaseItem()
	 * @generated
	 */
	EReference getRuleBaseItem_RuleBase();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.rulebase.PotentialDependency <em>Potential Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Dependency</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialDependency
	 * @generated
	 */
	EClass getPotentialDependency();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.PotentialDependency#getSourceRule <em>Source Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Rule</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialDependency#getSourceRule()
	 * @see #getPotentialDependency()
	 * @generated
	 */
	EReference getPotentialDependency_SourceRule();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.PotentialDependency#getTargetRule <em>Target Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Rule</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialDependency#getTargetRule()
	 * @see #getPotentialDependency()
	 * @generated
	 */
	EReference getPotentialDependency_TargetRule();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.rulebase.PotentialDependency#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialDependency#getKind()
	 * @see #getPotentialDependency()
	 * @generated
	 */
	EAttribute getPotentialDependency_Kind();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.rulebase.PotentialNodeDependency <em>Potential Node Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Node Dependency</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialNodeDependency
	 * @generated
	 */
	EClass getPotentialNodeDependency();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.difference.rulebase.PotentialNodeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialNodeDependency#getRuleBase()
	 * @see #getPotentialNodeDependency()
	 * @generated
	 */
	EReference getPotentialNodeDependency_RuleBase();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.PotentialNodeDependency#getSourceNode <em>Source Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Node</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialNodeDependency#getSourceNode()
	 * @see #getPotentialNodeDependency()
	 * @generated
	 */
	EReference getPotentialNodeDependency_SourceNode();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.PotentialNodeDependency#getTargetNode <em>Target Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Node</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialNodeDependency#getTargetNode()
	 * @see #getPotentialNodeDependency()
	 * @generated
	 */
	EReference getPotentialNodeDependency_TargetNode();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.rulebase.PotentialEdgeDependency <em>Potential Edge Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Edge Dependency</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialEdgeDependency
	 * @generated
	 */
	EClass getPotentialEdgeDependency();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.difference.rulebase.PotentialEdgeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialEdgeDependency#getRuleBase()
	 * @see #getPotentialEdgeDependency()
	 * @generated
	 */
	EReference getPotentialEdgeDependency_RuleBase();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.PotentialEdgeDependency#getSourceEdge <em>Source Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Edge</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialEdgeDependency#getSourceEdge()
	 * @see #getPotentialEdgeDependency()
	 * @generated
	 */
	EReference getPotentialEdgeDependency_SourceEdge();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.PotentialEdgeDependency#getTargetEdge <em>Target Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Edge</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialEdgeDependency#getTargetEdge()
	 * @see #getPotentialEdgeDependency()
	 * @generated
	 */
	EReference getPotentialEdgeDependency_TargetEdge();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.rulebase.PotentialAttributeDependency <em>Potential Attribute Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Potential Attribute Dependency</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialAttributeDependency
	 * @generated
	 */
	EClass getPotentialAttributeDependency();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.difference.rulebase.PotentialAttributeDependency#getRuleBase <em>Rule Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Rule Base</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialAttributeDependency#getRuleBase()
	 * @see #getPotentialAttributeDependency()
	 * @generated
	 */
	EReference getPotentialAttributeDependency_RuleBase();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.PotentialAttributeDependency#getSourceAttribute <em>Source Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Attribute</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialAttributeDependency#getSourceAttribute()
	 * @see #getPotentialAttributeDependency()
	 * @generated
	 */
	EReference getPotentialAttributeDependency_SourceAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.PotentialAttributeDependency#getTargetAttribute <em>Target Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Attribute</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialAttributeDependency#getTargetAttribute()
	 * @see #getPotentialAttributeDependency()
	 * @generated
	 */
	EReference getPotentialAttributeDependency_TargetAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.PotentialAttributeDependency#getSourceNode <em>Source Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Node</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialAttributeDependency#getSourceNode()
	 * @see #getPotentialAttributeDependency()
	 * @generated
	 */
	EReference getPotentialAttributeDependency_SourceNode();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.PotentialAttributeDependency#getTargetNode <em>Target Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Node</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialAttributeDependency#getTargetNode()
	 * @see #getPotentialAttributeDependency()
	 * @generated
	 */
	EReference getPotentialAttributeDependency_TargetNode();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.rulebase.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.sidiff.difference.rulebase.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.rulebase.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.difference.rulebase.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.rulebase.Parameter#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see org.sidiff.difference.rulebase.Parameter#getDirection()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Direction();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.rulebase.Parameter#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.sidiff.difference.rulebase.Parameter#getKind()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Kind();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.rulebase.Parameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.difference.rulebase.Parameter#getType()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.rulebase.Parameter#isMulti <em>Multi</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multi</em>'.
	 * @see org.sidiff.difference.rulebase.Parameter#isMulti()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Multi();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.difference.rulebase.PotentialDependencyKind <em>Potential Dependency Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Potential Dependency Kind</em>'.
	 * @see org.sidiff.difference.rulebase.PotentialDependencyKind
	 * @generated
	 */
	EEnum getPotentialDependencyKind();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.difference.rulebase.ParameterDirection <em>Parameter Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Parameter Direction</em>'.
	 * @see org.sidiff.difference.rulebase.ParameterDirection
	 * @generated
	 */
	EEnum getParameterDirection();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.difference.rulebase.ParameterKind <em>Parameter Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Parameter Kind</em>'.
	 * @see org.sidiff.difference.rulebase.ParameterKind
	 * @generated
	 */
	EEnum getParameterKind();

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
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl <em>Rule Base</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.RuleBaseImpl
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getRuleBase()
		 * @generated
		 */
		EClass RULE_BASE = eINSTANCE.getRuleBase();

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
		 * The meta object literal for the '<em><b>Characteristic Document Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_BASE__CHARACTERISTIC_DOCUMENT_TYPE = eINSTANCE.getRuleBase_CharacteristicDocumentType();

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
		 * The meta object literal for the '<em><b>Recognition Rules</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE__RECOGNITION_RULES = eINSTANCE.getRuleBase_RecognitionRules();

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
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.EditRuleImpl <em>Edit Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.EditRuleImpl
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getEditRule()
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
		 * The meta object literal for the '<em><b>Recognition Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDIT_RULE__RECOGNITION_RULE = eINSTANCE.getEditRule_RecognitionRule();

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
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl <em>Recognition Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.RecognitionRuleImpl
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getRecognitionRule()
		 * @generated
		 */
		EClass RECOGNITION_RULE = eINSTANCE.getRecognitionRule();

		/**
		 * The meta object literal for the '<em><b>Recognition Main Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECOGNITION_RULE__RECOGNITION_MAIN_UNIT = eINSTANCE.getRecognitionRule_RecognitionMainUnit();

		/**
		 * The meta object literal for the '<em><b>Edit Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECOGNITION_RULE__EDIT_RULE = eINSTANCE.getRecognitionRule_EditRule();

		/**
		 * The meta object literal for the '<em><b>Rule Base Item</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECOGNITION_RULE__RULE_BASE_ITEM = eINSTANCE.getRecognitionRule_RuleBaseItem();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.TraceImpl <em>Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.TraceImpl
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getTrace()
		 * @generated
		 */
		EClass TRACE = eINSTANCE.getTrace();

		/**
		 * The meta object literal for the '<em><b>Edit Rule Trace</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__EDIT_RULE_TRACE = eINSTANCE.getTrace_EditRuleTrace();

		/**
		 * The meta object literal for the '<em><b>Recognition Rule Trace</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__RECOGNITION_RULE_TRACE = eINSTANCE.getTrace_RecognitionRuleTrace();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.RuleBaseItemImpl <em>Rule Base Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.RuleBaseItemImpl
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getRuleBaseItem()
		 * @generated
		 */
		EClass RULE_BASE_ITEM = eINSTANCE.getRuleBaseItem();

		/**
		 * The meta object literal for the '<em><b>Active</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_BASE_ITEM__ACTIVE = eINSTANCE.getRuleBaseItem_Active();

		/**
		 * The meta object literal for the '<em><b>Edit Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE_ITEM__EDIT_RULE = eINSTANCE.getRuleBaseItem_EditRule();

		/**
		 * The meta object literal for the '<em><b>Recognition Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE_ITEM__RECOGNITION_RULE = eINSTANCE.getRuleBaseItem_RecognitionRule();

		/**
		 * The meta object literal for the '<em><b>Traces B</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE_ITEM__TRACES_B = eINSTANCE.getRuleBaseItem_TracesB();

		/**
		 * The meta object literal for the '<em><b>Traces A</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE_ITEM__TRACES_A = eINSTANCE.getRuleBaseItem_TracesA();

		/**
		 * The meta object literal for the '<em><b>Rule Base</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASE_ITEM__RULE_BASE = eINSTANCE.getRuleBaseItem_RuleBase();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.PotentialDependencyImpl <em>Potential Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.PotentialDependencyImpl
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getPotentialDependency()
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
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POTENTIAL_DEPENDENCY__KIND = eINSTANCE.getPotentialDependency_Kind();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.PotentialNodeDependencyImpl <em>Potential Node Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.PotentialNodeDependencyImpl
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getPotentialNodeDependency()
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
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.PotentialEdgeDependencyImpl <em>Potential Edge Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.PotentialEdgeDependencyImpl
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getPotentialEdgeDependency()
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
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.PotentialAttributeDependencyImpl <em>Potential Attribute Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.PotentialAttributeDependencyImpl
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getPotentialAttributeDependency()
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
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.ParameterImpl
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getParameter()
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
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.PotentialDependencyKind <em>Potential Dependency Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.PotentialDependencyKind
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getPotentialDependencyKind()
		 * @generated
		 */
		EEnum POTENTIAL_DEPENDENCY_KIND = eINSTANCE.getPotentialDependencyKind();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.ParameterDirection <em>Parameter Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.ParameterDirection
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getParameterDirection()
		 * @generated
		 */
		EEnum PARAMETER_DIRECTION = eINSTANCE.getParameterDirection();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.ParameterKind <em>Parameter Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.ParameterKind
		 * @see org.sidiff.difference.rulebase.impl.RulebasePackageImpl#getParameterKind()
		 * @generated
		 */
		EEnum PARAMETER_KIND = eINSTANCE.getParameterKind();

	}

} //RulebasePackage
