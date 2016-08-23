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

import org.sidiff.javaast.model.JNamedElement;
import org.sidiff.javaast.model.JParameter;
import org.sidiff.javaast.model.JType;
import org.sidiff.javaast.model.JTypedElement;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JParameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JParameterImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JParameterImpl#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JParameterImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JParameterImpl#isIsFinal <em>Is Final</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JParameterImpl extends JIdentifiableElementImpl implements JParameter
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
	 * The default value of the '{@link #isIsFinal() <em>Is Final</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsFinal()
	 * @generated
	 * @ordered
	 */
  protected static final boolean IS_FINAL_EDEFAULT = false;

  /**
	 * The cached value of the '{@link #isIsFinal() <em>Is Final</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsFinal()
	 * @generated
	 * @ordered
	 */
  protected boolean isFinal = IS_FINAL_EDEFAULT;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JParameterImpl()
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
		return JavaModelPackage.Literals.JPARAMETER;
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JPARAMETER__NAME, oldName, name));
	}

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getQualifiedName() {
		if ((eContainer() != null) && (eContainer() instanceof JNamedElement)){
			return ((JNamedElement)eContainer()).getQualifiedName() + "/" + this.getIName();
		} else{
			return getIName();
		}
	}
	
	private String getIName(){
		return getName() + "[" + getId() + "]";
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JavaModelPackage.JPARAMETER__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JPARAMETER__TYPE, oldType, type));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public boolean isIsFinal()
  {
		return isFinal;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setIsFinal(boolean newIsFinal)
  {
		boolean oldIsFinal = isFinal;
		isFinal = newIsFinal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JPARAMETER__IS_FINAL, oldIsFinal, isFinal));
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
			case JavaModelPackage.JPARAMETER__NAME:
				return getName();
			case JavaModelPackage.JPARAMETER__QUALIFIED_NAME:
				return getQualifiedName();
			case JavaModelPackage.JPARAMETER__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case JavaModelPackage.JPARAMETER__IS_FINAL:
				return isIsFinal();
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
			case JavaModelPackage.JPARAMETER__NAME:
				setName((String)newValue);
				return;
			case JavaModelPackage.JPARAMETER__TYPE:
				setType((JType)newValue);
				return;
			case JavaModelPackage.JPARAMETER__IS_FINAL:
				setIsFinal((Boolean)newValue);
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
			case JavaModelPackage.JPARAMETER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case JavaModelPackage.JPARAMETER__TYPE:
				setType((JType)null);
				return;
			case JavaModelPackage.JPARAMETER__IS_FINAL:
				setIsFinal(IS_FINAL_EDEFAULT);
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
			case JavaModelPackage.JPARAMETER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case JavaModelPackage.JPARAMETER__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case JavaModelPackage.JPARAMETER__TYPE:
				return type != null;
			case JavaModelPackage.JPARAMETER__IS_FINAL:
				return isFinal != IS_FINAL_EDEFAULT;
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
				case JavaModelPackage.JPARAMETER__NAME: return JavaModelPackage.JNAMED_ELEMENT__NAME;
				case JavaModelPackage.JPARAMETER__QUALIFIED_NAME: return JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME;
				default: return -1;
			}
		}
		if (baseClass == JTypedElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JPARAMETER__TYPE: return JavaModelPackage.JTYPED_ELEMENT__TYPE;
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
				case JavaModelPackage.JNAMED_ELEMENT__NAME: return JavaModelPackage.JPARAMETER__NAME;
				case JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME: return JavaModelPackage.JPARAMETER__QUALIFIED_NAME;
				default: return -1;
			}
		}
		if (baseClass == JTypedElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JTYPED_ELEMENT__TYPE: return JavaModelPackage.JPARAMETER__TYPE;
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
		result.append(", isFinal: ");
		result.append(isFinal);
		result.append(')');
		return result.toString();
	}

} //JParameterImpl
