/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MHistory State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState#isDeep <em>Deep</em>}</li>
 * </ul>
 *
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMHistoryState()
 * @model
 * @generated
 */
public interface MHistoryState extends MAbstractState {
	/**
	 * Returns the value of the '<em><b>Deep</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deep</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deep</em>' attribute.
	 * @see #setDeep(boolean)
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMHistoryState_Deep()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	boolean isDeep();

	/**
	 * Sets the value of the '{@link de.imotep.core.behavior.de_imotep_core_behavior.MHistoryState#isDeep <em>Deep</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deep</em>' attribute.
	 * @see #isDeep()
	 * @generated
	 */
	void setDeep(boolean value);

} // MHistoryState
