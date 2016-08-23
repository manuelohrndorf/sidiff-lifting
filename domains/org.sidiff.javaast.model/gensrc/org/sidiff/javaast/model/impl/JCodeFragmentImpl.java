/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.sidiff.javaast.model.JCodeFragment;
import org.sidiff.javaast.model.JField;
import org.sidiff.javaast.model.JIdentifiableElement;
import org.sidiff.javaast.model.JLocalVariable;
import org.sidiff.javaast.model.JMethod;
import org.sidiff.javaast.model.JParameter;
import org.sidiff.javaast.model.JType;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JCode Fragment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeFragmentImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeFragmentImpl#isCompileable <em>Compileable</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeFragmentImpl#getUsedTypes <em>Used Types</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeFragmentImpl#getCalledMethods <em>Called Methods</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeFragmentImpl#getCode <em>Code</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeFragmentImpl#getUsedFields <em>Used Fields</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeFragmentImpl#getUsedParameters <em>Used Parameters</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JCodeFragmentImpl#getUsedLocalVariables <em>Used Local Variables</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JCodeFragmentImpl extends JAdressableFragmentImpl implements JCodeFragment
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
	 * The cached value of the '{@link #getUsedTypes() <em>Used Types</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getUsedTypes()
	 * @generated
	 * @ordered
	 */
  protected EList<JType> usedTypes;

  /**
	 * The cached value of the '{@link #getCalledMethods() <em>Called Methods</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getCalledMethods()
	 * @generated
	 * @ordered
	 */
  protected EList<JMethod> calledMethods;

  /**
	 * The default value of the '{@link #getCode() <em>Code</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getCode()
	 * @generated
	 * @ordered
	 */
  protected static final String CODE_EDEFAULT = null;

  /**
	 * The cached value of the '{@link #getCode() <em>Code</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getCode()
	 * @generated
	 * @ordered
	 */
  protected String code = CODE_EDEFAULT;

  /**
	 * The cached value of the '{@link #getUsedFields() <em>Used Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getUsedFields()
	 * @generated
	 * @ordered
	 */
  protected EList<JField> usedFields;

  /**
	 * The cached value of the '{@link #getUsedParameters() <em>Used Parameters</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getUsedParameters()
	 * @generated
	 * @ordered
	 */
  protected EList<JParameter> usedParameters;

  /**
	 * The cached value of the '{@link #getUsedLocalVariables() <em>Used Local Variables</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getUsedLocalVariables()
	 * @generated
	 * @ordered
	 */
  protected EList<JLocalVariable> usedLocalVariables;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JCodeFragmentImpl()
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
		return JavaModelPackage.Literals.JCODE_FRAGMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCODE_FRAGMENT__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCODE_FRAGMENT__COMPILEABLE, oldCompileable, compileable));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JType> getUsedTypes()
  {
		if (usedTypes == null) {
			usedTypes = new EObjectResolvingEList<JType>(JType.class, this, JavaModelPackage.JCODE_FRAGMENT__USED_TYPES);
		}
		return usedTypes;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JMethod> getCalledMethods()
  {
		if (calledMethods == null) {
			calledMethods = new EObjectResolvingEList<JMethod>(JMethod.class, this, JavaModelPackage.JCODE_FRAGMENT__CALLED_METHODS);
		}
		return calledMethods;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getCode()
  {
		return code;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setCode(String newCode)
  {
		String oldCode = code;
		code = newCode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCODE_FRAGMENT__CODE, oldCode, code));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JField> getUsedFields()
  {
		if (usedFields == null) {
			usedFields = new EObjectResolvingEList<JField>(JField.class, this, JavaModelPackage.JCODE_FRAGMENT__USED_FIELDS);
		}
		return usedFields;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JParameter> getUsedParameters()
  {
		if (usedParameters == null) {
			usedParameters = new EObjectResolvingEList<JParameter>(JParameter.class, this, JavaModelPackage.JCODE_FRAGMENT__USED_PARAMETERS);
		}
		return usedParameters;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JLocalVariable> getUsedLocalVariables()
  {
		if (usedLocalVariables == null) {
			usedLocalVariables = new EObjectResolvingEList<JLocalVariable>(JLocalVariable.class, this, JavaModelPackage.JCODE_FRAGMENT__USED_LOCAL_VARIABLES);
		}
		return usedLocalVariables;
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
			case JavaModelPackage.JCODE_FRAGMENT__ID:
				return getId();
			case JavaModelPackage.JCODE_FRAGMENT__COMPILEABLE:
				return isCompileable();
			case JavaModelPackage.JCODE_FRAGMENT__USED_TYPES:
				return getUsedTypes();
			case JavaModelPackage.JCODE_FRAGMENT__CALLED_METHODS:
				return getCalledMethods();
			case JavaModelPackage.JCODE_FRAGMENT__CODE:
				return getCode();
			case JavaModelPackage.JCODE_FRAGMENT__USED_FIELDS:
				return getUsedFields();
			case JavaModelPackage.JCODE_FRAGMENT__USED_PARAMETERS:
				return getUsedParameters();
			case JavaModelPackage.JCODE_FRAGMENT__USED_LOCAL_VARIABLES:
				return getUsedLocalVariables();
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
			case JavaModelPackage.JCODE_FRAGMENT__ID:
				setId((String)newValue);
				return;
			case JavaModelPackage.JCODE_FRAGMENT__COMPILEABLE:
				setCompileable((Boolean)newValue);
				return;
			case JavaModelPackage.JCODE_FRAGMENT__USED_TYPES:
				getUsedTypes().clear();
				getUsedTypes().addAll((Collection<? extends JType>)newValue);
				return;
			case JavaModelPackage.JCODE_FRAGMENT__CALLED_METHODS:
				getCalledMethods().clear();
				getCalledMethods().addAll((Collection<? extends JMethod>)newValue);
				return;
			case JavaModelPackage.JCODE_FRAGMENT__CODE:
				setCode((String)newValue);
				return;
			case JavaModelPackage.JCODE_FRAGMENT__USED_FIELDS:
				getUsedFields().clear();
				getUsedFields().addAll((Collection<? extends JField>)newValue);
				return;
			case JavaModelPackage.JCODE_FRAGMENT__USED_PARAMETERS:
				getUsedParameters().clear();
				getUsedParameters().addAll((Collection<? extends JParameter>)newValue);
				return;
			case JavaModelPackage.JCODE_FRAGMENT__USED_LOCAL_VARIABLES:
				getUsedLocalVariables().clear();
				getUsedLocalVariables().addAll((Collection<? extends JLocalVariable>)newValue);
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
			case JavaModelPackage.JCODE_FRAGMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case JavaModelPackage.JCODE_FRAGMENT__COMPILEABLE:
				setCompileable(COMPILEABLE_EDEFAULT);
				return;
			case JavaModelPackage.JCODE_FRAGMENT__USED_TYPES:
				getUsedTypes().clear();
				return;
			case JavaModelPackage.JCODE_FRAGMENT__CALLED_METHODS:
				getCalledMethods().clear();
				return;
			case JavaModelPackage.JCODE_FRAGMENT__CODE:
				setCode(CODE_EDEFAULT);
				return;
			case JavaModelPackage.JCODE_FRAGMENT__USED_FIELDS:
				getUsedFields().clear();
				return;
			case JavaModelPackage.JCODE_FRAGMENT__USED_PARAMETERS:
				getUsedParameters().clear();
				return;
			case JavaModelPackage.JCODE_FRAGMENT__USED_LOCAL_VARIABLES:
				getUsedLocalVariables().clear();
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
			case JavaModelPackage.JCODE_FRAGMENT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case JavaModelPackage.JCODE_FRAGMENT__COMPILEABLE:
				return compileable != COMPILEABLE_EDEFAULT;
			case JavaModelPackage.JCODE_FRAGMENT__USED_TYPES:
				return usedTypes != null && !usedTypes.isEmpty();
			case JavaModelPackage.JCODE_FRAGMENT__CALLED_METHODS:
				return calledMethods != null && !calledMethods.isEmpty();
			case JavaModelPackage.JCODE_FRAGMENT__CODE:
				return CODE_EDEFAULT == null ? code != null : !CODE_EDEFAULT.equals(code);
			case JavaModelPackage.JCODE_FRAGMENT__USED_FIELDS:
				return usedFields != null && !usedFields.isEmpty();
			case JavaModelPackage.JCODE_FRAGMENT__USED_PARAMETERS:
				return usedParameters != null && !usedParameters.isEmpty();
			case JavaModelPackage.JCODE_FRAGMENT__USED_LOCAL_VARIABLES:
				return usedLocalVariables != null && !usedLocalVariables.isEmpty();
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
				case JavaModelPackage.JCODE_FRAGMENT__ID: return JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID;
				case JavaModelPackage.JCODE_FRAGMENT__COMPILEABLE: return JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE;
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
				case JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID: return JavaModelPackage.JCODE_FRAGMENT__ID;
				case JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE: return JavaModelPackage.JCODE_FRAGMENT__COMPILEABLE;
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
		result.append(", code: ");
		result.append(code);
		result.append(')');
		return result.toString();
	}

} //JCodeFragmentImpl
