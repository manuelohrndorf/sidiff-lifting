/**
 */
package org.sidiff.slicing.slicingmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.sidiff.slicing.slicingmodel.Slicing;
import org.sidiff.slicing.slicingmodel.SlicingmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Slicing</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.slicingmodel.impl.SlicingImpl#getUri <em>Uri</em>}</li>
 *   <li>{@link org.sidiff.slicing.slicingmodel.impl.SlicingImpl#getSlicedContextElements <em>Sliced Context Elements</em>}</li>
 *   <li>{@link org.sidiff.slicing.slicingmodel.impl.SlicingImpl#getSlicedBoundaryElements <em>Sliced Boundary Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SlicingImpl extends MinimalEObjectImpl.Container implements Slicing {
	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected EList<String> uri;

	/**
	 * The cached value of the '{@link #getSlicedContextElements() <em>Sliced Context Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlicedContextElements()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> slicedContextElements;

	/**
	 * The cached value of the '{@link #getSlicedBoundaryElements() <em>Sliced Boundary Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlicedBoundaryElements()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> slicedBoundaryElements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SlicingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlicingmodelPackage.Literals.SLICING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getUri() {
		if (uri == null) {
			uri = new EDataTypeUniqueEList<String>(String.class, this, SlicingmodelPackage.SLICING__URI);
		}
		return uri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getSlicedContextElements() {
		if (slicedContextElements == null) {
			slicedContextElements = new EObjectResolvingEList<EObject>(EObject.class, this, SlicingmodelPackage.SLICING__SLICED_CONTEXT_ELEMENTS);
		}
		return slicedContextElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getSlicedBoundaryElements() {
		if (slicedBoundaryElements == null) {
			slicedBoundaryElements = new EObjectResolvingEList<EObject>(EObject.class, this, SlicingmodelPackage.SLICING__SLICED_BOUNDARY_ELEMENTS);
		}
		return slicedBoundaryElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SlicingmodelPackage.SLICING__URI:
				return getUri();
			case SlicingmodelPackage.SLICING__SLICED_CONTEXT_ELEMENTS:
				return getSlicedContextElements();
			case SlicingmodelPackage.SLICING__SLICED_BOUNDARY_ELEMENTS:
				return getSlicedBoundaryElements();
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
			case SlicingmodelPackage.SLICING__URI:
				getUri().clear();
				getUri().addAll((Collection<? extends String>)newValue);
				return;
			case SlicingmodelPackage.SLICING__SLICED_CONTEXT_ELEMENTS:
				getSlicedContextElements().clear();
				getSlicedContextElements().addAll((Collection<? extends EObject>)newValue);
				return;
			case SlicingmodelPackage.SLICING__SLICED_BOUNDARY_ELEMENTS:
				getSlicedBoundaryElements().clear();
				getSlicedBoundaryElements().addAll((Collection<? extends EObject>)newValue);
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
			case SlicingmodelPackage.SLICING__URI:
				getUri().clear();
				return;
			case SlicingmodelPackage.SLICING__SLICED_CONTEXT_ELEMENTS:
				getSlicedContextElements().clear();
				return;
			case SlicingmodelPackage.SLICING__SLICED_BOUNDARY_ELEMENTS:
				getSlicedBoundaryElements().clear();
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
			case SlicingmodelPackage.SLICING__URI:
				return uri != null && !uri.isEmpty();
			case SlicingmodelPackage.SLICING__SLICED_CONTEXT_ELEMENTS:
				return slicedContextElements != null && !slicedContextElements.isEmpty();
			case SlicingmodelPackage.SLICING__SLICED_BOUNDARY_ELEMENTS:
				return slicedBoundaryElements != null && !slicedBoundaryElements.isEmpty();
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
		result.append(" (uri: ");
		result.append(uri);
		result.append(')');
		return result.toString();
	}

} //SlicingImpl
