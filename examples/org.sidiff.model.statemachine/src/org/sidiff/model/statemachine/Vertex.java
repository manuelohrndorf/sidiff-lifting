/**
 */
package org.sidiff.model.statemachine;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vertex</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.model.statemachine.Vertex#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.Vertex#getIncoming <em>Incoming</em>}</li>
 * </ul>
 *
 * @see org.sidiff.model.statemachine.StatemachinePackage#getVertex()
 * @model abstract="true"
 * @generated
 */
public interface Vertex extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.model.statemachine.Transition}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.model.statemachine.Transition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getVertex_Outgoing()
	 * @see org.sidiff.model.statemachine.Transition#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<Transition> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.model.statemachine.Transition}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.model.statemachine.Transition#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getVertex_Incoming()
	 * @see org.sidiff.model.statemachine.Transition#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<Transition> getIncoming();

} // Vertex
