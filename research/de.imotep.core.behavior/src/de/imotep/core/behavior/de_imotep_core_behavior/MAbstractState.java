/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MAbstract State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getStateGroup <em>State Group</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getParentRegion <em>Parent Region</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getOutgoingTransitions <em>Outgoing Transitions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getIncomingTransitions <em>Incoming Transitions</em>}</li>
 * </ul>
 *
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMAbstractState()
 * @model abstract="true"
 * @generated
 */
public interface MAbstractState extends MBehaviorEntity {
	/**
	 * Returns the value of the '<em><b>State Group</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Group</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Group</em>' reference.
	 * @see #setStateGroup(MStateGroup)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMAbstractState_StateGroup()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getStates
	 * @model opposite="states"
	 * @generated
	 */
	MStateGroup getStateGroup();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getStateGroup <em>State Group</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State Group</em>' reference.
	 * @see #getStateGroup()
	 * @generated
	 */
	void setStateGroup(MStateGroup value);

	/**
	 * Returns the value of the '<em><b>Parent Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Region</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Region</em>' reference.
	 * @see #setParentRegion(MRegion)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMAbstractState_ParentRegion()
	 * @model required="true"
	 * @generated
	 */
	MRegion getParentRegion();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getParentRegion <em>Parent Region</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Region</em>' reference.
	 * @see #getParentRegion()
	 * @generated
	 */
	void setParentRegion(MRegion value);

	/**
	 * Returns the value of the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition}.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getSourceState <em>Source State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Transitions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Transitions</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMAbstractState_OutgoingTransitions()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getSourceState
	 * @model opposite="sourceState"
	 * @generated
	 */
	EList<MTransition> getOutgoingTransitions();

	/**
	 * Returns the value of the '<em><b>Incoming Transitions</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition}.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getTargetState <em>Target State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Transitions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Transitions</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMAbstractState_IncomingTransitions()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MTransition#getTargetState
	 * @model opposite="targetState"
	 * @generated
	 */
	EList<MTransition> getIncomingTransitions();

} // MAbstractState
