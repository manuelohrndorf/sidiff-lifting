/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.sidiff.javaast.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JMethod</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getJavaDoc <em>Java Doc</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getGenericTypes <em>Generic Types</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getVisibilityKind <em>Visibility Kind</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#isIsAbstract <em>Is Abstract</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#isIsFinal <em>Is Final</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#isIsStatic <em>Is Static</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#isIsConstructor <em>Is Constructor</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#isIsSynchronized <em>Is Synchronized</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getCalls <em>Calls</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getCalledBy <em>Called By</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getRaisedException <em>Raised Exception</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.sidiff.javaast.model.impl.JMethodImpl#getAccesses <em>Accesses</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JMethodImpl extends JIdentifiableElementImpl implements JMethod
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
	 * The cached value of the '{@link #getGenericTypes() <em>Generic Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getGenericTypes()
	 * @generated
	 * @ordered
	 */
  protected EList<JGenericType> genericTypes;

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
	 * The default value of the '{@link #isIsConstructor() <em>Is Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsConstructor()
	 * @generated
	 * @ordered
	 */
  protected static final boolean IS_CONSTRUCTOR_EDEFAULT = false;

  /**
	 * The cached value of the '{@link #isIsConstructor() <em>Is Constructor</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsConstructor()
	 * @generated
	 * @ordered
	 */
  protected boolean isConstructor = IS_CONSTRUCTOR_EDEFAULT;

  /**
	 * The default value of the '{@link #isIsSynchronized() <em>Is Synchronized</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsSynchronized()
	 * @generated
	 * @ordered
	 */
  protected static final boolean IS_SYNCHRONIZED_EDEFAULT = false;

  /**
	 * The cached value of the '{@link #isIsSynchronized() <em>Is Synchronized</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #isIsSynchronized()
	 * @generated
	 * @ordered
	 */
  protected boolean isSynchronized = IS_SYNCHRONIZED_EDEFAULT;

  /**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
  protected EList<JParameter> parameters;

  /**
	 * The cached value of the '{@link #getCalls() <em>Calls</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getCalls()
	 * @generated
	 * @ordered
	 */
  protected EList<JMethod> calls;

  /**
	 * The cached value of the '{@link #getCalledBy() <em>Called By</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getCalledBy()
	 * @generated
	 * @ordered
	 */
  protected EList<JMethod> calledBy;

  /**
	 * The cached value of the '{@link #getRaisedException() <em>Raised Exception</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getRaisedException()
	 * @generated
	 * @ordered
	 */
  protected JClass raisedException;

  /**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
  protected JCodeBlock body;

  /**
	 * The cached value of the '{@link #getAccesses() <em>Accesses</em>}' reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getAccesses()
	 * @generated
	 * @ordered
	 */
  protected EList<JField> accesses;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JMethodImpl()
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
		return JavaModelPackage.Literals.JMETHOD;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__JAVA_DOC, oldJavaDoc, newJavaDoc);
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
				msgs = ((InternalEObject)javaDoc).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JMETHOD__JAVA_DOC, null, msgs);
			if (newJavaDoc != null)
				msgs = ((InternalEObject)newJavaDoc).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JMETHOD__JAVA_DOC, null, msgs);
			msgs = basicSetJavaDoc(newJavaDoc, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__JAVA_DOC, newJavaDoc, newJavaDoc));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__NAME, oldName, name));
	}

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getQualifiedName() {
		if ((eContainer() != null) && (eContainer() instanceof JNamedElement)){
			return ((JNamedElement)eContainer()).getQualifiedName() + "/" + getMName();
		} else{
			return getMName();
		}
	}

	private String getMName(){
		String res = this.getName() + "(";
		for (Iterator iterator = getParameters().iterator(); iterator.hasNext();) {
			JParameter p = (JParameter) iterator.next();
			res += p.getName() + " ";
			if (p.getType() instanceof JNamedElement){
				res+=((JNamedElement)p.getType()).getName();
			}else if (p.getType() instanceof JSimpleType){
				res+=((JSimpleType)p.getType()).getName();
			} 
			if (iterator.hasNext()){
				res+=",";
			}
			
			
		}
		res+=")";
		res+="[" + getId() + "]";
		
		return res;
	}
	
		/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JGenericType> getGenericTypes()
  {
		if (genericTypes == null) {
			genericTypes = new EObjectContainmentEList<JGenericType>(JGenericType.class, this, JavaModelPackage.JMETHOD__GENERIC_TYPES);
		}
		return genericTypes;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JavaModelPackage.JMETHOD__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__VISIBILITY_KIND, oldVisibilityKind, visibilityKind));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__IS_ABSTRACT, oldIsAbstract, isAbstract));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__IS_FINAL, oldIsFinal, isFinal));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__IS_STATIC, oldIsStatic, isStatic));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public boolean isIsConstructor()
  {
		return isConstructor;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setIsConstructor(boolean newIsConstructor)
  {
		boolean oldIsConstructor = isConstructor;
		isConstructor = newIsConstructor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__IS_CONSTRUCTOR, oldIsConstructor, isConstructor));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public boolean isIsSynchronized()
  {
		return isSynchronized;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setIsSynchronized(boolean newIsSynchronized)
  {
		boolean oldIsSynchronized = isSynchronized;
		isSynchronized = newIsSynchronized;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__IS_SYNCHRONIZED, oldIsSynchronized, isSynchronized));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JParameter> getParameters()
  {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<JParameter>(JParameter.class, this, JavaModelPackage.JMETHOD__PARAMETERS);
		}
		return parameters;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JMethod> getCalls()
  {
		if (calls == null) {
			calls = new EObjectWithInverseResolvingEList.ManyInverse<JMethod>(JMethod.class, this, JavaModelPackage.JMETHOD__CALLS, JavaModelPackage.JMETHOD__CALLED_BY);
		}
		return calls;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JMethod> getCalledBy()
  {
		if (calledBy == null) {
			calledBy = new EObjectWithInverseResolvingEList.ManyInverse<JMethod>(JMethod.class, this, JavaModelPackage.JMETHOD__CALLED_BY, JavaModelPackage.JMETHOD__CALLS);
		}
		return calledBy;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JClass getRaisedException()
  {
		if (raisedException != null && raisedException.eIsProxy()) {
			InternalEObject oldRaisedException = (InternalEObject)raisedException;
			raisedException = (JClass)eResolveProxy(oldRaisedException);
			if (raisedException != oldRaisedException) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JavaModelPackage.JMETHOD__RAISED_EXCEPTION, oldRaisedException, raisedException));
			}
		}
		return raisedException;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JClass basicGetRaisedException()
  {
		return raisedException;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setRaisedException(JClass newRaisedException)
  {
		JClass oldRaisedException = raisedException;
		raisedException = newRaisedException;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__RAISED_EXCEPTION, oldRaisedException, raisedException));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JCodeBlock getBody()
  {
		return body;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetBody(JCodeBlock newBody, NotificationChain msgs)
  {
		JCodeBlock oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__BODY, oldBody, newBody);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setBody(JCodeBlock newBody)
  {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JMETHOD__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JMETHOD__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JMETHOD__BODY, newBody, newBody));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EList<JField> getAccesses()
  {
		if (accesses == null) {
			accesses = new EObjectWithInverseResolvingEList.ManyInverse<JField>(JField.class, this, JavaModelPackage.JMETHOD__ACCESSES, JavaModelPackage.JFIELD__ACCESSED_BY);
		}
		return accesses;
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
			case JavaModelPackage.JMETHOD__CALLS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCalls()).basicAdd(otherEnd, msgs);
			case JavaModelPackage.JMETHOD__CALLED_BY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCalledBy()).basicAdd(otherEnd, msgs);
			case JavaModelPackage.JMETHOD__ACCESSES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAccesses()).basicAdd(otherEnd, msgs);
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
			case JavaModelPackage.JMETHOD__JAVA_DOC:
				return basicSetJavaDoc(null, msgs);
			case JavaModelPackage.JMETHOD__GENERIC_TYPES:
				return ((InternalEList<?>)getGenericTypes()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JMETHOD__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JMETHOD__CALLS:
				return ((InternalEList<?>)getCalls()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JMETHOD__CALLED_BY:
				return ((InternalEList<?>)getCalledBy()).basicRemove(otherEnd, msgs);
			case JavaModelPackage.JMETHOD__BODY:
				return basicSetBody(null, msgs);
			case JavaModelPackage.JMETHOD__ACCESSES:
				return ((InternalEList<?>)getAccesses()).basicRemove(otherEnd, msgs);
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
			case JavaModelPackage.JMETHOD__JAVA_DOC:
				return getJavaDoc();
			case JavaModelPackage.JMETHOD__NAME:
				return getName();
			case JavaModelPackage.JMETHOD__QUALIFIED_NAME:
				return getQualifiedName();
			case JavaModelPackage.JMETHOD__GENERIC_TYPES:
				return getGenericTypes();
			case JavaModelPackage.JMETHOD__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case JavaModelPackage.JMETHOD__VISIBILITY_KIND:
				return getVisibilityKind();
			case JavaModelPackage.JMETHOD__IS_ABSTRACT:
				return isIsAbstract();
			case JavaModelPackage.JMETHOD__IS_FINAL:
				return isIsFinal();
			case JavaModelPackage.JMETHOD__IS_STATIC:
				return isIsStatic();
			case JavaModelPackage.JMETHOD__IS_CONSTRUCTOR:
				return isIsConstructor();
			case JavaModelPackage.JMETHOD__IS_SYNCHRONIZED:
				return isIsSynchronized();
			case JavaModelPackage.JMETHOD__PARAMETERS:
				return getParameters();
			case JavaModelPackage.JMETHOD__CALLS:
				return getCalls();
			case JavaModelPackage.JMETHOD__CALLED_BY:
				return getCalledBy();
			case JavaModelPackage.JMETHOD__RAISED_EXCEPTION:
				if (resolve) return getRaisedException();
				return basicGetRaisedException();
			case JavaModelPackage.JMETHOD__BODY:
				return getBody();
			case JavaModelPackage.JMETHOD__ACCESSES:
				return getAccesses();
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
			case JavaModelPackage.JMETHOD__JAVA_DOC:
				setJavaDoc((JavaDoc)newValue);
				return;
			case JavaModelPackage.JMETHOD__NAME:
				setName((String)newValue);
				return;
			case JavaModelPackage.JMETHOD__GENERIC_TYPES:
				getGenericTypes().clear();
				getGenericTypes().addAll((Collection<? extends JGenericType>)newValue);
				return;
			case JavaModelPackage.JMETHOD__TYPE:
				setType((JType)newValue);
				return;
			case JavaModelPackage.JMETHOD__VISIBILITY_KIND:
				setVisibilityKind((JVisibilityKind)newValue);
				return;
			case JavaModelPackage.JMETHOD__IS_ABSTRACT:
				setIsAbstract((Boolean)newValue);
				return;
			case JavaModelPackage.JMETHOD__IS_FINAL:
				setIsFinal((Boolean)newValue);
				return;
			case JavaModelPackage.JMETHOD__IS_STATIC:
				setIsStatic((Boolean)newValue);
				return;
			case JavaModelPackage.JMETHOD__IS_CONSTRUCTOR:
				setIsConstructor((Boolean)newValue);
				return;
			case JavaModelPackage.JMETHOD__IS_SYNCHRONIZED:
				setIsSynchronized((Boolean)newValue);
				return;
			case JavaModelPackage.JMETHOD__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends JParameter>)newValue);
				return;
			case JavaModelPackage.JMETHOD__CALLS:
				getCalls().clear();
				getCalls().addAll((Collection<? extends JMethod>)newValue);
				return;
			case JavaModelPackage.JMETHOD__CALLED_BY:
				getCalledBy().clear();
				getCalledBy().addAll((Collection<? extends JMethod>)newValue);
				return;
			case JavaModelPackage.JMETHOD__RAISED_EXCEPTION:
				setRaisedException((JClass)newValue);
				return;
			case JavaModelPackage.JMETHOD__BODY:
				setBody((JCodeBlock)newValue);
				return;
			case JavaModelPackage.JMETHOD__ACCESSES:
				getAccesses().clear();
				getAccesses().addAll((Collection<? extends JField>)newValue);
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
			case JavaModelPackage.JMETHOD__JAVA_DOC:
				setJavaDoc((JavaDoc)null);
				return;
			case JavaModelPackage.JMETHOD__NAME:
				setName(NAME_EDEFAULT);
				return;
			case JavaModelPackage.JMETHOD__GENERIC_TYPES:
				getGenericTypes().clear();
				return;
			case JavaModelPackage.JMETHOD__TYPE:
				setType((JType)null);
				return;
			case JavaModelPackage.JMETHOD__VISIBILITY_KIND:
				setVisibilityKind(VISIBILITY_KIND_EDEFAULT);
				return;
			case JavaModelPackage.JMETHOD__IS_ABSTRACT:
				setIsAbstract(IS_ABSTRACT_EDEFAULT);
				return;
			case JavaModelPackage.JMETHOD__IS_FINAL:
				setIsFinal(IS_FINAL_EDEFAULT);
				return;
			case JavaModelPackage.JMETHOD__IS_STATIC:
				setIsStatic(IS_STATIC_EDEFAULT);
				return;
			case JavaModelPackage.JMETHOD__IS_CONSTRUCTOR:
				setIsConstructor(IS_CONSTRUCTOR_EDEFAULT);
				return;
			case JavaModelPackage.JMETHOD__IS_SYNCHRONIZED:
				setIsSynchronized(IS_SYNCHRONIZED_EDEFAULT);
				return;
			case JavaModelPackage.JMETHOD__PARAMETERS:
				getParameters().clear();
				return;
			case JavaModelPackage.JMETHOD__CALLS:
				getCalls().clear();
				return;
			case JavaModelPackage.JMETHOD__CALLED_BY:
				getCalledBy().clear();
				return;
			case JavaModelPackage.JMETHOD__RAISED_EXCEPTION:
				setRaisedException((JClass)null);
				return;
			case JavaModelPackage.JMETHOD__BODY:
				setBody((JCodeBlock)null);
				return;
			case JavaModelPackage.JMETHOD__ACCESSES:
				getAccesses().clear();
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
			case JavaModelPackage.JMETHOD__JAVA_DOC:
				return javaDoc != null;
			case JavaModelPackage.JMETHOD__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case JavaModelPackage.JMETHOD__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? getQualifiedName() != null : !QUALIFIED_NAME_EDEFAULT.equals(getQualifiedName());
			case JavaModelPackage.JMETHOD__GENERIC_TYPES:
				return genericTypes != null && !genericTypes.isEmpty();
			case JavaModelPackage.JMETHOD__TYPE:
				return type != null;
			case JavaModelPackage.JMETHOD__VISIBILITY_KIND:
				return visibilityKind != VISIBILITY_KIND_EDEFAULT;
			case JavaModelPackage.JMETHOD__IS_ABSTRACT:
				return isAbstract != IS_ABSTRACT_EDEFAULT;
			case JavaModelPackage.JMETHOD__IS_FINAL:
				return isFinal != IS_FINAL_EDEFAULT;
			case JavaModelPackage.JMETHOD__IS_STATIC:
				return isStatic != IS_STATIC_EDEFAULT;
			case JavaModelPackage.JMETHOD__IS_CONSTRUCTOR:
				return isConstructor != IS_CONSTRUCTOR_EDEFAULT;
			case JavaModelPackage.JMETHOD__IS_SYNCHRONIZED:
				return isSynchronized != IS_SYNCHRONIZED_EDEFAULT;
			case JavaModelPackage.JMETHOD__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case JavaModelPackage.JMETHOD__CALLS:
				return calls != null && !calls.isEmpty();
			case JavaModelPackage.JMETHOD__CALLED_BY:
				return calledBy != null && !calledBy.isEmpty();
			case JavaModelPackage.JMETHOD__RAISED_EXCEPTION:
				return raisedException != null;
			case JavaModelPackage.JMETHOD__BODY:
				return body != null;
			case JavaModelPackage.JMETHOD__ACCESSES:
				return accesses != null && !accesses.isEmpty();
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
				case JavaModelPackage.JMETHOD__JAVA_DOC: return JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC;
				default: return -1;
			}
		}
		if (baseClass == JNamedElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JMETHOD__NAME: return JavaModelPackage.JNAMED_ELEMENT__NAME;
				case JavaModelPackage.JMETHOD__QUALIFIED_NAME: return JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME;
				default: return -1;
			}
		}
		if (baseClass == JTemplate.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JMETHOD__GENERIC_TYPES: return JavaModelPackage.JTEMPLATE__GENERIC_TYPES;
				default: return -1;
			}
		}
		if (baseClass == JTypedElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JMETHOD__TYPE: return JavaModelPackage.JTYPED_ELEMENT__TYPE;
				default: return -1;
			}
		}
		if (baseClass == JVisibleElement.class) {
			switch (derivedFeatureID) {
				case JavaModelPackage.JMETHOD__VISIBILITY_KIND: return JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND;
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
				case JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC: return JavaModelPackage.JMETHOD__JAVA_DOC;
				default: return -1;
			}
		}
		if (baseClass == JNamedElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JNAMED_ELEMENT__NAME: return JavaModelPackage.JMETHOD__NAME;
				case JavaModelPackage.JNAMED_ELEMENT__QUALIFIED_NAME: return JavaModelPackage.JMETHOD__QUALIFIED_NAME;
				default: return -1;
			}
		}
		if (baseClass == JTemplate.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JTEMPLATE__GENERIC_TYPES: return JavaModelPackage.JMETHOD__GENERIC_TYPES;
				default: return -1;
			}
		}
		if (baseClass == JTypedElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JTYPED_ELEMENT__TYPE: return JavaModelPackage.JMETHOD__TYPE;
				default: return -1;
			}
		}
		if (baseClass == JVisibleElement.class) {
			switch (baseFeatureID) {
				case JavaModelPackage.JVISIBLE_ELEMENT__VISIBILITY_KIND: return JavaModelPackage.JMETHOD__VISIBILITY_KIND;
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
		result.append(", isAbstract: ");
		result.append(isAbstract);
		result.append(", isFinal: ");
		result.append(isFinal);
		result.append(", isStatic: ");
		result.append(isStatic);
		result.append(", isConstructor: ");
		result.append(isConstructor);
		result.append(", isSynchronized: ");
		result.append(isSynchronized);
		result.append(')');
		return result.toString();
	}

} //JMethodImpl
