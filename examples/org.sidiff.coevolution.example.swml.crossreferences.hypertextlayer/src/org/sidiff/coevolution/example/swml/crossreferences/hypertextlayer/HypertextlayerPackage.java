/**
 */
package org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextlayerFactory
 * @model kind="package"
 * @generated
 */
public interface HypertextlayerPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "hypertextlayer";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://hypertextlayer/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "hypertextlayer";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HypertextlayerPackage eINSTANCE = org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextLayerImpl <em>Hypertext Layer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextLayerImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getHypertextLayer()
	 * @generated
	 */
	int HYPERTEXT_LAYER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Pages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER__PAGES = 1;

	/**
	 * The feature id for the '<em><b>Start Page</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER__START_PAGE = 2;

	/**
	 * The feature id for the '<em><b>Data Layer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER__DATA_LAYER = 3;

	/**
	 * The number of structural features of the '<em>Hypertext Layer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Hypertext Layer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.PageImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getPage()
	 * @generated
	 */
	int PAGE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE__LINKS = 1;

	/**
	 * The number of structural features of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.DynamicPageImpl <em>Dynamic Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.DynamicPageImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getDynamicPage()
	 * @generated
	 */
	int DYNAMIC_PAGE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_PAGE__NAME = PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_PAGE__LINKS = PAGE__LINKS;

	/**
	 * The feature id for the '<em><b>Shows</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_PAGE__SHOWS = PAGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dynamic Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_PAGE_FEATURE_COUNT = PAGE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Dynamic Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_PAGE_OPERATION_COUNT = PAGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.StaticPageImpl <em>Static Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.StaticPageImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getStaticPage()
	 * @generated
	 */
	int STATIC_PAGE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_PAGE__NAME = PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_PAGE__LINKS = PAGE__LINKS;

	/**
	 * The number of structural features of the '<em>Static Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_PAGE_FEATURE_COUNT = PAGE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Static Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_PAGE_OPERATION_COUNT = PAGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.IndexPageImpl <em>Index Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.IndexPageImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getIndexPage()
	 * @generated
	 */
	int INDEX_PAGE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_PAGE__NAME = DYNAMIC_PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_PAGE__LINKS = DYNAMIC_PAGE__LINKS;

	/**
	 * The feature id for the '<em><b>Shows</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_PAGE__SHOWS = DYNAMIC_PAGE__SHOWS;

	/**
	 * The number of structural features of the '<em>Index Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_PAGE_FEATURE_COUNT = DYNAMIC_PAGE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Index Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEX_PAGE_OPERATION_COUNT = DYNAMIC_PAGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.DataPageImpl <em>Data Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.DataPageImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getDataPage()
	 * @generated
	 */
	int DATA_PAGE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_PAGE__NAME = DYNAMIC_PAGE__NAME;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_PAGE__LINKS = DYNAMIC_PAGE__LINKS;

	/**
	 * The feature id for the '<em><b>Shows</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_PAGE__SHOWS = DYNAMIC_PAGE__SHOWS;

	/**
	 * The number of structural features of the '<em>Data Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_PAGE_FEATURE_COUNT = DYNAMIC_PAGE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Data Page</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_PAGE_OPERATION_COUNT = DYNAMIC_PAGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.LinkImpl
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 6;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__TARGET = 0;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer <em>Hypertext Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hypertext Layer</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer
	 * @generated
	 */
	EClass getHypertextLayer();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer#getName()
	 * @see #getHypertextLayer()
	 * @generated
	 */
	EAttribute getHypertextLayer_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer#getPages <em>Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pages</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer#getPages()
	 * @see #getHypertextLayer()
	 * @generated
	 */
	EReference getHypertextLayer_Pages();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer#getStartPage <em>Start Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start Page</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer#getStartPage()
	 * @see #getHypertextLayer()
	 * @generated
	 */
	EReference getHypertextLayer_StartPage();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer#getDataLayer <em>Data Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Data Layer</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextLayer#getDataLayer()
	 * @see #getHypertextLayer()
	 * @generated
	 */
	EReference getHypertextLayer_DataLayer();

	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Page
	 * @generated
	 */
	EClass getPage();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Page#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Page#getName()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Page#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Page#getLinks()
	 * @see #getPage()
	 * @generated
	 */
	EReference getPage_Links();

	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.DynamicPage <em>Dynamic Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dynamic Page</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.DynamicPage
	 * @generated
	 */
	EClass getDynamicPage();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.DynamicPage#getShows <em>Shows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Shows</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.DynamicPage#getShows()
	 * @see #getDynamicPage()
	 * @generated
	 */
	EReference getDynamicPage_Shows();

	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.StaticPage <em>Static Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Static Page</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.StaticPage
	 * @generated
	 */
	EClass getStaticPage();

	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.IndexPage <em>Index Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Index Page</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.IndexPage
	 * @generated
	 */
	EClass getIndexPage();

	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.DataPage <em>Data Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Page</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.DataPage
	 * @generated
	 */
	EClass getDataPage();

	/**
	 * Returns the meta object for class '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Link#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.Link#getTarget()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Target();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	HypertextlayerFactory getHypertextlayerFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextLayerImpl <em>Hypertext Layer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextLayerImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getHypertextLayer()
		 * @generated
		 */
		EClass HYPERTEXT_LAYER = eINSTANCE.getHypertextLayer();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HYPERTEXT_LAYER__NAME = eINSTANCE.getHypertextLayer_Name();

		/**
		 * The meta object literal for the '<em><b>Pages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HYPERTEXT_LAYER__PAGES = eINSTANCE.getHypertextLayer_Pages();

		/**
		 * The meta object literal for the '<em><b>Start Page</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HYPERTEXT_LAYER__START_PAGE = eINSTANCE.getHypertextLayer_StartPage();

		/**
		 * The meta object literal for the '<em><b>Data Layer</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HYPERTEXT_LAYER__DATA_LAYER = eINSTANCE.getHypertextLayer_DataLayer();

		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.PageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.PageImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getPage()
		 * @generated
		 */
		EClass PAGE = eINSTANCE.getPage();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAGE__NAME = eINSTANCE.getPage_Name();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAGE__LINKS = eINSTANCE.getPage_Links();

		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.DynamicPageImpl <em>Dynamic Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.DynamicPageImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getDynamicPage()
		 * @generated
		 */
		EClass DYNAMIC_PAGE = eINSTANCE.getDynamicPage();

		/**
		 * The meta object literal for the '<em><b>Shows</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DYNAMIC_PAGE__SHOWS = eINSTANCE.getDynamicPage_Shows();

		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.StaticPageImpl <em>Static Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.StaticPageImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getStaticPage()
		 * @generated
		 */
		EClass STATIC_PAGE = eINSTANCE.getStaticPage();

		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.IndexPageImpl <em>Index Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.IndexPageImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getIndexPage()
		 * @generated
		 */
		EClass INDEX_PAGE = eINSTANCE.getIndexPage();

		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.DataPageImpl <em>Data Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.DataPageImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getDataPage()
		 * @generated
		 */
		EClass DATA_PAGE = eINSTANCE.getDataPage();

		/**
		 * The meta object literal for the '{@link org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.LinkImpl
		 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__TARGET = eINSTANCE.getLink_Target();

	}

} //HypertextlayerPackage
