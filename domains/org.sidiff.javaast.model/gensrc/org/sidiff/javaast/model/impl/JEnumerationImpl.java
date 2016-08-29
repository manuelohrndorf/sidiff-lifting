/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.javaast.model.JEnumeration;
import org.sidiff.javaast.model.JEnumerationLiteral;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JEnumeration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JEnumerationImpl#getLiterals <em>Literals</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JEnumerationImpl extends JClassImpl implements JEnumeration
{
  /**
	 * The cached value of the '{@link #getLiterals() <em>Literals</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getLiterals()
	 * @generated
	 * @ordered
	 */
  protected EList<JEnumerationLiteral> literals;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JEnumerationImpl()
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
		return JavaModelPackage.Literals.JENUMERATION;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JEnumerationLiteral> getLiterals()
  {
		if (literals == null) {
			literals = new EObjectContainmentEList<JEnumerationLiteral>(JEnumerationLiteral.class, this, JavaModelPackage.JENUMERATION__LITERALS);
		}
		return literals;
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
			case JavaModelPackage.JENUMERATION__LITERALS:
				return ((InternalEList<?>)getLiterals()).basicRemove(otherEnd, msgs);
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
			case JavaModelPackage.JENUMERATION__LITERALS:
				return getLiterals();
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
			case JavaModelPackage.JENUMERATION__LITERALS:
				getLiterals().clear();
				getLiterals().addAll((Collection<? extends JEnumerationLiteral>)newValue);
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
			case JavaModelPackage.JENUMERATION__LITERALS:
				getLiterals().clear();
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
			case JavaModelPackage.JENUMERATION__LITERALS:
				return literals != null && !literals.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //JEnumerationImpl
