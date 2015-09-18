/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.impl;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorFactory;
import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState;
import de.imotep.core.behavior.de_imotep_core_behavior.MAction;
import de.imotep.core.behavior.de_imotep_core_behavior.MBehaviorEntity;
import de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment;
import de.imotep.core.behavior.de_imotep_core_behavior.MELanguages;
import de.imotep.core.behavior.de_imotep_core_behavior.MErrorState;
import de.imotep.core.behavior.de_imotep_core_behavior.MErrorTransition;
import de.imotep.core.behavior.de_imotep_core_behavior.MEvent;
import de.imotep.core.behavior.de_imotep_core_behavior.MFinalState;
import de.imotep.core.behavior.de_imotep_core_behavior.MGuard;
import de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState;
import de.imotep.core.behavior.de_imotep_core_behavior.MInitialState;
import de.imotep.core.behavior.de_imotep_core_behavior.MRegion;
import de.imotep.core.behavior.de_imotep_core_behavior.MState;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState;
import de.imotep.core.behavior.de_imotep_core_behavior.MTerminateState;
import de.imotep.core.behavior.de_imotep_core_behavior.MTransition;

import de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class De_imotep_core_behaviorPackageImpl extends EPackageImpl implements De_imotep_core_behaviorPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mStateMachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mFinalStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mErrorStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mStateMachineStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mTerminateStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mTransitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mInitialStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mBehaviorEntityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mEventEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mHistoryStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mGuardEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mRegionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mCodeFragmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mStateGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mAbstractStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mErrorTransitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum meLanguagesEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private De_imotep_core_behaviorPackageImpl() {
		super(eNS_URI, De_imotep_core_behaviorFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link De_imotep_core_behaviorPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static De_imotep_core_behaviorPackage init() {
		if (isInited) return (De_imotep_core_behaviorPackage)EPackage.Registry.INSTANCE.getEPackage(De_imotep_core_behaviorPackage.eNS_URI);

		// Obtain or create and register package
		De_imotep_core_behaviorPackageImpl theDe_imotep_core_behaviorPackage = (De_imotep_core_behaviorPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof De_imotep_core_behaviorPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new De_imotep_core_behaviorPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		De_imotep_core_datamodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDe_imotep_core_behaviorPackage.createPackageContents();

		// Initialize created meta-data
		theDe_imotep_core_behaviorPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDe_imotep_core_behaviorPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(De_imotep_core_behaviorPackage.eNS_URI, theDe_imotep_core_behaviorPackage);
		return theDe_imotep_core_behaviorPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMStateMachine() {
		return mStateMachineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateMachine_Regions() {
		return (EReference)mStateMachineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateMachine_Actions() {
		return (EReference)mStateMachineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateMachine_Attributes() {
		return (EReference)mStateMachineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateMachine_RootRegion() {
		return (EReference)mStateMachineEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateMachine_Guards() {
		return (EReference)mStateMachineEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateMachine_Events() {
		return (EReference)mStateMachineEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMStateMachine_Position() {
		return (EAttribute)mStateMachineEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMStateMachine_Type() {
		return (EAttribute)mStateMachineEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMFinalState() {
		return mFinalStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMErrorState() {
		return mErrorStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMStateMachineState() {
		return mStateMachineStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateMachineState_StateMachine() {
		return (EReference)mStateMachineStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMTerminateState() {
		return mTerminateStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMTransition() {
		return mTransitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMTransition_SourceState() {
		return (EReference)mTransitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMTransition_TargetState() {
		return (EReference)mTransitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMTransition_Guard() {
		return (EReference)mTransitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMTransition_Event() {
		return (EReference)mTransitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMTransition_ParentRegion() {
		return (EReference)mTransitionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMTransition_Actions() {
		return (EReference)mTransitionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMTransition_Internal() {
		return (EAttribute)mTransitionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMTransition_Priority() {
		return (EAttribute)mTransitionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMInitialState() {
		return mInitialStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMBehaviorEntity() {
		return mBehaviorEntityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMState() {
		return mStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMState_OnEntryActions() {
		return (EReference)mStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMState_Regions() {
		return (EReference)mStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMState_DoActions() {
		return (EReference)mStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMState_OnExitActions() {
		return (EReference)mStateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMState_Temporary() {
		return (EAttribute)mStateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMEvent() {
		return mEventEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMHistoryState() {
		return mHistoryStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMHistoryState_Deep() {
		return (EAttribute)mHistoryStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMGuard() {
		return mGuardEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMGuard_Conditions() {
		return (EReference)mGuardEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMRegion() {
		return mRegionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMRegion_ParentState() {
		return (EReference)mRegionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMRegion_StateGroups() {
		return (EReference)mRegionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMRegion_States() {
		return (EReference)mRegionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMRegion_InitialState() {
		return (EReference)mRegionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMRegion_Transitions() {
		return (EReference)mRegionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMRegion_ParentStateMachine() {
		return (EReference)mRegionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMAction() {
		return mActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMAction_Expressions() {
		return (EReference)mActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMCodeFragment() {
		return mCodeFragmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMCodeFragment_Cuse() {
		return (EReference)mCodeFragmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMCodeFragment_Puse() {
		return (EReference)mCodeFragmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMCodeFragment_Duse() {
		return (EReference)mCodeFragmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMCodeFragment_UsedAttributes() {
		return (EReference)mCodeFragmentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMCodeFragment_Expression() {
		return (EAttribute)mCodeFragmentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMCodeFragment_Language() {
		return (EAttribute)mCodeFragmentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMStateGroup() {
		return mStateGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateGroup_ParentRegion() {
		return (EReference)mStateGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateGroup_States() {
		return (EReference)mStateGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateGroup_ParentStateGroup() {
		return (EReference)mStateGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateGroup_StateGroups() {
		return (EReference)mStateGroupEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateGroup_OnExitActions() {
		return (EReference)mStateGroupEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMStateGroup_OnEntryActions() {
		return (EReference)mStateGroupEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMAbstractState() {
		return mAbstractStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMAbstractState_StateGroup() {
		return (EReference)mAbstractStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMAbstractState_ParentRegion() {
		return (EReference)mAbstractStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMAbstractState_OutgoingTransitions() {
		return (EReference)mAbstractStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMAbstractState_IncomingTransitions() {
		return (EReference)mAbstractStateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMErrorTransition() {
		return mErrorTransitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getMELanguages() {
		return meLanguagesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_behaviorFactory getDe_imotep_core_behaviorFactory() {
		return (De_imotep_core_behaviorFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		mStateMachineEClass = createEClass(MSTATE_MACHINE);
		createEReference(mStateMachineEClass, MSTATE_MACHINE__REGIONS);
		createEReference(mStateMachineEClass, MSTATE_MACHINE__ACTIONS);
		createEReference(mStateMachineEClass, MSTATE_MACHINE__ATTRIBUTES);
		createEReference(mStateMachineEClass, MSTATE_MACHINE__ROOT_REGION);
		createEReference(mStateMachineEClass, MSTATE_MACHINE__GUARDS);
		createEReference(mStateMachineEClass, MSTATE_MACHINE__EVENTS);
		createEAttribute(mStateMachineEClass, MSTATE_MACHINE__POSITION);
		createEAttribute(mStateMachineEClass, MSTATE_MACHINE__TYPE);

		mFinalStateEClass = createEClass(MFINAL_STATE);

		mErrorStateEClass = createEClass(MERROR_STATE);

		mStateMachineStateEClass = createEClass(MSTATE_MACHINE_STATE);
		createEReference(mStateMachineStateEClass, MSTATE_MACHINE_STATE__STATE_MACHINE);

		mTerminateStateEClass = createEClass(MTERMINATE_STATE);

		mTransitionEClass = createEClass(MTRANSITION);
		createEReference(mTransitionEClass, MTRANSITION__SOURCE_STATE);
		createEReference(mTransitionEClass, MTRANSITION__TARGET_STATE);
		createEReference(mTransitionEClass, MTRANSITION__GUARD);
		createEReference(mTransitionEClass, MTRANSITION__EVENT);
		createEReference(mTransitionEClass, MTRANSITION__PARENT_REGION);
		createEReference(mTransitionEClass, MTRANSITION__ACTIONS);
		createEAttribute(mTransitionEClass, MTRANSITION__INTERNAL);
		createEAttribute(mTransitionEClass, MTRANSITION__PRIORITY);

		mInitialStateEClass = createEClass(MINITIAL_STATE);

		mBehaviorEntityEClass = createEClass(MBEHAVIOR_ENTITY);

		mStateEClass = createEClass(MSTATE);
		createEReference(mStateEClass, MSTATE__ON_ENTRY_ACTIONS);
		createEReference(mStateEClass, MSTATE__REGIONS);
		createEReference(mStateEClass, MSTATE__DO_ACTIONS);
		createEReference(mStateEClass, MSTATE__ON_EXIT_ACTIONS);
		createEAttribute(mStateEClass, MSTATE__TEMPORARY);

		mEventEClass = createEClass(MEVENT);

		mHistoryStateEClass = createEClass(MHISTORY_STATE);
		createEAttribute(mHistoryStateEClass, MHISTORY_STATE__DEEP);

		mGuardEClass = createEClass(MGUARD);
		createEReference(mGuardEClass, MGUARD__CONDITIONS);

		mRegionEClass = createEClass(MREGION);
		createEReference(mRegionEClass, MREGION__PARENT_STATE);
		createEReference(mRegionEClass, MREGION__STATE_GROUPS);
		createEReference(mRegionEClass, MREGION__STATES);
		createEReference(mRegionEClass, MREGION__INITIAL_STATE);
		createEReference(mRegionEClass, MREGION__TRANSITIONS);
		createEReference(mRegionEClass, MREGION__PARENT_STATE_MACHINE);

		mActionEClass = createEClass(MACTION);
		createEReference(mActionEClass, MACTION__EXPRESSIONS);

		mCodeFragmentEClass = createEClass(MCODE_FRAGMENT);
		createEReference(mCodeFragmentEClass, MCODE_FRAGMENT__CUSE);
		createEReference(mCodeFragmentEClass, MCODE_FRAGMENT__PUSE);
		createEReference(mCodeFragmentEClass, MCODE_FRAGMENT__DUSE);
		createEReference(mCodeFragmentEClass, MCODE_FRAGMENT__USED_ATTRIBUTES);
		createEAttribute(mCodeFragmentEClass, MCODE_FRAGMENT__EXPRESSION);
		createEAttribute(mCodeFragmentEClass, MCODE_FRAGMENT__LANGUAGE);

		mStateGroupEClass = createEClass(MSTATE_GROUP);
		createEReference(mStateGroupEClass, MSTATE_GROUP__PARENT_REGION);
		createEReference(mStateGroupEClass, MSTATE_GROUP__STATES);
		createEReference(mStateGroupEClass, MSTATE_GROUP__PARENT_STATE_GROUP);
		createEReference(mStateGroupEClass, MSTATE_GROUP__STATE_GROUPS);
		createEReference(mStateGroupEClass, MSTATE_GROUP__ON_EXIT_ACTIONS);
		createEReference(mStateGroupEClass, MSTATE_GROUP__ON_ENTRY_ACTIONS);

		mAbstractStateEClass = createEClass(MABSTRACT_STATE);
		createEReference(mAbstractStateEClass, MABSTRACT_STATE__STATE_GROUP);
		createEReference(mAbstractStateEClass, MABSTRACT_STATE__PARENT_REGION);
		createEReference(mAbstractStateEClass, MABSTRACT_STATE__OUTGOING_TRANSITIONS);
		createEReference(mAbstractStateEClass, MABSTRACT_STATE__INCOMING_TRANSITIONS);

		mErrorTransitionEClass = createEClass(MERROR_TRANSITION);

		// Create enums
		meLanguagesEEnum = createEEnum(ME_LANGUAGES);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		De_imotep_core_datamodelPackage theDe_imotep_core_datamodelPackage = (De_imotep_core_datamodelPackage)EPackage.Registry.INSTANCE.getEPackage(De_imotep_core_datamodelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		mStateMachineEClass.getESuperTypes().add(this.getMBehaviorEntity());
		mFinalStateEClass.getESuperTypes().add(this.getMAbstractState());
		mErrorStateEClass.getESuperTypes().add(this.getMState());
		mStateMachineStateEClass.getESuperTypes().add(this.getMAbstractState());
		mTerminateStateEClass.getESuperTypes().add(this.getMAbstractState());
		mTransitionEClass.getESuperTypes().add(this.getMBehaviorEntity());
		mInitialStateEClass.getESuperTypes().add(this.getMAbstractState());
		mBehaviorEntityEClass.getESuperTypes().add(theDe_imotep_core_datamodelPackage.getMNamedEntity());
		mStateEClass.getESuperTypes().add(this.getMAbstractState());
		mEventEClass.getESuperTypes().add(this.getMBehaviorEntity());
		mHistoryStateEClass.getESuperTypes().add(this.getMAbstractState());
		mGuardEClass.getESuperTypes().add(this.getMBehaviorEntity());
		mRegionEClass.getESuperTypes().add(this.getMBehaviorEntity());
		mActionEClass.getESuperTypes().add(this.getMBehaviorEntity());
		mCodeFragmentEClass.getESuperTypes().add(this.getMBehaviorEntity());
		mStateGroupEClass.getESuperTypes().add(this.getMBehaviorEntity());
		mAbstractStateEClass.getESuperTypes().add(this.getMBehaviorEntity());
		mErrorTransitionEClass.getESuperTypes().add(this.getMTransition());

		// Initialize classes, features, and operations; add parameters
		initEClass(mStateMachineEClass, MStateMachine.class, "MStateMachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMStateMachine_Regions(), this.getMRegion(), this.getMRegion_ParentStateMachine(), "regions", null, 0, -1, MStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMStateMachine_Actions(), this.getMAction(), null, "actions", null, 0, -1, MStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMStateMachine_Attributes(), theDe_imotep_core_datamodelPackage.getMAttribute(), null, "attributes", null, 0, -1, MStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMStateMachine_RootRegion(), this.getMRegion(), null, "rootRegion", null, 0, 1, MStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMStateMachine_Guards(), this.getMGuard(), null, "guards", null, 0, -1, MStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMStateMachine_Events(), this.getMEvent(), null, "events", null, 0, -1, MStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMStateMachine_Position(), ecorePackage.getEString(), "position", null, 0, 1, MStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getMStateMachine_Type(), ecorePackage.getEString(), "type", null, 0, 1, MStateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mFinalStateEClass, MFinalState.class, "MFinalState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mErrorStateEClass, MErrorState.class, "MErrorState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mStateMachineStateEClass, MStateMachineState.class, "MStateMachineState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMStateMachineState_StateMachine(), this.getMStateMachine(), null, "stateMachine", null, 1, 1, MStateMachineState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mTerminateStateEClass, MTerminateState.class, "MTerminateState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mTransitionEClass, MTransition.class, "MTransition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMTransition_SourceState(), this.getMAbstractState(), this.getMAbstractState_OutgoingTransitions(), "sourceState", null, 1, 1, MTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMTransition_TargetState(), this.getMAbstractState(), this.getMAbstractState_IncomingTransitions(), "targetState", null, 1, 1, MTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMTransition_Guard(), this.getMGuard(), null, "guard", null, 0, 1, MTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMTransition_Event(), this.getMEvent(), null, "event", null, 0, 1, MTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMTransition_ParentRegion(), this.getMRegion(), null, "parentRegion", null, 1, 1, MTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMTransition_Actions(), this.getMAction(), null, "actions", null, 0, -1, MTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMTransition_Internal(), ecorePackage.getEBoolean(), "internal", null, 1, 1, MTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getMTransition_Priority(), ecorePackage.getEInt(), "priority", null, 1, 1, MTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mInitialStateEClass, MInitialState.class, "MInitialState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mBehaviorEntityEClass, MBehaviorEntity.class, "MBehaviorEntity", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mStateEClass, MState.class, "MState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMState_OnEntryActions(), this.getMAction(), null, "onEntryActions", null, 0, -1, MState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMState_Regions(), this.getMRegion(), this.getMRegion_ParentState(), "regions", null, 0, -1, MState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMState_DoActions(), this.getMAction(), null, "doActions", null, 0, -1, MState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMState_OnExitActions(), this.getMAction(), null, "onExitActions", null, 0, -1, MState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMState_Temporary(), ecorePackage.getEBoolean(), "temporary", null, 1, 1, MState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mEventEClass, MEvent.class, "MEvent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mHistoryStateEClass, MHistoryState.class, "MHistoryState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMHistoryState_Deep(), ecorePackage.getEBoolean(), "deep", null, 1, 1, MHistoryState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mGuardEClass, MGuard.class, "MGuard", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMGuard_Conditions(), this.getMCodeFragment(), null, "conditions", null, 0, -1, MGuard.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mRegionEClass, MRegion.class, "MRegion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMRegion_ParentState(), this.getMState(), this.getMState_Regions(), "parentState", null, 0, 1, MRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMRegion_StateGroups(), this.getMStateGroup(), this.getMStateGroup_ParentRegion(), "stateGroups", null, 0, -1, MRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMRegion_States(), this.getMAbstractState(), null, "states", null, 0, -1, MRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMRegion_InitialState(), this.getMInitialState(), null, "initialState", null, 1, 1, MRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMRegion_Transitions(), this.getMTransition(), null, "transitions", null, 0, -1, MRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMRegion_ParentStateMachine(), this.getMStateMachine(), this.getMStateMachine_Regions(), "parentStateMachine", null, 1, 1, MRegion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mActionEClass, MAction.class, "MAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMAction_Expressions(), this.getMCodeFragment(), null, "expressions", null, 0, -1, MAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mCodeFragmentEClass, MCodeFragment.class, "MCodeFragment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMCodeFragment_Cuse(), theDe_imotep_core_datamodelPackage.getMAttribute(), null, "cuse", null, 0, -1, MCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMCodeFragment_Puse(), theDe_imotep_core_datamodelPackage.getMAttribute(), null, "puse", null, 0, -1, MCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMCodeFragment_Duse(), theDe_imotep_core_datamodelPackage.getMAttribute(), null, "duse", null, 0, -1, MCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMCodeFragment_UsedAttributes(), theDe_imotep_core_datamodelPackage.getMAttribute(), null, "usedAttributes", null, 0, -1, MCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMCodeFragment_Expression(), ecorePackage.getEString(), "expression", null, 1, 1, MCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getMCodeFragment_Language(), this.getMELanguages(), "language", null, 1, 1, MCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mStateGroupEClass, MStateGroup.class, "MStateGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMStateGroup_ParentRegion(), this.getMRegion(), this.getMRegion_StateGroups(), "parentRegion", null, 1, 1, MStateGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMStateGroup_States(), this.getMAbstractState(), this.getMAbstractState_StateGroup(), "states", null, 0, -1, MStateGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMStateGroup_ParentStateGroup(), this.getMStateGroup(), this.getMStateGroup_StateGroups(), "parentStateGroup", null, 0, 1, MStateGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMStateGroup_StateGroups(), this.getMStateGroup(), this.getMStateGroup_ParentStateGroup(), "stateGroups", null, 0, -1, MStateGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMStateGroup_OnExitActions(), this.getMAction(), null, "onExitActions", null, 0, -1, MStateGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMStateGroup_OnEntryActions(), this.getMAction(), null, "onEntryActions", null, 0, -1, MStateGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mAbstractStateEClass, MAbstractState.class, "MAbstractState", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMAbstractState_StateGroup(), this.getMStateGroup(), this.getMStateGroup_States(), "stateGroup", null, 0, 1, MAbstractState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMAbstractState_ParentRegion(), this.getMRegion(), null, "parentRegion", null, 1, 1, MAbstractState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMAbstractState_OutgoingTransitions(), this.getMTransition(), this.getMTransition_SourceState(), "outgoingTransitions", null, 0, -1, MAbstractState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMAbstractState_IncomingTransitions(), this.getMTransition(), this.getMTransition_TargetState(), "incomingTransitions", null, 0, -1, MAbstractState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mErrorTransitionEClass, MErrorTransition.class, "MErrorTransition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(meLanguagesEEnum, MELanguages.class, "MELanguages");
		addEEnumLiteral(meLanguagesEEnum, MELanguages.PROMELA);

		// Create resource
		createResource(eNS_URI);
	}

} //De_imotep_core_behaviorPackageImpl
