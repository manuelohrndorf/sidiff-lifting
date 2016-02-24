/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.javaast.model.JavaModelPackage
 * @generated
 */
public interface JavaModelFactory extends EFactory
{
  /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  JavaModelFactory eINSTANCE = org.sidiff.javaast.model.impl.JavaModelFactoryImpl.init();

  /**
	 * Returns a new object of class '<em>JProject</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JProject</em>'.
	 * @generated
	 */
  JProject createJProject();

  /**
	 * Returns a new object of class '<em>JPackage</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JPackage</em>'.
	 * @generated
	 */
  JPackage createJPackage();

  /**
	 * Returns a new object of class '<em>JClass</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JClass</em>'.
	 * @generated
	 */
  JClass createJClass();

  /**
	 * Returns a new object of class '<em>JGeneric Type</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JGeneric Type</em>'.
	 * @generated
	 */
  JGenericType createJGenericType();

  /**
	 * Returns a new object of class '<em>Java Doc</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Doc</em>'.
	 * @generated
	 */
  JavaDoc createJavaDoc();

  /**
	 * Returns a new object of class '<em>JField</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JField</em>'.
	 * @generated
	 */
  JField createJField();

  /**
	 * Returns a new object of class '<em>JMethod</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JMethod</em>'.
	 * @generated
	 */
  JMethod createJMethod();

  /**
	 * Returns a new object of class '<em>JParameter</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JParameter</em>'.
	 * @generated
	 */
  JParameter createJParameter();

  /**
	 * Returns a new object of class '<em>JCode Block</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JCode Block</em>'.
	 * @generated
	 */
  JCodeBlock createJCodeBlock();

  /**
	 * Returns a new object of class '<em>JCode Fragment</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JCode Fragment</em>'.
	 * @generated
	 */
  JCodeFragment createJCodeFragment();

  /**
	 * Returns a new object of class '<em>JLocal Variable</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JLocal Variable</em>'.
	 * @generated
	 */
  JLocalVariable createJLocalVariable();

  /**
	 * Returns a new object of class '<em>JInterface</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JInterface</em>'.
	 * @generated
	 */
  JInterface createJInterface();

  /**
	 * Returns a new object of class '<em>JConstant</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JConstant</em>'.
	 * @generated
	 */
  JConstant createJConstant();

  /**
	 * Returns a new object of class '<em>JArray Type</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JArray Type</em>'.
	 * @generated
	 */
  JArrayType createJArrayType();

  /**
	 * Returns a new object of class '<em>JSimple Type</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSimple Type</em>'.
	 * @generated
	 */
  JSimpleType createJSimpleType();

  /**
	 * Returns a new object of class '<em>JTemplate Wrapper</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JTemplate Wrapper</em>'.
	 * @generated
	 */
  JTemplateWrapper createJTemplateWrapper();

  /**
	 * Returns a new object of class '<em>JTemplate Binding</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JTemplate Binding</em>'.
	 * @generated
	 */
  JTemplateBinding createJTemplateBinding();

  /**
	 * Returns a new object of class '<em>JStructural Code Fragment</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JStructural Code Fragment</em>'.
	 * @generated
	 */
  JStructuralCodeFragment createJStructuralCodeFragment();

  /**
	 * Returns a new object of class '<em>JEnumeration</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JEnumeration</em>'.
	 * @generated
	 */
  JEnumeration createJEnumeration();

  /**
	 * Returns a new object of class '<em>JEnumeration Literal</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>JEnumeration Literal</em>'.
	 * @generated
	 */
  JEnumerationLiteral createJEnumerationLiteral();

  /**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
  JavaModelPackage getJavaModelPackage();

} //JavaModelFactory
