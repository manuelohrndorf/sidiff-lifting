/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage
 * @generated
 */
public interface De_imotep_core_behaviorFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	De_imotep_core_behaviorFactory eINSTANCE = de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>MState Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MState Machine</em>'.
	 * @generated
	 */
	MStateMachine createMStateMachine();

	/**
	 * Returns a new object of class '<em>MFinal State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MFinal State</em>'.
	 * @generated
	 */
	MFinalState createMFinalState();

	/**
	 * Returns a new object of class '<em>MError State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MError State</em>'.
	 * @generated
	 */
	MErrorState createMErrorState();

	/**
	 * Returns a new object of class '<em>MState Machine State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MState Machine State</em>'.
	 * @generated
	 */
	MStateMachineState createMStateMachineState();

	/**
	 * Returns a new object of class '<em>MTerminate State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MTerminate State</em>'.
	 * @generated
	 */
	MTerminateState createMTerminateState();

	/**
	 * Returns a new object of class '<em>MTransition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MTransition</em>'.
	 * @generated
	 */
	MTransition createMTransition();

	/**
	 * Returns a new object of class '<em>MInitial State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MInitial State</em>'.
	 * @generated
	 */
	MInitialState createMInitialState();

	/**
	 * Returns a new object of class '<em>MState</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MState</em>'.
	 * @generated
	 */
	MState createMState();

	/**
	 * Returns a new object of class '<em>MEvent</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MEvent</em>'.
	 * @generated
	 */
	MEvent createMEvent();

	/**
	 * Returns a new object of class '<em>MHistory State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MHistory State</em>'.
	 * @generated
	 */
	MHistoryState createMHistoryState();

	/**
	 * Returns a new object of class '<em>MGuard</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MGuard</em>'.
	 * @generated
	 */
	MGuard createMGuard();

	/**
	 * Returns a new object of class '<em>MRegion</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MRegion</em>'.
	 * @generated
	 */
	MRegion createMRegion();

	/**
	 * Returns a new object of class '<em>MAction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MAction</em>'.
	 * @generated
	 */
	MAction createMAction();

	/**
	 * Returns a new object of class '<em>MCode Fragment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MCode Fragment</em>'.
	 * @generated
	 */
	MCodeFragment createMCodeFragment();

	/**
	 * Returns a new object of class '<em>MState Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MState Group</em>'.
	 * @generated
	 */
	MStateGroup createMStateGroup();

	/**
	 * Returns a new object of class '<em>MError Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>MError Transition</em>'.
	 * @generated
	 */
	MErrorTransition createMErrorTransition();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	De_imotep_core_behaviorPackage getDe_imotep_core_behaviorPackage();

} //De_imotep_core_behaviorFactory
