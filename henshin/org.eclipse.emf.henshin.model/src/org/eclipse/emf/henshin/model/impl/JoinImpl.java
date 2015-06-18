/**
 */
package org.eclipse.emf.henshin.model.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Join;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Join</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.JoinImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.JoinImpl#getJoinFrom <em>Join From</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.JoinImpl#getJoinInto <em>Join Into</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JoinImpl extends EObjectImpl implements Join {
	/**
	 * The cached value of the '{@link #getJoinFrom() <em>Join From</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinFrom()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> joinFrom;
	/**
	 * The cached value of the '{@link #getJoinInto() <em>Join Into</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinInto()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> joinInto;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JoinImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.JOIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule() {
		if (eContainerFeatureID() != HenshinPackage.JOIN__RULE) return null;
		return (Rule)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRule(Rule newRule, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRule, HenshinPackage.JOIN__RULE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(Rule newRule) {
		if (newRule != eInternalContainer() || (eContainerFeatureID() != HenshinPackage.JOIN__RULE && newRule != null)) {
			if (EcoreUtil.isAncestor(this, newRule))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRule != null)
				msgs = ((InternalEObject)newRule).eInverseAdd(this, HenshinPackage.RULE__JOINS, Rule.class, msgs);
			msgs = basicSetRule(newRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.JOIN__RULE, newRule, newRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getJoinFrom() {
		if (joinFrom == null) {
			joinFrom = new EObjectWithInverseResolvingEList<Node>(Node.class, this, HenshinPackage.JOIN__JOIN_FROM, HenshinPackage.NODE__SRC_FOR_JOIN);
		}
		return joinFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getJoinInto() {
		if (joinInto == null) {
			joinInto = new EObjectWithInverseResolvingEList<Node>(Node.class, this, HenshinPackage.JOIN__JOIN_INTO, HenshinPackage.NODE__TGT_FOR_JOIN);
		}
		return joinInto;
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
			case HenshinPackage.JOIN__RULE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetRule((Rule)otherEnd, msgs);
			case HenshinPackage.JOIN__JOIN_FROM:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getJoinFrom()).basicAdd(otherEnd, msgs);
			case HenshinPackage.JOIN__JOIN_INTO:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getJoinInto()).basicAdd(otherEnd, msgs);
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
			case HenshinPackage.JOIN__RULE:
				return basicSetRule(null, msgs);
			case HenshinPackage.JOIN__JOIN_FROM:
				return ((InternalEList<?>)getJoinFrom()).basicRemove(otherEnd, msgs);
			case HenshinPackage.JOIN__JOIN_INTO:
				return ((InternalEList<?>)getJoinInto()).basicRemove(otherEnd, msgs);
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
			case HenshinPackage.JOIN__RULE:
				return eInternalContainer().eInverseRemove(this, HenshinPackage.RULE__JOINS, Rule.class, msgs);
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
			case HenshinPackage.JOIN__RULE:
				return getRule();
			case HenshinPackage.JOIN__JOIN_FROM:
				return getJoinFrom();
			case HenshinPackage.JOIN__JOIN_INTO:
				return getJoinInto();
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
			case HenshinPackage.JOIN__RULE:
				setRule((Rule)newValue);
				return;
			case HenshinPackage.JOIN__JOIN_FROM:
				getJoinFrom().clear();
				getJoinFrom().addAll((Collection<? extends Node>)newValue);
				return;
			case HenshinPackage.JOIN__JOIN_INTO:
				getJoinInto().clear();
				getJoinInto().addAll((Collection<? extends Node>)newValue);
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
			case HenshinPackage.JOIN__RULE:
				setRule((Rule)null);
				return;
			case HenshinPackage.JOIN__JOIN_FROM:
				getJoinFrom().clear();
				return;
			case HenshinPackage.JOIN__JOIN_INTO:
				getJoinInto().clear();
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
			case HenshinPackage.JOIN__RULE:
				return getRule() != null;
			case HenshinPackage.JOIN__JOIN_FROM:
				return joinFrom != null && !joinFrom.isEmpty();
			case HenshinPackage.JOIN__JOIN_INTO:
				return joinInto != null && !joinInto.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //JoinImpl
