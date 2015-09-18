/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MState Machine State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState#getStateMachine <em>State Machine</em>}</li>
 * </ul>
 *
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachineState()
 * @model
 * @generated
 */
public interface MStateMachineState extends MAbstractState {
	/**
	 * Returns the value of the '<em><b>State Machine</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Machine</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Machine</em>' containment reference.
	 * @see #setStateMachine(MStateMachine)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachineState_StateMachine()
	 * @model containment="true" required="true"
	 * @generated
	 */
	MStateMachine getStateMachine();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState#getStateMachine <em>State Machine</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Machine</em>' containment reference.
	 * @see #getStateMachine()
	 * @generated
	 */
	void setStateMachine(MStateMachine value);

} // MStateMachineState
