/**
 */
package org.sidiff.editrule.rulebase.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.sidiff.editrule.rulebase.PotentialEdgeConflict;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Potential Edge Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialEdgeConflictImpl#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialEdgeConflictImpl#getSourceEdge <em>Source Edge</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialEdgeConflictImpl#getTargetEdge <em>Target Edge</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PotentialEdgeConflictImpl extends PotentialConflictImpl implements PotentialEdgeConflict {
	/**
	 * The cached value of the '{@link #getSourceEdge() <em>Source Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceEdge()
	 * @generated
	 * @ordered
	 */
	protected Edge sourceEdge;
	/**
	 * The cached value of the '{@link #getTargetEdge() <em>Target Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetEdge()
	 * @generated
	 * @ordered
	 */
	protected Edge targetEdge;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialEdgeConflictImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.POTENTIAL_EDGE_CONFLICT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleBase getRuleBase() {
		if (eContainerFeatureID() != RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE) return null;
		return (RuleBase)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleBase(RuleBase newRuleBase, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRuleBase, RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRuleBase(RuleBase newRuleBase) {
		if (newRuleBase != eInternalContainer() || (eContainerFeatureID() != RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE && newRuleBase != null)) {
			if (EcoreUtil.isAncestor(this, newRuleBase))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRuleBase != null)
				msgs = ((InternalEObject)newRuleBase).eInverseAdd(this, RulebasePackage.RULE_BASE__POTENTIAL_EDGE_CONFLICTS, RuleBase.class, msgs);
			msgs = basicSetRuleBase(newRuleBase, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE, newRuleBase, newRuleBase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Edge getSourceEdge() {
		if (sourceEdge != null && sourceEdge.eIsProxy()) {
			InternalEObject oldSourceEdge = (InternalEObject)sourceEdge;
			sourceEdge = (Edge)eResolveProxy(oldSourceEdge);
			if (sourceEdge != oldSourceEdge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_EDGE_CONFLICT__SOURCE_EDGE, oldSourceEdge, sourceEdge));
			}
		}
		return sourceEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetSourceEdge() {
		return sourceEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceEdge(Edge newSourceEdge) {
		Edge oldSourceEdge = sourceEdge;
		sourceEdge = newSourceEdge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_EDGE_CONFLICT__SOURCE_EDGE, oldSourceEdge, sourceEdge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Edge getTargetEdge() {
		if (targetEdge != null && targetEdge.eIsProxy()) {
			InternalEObject oldTargetEdge = (InternalEObject)targetEdge;
			targetEdge = (Edge)eResolveProxy(oldTargetEdge);
			if (targetEdge != oldTargetEdge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_EDGE_CONFLICT__TARGET_EDGE, oldTargetEdge, targetEdge));
			}
		}
		return targetEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetTargetEdge() {
		return targetEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetEdge(Edge newTargetEdge) {
		Edge oldTargetEdge = targetEdge;
		targetEdge = newTargetEdge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_EDGE_CONFLICT__TARGET_EDGE, oldTargetEdge, targetEdge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetRuleBase((RuleBase)otherEnd, msgs);
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
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE:
				return basicSetRuleBase(null, msgs);
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
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE:
				return eInternalContainer().eInverseRemove(this, RulebasePackage.RULE_BASE__POTENTIAL_EDGE_CONFLICTS, RuleBase.class, msgs);
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
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE:
				return getRuleBase();
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__SOURCE_EDGE:
				if (resolve) return getSourceEdge();
				return basicGetSourceEdge();
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__TARGET_EDGE:
				if (resolve) return getTargetEdge();
				return basicGetTargetEdge();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE:
				setRuleBase((RuleBase)newValue);
				return;
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__SOURCE_EDGE:
				setSourceEdge((Edge)newValue);
				return;
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__TARGET_EDGE:
				setTargetEdge((Edge)newValue);
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
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE:
				setRuleBase((RuleBase)null);
				return;
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__SOURCE_EDGE:
				setSourceEdge((Edge)null);
				return;
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__TARGET_EDGE:
				setTargetEdge((Edge)null);
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
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE:
				return getRuleBase() != null;
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__SOURCE_EDGE:
				return sourceEdge != null;
			case RulebasePackage.POTENTIAL_EDGE_CONFLICT__TARGET_EDGE:
				return targetEdge != null;
		}
		return super.eIsSet(featureID);
	}

} //PotentialEdgeConflictImpl
