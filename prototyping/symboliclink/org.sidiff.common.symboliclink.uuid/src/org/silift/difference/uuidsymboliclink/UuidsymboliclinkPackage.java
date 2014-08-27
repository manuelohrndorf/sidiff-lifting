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
	 * The meta object id for the '{@link org.silift.difference.uuidsymboliclink.impl.UUIDSymbolicLinkObjectImpl <em>UUID Symbolic Link Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.silift.difference.uuidsymboliclink.impl.UUIDSymbolicLinkObjectImpl
	 * @see org.silift.difference.uuidsymboliclink.impl.UuidsymboliclinkPackageImpl#getUUIDSymbolicLinkObject()
	 * @generated
	 */
	int UUID_SYMBOLIC_LINK_OBJECT = 0;

	/**
	 * The feature id for the '<em><b>Reliability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_SYMBOLIC_LINK_OBJECT__RELIABILITY = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__RELIABILITY;

	/**
	 * The feature id for the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_SYMBOLIC_LINK_OBJECT__UUID = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_SYMBOLIC_LINK_OBJECT__NAME = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>UUID Symbolic Link Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_SYMBOLIC_LINK_OBJECT_FEATURE_COUNT = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>UUID Symbolic Link Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UUID_SYMBOLIC_LINK_OBJECT_OPERATION_COUNT = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.silift.difference.uuidsymboliclink.UUIDSymbolicLinkObject <em>UUID Symbolic Link Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UUID Symbolic Link Object</em>'.
	 * @see org.silift.difference.uuidsymboliclink.UUIDSymbolicLinkObject
	 * @generated
	 */
	EClass getUUIDSymbolicLinkObject();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.uuidsymboliclink.UUIDSymbolicLinkObject#getUuid <em>Uuid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uuid</em>'.
	 * @see org.silift.difference.uuidsymboliclink.UUIDSymbolicLinkObject#getUuid()
	 * @see #getUUIDSymbolicLinkObject()
	 * @generated
	 */
	EAttribute getUUIDSymbolicLinkObject_Uuid();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.uuidsymboliclink.UUIDSymbolicLinkObject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.silift.difference.uuidsymboliclink.UUIDSymbolicLinkObject#getName()
	 * @see #getUUIDSymbolicLinkObject()
	 * @generated
	 */
	EAttribute getUUIDSymbolicLinkObject_Name();

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
		 * The meta object literal for the '{@link org.silift.difference.uuidsymboliclink.impl.UUIDSymbolicLinkObjectImpl <em>UUID Symbolic Link Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.silift.difference.uuidsymboliclink.impl.UUIDSymbolicLinkObjectImpl
		 * @see org.silift.difference.uuidsymboliclink.impl.UuidsymboliclinkPackageImpl#getUUIDSymbolicLinkObject()
		 * @generated
		 */
		EClass UUID_SYMBOLIC_LINK_OBJECT = eINSTANCE.getUUIDSymbolicLinkObject();

		/**
		 * The meta object literal for the '<em><b>Uuid</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UUID_SYMBOLIC_LINK_OBJECT__UUID = eINSTANCE.getUUIDSymbolicLinkObject_Uuid();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UUID_SYMBOLIC_LINK_OBJECT__NAME = eINSTANCE.getUUIDSymbolicLinkObject_Name();

	}

} //UuidsymboliclinkPackage
