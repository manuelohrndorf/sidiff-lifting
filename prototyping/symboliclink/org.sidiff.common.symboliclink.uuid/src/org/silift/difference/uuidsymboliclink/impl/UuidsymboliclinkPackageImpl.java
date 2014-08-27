/**
 */
package org.silift.difference.uuidsymboliclink.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.silift.difference.symboliclink.SymboliclinkPackage;
import org.silift.difference.uuidsymboliclink.UUIDSymbolicLinkObject;
import org.silift.difference.uuidsymboliclink.UuidsymboliclinkFactory;
import org.silift.difference.uuidsymboliclink.UuidsymboliclinkPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UuidsymboliclinkPackageImpl extends EPackageImpl implements UuidsymboliclinkPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uuidSymbolicLinkObjectEClass = null;
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
	 * @see org.silift.difference.uuidsymboliclink.UuidsymboliclinkPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UuidsymboliclinkPackageImpl() {
		super(eNS_URI, UuidsymboliclinkFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link UuidsymboliclinkPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UuidsymboliclinkPackage init() {
		if (isInited) return (UuidsymboliclinkPackage)EPackage.Registry.INSTANCE.getEPackage(UuidsymboliclinkPackage.eNS_URI);

		// Obtain or create and register package
		UuidsymboliclinkPackageImpl theUuidsymboliclinkPackage = (UuidsymboliclinkPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UuidsymboliclinkPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UuidsymboliclinkPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		SymboliclinkPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUuidsymboliclinkPackage.createPackageContents();

		// Initialize created meta-data
		theUuidsymboliclinkPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUuidsymboliclinkPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UuidsymboliclinkPackage.eNS_URI, theUuidsymboliclinkPackage);
		return theUuidsymboliclinkPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUUIDSymbolicLinkObject() {
		return uuidSymbolicLinkObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUUIDSymbolicLinkObject_Uuid() {
		return (EAttribute)uuidSymbolicLinkObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUUIDSymbolicLinkObject_Name() {
		return (EAttribute)uuidSymbolicLinkObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UuidsymboliclinkFactory getUuidsymboliclinkFactory() {
		return (UuidsymboliclinkFactory)getEFactoryInstance();
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
		uuidSymbolicLinkObjectEClass = createEClass(UUID_SYMBOLIC_LINK_OBJECT);
		createEAttribute(uuidSymbolicLinkObjectEClass, UUID_SYMBOLIC_LINK_OBJECT__UUID);
		createEAttribute(uuidSymbolicLinkObjectEClass, UUID_SYMBOLIC_LINK_OBJECT__NAME);
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
		uuidSymbolicLinkObjectEClass.getESuperTypes().add(theSymboliclinkPackage.getSymbolicLinkObject());

		// Initialize classes, features, and operations; add parameters
		initEClass(uuidSymbolicLinkObjectEClass, UUIDSymbolicLinkObject.class, "UUIDSymbolicLinkObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUUIDSymbolicLinkObject_Uuid(), ecorePackage.getEString(), "uuid", null, 1, 1, UUIDSymbolicLinkObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUUIDSymbolicLinkObject_Name(), ecorePackage.getEString(), "name", null, 0, 1, UUIDSymbolicLinkObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //UuidsymboliclinkPackageImpl
