/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.Parameter;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Parameter Binding</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.sidiff.difference.asymmetric.impl.ParameterBindingImpl#getFormalName
 * <em>Formal Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class ParameterBindingImpl extends EObjectImpl implements ParameterBinding {
	/**
	 * The default value of the '{@link #getFormalName() <em>Formal Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFormalName()
	 * @generated
	 * @ordered
	 */
	protected static final String FORMAL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFormalName() <em>Formal Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFormalName()
	 * @generated
	 * @ordered
	 */
	protected String formalName = FORMAL_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ParameterBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AsymmetricPackage.Literals.PARAMETER_BINDING;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getFormalName() {
		return formalName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFormalName(String newFormalName) {
		String oldFormalName = formalName;
		formalName = newFormalName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AsymmetricPackage.PARAMETER_BINDING__FORMAL_NAME,
					oldFormalName, formalName));
	}

	/**
	 * Checks whether the #em{actual} value equals the #em{default} value
	 * 
	 * @generated NOT
	 */
	public boolean isDefaultValue() {

		// Can only be default if it is a value
		if (this instanceof ValueParameterBinding) {
			ValueParameterBinding valBinding = (ValueParameterBinding) this;

			// Check if there is a default value at all
			if (valBinding.getFormalParameter().getType().getDefaultValue() == null) {
				// Check if there is a value at all
				if (valBinding.getActual() == null) {
					return true;
				} else {
					return false;
				}
			}

			// Check whether the current value equals the default value
			else {
				// FIXME: This is not completely correct. Actually, we should
				// check the default value of the EStructuralFeature
				// (EAttribute) which is set by this parameter. BUt this
				// information is currently not available in the operation
				// interface. Finally this leads to the UpperBound workaround

				// Workaround
				if (valBinding.getFormalName().equals("UpperBound")) {
					OperationInvocation op = (OperationInvocation) valBinding.eContainer();
					if (op.getChangeSet().getName().equals("createTypedAttribute")) {
						return true;
					}
				}

				// Fixme (see above)
				return valBinding.getFormalParameter().getType().getDefaultValue().toString()
						.equals(valBinding.getActual());
			}
		} else {
			return false;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case AsymmetricPackage.PARAMETER_BINDING__FORMAL_NAME:
			return getFormalName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case AsymmetricPackage.PARAMETER_BINDING__FORMAL_NAME:
			setFormalName((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case AsymmetricPackage.PARAMETER_BINDING__FORMAL_NAME:
			setFormalName(FORMAL_NAME_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case AsymmetricPackage.PARAMETER_BINDING__FORMAL_NAME:
			return FORMAL_NAME_EDEFAULT == null ? formalName != null : !FORMAL_NAME_EDEFAULT.equals(formalName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (formalName: ");
		result.append(formalName);
		result.append(')');
		return result.toString();
	}

	@Override
	public Parameter getFormalParameter() {
		// step up containment hierarchy to OperationInvocation
		EObject parent = this.eContainer;
		while (!(parent instanceof OperationInvocation)) {
			parent = parent.eContainer();
		}

		// resolve edit rule and get formal parameter by name
		OperationInvocation opInvocation = (OperationInvocation) parent;
		EditRule editRule = opInvocation.resolveEditRule();
		return editRule.getParameterByName(this.getFormalName());
	}

} // ParameterBindingImpl
