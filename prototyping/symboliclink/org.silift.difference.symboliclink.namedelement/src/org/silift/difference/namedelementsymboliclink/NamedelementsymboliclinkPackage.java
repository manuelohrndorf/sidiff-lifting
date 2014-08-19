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
	 * The meta object id for the '{@link org.silift.difference.namedelementsymboliclink.impl.NamedElementSymbolicLinkImpl <em>Named Element Symbolic Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.silift.difference.namedelementsymboliclink.impl.NamedElementSymbolicLinkImpl
	 * @see org.silift.difference.namedelementsymboliclink.impl.NamedelementsymboliclinkPackageImpl#getNamedElementSymbolicLink()
	 * @generated
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK__NAME = SymboliclinkPackage.SYMBOLIC_LINK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK__QUALIFIED_NAME = SymboliclinkPackage.SYMBOLIC_LINK_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Named Element Symbolic Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_FEATURE_COUNT = SymboliclinkPackage.SYMBOLIC_LINK_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Named Element Symbolic Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_SYMBOLIC_LINK_OPERATION_COUNT = SymboliclinkPackage.SYMBOLIC_LINK_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink <em>Named Element Symbolic Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element Symbolic Link</em>'.
	 * @see org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink
	 * @generated
	 */
	EClass getNamedElementSymbolicLink();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink#getName()
	 * @see #getNamedElementSymbolicLink()
	 * @generated
	 */
	EAttribute getNamedElementSymbolicLink_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink#getQualifiedName <em>Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see org.silift.difference.namedelementsymboliclink.NamedElementSymbolicLink#getQualifiedName()
	 * @see #getNamedElementSymbolicLink()
	 * @generated
	 */
	EAttribute getNamedElementSymbolicLink_QualifiedName();

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
		 * The meta object literal for the '{@link org.silift.difference.namedelementsymboliclink.impl.NamedElementSymbolicLinkImpl <em>Named Element Symbolic Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.silift.difference.namedelementsymboliclink.impl.NamedElementSymbolicLinkImpl
		 * @see org.silift.difference.namedelementsymboliclink.impl.NamedelementsymboliclinkPackageImpl#getNamedElementSymbolicLink()
		 * @generated
		 */
		EClass NAMED_ELEMENT_SYMBOLIC_LINK = eINSTANCE.getNamedElementSymbolicLink();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT_SYMBOLIC_LINK__NAME = eINSTANCE.getNamedElementSymbolicLink_Name();

		/**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT_SYMBOLIC_LINK__QUALIFIED_NAME = eINSTANCE.getNamedElementSymbolicLink_QualifiedName();

	}

} //NamedelementsymboliclinkPackage
