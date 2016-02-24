/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel.impl;

import differencemodel.AddObject;
import differencemodel.AddReference;
import differencemodel.AttributeValueChange;
import differencemodel.Change;
import differencemodel.Correspondence;
import differencemodel.Dependency;
import differencemodel.DependencyKind;
import differencemodel.Difference;
import differencemodel.DifferencemodelFactory;
import differencemodel.DifferencemodelPackage;
import differencemodel.ObjectParameterSubstitution;
import differencemodel.ParameterSubstitution;
import differencemodel.RemoveObject;
import differencemodel.RemoveReference;
import differencemodel.SemanticChangeSet;

import differencemodel.ValueParameterSubstitution;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DifferencemodelPackageImpl extends EPackageImpl implements DifferencemodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass differenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removeReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass semanticChangeSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass correspondenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeValueChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterSubstitutionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectParameterSubstitutionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueParameterSubstitutionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dependencyKindEEnum = null;

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
	 * @see differencemodel.DifferencemodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DifferencemodelPackageImpl() {
		super(eNS_URI, DifferencemodelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DifferencemodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DifferencemodelPackage init() {
		if (isInited) return (DifferencemodelPackage)EPackage.Registry.INSTANCE.getEPackage(DifferencemodelPackage.eNS_URI);

		// Obtain or create and register package
		DifferencemodelPackageImpl theDifferencemodelPackage = (DifferencemodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DifferencemodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DifferencemodelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDifferencemodelPackage.createPackageContents();

		// Initialize created meta-data
		theDifferencemodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDifferencemodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DifferencemodelPackage.eNS_URI, theDifferencemodelPackage);
		return theDifferencemodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDifference() {
		return differenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDifference_Changes() {
		return (EReference)differenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDifference_ChangeSets() {
		return (EReference)differenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDifference_Correspondences() {
		return (EReference)differenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDifference_ModelA() {
		return (EAttribute)differenceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDifference_ModelB() {
		return (EAttribute)differenceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDifference_Dependencies() {
		return (EReference)differenceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAddObject() {
		return addObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddObject_Obj() {
		return (EReference)addObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveObject() {
		return removeObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveObject_Obj() {
		return (EReference)removeObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAddReference() {
		return addReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddReference_Src() {
		return (EReference)addReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddReference_Tgt() {
		return (EReference)addReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddReference_Type() {
		return (EReference)addReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoveReference() {
		return removeReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveReference_Src() {
		return (EReference)removeReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveReference_Tgt() {
		return (EReference)removeReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoveReference_Type() {
		return (EReference)removeReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChange() {
		return changeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSemanticChangeSet() {
		return semanticChangeSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSemanticChangeSet_Changes() {
		return (EReference)semanticChangeSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSemanticChangeSet_Name() {
		return (EAttribute)semanticChangeSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSemanticChangeSet_Priority() {
		return (EAttribute)semanticChangeSetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSemanticChangeSet_RefinementLevel() {
		return (EAttribute)semanticChangeSetEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSemanticChangeSet_EditRName() {
		return (EAttribute)semanticChangeSetEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSemanticChangeSet_ParameterSubstitutions() {
		return (EReference)semanticChangeSetEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSemanticChangeSet_RecognitionRName() {
		return (EAttribute)semanticChangeSetEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSemanticChangeSet_Outgoing() {
		return (EReference)semanticChangeSetEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSemanticChangeSet_Incoming() {
		return (EReference)semanticChangeSetEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCorrespondence() {
		return correspondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorrespondence_ObjA() {
		return (EReference)correspondenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorrespondence_ObjB() {
		return (EReference)correspondenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeValueChange() {
		return attributeValueChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeValueChange_ObjA() {
		return (EReference)attributeValueChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeValueChange_ObjB() {
		return (EReference)attributeValueChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeValueChange_Type() {
		return (EReference)attributeValueChangeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterSubstitution() {
		return parameterSubstitutionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterSubstitution_Formal() {
		return (EAttribute)parameterSubstitutionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectParameterSubstitution() {
		return objectParameterSubstitutionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectParameterSubstitution_ActualA() {
		return (EReference)objectParameterSubstitutionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectParameterSubstitution_ActualB() {
		return (EReference)objectParameterSubstitutionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueParameterSubstitution() {
		return valueParameterSubstitutionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValueParameterSubstitution_Actual() {
		return (EAttribute)valueParameterSubstitutionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDependency() {
		return dependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependency_Kind() {
		return (EAttribute)dependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependency_Source() {
		return (EReference)dependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependency_Target() {
		return (EReference)dependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDependencyKind() {
		return dependencyKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifferencemodelFactory getDifferencemodelFactory() {
		return (DifferencemodelFactory)getEFactoryInstance();
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
		differenceEClass = createEClass(DIFFERENCE);
		createEReference(differenceEClass, DIFFERENCE__CHANGES);
		createEReference(differenceEClass, DIFFERENCE__CHANGE_SETS);
		createEReference(differenceEClass, DIFFERENCE__CORRESPONDENCES);
		createEAttribute(differenceEClass, DIFFERENCE__MODEL_A);
		createEAttribute(differenceEClass, DIFFERENCE__MODEL_B);
		createEReference(differenceEClass, DIFFERENCE__DEPENDENCIES);

		addObjectEClass = createEClass(ADD_OBJECT);
		createEReference(addObjectEClass, ADD_OBJECT__OBJ);

		removeObjectEClass = createEClass(REMOVE_OBJECT);
		createEReference(removeObjectEClass, REMOVE_OBJECT__OBJ);

		addReferenceEClass = createEClass(ADD_REFERENCE);
		createEReference(addReferenceEClass, ADD_REFERENCE__SRC);
		createEReference(addReferenceEClass, ADD_REFERENCE__TGT);
		createEReference(addReferenceEClass, ADD_REFERENCE__TYPE);

		removeReferenceEClass = createEClass(REMOVE_REFERENCE);
		createEReference(removeReferenceEClass, REMOVE_REFERENCE__SRC);
		createEReference(removeReferenceEClass, REMOVE_REFERENCE__TGT);
		createEReference(removeReferenceEClass, REMOVE_REFERENCE__TYPE);

		changeEClass = createEClass(CHANGE);

		semanticChangeSetEClass = createEClass(SEMANTIC_CHANGE_SET);
		createEReference(semanticChangeSetEClass, SEMANTIC_CHANGE_SET__CHANGES);
		createEAttribute(semanticChangeSetEClass, SEMANTIC_CHANGE_SET__NAME);
		createEAttribute(semanticChangeSetEClass, SEMANTIC_CHANGE_SET__PRIORITY);
		createEAttribute(semanticChangeSetEClass, SEMANTIC_CHANGE_SET__REFINEMENT_LEVEL);
		createEAttribute(semanticChangeSetEClass, SEMANTIC_CHANGE_SET__EDIT_RNAME);
		createEReference(semanticChangeSetEClass, SEMANTIC_CHANGE_SET__PARAMETER_SUBSTITUTIONS);
		createEAttribute(semanticChangeSetEClass, SEMANTIC_CHANGE_SET__RECOGNITION_RNAME);
		createEReference(semanticChangeSetEClass, SEMANTIC_CHANGE_SET__OUTGOING);
		createEReference(semanticChangeSetEClass, SEMANTIC_CHANGE_SET__INCOMING);

		correspondenceEClass = createEClass(CORRESPONDENCE);
		createEReference(correspondenceEClass, CORRESPONDENCE__OBJ_A);
		createEReference(correspondenceEClass, CORRESPONDENCE__OBJ_B);

		attributeValueChangeEClass = createEClass(ATTRIBUTE_VALUE_CHANGE);
		createEReference(attributeValueChangeEClass, ATTRIBUTE_VALUE_CHANGE__OBJ_A);
		createEReference(attributeValueChangeEClass, ATTRIBUTE_VALUE_CHANGE__OBJ_B);
		createEReference(attributeValueChangeEClass, ATTRIBUTE_VALUE_CHANGE__TYPE);

		parameterSubstitutionEClass = createEClass(PARAMETER_SUBSTITUTION);
		createEAttribute(parameterSubstitutionEClass, PARAMETER_SUBSTITUTION__FORMAL);

		objectParameterSubstitutionEClass = createEClass(OBJECT_PARAMETER_SUBSTITUTION);
		createEReference(objectParameterSubstitutionEClass, OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_A);
		createEReference(objectParameterSubstitutionEClass, OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_B);

		valueParameterSubstitutionEClass = createEClass(VALUE_PARAMETER_SUBSTITUTION);
		createEAttribute(valueParameterSubstitutionEClass, VALUE_PARAMETER_SUBSTITUTION__ACTUAL);

		dependencyEClass = createEClass(DEPENDENCY);
		createEAttribute(dependencyEClass, DEPENDENCY__KIND);
		createEReference(dependencyEClass, DEPENDENCY__SOURCE);
		createEReference(dependencyEClass, DEPENDENCY__TARGET);

		// Create enums
		dependencyKindEEnum = createEEnum(DEPENDENCY_KIND);
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
		addObjectEClass.getESuperTypes().add(this.getChange());
		removeObjectEClass.getESuperTypes().add(this.getChange());
		addReferenceEClass.getESuperTypes().add(this.getChange());
		removeReferenceEClass.getESuperTypes().add(this.getChange());
		attributeValueChangeEClass.getESuperTypes().add(this.getChange());
		objectParameterSubstitutionEClass.getESuperTypes().add(this.getParameterSubstitution());
		valueParameterSubstitutionEClass.getESuperTypes().add(this.getParameterSubstitution());

		// Initialize classes and features; add operations and parameters
		initEClass(differenceEClass, Difference.class, "Difference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDifference_Changes(), this.getChange(), null, "changes", null, 0, -1, Difference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDifference_ChangeSets(), this.getSemanticChangeSet(), null, "changeSets", null, 0, -1, Difference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDifference_Correspondences(), this.getCorrespondence(), null, "correspondences", null, 0, -1, Difference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDifference_ModelA(), ecorePackage.getEResource(), "modelA", null, 0, 1, Difference.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDifference_ModelB(), ecorePackage.getEResource(), "modelB", null, 0, 1, Difference.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDifference_Dependencies(), this.getDependency(), null, "dependencies", null, 0, -1, Difference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(addObjectEClass, AddObject.class, "AddObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAddObject_Obj(), ecorePackage.getEObject(), null, "obj", null, 0, 1, AddObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(removeObjectEClass, RemoveObject.class, "RemoveObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRemoveObject_Obj(), ecorePackage.getEObject(), null, "obj", null, 0, 1, RemoveObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(addReferenceEClass, AddReference.class, "AddReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAddReference_Src(), ecorePackage.getEObject(), null, "src", null, 0, 1, AddReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAddReference_Tgt(), ecorePackage.getEObject(), null, "tgt", null, 0, 1, AddReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAddReference_Type(), theEcorePackage.getEReference(), null, "type", null, 0, 1, AddReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(removeReferenceEClass, RemoveReference.class, "RemoveReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRemoveReference_Src(), ecorePackage.getEObject(), null, "src", null, 0, 1, RemoveReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRemoveReference_Tgt(), ecorePackage.getEObject(), null, "tgt", null, 0, 1, RemoveReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRemoveReference_Type(), theEcorePackage.getEReference(), null, "type", null, 0, 1, RemoveReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(changeEClass, Change.class, "Change", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(semanticChangeSetEClass, SemanticChangeSet.class, "SemanticChangeSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSemanticChangeSet_Changes(), this.getChange(), null, "changes", null, 0, -1, SemanticChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSemanticChangeSet_Name(), theEcorePackage.getEString(), "name", null, 0, 1, SemanticChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSemanticChangeSet_Priority(), ecorePackage.getEInt(), "priority", "0", 0, 1, SemanticChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSemanticChangeSet_RefinementLevel(), ecorePackage.getEInt(), "refinementLevel", "0", 0, 1, SemanticChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSemanticChangeSet_EditRName(), theEcorePackage.getEString(), "editRName", null, 0, 1, SemanticChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSemanticChangeSet_ParameterSubstitutions(), this.getParameterSubstitution(), null, "parameterSubstitutions", null, 0, -1, SemanticChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSemanticChangeSet_RecognitionRName(), theEcorePackage.getEString(), "recognitionRName", null, 0, 1, SemanticChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSemanticChangeSet_Outgoing(), this.getDependency(), this.getDependency_Source(), "outgoing", null, 0, -1, SemanticChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSemanticChangeSet_Incoming(), this.getDependency(), this.getDependency_Target(), "incoming", null, 0, -1, SemanticChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(correspondenceEClass, Correspondence.class, "Correspondence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCorrespondence_ObjA(), ecorePackage.getEObject(), null, "objA", null, 0, 1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCorrespondence_ObjB(), ecorePackage.getEObject(), null, "objB", null, 0, 1, Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeValueChangeEClass, AttributeValueChange.class, "AttributeValueChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeValueChange_ObjA(), ecorePackage.getEObject(), null, "objA", null, 0, 1, AttributeValueChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeValueChange_ObjB(), ecorePackage.getEObject(), null, "objB", null, 0, 1, AttributeValueChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeValueChange_Type(), theEcorePackage.getEAttribute(), null, "type", null, 0, 1, AttributeValueChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterSubstitutionEClass, ParameterSubstitution.class, "ParameterSubstitution", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameterSubstitution_Formal(), theEcorePackage.getEString(), "formal", null, 0, 1, ParameterSubstitution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(objectParameterSubstitutionEClass, ObjectParameterSubstitution.class, "ObjectParameterSubstitution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getObjectParameterSubstitution_ActualA(), ecorePackage.getEObject(), null, "actualA", null, 0, 1, ObjectParameterSubstitution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getObjectParameterSubstitution_ActualB(), ecorePackage.getEObject(), null, "actualB", null, 0, 1, ObjectParameterSubstitution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueParameterSubstitutionEClass, ValueParameterSubstitution.class, "ValueParameterSubstitution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValueParameterSubstitution_Actual(), theEcorePackage.getEJavaObject(), "actual", null, 0, 1, ValueParameterSubstitution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dependencyEClass, Dependency.class, "Dependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDependency_Kind(), this.getDependencyKind(), "kind", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDependency_Source(), this.getSemanticChangeSet(), this.getSemanticChangeSet_Outgoing(), "source", null, 1, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDependency_Target(), this.getSemanticChangeSet(), this.getSemanticChangeSet_Incoming(), "target", null, 1, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(dependencyKindEEnum, DependencyKind.class, "DependencyKind");
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.USE_DELETE);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.CREATE_USE);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.DELETE_FORBID);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.FORBID_CREATE);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.CHANGE_USE);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.USE_CHANGE);

		// Create resource
		createResource(eNS_URI);
	}

} //DifferencemodelPackageImpl
