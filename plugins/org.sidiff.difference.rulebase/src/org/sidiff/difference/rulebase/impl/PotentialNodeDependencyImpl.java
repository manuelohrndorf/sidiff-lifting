/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.rulebase.PotentialNodeDependency;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Potential Node Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.impl.PotentialNodeDependencyImpl#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.PotentialNodeDependencyImpl#getSourceNode <em>Source Node</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.PotentialNodeDependencyImpl#getTargetNode <em>Target Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PotentialNodeDependencyImpl extends PotentialDependencyImpl implements PotentialNodeDependency {
	/**
	 * The cached value of the '{@link #getSourceNode() <em>Source Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceNode()
	 * @generated
	 * @ordered
	 */
	protected Node sourceNode;

	/**
	 * The cached value of the '{@link #getTargetNode() <em>Target Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetNode()
	 * @generated
	 * @ordered
	 */
	protected Node targetNode;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialNodeDependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.POTENTIAL_NODE_DEPENDENCY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBase getRuleBase() {
		if (eContainerFeatureID() != RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE) return null;
		return (RuleBase)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleBase(RuleBase newRuleBase, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRuleBase, RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleBase(RuleBase newRuleBase) {
		if (newRuleBase != eInternalContainer() || (eContainerFeatureID() != RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE && newRuleBase != null)) {
			if (EcoreUtil.isAncestor(this, newRuleBase))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRuleBase != null)
				msgs = ((InternalEObject)newRuleBase).eInverseAdd(this, RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES, RuleBase.class, msgs);
			msgs = basicSetRuleBase(newRuleBase, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE, newRuleBase, newRuleBase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getSourceNode() {
		if (sourceNode != null && sourceNode.eIsProxy()) {
			InternalEObject oldSourceNode = (InternalEObject)sourceNode;
			sourceNode = (Node)eResolveProxy(oldSourceNode);
			if (sourceNode != oldSourceNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_NODE_DEPENDENCY__SOURCE_NODE, oldSourceNode, sourceNode));
			}
		}
		return sourceNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetSourceNode() {
		return sourceNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceNode(Node newSourceNode) {
		Node oldSourceNode = sourceNode;
		sourceNode = newSourceNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_NODE_DEPENDENCY__SOURCE_NODE, oldSourceNode, sourceNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getTargetNode() {
		if (targetNode != null && targetNode.eIsProxy()) {
			InternalEObject oldTargetNode = (InternalEObject)targetNode;
			targetNode = (Node)eResolveProxy(oldTargetNode);
			if (targetNode != oldTargetNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_NODE_DEPENDENCY__TARGET_NODE, oldTargetNode, targetNode));
			}
		}
		return targetNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetTargetNode() {
		return targetNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetNode(Node newTargetNode) {
		Node oldTargetNode = targetNode;
		targetNode = newTargetNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_NODE_DEPENDENCY__TARGET_NODE, oldTargetNode, targetNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE:
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
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE:
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
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE:
				return eInternalContainer().eInverseRemove(this, RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES, RuleBase.class, msgs);
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
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE:
				return getRuleBase();
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__SOURCE_NODE:
				if (resolve) return getSourceNode();
				return basicGetSourceNode();
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__TARGET_NODE:
				if (resolve) return getTargetNode();
				return basicGetTargetNode();
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
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE:
				setRuleBase((RuleBase)newValue);
				return;
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__SOURCE_NODE:
				setSourceNode((Node)newValue);
				return;
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__TARGET_NODE:
				setTargetNode((Node)newValue);
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
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE:
				setRuleBase((RuleBase)null);
				return;
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__SOURCE_NODE:
				setSourceNode((Node)null);
				return;
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__TARGET_NODE:
				setTargetNode((Node)null);
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
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE:
				return getRuleBase() != null;
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__SOURCE_NODE:
				return sourceNode != null;
			case RulebasePackage.POTENTIAL_NODE_DEPENDENCY__TARGET_NODE:
				return targetNode != null;
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public String toString() {
		// Assertions
		Module tsSrcNode = (Module) getSourceNode().getGraph().getRule().eContainer();
		Module tsTgtNode = (Module) getTargetNode().getGraph().getRule().eContainer();
		Module tsSrcRule = getSourceRule().getExecuteModule();
		Module tsTgtRule = getTargetRule().getExecuteModule();
		
//		if(tsSrcNode != tsSrcRule){
//			System.err.println("tsSrcNode != tsSrcRule");
//		}
//		if (tsTgtNode != tsTgtRule){
//			System.err.println("tsTgtNode != tsTgtRule");
//		}
		
		assert (tsSrcNode == tsSrcRule) : tsSrcNode + "!=" + tsSrcRule;
		assert (tsTgtNode == tsTgtRule) : tsTgtNode + "!=" + tsTgtRule;
		// End Assertions
		
		StringBuffer res = new StringBuffer(super.toString());
		res.append("\n\tsrc node: " + getSourceNode());
		res.append("\n\ttgt node: " + getTargetNode());
		
		return res.toString();
	}

} //PotentialNodeDependencyImpl
