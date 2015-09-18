/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MState</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#getOnEntryActions <em>On Entry Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#getRegions <em>Regions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#getDoActions <em>Do Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#getOnExitActions <em>On Exit Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#isTemporary <em>Temporary</em>}</li>
 * </ul>
 *
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMState()
 * @model
 * @generated
 */
public interface MState extends MAbstractState {
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
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMState_OnEntryActions()
	 * @model
	 * @generated
	 */
	EList<MAction> getOnEntryActions();

	/**
	 * Returns the value of the '<em><b>Regions</b></em>' containment reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion}.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentState <em>Parent State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Regions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Regions</em>' containment reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMState_Regions()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentState
	 * @model opposite="parentState" containment="true"
	 * @generated
	 */
	EList<MRegion> getRegions();

	/**
	 * Returns the value of the '<em><b>Do Actions</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MAction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Do Actions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Do Actions</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMState_DoActions()
	 * @model
	 * @generated
	 */
	EList<MAction> getDoActions();

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
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMState_OnExitActions()
	 * @model
	 * @generated
	 */
	EList<MAction> getOnExitActions();

	/**
	 * Returns the value of the '<em><b>Temporary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporary</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporary</em>' attribute.
	 * @see #setTemporary(boolean)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMState_Temporary()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	boolean isTemporary();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MState#isTemporary <em>Temporary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporary</em>' attribute.
	 * @see #isTemporary()
	 * @generated
	 */
	void setTemporary(boolean value);

} // MState
