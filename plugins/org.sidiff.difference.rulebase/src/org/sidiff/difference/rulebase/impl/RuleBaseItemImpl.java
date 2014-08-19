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
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.RulebasePackage;
import org.sidiff.difference.rulebase.Trace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Base Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseItemImpl#isActive <em>Active</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseItemImpl#getEditRule <em>Edit Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseItemImpl#getRecognitionRule <em>Recognition Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseItemImpl#getTracesB <em>Traces B</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseItemImpl#getTracesA <em>Traces A</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseItemImpl#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseItemImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseItemImpl#isValid <em>Valid</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleBaseItemImpl extends EObjectImpl implements RuleBaseItem {
	/**
	 * The default value of the '{@link #isActive() <em>Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ACTIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isActive() <em>Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActive()
	 * @generated
	 * @ordered
	 */
	protected boolean active = ACTIVE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEditRule() <em>Edit Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditRule()
	 * @generated
	 * @ordered
	 */
	protected EditRule editRule;

	/**
	 * The cached value of the '{@link #getRecognitionRule() <em>Recognition Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecognitionRule()
	 * @generated
	 * @ordered
	 */
	protected RecognitionRule recognitionRule;

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
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #isValid() <em>Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValid()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VALID_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isValid() <em>Valid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValid()
	 * @generated
	 * @ordered
	 */
	protected boolean valid = VALID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleBaseItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.RULE_BASE_ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActive(boolean newActive) {
		boolean oldActive = active;
		active = newActive;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE_ITEM__ACTIVE, oldActive, active));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditRule getEditRule() {
		return editRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEditRule(EditRule newEditRule, NotificationChain msgs) {
		EditRule oldEditRule = editRule;
		editRule = newEditRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE_ITEM__EDIT_RULE, oldEditRule, newEditRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditRule(EditRule newEditRule) {
		if (newEditRule != editRule) {
			NotificationChain msgs = null;
			if (editRule != null)
				msgs = ((InternalEObject)editRule).eInverseRemove(this, RulebasePackage.EDIT_RULE__RULE_BASE_ITEM, EditRule.class, msgs);
			if (newEditRule != null)
				msgs = ((InternalEObject)newEditRule).eInverseAdd(this, RulebasePackage.EDIT_RULE__RULE_BASE_ITEM, EditRule.class, msgs);
			msgs = basicSetEditRule(newEditRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE_ITEM__EDIT_RULE, newEditRule, newEditRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RecognitionRule getRecognitionRule() {
		return recognitionRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRecognitionRule(RecognitionRule newRecognitionRule, NotificationChain msgs) {
		RecognitionRule oldRecognitionRule = recognitionRule;
		recognitionRule = newRecognitionRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE, oldRecognitionRule, newRecognitionRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecognitionRule(RecognitionRule newRecognitionRule) {
		if (newRecognitionRule != recognitionRule) {
			NotificationChain msgs = null;
			if (recognitionRule != null)
				msgs = ((InternalEObject)recognitionRule).eInverseRemove(this, RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM, RecognitionRule.class, msgs);
			if (newRecognitionRule != null)
				msgs = ((InternalEObject)newRecognitionRule).eInverseAdd(this, RulebasePackage.RECOGNITION_RULE__RULE_BASE_ITEM, RecognitionRule.class, msgs);
			msgs = basicSetRecognitionRule(newRecognitionRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE, newRecognitionRule, newRecognitionRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Trace> getTracesB() {
		if (tracesB == null) {
			tracesB = new EObjectContainmentEList<Trace>(Trace.class, this, RulebasePackage.RULE_BASE_ITEM__TRACES_B);
		}
		return tracesB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Trace> getTracesA() {
		if (tracesA == null) {
			tracesA = new EObjectContainmentEList<Trace>(Trace.class, this, RulebasePackage.RULE_BASE_ITEM__TRACES_A);
		}
		return tracesA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBase getRuleBase() {
		if (eContainerFeatureID() != RulebasePackage.RULE_BASE_ITEM__RULE_BASE) return null;
		return (RuleBase)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleBase(RuleBase newRuleBase, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRuleBase, RulebasePackage.RULE_BASE_ITEM__RULE_BASE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleBase(RuleBase newRuleBase) {
		if (newRuleBase != eInternalContainer() || (eContainerFeatureID() != RulebasePackage.RULE_BASE_ITEM__RULE_BASE && newRuleBase != null)) {
			if (EcoreUtil.isAncestor(this, newRuleBase))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRuleBase != null)
				msgs = ((InternalEObject)newRuleBase).eInverseAdd(this, RulebasePackage.RULE_BASE__ITEMS, RuleBase.class, msgs);
			msgs = basicSetRuleBase(newRuleBase, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE_ITEM__RULE_BASE, newRuleBase, newRuleBase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE_ITEM__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValid(boolean newValid) {
		boolean oldValid = valid;
		valid = newValid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE_ITEM__VALID, oldValid, valid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {
		return this.getEditRule().getExecuteModule().getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				if (editRule != null)
					msgs = ((InternalEObject)editRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RulebasePackage.RULE_BASE_ITEM__EDIT_RULE, null, msgs);
				return basicSetEditRule((EditRule)otherEnd, msgs);
			case RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE:
				if (recognitionRule != null)
					msgs = ((InternalEObject)recognitionRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE, null, msgs);
				return basicSetRecognitionRule((RecognitionRule)otherEnd, msgs);
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
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
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				return basicSetEditRule(null, msgs);
			case RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE:
				return basicSetRecognitionRule(null, msgs);
			case RulebasePackage.RULE_BASE_ITEM__TRACES_B:
				return ((InternalEList<?>)getTracesB()).basicRemove(otherEnd, msgs);
			case RulebasePackage.RULE_BASE_ITEM__TRACES_A:
				return ((InternalEList<?>)getTracesA()).basicRemove(otherEnd, msgs);
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
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
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				return eInternalContainer().eInverseRemove(this, RulebasePackage.RULE_BASE__ITEMS, RuleBase.class, msgs);
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
			case RulebasePackage.RULE_BASE_ITEM__ACTIVE:
				return isActive();
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				return getEditRule();
			case RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE:
				return getRecognitionRule();
			case RulebasePackage.RULE_BASE_ITEM__TRACES_B:
				return getTracesB();
			case RulebasePackage.RULE_BASE_ITEM__TRACES_A:
				return getTracesA();
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				return getRuleBase();
			case RulebasePackage.RULE_BASE_ITEM__VERSION:
				return getVersion();
			case RulebasePackage.RULE_BASE_ITEM__VALID:
				return isValid();
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
			case RulebasePackage.RULE_BASE_ITEM__ACTIVE:
				setActive((Boolean)newValue);
				return;
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				setEditRule((EditRule)newValue);
				return;
			case RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE:
				setRecognitionRule((RecognitionRule)newValue);
				return;
			case RulebasePackage.RULE_BASE_ITEM__TRACES_B:
				getTracesB().clear();
				getTracesB().addAll((Collection<? extends Trace>)newValue);
				return;
			case RulebasePackage.RULE_BASE_ITEM__TRACES_A:
				getTracesA().clear();
				getTracesA().addAll((Collection<? extends Trace>)newValue);
				return;
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				setRuleBase((RuleBase)newValue);
				return;
			case RulebasePackage.RULE_BASE_ITEM__VERSION:
				setVersion((String)newValue);
				return;
			case RulebasePackage.RULE_BASE_ITEM__VALID:
				setValid((Boolean)newValue);
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
			case RulebasePackage.RULE_BASE_ITEM__ACTIVE:
				setActive(ACTIVE_EDEFAULT);
				return;
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				setEditRule((EditRule)null);
				return;
			case RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE:
				setRecognitionRule((RecognitionRule)null);
				return;
			case RulebasePackage.RULE_BASE_ITEM__TRACES_B:
				getTracesB().clear();
				return;
			case RulebasePackage.RULE_BASE_ITEM__TRACES_A:
				getTracesA().clear();
				return;
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				setRuleBase((RuleBase)null);
				return;
			case RulebasePackage.RULE_BASE_ITEM__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case RulebasePackage.RULE_BASE_ITEM__VALID:
				setValid(VALID_EDEFAULT);
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
			case RulebasePackage.RULE_BASE_ITEM__ACTIVE:
				return active != ACTIVE_EDEFAULT;
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				return editRule != null;
			case RulebasePackage.RULE_BASE_ITEM__RECOGNITION_RULE:
				return recognitionRule != null;
			case RulebasePackage.RULE_BASE_ITEM__TRACES_B:
				return tracesB != null && !tracesB.isEmpty();
			case RulebasePackage.RULE_BASE_ITEM__TRACES_A:
				return tracesA != null && !tracesA.isEmpty();
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				return getRuleBase() != null;
			case RulebasePackage.RULE_BASE_ITEM__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case RulebasePackage.RULE_BASE_ITEM__VALID:
				return valid != VALID_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (active: ");
		result.append(active);
		result.append(", version: ");
		result.append(version);
		result.append(", valid: ");
		result.append(valid);
		result.append(')');
		return result.toString();
	}

} //RuleBaseItemImpl
