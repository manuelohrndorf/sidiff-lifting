/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel.impl;

import differencemodel.Correspondence;
import differencemodel.DifferencemodelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link differencemodel.impl.CorrespondenceImpl#getObjA <em>Obj A</em>}</li>
 *   <li>{@link differencemodel.impl.CorrespondenceImpl#getObjB <em>Obj B</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CorrespondenceImpl extends EObjectImpl implements Correspondence {
	/**
	 * The cached value of the '{@link #getObjA() <em>Obj A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjA()
	 * @generated
	 * @ordered
	 */
	protected EObject objA;

	/**
	 * The cached value of the '{@link #getObjB() <em>Obj B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjB()
	 * @generated
	 * @ordered
	 */
	protected EObject objB;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CorrespondenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DifferencemodelPackage.Literals.CORRESPONDENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getObjA() {
		if (objA != null && objA.eIsProxy()) {
			InternalEObject oldObjA = (InternalEObject)objA;
			objA = eResolveProxy(oldObjA);
			if (objA != oldObjA) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DifferencemodelPackage.CORRESPONDENCE__OBJ_A, oldObjA, objA));
			}
		}
		return objA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetObjA() {
		return objA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjA(EObject newObjA) {
		EObject oldObjA = objA;
		objA = newObjA;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifferencemodelPackage.CORRESPONDENCE__OBJ_A, oldObjA, objA));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getObjB() {
		if (objB != null && objB.eIsProxy()) {
			InternalEObject oldObjB = (InternalEObject)objB;
			objB = eResolveProxy(oldObjB);
			if (objB != oldObjB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DifferencemodelPackage.CORRESPONDENCE__OBJ_B, oldObjB, objB));
			}
		}
		return objB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetObjB() {
		return objB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjB(EObject newObjB) {
		EObject oldObjB = objB;
		objB = newObjB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifferencemodelPackage.CORRESPONDENCE__OBJ_B, oldObjB, objB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DifferencemodelPackage.CORRESPONDENCE__OBJ_A:
				if (resolve) return getObjA();
				return basicGetObjA();
			case DifferencemodelPackage.CORRESPONDENCE__OBJ_B:
				if (resolve) return getObjB();
				return basicGetObjB();
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
			case DifferencemodelPackage.CORRESPONDENCE__OBJ_A:
				setObjA((EObject)newValue);
				return;
			case DifferencemodelPackage.CORRESPONDENCE__OBJ_B:
				setObjB((EObject)newValue);
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
			case DifferencemodelPackage.CORRESPONDENCE__OBJ_A:
				setObjA((EObject)null);
				return;
			case DifferencemodelPackage.CORRESPONDENCE__OBJ_B:
				setObjB((EObject)null);
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
			case DifferencemodelPackage.CORRESPONDENCE__OBJ_A:
				return objA != null;
			case DifferencemodelPackage.CORRESPONDENCE__OBJ_B:
				return objB != null;
		}
		return super.eIsSet(featureID);
	}

} //CorrespondenceImpl
