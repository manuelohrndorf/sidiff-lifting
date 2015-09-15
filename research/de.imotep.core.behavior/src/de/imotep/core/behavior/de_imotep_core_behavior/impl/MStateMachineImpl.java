/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.impl;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MAction;
import de.imotep.core.behavior.de_imotep_core_behavior.MEvent;
import de.imotep.core.behavior.de_imotep_core_behavior.MGuard;
import de.imotep.core.behavior.de_imotep_core_behavior.MRegion;
import de.imotep.core.behavior.de_imotep_core_behavior.MStateMachine;

import de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MState Machine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl#getRegions <em>Regions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl#getActions <em>Actions</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl#getRootRegion <em>Root Region</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl#getGuards <em>Guards</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl#getEvents <em>Events</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MStateMachineImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MStateMachineImpl extends MBehaviorEntityImpl implements MStateMachine {
	/**
	 * The cached value of the '{@link #getRegions() <em>Regions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegions()
	 * @generated
	 * @ordered
	 */
	protected EList<MRegion> regions;

	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList<MAction> actions;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<MAttribute> attributes;

	/**
	 * The cached value of the '{@link #getRootRegion() <em>Root Region</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootRegion()
	 * @generated
	 * @ordered
	 */
	protected MRegion rootRegion;

	/**
	 * The cached value of the '{@link #getGuards() <em>Guards</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuards()
	 * @generated
	 * @ordered
	 */
	protected EList<MGuard> guards;

	/**
	 * The cached value of the '{@link #getEvents() <em>Events</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvents()
	 * @generated
	 * @ordered
	 */
	protected EList<MEvent> events;

	/**
	 * The default value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected static final String POSITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected String position = POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MStateMachineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return De_imotep_core_behaviorPackage.Literals.MSTATE_MACHINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MRegion> getRegions() {
		if (regions == null) {
			regions = new EObjectWithInverseResolvingEList<MRegion>(MRegion.class, this, De_imotep_core_behaviorPackage.MSTATE_MACHINE__REGIONS, De_imotep_core_behaviorPackage.MREGION__PARENT_STATE_MACHINE);
		}
		return regions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAction> getActions() {
		if (actions == null) {
			actions = new EObjectContainmentEList<MAction>(MAction.class, this, De_imotep_core_behaviorPackage.MSTATE_MACHINE__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<MAttribute>(MAttribute.class, this, De_imotep_core_behaviorPackage.MSTATE_MACHINE__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MRegion getRootRegion() {
		return rootRegion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRootRegion(MRegion newRootRegion, NotificationChain msgs) {
		MRegion oldRootRegion = rootRegion;
		rootRegion = newRootRegion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MSTATE_MACHINE__ROOT_REGION, oldRootRegion, newRootRegion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootRegion(MRegion newRootRegion) {
		if (newRootRegion != rootRegion) {
			NotificationChain msgs = null;
			if (rootRegion != null)
				msgs = ((InternalEObject)rootRegion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - De_imotep_core_behaviorPackage.MSTATE_MACHINE__ROOT_REGION, null, msgs);
			if (newRootRegion != null)
				msgs = ((InternalEObject)newRootRegion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - De_imotep_core_behaviorPackage.MSTATE_MACHINE__ROOT_REGION, null, msgs);
			msgs = basicSetRootRegion(newRootRegion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MSTATE_MACHINE__ROOT_REGION, newRootRegion, newRootRegion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MGuard> getGuards() {
		if (guards == null) {
			guards = new EObjectContainmentEList<MGuard>(MGuard.class, this, De_imotep_core_behaviorPackage.MSTATE_MACHINE__GUARDS);
		}
		return guards;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MEvent> getEvents() {
		if (events == null) {
			events = new EObjectContainmentEList<MEvent>(MEvent.class, this, De_imotep_core_behaviorPackage.MSTATE_MACHINE__EVENTS);
		}
		return events;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPosition(String newPosition) {
		String oldPosition = position;
		position = newPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MSTATE_MACHINE__POSITION, oldPosition, position));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MSTATE_MACHINE__TYPE, oldType, type));
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
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__REGIONS:
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
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__REGIONS:
				return ((InternalEList<?>)getRegions()).basicRemove(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ACTIONS:
				return ((InternalEList<?>)getActions()).basicRemove(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ROOT_REGION:
				return basicSetRootRegion(null, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__GUARDS:
				return ((InternalEList<?>)getGuards()).basicRemove(otherEnd, msgs);
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__EVENTS:
				return ((InternalEList<?>)getEvents()).basicRemove(otherEnd, msgs);
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
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__REGIONS:
				return getRegions();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ACTIONS:
				return getActions();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ATTRIBUTES:
				return getAttributes();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ROOT_REGION:
				return getRootRegion();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__GUARDS:
				return getGuards();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__EVENTS:
				return getEvents();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__POSITION:
				return getPosition();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__TYPE:
				return getType();
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
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__REGIONS:
				getRegions().clear();
				getRegions().addAll((Collection<? extends MRegion>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ACTIONS:
				getActions().clear();
				getActions().addAll((Collection<? extends MAction>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends MAttribute>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ROOT_REGION:
				setRootRegion((MRegion)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__GUARDS:
				getGuards().clear();
				getGuards().addAll((Collection<? extends MGuard>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__EVENTS:
				getEvents().clear();
				getEvents().addAll((Collection<? extends MEvent>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__POSITION:
				setPosition((String)newValue);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__TYPE:
				setType((String)newValue);
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
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__REGIONS:
				getRegions().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ACTIONS:
				getActions().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ATTRIBUTES:
				getAttributes().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ROOT_REGION:
				setRootRegion((MRegion)null);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__GUARDS:
				getGuards().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__EVENTS:
				getEvents().clear();
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__POSITION:
				setPosition(POSITION_EDEFAULT);
				return;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__TYPE:
				setType(TYPE_EDEFAULT);
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
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__REGIONS:
				return regions != null && !regions.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ACTIONS:
				return actions != null && !actions.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__ROOT_REGION:
				return rootRegion != null;
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__GUARDS:
				return guards != null && !guards.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__EVENTS:
				return events != null && !events.isEmpty();
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__POSITION:
				return POSITION_EDEFAULT == null ? position != null : !POSITION_EDEFAULT.equals(position);
			case De_imotep_core_behaviorPackage.MSTATE_MACHINE__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
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
		result.append(" (position: ");
		result.append(position);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //MStateMachineImpl
