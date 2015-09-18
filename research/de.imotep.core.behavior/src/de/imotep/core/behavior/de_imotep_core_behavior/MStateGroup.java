/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MState Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentRegion <em>Parent Region</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getStates <em>States</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentStateGroup <em>Parent State Group</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getStateGroups <em>State Groups</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getOnExitActions <em>On Exit Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getOnEntryActions <em>On Entry Actions</em>}</li>
 * </ul>
 *
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateGroup()
 * @model
 * @generated
 */
public interface MStateGroup extends MBehaviorEntity {
	/**
	 * Returns the value of the '<em><b>Parent Region</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getStateGroups <em>State Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Region</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Region</em>' container reference.
	 * @see #setParentRegion(MRegion)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateGroup_ParentRegion()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getStateGroups
	 * @model opposite="stateGroups" required="true" transient="false"
	 * @generated
	 */
	MRegion getParentRegion();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentRegion <em>Parent Region</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Region</em>' container reference.
	 * @see #getParentRegion()
	 * @generated
	 */
	void setParentRegion(MRegion value);

	/**
	 * Returns the value of the '<em><b>States</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState}.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getStateGroup <em>State Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>States</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateGroup_States()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState#getStateGroup
	 * @model opposite="stateGroup"
	 * @generated
	 */
	EList<MAbstractState> getStates();

	/**
	 * Returns the value of the '<em><b>Parent State Group</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getStateGroups <em>State Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent State Group</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent State Group</em>' reference.
	 * @see #setParentStateGroup(MStateGroup)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateGroup_ParentStateGroup()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getStateGroups
	 * @model opposite="stateGroups"
	 * @generated
	 */
	MStateGroup getParentStateGroup();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentStateGroup <em>Parent State Group</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent State Group</em>' reference.
	 * @see #getParentStateGroup()
	 * @generated
	 */
	void setParentStateGroup(MStateGroup value);

	/**
	 * Returns the value of the '<em><b>State Groups</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup}.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentStateGroup <em>Parent State Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State Groups</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State Groups</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateGroup_StateGroups()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup#getParentStateGroup
	 * @model opposite="parentStateGroup"
	 * @generated
	 */
	EList<MStateGroup> getStateGroups();

	/**
	 * Returns the value of the '<em><b>On Exit Actions</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MAction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Exit Actions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Exit Actions</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateGroup_OnExitActions()
	 * @model
	 * @generated
	 */
	EList<MAction> getOnExitActions();

	/**
	 * Returns the value of the '<em><b>On Entry Actions</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MAction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Entry Actions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Entry Actions</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateGroup_OnEntryActions()
	 * @model
	 * @generated
	 */
	EList<MAction> getOnEntryActions();

} // MStateGroup
