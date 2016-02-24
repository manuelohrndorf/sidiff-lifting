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
import org.sidiff.javaast.model.JCodeFragment;
import org.sidiff.javaast.model.JIdentifiableElement;
import org.sidiff.javaast.model.JLocalVariable;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JCode Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeBlockImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeBlockImpl#isCompileable <em>Compileable</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeBlockImpl#getCodeFragments <em>Code Fragments</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeBlockImpl#getSubBlocks <em>Sub Blocks</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeBlockImpl#getDefinedVariables <em>Defined Variables</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JCodeBlockImpl extends JAdressableFragmentImpl implements JCodeBlock
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
	 * The cached value of the '{@link #getCodeFragments() <em>Code Fragments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getCodeFragments()
	 * @generated
	 * @ordered
	 */
  protected EList<JCodeFragment> codeFragments;

  /**
	 * The cached value of the '{@link #getSubBlocks() <em>Sub Blocks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSubBlocks()
	 * @generated
	 * @ordered
	 */
  protected EList<JCodeBlock> subBlocks;

  /**
	 * The cached value of the '{@link #getDefinedVariables() <em>Defined Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getDefinedVariables()
	 * @generated
	 * @ordered
	 */
  protected EList<JLocalVariable> definedVariables;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JCodeBlockImpl()
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
		return JavaModelPackage.Literals.JCODE_BLOCK;
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCODE_BLOCK__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCODE_BLOCK__COMPILEABLE, oldCompileable, compileable));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JCodeFragment> getCodeFragments()
  {
		if (codeFragments == null) {
			codeFragments = new EObjectContainmentEList<JCodeFragment>(JCodeFragment.class, this, JavaModelPackage.JCODE_BLOCK__CODE_FRAGMENTS);
		}
		return codeFragments;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JCodeBlock> getSubBlocks()
  {
		if (subBlocks == null) {
			subBlocks = new EObjectContainmentEList<JCodeBlock>(JCodeBlock.class, this, JavaModelPackage.JCODE_BLOCK__SUB_BLOCKS);
		}
		return subBlocks;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JLocalVariable> getDefinedVariables()
  {
		if (definedVariables == null) {
			definedVariables = new EObjectContainmentEList<JLocalVariable>(JLocalVariable.class, this, JavaModelPackage.JCODE_BLOCK__DEFINED_VARIABLES);
		}
		return definedVariables;
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
			case JavaModelPackage.JCODE_BLOCK__CODE_FRAGMENTS:
				return ((InternalEList<?>)getCodeFragments()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JCODE_BLOCK__SUB_BLOCKS:
				return ((InternalEList<?>)getSubBlocks()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JCODE_BLOCK__DEFINED_VARIABLES:
				return ((InternalEList<?>)getDefinedVariables()).basicRemove(otherEnd, msgs);
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
			case JavaModelPackage.JCODE_BLOCK__ID:
				return getId();
			case JavaModelPackage.JCODE_BLOCK__COMPILEABLE:
				return isCompileable();
			case JavaModelPackage.JCODE_BLOCK__CODE_FRAGMENTS:
				return getCodeFragments();
			case JavaModelPackage.JCODE_BLOCK__SUB_BLOCKS:
				return getSubBlocks();
			case JavaModelPackage.JCODE_BLOCK__DEFINED_VARIABLES:
				return getDefinedVariables();
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
			case JavaModelPackage.JCODE_BLOCK__ID:
				setId((String)newValue);
				return;
			case JavaModelPackage.JCODE_BLOCK__COMPILEABLE:
				setCompileable((Boolean)newValue);
				return;
			case JavaModelPackage.JCODE_BLOCK__CODE_FRAGMENTS:
				getCodeFragments().clear();
				getCodeFragments().addAll((Collection<? extends JCodeFragment>)newValue);
				return;
			case JavaModelPackage.JCODE_BLOCK__SUB_BLOCKS:
				getSubBlocks().clear();
				getSubBlocks().addAll((Collection<? extends JCodeBlock>)newValue);
				return;
			case JavaModelPackage.JCODE_BLOCK__DEFINED_VARIABLES:
				getDefinedVariables().clear();
				getDefinedVariables().addAll((Collection<? extends JLocalVariable>)newValue);
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
			case JavaModelPackage.JCODE_BLOCK__ID:
				setId(ID_EDEFAULT);
				return;
			case JavaModelPackage.JCODE_BLOCK__COMPILEABLE:
				setCompileable(COMPILEABLE_EDEFAULT);
				return;
			case JavaModelPackage.JCODE_BLOCK__CODE_FRAGMENTS:
				getCodeFragments().clear();
				return;
			case JavaModelPackage.JCODE_BLOCK__SUB_BLOCKS:
				getSubBlocks().clear();
				return;
			case JavaModelPackage.JCODE_BLOCK__DEFINED_VARIABLES:
				getDefinedVariables().clear();
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
			case JavaModelPackage.JCODE_BLOCK__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case JavaModelPackage.JCODE_BLOCK__COMPILEABLE:
				return compileable != COMPILEABLE_EDEFAULT;
			case JavaModelPackage.JCODE_BLOCK__CODE_FRAGMENTS:
				return codeFragments != null && !codeFragments.isEmpty();
			case JavaModelPackage.JCODE_BLOCK__SUB_BLOCKS:
				return subBlocks != null && !subBlocks.isEmpty();
			case JavaModelPackage.JCODE_BLOCK__DEFINED_VARIABLES:
				return definedVariables != null && !definedVariables.isEmpty();
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
		if (baseClass == JIdentifiableElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JCODE_BLOCK__ID: return JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID;
				case JavaModelPackage.JCODE_BLOCK__COMPILEABLE: return JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE;
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
		if (baseClass == JIdentifiableElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID: return JavaModelPackage.JCODE_BLOCK__ID;
				case JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE: return JavaModelPackage.JCODE_BLOCK__COMPILEABLE;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", compileable: ");
		result.append(compileable);
		result.append(')');
		return result.toString();
	}

} //JCodeBlockImpl
