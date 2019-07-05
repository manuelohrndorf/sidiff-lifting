/**
 */
package org.sidiff.difference.rulebase;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.editrule.rulebase.RulebasePackage;

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
 * @see org.sidiff.difference.rulebase.LiftingRulebaseFactory
 * @model kind="package"
 * @generated
 */
public interface LiftingRulebasePackage extends EPackage {
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
	LiftingRulebasePackage eINSTANCE = org.sidiff.difference.rulebase.impl.LiftingRulebasePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl <em>Recognition Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.RecognitionRuleImpl
	 * @see org.sidiff.difference.rulebase.impl.LiftingRulebasePackageImpl#getRecognitionRule()
	 * @generated
	 */
	int RECOGNITION_RULE = 0;

	/**
	 * The feature id for the '<em><b>Rule Base Item</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOGNITION_RULE__RULE_BASE_ITEM = RulebasePackage.EDIT_RULE_ATTACHMENT__RULE_BASE_ITEM;

	/**
	 * The feature id for the '<em><b>Edit Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOGNITION_RULE__EDIT_RULE = RulebasePackage.EDIT_RULE_ATTACHMENT__EDIT_RULE;

	/**
	 * The feature id for the '<em><b>Recognition Main Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOGNITION_RULE__RECOGNITION_MAIN_UNIT = RulebasePackage.EDIT_RULE_ATTACHMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Traces B</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOGNITION_RULE__TRACES_B = RulebasePackage.EDIT_RULE_ATTACHMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Traces A</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOGNITION_RULE__TRACES_A = RulebasePackage.EDIT_RULE_ATTACHMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Recognition Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOGNITION_RULE_FEATURE_COUNT = RulebasePackage.EDIT_RULE_ATTACHMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.rulebase.impl.TraceImpl <em>Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.rulebase.impl.TraceImpl
	 * @see org.sidiff.difference.rulebase.impl.LiftingRulebasePackageImpl#getTrace()
	 * @generated
	 */
	int TRACE = 1;

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
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.rulebase.RecognitionRule#getTracesB <em>Traces B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Traces B</em>'.
	 * @see org.sidiff.difference.rulebase.RecognitionRule#getTracesB()
	 * @see #getRecognitionRule()
	 * @generated
	 */
	EReference getRecognitionRule_TracesB();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.rulebase.RecognitionRule#getTracesA <em>Traces A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Traces A</em>'.
	 * @see org.sidiff.difference.rulebase.RecognitionRule#getTracesA()
	 * @see #getRecognitionRule()
	 * @generated
	 */
	EReference getRecognitionRule_TracesA();

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
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LiftingRulebaseFactory getLiftingRulebaseFactory();

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
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl <em>Recognition Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.RecognitionRuleImpl
		 * @see org.sidiff.difference.rulebase.impl.LiftingRulebasePackageImpl#getRecognitionRule()
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
		 * The meta object literal for the '<em><b>Traces B</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECOGNITION_RULE__TRACES_B = eINSTANCE.getRecognitionRule_TracesB();

		/**
		 * The meta object literal for the '<em><b>Traces A</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECOGNITION_RULE__TRACES_A = eINSTANCE.getRecognitionRule_TracesA();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.rulebase.impl.TraceImpl <em>Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.rulebase.impl.TraceImpl
		 * @see org.sidiff.difference.rulebase.impl.LiftingRulebasePackageImpl#getTrace()
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

	}

} //LiftingRulebasePackage
