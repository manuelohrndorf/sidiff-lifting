/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.impl;

import de.imotep.core.behavior.de_imotep_core_behavior.*;

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
public class De_imotep_core_behaviorFactoryImpl extends EFactoryImpl implements De_imotep_core_behaviorFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static De_imotep_core_behaviorFactory init() {
		try {
			De_imotep_core_behaviorFactory theDe_imotep_core_behaviorFactory = (De_imotep_core_behaviorFactory)EPackage.Registry.INSTANCE.getEFactory(De_imotep_core_behaviorPackage.eNS_URI);
			if (theDe_imotep_core_behaviorFactory != null) {
				return theDe_imotep_core_behaviorFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new De_imotep_core_behaviorFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_behaviorFactoryImpl() {
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
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE: return createMStateMachine();
			case De_imotep_core_behaviorPackage.MFINAL_STATE: return createMFinalState();
			case De_imotep_core_behaviorPackage.MERROR_STATE: return createMErrorState();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE_STATE: return createMStateMachineState();
			case De_imotep_core_behaviorPackage.MTERMINATE_STATE: return createMTerminateState();
			case De_imotep_core_behaviorPackage.MTRANSITION: return createMTransition();
			case De_imotep_core_behaviorPackage.MINITIAL_STATE: return createMInitialState();
			case De_imotep_core_behaviorPackage.MSTATE: return createMState();
			case De_imotep_core_behaviorPackage.MEVENT: return createMEvent();
			case De_imotep_core_behaviorPackage.MHISTORY_STATE: return createMHistoryState();
			case De_imotep_core_behaviorPackage.MGUARD: return createMGuard();
			case De_imotep_core_behaviorPackage.MREGION: return createMRegion();
			case De_imotep_core_behaviorPackage.MACTION: return createMAction();
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT: return createMCodeFragment();
			case De_imotep_core_behaviorPackage.MSTATE_GROUP: return createMStateGroup();
			case De_imotep_core_behaviorPackage.MERROR_TRANSITION: return createMErrorTransition();
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
			case De_imotep_core_behaviorPackage.ME_LANGUAGES:
				return createMELanguagesFromString(eDataType, initialValue);
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
			case De_imotep_core_behaviorPackage.ME_LANGUAGES:
				return convertMELanguagesToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStateMachine createMStateMachine() {
		MStateMachineImpl mStateMachine = new MStateMachineImpl();
		return mStateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MFinalState createMFinalState() {
		MFinalStateImpl mFinalState = new MFinalStateImpl();
		return mFinalState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MErrorState createMErrorState() {
		MErrorStateImpl mErrorState = new MErrorStateImpl();
		return mErrorState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStateMachineState createMStateMachineState() {
		MStateMachineStateImpl mStateMachineState = new MStateMachineStateImpl();
		return mStateMachineState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MTerminateState createMTerminateState() {
		MTerminateStateImpl mTerminateState = new MTerminateStateImpl();
		return mTerminateState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MTransition createMTransition() {
		MTransitionImpl mTransition = new MTransitionImpl();
		return mTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MInitialState createMInitialState() {
		MInitialStateImpl mInitialState = new MInitialStateImpl();
		return mInitialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MState createMState() {
		MStateImpl mState = new MStateImpl();
		return mState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MEvent createMEvent() {
		MEventImpl mEvent = new MEventImpl();
		return mEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MHistoryState createMHistoryState() {
		MHistoryStateImpl mHistoryState = new MHistoryStateImpl();
		return mHistoryState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MGuard createMGuard() {
		MGuardImpl mGuard = new MGuardImpl();
		return mGuard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MRegion createMRegion() {
		MRegionImpl mRegion = new MRegionImpl();
		return mRegion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MAction createMAction() {
		MActionImpl mAction = new MActionImpl();
		return mAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MCodeFragment createMCodeFragment() {
		MCodeFragmentImpl mCodeFragment = new MCodeFragmentImpl();
		return mCodeFragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStateGroup createMStateGroup() {
		MStateGroupImpl mStateGroup = new MStateGroupImpl();
		return mStateGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MErrorTransition createMErrorTransition() {
		MErrorTransitionImpl mErrorTransition = new MErrorTransitionImpl();
		return mErrorTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MELanguages createMELanguagesFromString(EDataType eDataType, String initialValue) {
		MELanguages result = MELanguages.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMELanguagesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_behaviorPackage getDe_imotep_core_behaviorPackage() {
		return (De_imotep_core_behaviorPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static De_imotep_core_behaviorPackage getPackage() {
		return De_imotep_core_behaviorPackage.eINSTANCE;
	}

} //De_imotep_core_behaviorFactoryImpl
