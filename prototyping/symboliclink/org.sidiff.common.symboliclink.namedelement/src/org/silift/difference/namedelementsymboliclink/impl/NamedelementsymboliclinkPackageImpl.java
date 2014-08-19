/**
 */
package org.silift.difference.namedelementsymboliclink.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink;
import org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkFactory;
import org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage;

import org.silift.difference.symboliclink.SymboliclinkPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NamedelementsymboliclinkPackageImpl extends EPackageImpl implements NamedelementsymboliclinkPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedElementSymbolicLinkEClass = null;

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
	 * @see org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private NamedelementsymboliclinkPackageImpl() {
		super(eNS_URI, NamedelementsymboliclinkFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link NamedelementsymboliclinkPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static NamedelementsymboliclinkPackage init() {
		if (isInited) return (NamedelementsymboliclinkPackage)EPackage.Registry.INSTANCE.getEPackage(NamedelementsymboliclinkPackage.eNS_URI);

		// Obtain or create and register package
		NamedelementsymboliclinkPackageImpl theNamedelementsymboliclinkPackage = (NamedelementsymboliclinkPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof NamedelementsymboliclinkPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new NamedelementsymboliclinkPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		SymboliclinkPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theNamedelementsymboliclinkPackage.createPackageContents();

		// Initialize created meta-data
		theNamedelementsymboliclinkPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theNamedelementsymboliclinkPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(NamedelementsymboliclinkPackage.eNS_URI, theNamedelementsymboliclinkPackage);
		return theNamedelementsymboliclinkPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamedElementSymbolicLink() {
		return namedElementSymbolicLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedElementSymbolicLink_Name() {
		return (EAttribute)namedElementSymbolicLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedElementSymbolicLink_QualifiedName() {
		return (EAttribute)namedElementSymbolicLinkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamedelementsymboliclinkFactory getNamedelementsymboliclinkFactory() {
		return (NamedelementsymboliclinkFactory)getEFactoryInstance();
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
		namedElementSymbolicLinkEClass = createEClass(NAMED_ELEMENT_SYMBOLIC_LINK);
		createEAttribute(namedElementSymbolicLinkEClass, NAMED_ELEMENT_SYMBOLIC_LINK__NAME);
		createEAttribute(namedElementSymbolicLinkEClass, NAMED_ELEMENT_SYMBOLIC_LINK__QUALIFIED_NAME);
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
		SymboliclinkPackage theSymboliclinkPackage = (SymboliclinkPackage)EPackage.Registry.INSTANCE.getEPackage(SymboliclinkPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		namedElementSymbolicLinkEClass.getESuperTypes().add(theSymboliclinkPackage.getSymbolicLink());

		// Initialize classes, features, and operations; add parameters
		initEClass(namedElementSymbolicLinkEClass, NamedElementSymbolicLink.class, "NamedElementSymbolicLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedElementSymbolicLink_Name(), ecorePackage.getEString(), "name", null, 0, 1, NamedElementSymbolicLink.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedElementSymbolicLink_QualifiedName(), ecorePackage.getEString(), "qualifiedName", null, 0, 1, NamedElementSymbolicLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //NamedelementsymboliclinkPackageImpl
