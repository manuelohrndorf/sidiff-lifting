/**
 */
package org.silift.difference.symboliclink.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkReference;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.SymboliclinkPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Symbolic Links</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinksImpl#getLinkObjects <em>Link Objects</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinksImpl#getDocType <em>Doc Type</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinksImpl#getLinkReferences <em>Link References</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SymbolicLinksImpl extends MinimalEObjectImpl.Container implements SymbolicLinks {
	/**
	 * The cached value of the '{@link #getLinkObjects() <em>Link Objects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<SymbolicLinkObject> linkObjects;
	/**
	 * The default value of the '{@link #getDocType() <em>Doc Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocType()
	 * @generated
	 * @ordered
	 */
	protected static final String DOC_TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getDocType() <em>Doc Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocType()
	 * @generated
	 * @ordered
	 */
	protected String docType = DOC_TYPE_EDEFAULT;
	/**
	 * The cached value of the '{@link #getLinkReferences() <em>Link References</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<SymbolicLinkReference> linkReferences;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbolicLinksImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymboliclinkPackage.Literals.SYMBOLIC_LINKS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SymbolicLinkObject> getLinkObjects() {
		if (linkObjects == null) {
			linkObjects = new EObjectContainmentEList<SymbolicLinkObject>(SymbolicLinkObject.class, this, SymboliclinkPackage.SYMBOLIC_LINKS__LINK_OBJECTS);
		}
		return linkObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocType(String newDocType) {
		String oldDocType = docType;
		docType = newDocType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymboliclinkPackage.SYMBOLIC_LINKS__DOC_TYPE, oldDocType, docType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SymbolicLinkReference> getLinkReferences() {
		if (linkReferences == null) {
			linkReferences = new EObjectContainmentEList<SymbolicLinkReference>(SymbolicLinkReference.class, this, SymboliclinkPackage.SYMBOLIC_LINKS__LINK_REFERENCES);
		}
		return linkReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymboliclinkPackage.SYMBOLIC_LINKS__LINK_OBJECTS:
				return ((InternalEList<?>)getLinkObjects()).basicRemove(otherEnd, msgs);
			case SymboliclinkPackage.SYMBOLIC_LINKS__LINK_REFERENCES:
				return ((InternalEList<?>)getLinkReferences()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SymboliclinkPackage.SYMBOLIC_LINKS__LINK_OBJECTS:
				return getLinkObjects();
			case SymboliclinkPackage.SYMBOLIC_LINKS__DOC_TYPE:
				return getDocType();
			case SymboliclinkPackage.SYMBOLIC_LINKS__LINK_REFERENCES:
				return getLinkReferences();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SymboliclinkPackage.SYMBOLIC_LINKS__LINK_OBJECTS:
				getLinkObjects().clear();
				getLinkObjects().addAll((Collection<? extends SymbolicLinkObject>)newValue);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINKS__DOC_TYPE:
				setDocType((String)newValue);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINKS__LINK_REFERENCES:
				getLinkReferences().clear();
				getLinkReferences().addAll((Collection<? extends SymbolicLinkReference>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SymboliclinkPackage.SYMBOLIC_LINKS__LINK_OBJECTS:
				getLinkObjects().clear();
				return;
			case SymboliclinkPackage.SYMBOLIC_LINKS__DOC_TYPE:
				setDocType(DOC_TYPE_EDEFAULT);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINKS__LINK_REFERENCES:
				getLinkReferences().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SymboliclinkPackage.SYMBOLIC_LINKS__LINK_OBJECTS:
				return linkObjects != null && !linkObjects.isEmpty();
			case SymboliclinkPackage.SYMBOLIC_LINKS__DOC_TYPE:
				return DOC_TYPE_EDEFAULT == null ? docType != null : !DOC_TYPE_EDEFAULT.equals(docType);
			case SymboliclinkPackage.SYMBOLIC_LINKS__LINK_REFERENCES:
				return linkReferences != null && !linkReferences.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (docType: ");
		result.append(docType);
		result.append(')');
		return result.toString();
	}

} //SymbolicLinksImpl
