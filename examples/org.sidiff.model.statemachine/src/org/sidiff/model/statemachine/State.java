/**
 */
package org.sidiff.model.statemachine;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.model.statemachine.State#isIsComposite <em>Is Composite</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.State#getRegions <em>Regions</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.State#getDoAction <em>Do Action</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.State#getEntryAction <em>Entry Action</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.State#getExitAction <em>Exit Action</em>}</li>
 * </ul>
 *
 * @see org.sidiff.model.statemachine.StatemachinePackage#getState()
 * @model
 * @generated
 */
public interface State extends Vertex {
	/**
	 * Returns the value of the '<em><b>Is Composite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Composite</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Composite</em>' attribute.
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getState_IsComposite()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	boolean isIsComposite();

	/**
	 * Returns the value of the '<em><b>Regions</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.model.statemachine.Region}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Regions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Regions</em>' containment reference list.
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getState_Regions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Region> getRegions();

	/**
	 * Returns the value of the '<em><b>Do Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Do Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Do Action</em>' containment reference.
	 * @see #setDoAction(Action)
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getState_DoAction()
	 * @model containment="true"
	 * @generated
	 */
	Action getDoAction();

	/**
	 * Sets the value of the '{@link org.sidiff.model.statemachine.State#getDoAction <em>Do Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Do Action</em>' containment reference.
	 * @see #getDoAction()
	 * @generated
	 */
	void setDoAction(Action value);

	/**
	 * Returns the value of the '<em><b>Entry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Action</em>' containment reference.
	 * @see #setEntryAction(Action)
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getState_EntryAction()
	 * @model containment="true"
	 * @generated
	 */
	Action getEntryAction();

	/**
	 * Sets the value of the '{@link org.sidiff.model.statemachine.State#getEntryAction <em>Entry Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry Action</em>' containment reference.
	 * @see #getEntryAction()
	 * @generated
	 */
	void setEntryAction(Action value);

	/**
	 * Returns the value of the '<em><b>Exit Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exit Action</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exit Action</em>' containment reference.
	 * @see #setExitAction(Action)
	 * @see org.sidiff.model.statemachine.StatemachinePackage#getState_ExitAction()
	 * @model containment="true"
	 * @generated
	 */
	Action getExitAction();

	/**
	 * Sets the value of the '{@link org.sidiff.model.statemachine.State#getExitAction <em>Exit Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exit Action</em>' containment reference.
	 * @see #getExitAction()
	 * @generated
	 */
	void setExitAction(Action value);

} // State
