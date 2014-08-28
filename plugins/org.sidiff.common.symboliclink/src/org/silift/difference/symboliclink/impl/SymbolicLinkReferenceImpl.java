/**
 */
package org.silift.difference.symboliclink.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkReference;
import org.silift.difference.symboliclink.SymboliclinkPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Symbolic Link Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinkReferenceImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinkReferenceImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.silift.difference.symboliclink.impl.SymbolicLinkReferenceImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SymbolicLinkReferenceImpl extends MinimalEObjectImpl.Container implements SymbolicLinkReference {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EReference type;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected SymbolicLinkObject source;
	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected SymbolicLinkObject target;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbolicLinkReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymboliclinkPackage.Literals.SYMBOLIC_LINK_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (EReference)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EReference newType) {
		EReference oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolicLinkObject getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (SymbolicLinkObject)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolicLinkObject basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(SymbolicLinkObject newSource, NotificationChain msgs) {
		SymbolicLinkObject oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE, oldSource, newSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(SymbolicLinkObject newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING, SymbolicLinkObject.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING, SymbolicLinkObject.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolicLinkObject getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (SymbolicLinkObject)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolicLinkObject basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(SymbolicLinkObject newTarget, NotificationChain msgs) {
		SymbolicLinkObject oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(SymbolicLinkObject newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING, SymbolicLinkObject.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING, SymbolicLinkObject.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE:
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__OUTGOING, SymbolicLinkObject.class, msgs);
				return basicSetSource((SymbolicLinkObject)otherEnd, msgs);
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET:
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, SymboliclinkPackage.SYMBOLIC_LINK_OBJECT__INCOMING, SymbolicLinkObject.class, msgs);
				return basicSetTarget((SymbolicLinkObject)otherEnd, msgs);
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
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE:
				return basicSetSource(null, msgs);
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET:
				return basicSetTarget(null, msgs);
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
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TYPE:
				setType((EReference)newValue);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE:
				setSource((SymbolicLinkObject)newValue);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET:
				setTarget((SymbolicLinkObject)newValue);
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
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TYPE:
				setType((EReference)null);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE:
				setSource((SymbolicLinkObject)null);
				return;
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET:
				setTarget((SymbolicLinkObject)null);
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
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TYPE:
				return type != null;
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__SOURCE:
				return source != null;
			case SymboliclinkPackage.SYMBOLIC_LINK_REFERENCE__TARGET:
				return target != null;
		}
		return super.eIsSet(featureID);
	}

} //SymbolicLinkReferenceImpl
