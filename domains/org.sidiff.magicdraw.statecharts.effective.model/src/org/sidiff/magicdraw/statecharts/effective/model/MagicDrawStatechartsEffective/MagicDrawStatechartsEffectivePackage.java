/**
 */
package org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.MagicDrawStatechartsEffectiveFactory
 * @model kind="package"
 * @generated
 */
public interface MagicDrawStatechartsEffectivePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "MagicDrawStatechartsEffective";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://Magicdraw/Statecharts/Effective";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "mdStatechartsEffective";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MagicDrawStatechartsEffectivePackage eINSTANCE = org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.NamedElementImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAMESPACE = 1;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__QUALIFIED_NAME = 2;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Has qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = 0;

	/**
	 * The operation id for the '<em>Has no qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = 1;

	/**
	 * The operation id for the '<em>Visibility needs ownership</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP = 2;

	/**
	 * The operation id for the '<em>Create Dependency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___CREATE_DEPENDENCY__NAMEDELEMENT = 3;

	/**
	 * The operation id for the '<em>Create Usage</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___CREATE_USAGE__NAMEDELEMENT = 4;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___GET_LABEL = 5;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___GET_LABEL__BOOLEAN = 6;

	/**
	 * The operation id for the '<em>All Namespaces</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___ALL_NAMESPACES = 7;

	/**
	 * The operation id for the '<em>Is Distinguishable From</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE = 8;

	/**
	 * The operation id for the '<em>Get Namespace</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___GET_NAMESPACE = 9;

	/**
	 * The operation id for the '<em>Get Qualified Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___GET_QUALIFIED_NAME = 10;

	/**
	 * The operation id for the '<em>Separator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___SEPARATOR = 11;

	/**
	 * The number of operations of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_OPERATION_COUNT = 12;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.NamespaceImpl <em>Namespace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.NamespaceImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getNamespace()
	 * @generated
	 */
	int NAMESPACE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__NAMESPACE = NAMED_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__OWNED_MEMBER = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE__MEMBER = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Namespace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Has qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = NAMED_ELEMENT___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Has no qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = NAMED_ELEMENT___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Visibility needs ownership</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP = NAMED_ELEMENT___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Create Dependency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___CREATE_DEPENDENCY__NAMEDELEMENT = NAMED_ELEMENT___CREATE_DEPENDENCY__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Create Usage</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___CREATE_USAGE__NAMEDELEMENT = NAMED_ELEMENT___CREATE_USAGE__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___GET_LABEL = NAMED_ELEMENT___GET_LABEL;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___GET_LABEL__BOOLEAN = NAMED_ELEMENT___GET_LABEL__BOOLEAN;

	/**
	 * The operation id for the '<em>All Namespaces</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___ALL_NAMESPACES = NAMED_ELEMENT___ALL_NAMESPACES;

	/**
	 * The operation id for the '<em>Is Distinguishable From</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE = NAMED_ELEMENT___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Namespace</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___GET_NAMESPACE = NAMED_ELEMENT___GET_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Qualified Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___GET_QUALIFIED_NAME = NAMED_ELEMENT___GET_QUALIFIED_NAME;

	/**
	 * The operation id for the '<em>Separator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___SEPARATOR = NAMED_ELEMENT___SEPARATOR;

	/**
	 * The operation id for the '<em>Members distinguishable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Names Of Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___GET_NAMES_OF_MEMBER__NAMEDELEMENT = NAMED_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Members Are Distinguishable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___MEMBERS_ARE_DISTINGUISHABLE = NAMED_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE___GET_OWNED_MEMBERS = NAMED_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Namespace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateMachineImpl <em>State Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateMachineImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getStateMachine()
	 * @generated
	 */
	int STATE_MACHINE = 3;

	/**
	 * The feature id for the '<em><b>Connection Point</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__CONNECTION_POINT = 0;

	/**
	 * The feature id for the '<em><b>Submachine State</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__SUBMACHINE_STATE = 1;

	/**
	 * The feature id for the '<em><b>Region</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__REGION = 2;

	/**
	 * The feature id for the '<em><b>Extended State Machine</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__EXTENDED_STATE_MACHINE = 3;

	/**
	 * The number of structural features of the '<em>State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Method</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE___METHOD__DIAGNOSTICCHAIN_MAP = 0;

	/**
	 * The operation id for the '<em>Classifier context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE___CLASSIFIER_CONTEXT__DIAGNOSTICCHAIN_MAP = 1;

	/**
	 * The operation id for the '<em>Context classifier</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE___CONTEXT_CLASSIFIER__DIAGNOSTICCHAIN_MAP = 2;

	/**
	 * The operation id for the '<em>Connection points</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE___CONNECTION_POINTS__DIAGNOSTICCHAIN_MAP = 3;

	/**
	 * The operation id for the '<em>LCA</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE___LCA__STATE_STATE = 4;

	/**
	 * The operation id for the '<em>Ancestor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE___ANCESTOR__STATE_STATE = 5;

	/**
	 * The operation id for the '<em>Is Redefinition Context Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE___IS_REDEFINITION_CONTEXT_VALID__STATEMACHINE = 6;

	/**
	 * The number of operations of the '<em>State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_OPERATION_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.ProtocolStateMachineImpl <em>Protocol State Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.ProtocolStateMachineImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getProtocolStateMachine()
	 * @generated
	 */
	int PROTOCOL_STATE_MACHINE = 2;

	/**
	 * The feature id for the '<em><b>Connection Point</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE__CONNECTION_POINT = STATE_MACHINE__CONNECTION_POINT;

	/**
	 * The feature id for the '<em><b>Submachine State</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE__SUBMACHINE_STATE = STATE_MACHINE__SUBMACHINE_STATE;

	/**
	 * The feature id for the '<em><b>Region</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE__REGION = STATE_MACHINE__REGION;

	/**
	 * The feature id for the '<em><b>Extended State Machine</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE__EXTENDED_STATE_MACHINE = STATE_MACHINE__EXTENDED_STATE_MACHINE;

	/**
	 * The number of structural features of the '<em>Protocol State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE_FEATURE_COUNT = STATE_MACHINE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Method</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___METHOD__DIAGNOSTICCHAIN_MAP = STATE_MACHINE___METHOD__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Classifier context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___CLASSIFIER_CONTEXT__DIAGNOSTICCHAIN_MAP = STATE_MACHINE___CLASSIFIER_CONTEXT__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Context classifier</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___CONTEXT_CLASSIFIER__DIAGNOSTICCHAIN_MAP = STATE_MACHINE___CONTEXT_CLASSIFIER__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Connection points</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___CONNECTION_POINTS__DIAGNOSTICCHAIN_MAP = STATE_MACHINE___CONNECTION_POINTS__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>LCA</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___LCA__STATE_STATE = STATE_MACHINE___LCA__STATE_STATE;

	/**
	 * The operation id for the '<em>Ancestor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___ANCESTOR__STATE_STATE = STATE_MACHINE___ANCESTOR__STATE_STATE;

	/**
	 * The operation id for the '<em>Is Redefinition Context Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___IS_REDEFINITION_CONTEXT_VALID__STATEMACHINE = STATE_MACHINE___IS_REDEFINITION_CONTEXT_VALID__STATEMACHINE;

	/**
	 * The operation id for the '<em>Entry exit do</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___ENTRY_EXIT_DO__DIAGNOSTICCHAIN_MAP = STATE_MACHINE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Protocol transitions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___PROTOCOL_TRANSITIONS__DIAGNOSTICCHAIN_MAP = STATE_MACHINE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Deep or shallow history</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___DEEP_OR_SHALLOW_HISTORY__DIAGNOSTICCHAIN_MAP = STATE_MACHINE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Ports connected</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE___PORTS_CONNECTED__DIAGNOSTICCHAIN_MAP = STATE_MACHINE_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Protocol State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_STATE_MACHINE_OPERATION_COUNT = STATE_MACHINE_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.VertexImpl <em>Vertex</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.VertexImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getVertex()
	 * @generated
	 */
	int VERTEX = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__NAMESPACE = NAMED_ELEMENT__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__QUALIFIED_NAME = NAMED_ELEMENT__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__CONTAINER = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__INCOMING = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX__OUTGOING = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Vertex</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Has qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = NAMED_ELEMENT___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Has no qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = NAMED_ELEMENT___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Visibility needs ownership</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP = NAMED_ELEMENT___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Create Dependency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___CREATE_DEPENDENCY__NAMEDELEMENT = NAMED_ELEMENT___CREATE_DEPENDENCY__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Create Usage</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___CREATE_USAGE__NAMEDELEMENT = NAMED_ELEMENT___CREATE_USAGE__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___GET_LABEL = NAMED_ELEMENT___GET_LABEL;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___GET_LABEL__BOOLEAN = NAMED_ELEMENT___GET_LABEL__BOOLEAN;

	/**
	 * The operation id for the '<em>All Namespaces</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___ALL_NAMESPACES = NAMED_ELEMENT___ALL_NAMESPACES;

	/**
	 * The operation id for the '<em>Is Distinguishable From</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE = NAMED_ELEMENT___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Namespace</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___GET_NAMESPACE = NAMED_ELEMENT___GET_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Qualified Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___GET_QUALIFIED_NAME = NAMED_ELEMENT___GET_QUALIFIED_NAME;

	/**
	 * The operation id for the '<em>Separator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___SEPARATOR = NAMED_ELEMENT___SEPARATOR;

	/**
	 * The operation id for the '<em>Containing State Machine</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___CONTAINING_STATE_MACHINE = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Incomings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___GET_INCOMINGS = NAMED_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Outgoings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX___GET_OUTGOINGS = NAMED_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Vertex</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTEX_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.PseudostateImpl <em>Pseudostate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.PseudostateImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getPseudostate()
	 * @generated
	 */
	int PSEUDOSTATE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__NAME = VERTEX__NAME;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__NAMESPACE = VERTEX__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__QUALIFIED_NAME = VERTEX__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__CONTAINER = VERTEX__CONTAINER;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__INCOMING = VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__OUTGOING = VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>State</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__STATE = VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__KIND = VERTEX_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE__STATE_MACHINE = VERTEX_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Pseudostate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE_FEATURE_COUNT = VERTEX_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Has qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = VERTEX___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Has no qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = VERTEX___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Visibility needs ownership</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP = VERTEX___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Create Dependency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___CREATE_DEPENDENCY__NAMEDELEMENT = VERTEX___CREATE_DEPENDENCY__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Create Usage</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___CREATE_USAGE__NAMEDELEMENT = VERTEX___CREATE_USAGE__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___GET_LABEL = VERTEX___GET_LABEL;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___GET_LABEL__BOOLEAN = VERTEX___GET_LABEL__BOOLEAN;

	/**
	 * The operation id for the '<em>All Namespaces</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___ALL_NAMESPACES = VERTEX___ALL_NAMESPACES;

	/**
	 * The operation id for the '<em>Is Distinguishable From</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE = VERTEX___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Namespace</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___GET_NAMESPACE = VERTEX___GET_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Qualified Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___GET_QUALIFIED_NAME = VERTEX___GET_QUALIFIED_NAME;

	/**
	 * The operation id for the '<em>Separator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___SEPARATOR = VERTEX___SEPARATOR;

	/**
	 * The operation id for the '<em>Containing State Machine</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___CONTAINING_STATE_MACHINE = VERTEX___CONTAINING_STATE_MACHINE;

	/**
	 * The operation id for the '<em>Get Incomings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___GET_INCOMINGS = VERTEX___GET_INCOMINGS;

	/**
	 * The operation id for the '<em>Get Outgoings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___GET_OUTGOINGS = VERTEX___GET_OUTGOINGS;

	/**
	 * The operation id for the '<em>Junction vertex</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___JUNCTION_VERTEX__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>History vertices</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___HISTORY_VERTICES__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Transitions outgoing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___TRANSITIONS_OUTGOING__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Outgoing from initial</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___OUTGOING_FROM_INITIAL__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Fork vertex</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___FORK_VERTEX__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Join vertex</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___JOIN_VERTEX__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Choice vertex</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___CHOICE_VERTEX__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Initial vertex</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___INITIAL_VERTEX__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Transitions incoming</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE___TRANSITIONS_INCOMING__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 8;

	/**
	 * The number of operations of the '<em>Pseudostate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PSEUDOSTATE_OPERATION_COUNT = VERTEX_OPERATION_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.RegionImpl <em>Region</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.RegionImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getRegion()
	 * @generated
	 */
	int REGION = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__NAME = NAMESPACE__NAME;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__NAMESPACE = NAMESPACE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__OWNED_MEMBER = NAMESPACE__OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__MEMBER = NAMESPACE__MEMBER;

	/**
	 * The feature id for the '<em><b>Extended Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__EXTENDED_REGION = NAMESPACE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>State</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__STATE = NAMESPACE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__STATE_MACHINE = NAMESPACE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__TRANSITION = NAMESPACE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Subvertex</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__SUBVERTEX = NAMESPACE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Has qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = NAMESPACE___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Has no qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = NAMESPACE___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Visibility needs ownership</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP = NAMESPACE___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Create Dependency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___CREATE_DEPENDENCY__NAMEDELEMENT = NAMESPACE___CREATE_DEPENDENCY__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Create Usage</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___CREATE_USAGE__NAMEDELEMENT = NAMESPACE___CREATE_USAGE__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___GET_LABEL = NAMESPACE___GET_LABEL;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___GET_LABEL__BOOLEAN = NAMESPACE___GET_LABEL__BOOLEAN;

	/**
	 * The operation id for the '<em>All Namespaces</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___ALL_NAMESPACES = NAMESPACE___ALL_NAMESPACES;

	/**
	 * The operation id for the '<em>Is Distinguishable From</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE = NAMESPACE___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Namespace</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___GET_NAMESPACE = NAMESPACE___GET_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Qualified Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___GET_QUALIFIED_NAME = NAMESPACE___GET_QUALIFIED_NAME;

	/**
	 * The operation id for the '<em>Separator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___SEPARATOR = NAMESPACE___SEPARATOR;

	/**
	 * The operation id for the '<em>Members distinguishable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP = NAMESPACE___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Get Names Of Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___GET_NAMES_OF_MEMBER__NAMEDELEMENT = NAMESPACE___GET_NAMES_OF_MEMBER__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Members Are Distinguishable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___MEMBERS_ARE_DISTINGUISHABLE = NAMESPACE___MEMBERS_ARE_DISTINGUISHABLE;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___GET_OWNED_MEMBERS = NAMESPACE___GET_OWNED_MEMBERS;

	/**
	 * The operation id for the '<em>Shallow history vertex</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___SHALLOW_HISTORY_VERTEX__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Deep history vertex</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___DEEP_HISTORY_VERTEX__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Initial vertex</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___INITIAL_VERTEX__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Owned</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___OWNED__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Belongs To PSM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___BELONGS_TO_PSM = NAMESPACE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Containing State Machine</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___CONTAINING_STATE_MACHINE = NAMESPACE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Redefinition Context Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___IS_REDEFINITION_CONTEXT_VALID__REGION = NAMESPACE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Redefinition Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION___REDEFINITION_CONTEXT = NAMESPACE_OPERATION_COUNT + 7;

	/**
	 * The number of operations of the '<em>Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION_OPERATION_COUNT = NAMESPACE_OPERATION_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getState()
	 * @generated
	 */
	int STATE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = NAMESPACE__NAME;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAMESPACE = NAMESPACE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OWNED_MEMBER = NAMESPACE__OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__MEMBER = NAMESPACE__MEMBER;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__CONTAINER = NAMESPACE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__INCOMING = NAMESPACE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OUTGOING = NAMESPACE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Connection</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__CONNECTION = NAMESPACE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Connection Point</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__CONNECTION_POINT = NAMESPACE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Is Composite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IS_COMPOSITE = NAMESPACE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Is Orthogonal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IS_ORTHOGONAL = NAMESPACE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Is Simple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IS_SIMPLE = NAMESPACE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Is Submachine State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IS_SUBMACHINE_STATE = NAMESPACE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Redefined State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__REDEFINED_STATE = NAMESPACE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Submachine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__SUBMACHINE = NAMESPACE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Region</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__REGION = NAMESPACE_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 12;

	/**
	 * The operation id for the '<em>Has qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = NAMESPACE___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Has no qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = NAMESPACE___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Visibility needs ownership</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP = NAMESPACE___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Create Dependency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___CREATE_DEPENDENCY__NAMEDELEMENT = NAMESPACE___CREATE_DEPENDENCY__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Create Usage</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___CREATE_USAGE__NAMEDELEMENT = NAMESPACE___CREATE_USAGE__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_LABEL = NAMESPACE___GET_LABEL;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_LABEL__BOOLEAN = NAMESPACE___GET_LABEL__BOOLEAN;

	/**
	 * The operation id for the '<em>All Namespaces</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___ALL_NAMESPACES = NAMESPACE___ALL_NAMESPACES;

	/**
	 * The operation id for the '<em>Is Distinguishable From</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE = NAMESPACE___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Namespace</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_NAMESPACE = NAMESPACE___GET_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Qualified Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_QUALIFIED_NAME = NAMESPACE___GET_QUALIFIED_NAME;

	/**
	 * The operation id for the '<em>Separator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___SEPARATOR = NAMESPACE___SEPARATOR;

	/**
	 * The operation id for the '<em>Members distinguishable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP = NAMESPACE___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Get Names Of Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_NAMES_OF_MEMBER__NAMEDELEMENT = NAMESPACE___GET_NAMES_OF_MEMBER__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Members Are Distinguishable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___MEMBERS_ARE_DISTINGUISHABLE = NAMESPACE___MEMBERS_ARE_DISTINGUISHABLE;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_OWNED_MEMBERS = NAMESPACE___GET_OWNED_MEMBERS;

	/**
	 * The operation id for the '<em>Containing State Machine</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___CONTAINING_STATE_MACHINE = NAMESPACE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Incomings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_INCOMINGS = NAMESPACE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Outgoings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___GET_OUTGOINGS = NAMESPACE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Entry or exit</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___ENTRY_OR_EXIT__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Composite states</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___COMPOSITE_STATES__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Destinations or sources of transitions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___DESTINATIONS_OR_SOURCES_OF_TRANSITIONS__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Submachine or regions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___SUBMACHINE_OR_REGIONS__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Submachine states</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___SUBMACHINE_STATES__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Composite</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___IS_COMPOSITE = NAMESPACE_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Orthogonal</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___IS_ORTHOGONAL = NAMESPACE_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Redefinition Context Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___IS_REDEFINITION_CONTEXT_VALID__STATE = NAMESPACE_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Is Simple</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___IS_SIMPLE = NAMESPACE_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Is Submachine State</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___IS_SUBMACHINE_STATE = NAMESPACE_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Redefinition Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE___REDEFINITION_CONTEXT = NAMESPACE_OPERATION_COUNT + 13;

	/**
	 * The number of operations of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_OPERATION_COUNT = NAMESPACE_OPERATION_COUNT + 14;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.ConnectionPointReferenceImpl <em>Connection Point Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.ConnectionPointReferenceImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getConnectionPointReference()
	 * @generated
	 */
	int CONNECTION_POINT_REFERENCE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE__NAME = VERTEX__NAME;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE__NAMESPACE = VERTEX__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE__QUALIFIED_NAME = VERTEX__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE__CONTAINER = VERTEX__CONTAINER;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE__INCOMING = VERTEX__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE__OUTGOING = VERTEX__OUTGOING;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE__ENTRY = VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exit</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE__EXIT = VERTEX_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>State</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE__STATE = VERTEX_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Connection Point Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE_FEATURE_COUNT = VERTEX_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Has qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = VERTEX___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Has no qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = VERTEX___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Visibility needs ownership</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP = VERTEX___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Create Dependency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___CREATE_DEPENDENCY__NAMEDELEMENT = VERTEX___CREATE_DEPENDENCY__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Create Usage</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___CREATE_USAGE__NAMEDELEMENT = VERTEX___CREATE_USAGE__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___GET_LABEL = VERTEX___GET_LABEL;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___GET_LABEL__BOOLEAN = VERTEX___GET_LABEL__BOOLEAN;

	/**
	 * The operation id for the '<em>All Namespaces</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___ALL_NAMESPACES = VERTEX___ALL_NAMESPACES;

	/**
	 * The operation id for the '<em>Is Distinguishable From</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE = VERTEX___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Namespace</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___GET_NAMESPACE = VERTEX___GET_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Qualified Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___GET_QUALIFIED_NAME = VERTEX___GET_QUALIFIED_NAME;

	/**
	 * The operation id for the '<em>Separator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___SEPARATOR = VERTEX___SEPARATOR;

	/**
	 * The operation id for the '<em>Containing State Machine</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___CONTAINING_STATE_MACHINE = VERTEX___CONTAINING_STATE_MACHINE;

	/**
	 * The operation id for the '<em>Get Incomings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___GET_INCOMINGS = VERTEX___GET_INCOMINGS;

	/**
	 * The operation id for the '<em>Get Outgoings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___GET_OUTGOINGS = VERTEX___GET_OUTGOINGS;

	/**
	 * The operation id for the '<em>Entry pseudostates</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___ENTRY_PSEUDOSTATES__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Exit pseudostates</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE___EXIT_PSEUDOSTATES__DIAGNOSTICCHAIN_MAP = VERTEX_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Connection Point Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_POINT_REFERENCE_OPERATION_COUNT = VERTEX_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.TransitionImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAME = NAMESPACE__NAME;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAMESPACE = NAMESPACE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__QUALIFIED_NAME = NAMESPACE__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__OWNED_MEMBER = NAMESPACE__OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__MEMBER = NAMESPACE__MEMBER;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__KIND = NAMESPACE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Redefined Transition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__REDEFINED_TRANSITION = NAMESPACE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SOURCE = NAMESPACE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TARGET = NAMESPACE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__CONTAINER = NAMESPACE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = NAMESPACE_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Has qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = NAMESPACE___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Has no qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = NAMESPACE___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Visibility needs ownership</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP = NAMESPACE___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Create Dependency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___CREATE_DEPENDENCY__NAMEDELEMENT = NAMESPACE___CREATE_DEPENDENCY__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Create Usage</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___CREATE_USAGE__NAMEDELEMENT = NAMESPACE___CREATE_USAGE__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___GET_LABEL = NAMESPACE___GET_LABEL;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___GET_LABEL__BOOLEAN = NAMESPACE___GET_LABEL__BOOLEAN;

	/**
	 * The operation id for the '<em>All Namespaces</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___ALL_NAMESPACES = NAMESPACE___ALL_NAMESPACES;

	/**
	 * The operation id for the '<em>Is Distinguishable From</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE = NAMESPACE___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Namespace</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___GET_NAMESPACE = NAMESPACE___GET_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Qualified Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___GET_QUALIFIED_NAME = NAMESPACE___GET_QUALIFIED_NAME;

	/**
	 * The operation id for the '<em>Separator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___SEPARATOR = NAMESPACE___SEPARATOR;

	/**
	 * The operation id for the '<em>Members distinguishable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP = NAMESPACE___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Get Names Of Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___GET_NAMES_OF_MEMBER__NAMEDELEMENT = NAMESPACE___GET_NAMES_OF_MEMBER__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Members Are Distinguishable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___MEMBERS_ARE_DISTINGUISHABLE = NAMESPACE___MEMBERS_ARE_DISTINGUISHABLE;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___GET_OWNED_MEMBERS = NAMESPACE___GET_OWNED_MEMBERS;

	/**
	 * The operation id for the '<em>State is local</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___STATE_IS_LOCAL__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Fork segment guards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___FORK_SEGMENT_GUARDS__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Join segment state</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___JOIN_SEGMENT_STATE__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Initial transition</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___INITIAL_TRANSITION__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Outgoing pseudostates</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___OUTGOING_PSEUDOSTATES__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Signatures compatible</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___SIGNATURES_COMPATIBLE__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>State is internal</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___STATE_IS_INTERNAL__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Join segment guards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___JOIN_SEGMENT_GUARDS__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Fork segment state</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___FORK_SEGMENT_STATE__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>State is external</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___STATE_IS_EXTERNAL__DIAGNOSTICCHAIN_MAP = NAMESPACE_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Containing State Machine</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___CONTAINING_STATE_MACHINE = NAMESPACE_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Redefinition Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION___REDEFINITION_CONTEXT = NAMESPACE_OPERATION_COUNT + 11;

	/**
	 * The number of operations of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_OPERATION_COUNT = NAMESPACE_OPERATION_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.FinalStateImpl <em>Final State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.FinalStateImpl
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getFinalState()
	 * @generated
	 */
	int FINAL_STATE = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__NAMESPACE = STATE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__QUALIFIED_NAME = STATE__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Owned Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__OWNED_MEMBER = STATE__OWNED_MEMBER;

	/**
	 * The feature id for the '<em><b>Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__MEMBER = STATE__MEMBER;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__CONTAINER = STATE__CONTAINER;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__INCOMING = STATE__INCOMING;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__OUTGOING = STATE__OUTGOING;

	/**
	 * The feature id for the '<em><b>Connection</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__CONNECTION = STATE__CONNECTION;

	/**
	 * The feature id for the '<em><b>Connection Point</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__CONNECTION_POINT = STATE__CONNECTION_POINT;

	/**
	 * The feature id for the '<em><b>Is Composite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__IS_COMPOSITE = STATE__IS_COMPOSITE;

	/**
	 * The feature id for the '<em><b>Is Orthogonal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__IS_ORTHOGONAL = STATE__IS_ORTHOGONAL;

	/**
	 * The feature id for the '<em><b>Is Simple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__IS_SIMPLE = STATE__IS_SIMPLE;

	/**
	 * The feature id for the '<em><b>Is Submachine State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__IS_SUBMACHINE_STATE = STATE__IS_SUBMACHINE_STATE;

	/**
	 * The feature id for the '<em><b>Redefined State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__REDEFINED_STATE = STATE__REDEFINED_STATE;

	/**
	 * The feature id for the '<em><b>Submachine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__SUBMACHINE = STATE__SUBMACHINE;

	/**
	 * The feature id for the '<em><b>Region</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE__REGION = STATE__REGION;

	/**
	 * The number of structural features of the '<em>Final State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Has qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = STATE___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Has no qualified name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = STATE___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Visibility needs ownership</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP = STATE___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Create Dependency</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___CREATE_DEPENDENCY__NAMEDELEMENT = STATE___CREATE_DEPENDENCY__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Create Usage</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___CREATE_USAGE__NAMEDELEMENT = STATE___CREATE_USAGE__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___GET_LABEL = STATE___GET_LABEL;

	/**
	 * The operation id for the '<em>Get Label</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___GET_LABEL__BOOLEAN = STATE___GET_LABEL__BOOLEAN;

	/**
	 * The operation id for the '<em>All Namespaces</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___ALL_NAMESPACES = STATE___ALL_NAMESPACES;

	/**
	 * The operation id for the '<em>Is Distinguishable From</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE = STATE___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Namespace</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___GET_NAMESPACE = STATE___GET_NAMESPACE;

	/**
	 * The operation id for the '<em>Get Qualified Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___GET_QUALIFIED_NAME = STATE___GET_QUALIFIED_NAME;

	/**
	 * The operation id for the '<em>Separator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___SEPARATOR = STATE___SEPARATOR;

	/**
	 * The operation id for the '<em>Members distinguishable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP = STATE___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Get Names Of Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___GET_NAMES_OF_MEMBER__NAMEDELEMENT = STATE___GET_NAMES_OF_MEMBER__NAMEDELEMENT;

	/**
	 * The operation id for the '<em>Members Are Distinguishable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___MEMBERS_ARE_DISTINGUISHABLE = STATE___MEMBERS_ARE_DISTINGUISHABLE;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___GET_OWNED_MEMBERS = STATE___GET_OWNED_MEMBERS;

	/**
	 * The operation id for the '<em>Containing State Machine</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___CONTAINING_STATE_MACHINE = STATE___CONTAINING_STATE_MACHINE;

	/**
	 * The operation id for the '<em>Get Incomings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___GET_INCOMINGS = STATE___GET_INCOMINGS;

	/**
	 * The operation id for the '<em>Get Outgoings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___GET_OUTGOINGS = STATE___GET_OUTGOINGS;

	/**
	 * The operation id for the '<em>Entry or exit</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___ENTRY_OR_EXIT__DIAGNOSTICCHAIN_MAP = STATE___ENTRY_OR_EXIT__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Composite states</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___COMPOSITE_STATES__DIAGNOSTICCHAIN_MAP = STATE___COMPOSITE_STATES__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Destinations or sources of transitions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___DESTINATIONS_OR_SOURCES_OF_TRANSITIONS__DIAGNOSTICCHAIN_MAP = STATE___DESTINATIONS_OR_SOURCES_OF_TRANSITIONS__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Submachine or regions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___SUBMACHINE_OR_REGIONS__DIAGNOSTICCHAIN_MAP = STATE___SUBMACHINE_OR_REGIONS__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Submachine states</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___SUBMACHINE_STATES__DIAGNOSTICCHAIN_MAP = STATE___SUBMACHINE_STATES__DIAGNOSTICCHAIN_MAP;

	/**
	 * The operation id for the '<em>Is Composite</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___IS_COMPOSITE = STATE___IS_COMPOSITE;

	/**
	 * The operation id for the '<em>Is Orthogonal</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___IS_ORTHOGONAL = STATE___IS_ORTHOGONAL;

	/**
	 * The operation id for the '<em>Is Redefinition Context Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___IS_REDEFINITION_CONTEXT_VALID__STATE = STATE___IS_REDEFINITION_CONTEXT_VALID__STATE;

	/**
	 * The operation id for the '<em>Is Simple</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___IS_SIMPLE = STATE___IS_SIMPLE;

	/**
	 * The operation id for the '<em>Is Submachine State</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___IS_SUBMACHINE_STATE = STATE___IS_SUBMACHINE_STATE;

	/**
	 * The operation id for the '<em>Redefinition Context</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___REDEFINITION_CONTEXT = STATE___REDEFINITION_CONTEXT;

	/**
	 * The operation id for the '<em>No outgoing transitions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___NO_OUTGOING_TRANSITIONS__DIAGNOSTICCHAIN_MAP = STATE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Cannot reference submachine</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___CANNOT_REFERENCE_SUBMACHINE__DIAGNOSTICCHAIN_MAP = STATE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>No exit behavior</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___NO_EXIT_BEHAVIOR__DIAGNOSTICCHAIN_MAP = STATE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>No entry behavior</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___NO_ENTRY_BEHAVIOR__DIAGNOSTICCHAIN_MAP = STATE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>No state behavior</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___NO_STATE_BEHAVIOR__DIAGNOSTICCHAIN_MAP = STATE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>No regions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE___NO_REGIONS__DIAGNOSTICCHAIN_MAP = STATE_OPERATION_COUNT + 5;

	/**
	 * The number of operations of the '<em>Final State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINAL_STATE_OPERATION_COUNT = STATE_OPERATION_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.TransitionKind <em>Transition Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.TransitionKind
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getTransitionKind()
	 * @generated
	 */
	int TRANSITION_KIND = 11;

	/**
	 * The meta object id for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.PseudostateKind <em>Pseudostate Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.PseudostateKind
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getPseudostateKind()
	 * @generated
	 */
	int PSEUDOSTATE_KIND = 12;


	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Namespace</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace
	 * @generated
	 */
	EClass getNamespace();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#getOwnedMember <em>Owned Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Owned Member</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#getOwnedMember()
	 * @see #getNamespace()
	 * @generated
	 */
	EReference getNamespace_OwnedMember();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Member</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#getMember()
	 * @see #getNamespace()
	 * @generated
	 */
	EReference getNamespace_Member();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#members_distinguishable(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Members distinguishable</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Members distinguishable</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#members_distinguishable(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getNamespace__Members_distinguishable__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#getNamesOfMember(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement) <em>Get Names Of Member</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Names Of Member</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#getNamesOfMember(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement)
	 * @generated
	 */
	EOperation getNamespace__GetNamesOfMember__NamedElement();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#membersAreDistinguishable() <em>Members Are Distinguishable</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Members Are Distinguishable</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#membersAreDistinguishable()
	 * @generated
	 */
	EOperation getNamespace__MembersAreDistinguishable();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#getOwnedMembers() <em>Get Owned Members</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Owned Members</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace#getOwnedMembers()
	 * @generated
	 */
	EOperation getNamespace__GetOwnedMembers();

	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Namespace</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getNamespace()
	 * @see #getNamedElement()
	 * @generated
	 */
	EReference getNamedElement_Namespace();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getQualifiedName <em>Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getQualifiedName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_QualifiedName();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#has_qualified_name(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Has qualified name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has qualified name</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#has_qualified_name(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getNamedElement__Has_qualified_name__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#has_no_qualified_name(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Has no qualified name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has no qualified name</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#has_no_qualified_name(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getNamedElement__Has_no_qualified_name__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#visibility_needs_ownership(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Visibility needs ownership</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Visibility needs ownership</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#visibility_needs_ownership(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getNamedElement__Visibility_needs_ownership__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#createDependency(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement) <em>Create Dependency</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Dependency</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#createDependency(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement)
	 * @generated
	 */
	EOperation getNamedElement__CreateDependency__NamedElement();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#createUsage(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement) <em>Create Usage</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Usage</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#createUsage(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement)
	 * @generated
	 */
	EOperation getNamedElement__CreateUsage__NamedElement();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getLabel() <em>Get Label</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Label</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getLabel()
	 * @generated
	 */
	EOperation getNamedElement__GetLabel();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getLabel(boolean) <em>Get Label</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Label</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getLabel(boolean)
	 * @generated
	 */
	EOperation getNamedElement__GetLabel__boolean();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#allNamespaces() <em>All Namespaces</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>All Namespaces</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#allNamespaces()
	 * @generated
	 */
	EOperation getNamedElement__AllNamespaces();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#isDistinguishableFrom(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement, org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace) <em>Is Distinguishable From</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Distinguishable From</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#isDistinguishableFrom(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement, org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Namespace)
	 * @generated
	 */
	EOperation getNamedElement__IsDistinguishableFrom__NamedElement_Namespace();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getNamespace() <em>Get Namespace</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Namespace</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getNamespace()
	 * @generated
	 */
	EOperation getNamedElement__GetNamespace();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getQualifiedName() <em>Get Qualified Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Qualified Name</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#getQualifiedName()
	 * @generated
	 */
	EOperation getNamedElement__GetQualifiedName();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#separator() <em>Separator</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Separator</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.NamedElement#separator()
	 * @generated
	 */
	EOperation getNamedElement__Separator();

	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine <em>Protocol State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Protocol State Machine</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine
	 * @generated
	 */
	EClass getProtocolStateMachine();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine#entry_exit_do(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Entry exit do</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Entry exit do</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine#entry_exit_do(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getProtocolStateMachine__Entry_exit_do__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine#protocol_transitions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Protocol transitions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Protocol transitions</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine#protocol_transitions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getProtocolStateMachine__Protocol_transitions__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine#deep_or_shallow_history(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Deep or shallow history</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Deep or shallow history</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine#deep_or_shallow_history(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getProtocolStateMachine__Deep_or_shallow_history__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine#ports_connected(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Ports connected</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Ports connected</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ProtocolStateMachine#ports_connected(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getProtocolStateMachine__Ports_connected__DiagnosticChain_Map();

	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Machine</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine
	 * @generated
	 */
	EClass getStateMachine();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getConnectionPoint <em>Connection Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Connection Point</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getConnectionPoint()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_ConnectionPoint();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getSubmachineState <em>Submachine State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Submachine State</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getSubmachineState()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_SubmachineState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getRegion <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Region</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getRegion()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_Region();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getExtendedStateMachine <em>Extended State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Extended State Machine</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#getExtendedStateMachine()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_ExtendedStateMachine();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#method(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Method</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Method</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#method(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getStateMachine__Method__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#classifier_context(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Classifier context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Classifier context</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#classifier_context(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getStateMachine__Classifier_context__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#context_classifier(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Context classifier</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Context classifier</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#context_classifier(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getStateMachine__Context_classifier__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#connection_points(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Connection points</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Connection points</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#connection_points(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getStateMachine__Connection_points__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#LCA(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State, org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State) <em>LCA</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>LCA</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#LCA(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State, org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State)
	 * @generated
	 */
	EOperation getStateMachine__LCA__State_State();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#ancestor(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State, org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State) <em>Ancestor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Ancestor</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#ancestor(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State, org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State)
	 * @generated
	 */
	EOperation getStateMachine__Ancestor__State_State();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#isRedefinitionContextValid(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine) <em>Is Redefinition Context Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Redefinition Context Valid</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine#isRedefinitionContextValid(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.StateMachine)
	 * @generated
	 */
	EOperation getStateMachine__IsRedefinitionContextValid__StateMachine();

	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate <em>Pseudostate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pseudostate</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate
	 * @generated
	 */
	EClass getPseudostate();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>State</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#getState()
	 * @see #getPseudostate()
	 * @generated
	 */
	EReference getPseudostate_State();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#getKind()
	 * @see #getPseudostate()
	 * @generated
	 */
	EAttribute getPseudostate_Kind();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>State Machine</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#getStateMachine()
	 * @see #getPseudostate()
	 * @generated
	 */
	EReference getPseudostate_StateMachine();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#junction_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Junction vertex</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Junction vertex</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#junction_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getPseudostate__Junction_vertex__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#history_vertices(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>History vertices</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>History vertices</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#history_vertices(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getPseudostate__History_vertices__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#transitions_outgoing(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Transitions outgoing</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Transitions outgoing</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#transitions_outgoing(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getPseudostate__Transitions_outgoing__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#outgoing_from_initial(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Outgoing from initial</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Outgoing from initial</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#outgoing_from_initial(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getPseudostate__Outgoing_from_initial__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#fork_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Fork vertex</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Fork vertex</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#fork_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getPseudostate__Fork_vertex__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#join_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Join vertex</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Join vertex</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#join_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getPseudostate__Join_vertex__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#choice_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Choice vertex</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Choice vertex</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#choice_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getPseudostate__Choice_vertex__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#initial_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Initial vertex</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Initial vertex</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#initial_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getPseudostate__Initial_vertex__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#transitions_incoming(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Transitions incoming</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Transitions incoming</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Pseudostate#transitions_incoming(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getPseudostate__Transitions_incoming__DiagnosticChain_Map();

	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex <em>Vertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vertex</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex
	 * @generated
	 */
	EClass getVertex();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Container</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getContainer()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Container();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getIncoming()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Incoming();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getOutgoing()
	 * @see #getVertex()
	 * @generated
	 */
	EReference getVertex_Outgoing();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#containingStateMachine() <em>Containing State Machine</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Containing State Machine</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#containingStateMachine()
	 * @generated
	 */
	EOperation getVertex__ContainingStateMachine();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getIncomings() <em>Get Incomings</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Incomings</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getIncomings()
	 * @generated
	 */
	EOperation getVertex__GetIncomings();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getOutgoings() <em>Get Outgoings</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Outgoings</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Vertex#getOutgoings()
	 * @generated
	 */
	EOperation getVertex__GetOutgoings();

	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Region</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region
	 * @generated
	 */
	EClass getRegion();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getExtendedRegion <em>Extended Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Extended Region</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getExtendedRegion()
	 * @see #getRegion()
	 * @generated
	 */
	EReference getRegion_ExtendedRegion();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>State</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getState()
	 * @see #getRegion()
	 * @generated
	 */
	EReference getRegion_State();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>State Machine</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getStateMachine()
	 * @see #getRegion()
	 * @generated
	 */
	EReference getRegion_StateMachine();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getTransition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transition</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getTransition()
	 * @see #getRegion()
	 * @generated
	 */
	EReference getRegion_Transition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getSubvertex <em>Subvertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Subvertex</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#getSubvertex()
	 * @see #getRegion()
	 * @generated
	 */
	EReference getRegion_Subvertex();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#shallow_history_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Shallow history vertex</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Shallow history vertex</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#shallow_history_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getRegion__Shallow_history_vertex__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#deep_history_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Deep history vertex</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Deep history vertex</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#deep_history_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getRegion__Deep_history_vertex__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#initial_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Initial vertex</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Initial vertex</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#initial_vertex(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getRegion__Initial_vertex__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#owned(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Owned</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Owned</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#owned(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getRegion__Owned__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#belongsToPSM() <em>Belongs To PSM</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Belongs To PSM</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#belongsToPSM()
	 * @generated
	 */
	EOperation getRegion__BelongsToPSM();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#containingStateMachine() <em>Containing State Machine</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Containing State Machine</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#containingStateMachine()
	 * @generated
	 */
	EOperation getRegion__ContainingStateMachine();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#isRedefinitionContextValid(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region) <em>Is Redefinition Context Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Redefinition Context Valid</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#isRedefinitionContextValid(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region)
	 * @generated
	 */
	EOperation getRegion__IsRedefinitionContextValid__Region();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#redefinitionContext() <em>Redefinition Context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Redefinition Context</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Region#redefinitionContext()
	 * @generated
	 */
	EOperation getRegion__RedefinitionContext();

	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getConnection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Connection</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getConnection()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Connection();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getConnectionPoint <em>Connection Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Connection Point</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getConnectionPoint()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_ConnectionPoint();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsComposite <em>Is Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Composite</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsComposite()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_IsComposite();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsOrthogonal <em>Is Orthogonal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Orthogonal</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsOrthogonal()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_IsOrthogonal();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsSimple <em>Is Simple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Simple</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsSimple()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_IsSimple();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsSubmachineState <em>Is Submachine State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Submachine State</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isIsSubmachineState()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_IsSubmachineState();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getRedefinedState <em>Redefined State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Redefined State</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getRedefinedState()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_RedefinedState();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getSubmachine <em>Submachine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Submachine</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getSubmachine()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Submachine();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getRegion <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Region</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#getRegion()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Region();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#entry_or_exit(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Entry or exit</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Entry or exit</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#entry_or_exit(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getState__Entry_or_exit__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#composite_states(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Composite states</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Composite states</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#composite_states(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getState__Composite_states__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#destinations_or_sources_of_transitions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Destinations or sources of transitions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Destinations or sources of transitions</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#destinations_or_sources_of_transitions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getState__Destinations_or_sources_of_transitions__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#submachine_or_regions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Submachine or regions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Submachine or regions</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#submachine_or_regions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getState__Submachine_or_regions__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#submachine_states(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Submachine states</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Submachine states</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#submachine_states(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getState__Submachine_states__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isComposite() <em>Is Composite</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Composite</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isComposite()
	 * @generated
	 */
	EOperation getState__IsComposite();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isOrthogonal() <em>Is Orthogonal</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Orthogonal</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isOrthogonal()
	 * @generated
	 */
	EOperation getState__IsOrthogonal();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isRedefinitionContextValid(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State) <em>Is Redefinition Context Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Redefinition Context Valid</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isRedefinitionContextValid(org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State)
	 * @generated
	 */
	EOperation getState__IsRedefinitionContextValid__State();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isSimple() <em>Is Simple</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Simple</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isSimple()
	 * @generated
	 */
	EOperation getState__IsSimple();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isSubmachineState() <em>Is Submachine State</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Submachine State</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#isSubmachineState()
	 * @generated
	 */
	EOperation getState__IsSubmachineState();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#redefinitionContext() <em>Redefinition Context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Redefinition Context</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.State#redefinitionContext()
	 * @generated
	 */
	EOperation getState__RedefinitionContext();

	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference <em>Connection Point Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection Point Reference</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference
	 * @generated
	 */
	EClass getConnectionPointReference();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Entry</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#getEntry()
	 * @see #getConnectionPointReference()
	 * @generated
	 */
	EReference getConnectionPointReference_Entry();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#getExit <em>Exit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Exit</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#getExit()
	 * @see #getConnectionPointReference()
	 * @generated
	 */
	EReference getConnectionPointReference_Exit();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>State</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#getState()
	 * @see #getConnectionPointReference()
	 * @generated
	 */
	EReference getConnectionPointReference_State();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#entry_pseudostates(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Entry pseudostates</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Entry pseudostates</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#entry_pseudostates(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getConnectionPointReference__Entry_pseudostates__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#exit_pseudostates(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Exit pseudostates</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Exit pseudostates</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.ConnectionPointReference#exit_pseudostates(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getConnectionPointReference__Exit_pseudostates__DiagnosticChain_Map();

	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getKind()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Kind();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getRedefinedTransition <em>Redefined Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Redefined Transition</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getRedefinedTransition()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_RedefinedTransition();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getSource()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Source();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getTarget()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Target();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Container</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#getContainer()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Container();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#state_is_local(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>State is local</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>State is local</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#state_is_local(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTransition__State_is_local__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#fork_segment_guards(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Fork segment guards</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Fork segment guards</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#fork_segment_guards(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTransition__Fork_segment_guards__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#join_segment_state(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Join segment state</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Join segment state</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#join_segment_state(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTransition__Join_segment_state__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#initial_transition(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Initial transition</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Initial transition</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#initial_transition(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTransition__Initial_transition__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#outgoing_pseudostates(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Outgoing pseudostates</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Outgoing pseudostates</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#outgoing_pseudostates(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTransition__Outgoing_pseudostates__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#signatures_compatible(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Signatures compatible</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Signatures compatible</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#signatures_compatible(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTransition__Signatures_compatible__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#state_is_internal(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>State is internal</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>State is internal</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#state_is_internal(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTransition__State_is_internal__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#join_segment_guards(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Join segment guards</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Join segment guards</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#join_segment_guards(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTransition__Join_segment_guards__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#fork_segment_state(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Fork segment state</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Fork segment state</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#fork_segment_state(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTransition__Fork_segment_state__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#state_is_external(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>State is external</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>State is external</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#state_is_external(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getTransition__State_is_external__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#containingStateMachine() <em>Containing State Machine</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Containing State Machine</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#containingStateMachine()
	 * @generated
	 */
	EOperation getTransition__ContainingStateMachine();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#redefinitionContext() <em>Redefinition Context</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Redefinition Context</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.Transition#redefinitionContext()
	 * @generated
	 */
	EOperation getTransition__RedefinitionContext();

	/**
	 * Returns the meta object for class '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState <em>Final State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Final State</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState
	 * @generated
	 */
	EClass getFinalState();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#no_outgoing_transitions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>No outgoing transitions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>No outgoing transitions</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#no_outgoing_transitions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getFinalState__No_outgoing_transitions__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#cannot_reference_submachine(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Cannot reference submachine</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Cannot reference submachine</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#cannot_reference_submachine(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getFinalState__Cannot_reference_submachine__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#no_exit_behavior(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>No exit behavior</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>No exit behavior</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#no_exit_behavior(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getFinalState__No_exit_behavior__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#no_entry_behavior(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>No entry behavior</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>No entry behavior</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#no_entry_behavior(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getFinalState__No_entry_behavior__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#no_state_behavior(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>No state behavior</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>No state behavior</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#no_state_behavior(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getFinalState__No_state_behavior__DiagnosticChain_Map();

	/**
	 * Returns the meta object for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#no_regions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>No regions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>No regions</em>' operation.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.FinalState#no_regions(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getFinalState__No_regions__DiagnosticChain_Map();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.TransitionKind <em>Transition Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Transition Kind</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.TransitionKind
	 * @generated
	 */
	EEnum getTransitionKind();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.PseudostateKind <em>Pseudostate Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Pseudostate Kind</em>'.
	 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.PseudostateKind
	 * @generated
	 */
	EEnum getPseudostateKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MagicDrawStatechartsEffectiveFactory getMagicDrawStatechartsEffectiveFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.NamespaceImpl <em>Namespace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.NamespaceImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getNamespace()
		 * @generated
		 */
		EClass NAMESPACE = eINSTANCE.getNamespace();

		/**
		 * The meta object literal for the '<em><b>Owned Member</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMESPACE__OWNED_MEMBER = eINSTANCE.getNamespace_OwnedMember();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMESPACE__MEMBER = eINSTANCE.getNamespace_Member();

		/**
		 * The meta object literal for the '<em><b>Members distinguishable</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMESPACE___MEMBERS_DISTINGUISHABLE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getNamespace__Members_distinguishable__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Get Names Of Member</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMESPACE___GET_NAMES_OF_MEMBER__NAMEDELEMENT = eINSTANCE.getNamespace__GetNamesOfMember__NamedElement();

		/**
		 * The meta object literal for the '<em><b>Members Are Distinguishable</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMESPACE___MEMBERS_ARE_DISTINGUISHABLE = eINSTANCE.getNamespace__MembersAreDistinguishable();

		/**
		 * The meta object literal for the '<em><b>Get Owned Members</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMESPACE___GET_OWNED_MEMBERS = eINSTANCE.getNamespace__GetOwnedMembers();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.NamedElementImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMED_ELEMENT__NAMESPACE = eINSTANCE.getNamedElement_Namespace();

		/**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__QUALIFIED_NAME = eINSTANCE.getNamedElement_QualifiedName();

		/**
		 * The meta object literal for the '<em><b>Has qualified name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___HAS_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = eINSTANCE.getNamedElement__Has_qualified_name__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Has no qualified name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___HAS_NO_QUALIFIED_NAME__DIAGNOSTICCHAIN_MAP = eINSTANCE.getNamedElement__Has_no_qualified_name__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Visibility needs ownership</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___VISIBILITY_NEEDS_OWNERSHIP__DIAGNOSTICCHAIN_MAP = eINSTANCE.getNamedElement__Visibility_needs_ownership__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Create Dependency</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___CREATE_DEPENDENCY__NAMEDELEMENT = eINSTANCE.getNamedElement__CreateDependency__NamedElement();

		/**
		 * The meta object literal for the '<em><b>Create Usage</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___CREATE_USAGE__NAMEDELEMENT = eINSTANCE.getNamedElement__CreateUsage__NamedElement();

		/**
		 * The meta object literal for the '<em><b>Get Label</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___GET_LABEL = eINSTANCE.getNamedElement__GetLabel();

		/**
		 * The meta object literal for the '<em><b>Get Label</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___GET_LABEL__BOOLEAN = eINSTANCE.getNamedElement__GetLabel__boolean();

		/**
		 * The meta object literal for the '<em><b>All Namespaces</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___ALL_NAMESPACES = eINSTANCE.getNamedElement__AllNamespaces();

		/**
		 * The meta object literal for the '<em><b>Is Distinguishable From</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___IS_DISTINGUISHABLE_FROM__NAMEDELEMENT_NAMESPACE = eINSTANCE.getNamedElement__IsDistinguishableFrom__NamedElement_Namespace();

		/**
		 * The meta object literal for the '<em><b>Get Namespace</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___GET_NAMESPACE = eINSTANCE.getNamedElement__GetNamespace();

		/**
		 * The meta object literal for the '<em><b>Get Qualified Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___GET_QUALIFIED_NAME = eINSTANCE.getNamedElement__GetQualifiedName();

		/**
		 * The meta object literal for the '<em><b>Separator</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___SEPARATOR = eINSTANCE.getNamedElement__Separator();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.ProtocolStateMachineImpl <em>Protocol State Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.ProtocolStateMachineImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getProtocolStateMachine()
		 * @generated
		 */
		EClass PROTOCOL_STATE_MACHINE = eINSTANCE.getProtocolStateMachine();

		/**
		 * The meta object literal for the '<em><b>Entry exit do</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROTOCOL_STATE_MACHINE___ENTRY_EXIT_DO__DIAGNOSTICCHAIN_MAP = eINSTANCE.getProtocolStateMachine__Entry_exit_do__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Protocol transitions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROTOCOL_STATE_MACHINE___PROTOCOL_TRANSITIONS__DIAGNOSTICCHAIN_MAP = eINSTANCE.getProtocolStateMachine__Protocol_transitions__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Deep or shallow history</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROTOCOL_STATE_MACHINE___DEEP_OR_SHALLOW_HISTORY__DIAGNOSTICCHAIN_MAP = eINSTANCE.getProtocolStateMachine__Deep_or_shallow_history__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Ports connected</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROTOCOL_STATE_MACHINE___PORTS_CONNECTED__DIAGNOSTICCHAIN_MAP = eINSTANCE.getProtocolStateMachine__Ports_connected__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateMachineImpl <em>State Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateMachineImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getStateMachine()
		 * @generated
		 */
		EClass STATE_MACHINE = eINSTANCE.getStateMachine();

		/**
		 * The meta object literal for the '<em><b>Connection Point</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__CONNECTION_POINT = eINSTANCE.getStateMachine_ConnectionPoint();

		/**
		 * The meta object literal for the '<em><b>Submachine State</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__SUBMACHINE_STATE = eINSTANCE.getStateMachine_SubmachineState();

		/**
		 * The meta object literal for the '<em><b>Region</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__REGION = eINSTANCE.getStateMachine_Region();

		/**
		 * The meta object literal for the '<em><b>Extended State Machine</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__EXTENDED_STATE_MACHINE = eINSTANCE.getStateMachine_ExtendedStateMachine();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE_MACHINE___METHOD__DIAGNOSTICCHAIN_MAP = eINSTANCE.getStateMachine__Method__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Classifier context</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE_MACHINE___CLASSIFIER_CONTEXT__DIAGNOSTICCHAIN_MAP = eINSTANCE.getStateMachine__Classifier_context__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Context classifier</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE_MACHINE___CONTEXT_CLASSIFIER__DIAGNOSTICCHAIN_MAP = eINSTANCE.getStateMachine__Context_classifier__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Connection points</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE_MACHINE___CONNECTION_POINTS__DIAGNOSTICCHAIN_MAP = eINSTANCE.getStateMachine__Connection_points__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>LCA</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE_MACHINE___LCA__STATE_STATE = eINSTANCE.getStateMachine__LCA__State_State();

		/**
		 * The meta object literal for the '<em><b>Ancestor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE_MACHINE___ANCESTOR__STATE_STATE = eINSTANCE.getStateMachine__Ancestor__State_State();

		/**
		 * The meta object literal for the '<em><b>Is Redefinition Context Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE_MACHINE___IS_REDEFINITION_CONTEXT_VALID__STATEMACHINE = eINSTANCE.getStateMachine__IsRedefinitionContextValid__StateMachine();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.PseudostateImpl <em>Pseudostate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.PseudostateImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getPseudostate()
		 * @generated
		 */
		EClass PSEUDOSTATE = eINSTANCE.getPseudostate();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PSEUDOSTATE__STATE = eINSTANCE.getPseudostate_State();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PSEUDOSTATE__KIND = eINSTANCE.getPseudostate_Kind();

		/**
		 * The meta object literal for the '<em><b>State Machine</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PSEUDOSTATE__STATE_MACHINE = eINSTANCE.getPseudostate_StateMachine();

		/**
		 * The meta object literal for the '<em><b>Junction vertex</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PSEUDOSTATE___JUNCTION_VERTEX__DIAGNOSTICCHAIN_MAP = eINSTANCE.getPseudostate__Junction_vertex__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>History vertices</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PSEUDOSTATE___HISTORY_VERTICES__DIAGNOSTICCHAIN_MAP = eINSTANCE.getPseudostate__History_vertices__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Transitions outgoing</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PSEUDOSTATE___TRANSITIONS_OUTGOING__DIAGNOSTICCHAIN_MAP = eINSTANCE.getPseudostate__Transitions_outgoing__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Outgoing from initial</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PSEUDOSTATE___OUTGOING_FROM_INITIAL__DIAGNOSTICCHAIN_MAP = eINSTANCE.getPseudostate__Outgoing_from_initial__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Fork vertex</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PSEUDOSTATE___FORK_VERTEX__DIAGNOSTICCHAIN_MAP = eINSTANCE.getPseudostate__Fork_vertex__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Join vertex</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PSEUDOSTATE___JOIN_VERTEX__DIAGNOSTICCHAIN_MAP = eINSTANCE.getPseudostate__Join_vertex__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Choice vertex</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PSEUDOSTATE___CHOICE_VERTEX__DIAGNOSTICCHAIN_MAP = eINSTANCE.getPseudostate__Choice_vertex__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Initial vertex</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PSEUDOSTATE___INITIAL_VERTEX__DIAGNOSTICCHAIN_MAP = eINSTANCE.getPseudostate__Initial_vertex__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Transitions incoming</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PSEUDOSTATE___TRANSITIONS_INCOMING__DIAGNOSTICCHAIN_MAP = eINSTANCE.getPseudostate__Transitions_incoming__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.VertexImpl <em>Vertex</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.VertexImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getVertex()
		 * @generated
		 */
		EClass VERTEX = eINSTANCE.getVertex();

		/**
		 * The meta object literal for the '<em><b>Container</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__CONTAINER = eINSTANCE.getVertex_Container();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__INCOMING = eINSTANCE.getVertex_Incoming();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERTEX__OUTGOING = eINSTANCE.getVertex_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Containing State Machine</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERTEX___CONTAINING_STATE_MACHINE = eINSTANCE.getVertex__ContainingStateMachine();

		/**
		 * The meta object literal for the '<em><b>Get Incomings</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERTEX___GET_INCOMINGS = eINSTANCE.getVertex__GetIncomings();

		/**
		 * The meta object literal for the '<em><b>Get Outgoings</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERTEX___GET_OUTGOINGS = eINSTANCE.getVertex__GetOutgoings();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.RegionImpl <em>Region</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.RegionImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getRegion()
		 * @generated
		 */
		EClass REGION = eINSTANCE.getRegion();

		/**
		 * The meta object literal for the '<em><b>Extended Region</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGION__EXTENDED_REGION = eINSTANCE.getRegion_ExtendedRegion();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGION__STATE = eINSTANCE.getRegion_State();

		/**
		 * The meta object literal for the '<em><b>State Machine</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGION__STATE_MACHINE = eINSTANCE.getRegion_StateMachine();

		/**
		 * The meta object literal for the '<em><b>Transition</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGION__TRANSITION = eINSTANCE.getRegion_Transition();

		/**
		 * The meta object literal for the '<em><b>Subvertex</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGION__SUBVERTEX = eINSTANCE.getRegion_Subvertex();

		/**
		 * The meta object literal for the '<em><b>Shallow history vertex</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REGION___SHALLOW_HISTORY_VERTEX__DIAGNOSTICCHAIN_MAP = eINSTANCE.getRegion__Shallow_history_vertex__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Deep history vertex</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REGION___DEEP_HISTORY_VERTEX__DIAGNOSTICCHAIN_MAP = eINSTANCE.getRegion__Deep_history_vertex__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Initial vertex</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REGION___INITIAL_VERTEX__DIAGNOSTICCHAIN_MAP = eINSTANCE.getRegion__Initial_vertex__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Owned</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REGION___OWNED__DIAGNOSTICCHAIN_MAP = eINSTANCE.getRegion__Owned__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Belongs To PSM</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REGION___BELONGS_TO_PSM = eINSTANCE.getRegion__BelongsToPSM();

		/**
		 * The meta object literal for the '<em><b>Containing State Machine</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REGION___CONTAINING_STATE_MACHINE = eINSTANCE.getRegion__ContainingStateMachine();

		/**
		 * The meta object literal for the '<em><b>Is Redefinition Context Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REGION___IS_REDEFINITION_CONTEXT_VALID__REGION = eINSTANCE.getRegion__IsRedefinitionContextValid__Region();

		/**
		 * The meta object literal for the '<em><b>Redefinition Context</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REGION___REDEFINITION_CONTEXT = eINSTANCE.getRegion__RedefinitionContext();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.StateImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Connection</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__CONNECTION = eINSTANCE.getState_Connection();

		/**
		 * The meta object literal for the '<em><b>Connection Point</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__CONNECTION_POINT = eINSTANCE.getState_ConnectionPoint();

		/**
		 * The meta object literal for the '<em><b>Is Composite</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__IS_COMPOSITE = eINSTANCE.getState_IsComposite();

		/**
		 * The meta object literal for the '<em><b>Is Orthogonal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__IS_ORTHOGONAL = eINSTANCE.getState_IsOrthogonal();

		/**
		 * The meta object literal for the '<em><b>Is Simple</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__IS_SIMPLE = eINSTANCE.getState_IsSimple();

		/**
		 * The meta object literal for the '<em><b>Is Submachine State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__IS_SUBMACHINE_STATE = eINSTANCE.getState_IsSubmachineState();

		/**
		 * The meta object literal for the '<em><b>Redefined State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__REDEFINED_STATE = eINSTANCE.getState_RedefinedState();

		/**
		 * The meta object literal for the '<em><b>Submachine</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__SUBMACHINE = eINSTANCE.getState_Submachine();

		/**
		 * The meta object literal for the '<em><b>Region</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__REGION = eINSTANCE.getState_Region();

		/**
		 * The meta object literal for the '<em><b>Entry or exit</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___ENTRY_OR_EXIT__DIAGNOSTICCHAIN_MAP = eINSTANCE.getState__Entry_or_exit__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Composite states</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___COMPOSITE_STATES__DIAGNOSTICCHAIN_MAP = eINSTANCE.getState__Composite_states__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Destinations or sources of transitions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___DESTINATIONS_OR_SOURCES_OF_TRANSITIONS__DIAGNOSTICCHAIN_MAP = eINSTANCE.getState__Destinations_or_sources_of_transitions__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Submachine or regions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___SUBMACHINE_OR_REGIONS__DIAGNOSTICCHAIN_MAP = eINSTANCE.getState__Submachine_or_regions__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Submachine states</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___SUBMACHINE_STATES__DIAGNOSTICCHAIN_MAP = eINSTANCE.getState__Submachine_states__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Is Composite</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___IS_COMPOSITE = eINSTANCE.getState__IsComposite();

		/**
		 * The meta object literal for the '<em><b>Is Orthogonal</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___IS_ORTHOGONAL = eINSTANCE.getState__IsOrthogonal();

		/**
		 * The meta object literal for the '<em><b>Is Redefinition Context Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___IS_REDEFINITION_CONTEXT_VALID__STATE = eINSTANCE.getState__IsRedefinitionContextValid__State();

		/**
		 * The meta object literal for the '<em><b>Is Simple</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___IS_SIMPLE = eINSTANCE.getState__IsSimple();

		/**
		 * The meta object literal for the '<em><b>Is Submachine State</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___IS_SUBMACHINE_STATE = eINSTANCE.getState__IsSubmachineState();

		/**
		 * The meta object literal for the '<em><b>Redefinition Context</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STATE___REDEFINITION_CONTEXT = eINSTANCE.getState__RedefinitionContext();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.ConnectionPointReferenceImpl <em>Connection Point Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.ConnectionPointReferenceImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getConnectionPointReference()
		 * @generated
		 */
		EClass CONNECTION_POINT_REFERENCE = eINSTANCE.getConnectionPointReference();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION_POINT_REFERENCE__ENTRY = eINSTANCE.getConnectionPointReference_Entry();

		/**
		 * The meta object literal for the '<em><b>Exit</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION_POINT_REFERENCE__EXIT = eINSTANCE.getConnectionPointReference_Exit();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION_POINT_REFERENCE__STATE = eINSTANCE.getConnectionPointReference_State();

		/**
		 * The meta object literal for the '<em><b>Entry pseudostates</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CONNECTION_POINT_REFERENCE___ENTRY_PSEUDOSTATES__DIAGNOSTICCHAIN_MAP = eINSTANCE.getConnectionPointReference__Entry_pseudostates__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Exit pseudostates</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation CONNECTION_POINT_REFERENCE___EXIT_PSEUDOSTATES__DIAGNOSTICCHAIN_MAP = eINSTANCE.getConnectionPointReference__Exit_pseudostates__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.TransitionImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__KIND = eINSTANCE.getTransition_Kind();

		/**
		 * The meta object literal for the '<em><b>Redefined Transition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__REDEFINED_TRANSITION = eINSTANCE.getTransition_RedefinedTransition();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__SOURCE = eINSTANCE.getTransition_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__TARGET = eINSTANCE.getTransition_Target();

		/**
		 * The meta object literal for the '<em><b>Container</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__CONTAINER = eINSTANCE.getTransition_Container();

		/**
		 * The meta object literal for the '<em><b>State is local</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___STATE_IS_LOCAL__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTransition__State_is_local__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Fork segment guards</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___FORK_SEGMENT_GUARDS__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTransition__Fork_segment_guards__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Join segment state</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___JOIN_SEGMENT_STATE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTransition__Join_segment_state__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Initial transition</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___INITIAL_TRANSITION__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTransition__Initial_transition__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Outgoing pseudostates</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___OUTGOING_PSEUDOSTATES__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTransition__Outgoing_pseudostates__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Signatures compatible</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___SIGNATURES_COMPATIBLE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTransition__Signatures_compatible__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>State is internal</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___STATE_IS_INTERNAL__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTransition__State_is_internal__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Join segment guards</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___JOIN_SEGMENT_GUARDS__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTransition__Join_segment_guards__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Fork segment state</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___FORK_SEGMENT_STATE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTransition__Fork_segment_state__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>State is external</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___STATE_IS_EXTERNAL__DIAGNOSTICCHAIN_MAP = eINSTANCE.getTransition__State_is_external__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Containing State Machine</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___CONTAINING_STATE_MACHINE = eINSTANCE.getTransition__ContainingStateMachine();

		/**
		 * The meta object literal for the '<em><b>Redefinition Context</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TRANSITION___REDEFINITION_CONTEXT = eINSTANCE.getTransition__RedefinitionContext();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.FinalStateImpl <em>Final State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.FinalStateImpl
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getFinalState()
		 * @generated
		 */
		EClass FINAL_STATE = eINSTANCE.getFinalState();

		/**
		 * The meta object literal for the '<em><b>No outgoing transitions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FINAL_STATE___NO_OUTGOING_TRANSITIONS__DIAGNOSTICCHAIN_MAP = eINSTANCE.getFinalState__No_outgoing_transitions__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>Cannot reference submachine</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FINAL_STATE___CANNOT_REFERENCE_SUBMACHINE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getFinalState__Cannot_reference_submachine__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>No exit behavior</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FINAL_STATE___NO_EXIT_BEHAVIOR__DIAGNOSTICCHAIN_MAP = eINSTANCE.getFinalState__No_exit_behavior__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>No entry behavior</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FINAL_STATE___NO_ENTRY_BEHAVIOR__DIAGNOSTICCHAIN_MAP = eINSTANCE.getFinalState__No_entry_behavior__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>No state behavior</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FINAL_STATE___NO_STATE_BEHAVIOR__DIAGNOSTICCHAIN_MAP = eINSTANCE.getFinalState__No_state_behavior__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '<em><b>No regions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FINAL_STATE___NO_REGIONS__DIAGNOSTICCHAIN_MAP = eINSTANCE.getFinalState__No_regions__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.TransitionKind <em>Transition Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.TransitionKind
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getTransitionKind()
		 * @generated
		 */
		EEnum TRANSITION_KIND = eINSTANCE.getTransitionKind();

		/**
		 * The meta object literal for the '{@link org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.PseudostateKind <em>Pseudostate Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.PseudostateKind
		 * @see org.sidiff.magicdraw.statecharts.effective.model.MagicDrawStatechartsEffective.impl.MagicDrawStatechartsEffectivePackageImpl#getPseudostateKind()
		 * @generated
		 */
		EEnum PSEUDOSTATE_KIND = eINSTANCE.getPseudostateKind();

	}

} //MagicDrawStatechartsEffectivePackage
