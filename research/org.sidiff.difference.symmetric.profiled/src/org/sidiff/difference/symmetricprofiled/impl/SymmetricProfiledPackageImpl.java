/**
 */
package org.sidiff.difference.symmetricprofiled.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.sidiff.difference.symmetric.SymmetricPackage;

import org.sidiff.difference.symmetricprofiled.AppliedStereotype;
import org.sidiff.difference.symmetricprofiled.ProfiledSCS;
import org.sidiff.difference.symmetricprofiled.ProfiledSD;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledFactory;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SymmetricProfiledPackageImpl extends EPackageImpl implements SymmetricProfiledPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass profiledSDEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass profiledSCSEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass appliedStereotypeEClass = null;

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
	 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SymmetricProfiledPackageImpl() {
		super(eNS_URI, SymmetricProfiledFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SymmetricProfiledPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SymmetricProfiledPackage init() {
		if (isInited) return (SymmetricProfiledPackage)EPackage.Registry.INSTANCE.getEPackage(SymmetricProfiledPackage.eNS_URI);

		// Obtain or create and register package
		SymmetricProfiledPackageImpl theSymmetricProfiledPackage = (SymmetricProfiledPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SymmetricProfiledPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SymmetricProfiledPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		SymmetricPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSymmetricProfiledPackage.createPackageContents();

		// Initialize created meta-data
		theSymmetricProfiledPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSymmetricProfiledPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SymmetricProfiledPackage.eNS_URI, theSymmetricProfiledPackage);
		return theSymmetricProfiledPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProfiledSD() {
		return profiledSDEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProfiledSD_Profiledscss() {
		return (EReference)profiledSDEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProfiledSD_Sd() {
		return (EReference)profiledSDEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProfiledSD_Unprofiledscss() {
		return (EReference)profiledSDEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProfiledSCS() {
		return profiledSCSEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProfiledSCS_Scs() {
		return (EReference)profiledSCSEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProfiledSCS_Name() {
		return (EAttribute)profiledSCSEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProfiledSCS_AppliedStereotypes() {
		return (EReference)profiledSCSEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAppliedStereotype() {
		return appliedStereotypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAppliedStereotype_Name() {
		return (EAttribute)appliedStereotypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAppliedStereotype_StereoType() {
		return (EReference)appliedStereotypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAppliedStereotype_BaseObject() {
		return (EReference)appliedStereotypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAppliedStereotype_BaseReference() {
		return (EReference)appliedStereotypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAppliedStereotype_Profile() {
		return (EReference)appliedStereotypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymmetricProfiledFactory getSymmetricProfiledFactory() {
		return (SymmetricProfiledFactory)getEFactoryInstance();
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
		profiledSDEClass = createEClass(PROFILED_SD);
		createEReference(profiledSDEClass, PROFILED_SD__PROFILEDSCSS);
		createEReference(profiledSDEClass, PROFILED_SD__SD);
		createEReference(profiledSDEClass, PROFILED_SD__UNPROFILEDSCSS);

		profiledSCSEClass = createEClass(PROFILED_SCS);
		createEReference(profiledSCSEClass, PROFILED_SCS__SCS);
		createEAttribute(profiledSCSEClass, PROFILED_SCS__NAME);
		createEReference(profiledSCSEClass, PROFILED_SCS__APPLIED_STEREOTYPES);

		appliedStereotypeEClass = createEClass(APPLIED_STEREOTYPE);
		createEAttribute(appliedStereotypeEClass, APPLIED_STEREOTYPE__NAME);
		createEReference(appliedStereotypeEClass, APPLIED_STEREOTYPE__STEREO_TYPE);
		createEReference(appliedStereotypeEClass, APPLIED_STEREOTYPE__BASE_OBJECT);
		createEReference(appliedStereotypeEClass, APPLIED_STEREOTYPE__BASE_REFERENCE);
		createEReference(appliedStereotypeEClass, APPLIED_STEREOTYPE__PROFILE);
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
		SymmetricPackage theSymmetricPackage = (SymmetricPackage)EPackage.Registry.INSTANCE.getEPackage(SymmetricPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(profiledSDEClass, ProfiledSD.class, "ProfiledSD", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProfiledSD_Profiledscss(), this.getProfiledSCS(), null, "profiledscss", null, 0, -1, ProfiledSD.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProfiledSD_Sd(), theSymmetricPackage.getSymmetricDifference(), null, "sd", null, 1, 1, ProfiledSD.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProfiledSD_Unprofiledscss(), theSymmetricPackage.getSemanticChangeSet(), null, "unprofiledscss", null, 0, -1, ProfiledSD.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(profiledSCSEClass, ProfiledSCS.class, "ProfiledSCS", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProfiledSCS_Scs(), theSymmetricPackage.getSemanticChangeSet(), null, "scs", null, 1, 1, ProfiledSCS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProfiledSCS_Name(), ecorePackage.getEString(), "name", null, 1, 1, ProfiledSCS.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getProfiledSCS_AppliedStereotypes(), this.getAppliedStereotype(), null, "appliedStereotypes", null, 1, -1, ProfiledSCS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(appliedStereotypeEClass, AppliedStereotype.class, "AppliedStereotype", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAppliedStereotype_Name(), ecorePackage.getEString(), "name", null, 1, 1, AppliedStereotype.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getAppliedStereotype_StereoType(), theEcorePackage.getEObject(), null, "stereoType", null, 1, 1, AppliedStereotype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAppliedStereotype_BaseObject(), ecorePackage.getEObject(), null, "baseObject", null, 1, 1, AppliedStereotype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAppliedStereotype_BaseReference(), theEcorePackage.getEReference(), null, "baseReference", null, 1, 1, AppliedStereotype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAppliedStereotype_Profile(), theEcorePackage.getEPackage(), null, "profile", null, 1, 1, AppliedStereotype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SymmetricProfiledPackageImpl
