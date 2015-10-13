/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.impl;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MAbstractState;
import de.imotep.core.behavior.de_imotep_core_behavior.MAction;
import de.imotep.core.behavior.de_imotep_core_behavior.MEvent;
import de.imotep.core.behavior.de_imotep_core_behavior.MGuard;
import de.imotep.core.behavior.de_imotep_core_behavior.MRegion;
import de.imotep.core.behavior.de_imotep_core_behavior.MTransition;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MTransition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl#getSourceState <em>Source State</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl#getTargetState <em>Target State</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl#getEvent <em>Event</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl#getParentRegion <em>Parent Region</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl#isInternal <em>Internal</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MTransitionImpl#getPriority <em>Priority</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MTransitionImpl extends MBehaviorEntityImpl implements MTransition {
	/**
	 * The cached value of the '{@link #getSourceState() <em>Source State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceState()
	 * @generated
	 * @ordered
	 */
	protected MAbstractState sourceState;

	/**
	 * The cached value of the '{@link #getTargetState() <em>Target State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetState()
	 * @generated
	 * @ordered
	 */
	protected MAbstractState targetState;

	/**
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected MGuard guard;

	/**
	 * The cached value of the '{@link #getEvent() <em>Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvent()
	 * @generated
	 * @ordered
	 */
	protected MEvent event;

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
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList<MAction> actions;

	/**
	 * The default value of the '{@link #isInternal() <em>Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInternal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INTERNAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInternal() <em>Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInternal()
	 * @generated
	 * @ordered
	 */
	protected boolean internal = INTERNAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIORITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected int priority = PRIORITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MTransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return De_imotep_core_behaviorPackage.Literals.MTRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MAbstractState getSourceState() {
		if (sourceState != null && sourceState.eIsProxy()) {
			InternalEObject oldSourceState = (InternalEObject)sourceState;
			sourceState = (MAbstractState)eResolveProxy(oldSourceState);
			if (sourceState != oldSourceState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, De_imotep_core_behaviorPackage.MTRANSITION__SOURCE_STATE, oldSourceState, sourceState));
			}
		}
		return sourceState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MAbstractState basicGetSourceState() {
		return sourceState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSourceState(MAbstractState newSourceState, NotificationChain msgs) {
		MAbstractState oldSourceState = sourceState;
		sourceState = newSourceState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MTRANSITION__SOURCE_STATE, oldSourceState, newSourceState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceState(MAbstractState newSourceState) {
		if (newSourceState != sourceState) {
			NotificationChain msgs = null;
			if (sourceState != null)
				msgs = ((InternalEObject)sourceState).eInverseRemove(this, De_imotep_core_behaviorPackage.MABSTRACT_STATE__OUTGOING_TRANSITIONS, MAbstractState.class, msgs);
			if (newSourceState != null)
				msgs = ((InternalEObject)newSourceState).eInverseAdd(this, De_imotep_core_behaviorPackage.MABSTRACT_STATE__OUTGOING_TRANSITIONS, MAbstractState.class, msgs);
			msgs = basicSetSourceState(newSourceState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MTRANSITION__SOURCE_STATE, newSourceState, newSourceState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MAbstractState getTargetState() {
		if (targetState != null && targetState.eIsProxy()) {
			InternalEObject oldTargetState = (InternalEObject)targetState;
			targetState = (MAbstractState)eResolveProxy(oldTargetState);
			if (targetState != oldTargetState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, De_imotep_core_behaviorPackage.MTRANSITION__TARGET_STATE, oldTargetState, targetState));
			}
		}
		return targetState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MAbstractState basicGetTargetState() {
		return targetState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTargetState(MAbstractState newTargetState, NotificationChain msgs) {
		MAbstractState oldTargetState = targetState;
		targetState = newTargetState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MTRANSITION__TARGET_STATE, oldTargetState, newTargetState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetState(MAbstractState newTargetState) {
		if (newTargetState != targetState) {
			NotificationChain msgs = null;
			if (targetState != null)
				msgs = ((InternalEObject)targetState).eInverseRemove(this, De_imotep_core_behaviorPackage.MABSTRACT_STATE__INCOMING_TRANSITIONS, MAbstractState.class, msgs);
			if (newTargetState != null)
				msgs = ((InternalEObject)newTargetState).eInverseAdd(this, De_imotep_core_behaviorPackage.MABSTRACT_STATE__INCOMING_TRANSITIONS, MAbstractState.class, msgs);
			msgs = basicSetTargetState(newTargetState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MTRANSITION__TARGET_STATE, newTargetState, newTargetState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MGuard getGuard() {
		if (guard != null && guard.eIsProxy()) {
			InternalEObject oldGuard = (InternalEObject)guard;
			guard = (MGuard)eResolveProxy(oldGuard);
			if (guard != oldGuard) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, De_imotep_core_behaviorPackage.MTRANSITION__GUARD, oldGuard, guard));
			}
		}
		return guard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MGuard basicGetGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuard(MGuard newGuard) {
		MGuard oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MTRANSITION__GUARD, oldGuard, guard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MEvent getEvent() {
		if (event != null && event.eIsProxy()) {
			InternalEObject oldEvent = (InternalEObject)event;
			event = (MEvent)eResolveProxy(oldEvent);
			if (event != oldEvent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, De_imotep_core_behaviorPackage.MTRANSITION__EVENT, oldEvent, event));
			}
		}
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MEvent basicGetEvent() {
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvent(MEvent newEvent) {
		MEvent oldEvent = event;
		event = newEvent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MTRANSITION__EVENT, oldEvent, event));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, De_imotep_core_behaviorPackage.MTRANSITION__PARENT_REGION, oldParentRegion, parentRegion));
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
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MTRANSITION__PARENT_REGION, oldParentRegion, parentRegion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAction> getActions() {
		if (actions == null) {
			actions = new EObjectResolvingEList<MAction>(MAction.class, this, De_imotep_core_behaviorPackage.MTRANSITION__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInternal() {
		return internal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInternal(boolean newInternal) {
		boolean oldInternal = internal;
		internal = newInternal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MTRANSITION__INTERNAL, oldInternal, internal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(int newPriority) {
		int oldPriority = priority;
		priority = newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MTRANSITION__PRIORITY, oldPriority, priority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case De_imotep_core_behaviorPackage.MTRANSITION__SOURCE_STATE:
				if (sourceState != null)
					msgs = ((InternalEObject)sourceState).eInverseRemove(this, De_imotep_core_behaviorPackage.MABSTRACT_STATE__OUTGOING_TRANSITIONS, MAbstractState.class, msgs);
				return basicSetSourceState((MAbstractState)otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MTRANSITION__TARGET_STATE:
				if (targetState != null)
					msgs = ((InternalEObject)targetState).eInverseRemove(this, De_imotep_core_behaviorPackage.MABSTRACT_STATE__INCOMING_TRANSITIONS, MAbstractState.class, msgs);
				return basicSetTargetState((MAbstractState)otherEnd, msgs);
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
			case De_imotep_core_behaviorPackage.MTRANSITION__SOURCE_STATE:
				return basicSetSourceState(null, msgs);
			case De_imotep_core_behaviorPackage.MTRANSITION__TARGET_STATE:
				return basicSetTargetState(null, msgs);
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
			case De_imotep_core_behaviorPackage.MTRANSITION__SOURCE_STATE:
				if (resolve) return getSourceState();
				return basicGetSourceState();
			case De_imotep_core_behaviorPackage.MTRANSITION__TARGET_STATE:
				if (resolve) return getTargetState();
				return basicGetTargetState();
			case De_imotep_core_behaviorPackage.MTRANSITION__GUARD:
				if (resolve) return getGuard();
				return basicGetGuard();
			case De_imotep_core_behaviorPackage.MTRANSITION__EVENT:
				if (resolve) return getEvent();
				return basicGetEvent();
			case De_imotep_core_behaviorPackage.MTRANSITION__PARENT_REGION:
				if (resolve) return getParentRegion();
				return basicGetParentRegion();
			case De_imotep_core_behaviorPackage.MTRANSITION__ACTIONS:
				return getActions();
			case De_imotep_core_behaviorPackage.MTRANSITION__INTERNAL:
				return isInternal();
			case De_imotep_core_behaviorPackage.MTRANSITION__PRIORITY:
				return getPriority();
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
			case De_imotep_core_behaviorPackage.MTRANSITION__SOURCE_STATE:
				setSourceState((MAbstractState)newValue);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__TARGET_STATE:
				setTargetState((MAbstractState)newValue);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__GUARD:
				setGuard((MGuard)newValue);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__EVENT:
				setEvent((MEvent)newValue);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__PARENT_REGION:
				setParentRegion((MRegion)newValue);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__ACTIONS:
				getActions().clear();
				getActions().addAll((Collection<? extends MAction>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__INTERNAL:
				setInternal((Boolean)newValue);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__PRIORITY:
				setPriority((Integer)newValue);
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
			case De_imotep_core_behaviorPackage.MTRANSITION__SOURCE_STATE:
				setSourceState((MAbstractState)null);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__TARGET_STATE:
				setTargetState((MAbstractState)null);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__GUARD:
				setGuard((MGuard)null);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__EVENT:
				setEvent((MEvent)null);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__PARENT_REGION:
				setParentRegion((MRegion)null);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__ACTIONS:
				getActions().clear();
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__INTERNAL:
				setInternal(INTERNAL_EDEFAULT);
				return;
			case De_imotep_core_behaviorPackage.MTRANSITION__PRIORITY:
				setPriority(PRIORITY_EDEFAULT);
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
			case De_imotep_core_behaviorPackage.MTRANSITION__SOURCE_STATE:
				return sourceState != null;
			case De_imotep_core_behaviorPackage.MTRANSITION__TARGET_STATE:
				return targetState != null;
			case De_imotep_core_behaviorPackage.MTRANSITION__GUARD:
				return guard != null;
			case De_imotep_core_behaviorPackage.MTRANSITION__EVENT:
				return event != null;
			case De_imotep_core_behaviorPackage.MTRANSITION__PARENT_REGION:
				return parentRegion != null;
			case De_imotep_core_behaviorPackage.MTRANSITION__ACTIONS:
				return actions != null && !actions.isEmpty();
			case De_imotep_core_behaviorPackage.MTRANSITION__INTERNAL:
				return internal != INTERNAL_EDEFAULT;
			case De_imotep_core_behaviorPackage.MTRANSITION__PRIORITY:
				return priority != PRIORITY_EDEFAULT;
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
		result.append(" (internal: ");
		result.append(internal);
		result.append(", priority: ");
		result.append(priority);
		result.append(')');
		return result.toString();
	}

} //MTransitionImpl
