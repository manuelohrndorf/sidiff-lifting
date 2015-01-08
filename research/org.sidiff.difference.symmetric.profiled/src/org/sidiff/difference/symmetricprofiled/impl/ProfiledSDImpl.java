/**
 */
package org.sidiff.difference.symmetricprofiled.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;

import org.sidiff.difference.symmetricprofiled.ProfiledSCS;
import org.sidiff.difference.symmetricprofiled.ProfiledSD;
import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Profiled SD</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl#getProfiledscss <em>Profiledscss</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl#getSd <em>Sd</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetricprofiled.impl.ProfiledSDImpl#getUnprofiledscss <em>Unprofiledscss</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProfiledSDImpl extends MinimalEObjectImpl.Container implements ProfiledSD {
	/**
	 * The cached value of the '{@link #getProfiledscss() <em>Profiledscss</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProfiledscss()
	 * @generated
	 * @ordered
	 */
	protected EList<ProfiledSCS> profiledscss;

	/**
	 * The cached value of the '{@link #getSd() <em>Sd</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSd()
	 * @generated
	 * @ordered
	 */
	protected SymmetricDifference sd;

	/**
	 * The cached value of the '{@link #getUnprofiledscss() <em>Unprofiledscss</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnprofiledscss()
	 * @generated
	 * @ordered
	 */
	protected EList<SemanticChangeSet> unprofiledscss;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProfiledSDImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymmetricProfiledPackage.Literals.PROFILED_SD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProfiledSCS> getProfiledscss() {
		if (profiledscss == null) {
			profiledscss = new EObjectContainmentEList<ProfiledSCS>(ProfiledSCS.class, this, SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS);
		}
		return profiledscss;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymmetricDifference getSd() {
		if (sd != null && sd.eIsProxy()) {
			InternalEObject oldSd = (InternalEObject)sd;
			sd = (SymmetricDifference)eResolveProxy(oldSd);
			if (sd != oldSd) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymmetricProfiledPackage.PROFILED_SD__SD, oldSd, sd));
			}
		}
		return sd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymmetricDifference basicGetSd() {
		return sd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSd(SymmetricDifference newSd) {
		SymmetricDifference oldSd = sd;
		sd = newSd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricProfiledPackage.PROFILED_SD__SD, oldSd, sd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SemanticChangeSet> getUnprofiledscss() {
		if (unprofiledscss == null) {
			unprofiledscss = new EObjectResolvingEList<SemanticChangeSet>(SemanticChangeSet.class, this, SymmetricProfiledPackage.PROFILED_SD__UNPROFILEDSCSS);
		}
		return unprofiledscss;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS:
				return ((InternalEList<?>)getProfiledscss()).basicRemove(otherEnd, msgs);
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
			case SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS:
				return getProfiledscss();
			case SymmetricProfiledPackage.PROFILED_SD__SD:
				if (resolve) return getSd();
				return basicGetSd();
			case SymmetricProfiledPackage.PROFILED_SD__UNPROFILEDSCSS:
				return getUnprofiledscss();
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
			case SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS:
				getProfiledscss().clear();
				getProfiledscss().addAll((Collection<? extends ProfiledSCS>)newValue);
				return;
			case SymmetricProfiledPackage.PROFILED_SD__SD:
				setSd((SymmetricDifference)newValue);
				return;
			case SymmetricProfiledPackage.PROFILED_SD__UNPROFILEDSCSS:
				getUnprofiledscss().clear();
				getUnprofiledscss().addAll((Collection<? extends SemanticChangeSet>)newValue);
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
			case SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS:
				getProfiledscss().clear();
				return;
			case SymmetricProfiledPackage.PROFILED_SD__SD:
				setSd((SymmetricDifference)null);
				return;
			case SymmetricProfiledPackage.PROFILED_SD__UNPROFILEDSCSS:
				getUnprofiledscss().clear();
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
			case SymmetricProfiledPackage.PROFILED_SD__PROFILEDSCSS:
				return profiledscss != null && !profiledscss.isEmpty();
			case SymmetricProfiledPackage.PROFILED_SD__SD:
				return sd != null;
			case SymmetricProfiledPackage.PROFILED_SD__UNPROFILEDSCSS:
				return unprofiledscss != null && !unprofiledscss.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProfiledSDImpl
