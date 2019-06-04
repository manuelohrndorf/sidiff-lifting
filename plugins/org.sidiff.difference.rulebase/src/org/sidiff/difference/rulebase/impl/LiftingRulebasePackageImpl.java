/**
 */
package org.sidiff.difference.rulebase.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.sidiff.difference.rulebase.LiftingRulebaseFactory;
import org.sidiff.difference.rulebase.LiftingRulebasePackage;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.Trace;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LiftingRulebasePackageImpl extends EPackageImpl implements LiftingRulebasePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass recognitionRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass traceEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.sidiff.difference.rulebase.LiftingRulebasePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LiftingRulebasePackageImpl() {
		super(eNS_URI, LiftingRulebaseFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link LiftingRulebasePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LiftingRulebasePackage init() {
		if (isInited) return (LiftingRulebasePackage)EPackage.Registry.INSTANCE.getEPackage(LiftingRulebasePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredLiftingRulebasePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		LiftingRulebasePackageImpl theLiftingRulebasePackage = registeredLiftingRulebasePackage instanceof LiftingRulebasePackageImpl ? (LiftingRulebasePackageImpl)registeredLiftingRulebasePackage : new LiftingRulebasePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		HenshinPackage.eINSTANCE.eClass();
		RulebasePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theLiftingRulebasePackage.createPackageContents();

		// Initialize created meta-data
		theLiftingRulebasePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLiftingRulebasePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LiftingRulebasePackage.eNS_URI, theLiftingRulebasePackage);
		return theLiftingRulebasePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRecognitionRule() {
		return recognitionRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRecognitionRule_RecognitionMainUnit() {
		return (EReference)recognitionRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRecognitionRule_TracesB() {
		return (EReference)recognitionRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRecognitionRule_TracesA() {
		return (EReference)recognitionRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRecognitionRule_RecognitionModule() {
		return (EReference)recognitionRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTrace() {
		return traceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTrace_EditRuleTrace() {
		return (EReference)traceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTrace_RecognitionRuleTrace() {
		return (EReference)traceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LiftingRulebaseFactory getLiftingRulebaseFactory() {
		return (LiftingRulebaseFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		recognitionRuleEClass = createEClass(RECOGNITION_RULE);
		createEReference(recognitionRuleEClass, RECOGNITION_RULE__RECOGNITION_MAIN_UNIT);
		createEReference(recognitionRuleEClass, RECOGNITION_RULE__TRACES_B);
		createEReference(recognitionRuleEClass, RECOGNITION_RULE__TRACES_A);
		createEReference(recognitionRuleEClass, RECOGNITION_RULE__RECOGNITION_MODULE);

		traceEClass = createEClass(TRACE);
		createEReference(traceEClass, TRACE__EDIT_RULE_TRACE);
		createEReference(traceEClass, TRACE__RECOGNITION_RULE_TRACE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		RulebasePackage theRulebasePackage = (RulebasePackage)EPackage.Registry.INSTANCE.getEPackage(RulebasePackage.eNS_URI);
		HenshinPackage theHenshinPackage = (HenshinPackage)EPackage.Registry.INSTANCE.getEPackage(HenshinPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		recognitionRuleEClass.getESuperTypes().add(theRulebasePackage.getEditRuleAttachment());

		// Initialize classes and features; add operations and parameters
		initEClass(recognitionRuleEClass, RecognitionRule.class, "RecognitionRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRecognitionRule_RecognitionMainUnit(), theHenshinPackage.getRule(), null, "recognitionMainUnit", null, 1, 1, RecognitionRule.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRecognitionRule_TracesB(), this.getTrace(), null, "tracesB", null, 0, -1, RecognitionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRecognitionRule_TracesA(), this.getTrace(), null, "tracesA", null, 0, -1, RecognitionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRecognitionRule_RecognitionModule(), theHenshinPackage.getModule(), null, "recognitionModule", null, 1, 1, RecognitionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(traceEClass, Trace.class, "Trace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrace_EditRuleTrace(), theHenshinPackage.getNode(), null, "editRuleTrace", null, 0, 1, Trace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrace_RecognitionRuleTrace(), theHenshinPackage.getNode(), null, "recognitionRuleTrace", null, 0, 1, Trace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //LiftingRulebasePackageImpl
