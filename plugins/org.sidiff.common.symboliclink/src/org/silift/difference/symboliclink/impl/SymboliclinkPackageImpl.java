/**
 */
package org.silift.difference.symboliclink.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkReference;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.SymboliclinkFactory;
import org.silift.difference.symboliclink.SymboliclinkPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SymboliclinkPackageImpl extends EPackageImpl implements SymboliclinkPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbolicLinksEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbolicLinkObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbolicLinkReferenceEClass = null;

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
	 * @see org.silift.difference.symboliclink.SymboliclinkPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SymboliclinkPackageImpl() {
		super(eNS_URI, SymboliclinkFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SymboliclinkPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SymboliclinkPackage init() {
		if (isInited) return (SymboliclinkPackage)EPackage.Registry.INSTANCE.getEPackage(SymboliclinkPackage.eNS_URI);

		// Obtain or create and register package
		SymboliclinkPackageImpl theSymboliclinkPackage = (SymboliclinkPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SymboliclinkPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SymboliclinkPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSymboliclinkPackage.createPackageContents();

		// Initialize created meta-data
		theSymboliclinkPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSymboliclinkPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SymboliclinkPackage.eNS_URI, theSymboliclinkPackage);
		return theSymboliclinkPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSymbolicLinks() {
		return symbolicLinksEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSymbolicLinks_LinkObjects() {
		return (EReference)symbolicLinksEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSymbolicLinks_DocType() {
		return (EAttribute)symbolicLinksEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSymbolicLinks_LinkReferences() {
		return (EReference)symbolicLinksEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSymbolicLinkObject() {
		return symbolicLinkObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSymbolicLinkObject_Reliability() {
		return (EAttribute)symbolicLinkObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSymbolicLinkReference() {
		return symbolicLinkReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSymbolicLinkReference_Type() {
		return (EReference)symbolicLinkReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymboliclinkFactory getSymboliclinkFactory() {
		return (SymboliclinkFactory)getEFactoryInstance();
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
		symbolicLinksEClass = createEClass(SYMBOLIC_LINKS);
		createEReference(symbolicLinksEClass, SYMBOLIC_LINKS__LINK_OBJECTS);
		createEAttribute(symbolicLinksEClass, SYMBOLIC_LINKS__DOC_TYPE);
		createEReference(symbolicLinksEClass, SYMBOLIC_LINKS__LINK_REFERENCES);

		symbolicLinkObjectEClass = createEClass(SYMBOLIC_LINK_OBJECT);
		createEAttribute(symbolicLinkObjectEClass, SYMBOLIC_LINK_OBJECT__RELIABILITY);

		symbolicLinkReferenceEClass = createEClass(SYMBOLIC_LINK_REFERENCE);
		createEReference(symbolicLinkReferenceEClass, SYMBOLIC_LINK_REFERENCE__TYPE);
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

		// Initialize classes, features, and operations; add parameters
		initEClass(symbolicLinksEClass, SymbolicLinks.class, "SymbolicLinks", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSymbolicLinks_LinkObjects(), this.getSymbolicLinkObject(), null, "linkObjects", null, 0, -1, SymbolicLinks.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbolicLinks_DocType(), ecorePackage.getEString(), "docType", null, 0, 1, SymbolicLinks.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbolicLinks_LinkReferences(), this.getSymbolicLinkReference(), null, "linkReferences", null, 0, -1, SymbolicLinks.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(symbolicLinkObjectEClass, SymbolicLinkObject.class, "SymbolicLinkObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSymbolicLinkObject_Reliability(), ecorePackage.getEFloat(), "reliability", null, 0, 1, SymbolicLinkObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(symbolicLinkReferenceEClass, SymbolicLinkReference.class, "SymbolicLinkReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSymbolicLinkReference_Type(), theEcorePackage.getEReference(), null, "type", null, 1, 1, SymbolicLinkReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SymboliclinkPackageImpl
