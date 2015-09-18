/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.impl;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState;
import de.imotep.core.behavior.de_imotep_core_behavior.MInitialState;
import de.imotep.core.behavior.de_imotep_core_behavior.MRegion;
import de.imotep.core.behavior.de_imotep_core_behavior.MState;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine;
import de.imotep.core.behavior.de_imotep_core_behavior.MTransition;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MRegion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MRegionImpl#getParentState <em>Parent State</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MRegionImpl#getStateGroups <em>State Groups</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MRegionImpl#getStates <em>States</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MRegionImpl#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MRegionImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MRegionImpl#getParentStateMachine <em>Parent State Machine</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MRegionImpl extends MBehaviorEntityImpl implements MRegion {
	/**
	 * The cached value of the '{@link #getStateGroups() <em>State Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<MStateGroup> stateGroups;

	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<MAbstractState> states;

	/**
	 * The cached value of the '{@link #getInitialState() <em>Initial State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialState()
	 * @generated
	 * @ordered
	 */
	protected MInitialState initialState;

	/**
	 * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<MTransition> transitions;

	/**
	 * The cached value of the '{@link #getParentStateMachine() <em>Parent State Machine</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentStateMachine()
	 * @generated
	 * @ordered
	 */
	protected MStateMachine parentStateMachine;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MRegionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return De_imotep_core_behaviorPackage.Literals.MREGION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MState getParentState() {
		if (eContainerFeatureID() != De_imotep_core_behaviorPackage.MREGION__PARENT_STATE) return null;
		return (MState)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentState(MState newParentState, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentState, De_imotep_core_behaviorPackage.MREGION__PARENT_STATE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentState(MState newParentState) {
		if (newParentState != eInternalContainer() || (eContainerFeatureID() != De_imotep_core_behaviorPackage.MREGION__PARENT_STATE && newParentState != null)) {
			if (EcoreUtil.isAncestor(this, newParentState))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentState != null)
				msgs = ((InternalEObject)newParentState).eInverseAdd(this, De_imotep_core_behaviorPackage.MSTATE__REGIONS, MState.class, msgs);
			msgs = basicSetParentState(newParentState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MREGION__PARENT_STATE, newParentState, newParentState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MStateGroup> getStateGroups() {
		if (stateGroups == null) {
			stateGroups = new EObjectContainmentWithInverseEList<MStateGroup>(MStateGroup.class, this, De_imotep_core_behaviorPackage.MREGION__STATE_GROUPS, De_imotep_core_behaviorPackage.MSTATE_GROUP__PARENT_REGION);
		}
		return stateGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAbstractState> getStates() {
		if (states == null) {
			states = new EObjectContainmentEList<MAbstractState>(MAbstractState.class, this, De_imotep_core_behaviorPackage.MREGION__STATES);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MInitialState getInitialState() {
		if (initialState != null && initialState.eIsProxy()) {
			InternalEObject oldInitialState = (InternalEObject)initialState;
			initialState = (MInitialState)eResolveProxy(oldInitialState);
			if (initialState != oldInitialState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, De_imotep_core_behaviorPackage.MREGION__INITIAL_STATE, oldInitialState, initialState));
			}
		}
		return initialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MInitialState basicGetInitialState() {
		return initialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialState(MInitialState newInitialState) {
		MInitialState oldInitialState = initialState;
		initialState = newInitialState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MREGION__INITIAL_STATE, oldInitialState, initialState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MTransition> getTransitions() {
		if (transitions == null) {
			transitions = new EObjectContainmentEList<MTransition>(MTransition.class, this, De_imotep_core_behaviorPackage.MREGION__TRANSITIONS);
		}
		return transitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStateMachine getParentStateMachine() {
		if (parentStateMachine != null && parentStateMachine.eIsProxy()) {
			InternalEObject oldParentStateMachine = (InternalEObject)parentStateMachine;
			parentStateMachine = (MStateMachine)eResolveProxy(oldParentStateMachine);
			if (parentStateMachine != oldParentStateMachine) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, De_imotep_core_behaviorPackage.MREGION__PARENT_STATE_MACHINE, oldParentStateMachine, parentStateMachine));
			}
		}
		return parentStateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStateMachine basicGetParentStateMachine() {
		return parentStateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentStateMachine(MStateMachine newParentStateMachine, NotificationChain msgs) {
		MStateMachine oldParentStateMachine = parentStateMachine;
		parentStateMachine = newParentStateMachine;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MREGION__PARENT_STATE_MACHINE, oldParentStateMachine, newParentStateMachine);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentStateMachine(MStateMachine newParentStateMachine) {
		if (newParentStateMachine != parentStateMachine) {
			NotificationChain msgs = null;
			if (parentStateMachine != null)
				msgs = ((InternalEObject)parentStateMachine).eInverseRemove(this, De_imotep_core_behaviorPackage.MSTATE_MACHINE__REGIONS, MStateMachine.class, msgs);
			if (newParentStateMachine != null)
				msgs = ((InternalEObject)newParentStateMachine).eInverseAdd(this, De_imotep_core_behaviorPackage.MSTATE_MACHINE__REGIONS, MStateMachine.class, msgs);
			msgs = basicSetParentStateMachine(newParentStateMachine, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MREGION__PARENT_STATE_MACHINE, newParentStateMachine, newParentStateMachine));
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
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentState((MState)otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MREGION__STATE_GROUPS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStateGroups()).basicAdd(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE_MACHINE:
				if (parentStateMachine != null)
					msgs = ((InternalEObject)parentStateMachine).eInverseRemove(this, De_imotep_core_behaviorPackage.MSTATE_MACHINE__REGIONS, MStateMachine.class, msgs);
				return basicSetParentStateMachine((MStateMachine)otherEnd, msgs);
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
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE:
				return basicSetParentState(null, msgs);
			case De_imotep_core_behaviorPackage.MREGION__STATE_GROUPS:
				return ((InternalEList<?>)getStateGroups()).basicRemove(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MREGION__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MREGION__TRANSITIONS:
				return ((InternalEList<?>)getTransitions()).basicRemove(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE_MACHINE:
				return basicSetParentStateMachine(null, msgs);
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
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE:
				return eInternalContainer().eInverseRemove(this, De_imotep_core_behaviorPackage.MSTATE__REGIONS, MState.class, msgs);
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
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE:
				return getParentState();
			case De_imotep_core_behaviorPackage.MREGION__STATE_GROUPS:
				return getStateGroups();
			case De_imotep_core_behaviorPackage.MREGION__STATES:
				return getStates();
			case De_imotep_core_behaviorPackage.MREGION__INITIAL_STATE:
				if (resolve) return getInitialState();
				return basicGetInitialState();
			case De_imotep_core_behaviorPackage.MREGION__TRANSITIONS:
				return getTransitions();
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE_MACHINE:
				if (resolve) return getParentStateMachine();
				return basicGetParentStateMachine();
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
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE:
				setParentState((MState)newValue);
				return;
			case De_imotep_core_behaviorPackage.MREGION__STATE_GROUPS:
				getStateGroups().clear();
				getStateGroups().addAll((Collection<? extends MStateGroup>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MREGION__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends MAbstractState>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MREGION__INITIAL_STATE:
				setInitialState((MInitialState)newValue);
				return;
			case De_imotep_core_behaviorPackage.MREGION__TRANSITIONS:
				getTransitions().clear();
				getTransitions().addAll((Collection<? extends MTransition>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE_MACHINE:
				setParentStateMachine((MStateMachine)newValue);
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
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE:
				setParentState((MState)null);
				return;
			case De_imotep_core_behaviorPackage.MREGION__STATE_GROUPS:
				getStateGroups().clear();
				return;
			case De_imotep_core_behaviorPackage.MREGION__STATES:
				getStates().clear();
				return;
			case De_imotep_core_behaviorPackage.MREGION__INITIAL_STATE:
				setInitialState((MInitialState)null);
				return;
			case De_imotep_core_behaviorPackage.MREGION__TRANSITIONS:
				getTransitions().clear();
				return;
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE_MACHINE:
				setParentStateMachine((MStateMachine)null);
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
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE:
				return getParentState() != null;
			case De_imotep_core_behaviorPackage.MREGION__STATE_GROUPS:
				return stateGroups != null && !stateGroups.isEmpty();
			case De_imotep_core_behaviorPackage.MREGION__STATES:
				return states != null && !states.isEmpty();
			case De_imotep_core_behaviorPackage.MREGION__INITIAL_STATE:
				return initialState != null;
			case De_imotep_core_behaviorPackage.MREGION__TRANSITIONS:
				return transitions != null && !transitions.isEmpty();
			case De_imotep_core_behaviorPackage.MREGION__PARENT_STATE_MACHINE:
				return parentStateMachine != null;
		}
		return super.eIsSet(featureID);
	}

} //MRegionImpl
