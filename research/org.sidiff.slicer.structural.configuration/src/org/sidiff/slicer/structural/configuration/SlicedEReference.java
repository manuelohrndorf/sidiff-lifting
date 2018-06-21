/**
 */
package org.sidiff.slicer.structural.configuration;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sliced EReference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getType <em>Type</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getSlicedEClass <em>Sliced EClass</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getOverwrite <em>Overwrite</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEReference()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ReferenceNotDangling'"
 * @generated
 */
public interface SlicedEReference extends EObject {
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
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEReference_Type()
	 * @model required="true"
	 * @generated
	 */
	EReference getType();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EReference value);

	/**
	 * Returns the value of the '<em><b>Sliced EClass</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicedEReferences <em>Sliced EReferences</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sliced EClass</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sliced EClass</em>' container reference.
	 * @see #setSlicedEClass(SlicedEClass)
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEReference_SlicedEClass()
	 * @see org.sidiff.slicer.structural.configuration.SlicedEClass#getSlicedEReferences
	 * @model opposite="slicedEReferences" required="true" transient="false"
	 * @generated
	 */
	SlicedEClass getSlicedEClass();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getSlicedEClass <em>Sliced EClass</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sliced EClass</em>' container reference.
	 * @see #getSlicedEClass()
	 * @generated
	 */
	void setSlicedEClass(SlicedEClass value);

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
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEReference_Constraint()
	 * @model containment="true"
	 * @generated
	 */
	Constraint getConstraint();

	/**
	 * Sets the value of the '{@link org.sidiff.slicer.structural.configuration.SlicedEReference#getConstraint <em>Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraint</em>' containment reference.
	 * @see #getConstraint()
	 * @generated
	 */
	void setConstraint(Constraint value);

	/**
	 * Returns the value of the '<em><b>Overwrite</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overwrite</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Overwrite</em>' reference.
	 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage#getSlicedEReference_Overwrite()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	SlicedEReference getOverwrite();

} // SlicedEReference
