/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.symmetric.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.difference.symmetric.util.StringUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Value Change</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.symmetric.impl.AttributeValueChangeImpl#getObjA <em>Obj A</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetric.impl.AttributeValueChangeImpl#getObjB <em>Obj B</em>}</li>
 *   <li>{@link org.sidiff.difference.symmetric.impl.AttributeValueChangeImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeValueChangeImpl extends ChangeImpl implements AttributeValueChange {
	/**
	 * The cached value of the '{@link #getObjA() <em>Obj A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjA()
	 * @generated
	 * @ordered
	 */
	protected EObject objA;

	/**
	 * The cached value of the '{@link #getObjB() <em>Obj B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjB()
	 * @generated
	 * @ordered
	 */
	protected EObject objB;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EAttribute type;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeValueChangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SymmetricPackage.Literals.ATTRIBUTE_VALUE_CHANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getObjA() {
		if (objA != null && objA.eIsProxy()) {
			InternalEObject oldObjA = (InternalEObject)objA;
			objA = eResolveProxy(oldObjA);
			if (objA != oldObjA) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_A, oldObjA, objA));
			}
		}
		return objA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetObjA() {
		return objA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjA(EObject newObjA) {
		EObject oldObjA = objA;
		objA = newObjA;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_A, oldObjA, objA));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getObjB() {
		if (objB != null && objB.eIsProxy()) {
			InternalEObject oldObjB = (InternalEObject)objB;
			objB = eResolveProxy(oldObjB);
			if (objB != oldObjB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_B, oldObjB, objB));
			}
		}
		return objB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetObjB() {
		return objB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjB(EObject newObjB) {
		EObject oldObjB = objB;
		objB = newObjB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_B, oldObjB, objB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (EAttribute)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EAttribute newType) {
		EAttribute oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_A:
				if (resolve) return getObjA();
				return basicGetObjA();
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_B:
				if (resolve) return getObjB();
				return basicGetObjB();
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__TYPE:
				if (resolve) return getType();
				return basicGetType();
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
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_A:
				setObjA((EObject)newValue);
				return;
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_B:
				setObjB((EObject)newValue);
				return;
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__TYPE:
				setType((EAttribute)newValue);
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
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_A:
				setObjA((EObject)null);
				return;
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_B:
				setObjB((EObject)null);
				return;
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__TYPE:
				setType((EAttribute)null);
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
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_A:
				return objA != null;
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__OBJ_B:
				return objB != null;
			case SymmetricPackage.ATTRIBUTE_VALUE_CHANGE__TYPE:
				return type != null;
		}
		return super.eIsSet(featureID);
	}
	
	
	@Override
	public String toString() {
	    StringBuilder result = new StringBuilder(eClass().getName());
	    result.append('@');
	    result.append(Integer.toHexString(hashCode()));

		if (eIsProxy()) {
			result.append(" (eProxyURI: ");
			result.append(eProxyURI());
			
			if (eDynamicClass() != null) {
				result.append(" eClass: ");
				result.append(eDynamicClass());
			}
			result.append(')');
		} else if (eDynamicClass() != null) {
			result.append(" (eClass: ");
			result.append(eDynamicClass());
			result.append(')');
		}

	    return result.toString() 
	    		+ " (type: " + StringUtil.eAttributeToString(type)
	    		+ " objA: " + StringUtil.eObjectToString(getObjA())
	    		+ " objB: " + StringUtil.eObjectToString(getObjB()) + ")";
	}

} //AttributeValueChangeImpl
