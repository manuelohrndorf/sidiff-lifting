/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel.impl;

import de.imotep.core.datamodel.de_imotep_core_datamodel.De_imotep_core_datamodelPackage;
import de.imotep.core.datamodel.de_imotep_core_datamodel.MBooleanAttribute;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MBoolean Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MBooleanAttributeImpl#isInitValue <em>Init Value</em>}</li>
 *   <li>{@link de.imotep.core.datamodel.de_imotep_core_datamodel.impl.MBooleanAttributeImpl#isValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MBooleanAttributeImpl extends MAttributeImpl implements MBooleanAttribute {
	/**
	 * The default value of the '{@link #isInitValue() <em>Init Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInitValue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INIT_VALUE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInitValue() <em>Init Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInitValue()
	 * @generated
	 * @ordered
	 */
	protected boolean initValue = INIT_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VALUE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isValue()
	 * @generated
	 * @ordered
	 */
	protected boolean value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MBooleanAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return De_imotep_core_datamodelPackage.Literals.MBOOLEAN_ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInitValue() {
		return initValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitValue(boolean newInitValue) {
		boolean oldInitValue = initValue;
		initValue = newInitValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE__INIT_VALUE, oldInitValue, initValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(boolean newValue) {
		boolean oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE__INIT_VALUE:
				return isInitValue();
			case De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE__VALUE:
				return isValue();
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
			case De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE__INIT_VALUE:
				setInitValue((Boolean)newValue);
				return;
			case De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE__VALUE:
				setValue((Boolean)newValue);
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
			case De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE__INIT_VALUE:
				setInitValue(INIT_VALUE_EDEFAULT);
				return;
			case De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE__VALUE:
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
			case De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE__INIT_VALUE:
				return initValue != INIT_VALUE_EDEFAULT;
			case De_imotep_core_datamodelPackage.MBOOLEAN_ATTRIBUTE__VALUE:
				return value != VALUE_EDEFAULT;
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

} //MBooleanAttributeImpl
