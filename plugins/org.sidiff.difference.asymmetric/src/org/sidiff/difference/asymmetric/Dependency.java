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
 * A representation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.Dependency#getKind <em>Kind</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.Dependency#getSource <em>Source</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.Dependency#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getDependency()
 * @model abstract="true"
 * @generated
 */
public interface Dependency extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(OperationInvocation)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getDependency_Source()
	 * @model required="true" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	OperationInvocation getSource();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.Dependency#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(OperationInvocation value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(OperationInvocation)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getDependency_Target()
	 * @model required="true" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	OperationInvocation getTarget();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.Dependency#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(OperationInvocation value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.sidiff.difference.asymmetric.DependencyKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.sidiff.difference.asymmetric.DependencyKind
	 * @see #setKind(DependencyKind)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getDependency_Kind()
	 * @model
	 * @generated
	 */
	DependencyKind getKind();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.Dependency#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.sidiff.difference.asymmetric.DependencyKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(DependencyKind value);

} // Dependency
