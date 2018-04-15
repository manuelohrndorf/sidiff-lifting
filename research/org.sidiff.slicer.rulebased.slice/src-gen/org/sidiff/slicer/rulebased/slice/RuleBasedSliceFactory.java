/**
 */
package org.sidiff.slicer.rulebased.slice;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage
 * @generated
 */
public interface RuleBasedSliceFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RuleBasedSliceFactory eINSTANCE = org.sidiff.slicer.rulebased.slice.impl.RuleBasedSliceFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Executable Model Slice</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Executable Model Slice</em>'.
	 * @generated
	 */
	ExecutableModelSlice createExecutableModelSlice();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RuleBasedSlicePackage getRuleBasedSlicePackage();

} //RuleBasedSliceFactory
