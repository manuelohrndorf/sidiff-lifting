/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.util;

import de.imotep.core.behavior.de_imotep_core_behavior.*;

import de.imotep.core.datamodel.de_imotep_core_datamodel.MEntity;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MNamedEntity;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage
 * @generated
 */
public class De_imotep_core_behaviorSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static De_imotep_core_behaviorPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_behaviorSwitch() {
		if (modelPackage == null) {
			modelPackage = De_imotep_core_behaviorPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE: {
				MStateMachine mStateMachine = (MStateMachine)theEObject;
				T result = caseMStateMachine(mStateMachine);
				if (result == null) result = caseMBehaviorEntity(mStateMachine);
				if (result == null) result = caseMNamedEntity(mStateMachine);
				if (result == null) result = caseMEntity(mStateMachine);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MFINAL_STATE: {
				MFinalState mFinalState = (MFinalState)theEObject;
				T result = caseMFinalState(mFinalState);
				if (result == null) result = caseMAbstractState(mFinalState);
				if (result == null) result = caseMBehaviorEntity(mFinalState);
				if (result == null) result = caseMNamedEntity(mFinalState);
				if (result == null) result = caseMEntity(mFinalState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MERROR_STATE: {
				MErrorState mErrorState = (MErrorState)theEObject;
				T result = caseMErrorState(mErrorState);
				if (result == null) result = caseMState(mErrorState);
				if (result == null) result = caseMAbstractState(mErrorState);
				if (result == null) result = caseMBehaviorEntity(mErrorState);
				if (result == null) result = caseMNamedEntity(mErrorState);
				if (result == null) result = caseMEntity(mErrorState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE_STATE: {
				MStateMachineState mStateMachineState = (MStateMachineState)theEObject;
				T result = caseMStateMachineState(mStateMachineState);
				if (result == null) result = caseMAbstractState(mStateMachineState);
				if (result == null) result = caseMBehaviorEntity(mStateMachineState);
				if (result == null) result = caseMNamedEntity(mStateMachineState);
				if (result == null) result = caseMEntity(mStateMachineState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MTERMINATE_STATE: {
				MTerminateState mTerminateState = (MTerminateState)theEObject;
				T result = caseMTerminateState(mTerminateState);
				if (result == null) result = caseMAbstractState(mTerminateState);
				if (result == null) result = caseMBehaviorEntity(mTerminateState);
				if (result == null) result = caseMNamedEntity(mTerminateState);
				if (result == null) result = caseMEntity(mTerminateState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MTRANSITION: {
				MTransition mTransition = (MTransition)theEObject;
				T result = caseMTransition(mTransition);
				if (result == null) result = caseMBehaviorEntity(mTransition);
				if (result == null) result = caseMNamedEntity(mTransition);
				if (result == null) result = caseMEntity(mTransition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MINITIAL_STATE: {
				MInitialState mInitialState = (MInitialState)theEObject;
				T result = caseMInitialState(mInitialState);
				if (result == null) result = caseMAbstractState(mInitialState);
				if (result == null) result = caseMBehaviorEntity(mInitialState);
				if (result == null) result = caseMNamedEntity(mInitialState);
				if (result == null) result = caseMEntity(mInitialState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MBEHAVIOR_ENTITY: {
				MBehaviorEntity mBehaviorEntity = (MBehaviorEntity)theEObject;
				T result = caseMBehaviorEntity(mBehaviorEntity);
				if (result == null) result = caseMNamedEntity(mBehaviorEntity);
				if (result == null) result = caseMEntity(mBehaviorEntity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MSTATE: {
				MState mState = (MState)theEObject;
				T result = caseMState(mState);
				if (result == null) result = caseMAbstractState(mState);
				if (result == null) result = caseMBehaviorEntity(mState);
				if (result == null) result = caseMNamedEntity(mState);
				if (result == null) result = caseMEntity(mState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MEVENT: {
				MEvent mEvent = (MEvent)theEObject;
				T result = caseMEvent(mEvent);
				if (result == null) result = caseMBehaviorEntity(mEvent);
				if (result == null) result = caseMNamedEntity(mEvent);
				if (result == null) result = caseMEntity(mEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MHISTORY_STATE: {
				MHistoryState mHistoryState = (MHistoryState)theEObject;
				T result = caseMHistoryState(mHistoryState);
				if (result == null) result = caseMAbstractState(mHistoryState);
				if (result == null) result = caseMBehaviorEntity(mHistoryState);
				if (result == null) result = caseMNamedEntity(mHistoryState);
				if (result == null) result = caseMEntity(mHistoryState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MGUARD: {
				MGuard mGuard = (MGuard)theEObject;
				T result = caseMGuard(mGuard);
				if (result == null) result = caseMBehaviorEntity(mGuard);
				if (result == null) result = caseMNamedEntity(mGuard);
				if (result == null) result = caseMEntity(mGuard);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MREGION: {
				MRegion mRegion = (MRegion)theEObject;
				T result = caseMRegion(mRegion);
				if (result == null) result = caseMBehaviorEntity(mRegion);
				if (result == null) result = caseMNamedEntity(mRegion);
				if (result == null) result = caseMEntity(mRegion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MACTION: {
				MAction mAction = (MAction)theEObject;
				T result = caseMAction(mAction);
				if (result == null) result = caseMBehaviorEntity(mAction);
				if (result == null) result = caseMNamedEntity(mAction);
				if (result == null) result = caseMEntity(mAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT: {
				MCodeFragment mCodeFragment = (MCodeFragment)theEObject;
				T result = caseMCodeFragment(mCodeFragment);
				if (result == null) result = caseMBehaviorEntity(mCodeFragment);
				if (result == null) result = caseMNamedEntity(mCodeFragment);
				if (result == null) result = caseMEntity(mCodeFragment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MSTATE_GROUP: {
				MStateGroup mStateGroup = (MStateGroup)theEObject;
				T result = caseMStateGroup(mStateGroup);
				if (result == null) result = caseMBehaviorEntity(mStateGroup);
				if (result == null) result = caseMNamedEntity(mStateGroup);
				if (result == null) result = caseMEntity(mStateGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE: {
				MAbstractState mAbstractState = (MAbstractState)theEObject;
				T result = caseMAbstractState(mAbstractState);
				if (result == null) result = caseMBehaviorEntity(mAbstractState);
				if (result == null) result = caseMNamedEntity(mAbstractState);
				if (result == null) result = caseMEntity(mAbstractState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case De_imotep_core_behaviorPackage.MERROR_TRANSITION: {
				MErrorTransition mErrorTransition = (MErrorTransition)theEObject;
				T result = caseMErrorTransition(mErrorTransition);
				if (result == null) result = caseMTransition(mErrorTransition);
				if (result == null) result = caseMBehaviorEntity(mErrorTransition);
				if (result == null) result = caseMNamedEntity(mErrorTransition);
				if (result == null) result = caseMEntity(mErrorTransition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MState Machine</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MState Machine</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMStateMachine(MStateMachine object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MFinal State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MFinal State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMFinalState(MFinalState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MError State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MError State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMErrorState(MErrorState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MState Machine State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MState Machine State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMStateMachineState(MStateMachineState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MTerminate State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MTerminate State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMTerminateState(MTerminateState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MTransition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MTransition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMTransition(MTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MInitial State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MInitial State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMInitialState(MInitialState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MBehavior Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MBehavior Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMBehaviorEntity(MBehaviorEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MState</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MState</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMState(MState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MEvent</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MEvent</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMEvent(MEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MHistory State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MHistory State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMHistoryState(MHistoryState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MGuard</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MGuard</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMGuard(MGuard object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MRegion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MRegion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMRegion(MRegion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MAction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MAction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMAction(MAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MCode Fragment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MCode Fragment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMCodeFragment(MCodeFragment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MState Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MState Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMStateGroup(MStateGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MAbstract State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MAbstract State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMAbstractState(MAbstractState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MError Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MError Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMErrorTransition(MErrorTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MEntity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MEntity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMEntity(MEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>MNamed Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>MNamed Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMNamedEntity(MNamedEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //De_imotep_core_behaviorSwitch
