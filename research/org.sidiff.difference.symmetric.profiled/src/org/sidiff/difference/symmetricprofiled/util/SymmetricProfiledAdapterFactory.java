/**
 */
package org.sidiff.difference.symmetricprofiled.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.sidiff.difference.symmetricprofiled.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage
 * @generated
 */
public class SymmetricProfiledAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SymmetricProfiledPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymmetricProfiledAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SymmetricProfiledPackage.eINSTANCE;
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
	protected SymmetricProfiledSwitch<Adapter> modelSwitch =
		new SymmetricProfiledSwitch<Adapter>() {
			@Override
			public Adapter caseProfiledSymmetricDifference(ProfiledSymmetricDifference object) {
				return createProfiledSymmetricDifferenceAdapter();
			}
			@Override
			public Adapter caseProfiledSemanticChangeSet(ProfiledSemanticChangeSet object) {
				return createProfiledSemanticChangeSetAdapter();
			}
			@Override
			public Adapter caseAppliedStereotype(AppliedStereotype object) {
				return createAppliedStereotypeAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference <em>Profiled Symmetric Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSymmetricDifference
	 * @generated
	 */
	public Adapter createProfiledSymmetricDifferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet <em>Profiled Semantic Change Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.symmetricprofiled.ProfiledSemanticChangeSet
	 * @generated
	 */
	public Adapter createProfiledSemanticChangeSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.symmetricprofiled.AppliedStereotype <em>Applied Stereotype</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.symmetricprofiled.AppliedStereotype
	 * @generated
	 */
	public Adapter createAppliedStereotypeAdapter() {
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

} //SymmetricProfiledAdapterFactory
