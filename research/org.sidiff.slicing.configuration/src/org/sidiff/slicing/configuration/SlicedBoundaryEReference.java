/**
 */
package org.sidiff.slicing.configuration;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sliced Boundary EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getSource <em>Source</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getTarget <em>Target</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getSrcType <em>Src Type</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getTgtType <em>Tgt Type</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedBoundaryEReference()
 * @model
 * @generated
 */
public interface SlicedBoundaryEReference extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.slicing.configuration.SlicedEClass#getOutgoings <em>Outgoings</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' container reference.
	 * @see #setSource(SlicedEClass)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedBoundaryEReference_Source()
	 * @see org.sidiff.slicing.configuration.SlicedEClass#getOutgoings
	 * @model opposite="outgoings" transient="false"
	 * @generated
	 */
	SlicedEClass getSource();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getSource <em>Source</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' container reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(SlicedEClass value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.slicing.configuration.SlicedEClass#getIncomings <em>Incomings</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' container reference.
	 * @see #setTarget(SlicedEClass)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedBoundaryEReference_Target()
	 * @see org.sidiff.slicing.configuration.SlicedEClass#getIncomings
	 * @model opposite="incomings" transient="false"
	 * @generated
	 */
	SlicedEClass getTarget();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getTarget <em>Target</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' container reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(SlicedEClass value);

	/**
	 * Returns the value of the '<em><b>Src Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Src Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src Type</em>' reference.
	 * @see #setSrcType(EClass)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedBoundaryEReference_SrcType()
	 * @model required="true" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	EClass getSrcType();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getSrcType <em>Src Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src Type</em>' reference.
	 * @see #getSrcType()
	 * @generated
	 */
	void setSrcType(EClass value);

	/**
	 * Returns the value of the '<em><b>Tgt Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tgt Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgt Type</em>' reference.
	 * @see #setTgtType(EClass)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedBoundaryEReference_TgtType()
	 * @model required="true" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	EClass getTgtType();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getTgtType <em>Tgt Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tgt Type</em>' reference.
	 * @see #getTgtType()
	 * @generated
	 */
	void setTgtType(EClass value);

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
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedBoundaryEReference_Type()
	 * @model required="true"
	 * @generated
	 */
	EReference getType();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EReference value);

} // SlicedBoundaryEReference
