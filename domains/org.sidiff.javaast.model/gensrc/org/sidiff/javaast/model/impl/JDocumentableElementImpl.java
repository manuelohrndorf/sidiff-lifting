/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.sidiff.javaast.model.JDocumentableElement;
import org.sidiff.javaast.model.JavaDoc;
import org.sidiff.javaast.model.JavaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JDocumentable Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.sidiff.javaast.model.impl.JDocumentableElementImpl#getJavaDoc <em>Java Doc</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class JDocumentableElementImpl extends EObjectImpl implements JDocumentableElement
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
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JDocumentableElementImpl()
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
		return JavaModelPackage.Literals.JDOCUMENTABLE_ELEMENT;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC, oldJavaDoc, newJavaDoc);
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
				msgs = ((InternalEObject)javaDoc).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC, null, msgs);
			if (newJavaDoc != null)
				msgs = ((InternalEObject)newJavaDoc).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC, null, msgs);
			msgs = basicSetJavaDoc(newJavaDoc, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC, newJavaDoc, newJavaDoc));
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
			case JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC:
				return basicSetJavaDoc(null, msgs);
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
			case JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC:
				return getJavaDoc();
		}
		return super.eGet(featureID, resolve, coreType);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public void eSet(int featureID, Object newValue)
  {
		switch (featureID) {
			case JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC:
				setJavaDoc((JavaDoc)newValue);
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
			case JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC:
				setJavaDoc((JavaDoc)null);
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
			case JavaModelPackage.JDOCUMENTABLE_ELEMENT__JAVA_DOC:
				return javaDoc != null;
		}
		return super.eIsSet(featureID);
	}

} //JDocumentableElementImpl
