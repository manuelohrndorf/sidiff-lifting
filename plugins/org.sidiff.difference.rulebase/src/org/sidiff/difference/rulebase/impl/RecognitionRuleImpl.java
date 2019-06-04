/**
 */
package org.sidiff.difference.rulebase.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.difference.rulebase.LiftingRulebasePackage;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.Trace;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Recognition Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl#getRuleBaseItem <em>Rule Base Item</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl#getEditRule <em>Edit Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl#getRecognitionMainUnit <em>Recognition Main Unit</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl#getTracesB <em>Traces B</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl#getTracesA <em>Traces A</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RecognitionRuleImpl#getRecognitionModule <em>Recognition Module</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RecognitionRuleImpl extends EObjectImpl implements RecognitionRule {
	/**
	 * The cached value of the '{@link #getTracesB() <em>Traces B</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTracesB()
	 * @generated
	 * @ordered
	 */
	protected EList<Trace> tracesB;

	/**
	 * The cached value of the '{@link #getTracesA() <em>Traces A</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTracesA()
	 * @generated
	 * @ordered
	 */
	protected EList<Trace> tracesA;

	/**
	 * The cached value of the '{@link #getRecognitionModule() <em>Recognition Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecognitionModule()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.emf.henshin.model.Module recognitionModule;

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
		return LiftingRulebasePackage.Literals.RECOGNITION_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleBaseItem getRuleBaseItem() {
		if (eContainerFeatureID() != LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM) return null;
		return (RuleBaseItem)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleBaseItem(RuleBaseItem newRuleBaseItem, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRuleBaseItem, LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRuleBaseItem(RuleBaseItem newRuleBaseItem) {
		if (newRuleBaseItem != eInternalContainer() || (eContainerFeatureID() != LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM && newRuleBaseItem != null)) {
			if (EcoreUtil.isAncestor(this, newRuleBaseItem))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRuleBaseItem != null)
				msgs = ((InternalEObject)newRuleBaseItem).eInverseAdd(this, RulebasePackage.RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS, RuleBaseItem.class, msgs);
			msgs = basicSetRuleBaseItem(newRuleBaseItem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM, newRuleBaseItem, newRuleBaseItem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
		return getRuleBaseItem().getEditRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getRecognitionMainUnit() {
		Rule recognitionMainUnit = basicGetRecognitionMainUnit();
		return recognitionMainUnit != null && recognitionMainUnit.eIsProxy() ? (Rule)eResolveProxy((InternalEObject)recognitionMainUnit) : recognitionMainUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule basicGetRecognitionMainUnit() {
		for(Unit unit : getRecognitionModule().getUnits()) {
			if(unit instanceof Rule) {
				return (Rule)unit;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Trace> getTracesB() {
		if (tracesB == null) {
			tracesB = new EObjectContainmentEList<Trace>(Trace.class, this, LiftingRulebasePackage.RECOGNITION_RULE__TRACES_B);
		}
		return tracesB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Trace> getTracesA() {
		if (tracesA == null) {
			tracesA = new EObjectContainmentEList<Trace>(Trace.class, this, LiftingRulebasePackage.RECOGNITION_RULE__TRACES_A);
		}
		return tracesA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emf.henshin.model.Module getRecognitionModule() {
		if (recognitionModule != null && recognitionModule.eIsProxy()) {
			InternalEObject oldRecognitionModule = (InternalEObject)recognitionModule;
			recognitionModule = (org.eclipse.emf.henshin.model.Module)eResolveProxy(oldRecognitionModule);
			if (recognitionModule != oldRecognitionModule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LiftingRulebasePackage.RECOGNITION_RULE__RECOGNITION_MODULE, oldRecognitionModule, recognitionModule));
			}
		}
		return recognitionModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.emf.henshin.model.Module basicGetRecognitionModule() {
		return recognitionModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRecognitionModule(org.eclipse.emf.henshin.model.Module newRecognitionModule) {
		org.eclipse.emf.henshin.model.Module oldRecognitionModule = recognitionModule;
		recognitionModule = newRecognitionModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LiftingRulebasePackage.RECOGNITION_RULE__RECOGNITION_MODULE, oldRecognitionModule, recognitionModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
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
			case LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				return basicSetRuleBaseItem(null, msgs);
			case LiftingRulebasePackage.RECOGNITION_RULE__TRACES_B:
				return ((InternalEList<?>)getTracesB()).basicRemove(otherEnd, msgs);
			case LiftingRulebasePackage.RECOGNITION_RULE__TRACES_A:
				return ((InternalEList<?>)getTracesA()).basicRemove(otherEnd, msgs);
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
			case LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				return eInternalContainer().eInverseRemove(this, RulebasePackage.RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS, RuleBaseItem.class, msgs);
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
			case LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				return getRuleBaseItem();
			case LiftingRulebasePackage.RECOGNITION_RULE__EDIT_RULE:
				if (resolve) return getEditRule();
				return basicGetEditRule();
			case LiftingRulebasePackage.RECOGNITION_RULE__RECOGNITION_MAIN_UNIT:
				if (resolve) return getRecognitionMainUnit();
				return basicGetRecognitionMainUnit();
			case LiftingRulebasePackage.RECOGNITION_RULE__TRACES_B:
				return getTracesB();
			case LiftingRulebasePackage.RECOGNITION_RULE__TRACES_A:
				return getTracesA();
			case LiftingRulebasePackage.RECOGNITION_RULE__RECOGNITION_MODULE:
				if (resolve) return getRecognitionModule();
				return basicGetRecognitionModule();
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
			case LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				setRuleBaseItem((RuleBaseItem)newValue);
				return;
			case LiftingRulebasePackage.RECOGNITION_RULE__TRACES_B:
				getTracesB().clear();
				getTracesB().addAll((Collection<? extends Trace>)newValue);
				return;
			case LiftingRulebasePackage.RECOGNITION_RULE__TRACES_A:
				getTracesA().clear();
				getTracesA().addAll((Collection<? extends Trace>)newValue);
				return;
			case LiftingRulebasePackage.RECOGNITION_RULE__RECOGNITION_MODULE:
				setRecognitionModule((org.eclipse.emf.henshin.model.Module)newValue);
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
			case LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				setRuleBaseItem((RuleBaseItem)null);
				return;
			case LiftingRulebasePackage.RECOGNITION_RULE__TRACES_B:
				getTracesB().clear();
				return;
			case LiftingRulebasePackage.RECOGNITION_RULE__TRACES_A:
				getTracesA().clear();
				return;
			case LiftingRulebasePackage.RECOGNITION_RULE__RECOGNITION_MODULE:
				setRecognitionModule((org.eclipse.emf.henshin.model.Module)null);
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
			case LiftingRulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM:
				return getRuleBaseItem() != null;
			case LiftingRulebasePackage.RECOGNITION_RULE__EDIT_RULE:
				return basicGetEditRule() != null;
			case LiftingRulebasePackage.RECOGNITION_RULE__RECOGNITION_MAIN_UNIT:
				return basicGetRecognitionMainUnit() != null;
			case LiftingRulebasePackage.RECOGNITION_RULE__TRACES_B:
				return tracesB != null && !tracesB.isEmpty();
			case LiftingRulebasePackage.RECOGNITION_RULE__TRACES_A:
				return tracesA != null && !tracesA.isEmpty();
			case LiftingRulebasePackage.RECOGNITION_RULE__RECOGNITION_MODULE:
				return recognitionModule != null;
		}
		return super.eIsSet(featureID);
	}

} //RecognitionRuleImpl
