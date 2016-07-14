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
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.EditRuleAttachment;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Base Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseItemImpl#getEditRule <em>Edit Rule</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseItemImpl#getRuleBase <em>Rule Base</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseItemImpl#isActive <em>Active</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseItemImpl#getEditRuleAttachments <em>Edit Rule Attachments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleBaseItemImpl extends EObjectImpl implements RuleBaseItem {
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
	 * The default value of the '{@link #isActive() <em>Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ACTIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #getEditRuleAttachments() <em>Edit Rule Attachments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditRuleAttachments()
	 * @generated
	 * @ordered
	 */
	protected EList<EditRuleAttachment> editRuleAttachments;

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
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends EditRuleAttachment> T getEditRuleAttachment(Class<T> type) {
		
		for (EditRuleAttachment attachments : getEditRuleAttachments()) {
			if (type.isInstance(attachments)) {
				return (T) attachments;
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
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.RULE_BASE_ITEM;
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
	 * @generated NOT
	 */
	public boolean isActive() {
		return this.getEditRule().getExecuteMainUnit().isActivated();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setActive(boolean newActive) {
		this.getEditRule().getExecuteMainUnit().setActivated(newActive);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EditRuleAttachment> getEditRuleAttachments() {
		if (editRuleAttachments == null) {
			editRuleAttachments = new EObjectContainmentWithInverseEList<EditRuleAttachment>(EditRuleAttachment.class, this, RulebasePackage.RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS, RulebasePackage.EDIT_RULE_ATTACHMENT__RULE_BASE_ITEM);
		}
		return editRuleAttachments;
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
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				if (editRule != null)
					msgs = ((InternalEObject)editRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RulebasePackage.RULE_BASE_ITEM__EDIT_RULE, null, msgs);
				return basicSetEditRule((EditRule)otherEnd, msgs);
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetRuleBase((RuleBase)otherEnd, msgs);
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getEditRuleAttachments()).basicAdd(otherEnd, msgs);
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
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				return basicSetRuleBase(null, msgs);
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS:
				return ((InternalEList<?>)getEditRuleAttachments()).basicRemove(otherEnd, msgs);
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
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				return getEditRule();
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				return getRuleBase();
			case RulebasePackage.RULE_BASE_ITEM__ACTIVE:
				return isActive();
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS:
				return getEditRuleAttachments();
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
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				setEditRule((EditRule)newValue);
				return;
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				setRuleBase((RuleBase)newValue);
				return;
			case RulebasePackage.RULE_BASE_ITEM__ACTIVE:
				setActive((Boolean)newValue);
				return;
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS:
				getEditRuleAttachments().clear();
				getEditRuleAttachments().addAll((Collection<? extends EditRuleAttachment>)newValue);
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
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				setEditRule((EditRule)null);
				return;
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				setRuleBase((RuleBase)null);
				return;
			case RulebasePackage.RULE_BASE_ITEM__ACTIVE:
				setActive(ACTIVE_EDEFAULT);
				return;
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS:
				getEditRuleAttachments().clear();
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
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE:
				return editRule != null;
			case RulebasePackage.RULE_BASE_ITEM__RULE_BASE:
				return getRuleBase() != null;
			case RulebasePackage.RULE_BASE_ITEM__ACTIVE:
				return isActive() != ACTIVE_EDEFAULT;
			case RulebasePackage.RULE_BASE_ITEM__EDIT_RULE_ATTACHMENTS:
				return editRuleAttachments != null && !editRuleAttachments.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * Shrinks the contained lists to the minimal size.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void shrink() {
		
		// Compress attachment list:
		if (editRuleAttachments instanceof BasicEList) {
			((BasicEList<?>) editRuleAttachments).shrink();
		}
		
		// Compress edit-rule:
		if (getEditRule().getClassification() instanceof BasicEList) {
			((BasicEList<?>) getEditRule().getClassification()).shrink();
		}
		if (getEditRule().getParameters() instanceof BasicEList) {
			((BasicEList<?>) getEditRule().getParameters()).shrink();
		}
	}

} //RuleBaseItemImpl
