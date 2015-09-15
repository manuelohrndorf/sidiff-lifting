/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.impl;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MAction;
import de.imotep.core.behavior.de_imotep_core_behavior.MRegion;
import de.imotep.core.behavior.de_imotep_core_behavior.MState;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MState</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateImpl#getOnEntryActions <em>On Entry Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateImpl#getRegions <em>Regions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateImpl#getDoActions <em>Do Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateImpl#getOnExitActions <em>On Exit Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateImpl#isTemporary <em>Temporary</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MStateImpl extends MAbstractStateImpl implements MState {
	/**
	 * The cached value of the '{@link #getOnEntryActions() <em>On Entry Actions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnEntryActions()
	 * @generated
	 * @ordered
	 */
	protected EList<MAction> onEntryActions;

	/**
	 * The cached value of the '{@link #getRegions() <em>Regions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegions()
	 * @generated
	 * @ordered
	 */
	protected EList<MRegion> regions;

	/**
	 * The cached value of the '{@link #getDoActions() <em>Do Actions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoActions()
	 * @generated
	 * @ordered
	 */
	protected EList<MAction> doActions;

	/**
	 * The cached value of the '{@link #getOnExitActions() <em>On Exit Actions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnExitActions()
	 * @generated
	 * @ordered
	 */
	protected EList<MAction> onExitActions;

	/**
	 * The default value of the '{@link #isTemporary() <em>Temporary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTemporary()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TEMPORARY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTemporary() <em>Temporary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTemporary()
	 * @generated
	 * @ordered
	 */
	protected boolean temporary = TEMPORARY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return De_imotep_core_behaviorPackage.Literals.MSTATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAction> getOnEntryActions() {
		if (onEntryActions == null) {
			onEntryActions = new EObjectResolvingEList<MAction>(MAction.class, this, De_imotep_core_behaviorPackage.MSTATE__ON_ENTRY_ACTIONS);
		}
		return onEntryActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MRegion> getRegions() {
		if (regions == null) {
			regions = new EObjectContainmentWithInverseEList<MRegion>(MRegion.class, this, De_imotep_core_behaviorPackage.MSTATE__REGIONS, De_imotep_core_behaviorPackage.MREGION__PARENT_STATE);
		}
		return regions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAction> getDoActions() {
		if (doActions == null) {
			doActions = new EObjectResolvingEList<MAction>(MAction.class, this, De_imotep_core_behaviorPackage.MSTATE__DO_ACTIONS);
		}
		return doActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAction> getOnExitActions() {
		if (onExitActions == null) {
			onExitActions = new EObjectResolvingEList<MAction>(MAction.class, this, De_imotep_core_behaviorPackage.MSTATE__ON_EXIT_ACTIONS);
		}
		return onExitActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTemporary() {
		return temporary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemporary(boolean newTemporary) {
		boolean oldTemporary = temporary;
		temporary = newTemporary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MSTATE__TEMPORARY, oldTemporary, temporary));
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
			case De_imotep_core_behaviorPackage.MSTATE__REGIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRegions()).basicAdd(otherEnd, msgs);
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
			case De_imotep_core_behaviorPackage.MSTATE__REGIONS:
				return ((InternalEList<?>)getRegions()).basicRemove(otherEnd, msgs);
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
			case De_imotep_core_behaviorPackage.MSTATE__ON_ENTRY_ACTIONS:
				return getOnEntryActions();
			case De_imotep_core_behaviorPackage.MSTATE__REGIONS:
				return getRegions();
			case De_imotep_core_behaviorPackage.MSTATE__DO_ACTIONS:
				return getDoActions();
			case De_imotep_core_behaviorPackage.MSTATE__ON_EXIT_ACTIONS:
				return getOnExitActions();
			case De_imotep_core_behaviorPackage.MSTATE__TEMPORARY:
				return isTemporary();
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
			case De_imotep_core_behaviorPackage.MSTATE__ON_ENTRY_ACTIONS:
				getOnEntryActions().clear();
				getOnEntryActions().addAll((Collection<? extends MAction>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE__REGIONS:
				getRegions().clear();
				getRegions().addAll((Collection<? extends MRegion>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE__DO_ACTIONS:
				getDoActions().clear();
				getDoActions().addAll((Collection<? extends MAction>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE__ON_EXIT_ACTIONS:
				getOnExitActions().clear();
				getOnExitActions().addAll((Collection<? extends MAction>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE__TEMPORARY:
				setTemporary((Boolean)newValue);
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
			case De_imotep_core_behaviorPackage.MSTATE__ON_ENTRY_ACTIONS:
				getOnEntryActions().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE__REGIONS:
				getRegions().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE__DO_ACTIONS:
				getDoActions().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE__ON_EXIT_ACTIONS:
				getOnExitActions().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE__TEMPORARY:
				setTemporary(TEMPORARY_EDEFAULT);
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
			case De_imotep_core_behaviorPackage.MSTATE__ON_ENTRY_ACTIONS:
				return onEntryActions != null && !onEntryActions.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE__REGIONS:
				return regions != null && !regions.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE__DO_ACTIONS:
				return doActions != null && !doActions.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE__ON_EXIT_ACTIONS:
				return onExitActions != null && !onExitActions.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE__TEMPORARY:
				return temporary != TEMPORARY_EDEFAULT;
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
		result.append(" (temporary: ");
		result.append(temporary);
		result.append(')');
		return result.toString();
	}

} //MStateImpl
