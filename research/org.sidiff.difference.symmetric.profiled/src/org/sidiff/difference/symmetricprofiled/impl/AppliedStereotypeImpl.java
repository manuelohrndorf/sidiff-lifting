/**
 */
package org.sidiff.difference.symmetricprofiled.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.sidiff.difference.symmetricprofiled.AppliedStereotype;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Applied Stereotype</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl#getStereoType <em>Stereo Type</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl#getBaseObject <em>Base Object</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.AppliedStereotypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AppliedStereotypeImpl extends MinimalEObjectImpl.Container
		implements AppliedStereotype {
	/**
	 * The cached value of the '{@link #getStereoType() <em>Stereo Type</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getStereoType()
	 * @generated
	 * @ordered
	 */
	protected EObject stereoType;

	/**
	 * The cached value of the '{@link #getBaseObject() <em>Base Object</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getBaseObject()
	 * @generated
	 * @ordered
	 */
	protected EObject baseObject;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected AppliedStereotypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymmetricProfiledPackage.Literals.APPLIED_STEREOTYPE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getName() {
		if (name == null || name.equals(NAME_EDEFAULT)) {
			String result=getStereoType().eClass().getInstanceTypeName();
			if (result != null){
				name =result;
			}
		}
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetBaseObject() {
		return baseObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseObject(EObject newBaseObject) {
		EObject oldBaseObject = baseObject;
		baseObject = newBaseObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_OBJECT, oldBaseObject, baseObject));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__STEREO_TYPE:
				if (resolve) return getStereoType();
				return basicGetStereoType();
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_OBJECT:
				if (resolve) return getBaseObject();
				return basicGetBaseObject();
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__STEREO_TYPE:
				return stereoType != null;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__BASE_OBJECT:
				return baseObject != null;
			case SymmetricProfiledPackage.APPLIED_STEREOTYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetStereoType() {
		return stereoType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setStereoType(EObject newStereoType) {
		EObject oldStereoType = stereoType;
		stereoType = newStereoType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.APPLIED_STEREOTYPE__STEREO_TYPE, oldStereoType, stereoType));
	}

} // AppliedStereotypeImpl
