/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.util;

import de.imotep.core.behavior.de_imotep_core_behavior.*;

import de.imotep.core.datamodel.de_imotep_core_datamodel.MEntity;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MNamedEntity;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage
 * @generated
 */
public class De_imotep_core_behaviorAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static De_imotep_core_behaviorPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_behaviorAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = De_imotep_core_behaviorPackage.eINSTANCE;
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
	protected De_imotep_core_behaviorSwitch<Adapter> modelSwitch =
		new De_imotep_core_behaviorSwitch<Adapter>() {
			@Override
			public Adapter caseMStateMachine(MStateMachine object) {
				return createMStateMachineAdapter();
			}
			@Override
			public Adapter caseMFinalState(MFinalState object) {
				return createMFinalStateAdapter();
			}
			@Override
			public Adapter caseMErrorState(MErrorState object) {
				return createMErrorStateAdapter();
			}
			@Override
			public Adapter caseMStateMachineState(MStateMachineState object) {
				return createMStateMachineStateAdapter();
			}
			@Override
			public Adapter caseMTerminateState(MTerminateState object) {
				return createMTerminateStateAdapter();
			}
			@Override
			public Adapter caseMTransition(MTransition object) {
				return createMTransitionAdapter();
			}
			@Override
			public Adapter caseMInitialState(MInitialState object) {
				return createMInitialStateAdapter();
			}
			@Override
			public Adapter caseMBehaviorEntity(MBehaviorEntity object) {
				return createMBehaviorEntityAdapter();
			}
			@Override
			public Adapter caseMState(MState object) {
				return createMStateAdapter();
			}
			@Override
			public Adapter caseMEvent(MEvent object) {
				return createMEventAdapter();
			}
			@Override
			public Adapter caseMHistoryState(MHistoryState object) {
				return createMHistoryStateAdapter();
			}
			@Override
			public Adapter caseMGuard(MGuard object) {
				return createMGuardAdapter();
			}
			@Override
			public Adapter caseMRegion(MRegion object) {
				return createMRegionAdapter();
			}
			@Override
			public Adapter caseMAction(MAction object) {
				return createMActionAdapter();
			}
			@Override
			public Adapter caseMCodeFragment(MCodeFragment object) {
				return createMCodeFragmentAdapter();
			}
			@Override
			public Adapter caseMStateGroup(MStateGroup object) {
				return createMStateGroupAdapter();
			}
			@Override
			public Adapter caseMAbstractState(MAbstractState object) {
				return createMAbstractStateAdapter();
			}
			@Override
			public Adapter caseMErrorTransition(MErrorTransition object) {
				return createMErrorTransitionAdapter();
			}
			@Override
			public Adapter caseMEntity(MEntity object) {
				return createMEntityAdapter();
			}
			@Override
			public Adapter caseMNamedEntity(MNamedEntity object) {
				return createMNamedEntityAdapter();
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
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine <em>MState Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine
	 * @generated
	 */
	public Adapter createMStateMachineAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MFinalState <em>MFinal State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MFinalState
	 * @generated
	 */
	public Adapter createMFinalStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MErrorState <em>MError State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MErrorState
	 * @generated
	 */
	public Adapter createMErrorStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState <em>MState Machine State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState
	 * @generated
	 */
	public Adapter createMStateMachineStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTerminateState <em>MTerminate State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTerminateState
	 * @generated
	 */
	public Adapter createMTerminateStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition <em>MTransition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition
	 * @generated
	 */
	public Adapter createMTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MInitialState <em>MInitial State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MInitialState
	 * @generated
	 */
	public Adapter createMInitialStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MBehaviorEntity <em>MBehavior Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MBehaviorEntity
	 * @generated
	 */
	public Adapter createMBehaviorEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MState <em>MState</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MState
	 * @generated
	 */
	public Adapter createMStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MEvent <em>MEvent</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MEvent
	 * @generated
	 */
	public Adapter createMEventAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState <em>MHistory State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState
	 * @generated
	 */
	public Adapter createMHistoryStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MGuard <em>MGuard</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MGuard
	 * @generated
	 */
	public Adapter createMGuardAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion <em>MRegion</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion
	 * @generated
	 */
	public Adapter createMRegionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAction <em>MAction</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MAction
	 * @generated
	 */
	public Adapter createMActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment <em>MCode Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment
	 * @generated
	 */
	public Adapter createMCodeFragmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup <em>MState Group</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup
	 * @generated
	 */
	public Adapter createMStateGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState <em>MAbstract State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState
	 * @generated
	 */
	public Adapter createMAbstractStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MErrorTransition <em>MError Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MErrorTransition
	 * @generated
	 */
	public Adapter createMErrorTransitionAdapter() {
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

} //De_imotep_core_behaviorAdapterFactory
