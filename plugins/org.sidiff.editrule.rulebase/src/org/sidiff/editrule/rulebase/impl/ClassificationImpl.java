/**
 */
package org.sidiff.editrule.rulebase.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.sidiff.editrule.rulebase.Classification;
import org.sidiff.editrule.rulebase.RulebasePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Classification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.ClassificationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.editrule.rulebase.impl.ClassificationImpl#getClassificator <em>Classificator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClassificationImpl extends EObjectImpl implements Classification {
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
	 * The default value of the '{@link #getClassificator() <em>Classificator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassificator()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASSIFICATOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassificator() <em>Classificator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassificator()
	 * @generated
	 * @ordered
	 */
	protected String classificator = CLASSIFICATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulebasePackage.Literals.CLASSIFICATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.CLASSIFICATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getClassificator() {
		return classificator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setClassificator(String newClassificator) {
		String oldClassificator = classificator;
		classificator = newClassificator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulebasePackage.CLASSIFICATION__CLASSIFICATOR, oldClassificator, classificator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulebasePackage.CLASSIFICATION__NAME:
				return getName();
			case RulebasePackage.CLASSIFICATION__CLASSIFICATOR:
				return getClassificator();
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
			case RulebasePackage.CLASSIFICATION__NAME:
				setName((String)newValue);
				return;
			case RulebasePackage.CLASSIFICATION__CLASSIFICATOR:
				setClassificator((String)newValue);
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
			case RulebasePackage.CLASSIFICATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RulebasePackage.CLASSIFICATION__CLASSIFICATOR:
				setClassificator(CLASSIFICATOR_EDEFAULT);
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
			case RulebasePackage.CLASSIFICATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RulebasePackage.CLASSIFICATION__CLASSIFICATOR:
				return CLASSIFICATOR_EDEFAULT == null ? classificator != null : !CLASSIFICATOR_EDEFAULT.equals(classificator);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", classificator: ");
		result.append(classificator);
		result.append(')');
		return result.toString();
	}

} //ClassificationImpl
