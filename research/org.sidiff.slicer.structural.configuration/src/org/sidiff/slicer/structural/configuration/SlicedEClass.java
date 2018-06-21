/**
 */
package org.sidiff.slicer.structural.configuration;

import java.util.Map;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sliced EClass</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicingConfiguration <em>Slicing Configuration</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getType <em>Type</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicedEReferences <em>Sliced EReferences</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getOppositeSlicedEReferenceType <em>Opposite Sliced EReference Type</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEClass()
 * @model
 * @generated
 */
public interface SlicedEClass extends EObject {
	/**
	 * Returns the value of the '<em><b>Slicing Configuration</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.slicer.structural.configuration.SlicingConfiguration#getSlicedEClasses <em>Sliced EClasses</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slicing Configuration</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slicing Configuration</em>' container reference.
	 * @see #setSlicingConfiguration(SlicingConfiguration)
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEClass_SlicingConfiguration()
	 * @see org.sidiff.slicer.structural.configuration.SlicingConfiguration#getSlicedEClasses
	 * @model opposite="slicedEClasses" required="true" transient="false"
	 * @generated
	 */
	SlicingConfiguration getSlicingConfiguration();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicingConfiguration <em>Slicing Configuration</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Slicing Configuration</em>' container reference.
	 * @see #getSlicingConfiguration()
	 * @generated
	 */
	void setSlicingConfiguration(SlicingConfiguration value);

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
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEClass_Type()
	 * @model required="true"
	 * @generated
	 */
	EClass getType();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClass value);

	/**
	 * Returns the value of the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint</em>' containment reference.
	 * @see #setConstraint(Constraint)
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEClass_Constraint()
	 * @model containment="true"
	 * @generated
	 */
	Constraint getConstraint();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getConstraint <em>Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraint</em>' containment reference.
	 * @see #getConstraint()
	 * @generated
	 */
	void setConstraint(Constraint value);

	/**
	 * Returns the value of the '<em><b>Sliced EReferences</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.slicer.structural.configuration.SlicedEReference}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getSlicedEClass <em>Sliced EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sliced EReferences</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sliced EReferences</em>' containment reference list.
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEClass_SlicedEReferences()
	 * @see org.sidiff.slicer.structural.configuration.SlicedEReference#getSlicedEClass
	 * @model opposite="slicedEClass" containment="true"
	 * @generated
	 */
	EList<SlicedEReference> getSlicedEReferences();

	/**
	 * Returns the value of the '<em><b>Opposite Sliced EReference Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Opposite Sliced EReference Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Opposite Sliced EReference Type</em>' attribute.
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEClass_OppositeSlicedEReferenceType()
	 * @model transient="true" changeable="false" derived="true"
	 * @generated
	 */
	Map<EReference, SlicedEReference> getOppositeSlicedEReferenceType();

} // SlicedEClass
