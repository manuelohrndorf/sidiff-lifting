/**
 */
package org.sidiff.difference.asymmetric.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.Dependency;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dependency Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.DependencyContainerImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.DependencyContainerImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.DependencyContainerImpl#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DependencyContainerImpl extends EObjectImpl implements DependencyContainer {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected OperationInvocation source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected OperationInvocation target;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<Dependency> dependencies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DependencyContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AsymmetricPackage.Literals.DEPENDENCY_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OperationInvocation getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (OperationInvocation)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationInvocation basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(OperationInvocation newSource, NotificationChain msgs) {
		OperationInvocation oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE, oldSource, newSource);
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
	public void setSource(OperationInvocation newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, AsymmetricPackage.OPERATION_INVOCATION__OUTGOING, OperationInvocation.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, AsymmetricPackage.OPERATION_INVOCATION__OUTGOING, OperationInvocation.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OperationInvocation getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (OperationInvocation)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationInvocation basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(OperationInvocation newTarget, NotificationChain msgs) {
		OperationInvocation oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET, oldTarget, newTarget);
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
	public void setTarget(OperationInvocation newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, AsymmetricPackage.OPERATION_INVOCATION__INCOMING, OperationInvocation.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, AsymmetricPackage.OPERATION_INVOCATION__INCOMING, OperationInvocation.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Dependency> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectContainmentEList<Dependency>(Dependency.class, this, AsymmetricPackage.DEPENDENCY_CONTAINER__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE:
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, AsymmetricPackage.OPERATION_INVOCATION__OUTGOING, OperationInvocation.class, msgs);
				return basicSetSource((OperationInvocation)otherEnd, msgs);
			case AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET:
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, AsymmetricPackage.OPERATION_INVOCATION__INCOMING, OperationInvocation.class, msgs);
				return basicSetTarget((OperationInvocation)otherEnd, msgs);
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
			case AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE:
				return basicSetSource(null, msgs);
			case AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET:
				return basicSetTarget(null, msgs);
			case AsymmetricPackage.DEPENDENCY_CONTAINER__DEPENDENCIES:
				return ((InternalEList<?>)getDependencies()).basicRemove(otherEnd, msgs);
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
			case AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case AsymmetricPackage.DEPENDENCY_CONTAINER__DEPENDENCIES:
				return getDependencies();
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
			case AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE:
				setSource((OperationInvocation)newValue);
				return;
			case AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET:
				setTarget((OperationInvocation)newValue);
				return;
			case AsymmetricPackage.DEPENDENCY_CONTAINER__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends Dependency>)newValue);
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
			case AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE:
				setSource((OperationInvocation)null);
				return;
			case AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET:
				setTarget((OperationInvocation)null);
				return;
			case AsymmetricPackage.DEPENDENCY_CONTAINER__DEPENDENCIES:
				getDependencies().clear();
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
			case AsymmetricPackage.DEPENDENCY_CONTAINER__SOURCE:
				return source != null;
			case AsymmetricPackage.DEPENDENCY_CONTAINER__TARGET:
				return target != null;
			case AsymmetricPackage.DEPENDENCY_CONTAINER__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DependencyContainerImpl
