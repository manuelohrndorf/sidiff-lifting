/**
 */
package org.sidiff.slicing.configuration.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.helper.OCLHelper;
import org.sidiff.slicing.configuration.ConfigurationFactory;
import org.sidiff.slicing.configuration.ConfigurationPackage;
import org.sidiff.slicing.configuration.Constraint;
import org.sidiff.slicing.configuration.IConstraintInterpreter;
import org.sidiff.slicing.configuration.OCLConstraintInterpreter;
import org.sidiff.slicing.configuration.SlicedEClass;
import org.sidiff.slicing.configuration.SlicingConfiguration;
import org.sidiff.slicing.configuration.SlicingMode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConfigurationPackageImpl extends EPackageImpl implements ConfigurationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass slicingConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass slicedEClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iConstraintInterpreterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass oclConstraintInterpreterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum slicingModeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType eoclEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType eoclHelperEDataType = null;

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
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ConfigurationPackageImpl() {
		super(eNS_URI, ConfigurationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ConfigurationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ConfigurationPackage init() {
		if (isInited) return (ConfigurationPackage)EPackage.Registry.INSTANCE.getEPackage(ConfigurationPackage.eNS_URI);

		// Obtain or create and register package
		ConfigurationPackageImpl theConfigurationPackage = (ConfigurationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ConfigurationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ConfigurationPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theConfigurationPackage.createPackageContents();

		// Initialize created meta-data
		theConfigurationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theConfigurationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConfigurationPackage.eNS_URI, theConfigurationPackage);
		return theConfigurationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSlicingConfiguration() {
		return slicingConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSlicingConfiguration_Name() {
		return (EAttribute)slicingConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSlicingConfiguration_Description() {
		return (EAttribute)slicingConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSlicingConfiguration_DocumentTypes() {
		return (EAttribute)slicingConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlicingConfiguration_Imports() {
		return (EReference)slicingConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSlicingConfiguration_SlicingMode() {
		return (EAttribute)slicingConfigurationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlicingConfiguration_SlicedEClasses() {
		return (EReference)slicingConfigurationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSlicingConfiguration_OppositeSlicedEClassType() {
		return (EAttribute)slicingConfigurationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlicingConfiguration_Constraints() {
		return (EReference)slicingConfigurationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlicingConfiguration_Constraintinterpreter() {
		return (EReference)slicingConfigurationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSlicedEClass() {
		return slicedEClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlicedEClass_SlicingConfiguration() {
		return (EReference)slicedEClassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlicedEClass_Type() {
		return (EReference)slicedEClassEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlicedEClass_Constraints() {
		return (EReference)slicedEClassEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstraint() {
		return constraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConstraint_Expression() {
		return (EAttribute)constraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIConstraintInterpreter() {
		return iConstraintInterpreterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIConstraintInterpreter_Key() {
		return (EAttribute)iConstraintInterpreterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getIConstraintInterpreter__Evaluate__Constraint_EObject() {
		return iConstraintInterpreterEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getIConstraintInterpreter__Evaluate__EList_EObject() {
		return iConstraintInterpreterEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOCLConstraintInterpreter() {
		return oclConstraintInterpreterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOCLConstraintInterpreter_Ocl() {
		return (EAttribute)oclConstraintInterpreterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOCLConstraintInterpreter_OclHelper() {
		return (EAttribute)oclConstraintInterpreterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSlicingMode() {
		return slicingModeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getEOCL() {
		return eoclEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getEOCLHelper() {
		return eoclHelperEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationFactory getConfigurationFactory() {
		return (ConfigurationFactory)getEFactoryInstance();
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
		slicingConfigurationEClass = createEClass(SLICING_CONFIGURATION);
		createEAttribute(slicingConfigurationEClass, SLICING_CONFIGURATION__NAME);
		createEAttribute(slicingConfigurationEClass, SLICING_CONFIGURATION__DESCRIPTION);
		createEAttribute(slicingConfigurationEClass, SLICING_CONFIGURATION__DOCUMENT_TYPES);
		createEReference(slicingConfigurationEClass, SLICING_CONFIGURATION__IMPORTS);
		createEAttribute(slicingConfigurationEClass, SLICING_CONFIGURATION__SLICING_MODE);
		createEReference(slicingConfigurationEClass, SLICING_CONFIGURATION__SLICED_ECLASSES);
		createEAttribute(slicingConfigurationEClass, SLICING_CONFIGURATION__OPPOSITE_SLICED_ECLASS_TYPE);
		createEReference(slicingConfigurationEClass, SLICING_CONFIGURATION__CONSTRAINTS);
		createEReference(slicingConfigurationEClass, SLICING_CONFIGURATION__CONSTRAINTINTERPRETER);

		slicedEClassEClass = createEClass(SLICED_ECLASS);
		createEReference(slicedEClassEClass, SLICED_ECLASS__SLICING_CONFIGURATION);
		createEReference(slicedEClassEClass, SLICED_ECLASS__TYPE);
		createEReference(slicedEClassEClass, SLICED_ECLASS__CONSTRAINTS);

		constraintEClass = createEClass(CONSTRAINT);
		createEAttribute(constraintEClass, CONSTRAINT__EXPRESSION);

		iConstraintInterpreterEClass = createEClass(ICONSTRAINT_INTERPRETER);
		createEAttribute(iConstraintInterpreterEClass, ICONSTRAINT_INTERPRETER__KEY);
		createEOperation(iConstraintInterpreterEClass, ICONSTRAINT_INTERPRETER___EVALUATE__CONSTRAINT_EOBJECT);
		createEOperation(iConstraintInterpreterEClass, ICONSTRAINT_INTERPRETER___EVALUATE__ELIST_EOBJECT);

		oclConstraintInterpreterEClass = createEClass(OCL_CONSTRAINT_INTERPRETER);
		createEAttribute(oclConstraintInterpreterEClass, OCL_CONSTRAINT_INTERPRETER__OCL);
		createEAttribute(oclConstraintInterpreterEClass, OCL_CONSTRAINT_INTERPRETER__OCL_HELPER);

		// Create enums
		slicingModeEEnum = createEEnum(SLICING_MODE);

		// Create data types
		eoclEDataType = createEDataType(EOCL);
		eoclHelperEDataType = createEDataType(EOCL_HELPER);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		oclConstraintInterpreterEClass.getESuperTypes().add(this.getIConstraintInterpreter());

		// Initialize classes, features, and operations; add parameters
		initEClass(slicingConfigurationEClass, SlicingConfiguration.class, "SlicingConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSlicingConfiguration_Name(), ecorePackage.getEString(), "name", null, 1, 1, SlicingConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSlicingConfiguration_Description(), ecorePackage.getEString(), "description", null, 0, 1, SlicingConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSlicingConfiguration_DocumentTypes(), ecorePackage.getEString(), "documentTypes", null, 0, -1, SlicingConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSlicingConfiguration_Imports(), theEcorePackage.getEPackage(), null, "imports", null, 0, -1, SlicingConfiguration.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getSlicingConfiguration_SlicingMode(), this.getSlicingMode(), "slicingMode", "OPTIMISTIC", 1, 1, SlicingConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSlicingConfiguration_SlicedEClasses(), this.getSlicedEClass(), this.getSlicedEClass_SlicingConfiguration(), "slicedEClasses", null, 0, -1, SlicingConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEAttribute(getSlicingConfiguration_OppositeSlicedEClassType(), g1, "oppositeSlicedEClassType", null, 0, 1, SlicingConfiguration.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getSlicingConfiguration_Constraints(), this.getConstraint(), null, "constraints", null, 0, -1, SlicingConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSlicingConfiguration_Constraintinterpreter(), this.getIConstraintInterpreter(), null, "constraintinterpreter", null, 0, 1, SlicingConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(slicedEClassEClass, SlicedEClass.class, "SlicedEClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSlicedEClass_SlicingConfiguration(), this.getSlicingConfiguration(), this.getSlicingConfiguration_SlicedEClasses(), "slicingConfiguration", null, 1, 1, SlicedEClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSlicedEClass_Type(), theEcorePackage.getEClass(), null, "type", null, 1, 1, SlicedEClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSlicedEClass_Constraints(), this.getConstraint(), null, "constraints", null, 0, -1, SlicedEClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(constraintEClass, Constraint.class, "Constraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConstraint_Expression(), theEcorePackage.getEString(), "expression", null, 0, 1, Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iConstraintInterpreterEClass, IConstraintInterpreter.class, "IConstraintInterpreter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIConstraintInterpreter_Key(), theEcorePackage.getEString(), "key", null, 0, 1, IConstraintInterpreter.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getIConstraintInterpreter__Evaluate__Constraint_EObject(), theEcorePackage.getEBoolean(), "evaluate", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getConstraint(), "constraint", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getIConstraintInterpreter__Evaluate__EList_EObject(), theEcorePackage.getEBoolean(), "evaluate", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(theEcorePackage.getEEList());
		g2 = createEGenericType(this.getConstraint());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "constraints", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(oclConstraintInterpreterEClass, OCLConstraintInterpreter.class, "OCLConstraintInterpreter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOCLConstraintInterpreter_Ocl(), this.getEOCL(), "ocl", null, 1, 1, OCLConstraintInterpreter.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOCLConstraintInterpreter_OclHelper(), this.getEOCLHelper(), "oclHelper", null, 1, 1, OCLConstraintInterpreter.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(slicingModeEEnum, SlicingMode.class, "SlicingMode");
		addEEnumLiteral(slicingModeEEnum, SlicingMode.OPTIMISTIC);
		addEEnumLiteral(slicingModeEEnum, SlicingMode.PESSIMISTIC);

		// Initialize data types
		initEDataType(eoclEDataType, OCL.class, "EOCL", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(eoclHelperEDataType, OCLHelper.class, "EOCLHelper", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ConfigurationPackageImpl
