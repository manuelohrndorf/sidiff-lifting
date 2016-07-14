/**
 */
package org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HypertextlayerFactoryImpl extends EFactoryImpl implements HypertextlayerFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HypertextlayerFactory init() {
		try {
			HypertextlayerFactory theHypertextlayerFactory = (HypertextlayerFactory)EPackage.Registry.INSTANCE.getEFactory(HypertextlayerPackage.eNS_URI);
			if (theHypertextlayerFactory != null) {
				return theHypertextlayerFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HypertextlayerFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HypertextlayerFactoryImpl() {
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
			case HypertextlayerPackage.HYPERTEXT_LAYER: return createHypertextLayer();
			case HypertextlayerPackage.STATIC_PAGE: return createStaticPage();
			case HypertextlayerPackage.INDEX_PAGE: return createIndexPage();
			case HypertextlayerPackage.DATA_PAGE: return createDataPage();
			case HypertextlayerPackage.LINK: return createLink();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
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
	public Link createLink() {
		LinkImpl link = new LinkImpl();
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HypertextlayerPackage getHypertextlayerPackage() {
		return (HypertextlayerPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static HypertextlayerPackage getPackage() {
		return HypertextlayerPackage.eINSTANCE;
	}

} //HypertextlayerFactoryImpl
