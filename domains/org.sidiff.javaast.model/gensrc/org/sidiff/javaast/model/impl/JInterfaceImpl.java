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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.javaast.model.JClass;
import org.sidiff.javaast.model.JConstant;
import org.sidiff.javaast.model.JDocumentableElement;
import org.sidiff.javaast.model.JGenericType;
import org.sidiff.javaast.model.JIdentifiableElement;
import org.sidiff.javaast.model.JInterface;
import org.sidiff.javaast.model.JMethod;
import org.sidiff.javaast.model.JTemplate;
import org.sidiff.javaast.model.JType;
import org.sidiff.javaast.model.JVisibilityKind;
import org.sidiff.javaast.model.JVisibleElement;
import org.sidiff.javaast.model.JavaDoc;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JInterface</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#isCompileable <em>Compileable</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#getJavaDoc <em>Java Doc</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#getGenericTypes <em>Generic Types</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#isIsExternal <em>Is External</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#getVisibilityKind <em>Visibility Kind</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#getConstants <em>Constants</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#getMethodSignatures <em>Method Signatures</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#getSuperInterfaces <em>Super Interfaces</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#getSubInterfaces <em>Sub Interfaces</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JInterfaceImpl#getImplementingClasses <em>Implementing Classes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JInterfaceImpl extends JClassifierImpl implements JInterface
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
	 * The cached value of the '{@link #getJavaDoc() <em>Java Doc</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getJavaDoc()
	 * @generated
	 * @ordered
	 */
  protected JavaDoc javaDoc;

  /**
	 * The cached value of the '{@link #getGenericTypes() <em>Generic Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getGenericTypes()
	 * @generated
	 * @ordered
	 */
  protected EList<JGenericType> genericTypes;

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
	 * The cached value of the '{@link #getConstants() <em>Constants</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getConstants()
	 * @generated
	 * @ordered
	 */
  protected EList<JConstant> constants;

  /**
	 * The cached value of the '{@link #getMethodSignatures() <em>Method Signatures</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getMethodSignatures()
	 * @generated
	 * @ordered
	 */
  protected EList<JMethod> methodSignatures;

  /**
	 * The cached value of the '{@link #getSuperInterfaces() <em>Super Interfaces</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSuperInterfaces()
	 * @generated
	 * @ordered
	 */
  protected EList<JInterface> superInterfaces;

  /**
	 * The cached value of the '{@link #getSubInterfaces() <em>Sub Interfaces</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSubInterfaces()
	 * @generated
	 * @ordered
	 */
  protected EList<JInterface> subInterfaces;

  /**
	 * The cached value of the '{@link #getImplementingClasses() <em>Implementing Classes</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getImplementingClasses()
	 * @generated
	 * @ordered
	 */
  protected EList<JClass> implementingClasses;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JInterfaceImpl()
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
		return JavaModelPackage.Literals.JINTERFACE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JINTERFACE__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JINTERFACE__COMPILEABLE, oldCompileable, compileable));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JavaModelPackage.JINTERFACE__JAVA_DOC, oldJavaDoc, newJavaDoc);
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
				msgs = ((InternalEObject)javaDoc).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JINTERFACE__JAVA_DOC, null, msgs);
			if (newJavaDoc != null)
				msgs = ((InternalEObject)newJavaDoc).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JINTERFACE__JAVA_DOC, null, msgs);
			msgs = basicSetJavaDoc(newJavaDoc, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JINTERFACE__JAVA_DOC, newJavaDoc, newJavaDoc));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JGenericType> getGenericTypes()
  {
		if (genericTypes == null) {
			genericTypes = new EObjectContainmentEList<JGenericType>(JGenericType.class, this, JavaModelPackage.JINTERFACE__GENERIC_TYPES);
		}
		return genericTypes;
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JINTERFACE__IS_EXTERNAL, oldIsExternal, isExternal));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JINTERFACE__VISIBILITY_KIND, oldVisibilityKind, visibilityKind));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JConstant> getConstants()
  {
		if (constants == null) {
			constants = new EObjectContainmentEList<JConstant>(JConstant.class, this, JavaModelPackage.JINTERFACE__CONSTANTS);
		}
		return constants;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JMethod> getMethodSignatures()
  {
		if (methodSignatures == null) {
			methodSignatures = new EObjectContainmentEList<JMethod>(JMethod.class, this, JavaModelPackage.JINTERFACE__METHOD_SIGNATURES);
		}
		return methodSignatures;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JInterface> getSuperInterfaces()
  {
		if (superInterfaces == null) {
			superInterfaces = new EObjectWithInverseResolvingEList.ManyInverse<JInterface>(JInterface.class, this, JavaModelPackage.JINTERFACE__SUPER_INTERFACES, JavaModelPackage.JINTERFACE__SUB_INTERFACES);
		}
		return superInterfaces;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JInterface> getSubInterfaces()
  {
		if (subInterfaces == null) {
			subInterfaces = new EObjectWithInverseResolvingEList.ManyInverse<JInterface>(JInterface.class, this, JavaModelPackage.JINTERFACE__SUB_INTERFACES, JavaModelPackage.JINTERFACE__SUPER_INTERFACES);
		}
		return subInterfaces;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JClass> getImplementingClasses()
  {
		if (implementingClasses == null) {
			implementingClasses = new EObjectWithInverseResolvingEList.ManyInverse<JClass>(JClass.class, this, JavaModelPackage.JINTERFACE__IMPLEMENTING_CLASSES, JavaModelPackage.JCLASS__IMPLEMENTED_INTERFACES);
		}
		return implementingClasses;
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
			case JavaModelPackage.JINTERFACE__SUPER_INTERFACES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSuperInterfaces()).basicAdd(otherEnd, msgs);
			case JavaModelPackage.JINTERFACE__SUB_INTERFACES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSubInterfaces()).basicAdd(otherEnd, msgs);
			case JavaModelPackage.JINTERFACE__IMPLEMENTING_CLASSES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getImplementingClasses()).basicAdd(otherEnd, msgs);
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
			case JavaModelPackage.JINTERFACE__JAVA_DOC:
				return basicSetJavaDoc(null, msgs);
			case JavaModelPackage.JINTERFACE__GENERIC_TYPES:
				return ((InternalEList<?>)getGenericTypes()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JINTERFACE__CONSTANTS:
				return ((InternalEList<?>)getConstants()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JINTERFACE__METHOD_SIGNATURES:
				return ((InternalEList<?>)getMethodSignatures()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JINTERFACE__SUPER_INTERFACES:
				return ((InternalEList<?>)getSuperInterfaces()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JINTERFACE__SUB_INTERFACES:
				return ((InternalEList<?>)getSubInterfaces()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JINTERFACE__IMPLEMENTING_CLASSES:
				return ((InternalEList<?>)getImplementingClasses()).basicRemove(otherEnd, msgs);
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
			case JavaModelPackage.JINTERFACE__ID:
				return getId();
			case JavaModelPackage.JINTERFACE__COMPILEABLE:
				return isCompileable();
			case JavaModelPackage.JINTERFACE__JAVA_DOC:
				return getJavaDoc();
			case JavaModelPackage.JINTERFACE__GENERIC_TYPES:
				return getGenericTypes();
			case JavaModelPackage.JINTERFACE__IS_EXTERNAL:
				return isIsExternal();
			case JavaModelPackage.JINTERFACE__VISIBILITY_KIND:
				return getVisibilityKind();
			case JavaModelPackage.JINTERFACE__CONSTANTS:
				return getConstants();
			case JavaModelPackage.JINTERFACE__METHOD_SIGNATURES:
				return getMethodSignatures();
			case JavaModelPackage.JINTERFACE__SUPER_INTERFACES:
				return getSuperInterfaces();
			case JavaModelPackage.JINTERFACE__SUB_INTERFACES:
				return getSubInterfaces();
			case JavaModelPackage.JINTERFACE__IMPLEMENTING_CLASSES:
				return getImplementingClasses();
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
			case JavaModelPackage.JINTERFACE__ID:
				setId((String)newValue);
				return;
			case JavaModelPackage.JINTERFACE__COMPILEABLE:
				setCompileable((Boolean)newValue);
				return;
			case JavaModelPackage.JINTERFACE__JAVA_DOC:
				setJavaDoc((JavaDoc)newValue);
				return;
			case JavaModelPackage.JINTERFACE__GENERIC_TYPES:
				getGenericTypes().clear();
				getGenericTypes().addAll((Collection<? extends JGenericType>)newValue);
				return;
			case JavaModelPackage.JINTERFACE__IS_EXTERNAL:
				setIsExternal((Boolean)newValue);
				return;
			case JavaModelPackage.JINTERFACE__VISIBILITY_KIND:
				setVisibilityKind((JVisibilityKind)newValue);
				return;
			case JavaModelPackage.JINTERFACE__CONSTANTS:
				getConstants().clear();
				getConstants().addAll((Collection<? extends JConstant>)newValue);
				return;
			case JavaModelPackage.JINTERFACE__METHOD_SIGNATURES:
				getMethodSignatures().clear();
				getMethodSignatures().addAll((Collection<? extends JMethod>)newValue);
				return;
			case JavaModelPackage.JINTERFACE__SUPER_INTERFACES:
				getSuperInterfaces().clear();
				getSuperInterfaces().addAll((Collection<? extends JInterface>)newValue);
				return;
			case JavaModelPackage.JINTERFACE__SUB_INTERFACES:
				getSubInterfaces().clear();
				getSubInterfaces().addAll((Collection<? extends JInterface>)newValue);
				return;
			case JavaModelPackage.JINTERFACE__IMPLEMENTING_CLASSES:
				getImplementingClasses().clear();
				getImplementingClasses().addAll((Collection<? extends JClass>)newValue);
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
			case JavaModelPackage.JINTERFACE__ID:
				setId(ID_EDEFAULT);
				return;
			case JavaModelPackage.JINTERFACE__COMPILEABLE:
				setCompileable(COMPILEABLE_EDEFAULT);
				return;
			case JavaModelPackage.JINTERFACE__JAVA_DOC:
				setJavaDoc((JavaDoc)null);
				return;
			case JavaModelPackage.JINTERFACE__GENERIC_TYPES:
				getGenericTypes().clear();
				return;
			case JavaModelPackage.JINTERFACE__IS_EXTERNAL:
				setIsExternal(IS_EXTERNAL_EDEFAULT);
				return;
			case JavaModelPackage.JINTERFACE__VISIBILITY_KIND:
				setVisibilityKind(VISIBILITY_KIND_EDEFAULT);
				return;
			case JavaModelPackage.JINTERFACE__CONSTANTS:
				getConstants().clear();
				return;
			case JavaModelPackage.JINTERFACE__METHOD_SIGNATURES:
				getMethodSignatures().clear();
				return;
			case JavaModelPackage.JINTERFACE__SUPER_INTERFACES:
				getSuperInterfaces().clear();
				return;
			case JavaModelPackage.JINTERFACE__SUB_INTERFACES:
				getSubInterfaces().clear();
				return;
			case JavaModelPackage.JINTERFACE__IMPLEMENTING_CLASSES:
				getImplementingClasses().clear();
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
			case JavaModelPackage.JINTERFACE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case JavaModelPackage.JINTERFACE__COMPILEABLE:
				return compileable != COMPILEABLE_EDEFAULT;
			case JavaModelPackage.JINTERFACE__JAVA_DOC:
				return javaDoc != null;
			case JavaModelPackage.JINTERFACE__GENERIC_TYPES:
				return genericTypes != null && !genericTypes.isEmpty();
			case JavaModelPackage.JINTERFACE__IS_EXTERNAL:
				return isExternal != IS_EXTERNAL_EDEFAULT;
			case JavaModelPackage.JINTERFACE__VISIBILITY_KIND:
				return visibilityKind != VISIBILITY_KIND_EDEFAULT;
			case JavaModelPackage.JINTERFACE__CONSTANTS:
				return constants != null && !constants.isEmpty();
			case JavaModelPackage.JINTERFACE__METHOD_SIGNATURES:
				return methodSignatures != null && !methodSignatures.isEmpty();
			case JavaModelPackage.JINTERFACE__SUPER_INTERFACES:
				return superInterfaces != null && !superInterfaces.isEmpty();
			case JavaModelPackage.JINTERFACE__SUB_INTERFACES:
				return subInterfaces != null && !subInterfaces.isEmpty();
			case JavaModelPackage.JINTERFACE__IMPLEMENTING_CLASSES:
				return implementingClasses != null && !implementingClasses.isEmpty();
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
				case JavaModelPackage.JINTERFACE__ID: return JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID;
				case JavaModelPackage.JINTERFACE__COMPILEABLE: return JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE;
				default: return -1;
			}
		}
		if (baseClass == JDocumentableElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JINTERFACE__JAVA_DOC: return JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC;
				default: return -1;
			}
		}
		if (baseClass == JTemplate.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JINTERFACE__GENERIC_TYPES: return JavaModelPackage.JTEMPLATE__GENERIC_TYPES;
				default: return -1;
			}
		}
		if (baseClass == JType.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JINTERFACE__IS_EXTERNAL: return JavaModelPackage.JTYPE__IS_EXTERNAL;
				default: return -1;
			}
		}
		if (baseClass == JVisibleElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JINTERFACE__VISIBILITY_KIND: return JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND;
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
				case JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID: return JavaModelPackage.JINTERFACE__ID;
				case JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE: return JavaModelPackage.JINTERFACE__COMPILEABLE;
				default: return -1;
			}
		}
		if (baseClass == JDocumentableElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC: return JavaModelPackage.JINTERFACE__JAVA_DOC;
				default: return -1;
			}
		}
		if (baseClass == JTemplate.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JTEMPLATE__GENERIC_TYPES: return JavaModelPackage.JINTERFACE__GENERIC_TYPES;
				default: return -1;
			}
		}
		if (baseClass == JType.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JTYPE__IS_EXTERNAL: return JavaModelPackage.JINTERFACE__IS_EXTERNAL;
				default: return -1;
			}
		}
		if (baseClass == JVisibleElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND: return JavaModelPackage.JINTERFACE__VISIBILITY_KIND;
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
		result.append(", isExternal: ");
		result.append(isExternal);
		result.append(", visibilityKind: ");
		result.append(visibilityKind);
		result.append(')');
		return result.toString();
	}

} //JInterfaceImpl
