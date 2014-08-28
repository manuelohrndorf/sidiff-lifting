/**
 */
package org.silift.difference.symboliclink;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.silift.difference.symboliclink.SymboliclinkPackage
 * @generated
 */
public interface SymboliclinkFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SymboliclinkFactory eINSTANCE = org.silift.difference.symboliclink.impl.SymboliclinkFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Symbolic Links</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Symbolic Links</em>'.
	 * @generated
	 */
	SymbolicLinks createSymbolicLinks();

	/**
	 * Returns a new object of class '<em>Symbolic Link Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Symbolic Link Reference</em>'.
	 * @generated
	 */
	SymbolicLinkReference createSymbolicLinkReference();

	/**
	 * Returns a new object of class '<em>External Symbolic Link Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>External Symbolic Link Object</em>'.
	 * @generated
	 */
	ExternalSymbolicLinkObject createExternalSymbolicLinkObject();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SymboliclinkPackage getSymboliclinkPackage();

} //SymboliclinkFactory
