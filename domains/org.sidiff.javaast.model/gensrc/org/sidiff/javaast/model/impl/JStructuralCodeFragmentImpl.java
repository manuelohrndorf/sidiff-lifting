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

import org.sidiff.javaast.model.JCodeBlock;
import org.sidiff.javaast.model.JStructuralCodeFragment;
import org.sidiff.javaast.model.JavaModelPackage;
import org.sidiff.javaast.model.StructuralFragmentType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JStructural Code Fragment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JStructuralCodeFragmentImpl#getFragmentType <em>Fragment Type</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JStructuralCodeFragmentImpl#getCodeBlocks <em>Code Blocks</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JStructuralCodeFragmentImpl extends JCodeFragmentImpl implements JStructuralCodeFragment
{
  /**
	 * The default value of the '{@link #getFragmentType() <em>Fragment Type</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getFragmentType()
	 * @generated
	 * @ordered
	 */
  protected static final StructuralFragmentType FRAGMENT_TYPE_EDEFAULT = StructuralFragmentType.IF;

  /**
	 * The cached value of the '{@link #getFragmentType() <em>Fragment Type</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getFragmentType()
	 * @generated
	 * @ordered
	 */
  protected StructuralFragmentType fragmentType = FRAGMENT_TYPE_EDEFAULT;

  /**
	 * The cached value of the '{@link #getCodeBlocks() <em>Code Blocks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getCodeBlocks()
	 * @generated
	 * @ordered
	 */
  protected EList<JCodeBlock> codeBlocks;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JStructuralCodeFragmentImpl()
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
		return JavaModelPackage.Literals.JSTRUCTURAL_CODE_FRAGMENT;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public StructuralFragmentType getFragmentType()
  {
		return fragmentType;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setFragmentType(StructuralFragmentType newFragmentType)
  {
		StructuralFragmentType oldFragmentType = fragmentType;
		fragmentType = newFragmentType == null ? FRAGMENT_TYPE_EDEFAULT : newFragmentType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__FRAGMENT_TYPE, oldFragmentType, fragmentType));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JCodeBlock> getCodeBlocks()
  {
		if (codeBlocks == null) {
			codeBlocks = new EObjectContainmentEList<JCodeBlock>(JCodeBlock.class, this, JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__CODE_BLOCKS);
		}
		return codeBlocks;
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
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__CODE_BLOCKS:
				return ((InternalEList<?>)getCodeBlocks()).basicRemove(otherEnd, msgs);
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
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__FRAGMENT_TYPE:
				return getFragmentType();
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__CODE_BLOCKS:
				return getCodeBlocks();
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
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__FRAGMENT_TYPE:
				setFragmentType((StructuralFragmentType)newValue);
				return;
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__CODE_BLOCKS:
				getCodeBlocks().clear();
				getCodeBlocks().addAll((Collection<? extends JCodeBlock>)newValue);
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
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__FRAGMENT_TYPE:
				setFragmentType(FRAGMENT_TYPE_EDEFAULT);
				return;
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__CODE_BLOCKS:
				getCodeBlocks().clear();
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
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__FRAGMENT_TYPE:
				return fragmentType != FRAGMENT_TYPE_EDEFAULT;
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT__CODE_BLOCKS:
				return codeBlocks != null && !codeBlocks.isEmpty();
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
		result.append(" (fragmentType: ");
		result.append(fragmentType);
		result.append(')');
		return result.toString();
	}

} //JStructuralCodeFragmentImpl
