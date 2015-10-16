/**
 */
package org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * State machines can be used to express the behavior of part of a system. Behavior is modeled as a traversal of a graph of state nodes interconnected by one or more joined transition arcs that are triggered by the dispatching of series of (event) occurrences. During this traversal, the state machine executes a series of activities associated with various elements of the state machine.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getConnectionPoint <em>Connection Point</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getSubmachineState <em>Submachine State</em>}</li>
 *   <li>{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getRegion <em>Region</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getStateMachine()
 * @model
 * @generated
 */
public interface StateMachine extends EObject {
	/**
	 * Returns the value of the '<em><b>Connection Point</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The connection points defined for this state machine. They represent the interface of the state machine when used as part of submachine state.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Connection Point</em>' containment reference list.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getStateMachine_ConnectionPoint()
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#getStateMachine
	 * @model opposite="stateMachine" containment="true" ordered="false"
	 * @generated
	 */
	EList<Pseudostate> getConnectionPoint();

	/**
	 * Returns the value of the '<em><b>Submachine State</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getSubmachine <em>Submachine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * References the submachine(s) in case of a submachine state. Multiple machines are referenced in case of a concurrent state.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Submachine State</em>' reference list.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getStateMachine_SubmachineState()
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getSubmachine
	 * @model opposite="submachine" ordered="false"
	 * @generated
	 */
	EList<State> getSubmachineState();

	/**
	 * Returns the value of the '<em><b>Region</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The regions owned directly by the state machine.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Region</em>' containment reference list.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#getStateMachine_Region()
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getStateMachine
	 * @model opposite="stateMachine" containment="true" required="true" ordered="false"
	 * @generated
	 */
	EList<Region> getRegion();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A state machine as the method for a behavioral feature cannot have entry/exit connection points.
	 * specification->notEmpty() implies connectionPoint->isEmpty()
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean method(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The classifier context of a state machine cannot be an interface.
	 * context->notEmpty() implies not context.oclIsKindOf(Interface)
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean classifier_context(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The context classifier of the method state machine of a behavioral feature must be the classifier that owns the behavioral feature.
	 * specification->notEmpty() implies (context->notEmpty() and specification->featuringClassifier->exists (c | c = context))
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean context_classifier(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The connection points of a state machine are pseudostates of kind entry point or exit point.
	 * conectionPoint->forAll (c | c.kind = #entryPoint or c.kind = #exitPoint)
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	boolean connection_points(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The operation LCA(s1,s2) returns an orthogonal state or region which is the least common ancestor of states s1 and s2, based on the statemachine containment hierarchy.
	 * true
	 * <!-- end-model-doc -->
	 * @model required="true" ordered="false" s1Required="true" s1Ordered="false" s2Required="true" s2Ordered="false"
	 * @generated
	 */
	Namespace LCA(State s1, State s2);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The query ancestor(s1, s2) checks whether s1 is an ancestor state of state s2.
	 * result = 
	 * if (s2 = s1) then 
	 * 	true 
	 * else 
	 * 	if (s2.container->isEmpty() or not s2.container.owner.oclIsKindOf(State)) then 
	 * 		false 
	 * 	else 
	 * 		ancestor(s1, s2.container.owner.oclAsType(State))
	 * 	endif
	 * endif 
	 * 
	 * <!-- end-model-doc -->
	 * @model dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false" s1Required="true" s1Ordered="false" s2Required="true" s2Ordered="false"
	 * @generated
	 */
	boolean ancestor(State s1, State s2);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The query isRedefinitionContextValid() specifies whether the redefinition contexts of a statemachine are properly related to the redefinition contexts of the specified statemachine to allow this element to redefine the other. The containing classifier of a redefining statemachine must redefine the containing classifier of the redefined statemachine.
	 * result = true
	 * <!-- end-model-doc -->
	 * @model dataType="org.eclipse.uml2.types.Boolean" required="true" ordered="false" redefinedRequired="true" redefinedOrdered="false"
	 * @generated
	 */
	boolean isRedefinitionContextValid(StateMachine redefined);

} // StateMachine
