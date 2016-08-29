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

import org.sidiff.javaast.model.JVisibilityKind;
import org.sidiff.javaast.model.JVisibleElement;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JVisible Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JVisibleElementImpl#getVisibilityKind <em>Visibility Kind</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class JVisibleElementImpl extends EObjectImpl implements JVisibleElement
{
  /**
	 * The default value of the '{@link #getVisibilityKind() <em>Visibility Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getVisibilityKind()
	 * @generated
	 * @ordered
	 */
  protected static final JVisibilityKind VISIBILITY_KIND_EDEFAULT = JVisibilityKind.PUBLIC;

  /**
	 * The cached value of the '{@link #getVisibilityKind() <em>Visibility Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getVisibilityKind()
	 * @generated
	 * @ordered
	 */
  protected JVisibilityKind visibilityKind = VISIBILITY_KIND_EDEFAULT;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JVisibleElementImpl()
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
		return JavaModelPackage.Literals.JVISIBLE_ELEMENT;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JVisibilityKind getVisibilityKind()
  {
		return visibilityKind;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setVisibilityKind(JVisibilityKind newVisibilityKind)
  {
		JVisibilityKind oldVisibilityKind = visibilityKind;
		visibilityKind = newVisibilityKind == null ? VISIBILITY_KIND_EDEFAULT : newVisibilityKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND, oldVisibilityKind, visibilityKind));
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
			case JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND:
				return getVisibilityKind();
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
			case JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND:
				setVisibilityKind((JVisibilityKind)newValue);
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
			case JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND:
				setVisibilityKind(VISIBILITY_KIND_EDEFAULT);
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
			case JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND:
				return visibilityKind != VISIBILITY_KIND_EDEFAULT;
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
		result.append(" (visibilityKind: ");
		result.append(visibilityKind);
		result.append(')');
		return result.toString();
	}

} //JVisibleElementImpl
