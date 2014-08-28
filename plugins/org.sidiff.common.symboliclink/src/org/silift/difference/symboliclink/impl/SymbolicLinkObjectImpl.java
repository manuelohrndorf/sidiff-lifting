/**
 */
package org.silift.difference.symboliclink.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
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
	public float getReliability() {
		return reliability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
	public EList<SymbolicLinkReference> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<SymbolicLinkReference>(SymbolicLinkReference.class, this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SymbolicLinkReference> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<SymbolicLinkReference>(SymbolicLinkReference.class, this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET);
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<SymbolicLinkReference> getOutgoings(EReference type) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

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
