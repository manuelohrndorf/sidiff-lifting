/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.editrule.rulebase.impl;

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
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.HenshinUnitAnalysis;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.editrule.rulebase.Classification;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.EditRuleAttachment;
import org.sidiff.editrule.rulebase.Parameter;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edit Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.EditRuleImpl#getExecuteMainUnit <em>Execute Main Unit</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.EditRuleImpl#getRuleBaseItem <em>Rule Base Item</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.EditRuleImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.EditRuleImpl#isUseDerivedFeatures <em>Use Derived Features</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.EditRuleImpl#getInverse <em>Inverse</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.EditRuleImpl#getClassification <em>Classification</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.EditRuleImpl#getExecuteModule <em>Execute Module</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EditRuleImpl extends EObjectImpl implements EditRule {
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
	 * The cached value of the '{@link #getInverse() <em>Inverse</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInverse()
	 * @generated
	 * @ordered
	 */
	protected EditRule inverse;

	/**
	 * The cached value of the '{@link #getClassification() <em>Classification</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassification()
	 * @generated
	 * @ordered
	 */
	protected EList<Classification> classification;

	/**
	 * The cached value of the '{@link #getExecuteModule() <em>Execute Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecuteModule()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.emf.henshin.model.Module executeModule;

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
	 * @generated NOT
	 */
	@Override
	public <T extends EditRuleAttachment> T getEditRuleAttachment(Class<T> type) {
		return getRuleBaseItem().getEditRuleAttachment(type);
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
	@Override
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
	@Override
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
	@Override
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
	@Override
	public boolean isUseDerivedFeatures() {
		return useDerivedFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUseDerivedFeatures(boolean newUseDerivedFeatures) {
		boolean oldUseDerivedFeatures = useDerivedFeatures;
		useDerivedFeatures = newUseDerivedFeatures;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.EDIT_RULE__USE_DERIVED_FEATURES, oldUseDerivedFeatures, useDerivedFeatures));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EditRule getInverse() {
		if (inverse != null && inverse.eIsProxy()) {
			InternalEObject oldInverse = (InternalEObject)inverse;
			inverse = (EditRule)eResolveProxy(oldInverse);
			if (inverse != oldInverse) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.EDIT_RULE__INVERSE, oldInverse, inverse));
			}
		}
		return inverse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditRule basicGetInverse() {
		return inverse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInverse(EditRule newInverse) {
		EditRule oldInverse = inverse;
		inverse = newInverse;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.EDIT_RULE__INVERSE, oldInverse, inverse));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Classification> getClassification() {
		if (classification == null) {
			classification = new EObjectContainmentEList<Classification>(Classification.class, this, RulebasePackage.EDIT_RULE__CLASSIFICATION);
		}
		return classification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.emf.henshin.model.Module getExecuteModule() {
		if (executeModule != null && executeModule.eIsProxy()) {
			InternalEObject oldExecuteModule = (InternalEObject)executeModule;
			executeModule = (org.eclipse.emf.henshin.model.Module)eResolveProxy(oldExecuteModule);
			if (executeModule != oldExecuteModule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.EDIT_RULE__EXECUTE_MODULE, oldExecuteModule, executeModule));
			}
		}
		return executeModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.emf.henshin.model.Module basicGetExecuteModule() {
		return executeModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExecuteModule(org.eclipse.emf.henshin.model.Module newExecuteModule) {
		org.eclipse.emf.henshin.model.Module oldExecuteModule = executeModule;
		executeModule = newExecuteModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.EDIT_RULE__EXECUTE_MODULE, oldExecuteModule, executeModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Unit getExecuteMainUnit() {
		Unit executeMainUnit = basicGetExecuteMainUnit();
		return executeMainUnit != null && executeMainUnit.eIsProxy() ? (Unit)eResolveProxy((InternalEObject)executeMainUnit) : executeMainUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Unit basicGetExecuteMainUnit() {
		try {
			return HenshinUnitAnalysis.findExecuteMainUnit(getExecuteModule());
		} catch (NoMainUnitFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
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
			case RulebasePackage.EDIT_RULE__CLASSIFICATION:
				return ((InternalEList<?>)getClassification()).basicRemove(otherEnd, msgs);
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
			case RulebasePackage.EDIT_RULE__RULE_BASE_ITEM:
				return getRuleBaseItem();
			case RulebasePackage.EDIT_RULE__PARAMETERS:
				return getParameters();
			case RulebasePackage.EDIT_RULE__USE_DERIVED_FEATURES:
				return isUseDerivedFeatures();
			case RulebasePackage.EDIT_RULE__INVERSE:
				if (resolve) return getInverse();
				return basicGetInverse();
			case RulebasePackage.EDIT_RULE__CLASSIFICATION:
				return getClassification();
			case RulebasePackage.EDIT_RULE__EXECUTE_MODULE:
				if (resolve) return getExecuteModule();
				return basicGetExecuteModule();
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
			case RulebasePackage.EDIT_RULE__INVERSE:
				setInverse((EditRule)newValue);
				return;
			case RulebasePackage.EDIT_RULE__CLASSIFICATION:
				getClassification().clear();
				getClassification().addAll((Collection<? extends Classification>)newValue);
				return;
			case RulebasePackage.EDIT_RULE__EXECUTE_MODULE:
				setExecuteModule((org.eclipse.emf.henshin.model.Module)newValue);
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
			case RulebasePackage.EDIT_RULE__RULE_BASE_ITEM:
				setRuleBaseItem((RuleBaseItem)null);
				return;
			case RulebasePackage.EDIT_RULE__PARAMETERS:
				getParameters().clear();
				return;
			case RulebasePackage.EDIT_RULE__USE_DERIVED_FEATURES:
				setUseDerivedFeatures(USE_DERIVED_FEATURES_EDEFAULT);
				return;
			case RulebasePackage.EDIT_RULE__INVERSE:
				setInverse((EditRule)null);
				return;
			case RulebasePackage.EDIT_RULE__CLASSIFICATION:
				getClassification().clear();
				return;
			case RulebasePackage.EDIT_RULE__EXECUTE_MODULE:
				setExecuteModule((org.eclipse.emf.henshin.model.Module)null);
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
				return basicGetExecuteMainUnit() != null;
			case RulebasePackage.EDIT_RULE__RULE_BASE_ITEM:
				return getRuleBaseItem() != null;
			case RulebasePackage.EDIT_RULE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case RulebasePackage.EDIT_RULE__USE_DERIVED_FEATURES:
				return useDerivedFeatures != USE_DERIVED_FEATURES_EDEFAULT;
			case RulebasePackage.EDIT_RULE__INVERSE:
				return inverse != null;
			case RulebasePackage.EDIT_RULE__CLASSIFICATION:
				return classification != null && !classification.isEmpty();
			case RulebasePackage.EDIT_RULE__EXECUTE_MODULE:
				return executeModule != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer(super.toString());
		if(!eIsProxy()) {
			res.append(" (" + getExecuteModule().getName() + ")");
		}
		return res.toString();
	}
} //EditRuleImpl
