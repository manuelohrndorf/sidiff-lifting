/**
 */
package org.silift.difference.symboliclink.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.silift.difference.symboliclink.ExternalSymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkAttribute;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalSymbolicLinkObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbolicLinkAttributeEClass = null;

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
	@Override
	public EClass getSymbolicLinks() {
		return symbolicLinksEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSymbolicLinks_LinkObjects() {
		return (EReference)symbolicLinksEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSymbolicLinks_DocType() {
		return (EAttribute)symbolicLinksEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSymbolicLinks_LinkReferences() {
		return (EReference)symbolicLinksEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSymbolicLinkObject() {
		return symbolicLinkObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSymbolicLinkObject_Reliability() {
		return (EAttribute)symbolicLinkObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSymbolicLinkObject_Outgoing() {
		return (EReference)symbolicLinkObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSymbolicLinkObject_Incoming() {
		return (EReference)symbolicLinkObjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSymbolicLinkObject_LinkAttributes() {
		return (EReference)symbolicLinkObjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSymbolicLinkObject_Type() {
		return (EReference)symbolicLinkObjectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSymbolicLinkObject__GetOutgoings__EReference() {
		return symbolicLinkObjectEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSymbolicLinkObject__HashCode() {
		return symbolicLinkObjectEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EOperation getSymbolicLinkObject__Equals__Object() {
		return symbolicLinkObjectEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSymbolicLinkReference() {
		return symbolicLinkReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSymbolicLinkReference_Type() {
		return (EReference)symbolicLinkReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSymbolicLinkReference_Source() {
		return (EReference)symbolicLinkReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSymbolicLinkReference_Target() {
		return (EReference)symbolicLinkReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExternalSymbolicLinkObject() {
		return externalSymbolicLinkObjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExternalSymbolicLinkObject_EObject() {
		return (EReference)externalSymbolicLinkObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExternalSymbolicLinkObject_From() {
		return (EAttribute)externalSymbolicLinkObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSymbolicLinkAttribute() {
		return symbolicLinkAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSymbolicLinkAttribute_Value() {
		return (EAttribute)symbolicLinkAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSymbolicLinkAttribute_Type() {
		return (EReference)symbolicLinkAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
		createEReference(symbolicLinkObjectEClass, SYMBOLIC_LINK_OBJECT__OUTGOING);
		createEReference(symbolicLinkObjectEClass, SYMBOLIC_LINK_OBJECT__INCOMING);
		createEReference(symbolicLinkObjectEClass, SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES);
		createEReference(symbolicLinkObjectEClass, SYMBOLIC_LINK_OBJECT__TYPE);
		createEOperation(symbolicLinkObjectEClass, SYMBOLIC_LINK_OBJECT___GET_OUTGOINGS__EREFERENCE);
		createEOperation(symbolicLinkObjectEClass, SYMBOLIC_LINK_OBJECT___HASH_CODE);
		createEOperation(symbolicLinkObjectEClass, SYMBOLIC_LINK_OBJECT___EQUALS__OBJECT);

		symbolicLinkReferenceEClass = createEClass(SYMBOLIC_LINK_REFERENCE);
		createEReference(symbolicLinkReferenceEClass, SYMBOLIC_LINK_REFERENCE__TYPE);
		createEReference(symbolicLinkReferenceEClass, SYMBOLIC_LINK_REFERENCE__SOURCE);
		createEReference(symbolicLinkReferenceEClass, SYMBOLIC_LINK_REFERENCE__TARGET);

		externalSymbolicLinkObjectEClass = createEClass(EXTERNAL_SYMBOLIC_LINK_OBJECT);
		createEReference(externalSymbolicLinkObjectEClass, EXTERNAL_SYMBOLIC_LINK_OBJECT__EOBJECT);
		createEAttribute(externalSymbolicLinkObjectEClass, EXTERNAL_SYMBOLIC_LINK_OBJECT__FROM);

		symbolicLinkAttributeEClass = createEClass(SYMBOLIC_LINK_ATTRIBUTE);
		createEAttribute(symbolicLinkAttributeEClass, SYMBOLIC_LINK_ATTRIBUTE__VALUE);
		createEReference(symbolicLinkAttributeEClass, SYMBOLIC_LINK_ATTRIBUTE__TYPE);
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
		externalSymbolicLinkObjectEClass.getESuperTypes().add(this.getSymbolicLinkObject());

		// Initialize classes, features, and operations; add parameters
		initEClass(symbolicLinksEClass, SymbolicLinks.class, "SymbolicLinks", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSymbolicLinks_LinkObjects(), this.getSymbolicLinkObject(), null, "linkObjects", null, 0, -1, SymbolicLinks.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSymbolicLinks_DocType(), ecorePackage.getEString(), "docType", null, 0, 1, SymbolicLinks.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbolicLinks_LinkReferences(), this.getSymbolicLinkReference(), null, "linkReferences", null, 0, -1, SymbolicLinks.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(symbolicLinkObjectEClass, SymbolicLinkObject.class, "SymbolicLinkObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSymbolicLinkObject_Reliability(), ecorePackage.getEFloat(), "reliability", null, 0, 1, SymbolicLinkObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbolicLinkObject_Outgoing(), this.getSymbolicLinkReference(), this.getSymbolicLinkReference_Source(), "outgoing", null, 0, -1, SymbolicLinkObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbolicLinkObject_Incoming(), this.getSymbolicLinkReference(), this.getSymbolicLinkReference_Target(), "incoming", null, 0, -1, SymbolicLinkObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbolicLinkObject_LinkAttributes(), this.getSymbolicLinkAttribute(), null, "linkAttributes", null, 0, -1, SymbolicLinkObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbolicLinkObject_Type(), theEcorePackage.getEObject(), null, "type", null, 1, 1, SymbolicLinkObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getSymbolicLinkObject__GetOutgoings__EReference(), null, "getOutgoings", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEReference(), "type", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEEList());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEOperation(getSymbolicLinkObject__HashCode(), ecorePackage.getEInt(), "hashCode", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getSymbolicLinkObject__Equals__Object(), ecorePackage.getEBoolean(), "equals", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEJavaObject(), "o", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(symbolicLinkReferenceEClass, SymbolicLinkReference.class, "SymbolicLinkReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSymbolicLinkReference_Type(), theEcorePackage.getEReference(), null, "type", null, 1, 1, SymbolicLinkReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbolicLinkReference_Source(), this.getSymbolicLinkObject(), this.getSymbolicLinkObject_Outgoing(), "source", null, 1, 1, SymbolicLinkReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbolicLinkReference_Target(), this.getSymbolicLinkObject(), this.getSymbolicLinkObject_Incoming(), "target", null, 1, 1, SymbolicLinkReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(externalSymbolicLinkObjectEClass, ExternalSymbolicLinkObject.class, "ExternalSymbolicLinkObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExternalSymbolicLinkObject_EObject(), theEcorePackage.getEObject(), null, "eObject", null, 1, 1, ExternalSymbolicLinkObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExternalSymbolicLinkObject_From(), theEcorePackage.getEString(), "from", null, 0, 1, ExternalSymbolicLinkObject.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(symbolicLinkAttributeEClass, SymbolicLinkAttribute.class, "SymbolicLinkAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSymbolicLinkAttribute_Value(), ecorePackage.getEString(), "value", null, 0, 1, SymbolicLinkAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbolicLinkAttribute_Type(), theEcorePackage.getEAttribute(), null, "type", null, 1, 1, SymbolicLinkAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SymboliclinkPackageImpl
