/**
 */
package org.sidiff.slicer.slice.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.entities.Attribute;
import org.sidiff.entities.Reference;

import org.sidiff.entities.impl.ElementImpl;

import org.sidiff.slicer.slice.SlicePackage;
import org.sidiff.slicer.slice.SlicedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sliced Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.slice.impl.SlicedElementImpl#getSlicedReferences <em>Sliced References</em>}</li>
 *   <li>{@link org.sidiff.slicer.slice.impl.SlicedElementImpl#getSlicedAttributes <em>Sliced Attributes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SlicedElementImpl extends ElementImpl implements SlicedElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "(c), Robert Müller and Christopher Pietsch, Software Engineering Group, University of Siegen 2017 all rights reserved";

	/**
	 * The cached value of the '{@link #getSlicedReferences() <em>Sliced References</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlicedReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<Reference> slicedReferences;

	/**
	 * The cached value of the '{@link #getSlicedAttributes() <em>Sliced Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlicedAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> slicedAttributes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SlicedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlicePackage.Literals.SLICED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Reference> getSlicedReferences() {
		if (slicedReferences == null) {
			slicedReferences = new EObjectContainmentEList<Reference>(Reference.class, this, SlicePackage.SLICED_ELEMENT__SLICED_REFERENCES);
		}
		return slicedReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Attribute> getSlicedAttributes() {
		if (slicedAttributes == null) {
			slicedAttributes = new EObjectContainmentEList<Attribute>(Attribute.class, this, SlicePackage.SLICED_ELEMENT__SLICED_ATTRIBUTES);
		}
		return slicedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SlicePackage.SLICED_ELEMENT__SLICED_REFERENCES:
				return ((InternalEList<?>)getSlicedReferences()).basicRemove(otherEnd, msgs);
			case SlicePackage.SLICED_ELEMENT__SLICED_ATTRIBUTES:
				return ((InternalEList<?>)getSlicedAttributes()).basicRemove(otherEnd, msgs);
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
			case SlicePackage.SLICED_ELEMENT__SLICED_REFERENCES:
				return getSlicedReferences();
			case SlicePackage.SLICED_ELEMENT__SLICED_ATTRIBUTES:
				return getSlicedAttributes();
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
			case SlicePackage.SLICED_ELEMENT__SLICED_REFERENCES:
				getSlicedReferences().clear();
				getSlicedReferences().addAll((Collection<? extends Reference>)newValue);
				return;
			case SlicePackage.SLICED_ELEMENT__SLICED_ATTRIBUTES:
				getSlicedAttributes().clear();
				getSlicedAttributes().addAll((Collection<? extends Attribute>)newValue);
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
			case SlicePackage.SLICED_ELEMENT__SLICED_REFERENCES:
				getSlicedReferences().clear();
				return;
			case SlicePackage.SLICED_ELEMENT__SLICED_ATTRIBUTES:
				getSlicedAttributes().clear();
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
			case SlicePackage.SLICED_ELEMENT__SLICED_REFERENCES:
				return slicedReferences != null && !slicedReferences.isEmpty();
			case SlicePackage.SLICED_ELEMENT__SLICED_ATTRIBUTES:
				return slicedAttributes != null && !slicedAttributes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SlicedElementImpl
