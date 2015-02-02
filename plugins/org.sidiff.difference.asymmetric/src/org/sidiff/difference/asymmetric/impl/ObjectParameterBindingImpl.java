/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterMapping;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object Parameter Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.ObjectParameterBindingImpl#getActualA <em>Actual A</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.ObjectParameterBindingImpl#getActualB <em>Actual B</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.ObjectParameterBindingImpl#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.ObjectParameterBindingImpl#getIncoming <em>Incoming</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ObjectParameterBindingImpl extends ParameterBindingImpl implements ObjectParameterBinding {
	/**
	 * The cached value of the '{@link #getActualA() <em>Actual A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActualA()
	 * @generated
	 * @ordered
	 */
	protected EObject actualA;

	/**
	 * The cached value of the '{@link #getActualB() <em>Actual B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActualB()
	 * @generated
	 * @ordered
	 */
	protected EObject actualB;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterMapping> outgoing;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected ParameterMapping incoming;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ObjectParameterBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AsymmetricPackage.Literals.OBJECT_PARAMETER_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getActualA() {
		if (actualA != null && actualA.eIsProxy()) {
			InternalEObject oldActualA = (InternalEObject)actualA;
			actualA = eResolveProxy(oldActualA);
			if (actualA != oldActualA) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_A, oldActualA, actualA));
			}
		}
		return actualA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetActualA() {
		return actualA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActualA(EObject newActualA) {
		EObject oldActualA = actualA;
		actualA = newActualA;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_A, oldActualA, actualA));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getActualB() {
		if (actualB != null && actualB.eIsProxy()) {
			InternalEObject oldActualB = (InternalEObject)actualB;
			actualB = eResolveProxy(oldActualB);
			if (actualB != oldActualB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_B, oldActualB, actualB));
			}
		}
		return actualB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetActualB() {
		return actualB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActualB(EObject newActualB) {
		EObject oldActualB = actualB;
		actualB = newActualB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_B, oldActualB, actualB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterMapping> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<ParameterMapping>(ParameterMapping.class, this, AsymmetricPackage.OBJECT_PARAMETER_BINDING__OUTGOING, AsymmetricPackage.PARAMETER_MAPPING__SOURCE);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterMapping getIncoming() {
		if (incoming != null && incoming.eIsProxy()) {
			InternalEObject oldIncoming = (InternalEObject)incoming;
			incoming = (ParameterMapping)eResolveProxy(oldIncoming);
			if (incoming != oldIncoming) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING, oldIncoming, incoming));
			}
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterMapping basicGetIncoming() {
		return incoming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIncoming(ParameterMapping newIncoming, NotificationChain msgs) {
		ParameterMapping oldIncoming = incoming;
		incoming = newIncoming;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING, oldIncoming, newIncoming);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIncoming(ParameterMapping newIncoming) {
		if (newIncoming != incoming) {
			NotificationChain msgs = null;
			if (incoming != null)
				msgs = ((InternalEObject)incoming).eInverseRemove(this, AsymmetricPackage.PARAMETER_MAPPING__TARGET, ParameterMapping.class, msgs);
			if (newIncoming != null)
				msgs = ((InternalEObject)newIncoming).eInverseAdd(this, AsymmetricPackage.PARAMETER_MAPPING__TARGET, ParameterMapping.class, msgs);
			msgs = basicSetIncoming(newIncoming, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING, newIncoming, newIncoming));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isMappingTarget() {
		return getIncoming() != null;
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
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__OUTGOING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoing()).basicAdd(otherEnd, msgs);
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING:
				if (incoming != null)
					msgs = ((InternalEObject)incoming).eInverseRemove(this, AsymmetricPackage.PARAMETER_MAPPING__TARGET, ParameterMapping.class, msgs);
				return basicSetIncoming((ParameterMapping)otherEnd, msgs);
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
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__OUTGOING:
				return ((InternalEList<?>)getOutgoing()).basicRemove(otherEnd, msgs);
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING:
				return basicSetIncoming(null, msgs);
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
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_A:
				if (resolve) return getActualA();
				return basicGetActualA();
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_B:
				if (resolve) return getActualB();
				return basicGetActualB();
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__OUTGOING:
				return getOutgoing();
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING:
				if (resolve) return getIncoming();
				return basicGetIncoming();
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
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_A:
				setActualA((EObject)newValue);
				return;
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_B:
				setActualB((EObject)newValue);
				return;
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__OUTGOING:
				getOutgoing().clear();
				getOutgoing().addAll((Collection<? extends ParameterMapping>)newValue);
				return;
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING:
				setIncoming((ParameterMapping)newValue);
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
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_A:
				setActualA((EObject)null);
				return;
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_B:
				setActualB((EObject)null);
				return;
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__OUTGOING:
				getOutgoing().clear();
				return;
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING:
				setIncoming((ParameterMapping)null);
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
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_A:
				return actualA != null;
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__ACTUAL_B:
				return actualB != null;
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING:
				return incoming != null;
		}
		return super.eIsSet(featureID);
	}

} //ObjectParameterBindingImpl
