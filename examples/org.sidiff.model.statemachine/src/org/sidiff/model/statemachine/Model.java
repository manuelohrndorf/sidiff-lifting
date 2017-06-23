/**
 */
package org.sidiff.model.statemachine;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.model.statemachine.Model#getStatemachine <em>Statemachine</em>}</li>
 * </ul>
 *
 * @see org.sidiff.model.statemachine.StatemachinePackage#getModel()
 * @model
 * @generated
 */
public interface Model extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Statemachine</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.model.statemachine.StateMachine}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Statemachine</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statemachine</em>' containment reference list.
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getModel_Statemachine()
	 * @model containment="true"
	 * @generated
	 */
	EList<StateMachine> getStatemachine();

} // Model
