/**
 */
package org.sidiff.editrule.rulebase.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

import org.sidiff.editrule.rulebase.PotentialDanglingEdgeDependency;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Potential Dangling Edge Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialDanglingEdgeDependencyImpl#getDeletionNode <em>Deletion Node</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialDanglingEdgeDependencyImpl#getDeletionEdge <em>Deletion Edge</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PotentialDanglingEdgeDependencyImpl extends PotentialDependencyImpl implements PotentialDanglingEdgeDependency {
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
	 * The cached value of the '{@link #getDeletionEdge() <em>Deletion Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletionEdge()
	 * @generated
	 * @ordered
	 */
	protected Edge deletionEdge;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialDanglingEdgeDependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.POTENTIAL_DANGLING_EDGE_DEPENDENCY;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_NODE, oldDeletionNode, deletionNode));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_NODE, oldDeletionNode, deletionNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Edge getDeletionEdge() {
		if (deletionEdge != null && deletionEdge.eIsProxy()) {
			InternalEObject oldDeletionEdge = (InternalEObject)deletionEdge;
			deletionEdge = (Edge)eResolveProxy(oldDeletionEdge);
			if (deletionEdge != oldDeletionEdge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_EDGE, oldDeletionEdge, deletionEdge));
			}
		}
		return deletionEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetDeletionEdge() {
		return deletionEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeletionEdge(Edge newDeletionEdge) {
		Edge oldDeletionEdge = deletionEdge;
		deletionEdge = newDeletionEdge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_EDGE, oldDeletionEdge, deletionEdge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_NODE:
				if (resolve) return getDeletionNode();
				return basicGetDeletionNode();
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_EDGE:
				if (resolve) return getDeletionEdge();
				return basicGetDeletionEdge();
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
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_NODE:
				setDeletionNode((Node)newValue);
				return;
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_EDGE:
				setDeletionEdge((Edge)newValue);
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
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_NODE:
				setDeletionNode((Node)null);
				return;
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_EDGE:
				setDeletionEdge((Edge)null);
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
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_NODE:
				return deletionNode != null;
			case RulebasePackage.POTENTIAL_DANGLING_EDGE_DEPENDENCY__DELETION_EDGE:
				return deletionEdge != null;
		}
		return super.eIsSet(featureID);
	}

} //PotentialDanglingEdgeDependencyImpl
