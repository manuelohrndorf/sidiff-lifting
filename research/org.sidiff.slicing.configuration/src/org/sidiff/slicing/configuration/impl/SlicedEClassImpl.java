/**
 */
package org.sidiff.slicing.configuration.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.slicing.configuration.ConfigurationPackage;
import org.sidiff.slicing.configuration.SlicedBoundaryEReference;
import org.sidiff.slicing.configuration.SlicedEClass;
import org.sidiff.slicing.configuration.SlicingConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sliced EClass</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedEClassImpl#isBoundary <em>Boundary</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedEClassImpl#getSlicingConfiguration <em>Slicing Configuration</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedEClassImpl#getOutgoings <em>Outgoings</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedEClassImpl#getIncomings <em>Incomings</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedEClassImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SlicedEClassImpl extends MinimalEObjectImpl.Container implements SlicedEClass {
	/**
	 * The default value of the '{@link #isBoundary() <em>Boundary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBoundary()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOUNDARY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBoundary() <em>Boundary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBoundary()
	 * @generated
	 * @ordered
	 */
	protected boolean boundary = BOUNDARY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOutgoings() <em>Outgoings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoings()
	 * @generated
	 * @ordered
	 */
	protected EList<SlicedBoundaryEReference> outgoings;

	/**
	 * The cached value of the '{@link #getIncomings() <em>Incomings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomings()
	 * @generated
	 * @ordered
	 */
	protected EList<SlicedBoundaryEReference> incomings;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EClass type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SlicedEClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.SLICED_ECLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBoundary() {
		return boundary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBoundary(boolean newBoundary) {
		boolean oldBoundary = boundary;
		boundary = newBoundary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_ECLASS__BOUNDARY, oldBoundary, boundary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlicingConfiguration getSlicingConfiguration() {
		if (eContainerFeatureID() != ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION) return null;
		return (SlicingConfiguration)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSlicingConfiguration(SlicingConfiguration newSlicingConfiguration, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSlicingConfiguration, ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSlicingConfiguration(SlicingConfiguration newSlicingConfiguration) {
		if (newSlicingConfiguration != eInternalContainer() || (eContainerFeatureID() != ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION && newSlicingConfiguration != null)) {
			if (EcoreUtil.isAncestor(this, newSlicingConfiguration))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSlicingConfiguration != null)
				msgs = ((InternalEObject)newSlicingConfiguration).eInverseAdd(this, ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES, SlicingConfiguration.class, msgs);
			msgs = basicSetSlicingConfiguration(newSlicingConfiguration, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION, newSlicingConfiguration, newSlicingConfiguration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SlicedBoundaryEReference> getOutgoings() {
		if (outgoings == null) {
			outgoings = new EObjectContainmentWithInverseEList<SlicedBoundaryEReference>(SlicedBoundaryEReference.class, this, ConfigurationPackage.SLICED_ECLASS__OUTGOINGS, ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE);
		}
		return outgoings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SlicedBoundaryEReference> getIncomings() {
		if (incomings == null) {
			incomings = new EObjectContainmentWithInverseEList<SlicedBoundaryEReference>(SlicedBoundaryEReference.class, this, ConfigurationPackage.SLICED_ECLASS__INCOMINGS, ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET);
		}
		return incomings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (EClass)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigurationPackage.SLICED_ECLASS__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EClass newType) {
		EClass oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_ECLASS__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSlicingConfiguration((SlicingConfiguration)otherEnd, msgs);
			case ConfigurationPackage.SLICED_ECLASS__OUTGOINGS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoings()).basicAdd(otherEnd, msgs);
			case ConfigurationPackage.SLICED_ECLASS__INCOMINGS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomings()).basicAdd(otherEnd, msgs);
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
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				return basicSetSlicingConfiguration(null, msgs);
			case ConfigurationPackage.SLICED_ECLASS__OUTGOINGS:
				return ((InternalEList<?>)getOutgoings()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.SLICED_ECLASS__INCOMINGS:
				return ((InternalEList<?>)getIncomings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				return eInternalContainer().eInverseRemove(this, ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES, SlicingConfiguration.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigurationPackage.SLICED_ECLASS__BOUNDARY:
				return isBoundary();
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				return getSlicingConfiguration();
			case ConfigurationPackage.SLICED_ECLASS__OUTGOINGS:
				return getOutgoings();
			case ConfigurationPackage.SLICED_ECLASS__INCOMINGS:
				return getIncomings();
			case ConfigurationPackage.SLICED_ECLASS__TYPE:
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
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConfigurationPackage.SLICED_ECLASS__BOUNDARY:
				setBoundary((Boolean)newValue);
				return;
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				setSlicingConfiguration((SlicingConfiguration)newValue);
				return;
			case ConfigurationPackage.SLICED_ECLASS__OUTGOINGS:
				getOutgoings().clear();
				getOutgoings().addAll((Collection<? extends SlicedBoundaryEReference>)newValue);
				return;
			case ConfigurationPackage.SLICED_ECLASS__INCOMINGS:
				getIncomings().clear();
				getIncomings().addAll((Collection<? extends SlicedBoundaryEReference>)newValue);
				return;
			case ConfigurationPackage.SLICED_ECLASS__TYPE:
				setType((EClass)newValue);
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
			case ConfigurationPackage.SLICED_ECLASS__BOUNDARY:
				setBoundary(BOUNDARY_EDEFAULT);
				return;
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				setSlicingConfiguration((SlicingConfiguration)null);
				return;
			case ConfigurationPackage.SLICED_ECLASS__OUTGOINGS:
				getOutgoings().clear();
				return;
			case ConfigurationPackage.SLICED_ECLASS__INCOMINGS:
				getIncomings().clear();
				return;
			case ConfigurationPackage.SLICED_ECLASS__TYPE:
				setType((EClass)null);
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
			case ConfigurationPackage.SLICED_ECLASS__BOUNDARY:
				return boundary != BOUNDARY_EDEFAULT;
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				return getSlicingConfiguration() != null;
			case ConfigurationPackage.SLICED_ECLASS__OUTGOINGS:
				return outgoings != null && !outgoings.isEmpty();
			case ConfigurationPackage.SLICED_ECLASS__INCOMINGS:
				return incomings != null && !incomings.isEmpty();
			case ConfigurationPackage.SLICED_ECLASS__TYPE:
				return type != null;
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
		result.append(" (boundary: ");
		result.append(boundary);
		result.append(')');
		return result.toString();
	}

} //SlicedEClassImpl
