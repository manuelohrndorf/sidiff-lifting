/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;

import de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MState Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getRegions <em>Regions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getActions <em>Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getRootRegion <em>Root Region</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getGuards <em>Guards</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getEvents <em>Events</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getPosition <em>Position</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachine()
 * @model
 * @generated
 */
public interface MStateMachine extends MBehaviorEntity {
	/**
	 * Returns the value of the '<em><b>Regions</b></em>' reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion}.
	 * It is bidirectional and its opposite is '{@link de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentStateMachine <em>Parent State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Regions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Regions</em>' reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachine_Regions()
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.MRegion#getParentStateMachine
	 * @model opposite="parentStateMachine"
	 * @generated
	 */
	EList<MRegion> getRegions();

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MAction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachine_Actions()
	 * @model containment="true"
	 * @generated
	 */
	EList<MAction> getActions();

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachine_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<MAttribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Root Region</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Region</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Region</em>' containment reference.
	 * @see #setRootRegion(MRegion)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachine_RootRegion()
	 * @model containment="true"
	 * @generated
	 */
	MRegion getRootRegion();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getRootRegion <em>Root Region</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Region</em>' containment reference.
	 * @see #getRootRegion()
	 * @generated
	 */
	void setRootRegion(MRegion value);

	/**
	 * Returns the value of the '<em><b>Guards</b></em>' containment reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MGuard}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guards</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guards</em>' containment reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachine_Guards()
	 * @model containment="true"
	 * @generated
	 */
	EList<MGuard> getGuards();

	/**
	 * Returns the value of the '<em><b>Events</b></em>' containment reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MEvent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Events</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Events</em>' containment reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachine_Events()
	 * @model containment="true"
	 * @generated
	 */
	EList<MEvent> getEvents();

	/**
	 * Returns the value of the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Position</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' attribute.
	 * @see #setPosition(String)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachine_Position()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getPosition();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getPosition <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' attribute.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMStateMachine_Type()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // MStateMachine
