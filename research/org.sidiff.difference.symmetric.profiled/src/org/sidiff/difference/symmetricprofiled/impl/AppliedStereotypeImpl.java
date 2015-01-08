/**
 */
package org.sidiff.difference.symmetricprofiled.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.sidiff.difference.symmetricprofiled.AppliedStereotype;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Applied Stereotype</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl#getStereoType <em>Stereo Type</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl#getBaseObject <em>Base Object</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl#getBaseReference <em>Base Reference</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl#getProfile <em>Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AppliedStereotypeImpl extends MinimalEObjectImpl.Container implements AppliedStereotype {
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
	 * The cached value of the '{@link #getStereoType() <em>Stereo Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStereoType()
	 * @generated
	 * @ordered
	 */
	protected EObject stereoType;

	/**
	 * The cached value of the '{@link #getBaseObject() <em>Base Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseObject()
	 * @generated
	 * @ordered
	 */
	protected EObject baseObject;

	/**
	 * The cached value of the '{@link #getBaseReference() <em>Base Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseReference()
	 * @generated
	 * @ordered
	 */
	protected EReference baseReference;

	/**
	 * The cached value of the '{@link #getProfile() <em>Profile</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProfile()
	 * @generated
	 * @ordered
	 */
	protected EPackage profile;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AppliedStereotypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymmetricProfiledPackage.Literals.APPLIED_STEREOTYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {		
		if(getStereoType() != null && getStereoType().eGet(getStereoType().eClass().getEStructuralFeature("name")) != null){
			return (String) getStereoType().eGet(getStereoType().eClass().getEStructuralFeature("name"));
		}
		else
			return "";
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getStereoType() {
		if (stereoType != null && stereoType.eIsProxy()) {
			InternalEObject oldStereoType = (InternalEObject)stereoType;
			stereoType = eResolveProxy(oldStereoType);
			if (stereoType != oldStereoType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymmetricProfiledPackage.APPLIED_STEREOTYPE__STEREO_TYPE, oldStereoType, stereoType));
			}
		}
		return stereoType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetStereoType() {
		return stereoType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStereoType(EObject newStereoType) {
		EObject oldStereoType = stereoType;
		stereoType = newStereoType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.APPLIED_STEREOTYPE__STEREO_TYPE, oldStereoType, stereoType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getBaseObject() {
		if (baseObject != null && baseObject.eIsProxy()) {
			InternalEObject oldBaseObject = (InternalEObject)baseObject;
			baseObject = eResolveProxy(oldBaseObject);
			if (baseObject != oldBaseObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_OBJECT, oldBaseObject, baseObject));
			}
		}
		return baseObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetBaseObject() {
		return baseObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseObject(EObject newBaseObject) {
		EObject oldBaseObject = baseObject;
		baseObject = newBaseObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_OBJECT, oldBaseObject, baseObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBaseReference() {
		if (baseReference != null && baseReference.eIsProxy()) {
			InternalEObject oldBaseReference = (InternalEObject)baseReference;
			baseReference = (EReference)eResolveProxy(oldBaseReference);
			if (baseReference != oldBaseReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_REFERENCE, oldBaseReference, baseReference));
			}
		}
		return baseReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetBaseReference() {
		return baseReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseReference(EReference newBaseReference) {
		EReference oldBaseReference = baseReference;
		baseReference = newBaseReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_REFERENCE, oldBaseReference, baseReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getProfile() {
		if (profile != null && profile.eIsProxy()) {
			InternalEObject oldProfile = (InternalEObject)profile;
			profile = (EPackage)eResolveProxy(oldProfile);
			if (profile != oldProfile) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymmetricProfiledPackage.APPLIED_STEREOTYPE__PROFILE, oldProfile, profile));
			}
		}
		return profile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetProfile() {
		return profile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProfile(EPackage newProfile) {
		EPackage oldProfile = profile;
		profile = newProfile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.APPLIED_STEREOTYPE__PROFILE, oldProfile, profile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__NAME:
				return getName();
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__STEREO_TYPE:
				if (resolve) return getStereoType();
				return basicGetStereoType();
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_OBJECT:
				if (resolve) return getBaseObject();
				return basicGetBaseObject();
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_REFERENCE:
				if (resolve) return getBaseReference();
				return basicGetBaseReference();
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__PROFILE:
				if (resolve) return getProfile();
				return basicGetProfile();
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
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__STEREO_TYPE:
				setStereoType((EObject)newValue);
				return;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_OBJECT:
				setBaseObject((EObject)newValue);
				return;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_REFERENCE:
				setBaseReference((EReference)newValue);
				return;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__PROFILE:
				setProfile((EPackage)newValue);
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
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__STEREO_TYPE:
				setStereoType((EObject)null);
				return;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_OBJECT:
				setBaseObject((EObject)null);
				return;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_REFERENCE:
				setBaseReference((EReference)null);
				return;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__PROFILE:
				setProfile((EPackage)null);
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
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__STEREO_TYPE:
				return stereoType != null;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_OBJECT:
				return baseObject != null;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_REFERENCE:
				return baseReference != null;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__PROFILE:
				return profile != null;
		}
		return super.eIsSet(featureID);
	}

} //AppliedStereotypeImpl
