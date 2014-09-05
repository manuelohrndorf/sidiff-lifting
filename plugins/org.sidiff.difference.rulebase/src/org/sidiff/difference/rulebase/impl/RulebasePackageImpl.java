/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.Parameter;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.difference.rulebase.ParameterKind;
import org.sidiff.difference.rulebase.PotentialAttributeDependency;
import org.sidiff.difference.rulebase.PotentialDependency;
import org.sidiff.difference.rulebase.PotentialDependencyKind;
import org.sidiff.difference.rulebase.PotentialEdgeDependency;
import org.sidiff.difference.rulebase.PotentialNodeDependency;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.RulebaseFactory;
import org.sidiff.difference.rulebase.RulebasePackage;
import org.sidiff.difference.rulebase.Trace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RulebasePackageImpl extends EPackageImpl implements RulebasePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleBaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass editRuleEClass = null;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleBaseItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialNodeDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialEdgeDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass potentialAttributeDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum potentialDependencyKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum parameterDirectionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum parameterKindEEnum = null;

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
	 * @see org.sidiff.difference.rulebase.RulebasePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RulebasePackageImpl() {
		super(eNS_URI, RulebaseFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RulebasePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RulebasePackage init() {
		if (isInited) return (RulebasePackage)EPackage.Registry.INSTANCE.getEPackage(RulebasePackage.eNS_URI);

		// Obtain or create and register package
		RulebasePackageImpl theRulebasePackage = (RulebasePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RulebasePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RulebasePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		HenshinPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRulebasePackage.createPackageContents();

		// Initialize created meta-data
		theRulebasePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRulebasePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RulebasePackage.eNS_URI, theRulebasePackage);
		return theRulebasePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleBase() {
		return ruleBaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleBase_Name() {
		return (EAttribute)ruleBaseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleBase_DocumentTypes() {
		return (EAttribute)ruleBaseEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleBase_EditRuleFolder() {
		return (EAttribute)ruleBaseEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleBase_RecognitionRuleFolder() {
		return (EAttribute)ruleBaseEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_Items() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_EditRules() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_RecognitionRules() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_PotentialNodeDependencies() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_PotentialEdgeDependencies() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBase_PotentialAttributeDependencies() {
		return (EReference)ruleBaseEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEditRule() {
		return editRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRule_RecognitionRule() {
		return (EReference)editRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRule_RuleBaseItem() {
		return (EReference)editRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRule_Parameters() {
		return (EReference)editRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEditRule_UseDerivedFeatures() {
		return (EAttribute)editRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEditRule_ExecuteMainUnit() {
		return (EReference)editRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRecognitionRule() {
		return recognitionRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRecognitionRule_RecognitionMainUnit() {
		return (EReference)recognitionRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRecognitionRule_EditRule() {
		return (EReference)recognitionRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRecognitionRule_RuleBaseItem() {
		return (EReference)recognitionRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTrace() {
		return traceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTrace_EditRuleTrace() {
		return (EReference)traceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTrace_RecognitionRuleTrace() {
		return (EReference)traceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleBaseItem() {
		return ruleBaseItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBaseItem_EditRule() {
		return (EReference)ruleBaseItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBaseItem_RecognitionRule() {
		return (EReference)ruleBaseItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBaseItem_TracesB() {
		return (EReference)ruleBaseItemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBaseItem_TracesA() {
		return (EReference)ruleBaseItemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleBaseItem_RuleBase() {
		return (EReference)ruleBaseItemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleBaseItem_Active() {
		return (EAttribute)ruleBaseItemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialDependency() {
		return potentialDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialDependency_SourceRule() {
		return (EReference)potentialDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialDependency_TargetRule() {
		return (EReference)potentialDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPotentialDependency_Kind() {
		return (EAttribute)potentialDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialNodeDependency() {
		return potentialNodeDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialNodeDependency_RuleBase() {
		return (EReference)potentialNodeDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialNodeDependency_SourceNode() {
		return (EReference)potentialNodeDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialNodeDependency_TargetNode() {
		return (EReference)potentialNodeDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialEdgeDependency() {
		return potentialEdgeDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialEdgeDependency_RuleBase() {
		return (EReference)potentialEdgeDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialEdgeDependency_SourceEdge() {
		return (EReference)potentialEdgeDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialEdgeDependency_TargetEdge() {
		return (EReference)potentialEdgeDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPotentialAttributeDependency() {
		return potentialAttributeDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeDependency_RuleBase() {
		return (EReference)potentialAttributeDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeDependency_SourceAttribute() {
		return (EReference)potentialAttributeDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeDependency_TargetAttribute() {
		return (EReference)potentialAttributeDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeDependency_SourceNode() {
		return (EReference)potentialAttributeDependencyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPotentialAttributeDependency_TargetNode() {
		return (EReference)potentialAttributeDependencyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Name() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Direction() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Kind() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameter_Type() {
		return (EReference)parameterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Multi() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPotentialDependencyKind() {
		return potentialDependencyKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getParameterDirection() {
		return parameterDirectionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getParameterKind() {
		return parameterKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulebaseFactory getRulebaseFactory() {
		return (RulebaseFactory)getEFactoryInstance();
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
		ruleBaseEClass = createEClass(RULE_BASE);
		createEAttribute(ruleBaseEClass, RULE_BASE__NAME);
		createEReference(ruleBaseEClass, RULE_BASE__ITEMS);
		createEReference(ruleBaseEClass, RULE_BASE__EDIT_RULES);
		createEReference(ruleBaseEClass, RULE_BASE__RECOGNITION_RULES);
		createEReference(ruleBaseEClass, RULE_BASE__POTENTIAL_NODE_DEPENDENCIES);
		createEReference(ruleBaseEClass, RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES);
		createEReference(ruleBaseEClass, RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES);
		createEAttribute(ruleBaseEClass, RULE_BASE__DOCUMENT_TYPES);
		createEAttribute(ruleBaseEClass, RULE_BASE__EDIT_RULE_FOLDER);
		createEAttribute(ruleBaseEClass, RULE_BASE__RECOGNITION_RULE_FOLDER);

		editRuleEClass = createEClass(EDIT_RULE);
		createEReference(editRuleEClass, EDIT_RULE__EXECUTE_MAIN_UNIT);
		createEReference(editRuleEClass, EDIT_RULE__RECOGNITION_RULE);
		createEReference(editRuleEClass, EDIT_RULE__RULE_BASE_ITEM);
		createEReference(editRuleEClass, EDIT_RULE__PARAMETERS);
		createEAttribute(editRuleEClass, EDIT_RULE__USE_DERIVED_FEATURES);

		recognitionRuleEClass = createEClass(RECOGNITION_RULE);
		createEReference(recognitionRuleEClass, RECOGNITION_RULE__RECOGNITION_MAIN_UNIT);
		createEReference(recognitionRuleEClass, RECOGNITION_RULE__EDIT_RULE);
		createEReference(recognitionRuleEClass, RECOGNITION_RULE__RULE_BASE_ITEM);

		traceEClass = createEClass(TRACE);
		createEReference(traceEClass, TRACE__EDIT_RULE_TRACE);
		createEReference(traceEClass, TRACE__RECOGNITION_RULE_TRACE);

		ruleBaseItemEClass = createEClass(RULE_BASE_ITEM);
		createEReference(ruleBaseItemEClass, RULE_BASE_ITEM__EDIT_RULE);
		createEReference(ruleBaseItemEClass, RULE_BASE_ITEM__RECOGNITION_RULE);
		createEReference(ruleBaseItemEClass, RULE_BASE_ITEM__TRACES_B);
		createEReference(ruleBaseItemEClass, RULE_BASE_ITEM__TRACES_A);
		createEReference(ruleBaseItemEClass, RULE_BASE_ITEM__RULE_BASE);
		createEAttribute(ruleBaseItemEClass, RULE_BASE_ITEM__ACTIVE);

		potentialDependencyEClass = createEClass(POTENTIAL_DEPENDENCY);
		createEAttribute(potentialDependencyEClass, POTENTIAL_DEPENDENCY__KIND);
		createEReference(potentialDependencyEClass, POTENTIAL_DEPENDENCY__SOURCE_RULE);
		createEReference(potentialDependencyEClass, POTENTIAL_DEPENDENCY__TARGET_RULE);

		potentialNodeDependencyEClass = createEClass(POTENTIAL_NODE_DEPENDENCY);
		createEReference(potentialNodeDependencyEClass, POTENTIAL_NODE_DEPENDENCY__RULE_BASE);
		createEReference(potentialNodeDependencyEClass, POTENTIAL_NODE_DEPENDENCY__SOURCE_NODE);
		createEReference(potentialNodeDependencyEClass, POTENTIAL_NODE_DEPENDENCY__TARGET_NODE);

		potentialEdgeDependencyEClass = createEClass(POTENTIAL_EDGE_DEPENDENCY);
		createEReference(potentialEdgeDependencyEClass, POTENTIAL_EDGE_DEPENDENCY__RULE_BASE);
		createEReference(potentialEdgeDependencyEClass, POTENTIAL_EDGE_DEPENDENCY__SOURCE_EDGE);
		createEReference(potentialEdgeDependencyEClass, POTENTIAL_EDGE_DEPENDENCY__TARGET_EDGE);

		potentialAttributeDependencyEClass = createEClass(POTENTIAL_ATTRIBUTE_DEPENDENCY);
		createEReference(potentialAttributeDependencyEClass, POTENTIAL_ATTRIBUTE_DEPENDENCY__RULE_BASE);
		createEReference(potentialAttributeDependencyEClass, POTENTIAL_ATTRIBUTE_DEPENDENCY__SOURCE_ATTRIBUTE);
		createEReference(potentialAttributeDependencyEClass, POTENTIAL_ATTRIBUTE_DEPENDENCY__TARGET_ATTRIBUTE);
		createEReference(potentialAttributeDependencyEClass, POTENTIAL_ATTRIBUTE_DEPENDENCY__SOURCE_NODE);
		createEReference(potentialAttributeDependencyEClass, POTENTIAL_ATTRIBUTE_DEPENDENCY__TARGET_NODE);

		parameterEClass = createEClass(PARAMETER);
		createEAttribute(parameterEClass, PARAMETER__NAME);
		createEAttribute(parameterEClass, PARAMETER__DIRECTION);
		createEAttribute(parameterEClass, PARAMETER__KIND);
		createEReference(parameterEClass, PARAMETER__TYPE);
		createEAttribute(parameterEClass, PARAMETER__MULTI);

		// Create enums
		potentialDependencyKindEEnum = createEEnum(POTENTIAL_DEPENDENCY_KIND);
		parameterDirectionEEnum = createEEnum(PARAMETER_DIRECTION);
		parameterKindEEnum = createEEnum(PARAMETER_KIND);
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
		HenshinPackage theHenshinPackage = (HenshinPackage)EPackage.Registry.INSTANCE.getEPackage(HenshinPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		potentialNodeDependencyEClass.getESuperTypes().add(this.getPotentialDependency());
		potentialEdgeDependencyEClass.getESuperTypes().add(this.getPotentialDependency());
		potentialAttributeDependencyEClass.getESuperTypes().add(this.getPotentialDependency());

		// Initialize classes and features; add operations and parameters
		initEClass(ruleBaseEClass, RuleBase.class, "RuleBase", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleBase_Name(), ecorePackage.getEString(), "name", null, 0, 1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_Items(), this.getRuleBaseItem(), this.getRuleBaseItem_RuleBase(), "items", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_EditRules(), this.getEditRule(), null, "editRules", null, 0, -1, RuleBase.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_RecognitionRules(), this.getRecognitionRule(), null, "recognitionRules", null, 0, -1, RuleBase.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_PotentialNodeDependencies(), this.getPotentialNodeDependency(), this.getPotentialNodeDependency_RuleBase(), "potentialNodeDependencies", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_PotentialEdgeDependencies(), this.getPotentialEdgeDependency(), this.getPotentialEdgeDependency_RuleBase(), "potentialEdgeDependencies", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBase_PotentialAttributeDependencies(), this.getPotentialAttributeDependency(), this.getPotentialAttributeDependency_RuleBase(), "potentialAttributeDependencies", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleBase_DocumentTypes(), ecorePackage.getEString(), "documentTypes", null, 0, -1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleBase_EditRuleFolder(), ecorePackage.getEString(), "editRuleFolder", null, 0, 1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleBase_RecognitionRuleFolder(), ecorePackage.getEString(), "recognitionRuleFolder", null, 0, 1, RuleBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(editRuleEClass, EditRule.class, "EditRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEditRule_ExecuteMainUnit(), theHenshinPackage.getUnit(), null, "executeMainUnit", null, 0, 1, EditRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEditRule_RecognitionRule(), this.getRecognitionRule(), this.getRecognitionRule_EditRule(), "recognitionRule", null, 0, 1, EditRule.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEditRule_RuleBaseItem(), this.getRuleBaseItem(), this.getRuleBaseItem_EditRule(), "ruleBaseItem", null, 0, 1, EditRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEditRule_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, EditRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEditRule_UseDerivedFeatures(), ecorePackage.getEBoolean(), "useDerivedFeatures", null, 0, 1, EditRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(editRuleEClass, theHenshinPackage.getModule(), "getExecuteModule", 0, 1, IS_UNIQUE, IS_ORDERED);

		EOperation op = addEOperation(editRuleEClass, this.getParameter(), "getParameterByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(recognitionRuleEClass, RecognitionRule.class, "RecognitionRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRecognitionRule_RecognitionMainUnit(), theHenshinPackage.getRule(), null, "recognitionMainUnit", null, 0, 1, RecognitionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRecognitionRule_EditRule(), this.getEditRule(), this.getEditRule_RecognitionRule(), "editRule", null, 0, 1, RecognitionRule.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getRecognitionRule_RuleBaseItem(), this.getRuleBaseItem(), this.getRuleBaseItem_RecognitionRule(), "ruleBaseItem", null, 0, 1, RecognitionRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(recognitionRuleEClass, theHenshinPackage.getModule(), "getRecognitionModule", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(traceEClass, Trace.class, "Trace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrace_EditRuleTrace(), theHenshinPackage.getNode(), null, "editRuleTrace", null, 0, 1, Trace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTrace_RecognitionRuleTrace(), theHenshinPackage.getNode(), null, "recognitionRuleTrace", null, 0, 1, Trace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleBaseItemEClass, RuleBaseItem.class, "RuleBaseItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleBaseItem_EditRule(), this.getEditRule(), this.getEditRule_RuleBaseItem(), "editRule", null, 0, 1, RuleBaseItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBaseItem_RecognitionRule(), this.getRecognitionRule(), this.getRecognitionRule_RuleBaseItem(), "recognitionRule", null, 0, 1, RuleBaseItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBaseItem_TracesB(), this.getTrace(), null, "tracesB", null, 0, -1, RuleBaseItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBaseItem_TracesA(), this.getTrace(), null, "tracesA", null, 0, -1, RuleBaseItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleBaseItem_RuleBase(), this.getRuleBase(), this.getRuleBase_Items(), "ruleBase", null, 0, 1, RuleBaseItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleBaseItem_Active(), ecorePackage.getEBoolean(), "active", null, 0, 1, RuleBaseItem.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(potentialDependencyEClass, PotentialDependency.class, "PotentialDependency", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPotentialDependency_Kind(), this.getPotentialDependencyKind(), "kind", null, 0, 1, PotentialDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialDependency_SourceRule(), this.getEditRule(), null, "sourceRule", null, 1, 1, PotentialDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialDependency_TargetRule(), this.getEditRule(), null, "targetRule", null, 1, 1, PotentialDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(potentialNodeDependencyEClass, PotentialNodeDependency.class, "PotentialNodeDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPotentialNodeDependency_RuleBase(), this.getRuleBase(), this.getRuleBase_PotentialNodeDependencies(), "ruleBase", null, 0, 1, PotentialNodeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialNodeDependency_SourceNode(), theHenshinPackage.getNode(), null, "sourceNode", null, 0, 1, PotentialNodeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialNodeDependency_TargetNode(), theHenshinPackage.getNode(), null, "targetNode", null, 0, 1, PotentialNodeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(potentialEdgeDependencyEClass, PotentialEdgeDependency.class, "PotentialEdgeDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPotentialEdgeDependency_RuleBase(), this.getRuleBase(), this.getRuleBase_PotentialEdgeDependencies(), "ruleBase", null, 0, 1, PotentialEdgeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialEdgeDependency_SourceEdge(), theHenshinPackage.getEdge(), null, "sourceEdge", null, 0, 1, PotentialEdgeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialEdgeDependency_TargetEdge(), theHenshinPackage.getEdge(), null, "targetEdge", null, 0, 1, PotentialEdgeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(potentialAttributeDependencyEClass, PotentialAttributeDependency.class, "PotentialAttributeDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPotentialAttributeDependency_RuleBase(), this.getRuleBase(), this.getRuleBase_PotentialAttributeDependencies(), "ruleBase", null, 0, 1, PotentialAttributeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeDependency_SourceAttribute(), theHenshinPackage.getAttribute(), null, "sourceAttribute", null, 0, 1, PotentialAttributeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeDependency_TargetAttribute(), theHenshinPackage.getAttribute(), null, "targetAttribute", null, 0, 1, PotentialAttributeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeDependency_SourceNode(), theHenshinPackage.getNode(), null, "sourceNode", null, 0, 1, PotentialAttributeDependency.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getPotentialAttributeDependency_TargetNode(), theHenshinPackage.getNode(), null, "targetNode", null, 0, 1, PotentialAttributeDependency.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameter_Name(), ecorePackage.getEString(), "name", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Direction(), this.getParameterDirection(), "direction", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Kind(), this.getParameterKind(), "kind", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParameter_Type(), ecorePackage.getEClassifier(), null, "type", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Multi(), ecorePackage.getEBoolean(), "multi", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(potentialDependencyKindEEnum, PotentialDependencyKind.class, "PotentialDependencyKind");
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.USE_DELETE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.CREATE_USE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.CHANGE_USE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.USE_CHANGE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.FORBID_CREATE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.DELETE_FORBID);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.FORBID_CHANGE);
		addEEnumLiteral(potentialDependencyKindEEnum, PotentialDependencyKind.CHANGE_FORBID);

		initEEnum(parameterDirectionEEnum, ParameterDirection.class, "ParameterDirection");
		addEEnumLiteral(parameterDirectionEEnum, ParameterDirection.IN);
		addEEnumLiteral(parameterDirectionEEnum, ParameterDirection.OUT);

		initEEnum(parameterKindEEnum, ParameterKind.class, "ParameterKind");
		addEEnumLiteral(parameterKindEEnum, ParameterKind.OBJECT);
		addEEnumLiteral(parameterKindEEnum, ParameterKind.VALUE);

		// Create resource
		createResource(eNS_URI);
	}

} //RulebasePackageImpl
