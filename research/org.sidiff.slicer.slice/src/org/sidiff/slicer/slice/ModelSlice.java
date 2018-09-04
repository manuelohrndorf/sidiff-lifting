/**
 */
package org.sidiff.slicer.slice;

import java.util.function.Predicate;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "(c), Robert Müller and Christopher Pietsch, Software Engineering Group, University of Siegen 2017 all rights reserved";

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
	 * <!-- begin-model-doc -->
	 * Returns a collection of all containers that properly contain all sliced objects.
	 * The copy selector is a predicate that should return true for all objects, that should be copied.
	 * Returning false references the original object.
	 * <!-- end-model-doc -->
	 * @model copySelectorDataType="org.sidiff.slicer.slice.ICopySelector"
	 * @generated
	 */
	EList<EObject> export(Predicate<EObject> copySelector);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='ResourceSet resourceSet = new ResourceSetImpl();  Resource resource = resourceSet.createResource(EMFStorage.pathToUri(path)); resource.getContents().add(this);  try { resource.save(null); } catch (IOException e) {e.printStackTrace(); }'"
	 * @generated
	 */
	void serialize(String path);

} // ModelSlice
