/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.rulebase.impl;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
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
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.PotentialAttributeDependency;
import org.sidiff.difference.rulebase.PotentialEdgeDependency;
import org.sidiff.difference.rulebase.PotentialNodeDependency;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.RulebasePackage;
import org.silift.common.util.access.EMFModelAccessEx;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Base</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getItems <em>Items</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getEditRules <em>Edit Rules</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getRecognitionRules <em>Recognition Rules</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getPotentialNodeDependencies <em>Potential Node Dependencies</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getPotentialEdgeDependencies <em>Potential Edge Dependencies</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getPotentialAttributeDependencies <em>Potential Attribute Dependencies</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getDocumentTypes <em>Document Types</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getCharacteristicDocumentType <em>Characteristic Document Type</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getEditRuleFolder <em>Edit Rule Folder</em>}</li>
 *   <li>{@link org.sidiff.difference.rulebase.impl.RuleBaseImpl#getRecognitionRuleFolder <em>Recognition Rule Folder</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleBaseImpl extends EObjectImpl implements RuleBase {
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
	 * The default value of the '{@link #getCharacteristicDocumentType() <em>Characteristic Document Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharacteristicDocumentType()
	 * @generated
	 * @ordered
	 */
	protected static final String CHARACTERISTIC_DOCUMENT_TYPE_EDEFAULT = "";

	/**
	 * The default value of the '{@link #getEditRuleFolder() <em>Edit Rule Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditRuleFolder()
	 * @generated
	 * @ordered
	 */
	protected static final String EDIT_RULE_FOLDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditRuleFolder() <em>Edit Rule Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditRuleFolder()
	 * @generated
	 * @ordered
	 */
	protected String editRuleFolder = EDIT_RULE_FOLDER_EDEFAULT;

	/**
	 * The default value of the '{@link #getRecognitionRuleFolder() <em>Recognition Rule Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecognitionRuleFolder()
	 * @generated
	 * @ordered
	 */
	protected static final String RECOGNITION_RULE_FOLDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRecognitionRuleFolder() <em>Recognition Rule Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecognitionRuleFolder()
	 * @generated
	 * @ordered
	 */
	protected String recognitionRuleFolder = RECOGNITION_RULE_FOLDER_EDEFAULT;

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
	 * @generated NOT
	 */
	@Override
	public String getCharacteristicDocumentType() {
		Set<String> docTypes = new LinkedHashSet<String>(getDocumentTypes());
		return EMFModelAccessEx.getCharacteristicDocumentType(docTypes);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEditRuleFolder() {
		return editRuleFolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditRuleFolder(String newEditRuleFolder) {
		String oldEditRuleFolder = editRuleFolder;
		editRuleFolder = newEditRuleFolder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE__EDIT_RULE_FOLDER, oldEditRuleFolder, editRuleFolder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRecognitionRuleFolder() {
		return recognitionRuleFolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecognitionRuleFolder(String newRecognitionRuleFolder) {
		String oldRecognitionRuleFolder = recognitionRuleFolder;
		recognitionRuleFolder = newRecognitionRuleFolder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.RULE_BASE__RECOGNITION_RULE_FOLDER, oldRecognitionRuleFolder, recognitionRuleFolder));
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
	 * @generated NOT
	 */
	@Override
	public EList<RecognitionRule> getRecognitionRules() {
		EObjectEList<RecognitionRule> eRecognitionRules = new EObjectResolvingEList<RecognitionRule>(RecognitionRule.class, this, RulebasePackage.RULE_BASE__EDIT_RULES);

		for (RuleBaseItem item : getItems()) {
			// Info: The recognition rule is not shipped with a patch...
			if (item.getRecognitionRule() != null) {
				eRecognitionRules.add(item.getRecognitionRule());
			}
		}

		return eRecognitionRules;
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
			case RulebasePackage.RULE_BASE__NAME:
				return getName();
			case RulebasePackage.RULE_BASE__ITEMS:
				return getItems();
			case RulebasePackage.RULE_BASE__EDIT_RULES:
				return getEditRules();
			case RulebasePackage.RULE_BASE__RECOGNITION_RULES:
				return getRecognitionRules();
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES:
				return getPotentialNodeDependencies();
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES:
				return getPotentialEdgeDependencies();
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES:
				return getPotentialAttributeDependencies();
			case RulebasePackage.RULE_BASE__DOCUMENT_TYPES:
				return getDocumentTypes();
			case RulebasePackage.RULE_BASE__CHARACTERISTIC_DOCUMENT_TYPE:
				return getCharacteristicDocumentType();
			case RulebasePackage.RULE_BASE__EDIT_RULE_FOLDER:
				return getEditRuleFolder();
			case RulebasePackage.RULE_BASE__RECOGNITION_RULE_FOLDER:
				return getRecognitionRuleFolder();
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
			case RulebasePackage.RULE_BASE__EDIT_RULE_FOLDER:
				setEditRuleFolder((String)newValue);
				return;
			case RulebasePackage.RULE_BASE__RECOGNITION_RULE_FOLDER:
				setRecognitionRuleFolder((String)newValue);
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
			case RulebasePackage.RULE_BASE__EDIT_RULE_FOLDER:
				setEditRuleFolder(EDIT_RULE_FOLDER_EDEFAULT);
				return;
			case RulebasePackage.RULE_BASE__RECOGNITION_RULE_FOLDER:
				setRecognitionRuleFolder(RECOGNITION_RULE_FOLDER_EDEFAULT);
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
			case RulebasePackage.RULE_BASE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RulebasePackage.RULE_BASE__ITEMS:
				return items != null && !items.isEmpty();
			case RulebasePackage.RULE_BASE__EDIT_RULES:
				return !getEditRules().isEmpty();
			case RulebasePackage.RULE_BASE__RECOGNITION_RULES:
				return !getRecognitionRules().isEmpty();
			case RulebasePackage.RULE_BASE__POTENTIAL_NODE_DEPENDENCIES:
				return potentialNodeDependencies != null && !potentialNodeDependencies.isEmpty();
			case RulebasePackage.RULE_BASE__POTENTIAL_EDGE_DEPENDENCIES:
				return potentialEdgeDependencies != null && !potentialEdgeDependencies.isEmpty();
			case RulebasePackage.RULE_BASE__POTENTIAL_ATTRIBUTE_DEPENDENCIES:
				return potentialAttributeDependencies != null && !potentialAttributeDependencies.isEmpty();
			case RulebasePackage.RULE_BASE__DOCUMENT_TYPES:
				return documentTypes != null && !documentTypes.isEmpty();
			case RulebasePackage.RULE_BASE__CHARACTERISTIC_DOCUMENT_TYPE:
				return CHARACTERISTIC_DOCUMENT_TYPE_EDEFAULT == null ? getCharacteristicDocumentType() != null : !CHARACTERISTIC_DOCUMENT_TYPE_EDEFAULT.equals(getCharacteristicDocumentType());
			case RulebasePackage.RULE_BASE__EDIT_RULE_FOLDER:
				return EDIT_RULE_FOLDER_EDEFAULT == null ? editRuleFolder != null : !EDIT_RULE_FOLDER_EDEFAULT.equals(editRuleFolder);
			case RulebasePackage.RULE_BASE__RECOGNITION_RULE_FOLDER:
				return RECOGNITION_RULE_FOLDER_EDEFAULT == null ? recognitionRuleFolder != null : !RECOGNITION_RULE_FOLDER_EDEFAULT.equals(recognitionRuleFolder);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", documentTypes: ");
		result.append(documentTypes);
		result.append(", editRuleFolder: ");
		result.append(editRuleFolder);
		result.append(", recognitionRuleFolder: ");
		result.append(recognitionRuleFolder);
		result.append(')');
		return result.toString();
	}

} //RuleBaseImpl
