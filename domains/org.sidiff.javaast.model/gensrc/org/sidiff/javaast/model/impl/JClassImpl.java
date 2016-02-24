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
import org.sidiff.javaast.model.JCodeBlock;
import org.sidiff.javaast.model.JDocumentableElement;
import org.sidiff.javaast.model.JField;
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
 * An implementation of the model object '<em><b>JClass</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#isCompileable <em>Compileable</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#getJavaDoc <em>Java Doc</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#getGenericTypes <em>Generic Types</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#isIsExternal <em>Is External</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#getVisibilityKind <em>Visibility Kind</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#getFields <em>Fields</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#isIsAbstract <em>Is Abstract</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#isIsFinal <em>Is Final</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#getMethods <em>Methods</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#getSuperClass <em>Super Class</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#getSubClasses <em>Sub Classes</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#getImplementedInterfaces <em>Implemented Interfaces</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JClassImpl#getStaticInitializationBlock <em>Static Initialization Block</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JClassImpl extends JClassifierImpl implements JClass
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
	 * The cached value of the '{@link #getFields() <em>Fields</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getFields()
	 * @generated
	 * @ordered
	 */
  protected EList<JField> fields;

  /**
	 * The default value of the '{@link #isIsAbstract() <em>Is Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsAbstract()
	 * @generated
	 * @ordered
	 */
  protected static final boolean IS_ABSTRACT_EDEFAULT = false;

  /**
	 * The cached value of the '{@link #isIsAbstract() <em>Is Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsAbstract()
	 * @generated
	 * @ordered
	 */
  protected boolean isAbstract = IS_ABSTRACT_EDEFAULT;

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
	 * The cached value of the '{@link #getMethods() <em>Methods</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getMethods()
	 * @generated
	 * @ordered
	 */
  protected EList<JMethod> methods;

  /**
	 * The cached value of the '{@link #getSuperClass() <em>Super Class</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSuperClass()
	 * @generated
	 * @ordered
	 */
  protected JClass superClass;

  /**
	 * The cached value of the '{@link #getSubClasses() <em>Sub Classes</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSubClasses()
	 * @generated
	 * @ordered
	 */
  protected EList<JClass> subClasses;

  /**
	 * The cached value of the '{@link #getImplementedInterfaces() <em>Implemented Interfaces</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getImplementedInterfaces()
	 * @generated
	 * @ordered
	 */
  protected EList<JInterface> implementedInterfaces;

  /**
	 * The cached value of the '{@link #getStaticInitializationBlock() <em>Static Initialization Block</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getStaticInitializationBlock()
	 * @generated
	 * @ordered
	 */
  protected JCodeBlock staticInitializationBlock;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JClassImpl()
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
		return JavaModelPackage.Literals.JCLASS;
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__COMPILEABLE, oldCompileable, compileable));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__JAVA_DOC, oldJavaDoc, newJavaDoc);
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
				msgs = ((InternalEObject)javaDoc).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JCLASS__JAVA_DOC, null, msgs);
			if (newJavaDoc != null)
				msgs = ((InternalEObject)newJavaDoc).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JCLASS__JAVA_DOC, null, msgs);
			msgs = basicSetJavaDoc(newJavaDoc, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__JAVA_DOC, newJavaDoc, newJavaDoc));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JGenericType> getGenericTypes()
  {
		if (genericTypes == null) {
			genericTypes = new EObjectContainmentEList<JGenericType>(JGenericType.class, this, JavaModelPackage.JCLASS__GENERIC_TYPES);
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__IS_EXTERNAL, oldIsExternal, isExternal));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__VISIBILITY_KIND, oldVisibilityKind, visibilityKind));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JField> getFields()
  {
		if (fields == null) {
			fields = new EObjectContainmentEList<JField>(JField.class, this, JavaModelPackage.JCLASS__FIELDS);
		}
		return fields;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public boolean isIsAbstract()
  {
		return isAbstract;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setIsAbstract(boolean newIsAbstract)
  {
		boolean oldIsAbstract = isAbstract;
		isAbstract = newIsAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__IS_ABSTRACT, oldIsAbstract, isAbstract));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__IS_FINAL, oldIsFinal, isFinal));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JMethod> getMethods()
  {
		if (methods == null) {
			methods = new EObjectContainmentEList<JMethod>(JMethod.class, this, JavaModelPackage.JCLASS__METHODS);
		}
		return methods;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JClass getSuperClass()
  {
		if (superClass != null && superClass.eIsProxy()) {
			InternalEObject oldSuperClass = (InternalEObject)superClass;
			superClass = (JClass)eResolveProxy(oldSuperClass);
			if (superClass != oldSuperClass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JavaModelPackage.JCLASS__SUPER_CLASS, oldSuperClass, superClass));
			}
		}
		return superClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JClass basicGetSuperClass()
  {
		return superClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetSuperClass(JClass newSuperClass, NotificationChain msgs)
  {
		JClass oldSuperClass = superClass;
		superClass = newSuperClass;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__SUPER_CLASS, oldSuperClass, newSuperClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setSuperClass(JClass newSuperClass)
  {
		if (newSuperClass != superClass) {
			NotificationChain msgs = null;
			if (superClass != null)
				msgs = ((InternalEObject)superClass).eInverseRemove(this, JavaModelPackage.JCLASS__SUB_CLASSES, JClass.class, msgs);
			if (newSuperClass != null)
				msgs = ((InternalEObject)newSuperClass).eInverseAdd(this, JavaModelPackage.JCLASS__SUB_CLASSES, JClass.class, msgs);
			msgs = basicSetSuperClass(newSuperClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__SUPER_CLASS, newSuperClass, newSuperClass));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JClass> getSubClasses()
  {
		if (subClasses == null) {
			subClasses = new EObjectWithInverseResolvingEList<JClass>(JClass.class, this, JavaModelPackage.JCLASS__SUB_CLASSES, JavaModelPackage.JCLASS__SUPER_CLASS);
		}
		return subClasses;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JInterface> getImplementedInterfaces()
  {
		if (implementedInterfaces == null) {
			implementedInterfaces = new EObjectWithInverseResolvingEList.ManyInverse<JInterface>(JInterface.class, this, JavaModelPackage.JCLASS__IMPLEMENTED_INTERFACES, JavaModelPackage.JINTERFACE__IMPLEMENTING_CLASSES);
		}
		return implementedInterfaces;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JCodeBlock getStaticInitializationBlock()
  {
		return staticInitializationBlock;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetStaticInitializationBlock(JCodeBlock newStaticInitializationBlock, NotificationChain msgs)
  {
		JCodeBlock oldStaticInitializationBlock = staticInitializationBlock;
		staticInitializationBlock = newStaticInitializationBlock;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__STATIC_INITIALIZATION_BLOCK, oldStaticInitializationBlock, newStaticInitializationBlock);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setStaticInitializationBlock(JCodeBlock newStaticInitializationBlock)
  {
		if (newStaticInitializationBlock != staticInitializationBlock) {
			NotificationChain msgs = null;
			if (staticInitializationBlock != null)
				msgs = ((InternalEObject)staticInitializationBlock).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JCLASS__STATIC_INITIALIZATION_BLOCK, null, msgs);
			if (newStaticInitializationBlock != null)
				msgs = ((InternalEObject)newStaticInitializationBlock).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JCLASS__STATIC_INITIALIZATION_BLOCK, null, msgs);
			msgs = basicSetStaticInitializationBlock(newStaticInitializationBlock, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JCLASS__STATIC_INITIALIZATION_BLOCK, newStaticInitializationBlock, newStaticInitializationBlock));
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
			case JavaModelPackage.JCLASS__SUPER_CLASS:
				if (superClass != null)
					msgs = ((InternalEObject)superClass).eInverseRemove(this, JavaModelPackage.JCLASS__SUB_CLASSES, JClass.class, msgs);
				return basicSetSuperClass((JClass)otherEnd, msgs);
			case JavaModelPackage.JCLASS__SUB_CLASSES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSubClasses()).basicAdd(otherEnd, msgs);
			case JavaModelPackage.JCLASS__IMPLEMENTED_INTERFACES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getImplementedInterfaces()).basicAdd(otherEnd, msgs);
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
			case JavaModelPackage.JCLASS__JAVA_DOC:
				return basicSetJavaDoc(null, msgs);
			case JavaModelPackage.JCLASS__GENERIC_TYPES:
				return ((InternalEList<?>)getGenericTypes()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JCLASS__FIELDS:
				return ((InternalEList<?>)getFields()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JCLASS__METHODS:
				return ((InternalEList<?>)getMethods()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JCLASS__SUPER_CLASS:
				return basicSetSuperClass(null, msgs);
			case JavaModelPackage.JCLASS__SUB_CLASSES:
				return ((InternalEList<?>)getSubClasses()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JCLASS__IMPLEMENTED_INTERFACES:
				return ((InternalEList<?>)getImplementedInterfaces()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JCLASS__STATIC_INITIALIZATION_BLOCK:
				return basicSetStaticInitializationBlock(null, msgs);
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
			case JavaModelPackage.JCLASS__ID:
				return getId();
			case JavaModelPackage.JCLASS__COMPILEABLE:
				return isCompileable();
			case JavaModelPackage.JCLASS__JAVA_DOC:
				return getJavaDoc();
			case JavaModelPackage.JCLASS__GENERIC_TYPES:
				return getGenericTypes();
			case JavaModelPackage.JCLASS__IS_EXTERNAL:
				return isIsExternal();
			case JavaModelPackage.JCLASS__VISIBILITY_KIND:
				return getVisibilityKind();
			case JavaModelPackage.JCLASS__FIELDS:
				return getFields();
			case JavaModelPackage.JCLASS__IS_ABSTRACT:
				return isIsAbstract();
			case JavaModelPackage.JCLASS__IS_FINAL:
				return isIsFinal();
			case JavaModelPackage.JCLASS__METHODS:
				return getMethods();
			case JavaModelPackage.JCLASS__SUPER_CLASS:
				if (resolve) return getSuperClass();
				return basicGetSuperClass();
			case JavaModelPackage.JCLASS__SUB_CLASSES:
				return getSubClasses();
			case JavaModelPackage.JCLASS__IMPLEMENTED_INTERFACES:
				return getImplementedInterfaces();
			case JavaModelPackage.JCLASS__STATIC_INITIALIZATION_BLOCK:
				return getStaticInitializationBlock();
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
			case JavaModelPackage.JCLASS__ID:
				setId((String)newValue);
				return;
			case JavaModelPackage.JCLASS__COMPILEABLE:
				setCompileable((Boolean)newValue);
				return;
			case JavaModelPackage.JCLASS__JAVA_DOC:
				setJavaDoc((JavaDoc)newValue);
				return;
			case JavaModelPackage.JCLASS__GENERIC_TYPES:
				getGenericTypes().clear();
				getGenericTypes().addAll((Collection<? extends JGenericType>)newValue);
				return;
			case JavaModelPackage.JCLASS__IS_EXTERNAL:
				setIsExternal((Boolean)newValue);
				return;
			case JavaModelPackage.JCLASS__VISIBILITY_KIND:
				setVisibilityKind((JVisibilityKind)newValue);
				return;
			case JavaModelPackage.JCLASS__FIELDS:
				getFields().clear();
				getFields().addAll((Collection<? extends JField>)newValue);
				return;
			case JavaModelPackage.JCLASS__IS_ABSTRACT:
				setIsAbstract((Boolean)newValue);
				return;
			case JavaModelPackage.JCLASS__IS_FINAL:
				setIsFinal((Boolean)newValue);
				return;
			case JavaModelPackage.JCLASS__METHODS:
				getMethods().clear();
				getMethods().addAll((Collection<? extends JMethod>)newValue);
				return;
			case JavaModelPackage.JCLASS__SUPER_CLASS:
				setSuperClass((JClass)newValue);
				return;
			case JavaModelPackage.JCLASS__SUB_CLASSES:
				getSubClasses().clear();
				getSubClasses().addAll((Collection<? extends JClass>)newValue);
				return;
			case JavaModelPackage.JCLASS__IMPLEMENTED_INTERFACES:
				getImplementedInterfaces().clear();
				getImplementedInterfaces().addAll((Collection<? extends JInterface>)newValue);
				return;
			case JavaModelPackage.JCLASS__STATIC_INITIALIZATION_BLOCK:
				setStaticInitializationBlock((JCodeBlock)newValue);
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
			case JavaModelPackage.JCLASS__ID:
				setId(ID_EDEFAULT);
				return;
			case JavaModelPackage.JCLASS__COMPILEABLE:
				setCompileable(COMPILEABLE_EDEFAULT);
				return;
			case JavaModelPackage.JCLASS__JAVA_DOC:
				setJavaDoc((JavaDoc)null);
				return;
			case JavaModelPackage.JCLASS__GENERIC_TYPES:
				getGenericTypes().clear();
				return;
			case JavaModelPackage.JCLASS__IS_EXTERNAL:
				setIsExternal(IS_EXTERNAL_EDEFAULT);
				return;
			case JavaModelPackage.JCLASS__VISIBILITY_KIND:
				setVisibilityKind(VISIBILITY_KIND_EDEFAULT);
				return;
			case JavaModelPackage.JCLASS__FIELDS:
				getFields().clear();
				return;
			case JavaModelPackage.JCLASS__IS_ABSTRACT:
				setIsAbstract(IS_ABSTRACT_EDEFAULT);
				return;
			case JavaModelPackage.JCLASS__IS_FINAL:
				setIsFinal(IS_FINAL_EDEFAULT);
				return;
			case JavaModelPackage.JCLASS__METHODS:
				getMethods().clear();
				return;
			case JavaModelPackage.JCLASS__SUPER_CLASS:
				setSuperClass((JClass)null);
				return;
			case JavaModelPackage.JCLASS__SUB_CLASSES:
				getSubClasses().clear();
				return;
			case JavaModelPackage.JCLASS__IMPLEMENTED_INTERFACES:
				getImplementedInterfaces().clear();
				return;
			case JavaModelPackage.JCLASS__STATIC_INITIALIZATION_BLOCK:
				setStaticInitializationBlock((JCodeBlock)null);
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
			case JavaModelPackage.JCLASS__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case JavaModelPackage.JCLASS__COMPILEABLE:
				return compileable != COMPILEABLE_EDEFAULT;
			case JavaModelPackage.JCLASS__JAVA_DOC:
				return javaDoc != null;
			case JavaModelPackage.JCLASS__GENERIC_TYPES:
				return genericTypes != null && !genericTypes.isEmpty();
			case JavaModelPackage.JCLASS__IS_EXTERNAL:
				return isExternal != IS_EXTERNAL_EDEFAULT;
			case JavaModelPackage.JCLASS__VISIBILITY_KIND:
				return visibilityKind != VISIBILITY_KIND_EDEFAULT;
			case JavaModelPackage.JCLASS__FIELDS:
				return fields != null && !fields.isEmpty();
			case JavaModelPackage.JCLASS__IS_ABSTRACT:
				return isAbstract != IS_ABSTRACT_EDEFAULT;
			case JavaModelPackage.JCLASS__IS_FINAL:
				return isFinal != IS_FINAL_EDEFAULT;
			case JavaModelPackage.JCLASS__METHODS:
				return methods != null && !methods.isEmpty();
			case JavaModelPackage.JCLASS__SUPER_CLASS:
				return superClass != null;
			case JavaModelPackage.JCLASS__SUB_CLASSES:
				return subClasses != null && !subClasses.isEmpty();
			case JavaModelPackage.JCLASS__IMPLEMENTED_INTERFACES:
				return implementedInterfaces != null && !implementedInterfaces.isEmpty();
			case JavaModelPackage.JCLASS__STATIC_INITIALIZATION_BLOCK:
				return staticInitializationBlock != null;
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
				case JavaModelPackage.JCLASS__ID: return JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID;
				case JavaModelPackage.JCLASS__COMPILEABLE: return JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE;
				default: return -1;
			}
		}
		if (baseClass == JDocumentableElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JCLASS__JAVA_DOC: return JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC;
				default: return -1;
			}
		}
		if (baseClass == JTemplate.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JCLASS__GENERIC_TYPES: return JavaModelPackage.JTEMPLATE__GENERIC_TYPES;
				default: return -1;
			}
		}
		if (baseClass == JType.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JCLASS__IS_EXTERNAL: return JavaModelPackage.JTYPE__IS_EXTERNAL;
				default: return -1;
			}
		}
		if (baseClass == JVisibleElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JCLASS__VISIBILITY_KIND: return JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND;
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
				case JavaModelPackage.JIDENTIFIABLE_ELEMENT__ID: return JavaModelPackage.JCLASS__ID;
				case JavaModelPackage.JIDENTIFIABLE_ELEMENT__COMPILEABLE: return JavaModelPackage.JCLASS__COMPILEABLE;
				default: return -1;
			}
		}
		if (baseClass == JDocumentableElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC: return JavaModelPackage.JCLASS__JAVA_DOC;
				default: return -1;
			}
		}
		if (baseClass == JTemplate.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JTEMPLATE__GENERIC_TYPES: return JavaModelPackage.JCLASS__GENERIC_TYPES;
				default: return -1;
			}
		}
		if (baseClass == JType.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JTYPE__IS_EXTERNAL: return JavaModelPackage.JCLASS__IS_EXTERNAL;
				default: return -1;
			}
		}
		if (baseClass == JVisibleElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND: return JavaModelPackage.JCLASS__VISIBILITY_KIND;
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
		result.append(", isAbstract: ");
		result.append(isAbstract);
		result.append(", isFinal: ");
		result.append(isFinal);
		result.append(')');
		return result.toString();
	}

} //JClassImpl
