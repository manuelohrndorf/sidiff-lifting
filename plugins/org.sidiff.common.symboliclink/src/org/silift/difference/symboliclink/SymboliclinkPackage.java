/**
 */
package org.silift.difference.symboliclink;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
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
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT__OUTGOING = 1;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT__INCOMING = 2;

	/**
	 * The feature id for the '<em><b>Link Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT__TYPE = 4;

	/**
	 * The number of structural features of the '<em>Symbolic Link Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT_FEATURE_COUNT = 5;

	/**
	 * The operation id for the '<em>Get Outgoings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT___GET_OUTGOINGS__EREFERENCE = 0;

	/**
	 * The operation id for the '<em>Hash Code</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT___HASH_CODE = 1;

	/**
	 * The number of operations of the '<em>Symbolic Link Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_OBJECT_OPERATION_COUNT = 2;

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
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_REFERENCE__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_REFERENCE__TARGET = 2;

	/**
	 * The number of structural features of the '<em>Symbolic Link Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_REFERENCE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Symbolic Link Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_REFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.silift.difference.symboliclink.impl.ExternalSymbolicLinkObjectImpl <em>External Symbolic Link Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.silift.difference.symboliclink.impl.ExternalSymbolicLinkObjectImpl
	 * @see org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl#getExternalSymbolicLinkObject()
	 * @generated
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT = 3;

	/**
	 * The feature id for the '<em><b>Reliability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT__RELIABILITY = SYMBOLIC_LINK_OBJECT__RELIABILITY;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT__OUTGOING = SYMBOLIC_LINK_OBJECT__OUTGOING;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT__INCOMING = SYMBOLIC_LINK_OBJECT__INCOMING;

	/**
	 * The feature id for the '<em><b>Link Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES = SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT__TYPE = SYMBOLIC_LINK_OBJECT__TYPE;

	/**
	 * The feature id for the '<em><b>EObject</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT__EOBJECT = SYMBOLIC_LINK_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT__FROM = SYMBOLIC_LINK_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>External Symbolic Link Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT_FEATURE_COUNT = SYMBOLIC_LINK_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Outgoings</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT___GET_OUTGOINGS__EREFERENCE = SYMBOLIC_LINK_OBJECT___GET_OUTGOINGS__EREFERENCE;

	/**
	 * The operation id for the '<em>Hash Code</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT___HASH_CODE = SYMBOLIC_LINK_OBJECT___HASH_CODE;

	/**
	 * The number of operations of the '<em>External Symbolic Link Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SYMBOLIC_LINK_OBJECT_OPERATION_COUNT = SYMBOLIC_LINK_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.silift.difference.symboliclink.impl.SymbolicLinkAttributeImpl <em>Symbolic Link Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.silift.difference.symboliclink.impl.SymbolicLinkAttributeImpl
	 * @see org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl#getSymbolicLinkAttribute()
	 * @generated
	 */
	int SYMBOLIC_LINK_ATTRIBUTE = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_ATTRIBUTE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_ATTRIBUTE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Symbolic Link Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Symbolic Link Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOLIC_LINK_ATTRIBUTE_OPERATION_COUNT = 0;

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
	 * Returns the meta object for the reference list '{@link org.silift.difference.symboliclink.SymbolicLinkObject#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject#getOutgoing()
	 * @see #getSymbolicLinkObject()
	 * @generated
	 */
	EReference getSymbolicLinkObject_Outgoing();

	/**
	 * Returns the meta object for the reference list '{@link org.silift.difference.symboliclink.SymbolicLinkObject#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject#getIncoming()
	 * @see #getSymbolicLinkObject()
	 * @generated
	 */
	EReference getSymbolicLinkObject_Incoming();

	/**
	 * Returns the meta object for the containment reference list '{@link org.silift.difference.symboliclink.SymbolicLinkObject#getLinkAttributes <em>Link Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Link Attributes</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject#getLinkAttributes()
	 * @see #getSymbolicLinkObject()
	 * @generated
	 */
	EReference getSymbolicLinkObject_LinkAttributes();

	/**
	 * Returns the meta object for the reference '{@link org.silift.difference.symboliclink.SymbolicLinkObject#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject#getType()
	 * @see #getSymbolicLinkObject()
	 * @generated
	 */
	EReference getSymbolicLinkObject_Type();

	/**
	 * Returns the meta object for the '{@link org.silift.difference.symboliclink.SymbolicLinkObject#getOutgoings(org.eclipse.emf.ecore.EReference) <em>Get Outgoings</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Outgoings</em>' operation.
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject#getOutgoings(org.eclipse.emf.ecore.EReference)
	 * @generated
	 */
	EOperation getSymbolicLinkObject__GetOutgoings__EReference();

	/**
	 * Returns the meta object for the '{@link org.silift.difference.symboliclink.SymbolicLinkObject#hashCode() <em>Hash Code</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Hash Code</em>' operation.
	 * @see org.silift.difference.symboliclink.SymbolicLinkObject#hashCode()
	 * @generated
	 */
	EOperation getSymbolicLinkObject__HashCode();

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
	 * Returns the meta object for the reference '{@link org.silift.difference.symboliclink.SymbolicLinkReference#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkReference#getSource()
	 * @see #getSymbolicLinkReference()
	 * @generated
	 */
	EReference getSymbolicLinkReference_Source();

	/**
	 * Returns the meta object for the reference '{@link org.silift.difference.symboliclink.SymbolicLinkReference#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkReference#getTarget()
	 * @see #getSymbolicLinkReference()
	 * @generated
	 */
	EReference getSymbolicLinkReference_Target();

	/**
	 * Returns the meta object for class '{@link org.silift.difference.symboliclink.ExternalSymbolicLinkObject <em>External Symbolic Link Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Symbolic Link Object</em>'.
	 * @see org.silift.difference.symboliclink.ExternalSymbolicLinkObject
	 * @generated
	 */
	EClass getExternalSymbolicLinkObject();

	/**
	 * Returns the meta object for the reference '{@link org.silift.difference.symboliclink.ExternalSymbolicLinkObject#getEObject <em>EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EObject</em>'.
	 * @see org.silift.difference.symboliclink.ExternalSymbolicLinkObject#getEObject()
	 * @see #getExternalSymbolicLinkObject()
	 * @generated
	 */
	EReference getExternalSymbolicLinkObject_EObject();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.symboliclink.ExternalSymbolicLinkObject#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>From</em>'.
	 * @see org.silift.difference.symboliclink.ExternalSymbolicLinkObject#getFrom()
	 * @see #getExternalSymbolicLinkObject()
	 * @generated
	 */
	EAttribute getExternalSymbolicLinkObject_From();

	/**
	 * Returns the meta object for class '{@link org.silift.difference.symboliclink.SymbolicLinkAttribute <em>Symbolic Link Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbolic Link Attribute</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkAttribute
	 * @generated
	 */
	EClass getSymbolicLinkAttribute();

	/**
	 * Returns the meta object for the attribute '{@link org.silift.difference.symboliclink.SymbolicLinkAttribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkAttribute#getValue()
	 * @see #getSymbolicLinkAttribute()
	 * @generated
	 */
	EAttribute getSymbolicLinkAttribute_Value();

	/**
	 * Returns the meta object for the reference '{@link org.silift.difference.symboliclink.SymbolicLinkAttribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.silift.difference.symboliclink.SymbolicLinkAttribute#getType()
	 * @see #getSymbolicLinkAttribute()
	 * @generated
	 */
	EReference getSymbolicLinkAttribute_Type();

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
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLIC_LINK_OBJECT__OUTGOING = eINSTANCE.getSymbolicLinkObject_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLIC_LINK_OBJECT__INCOMING = eINSTANCE.getSymbolicLinkObject_Incoming();

		/**
		 * The meta object literal for the '<em><b>Link Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES = eINSTANCE.getSymbolicLinkObject_LinkAttributes();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLIC_LINK_OBJECT__TYPE = eINSTANCE.getSymbolicLinkObject_Type();

		/**
		 * The meta object literal for the '<em><b>Get Outgoings</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SYMBOLIC_LINK_OBJECT___GET_OUTGOINGS__EREFERENCE = eINSTANCE.getSymbolicLinkObject__GetOutgoings__EReference();

		/**
		 * The meta object literal for the '<em><b>Hash Code</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SYMBOLIC_LINK_OBJECT___HASH_CODE = eINSTANCE.getSymbolicLinkObject__HashCode();

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

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLIC_LINK_REFERENCE__SOURCE = eINSTANCE.getSymbolicLinkReference_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLIC_LINK_REFERENCE__TARGET = eINSTANCE.getSymbolicLinkReference_Target();

		/**
		 * The meta object literal for the '{@link org.silift.difference.symboliclink.impl.ExternalSymbolicLinkObjectImpl <em>External Symbolic Link Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.silift.difference.symboliclink.impl.ExternalSymbolicLinkObjectImpl
		 * @see org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl#getExternalSymbolicLinkObject()
		 * @generated
		 */
		EClass EXTERNAL_SYMBOLIC_LINK_OBJECT = eINSTANCE.getExternalSymbolicLinkObject();

		/**
		 * The meta object literal for the '<em><b>EObject</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_SYMBOLIC_LINK_OBJECT__EOBJECT = eINSTANCE.getExternalSymbolicLinkObject_EObject();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_SYMBOLIC_LINK_OBJECT__FROM = eINSTANCE.getExternalSymbolicLinkObject_From();

		/**
		 * The meta object literal for the '{@link org.silift.difference.symboliclink.impl.SymbolicLinkAttributeImpl <em>Symbolic Link Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.silift.difference.symboliclink.impl.SymbolicLinkAttributeImpl
		 * @see org.silift.difference.symboliclink.impl.SymboliclinkPackageImpl#getSymbolicLinkAttribute()
		 * @generated
		 */
		EClass SYMBOLIC_LINK_ATTRIBUTE = eINSTANCE.getSymbolicLinkAttribute();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOLIC_LINK_ATTRIBUTE__VALUE = eINSTANCE.getSymbolicLinkAttribute_Value();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOLIC_LINK_ATTRIBUTE__TYPE = eINSTANCE.getSymbolicLinkAttribute_Type();

	}

} //SymboliclinkPackage
