/**
 */
package org.sidiff.model.statemachine.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.model.statemachine.Action;
import org.sidiff.model.statemachine.Region;
import org.sidiff.model.statemachine.State;
import org.sidiff.model.statemachine.StatemachinePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.model.statemachine.impl.StateImpl#isIsComposite <em>Is Composite</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.impl.StateImpl#getRegions <em>Regions</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.impl.StateImpl#getDoAction <em>Do Action</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.impl.StateImpl#getEntryAction <em>Entry Action</em>}</li>
 *   <li>{@link org.sidiff.model.statemachine.impl.StateImpl#getExitAction <em>Exit Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StateImpl extends VertexImpl implements State {
	/**
	 * The default value of the '{@link #isIsComposite() <em>Is Composite</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsComposite()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_COMPOSITE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #getRegions() <em>Regions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegions()
	 * @generated
	 * @ordered
	 */
	protected EList<Region> regions;

	/**
	 * The cached value of the '{@link #getDoAction() <em>Do Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoAction()
	 * @generated
	 * @ordered
	 */
	protected Action doAction;

	/**
	 * The cached value of the '{@link #getEntryAction() <em>Entry Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryAction()
	 * @generated
	 * @ordered
	 */
	protected Action entryAction;

	/**
	 * The cached value of the '{@link #getExitAction() <em>Exit Action</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExitAction()
	 * @generated
	 * @ordered
	 */
	protected Action exitAction;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatemachinePackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isIsComposite() {
		return regions.size()>0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Region> getRegions() {
		if (regions == null) {
			regions = new EObjectContainmentEList<Region>(Region.class, this, StatemachinePackage.STATE__REGIONS);
		}
		return regions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action getDoAction() {
		return doAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDoAction(Action newDoAction, NotificationChain msgs) {
		Action oldDoAction = doAction;
		doAction = newDoAction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatemachinePackage.STATE__DO_ACTION, oldDoAction, newDoAction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDoAction(Action newDoAction) {
		if (newDoAction != doAction) {
			NotificationChain msgs = null;
			if (doAction != null)
				msgs = ((InternalEObject)doAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatemachinePackage.STATE__DO_ACTION, null, msgs);
			if (newDoAction != null)
				msgs = ((InternalEObject)newDoAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatemachinePackage.STATE__DO_ACTION, null, msgs);
			msgs = basicSetDoAction(newDoAction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatemachinePackage.STATE__DO_ACTION, newDoAction, newDoAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action getEntryAction() {
		return entryAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntryAction(Action newEntryAction, NotificationChain msgs) {
		Action oldEntryAction = entryAction;
		entryAction = newEntryAction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatemachinePackage.STATE__ENTRY_ACTION, oldEntryAction, newEntryAction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntryAction(Action newEntryAction) {
		if (newEntryAction != entryAction) {
			NotificationChain msgs = null;
			if (entryAction != null)
				msgs = ((InternalEObject)entryAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatemachinePackage.STATE__ENTRY_ACTION, null, msgs);
			if (newEntryAction != null)
				msgs = ((InternalEObject)newEntryAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatemachinePackage.STATE__ENTRY_ACTION, null, msgs);
			msgs = basicSetEntryAction(newEntryAction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatemachinePackage.STATE__ENTRY_ACTION, newEntryAction, newEntryAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action getExitAction() {
		return exitAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExitAction(Action newExitAction, NotificationChain msgs) {
		Action oldExitAction = exitAction;
		exitAction = newExitAction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StatemachinePackage.STATE__EXIT_ACTION, oldExitAction, newExitAction);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExitAction(Action newExitAction) {
		if (newExitAction != exitAction) {
			NotificationChain msgs = null;
			if (exitAction != null)
				msgs = ((InternalEObject)exitAction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StatemachinePackage.STATE__EXIT_ACTION, null, msgs);
			if (newExitAction != null)
				msgs = ((InternalEObject)newExitAction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StatemachinePackage.STATE__EXIT_ACTION, null, msgs);
			msgs = basicSetExitAction(newExitAction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatemachinePackage.STATE__EXIT_ACTION, newExitAction, newExitAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StatemachinePackage.STATE__REGIONS:
				return ((InternalEList<?>)getRegions()).basicRemove(otherEnd, msgs);
			case StatemachinePackage.STATE__DO_ACTION:
				return basicSetDoAction(null, msgs);
			case StatemachinePackage.STATE__ENTRY_ACTION:
				return basicSetEntryAction(null, msgs);
			case StatemachinePackage.STATE__EXIT_ACTION:
				return basicSetExitAction(null, msgs);
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
			case StatemachinePackage.STATE__IS_COMPOSITE:
				return isIsComposite();
			case StatemachinePackage.STATE__REGIONS:
				return getRegions();
			case StatemachinePackage.STATE__DO_ACTION:
				return getDoAction();
			case StatemachinePackage.STATE__ENTRY_ACTION:
				return getEntryAction();
			case StatemachinePackage.STATE__EXIT_ACTION:
				return getExitAction();
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
			case StatemachinePackage.STATE__REGIONS:
				getRegions().clear();
				getRegions().addAll((Collection<? extends Region>)newValue);
				return;
			case StatemachinePackage.STATE__DO_ACTION:
				setDoAction((Action)newValue);
				return;
			case StatemachinePackage.STATE__ENTRY_ACTION:
				setEntryAction((Action)newValue);
				return;
			case StatemachinePackage.STATE__EXIT_ACTION:
				setExitAction((Action)newValue);
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
			case StatemachinePackage.STATE__REGIONS:
				getRegions().clear();
				return;
			case StatemachinePackage.STATE__DO_ACTION:
				setDoAction((Action)null);
				return;
			case StatemachinePackage.STATE__ENTRY_ACTION:
				setEntryAction((Action)null);
				return;
			case StatemachinePackage.STATE__EXIT_ACTION:
				setExitAction((Action)null);
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
			case StatemachinePackage.STATE__IS_COMPOSITE:
				return isIsComposite() != IS_COMPOSITE_EDEFAULT;
			case StatemachinePackage.STATE__REGIONS:
				return regions != null && !regions.isEmpty();
			case StatemachinePackage.STATE__DO_ACTION:
				return doAction != null;
			case StatemachinePackage.STATE__ENTRY_ACTION:
				return entryAction != null;
			case StatemachinePackage.STATE__EXIT_ACTION:
				return exitAction != null;
		}
		return super.eIsSet(featureID);
	}

} //StateImpl
