/**
 */
package org.silift.difference.symboliclink.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.silift.difference.symboliclink.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.silift.difference.symboliclink.SymboliclinkPackage
 * @generated
 */
public class SymboliclinkAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SymboliclinkPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymboliclinkAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SymboliclinkPackage.eINSTANCE;
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
	protected SymboliclinkSwitch<Adapter> modelSwitch =
		new SymboliclinkSwitch<Adapter>() {
			@Override
			public Adapter caseSymbolicLinks(SymbolicLinks object) {
				return createSymbolicLinksAdapter();
			}
			@Override
			public Adapter caseSymbolicLinkObject(SymbolicLinkObject object) {
				return createSymbolicLinkObjectAdapter();
			}
			@Override
			public Adapter caseSymbolicLinkReference(SymbolicLinkReference object) {
				return createSymbolicLinkReferenceAdapter();
			}
			@Override
			public Adapter caseExternalSymbolicLinkObject(ExternalSymbolicLinkObject object) {
				return createExternalSymbolicLinkObjectAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.silift.difference.symboliclink.SymbolicLinks <em>Symbolic Links</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.silift.difference.symboliclink.SymbolicLinks
	 * @generated
	 */
	public Adapter createSymbolicLinksAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.silift.difference.symboliclink.SymbolicLinkObject <em>Symbolic Link Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject
	 * @generated
	 */
	public Adapter createSymbolicLinkObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.silift.difference.symboliclink.SymbolicLinkReference <em>Symbolic Link Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.silift.difference.symboliclink.SymbolicLinkReference
	 * @generated
	 */
	public Adapter createSymbolicLinkReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.silift.difference.symboliclink.ExternalSymbolicLinkObject <em>External Symbolic Link Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.silift.difference.symboliclink.ExternalSymbolicLinkObject
	 * @generated
	 */
	public Adapter createExternalSymbolicLinkObjectAdapter() {
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

} //SymboliclinkAdapterFactory
