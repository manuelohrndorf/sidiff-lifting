/**
 */
package org.sidiff.model.statemachine;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Region</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.model.statemachine.Region#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.Region#getVerticies <em>Verticies</em>}</li>
 * </ul>
 *
 * @see org.sidiff.model.statemachine.StatemachinePackage#getRegion()
 * @model
 * @generated
 */
public interface Region extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.model.statemachine.Transition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitions</em>' containment reference list.
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getRegion_Transitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Transition> getTransitions();

	/**
	 * Returns the value of the '<em><b>Verticies</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.model.statemachine.Vertex}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Verticies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Verticies</em>' containment reference list.
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getRegion_Verticies()
	 * @model containment="true"
	 * @generated
	 */
	EList<Vertex> getVerticies();

} // Region
