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

import org.sidiff.javaast.model.JIdentifiableElement;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JIdentifiable Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JIdentifiableElementImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JIdentifiableElementImpl#isCompileable <em>Compileable</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class JIdentifiableElementImpl extends EObjectImpl implements JIdentifiableElement
{
  /**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
  protected static final String ID_EDEFAULT = "";

  /**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
  protected String id = ID_EDEFAULT;

  /**
	 * The default value of the '{@link #isCompileable() <em>Compileable</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isCompileable()
	 * @generated
	 * @ordered
	 */
  protected static final boolean COMPILEABLE_EDEFAULT = true;

  /**
	 * The cached value of the '{@link #isCompileable() <em>Compileable</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isCompileable()
	 * @generated
	 * @ordered
	 */
  protected boolean compileable = COMPILEABLE_EDEFAULT;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JIdentifiableElementImpl()
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
		return JavaModelPackage.Literals.JIDENTIFIABLE_ELEMENT;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getId()
  {
		return id;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setId(String newId)
  {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID, oldId, id));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public boolean isCompileable()
  {
		return compileable;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setCompileable(boolean newCompileable)
  {
		boolean oldCompileable = compileable;
		compileable = newCompileable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE, oldCompileable, compileable));
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
			case JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID:
				return getId();
			case JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE:
				return isCompileable();
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
			case JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID:
				setId((String)newValue);
				return;
			case JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE:
				setCompileable((Boolean)newValue);
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
			case JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE:
				setCompileable(COMPILEABLE_EDEFAULT);
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
			case JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE:
				return compileable != COMPILEABLE_EDEFAULT;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", compileable: ");
		result.append(compileable);
		result.append(')');
		return result.toString();
	}

} //JIdentifiableElementImpl
