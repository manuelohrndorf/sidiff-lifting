/**
 */
package sample.mm.refined.samplemm.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import sample.mm.refined.samplemm.Item;
import sample.mm.refined.samplemm.SamplemmPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link sample.mm.refined.samplemm.impl.ItemImpl#getName <em>Name</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.impl.ItemImpl#getPre <em>Pre</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.impl.ItemImpl#getSucc <em>Succ</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ItemImpl extends EObjectImpl implements Item {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

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
	protected ItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamplemmPackage.Literals.ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplemmPackage.ITEM__NAME, oldName, name));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamplemmPackage.ITEM__PRE, oldPre, pre));
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
	public NotificationChain basicSetPre(Item newPre, NotificationChain msgs) {
		Item oldPre = pre;
		pre = newPre;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamplemmPackage.ITEM__PRE, oldPre, newPre);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPre(Item newPre) {
		if (newPre != pre) {
			NotificationChain msgs = null;
			if (pre != null)
				msgs = ((InternalEObject)pre).eInverseRemove(this, SamplemmPackage.ITEM__SUCC, Item.class, msgs);
			if (newPre != null)
				msgs = ((InternalEObject)newPre).eInverseAdd(this, SamplemmPackage.ITEM__SUCC, Item.class, msgs);
			msgs = basicSetPre(newPre, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplemmPackage.ITEM__PRE, newPre, newPre));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamplemmPackage.ITEM__SUCC, oldSucc, succ));
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
	public NotificationChain basicSetSucc(Item newSucc, NotificationChain msgs) {
		Item oldSucc = succ;
		succ = newSucc;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamplemmPackage.ITEM__SUCC, oldSucc, newSucc);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSucc(Item newSucc) {
		if (newSucc != succ) {
			NotificationChain msgs = null;
			if (succ != null)
				msgs = ((InternalEObject)succ).eInverseRemove(this, SamplemmPackage.ITEM__PRE, Item.class, msgs);
			if (newSucc != null)
				msgs = ((InternalEObject)newSucc).eInverseAdd(this, SamplemmPackage.ITEM__PRE, Item.class, msgs);
			msgs = basicSetSucc(newSucc, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamplemmPackage.ITEM__SUCC, newSucc, newSucc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamplemmPackage.ITEM__PRE:
				if (pre != null)
					msgs = ((InternalEObject)pre).eInverseRemove(this, SamplemmPackage.ITEM__SUCC, Item.class, msgs);
				return basicSetPre((Item)otherEnd, msgs);
			case SamplemmPackage.ITEM__SUCC:
				if (succ != null)
					msgs = ((InternalEObject)succ).eInverseRemove(this, SamplemmPackage.ITEM__PRE, Item.class, msgs);
				return basicSetSucc((Item)otherEnd, msgs);
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
			case SamplemmPackage.ITEM__PRE:
				return basicSetPre(null, msgs);
			case SamplemmPackage.ITEM__SUCC:
				return basicSetSucc(null, msgs);
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
			case SamplemmPackage.ITEM__NAME:
				return getName();
			case SamplemmPackage.ITEM__PRE:
				if (resolve) return getPre();
				return basicGetPre();
			case SamplemmPackage.ITEM__SUCC:
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
			case SamplemmPackage.ITEM__NAME:
				setName((String)newValue);
				return;
			case SamplemmPackage.ITEM__PRE:
				setPre((Item)newValue);
				return;
			case SamplemmPackage.ITEM__SUCC:
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
			case SamplemmPackage.ITEM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SamplemmPackage.ITEM__PRE:
				setPre((Item)null);
				return;
			case SamplemmPackage.ITEM__SUCC:
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
			case SamplemmPackage.ITEM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SamplemmPackage.ITEM__PRE:
				return pre != null;
			case SamplemmPackage.ITEM__SUCC:
				return succ != null;
		}
		return super.eIsSet(featureID);
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ItemImpl
