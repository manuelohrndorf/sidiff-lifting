/**
 */
package org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A state models a situation during which some (usually implicit) invariant condition holds.
 * The states of protocol state machines are exposed to the users of their context classifiers. A protocol state represents an exposed stable situation of its context classifier: when an instance of the classifier is not processing any operation, users of this instance can always know its state configuration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getConnection <em>Connection</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getConnectionPoint <em>Connection Point</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsComposite <em>Is Composite</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsOrthogonal <em>Is Orthogonal</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsSimple <em>Is Simple</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsSubmachineState <em>Is Submachine State</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getSubmachine <em>Submachine</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getRegion <em>Region</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getEnty <em>Enty</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getExit <em>Exit</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getDoActivity <em>Do Activity</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getStateInvariant <em>State Invariant</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState()
 * @model
 * @generated
 */
public interface State extends Namespace, Vertex {
	/**
	 * Returns the value of the '<em><b>Connection</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The entry and exit connection points used in conjunction with this (submachine) state, i.e. as targets and sources, respectively, in the region with the submachine state. A connection point reference references the corresponding definition of a connection point pseudostate in the statemachine referenced by the submachinestate.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Connection</em>' containment reference list.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_Connection()
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#getState
	 * @model opposite="state" containment="true" ordered="false"
	 * @generated
	 */
	EList<ConnectionPointReference> getConnection();

	/**
	 * Returns the value of the '<em><b>Connection Point</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The entry and exit pseudostates of a composite state. These can only be entry or exit Pseudostates, and they must have different names. They can only be defined for composite states.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Connection Point</em>' containment reference list.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_ConnectionPoint()
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#getState
	 * @model opposite="state" containment="true" ordered="false"
	 * @generated
	 */
	EList<Pseudostate> getConnectionPoint();

	/**
	 * Returns the value of the '<em><b>Is Composite</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A state with isComposite=true is said to be a composite state. A composite state is a state that contains at least one region.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Is Composite</em>' attribute.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_IsComposite()
	 * @model default="false" required="true" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	boolean isIsComposite();

	/**
	 * Returns the value of the '<em><b>Is Orthogonal</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A state with isOrthogonal=true is said to be an orthogonal composite state. An orthogonal composite state contains two or more regions.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Is Orthogonal</em>' attribute.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_IsOrthogonal()
	 * @model default="false" required="true" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	boolean isIsOrthogonal();

	/**
	 * Returns the value of the '<em><b>Is Simple</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A state with isSimple=true is said to be a simple state. A simple state does not have any regions and it does not refer to any submachine state machine.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Is Simple</em>' attribute.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_IsSimple()
	 * @model default="true" required="true" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	boolean isIsSimple();

	/**
	 * Returns the value of the '<em><b>Is Submachine State</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A state with isSubmachineState=true is said to be a submachine state. Such a state refers to a state machine (submachine).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Is Submachine State</em>' attribute.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_IsSubmachineState()
	 * @model default="false" required="true" transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	boolean isIsSubmachineState();

	/**
	 * Returns the value of the '<em><b>Submachine</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getSubmachineState <em>Submachine State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The state machine that is to be inserted in place of the (submachine) state.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Submachine</em>' reference.
	 * @see #setSubmachine(StateMachine)
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_Submachine()
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getSubmachineState
	 * @model opposite="submachineState" ordered="false"
	 * @generated
	 */
	StateMachine getSubmachine();

	/**
	 * Sets the value of the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getSubmachine <em>Submachine</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Submachine</em>' reference.
	 * @see #getSubmachine()
	 * @generated
	 */
	void setSubmachine(StateMachine value);

	/**
	 * Returns the value of the '<em><b>Region</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The regions owned directly by the state.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Region</em>' containment reference list.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_Region()
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getState
	 * @model opposite="state" containment="true" ordered="false"
	 * @generated
	 */
	EList<Region> getRegion();

	/**
	 * Returns the value of the '<em><b>Enty</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enty</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enty</em>' containment reference.
	 * @see #setEnty(Behavior)
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_Enty()
	 * @model containment="true"
	 * @generated
	 */
	Behavior getEnty();

	/**
	 * Sets the value of the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getEnty <em>Enty</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enty</em>' containment reference.
	 * @see #getEnty()
	 * @generated
	 */
	void setEnty(Behavior value);

	/**
	 * Returns the value of the '<em><b>Exit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exit</em>' containment reference.
	 * @see #setExit(Behavior)
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_Exit()
	 * @model containment="true"
	 * @generated
	 */
	Behavior getExit();

	/**
	 * Sets the value of the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getExit <em>Exit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exit</em>' containment reference.
	 * @see #getExit()
	 * @generated
	 */
	void setExit(Behavior value);

	/**
	 * Returns the value of the '<em><b>Do Activity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Do Activity</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Do Activity</em>' containment reference.
	 * @see #setDoActivity(Behavior)
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_DoActivity()
	 * @model containment="true"
	 * @generated
	 */
	Behavior getDoActivity();

	/**
	 * Sets the value of the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getDoActivity <em>Do Activity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Do Activity</em>' containment reference.
	 * @see #getDoActivity()
	 * @generated
	 */
	void setDoActivity(Behavior value);

	/**
	 * Returns the value of the '<em><b>State Invariant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Invariant</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Invariant</em>' containment reference.
	 * @see #setStateInvariant(Constraint)
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getState_StateInvariant()
	 * @model containment="true"
	 * @generated
	 */
	Constraint getStateInvariant();

	/**
	 * Sets the value of the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getStateInvariant <em>State Invariant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Invariant</em>' containment reference.
	 * @see #getStateInvariant()
	 * @generated
	 */
	void setStateInvariant(Constraint value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Only entry or exit pseudostates can serve as connection points.
	 * connectionPoint->forAll(cp|cp.kind = #entry or cp.kind = #exit)
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean entry_or_exit(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Only composite states can have entry or exit pseudostates defined.
	 * connectionPoint->notEmpty() implies isComoposite
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean composite_states(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The connection point references used as destinations/sources of transitions associated with a submachine state must be defined as entry/exit points in the submachine state machine.
	 * self.isSubmachineState implies (self.connection->forAll (cp |
	 * cp.entry->forAll (p | p.statemachine = self.submachine) and
	 * cp.exit->forAll (p | p.statemachine = self.submachine)))
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean destinations_or_sources_of_transitions(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A state is not allowed to have both a submachine and regions.
	 * isComposite implies not isSubmachineState
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean submachine_or_regions(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Only submachine states can have connection point references.
	 * isSubmachineState implies connection->notEmpty ( )
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean submachine_states(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A composite state is a state with at least one region.
	 * result = region.notEmpty()
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isComposite();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An orthogonal state is a composite state with at least 2 regions
	 * result = (region->size () > 1)
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isOrthogonal();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The query isRedefinitionContextValid() specifies whether the redefinition contexts of a state are properly related to the redefinition contexts of the specified state to allow this element to redefine the other. The containing region of a redefining state must redefine the containing region of the redefined state.
	 * result = true
	 * <!-- end-model-doc -->
	 * @model dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false" redefinedRequired="true" redefinedOrdered="false"
	 * @generated
	 */
	boolean isRedefinitionContextValid(State redefined);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A simple state is a state without any regions.
	 * result = region.isEmpty()
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isSimple();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Only submachine states can have a reference statemachine.
	 * result = submachine.notEmpty()
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false"
	 * @generated
	 */
	boolean isSubmachineState();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The redefinition context of a state is the nearest containing statemachine.
	 * result = let sm = containingStateMachine() in
	 * if sm.context->isEmpty() or sm.general->notEmpty() then
	 * sm
	 * else
	 * sm.context
	 * endif
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	void redefinitionContext();

} // State
