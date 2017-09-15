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
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Potential Conflict</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl#getPotentialConflictKind <em>Potential Conflict Kind</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl#isResolvable <em>Resolvable</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl#getSourceRule <em>Source Rule</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl#getTargetRule <em>Target Rule</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.PotentialConflictImpl#getConflictResolution <em>Conflict Resolution</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PotentialConflictImpl extends EObjectImpl implements PotentialConflict {
	/**
	 * The default value of the '{@link #getPotentialConflictKind() <em>Potential Conflict Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPotentialConflictKind()
	 * @generated
	 * @ordered
	 */
	protected static final PotentialConflictKind POTENTIAL_CONFLICT_KIND_EDEFAULT = PotentialConflictKind.DELETE_USE;

	/**
	 * The cached value of the '{@link #getPotentialConflictKind() <em>Potential Conflict Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPotentialConflictKind()
	 * @generated
	 * @ordered
	 */
	protected PotentialConflictKind potentialConflictKind = POTENTIAL_CONFLICT_KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #isResolvable() <em>Resolvable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isResolvable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RESOLVABLE_EDEFAULT = false;

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
	 * The cached value of the '{@link #getConflictResolution() <em>Conflict Resolution</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConflictResolution()
	 * @generated
	 * @ordered
	 */
	protected PotentialDependency conflictResolution;

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
	public PotentialConflictKind getPotentialConflictKind() {
		return potentialConflictKind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPotentialConflictKind(PotentialConflictKind newPotentialConflictKind) {
		PotentialConflictKind oldPotentialConflictKind = potentialConflictKind;
		potentialConflictKind = newPotentialConflictKind == null ? POTENTIAL_CONFLICT_KIND_EDEFAULT : newPotentialConflictKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_CONFLICT__POTENTIAL_CONFLICT_KIND, oldPotentialConflictKind, potentialConflictKind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isResolvable() {
		return getConflictResolution() != null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
	public PotentialDependency getConflictResolution() {
		if (conflictResolution != null && conflictResolution.eIsProxy()) {
			InternalEObject oldConflictResolution = (InternalEObject)conflictResolution;
			conflictResolution = (PotentialDependency)eResolveProxy(oldConflictResolution);
			if (conflictResolution != oldConflictResolution) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_CONFLICT__CONFLICT_RESOLUTION, oldConflictResolution, conflictResolution));
			}
		}
		return conflictResolution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PotentialDependency basicGetConflictResolution() {
		return conflictResolution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConflictResolution(PotentialDependency newConflictResolution) {
		PotentialDependency oldConflictResolution = conflictResolution;
		conflictResolution = newConflictResolution;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_CONFLICT__CONFLICT_RESOLUTION, oldConflictResolution, conflictResolution));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulebasePackage.POTENTIAL_CONFLICT__POTENTIAL_CONFLICT_KIND:
				return getPotentialConflictKind();
			case RulebasePackage.POTENTIAL_CONFLICT__RESOLVABLE:
				return isResolvable();
			case RulebasePackage.POTENTIAL_CONFLICT__SOURCE_RULE:
				if (resolve) return getSourceRule();
				return basicGetSourceRule();
			case RulebasePackage.POTENTIAL_CONFLICT__TARGET_RULE:
				if (resolve) return getTargetRule();
				return basicGetTargetRule();
			case RulebasePackage.POTENTIAL_CONFLICT__CONFLICT_RESOLUTION:
				if (resolve) return getConflictResolution();
				return basicGetConflictResolution();
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
			case RulebasePackage.POTENTIAL_CONFLICT__POTENTIAL_CONFLICT_KIND:
				setPotentialConflictKind((PotentialConflictKind)newValue);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__SOURCE_RULE:
				setSourceRule((EditRule)newValue);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__TARGET_RULE:
				setTargetRule((EditRule)newValue);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__CONFLICT_RESOLUTION:
				setConflictResolution((PotentialDependency)newValue);
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
			case RulebasePackage.POTENTIAL_CONFLICT__POTENTIAL_CONFLICT_KIND:
				setPotentialConflictKind(POTENTIAL_CONFLICT_KIND_EDEFAULT);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__SOURCE_RULE:
				setSourceRule((EditRule)null);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__TARGET_RULE:
				setTargetRule((EditRule)null);
				return;
			case RulebasePackage.POTENTIAL_CONFLICT__CONFLICT_RESOLUTION:
				setConflictResolution((PotentialDependency)null);
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
			case RulebasePackage.POTENTIAL_CONFLICT__POTENTIAL_CONFLICT_KIND:
				return potentialConflictKind != POTENTIAL_CONFLICT_KIND_EDEFAULT;
			case RulebasePackage.POTENTIAL_CONFLICT__RESOLVABLE:
				return isResolvable() != RESOLVABLE_EDEFAULT;
			case RulebasePackage.POTENTIAL_CONFLICT__SOURCE_RULE:
				return sourceRule != null;
			case RulebasePackage.POTENTIAL_CONFLICT__TARGET_RULE:
				return targetRule != null;
			case RulebasePackage.POTENTIAL_CONFLICT__CONFLICT_RESOLUTION:
				return conflictResolution != null;
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
		result.append(" (potentialConflictKind: ");
		result.append(potentialConflictKind);
		result.append(')');
		return result.toString();
	}

} //PotentialConflictImpl
