/**
 */
package org.sidiff.slicer.rulebased.slice.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.sidiff.difference.asymmetric.AsymmetricPackage;

import org.sidiff.difference.symmetric.SymmetricPackage;

import org.sidiff.entities.EntitiesPackage;

import org.sidiff.formula.FormulaPackage;

import org.sidiff.matching.model.MatchingModelPackage;

import org.sidiff.slicer.rulebased.slice.ExecutableModelSlice;
import org.sidiff.slicer.rulebased.slice.RuleBasedSliceFactory;
import org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage;

import org.sidiff.slicer.slice.SlicePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RuleBasedSlicePackageImpl extends EPackageImpl implements RuleBasedSlicePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executableModelSliceEClass = null;

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
	 * @see org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RuleBasedSlicePackageImpl() {
		super(eNS_URI, RuleBasedSliceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RuleBasedSlicePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RuleBasedSlicePackage init() {
		if (isInited)
			return (RuleBasedSlicePackage) EPackage.Registry.INSTANCE.getEPackage(RuleBasedSlicePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredRuleBasedSlicePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		RuleBasedSlicePackageImpl theRuleBasedSlicePackage = registeredRuleBasedSlicePackage instanceof RuleBasedSlicePackageImpl
				? (RuleBasedSlicePackageImpl) registeredRuleBasedSlicePackage
				: new RuleBasedSlicePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		AsymmetricPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		EntitiesPackage.eINSTANCE.eClass();
		FormulaPackage.eINSTANCE.eClass();
		MatchingModelPackage.eINSTANCE.eClass();
		SlicePackage.eINSTANCE.eClass();
		SymmetricPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRuleBasedSlicePackage.createPackageContents();

		// Initialize created meta-data
		theRuleBasedSlicePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRuleBasedSlicePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RuleBasedSlicePackage.eNS_URI, theRuleBasedSlicePackage);
		return theRuleBasedSlicePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecutableModelSlice() {
		return executableModelSliceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutableModelSlice_AsymmetricDifference() {
		return (EReference) executableModelSliceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExecutableModelSlice__Serialize__String_boolean() {
		return executableModelSliceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBasedSliceFactory getRuleBasedSliceFactory() {
		return (RuleBasedSliceFactory) getEFactoryInstance();
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
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		executableModelSliceEClass = createEClass(EXECUTABLE_MODEL_SLICE);
		createEReference(executableModelSliceEClass, EXECUTABLE_MODEL_SLICE__ASYMMETRIC_DIFFERENCE);
		createEOperation(executableModelSliceEClass, EXECUTABLE_MODEL_SLICE___SERIALIZE__STRING_BOOLEAN);
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
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		SlicePackage theSlicePackage = (SlicePackage) EPackage.Registry.INSTANCE.getEPackage(SlicePackage.eNS_URI);
		AsymmetricPackage theAsymmetricPackage = (AsymmetricPackage) EPackage.Registry.INSTANCE
				.getEPackage(AsymmetricPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		executableModelSliceEClass.getESuperTypes().add(theSlicePackage.getModelSlice());

		// Initialize classes, features, and operations; add parameters
		initEClass(executableModelSliceEClass, ExecutableModelSlice.class, "ExecutableModelSlice", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExecutableModelSlice_AsymmetricDifference(), theAsymmetricPackage.getAsymmetricDifference(),
				null, "asymmetricDifference", null, 1, 1, ExecutableModelSlice.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getExecutableModelSlice__Serialize__String_boolean(), null, "serialize", 0, 1,
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "path", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "zip", 1, 1, IS_UNIQUE, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //RuleBasedSlicePackageImpl
