/**
 */
package org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Protocol State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A protocol state machine is always defined in the context of a classifier. It specifies which operations of the classifier can be called in which state and under which condition, thus specifying the allowed call sequences on the classifier's operations. A protocol state machine presents the possible and permitted transitions on the instances of its context classifier, together with the operations which carry the transitions. In this manner, an instance lifecycle can be created for a classifier, by specifying the order in which the operations can be activated and the states through which an instance progresses during its existence.
 * <!-- end-model-doc -->
 *
 *
 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getProtocolStateMachine()
 * @model
 * @generated
 */
public interface ProtocolStateMachine extends StateMachine {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The states of a protocol state machine cannot have entry, exit, or do activity actions.
	 * region->forAll(r | r.subvertex->forAll(v | v.oclIsKindOf(State) implies
	 * (v.entry->isEmpty() and v.exit->isEmpty() and v.doActivity->isEmpty())))
	 * 
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean entry_exit_do(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * All transitions of a protocol state machine must be protocol transitions. (transitions as extended by the ProtocolStateMachines package)
	 * region->forAll(r | r.transition->forAll(t | t.oclIsTypeOf(ProtocolTransition)))
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean protocol_transitions(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Protocol state machines cannot have deep or shallow history pseudostates.
	 * region->forAll (r | r.subvertex->forAll (v | v.oclIsKindOf(Psuedostate) implies
	 * ((v.kind <> #deepHistory) and (v.kind <> #shallowHistory)))))
	 * 
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean deep_or_shallow_history(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If two ports are connected, then the protocol state machine of the required interface (if defined) must be conformant to the protocol state machine of the provided interface (if defined).
	 * true
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean ports_connected(DiagnosticChain diagnostics, Map<Object, Object> context);

} // ProtocolStateMachine
