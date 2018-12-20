/**
 */
package org.sidiff.editrule.rulebase.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.editrule.rulebase.PotentialAttributeConflict;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Potential Attribute Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialAttributeConflictImpl#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialAttributeConflictImpl#getSourceAttribute <em>Source Attribute</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialAttributeConflictImpl#getTargetAttribute <em>Target Attribute</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialAttributeConflictImpl#getSourceNode <em>Source Node</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialAttributeConflictImpl#getTargetNode <em>Target Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PotentialAttributeConflictImpl extends PotentialConflictImpl implements PotentialAttributeConflict {
	/**
	 * The cached value of the '{@link #getSourceAttribute() <em>Source Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceAttribute()
	 * @generated
	 * @ordered
	 */
	protected Attribute sourceAttribute;
	/**
	 * The cached value of the '{@link #getTargetAttribute() <em>Target Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetAttribute()
	 * @generated
	 * @ordered
	 */
	protected Attribute targetAttribute;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialAttributeConflictImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.POTENTIAL_ATTRIBUTE_CONFLICT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleBase getRuleBase() {
		if (eContainerFeatureID() != RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE) return null;
		return (RuleBase)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleBase(RuleBase newRuleBase, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRuleBase, RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRuleBase(RuleBase newRuleBase) {
		if (newRuleBase != eInternalContainer() || (eContainerFeatureID() != RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE && newRuleBase != null)) {
			if (EcoreUtil.isAncestor(this, newRuleBase))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRuleBase != null)
				msgs = ((InternalEObject)newRuleBase).eInverseAdd(this, RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS, RuleBase.class, msgs);
			msgs = basicSetRuleBase(newRuleBase, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE, newRuleBase, newRuleBase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Attribute getSourceAttribute() {
		if (sourceAttribute != null && sourceAttribute.eIsProxy()) {
			InternalEObject oldSourceAttribute = (InternalEObject)sourceAttribute;
			sourceAttribute = (Attribute)eResolveProxy(oldSourceAttribute);
			if (sourceAttribute != oldSourceAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_ATTRIBUTE, oldSourceAttribute, sourceAttribute));
			}
		}
		return sourceAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute basicGetSourceAttribute() {
		return sourceAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceAttribute(Attribute newSourceAttribute) {
		Attribute oldSourceAttribute = sourceAttribute;
		sourceAttribute = newSourceAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_ATTRIBUTE, oldSourceAttribute, sourceAttribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Attribute getTargetAttribute() {
		if (targetAttribute != null && targetAttribute.eIsProxy()) {
			InternalEObject oldTargetAttribute = (InternalEObject)targetAttribute;
			targetAttribute = (Attribute)eResolveProxy(oldTargetAttribute);
			if (targetAttribute != oldTargetAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_ATTRIBUTE, oldTargetAttribute, targetAttribute));
			}
		}
		return targetAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute basicGetTargetAttribute() {
		return targetAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetAttribute(Attribute newTargetAttribute) {
		Attribute oldTargetAttribute = targetAttribute;
		targetAttribute = newTargetAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_ATTRIBUTE, oldTargetAttribute, targetAttribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Node getSourceNode() {
		Node sourceNode = basicGetSourceNode();
		return sourceNode != null && sourceNode.eIsProxy() ? (Node)eResolveProxy((InternalEObject)sourceNode) : sourceNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Node basicGetSourceNode() {
		return this.basicGetSourceAttribute().getNode();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Node getTargetNode() {
		Node targetNode = basicGetTargetNode();
		return targetNode != null && targetNode.eIsProxy() ? (Node)eResolveProxy((InternalEObject)targetNode) : targetNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Node basicGetTargetNode() {
		return this.getTargetAttribute().getNode();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE:
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
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE:
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
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE:
				return eInternalContainer().eInverseRemove(this, RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS, RuleBase.class, msgs);
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
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE:
				return getRuleBase();
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_ATTRIBUTE:
				if (resolve) return getSourceAttribute();
				return basicGetSourceAttribute();
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_ATTRIBUTE:
				if (resolve) return getTargetAttribute();
				return basicGetTargetAttribute();
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_NODE:
				if (resolve) return getSourceNode();
				return basicGetSourceNode();
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_NODE:
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
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE:
				setRuleBase((RuleBase)newValue);
				return;
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_ATTRIBUTE:
				setSourceAttribute((Attribute)newValue);
				return;
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_ATTRIBUTE:
				setTargetAttribute((Attribute)newValue);
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
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE:
				setRuleBase((RuleBase)null);
				return;
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_ATTRIBUTE:
				setSourceAttribute((Attribute)null);
				return;
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_ATTRIBUTE:
				setTargetAttribute((Attribute)null);
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
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE:
				return getRuleBase() != null;
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_ATTRIBUTE:
				return sourceAttribute != null;
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_ATTRIBUTE:
				return targetAttribute != null;
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__SOURCE_NODE:
				return basicGetSourceNode() != null;
			case RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__TARGET_NODE:
				return basicGetTargetNode() != null;
		}
		return super.eIsSet(featureID);
	}

} //PotentialAttributeConflictImpl
