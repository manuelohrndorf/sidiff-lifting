/**
 */
package org.sidiff.difference.asymmetric;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.EdgeDependency#getSrcObject <em>Src Object</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.EdgeDependency#getTgtObject <em>Tgt Object</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.EdgeDependency#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getEdgeDependency()
 * @model
 * @generated
 */
public interface EdgeDependency extends Dependency {
	/**
	 * Returns the value of the '<em><b>Src Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Src Object</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src Object</em>' reference.
	 * @see #setSrcObject(EObject)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getEdgeDependency_SrcObject()
	 * @model
	 * @generated
	 */
	EObject getSrcObject();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.EdgeDependency#getSrcObject <em>Src Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src Object</em>' reference.
	 * @see #getSrcObject()
	 * @generated
	 */
	void setSrcObject(EObject value);

	/**
	 * Returns the value of the '<em><b>Tgt Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tgt Object</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgt Object</em>' reference.
	 * @see #setTgtObject(EObject)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getEdgeDependency_TgtObject()
	 * @model
	 * @generated
	 */
	EObject getTgtObject();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.EdgeDependency#getTgtObject <em>Tgt Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tgt Object</em>' reference.
	 * @see #getTgtObject()
	 * @generated
	 */
	void setTgtObject(EObject value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EReference)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getEdgeDependency_Type()
	 * @model
	 * @generated
	 */
	EReference getType();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.EdgeDependency#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EReference value);

} // EdgeDependency
