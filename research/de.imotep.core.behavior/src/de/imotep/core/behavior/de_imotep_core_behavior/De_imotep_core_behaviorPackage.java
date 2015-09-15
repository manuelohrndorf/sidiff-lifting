/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;

import de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorFactory
 * @model kind="package"
 * @generated
 */
public interface De_imotep_core_behaviorPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "de_imotep_core_behavior";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.moflon.org.de_imotep_core_behavior";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "de_imotep_core_behavior";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	De_imotep_core_behaviorPackage eINSTANCE = de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MBehaviorEntityImpl <em>MBehavior Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MBehaviorEntityImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMBehaviorEntity()
	 * @generated
	 */
	int MBEHAVIOR_ENTITY = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBEHAVIOR_ENTITY__ID = De_imotep_core_datamodelPackage.MNAMED_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBEHAVIOR_ENTITY__NAME = De_imotep_core_datamodelPackage.MNAMED_ENTITY__NAME;

	/**
	 * The number of structural features of the '<em>MBehavior Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBEHAVIOR_ENTITY_FEATURE_COUNT = De_imotep_core_datamodelPackage.MNAMED_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>MBehavior Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MBEHAVIOR_ENTITY_OPERATION_COUNT = De_imotep_core_datamodelPackage.MNAMED_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl <em>MState Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMStateMachine()
	 * @generated
	 */
	int MSTATE_MACHINE = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE__ID = MBEHAVIOR_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE__NAME = MBEHAVIOR_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Regions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE__REGIONS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE__ACTIONS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE__ATTRIBUTES = MBEHAVIOR_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Root Region</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE__ROOT_REGION = MBEHAVIOR_ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Guards</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE__GUARDS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Events</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE__EVENTS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE__POSITION = MBEHAVIOR_ENTITY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE__TYPE = MBEHAVIOR_ENTITY_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>MState Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_FEATURE_COUNT = MBEHAVIOR_ENTITY_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>MState Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_OPERATION_COUNT = MBEHAVIOR_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MAbstractStateImpl <em>MAbstract State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MAbstractStateImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMAbstractState()
	 * @generated
	 */
	int MABSTRACT_STATE = 16;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MABSTRACT_STATE__ID = MBEHAVIOR_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MABSTRACT_STATE__NAME = MBEHAVIOR_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>State Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MABSTRACT_STATE__STATE_GROUP = MBEHAVIOR_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MABSTRACT_STATE__PARENT_REGION = MBEHAVIOR_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MABSTRACT_STATE__OUTGOING_TRANSITIONS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MABSTRACT_STATE__INCOMING_TRANSITIONS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>MAbstract State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MABSTRACT_STATE_FEATURE_COUNT = MBEHAVIOR_ENTITY_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>MAbstract State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MABSTRACT_STATE_OPERATION_COUNT = MBEHAVIOR_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MFinalStateImpl <em>MFinal State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MFinalStateImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMFinalState()
	 * @generated
	 */
	int MFINAL_STATE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MFINAL_STATE__ID = MABSTRACT_STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MFINAL_STATE__NAME = MABSTRACT_STATE__NAME;

	/**
	 * The feature id for the '<em><b>State Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MFINAL_STATE__STATE_GROUP = MABSTRACT_STATE__STATE_GROUP;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MFINAL_STATE__PARENT_REGION = MABSTRACT_STATE__PARENT_REGION;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MFINAL_STATE__OUTGOING_TRANSITIONS = MABSTRACT_STATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MFINAL_STATE__INCOMING_TRANSITIONS = MABSTRACT_STATE__INCOMING_TRANSITIONS;

	/**
	 * The number of structural features of the '<em>MFinal State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MFINAL_STATE_FEATURE_COUNT = MABSTRACT_STATE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>MFinal State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MFINAL_STATE_OPERATION_COUNT = MABSTRACT_STATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateImpl <em>MState</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMState()
	 * @generated
	 */
	int MSTATE = 8;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__ID = MABSTRACT_STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__NAME = MABSTRACT_STATE__NAME;

	/**
	 * The feature id for the '<em><b>State Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__STATE_GROUP = MABSTRACT_STATE__STATE_GROUP;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__PARENT_REGION = MABSTRACT_STATE__PARENT_REGION;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__OUTGOING_TRANSITIONS = MABSTRACT_STATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__INCOMING_TRANSITIONS = MABSTRACT_STATE__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>On Entry Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__ON_ENTRY_ACTIONS = MABSTRACT_STATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Regions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__REGIONS = MABSTRACT_STATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Do Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__DO_ACTIONS = MABSTRACT_STATE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>On Exit Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__ON_EXIT_ACTIONS = MABSTRACT_STATE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Temporary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE__TEMPORARY = MABSTRACT_STATE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>MState</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_FEATURE_COUNT = MABSTRACT_STATE_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>MState</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_OPERATION_COUNT = MABSTRACT_STATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MErrorStateImpl <em>MError State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MErrorStateImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMErrorState()
	 * @generated
	 */
	int MERROR_STATE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__ID = MSTATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__NAME = MSTATE__NAME;

	/**
	 * The feature id for the '<em><b>State Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__STATE_GROUP = MSTATE__STATE_GROUP;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__PARENT_REGION = MSTATE__PARENT_REGION;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__OUTGOING_TRANSITIONS = MSTATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__INCOMING_TRANSITIONS = MSTATE__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>On Entry Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__ON_ENTRY_ACTIONS = MSTATE__ON_ENTRY_ACTIONS;

	/**
	 * The feature id for the '<em><b>Regions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__REGIONS = MSTATE__REGIONS;

	/**
	 * The feature id for the '<em><b>Do Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__DO_ACTIONS = MSTATE__DO_ACTIONS;

	/**
	 * The feature id for the '<em><b>On Exit Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__ON_EXIT_ACTIONS = MSTATE__ON_EXIT_ACTIONS;

	/**
	 * The feature id for the '<em><b>Temporary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE__TEMPORARY = MSTATE__TEMPORARY;

	/**
	 * The number of structural features of the '<em>MError State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE_FEATURE_COUNT = MSTATE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>MError State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_STATE_OPERATION_COUNT = MSTATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineStateImpl <em>MState Machine State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineStateImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMStateMachineState()
	 * @generated
	 */
	int MSTATE_MACHINE_STATE = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_STATE__ID = MABSTRACT_STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_STATE__NAME = MABSTRACT_STATE__NAME;

	/**
	 * The feature id for the '<em><b>State Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_STATE__STATE_GROUP = MABSTRACT_STATE__STATE_GROUP;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_STATE__PARENT_REGION = MABSTRACT_STATE__PARENT_REGION;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_STATE__OUTGOING_TRANSITIONS = MABSTRACT_STATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_STATE__INCOMING_TRANSITIONS = MABSTRACT_STATE__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>State Machine</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_STATE__STATE_MACHINE = MABSTRACT_STATE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>MState Machine State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_STATE_FEATURE_COUNT = MABSTRACT_STATE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>MState Machine State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_MACHINE_STATE_OPERATION_COUNT = MABSTRACT_STATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTerminateStateImpl <em>MTerminate State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MTerminateStateImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMTerminateState()
	 * @generated
	 */
	int MTERMINATE_STATE = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTERMINATE_STATE__ID = MABSTRACT_STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTERMINATE_STATE__NAME = MABSTRACT_STATE__NAME;

	/**
	 * The feature id for the '<em><b>State Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTERMINATE_STATE__STATE_GROUP = MABSTRACT_STATE__STATE_GROUP;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTERMINATE_STATE__PARENT_REGION = MABSTRACT_STATE__PARENT_REGION;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTERMINATE_STATE__OUTGOING_TRANSITIONS = MABSTRACT_STATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTERMINATE_STATE__INCOMING_TRANSITIONS = MABSTRACT_STATE__INCOMING_TRANSITIONS;

	/**
	 * The number of structural features of the '<em>MTerminate State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTERMINATE_STATE_FEATURE_COUNT = MABSTRACT_STATE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>MTerminate State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTERMINATE_STATE_OPERATION_COUNT = MABSTRACT_STATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl <em>MTransition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMTransition()
	 * @generated
	 */
	int MTRANSITION = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION__ID = MBEHAVIOR_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION__NAME = MBEHAVIOR_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Source State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION__SOURCE_STATE = MBEHAVIOR_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION__TARGET_STATE = MBEHAVIOR_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION__GUARD = MBEHAVIOR_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION__EVENT = MBEHAVIOR_ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION__PARENT_REGION = MBEHAVIOR_ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION__ACTIONS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION__INTERNAL = MBEHAVIOR_ENTITY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION__PRIORITY = MBEHAVIOR_ENTITY_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>MTransition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION_FEATURE_COUNT = MBEHAVIOR_ENTITY_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>MTransition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MTRANSITION_OPERATION_COUNT = MBEHAVIOR_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MInitialStateImpl <em>MInitial State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MInitialStateImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMInitialState()
	 * @generated
	 */
	int MINITIAL_STATE = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINITIAL_STATE__ID = MABSTRACT_STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINITIAL_STATE__NAME = MABSTRACT_STATE__NAME;

	/**
	 * The feature id for the '<em><b>State Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINITIAL_STATE__STATE_GROUP = MABSTRACT_STATE__STATE_GROUP;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINITIAL_STATE__PARENT_REGION = MABSTRACT_STATE__PARENT_REGION;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINITIAL_STATE__OUTGOING_TRANSITIONS = MABSTRACT_STATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINITIAL_STATE__INCOMING_TRANSITIONS = MABSTRACT_STATE__INCOMING_TRANSITIONS;

	/**
	 * The number of structural features of the '<em>MInitial State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINITIAL_STATE_FEATURE_COUNT = MABSTRACT_STATE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>MInitial State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MINITIAL_STATE_OPERATION_COUNT = MABSTRACT_STATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MEventImpl <em>MEvent</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MEventImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMEvent()
	 * @generated
	 */
	int MEVENT = 9;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEVENT__ID = MBEHAVIOR_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEVENT__NAME = MBEHAVIOR_ENTITY__NAME;

	/**
	 * The number of structural features of the '<em>MEvent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEVENT_FEATURE_COUNT = MBEHAVIOR_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>MEvent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEVENT_OPERATION_COUNT = MBEHAVIOR_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MHistoryStateImpl <em>MHistory State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MHistoryStateImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMHistoryState()
	 * @generated
	 */
	int MHISTORY_STATE = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MHISTORY_STATE__ID = MABSTRACT_STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MHISTORY_STATE__NAME = MABSTRACT_STATE__NAME;

	/**
	 * The feature id for the '<em><b>State Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MHISTORY_STATE__STATE_GROUP = MABSTRACT_STATE__STATE_GROUP;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MHISTORY_STATE__PARENT_REGION = MABSTRACT_STATE__PARENT_REGION;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MHISTORY_STATE__OUTGOING_TRANSITIONS = MABSTRACT_STATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MHISTORY_STATE__INCOMING_TRANSITIONS = MABSTRACT_STATE__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Deep</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MHISTORY_STATE__DEEP = MABSTRACT_STATE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>MHistory State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MHISTORY_STATE_FEATURE_COUNT = MABSTRACT_STATE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>MHistory State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MHISTORY_STATE_OPERATION_COUNT = MABSTRACT_STATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MGuardImpl <em>MGuard</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MGuardImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMGuard()
	 * @generated
	 */
	int MGUARD = 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MGUARD__ID = MBEHAVIOR_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MGUARD__NAME = MBEHAVIOR_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MGUARD__CONDITIONS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>MGuard</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MGUARD_FEATURE_COUNT = MBEHAVIOR_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>MGuard</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MGUARD_OPERATION_COUNT = MBEHAVIOR_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MRegionImpl <em>MRegion</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MRegionImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMRegion()
	 * @generated
	 */
	int MREGION = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MREGION__ID = MBEHAVIOR_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MREGION__NAME = MBEHAVIOR_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Parent State</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MREGION__PARENT_STATE = MBEHAVIOR_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>State Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MREGION__STATE_GROUPS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MREGION__STATES = MBEHAVIOR_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MREGION__INITIAL_STATE = MBEHAVIOR_ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MREGION__TRANSITIONS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Parent State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MREGION__PARENT_STATE_MACHINE = MBEHAVIOR_ENTITY_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>MRegion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MREGION_FEATURE_COUNT = MBEHAVIOR_ENTITY_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>MRegion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MREGION_OPERATION_COUNT = MBEHAVIOR_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MActionImpl <em>MAction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MActionImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMAction()
	 * @generated
	 */
	int MACTION = 13;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACTION__ID = MBEHAVIOR_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACTION__NAME = MBEHAVIOR_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACTION__EXPRESSIONS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>MAction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACTION_FEATURE_COUNT = MBEHAVIOR_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>MAction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACTION_OPERATION_COUNT = MBEHAVIOR_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MCodeFragmentImpl <em>MCode Fragment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MCodeFragmentImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMCodeFragment()
	 * @generated
	 */
	int MCODE_FRAGMENT = 14;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MCODE_FRAGMENT__ID = MBEHAVIOR_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MCODE_FRAGMENT__NAME = MBEHAVIOR_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Cuse</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MCODE_FRAGMENT__CUSE = MBEHAVIOR_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Puse</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MCODE_FRAGMENT__PUSE = MBEHAVIOR_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Duse</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MCODE_FRAGMENT__DUSE = MBEHAVIOR_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Used Attributes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MCODE_FRAGMENT__USED_ATTRIBUTES = MBEHAVIOR_ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MCODE_FRAGMENT__EXPRESSION = MBEHAVIOR_ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MCODE_FRAGMENT__LANGUAGE = MBEHAVIOR_ENTITY_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>MCode Fragment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MCODE_FRAGMENT_FEATURE_COUNT = MBEHAVIOR_ENTITY_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>MCode Fragment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MCODE_FRAGMENT_OPERATION_COUNT = MBEHAVIOR_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateGroupImpl <em>MState Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateGroupImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMStateGroup()
	 * @generated
	 */
	int MSTATE_GROUP = 15;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_GROUP__ID = MBEHAVIOR_ENTITY__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_GROUP__NAME = MBEHAVIOR_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_GROUP__PARENT_REGION = MBEHAVIOR_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>States</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_GROUP__STATES = MBEHAVIOR_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent State Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_GROUP__PARENT_STATE_GROUP = MBEHAVIOR_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>State Groups</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_GROUP__STATE_GROUPS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>On Exit Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_GROUP__ON_EXIT_ACTIONS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>On Entry Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_GROUP__ON_ENTRY_ACTIONS = MBEHAVIOR_ENTITY_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>MState Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_GROUP_FEATURE_COUNT = MBEHAVIOR_ENTITY_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>MState Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MSTATE_GROUP_OPERATION_COUNT = MBEHAVIOR_ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MErrorTransitionImpl <em>MError Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MErrorTransitionImpl
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMErrorTransition()
	 * @generated
	 */
	int MERROR_TRANSITION = 17;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION__ID = MTRANSITION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION__NAME = MTRANSITION__NAME;

	/**
	 * The feature id for the '<em><b>Source State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION__SOURCE_STATE = MTRANSITION__SOURCE_STATE;

	/**
	 * The feature id for the '<em><b>Target State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION__TARGET_STATE = MTRANSITION__TARGET_STATE;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION__GUARD = MTRANSITION__GUARD;

	/**
	 * The feature id for the '<em><b>Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION__EVENT = MTRANSITION__EVENT;

	/**
	 * The feature id for the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION__PARENT_REGION = MTRANSITION__PARENT_REGION;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION__ACTIONS = MTRANSITION__ACTIONS;

	/**
	 * The feature id for the '<em><b>Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION__INTERNAL = MTRANSITION__INTERNAL;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION__PRIORITY = MTRANSITION__PRIORITY;

	/**
	 * The number of structural features of the '<em>MError Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION_FEATURE_COUNT = MTRANSITION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>MError Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERROR_TRANSITION_OPERATION_COUNT = MTRANSITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MELanguages <em>ME Languages</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MELanguages
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMELanguages()
	 * @generated
	 */
	int ME_LANGUAGES = 18;


	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine <em>MState Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MState Machine</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine
	 * @generated
	 */
	EClass getMStateMachine();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getRegions <em>Regions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Regions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getRegions()
	 * @see #getMStateMachine()
	 * @generated
	 */
	EReference getMStateMachine_Regions();

	/**
	 * Returns the meta object for the containment reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getActions()
	 * @see #getMStateMachine()
	 * @generated
	 */
	EReference getMStateMachine_Actions();

	/**
	 * Returns the meta object for the containment reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getAttributes()
	 * @see #getMStateMachine()
	 * @generated
	 */
	EReference getMStateMachine_Attributes();

	/**
	 * Returns the meta object for the containment reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getRootRegion <em>Root Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root Region</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getRootRegion()
	 * @see #getMStateMachine()
	 * @generated
	 */
	EReference getMStateMachine_RootRegion();

	/**
	 * Returns the meta object for the containment reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getGuards <em>Guards</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Guards</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getGuards()
	 * @see #getMStateMachine()
	 * @generated
	 */
	EReference getMStateMachine_Guards();

	/**
	 * Returns the meta object for the containment reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getEvents <em>Events</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Events</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getEvents()
	 * @see #getMStateMachine()
	 * @generated
	 */
	EReference getMStateMachine_Events();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Position</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getPosition()
	 * @see #getMStateMachine()
	 * @generated
	 */
	EAttribute getMStateMachine_Position();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getType()
	 * @see #getMStateMachine()
	 * @generated
	 */
	EAttribute getMStateMachine_Type();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MFinalState <em>MFinal State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MFinal State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MFinalState
	 * @generated
	 */
	EClass getMFinalState();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MErrorState <em>MError State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MError State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MErrorState
	 * @generated
	 */
	EClass getMErrorState();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState <em>MState Machine State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MState Machine State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState
	 * @generated
	 */
	EClass getMStateMachineState();

	/**
	 * Returns the meta object for the containment reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState#getStateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>State Machine</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachineState#getStateMachine()
	 * @see #getMStateMachineState()
	 * @generated
	 */
	EReference getMStateMachineState_StateMachine();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTerminateState <em>MTerminate State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MTerminate State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTerminateState
	 * @generated
	 */
	EClass getMTerminateState();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition <em>MTransition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MTransition</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition
	 * @generated
	 */
	EClass getMTransition();

	/**
	 * Returns the meta object for the reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getSourceState <em>Source State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getSourceState()
	 * @see #getMTransition()
	 * @generated
	 */
	EReference getMTransition_SourceState();

	/**
	 * Returns the meta object for the reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getTargetState <em>Target State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getTargetState()
	 * @see #getMTransition()
	 * @generated
	 */
	EReference getMTransition_TargetState();

	/**
	 * Returns the meta object for the reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getGuard()
	 * @see #getMTransition()
	 * @generated
	 */
	EReference getMTransition_Guard();

	/**
	 * Returns the meta object for the reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Event</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getEvent()
	 * @see #getMTransition()
	 * @generated
	 */
	EReference getMTransition_Event();

	/**
	 * Returns the meta object for the reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getParentRegion <em>Parent Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent Region</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getParentRegion()
	 * @see #getMTransition()
	 * @generated
	 */
	EReference getMTransition_ParentRegion();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Actions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getActions()
	 * @see #getMTransition()
	 * @generated
	 */
	EReference getMTransition_Actions();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition#isInternal <em>Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Internal</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition#isInternal()
	 * @see #getMTransition()
	 * @generated
	 */
	EAttribute getMTransition_Internal();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getPriority()
	 * @see #getMTransition()
	 * @generated
	 */
	EAttribute getMTransition_Priority();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MInitialState <em>MInitial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MInitial State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MInitialState
	 * @generated
	 */
	EClass getMInitialState();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MBehaviorEntity <em>MBehavior Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MBehavior Entity</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MBehaviorEntity
	 * @generated
	 */
	EClass getMBehaviorEntity();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MState <em>MState</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MState</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MState
	 * @generated
	 */
	EClass getMState();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#getOnEntryActions <em>On Entry Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>On Entry Actions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MState#getOnEntryActions()
	 * @see #getMState()
	 * @generated
	 */
	EReference getMState_OnEntryActions();

	/**
	 * Returns the meta object for the containment reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#getRegions <em>Regions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Regions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MState#getRegions()
	 * @see #getMState()
	 * @generated
	 */
	EReference getMState_Regions();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#getDoActions <em>Do Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Do Actions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MState#getDoActions()
	 * @see #getMState()
	 * @generated
	 */
	EReference getMState_DoActions();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#getOnExitActions <em>On Exit Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>On Exit Actions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MState#getOnExitActions()
	 * @see #getMState()
	 * @generated
	 */
	EReference getMState_OnExitActions();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#isTemporary <em>Temporary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temporary</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MState#isTemporary()
	 * @see #getMState()
	 * @generated
	 */
	EAttribute getMState_Temporary();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MEvent <em>MEvent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MEvent</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MEvent
	 * @generated
	 */
	EClass getMEvent();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState <em>MHistory State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MHistory State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState
	 * @generated
	 */
	EClass getMHistoryState();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState#isDeep <em>Deep</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deep</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState#isDeep()
	 * @see #getMHistoryState()
	 * @generated
	 */
	EAttribute getMHistoryState_Deep();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MGuard <em>MGuard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MGuard</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MGuard
	 * @generated
	 */
	EClass getMGuard();

	/**
	 * Returns the meta object for the containment reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MGuard#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conditions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MGuard#getConditions()
	 * @see #getMGuard()
	 * @generated
	 */
	EReference getMGuard_Conditions();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion <em>MRegion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MRegion</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion
	 * @generated
	 */
	EClass getMRegion();

	/**
	 * Returns the meta object for the container reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentState <em>Parent State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentState()
	 * @see #getMRegion()
	 * @generated
	 */
	EReference getMRegion_ParentState();

	/**
	 * Returns the meta object for the containment reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getStateGroups <em>State Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>State Groups</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getStateGroups()
	 * @see #getMRegion()
	 * @generated
	 */
	EReference getMRegion_StateGroups();

	/**
	 * Returns the meta object for the containment reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getStates()
	 * @see #getMRegion()
	 * @generated
	 */
	EReference getMRegion_States();

	/**
	 * Returns the meta object for the reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Initial State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getInitialState()
	 * @see #getMRegion()
	 * @generated
	 */
	EReference getMRegion_InitialState();

	/**
	 * Returns the meta object for the containment reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getTransitions <em>Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transitions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getTransitions()
	 * @see #getMRegion()
	 * @generated
	 */
	EReference getMRegion_Transitions();

	/**
	 * Returns the meta object for the reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentStateMachine <em>Parent State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent State Machine</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentStateMachine()
	 * @see #getMRegion()
	 * @generated
	 */
	EReference getMRegion_ParentStateMachine();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAction <em>MAction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MAction</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MAction
	 * @generated
	 */
	EClass getMAction();

	/**
	 * Returns the meta object for the containment reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAction#getExpressions <em>Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Expressions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MAction#getExpressions()
	 * @see #getMAction()
	 * @generated
	 */
	EReference getMAction_Expressions();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment <em>MCode Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MCode Fragment</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment
	 * @generated
	 */
	EClass getMCodeFragment();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getCuse <em>Cuse</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Cuse</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getCuse()
	 * @see #getMCodeFragment()
	 * @generated
	 */
	EReference getMCodeFragment_Cuse();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getPuse <em>Puse</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Puse</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getPuse()
	 * @see #getMCodeFragment()
	 * @generated
	 */
	EReference getMCodeFragment_Puse();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getDuse <em>Duse</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Duse</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getDuse()
	 * @see #getMCodeFragment()
	 * @generated
	 */
	EReference getMCodeFragment_Duse();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getUsedAttributes <em>Used Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Used Attributes</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getUsedAttributes()
	 * @see #getMCodeFragment()
	 * @generated
	 */
	EReference getMCodeFragment_UsedAttributes();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getExpression()
	 * @see #getMCodeFragment()
	 * @generated
	 */
	EAttribute getMCodeFragment_Expression();

	/**
	 * Returns the meta object for the attribute '{@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment#getLanguage()
	 * @see #getMCodeFragment()
	 * @generated
	 */
	EAttribute getMCodeFragment_Language();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup <em>MState Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MState Group</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup
	 * @generated
	 */
	EClass getMStateGroup();

	/**
	 * Returns the meta object for the container reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentRegion <em>Parent Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Region</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentRegion()
	 * @see #getMStateGroup()
	 * @generated
	 */
	EReference getMStateGroup_ParentRegion();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>States</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getStates()
	 * @see #getMStateGroup()
	 * @generated
	 */
	EReference getMStateGroup_States();

	/**
	 * Returns the meta object for the reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentStateGroup <em>Parent State Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent State Group</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentStateGroup()
	 * @see #getMStateGroup()
	 * @generated
	 */
	EReference getMStateGroup_ParentStateGroup();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getStateGroups <em>State Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>State Groups</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getStateGroups()
	 * @see #getMStateGroup()
	 * @generated
	 */
	EReference getMStateGroup_StateGroups();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getOnExitActions <em>On Exit Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>On Exit Actions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getOnExitActions()
	 * @see #getMStateGroup()
	 * @generated
	 */
	EReference getMStateGroup_OnExitActions();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getOnEntryActions <em>On Entry Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>On Entry Actions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getOnEntryActions()
	 * @see #getMStateGroup()
	 * @generated
	 */
	EReference getMStateGroup_OnEntryActions();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState <em>MAbstract State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MAbstract State</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState
	 * @generated
	 */
	EClass getMAbstractState();

	/**
	 * Returns the meta object for the reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getStateGroup <em>State Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>State Group</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getStateGroup()
	 * @see #getMAbstractState()
	 * @generated
	 */
	EReference getMAbstractState_StateGroup();

	/**
	 * Returns the meta object for the reference '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getParentRegion <em>Parent Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent Region</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getParentRegion()
	 * @see #getMAbstractState()
	 * @generated
	 */
	EReference getMAbstractState_ParentRegion();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getOutgoingTransitions <em>Outgoing Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Transitions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getOutgoingTransitions()
	 * @see #getMAbstractState()
	 * @generated
	 */
	EReference getMAbstractState_OutgoingTransitions();

	/**
	 * Returns the meta object for the reference list '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getIncomingTransitions <em>Incoming Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Transitions</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getIncomingTransitions()
	 * @see #getMAbstractState()
	 * @generated
	 */
	EReference getMAbstractState_IncomingTransitions();

	/**
	 * Returns the meta object for class '{@link de.imotep.core.behavior.de_imotep_core_behavior.MErrorTransition <em>MError Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>MError Transition</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MErrorTransition
	 * @generated
	 */
	EClass getMErrorTransition();

	/**
	 * Returns the meta object for enum '{@link de.imotep.core.behavior.de_imotep_core_behavior.MELanguages <em>ME Languages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ME Languages</em>'.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MELanguages
	 * @generated
	 */
	EEnum getMELanguages();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	De_imotep_core_behaviorFactory getDe_imotep_core_behaviorFactory();

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
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl <em>MState Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMStateMachine()
		 * @generated
		 */
		EClass MSTATE_MACHINE = eINSTANCE.getMStateMachine();

		/**
		 * The meta object literal for the '<em><b>Regions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_MACHINE__REGIONS = eINSTANCE.getMStateMachine_Regions();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_MACHINE__ACTIONS = eINSTANCE.getMStateMachine_Actions();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_MACHINE__ATTRIBUTES = eINSTANCE.getMStateMachine_Attributes();

		/**
		 * The meta object literal for the '<em><b>Root Region</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_MACHINE__ROOT_REGION = eINSTANCE.getMStateMachine_RootRegion();

		/**
		 * The meta object literal for the '<em><b>Guards</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_MACHINE__GUARDS = eINSTANCE.getMStateMachine_Guards();

		/**
		 * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_MACHINE__EVENTS = eINSTANCE.getMStateMachine_Events();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MSTATE_MACHINE__POSITION = eINSTANCE.getMStateMachine_Position();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MSTATE_MACHINE__TYPE = eINSTANCE.getMStateMachine_Type();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MFinalStateImpl <em>MFinal State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MFinalStateImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMFinalState()
		 * @generated
		 */
		EClass MFINAL_STATE = eINSTANCE.getMFinalState();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MErrorStateImpl <em>MError State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MErrorStateImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMErrorState()
		 * @generated
		 */
		EClass MERROR_STATE = eINSTANCE.getMErrorState();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineStateImpl <em>MState Machine State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineStateImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMStateMachineState()
		 * @generated
		 */
		EClass MSTATE_MACHINE_STATE = eINSTANCE.getMStateMachineState();

		/**
		 * The meta object literal for the '<em><b>State Machine</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_MACHINE_STATE__STATE_MACHINE = eINSTANCE.getMStateMachineState_StateMachine();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTerminateStateImpl <em>MTerminate State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MTerminateStateImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMTerminateState()
		 * @generated
		 */
		EClass MTERMINATE_STATE = eINSTANCE.getMTerminateState();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl <em>MTransition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMTransition()
		 * @generated
		 */
		EClass MTRANSITION = eINSTANCE.getMTransition();

		/**
		 * The meta object literal for the '<em><b>Source State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MTRANSITION__SOURCE_STATE = eINSTANCE.getMTransition_SourceState();

		/**
		 * The meta object literal for the '<em><b>Target State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MTRANSITION__TARGET_STATE = eINSTANCE.getMTransition_TargetState();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MTRANSITION__GUARD = eINSTANCE.getMTransition_Guard();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MTRANSITION__EVENT = eINSTANCE.getMTransition_Event();

		/**
		 * The meta object literal for the '<em><b>Parent Region</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MTRANSITION__PARENT_REGION = eINSTANCE.getMTransition_ParentRegion();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MTRANSITION__ACTIONS = eINSTANCE.getMTransition_Actions();

		/**
		 * The meta object literal for the '<em><b>Internal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MTRANSITION__INTERNAL = eINSTANCE.getMTransition_Internal();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MTRANSITION__PRIORITY = eINSTANCE.getMTransition_Priority();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MInitialStateImpl <em>MInitial State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MInitialStateImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMInitialState()
		 * @generated
		 */
		EClass MINITIAL_STATE = eINSTANCE.getMInitialState();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MBehaviorEntityImpl <em>MBehavior Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MBehaviorEntityImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMBehaviorEntity()
		 * @generated
		 */
		EClass MBEHAVIOR_ENTITY = eINSTANCE.getMBehaviorEntity();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateImpl <em>MState</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMState()
		 * @generated
		 */
		EClass MSTATE = eINSTANCE.getMState();

		/**
		 * The meta object literal for the '<em><b>On Entry Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE__ON_ENTRY_ACTIONS = eINSTANCE.getMState_OnEntryActions();

		/**
		 * The meta object literal for the '<em><b>Regions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE__REGIONS = eINSTANCE.getMState_Regions();

		/**
		 * The meta object literal for the '<em><b>Do Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE__DO_ACTIONS = eINSTANCE.getMState_DoActions();

		/**
		 * The meta object literal for the '<em><b>On Exit Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE__ON_EXIT_ACTIONS = eINSTANCE.getMState_OnExitActions();

		/**
		 * The meta object literal for the '<em><b>Temporary</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MSTATE__TEMPORARY = eINSTANCE.getMState_Temporary();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MEventImpl <em>MEvent</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MEventImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMEvent()
		 * @generated
		 */
		EClass MEVENT = eINSTANCE.getMEvent();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MHistoryStateImpl <em>MHistory State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MHistoryStateImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMHistoryState()
		 * @generated
		 */
		EClass MHISTORY_STATE = eINSTANCE.getMHistoryState();

		/**
		 * The meta object literal for the '<em><b>Deep</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MHISTORY_STATE__DEEP = eINSTANCE.getMHistoryState_Deep();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MGuardImpl <em>MGuard</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MGuardImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMGuard()
		 * @generated
		 */
		EClass MGUARD = eINSTANCE.getMGuard();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MGUARD__CONDITIONS = eINSTANCE.getMGuard_Conditions();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MRegionImpl <em>MRegion</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MRegionImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMRegion()
		 * @generated
		 */
		EClass MREGION = eINSTANCE.getMRegion();

		/**
		 * The meta object literal for the '<em><b>Parent State</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MREGION__PARENT_STATE = eINSTANCE.getMRegion_ParentState();

		/**
		 * The meta object literal for the '<em><b>State Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MREGION__STATE_GROUPS = eINSTANCE.getMRegion_StateGroups();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MREGION__STATES = eINSTANCE.getMRegion_States();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MREGION__INITIAL_STATE = eINSTANCE.getMRegion_InitialState();

		/**
		 * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MREGION__TRANSITIONS = eINSTANCE.getMRegion_Transitions();

		/**
		 * The meta object literal for the '<em><b>Parent State Machine</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MREGION__PARENT_STATE_MACHINE = eINSTANCE.getMRegion_ParentStateMachine();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MActionImpl <em>MAction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MActionImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMAction()
		 * @generated
		 */
		EClass MACTION = eINSTANCE.getMAction();

		/**
		 * The meta object literal for the '<em><b>Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MACTION__EXPRESSIONS = eINSTANCE.getMAction_Expressions();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MCodeFragmentImpl <em>MCode Fragment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MCodeFragmentImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMCodeFragment()
		 * @generated
		 */
		EClass MCODE_FRAGMENT = eINSTANCE.getMCodeFragment();

		/**
		 * The meta object literal for the '<em><b>Cuse</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MCODE_FRAGMENT__CUSE = eINSTANCE.getMCodeFragment_Cuse();

		/**
		 * The meta object literal for the '<em><b>Puse</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MCODE_FRAGMENT__PUSE = eINSTANCE.getMCodeFragment_Puse();

		/**
		 * The meta object literal for the '<em><b>Duse</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MCODE_FRAGMENT__DUSE = eINSTANCE.getMCodeFragment_Duse();

		/**
		 * The meta object literal for the '<em><b>Used Attributes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MCODE_FRAGMENT__USED_ATTRIBUTES = eINSTANCE.getMCodeFragment_UsedAttributes();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MCODE_FRAGMENT__EXPRESSION = eINSTANCE.getMCodeFragment_Expression();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MCODE_FRAGMENT__LANGUAGE = eINSTANCE.getMCodeFragment_Language();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateGroupImpl <em>MState Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateGroupImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMStateGroup()
		 * @generated
		 */
		EClass MSTATE_GROUP = eINSTANCE.getMStateGroup();

		/**
		 * The meta object literal for the '<em><b>Parent Region</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_GROUP__PARENT_REGION = eINSTANCE.getMStateGroup_ParentRegion();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_GROUP__STATES = eINSTANCE.getMStateGroup_States();

		/**
		 * The meta object literal for the '<em><b>Parent State Group</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_GROUP__PARENT_STATE_GROUP = eINSTANCE.getMStateGroup_ParentStateGroup();

		/**
		 * The meta object literal for the '<em><b>State Groups</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_GROUP__STATE_GROUPS = eINSTANCE.getMStateGroup_StateGroups();

		/**
		 * The meta object literal for the '<em><b>On Exit Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_GROUP__ON_EXIT_ACTIONS = eINSTANCE.getMStateGroup_OnExitActions();

		/**
		 * The meta object literal for the '<em><b>On Entry Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MSTATE_GROUP__ON_ENTRY_ACTIONS = eINSTANCE.getMStateGroup_OnEntryActions();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MAbstractStateImpl <em>MAbstract State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MAbstractStateImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMAbstractState()
		 * @generated
		 */
		EClass MABSTRACT_STATE = eINSTANCE.getMAbstractState();

		/**
		 * The meta object literal for the '<em><b>State Group</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MABSTRACT_STATE__STATE_GROUP = eINSTANCE.getMAbstractState_StateGroup();

		/**
		 * The meta object literal for the '<em><b>Parent Region</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MABSTRACT_STATE__PARENT_REGION = eINSTANCE.getMAbstractState_ParentRegion();

		/**
		 * The meta object literal for the '<em><b>Outgoing Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MABSTRACT_STATE__OUTGOING_TRANSITIONS = eINSTANCE.getMAbstractState_OutgoingTransitions();

		/**
		 * The meta object literal for the '<em><b>Incoming Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MABSTRACT_STATE__INCOMING_TRANSITIONS = eINSTANCE.getMAbstractState_IncomingTransitions();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MErrorTransitionImpl <em>MError Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.MErrorTransitionImpl
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMErrorTransition()
		 * @generated
		 */
		EClass MERROR_TRANSITION = eINSTANCE.getMErrorTransition();

		/**
		 * The meta object literal for the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MELanguages <em>ME Languages</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.MELanguages
		 * @see de.imotep.core.behavior.de_imotep_core_behavior.impl.De_imotep_core_behaviorPackageImpl#getMELanguages()
		 * @generated
		 */
		EEnum ME_LANGUAGES = eINSTANCE.getMELanguages();

	}

} //De_imotep_core_behaviorPackage
