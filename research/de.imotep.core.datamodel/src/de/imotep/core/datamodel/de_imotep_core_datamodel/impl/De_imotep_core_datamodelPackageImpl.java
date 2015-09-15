/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel.impl;

import de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelFactory;
import de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MBooleanAttribute;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MEVisibility;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MEntity;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MNamedEntity;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MRangedIntegerAttribute;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class De_imotep_core_datamodelPackageImpl extends EPackageImpl implements De_imotep_core_datamodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mNamedEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mIntegerAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mStringAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mRangedIntegerAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mBooleanAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum meVisibilityEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jCollectionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jListEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jSetEDataType = null;

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
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private De_imotep_core_datamodelPackageImpl() {
		super(eNS_URI, De_imotep_core_datamodelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link De_imotep_core_datamodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static De_imotep_core_datamodelPackage init() {
		if (isInited) return (De_imotep_core_datamodelPackage)EPackage.Registry.INSTANCE.getEPackage(De_imotep_core_datamodelPackage.eNS_URI);

		// Obtain or create and register package
		De_imotep_core_datamodelPackageImpl theDe_imotep_core_datamodelPackage = (De_imotep_core_datamodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof De_imotep_core_datamodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new De_imotep_core_datamodelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theDe_imotep_core_datamodelPackage.createPackageContents();

		// Initialize created meta-data
		theDe_imotep_core_datamodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDe_imotep_core_datamodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(De_imotep_core_datamodelPackage.eNS_URI, theDe_imotep_core_datamodelPackage);
		return theDe_imotep_core_datamodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMAttribute() {
		return mAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMAttribute_IsStatic() {
		return (EAttribute)mAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMAttribute_Visibility() {
		return (EAttribute)mAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMNamedEntity() {
		return mNamedEntityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMNamedEntity_Name() {
		return (EAttribute)mNamedEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMIntegerAttribute() {
		return mIntegerAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMIntegerAttribute_InitValue() {
		return (EAttribute)mIntegerAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMIntegerAttribute_Value() {
		return (EAttribute)mIntegerAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMStringAttribute() {
		return mStringAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMStringAttribute_InitValue() {
		return (EAttribute)mStringAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMStringAttribute_Value() {
		return (EAttribute)mStringAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMEntity() {
		return mEntityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMEntity_Id() {
		return (EAttribute)mEntityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMRangedIntegerAttribute() {
		return mRangedIntegerAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMRangedIntegerAttribute_Maximum() {
		return (EAttribute)mRangedIntegerAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMRangedIntegerAttribute_Minimum() {
		return (EAttribute)mRangedIntegerAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMBooleanAttribute() {
		return mBooleanAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMBooleanAttribute_InitValue() {
		return (EAttribute)mBooleanAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMBooleanAttribute_Value() {
		return (EAttribute)mBooleanAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMEVisibility() {
		return meVisibilityEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJObject() {
		return jObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJCollection() {
		return jCollectionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJList() {
		return jListEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJSet() {
		return jSetEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_datamodelFactory getDe_imotep_core_datamodelFactory() {
		return (De_imotep_core_datamodelFactory)getEFactoryInstance();
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
		mAttributeEClass = createEClass(MATTRIBUTE);
		createEAttribute(mAttributeEClass, MATTRIBUTE__IS_STATIC);
		createEAttribute(mAttributeEClass, MATTRIBUTE__VISIBILITY);

		mNamedEntityEClass = createEClass(MNAMED_ENTITY);
		createEAttribute(mNamedEntityEClass, MNAMED_ENTITY__NAME);

		mIntegerAttributeEClass = createEClass(MINTEGER_ATTRIBUTE);
		createEAttribute(mIntegerAttributeEClass, MINTEGER_ATTRIBUTE__INIT_VALUE);
		createEAttribute(mIntegerAttributeEClass, MINTEGER_ATTRIBUTE__VALUE);

		mStringAttributeEClass = createEClass(MSTRING_ATTRIBUTE);
		createEAttribute(mStringAttributeEClass, MSTRING_ATTRIBUTE__INIT_VALUE);
		createEAttribute(mStringAttributeEClass, MSTRING_ATTRIBUTE__VALUE);

		mEntityEClass = createEClass(MENTITY);
		createEAttribute(mEntityEClass, MENTITY__ID);

		mRangedIntegerAttributeEClass = createEClass(MRANGED_INTEGER_ATTRIBUTE);
		createEAttribute(mRangedIntegerAttributeEClass, MRANGED_INTEGER_ATTRIBUTE__MAXIMUM);
		createEAttribute(mRangedIntegerAttributeEClass, MRANGED_INTEGER_ATTRIBUTE__MINIMUM);

		mBooleanAttributeEClass = createEClass(MBOOLEAN_ATTRIBUTE);
		createEAttribute(mBooleanAttributeEClass, MBOOLEAN_ATTRIBUTE__INIT_VALUE);
		createEAttribute(mBooleanAttributeEClass, MBOOLEAN_ATTRIBUTE__VALUE);

		// Create enums
		meVisibilityEEnum = createEEnum(ME_VISIBILITY);

		// Create data types
		jObjectEDataType = createEDataType(JOBJECT);
		jCollectionEDataType = createEDataType(JCOLLECTION);
		jListEDataType = createEDataType(JLIST);
		jSetEDataType = createEDataType(JSET);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		mAttributeEClass.getESuperTypes().add(this.getMNamedEntity());
		mNamedEntityEClass.getESuperTypes().add(this.getMEntity());
		mIntegerAttributeEClass.getESuperTypes().add(this.getMAttribute());
		mStringAttributeEClass.getESuperTypes().add(this.getMAttribute());
		mRangedIntegerAttributeEClass.getESuperTypes().add(this.getMIntegerAttribute());
		mBooleanAttributeEClass.getESuperTypes().add(this.getMAttribute());

		// Initialize classes, features, and operations; add parameters
		initEClass(mAttributeEClass, MAttribute.class, "MAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMAttribute_IsStatic(), ecorePackage.getEBoolean(), "isStatic", null, 1, 1, MAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getMAttribute_Visibility(), this.getMEVisibility(), "visibility", null, 1, 1, MAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mNamedEntityEClass, MNamedEntity.class, "MNamedEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMNamedEntity_Name(), ecorePackage.getEString(), "name", null, 1, 1, MNamedEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mIntegerAttributeEClass, MIntegerAttribute.class, "MIntegerAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMIntegerAttribute_InitValue(), ecorePackage.getEInt(), "initValue", null, 1, 1, MIntegerAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getMIntegerAttribute_Value(), ecorePackage.getEInt(), "value", null, 1, 1, MIntegerAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mStringAttributeEClass, MStringAttribute.class, "MStringAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMStringAttribute_InitValue(), ecorePackage.getEString(), "initValue", null, 1, 1, MStringAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getMStringAttribute_Value(), ecorePackage.getEString(), "value", null, 1, 1, MStringAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mEntityEClass, MEntity.class, "MEntity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMEntity_Id(), ecorePackage.getEString(), "id", null, 1, 1, MEntity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mRangedIntegerAttributeEClass, MRangedIntegerAttribute.class, "MRangedIntegerAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMRangedIntegerAttribute_Maximum(), ecorePackage.getEInt(), "maximum", "0", 1, 1, MRangedIntegerAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getMRangedIntegerAttribute_Minimum(), ecorePackage.getEInt(), "minimum", "0", 1, 1, MRangedIntegerAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mBooleanAttributeEClass, MBooleanAttribute.class, "MBooleanAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMBooleanAttribute_InitValue(), ecorePackage.getEBoolean(), "initValue", null, 1, 1, MBooleanAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getMBooleanAttribute_Value(), ecorePackage.getEBoolean(), "value", null, 1, 1, MBooleanAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(meVisibilityEEnum, MEVisibility.class, "MEVisibility");
		addEEnumLiteral(meVisibilityEEnum, MEVisibility.PUBLIC);
		addEEnumLiteral(meVisibilityEEnum, MEVisibility.PRIVATE);

		// Initialize data types
		initEDataType(jObjectEDataType, Object.class, "JObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jCollectionEDataType, Object.class, "JCollection", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jListEDataType, Object.class, "JList", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jSetEDataType, Object.class, "JSet", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //De_imotep_core_datamodelPackageImpl
