/**
 */
package org.sidiff.editrule.rulebase.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

import org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Potential Dangling Edge Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialDanglingEdgeConflictImpl#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialDanglingEdgeConflictImpl#getDeletionNode <em>Deletion Node</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialDanglingEdgeConflictImpl#getCreationEdge <em>Creation Edge</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PotentialDanglingEdgeConflictImpl extends PotentialConflictImpl implements PotentialDanglingEdgeConflict {
	/**
	 * The cached value of the '{@link #getRuleBase() <em>Rule Base</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleBase()
	 * @generated
	 * @ordered
	 */
	protected RuleBase ruleBase;

	/**
	 * The cached value of the '{@link #getDeletionNode() <em>Deletion Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletionNode()
	 * @generated
	 * @ordered
	 */
	protected Node deletionNode;

	/**
	 * The cached value of the '{@link #getCreationEdge() <em>Creation Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationEdge()
	 * @generated
	 * @ordered
	 */
	protected Edge creationEdge;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialDanglingEdgeConflictImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.POTENTIAL_DANGLING_EDGE_CONFLICT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleBase getRuleBase() {
		if (ruleBase != null && ruleBase.eIsProxy()) {
			InternalEObject oldRuleBase = (InternalEObject)ruleBase;
			ruleBase = (RuleBase)eResolveProxy(oldRuleBase);
			if (ruleBase != oldRuleBase) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE, oldRuleBase, ruleBase));
			}
		}
		return ruleBase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBase basicGetRuleBase() {
		return ruleBase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleBase(RuleBase newRuleBase, NotificationChain msgs) {
		RuleBase oldRuleBase = ruleBase;
		ruleBase = newRuleBase;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE, oldRuleBase, newRuleBase);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRuleBase(RuleBase newRuleBase) {
		if (newRuleBase != ruleBase) {
			NotificationChain msgs = null;
			if (ruleBase != null)
				msgs = ((InternalEObject)ruleBase).eInverseRemove(this, RulebasePackage.RULE_BASE__POTENTIAL_DANGLING_EDGE_CONFLICTS, RuleBase.class, msgs);
			if (newRuleBase != null)
				msgs = ((InternalEObject)newRuleBase).eInverseAdd(this, RulebasePackage.RULE_BASE__POTENTIAL_DANGLING_EDGE_CONFLICTS, RuleBase.class, msgs);
			msgs = basicSetRuleBase(newRuleBase, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE, newRuleBase, newRuleBase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Node getDeletionNode() {
		if (deletionNode != null && deletionNode.eIsProxy()) {
			InternalEObject oldDeletionNode = (InternalEObject)deletionNode;
			deletionNode = (Node)eResolveProxy(oldDeletionNode);
			if (deletionNode != oldDeletionNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__DELETION_NODE, oldDeletionNode, deletionNode));
			}
		}
		return deletionNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetDeletionNode() {
		return deletionNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeletionNode(Node newDeletionNode) {
		Node oldDeletionNode = deletionNode;
		deletionNode = newDeletionNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__DELETION_NODE, oldDeletionNode, deletionNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Edge getCreationEdge() {
		if (creationEdge != null && creationEdge.eIsProxy()) {
			InternalEObject oldCreationEdge = (InternalEObject)creationEdge;
			creationEdge = (Edge)eResolveProxy(oldCreationEdge);
			if (creationEdge != oldCreationEdge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__CREATION_EDGE, oldCreationEdge, creationEdge));
			}
		}
		return creationEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetCreationEdge() {
		return creationEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreationEdge(Edge newCreationEdge) {
		Edge oldCreationEdge = creationEdge;
		creationEdge = newCreationEdge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__CREATION_EDGE, oldCreationEdge, creationEdge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE:
				if (ruleBase != null)
					msgs = ((InternalEObject)ruleBase).eInverseRemove(this, RulebasePackage.RULE_BASE__POTENTIAL_DANGLING_EDGE_CONFLICTS, RuleBase.class, msgs);
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
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE:
				if (resolve) return getRuleBase();
				return basicGetRuleBase();
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__DELETION_NODE:
				if (resolve) return getDeletionNode();
				return basicGetDeletionNode();
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__CREATION_EDGE:
				if (resolve) return getCreationEdge();
				return basicGetCreationEdge();
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
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE:
				setRuleBase((RuleBase)newValue);
				return;
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__DELETION_NODE:
				setDeletionNode((Node)newValue);
				return;
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__CREATION_EDGE:
				setCreationEdge((Edge)newValue);
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
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE:
				setRuleBase((RuleBase)null);
				return;
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__DELETION_NODE:
				setDeletionNode((Node)null);
				return;
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__CREATION_EDGE:
				setCreationEdge((Edge)null);
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
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__RULE_BASE:
				return ruleBase != null;
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__DELETION_NODE:
				return deletionNode != null;
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_CONFLICT__CREATION_EDGE:
				return creationEdge != null;
		}
		return super.eIsSet(featureID);
	}

} //PotentialDanglingEdgeConflictImpl
