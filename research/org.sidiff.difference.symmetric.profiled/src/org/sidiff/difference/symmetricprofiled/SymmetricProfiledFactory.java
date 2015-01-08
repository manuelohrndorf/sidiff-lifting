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
	 * Returns a new object of class '<em>Profiled SD</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Profiled SD</em>'.
	 * @generated
	 */
	ProfiledSD createProfiledSD();

	/**
	 * Returns a new object of class '<em>Profiled SCS</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Profiled SCS</em>'.
	 * @generated
	 */
	ProfiledSCS createProfiledSCS();

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
