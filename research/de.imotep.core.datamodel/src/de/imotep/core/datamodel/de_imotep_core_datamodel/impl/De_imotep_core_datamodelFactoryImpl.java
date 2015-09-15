/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel.impl;

import de.imotep.core.datamodel.de_imotep_core_datamodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class De_imotep_core_datamodelFactoryImpl extends EFactoryImpl implements De_imotep_core_datamodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static De_imotep_core_datamodelFactory init() {
		try {
			De_imotep_core_datamodelFactory theDe_imotep_core_datamodelFactory = (De_imotep_core_datamodelFactory)EPackage.Registry.INSTANCE.getEFactory(De_imotep_core_datamodelPackage.eNS_URI);
			if (theDe_imotep_core_datamodelFactory != null) {
				return theDe_imotep_core_datamodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new De_imotep_core_datamodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_datamodelFactoryImpl() {
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
			case De_imotep_core_datamodelPackage.MNAMED_ENTITY: return createMNamedEntity();
			case De_imotep_core_datamodelPackage.MINTEGER_ATTRIBUTE: return createMIntegerAttribute();
			case De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE: return createMStringAttribute();
			case De_imotep_core_datamodelPackage.MENTITY: return createMEntity();
			case De_imotep_core_datamodelPackage.MRANGED_INTEGER_ATTRIBUTE: return createMRangedIntegerAttribute();
			case De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE: return createMBooleanAttribute();
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
			case De_imotep_core_datamodelPackage.ME_VISIBILITY:
				return createMEVisibilityFromString(eDataType, initialValue);
			case De_imotep_core_datamodelPackage.JOBJECT:
				return createJObjectFromString(eDataType, initialValue);
			case De_imotep_core_datamodelPackage.JCOLLECTION:
				return createJCollectionFromString(eDataType, initialValue);
			case De_imotep_core_datamodelPackage.JLIST:
				return createJListFromString(eDataType, initialValue);
			case De_imotep_core_datamodelPackage.JSET:
				return createJSetFromString(eDataType, initialValue);
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
			case De_imotep_core_datamodelPackage.ME_VISIBILITY:
				return convertMEVisibilityToString(eDataType, instanceValue);
			case De_imotep_core_datamodelPackage.JOBJECT:
				return convertJObjectToString(eDataType, instanceValue);
			case De_imotep_core_datamodelPackage.JCOLLECTION:
				return convertJCollectionToString(eDataType, instanceValue);
			case De_imotep_core_datamodelPackage.JLIST:
				return convertJListToString(eDataType, instanceValue);
			case De_imotep_core_datamodelPackage.JSET:
				return convertJSetToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MNamedEntity createMNamedEntity() {
		MNamedEntityImpl mNamedEntity = new MNamedEntityImpl();
		return mNamedEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MIntegerAttribute createMIntegerAttribute() {
		MIntegerAttributeImpl mIntegerAttribute = new MIntegerAttributeImpl();
		return mIntegerAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStringAttribute createMStringAttribute() {
		MStringAttributeImpl mStringAttribute = new MStringAttributeImpl();
		return mStringAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MEntity createMEntity() {
		MEntityImpl mEntity = new MEntityImpl();
		return mEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MRangedIntegerAttribute createMRangedIntegerAttribute() {
		MRangedIntegerAttributeImpl mRangedIntegerAttribute = new MRangedIntegerAttributeImpl();
		return mRangedIntegerAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MBooleanAttribute createMBooleanAttribute() {
		MBooleanAttributeImpl mBooleanAttribute = new MBooleanAttributeImpl();
		return mBooleanAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MEVisibility createMEVisibilityFromString(EDataType eDataType, String initialValue) {
		MEVisibility result = MEVisibility.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMEVisibilityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createJObjectFromString(EDataType eDataType, String initialValue) {
		return super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJObjectToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createJCollectionFromString(EDataType eDataType, String initialValue) {
		return super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJCollectionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createJListFromString(EDataType eDataType, String initialValue) {
		return super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJListToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createJSetFromString(EDataType eDataType, String initialValue) {
		return super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertJSetToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_datamodelPackage getDe_imotep_core_datamodelPackage() {
		return (De_imotep_core_datamodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static De_imotep_core_datamodelPackage getPackage() {
		return De_imotep_core_datamodelPackage.eINSTANCE;
	}

} //De_imotep_core_datamodelFactoryImpl
