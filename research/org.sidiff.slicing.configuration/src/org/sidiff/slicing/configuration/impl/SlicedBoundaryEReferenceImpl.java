/**
 */
package org.sidiff.slicing.configuration.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.sidiff.slicing.configuration.ConfigurationPackage;
import org.sidiff.slicing.configuration.SlicedBoundaryEReference;
import org.sidiff.slicing.configuration.SlicedEClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sliced Boundary EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedBoundaryEReferenceImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedBoundaryEReferenceImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedBoundaryEReferenceImpl#getSrcType <em>Src Type</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedBoundaryEReferenceImpl#getTgtType <em>Tgt Type</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedBoundaryEReferenceImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SlicedBoundaryEReferenceImpl extends MinimalEObjectImpl.Container implements SlicedBoundaryEReference {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EReference type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SlicedBoundaryEReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.SLICED_BOUNDARY_EREFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlicedEClass getSource() {
		if (eContainerFeatureID() != ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE) return null;
		return (SlicedEClass)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(SlicedEClass newSource, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSource, ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(SlicedEClass newSource) {
		if (newSource != eInternalContainer() || (eContainerFeatureID() != ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE && newSource != null)) {
			if (EcoreUtil.isAncestor(this, newSource))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, ConfigurationPackage.SLICED_ECLASS__OUTGOINGS, SlicedEClass.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlicedEClass getTarget() {
		if (eContainerFeatureID() != ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET) return null;
		return (SlicedEClass)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(SlicedEClass newTarget, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newTarget, ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(SlicedEClass newTarget) {
		if (newTarget != eInternalContainer() || (eContainerFeatureID() != ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET && newTarget != null)) {
			if (EcoreUtil.isAncestor(this, newTarget))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, ConfigurationPackage.SLICED_ECLASS__INCOMINGS, SlicedEClass.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSrcType() {
		EClass srcType = basicGetSrcType();
		return srcType != null && srcType.eIsProxy() ? (EClass)eResolveProxy((InternalEObject)srcType) : srcType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EClass basicGetSrcType() {
		return type.getEContainingClass();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSrcType(EClass newSrcType) {
		// TODO: implement this method to set the 'Src Type' reference
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTgtType() {
		EClass tgtType = basicGetTgtType();
		return tgtType != null && tgtType.eIsProxy() ? (EClass)eResolveProxy((InternalEObject)tgtType) : tgtType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EClass basicGetTgtType() {
		EClass eClass = null;
		if(type.getEType() instanceof EClass){
			eClass = (EClass)type.getEType();
		}
		return eClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTgtType(EClass newTgtType) {
		// TODO: implement this method to set the 'Tgt Type' reference
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (EReference)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EReference newType) {
		EReference oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSource((SlicedEClass)otherEnd, msgs);
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetTarget((SlicedEClass)otherEnd, msgs);
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
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE:
				return basicSetSource(null, msgs);
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET:
				return basicSetTarget(null, msgs);
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
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE:
				return eInternalContainer().eInverseRemove(this, ConfigurationPackage.SLICED_ECLASS__OUTGOINGS, SlicedEClass.class, msgs);
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET:
				return eInternalContainer().eInverseRemove(this, ConfigurationPackage.SLICED_ECLASS__INCOMINGS, SlicedEClass.class, msgs);
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
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE:
				return getSource();
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET:
				return getTarget();
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SRC_TYPE:
				if (resolve) return getSrcType();
				return basicGetSrcType();
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TGT_TYPE:
				if (resolve) return getTgtType();
				return basicGetTgtType();
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TYPE:
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE:
				setSource((SlicedEClass)newValue);
				return;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET:
				setTarget((SlicedEClass)newValue);
				return;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SRC_TYPE:
				setSrcType((EClass)newValue);
				return;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TGT_TYPE:
				setTgtType((EClass)newValue);
				return;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TYPE:
				setType((EReference)newValue);
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
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE:
				setSource((SlicedEClass)null);
				return;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET:
				setTarget((SlicedEClass)null);
				return;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SRC_TYPE:
				setSrcType((EClass)null);
				return;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TGT_TYPE:
				setTgtType((EClass)null);
				return;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TYPE:
				setType((EReference)null);
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
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SOURCE:
				return getSource() != null;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TARGET:
				return getTarget() != null;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__SRC_TYPE:
				return basicGetSrcType() != null;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TGT_TYPE:
				return basicGetTgtType() != null;
			case ConfigurationPackage.SLICED_BOUNDARY_EREFERENCE__TYPE:
				return type != null;
		}
		return super.eIsSet(featureID);
	}

} //SlicedBoundaryEReferenceImpl
