/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.ParameterMapping#getSource <em>Source</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.ParameterMapping#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getParameterMapping()
 * @model
 * @generated
 */
public interface ParameterMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(ObjectParameterBinding)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getParameterMapping_Source()
	 * @see org.sidiff.difference.asymmetric.ObjectParameterBinding#getOutgoing
	 * @model opposite="outgoing" required="true"
	 * @generated
	 */
	ObjectParameterBinding getSource();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.ParameterMapping#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(ObjectParameterBinding value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(ObjectParameterBinding)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getParameterMapping_Target()
	 * @see org.sidiff.difference.asymmetric.ObjectParameterBinding#getIncoming
	 * @model opposite="incoming" required="true"
	 * @generated
	 */
	ObjectParameterBinding getTarget();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.ParameterMapping#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(ObjectParameterBinding value);

} // ParameterMapping
