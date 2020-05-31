/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterMapping;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.ParameterMappingImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.ParameterMappingImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterMappingImpl extends EObjectImpl implements ParameterMapping {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected ObjectParameterBinding source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected ObjectParameterBinding target;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AsymmetricPackage.Literals.PARAMETER_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ObjectParameterBinding getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (ObjectParameterBinding)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.PARAMETER_MAPPING__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectParameterBinding basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(ObjectParameterBinding newSource, NotificationChain msgs) {
		ObjectParameterBinding oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AsymmetricPackage.PARAMETER_MAPPING__SOURCE, oldSource, newSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSource(ObjectParameterBinding newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, AsymmetricPackage.OBJECT_PARAMETER_BINDING__OUTGOING, ObjectParameterBinding.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, AsymmetricPackage.OBJECT_PARAMETER_BINDING__OUTGOING, ObjectParameterBinding.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.PARAMETER_MAPPING__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ObjectParameterBinding getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (ObjectParameterBinding)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.PARAMETER_MAPPING__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectParameterBinding basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(ObjectParameterBinding newTarget, NotificationChain msgs) {
		ObjectParameterBinding oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AsymmetricPackage.PARAMETER_MAPPING__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTarget(ObjectParameterBinding newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING, ObjectParameterBinding.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING, ObjectParameterBinding.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.PARAMETER_MAPPING__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AsymmetricPackage.PARAMETER_MAPPING__SOURCE:
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, AsymmetricPackage.OBJECT_PARAMETER_BINDING__OUTGOING, ObjectParameterBinding.class, msgs);
				return basicSetSource((ObjectParameterBinding)otherEnd, msgs);
			case AsymmetricPackage.PARAMETER_MAPPING__TARGET:
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, AsymmetricPackage.OBJECT_PARAMETER_BINDING__INCOMING, ObjectParameterBinding.class, msgs);
				return basicSetTarget((ObjectParameterBinding)otherEnd, msgs);
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
			case AsymmetricPackage.PARAMETER_MAPPING__SOURCE:
				return basicSetSource(null, msgs);
			case AsymmetricPackage.PARAMETER_MAPPING__TARGET:
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
			case AsymmetricPackage.PARAMETER_MAPPING__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case AsymmetricPackage.PARAMETER_MAPPING__TARGET:
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
			case AsymmetricPackage.PARAMETER_MAPPING__SOURCE:
				setSource((ObjectParameterBinding)newValue);
				return;
			case AsymmetricPackage.PARAMETER_MAPPING__TARGET:
				setTarget((ObjectParameterBinding)newValue);
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
			case AsymmetricPackage.PARAMETER_MAPPING__SOURCE:
				setSource((ObjectParameterBinding)null);
				return;
			case AsymmetricPackage.PARAMETER_MAPPING__TARGET:
				setTarget((ObjectParameterBinding)null);
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
			case AsymmetricPackage.PARAMETER_MAPPING__SOURCE:
				return source != null;
			case AsymmetricPackage.PARAMETER_MAPPING__TARGET:
				return target != null;
		}
		return super.eIsSet(featureID);
	}

} //ParameterMappingImpl
