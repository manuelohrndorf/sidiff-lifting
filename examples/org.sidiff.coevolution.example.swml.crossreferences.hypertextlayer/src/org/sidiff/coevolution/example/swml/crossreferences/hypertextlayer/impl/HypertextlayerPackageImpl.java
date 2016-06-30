/**
 */
package org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.sidiff.coevolution.example.swml.crossreferences.datalayer.DatalayerPackage;

import org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.DataPage;
import org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.DynamicPage;
import org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer;
import org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextlayerFactory;
import org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextlayerPackage;
import org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.IndexPage;
import org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Link;
import org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Page;
import org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.StaticPage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HypertextlayerPackageImpl extends EPackageImpl implements HypertextlayerPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hypertextLayerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dynamicPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass staticPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass indexPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataPageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkEClass = null;

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
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextlayerPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private HypertextlayerPackageImpl() {
		super(eNS_URI, HypertextlayerFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link HypertextlayerPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static HypertextlayerPackage init() {
		if (isInited) return (HypertextlayerPackage)EPackage.Registry.INSTANCE.getEPackage(HypertextlayerPackage.eNS_URI);

		// Obtain or create and register package
		HypertextlayerPackageImpl theHypertextlayerPackage = (HypertextlayerPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof HypertextlayerPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new HypertextlayerPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		DatalayerPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theHypertextlayerPackage.createPackageContents();

		// Initialize created meta-data
		theHypertextlayerPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theHypertextlayerPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(HypertextlayerPackage.eNS_URI, theHypertextlayerPackage);
		return theHypertextlayerPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHypertextLayer() {
		return hypertextLayerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHypertextLayer_Name() {
		return (EAttribute)hypertextLayerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHypertextLayer_Pages() {
		return (EReference)hypertextLayerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHypertextLayer_StartPage() {
		return (EReference)hypertextLayerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHypertextLayer_DataLayer() {
		return (EReference)hypertextLayerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPage() {
		return pageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPage_Name() {
		return (EAttribute)pageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPage_Links() {
		return (EReference)pageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDynamicPage() {
		return dynamicPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDynamicPage_Shows() {
		return (EReference)dynamicPageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStaticPage() {
		return staticPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIndexPage() {
		return indexPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataPage() {
		return dataPageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLink() {
		return linkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLink_Target() {
		return (EReference)linkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HypertextlayerFactory getHypertextlayerFactory() {
		return (HypertextlayerFactory)getEFactoryInstance();
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
		hypertextLayerEClass = createEClass(HYPERTEXT_LAYER);
		createEAttribute(hypertextLayerEClass, HYPERTEXT_LAYER__NAME);
		createEReference(hypertextLayerEClass, HYPERTEXT_LAYER__PAGES);
		createEReference(hypertextLayerEClass, HYPERTEXT_LAYER__START_PAGE);
		createEReference(hypertextLayerEClass, HYPERTEXT_LAYER__DATA_LAYER);

		pageEClass = createEClass(PAGE);
		createEAttribute(pageEClass, PAGE__NAME);
		createEReference(pageEClass, PAGE__LINKS);

		dynamicPageEClass = createEClass(DYNAMIC_PAGE);
		createEReference(dynamicPageEClass, DYNAMIC_PAGE__SHOWS);

		staticPageEClass = createEClass(STATIC_PAGE);

		indexPageEClass = createEClass(INDEX_PAGE);

		dataPageEClass = createEClass(DATA_PAGE);

		linkEClass = createEClass(LINK);
		createEReference(linkEClass, LINK__TARGET);
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
		DatalayerPackage theDatalayerPackage = (DatalayerPackage)EPackage.Registry.INSTANCE.getEPackage(DatalayerPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		dynamicPageEClass.getESuperTypes().add(this.getPage());
		staticPageEClass.getESuperTypes().add(this.getPage());
		indexPageEClass.getESuperTypes().add(this.getDynamicPage());
		dataPageEClass.getESuperTypes().add(this.getDynamicPage());

		// Initialize classes, features, and operations; add parameters
		initEClass(hypertextLayerEClass, HypertextLayer.class, "HypertextLayer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHypertextLayer_Name(), ecorePackage.getEString(), "name", null, 0, 1, HypertextLayer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHypertextLayer_Pages(), this.getPage(), null, "pages", null, 0, -1, HypertextLayer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHypertextLayer_StartPage(), this.getStaticPage(), null, "startPage", null, 0, 1, HypertextLayer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHypertextLayer_DataLayer(), theDatalayerPackage.getDataLayer(), null, "dataLayer", null, 0, 1, HypertextLayer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pageEClass, Page.class, "Page", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPage_Name(), ecorePackage.getEString(), "name", null, 0, 1, Page.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPage_Links(), this.getLink(), null, "links", null, 0, -1, Page.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dynamicPageEClass, DynamicPage.class, "DynamicPage", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDynamicPage_Shows(), theDatalayerPackage.getEntity(), null, "shows", null, 0, 1, DynamicPage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(staticPageEClass, StaticPage.class, "StaticPage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(indexPageEClass, IndexPage.class, "IndexPage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dataPageEClass, DataPage.class, "DataPage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(linkEClass, Link.class, "Link", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLink_Target(), this.getPage(), null, "target", null, 0, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //HypertextlayerPackageImpl
