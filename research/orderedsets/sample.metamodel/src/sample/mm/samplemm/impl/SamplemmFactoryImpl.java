/**
 */
package sample.mm.samplemm.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import sample.mm.samplemm.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SamplemmFactoryImpl extends EFactoryImpl implements SamplemmFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SamplemmFactory init() {
		try {
			SamplemmFactory theSamplemmFactory = (SamplemmFactory)EPackage.Registry.INSTANCE.getEFactory("http://samplemm/1.0"); 
			if (theSamplemmFactory != null) {
				return theSamplemmFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SamplemmFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamplemmFactoryImpl() {
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
			case SamplemmPackage.NC_LIST: return createNCList();
			case SamplemmPackage.ITEM: return createItem();
			case SamplemmPackage.SAMPLE_METAMODEL: return createSampleMetamodel();
			case SamplemmPackage.CLIST: return createCList();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NCList createNCList() {
		NCListImpl ncList = new NCListImpl();
		return ncList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Item createItem() {
		ItemImpl item = new ItemImpl();
		return item;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SampleMetamodel createSampleMetamodel() {
		SampleMetamodelImpl sampleMetamodel = new SampleMetamodelImpl();
		return sampleMetamodel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CList createCList() {
		CListImpl cList = new CListImpl();
		return cList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamplemmPackage getSamplemmPackage() {
		return (SamplemmPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SamplemmPackage getPackage() {
		return SamplemmPackage.eINSTANCE;
	}

} //SamplemmFactoryImpl
