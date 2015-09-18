/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.impl;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState;
import de.imotep.core.behavior.de_imotep_core_behavior.MRegion;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateGroup;
import de.imotep.core.behavior.de_imotep_core_behavior.MTransition;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MAbstract State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MAbstractStateImpl#getStateGroup <em>State Group</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MAbstractStateImpl#getParentRegion <em>Parent Region</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MAbstractStateImpl#getOutgoingTransitions <em>Outgoing Transitions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MAbstractStateImpl#getIncomingTransitions <em>Incoming Transitions</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MAbstractStateImpl extends MBehaviorEntityImpl implements MAbstractState {
	/**
	 * The cached value of the '{@link #getStateGroup() <em>State Group</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateGroup()
	 * @generated
	 * @ordered
	 */
	protected MStateGroup stateGroup;

	/**
	 * The cached value of the '{@link #getParentRegion() <em>Parent Region</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentRegion()
	 * @generated
	 * @ordered
	 */
	protected MRegion parentRegion;

	/**
	 * The cached value of the '{@link #getOutgoingTransitions() <em>Outgoing Transitions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<MTransition> outgoingTransitions;

	/**
	 * The cached value of the '{@link #getIncomingTransitions() <em>Incoming Transitions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingTransitions()
	 * @generated
	 * @ordered
	 */
	protected EList<MTransition> incomingTransitions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MAbstractStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return De_imotep_core_behaviorPackage.Literals.MABSTRACT_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStateGroup getStateGroup() {
		if (stateGroup != null && stateGroup.eIsProxy()) {
			InternalEObject oldStateGroup = (InternalEObject)stateGroup;
			stateGroup = (MStateGroup)eResolveProxy(oldStateGroup);
			if (stateGroup != oldStateGroup) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, De_imotep_core_behaviorPackage.MABSTRACT_STATE__STATE_GROUP, oldStateGroup, stateGroup));
			}
		}
		return stateGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MStateGroup basicGetStateGroup() {
		return stateGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStateGroup(MStateGroup newStateGroup, NotificationChain msgs) {
		MStateGroup oldStateGroup = stateGroup;
		stateGroup = newStateGroup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MABSTRACT_STATE__STATE_GROUP, oldStateGroup, newStateGroup);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateGroup(MStateGroup newStateGroup) {
		if (newStateGroup != stateGroup) {
			NotificationChain msgs = null;
			if (stateGroup != null)
				msgs = ((InternalEObject)stateGroup).eInverseRemove(this, De_imotep_core_behaviorPackage.MSTATE_GROUP__STATES, MStateGroup.class, msgs);
			if (newStateGroup != null)
				msgs = ((InternalEObject)newStateGroup).eInverseAdd(this, De_imotep_core_behaviorPackage.MSTATE_GROUP__STATES, MStateGroup.class, msgs);
			msgs = basicSetStateGroup(newStateGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MABSTRACT_STATE__STATE_GROUP, newStateGroup, newStateGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MRegion getParentRegion() {
		if (parentRegion != null && parentRegion.eIsProxy()) {
			InternalEObject oldParentRegion = (InternalEObject)parentRegion;
			parentRegion = (MRegion)eResolveProxy(oldParentRegion);
			if (parentRegion != oldParentRegion) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, De_imotep_core_behaviorPackage.MABSTRACT_STATE__PARENT_REGION, oldParentRegion, parentRegion));
			}
		}
		return parentRegion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MRegion basicGetParentRegion() {
		return parentRegion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentRegion(MRegion newParentRegion) {
		MRegion oldParentRegion = parentRegion;
		parentRegion = newParentRegion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MABSTRACT_STATE__PARENT_REGION, oldParentRegion, parentRegion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MTransition> getOutgoingTransitions() {
		if (outgoingTransitions == null) {
			outgoingTransitions = new EObjectWithInverseResolvingEList<MTransition>(MTransition.class, this, De_imotep_core_behaviorPackage.MABSTRACT_STATE__OUTGOING_TRANSITIONS, De_imotep_core_behaviorPackage.MTRANSITION__SOURCE_STATE);
		}
		return outgoingTransitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MTransition> getIncomingTransitions() {
		if (incomingTransitions == null) {
			incomingTransitions = new EObjectWithInverseResolvingEList<MTransition>(MTransition.class, this, De_imotep_core_behaviorPackage.MABSTRACT_STATE__INCOMING_TRANSITIONS, De_imotep_core_behaviorPackage.MTRANSITION__TARGET_STATE);
		}
		return incomingTransitions;
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
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__STATE_GROUP:
				if (stateGroup != null)
					msgs = ((InternalEObject)stateGroup).eInverseRemove(this, De_imotep_core_behaviorPackage.MSTATE_GROUP__STATES, MStateGroup.class, msgs);
				return basicSetStateGroup((MStateGroup)otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__OUTGOING_TRANSITIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingTransitions()).basicAdd(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__INCOMING_TRANSITIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingTransitions()).basicAdd(otherEnd, msgs);
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
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__STATE_GROUP:
				return basicSetStateGroup(null, msgs);
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__OUTGOING_TRANSITIONS:
				return ((InternalEList<?>)getOutgoingTransitions()).basicRemove(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__INCOMING_TRANSITIONS:
				return ((InternalEList<?>)getIncomingTransitions()).basicRemove(otherEnd, msgs);
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
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__STATE_GROUP:
				if (resolve) return getStateGroup();
				return basicGetStateGroup();
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__PARENT_REGION:
				if (resolve) return getParentRegion();
				return basicGetParentRegion();
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__OUTGOING_TRANSITIONS:
				return getOutgoingTransitions();
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__INCOMING_TRANSITIONS:
				return getIncomingTransitions();
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
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__STATE_GROUP:
				setStateGroup((MStateGroup)newValue);
				return;
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__PARENT_REGION:
				setParentRegion((MRegion)newValue);
				return;
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__OUTGOING_TRANSITIONS:
				getOutgoingTransitions().clear();
				getOutgoingTransitions().addAll((Collection<? extends MTransition>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__INCOMING_TRANSITIONS:
				getIncomingTransitions().clear();
				getIncomingTransitions().addAll((Collection<? extends MTransition>)newValue);
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
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__STATE_GROUP:
				setStateGroup((MStateGroup)null);
				return;
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__PARENT_REGION:
				setParentRegion((MRegion)null);
				return;
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__OUTGOING_TRANSITIONS:
				getOutgoingTransitions().clear();
				return;
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__INCOMING_TRANSITIONS:
				getIncomingTransitions().clear();
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
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__STATE_GROUP:
				return stateGroup != null;
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__PARENT_REGION:
				return parentRegion != null;
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__OUTGOING_TRANSITIONS:
				return outgoingTransitions != null && !outgoingTransitions.isEmpty();
			case De_imotep_core_behaviorPackage.MABSTRACT_STATE__INCOMING_TRANSITIONS:
				return incomingTransitions != null && !incomingTransitions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MAbstractStateImpl
