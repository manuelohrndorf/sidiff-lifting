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
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.EditRuleAttachment;
import org.sidiff.editrule.rulebase.PotentialAttributeConflict;
import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeConflict;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Base</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getKey <em>Key</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getItems <em>Items</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getEditRules <em>Edit Rules</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getPotentialNodeDependencies <em>Potential Node Dependencies</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getPotentialEdgeDependencies <em>Potential Edge Dependencies</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getPotentialAttributeDependencies <em>Potential Attribute Dependencies</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getDocumentTypes <em>Document Types</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getPotentialNodeConflicts <em>Potential Node Conflicts</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getPotentialEdgeConflicts <em>Potential Edge Conflicts</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.RuleBaseImpl#getPotentialAttributeConflicts <em>Potential Attribute Conflicts</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleBaseImpl extends EObjectImpl implements RuleBase {
	/**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getItems() <em>Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItems()
	 * @generated
	 * @ordered
	 */
	protected EList<RuleBaseItem> items;

	/**
	 * The cached value of the '{@link #getPotentialNodeDependencies() <em>Potential Node Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPotentialNodeDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<PotentialNodeDependency> potentialNodeDependencies;

	/**
	 * The cached value of the '{@link #getPotentialEdgeDependencies() <em>Potential Edge Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPotentialEdgeDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<PotentialEdgeDependency> potentialEdgeDependencies;

	/**
	 * The cached value of the '{@link #getPotentialAttributeDependencies() <em>Potential Attribute Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPotentialAttributeDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<PotentialAttributeDependency> potentialAttributeDependencies;

	/**
	 * The cached value of the '{@link #getDocumentTypes() <em>Document Types</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<String> documentTypes;

	/**
	 * The cached value of the '{@link #getPotentialNodeConflicts() <em>Potential Node Conflicts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPotentialNodeConflicts()
	 * @generated
	 * @ordered
	 */
	protected EList<PotentialNodeConflict> potentialNodeConflicts;

	/**
	 * The cached value of the '{@link #getPotentialEdgeConflicts() <em>Potential Edge Conflicts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPotentialEdgeConflicts()
	 * @generated
	 * @ordered
	 */
	protected EList<PotentialEdgeConflict> potentialEdgeConflicts;

	/**
	 * The cached value of the '{@link #getPotentialAttributeConflicts() <em>Potential Attribute Conflicts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPotentialAttributeConflicts()
	 * @generated
	 * @ordered
	 */
	protected EList<PotentialAttributeConflict> potentialAttributeConflicts;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleBaseImpl() {
		super();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public <T extends EditRuleAttachment> EList<T> getEditRuleAttachments(Class<T> type) {
		EList<T> coRules = new BasicEList<T>();
		
		for (RuleBaseItem item : getItems()) {
			coRules.add(item.getEditRuleAttachment(type));
		}
		
		return coRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.RULE_BASE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getDocumentTypes() {
		if (documentTypes == null) {
			documentTypes = new EDataTypeUniqueEList<String>(String.class, this, RulebasePackage.RULE_BASE__DOCUMENT_TYPES);
		}
		return documentTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PotentialNodeConflict> getPotentialNodeConflicts() {
		if (potentialNodeConflicts == null) {
			potentialNodeConflicts = new EObjectContainmentWithInverseEList<PotentialNodeConflict>(PotentialNodeConflict.class, this, RulebasePackage.RULE_BASE__POTENTIAL_NODE_CONFLICTS, RulebasePackage.POTENTIAL_NODE_CONFLICT__RULE_BASE);
		}
		return potentialNodeConflicts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PotentialEdgeConflict> getPotentialEdgeConflicts() {
		if (potentialEdgeConflicts == null) {
			potentialEdgeConflicts = new EObjectContainmentWithInverseEList<PotentialEdgeConflict>(PotentialEdgeConflict.class, this, RulebasePackage.RULE_BASE__POTENTIAL_EDGE_CONFLICTS, RulebasePackage.POTENTIAL_EDGE_CONFLICT__RULE_BASE);
		}
		return potentialEdgeConflicts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PotentialAttributeConflict> getPotentialAttributeConflicts() {
		if (potentialAttributeConflicts == null) {
			potentialAttributeConflicts = new EObjectContainmentWithInverseEList<PotentialAttributeConflict>(PotentialAttributeConflict.class, this, RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS, RulebasePackage.POTENTIAL_ATTRIBUTE_CONFLICT__RULE_BASE);
		}
		return potentialAttributeConflicts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RuleBaseItem> getItems() {
		if (items == null) {
			items = new EObjectContainmentWithInverseEList<RuleBaseItem>(RuleBaseItem.class, this, RulebasePackage.RULE_BASE__ITEMS, RulebasePackage.RULE_BASE_ITEM__RULE_BASE);
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<EditRule> getEditRules() {
		EObjectEList<EditRule> eEditRules = new EObjectResolvingEList<EditRule>(EditRule.class, this, RulebasePackage.RULE_BASE__EDIT_RULES);

		for (RuleBaseItem item : getItems()) {
			eEditRules.add(item.getEditRule());
		}

		return eEditRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PotentialNodeDependency> getPotentialNodeDependencies() {
		if (potentialNodeDependencies == null) {
			potentialNodeDependencies = new EObjectContainmentWithInverseEList<PotentialNodeDependency>(PotentialNodeDependency.class, this, RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES, RulebasePackage.POTENTIAL_NODE_DEPENDENCY__RULE_BASE);
		}
		return potentialNodeDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PotentialEdgeDependency> getPotentialEdgeDependencies() {
		if (potentialEdgeDependencies == null) {
			potentialEdgeDependencies = new EObjectContainmentWithInverseEList<PotentialEdgeDependency>(PotentialEdgeDependency.class, this, RulebasePackage.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES, RulebasePackage.POTENTIAL_EDGE_DEPENDENCY__RULE_BASE);
		}
		return potentialEdgeDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PotentialAttributeDependency> getPotentialAttributeDependencies() {
		if (potentialAttributeDependencies == null) {
			potentialAttributeDependencies = new EObjectContainmentWithInverseEList<PotentialAttributeDependency>(PotentialAttributeDependency.class, this, RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES, RulebasePackage.POTENTIAL_ATTRIBUTE_DEPENDENCY__RULE_BASE);
		}
		return potentialAttributeDependencies;
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
			case RulebasePackage.RULE_BASE__ITEMS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getItems()).basicAdd(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPotentialNodeDependencies()).basicAdd(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPotentialEdgeDependencies()).basicAdd(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPotentialAttributeDependencies()).basicAdd(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_CONFLICTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPotentialNodeConflicts()).basicAdd(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_CONFLICTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPotentialEdgeConflicts()).basicAdd(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPotentialAttributeConflicts()).basicAdd(otherEnd, msgs);
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
			case RulebasePackage.RULE_BASE__ITEMS:
				return ((InternalEList<?>)getItems()).basicRemove(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES:
				return ((InternalEList<?>)getPotentialNodeDependencies()).basicRemove(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES:
				return ((InternalEList<?>)getPotentialEdgeDependencies()).basicRemove(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES:
				return ((InternalEList<?>)getPotentialAttributeDependencies()).basicRemove(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_CONFLICTS:
				return ((InternalEList<?>)getPotentialNodeConflicts()).basicRemove(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_CONFLICTS:
				return ((InternalEList<?>)getPotentialEdgeConflicts()).basicRemove(otherEnd, msgs);
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS:
				return ((InternalEList<?>)getPotentialAttributeConflicts()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulebasePackage.RULE_BASE__KEY:
				return getKey();
			case RulebasePackage.RULE_BASE__NAME:
				return getName();
			case RulebasePackage.RULE_BASE__ITEMS:
				return getItems();
			case RulebasePackage.RULE_BASE__EDIT_RULES:
				return getEditRules();
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES:
				return getPotentialNodeDependencies();
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES:
				return getPotentialEdgeDependencies();
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES:
				return getPotentialAttributeDependencies();
			case RulebasePackage.RULE_BASE__DOCUMENT_TYPES:
				return getDocumentTypes();
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_CONFLICTS:
				return getPotentialNodeConflicts();
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_CONFLICTS:
				return getPotentialEdgeConflicts();
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS:
				return getPotentialAttributeConflicts();
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
			case RulebasePackage.RULE_BASE__KEY:
				setKey((String)newValue);
				return;
			case RulebasePackage.RULE_BASE__NAME:
				setName((String)newValue);
				return;
			case RulebasePackage.RULE_BASE__ITEMS:
				getItems().clear();
				getItems().addAll((Collection<? extends RuleBaseItem>)newValue);
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES:
				getPotentialNodeDependencies().clear();
				getPotentialNodeDependencies().addAll((Collection<? extends PotentialNodeDependency>)newValue);
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES:
				getPotentialEdgeDependencies().clear();
				getPotentialEdgeDependencies().addAll((Collection<? extends PotentialEdgeDependency>)newValue);
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES:
				getPotentialAttributeDependencies().clear();
				getPotentialAttributeDependencies().addAll((Collection<? extends PotentialAttributeDependency>)newValue);
				return;
			case RulebasePackage.RULE_BASE__DOCUMENT_TYPES:
				getDocumentTypes().clear();
				getDocumentTypes().addAll((Collection<? extends String>)newValue);
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_CONFLICTS:
				getPotentialNodeConflicts().clear();
				getPotentialNodeConflicts().addAll((Collection<? extends PotentialNodeConflict>)newValue);
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_CONFLICTS:
				getPotentialEdgeConflicts().clear();
				getPotentialEdgeConflicts().addAll((Collection<? extends PotentialEdgeConflict>)newValue);
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS:
				getPotentialAttributeConflicts().clear();
				getPotentialAttributeConflicts().addAll((Collection<? extends PotentialAttributeConflict>)newValue);
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
			case RulebasePackage.RULE_BASE__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case RulebasePackage.RULE_BASE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RulebasePackage.RULE_BASE__ITEMS:
				getItems().clear();
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES:
				getPotentialNodeDependencies().clear();
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES:
				getPotentialEdgeDependencies().clear();
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES:
				getPotentialAttributeDependencies().clear();
				return;
			case RulebasePackage.RULE_BASE__DOCUMENT_TYPES:
				getDocumentTypes().clear();
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_CONFLICTS:
				getPotentialNodeConflicts().clear();
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_CONFLICTS:
				getPotentialEdgeConflicts().clear();
				return;
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS:
				getPotentialAttributeConflicts().clear();
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
			case RulebasePackage.RULE_BASE__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case RulebasePackage.RULE_BASE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RulebasePackage.RULE_BASE__ITEMS:
				return items != null && !items.isEmpty();
			case RulebasePackage.RULE_BASE__EDIT_RULES:
				return !getEditRules().isEmpty();
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES:
				return potentialNodeDependencies != null && !potentialNodeDependencies.isEmpty();
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES:
				return potentialEdgeDependencies != null && !potentialEdgeDependencies.isEmpty();
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES:
				return potentialAttributeDependencies != null && !potentialAttributeDependencies.isEmpty();
			case RulebasePackage.RULE_BASE__DOCUMENT_TYPES:
				return documentTypes != null && !documentTypes.isEmpty();
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_CONFLICTS:
				return potentialNodeConflicts != null && !potentialNodeConflicts.isEmpty();
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_CONFLICTS:
				return potentialEdgeConflicts != null && !potentialEdgeConflicts.isEmpty();
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_CONFLICTS:
				return potentialAttributeConflicts != null && !potentialAttributeConflicts.isEmpty();
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
		result.append(" (key: ");
		result.append(key);
		result.append(", name: ");
		result.append(name);
		result.append(", documentTypes: ");
		result.append(documentTypes);
		result.append(')');
		return result.toString();
	}

} //RuleBaseImpl
