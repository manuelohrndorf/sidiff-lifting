/**
 */
package org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.uml2.types.TypesPackage;

import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Behavior;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Constraint;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectiveFactory;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.PseudostateKind;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.TransitionKind;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Trigger;
import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex;

import org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.util.MagicDrawStatechartsEffectiveValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MagicDrawStatechartsEffectivePackageImpl extends EPackageImpl implements MagicDrawStatechartsEffectivePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namespaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass protocolStateMachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateMachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pseudostateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vertexEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass regionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectionPointReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass finalStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass behaviorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass triggerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum transitionKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum pseudostateKindEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectivePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MagicDrawStatechartsEffectivePackageImpl() {
		super(eNS_URI, MagicDrawStatechartsEffectiveFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link MagicDrawStatechartsEffectivePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MagicDrawStatechartsEffectivePackage init() {
		if (isInited) return (MagicDrawStatechartsEffectivePackage)EPackage.Registry.INSTANCE.getEPackage(MagicDrawStatechartsEffectivePackage.eNS_URI);

		// Obtain or create and register package
		MagicDrawStatechartsEffectivePackageImpl theMagicDrawStatechartsEffectivePackage = (MagicDrawStatechartsEffectivePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MagicDrawStatechartsEffectivePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MagicDrawStatechartsEffectivePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		TypesPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMagicDrawStatechartsEffectivePackage.createPackageContents();

		// Initialize created meta-data
		theMagicDrawStatechartsEffectivePackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theMagicDrawStatechartsEffectivePackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return MagicDrawStatechartsEffectiveValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theMagicDrawStatechartsEffectivePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MagicDrawStatechartsEffectivePackage.eNS_URI, theMagicDrawStatechartsEffectivePackage);
		return theMagicDrawStatechartsEffectivePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamespace() {
		return namespaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamespace__Members_distinguishable__DiagnosticChain_Map() {
		return namespaceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamespace__GetNamesOfMember__NamedElement() {
		return namespaceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamespace__MembersAreDistinguishable() {
		return namespaceEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamespace__GetOwnedMembers() {
		return namespaceEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamedElement() {
		return namedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedElement_Name() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamedElement_QualifiedName() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__Has_qualified_name__DiagnosticChain_Map() {
		return namedElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__Has_no_qualified_name__DiagnosticChain_Map() {
		return namedElementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__Visibility_needs_ownership__DiagnosticChain_Map() {
		return namedElementEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__CreateDependency__NamedElement() {
		return namedElementEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__CreateUsage__NamedElement() {
		return namedElementEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__GetLabel() {
		return namedElementEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__GetLabel__boolean() {
		return namedElementEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__AllNamespaces() {
		return namedElementEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__IsDistinguishableFrom__NamedElement_Namespace() {
		return namedElementEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__GetNamespace() {
		return namedElementEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__GetQualifiedName() {
		return namedElementEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getNamedElement__Separator() {
		return namedElementEClass.getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProtocolStateMachine() {
		return protocolStateMachineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProtocolStateMachine__Entry_exit_do__DiagnosticChain_Map() {
		return protocolStateMachineEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProtocolStateMachine__Protocol_transitions__DiagnosticChain_Map() {
		return protocolStateMachineEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProtocolStateMachine__Deep_or_shallow_history__DiagnosticChain_Map() {
		return protocolStateMachineEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProtocolStateMachine__Ports_connected__DiagnosticChain_Map() {
		return protocolStateMachineEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateMachine() {
		return stateMachineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachine_ConnectionPoint() {
		return (EReference)stateMachineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachine_SubmachineState() {
		return (EReference)stateMachineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachine_Region() {
		return (EReference)stateMachineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getStateMachine__Method__DiagnosticChain_Map() {
		return stateMachineEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getStateMachine__Classifier_context__DiagnosticChain_Map() {
		return stateMachineEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getStateMachine__Context_classifier__DiagnosticChain_Map() {
		return stateMachineEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getStateMachine__Connection_points__DiagnosticChain_Map() {
		return stateMachineEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getStateMachine__LCA__State_State() {
		return stateMachineEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getStateMachine__Ancestor__State_State() {
		return stateMachineEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getStateMachine__IsRedefinitionContextValid__StateMachine() {
		return stateMachineEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPseudostate() {
		return pseudostateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPseudostate_State() {
		return (EReference)pseudostateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPseudostate_Kind() {
		return (EAttribute)pseudostateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPseudostate_StateMachine() {
		return (EReference)pseudostateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPseudostate__Junction_vertex__DiagnosticChain_Map() {
		return pseudostateEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPseudostate__History_vertices__DiagnosticChain_Map() {
		return pseudostateEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPseudostate__Transitions_outgoing__DiagnosticChain_Map() {
		return pseudostateEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPseudostate__Outgoing_from_initial__DiagnosticChain_Map() {
		return pseudostateEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPseudostate__Fork_vertex__DiagnosticChain_Map() {
		return pseudostateEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPseudostate__Join_vertex__DiagnosticChain_Map() {
		return pseudostateEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPseudostate__Choice_vertex__DiagnosticChain_Map() {
		return pseudostateEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPseudostate__Initial_vertex__DiagnosticChain_Map() {
		return pseudostateEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPseudostate__Transitions_incoming__DiagnosticChain_Map() {
		return pseudostateEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVertex() {
		return vertexEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVertex_Container() {
		return (EReference)vertexEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVertex_Incoming() {
		return (EReference)vertexEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVertex_Outgoing() {
		return (EReference)vertexEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVertex__ContainingStateMachine() {
		return vertexEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVertex__GetIncomings() {
		return vertexEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getVertex__GetOutgoings() {
		return vertexEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRegion() {
		return regionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegion_State() {
		return (EReference)regionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegion_StateMachine() {
		return (EReference)regionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegion_Transition() {
		return (EReference)regionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegion_Subvertex() {
		return (EReference)regionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRegion__Shallow_history_vertex__DiagnosticChain_Map() {
		return regionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRegion__Deep_history_vertex__DiagnosticChain_Map() {
		return regionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRegion__Initial_vertex__DiagnosticChain_Map() {
		return regionEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRegion__Owned__DiagnosticChain_Map() {
		return regionEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRegion__BelongsToPSM() {
		return regionEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRegion__ContainingStateMachine() {
		return regionEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRegion__IsRedefinitionContextValid__Region() {
		return regionEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRegion__RedefinitionContext() {
		return regionEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getState() {
		return stateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Connection() {
		return (EReference)stateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_ConnectionPoint() {
		return (EReference)stateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_IsComposite() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_IsOrthogonal() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_IsSimple() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_IsSubmachineState() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Submachine() {
		return (EReference)stateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Region() {
		return (EReference)stateEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Enty() {
		return (EReference)stateEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Exit() {
		return (EReference)stateEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_DoActivity() {
		return (EReference)stateEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_StateInvariant() {
		return (EReference)stateEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__Entry_or_exit__DiagnosticChain_Map() {
		return stateEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__Composite_states__DiagnosticChain_Map() {
		return stateEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__Destinations_or_sources_of_transitions__DiagnosticChain_Map() {
		return stateEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__Submachine_or_regions__DiagnosticChain_Map() {
		return stateEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__Submachine_states__DiagnosticChain_Map() {
		return stateEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__IsComposite() {
		return stateEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__IsOrthogonal() {
		return stateEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__IsRedefinitionContextValid__State() {
		return stateEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__IsSimple() {
		return stateEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__IsSubmachineState() {
		return stateEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getState__RedefinitionContext() {
		return stateEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectionPointReference() {
		return connectionPointReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionPointReference_Entry() {
		return (EReference)connectionPointReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionPointReference_Exit() {
		return (EReference)connectionPointReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionPointReference_State() {
		return (EReference)connectionPointReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getConnectionPointReference__Entry_pseudostates__DiagnosticChain_Map() {
		return connectionPointReferenceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getConnectionPointReference__Exit_pseudostates__DiagnosticChain_Map() {
		return connectionPointReferenceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTransition() {
		return transitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_Kind() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Source() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Target() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Container() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Trigger() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Guard() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__State_is_local__DiagnosticChain_Map() {
		return transitionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__Fork_segment_guards__DiagnosticChain_Map() {
		return transitionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__Join_segment_state__DiagnosticChain_Map() {
		return transitionEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__Initial_transition__DiagnosticChain_Map() {
		return transitionEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__Outgoing_pseudostates__DiagnosticChain_Map() {
		return transitionEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__Signatures_compatible__DiagnosticChain_Map() {
		return transitionEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__State_is_internal__DiagnosticChain_Map() {
		return transitionEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__Join_segment_guards__DiagnosticChain_Map() {
		return transitionEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__Fork_segment_state__DiagnosticChain_Map() {
		return transitionEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__State_is_external__DiagnosticChain_Map() {
		return transitionEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__ContainingStateMachine() {
		return transitionEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTransition__RedefinitionContext() {
		return transitionEClass.getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFinalState() {
		return finalStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFinalState__No_outgoing_transitions__DiagnosticChain_Map() {
		return finalStateEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFinalState__Cannot_reference_submachine__DiagnosticChain_Map() {
		return finalStateEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFinalState__No_exit_behavior__DiagnosticChain_Map() {
		return finalStateEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFinalState__No_entry_behavior__DiagnosticChain_Map() {
		return finalStateEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFinalState__No_state_behavior__DiagnosticChain_Map() {
		return finalStateEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFinalState__No_regions__DiagnosticChain_Map() {
		return finalStateEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstraint() {
		return constraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBehavior() {
		return behaviorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTrigger() {
		return triggerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTransitionKind() {
		return transitionKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPseudostateKind() {
		return pseudostateKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MagicDrawStatechartsEffectiveFactory getMagicDrawStatechartsEffectiveFactory() {
		return (MagicDrawStatechartsEffectiveFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		namespaceEClass = createEClass(NAMESPACE);
		createEOperation(namespaceEClass, NAMESPACE___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP);
		createEOperation(namespaceEClass, NAMESPACE___GET_NAMES_OF_MEMBER__NAMEDELEMENT);
		createEOperation(namespaceEClass, NAMESPACE___MEMBERS_ARE_DISTINGUISHABLE);
		createEOperation(namespaceEClass, NAMESPACE___GET_OWNED_MEMBERS);

		namedElementEClass = createEClass(NAMED_ELEMENT);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__QUALIFIED_NAME);
		createEOperation(namedElementEClass, NAMED_ELEMENT___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP);
		createEOperation(namedElementEClass, NAMED_ELEMENT___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP);
		createEOperation(namedElementEClass, NAMED_ELEMENT___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP);
		createEOperation(namedElementEClass, NAMED_ELEMENT___CREATE_DEPENDENCY__NAMEDELEMENT);
		createEOperation(namedElementEClass, NAMED_ELEMENT___CREATE_USAGE__NAMEDELEMENT);
		createEOperation(namedElementEClass, NAMED_ELEMENT___GET_LABEL);
		createEOperation(namedElementEClass, NAMED_ELEMENT___GET_LABEL__BOOLEAN);
		createEOperation(namedElementEClass, NAMED_ELEMENT___ALL_NAMESPACES);
		createEOperation(namedElementEClass, NAMED_ELEMENT___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE);
		createEOperation(namedElementEClass, NAMED_ELEMENT___GET_NAMESPACE);
		createEOperation(namedElementEClass, NAMED_ELEMENT___GET_QUALIFIED_NAME);
		createEOperation(namedElementEClass, NAMED_ELEMENT___SEPARATOR);

		protocolStateMachineEClass = createEClass(PROTOCOL_STATE_MACHINE);
		createEOperation(protocolStateMachineEClass, PROTOCOL_STATE_MACHINE___ENTRY_EXIT_DO__DIAGNOSTICCHAIN_MAP);
		createEOperation(protocolStateMachineEClass, PROTOCOL_STATE_MACHINE___PROTOCOL_TRANSITIONS__DIAGNOSTICCHAIN_MAP);
		createEOperation(protocolStateMachineEClass, PROTOCOL_STATE_MACHINE___DEEP_OR_SHALLOW_HISTORY__DIAGNOSTICCHAIN_MAP);
		createEOperation(protocolStateMachineEClass, PROTOCOL_STATE_MACHINE___PORTS_CONNECTED__DIAGNOSTICCHAIN_MAP);

		stateMachineEClass = createEClass(STATE_MACHINE);
		createEReference(stateMachineEClass, STATE_MACHINE__CONNECTION_POINT);
		createEReference(stateMachineEClass, STATE_MACHINE__SUBMACHINE_STATE);
		createEReference(stateMachineEClass, STATE_MACHINE__REGION);
		createEOperation(stateMachineEClass, STATE_MACHINE___METHOD__DIAGNOSTICCHAIN_MAP);
		createEOperation(stateMachineEClass, STATE_MACHINE___CLASSIFIER_CONTEXT__DIAGNOSTICCHAIN_MAP);
		createEOperation(stateMachineEClass, STATE_MACHINE___CONTEXT_CLASSIFIER__DIAGNOSTICCHAIN_MAP);
		createEOperation(stateMachineEClass, STATE_MACHINE___CONNECTION_POINTS__DIAGNOSTICCHAIN_MAP);
		createEOperation(stateMachineEClass, STATE_MACHINE___LCA__STATE_STATE);
		createEOperation(stateMachineEClass, STATE_MACHINE___ANCESTOR__STATE_STATE);
		createEOperation(stateMachineEClass, STATE_MACHINE___IS_REDEFINITION_CONTEXT_VALID__STATEMACHINE);

		pseudostateEClass = createEClass(PSEUDOSTATE);
		createEReference(pseudostateEClass, PSEUDOSTATE__STATE);
		createEAttribute(pseudostateEClass, PSEUDOSTATE__KIND);
		createEReference(pseudostateEClass, PSEUDOSTATE__STATE_MACHINE);
		createEOperation(pseudostateEClass, PSEUDOSTATE___JUNCTION_VERTEX__DIAGNOSTICCHAIN_MAP);
		createEOperation(pseudostateEClass, PSEUDOSTATE___HISTORY_VERTICES__DIAGNOSTICCHAIN_MAP);
		createEOperation(pseudostateEClass, PSEUDOSTATE___TRANSITIONS_OUTGOING__DIAGNOSTICCHAIN_MAP);
		createEOperation(pseudostateEClass, PSEUDOSTATE___OUTGOING_FROM_INITIAL__DIAGNOSTICCHAIN_MAP);
		createEOperation(pseudostateEClass, PSEUDOSTATE___FORK_VERTEX__DIAGNOSTICCHAIN_MAP);
		createEOperation(pseudostateEClass, PSEUDOSTATE___JOIN_VERTEX__DIAGNOSTICCHAIN_MAP);
		createEOperation(pseudostateEClass, PSEUDOSTATE___CHOICE_VERTEX__DIAGNOSTICCHAIN_MAP);
		createEOperation(pseudostateEClass, PSEUDOSTATE___INITIAL_VERTEX__DIAGNOSTICCHAIN_MAP);
		createEOperation(pseudostateEClass, PSEUDOSTATE___TRANSITIONS_INCOMING__DIAGNOSTICCHAIN_MAP);

		vertexEClass = createEClass(VERTEX);
		createEReference(vertexEClass, VERTEX__CONTAINER);
		createEReference(vertexEClass, VERTEX__INCOMING);
		createEReference(vertexEClass, VERTEX__OUTGOING);
		createEOperation(vertexEClass, VERTEX___CONTAINING_STATE_MACHINE);
		createEOperation(vertexEClass, VERTEX___GET_INCOMINGS);
		createEOperation(vertexEClass, VERTEX___GET_OUTGOINGS);

		regionEClass = createEClass(REGION);
		createEReference(regionEClass, REGION__STATE);
		createEReference(regionEClass, REGION__STATE_MACHINE);
		createEReference(regionEClass, REGION__TRANSITION);
		createEReference(regionEClass, REGION__SUBVERTEX);
		createEOperation(regionEClass, REGION___SHALLOW_HISTORY_VERTEX__DIAGNOSTICCHAIN_MAP);
		createEOperation(regionEClass, REGION___DEEP_HISTORY_VERTEX__DIAGNOSTICCHAIN_MAP);
		createEOperation(regionEClass, REGION___INITIAL_VERTEX__DIAGNOSTICCHAIN_MAP);
		createEOperation(regionEClass, REGION___OWNED__DIAGNOSTICCHAIN_MAP);
		createEOperation(regionEClass, REGION___BELONGS_TO_PSM);
		createEOperation(regionEClass, REGION___CONTAINING_STATE_MACHINE);
		createEOperation(regionEClass, REGION___IS_REDEFINITION_CONTEXT_VALID__REGION);
		createEOperation(regionEClass, REGION___REDEFINITION_CONTEXT);

		stateEClass = createEClass(STATE);
		createEReference(stateEClass, STATE__CONNECTION);
		createEReference(stateEClass, STATE__CONNECTION_POINT);
		createEAttribute(stateEClass, STATE__IS_COMPOSITE);
		createEAttribute(stateEClass, STATE__IS_ORTHOGONAL);
		createEAttribute(stateEClass, STATE__IS_SIMPLE);
		createEAttribute(stateEClass, STATE__IS_SUBMACHINE_STATE);
		createEReference(stateEClass, STATE__SUBMACHINE);
		createEReference(stateEClass, STATE__REGION);
		createEReference(stateEClass, STATE__ENTY);
		createEReference(stateEClass, STATE__EXIT);
		createEReference(stateEClass, STATE__DO_ACTIVITY);
		createEReference(stateEClass, STATE__STATE_INVARIANT);
		createEOperation(stateEClass, STATE___ENTRY_OR_EXIT__DIAGNOSTICCHAIN_MAP);
		createEOperation(stateEClass, STATE___COMPOSITE_STATES__DIAGNOSTICCHAIN_MAP);
		createEOperation(stateEClass, STATE___DESTINATIONS_OR_SOURCES_OF_TRANSITIONS__DIAGNOSTICCHAIN_MAP);
		createEOperation(stateEClass, STATE___SUBMACHINE_OR_REGIONS__DIAGNOSTICCHAIN_MAP);
		createEOperation(stateEClass, STATE___SUBMACHINE_STATES__DIAGNOSTICCHAIN_MAP);
		createEOperation(stateEClass, STATE___IS_COMPOSITE);
		createEOperation(stateEClass, STATE___IS_ORTHOGONAL);
		createEOperation(stateEClass, STATE___IS_REDEFINITION_CONTEXT_VALID__STATE);
		createEOperation(stateEClass, STATE___IS_SIMPLE);
		createEOperation(stateEClass, STATE___IS_SUBMACHINE_STATE);
		createEOperation(stateEClass, STATE___REDEFINITION_CONTEXT);

		connectionPointReferenceEClass = createEClass(CONNECTION_POINT_REFERENCE);
		createEReference(connectionPointReferenceEClass, CONNECTION_POINT_REFERENCE__ENTRY);
		createEReference(connectionPointReferenceEClass, CONNECTION_POINT_REFERENCE__EXIT);
		createEReference(connectionPointReferenceEClass, CONNECTION_POINT_REFERENCE__STATE);
		createEOperation(connectionPointReferenceEClass, CONNECTION_POINT_REFERENCE___ENTRY_PSEUDOSTATES__DIAGNOSTICCHAIN_MAP);
		createEOperation(connectionPointReferenceEClass, CONNECTION_POINT_REFERENCE___EXIT_PSEUDOSTATES__DIAGNOSTICCHAIN_MAP);

		transitionEClass = createEClass(TRANSITION);
		createEAttribute(transitionEClass, TRANSITION__KIND);
		createEReference(transitionEClass, TRANSITION__SOURCE);
		createEReference(transitionEClass, TRANSITION__TARGET);
		createEReference(transitionEClass, TRANSITION__CONTAINER);
		createEReference(transitionEClass, TRANSITION__TRIGGER);
		createEReference(transitionEClass, TRANSITION__GUARD);
		createEOperation(transitionEClass, TRANSITION___STATE_IS_LOCAL__DIAGNOSTICCHAIN_MAP);
		createEOperation(transitionEClass, TRANSITION___FORK_SEGMENT_GUARDS__DIAGNOSTICCHAIN_MAP);
		createEOperation(transitionEClass, TRANSITION___JOIN_SEGMENT_STATE__DIAGNOSTICCHAIN_MAP);
		createEOperation(transitionEClass, TRANSITION___INITIAL_TRANSITION__DIAGNOSTICCHAIN_MAP);
		createEOperation(transitionEClass, TRANSITION___OUTGOING_PSEUDOSTATES__DIAGNOSTICCHAIN_MAP);
		createEOperation(transitionEClass, TRANSITION___SIGNATURES_COMPATIBLE__DIAGNOSTICCHAIN_MAP);
		createEOperation(transitionEClass, TRANSITION___STATE_IS_INTERNAL__DIAGNOSTICCHAIN_MAP);
		createEOperation(transitionEClass, TRANSITION___JOIN_SEGMENT_GUARDS__DIAGNOSTICCHAIN_MAP);
		createEOperation(transitionEClass, TRANSITION___FORK_SEGMENT_STATE__DIAGNOSTICCHAIN_MAP);
		createEOperation(transitionEClass, TRANSITION___STATE_IS_EXTERNAL__DIAGNOSTICCHAIN_MAP);
		createEOperation(transitionEClass, TRANSITION___CONTAINING_STATE_MACHINE);
		createEOperation(transitionEClass, TRANSITION___REDEFINITION_CONTEXT);

		finalStateEClass = createEClass(FINAL_STATE);
		createEOperation(finalStateEClass, FINAL_STATE___NO_OUTGOING_TRANSITIONS__DIAGNOSTICCHAIN_MAP);
		createEOperation(finalStateEClass, FINAL_STATE___CANNOT_REFERENCE_SUBMACHINE__DIAGNOSTICCHAIN_MAP);
		createEOperation(finalStateEClass, FINAL_STATE___NO_EXIT_BEHAVIOR__DIAGNOSTICCHAIN_MAP);
		createEOperation(finalStateEClass, FINAL_STATE___NO_ENTRY_BEHAVIOR__DIAGNOSTICCHAIN_MAP);
		createEOperation(finalStateEClass, FINAL_STATE___NO_STATE_BEHAVIOR__DIAGNOSTICCHAIN_MAP);
		createEOperation(finalStateEClass, FINAL_STATE___NO_REGIONS__DIAGNOSTICCHAIN_MAP);

		constraintEClass = createEClass(CONSTRAINT);

		behaviorEClass = createEClass(BEHAVIOR);

		triggerEClass = createEClass(TRIGGER);

		// Create enums
		transitionKindEEnum = createEEnum(TRANSITION_KIND);
		pseudostateKindEEnum = createEEnum(PSEUDOSTATE_KIND);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		namespaceEClass.getESuperTypes().add(this.getNamedElement());
		protocolStateMachineEClass.getESuperTypes().add(this.getStateMachine());
		pseudostateEClass.getESuperTypes().add(this.getVertex());
		vertexEClass.getESuperTypes().add(this.getNamedElement());
		regionEClass.getESuperTypes().add(this.getNamespace());
		stateEClass.getESuperTypes().add(this.getNamespace());
		stateEClass.getESuperTypes().add(this.getVertex());
		connectionPointReferenceEClass.getESuperTypes().add(this.getVertex());
		transitionEClass.getESuperTypes().add(this.getNamespace());
		finalStateEClass.getESuperTypes().add(this.getState());

		// Initialize classes, features, and operations; add parameters
		initEClass(namespaceEClass, Namespace.class, "Namespace", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = initEOperation(getNamespace__Members_distinguishable__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "members_distinguishable", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEMap());
		EGenericType g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getNamespace__GetNamesOfMember__NamedElement(), theTypesPackage.getString(), "getNamesOfMember", 0, -1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getNamedElement(), "element", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getNamespace__MembersAreDistinguishable(), theTypesPackage.getBoolean(), "membersAreDistinguishable", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getNamespace__GetOwnedMembers(), this.getNamedElement(), "getOwnedMembers", 0, -1, IS_UNIQUE, !IS_ORDERED);

		initEClass(namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getNamedElement_QualifiedName(), theTypesPackage.getString(), "qualifiedName", null, 0, 1, NamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);

		op = initEOperation(getNamedElement__Has_qualified_name__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "has_qualified_name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getNamedElement__Has_no_qualified_name__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "has_no_qualified_name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getNamedElement__Visibility_needs_ownership__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "visibility_needs_ownership", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getNamedElement__CreateDependency__NamedElement(), null, "createDependency", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getNamedElement(), "supplier", 1, 1, IS_UNIQUE, !IS_ORDERED);

		op = initEOperation(getNamedElement__CreateUsage__NamedElement(), null, "createUsage", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getNamedElement(), "supplier", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getNamedElement__GetLabel(), theTypesPackage.getString(), "getLabel", 0, 1, IS_UNIQUE, !IS_ORDERED);

		op = initEOperation(getNamedElement__GetLabel__boolean(), theTypesPackage.getString(), "getLabel", 0, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, theTypesPackage.getBoolean(), "localize", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getNamedElement__AllNamespaces(), this.getNamespace(), "allNamespaces", 0, -1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getNamedElement__IsDistinguishableFrom__NamedElement_Namespace(), theTypesPackage.getBoolean(), "isDistinguishableFrom", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getNamedElement(), "n", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getNamespace(), "ns", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getNamedElement__GetNamespace(), this.getNamespace(), "getNamespace", 0, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getNamedElement__GetQualifiedName(), theTypesPackage.getString(), "getQualifiedName", 0, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getNamedElement__Separator(), theTypesPackage.getString(), "separator", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEClass(protocolStateMachineEClass, ProtocolStateMachine.class, "ProtocolStateMachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getProtocolStateMachine__Entry_exit_do__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "entry_exit_do", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getProtocolStateMachine__Protocol_transitions__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "protocol_transitions", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getProtocolStateMachine__Deep_or_shallow_history__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "deep_or_shallow_history", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getProtocolStateMachine__Ports_connected__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "ports_connected", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(stateMachineEClass, StateMachine.class, "StateMachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStateMachine_ConnectionPoint(), this.getPseudostate(), this.getPseudostate_StateMachine(), "connectionPoint", null, 0, -1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getStateMachine_SubmachineState(), this.getState(), this.getState_Submachine(), "submachineState", null, 0, -1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getStateMachine_Region(), this.getRegion(), this.getRegion_StateMachine(), "region", null, 1, -1, StateMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		op = initEOperation(getStateMachine__Method__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "method", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getStateMachine__Classifier_context__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "classifier_context", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getStateMachine__Context_classifier__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "context_classifier", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getStateMachine__Connection_points__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "connection_points", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getStateMachine__LCA__State_State(), this.getNamespace(), "LCA", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getState(), "s1", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getState(), "s2", 1, 1, IS_UNIQUE, !IS_ORDERED);

		op = initEOperation(getStateMachine__Ancestor__State_State(), theTypesPackage.getBoolean(), "ancestor", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getState(), "s1", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getState(), "s2", 1, 1, IS_UNIQUE, !IS_ORDERED);

		op = initEOperation(getStateMachine__IsRedefinitionContextValid__StateMachine(), theTypesPackage.getBoolean(), "isRedefinitionContextValid", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getStateMachine(), "redefined", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEClass(pseudostateEClass, Pseudostate.class, "Pseudostate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPseudostate_State(), this.getState(), this.getState_ConnectionPoint(), "state", null, 0, 1, Pseudostate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getPseudostate_Kind(), this.getPseudostateKind(), "kind", "initial", 1, 1, Pseudostate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getPseudostate_StateMachine(), this.getStateMachine(), this.getStateMachine_ConnectionPoint(), "stateMachine", null, 0, 1, Pseudostate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		op = initEOperation(getPseudostate__Junction_vertex__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "junction_vertex", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getPseudostate__History_vertices__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "history_vertices", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getPseudostate__Transitions_outgoing__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "transitions_outgoing", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getPseudostate__Outgoing_from_initial__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "outgoing_from_initial", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getPseudostate__Fork_vertex__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "fork_vertex", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getPseudostate__Join_vertex__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "join_vertex", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getPseudostate__Choice_vertex__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "choice_vertex", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getPseudostate__Initial_vertex__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "initial_vertex", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getPseudostate__Transitions_incoming__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "transitions_incoming", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(vertexEClass, Vertex.class, "Vertex", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVertex_Container(), this.getRegion(), this.getRegion_Subvertex(), "container", null, 0, 1, Vertex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getVertex_Incoming(), this.getTransition(), this.getTransition_Target(), "incoming", null, 0, -1, Vertex.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getVertex_Outgoing(), this.getTransition(), this.getTransition_Source(), "outgoing", null, 0, -1, Vertex.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEOperation(getVertex__ContainingStateMachine(), this.getStateMachine(), "containingStateMachine", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getVertex__GetIncomings(), this.getTransition(), "getIncomings", 0, -1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getVertex__GetOutgoings(), this.getTransition(), "getOutgoings", 0, -1, IS_UNIQUE, !IS_ORDERED);

		initEClass(regionEClass, Region.class, "Region", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRegion_State(), this.getState(), this.getState_Region(), "state", null, 0, 1, Region.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getRegion_StateMachine(), this.getStateMachine(), this.getStateMachine_Region(), "stateMachine", null, 0, 1, Region.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getRegion_Transition(), this.getTransition(), this.getTransition_Container(), "transition", null, 0, -1, Region.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getRegion_Subvertex(), this.getVertex(), this.getVertex_Container(), "subvertex", null, 0, -1, Region.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		op = initEOperation(getRegion__Shallow_history_vertex__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "shallow_history_vertex", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRegion__Deep_history_vertex__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "deep_history_vertex", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRegion__Initial_vertex__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "initial_vertex", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRegion__Owned__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "owned", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getRegion__BelongsToPSM(), theTypesPackage.getBoolean(), "belongsToPSM", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getRegion__ContainingStateMachine(), this.getStateMachine(), "containingStateMachine", 1, 1, IS_UNIQUE, !IS_ORDERED);

		op = initEOperation(getRegion__IsRedefinitionContextValid__Region(), theTypesPackage.getBoolean(), "isRedefinitionContextValid", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getRegion(), "redefined", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getRegion__RedefinitionContext(), null, "redefinitionContext", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEClass(stateEClass, State.class, "State", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getState_Connection(), this.getConnectionPointReference(), this.getConnectionPointReference_State(), "connection", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getState_ConnectionPoint(), this.getPseudostate(), this.getPseudostate_State(), "connectionPoint", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getState_IsComposite(), ecorePackage.getEBoolean(), "isComposite", "false", 1, 1, State.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEAttribute(getState_IsOrthogonal(), ecorePackage.getEBoolean(), "isOrthogonal", "false", 1, 1, State.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEAttribute(getState_IsSimple(), ecorePackage.getEBoolean(), "isSimple", "true", 1, 1, State.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEAttribute(getState_IsSubmachineState(), ecorePackage.getEBoolean(), "isSubmachineState", "false", 1, 1, State.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getState_Submachine(), this.getStateMachine(), this.getStateMachine_SubmachineState(), "submachine", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getState_Region(), this.getRegion(), this.getRegion_State(), "region", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getState_Enty(), this.getBehavior(), null, "enty", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_Exit(), this.getBehavior(), null, "exit", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_DoActivity(), this.getBehavior(), null, "doActivity", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_StateInvariant(), this.getConstraint(), null, "stateInvariant", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getState__Entry_or_exit__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "entry_or_exit", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getState__Composite_states__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "composite_states", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getState__Destinations_or_sources_of_transitions__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "destinations_or_sources_of_transitions", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getState__Submachine_or_regions__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "submachine_or_regions", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getState__Submachine_states__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "submachine_states", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getState__IsComposite(), theTypesPackage.getBoolean(), "isComposite", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getState__IsOrthogonal(), theTypesPackage.getBoolean(), "isOrthogonal", 1, 1, IS_UNIQUE, !IS_ORDERED);

		op = initEOperation(getState__IsRedefinitionContextValid__State(), theTypesPackage.getBoolean(), "isRedefinitionContextValid", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getState(), "redefined", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getState__IsSimple(), theTypesPackage.getBoolean(), "isSimple", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getState__IsSubmachineState(), theTypesPackage.getBoolean(), "isSubmachineState", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getState__RedefinitionContext(), null, "redefinitionContext", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEClass(connectionPointReferenceEClass, ConnectionPointReference.class, "ConnectionPointReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnectionPointReference_Entry(), this.getPseudostate(), null, "entry", null, 0, -1, ConnectionPointReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getConnectionPointReference_Exit(), this.getPseudostate(), null, "exit", null, 0, -1, ConnectionPointReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getConnectionPointReference_State(), this.getState(), this.getState_Connection(), "state", null, 0, 1, ConnectionPointReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		op = initEOperation(getConnectionPointReference__Entry_pseudostates__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "entry_pseudostates", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getConnectionPointReference__Exit_pseudostates__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "exit_pseudostates", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(transitionEClass, Transition.class, "Transition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTransition_Kind(), this.getTransitionKind(), "kind", "external", 1, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getTransition_Source(), this.getVertex(), this.getVertex_Outgoing(), "source", null, 1, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getTransition_Target(), this.getVertex(), this.getVertex_Incoming(), "target", null, 1, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getTransition_Container(), this.getRegion(), this.getRegion_Transition(), "container", null, 1, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getTransition_Trigger(), this.getTrigger(), null, "trigger", null, 0, -1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransition_Guard(), this.getConstraint(), null, "guard", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getTransition__State_is_local__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "state_is_local", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTransition__Fork_segment_guards__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "fork_segment_guards", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTransition__Join_segment_state__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "join_segment_state", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTransition__Initial_transition__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "initial_transition", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTransition__Outgoing_pseudostates__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "outgoing_pseudostates", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTransition__Signatures_compatible__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "signatures_compatible", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTransition__State_is_internal__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "state_is_internal", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTransition__Join_segment_guards__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "join_segment_guards", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTransition__Fork_segment_state__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "fork_segment_state", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTransition__State_is_external__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "state_is_external", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getTransition__ContainingStateMachine(), this.getStateMachine(), "containingStateMachine", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getTransition__RedefinitionContext(), null, "redefinitionContext", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEClass(finalStateEClass, FinalState.class, "FinalState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getFinalState__No_outgoing_transitions__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "no_outgoing_transitions", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFinalState__Cannot_reference_submachine__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "cannot_reference_submachine", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFinalState__No_exit_behavior__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "no_exit_behavior", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFinalState__No_entry_behavior__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "no_entry_behavior", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFinalState__No_state_behavior__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "no_state_behavior", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFinalState__No_regions__DiagnosticChain_Map(), ecorePackage.getEBoolean(), "no_regions", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDiagnosticChain(), "diagnostics", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEJavaObject());
		g1.getETypeArguments().add(g2);
		addEParameter(op, g1, "context", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(constraintEClass, Constraint.class, "Constraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(behaviorEClass, Behavior.class, "Behavior", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(triggerEClass, Trigger.class, "Trigger", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(transitionKindEEnum, TransitionKind.class, "TransitionKind");
		addEEnumLiteral(transitionKindEEnum, TransitionKind.INTERNAL);
		addEEnumLiteral(transitionKindEEnum, TransitionKind.LOCAL);
		addEEnumLiteral(transitionKindEEnum, TransitionKind.EXTERNAL);

		initEEnum(pseudostateKindEEnum, PseudostateKind.class, "PseudostateKind");
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.INITIAL);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.DEEP_HISTORY);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.SHALLOW_HISTORY);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.JOIN);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.FORK);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.JUNCTION);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.CHOICE);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.ENTRY_POINT);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.EXIT_POINT);
		addEEnumLiteral(pseudostateKindEEnum, PseudostateKind.TERMINATE);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// duplicates
		createDuplicatesAnnotations();
		// redefines
		createRedefinesAnnotations();
		// subsets
		createSubsetsAnnotations();
	}

	/**
	 * Initializes the annotations for <b>duplicates</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createDuplicatesAnnotations() {
		String source = "duplicates";	
		addAnnotation
		  (protocolStateMachineEClass, 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (stateEClass, 
		   source, 
		   new String[] {
		   });
	}

	/**
	 * Initializes the annotations for <b>redefines</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createRedefinesAnnotations() {
		String source = "redefines";	
		addAnnotation
		  (getStateMachine__IsRedefinitionContextValid__StateMachine(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getRegion__IsRedefinitionContextValid__Region(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getState__IsRedefinitionContextValid__State(), 
		   source, 
		   new String[] {
		   });
	}

	/**
	 * Initializes the annotations for <b>subsets</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createSubsetsAnnotations() {
		String source = "subsets";	
		addAnnotation
		  (getStateMachine_ConnectionPoint(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getStateMachine_Region(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getPseudostate_State(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getPseudostate_StateMachine(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getVertex_Container(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getRegion_State(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getRegion_StateMachine(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getRegion_Transition(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getRegion_Subvertex(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getState_Connection(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getState_ConnectionPoint(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getState_Region(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getConnectionPointReference_State(), 
		   source, 
		   new String[] {
		   });	
		addAnnotation
		  (getTransition_Container(), 
		   source, 
		   new String[] {
		   });
	}

} //MagicDrawStatechartsEffectivePackageImpl
