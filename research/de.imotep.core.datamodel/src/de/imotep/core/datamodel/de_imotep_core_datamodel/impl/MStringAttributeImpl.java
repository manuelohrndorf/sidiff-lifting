/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel.impl;

import de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MStringAttribute;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MString Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MStringAttributeImpl#getInitValue <em>Init Value</em>}</li>
 *   <li>{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MStringAttributeImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MStringAttributeImpl extends MAttributeImpl implements MStringAttribute {
	/**
	 * The default value of the '{@link #getInitValue() <em>Init Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitValue()
	 * @generated
	 * @ordered
	 */
	protected static final String INIT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInitValue() <em>Init Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitValue()
	 * @generated
	 * @ordered
	 */
	protected String initValue = INIT_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MStringAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return De_imotep_core_datamodelPackage.Literals.MSTRING_ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInitValue() {
		return initValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitValue(String newInitValue) {
		String oldInitValue = initValue;
		initValue = newInitValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE__INIT_VALUE, oldInitValue, initValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE__INIT_VALUE:
				return getInitValue();
			case De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE__VALUE:
				return getValue();
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
			case De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE__INIT_VALUE:
				setInitValue((String)newValue);
				return;
			case De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE__VALUE:
				setValue((String)newValue);
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
			case De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE__INIT_VALUE:
				setInitValue(INIT_VALUE_EDEFAULT);
				return;
			case De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE__INIT_VALUE:
				return INIT_VALUE_EDEFAULT == null ? initValue != null : !INIT_VALUE_EDEFAULT.equals(initValue);
			case De_imotep_core_datamodelPackage.MSTRING_ATTRIBUTE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		result.append(" (initValue: ");
		result.append(initValue);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //MStringAttributeImpl
