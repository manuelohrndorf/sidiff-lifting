/**
 */
package org.silift.difference.symboliclink.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.silift.difference.symboliclink.SymbolicLinkAttribute;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkReference;
import org.silift.difference.symboliclink.SymboliclinkPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Symbolic Link Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinkObjectImpl#getReliability <em>Reliability</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinkObjectImpl#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinkObjectImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinkObjectImpl#getLinkAttributes <em>Link Attributes</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinkObjectImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SymbolicLinkObjectImpl extends MinimalEObjectImpl.Container implements SymbolicLinkObject {
	/**
	 * The default value of the '{@link #getReliability() <em>Reliability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReliability()
	 * @generated
	 * @ordered
	 */
	protected static final float RELIABILITY_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getReliability() <em>Reliability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReliability()
	 * @generated
	 * @ordered
	 */
	protected float reliability = RELIABILITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<SymbolicLinkReference> outgoing;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<SymbolicLinkReference> incoming;

	/**
	 * The cached value of the '{@link #getLinkAttributes() <em>Link Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<SymbolicLinkAttribute> linkAttributes;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EObject type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbolicLinkObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymboliclinkPackage.Literals.SYMBOLIC_LINK_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public float getReliability() {
		return reliability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReliability(float newReliability) {
		float oldReliability = reliability;
		reliability = newReliability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__RELIABILITY, oldReliability, reliability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SymbolicLinkReference> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<>(SymbolicLinkReference.class, this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SymbolicLinkReference> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<>(SymbolicLinkReference.class, this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET);
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SymbolicLinkAttribute> getLinkAttributes() {
		if (linkAttributes == null) {
			linkAttributes = new EObjectContainmentEList<>(SymbolicLinkAttribute.class, this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES);
		}
		return linkAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(EObject newType) {
		EObject oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<SymbolicLinkReference> getOutgoings(EReference type) {
		EList<SymbolicLinkReference> outgoings = new EObjectEList<>(SymbolicLinkReference.class, this, SymboliclinkPackage.EXTERNAL_SYMBOLIC_LINK_OBJECT___GET_OUTGOINGS__EREFERENCE);
		for(SymbolicLinkReference ref : getOutgoing()){
			if(ref.getType().equals(type)){
				outgoings.add(ref);
			}
		}
		return outgoings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public abstract int hashCode();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public abstract boolean equals(Object o);
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoing()).basicAdd(otherEnd, msgs);
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncoming()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING:
				return ((InternalEList<?>)getOutgoing()).basicRemove(otherEnd, msgs);
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING:
				return ((InternalEList<?>)getIncoming()).basicRemove(otherEnd, msgs);
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES:
				return ((InternalEList<?>)getLinkAttributes()).basicRemove(otherEnd, msgs);
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
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__RELIABILITY:
				return getReliability();
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING:
				return getOutgoing();
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING:
				return getIncoming();
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES:
				return getLinkAttributes();
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__TYPE:
				if (resolve) return getType();
				return basicGetType();
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
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__RELIABILITY:
				setReliability((Float)newValue);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING:
				getOutgoing().clear();
				getOutgoing().addAll((Collection<? extends SymbolicLinkReference>)newValue);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING:
				getIncoming().clear();
				getIncoming().addAll((Collection<? extends SymbolicLinkReference>)newValue);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES:
				getLinkAttributes().clear();
				getLinkAttributes().addAll((Collection<? extends SymbolicLinkAttribute>)newValue);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__TYPE:
				setType((EObject)newValue);
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
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__RELIABILITY:
				setReliability(RELIABILITY_EDEFAULT);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING:
				getOutgoing().clear();
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING:
				getIncoming().clear();
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES:
				getLinkAttributes().clear();
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__TYPE:
				setType((EObject)null);
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
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__RELIABILITY:
				return reliability != RELIABILITY_EDEFAULT;
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING:
				return incoming != null && !incoming.isEmpty();
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__LINK_ATTRIBUTES:
				return linkAttributes != null && !linkAttributes.isEmpty();
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__TYPE:
				return type != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT___GET_OUTGOINGS__EREFERENCE:
				return getOutgoings((EReference)arguments.get(0));
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT___HASH_CODE:
				return hashCode();
			case SymboliclinkPackage.SYMBOLIC_LINK_OBJECT___EQUALS__OBJECT:
				return equals(arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (reliability: ");
		result.append(reliability);
		result.append(')');
		return result.toString();
	}

} //SymbolicLinkObjectImpl
