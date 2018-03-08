/**
 */
package org.sidiff.slicer.slice.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.sidiff.entities.EntitiesPackage;

import org.sidiff.formula.FormulaPackage;

import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.slice.SliceFactory;
import org.sidiff.slicer.slice.SlicePackage;
import org.sidiff.slicer.slice.SlicedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SlicePackageImpl extends EPackageImpl implements SlicePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelSliceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass slicedElementEClass = null;

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
	 * @see org.sidiff.slicer.slice.SlicePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SlicePackageImpl() {
		super(eNS_URI, SliceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SlicePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SlicePackage init() {
		if (isInited)
			return (SlicePackage) EPackage.Registry.INSTANCE.getEPackage(SlicePackage.eNS_URI);

		// Obtain or create and register package
		SlicePackageImpl theSlicePackage = (SlicePackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof SlicePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new SlicePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EntitiesPackage.eINSTANCE.eClass();
		FormulaPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSlicePackage.createPackageContents();

		// Initialize created meta-data
		theSlicePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSlicePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SlicePackage.eNS_URI, theSlicePackage);
		return theSlicePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelSlice() {
		return modelSliceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelSlice_SlicedElements() {
		return (EReference) modelSliceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelSlice_Type() {
		return (EReference) modelSliceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getModelSlice__Export() {
		return modelSliceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSlicedElement() {
		return slicedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlicedElement_SlicedReferences() {
		return (EReference) slicedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSlicedElement_SlicedAttributes() {
		return (EReference) slicedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SliceFactory getSliceFactory() {
		return (SliceFactory) getEFactoryInstance();
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
		modelSliceEClass = createEClass(MODEL_SLICE);
		createEReference(modelSliceEClass, MODEL_SLICE__SLICED_ELEMENTS);
		createEReference(modelSliceEClass, MODEL_SLICE__TYPE);
		createEOperation(modelSliceEClass, MODEL_SLICE___EXPORT);

		slicedElementEClass = createEClass(SLICED_ELEMENT);
		createEReference(slicedElementEClass, SLICED_ELEMENT__SLICED_REFERENCES);
		createEReference(slicedElementEClass, SLICED_ELEMENT__SLICED_ATTRIBUTES);
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
		EntitiesPackage theEntitiesPackage = (EntitiesPackage) EPackage.Registry.INSTANCE
				.getEPackage(EntitiesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		slicedElementEClass.getESuperTypes().add(theEntitiesPackage.getElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(modelSliceEClass, ModelSlice.class, "ModelSlice", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelSlice_SlicedElements(), this.getSlicedElement(), null, "slicedElements", null, 0, -1,
				ModelSlice.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModelSlice_Type(), ecorePackage.getEPackage(), null, "type", null, 1, -1, ModelSlice.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getModelSlice__Export(), ecorePackage.getEObject(), "export", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(slicedElementEClass, SlicedElement.class, "SlicedElement", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSlicedElement_SlicedReferences(), theEntitiesPackage.getReference(), null, "slicedReferences",
				null, 0, -1, SlicedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSlicedElement_SlicedAttributes(), theEntitiesPackage.getAttribute(), null, "slicedAttributes",
				null, 0, -1, SlicedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SlicePackageImpl
