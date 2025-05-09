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

import org.sidiff.javaast.model.JLocalVariable;
import org.sidiff.javaast.model.JNamedElement;
import org.sidiff.javaast.model.JType;
import org.sidiff.javaast.model.JTypedElement;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JLocal Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JLocalVariableImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JLocalVariableImpl#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JLocalVariableImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JLocalVariableImpl extends JIdentifiableElementImpl implements JLocalVariable
{
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
	 * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_NAME_EDEFAULT = null;

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
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JLocalVariableImpl()
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
		return JavaModelPackage.Literals.JLOCAL_VARIABLE;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getName()
  {
		return name;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setName(String newName)
  {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JLOCAL_VARIABLE__NAME, oldName, name));
	}

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getQualifiedName() {
		if ((eContainer() != null) && (eContainer() instanceof JNamedElement)){
			return ((JNamedElement)eContainer()).getQualifiedName() + "/" + this.getName();
		} else{
			return getName();
		}
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JavaModelPackage.JLOCAL_VARIABLE__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JLOCAL_VARIABLE__TYPE, oldType, type));
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
			case JavaModelPackage.JLOCAL_VARIABLE__NAME:
				return getName();
			case JavaModelPackage.JLOCAL_VARIABLE__QUALIFIED_NAME:
				return getQualifiedName();
			case JavaModelPackage.JLOCAL_VARIABLE__TYPE:
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
  public void eSet(int featureID, Object newValue)
  {
		switch (featureID) {
			case JavaModelPackage.JLOCAL_VARIABLE__NAME:
				setName((String)newValue);
				return;
			case JavaModelPackage.JLOCAL_VARIABLE__TYPE:
				setType((JType)newValue);
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
			case JavaModelPackage.JLOCAL_VARIABLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case JavaModelPackage.JLOCAL_VARIABLE__TYPE:
				setType((JType)null);
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
			case JavaModelPackage.JLOCAL_VARIABLE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case JavaModelPackage.JLOCAL_VARIABLE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case JavaModelPackage.JLOCAL_VARIABLE__TYPE:
				return type != null;
		}
		return super.eIsSet(featureID);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
  {
		if (baseClass == JNamedElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JLOCAL_VARIABLE__NAME: return JavaModelPackage.JNAMED_ELEMENT__NAME;
				case JavaModelPackage.JLOCAL_VARIABLE__QUALIFIED_NAME: return JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME;
				default: return -1;
			}
		}
		if (baseClass == JTypedElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JLOCAL_VARIABLE__TYPE: return JavaModelPackage.JTYPED_ELEMENT__TYPE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
  {
		if (baseClass == JNamedElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JNAMED_ELEMENT__NAME: return JavaModelPackage.JLOCAL_VARIABLE__NAME;
				case JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME: return JavaModelPackage.JLOCAL_VARIABLE__QUALIFIED_NAME;
				default: return -1;
			}
		}
		if (baseClass == JTypedElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JTYPED_ELEMENT__TYPE: return JavaModelPackage.JLOCAL_VARIABLE__TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //JLocalVariableImpl
