/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object Parameter Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getActualA <em>Actual A</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getActualB <em>Actual B</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getIncoming <em>Incoming</em>}</li>
 * </ul>
 *
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getObjectParameterBinding()
 * @model
 * @generated
 */
public interface ObjectParameterBinding extends ParameterBinding {
	/**
	 * Returns the value of the '<em><b>Actual A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actual A</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actual A</em>' reference.
	 * @see #setActualA(EObject)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getObjectParameterBinding_ActualA()
	 * @model
	 * @generated
	 */
	EObject getActualA();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getActualA <em>Actual A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actual A</em>' reference.
	 * @see #getActualA()
	 * @generated
	 */
	void setActualA(EObject value);

	/**
	 * Returns the value of the '<em><b>Actual B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actual B</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actual B</em>' reference.
	 * @see #setActualB(EObject)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getObjectParameterBinding_ActualB()
	 * @model
	 * @generated
	 */
	EObject getActualB();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getActualB <em>Actual B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actual B</em>' reference.
	 * @see #getActualB()
	 * @generated
	 */
	void setActualB(EObject value);

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.difference.asymmetric.ParameterMapping}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.asymmetric.ParameterMapping#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getObjectParameterBinding_Outgoing()
	 * @see org.sidiff.difference.asymmetric.ParameterMapping#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<ParameterMapping> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.asymmetric.ParameterMapping#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference.
	 * @see #setIncoming(ParameterMapping)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getObjectParameterBinding_Incoming()
	 * @see org.sidiff.difference.asymmetric.ParameterMapping#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	ParameterMapping getIncoming();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getIncoming <em>Incoming</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Incoming</em>' reference.
	 * @see #getIncoming()
	 * @generated
	 */
	void setIncoming(ParameterMapping value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isMappingTarget();

} // ObjectParameterBinding
