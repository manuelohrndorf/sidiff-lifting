/**
 */
package org.sidiff.patching.patch.patch.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.difference.asymmetric.AsymmetricDifference;

import org.sidiff.difference.rulebase.EditRule;

import org.sidiff.patching.patch.patch.Patch;
import org.sidiff.patching.patch.patch.PatchPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Patch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.patching.patch.patch.impl.PatchImpl#getAsymmetricDifference <em>Asymmetric Difference</em>}</li>
 *   <li>{@link org.sidiff.patching.patch.patch.impl.PatchImpl#getSettings <em>Settings</em>}</li>
 *   <li>{@link org.sidiff.patching.patch.patch.impl.PatchImpl#getEditRules <em>Edit Rules</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PatchImpl extends MinimalEObjectImpl.Container implements Patch {
	/**
	 * The cached value of the '{@link #getAsymmetricDifference() <em>Asymmetric Difference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsymmetricDifference()
	 * @generated
	 * @ordered
	 */
	protected AsymmetricDifference asymmetricDifference;

	/**
	 * The cached value of the '{@link #getSettings() <em>Settings</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSettings()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> settings;

	/**
	 * The cached value of the '{@link #getEditRules() <em>Edit Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditRules()
	 * @generated
	 * @ordered
	 */
	protected EList<EditRule> editRules;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PatchPackage.Literals.PATCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsymmetricDifference getAsymmetricDifference() {
		if (asymmetricDifference != null && asymmetricDifference.eIsProxy()) {
			InternalEObject oldAsymmetricDifference = (InternalEObject)asymmetricDifference;
			asymmetricDifference = (AsymmetricDifference)eResolveProxy(oldAsymmetricDifference);
			if (asymmetricDifference != oldAsymmetricDifference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PatchPackage.PATCH__ASYMMETRIC_DIFFERENCE, oldAsymmetricDifference, asymmetricDifference));
			}
		}
		return asymmetricDifference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsymmetricDifference basicGetAsymmetricDifference() {
		return asymmetricDifference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAsymmetricDifference(AsymmetricDifference newAsymmetricDifference) {
		AsymmetricDifference oldAsymmetricDifference = asymmetricDifference;
		asymmetricDifference = newAsymmetricDifference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatchPackage.PATCH__ASYMMETRIC_DIFFERENCE, oldAsymmetricDifference, asymmetricDifference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getSettings() {
		if (settings == null) {
			settings = new EcoreEMap<String,String>(PatchPackage.Literals.SETTING, SettingImpl.class, this, PatchPackage.PATCH__SETTINGS);
		}
		return settings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EditRule> getEditRules() {
		if (editRules == null) {
			editRules = new EObjectContainmentEList<EditRule>(EditRule.class, this, PatchPackage.PATCH__EDIT_RULES);
		}
		return editRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PatchPackage.PATCH__SETTINGS:
				return ((InternalEList<?>)getSettings()).basicRemove(otherEnd, msgs);
			case PatchPackage.PATCH__EDIT_RULES:
				return ((InternalEList<?>)getEditRules()).basicRemove(otherEnd, msgs);
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
			case PatchPackage.PATCH__ASYMMETRIC_DIFFERENCE:
				if (resolve) return getAsymmetricDifference();
				return basicGetAsymmetricDifference();
			case PatchPackage.PATCH__SETTINGS:
				if (coreType) return getSettings();
				else return getSettings().map();
			case PatchPackage.PATCH__EDIT_RULES:
				return getEditRules();
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
			case PatchPackage.PATCH__ASYMMETRIC_DIFFERENCE:
				setAsymmetricDifference((AsymmetricDifference)newValue);
				return;
			case PatchPackage.PATCH__SETTINGS:
				((EStructuralFeature.Setting)getSettings()).set(newValue);
				return;
			case PatchPackage.PATCH__EDIT_RULES:
				getEditRules().clear();
				getEditRules().addAll((Collection<? extends EditRule>)newValue);
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
			case PatchPackage.PATCH__ASYMMETRIC_DIFFERENCE:
				setAsymmetricDifference((AsymmetricDifference)null);
				return;
			case PatchPackage.PATCH__SETTINGS:
				getSettings().clear();
				return;
			case PatchPackage.PATCH__EDIT_RULES:
				getEditRules().clear();
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
			case PatchPackage.PATCH__ASYMMETRIC_DIFFERENCE:
				return asymmetricDifference != null;
			case PatchPackage.PATCH__SETTINGS:
				return settings != null && !settings.isEmpty();
			case PatchPackage.PATCH__EDIT_RULES:
				return editRules != null && !editRules.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PatchImpl
