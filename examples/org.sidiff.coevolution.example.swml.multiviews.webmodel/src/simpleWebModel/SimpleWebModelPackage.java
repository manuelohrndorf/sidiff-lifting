/**
 */
package simpleWebModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see simpleWebModel.SimpleWebModelFactory
 * @model kind="package"
 * @generated
 */
public interface SimpleWebModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "simpleWebModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://simpleWebModel/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "simpleWebModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimpleWebModelPackage eINSTANCE = simpleWebModel.impl.SimpleWebModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link simpleWebModel.impl.WebModelImpl <em>Web Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.WebModelImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getWebModel()
	 * @generated
	 */
	int WEB_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Data Layer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_MODEL__DATA_LAYER = 1;

	/**
	 * The feature id for the '<em><b>Hypertext Layer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_MODEL__HYPERTEXT_LAYER = 2;

	/**
	 * The number of structural features of the '<em>Web Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_MODEL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Web Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEB_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link simpleWebModel.impl.DataLayerImpl <em>Data Layer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.DataLayerImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getDataLayer()
	 * @generated
	 */
	int DATA_LAYER = 1;

	/**
	 * The feature id for the '<em><b>Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_LAYER__ENTITIES = 0;

	/**
	 * The number of structural features of the '<em>Data Layer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_LAYER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Data Layer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_LAYER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link simpleWebModel.impl.EntityImpl <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.EntityImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getEntity()
	 * @generated
	 */
	int ENTITY = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__ATTRIBUTES = 1;

	/**
	 * The feature id for the '<em><b>References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__REFERENCES = 2;

	/**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link simpleWebModel.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.AttributeImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link simpleWebModel.impl.ReferenceImpl <em>Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.ReferenceImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getReference()
	 * @generated
	 */
	int REFERENCE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link simpleWebModel.impl.HypertextLayerImpl <em>Hypertext Layer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.HypertextLayerImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getHypertextLayer()
	 * @generated
	 */
	int HYPERTEXT_LAYER = 5;

	/**
	 * The feature id for the '<em><b>Pages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER__PAGES = 0;

	/**
	 * The feature id for the '<em><b>Start Page</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER__START_PAGE = 1;

	/**
	 * The feature id for the '<em><b>Data Layer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER__DATA_LAYER = 2;

	/**
	 * The number of structural features of the '<em>Hypertext Layer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Hypertext Layer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYPERTEXT_LAYER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link simpleWebModel.impl.PageImpl <em>Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.PageImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getPage()
	 * @generated
	 */
	int PAGE = 6;

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
	 * The meta object id for the '{@link simpleWebModel.impl.StaticPageImpl <em>Static Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.StaticPageImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getStaticPage()
	 * @generated
	 */
	int STATIC_PAGE = 7;

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
	 * The meta object id for the '{@link simpleWebModel.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.LinkImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 8;

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
	 * The meta object id for the '{@link simpleWebModel.impl.DynamicPageImpl <em>Dynamic Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.DynamicPageImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getDynamicPage()
	 * @generated
	 */
	int DYNAMIC_PAGE = 9;

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
	 * The meta object id for the '{@link simpleWebModel.impl.IndexPageImpl <em>Index Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.IndexPageImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getIndexPage()
	 * @generated
	 */
	int INDEX_PAGE = 10;

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
	 * The meta object id for the '{@link simpleWebModel.impl.DataPageImpl <em>Data Page</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.impl.DataPageImpl
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getDataPage()
	 * @generated
	 */
	int DATA_PAGE = 11;

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
	 * The meta object id for the '{@link simpleWebModel.SimpleType <em>Simple Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see simpleWebModel.SimpleType
	 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getSimpleType()
	 * @generated
	 */
	int SIMPLE_TYPE = 12;


	/**
	 * Returns the meta object for class '{@link simpleWebModel.WebModel <em>Web Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Web Model</em>'.
	 * @see simpleWebModel.WebModel
	 * @generated
	 */
	EClass getWebModel();

	/**
	 * Returns the meta object for the attribute '{@link simpleWebModel.WebModel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see simpleWebModel.WebModel#getName()
	 * @see #getWebModel()
	 * @generated
	 */
	EAttribute getWebModel_Name();

	/**
	 * Returns the meta object for the containment reference '{@link simpleWebModel.WebModel#getDataLayer <em>Data Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data Layer</em>'.
	 * @see simpleWebModel.WebModel#getDataLayer()
	 * @see #getWebModel()
	 * @generated
	 */
	EReference getWebModel_DataLayer();

	/**
	 * Returns the meta object for the containment reference '{@link simpleWebModel.WebModel#getHypertextLayer <em>Hypertext Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Hypertext Layer</em>'.
	 * @see simpleWebModel.WebModel#getHypertextLayer()
	 * @see #getWebModel()
	 * @generated
	 */
	EReference getWebModel_HypertextLayer();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.DataLayer <em>Data Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Layer</em>'.
	 * @see simpleWebModel.DataLayer
	 * @generated
	 */
	EClass getDataLayer();

	/**
	 * Returns the meta object for the containment reference list '{@link simpleWebModel.DataLayer#getEntities <em>Entities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entities</em>'.
	 * @see simpleWebModel.DataLayer#getEntities()
	 * @see #getDataLayer()
	 * @generated
	 */
	EReference getDataLayer_Entities();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see simpleWebModel.Entity
	 * @generated
	 */
	EClass getEntity();

	/**
	 * Returns the meta object for the attribute '{@link simpleWebModel.Entity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see simpleWebModel.Entity#getName()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link simpleWebModel.Entity#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see simpleWebModel.Entity#getAttributes()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_Attributes();

	/**
	 * Returns the meta object for the containment reference list '{@link simpleWebModel.Entity#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>References</em>'.
	 * @see simpleWebModel.Entity#getReferences()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_References();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see simpleWebModel.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the attribute '{@link simpleWebModel.Attribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see simpleWebModel.Attribute#getName()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Name();

	/**
	 * Returns the meta object for the attribute '{@link simpleWebModel.Attribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see simpleWebModel.Attribute#getType()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Type();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.Reference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference</em>'.
	 * @see simpleWebModel.Reference
	 * @generated
	 */
	EClass getReference();

	/**
	 * Returns the meta object for the attribute '{@link simpleWebModel.Reference#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see simpleWebModel.Reference#getName()
	 * @see #getReference()
	 * @generated
	 */
	EAttribute getReference_Name();

	/**
	 * Returns the meta object for the reference '{@link simpleWebModel.Reference#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see simpleWebModel.Reference#getType()
	 * @see #getReference()
	 * @generated
	 */
	EReference getReference_Type();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.HypertextLayer <em>Hypertext Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hypertext Layer</em>'.
	 * @see simpleWebModel.HypertextLayer
	 * @generated
	 */
	EClass getHypertextLayer();

	/**
	 * Returns the meta object for the containment reference list '{@link simpleWebModel.HypertextLayer#getPages <em>Pages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Pages</em>'.
	 * @see simpleWebModel.HypertextLayer#getPages()
	 * @see #getHypertextLayer()
	 * @generated
	 */
	EReference getHypertextLayer_Pages();

	/**
	 * Returns the meta object for the reference '{@link simpleWebModel.HypertextLayer#getStartPage <em>Start Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start Page</em>'.
	 * @see simpleWebModel.HypertextLayer#getStartPage()
	 * @see #getHypertextLayer()
	 * @generated
	 */
	EReference getHypertextLayer_StartPage();

	/**
	 * Returns the meta object for the reference '{@link simpleWebModel.HypertextLayer#getDataLayer <em>Data Layer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Data Layer</em>'.
	 * @see simpleWebModel.HypertextLayer#getDataLayer()
	 * @see #getHypertextLayer()
	 * @generated
	 */
	EReference getHypertextLayer_DataLayer();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.Page <em>Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Page</em>'.
	 * @see simpleWebModel.Page
	 * @generated
	 */
	EClass getPage();

	/**
	 * Returns the meta object for the attribute '{@link simpleWebModel.Page#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see simpleWebModel.Page#getName()
	 * @see #getPage()
	 * @generated
	 */
	EAttribute getPage_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link simpleWebModel.Page#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see simpleWebModel.Page#getLinks()
	 * @see #getPage()
	 * @generated
	 */
	EReference getPage_Links();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.StaticPage <em>Static Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Static Page</em>'.
	 * @see simpleWebModel.StaticPage
	 * @generated
	 */
	EClass getStaticPage();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see simpleWebModel.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '{@link simpleWebModel.Link#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see simpleWebModel.Link#getTarget()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Target();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.DynamicPage <em>Dynamic Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dynamic Page</em>'.
	 * @see simpleWebModel.DynamicPage
	 * @generated
	 */
	EClass getDynamicPage();

	/**
	 * Returns the meta object for the reference '{@link simpleWebModel.DynamicPage#getShows <em>Shows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Shows</em>'.
	 * @see simpleWebModel.DynamicPage#getShows()
	 * @see #getDynamicPage()
	 * @generated
	 */
	EReference getDynamicPage_Shows();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.IndexPage <em>Index Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Index Page</em>'.
	 * @see simpleWebModel.IndexPage
	 * @generated
	 */
	EClass getIndexPage();

	/**
	 * Returns the meta object for class '{@link simpleWebModel.DataPage <em>Data Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Page</em>'.
	 * @see simpleWebModel.DataPage
	 * @generated
	 */
	EClass getDataPage();

	/**
	 * Returns the meta object for enum '{@link simpleWebModel.SimpleType <em>Simple Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Simple Type</em>'.
	 * @see simpleWebModel.SimpleType
	 * @generated
	 */
	EEnum getSimpleType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SimpleWebModelFactory getSimpleWebModelFactory();

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
		 * The meta object literal for the '{@link simpleWebModel.impl.WebModelImpl <em>Web Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.WebModelImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getWebModel()
		 * @generated
		 */
		EClass WEB_MODEL = eINSTANCE.getWebModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEB_MODEL__NAME = eINSTANCE.getWebModel_Name();

		/**
		 * The meta object literal for the '<em><b>Data Layer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WEB_MODEL__DATA_LAYER = eINSTANCE.getWebModel_DataLayer();

		/**
		 * The meta object literal for the '<em><b>Hypertext Layer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WEB_MODEL__HYPERTEXT_LAYER = eINSTANCE.getWebModel_HypertextLayer();

		/**
		 * The meta object literal for the '{@link simpleWebModel.impl.DataLayerImpl <em>Data Layer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.DataLayerImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getDataLayer()
		 * @generated
		 */
		EClass DATA_LAYER = eINSTANCE.getDataLayer();

		/**
		 * The meta object literal for the '<em><b>Entities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_LAYER__ENTITIES = eINSTANCE.getDataLayer_Entities();

		/**
		 * The meta object literal for the '{@link simpleWebModel.impl.EntityImpl <em>Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.EntityImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getEntity()
		 * @generated
		 */
		EClass ENTITY = eINSTANCE.getEntity();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__NAME = eINSTANCE.getEntity_Name();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__ATTRIBUTES = eINSTANCE.getEntity_Attributes();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__REFERENCES = eINSTANCE.getEntity_References();

		/**
		 * The meta object literal for the '{@link simpleWebModel.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.AttributeImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__NAME = eINSTANCE.getAttribute_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

		/**
		 * The meta object literal for the '{@link simpleWebModel.impl.ReferenceImpl <em>Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.ReferenceImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getReference()
		 * @generated
		 */
		EClass REFERENCE = eINSTANCE.getReference();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REFERENCE__NAME = eINSTANCE.getReference_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE__TYPE = eINSTANCE.getReference_Type();

		/**
		 * The meta object literal for the '{@link simpleWebModel.impl.HypertextLayerImpl <em>Hypertext Layer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.HypertextLayerImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getHypertextLayer()
		 * @generated
		 */
		EClass HYPERTEXT_LAYER = eINSTANCE.getHypertextLayer();

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
		 * The meta object literal for the '{@link simpleWebModel.impl.PageImpl <em>Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.PageImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getPage()
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
		 * The meta object literal for the '{@link simpleWebModel.impl.StaticPageImpl <em>Static Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.StaticPageImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getStaticPage()
		 * @generated
		 */
		EClass STATIC_PAGE = eINSTANCE.getStaticPage();

		/**
		 * The meta object literal for the '{@link simpleWebModel.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.LinkImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getLink()
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

		/**
		 * The meta object literal for the '{@link simpleWebModel.impl.DynamicPageImpl <em>Dynamic Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.DynamicPageImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getDynamicPage()
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
		 * The meta object literal for the '{@link simpleWebModel.impl.IndexPageImpl <em>Index Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.IndexPageImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getIndexPage()
		 * @generated
		 */
		EClass INDEX_PAGE = eINSTANCE.getIndexPage();

		/**
		 * The meta object literal for the '{@link simpleWebModel.impl.DataPageImpl <em>Data Page</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.impl.DataPageImpl
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getDataPage()
		 * @generated
		 */
		EClass DATA_PAGE = eINSTANCE.getDataPage();

		/**
		 * The meta object literal for the '{@link simpleWebModel.SimpleType <em>Simple Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see simpleWebModel.SimpleType
		 * @see simpleWebModel.impl.SimpleWebModelPackageImpl#getSimpleType()
		 * @generated
		 */
		EEnum SIMPLE_TYPE = eINSTANCE.getSimpleType();

	}

} //SimpleWebModelPackage
