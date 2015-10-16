/**
 */
package org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vertex</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A vertex is an abstraction of a node in a state machine graph. In general, it can be the source or destination of any number of transitions.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getContainer <em>Container</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getOutgoing <em>Outgoing</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getVertex()
 * @model abstract="true"
 * @generated
 */
public interface Vertex extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Container</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getSubvertex <em>Subvertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The region that contains this vertex.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Container</em>' container reference.
	 * @see #setContainer(Region)
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getVertex_Container()
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getSubvertex
	 * @model opposite="subvertex" transient="false" ordered="false"
	 * @generated
	 */
	Region getContainer();

	/**
	 * Sets the value of the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getContainer <em>Container</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container</em>' container reference.
	 * @see #getContainer()
	 * @generated
	 */
	void setContainer(Region value);

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies the transitions entering this vertex.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getVertex_Incoming()
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getTarget
	 * @model opposite="target" changeable="false" ordered="false"
	 * @generated
	 */
	EList<Transition> getIncoming();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies the transitions departing from this vertex.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getVertex_Outgoing()
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getSource
	 * @model opposite="source" changeable="false" ordered="false"
	 * @generated
	 */
	EList<Transition> getOutgoing();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The operation containingStateMachine() returns the state machine in which this Vertex is defined
	 * result = if not container->isEmpty()
	 * then
	 * -- the container is a region
	 * container.containingStateMachine()
	 * else if (oclIsKindOf(Pseudostate)) then
	 * -- entry or exit point?
	 * if (kind = #entryPoint) or (kind = #exitPoint) then
	 * stateMachine
	 * else if (oclIsKindOf(ConnectionPointReference)) then
	 * state.containingStateMachine() -- no other valid cases possible
	 * endif
	 * 
	 * <!-- end-model-doc -->
	 * @model required="true" ordered="false"
	 * @generated
	 */
	StateMachine containingStateMachine();

} // Vertex
