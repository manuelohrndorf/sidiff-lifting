/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MAction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.MAction#getExpressions <em>Expressions</em>}</li>
 * </ul>
 *
 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMAction()
 * @model
 * @generated
 */
public interface MAction extends MBehaviorEntity {
	/**
	 * Returns the value of the '<em><b>Expressions</b></em>' containment reference list.
	 * The list contents are of type {@link de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expressions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expressions</em>' containment reference list.
	 * @see de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage#getMAction_Expressions()
	 * @model containment="true"
	 * @generated
	 */
	EList<MCodeFragment> getExpressions();

} // MAction
