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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.slicing.configuration.ConfigurationPackage;
import org.sidiff.slicing.configuration.Constraint;
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
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedEClassImpl#getSlicingConfiguration <em>Slicing Configuration</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedEClassImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.sidiff.slicing.configuration.impl.SlicedEClassImpl#getConstraints <em>Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SlicedEClassImpl extends MinimalEObjectImpl.Container implements SlicedEClass {
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
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraint> constraints;

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
	public EList<Constraint> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectResolvingEList<Constraint>(Constraint.class, this, ConfigurationPackage.SLICED_ECLASS__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSlicingConfiguration((SlicingConfiguration)otherEnd, msgs);
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
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				return getSlicingConfiguration();
			case ConfigurationPackage.SLICED_ECLASS__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case ConfigurationPackage.SLICED_ECLASS__CONSTRAINTS:
				return getConstraints();
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
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				setSlicingConfiguration((SlicingConfiguration)newValue);
				return;
			case ConfigurationPackage.SLICED_ECLASS__TYPE:
				setType((EClass)newValue);
				return;
			case ConfigurationPackage.SLICED_ECLASS__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection<? extends Constraint>)newValue);
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
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				setSlicingConfiguration((SlicingConfiguration)null);
				return;
			case ConfigurationPackage.SLICED_ECLASS__TYPE:
				setType((EClass)null);
				return;
			case ConfigurationPackage.SLICED_ECLASS__CONSTRAINTS:
				getConstraints().clear();
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
			case ConfigurationPackage.SLICED_ECLASS__SLICING_CONFIGURATION:
				return getSlicingConfiguration() != null;
			case ConfigurationPackage.SLICED_ECLASS__TYPE:
				return type != null;
			case ConfigurationPackage.SLICED_ECLASS__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SlicedEClassImpl
