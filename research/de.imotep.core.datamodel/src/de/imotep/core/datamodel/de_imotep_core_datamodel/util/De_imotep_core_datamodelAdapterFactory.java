/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel.util;

import de.imotep.core.datamodel.de_imotep_core_datamodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage
 * @generated
 */
public class De_imotep_core_datamodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static De_imotep_core_datamodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_datamodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = De_imotep_core_datamodelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected De_imotep_core_datamodelSwitch<Adapter> modelSwitch =
		new De_imotep_core_datamodelSwitch<Adapter>() {
			@Override
			public Adapter caseMAttribute(MAttribute object) {
				return createMAttributeAdapter();
			}
			@Override
			public Adapter caseMNamedEntity(MNamedEntity object) {
				return createMNamedEntityAdapter();
			}
			@Override
			public Adapter caseMIntegerAttribute(MIntegerAttribute object) {
				return createMIntegerAttributeAdapter();
			}
			@Override
			public Adapter caseMStringAttribute(MStringAttribute object) {
				return createMStringAttributeAdapter();
			}
			@Override
			public Adapter caseMEntity(MEntity object) {
				return createMEntityAdapter();
			}
			@Override
			public Adapter caseMRangedIntegerAttribute(MRangedIntegerAttribute object) {
				return createMRangedIntegerAttributeAdapter();
			}
			@Override
			public Adapter caseMBooleanAttribute(MBooleanAttribute object) {
				return createMBooleanAttributeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute <em>MAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute
	 * @generated
	 */
	public Adapter createMAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MNamedEntity <em>MNamed Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MNamedEntity
	 * @generated
	 */
	public Adapter createMNamedEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute <em>MInteger Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MIntegerAttribute
	 * @generated
	 */
	public Adapter createMIntegerAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute <em>MString Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute
	 * @generated
	 */
	public Adapter createMStringAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MEntity <em>MEntity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MEntity
	 * @generated
	 */
	public Adapter createMEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MRangedIntegerAttribute <em>MRanged Integer Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MRangedIntegerAttribute
	 * @generated
	 */
	public Adapter createMRangedIntegerAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.datamodel.de_imotep_core_datamodel.MBooleanAttribute <em>MBoolean Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.MBooleanAttribute
	 * @generated
	 */
	public Adapter createMBooleanAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //De_imotep_core_datamodelAdapterFactory
