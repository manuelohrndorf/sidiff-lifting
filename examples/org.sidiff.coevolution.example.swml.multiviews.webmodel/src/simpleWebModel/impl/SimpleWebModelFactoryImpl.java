/**
 */
package simpleWebModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import simpleWebModel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SimpleWebModelFactoryImpl extends EFactoryImpl implements SimpleWebModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SimpleWebModelFactory init() {
		try {
			SimpleWebModelFactory theSimpleWebModelFactory = (SimpleWebModelFactory)EPackage.Registry.INSTANCE.getEFactory(SimpleWebModelPackage.eNS_URI);
			if (theSimpleWebModelFactory != null) {
				return theSimpleWebModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SimpleWebModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleWebModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SimpleWebModelPackage.WEB_MODEL: return createWebModel();
			case SimpleWebModelPackage.DATA_LAYER: return createDataLayer();
			case SimpleWebModelPackage.ENTITY: return createEntity();
			case SimpleWebModelPackage.ATTRIBUTE: return createAttribute();
			case SimpleWebModelPackage.REFERENCE: return createReference();
			case SimpleWebModelPackage.HYPERTEXT_LAYER: return createHypertextLayer();
			case SimpleWebModelPackage.STATIC_PAGE: return createStaticPage();
			case SimpleWebModelPackage.LINK: return createLink();
			case SimpleWebModelPackage.INDEX_PAGE: return createIndexPage();
			case SimpleWebModelPackage.DATA_PAGE: return createDataPage();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case SimpleWebModelPackage.SIMPLE_TYPE:
				return createSimpleTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case SimpleWebModelPackage.SIMPLE_TYPE:
				return convertSimpleTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WebModel createWebModel() {
		WebModelImpl webModel = new WebModelImpl();
		return webModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataLayer createDataLayer() {
		DataLayerImpl dataLayer = new DataLayerImpl();
		return dataLayer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity createEntity() {
		EntityImpl entity = new EntityImpl();
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Reference createReference() {
		ReferenceImpl reference = new ReferenceImpl();
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HypertextLayer createHypertextLayer() {
		HypertextLayerImpl hypertextLayer = new HypertextLayerImpl();
		return hypertextLayer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StaticPage createStaticPage() {
		StaticPageImpl staticPage = new StaticPageImpl();
		return staticPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Link createLink() {
		LinkImpl link = new LinkImpl();
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexPage createIndexPage() {
		IndexPageImpl indexPage = new IndexPageImpl();
		return indexPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataPage createDataPage() {
		DataPageImpl dataPage = new DataPageImpl();
		return dataPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleType createSimpleTypeFromString(EDataType eDataType, String initialValue) {
		SimpleType result = SimpleType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSimpleTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleWebModelPackage getSimpleWebModelPackage() {
		return (SimpleWebModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SimpleWebModelPackage getPackage() {
		return SimpleWebModelPackage.eINSTANCE;
	}

} //SimpleWebModelFactoryImpl
