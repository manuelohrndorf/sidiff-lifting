/**
 */
package org.sidiff.slicer.slice;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.slicer.slice.SlicePackage
 * @generated
 */
public interface SliceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SliceFactory eINSTANCE = org.sidiff.slicer.slice.impl.SliceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Model Slice</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model Slice</em>'.
	 * @generated
	 */
	ModelSlice createModelSlice();

	/**
	 * Returns a new object of class '<em>Sliced Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sliced Element</em>'.
	 * @generated
	 */
	SlicedElement createSlicedElement();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SlicePackage getSlicePackage();

} //SliceFactory
