/**
 */
package sample.mm.refined.samplemm.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import sample.mm.refined.samplemm.CList;
import sample.mm.refined.samplemm.Item;
import sample.mm.refined.samplemm.NCList;
import sample.mm.refined.samplemm.SampleMetamodel;
import sample.mm.refined.samplemm.SamplemmPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sample Metamodel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link sample.mm.refined.samplemm.impl.SampleMetamodelImpl#getItems <em>Items</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.impl.SampleMetamodelImpl#getNcLists <em>Nc Lists</em>}</li>
 *   <li>{@link sample.mm.refined.samplemm.impl.SampleMetamodelImpl#getCLists <em>CLists</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SampleMetamodelImpl extends EObjectImpl implements SampleMetamodel {
	/**
	 * The cached value of the '{@link #getItems() <em>Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItems()
	 * @generated
	 * @ordered
	 */
	protected EList<Item> items;

	/**
	 * The cached value of the '{@link #getNcLists() <em>Nc Lists</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNcLists()
	 * @generated
	 * @ordered
	 */
	protected EList<NCList> ncLists;

	/**
	 * The cached value of the '{@link #getCLists() <em>CLists</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCLists()
	 * @generated
	 * @ordered
	 */
	protected EList<CList> cLists;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SampleMetamodelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamplemmPackage.Literals.SAMPLE_METAMODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Item> getItems() {
		if (items == null) {
			items = new EObjectContainmentEList<Item>(Item.class, this, SamplemmPackage.SAMPLE_METAMODEL__ITEMS);
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NCList> getNcLists() {
		if (ncLists == null) {
			ncLists = new EObjectContainmentEList<NCList>(NCList.class, this, SamplemmPackage.SAMPLE_METAMODEL__NC_LISTS);
		}
		return ncLists;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CList> getCLists() {
		if (cLists == null) {
			cLists = new EObjectContainmentEList<CList>(CList.class, this, SamplemmPackage.SAMPLE_METAMODEL__CLISTS);
		}
		return cLists;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamplemmPackage.SAMPLE_METAMODEL__ITEMS:
				return ((InternalEList<?>)getItems()).basicRemove(otherEnd, msgs);
			case SamplemmPackage.SAMPLE_METAMODEL__NC_LISTS:
				return ((InternalEList<?>)getNcLists()).basicRemove(otherEnd, msgs);
			case SamplemmPackage.SAMPLE_METAMODEL__CLISTS:
				return ((InternalEList<?>)getCLists()).basicRemove(otherEnd, msgs);
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
			case SamplemmPackage.SAMPLE_METAMODEL__ITEMS:
				return getItems();
			case SamplemmPackage.SAMPLE_METAMODEL__NC_LISTS:
				return getNcLists();
			case SamplemmPackage.SAMPLE_METAMODEL__CLISTS:
				return getCLists();
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
			case SamplemmPackage.SAMPLE_METAMODEL__ITEMS:
				getItems().clear();
				getItems().addAll((Collection<? extends Item>)newValue);
				return;
			case SamplemmPackage.SAMPLE_METAMODEL__NC_LISTS:
				getNcLists().clear();
				getNcLists().addAll((Collection<? extends NCList>)newValue);
				return;
			case SamplemmPackage.SAMPLE_METAMODEL__CLISTS:
				getCLists().clear();
				getCLists().addAll((Collection<? extends CList>)newValue);
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
			case SamplemmPackage.SAMPLE_METAMODEL__ITEMS:
				getItems().clear();
				return;
			case SamplemmPackage.SAMPLE_METAMODEL__NC_LISTS:
				getNcLists().clear();
				return;
			case SamplemmPackage.SAMPLE_METAMODEL__CLISTS:
				getCLists().clear();
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
			case SamplemmPackage.SAMPLE_METAMODEL__ITEMS:
				return items != null && !items.isEmpty();
			case SamplemmPackage.SAMPLE_METAMODEL__NC_LISTS:
				return ncLists != null && !ncLists.isEmpty();
			case SamplemmPackage.SAMPLE_METAMODEL__CLISTS:
				return cLists != null && !cLists.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SampleMetamodelImpl
