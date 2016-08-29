/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.sidiff.javaast.model.JGenericType;
import org.sidiff.javaast.model.JTemplateBinding;
import org.sidiff.javaast.model.JType;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JTemplate Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JTemplateBindingImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JTemplateBindingImpl#getGenericType <em>Generic Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JTemplateBindingImpl extends JIdentifiableElementImpl implements JTemplateBinding
{
  /**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
  protected JType type;

  /**
	 * The cached value of the '{@link #getGenericType() <em>Generic Type</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getGenericType()
	 * @generated
	 * @ordered
	 */
  protected JGenericType genericType;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JTemplateBindingImpl()
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
		return JavaModelPackage.Literals.JTEMPLATE_BINDING;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JType getType()
  {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (JType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JavaModelPackage.JTEMPLATE_BINDING__TYPE, oldType, type));
			}
		}
		return type;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JType basicGetType()
  {
		return type;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setType(JType newType)
  {
		JType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JTEMPLATE_BINDING__TYPE, oldType, type));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JGenericType getGenericType()
  {
		if (genericType != null && genericType.eIsProxy()) {
			InternalEObject oldGenericType = (InternalEObject)genericType;
			genericType = (JGenericType)eResolveProxy(oldGenericType);
			if (genericType != oldGenericType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JavaModelPackage.JTEMPLATE_BINDING__GENERIC_TYPE, oldGenericType, genericType));
			}
		}
		return genericType;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JGenericType basicGetGenericType()
  {
		return genericType;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setGenericType(JGenericType newGenericType)
  {
		JGenericType oldGenericType = genericType;
		genericType = newGenericType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JTEMPLATE_BINDING__GENERIC_TYPE, oldGenericType, genericType));
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
			case JavaModelPackage.JTEMPLATE_BINDING__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case JavaModelPackage.JTEMPLATE_BINDING__GENERIC_TYPE:
				if (resolve) return getGenericType();
				return basicGetGenericType();
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
			case JavaModelPackage.JTEMPLATE_BINDING__TYPE:
				setType((JType)newValue);
				return;
			case JavaModelPackage.JTEMPLATE_BINDING__GENERIC_TYPE:
				setGenericType((JGenericType)newValue);
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
			case JavaModelPackage.JTEMPLATE_BINDING__TYPE:
				setType((JType)null);
				return;
			case JavaModelPackage.JTEMPLATE_BINDING__GENERIC_TYPE:
				setGenericType((JGenericType)null);
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
			case JavaModelPackage.JTEMPLATE_BINDING__TYPE:
				return type != null;
			case JavaModelPackage.JTEMPLATE_BINDING__GENERIC_TYPE:
				return genericType != null;
		}
		return super.eIsSet(featureID);
	}

} //JTemplateBindingImpl
