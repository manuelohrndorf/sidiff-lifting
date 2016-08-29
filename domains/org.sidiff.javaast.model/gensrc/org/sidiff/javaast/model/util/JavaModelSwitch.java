/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.sidiff.javaast.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.sidiff.javaast.model.JavaModelPackage
 * @generated
 */
public class JavaModelSwitch<T> extends Switch<T>
{
  /**
	 * The cached model package
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected static JavaModelPackage modelPackage;

  /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JavaModelSwitch()
  {
		if (modelPackage == null) {
			modelPackage = JavaModelPackage.eINSTANCE;
		}
	}

  /**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

		/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
  @Override
		protected T doSwitch(int classifierID, EObject theEObject)
  {
		switch (classifierID) {
			case JavaModelPackage.JPROJECT: {
				JProject jProject = (JProject)theEObject;
				T result = caseJProject(jProject);
				if (result == null) result = caseJIdentifiableElement(jProject);
				if (result == null) result = caseJNamedElement(jProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JNAMED_ELEMENT: {
				JNamedElement jNamedElement = (JNamedElement)theEObject;
				T result = caseJNamedElement(jNamedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JIDENTIFIABLE_ELEMENT: {
				JIdentifiableElement jIdentifiableElement = (JIdentifiableElement)theEObject;
				T result = caseJIdentifiableElement(jIdentifiableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JPACKAGE: {
				JPackage jPackage = (JPackage)theEObject;
				T result = caseJPackage(jPackage);
				if (result == null) result = caseJIdentifiableElement(jPackage);
				if (result == null) result = caseJNamedElement(jPackage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JCLASS: {
				JClass jClass = (JClass)theEObject;
				T result = caseJClass(jClass);
				if (result == null) result = caseJClassifier(jClass);
				if (result == null) result = caseJIdentifiableElement(jClass);
				if (result == null) result = caseJDocumentableElement(jClass);
				if (result == null) result = caseJTemplate(jClass);
				if (result == null) result = caseJType(jClass);
				if (result == null) result = caseJVisibleElement(jClass);
				if (result == null) result = caseJNamedElement(jClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JVISIBLE_ELEMENT: {
				JVisibleElement jVisibleElement = (JVisibleElement)theEObject;
				T result = caseJVisibleElement(jVisibleElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JTYPE: {
				JType jType = (JType)theEObject;
				T result = caseJType(jType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JTEMPLATE: {
				JTemplate jTemplate = (JTemplate)theEObject;
				T result = caseJTemplate(jTemplate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JGENERIC_TYPE: {
				JGenericType jGenericType = (JGenericType)theEObject;
				T result = caseJGenericType(jGenericType);
				if (result == null) result = caseJIdentifiableElement(jGenericType);
				if (result == null) result = caseJNamedElement(jGenericType);
				if (result == null) result = caseJType(jGenericType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JDOCUMENTABLE_ELEMENT: {
				JDocumentableElement jDocumentableElement = (JDocumentableElement)theEObject;
				T result = caseJDocumentableElement(jDocumentableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JAVA_DOC: {
				JavaDoc javaDoc = (JavaDoc)theEObject;
				T result = caseJavaDoc(javaDoc);
				if (result == null) result = caseJIdentifiableElement(javaDoc);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JCLASSIFIER: {
				JClassifier jClassifier = (JClassifier)theEObject;
				T result = caseJClassifier(jClassifier);
				if (result == null) result = caseJNamedElement(jClassifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JFIELD: {
				JField jField = (JField)theEObject;
				T result = caseJField(jField);
				if (result == null) result = caseJIdentifiableElement(jField);
				if (result == null) result = caseJDocumentableElement(jField);
				if (result == null) result = caseJNamedElement(jField);
				if (result == null) result = caseJTypedElement(jField);
				if (result == null) result = caseJVisibleElement(jField);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JTYPED_ELEMENT: {
				JTypedElement jTypedElement = (JTypedElement)theEObject;
				T result = caseJTypedElement(jTypedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JMETHOD: {
				JMethod jMethod = (JMethod)theEObject;
				T result = caseJMethod(jMethod);
				if (result == null) result = caseJIdentifiableElement(jMethod);
				if (result == null) result = caseJDocumentableElement(jMethod);
				if (result == null) result = caseJNamedElement(jMethod);
				if (result == null) result = caseJTemplate(jMethod);
				if (result == null) result = caseJTypedElement(jMethod);
				if (result == null) result = caseJVisibleElement(jMethod);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JPARAMETER: {
				JParameter jParameter = (JParameter)theEObject;
				T result = caseJParameter(jParameter);
				if (result == null) result = caseJIdentifiableElement(jParameter);
				if (result == null) result = caseJNamedElement(jParameter);
				if (result == null) result = caseJTypedElement(jParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JCODE_BLOCK: {
				JCodeBlock jCodeBlock = (JCodeBlock)theEObject;
				T result = caseJCodeBlock(jCodeBlock);
				if (result == null) result = caseJAdressableFragment(jCodeBlock);
				if (result == null) result = caseJIdentifiableElement(jCodeBlock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JADRESSABLE_FRAGMENT: {
				JAdressableFragment jAdressableFragment = (JAdressableFragment)theEObject;
				T result = caseJAdressableFragment(jAdressableFragment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JCODE_FRAGMENT: {
				JCodeFragment jCodeFragment = (JCodeFragment)theEObject;
				T result = caseJCodeFragment(jCodeFragment);
				if (result == null) result = caseJAdressableFragment(jCodeFragment);
				if (result == null) result = caseJIdentifiableElement(jCodeFragment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JLOCAL_VARIABLE: {
				JLocalVariable jLocalVariable = (JLocalVariable)theEObject;
				T result = caseJLocalVariable(jLocalVariable);
				if (result == null) result = caseJIdentifiableElement(jLocalVariable);
				if (result == null) result = caseJNamedElement(jLocalVariable);
				if (result == null) result = caseJTypedElement(jLocalVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JINTERFACE: {
				JInterface jInterface = (JInterface)theEObject;
				T result = caseJInterface(jInterface);
				if (result == null) result = caseJClassifier(jInterface);
				if (result == null) result = caseJIdentifiableElement(jInterface);
				if (result == null) result = caseJDocumentableElement(jInterface);
				if (result == null) result = caseJTemplate(jInterface);
				if (result == null) result = caseJType(jInterface);
				if (result == null) result = caseJVisibleElement(jInterface);
				if (result == null) result = caseJNamedElement(jInterface);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JCONSTANT: {
				JConstant jConstant = (JConstant)theEObject;
				T result = caseJConstant(jConstant);
				if (result == null) result = caseJField(jConstant);
				if (result == null) result = caseJIdentifiableElement(jConstant);
				if (result == null) result = caseJDocumentableElement(jConstant);
				if (result == null) result = caseJNamedElement(jConstant);
				if (result == null) result = caseJTypedElement(jConstant);
				if (result == null) result = caseJVisibleElement(jConstant);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JARRAY_TYPE: {
				JArrayType jArrayType = (JArrayType)theEObject;
				T result = caseJArrayType(jArrayType);
				if (result == null) result = caseJClass(jArrayType);
				if (result == null) result = caseJTypedElement(jArrayType);
				if (result == null) result = caseJClassifier(jArrayType);
				if (result == null) result = caseJIdentifiableElement(jArrayType);
				if (result == null) result = caseJDocumentableElement(jArrayType);
				if (result == null) result = caseJTemplate(jArrayType);
				if (result == null) result = caseJType(jArrayType);
				if (result == null) result = caseJVisibleElement(jArrayType);
				if (result == null) result = caseJNamedElement(jArrayType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JSIMPLE_TYPE: {
				JSimpleType jSimpleType = (JSimpleType)theEObject;
				T result = caseJSimpleType(jSimpleType);
				if (result == null) result = caseJIdentifiableElement(jSimpleType);
				if (result == null) result = caseJType(jSimpleType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JTEMPLATE_WRAPPER: {
				JTemplateWrapper jTemplateWrapper = (JTemplateWrapper)theEObject;
				T result = caseJTemplateWrapper(jTemplateWrapper);
				if (result == null) result = caseJIdentifiableElement(jTemplateWrapper);
				if (result == null) result = caseJNamedElement(jTemplateWrapper);
				if (result == null) result = caseJType(jTemplateWrapper);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JTEMPLATE_BINDING: {
				JTemplateBinding jTemplateBinding = (JTemplateBinding)theEObject;
				T result = caseJTemplateBinding(jTemplateBinding);
				if (result == null) result = caseJIdentifiableElement(jTemplateBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT: {
				JStructuralCodeFragment jStructuralCodeFragment = (JStructuralCodeFragment)theEObject;
				T result = caseJStructuralCodeFragment(jStructuralCodeFragment);
				if (result == null) result = caseJCodeFragment(jStructuralCodeFragment);
				if (result == null) result = caseJAdressableFragment(jStructuralCodeFragment);
				if (result == null) result = caseJIdentifiableElement(jStructuralCodeFragment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JENUMERATION: {
				JEnumeration jEnumeration = (JEnumeration)theEObject;
				T result = caseJEnumeration(jEnumeration);
				if (result == null) result = caseJClass(jEnumeration);
				if (result == null) result = caseJClassifier(jEnumeration);
				if (result == null) result = caseJIdentifiableElement(jEnumeration);
				if (result == null) result = caseJDocumentableElement(jEnumeration);
				if (result == null) result = caseJTemplate(jEnumeration);
				if (result == null) result = caseJType(jEnumeration);
				if (result == null) result = caseJVisibleElement(jEnumeration);
				if (result == null) result = caseJNamedElement(jEnumeration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case JavaModelPackage.JENUMERATION_LITERAL: {
				JEnumerationLiteral jEnumerationLiteral = (JEnumerationLiteral)theEObject;
				T result = caseJEnumerationLiteral(jEnumerationLiteral);
				if (result == null) result = caseJIdentifiableElement(jEnumerationLiteral);
				if (result == null) result = caseJNamedElement(jEnumerationLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JProject</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JProject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJProject(JProject object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JNamed Element</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JNamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJNamedElement(JNamedElement object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JIdentifiable Element</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JIdentifiable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJIdentifiableElement(JIdentifiableElement object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JPackage</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JPackage</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJPackage(JPackage object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JClass</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JClass</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJClass(JClass object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JVisible Element</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JVisible Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJVisibleElement(JVisibleElement object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JType</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JType</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJType(JType object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JTemplate</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JTemplate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJTemplate(JTemplate object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JGeneric Type</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JGeneric Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJGenericType(JGenericType object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JDocumentable Element</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JDocumentable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJDocumentableElement(JDocumentableElement object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>Java Doc</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Doc</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJavaDoc(JavaDoc object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JClassifier</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JClassifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJClassifier(JClassifier object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JField</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JField</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJField(JField object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JTyped Element</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JTyped Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJTypedElement(JTypedElement object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JMethod</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JMethod</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJMethod(JMethod object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JParameter</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JParameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJParameter(JParameter object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JCode Block</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JCode Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJCodeBlock(JCodeBlock object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JAdressable Fragment</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JAdressable Fragment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJAdressableFragment(JAdressableFragment object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JCode Fragment</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JCode Fragment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJCodeFragment(JCodeFragment object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JLocal Variable</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JLocal Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJLocalVariable(JLocalVariable object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JInterface</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JInterface</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJInterface(JInterface object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JConstant</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JConstant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJConstant(JConstant object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JArray Type</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JArray Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJArrayType(JArrayType object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JSimple Type</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JSimple Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJSimpleType(JSimpleType object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JTemplate Wrapper</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JTemplate Wrapper</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJTemplateWrapper(JTemplateWrapper object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JTemplate Binding</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JTemplate Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJTemplateBinding(JTemplateBinding object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JStructural Code Fragment</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JStructural Code Fragment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJStructuralCodeFragment(JStructuralCodeFragment object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JEnumeration</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JEnumeration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJEnumeration(JEnumeration object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>JEnumeration Literal</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JEnumeration Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public T caseJEnumerationLiteral(JEnumerationLiteral object)
  {
		return null;
	}

  /**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
  @Override
		public T defaultCase(EObject object)
  {
		return null;
	}

} //JavaModelSwitch
