/**
 */
package org.sidiff.slicer.structural.configuration.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.sidiff.slicer.structural.configuration.ConfigurationPackage;
import org.sidiff.slicer.structural.configuration.Constraint;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicedEReference;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sliced EReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicedEReferenceImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicedEReferenceImpl#getSlicedEClass <em>Sliced EClass</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicedEReferenceImpl#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link org.sidiff.slicer.structural.configuration.impl.SlicedEReferenceImpl#getOverwrite <em>Overwrite</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SlicedEReferenceImpl extends MinimalEObjectImpl.Container implements SlicedEReference {
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
	 * The cached value of the '{@link #getConstraint() <em>Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraint()
	 * @generated
	 * @ordered
	 */
	protected Constraint constraint;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SlicedEReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.SLICED_EREFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (EReference)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigurationPackage.SLICED_EREFERENCE__TYPE, oldType, type));
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
	@Override
	public void setType(EReference newType) {
		EReference oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_EREFERENCE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SlicedEClass getSlicedEClass() {
		if (eContainerFeatureID() != ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS) return null;
		return (SlicedEClass)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSlicedEClass(SlicedEClass newSlicedEClass, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSlicedEClass, ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSlicedEClass(SlicedEClass newSlicedEClass) {
		if (newSlicedEClass != eInternalContainer() || (eContainerFeatureID() != ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS && newSlicedEClass != null)) {
			if (EcoreUtil.isAncestor(this, newSlicedEClass))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSlicedEClass != null)
				msgs = ((InternalEObject)newSlicedEClass).eInverseAdd(this, ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES, SlicedEClass.class, msgs);
			msgs = basicSetSlicedEClass(newSlicedEClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS, newSlicedEClass, newSlicedEClass));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_EREFERENCE__CONSTRAINT, oldConstraint, newConstraint);
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
				msgs = ((InternalEObject)constraint).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.SLICED_EREFERENCE__CONSTRAINT, null, msgs);
			if (newConstraint != null)
				msgs = ((InternalEObject)newConstraint).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConfigurationPackage.SLICED_EREFERENCE__CONSTRAINT, null, msgs);
			msgs = basicSetConstraint(newConstraint, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.SLICED_EREFERENCE__CONSTRAINT, newConstraint, newConstraint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SlicedEReference getOverwrite() {
		SlicedEReference overwrite = basicGetOverwrite();
		return overwrite != null && overwrite.eIsProxy() ? (SlicedEReference)eResolveProxy((InternalEObject)overwrite) : overwrite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public SlicedEReference basicGetOverwrite() {
		EClass eClass = this.getSlicedEClass().getType();
		if(eClass == null)
			return null;
		SlicingConfiguration slicingConfiguration = this.getSlicedEClass().getSlicingConfiguration();
		for(EClass superEClass : eClass.getEAllSuperTypes()){
			if(slicingConfiguration.getOppositeSlicedEClassType().get(superEClass) != null){
				SlicedEClass slicedESuperClass = slicingConfiguration.getOppositeSlicedEClassType().get(superEClass);
				if(slicedESuperClass.getOppositeSlicedEReferenceType().get(this.getType()) != null){
					return slicedESuperClass.getOppositeSlicedEReferenceType().get(this.type);
				}
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSlicedEClass((SlicedEClass)otherEnd, msgs);
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
			case ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS:
				return basicSetSlicedEClass(null, msgs);
			case ConfigurationPackage.SLICED_EREFERENCE__CONSTRAINT:
				return basicSetConstraint(null, msgs);
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
			case ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS:
				return eInternalContainer().eInverseRemove(this, ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES, SlicedEClass.class, msgs);
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
			case ConfigurationPackage.SLICED_EREFERENCE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS:
				return getSlicedEClass();
			case ConfigurationPackage.SLICED_EREFERENCE__CONSTRAINT:
				return getConstraint();
			case ConfigurationPackage.SLICED_EREFERENCE__OVERWRITE:
				if (resolve) return getOverwrite();
				return basicGetOverwrite();
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
			case ConfigurationPackage.SLICED_EREFERENCE__TYPE:
				setType((EReference)newValue);
				return;
			case ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS:
				setSlicedEClass((SlicedEClass)newValue);
				return;
			case ConfigurationPackage.SLICED_EREFERENCE__CONSTRAINT:
				setConstraint((Constraint)newValue);
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
			case ConfigurationPackage.SLICED_EREFERENCE__TYPE:
				setType((EReference)null);
				return;
			case ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS:
				setSlicedEClass((SlicedEClass)null);
				return;
			case ConfigurationPackage.SLICED_EREFERENCE__CONSTRAINT:
				setConstraint((Constraint)null);
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
			case ConfigurationPackage.SLICED_EREFERENCE__TYPE:
				return type != null;
			case ConfigurationPackage.SLICED_EREFERENCE__SLICED_ECLASS:
				return getSlicedEClass() != null;
			case ConfigurationPackage.SLICED_EREFERENCE__CONSTRAINT:
				return constraint != null;
			case ConfigurationPackage.SLICED_EREFERENCE__OVERWRITE:
				return basicGetOverwrite() != null;
		}
		return super.eIsSet(featureID);
	}

} //SlicedEReferenceImpl
