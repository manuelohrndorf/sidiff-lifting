/**
 */
package org.silift.difference.symboliclink;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.silift.difference.symboliclink.SymboliclinkFactory
 * @model kind="package"
 * @generated
 */
public interface SymboliclinkPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "symboliclink";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://symboliclink/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "symboliclink";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SymboliclinkPackage eINSTANCE = org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.silift.difference.symboliclink.impl.SymbolicLinksImpl <em>Symbolic Links</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.silift.difference.symboliclink.impl.SymbolicLinksImpl
	 * @see org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl#getSymbolicLinks()
	 * @generated
	 */
	int SYMBOLIC_LINKS = 0;

	/**
	 * The feature id for the '<em><b>Link Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINKS__LINK_OBJECTS = 0;

	/**
	 * The feature id for the '<em><b>Doc Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINKS__DOC_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Link References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINKS__LINK_REFERENCES = 2;

	/**
	 * The number of structural features of the '<em>Symbolic Links</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINKS_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Symbolic Links</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINKS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.silift.difference.symboliclink.impl.SymbolicLinkObjectImpl <em>Symbolic Link Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.silift.difference.symboliclink.impl.SymbolicLinkObjectImpl
	 * @see org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl#getSymbolicLinkObject()
	 * @generated
	 */
	int SYMBOLIC_LINK_OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Reliability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT__RELIABILITY = 0;

	/**
	 * The number of structural features of the '<em>Symbolic Link Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Symbolic Link Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.silift.difference.symboliclink.impl.SymbolicLinkReferenceImpl <em>Symbolic Link Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.silift.difference.symboliclink.impl.SymbolicLinkReferenceImpl
	 * @see org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl#getSymbolicLinkReference()
	 * @generated
	 */
	int SYMBOLIC_LINK_REFERENCE = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_REFERENCE__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Symbolic Link Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_REFERENCE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Symbolic Link Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_REFERENCE_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.silift.difference.symboliclink.SymbolicLinks <em>Symbolic Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbolic Links</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinks
	 * @generated
	 */
	EClass getSymbolicLinks();

	/**
	 * Returns the meta object for the containment reference list '{@link org.silift.difference.symboliclink.SymbolicLinks#getLinkObjects <em>Link Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Link Objects</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinks#getLinkObjects()
	 * @see #getSymbolicLinks()
	 * @generated
	 */
	EReference getSymbolicLinks_LinkObjects();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.symboliclink.SymbolicLinks#getDocType <em>Doc Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Doc Type</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinks#getDocType()
	 * @see #getSymbolicLinks()
	 * @generated
	 */
	EAttribute getSymbolicLinks_DocType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.silift.difference.symboliclink.SymbolicLinks#getLinkReferences <em>Link References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Link References</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinks#getLinkReferences()
	 * @see #getSymbolicLinks()
	 * @generated
	 */
	EReference getSymbolicLinks_LinkReferences();

	/**
	 * Returns the meta object for class '{@link org.silift.difference.symboliclink.SymbolicLinkObject <em>Symbolic Link Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbolic Link Object</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject
	 * @generated
	 */
	EClass getSymbolicLinkObject();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.symboliclink.SymbolicLinkObject#getReliability <em>Reliability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reliability</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject#getReliability()
	 * @see #getSymbolicLinkObject()
	 * @generated
	 */
	EAttribute getSymbolicLinkObject_Reliability();

	/**
	 * Returns the meta object for class '{@link org.silift.difference.symboliclink.SymbolicLinkReference <em>Symbolic Link Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbolic Link Reference</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkReference
	 * @generated
	 */
	EClass getSymbolicLinkReference();

	/**
	 * Returns the meta object for the reference '{@link org.silift.difference.symboliclink.SymbolicLinkReference#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkReference#getType()
	 * @see #getSymbolicLinkReference()
	 * @generated
	 */
	EReference getSymbolicLinkReference_Type();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SymboliclinkFactory getSymboliclinkFactory();

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
		 * The meta object literal for the '{@link org.silift.difference.symboliclink.impl.SymbolicLinksImpl <em>Symbolic Links</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.silift.difference.symboliclink.impl.SymbolicLinksImpl
		 * @see org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl#getSymbolicLinks()
		 * @generated
		 */
		EClass SYMBOLIC_LINKS = eINSTANCE.getSymbolicLinks();

		/**
		 * The meta object literal for the '<em><b>Link Objects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLIC_LINKS__LINK_OBJECTS = eINSTANCE.getSymbolicLinks_LinkObjects();

		/**
		 * The meta object literal for the '<em><b>Doc Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOLIC_LINKS__DOC_TYPE = eINSTANCE.getSymbolicLinks_DocType();

		/**
		 * The meta object literal for the '<em><b>Link References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLIC_LINKS__LINK_REFERENCES = eINSTANCE.getSymbolicLinks_LinkReferences();

		/**
		 * The meta object literal for the '{@link org.silift.difference.symboliclink.impl.SymbolicLinkObjectImpl <em>Symbolic Link Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.silift.difference.symboliclink.impl.SymbolicLinkObjectImpl
		 * @see org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl#getSymbolicLinkObject()
		 * @generated
		 */
		EClass SYMBOLIC_LINK_OBJECT = eINSTANCE.getSymbolicLinkObject();

		/**
		 * The meta object literal for the '<em><b>Reliability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOLIC_LINK_OBJECT__RELIABILITY = eINSTANCE.getSymbolicLinkObject_Reliability();

		/**
		 * The meta object literal for the '{@link org.silift.difference.symboliclink.impl.SymbolicLinkReferenceImpl <em>Symbolic Link Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.silift.difference.symboliclink.impl.SymbolicLinkReferenceImpl
		 * @see org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl#getSymbolicLinkReference()
		 * @generated
		 */
		EClass SYMBOLIC_LINK_REFERENCE = eINSTANCE.getSymbolicLinkReference();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLIC_LINK_REFERENCE__TYPE = eINSTANCE.getSymbolicLinkReference_Type();

	}

} //SymboliclinkPackage
