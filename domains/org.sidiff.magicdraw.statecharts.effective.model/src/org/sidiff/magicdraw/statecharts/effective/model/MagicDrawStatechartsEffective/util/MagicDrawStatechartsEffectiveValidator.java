/**
 */
package org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage
 * @generated
 */
public class MagicDrawStatechartsEffectiveValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final MagicDrawStatechartsEffectiveValidator INSTANCE = new MagicDrawStatechartsEffectiveValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective";

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Members distinguishable' of 'Namespace'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int NAMESPACE__MEMBERS_DISTINGUISHABLE = 1;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Has qualified name' of 'Named Element'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int NAMED_ELEMENT__HAS_QUALIFIED_NAME = 2;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Has no qualified name' of 'Named Element'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int NAMED_ELEMENT__HAS_NO_QUALIFIED_NAME = 3;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Visibility needs ownership' of 'Named Element'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int NAMED_ELEMENT__VISIBILITY_NEEDS_OWNERSHIP = 4;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Entry exit do' of 'Protocol State Machine'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PROTOCOL_STATE_MACHINE__ENTRY_EXIT_DO = 5;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Protocol transitions' of 'Protocol State Machine'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PROTOCOL_STATE_MACHINE__PROTOCOL_TRANSITIONS = 6;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Deep or shallow history' of 'Protocol State Machine'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PROTOCOL_STATE_MACHINE__DEEP_OR_SHALLOW_HISTORY = 7;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Ports connected' of 'Protocol State Machine'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PROTOCOL_STATE_MACHINE__PORTS_CONNECTED = 8;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Method' of 'State Machine'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int STATE_MACHINE__METHOD = 9;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Classifier context' of 'State Machine'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int STATE_MACHINE__CLASSIFIER_CONTEXT = 10;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Context classifier' of 'State Machine'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int STATE_MACHINE__CONTEXT_CLASSIFIER = 11;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Connection points' of 'State Machine'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int STATE_MACHINE__CONNECTION_POINTS = 12;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Junction vertex' of 'Pseudostate'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PSEUDOSTATE__JUNCTION_VERTEX = 13;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'History vertices' of 'Pseudostate'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PSEUDOSTATE__HISTORY_VERTICES = 14;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Transitions outgoing' of 'Pseudostate'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PSEUDOSTATE__TRANSITIONS_OUTGOING = 15;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Outgoing from initial' of 'Pseudostate'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PSEUDOSTATE__OUTGOING_FROM_INITIAL = 16;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Fork vertex' of 'Pseudostate'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PSEUDOSTATE__FORK_VERTEX = 17;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Join vertex' of 'Pseudostate'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PSEUDOSTATE__JOIN_VERTEX = 18;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Choice vertex' of 'Pseudostate'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PSEUDOSTATE__CHOICE_VERTEX = 19;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Initial vertex' of 'Pseudostate'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PSEUDOSTATE__INITIAL_VERTEX = 20;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Transitions incoming' of 'Pseudostate'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int PSEUDOSTATE__TRANSITIONS_INCOMING = 21;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Shallow history vertex' of 'Region'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int REGION__SHALLOW_HISTORY_VERTEX = 22;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Deep history vertex' of 'Region'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int REGION__DEEP_HISTORY_VERTEX = 23;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Initial vertex' of 'Region'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int REGION__INITIAL_VERTEX = 24;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Owned' of 'Region'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int REGION__OWNED = 25;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Entry or exit' of 'State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int STATE__ENTRY_OR_EXIT = 26;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Composite states' of 'State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int STATE__COMPOSITE_STATES = 27;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Destinations or sources of transitions' of 'State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int STATE__DESTINATIONS_OR_SOURCES_OF_TRANSITIONS = 28;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Submachine or regions' of 'State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int STATE__SUBMACHINE_OR_REGIONS = 29;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Submachine states' of 'State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int STATE__SUBMACHINE_STATES = 30;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Entry pseudostates' of 'Connection Point Reference'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int CONNECTION_POINT_REFERENCE__ENTRY_PSEUDOSTATES = 31;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Exit pseudostates' of 'Connection Point Reference'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int CONNECTION_POINT_REFERENCE__EXIT_PSEUDOSTATES = 32;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'State is local' of 'Transition'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int TRANSITION__STATE_IS_LOCAL = 33;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Fork segment guards' of 'Transition'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int TRANSITION__FORK_SEGMENT_GUARDS = 34;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Join segment state' of 'Transition'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int TRANSITION__JOIN_SEGMENT_STATE = 35;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Initial transition' of 'Transition'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int TRANSITION__INITIAL_TRANSITION = 36;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Outgoing pseudostates' of 'Transition'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int TRANSITION__OUTGOING_PSEUDOSTATES = 37;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Signatures compatible' of 'Transition'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int TRANSITION__SIGNATURES_COMPATIBLE = 38;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'State is internal' of 'Transition'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int TRANSITION__STATE_IS_INTERNAL = 39;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Join segment guards' of 'Transition'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int TRANSITION__JOIN_SEGMENT_GUARDS = 40;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Fork segment state' of 'Transition'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int TRANSITION__FORK_SEGMENT_STATE = 41;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'State is external' of 'Transition'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int TRANSITION__STATE_IS_EXTERNAL = 42;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'No outgoing transitions' of 'Final State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int FINAL_STATE__NO_OUTGOING_TRANSITIONS = 43;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'Cannot reference submachine' of 'Final State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int FINAL_STATE__CANNOT_REFERENCE_SUBMACHINE = 44;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'No exit behavior' of 'Final State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int FINAL_STATE__NO_EXIT_BEHAVIOR = 45;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'No entry behavior' of 'Final State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int FINAL_STATE__NO_ENTRY_BEHAVIOR = 46;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'No state behavior' of 'Final State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int FINAL_STATE__NO_STATE_BEHAVIOR = 47;

	/**
	 * The {@link org.eclipse.emf.common.util.Diagnostic#getCode() code} for constraint 'No regions' of 'Final State'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final int FINAL_STATE__NO_REGIONS = 48;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 48;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MagicDrawStatechartsEffectiveValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return MagicDrawStatechartsEffectivePackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case MagicDrawStatechartsEffectivePackage.NAMESPACE:
				return validateNamespace((Namespace)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.NAMED_ELEMENT:
				return validateNamedElement((NamedElement)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.PROTOCOL_STATE_MACHINE:
				return validateProtocolStateMachine((ProtocolStateMachine)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.STATE_MACHINE:
				return validateStateMachine((StateMachine)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.PSEUDOSTATE:
				return validatePseudostate((Pseudostate)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.VERTEX:
				return validateVertex((Vertex)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.REGION:
				return validateRegion((Region)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.STATE:
				return validateState((State)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.CONNECTION_POINT_REFERENCE:
				return validateConnectionPointReference((ConnectionPointReference)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.TRANSITION:
				return validateTransition((Transition)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.FINAL_STATE:
				return validateFinalState((FinalState)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.CONSTRAINT:
				return validateConstraint((Constraint)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.BEHAVIOR:
				return validateBehavior((Behavior)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.TRIGGER:
				return validateTrigger((Trigger)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.TRANSITION_KIND:
				return validateTransitionKind((TransitionKind)value, diagnostics, context);
			case MagicDrawStatechartsEffectivePackage.PSEUDOSTATE_KIND:
				return validatePseudostateKind((PseudostateKind)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamespace(Namespace namespace, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(namespace, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_qualified_name(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_no_qualified_name(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_visibility_needs_ownership(namespace, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamespace_members_distinguishable(namespace, diagnostics, context);
		return result;
	}

	/**
	 * Validates the members_distinguishable constraint of '<em>Namespace</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamespace_members_distinguishable(Namespace namespace, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return namespace.members_distinguishable(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(namedElement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_qualified_name(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_no_qualified_name(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_visibility_needs_ownership(namedElement, diagnostics, context);
		return result;
	}

	/**
	 * Validates the has_qualified_name constraint of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement_has_qualified_name(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return namedElement.has_qualified_name(diagnostics, context);
	}

	/**
	 * Validates the has_no_qualified_name constraint of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement_has_no_qualified_name(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return namedElement.has_no_qualified_name(diagnostics, context);
	}

	/**
	 * Validates the visibility_needs_ownership constraint of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement_visibility_needs_ownership(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return namedElement.visibility_needs_ownership(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProtocolStateMachine(ProtocolStateMachine protocolStateMachine, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(protocolStateMachine, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateStateMachine_method(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateStateMachine_classifier_context(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateStateMachine_context_classifier(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateStateMachine_connection_points(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateProtocolStateMachine_entry_exit_do(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateProtocolStateMachine_protocol_transitions(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateProtocolStateMachine_deep_or_shallow_history(protocolStateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateProtocolStateMachine_ports_connected(protocolStateMachine, diagnostics, context);
		return result;
	}

	/**
	 * Validates the entry_exit_do constraint of '<em>Protocol State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProtocolStateMachine_entry_exit_do(ProtocolStateMachine protocolStateMachine, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return protocolStateMachine.entry_exit_do(diagnostics, context);
	}

	/**
	 * Validates the protocol_transitions constraint of '<em>Protocol State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProtocolStateMachine_protocol_transitions(ProtocolStateMachine protocolStateMachine, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return protocolStateMachine.protocol_transitions(diagnostics, context);
	}

	/**
	 * Validates the deep_or_shallow_history constraint of '<em>Protocol State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProtocolStateMachine_deep_or_shallow_history(ProtocolStateMachine protocolStateMachine, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return protocolStateMachine.deep_or_shallow_history(diagnostics, context);
	}

	/**
	 * Validates the ports_connected constraint of '<em>Protocol State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProtocolStateMachine_ports_connected(ProtocolStateMachine protocolStateMachine, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return protocolStateMachine.ports_connected(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStateMachine(StateMachine stateMachine, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(stateMachine, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateStateMachine_method(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateStateMachine_classifier_context(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateStateMachine_context_classifier(stateMachine, diagnostics, context);
		if (result || diagnostics != null) result &= validateStateMachine_connection_points(stateMachine, diagnostics, context);
		return result;
	}

	/**
	 * Validates the method constraint of '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStateMachine_method(StateMachine stateMachine, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return stateMachine.method(diagnostics, context);
	}

	/**
	 * Validates the classifier_context constraint of '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStateMachine_classifier_context(StateMachine stateMachine, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return stateMachine.classifier_context(diagnostics, context);
	}

	/**
	 * Validates the context_classifier constraint of '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStateMachine_context_classifier(StateMachine stateMachine, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return stateMachine.context_classifier(diagnostics, context);
	}

	/**
	 * Validates the connection_points constraint of '<em>State Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStateMachine_connection_points(StateMachine stateMachine, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return stateMachine.connection_points(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostate(Pseudostate pseudostate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(pseudostate, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_qualified_name(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_no_qualified_name(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_visibility_needs_ownership(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validatePseudostate_junction_vertex(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validatePseudostate_history_vertices(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validatePseudostate_transitions_outgoing(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validatePseudostate_outgoing_from_initial(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validatePseudostate_fork_vertex(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validatePseudostate_join_vertex(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validatePseudostate_choice_vertex(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validatePseudostate_initial_vertex(pseudostate, diagnostics, context);
		if (result || diagnostics != null) result &= validatePseudostate_transitions_incoming(pseudostate, diagnostics, context);
		return result;
	}

	/**
	 * Validates the junction_vertex constraint of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostate_junction_vertex(Pseudostate pseudostate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return pseudostate.junction_vertex(diagnostics, context);
	}

	/**
	 * Validates the history_vertices constraint of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostate_history_vertices(Pseudostate pseudostate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return pseudostate.history_vertices(diagnostics, context);
	}

	/**
	 * Validates the transitions_outgoing constraint of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostate_transitions_outgoing(Pseudostate pseudostate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return pseudostate.transitions_outgoing(diagnostics, context);
	}

	/**
	 * Validates the outgoing_from_initial constraint of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostate_outgoing_from_initial(Pseudostate pseudostate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return pseudostate.outgoing_from_initial(diagnostics, context);
	}

	/**
	 * Validates the fork_vertex constraint of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostate_fork_vertex(Pseudostate pseudostate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return pseudostate.fork_vertex(diagnostics, context);
	}

	/**
	 * Validates the join_vertex constraint of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostate_join_vertex(Pseudostate pseudostate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return pseudostate.join_vertex(diagnostics, context);
	}

	/**
	 * Validates the choice_vertex constraint of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostate_choice_vertex(Pseudostate pseudostate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return pseudostate.choice_vertex(diagnostics, context);
	}

	/**
	 * Validates the initial_vertex constraint of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostate_initial_vertex(Pseudostate pseudostate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return pseudostate.initial_vertex(diagnostics, context);
	}

	/**
	 * Validates the transitions_incoming constraint of '<em>Pseudostate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostate_transitions_incoming(Pseudostate pseudostate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return pseudostate.transitions_incoming(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVertex(Vertex vertex, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(vertex, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(vertex, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(vertex, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(vertex, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(vertex, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(vertex, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(vertex, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(vertex, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(vertex, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_qualified_name(vertex, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_no_qualified_name(vertex, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_visibility_needs_ownership(vertex, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRegion(Region region, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(region, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(region, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(region, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(region, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(region, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(region, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(region, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(region, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(region, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_qualified_name(region, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_no_qualified_name(region, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_visibility_needs_ownership(region, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamespace_members_distinguishable(region, diagnostics, context);
		if (result || diagnostics != null) result &= validateRegion_shallow_history_vertex(region, diagnostics, context);
		if (result || diagnostics != null) result &= validateRegion_deep_history_vertex(region, diagnostics, context);
		if (result || diagnostics != null) result &= validateRegion_initial_vertex(region, diagnostics, context);
		if (result || diagnostics != null) result &= validateRegion_owned(region, diagnostics, context);
		return result;
	}

	/**
	 * Validates the shallow_history_vertex constraint of '<em>Region</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRegion_shallow_history_vertex(Region region, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return region.shallow_history_vertex(diagnostics, context);
	}

	/**
	 * Validates the deep_history_vertex constraint of '<em>Region</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRegion_deep_history_vertex(Region region, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return region.deep_history_vertex(diagnostics, context);
	}

	/**
	 * Validates the initial_vertex constraint of '<em>Region</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRegion_initial_vertex(Region region, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return region.initial_vertex(diagnostics, context);
	}

	/**
	 * Validates the owned constraint of '<em>Region</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRegion_owned(Region region, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return region.owned(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateState(State state, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(state, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(state, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(state, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(state, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(state, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(state, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(state, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(state, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(state, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_qualified_name(state, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_no_qualified_name(state, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_visibility_needs_ownership(state, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamespace_members_distinguishable(state, diagnostics, context);
		if (result || diagnostics != null) result &= validateState_entry_or_exit(state, diagnostics, context);
		if (result || diagnostics != null) result &= validateState_composite_states(state, diagnostics, context);
		if (result || diagnostics != null) result &= validateState_destinations_or_sources_of_transitions(state, diagnostics, context);
		if (result || diagnostics != null) result &= validateState_submachine_or_regions(state, diagnostics, context);
		if (result || diagnostics != null) result &= validateState_submachine_states(state, diagnostics, context);
		return result;
	}

	/**
	 * Validates the entry_or_exit constraint of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateState_entry_or_exit(State state, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return state.entry_or_exit(diagnostics, context);
	}

	/**
	 * Validates the composite_states constraint of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateState_composite_states(State state, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return state.composite_states(diagnostics, context);
	}

	/**
	 * Validates the destinations_or_sources_of_transitions constraint of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateState_destinations_or_sources_of_transitions(State state, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return state.destinations_or_sources_of_transitions(diagnostics, context);
	}

	/**
	 * Validates the submachine_or_regions constraint of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateState_submachine_or_regions(State state, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return state.submachine_or_regions(diagnostics, context);
	}

	/**
	 * Validates the submachine_states constraint of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateState_submachine_states(State state, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return state.submachine_states(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConnectionPointReference(ConnectionPointReference connectionPointReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(connectionPointReference, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_qualified_name(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_no_qualified_name(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_visibility_needs_ownership(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateConnectionPointReference_entry_pseudostates(connectionPointReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateConnectionPointReference_exit_pseudostates(connectionPointReference, diagnostics, context);
		return result;
	}

	/**
	 * Validates the entry_pseudostates constraint of '<em>Connection Point Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConnectionPointReference_entry_pseudostates(ConnectionPointReference connectionPointReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return connectionPointReference.entry_pseudostates(diagnostics, context);
	}

	/**
	 * Validates the exit_pseudostates constraint of '<em>Connection Point Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConnectionPointReference_exit_pseudostates(ConnectionPointReference connectionPointReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return connectionPointReference.exit_pseudostates(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(transition, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_qualified_name(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_no_qualified_name(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_visibility_needs_ownership(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamespace_members_distinguishable(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransition_state_is_local(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransition_fork_segment_guards(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransition_join_segment_state(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransition_initial_transition(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransition_outgoing_pseudostates(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransition_signatures_compatible(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransition_state_is_internal(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransition_join_segment_guards(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransition_fork_segment_state(transition, diagnostics, context);
		if (result || diagnostics != null) result &= validateTransition_state_is_external(transition, diagnostics, context);
		return result;
	}

	/**
	 * Validates the state_is_local constraint of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition_state_is_local(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return transition.state_is_local(diagnostics, context);
	}

	/**
	 * Validates the fork_segment_guards constraint of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition_fork_segment_guards(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return transition.fork_segment_guards(diagnostics, context);
	}

	/**
	 * Validates the join_segment_state constraint of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition_join_segment_state(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return transition.join_segment_state(diagnostics, context);
	}

	/**
	 * Validates the initial_transition constraint of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition_initial_transition(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return transition.initial_transition(diagnostics, context);
	}

	/**
	 * Validates the outgoing_pseudostates constraint of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition_outgoing_pseudostates(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return transition.outgoing_pseudostates(diagnostics, context);
	}

	/**
	 * Validates the signatures_compatible constraint of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition_signatures_compatible(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return transition.signatures_compatible(diagnostics, context);
	}

	/**
	 * Validates the state_is_internal constraint of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition_state_is_internal(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return transition.state_is_internal(diagnostics, context);
	}

	/**
	 * Validates the join_segment_guards constraint of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition_join_segment_guards(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return transition.join_segment_guards(diagnostics, context);
	}

	/**
	 * Validates the fork_segment_state constraint of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition_fork_segment_state(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return transition.fork_segment_state(diagnostics, context);
	}

	/**
	 * Validates the state_is_external constraint of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransition_state_is_external(Transition transition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return transition.state_is_external(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFinalState(FinalState finalState, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(finalState, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_qualified_name(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_has_no_qualified_name(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_visibility_needs_ownership(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamespace_members_distinguishable(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateState_entry_or_exit(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateState_composite_states(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateState_destinations_or_sources_of_transitions(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateState_submachine_or_regions(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateState_submachine_states(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateFinalState_no_outgoing_transitions(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateFinalState_cannot_reference_submachine(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateFinalState_no_exit_behavior(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateFinalState_no_entry_behavior(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateFinalState_no_state_behavior(finalState, diagnostics, context);
		if (result || diagnostics != null) result &= validateFinalState_no_regions(finalState, diagnostics, context);
		return result;
	}

	/**
	 * Validates the no_outgoing_transitions constraint of '<em>Final State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFinalState_no_outgoing_transitions(FinalState finalState, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return finalState.no_outgoing_transitions(diagnostics, context);
	}

	/**
	 * Validates the cannot_reference_submachine constraint of '<em>Final State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFinalState_cannot_reference_submachine(FinalState finalState, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return finalState.cannot_reference_submachine(diagnostics, context);
	}

	/**
	 * Validates the no_exit_behavior constraint of '<em>Final State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFinalState_no_exit_behavior(FinalState finalState, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return finalState.no_exit_behavior(diagnostics, context);
	}

	/**
	 * Validates the no_entry_behavior constraint of '<em>Final State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFinalState_no_entry_behavior(FinalState finalState, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return finalState.no_entry_behavior(diagnostics, context);
	}

	/**
	 * Validates the no_state_behavior constraint of '<em>Final State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFinalState_no_state_behavior(FinalState finalState, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return finalState.no_state_behavior(diagnostics, context);
	}

	/**
	 * Validates the no_regions constraint of '<em>Final State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFinalState_no_regions(FinalState finalState, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return finalState.no_regions(diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConstraint(Constraint constraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(constraint, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBehavior(Behavior behavior, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(behavior, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTrigger(Trigger trigger, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(trigger, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransitionKind(TransitionKind transitionKind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePseudostateKind(PseudostateKind pseudostateKind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //MagicDrawStatechartsEffectiveValidator
