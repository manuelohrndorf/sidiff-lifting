/**
 * <copyright>
 * </copyright>
 *
 * $Id$
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
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.Parameter;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edit Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.impl.EditRuleImpl#getExecuteMainUnit <em>Execute Main Unit</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.EditRuleImpl#getRecognitionRule <em>Recognition Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.EditRuleImpl#getRuleBaseItem <em>Rule Base Item</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.EditRuleImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.EditRuleImpl#isUseDerivedFeatures <em>Use Derived Features</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EditRuleImpl extends EObjectImpl implements EditRule {
	/**
	 * The cached value of the '{@link #getExecuteMainUnit() <em>Execute Main Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecuteMainUnit()
	 * @generated
	 * @ordered
	 */
	protected Unit executeMainUnit;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;

	/**
	 * The default value of the '{@link #isUseDerivedFeatures() <em>Use Derived Features</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseDerivedFeatures()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_DERIVED_FEATURES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseDerivedFeatures() <em>Use Derived Features</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseDerivedFeatures()
	 * @generated
	 * @ordered
	 */
	protected boolean useDerivedFeatures = USE_DERIVED_FEATURES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EditRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.EDIT_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RecognitionRule getRecognitionRule() {
		RecognitionRule recognitionRule = basicGetRecognitionRule();
		return recognitionRule != null && recognitionRule.eIsProxy() ? (RecognitionRule)eResolveProxy((InternalEObject)recognitionRule) : recognitionRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public RecognitionRule basicGetRecognitionRule() {
		return ((RuleBaseItem)(this.eContainer())).getRecognitionRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setRecognitionRule(RecognitionRule newRecognitionRule) {
		((RuleBaseItem)(this.eContainer())).setRecognitionRule(newRecognitionRule);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBaseItem getRuleBaseItem() {
		if (eContainerFeatureID() != RulebasePackage.EDIT_RULE__RULE_BASE_ITEM) return null;
		return (RuleBaseItem)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleBaseItem(RuleBaseItem newRuleBaseItem, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRuleBaseItem, RulebasePackage.EDIT_RULE__RULE_BASE_ITEM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleBaseItem(RuleBaseItem newRuleBaseItem) {
		if (newRuleBaseItem != eInternalContainer() || (eContainerFeatureID() != RulebasePackage.EDIT_RULE__RULE_BASE_ITEM && newRuleBaseItem != null)) {
			if (EcoreUtil.isAncestor(this, newRuleBaseItem))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRuleBaseItem != null)
				msgs = ((InternalEObject)newRuleBaseItem).eInverseAdd(this, RulebasePackage.RULE_BASE_ITEM__EDIT_RULE, RuleBaseItem.class, msgs);
			msgs = basicSetRuleBaseItem(newRuleBaseItem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.EDIT_RULE__RULE_BASE_ITEM, newRuleBaseItem, newRuleBaseItem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, RulebasePackage.EDIT_RULE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseDerivedFeatures() {
		return useDerivedFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseDerivedFeatures(boolean newUseDerivedFeatures) {
		boolean oldUseDerivedFeatures = useDerivedFeatures;
		useDerivedFeatures = newUseDerivedFeatures;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.EDIT_RULE__USE_DERIVED_FEATURES, oldUseDerivedFeatures, useDerivedFeatures));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Module getExecuteModule() {
		return (Module) this.getExecuteMainUnit().eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Unit getExecuteMainUnit() {
		if (executeMainUnit != null && executeMainUnit.eIsProxy()) {
			InternalEObject oldExecuteMainUnit = (InternalEObject)executeMainUnit;
			executeMainUnit = (Unit)eResolveProxy(oldExecuteMainUnit);
			if (executeMainUnit != oldExecuteMainUnit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.EDIT_RULE__EXECUTE_MAIN_UNIT, oldExecuteMainUnit, executeMainUnit));
			}
		}
		return executeMainUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Unit basicGetExecuteMainUnit() {
		return executeMainUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecuteMainUnit(Unit newExecuteMainUnit) {
		Unit oldExecuteMainUnit = executeMainUnit;
		executeMainUnit = newExecuteMainUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.EDIT_RULE__EXECUTE_MAIN_UNIT, oldExecuteMainUnit, executeMainUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Parameter getParameterByName(String name) {
		for (Parameter param : getParameters()) {
			if (param.getName().equals(name)){
				return param;
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
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulebasePackage.EDIT_RULE__RULE_BASE_ITEM:
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
			case RulebasePackage.EDIT_RULE__RULE_BASE_ITEM:
				return basicSetRuleBaseItem(null, msgs);
			case RulebasePackage.EDIT_RULE__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
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
			case RulebasePackage.EDIT_RULE__RULE_BASE_ITEM:
				return eInternalContainer().eInverseRemove(this, RulebasePackage.RULE_BASE_ITEM__EDIT_RULE, RuleBaseItem.class, msgs);
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
			case RulebasePackage.EDIT_RULE__EXECUTE_MAIN_UNIT:
				if (resolve) return getExecuteMainUnit();
				return basicGetExecuteMainUnit();
			case RulebasePackage.EDIT_RULE__RECOGNITION_RULE:
				if (resolve) return getRecognitionRule();
				return basicGetRecognitionRule();
			case RulebasePackage.EDIT_RULE__RULE_BASE_ITEM:
				return getRuleBaseItem();
			case RulebasePackage.EDIT_RULE__PARAMETERS:
				return getParameters();
			case RulebasePackage.EDIT_RULE__USE_DERIVED_FEATURES:
				return isUseDerivedFeatures();
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
			case RulebasePackage.EDIT_RULE__EXECUTE_MAIN_UNIT:
				setExecuteMainUnit((Unit)newValue);
				return;
			case RulebasePackage.EDIT_RULE__RECOGNITION_RULE:
				setRecognitionRule((RecognitionRule)newValue);
				return;
			case RulebasePackage.EDIT_RULE__RULE_BASE_ITEM:
				setRuleBaseItem((RuleBaseItem)newValue);
				return;
			case RulebasePackage.EDIT_RULE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
				return;
			case RulebasePackage.EDIT_RULE__USE_DERIVED_FEATURES:
				setUseDerivedFeatures((Boolean)newValue);
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
			case RulebasePackage.EDIT_RULE__EXECUTE_MAIN_UNIT:
				setExecuteMainUnit((Unit)null);
				return;
			case RulebasePackage.EDIT_RULE__RECOGNITION_RULE:
				setRecognitionRule((RecognitionRule)null);
				return;
			case RulebasePackage.EDIT_RULE__RULE_BASE_ITEM:
				setRuleBaseItem((RuleBaseItem)null);
				return;
			case RulebasePackage.EDIT_RULE__PARAMETERS:
				getParameters().clear();
				return;
			case RulebasePackage.EDIT_RULE__USE_DERIVED_FEATURES:
				setUseDerivedFeatures(USE_DERIVED_FEATURES_EDEFAULT);
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
			case RulebasePackage.EDIT_RULE__EXECUTE_MAIN_UNIT:
				return executeMainUnit != null;
			case RulebasePackage.EDIT_RULE__RECOGNITION_RULE:
				return basicGetRecognitionRule() != null;
			case RulebasePackage.EDIT_RULE__RULE_BASE_ITEM:
				return getRuleBaseItem() != null;
			case RulebasePackage.EDIT_RULE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case RulebasePackage.EDIT_RULE__USE_DERIVED_FEATURES:
				return useDerivedFeatures != USE_DERIVED_FEATURES_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer(super.toString());
		if (eIsProxy()){
			res.append(" ("+ getExecuteMainUnit() + ")");
		} else {			
			res.append(" (" + getExecuteModule().getName() + ")");
		}
		
		return res.toString();
	}
	
} //EditRuleImpl
