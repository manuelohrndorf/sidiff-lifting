/**
 */
package org.sidiff.slicer.slice;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Slice</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.slice.ModelSlice#getSlicedElements <em>Sliced Elements</em>}</li>
 *   <li>{@link org.sidiff.slicer.slice.ModelSlice#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicer.slice.SlicePackage#getModelSlice()
 * @model
 * @generated
 */
public interface ModelSlice extends EObject {
	/**
	 * Returns the value of the '<em><b>Sliced Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.slicer.slice.SlicedElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sliced Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sliced Elements</em>' containment reference list.
	 * @see org.sidiff.slicer.slice.SlicePackage#getModelSlice_SlicedElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<SlicedElement> getSlicedElements();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference list.
	 * @see org.sidiff.slicer.slice.SlicePackage#getModelSlice_Type()
	 * @model required="true"
	 * @generated
	 */
	EList<EPackage> getType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<EObject> export();

} // ModelSlice
