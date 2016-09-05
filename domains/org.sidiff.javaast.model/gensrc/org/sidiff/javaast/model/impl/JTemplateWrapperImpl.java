/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.javaast.model.JNamedElement;
import org.sidiff.javaast.model.JTemplateBinding;
import org.sidiff.javaast.model.JTemplateWrapper;
import org.sidiff.javaast.model.JType;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JTemplate Wrapper</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JTemplateWrapperImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JTemplateWrapperImpl#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JTemplateWrapperImpl#isIsExternal <em>Is External</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JTemplateWrapperImpl#getBindings <em>Bindings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JTemplateWrapperImpl extends JIdentifiableElementImpl implements JTemplateWrapper
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
	 * The default value of the '{@link #isIsExternal() <em>Is External</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsExternal()
	 * @generated
	 * @ordered
	 */
  protected static final boolean IS_EXTERNAL_EDEFAULT = false;

  /**
	 * The cached value of the '{@link #isIsExternal() <em>Is External</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsExternal()
	 * @generated
	 * @ordered
	 */
  protected boolean isExternal = IS_EXTERNAL_EDEFAULT;

  /**
	 * The cached value of the '{@link #getBindings() <em>Bindings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getBindings()
	 * @generated
	 * @ordered
	 */
  protected EList<JTemplateBinding> bindings;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JTemplateWrapperImpl()
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
		return JavaModelPackage.Literals.JTEMPLATE_WRAPPER;
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
	 * @generated NOT
	 */
  public void setName(String newName)
  {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JTEMPLATE_WRAPPER__NAME, oldName, name));
	}

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getQualifiedName() {
		// HACK Blind kopiert
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
  public boolean isIsExternal()
  {
		return isExternal;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setIsExternal(boolean newIsExternal)
  {
		boolean oldIsExternal = isExternal;
		isExternal = newIsExternal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JTEMPLATE_WRAPPER__IS_EXTERNAL, oldIsExternal, isExternal));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JTemplateBinding> getBindings()
  {
		if (bindings == null) {
			bindings = new EObjectContainmentEList<JTemplateBinding>(JTemplateBinding.class, this, JavaModelPackage.JTEMPLATE_WRAPPER__BINDINGS);
		}
		return bindings;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
		switch (featureID) {
			case JavaModelPackage.JTEMPLATE_WRAPPER__BINDINGS:
				return ((InternalEList<?>)getBindings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
			case JavaModelPackage.JTEMPLATE_WRAPPER__NAME:
				return getName();
			case JavaModelPackage.JTEMPLATE_WRAPPER__QUALIFIED_NAME:
				return getQualifiedName();
			case JavaModelPackage.JTEMPLATE_WRAPPER__IS_EXTERNAL:
				return isIsExternal();
			case JavaModelPackage.JTEMPLATE_WRAPPER__BINDINGS:
				return getBindings();
		}
		return super.eGet(featureID, resolve, coreType);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
		switch (featureID) {
			case JavaModelPackage.JTEMPLATE_WRAPPER__NAME:
				setName((String)newValue);
				return;
			case JavaModelPackage.JTEMPLATE_WRAPPER__IS_EXTERNAL:
				setIsExternal((Boolean)newValue);
				return;
			case JavaModelPackage.JTEMPLATE_WRAPPER__BINDINGS:
				getBindings().clear();
				getBindings().addAll((Collection<? extends JTemplateBinding>)newValue);
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
			case JavaModelPackage.JTEMPLATE_WRAPPER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case JavaModelPackage.JTEMPLATE_WRAPPER__IS_EXTERNAL:
				setIsExternal(IS_EXTERNAL_EDEFAULT);
				return;
			case JavaModelPackage.JTEMPLATE_WRAPPER__BINDINGS:
				getBindings().clear();
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
			case JavaModelPackage.JTEMPLATE_WRAPPER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case JavaModelPackage.JTEMPLATE_WRAPPER__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case JavaModelPackage.JTEMPLATE_WRAPPER__IS_EXTERNAL:
				return isExternal != IS_EXTERNAL_EDEFAULT;
			case JavaModelPackage.JTEMPLATE_WRAPPER__BINDINGS:
				return bindings != null && !bindings.isEmpty();
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
				case JavaModelPackage.JTEMPLATE_WRAPPER__NAME: return JavaModelPackage.JNAMED_ELEMENT__NAME;
				case JavaModelPackage.JTEMPLATE_WRAPPER__QUALIFIED_NAME: return JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME;
				default: return -1;
			}
		}
		if (baseClass == JType.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JTEMPLATE_WRAPPER__IS_EXTERNAL: return JavaModelPackage.JTYPE__IS_EXTERNAL;
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
				case JavaModelPackage.JNAMED_ELEMENT__NAME: return JavaModelPackage.JTEMPLATE_WRAPPER__NAME;
				case JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME: return JavaModelPackage.JTEMPLATE_WRAPPER__QUALIFIED_NAME;
				default: return -1;
			}
		}
		if (baseClass == JType.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JTYPE__IS_EXTERNAL: return JavaModelPackage.JTEMPLATE_WRAPPER__IS_EXTERNAL;
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
		result.append(", isExternal: ");
		result.append(isExternal);
		result.append(')');
		return result.toString();
	}

} //JTemplateWrapperImpl
