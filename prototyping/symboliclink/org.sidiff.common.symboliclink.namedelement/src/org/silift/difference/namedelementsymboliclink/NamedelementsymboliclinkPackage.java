/**
 */
package org.silift.difference.namedelementsymboliclink;

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
 * @see org.silift.difference.namedelementsymboliclink.NamedelementsymboliclinkFactory
 * @model kind="package"
 * @generated
 */
public interface NamedelementsymboliclinkPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "namedelementsymboliclink";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://namedelementsymboliclink/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "namedelementsymboliclink";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NamedelementsymboliclinkPackage eINSTANCE = org.silift.difference.namedelementsymboliclink.impl.NamedelementsymboliclinkPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.silift.difference.namedelementsymboliclink.impl.NamedElementSymbolicLinkObjectImpl <em>Named Element Symbolic Link Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.silift.difference.namedelementsymboliclink.impl.NamedElementSymbolicLinkObjectImpl
	 * @see org.silift.difference.namedelementsymboliclink.impl.NamedelementsymboliclinkPackageImpl#getNamedElementSymbolicLinkObject()
	 * @generated
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT = 0;

	/**
	 * The feature id for the '<em><b>Reliability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__RELIABILITY = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__RELIABILITY;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__OUTGOING = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__INCOMING = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__NAME = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__QUALIFIED_NAME = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Named Element Symbolic Link Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT_FEATURE_COUNT = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Outgoings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT___GET_OUTGOINGS__EREFERENCE = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT___GET_OUTGOINGS__EREFERENCE;

	/**
	 * The operation id for the '<em>Hash Code</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT___HASH_CODE = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT___HASH_CODE;

	/**
	 * The number of operations of the '<em>Named Element Symbolic Link Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT_OPERATION_COUNT = SymboliclinkPackage.SYMBOLIC_LINK_OBJECT_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject <em>Named Element Symbolic Link Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element Symbolic Link Object</em>'.
	 * @see org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject
	 * @generated
	 */
	EClass getNamedElementSymbolicLinkObject();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject#getName()
	 * @see #getNamedElementSymbolicLinkObject()
	 * @generated
	 */
	EAttribute getNamedElementSymbolicLinkObject_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject#getQualifiedName <em>Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLinkObject#getQualifiedName()
	 * @see #getNamedElementSymbolicLinkObject()
	 * @generated
	 */
	EAttribute getNamedElementSymbolicLinkObject_QualifiedName();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NamedelementsymboliclinkFactory getNamedelementsymboliclinkFactory();

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
		 * The meta object literal for the '{@link org.silift.difference.namedelementsymboliclink.impl.NamedElementSymbolicLinkObjectImpl <em>Named Element Symbolic Link Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.silift.difference.namedelementsymboliclink.impl.NamedElementSymbolicLinkObjectImpl
		 * @see org.silift.difference.namedelementsymboliclink.impl.NamedelementsymboliclinkPackageImpl#getNamedElementSymbolicLinkObject()
		 * @generated
		 */
		EClass NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT = eINSTANCE.getNamedElementSymbolicLinkObject();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__NAME = eINSTANCE.getNamedElementSymbolicLinkObject_Name();

		/**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT_SYMBOLIC_LINK_OBJECT__QUALIFIED_NAME = eINSTANCE.getNamedElementSymbolicLinkObject_QualifiedName();

	}

} //NamedelementsymboliclinkPackage
