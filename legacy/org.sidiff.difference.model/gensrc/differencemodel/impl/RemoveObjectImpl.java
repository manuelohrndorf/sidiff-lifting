/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel.impl;

import differencemodel.DifferencemodelPackage;
import differencemodel.RemoveObject;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remove Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link differencemodel.impl.RemoveObjectImpl#getObj <em>Obj</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RemoveObjectImpl extends ChangeImpl implements RemoveObject {
	/**
	 * The cached value of the '{@link #getObj() <em>Obj</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObj()
	 * @generated
	 * @ordered
	 */
	protected EObject obj;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoveObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DifferencemodelPackage.Literals.REMOVE_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getObj() {
		if (obj != null && obj.eIsProxy()) {
			InternalEObject oldObj = (InternalEObject)obj;
			obj = eResolveProxy(oldObj);
			if (obj != oldObj) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DifferencemodelPackage.REMOVE_OBJECT__OBJ, oldObj, obj));
			}
		}
		return obj;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetObj() {
		return obj;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObj(EObject newObj) {
		EObject oldObj = obj;
		obj = newObj;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifferencemodelPackage.REMOVE_OBJECT__OBJ, oldObj, obj));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DifferencemodelPackage.REMOVE_OBJECT__OBJ:
				if (resolve) return getObj();
				return basicGetObj();
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
			case DifferencemodelPackage.REMOVE_OBJECT__OBJ:
				setObj((EObject)newValue);
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
			case DifferencemodelPackage.REMOVE_OBJECT__OBJ:
				setObj((EObject)null);
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
			case DifferencemodelPackage.REMOVE_OBJECT__OBJ:
				return obj != null;
		}
		return super.eIsSet(featureID);
	}

} //RemoveObjectImpl
