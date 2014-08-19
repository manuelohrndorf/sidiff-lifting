/**
 */
package sample.mm.refined.samplemm.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import sample.mm.refined.samplemm.Item;
import sample.mm.refined.samplemm.Item_Link;
import sample.mm.refined.samplemm.NCList;
import sample.mm.refined.samplemm.SamplemmPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>NC List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link sample.mm.refined.samplemm.impl.NCListImpl#getItems <em>Items</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.impl.NCListImpl#getName <em>Name</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.impl.NCListImpl#getItem_links <em>Item links</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NCListImpl extends EObjectImpl implements NCList {
	/**
	 * The cached value of the '{@link #getItems() <em>Items</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItems()
	 * @generated
	 * @ordered
	 */
	protected EList<Item> items;

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
	 * The cached value of the '{@link #getItem_links() <em>Item links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItem_links()
	 * @generated
	 * @ordered
	 */
	protected EList<Item_Link> item_links;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NCListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamplemmPackage.Literals.NC_LIST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Item> getItems() {
		if (items == null) {
			items = new EObjectResolvingEList<Item>(Item.class, this, SamplemmPackage.NC_LIST__ITEMS);
		}
		return items;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SamplemmPackage.NC_LIST__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Item_Link> getItem_links() {
		if (item_links == null) {
			item_links = new EObjectContainmentEList<Item_Link>(Item_Link.class, this, SamplemmPackage.NC_LIST__ITEM_LINKS);
		}
		return item_links;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamplemmPackage.NC_LIST__ITEM_LINKS:
				return ((InternalEList<?>)getItem_links()).basicRemove(otherEnd, msgs);
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
			case SamplemmPackage.NC_LIST__ITEMS:
				return getItems();
			case SamplemmPackage.NC_LIST__NAME:
				return getName();
			case SamplemmPackage.NC_LIST__ITEM_LINKS:
				return getItem_links();
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
			case SamplemmPackage.NC_LIST__ITEMS:
				getItems().clear();
				getItems().addAll((Collection<? extends Item>)newValue);
				return;
			case SamplemmPackage.NC_LIST__NAME:
				setName((String)newValue);
				return;
			case SamplemmPackage.NC_LIST__ITEM_LINKS:
				getItem_links().clear();
				getItem_links().addAll((Collection<? extends Item_Link>)newValue);
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
			case SamplemmPackage.NC_LIST__ITEMS:
				getItems().clear();
				return;
			case SamplemmPackage.NC_LIST__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SamplemmPackage.NC_LIST__ITEM_LINKS:
				getItem_links().clear();
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
			case SamplemmPackage.NC_LIST__ITEMS:
				return items != null && !items.isEmpty();
			case SamplemmPackage.NC_LIST__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SamplemmPackage.NC_LIST__ITEM_LINKS:
				return item_links != null && !item_links.isEmpty();
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

} //NCListImpl
