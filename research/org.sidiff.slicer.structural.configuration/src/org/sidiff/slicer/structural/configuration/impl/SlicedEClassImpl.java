/**
 */
package org.sidiff.slicer.structural.configuration.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.slicer.structural.configuration.ConfigurationPackage;
import org.sidiff.slicer.structural.configuration.Constraint;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicedEReference;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sliced EClass</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicedEClassImpl#getSlicingConfiguration <em>Slicing Configuration</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicedEClassImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicedEClassImpl#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicedEClassImpl#getSlicedEReferences <em>Sliced EReferences</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicedEClassImpl#getOppositeSlicedEReferenceType <em>Opposite Sliced EReference Type</em>}</li>
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
	 * The cached value of the '{@link #getConstraint() <em>Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraint()
	 * @generated
	 * @ordered
	 */
	protected Constraint constraint;

	/**
	 * The cached value of the '{@link #getSlicedEReferences() <em>Sliced EReferences</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSlicedEReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<SlicedEReference> slicedEReferences;
	
	/**
	 * The cached value of the '{@link #getOppositeSlicedEReferenceType() <em>Opposite Sliced EReference Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOppositeSlicedEReferenceType()
	 * @generated
	 * @ordered
	 */
	protected Map<EReference, SlicedEReference> oppositeSlicedEReferenceType;

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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
	public Constraint getConstraint() {
		return constraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConstraint(Constraint newConstraint, NotificationChain msgs) {
		Constraint oldConstraint = constraint;
		constraint = newConstraint;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_ECLASS__CONSTRAINT, oldConstraint, newConstraint);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConstraint(Constraint newConstraint) {
		if (newConstraint != constraint) {
			NotificationChain msgs = null;
			if (constraint != null)
				msgs = ((InternalEObject)constraint).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.SLICED_ECLASS__CONSTRAINT, null, msgs);
			if (newConstraint != null)
				msgs = ((InternalEObject)newConstraint).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.SLICED_ECLASS__CONSTRAINT, null, msgs);
			msgs = basicSetConstraint(newConstraint, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_ECLASS__CONSTRAINT, newConstraint, newConstraint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SlicedEReference> getSlicedEReferences() {
		if (slicedEReferences == null) {
			slicedEReferences = new EObjectContainmentWithInverseEList<SlicedEReference>(SlicedEReference.class, this, ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES, ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS);
		}
		return slicedEReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Map<EReference, SlicedEReference> getOppositeSlicedEReferenceType() {
		oppositeSlicedEReferenceType = new HashMap<EReference, SlicedEReference>();
		for (SlicedEReference slicedEReferences : slicedEReferences) {
			if (slicedEReferences.getType() != null) {
				oppositeSlicedEReferenceType.put(slicedEReferences.getType(), slicedEReferences);
			}
		}
		return oppositeSlicedEReferenceType;
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
			case ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSlicedEReferences()).basicAdd(otherEnd, msgs);
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
			case ConfigurationPackage.SLICED_ECLASS__CONSTRAINT:
				return basicSetConstraint(null, msgs);
			case ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES:
				return ((InternalEList<?>)getSlicedEReferences()).basicRemove(otherEnd, msgs);
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
			case ConfigurationPackage.SLICED_ECLASS__CONSTRAINT:
				return getConstraint();
			case ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES:
				return getSlicedEReferences();
			case ConfigurationPackage.SLICED_ECLASS__OPPOSITE_SLICED_EREFERENCE_TYPE:
				return getOppositeSlicedEReferenceType();
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
			case ConfigurationPackage.SLICED_ECLASS__CONSTRAINT:
				setConstraint((Constraint)newValue);
				return;
			case ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES:
				getSlicedEReferences().clear();
				getSlicedEReferences().addAll((Collection<? extends SlicedEReference>)newValue);
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
			case ConfigurationPackage.SLICED_ECLASS__CONSTRAINT:
				setConstraint((Constraint)null);
				return;
			case ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES:
				getSlicedEReferences().clear();
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
			case ConfigurationPackage.SLICED_ECLASS__CONSTRAINT:
				return constraint != null;
			case ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES:
				return slicedEReferences != null && !slicedEReferences.isEmpty();
			case ConfigurationPackage.SLICED_ECLASS__OPPOSITE_SLICED_EREFERENCE_TYPE:
				return oppositeSlicedEReferenceType != null;
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
		result.append(" (oppositeSlicedEReferenceType: ");
		result.append(oppositeSlicedEReferenceType);
		result.append(')');
		return result.toString();
	}

} //SlicedEClassImpl
