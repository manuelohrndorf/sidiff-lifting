/**
 */
package org.silift.difference.uuidsymboliclink;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.silift.difference.symboliclink.SymboliclinkPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.silift.difference.uuidsymboliclink.UuidsymboliclinkFactory
 * @model kind="package"
 * @generated
 */
public interface UuidsymboliclinkPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "uuidsymboliclink";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://uuidsymboliclink/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "uuidsymboliclink";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UuidsymboliclinkPackage eINSTANCE = org.silift.difference.uuidsymboliclink.impl.UuidsymboliclinkPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.silift.difference.uuidsymboliclink.impl.UUIDSymbolicLinkImpl <em>UUID Symbolic Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.silift.difference.uuidsymboliclink.impl.UUIDSymbolicLinkImpl
	 * @see org.silift.difference.uuidsymboliclink.impl.UuidsymboliclinkPackageImpl#getUUIDSymbolicLink()
	 * @generated
	 */
	int UUID_SYMBOLIC_LINK = 0;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_SYMBOLIC_LINK__UUID = SymboliclinkPackage.SYMBOLIC_LINK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_SYMBOLIC_LINK__NAME = SymboliclinkPackage.SYMBOLIC_LINK_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>UUID Symbolic Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_SYMBOLIC_LINK_FEATURE_COUNT = SymboliclinkPackage.SYMBOLIC_LINK_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>UUID Symbolic Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_SYMBOLIC_LINK_OPERATION_COUNT = SymboliclinkPackage.SYMBOLIC_LINK_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.silift.difference.uuidsymboliclink.UUIDSymbolicLink <em>UUID Symbolic Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UUID Symbolic Link</em>'.
	 * @see org.silift.difference.uuidsymboliclink.UUIDSymbolicLink
	 * @generated
	 */
	EClass getUUIDSymbolicLink();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.uuidsymboliclink.UUIDSymbolicLink#getUuid <em>Uuid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uuid</em>'.
	 * @see org.silift.difference.uuidsymboliclink.UUIDSymbolicLink#getUuid()
	 * @see #getUUIDSymbolicLink()
	 * @generated
	 */
	EAttribute getUUIDSymbolicLink_Uuid();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.uuidsymboliclink.UUIDSymbolicLink#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.silift.difference.uuidsymboliclink.UUIDSymbolicLink#getName()
	 * @see #getUUIDSymbolicLink()
	 * @generated
	 */
	EAttribute getUUIDSymbolicLink_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UuidsymboliclinkFactory getUuidsymboliclinkFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.silift.difference.uuidsymboliclink.impl.UUIDSymbolicLinkImpl <em>UUID Symbolic Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.silift.difference.uuidsymboliclink.impl.UUIDSymbolicLinkImpl
		 * @see org.silift.difference.uuidsymboliclink.impl.UuidsymboliclinkPackageImpl#getUUIDSymbolicLink()
		 * @generated
		 */
		EClass UUID_SYMBOLIC_LINK = eINSTANCE.getUUIDSymbolicLink();

		/**
		 * The meta object literal for the '<em><b>Uuid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UUID_SYMBOLIC_LINK__UUID = eINSTANCE.getUUIDSymbolicLink_Uuid();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UUID_SYMBOLIC_LINK__NAME = eINSTANCE.getUUIDSymbolicLink_Name();

	}

} //UuidsymboliclinkPackage
