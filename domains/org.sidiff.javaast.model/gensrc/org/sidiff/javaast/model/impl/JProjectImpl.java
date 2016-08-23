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
import org.sidiff.javaast.model.JPackage;
import org.sidiff.javaast.model.JProject;
import org.sidiff.javaast.model.JType;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JProject</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JProjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JProjectImpl#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JProjectImpl#getPackages <em>Packages</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JProjectImpl#getSimpleTypes <em>Simple Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JProjectImpl extends JIdentifiableElementImpl implements JProject
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
	 * The cached value of the '{@link #getPackages() <em>Packages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getPackages()
	 * @generated
	 * @ordered
	 */
  protected EList<JPackage> packages;

  /**
	 * The cached value of the '{@link #getSimpleTypes() <em>Simple Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSimpleTypes()
	 * @generated
	 * @ordered
	 */
  protected EList<JType> simpleTypes;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JProjectImpl()
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
		return JavaModelPackage.Literals.JPROJECT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JPROJECT__NAME, oldName, name));
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
  public EList<JPackage> getPackages()
  {
		if (packages == null) {
			packages = new EObjectContainmentEList<JPackage>(JPackage.class, this, JavaModelPackage.JPROJECT__PACKAGES);
		}
		return packages;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JType> getSimpleTypes()
  {
		if (simpleTypes == null) {
			simpleTypes = new EObjectContainmentEList<JType>(JType.class, this, JavaModelPackage.JPROJECT__SIMPLE_TYPES);
		}
		return simpleTypes;
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
			case JavaModelPackage.JPROJECT__PACKAGES:
				return ((InternalEList<?>)getPackages()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JPROJECT__SIMPLE_TYPES:
				return ((InternalEList<?>)getSimpleTypes()).basicRemove(otherEnd, msgs);
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
			case JavaModelPackage.JPROJECT__NAME:
				return getName();
			case JavaModelPackage.JPROJECT__QUALIFIED_NAME:
				return getQualifiedName();
			case JavaModelPackage.JPROJECT__PACKAGES:
				return getPackages();
			case JavaModelPackage.JPROJECT__SIMPLE_TYPES:
				return getSimpleTypes();
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
			case JavaModelPackage.JPROJECT__NAME:
				setName((String)newValue);
				return;
			case JavaModelPackage.JPROJECT__PACKAGES:
				getPackages().clear();
				getPackages().addAll((Collection<? extends JPackage>)newValue);
				return;
			case JavaModelPackage.JPROJECT__SIMPLE_TYPES:
				getSimpleTypes().clear();
				getSimpleTypes().addAll((Collection<? extends JType>)newValue);
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
			case JavaModelPackage.JPROJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case JavaModelPackage.JPROJECT__PACKAGES:
				getPackages().clear();
				return;
			case JavaModelPackage.JPROJECT__SIMPLE_TYPES:
				getSimpleTypes().clear();
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
			case JavaModelPackage.JPROJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case JavaModelPackage.JPROJECT__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case JavaModelPackage.JPROJECT__PACKAGES:
				return packages != null && !packages.isEmpty();
			case JavaModelPackage.JPROJECT__SIMPLE_TYPES:
				return simpleTypes != null && !simpleTypes.isEmpty();
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
				case JavaModelPackage.JPROJECT__NAME: return JavaModelPackage.JNAMED_ELEMENT__NAME;
				case JavaModelPackage.JPROJECT__QUALIFIED_NAME: return JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME;
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
				case JavaModelPackage.JNAMED_ELEMENT__NAME: return JavaModelPackage.JPROJECT__NAME;
				case JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME: return JavaModelPackage.JPROJECT__QUALIFIED_NAME;
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

} //JProjectImpl
