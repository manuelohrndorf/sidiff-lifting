/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.sidiff.javaast.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JavaModelFactoryImpl extends EFactoryImpl implements JavaModelFactory
{
  /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static JavaModelFactory init()
  {
		try {
			JavaModelFactory theJavaModelFactory = (JavaModelFactory)EPackage.Registry.INSTANCE.getEFactory(JavaModelPackage.eNS_URI);
			if (theJavaModelFactory != null) {
				return theJavaModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new JavaModelFactoryImpl();
	}

  /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JavaModelFactoryImpl()
  {
		super();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public EObject create(EClass eClass)
  {
		switch (eClass.getClassifierID()) {
			case JavaModelPackage.JPROJECT: return createJProject();
			case JavaModelPackage.JPACKAGE: return createJPackage();
			case JavaModelPackage.JCLASS: return createJClass();
			case JavaModelPackage.JGENERIC_TYPE: return createJGenericType();
			case JavaModelPackage.JAVA_DOC: return createJavaDoc();
			case JavaModelPackage.JFIELD: return createJField();
			case JavaModelPackage.JMETHOD: return createJMethod();
			case JavaModelPackage.JPARAMETER: return createJParameter();
			case JavaModelPackage.JCODE_BLOCK: return createJCodeBlock();
			case JavaModelPackage.JCODE_FRAGMENT: return createJCodeFragment();
			case JavaModelPackage.JLOCAL_VARIABLE: return createJLocalVariable();
			case JavaModelPackage.JINTERFACE: return createJInterface();
			case JavaModelPackage.JCONSTANT: return createJConstant();
			case JavaModelPackage.JARRAY_TYPE: return createJArrayType();
			case JavaModelPackage.JSIMPLE_TYPE: return createJSimpleType();
			case JavaModelPackage.JTEMPLATE_WRAPPER: return createJTemplateWrapper();
			case JavaModelPackage.JTEMPLATE_BINDING: return createJTemplateBinding();
			case JavaModelPackage.JSTRUCTURAL_CODE_FRAGMENT: return createJStructuralCodeFragment();
			case JavaModelPackage.JENUMERATION: return createJEnumeration();
			case JavaModelPackage.JENUMERATION_LITERAL: return createJEnumerationLiteral();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue)
  {
		switch (eDataType.getClassifierID()) {
			case JavaModelPackage.JVISIBILITY_KIND:
				return createJVisibilityKindFromString(eDataType, initialValue);
			case JavaModelPackage.JSIMPLE_TYPE_KIND:
				return createJSimpleTypeKindFromString(eDataType, initialValue);
			case JavaModelPackage.STRUCTURAL_FRAGMENT_TYPE:
				return createStructuralFragmentTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue)
  {
		switch (eDataType.getClassifierID()) {
			case JavaModelPackage.JVISIBILITY_KIND:
				return convertJVisibilityKindToString(eDataType, instanceValue);
			case JavaModelPackage.JSIMPLE_TYPE_KIND:
				return convertJSimpleTypeKindToString(eDataType, instanceValue);
			case JavaModelPackage.STRUCTURAL_FRAGMENT_TYPE:
				return convertStructuralFragmentTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JProject createJProject()
  {
		JProjectImpl jProject = new JProjectImpl();
		return jProject;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JPackage createJPackage()
  {
		JPackageImpl jPackage = new JPackageImpl();
		return jPackage;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JClass createJClass()
  {
		JClassImpl jClass = new JClassImpl();
		return jClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JGenericType createJGenericType()
  {
		JGenericTypeImpl jGenericType = new JGenericTypeImpl();
		return jGenericType;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JavaDoc createJavaDoc()
  {
		JavaDocImpl javaDoc = new JavaDocImpl();
		return javaDoc;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JField createJField()
  {
		JFieldImpl jField = new JFieldImpl();
		return jField;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JMethod createJMethod()
  {
		JMethodImpl jMethod = new JMethodImpl();
		return jMethod;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JParameter createJParameter()
  {
		JParameterImpl jParameter = new JParameterImpl();
		return jParameter;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JCodeBlock createJCodeBlock()
  {
		JCodeBlockImpl jCodeBlock = new JCodeBlockImpl();
		return jCodeBlock;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JCodeFragment createJCodeFragment()
  {
		JCodeFragmentImpl jCodeFragment = new JCodeFragmentImpl();
		return jCodeFragment;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JLocalVariable createJLocalVariable()
  {
		JLocalVariableImpl jLocalVariable = new JLocalVariableImpl();
		return jLocalVariable;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JInterface createJInterface()
  {
		JInterfaceImpl jInterface = new JInterfaceImpl();
		return jInterface;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JConstant createJConstant()
  {
		JConstantImpl jConstant = new JConstantImpl();
		return jConstant;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JArrayType createJArrayType()
  {
		JArrayTypeImpl jArrayType = new JArrayTypeImpl();
		return jArrayType;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JSimpleType createJSimpleType()
  {
		JSimpleTypeImpl jSimpleType = new JSimpleTypeImpl();
		return jSimpleType;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JTemplateWrapper createJTemplateWrapper()
  {
		JTemplateWrapperImpl jTemplateWrapper = new JTemplateWrapperImpl();
		return jTemplateWrapper;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JTemplateBinding createJTemplateBinding()
  {
		JTemplateBindingImpl jTemplateBinding = new JTemplateBindingImpl();
		return jTemplateBinding;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JStructuralCodeFragment createJStructuralCodeFragment()
  {
		JStructuralCodeFragmentImpl jStructuralCodeFragment = new JStructuralCodeFragmentImpl();
		return jStructuralCodeFragment;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JEnumeration createJEnumeration()
  {
		JEnumerationImpl jEnumeration = new JEnumerationImpl();
		return jEnumeration;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JEnumerationLiteral createJEnumerationLiteral()
  {
		JEnumerationLiteralImpl jEnumerationLiteral = new JEnumerationLiteralImpl();
		return jEnumerationLiteral;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JVisibilityKind createJVisibilityKindFromString(EDataType eDataType, String initialValue)
  {
		JVisibilityKind result = JVisibilityKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String convertJVisibilityKindToString(EDataType eDataType, Object instanceValue)
  {
		return instanceValue == null ? null : instanceValue.toString();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JSimpleTypeKind createJSimpleTypeKindFromString(EDataType eDataType, String initialValue)
  {
		JSimpleTypeKind result = JSimpleTypeKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String convertJSimpleTypeKindToString(EDataType eDataType, Object instanceValue)
  {
		return instanceValue == null ? null : instanceValue.toString();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public StructuralFragmentType createStructuralFragmentTypeFromString(EDataType eDataType, String initialValue)
  {
		StructuralFragmentType result = StructuralFragmentType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String convertStructuralFragmentTypeToString(EDataType eDataType, Object instanceValue)
  {
		return instanceValue == null ? null : instanceValue.toString();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JavaModelPackage getJavaModelPackage()
  {
		return (JavaModelPackage)getEPackage();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
  @Deprecated
  public static JavaModelPackage getPackage()
  {
		return JavaModelPackage.eINSTANCE;
	}

} //JavaModelFactoryImpl
