/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.sidiff.javaast.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.javaast.model.JavaModelPackage
 * @generated
 */
public class JavaModelAdapterFactory extends AdapterFactoryImpl
{
  /**
	 * The cached model package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected static JavaModelPackage modelPackage;

  /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JavaModelAdapterFactory()
  {
		if (modelPackage == null) {
			modelPackage = JavaModelPackage.eINSTANCE;
		}
	}

  /**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
  @Override
  public boolean isFactoryForType(Object object)
  {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

  /**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected JavaModelSwitch<Adapter> modelSwitch =
    new JavaModelSwitch<Adapter>() {
			@Override
			public Adapter caseJProject(JProject object) {
				return createJProjectAdapter();
			}
			@Override
			public Adapter caseJNamedElement(JNamedElement object) {
				return createJNamedElementAdapter();
			}
			@Override
			public Adapter caseJIdentifiableElement(JIdentifiableElement object) {
				return createJIdentifiableElementAdapter();
			}
			@Override
			public Adapter caseJPackage(JPackage object) {
				return createJPackageAdapter();
			}
			@Override
			public Adapter caseJClass(JClass object) {
				return createJClassAdapter();
			}
			@Override
			public Adapter caseJVisibleElement(JVisibleElement object) {
				return createJVisibleElementAdapter();
			}
			@Override
			public Adapter caseJType(JType object) {
				return createJTypeAdapter();
			}
			@Override
			public Adapter caseJTemplate(JTemplate object) {
				return createJTemplateAdapter();
			}
			@Override
			public Adapter caseJGenericType(JGenericType object) {
				return createJGenericTypeAdapter();
			}
			@Override
			public Adapter caseJDocumentableElement(JDocumentableElement object) {
				return createJDocumentableElementAdapter();
			}
			@Override
			public Adapter caseJavaDoc(JavaDoc object) {
				return createJavaDocAdapter();
			}
			@Override
			public Adapter caseJClassifier(JClassifier object) {
				return createJClassifierAdapter();
			}
			@Override
			public Adapter caseJField(JField object) {
				return createJFieldAdapter();
			}
			@Override
			public Adapter caseJTypedElement(JTypedElement object) {
				return createJTypedElementAdapter();
			}
			@Override
			public Adapter caseJMethod(JMethod object) {
				return createJMethodAdapter();
			}
			@Override
			public Adapter caseJParameter(JParameter object) {
				return createJParameterAdapter();
			}
			@Override
			public Adapter caseJCodeBlock(JCodeBlock object) {
				return createJCodeBlockAdapter();
			}
			@Override
			public Adapter caseJAdressableFragment(JAdressableFragment object) {
				return createJAdressableFragmentAdapter();
			}
			@Override
			public Adapter caseJCodeFragment(JCodeFragment object) {
				return createJCodeFragmentAdapter();
			}
			@Override
			public Adapter caseJLocalVariable(JLocalVariable object) {
				return createJLocalVariableAdapter();
			}
			@Override
			public Adapter caseJInterface(JInterface object) {
				return createJInterfaceAdapter();
			}
			@Override
			public Adapter caseJConstant(JConstant object) {
				return createJConstantAdapter();
			}
			@Override
			public Adapter caseJArrayType(JArrayType object) {
				return createJArrayTypeAdapter();
			}
			@Override
			public Adapter caseJSimpleType(JSimpleType object) {
				return createJSimpleTypeAdapter();
			}
			@Override
			public Adapter caseJTemplateWrapper(JTemplateWrapper object) {
				return createJTemplateWrapperAdapter();
			}
			@Override
			public Adapter caseJTemplateBinding(JTemplateBinding object) {
				return createJTemplateBindingAdapter();
			}
			@Override
			public Adapter caseJStructuralCodeFragment(JStructuralCodeFragment object) {
				return createJStructuralCodeFragmentAdapter();
			}
			@Override
			public Adapter caseJEnumeration(JEnumeration object) {
				return createJEnumerationAdapter();
			}
			@Override
			public Adapter caseJEnumerationLiteral(JEnumerationLiteral object) {
				return createJEnumerationLiteralAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

  /**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
  @Override
  public Adapter createAdapter(Notifier target)
  {
		return modelSwitch.doSwitch((EObject)target);
	}


  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JProject <em>JProject</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JProject
	 * @generated
	 */
  public Adapter createJProjectAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JNamedElement <em>JNamed Element</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JNamedElement
	 * @generated
	 */
  public Adapter createJNamedElementAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JIdentifiableElement <em>JIdentifiable Element</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JIdentifiableElement
	 * @generated
	 */
  public Adapter createJIdentifiableElementAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JPackage <em>JPackage</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JPackage
	 * @generated
	 */
  public Adapter createJPackageAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JClass <em>JClass</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JClass
	 * @generated
	 */
  public Adapter createJClassAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JVisibleElement <em>JVisible Element</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JVisibleElement
	 * @generated
	 */
  public Adapter createJVisibleElementAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JType <em>JType</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JType
	 * @generated
	 */
  public Adapter createJTypeAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JTemplate <em>JTemplate</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JTemplate
	 * @generated
	 */
  public Adapter createJTemplateAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JGenericType <em>JGeneric Type</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JGenericType
	 * @generated
	 */
  public Adapter createJGenericTypeAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JDocumentableElement <em>JDocumentable Element</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JDocumentableElement
	 * @generated
	 */
  public Adapter createJDocumentableElementAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JavaDoc <em>Java Doc</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JavaDoc
	 * @generated
	 */
  public Adapter createJavaDocAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JClassifier <em>JClassifier</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JClassifier
	 * @generated
	 */
  public Adapter createJClassifierAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JField <em>JField</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JField
	 * @generated
	 */
  public Adapter createJFieldAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JTypedElement <em>JTyped Element</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JTypedElement
	 * @generated
	 */
  public Adapter createJTypedElementAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JMethod <em>JMethod</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JMethod
	 * @generated
	 */
  public Adapter createJMethodAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JParameter <em>JParameter</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JParameter
	 * @generated
	 */
  public Adapter createJParameterAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JCodeBlock <em>JCode Block</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JCodeBlock
	 * @generated
	 */
  public Adapter createJCodeBlockAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JAdressableFragment <em>JAdressable Fragment</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JAdressableFragment
	 * @generated
	 */
  public Adapter createJAdressableFragmentAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JCodeFragment <em>JCode Fragment</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JCodeFragment
	 * @generated
	 */
  public Adapter createJCodeFragmentAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JLocalVariable <em>JLocal Variable</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JLocalVariable
	 * @generated
	 */
  public Adapter createJLocalVariableAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JInterface <em>JInterface</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JInterface
	 * @generated
	 */
  public Adapter createJInterfaceAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JConstant <em>JConstant</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JConstant
	 * @generated
	 */
  public Adapter createJConstantAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JArrayType <em>JArray Type</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JArrayType
	 * @generated
	 */
  public Adapter createJArrayTypeAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JSimpleType <em>JSimple Type</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JSimpleType
	 * @generated
	 */
  public Adapter createJSimpleTypeAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JTemplateWrapper <em>JTemplate Wrapper</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JTemplateWrapper
	 * @generated
	 */
  public Adapter createJTemplateWrapperAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JTemplateBinding <em>JTemplate Binding</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JTemplateBinding
	 * @generated
	 */
  public Adapter createJTemplateBindingAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JStructuralCodeFragment <em>JStructural Code Fragment</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JStructuralCodeFragment
	 * @generated
	 */
  public Adapter createJStructuralCodeFragmentAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JEnumeration <em>JEnumeration</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JEnumeration
	 * @generated
	 */
  public Adapter createJEnumerationAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for an object of class '{@link org.sidiff.javaast.model.JEnumerationLiteral <em>JEnumeration Literal</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.javaast.model.JEnumerationLiteral
	 * @generated
	 */
  public Adapter createJEnumerationLiteralAdapter()
  {
		return null;
	}

  /**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
  public Adapter createEObjectAdapter()
  {
		return null;
	}

} //JavaModelAdapterFactory
