/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.sidiff.javaast.model.JAdressableFragment;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JAdressable Fragment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JAdressableFragmentImpl#getStartByte <em>Start Byte</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JAdressableFragmentImpl#getByteLength <em>Byte Length</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class JAdressableFragmentImpl extends EObjectImpl implements JAdressableFragment
{
  /**
	 * The default value of the '{@link #getStartByte() <em>Start Byte</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getStartByte()
	 * @generated
	 * @ordered
	 */
  protected static final int START_BYTE_EDEFAULT = 0;

  /**
	 * The cached value of the '{@link #getStartByte() <em>Start Byte</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getStartByte()
	 * @generated
	 * @ordered
	 */
  protected int startByte = START_BYTE_EDEFAULT;

  /**
	 * The default value of the '{@link #getByteLength() <em>Byte Length</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getByteLength()
	 * @generated
	 * @ordered
	 */
  protected static final int BYTE_LENGTH_EDEFAULT = 0;

  /**
	 * The cached value of the '{@link #getByteLength() <em>Byte Length</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getByteLength()
	 * @generated
	 * @ordered
	 */
  protected int byteLength = BYTE_LENGTH_EDEFAULT;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JAdressableFragmentImpl()
  {
		super();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  protected EClass eStaticClass()
  {
		return JavaModelPackage.Literals.JADRESSABLE_FRAGMENT;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public int getStartByte()
  {
		return startByte;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setStartByte(int newStartByte)
  {
		int oldStartByte = startByte;
		startByte = newStartByte;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JADRESSABLE_FRAGMENT__START_BYTE, oldStartByte, startByte));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public int getByteLength()
  {
		return byteLength;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setByteLength(int newByteLength)
  {
		int oldByteLength = byteLength;
		byteLength = newByteLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JADRESSABLE_FRAGMENT__BYTE_LENGTH, oldByteLength, byteLength));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
		switch (featureID) {
			case JavaModelPackage.JADRESSABLE_FRAGMENT__START_BYTE:
				return getStartByte();
			case JavaModelPackage.JADRESSABLE_FRAGMENT__BYTE_LENGTH:
				return getByteLength();
		}
		return super.eGet(featureID, resolve, coreType);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public void eSet(int featureID, Object newValue)
  {
		switch (featureID) {
			case JavaModelPackage.JADRESSABLE_FRAGMENT__START_BYTE:
				setStartByte((Integer)newValue);
				return;
			case JavaModelPackage.JADRESSABLE_FRAGMENT__BYTE_LENGTH:
				setByteLength((Integer)newValue);
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
  public void eUnset(int featureID)
  {
		switch (featureID) {
			case JavaModelPackage.JADRESSABLE_FRAGMENT__START_BYTE:
				setStartByte(START_BYTE_EDEFAULT);
				return;
			case JavaModelPackage.JADRESSABLE_FRAGMENT__BYTE_LENGTH:
				setByteLength(BYTE_LENGTH_EDEFAULT);
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
  public boolean eIsSet(int featureID)
  {
		switch (featureID) {
			case JavaModelPackage.JADRESSABLE_FRAGMENT__START_BYTE:
				return startByte != START_BYTE_EDEFAULT;
			case JavaModelPackage.JADRESSABLE_FRAGMENT__BYTE_LENGTH:
				return byteLength != BYTE_LENGTH_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public String toString()
  {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (startByte: ");
		result.append(startByte);
		result.append(", byteLength: ");
		result.append(byteLength);
		result.append(')');
		return result.toString();
	}

} //JAdressableFragmentImpl
