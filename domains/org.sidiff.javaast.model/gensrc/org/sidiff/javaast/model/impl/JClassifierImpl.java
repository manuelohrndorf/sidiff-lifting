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

import org.sidiff.javaast.model.JClassifier;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JClassifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassifierImpl#getInnerClassifiers <em>Inner Classifiers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class JClassifierImpl extends JNamedElementImpl implements JClassifier
{
  /**
	 * The cached value of the '{@link #getInnerClassifiers() <em>Inner Classifiers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getInnerClassifiers()
	 * @generated
	 * @ordered
	 */
  protected EList<JClassifier> innerClassifiers;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JClassifierImpl()
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
		return JavaModelPackage.Literals.JCLASSIFIER;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JClassifier> getInnerClassifiers()
  {
		if (innerClassifiers == null) {
			innerClassifiers = new EObjectContainmentEList<JClassifier>(JClassifier.class, this, JavaModelPackage.JCLASSIFIER__INNER_CLASSIFIERS);
		}
		return innerClassifiers;
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
			case JavaModelPackage.JCLASSIFIER__INNER_CLASSIFIERS:
				return ((InternalEList<?>)getInnerClassifiers()).basicRemove(otherEnd, msgs);
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
			case JavaModelPackage.JCLASSIFIER__INNER_CLASSIFIERS:
				return getInnerClassifiers();
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
			case JavaModelPackage.JCLASSIFIER__INNER_CLASSIFIERS:
				getInnerClassifiers().clear();
				getInnerClassifiers().addAll((Collection<? extends JClassifier>)newValue);
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
			case JavaModelPackage.JCLASSIFIER__INNER_CLASSIFIERS:
				getInnerClassifiers().clear();
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
			case JavaModelPackage.JCLASSIFIER__INNER_CLASSIFIERS:
				return innerClassifiers != null && !innerClassifiers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //JClassifierImpl
