/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.PotentialDependency;
import org.sidiff.difference.rulebase.PotentialDependencyKind;
import org.sidiff.difference.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Potential Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.impl.PotentialDependencyImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.PotentialDependencyImpl#getSourceRule <em>Source Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.PotentialDependencyImpl#getTargetRule <em>Target Rule</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.PotentialDependencyImpl#isTransient <em>Transient</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PotentialDependencyImpl extends EObjectImpl implements PotentialDependency {
	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final PotentialDependencyKind KIND_EDEFAULT = PotentialDependencyKind.USE_DELETE;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected PotentialDependencyKind kind = KIND_EDEFAULT;

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
	 * The default value of the '{@link #isTransient() <em>Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTransient()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TRANSIENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTransient() <em>Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTransient()
	 * @generated
	 * @ordered
	 */
	protected boolean transient_ = TRANSIENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PotentialDependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.POTENTIAL_DEPENDENCY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PotentialDependencyKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKind(PotentialDependencyKind newKind) {
		PotentialDependencyKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_DEPENDENCY__KIND, oldKind, kind));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_DEPENDENCY__SOURCE_RULE, oldSourceRule, sourceRule));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_DEPENDENCY__SOURCE_RULE, oldSourceRule, sourceRule));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulebasePackage.POTENTIAL_DEPENDENCY__TARGET_RULE, oldTargetRule, targetRule));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_DEPENDENCY__TARGET_RULE, oldTargetRule, targetRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTransient() {
		return transient_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransient(boolean newTransient) {
		boolean oldTransient = transient_;
		transient_ = newTransient;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.POTENTIAL_DEPENDENCY__TRANSIENT, oldTransient, transient_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulebasePackage.POTENTIAL_DEPENDENCY__KIND:
				return getKind();
			case RulebasePackage.POTENTIAL_DEPENDENCY__SOURCE_RULE:
				if (resolve) return getSourceRule();
				return basicGetSourceRule();
			case RulebasePackage.POTENTIAL_DEPENDENCY__TARGET_RULE:
				if (resolve) return getTargetRule();
				return basicGetTargetRule();
			case RulebasePackage.POTENTIAL_DEPENDENCY__TRANSIENT:
				return isTransient();
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
			case RulebasePackage.POTENTIAL_DEPENDENCY__KIND:
				setKind((PotentialDependencyKind)newValue);
				return;
			case RulebasePackage.POTENTIAL_DEPENDENCY__SOURCE_RULE:
				setSourceRule((EditRule)newValue);
				return;
			case RulebasePackage.POTENTIAL_DEPENDENCY__TARGET_RULE:
				setTargetRule((EditRule)newValue);
				return;
			case RulebasePackage.POTENTIAL_DEPENDENCY__TRANSIENT:
				setTransient((Boolean)newValue);
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
			case RulebasePackage.POTENTIAL_DEPENDENCY__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case RulebasePackage.POTENTIAL_DEPENDENCY__SOURCE_RULE:
				setSourceRule((EditRule)null);
				return;
			case RulebasePackage.POTENTIAL_DEPENDENCY__TARGET_RULE:
				setTargetRule((EditRule)null);
				return;
			case RulebasePackage.POTENTIAL_DEPENDENCY__TRANSIENT:
				setTransient(TRANSIENT_EDEFAULT);
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
			case RulebasePackage.POTENTIAL_DEPENDENCY__KIND:
				return kind != KIND_EDEFAULT;
			case RulebasePackage.POTENTIAL_DEPENDENCY__SOURCE_RULE:
				return sourceRule != null;
			case RulebasePackage.POTENTIAL_DEPENDENCY__TARGET_RULE:
				return targetRule != null;
			case RulebasePackage.POTENTIAL_DEPENDENCY__TRANSIENT:
				return transient_ != TRANSIENT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (kind: ");
		result.append(kind);
		result.append(')');
		
		result.append("\n\tsrc rule: " + getSourceRule());
		result.append("\n\ttgt rule: " + getTargetRule());
		
		return result.toString();
	}

} //PotentialDependencyImpl
