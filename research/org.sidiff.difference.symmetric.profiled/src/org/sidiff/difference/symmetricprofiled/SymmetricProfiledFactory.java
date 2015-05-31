/**
 */
package org.sidiff.difference.symmetricprofiled;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage
 * @generated
 */
public interface SymmetricProfiledFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SymmetricProfiledFactory eINSTANCE = org.sidiff.difference.symmetricprofiled.impl.SymmetricProfiledFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Profiled Symmetric Difference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Profiled Symmetric Difference</em>'.
	 * @generated
	 */
	ProfiledSymmetricDifference createProfiledSymmetricDifference();

	/**
	 * Returns a new object of class '<em>Profiled Semantic Change Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Profiled Semantic Change Set</em>'.
	 * @generated
	 */
	ProfiledSemanticChangeSet createProfiledSemanticChangeSet();

	/**
	 * Returns a new object of class '<em>Applied Stereotype</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Applied Stereotype</em>'.
	 * @generated
	 */
	AppliedStereotype createAppliedStereotype();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SymmetricProfiledPackage getSymmetricProfiledPackage();

} //SymmetricProfiledFactory
