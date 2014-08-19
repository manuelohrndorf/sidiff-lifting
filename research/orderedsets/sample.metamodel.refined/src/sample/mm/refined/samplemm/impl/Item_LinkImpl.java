/**
 */
package sample.mm.refined.samplemm.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import sample.mm.refined.samplemm.Item;
import sample.mm.refined.samplemm.Item_Link;
import sample.mm.refined.samplemm.SamplemmPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link sample.mm.refined.samplemm.impl.Item_LinkImpl#getPre <em>Pre</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.impl.Item_LinkImpl#getSucc <em>Succ</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class Item_LinkImpl extends EObjectImpl implements Item_Link {
	/**
	 * The cached value of the '{@link #getPre() <em>Pre</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPre()
	 * @generated
	 * @ordered
	 */
	protected Item pre;

	/**
	 * The cached value of the '{@link #getSucc() <em>Succ</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSucc()
	 * @generated
	 * @ordered
	 */
	protected Item succ;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Item_LinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamplemmPackage.Literals.ITEM_LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Item getPre() {
		if (pre != null && pre.eIsProxy()) {
			InternalEObject oldPre = (InternalEObject)pre;
			pre = (Item)eResolveProxy(oldPre);
			if (pre != oldPre) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamplemmPackage.ITEM_LINK__PRE, oldPre, pre));
			}
		}
		return pre;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Item basicGetPre() {
		return pre;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPre(Item newPre) {
		Item oldPre = pre;
		pre = newPre;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplemmPackage.ITEM_LINK__PRE, oldPre, pre));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Item getSucc() {
		if (succ != null && succ.eIsProxy()) {
			InternalEObject oldSucc = (InternalEObject)succ;
			succ = (Item)eResolveProxy(oldSucc);
			if (succ != oldSucc) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamplemmPackage.ITEM_LINK__SUCC, oldSucc, succ));
			}
		}
		return succ;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Item basicGetSucc() {
		return succ;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSucc(Item newSucc) {
		Item oldSucc = succ;
		succ = newSucc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplemmPackage.ITEM_LINK__SUCC, oldSucc, succ));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SamplemmPackage.ITEM_LINK__PRE:
				if (resolve) return getPre();
				return basicGetPre();
			case SamplemmPackage.ITEM_LINK__SUCC:
				if (resolve) return getSucc();
				return basicGetSucc();
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
			case SamplemmPackage.ITEM_LINK__PRE:
				setPre((Item)newValue);
				return;
			case SamplemmPackage.ITEM_LINK__SUCC:
				setSucc((Item)newValue);
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
			case SamplemmPackage.ITEM_LINK__PRE:
				setPre((Item)null);
				return;
			case SamplemmPackage.ITEM_LINK__SUCC:
				setSucc((Item)null);
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
			case SamplemmPackage.ITEM_LINK__PRE:
				return pre != null;
			case SamplemmPackage.ITEM_LINK__SUCC:
				return succ != null;
		}
		return super.eIsSet(featureID);
	}

} //Item_LinkImpl
