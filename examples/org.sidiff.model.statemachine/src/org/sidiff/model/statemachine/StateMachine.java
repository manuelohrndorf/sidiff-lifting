/**
 */
package org.sidiff.model.statemachine;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.model.statemachine.StateMachine#getRegions <em>Regions</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.StateMachine#getEvents <em>Events</em>}</li>
 * </ul>
 *
 * @see org.sidiff.model.statemachine.StatemachinePackage#getStateMachine()
 * @model
 * @generated
 */
public interface StateMachine extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Regions</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.model.statemachine.Region}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Regions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Regions</em>' containment reference list.
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getStateMachine_Regions()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Region> getRegions();

	/**
	 * Returns the value of the '<em><b>Events</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.model.statemachine.Event}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Events</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Events</em>' containment reference list.
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getStateMachine_Events()
	 * @model containment="true"
	 * @generated
	 */
	EList<Event> getEvents();

} // StateMachine
