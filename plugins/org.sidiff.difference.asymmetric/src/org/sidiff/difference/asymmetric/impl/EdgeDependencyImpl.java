/**
 */
package org.sidiff.difference.asymmetric.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.EdgeDependency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.EdgeDependencyImpl#getSrcObject <em>Src Object</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.EdgeDependencyImpl#getTgtObject <em>Tgt Object</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.EdgeDependencyImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EdgeDependencyImpl extends DependencyImpl implements EdgeDependency {
	/**
	 * The cached value of the '{@link #getSrcObject() <em>Src Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcObject()
	 * @generated
	 * @ordered
	 */
	protected EObject srcObject;

	/**
	 * The cached value of the '{@link #getTgtObject() <em>Tgt Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTgtObject()
	 * @generated
	 * @ordered
	 */
	protected EObject tgtObject;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EdgeDependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AsymmetricPackage.Literals.EDGE_DEPENDENCY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getSrcObject() {
		if (srcObject != null && srcObject.eIsProxy()) {
			InternalEObject oldSrcObject = (InternalEObject)srcObject;
			srcObject = eResolveProxy(oldSrcObject);
			if (srcObject != oldSrcObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.EDGE_DEPENDENCY__SRC_OBJECT, oldSrcObject, srcObject));
			}
		}
		return srcObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetSrcObject() {
		return srcObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSrcObject(EObject newSrcObject) {
		EObject oldSrcObject = srcObject;
		srcObject = newSrcObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.EDGE_DEPENDENCY__SRC_OBJECT, oldSrcObject, srcObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getTgtObject() {
		if (tgtObject != null && tgtObject.eIsProxy()) {
			InternalEObject oldTgtObject = (InternalEObject)tgtObject;
			tgtObject = eResolveProxy(oldTgtObject);
			if (tgtObject != oldTgtObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.EDGE_DEPENDENCY__TGT_OBJECT, oldTgtObject, tgtObject));
			}
		}
		return tgtObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetTgtObject() {
		return tgtObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTgtObject(EObject newTgtObject) {
		EObject oldTgtObject = tgtObject;
		tgtObject = newTgtObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.EDGE_DEPENDENCY__TGT_OBJECT, oldTgtObject, tgtObject));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AsymmetricPackage.EDGE_DEPENDENCY__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.EDGE_DEPENDENCY__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AsymmetricPackage.EDGE_DEPENDENCY__SRC_OBJECT:
				if (resolve) return getSrcObject();
				return basicGetSrcObject();
			case AsymmetricPackage.EDGE_DEPENDENCY__TGT_OBJECT:
				if (resolve) return getTgtObject();
				return basicGetTgtObject();
			case AsymmetricPackage.EDGE_DEPENDENCY__TYPE:
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AsymmetricPackage.EDGE_DEPENDENCY__SRC_OBJECT:
				setSrcObject((EObject)newValue);
				return;
			case AsymmetricPackage.EDGE_DEPENDENCY__TGT_OBJECT:
				setTgtObject((EObject)newValue);
				return;
			case AsymmetricPackage.EDGE_DEPENDENCY__TYPE:
				setType((EReference)newValue);
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
			case AsymmetricPackage.EDGE_DEPENDENCY__SRC_OBJECT:
				setSrcObject((EObject)null);
				return;
			case AsymmetricPackage.EDGE_DEPENDENCY__TGT_OBJECT:
				setTgtObject((EObject)null);
				return;
			case AsymmetricPackage.EDGE_DEPENDENCY__TYPE:
				setType((EReference)null);
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
			case AsymmetricPackage.EDGE_DEPENDENCY__SRC_OBJECT:
				return srcObject != null;
			case AsymmetricPackage.EDGE_DEPENDENCY__TGT_OBJECT:
				return tgtObject != null;
			case AsymmetricPackage.EDGE_DEPENDENCY__TYPE:
				return type != null;
		}
		return super.eIsSet(featureID);
	}

} //EdgeDependencyImpl
