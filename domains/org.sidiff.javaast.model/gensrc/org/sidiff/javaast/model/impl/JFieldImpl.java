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

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.javaast.model.JCodeBlock;
import org.sidiff.javaast.model.JDocumentableElement;
import org.sidiff.javaast.model.JField;
import org.sidiff.javaast.model.JMethod;
import org.sidiff.javaast.model.JNamedElement;
import org.sidiff.javaast.model.JType;
import org.sidiff.javaast.model.JTypedElement;
import org.sidiff.javaast.model.JVisibilityKind;
import org.sidiff.javaast.model.JVisibleElement;
import org.sidiff.javaast.model.JavaDoc;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JField</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JFieldImpl#getJavaDoc <em>Java Doc</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JFieldImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JFieldImpl#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JFieldImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JFieldImpl#getVisibilityKind <em>Visibility Kind</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JFieldImpl#isIsFinal <em>Is Final</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JFieldImpl#isIsStatic <em>Is Static</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JFieldImpl#getAccessedBy <em>Accessed By</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JFieldImpl#isIsTransient <em>Is Transient</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JFieldImpl#getInitialization <em>Initialization</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JFieldImpl extends JIdentifiableElementImpl implements JField
{
  /**
	 * The cached value of the '{@link #getJavaDoc() <em>Java Doc</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getJavaDoc()
	 * @generated
	 * @ordered
	 */
  protected JavaDoc javaDoc;

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
	 * The default value of the '{@link #getVisibilityKind() <em>Visibility Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getVisibilityKind()
	 * @generated
	 * @ordered
	 */
  protected static final JVisibilityKind VISIBILITY_KIND_EDEFAULT = JVisibilityKind.PUBLIC;

  /**
	 * The cached value of the '{@link #getVisibilityKind() <em>Visibility Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getVisibilityKind()
	 * @generated
	 * @ordered
	 */
  protected JVisibilityKind visibilityKind = VISIBILITY_KIND_EDEFAULT;

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
	 * The default value of the '{@link #isIsStatic() <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsStatic()
	 * @generated
	 * @ordered
	 */
  protected static final boolean IS_STATIC_EDEFAULT = false;

  /**
	 * The cached value of the '{@link #isIsStatic() <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsStatic()
	 * @generated
	 * @ordered
	 */
  protected boolean isStatic = IS_STATIC_EDEFAULT;

  /**
	 * The cached value of the '{@link #getAccessedBy() <em>Accessed By</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getAccessedBy()
	 * @generated
	 * @ordered
	 */
  protected EList<JMethod> accessedBy;

  /**
	 * The default value of the '{@link #isIsTransient() <em>Is Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsTransient()
	 * @generated
	 * @ordered
	 */
  protected static final boolean IS_TRANSIENT_EDEFAULT = false;

  /**
	 * The cached value of the '{@link #isIsTransient() <em>Is Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsTransient()
	 * @generated
	 * @ordered
	 */
  protected boolean isTransient = IS_TRANSIENT_EDEFAULT;

  /**
	 * The cached value of the '{@link #getInitialization() <em>Initialization</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getInitialization()
	 * @generated
	 * @ordered
	 */
  protected JCodeBlock initialization;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JFieldImpl()
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
		return JavaModelPackage.Literals.JFIELD;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JavaDoc getJavaDoc()
  {
		return javaDoc;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetJavaDoc(JavaDoc newJavaDoc, NotificationChain msgs)
  {
		JavaDoc oldJavaDoc = javaDoc;
		javaDoc = newJavaDoc;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JavaModelPackage.JFIELD__JAVA_DOC, oldJavaDoc, newJavaDoc);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setJavaDoc(JavaDoc newJavaDoc)
  {
		if (newJavaDoc != javaDoc) {
			NotificationChain msgs = null;
			if (javaDoc != null)
				msgs = ((InternalEObject)javaDoc).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JFIELD__JAVA_DOC, null, msgs);
			if (newJavaDoc != null)
				msgs = ((InternalEObject)newJavaDoc).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JFIELD__JAVA_DOC, null, msgs);
			msgs = basicSetJavaDoc(newJavaDoc, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JFIELD__JAVA_DOC, newJavaDoc, newJavaDoc));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JFIELD__NAME, oldName, name));
	}

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JavaModelPackage.JFIELD__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JFIELD__TYPE, oldType, type));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JVisibilityKind getVisibilityKind()
  {
		return visibilityKind;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setVisibilityKind(JVisibilityKind newVisibilityKind)
  {
		JVisibilityKind oldVisibilityKind = visibilityKind;
		visibilityKind = newVisibilityKind == null ? VISIBILITY_KIND_EDEFAULT : newVisibilityKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JFIELD__VISIBILITY_KIND, oldVisibilityKind, visibilityKind));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JFIELD__IS_FINAL, oldIsFinal, isFinal));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public boolean isIsStatic()
  {
		return isStatic;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setIsStatic(boolean newIsStatic)
  {
		boolean oldIsStatic = isStatic;
		isStatic = newIsStatic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JFIELD__IS_STATIC, oldIsStatic, isStatic));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JMethod> getAccessedBy()
  {
		if (accessedBy == null) {
			accessedBy = new EObjectWithInverseResolvingEList.ManyInverse<JMethod>(JMethod.class, this, JavaModelPackage.JFIELD__ACCESSED_BY, JavaModelPackage.JMETHOD__ACCESSES);
		}
		return accessedBy;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public boolean isIsTransient()
  {
		return isTransient;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setIsTransient(boolean newIsTransient)
  {
		boolean oldIsTransient = isTransient;
		isTransient = newIsTransient;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JFIELD__IS_TRANSIENT, oldIsTransient, isTransient));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JCodeBlock getInitialization()
  {
		return initialization;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetInitialization(JCodeBlock newInitialization, NotificationChain msgs)
  {
		JCodeBlock oldInitialization = initialization;
		initialization = newInitialization;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JavaModelPackage.JFIELD__INITIALIZATION, oldInitialization, newInitialization);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setInitialization(JCodeBlock newInitialization)
  {
		if (newInitialization != initialization) {
			NotificationChain msgs = null;
			if (initialization != null)
				msgs = ((InternalEObject)initialization).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JFIELD__INITIALIZATION, null, msgs);
			if (newInitialization != null)
				msgs = ((InternalEObject)newInitialization).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JFIELD__INITIALIZATION, null, msgs);
			msgs = basicSetInitialization(newInitialization, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JFIELD__INITIALIZATION, newInitialization, newInitialization));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @SuppressWarnings("unchecked")
  @Override
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
		switch (featureID) {
			case JavaModelPackage.JFIELD__ACCESSED_BY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAccessedBy()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
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
			case JavaModelPackage.JFIELD__JAVA_DOC:
				return basicSetJavaDoc(null, msgs);
			case JavaModelPackage.JFIELD__ACCESSED_BY:
				return ((InternalEList<?>)getAccessedBy()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JFIELD__INITIALIZATION:
				return basicSetInitialization(null, msgs);
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
			case JavaModelPackage.JFIELD__JAVA_DOC:
				return getJavaDoc();
			case JavaModelPackage.JFIELD__NAME:
				return getName();
			case JavaModelPackage.JFIELD__QUALIFIED_NAME:
				return getQualifiedName();
			case JavaModelPackage.JFIELD__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case JavaModelPackage.JFIELD__VISIBILITY_KIND:
				return getVisibilityKind();
			case JavaModelPackage.JFIELD__IS_FINAL:
				return isIsFinal();
			case JavaModelPackage.JFIELD__IS_STATIC:
				return isIsStatic();
			case JavaModelPackage.JFIELD__ACCESSED_BY:
				return getAccessedBy();
			case JavaModelPackage.JFIELD__IS_TRANSIENT:
				return isIsTransient();
			case JavaModelPackage.JFIELD__INITIALIZATION:
				return getInitialization();
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
			case JavaModelPackage.JFIELD__JAVA_DOC:
				setJavaDoc((JavaDoc)newValue);
				return;
			case JavaModelPackage.JFIELD__NAME:
				setName((String)newValue);
				return;
			case JavaModelPackage.JFIELD__TYPE:
				setType((JType)newValue);
				return;
			case JavaModelPackage.JFIELD__VISIBILITY_KIND:
				setVisibilityKind((JVisibilityKind)newValue);
				return;
			case JavaModelPackage.JFIELD__IS_FINAL:
				setIsFinal((Boolean)newValue);
				return;
			case JavaModelPackage.JFIELD__IS_STATIC:
				setIsStatic((Boolean)newValue);
				return;
			case JavaModelPackage.JFIELD__ACCESSED_BY:
				getAccessedBy().clear();
				getAccessedBy().addAll((Collection<? extends JMethod>)newValue);
				return;
			case JavaModelPackage.JFIELD__IS_TRANSIENT:
				setIsTransient((Boolean)newValue);
				return;
			case JavaModelPackage.JFIELD__INITIALIZATION:
				setInitialization((JCodeBlock)newValue);
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
			case JavaModelPackage.JFIELD__JAVA_DOC:
				setJavaDoc((JavaDoc)null);
				return;
			case JavaModelPackage.JFIELD__NAME:
				setName(NAME_EDEFAULT);
				return;
			case JavaModelPackage.JFIELD__TYPE:
				setType((JType)null);
				return;
			case JavaModelPackage.JFIELD__VISIBILITY_KIND:
				setVisibilityKind(VISIBILITY_KIND_EDEFAULT);
				return;
			case JavaModelPackage.JFIELD__IS_FINAL:
				setIsFinal(IS_FINAL_EDEFAULT);
				return;
			case JavaModelPackage.JFIELD__IS_STATIC:
				setIsStatic(IS_STATIC_EDEFAULT);
				return;
			case JavaModelPackage.JFIELD__ACCESSED_BY:
				getAccessedBy().clear();
				return;
			case JavaModelPackage.JFIELD__IS_TRANSIENT:
				setIsTransient(IS_TRANSIENT_EDEFAULT);
				return;
			case JavaModelPackage.JFIELD__INITIALIZATION:
				setInitialization((JCodeBlock)null);
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
			case JavaModelPackage.JFIELD__JAVA_DOC:
				return javaDoc != null;
			case JavaModelPackage.JFIELD__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case JavaModelPackage.JFIELD__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case JavaModelPackage.JFIELD__TYPE:
				return type != null;
			case JavaModelPackage.JFIELD__VISIBILITY_KIND:
				return visibilityKind != VISIBILITY_KIND_EDEFAULT;
			case JavaModelPackage.JFIELD__IS_FINAL:
				return isFinal != IS_FINAL_EDEFAULT;
			case JavaModelPackage.JFIELD__IS_STATIC:
				return isStatic != IS_STATIC_EDEFAULT;
			case JavaModelPackage.JFIELD__ACCESSED_BY:
				return accessedBy != null && !accessedBy.isEmpty();
			case JavaModelPackage.JFIELD__IS_TRANSIENT:
				return isTransient != IS_TRANSIENT_EDEFAULT;
			case JavaModelPackage.JFIELD__INITIALIZATION:
				return initialization != null;
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
		if (baseClass == JDocumentableElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JFIELD__JAVA_DOC: return JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC;
				default: return -1;
			}
		}
		if (baseClass == JNamedElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JFIELD__NAME: return JavaModelPackage.JNAMED_ELEMENT__NAME;
				case JavaModelPackage.JFIELD__QUALIFIED_NAME: return JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME;
				default: return -1;
			}
		}
		if (baseClass == JTypedElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JFIELD__TYPE: return JavaModelPackage.JTYPED_ELEMENT__TYPE;
				default: return -1;
			}
		}
		if (baseClass == JVisibleElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JFIELD__VISIBILITY_KIND: return JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND;
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
		if (baseClass == JDocumentableElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC: return JavaModelPackage.JFIELD__JAVA_DOC;
				default: return -1;
			}
		}
		if (baseClass == JNamedElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JNAMED_ELEMENT__NAME: return JavaModelPackage.JFIELD__NAME;
				case JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME: return JavaModelPackage.JFIELD__QUALIFIED_NAME;
				default: return -1;
			}
		}
		if (baseClass == JTypedElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JTYPED_ELEMENT__TYPE: return JavaModelPackage.JFIELD__TYPE;
				default: return -1;
			}
		}
		if (baseClass == JVisibleElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND: return JavaModelPackage.JFIELD__VISIBILITY_KIND;
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
		result.append(", visibilityKind: ");
		result.append(visibilityKind);
		result.append(", isFinal: ");
		result.append(isFinal);
		result.append(", isStatic: ");
		result.append(isStatic);
		result.append(", isTransient: ");
		result.append(isTransient);
		result.append(')');
		return result.toString();
	}

} //JFieldImpl
