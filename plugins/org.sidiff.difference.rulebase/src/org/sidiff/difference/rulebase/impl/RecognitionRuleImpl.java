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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Recognition Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl#getRecognitionMainUnit <em>Recognition Main Unit</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl#getEditRule <em>Edit Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl#getRuleBaseItem <em>Rule Base Item</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RecognitionRuleImpl extends EObjectImpl implements RecognitionRule {
	/**
	 * The cached value of the '{@link #getRecognitionMainUnit() <em>Recognition Main Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecognitionMainUnit()
	 * @generated
	 * @ordered
	 */
	protected Rule recognitionMainUnit;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RecognitionRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.RECOGNITION_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRecognitionMainUnit() {
		if (recognitionMainUnit != null && recognitionMainUnit.eIsProxy()) {
			InternalEObject oldRecognitionMainUnit = (InternalEObject)recognitionMainUnit;
			recognitionMainUnit = (Rule)eResolveProxy(oldRecognitionMainUnit);
			if (recognitionMainUnit != oldRecognitionMainUnit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.RECOGNITION_RULE__RECOGNITION_MAIN_UNIT, oldRecognitionMainUnit, recognitionMainUnit));
			}
		}
		return recognitionMainUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetRecognitionMainUnit() {
		return recognitionMainUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecognitionMainUnit(Rule newRecognitionMainUnit) {
		Rule oldRecognitionMainUnit = recognitionMainUnit;
		recognitionMainUnit = newRecognitionMainUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RECOGNITION_RULE__RECOGNITION_MAIN_UNIT, oldRecognitionMainUnit, recognitionMainUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditRule getEditRule() {
		EditRule editRule = basicGetEditRule();
		return editRule != null && editRule.eIsProxy() ? (EditRule)eResolveProxy((InternalEObject)editRule) : editRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EditRule basicGetEditRule() {
		return ((RuleBaseItem)(this.eContainer())).getEditRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setEditRule(EditRule newEditRule) {
		((RuleBaseItem)(this.eContainer())).setEditRule(newEditRule);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBaseItem getRuleBaseItem() {
		if (eContainerFeatureID() != RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM) return null;
		return (RuleBaseItem)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleBaseItem(RuleBaseItem newRuleBaseItem, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRuleBaseItem, RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleBaseItem(RuleBaseItem newRuleBaseItem) {
		if (newRuleBaseItem != eInternalContainer() || (eContainerFeatureID() != RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM && newRuleBaseItem != null)) {
			if (EcoreUtil.isAncestor(this, newRuleBaseItem))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRuleBaseItem != null)
				msgs = ((InternalEObject)newRuleBaseItem).eInverseAdd(this, RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE, RuleBaseItem.class, msgs);
			msgs = basicSetRuleBaseItem(newRuleBaseItem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM, newRuleBaseItem, newRuleBaseItem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Module getRecognitionModule() {
		return (Module) this.getRecognitionMainUnit().eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetRuleBaseItem((RuleBaseItem)otherEnd, msgs);
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
			case RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				return basicSetRuleBaseItem(null, msgs);
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
			case RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				return eInternalContainer().eInverseRemove(this, RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE, RuleBaseItem.class, msgs);
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
			case RulebasePackage.RECOGNITION_RULE__RECOGNITION_MAIN_UNIT:
				if (resolve) return getRecognitionMainUnit();
				return basicGetRecognitionMainUnit();
			case RulebasePackage.RECOGNITION_RULE__EDIT_RULE:
				if (resolve) return getEditRule();
				return basicGetEditRule();
			case RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				return getRuleBaseItem();
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
			case RulebasePackage.RECOGNITION_RULE__RECOGNITION_MAIN_UNIT:
				setRecognitionMainUnit((Rule)newValue);
				return;
			case RulebasePackage.RECOGNITION_RULE__EDIT_RULE:
				setEditRule((EditRule)newValue);
				return;
			case RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				setRuleBaseItem((RuleBaseItem)newValue);
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
			case RulebasePackage.RECOGNITION_RULE__RECOGNITION_MAIN_UNIT:
				setRecognitionMainUnit((Rule)null);
				return;
			case RulebasePackage.RECOGNITION_RULE__EDIT_RULE:
				setEditRule((EditRule)null);
				return;
			case RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				setRuleBaseItem((RuleBaseItem)null);
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
			case RulebasePackage.RECOGNITION_RULE__RECOGNITION_MAIN_UNIT:
				return recognitionMainUnit != null;
			case RulebasePackage.RECOGNITION_RULE__EDIT_RULE:
				return basicGetEditRule() != null;
			case RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				return getRuleBaseItem() != null;
		}
		return super.eIsSet(featureID);
	}

} //RecognitionRuleImpl
