/**
 */
package org.sidiff.difference.asymmetric;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dependency Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.DependencyContainer#getSource <em>Source</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.DependencyContainer#getTarget <em>Target</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.DependencyContainer#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 *
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getDependencyContainer()
 * @model
 * @generated
 */
public interface DependencyContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.asymmetric.OperationInvocation#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(OperationInvocation)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getDependencyContainer_Source()
	 * @see org.sidiff.difference.asymmetric.OperationInvocation#getOutgoing
	 * @model opposite="outgoing" required="true"
	 * @generated
	 */
	OperationInvocation getSource();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.DependencyContainer#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(OperationInvocation value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.asymmetric.OperationInvocation#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(OperationInvocation)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getDependencyContainer_Target()
	 * @see org.sidiff.difference.asymmetric.OperationInvocation#getIncoming
	 * @model opposite="incoming" required="true"
	 * @generated
	 */
	OperationInvocation getTarget();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.DependencyContainer#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(OperationInvocation value);

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.asymmetric.Dependency}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependencies</em>' containment reference list.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getDependencyContainer_Dependencies()
	 * @model containment="true"
	 * @generated
	 */
	EList<Dependency> getDependencies();

} // DependencyContainer
