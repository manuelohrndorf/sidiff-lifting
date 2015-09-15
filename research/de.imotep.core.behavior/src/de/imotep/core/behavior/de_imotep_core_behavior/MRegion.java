/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MRegion</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentState <em>Parent State</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getStateGroups <em>State Groups</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getStates <em>States</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentStateMachine <em>Parent State Machine</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMRegion()
 * @model
 * @generated
 */
public interface MRegion extends MBehaviorEntity {
	/**
	 * Returns the value of the '<em><b>Parent State</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#getRegions <em>Regions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent State</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent State</em>' container reference.
	 * @see #setParentState(MState)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMRegion_ParentState()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MState#getRegions
	 * @model opposite="regions" transient="false"
	 * @generated
	 */
	MState getParentState();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentState <em>Parent State</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent State</em>' container reference.
	 * @see #getParentState()
	 * @generated
	 */
	void setParentState(MState value);

	/**
	 * Returns the value of the '<em><b>State Groups</b></em>' containment reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup}.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentRegion <em>Parent Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Groups</em>' containment reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMRegion_StateGroups()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentRegion
	 * @model opposite="parentRegion" containment="true"
	 * @generated
	 */
	EList<MStateGroup> getStateGroups();

	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>States</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMRegion_States()
	 * @model containment="true"
	 * @generated
	 */
	EList<MAbstractState> getStates();

	/**
	 * Returns the value of the '<em><b>Initial State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State</em>' reference.
	 * @see #setInitialState(MInitialState)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMRegion_InitialState()
	 * @model required="true"
	 * @generated
	 */
	MInitialState getInitialState();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getInitialState <em>Initial State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial State</em>' reference.
	 * @see #getInitialState()
	 * @generated
	 */
	void setInitialState(MInitialState value);

	/**
	 * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MTransition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitions</em>' containment reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMRegion_Transitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<MTransition> getTransitions();

	/**
	 * Returns the value of the '<em><b>Parent State Machine</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getRegions <em>Regions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent State Machine</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent State Machine</em>' reference.
	 * @see #setParentStateMachine(MStateMachine)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMRegion_ParentStateMachine()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getRegions
	 * @model opposite="regions" required="true"
	 * @generated
	 */
	MStateMachine getParentStateMachine();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentStateMachine <em>Parent State Machine</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent State Machine</em>' reference.
	 * @see #getParentStateMachine()
	 * @generated
	 */
	void setParentStateMachine(MStateMachine value);

} // MRegion
