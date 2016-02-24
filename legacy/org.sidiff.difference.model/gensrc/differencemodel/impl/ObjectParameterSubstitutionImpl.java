/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel.impl;

import differencemodel.DifferencemodelPackage;
import differencemodel.ObjectParameterSubstitution;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object Parameter Substitution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link differencemodel.impl.ObjectParameterSubstitutionImpl#getActualA <em>Actual A</em>}</li>
 *   <li>{@link differencemodel.impl.ObjectParameterSubstitutionImpl#getActualB <em>Actual B</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ObjectParameterSubstitutionImpl extends ParameterSubstitutionImpl implements ObjectParameterSubstitution {
	/**
	 * The cached value of the '{@link #getActualA() <em>Actual A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActualA()
	 * @generated
	 * @ordered
	 */
	protected EObject actualA;

	/**
	 * The cached value of the '{@link #getActualB() <em>Actual B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActualB()
	 * @generated
	 * @ordered
	 */
	protected EObject actualB;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ObjectParameterSubstitutionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DifferencemodelPackage.Literals.OBJECT_PARAMETER_SUBSTITUTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getActualA() {
		if (actualA != null && actualA.eIsProxy()) {
			InternalEObject oldActualA = (InternalEObject)actualA;
			actualA = eResolveProxy(oldActualA);
			if (actualA != oldActualA) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_A, oldActualA, actualA));
			}
		}
		return actualA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetActualA() {
		return actualA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActualA(EObject newActualA) {
		EObject oldActualA = actualA;
		actualA = newActualA;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_A, oldActualA, actualA));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getActualB() {
		if (actualB != null && actualB.eIsProxy()) {
			InternalEObject oldActualB = (InternalEObject)actualB;
			actualB = eResolveProxy(oldActualB);
			if (actualB != oldActualB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_B, oldActualB, actualB));
			}
		}
		return actualB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetActualB() {
		return actualB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActualB(EObject newActualB) {
		EObject oldActualB = actualB;
		actualB = newActualB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_B, oldActualB, actualB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_A:
				if (resolve) return getActualA();
				return basicGetActualA();
			case DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_B:
				if (resolve) return getActualB();
				return basicGetActualB();
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
			case DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_A:
				setActualA((EObject)newValue);
				return;
			case DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_B:
				setActualB((EObject)newValue);
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
			case DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_A:
				setActualA((EObject)null);
				return;
			case DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_B:
				setActualB((EObject)null);
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
			case DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_A:
				return actualA != null;
			case DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION__ACTUAL_B:
				return actualB != null;
		}
		return super.eIsSet(featureID);
	}

} //ObjectParameterSubstitutionImpl
