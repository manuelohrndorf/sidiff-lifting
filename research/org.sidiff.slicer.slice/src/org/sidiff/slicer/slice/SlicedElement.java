/**
 */
package org.sidiff.slicer.slice;

import org.eclipse.emf.common.util.EList;

import org.sidiff.entities.Attribute;
import org.sidiff.entities.Element;
import org.sidiff.entities.Reference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sliced Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.slice.SlicedElement#getSlicedReferences <em>Sliced References</em>}</li>
 *   <li>{@link org.sidiff.slicer.slice.SlicedElement#getSlicedAttributes <em>Sliced Attributes</em>}</li>
 * </ul>
 *
 * @see org.sidiff.slicer.slice.SlicePackage#getSlicedElement()
 * @model
 * @generated
 */
public interface SlicedElement extends Element {
	/**
	 * Returns the value of the '<em><b>Sliced References</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.entities.Reference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sliced References</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sliced References</em>' containment reference list.
	 * @see org.sidiff.slicer.slice.SlicePackage#getSlicedElement_SlicedReferences()
	 * @model containment="true"
	 * @generated
	 */
	EList<Reference> getSlicedReferences();

	/**
	 * Returns the value of the '<em><b>Sliced Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.entities.Attribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sliced Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sliced Attributes</em>' containment reference list.
	 * @see org.sidiff.slicer.slice.SlicePackage#getSlicedElement_SlicedAttributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Attribute> getSlicedAttributes();

} // SlicedElement
