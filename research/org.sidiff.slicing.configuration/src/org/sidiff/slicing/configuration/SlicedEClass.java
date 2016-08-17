/**
 */
package org.sidiff.slicing.configuration;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sliced EClass</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.configuration.SlicedEClass#isBoundary <em>Boundary</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicedEClass#getSlicingConfiguration <em>Slicing Configuration</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicedEClass#getOutgoings <em>Outgoings</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicedEClass#getIncomings <em>Incomings</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.SlicedEClass#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedEClass()
 * @model
 * @generated
 */
public interface SlicedEClass extends EObject {
	/**
	 * Returns the value of the '<em><b>Boundary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Boundary</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boundary</em>' attribute.
	 * @see #setBoundary(boolean)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedEClass_Boundary()
	 * @model required="true"
	 * @generated
	 */
	boolean isBoundary();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicedEClass#isBoundary <em>Boundary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Boundary</em>' attribute.
	 * @see #isBoundary()
	 * @generated
	 */
	void setBoundary(boolean value);

	/**
	 * Returns the value of the '<em><b>Slicing Configuration</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.slicing.configuration.SlicingConfiguration#getSlicedEClasses <em>Sliced EClasses</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slicing Configuration</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slicing Configuration</em>' container reference.
	 * @see #setSlicingConfiguration(SlicingConfiguration)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedEClass_SlicingConfiguration()
	 * @see org.sidiff.slicing.configuration.SlicingConfiguration#getSlicedEClasses
	 * @model opposite="slicedEClasses" required="true" transient="false"
	 * @generated
	 */
	SlicingConfiguration getSlicingConfiguration();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicedEClass#getSlicingConfiguration <em>Slicing Configuration</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Slicing Configuration</em>' container reference.
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	void setSlicingConfiguration(SlicingConfiguration value);

	/**
	 * Returns the value of the '<em><b>Outgoings</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.slicing.configuration.SlicedBoundaryEReference}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoings</em>' containment reference list.
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedEClass_Outgoings()
	 * @see org.sidiff.slicing.configuration.SlicedBoundaryEReference#getSource
	 * @model opposite="source" containment="true"
	 * @generated
	 */
	EList<SlicedBoundaryEReference> getOutgoings();

	/**
	 * Returns the value of the '<em><b>Incomings</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.slicing.configuration.SlicedBoundaryEReference}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.slicing.configuration.SlicedBoundaryEReference#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incomings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incomings</em>' containment reference list.
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedEClass_Incomings()
	 * @see org.sidiff.slicing.configuration.SlicedBoundaryEReference#getTarget
	 * @model opposite="target" containment="true"
	 * @generated
	 */
	EList<SlicedBoundaryEReference> getIncomings();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EClass)
	 * @see org.sidiff.slicing.configuration.ConfigurationPackage#getSlicedEClass_Type()
	 * @model required="true"
	 * @generated
	 */
	EClass getType();

	/**
	 * Sets the value of the '{@link org.sidiff.slicing.configuration.SlicedEClass#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClass value);

} // SlicedEClass
