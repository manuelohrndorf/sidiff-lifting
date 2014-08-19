/**
 */
package org.silift.difference.uuidsymboliclink;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.silift.difference.uuidsymboliclink.UuidsymboliclinkPackage
 * @generated
 */
public interface UuidsymboliclinkFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UuidsymboliclinkFactory eINSTANCE = org.silift.difference.uuidsymboliclink.impl.UuidsymboliclinkFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>UUID Symbolic Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UUID Symbolic Link</em>'.
	 * @generated
	 */
	UUIDSymbolicLink createUUIDSymbolicLink();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	UuidsymboliclinkPackage getUuidsymboliclinkPackage();

} //UuidsymboliclinkFactory
