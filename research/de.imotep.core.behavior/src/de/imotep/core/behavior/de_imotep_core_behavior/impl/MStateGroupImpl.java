/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.impl;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState;
import de.imotep.core.behavior.de_imotep_core_behavior.MAction;
import de.imotep.core.behavior.de_imotep_core_behavior.MRegion;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MState Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateGroupImpl#getParentRegion <em>Parent Region</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateGroupImpl#getStates <em>States</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateGroupImpl#getParentStateGroup <em>Parent State Group</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateGroupImpl#getStateGroups <em>State Groups</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateGroupImpl#getOnExitActions <em>On Exit Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateGroupImpl#getOnEntryActions <em>On Entry Actions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MStateGroupImpl extends MBehaviorEntityImpl implements MStateGroup {
	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<MAbstractState> states;

	/**
	 * The cached value of the '{@link #getParentStateGroup() <em>Parent State Group</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentStateGroup()
	 * @generated
	 * @ordered
	 */
	protected MStateGroup parentStateGroup;

	/**
	 * The cached value of the '{@link #getStateGroups() <em>State Groups</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<MStateGroup> stateGroups;

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
	 * The cached value of the '{@link #getOnEntryActions() <em>On Entry Actions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOnEntryActions()
	 * @generated
	 * @ordered
	 */
	protected EList<MAction> onEntryActions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MStateGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return De_imotep_core_behaviorPackage.Literals.MSTATE_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MRegion getParentRegion() {
		if (eContainerFeatureID() != De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION) return null;
		return (MRegion)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentRegion(MRegion newParentRegion, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentRegion, De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentRegion(MRegion newParentRegion) {
		if (newParentRegion != eInternalContainer() || (eContainerFeatureID() != De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION && newParentRegion != null)) {
			if (EcoreUtil.isAncestor(this, newParentRegion))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentRegion != null)
				msgs = ((InternalEObject)newParentRegion).eInverseAdd(this, De_imotep_core_behaviorPackage.MREGION__STATE_GROUPS, MRegion.class, msgs);
			msgs = basicSetParentRegion(newParentRegion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION, newParentRegion, newParentRegion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAbstractState> getStates() {
		if (states == null) {
			states = new EObjectWithInverseResolvingEList<MAbstractState>(MAbstractState.class, this, De_imotep_core_behaviorPackage.MSTATE_GROUP__STATES, De_imotep_core_behaviorPackage.MABSTRACT_STATE__STATE_GROUP);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStateGroup getParentStateGroup() {
		if (parentStateGroup != null && parentStateGroup.eIsProxy()) {
			InternalEObject oldParentStateGroup = (InternalEObject)parentStateGroup;
			parentStateGroup = (MStateGroup)eResolveProxy(oldParentStateGroup);
			if (parentStateGroup != oldParentStateGroup) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_STATE_GROUP, oldParentStateGroup, parentStateGroup));
			}
		}
		return parentStateGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStateGroup basicGetParentStateGroup() {
		return parentStateGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentStateGroup(MStateGroup newParentStateGroup, NotificationChain msgs) {
		MStateGroup oldParentStateGroup = parentStateGroup;
		parentStateGroup = newParentStateGroup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_STATE_GROUP, oldParentStateGroup, newParentStateGroup);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentStateGroup(MStateGroup newParentStateGroup) {
		if (newParentStateGroup != parentStateGroup) {
			NotificationChain msgs = null;
			if (parentStateGroup != null)
				msgs = ((InternalEObject)parentStateGroup).eInverseRemove(this, De_imotep_core_behaviorPackage.MSTATE_GROUP__STATE_GROUPS, MStateGroup.class, msgs);
			if (newParentStateGroup != null)
				msgs = ((InternalEObject)newParentStateGroup).eInverseAdd(this, De_imotep_core_behaviorPackage.MSTATE_GROUP__STATE_GROUPS, MStateGroup.class, msgs);
			msgs = basicSetParentStateGroup(newParentStateGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_STATE_GROUP, newParentStateGroup, newParentStateGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MStateGroup> getStateGroups() {
		if (stateGroups == null) {
			stateGroups = new EObjectWithInverseResolvingEList<MStateGroup>(MStateGroup.class, this, De_imotep_core_behaviorPackage.MSTATE_GROUP__STATE_GROUPS, De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_STATE_GROUP);
		}
		return stateGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAction> getOnExitActions() {
		if (onExitActions == null) {
			onExitActions = new EObjectResolvingEList<MAction>(MAction.class, this, De_imotep_core_behaviorPackage.MSTATE_GROUP__ON_EXIT_ACTIONS);
		}
		return onExitActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAction> getOnEntryActions() {
		if (onEntryActions == null) {
			onEntryActions = new EObjectResolvingEList<MAction>(MAction.class, this, De_imotep_core_behaviorPackage.MSTATE_GROUP__ON_ENTRY_ACTIONS);
		}
		return onEntryActions;
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
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentRegion((MRegion)otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStates()).basicAdd(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_STATE_GROUP:
				if (parentStateGroup != null)
					msgs = ((InternalEObject)parentStateGroup).eInverseRemove(this, De_imotep_core_behaviorPackage.MSTATE_GROUP__STATE_GROUPS, MStateGroup.class, msgs);
				return basicSetParentStateGroup((MStateGroup)otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATE_GROUPS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStateGroups()).basicAdd(otherEnd, msgs);
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
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION:
				return basicSetParentRegion(null, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_STATE_GROUP:
				return basicSetParentStateGroup(null, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATE_GROUPS:
				return ((InternalEList<?>)getStateGroups()).basicRemove(otherEnd, msgs);
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
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION:
				return eInternalContainer().eInverseRemove(this, De_imotep_core_behaviorPackage.MREGION__STATE_GROUPS, MRegion.class, msgs);
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
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION:
				return getParentRegion();
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATES:
				return getStates();
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_STATE_GROUP:
				if (resolve) return getParentStateGroup();
				return basicGetParentStateGroup();
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATE_GROUPS:
				return getStateGroups();
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__ON_EXIT_ACTIONS:
				return getOnExitActions();
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__ON_ENTRY_ACTIONS:
				return getOnEntryActions();
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
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION:
				setParentRegion((MRegion)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends MAbstractState>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_STATE_GROUP:
				setParentStateGroup((MStateGroup)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATE_GROUPS:
				getStateGroups().clear();
				getStateGroups().addAll((Collection<? extends MStateGroup>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__ON_EXIT_ACTIONS:
				getOnExitActions().clear();
				getOnExitActions().addAll((Collection<? extends MAction>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__ON_ENTRY_ACTIONS:
				getOnEntryActions().clear();
				getOnEntryActions().addAll((Collection<? extends MAction>)newValue);
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
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION:
				setParentRegion((MRegion)null);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATES:
				getStates().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_STATE_GROUP:
				setParentStateGroup((MStateGroup)null);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATE_GROUPS:
				getStateGroups().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__ON_EXIT_ACTIONS:
				getOnExitActions().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__ON_ENTRY_ACTIONS:
				getOnEntryActions().clear();
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
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION:
				return getParentRegion() != null;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATES:
				return states != null && !states.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_STATE_GROUP:
				return parentStateGroup != null;
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__STATE_GROUPS:
				return stateGroups != null && !stateGroups.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__ON_EXIT_ACTIONS:
				return onExitActions != null && !onExitActions.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE_GROUP__ON_ENTRY_ACTIONS:
				return onEntryActions != null && !onEntryActions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MStateGroupImpl
