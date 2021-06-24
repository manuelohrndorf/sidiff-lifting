/**
 */
package org.sidiff.editrule.rulebase.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialConflict;
import org.sidiff.editrule.rulebase.PotentialConflictKind;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Potential Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl#getSourceRule <em>Source Rule</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl#getTargetRule <em>Target Rule</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl#isDuplicate <em>Duplicate</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl#isCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PotentialConflictImpl extends EObjectImpl implements PotentialConflict {
	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final PotentialConflictKind KIND_EDEFAULT = PotentialConflictKind.DELETE_USE;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected PotentialConflictKind kind = KIND_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSourceRule() <em>Source Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceRule()
	 * @generated
	 * @ordered
	 */
	protected EditRule sourceRule;

	/**
	 * The cached value of the '{@link #getTargetRule() <em>Target Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetRule()
	 * @generated
	 * @ordered
	 */
	protected EditRule targetRule;

	/**
	 * The default value of the '{@link #isDuplicate() <em>Duplicate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDuplicate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DUPLICATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDuplicate() <em>Duplicate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDuplicate()
	 * @generated
	 * @ordered
	 */
	protected boolean duplicate = DUPLICATE_EDEFAULT;

	/**
	 * The default value of the '{@link #isCondition() <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCondition()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONDITION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCondition() <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCondition()
	 * @generated
	 * @ordered
	 */
	protected boolean condition = CONDITION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialConflictImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.POTENTIAL_CONFLICT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PotentialConflictKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKind(PotentialConflictKind newKind) {
		PotentialConflictKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_CONFLICT__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EditRule getSourceRule() {
		if (sourceRule != null && sourceRule.eIsProxy()) {
			InternalEObject oldSourceRule = (InternalEObject)sourceRule;
			sourceRule = (EditRule)eResolveProxy(oldSourceRule);
			if (sourceRule != oldSourceRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_CONFLICT__SOURCE_RULE, oldSourceRule, sourceRule));
			}
		}
		return sourceRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditRule basicGetSourceRule() {
		return sourceRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceRule(EditRule newSourceRule) {
		EditRule oldSourceRule = sourceRule;
		sourceRule = newSourceRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_CONFLICT__SOURCE_RULE, oldSourceRule, sourceRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EditRule getTargetRule() {
		if (targetRule != null && targetRule.eIsProxy()) {
			InternalEObject oldTargetRule = (InternalEObject)targetRule;
			targetRule = (EditRule)eResolveProxy(oldTargetRule);
			if (targetRule != oldTargetRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_CONFLICT__TARGET_RULE, oldTargetRule, targetRule));
			}
		}
		return targetRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditRule basicGetTargetRule() {
		return targetRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetRule(EditRule newTargetRule) {
		EditRule oldTargetRule = targetRule;
		targetRule = newTargetRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_CONFLICT__TARGET_RULE, oldTargetRule, targetRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDuplicate() {
		return duplicate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDuplicate(boolean newDuplicate) {
		boolean oldDuplicate = duplicate;
		duplicate = newDuplicate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_CONFLICT__DUPLICATE, oldDuplicate, duplicate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCondition(boolean newCondition) {
		boolean oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_CONFLICT__CONDITION, oldCondition, condition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulebasePackage.POTENTIAL_CONFLICT__KIND:
				return getKind();
			case RulebasePackage.POTENTIAL_CONFLICT__SOURCE_RULE:
				if (resolve) return getSourceRule();
				return basicGetSourceRule();
			case RulebasePackage.POTENTIAL_CONFLICT__TARGET_RULE:
				if (resolve) return getTargetRule();
				return basicGetTargetRule();
			case RulebasePackage.POTENTIAL_CONFLICT__DUPLICATE:
				return isDuplicate();
			case RulebasePackage.POTENTIAL_CONFLICT__CONDITION:
				return isCondition();
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
			case RulebasePackage.POTENTIAL_CONFLICT__KIND:
				setKind((PotentialConflictKind)newValue);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__SOURCE_RULE:
				setSourceRule((EditRule)newValue);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__TARGET_RULE:
				setTargetRule((EditRule)newValue);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__DUPLICATE:
				setDuplicate((Boolean)newValue);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__CONDITION:
				setCondition((Boolean)newValue);
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
			case RulebasePackage.POTENTIAL_CONFLICT__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__SOURCE_RULE:
				setSourceRule((EditRule)null);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__TARGET_RULE:
				setTargetRule((EditRule)null);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__DUPLICATE:
				setDuplicate(DUPLICATE_EDEFAULT);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__CONDITION:
				setCondition(CONDITION_EDEFAULT);
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
			case RulebasePackage.POTENTIAL_CONFLICT__KIND:
				return kind != KIND_EDEFAULT;
			case RulebasePackage.POTENTIAL_CONFLICT__SOURCE_RULE:
				return sourceRule != null;
			case RulebasePackage.POTENTIAL_CONFLICT__TARGET_RULE:
				return targetRule != null;
			case RulebasePackage.POTENTIAL_CONFLICT__DUPLICATE:
				return duplicate != DUPLICATE_EDEFAULT;
			case RulebasePackage.POTENTIAL_CONFLICT__CONDITION:
				return condition != CONDITION_EDEFAULT;
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (kind: ");
		result.append(kind);
		result.append(", duplicate: ");
		result.append(duplicate);
		result.append(", condition: ");
		result.append(condition);
		result.append(')');
		return result.toString();
	}

} //PotentialConflictImpl
