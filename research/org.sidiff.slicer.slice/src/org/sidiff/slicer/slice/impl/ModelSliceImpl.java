/**
 */
package org.sidiff.slicer.slice.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.slice.SlicePackage;
import org.sidiff.slicer.slice.SlicedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Slice</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.slice.impl.ModelSliceImpl#getSlicedElements <em>Sliced Elements</em>}</li>
 *   <li>{@link org.sidiff.slicer.slice.impl.ModelSliceImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelSliceImpl extends MinimalEObjectImpl.Container implements ModelSlice {
	/**
	 * The cached value of the '{@link #getSlicedElements() <em>Sliced Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlicedElements()
	 * @generated
	 * @ordered
	 */
	protected EList<SlicedElement> slicedElements;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelSliceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlicePackage.Literals.MODEL_SLICE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SlicedElement> getSlicedElements() {
		if (slicedElements == null) {
			slicedElements = new EObjectContainmentEList<SlicedElement>(SlicedElement.class, this,
					SlicePackage.MODEL_SLICE__SLICED_ELEMENTS);
		}
		return slicedElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EPackage> getType() {
		if (type == null) {
			type = new EObjectResolvingEList<EPackage>(EPackage.class, this, SlicePackage.MODEL_SLICE__TYPE);
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case SlicePackage.MODEL_SLICE__SLICED_ELEMENTS:
			return ((InternalEList<?>) getSlicedElements()).basicRemove(otherEnd, msgs);
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
		case SlicePackage.MODEL_SLICE__SLICED_ELEMENTS:
			return getSlicedElements();
		case SlicePackage.MODEL_SLICE__TYPE:
			return getType();
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
		case SlicePackage.MODEL_SLICE__SLICED_ELEMENTS:
			getSlicedElements().clear();
			getSlicedElements().addAll((Collection<? extends SlicedElement>) newValue);
			return;
		case SlicePackage.MODEL_SLICE__TYPE:
			getType().clear();
			getType().addAll((Collection<? extends EPackage>) newValue);
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
		case SlicePackage.MODEL_SLICE__SLICED_ELEMENTS:
			getSlicedElements().clear();
			return;
		case SlicePackage.MODEL_SLICE__TYPE:
			getType().clear();
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
		case SlicePackage.MODEL_SLICE__SLICED_ELEMENTS:
			return slicedElements != null && !slicedElements.isEmpty();
		case SlicePackage.MODEL_SLICE__TYPE:
			return type != null && !type.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ModelSliceImpl
