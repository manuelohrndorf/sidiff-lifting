/**
 */
package org.silift.difference.namedelementsymboliclink;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkPackage
 * @generated
 */
public interface NamedelementsymboliclinkFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NamedelementsymboliclinkFactory eINSTANCE = org.silift.difference.namedelementsymboliclink.impl.NamedelementsymboliclinkFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Named Element Symbolic Link Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Named Element Symbolic Link Object</em>'.
	 * @generated
	 */
	NamedElementSymbolicLinkObject createNamedElementSymbolicLinkObject();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NamedelementsymboliclinkPackage getNamedelementsymboliclinkPackage();

} //NamedelementsymboliclinkFactory
