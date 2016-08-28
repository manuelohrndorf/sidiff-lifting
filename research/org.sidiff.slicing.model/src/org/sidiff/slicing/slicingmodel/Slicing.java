/**
 */
package org.sidiff.slicing.slicingmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Slicing</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.slicingmodel.Slicing#getUri <em>Uri</em>}</li>
 *   <li>{@link org.sidiff.slicing.slicingmodel.Slicing#getSlicedContextElements <em>Sliced Context Elements</em>}</li>
 *   <li>{@link org.sidiff.slicing.slicingmodel.Slicing#getSlicedBoundaryElements <em>Sliced Boundary Elements</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicing.slicingmodel.SlicingmodelPackage#getSlicing()
 * @model
 * @generated
 */
public interface Slicing extends EObject {
	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute list.
	 * @see org.sidiff.slicing.slicingmodel.SlicingmodelPackage#getSlicing_Uri()
	 * @model required="true"
	 * @generated
	 */
	EList<String> getUri();

	/**
	 * Returns the value of the '<em><b>Sliced Context Elements</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sliced Context Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sliced Context Elements</em>' reference list.
	 * @see org.sidiff.slicing.slicingmodel.SlicingmodelPackage#getSlicing_SlicedContextElements()
	 * @model
	 * @generated
	 */
	EList<EObject> getSlicedContextElements();

	/**
	 * Returns the value of the '<em><b>Sliced Boundary Elements</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sliced Boundary Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sliced Boundary Elements</em>' reference list.
	 * @see org.sidiff.slicing.slicingmodel.SlicingmodelPackage#getSlicing_SlicedBoundaryElements()
	 * @model
	 * @generated
	 */
	EList<EObject> getSlicedBoundaryElements();

} // Slicing
