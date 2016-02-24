/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.javaast.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.sidiff.javaast.model.JAdressableFragment;
import org.sidiff.javaast.model.JArrayType;
import org.sidiff.javaast.model.JClass;
import org.sidiff.javaast.model.JClassifier;
import org.sidiff.javaast.model.JCodeBlock;
import org.sidiff.javaast.model.JCodeFragment;
import org.sidiff.javaast.model.JConstant;
import org.sidiff.javaast.model.JDocumentableElement;
import org.sidiff.javaast.model.JEnumeration;
import org.sidiff.javaast.model.JEnumerationLiteral;
import org.sidiff.javaast.model.JField;
import org.sidiff.javaast.model.JGenericType;
import org.sidiff.javaast.model.JIdentifiableElement;
import org.sidiff.javaast.model.JInterface;
import org.sidiff.javaast.model.JLocalVariable;
import org.sidiff.javaast.model.JMethod;
import org.sidiff.javaast.model.JNamedElement;
import org.sidiff.javaast.model.JPackage;
import org.sidiff.javaast.model.JParameter;
import org.sidiff.javaast.model.JProject;
import org.sidiff.javaast.model.JSimpleType;
import org.sidiff.javaast.model.JSimpleTypeKind;
import org.sidiff.javaast.model.JStructuralCodeFragment;
import org.sidiff.javaast.model.JTemplate;
import org.sidiff.javaast.model.JTemplateBinding;
import org.sidiff.javaast.model.JTemplateWrapper;
import org.sidiff.javaast.model.JType;
import org.sidiff.javaast.model.JTypedElement;
import org.sidiff.javaast.model.JVisibilityKind;
import org.sidiff.javaast.model.JVisibleElement;
import org.sidiff.javaast.model.JavaDoc;
import org.sidiff.javaast.model.JavaModelFactory;
import org.sidiff.javaast.model.JavaModelPackage;
import org.sidiff.javaast.model.StructuralFragmentType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class JavaModelPackageImpl extends EPackageImpl implements JavaModelPackage
{
  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jProjectEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jNamedElementEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jIdentifiableElementEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jPackageEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jClassEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jVisibleElementEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jTypeEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jTemplateEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jGenericTypeEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jDocumentableElementEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass javaDocEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jClassifierEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jFieldEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jTypedElementEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jMethodEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jParameterEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jCodeBlockEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jAdressableFragmentEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jCodeFragmentEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jLocalVariableEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jInterfaceEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jConstantEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jArrayTypeEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jSimpleTypeEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jTemplateWrapperEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jTemplateBindingEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jStructuralCodeFragmentEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jEnumerationEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EClass jEnumerationLiteralEClass = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EEnum jVisibilityKindEEnum = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EEnum jSimpleTypeKindEEnum = null;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private EEnum structuralFragmentTypeEEnum = null;

  /**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.sidiff.javaast.model.JavaModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
  private JavaModelPackageImpl()
  {
		super(eNS_URI, JavaModelFactory.eINSTANCE);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private static boolean isInited = false;

  /**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link JavaModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
  public static JavaModelPackage init()
  {
		if (isInited) return (JavaModelPackage)EPackage.Registry.INSTANCE.getEPackage(JavaModelPackage.eNS_URI);

		// Obtain or create and register package
		JavaModelPackageImpl theJavaModelPackage = (JavaModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof JavaModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new JavaModelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theJavaModelPackage.createPackageContents();

		// Initialize created meta-data
		theJavaModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theJavaModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(JavaModelPackage.eNS_URI, theJavaModelPackage);
		return theJavaModelPackage;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJProject()
  {
		return jProjectEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJProject_Packages()
  {
		return (EReference)jProjectEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJProject_SimpleTypes()
  {
		return (EReference)jProjectEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJNamedElement()
  {
		return jNamedElementEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJNamedElement_Name()
  {
		return (EAttribute)jNamedElementEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJNamedElement_QualifiedName() {
		return (EAttribute)jNamedElementEClass.getEStructuralFeatures().get(1);
	}

		/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJIdentifiableElement()
  {
		return jIdentifiableElementEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJIdentifiableElement_Id()
  {
		return (EAttribute)jIdentifiableElementEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJIdentifiableElement_Compileable()
  {
		return (EAttribute)jIdentifiableElementEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJPackage()
  {
		return jPackageEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJPackage_SubPackages()
  {
		return (EReference)jPackageEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJPackage_Classes()
  {
		return (EReference)jPackageEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJPackage_Interfaces()
  {
		return (EReference)jPackageEClass.getEStructuralFeatures().get(2);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJClass()
  {
		return jClassEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJClass_Fields()
  {
		return (EReference)jClassEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJClass_IsAbstract()
  {
		return (EAttribute)jClassEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJClass_IsFinal()
  {
		return (EAttribute)jClassEClass.getEStructuralFeatures().get(2);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJClass_Methods()
  {
		return (EReference)jClassEClass.getEStructuralFeatures().get(3);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJClass_SuperClass()
  {
		return (EReference)jClassEClass.getEStructuralFeatures().get(4);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJClass_SubClasses()
  {
		return (EReference)jClassEClass.getEStructuralFeatures().get(5);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJClass_ImplementedInterfaces()
  {
		return (EReference)jClassEClass.getEStructuralFeatures().get(6);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJClass_StaticInitializationBlock()
  {
		return (EReference)jClassEClass.getEStructuralFeatures().get(7);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJVisibleElement()
  {
		return jVisibleElementEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJVisibleElement_VisibilityKind()
  {
		return (EAttribute)jVisibleElementEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJType()
  {
		return jTypeEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJType_IsExternal()
  {
		return (EAttribute)jTypeEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJTemplate()
  {
		return jTemplateEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJTemplate_GenericTypes()
  {
		return (EReference)jTemplateEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJGenericType()
  {
		return jGenericTypeEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJDocumentableElement()
  {
		return jDocumentableElementEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJDocumentableElement_JavaDoc()
  {
		return (EReference)jDocumentableElementEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJavaDoc()
  {
		return javaDocEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJavaDoc_Text()
  {
		return (EAttribute)javaDocEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJClassifier()
  {
		return jClassifierEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJClassifier_InnerClassifiers()
  {
		return (EReference)jClassifierEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJField()
  {
		return jFieldEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJField_IsFinal()
  {
		return (EAttribute)jFieldEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJField_IsStatic()
  {
		return (EAttribute)jFieldEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJField_AccessedBy()
  {
		return (EReference)jFieldEClass.getEStructuralFeatures().get(2);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJField_IsTransient()
  {
		return (EAttribute)jFieldEClass.getEStructuralFeatures().get(3);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJField_Initialization()
  {
		return (EReference)jFieldEClass.getEStructuralFeatures().get(4);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJTypedElement()
  {
		return jTypedElementEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJTypedElement_Type()
  {
		return (EReference)jTypedElementEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJMethod()
  {
		return jMethodEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJMethod_IsAbstract()
  {
		return (EAttribute)jMethodEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJMethod_IsFinal()
  {
		return (EAttribute)jMethodEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJMethod_IsStatic()
  {
		return (EAttribute)jMethodEClass.getEStructuralFeatures().get(2);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJMethod_IsConstructor()
  {
		return (EAttribute)jMethodEClass.getEStructuralFeatures().get(3);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJMethod_IsSynchronized()
  {
		return (EAttribute)jMethodEClass.getEStructuralFeatures().get(4);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJMethod_Parameters()
  {
		return (EReference)jMethodEClass.getEStructuralFeatures().get(5);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJMethod_Calls()
  {
		return (EReference)jMethodEClass.getEStructuralFeatures().get(6);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJMethod_CalledBy()
  {
		return (EReference)jMethodEClass.getEStructuralFeatures().get(7);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJMethod_RaisedException()
  {
		return (EReference)jMethodEClass.getEStructuralFeatures().get(8);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJMethod_Body()
  {
		return (EReference)jMethodEClass.getEStructuralFeatures().get(9);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJMethod_Accesses()
  {
		return (EReference)jMethodEClass.getEStructuralFeatures().get(10);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJParameter()
  {
		return jParameterEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJParameter_IsFinal()
  {
		return (EAttribute)jParameterEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJCodeBlock()
  {
		return jCodeBlockEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJCodeBlock_CodeFragments()
  {
		return (EReference)jCodeBlockEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJCodeBlock_SubBlocks()
  {
		return (EReference)jCodeBlockEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJCodeBlock_DefinedVariables()
  {
		return (EReference)jCodeBlockEClass.getEStructuralFeatures().get(2);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJAdressableFragment()
  {
		return jAdressableFragmentEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJAdressableFragment_StartByte()
  {
		return (EAttribute)jAdressableFragmentEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJAdressableFragment_ByteLength()
  {
		return (EAttribute)jAdressableFragmentEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJCodeFragment()
  {
		return jCodeFragmentEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJCodeFragment_UsedTypes()
  {
		return (EReference)jCodeFragmentEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJCodeFragment_CalledMethods()
  {
		return (EReference)jCodeFragmentEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJCodeFragment_Code()
  {
		return (EAttribute)jCodeFragmentEClass.getEStructuralFeatures().get(2);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJCodeFragment_UsedFields()
  {
		return (EReference)jCodeFragmentEClass.getEStructuralFeatures().get(3);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJCodeFragment_UsedParameters()
  {
		return (EReference)jCodeFragmentEClass.getEStructuralFeatures().get(4);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJCodeFragment_UsedLocalVariables()
  {
		return (EReference)jCodeFragmentEClass.getEStructuralFeatures().get(5);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJLocalVariable()
  {
		return jLocalVariableEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJInterface()
  {
		return jInterfaceEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJInterface_Constants()
  {
		return (EReference)jInterfaceEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJInterface_MethodSignatures()
  {
		return (EReference)jInterfaceEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJInterface_SuperInterfaces()
  {
		return (EReference)jInterfaceEClass.getEStructuralFeatures().get(2);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJInterface_SubInterfaces()
  {
		return (EReference)jInterfaceEClass.getEStructuralFeatures().get(3);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJInterface_ImplementingClasses()
  {
		return (EReference)jInterfaceEClass.getEStructuralFeatures().get(4);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJConstant()
  {
		return jConstantEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJArrayType()
  {
		return jArrayTypeEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJArrayType_ArrayDimensions()
  {
		return (EAttribute)jArrayTypeEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJSimpleType()
  {
		return jSimpleTypeEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJSimpleType_Kind()
  {
		return (EAttribute)jSimpleTypeEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJSimpleType_Name() {
		return (EAttribute)jSimpleTypeEClass.getEStructuralFeatures().get(1);
	}

		/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJTemplateWrapper()
  {
		return jTemplateWrapperEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJTemplateWrapper_Bindings()
  {
		return (EReference)jTemplateWrapperEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJTemplateBinding()
  {
		return jTemplateBindingEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJTemplateBinding_Type()
  {
		return (EReference)jTemplateBindingEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJTemplateBinding_GenericType()
  {
		return (EReference)jTemplateBindingEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJStructuralCodeFragment()
  {
		return jStructuralCodeFragmentEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EAttribute getJStructuralCodeFragment_FragmentType()
  {
		return (EAttribute)jStructuralCodeFragmentEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJStructuralCodeFragment_CodeBlocks()
  {
		return (EReference)jStructuralCodeFragmentEClass.getEStructuralFeatures().get(1);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJEnumeration()
  {
		return jEnumerationEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EReference getJEnumeration_Literals()
  {
		return (EReference)jEnumerationEClass.getEStructuralFeatures().get(0);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EClass getJEnumerationLiteral()
  {
		return jEnumerationLiteralEClass;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EEnum getJVisibilityKind()
  {
		return jVisibilityKindEEnum;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EEnum getJSimpleTypeKind()
  {
		return jSimpleTypeKindEEnum;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EEnum getStructuralFragmentType()
  {
		return structuralFragmentTypeEEnum;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public JavaModelFactory getJavaModelFactory()
  {
		return (JavaModelFactory)getEFactoryInstance();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private boolean isCreated = false;

  /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void createPackageContents()
  {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		jProjectEClass = createEClass(JPROJECT);
		createEReference(jProjectEClass, JPROJECT__PACKAGES);
		createEReference(jProjectEClass, JPROJECT__SIMPLE_TYPES);

		jNamedElementEClass = createEClass(JNAMED_ELEMENT);
		createEAttribute(jNamedElementEClass, JNAMED_ELEMENT__NAME);
		createEAttribute(jNamedElementEClass, JNAMED_ELEMENT__QUALIFIED_NAME);

		jIdentifiableElementEClass = createEClass(JIDENTIFIABLE_ELEMENT);
		createEAttribute(jIdentifiableElementEClass, JIDENTIFIABLE_ELEMENT__ID);
		createEAttribute(jIdentifiableElementEClass, JIDENTIFIABLE_ELEMENT__COMPILEABLE);

		jPackageEClass = createEClass(JPACKAGE);
		createEReference(jPackageEClass, JPACKAGE__SUB_PACKAGES);
		createEReference(jPackageEClass, JPACKAGE__CLASSES);
		createEReference(jPackageEClass, JPACKAGE__INTERFACES);

		jClassEClass = createEClass(JCLASS);
		createEReference(jClassEClass, JCLASS__FIELDS);
		createEAttribute(jClassEClass, JCLASS__IS_ABSTRACT);
		createEAttribute(jClassEClass, JCLASS__IS_FINAL);
		createEReference(jClassEClass, JCLASS__METHODS);
		createEReference(jClassEClass, JCLASS__SUPER_CLASS);
		createEReference(jClassEClass, JCLASS__SUB_CLASSES);
		createEReference(jClassEClass, JCLASS__IMPLEMENTED_INTERFACES);
		createEReference(jClassEClass, JCLASS__STATIC_INITIALIZATION_BLOCK);

		jVisibleElementEClass = createEClass(JVISIBLE_ELEMENT);
		createEAttribute(jVisibleElementEClass, JVISIBLE_ELEMENT__VISIBILITY_KIND);

		jTypeEClass = createEClass(JTYPE);
		createEAttribute(jTypeEClass, JTYPE__IS_EXTERNAL);

		jTemplateEClass = createEClass(JTEMPLATE);
		createEReference(jTemplateEClass, JTEMPLATE__GENERIC_TYPES);

		jGenericTypeEClass = createEClass(JGENERIC_TYPE);

		jDocumentableElementEClass = createEClass(JDOCUMENTABLE_ELEMENT);
		createEReference(jDocumentableElementEClass, JDOCUMENTABLE_ELEMENT__JAVA_DOC);

		javaDocEClass = createEClass(JAVA_DOC);
		createEAttribute(javaDocEClass, JAVA_DOC__TEXT);

		jClassifierEClass = createEClass(JCLASSIFIER);
		createEReference(jClassifierEClass, JCLASSIFIER__INNER_CLASSIFIERS);

		jFieldEClass = createEClass(JFIELD);
		createEAttribute(jFieldEClass, JFIELD__IS_FINAL);
		createEAttribute(jFieldEClass, JFIELD__IS_STATIC);
		createEReference(jFieldEClass, JFIELD__ACCESSED_BY);
		createEAttribute(jFieldEClass, JFIELD__IS_TRANSIENT);
		createEReference(jFieldEClass, JFIELD__INITIALIZATION);

		jTypedElementEClass = createEClass(JTYPED_ELEMENT);
		createEReference(jTypedElementEClass, JTYPED_ELEMENT__TYPE);

		jMethodEClass = createEClass(JMETHOD);
		createEAttribute(jMethodEClass, JMETHOD__IS_ABSTRACT);
		createEAttribute(jMethodEClass, JMETHOD__IS_FINAL);
		createEAttribute(jMethodEClass, JMETHOD__IS_STATIC);
		createEAttribute(jMethodEClass, JMETHOD__IS_CONSTRUCTOR);
		createEAttribute(jMethodEClass, JMETHOD__IS_SYNCHRONIZED);
		createEReference(jMethodEClass, JMETHOD__PARAMETERS);
		createEReference(jMethodEClass, JMETHOD__CALLS);
		createEReference(jMethodEClass, JMETHOD__CALLED_BY);
		createEReference(jMethodEClass, JMETHOD__RAISED_EXCEPTION);
		createEReference(jMethodEClass, JMETHOD__BODY);
		createEReference(jMethodEClass, JMETHOD__ACCESSES);

		jParameterEClass = createEClass(JPARAMETER);
		createEAttribute(jParameterEClass, JPARAMETER__IS_FINAL);

		jCodeBlockEClass = createEClass(JCODE_BLOCK);
		createEReference(jCodeBlockEClass, JCODE_BLOCK__CODE_FRAGMENTS);
		createEReference(jCodeBlockEClass, JCODE_BLOCK__SUB_BLOCKS);
		createEReference(jCodeBlockEClass, JCODE_BLOCK__DEFINED_VARIABLES);

		jAdressableFragmentEClass = createEClass(JADRESSABLE_FRAGMENT);
		createEAttribute(jAdressableFragmentEClass, JADRESSABLE_FRAGMENT__START_BYTE);
		createEAttribute(jAdressableFragmentEClass, JADRESSABLE_FRAGMENT__BYTE_LENGTH);

		jCodeFragmentEClass = createEClass(JCODE_FRAGMENT);
		createEReference(jCodeFragmentEClass, JCODE_FRAGMENT__USED_TYPES);
		createEReference(jCodeFragmentEClass, JCODE_FRAGMENT__CALLED_METHODS);
		createEAttribute(jCodeFragmentEClass, JCODE_FRAGMENT__CODE);
		createEReference(jCodeFragmentEClass, JCODE_FRAGMENT__USED_FIELDS);
		createEReference(jCodeFragmentEClass, JCODE_FRAGMENT__USED_PARAMETERS);
		createEReference(jCodeFragmentEClass, JCODE_FRAGMENT__USED_LOCAL_VARIABLES);

		jLocalVariableEClass = createEClass(JLOCAL_VARIABLE);

		jInterfaceEClass = createEClass(JINTERFACE);
		createEReference(jInterfaceEClass, JINTERFACE__CONSTANTS);
		createEReference(jInterfaceEClass, JINTERFACE__METHOD_SIGNATURES);
		createEReference(jInterfaceEClass, JINTERFACE__SUPER_INTERFACES);
		createEReference(jInterfaceEClass, JINTERFACE__SUB_INTERFACES);
		createEReference(jInterfaceEClass, JINTERFACE__IMPLEMENTING_CLASSES);

		jConstantEClass = createEClass(JCONSTANT);

		jArrayTypeEClass = createEClass(JARRAY_TYPE);
		createEAttribute(jArrayTypeEClass, JARRAY_TYPE__ARRAY_DIMENSIONS);

		jSimpleTypeEClass = createEClass(JSIMPLE_TYPE);
		createEAttribute(jSimpleTypeEClass, JSIMPLE_TYPE__KIND);
		createEAttribute(jSimpleTypeEClass, JSIMPLE_TYPE__NAME);

		jTemplateWrapperEClass = createEClass(JTEMPLATE_WRAPPER);
		createEReference(jTemplateWrapperEClass, JTEMPLATE_WRAPPER__BINDINGS);

		jTemplateBindingEClass = createEClass(JTEMPLATE_BINDING);
		createEReference(jTemplateBindingEClass, JTEMPLATE_BINDING__TYPE);
		createEReference(jTemplateBindingEClass, JTEMPLATE_BINDING__GENERIC_TYPE);

		jStructuralCodeFragmentEClass = createEClass(JSTRUCTURAL_CODE_FRAGMENT);
		createEAttribute(jStructuralCodeFragmentEClass, JSTRUCTURAL_CODE_FRAGMENT__FRAGMENT_TYPE);
		createEReference(jStructuralCodeFragmentEClass, JSTRUCTURAL_CODE_FRAGMENT__CODE_BLOCKS);

		jEnumerationEClass = createEClass(JENUMERATION);
		createEReference(jEnumerationEClass, JENUMERATION__LITERALS);

		jEnumerationLiteralEClass = createEClass(JENUMERATION_LITERAL);

		// Create enums
		jVisibilityKindEEnum = createEEnum(JVISIBILITY_KIND);
		jSimpleTypeKindEEnum = createEEnum(JSIMPLE_TYPE_KIND);
		structuralFragmentTypeEEnum = createEEnum(STRUCTURAL_FRAGMENT_TYPE);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private boolean isInitialized = false;

  /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void initializePackageContents()
  {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		jProjectEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jProjectEClass.getESuperTypes().add(this.getJNamedElement());
		jPackageEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jPackageEClass.getESuperTypes().add(this.getJNamedElement());
		jClassEClass.getESuperTypes().add(this.getJClassifier());
		jClassEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jClassEClass.getESuperTypes().add(this.getJDocumentableElement());
		jClassEClass.getESuperTypes().add(this.getJTemplate());
		jClassEClass.getESuperTypes().add(this.getJType());
		jClassEClass.getESuperTypes().add(this.getJVisibleElement());
		jGenericTypeEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jGenericTypeEClass.getESuperTypes().add(this.getJNamedElement());
		jGenericTypeEClass.getESuperTypes().add(this.getJType());
		javaDocEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jClassifierEClass.getESuperTypes().add(this.getJNamedElement());
		jFieldEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jFieldEClass.getESuperTypes().add(this.getJDocumentableElement());
		jFieldEClass.getESuperTypes().add(this.getJNamedElement());
		jFieldEClass.getESuperTypes().add(this.getJTypedElement());
		jFieldEClass.getESuperTypes().add(this.getJVisibleElement());
		jMethodEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jMethodEClass.getESuperTypes().add(this.getJDocumentableElement());
		jMethodEClass.getESuperTypes().add(this.getJNamedElement());
		jMethodEClass.getESuperTypes().add(this.getJTemplate());
		jMethodEClass.getESuperTypes().add(this.getJTypedElement());
		jMethodEClass.getESuperTypes().add(this.getJVisibleElement());
		jParameterEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jParameterEClass.getESuperTypes().add(this.getJNamedElement());
		jParameterEClass.getESuperTypes().add(this.getJTypedElement());
		jCodeBlockEClass.getESuperTypes().add(this.getJAdressableFragment());
		jCodeBlockEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jCodeFragmentEClass.getESuperTypes().add(this.getJAdressableFragment());
		jCodeFragmentEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jLocalVariableEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jLocalVariableEClass.getESuperTypes().add(this.getJNamedElement());
		jLocalVariableEClass.getESuperTypes().add(this.getJTypedElement());
		jInterfaceEClass.getESuperTypes().add(this.getJClassifier());
		jInterfaceEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jInterfaceEClass.getESuperTypes().add(this.getJDocumentableElement());
		jInterfaceEClass.getESuperTypes().add(this.getJTemplate());
		jInterfaceEClass.getESuperTypes().add(this.getJType());
		jInterfaceEClass.getESuperTypes().add(this.getJVisibleElement());
		jConstantEClass.getESuperTypes().add(this.getJField());
		jArrayTypeEClass.getESuperTypes().add(this.getJClass());
		jArrayTypeEClass.getESuperTypes().add(this.getJTypedElement());
		jSimpleTypeEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jSimpleTypeEClass.getESuperTypes().add(this.getJType());
		jTemplateWrapperEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jTemplateWrapperEClass.getESuperTypes().add(this.getJNamedElement());
		jTemplateWrapperEClass.getESuperTypes().add(this.getJType());
		jTemplateBindingEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jStructuralCodeFragmentEClass.getESuperTypes().add(this.getJCodeFragment());
		jEnumerationEClass.getESuperTypes().add(this.getJClass());
		jEnumerationLiteralEClass.getESuperTypes().add(this.getJIdentifiableElement());
		jEnumerationLiteralEClass.getESuperTypes().add(this.getJNamedElement());

		// Initialize classes and features; add operations and parameters
		initEClass(jProjectEClass, JProject.class, "JProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJProject_Packages(), this.getJPackage(), null, "packages", null, 0, -1, JProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJProject_SimpleTypes(), this.getJType(), null, "simpleTypes", null, 0, -1, JProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jNamedElementEClass, JNamedElement.class, "JNamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJNamedElement_Name(), ecorePackage.getEString(), "name", null, 1, 1, JNamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJNamedElement_QualifiedName(), ecorePackage.getEString(), "qualifiedName", null, 0, 1, JNamedElement.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(jIdentifiableElementEClass, JIdentifiableElement.class, "JIdentifiableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJIdentifiableElement_Id(), ecorePackage.getEString(), "id", "", 1, 1, JIdentifiableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJIdentifiableElement_Compileable(), ecorePackage.getEBoolean(), "compileable", "true", 1, 1, JIdentifiableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jPackageEClass, JPackage.class, "JPackage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJPackage_SubPackages(), this.getJPackage(), null, "subPackages", null, 0, -1, JPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJPackage_Classes(), this.getJClass(), null, "classes", null, 0, -1, JPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJPackage_Interfaces(), this.getJInterface(), null, "interfaces", null, 0, -1, JPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jClassEClass, JClass.class, "JClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJClass_Fields(), this.getJField(), null, "fields", null, 0, -1, JClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJClass_IsAbstract(), ecorePackage.getEBoolean(), "isAbstract", null, 1, 1, JClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJClass_IsFinal(), ecorePackage.getEBoolean(), "isFinal", null, 1, 1, JClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJClass_Methods(), this.getJMethod(), null, "methods", null, 0, -1, JClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJClass_SuperClass(), this.getJClass(), this.getJClass_SubClasses(), "superClass", null, 1, 1, JClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJClass_SubClasses(), this.getJClass(), this.getJClass_SuperClass(), "subClasses", null, 0, -1, JClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJClass_ImplementedInterfaces(), this.getJInterface(), this.getJInterface_ImplementingClasses(), "implementedInterfaces", null, 0, -1, JClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJClass_StaticInitializationBlock(), this.getJCodeBlock(), null, "staticInitializationBlock", null, 0, 1, JClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jVisibleElementEClass, JVisibleElement.class, "JVisibleElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJVisibleElement_VisibilityKind(), this.getJVisibilityKind(), "visibilityKind", null, 1, 1, JVisibleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jTypeEClass, JType.class, "JType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJType_IsExternal(), ecorePackage.getEBoolean(), "isExternal", null, 1, 1, JType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jTemplateEClass, JTemplate.class, "JTemplate", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJTemplate_GenericTypes(), this.getJGenericType(), null, "genericTypes", null, 0, -1, JTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jGenericTypeEClass, JGenericType.class, "JGenericType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jDocumentableElementEClass, JDocumentableElement.class, "JDocumentableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJDocumentableElement_JavaDoc(), this.getJavaDoc(), null, "javaDoc", null, 0, 1, JDocumentableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(javaDocEClass, JavaDoc.class, "JavaDoc", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJavaDoc_Text(), ecorePackage.getEString(), "text", null, 1, 1, JavaDoc.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jClassifierEClass, JClassifier.class, "JClassifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJClassifier_InnerClassifiers(), this.getJClassifier(), null, "innerClassifiers", null, 0, -1, JClassifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jFieldEClass, JField.class, "JField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJField_IsFinal(), ecorePackage.getEBoolean(), "isFinal", null, 1, 1, JField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJField_IsStatic(), ecorePackage.getEBoolean(), "isStatic", null, 1, 1, JField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJField_AccessedBy(), this.getJMethod(), this.getJMethod_Accesses(), "accessedBy", null, 0, -1, JField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJField_IsTransient(), ecorePackage.getEBoolean(), "isTransient", null, 1, 1, JField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJField_Initialization(), this.getJCodeBlock(), null, "initialization", null, 0, 1, JField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jTypedElementEClass, JTypedElement.class, "JTypedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJTypedElement_Type(), this.getJType(), null, "type", null, 1, 1, JTypedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jMethodEClass, JMethod.class, "JMethod", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJMethod_IsAbstract(), ecorePackage.getEBoolean(), "isAbstract", null, 1, 1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJMethod_IsFinal(), ecorePackage.getEBoolean(), "isFinal", null, 1, 1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJMethod_IsStatic(), ecorePackage.getEBoolean(), "isStatic", null, 1, 1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJMethod_IsConstructor(), ecorePackage.getEBoolean(), "isConstructor", null, 1, 1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJMethod_IsSynchronized(), ecorePackage.getEBoolean(), "isSynchronized", null, 1, 1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJMethod_Parameters(), this.getJParameter(), null, "parameters", null, 0, -1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJMethod_Calls(), this.getJMethod(), this.getJMethod_CalledBy(), "calls", null, 0, -1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJMethod_CalledBy(), this.getJMethod(), this.getJMethod_Calls(), "calledBy", null, 0, -1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJMethod_RaisedException(), this.getJClass(), null, "raisedException", null, 0, 1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJMethod_Body(), this.getJCodeBlock(), null, "body", null, 1, 1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJMethod_Accesses(), this.getJField(), this.getJField_AccessedBy(), "accesses", null, 0, -1, JMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jParameterEClass, JParameter.class, "JParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJParameter_IsFinal(), ecorePackage.getEBoolean(), "isFinal", null, 1, 1, JParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jCodeBlockEClass, JCodeBlock.class, "JCodeBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJCodeBlock_CodeFragments(), this.getJCodeFragment(), null, "codeFragments", null, 0, -1, JCodeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJCodeBlock_SubBlocks(), this.getJCodeBlock(), null, "subBlocks", null, 0, -1, JCodeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJCodeBlock_DefinedVariables(), this.getJLocalVariable(), null, "definedVariables", null, 0, -1, JCodeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jAdressableFragmentEClass, JAdressableFragment.class, "JAdressableFragment", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJAdressableFragment_StartByte(), ecorePackage.getEInt(), "startByte", null, 1, 1, JAdressableFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJAdressableFragment_ByteLength(), ecorePackage.getEInt(), "byteLength", null, 1, 1, JAdressableFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jCodeFragmentEClass, JCodeFragment.class, "JCodeFragment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJCodeFragment_UsedTypes(), this.getJType(), null, "usedTypes", null, 0, -1, JCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJCodeFragment_CalledMethods(), this.getJMethod(), null, "calledMethods", null, 0, -1, JCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJCodeFragment_Code(), ecorePackage.getEString(), "code", null, 1, 1, JCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJCodeFragment_UsedFields(), this.getJField(), null, "usedFields", null, 0, -1, JCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJCodeFragment_UsedParameters(), this.getJParameter(), null, "usedParameters", null, 0, -1, JCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJCodeFragment_UsedLocalVariables(), this.getJLocalVariable(), null, "usedLocalVariables", null, 0, -1, JCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jLocalVariableEClass, JLocalVariable.class, "JLocalVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jInterfaceEClass, JInterface.class, "JInterface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJInterface_Constants(), this.getJConstant(), null, "constants", null, 0, -1, JInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJInterface_MethodSignatures(), this.getJMethod(), null, "methodSignatures", null, 0, -1, JInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJInterface_SuperInterfaces(), this.getJInterface(), this.getJInterface_SubInterfaces(), "superInterfaces", null, 0, -1, JInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJInterface_SubInterfaces(), this.getJInterface(), this.getJInterface_SuperInterfaces(), "subInterfaces", null, 0, -1, JInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJInterface_ImplementingClasses(), this.getJClass(), this.getJClass_ImplementedInterfaces(), "implementingClasses", null, 0, -1, JInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jConstantEClass, JConstant.class, "JConstant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(jArrayTypeEClass, JArrayType.class, "JArrayType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJArrayType_ArrayDimensions(), ecorePackage.getEInt(), "arrayDimensions", null, 1, 1, JArrayType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jSimpleTypeEClass, JSimpleType.class, "JSimpleType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJSimpleType_Kind(), this.getJSimpleTypeKind(), "kind", null, 1, 1, JSimpleType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getJSimpleType_Name(), ecorePackage.getEString(), "name", null, 0, 1, JSimpleType.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(jTemplateWrapperEClass, JTemplateWrapper.class, "JTemplateWrapper", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJTemplateWrapper_Bindings(), this.getJTemplateBinding(), null, "bindings", null, 0, -1, JTemplateWrapper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jTemplateBindingEClass, JTemplateBinding.class, "JTemplateBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJTemplateBinding_Type(), this.getJType(), null, "type", null, 1, 1, JTemplateBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJTemplateBinding_GenericType(), this.getJGenericType(), null, "genericType", null, 1, 1, JTemplateBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jStructuralCodeFragmentEClass, JStructuralCodeFragment.class, "JStructuralCodeFragment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJStructuralCodeFragment_FragmentType(), this.getStructuralFragmentType(), "fragmentType", null, 1, 1, JStructuralCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getJStructuralCodeFragment_CodeBlocks(), this.getJCodeBlock(), null, "codeBlocks", null, 0, -1, JStructuralCodeFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jEnumerationEClass, JEnumeration.class, "JEnumeration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJEnumeration_Literals(), this.getJEnumerationLiteral(), null, "literals", null, 0, -1, JEnumeration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(jEnumerationLiteralEClass, JEnumerationLiteral.class, "JEnumerationLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(jVisibilityKindEEnum, JVisibilityKind.class, "JVisibilityKind");
		addEEnumLiteral(jVisibilityKindEEnum, JVisibilityKind.PUBLIC);
		addEEnumLiteral(jVisibilityKindEEnum, JVisibilityKind.PRIVATE);
		addEEnumLiteral(jVisibilityKindEEnum, JVisibilityKind.PROTECTED);
		addEEnumLiteral(jVisibilityKindEEnum, JVisibilityKind.PACKAGE);

		initEEnum(jSimpleTypeKindEEnum, JSimpleTypeKind.class, "JSimpleTypeKind");
		addEEnumLiteral(jSimpleTypeKindEEnum, JSimpleTypeKind.BYTE);
		addEEnumLiteral(jSimpleTypeKindEEnum, JSimpleTypeKind.SHORT);
		addEEnumLiteral(jSimpleTypeKindEEnum, JSimpleTypeKind.CHAR);
		addEEnumLiteral(jSimpleTypeKindEEnum, JSimpleTypeKind.INT);
		addEEnumLiteral(jSimpleTypeKindEEnum, JSimpleTypeKind.LONG);
		addEEnumLiteral(jSimpleTypeKindEEnum, JSimpleTypeKind.FLOAT);
		addEEnumLiteral(jSimpleTypeKindEEnum, JSimpleTypeKind.DOUBLE);
		addEEnumLiteral(jSimpleTypeKindEEnum, JSimpleTypeKind.BOOLEAN);
		addEEnumLiteral(jSimpleTypeKindEEnum, JSimpleTypeKind.VOID);

		initEEnum(structuralFragmentTypeEEnum, StructuralFragmentType.class, "StructuralFragmentType");
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.IF);
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.FOR);
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.FOREACH);
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.WHILE);
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.DO);
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.SWITCH);
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.CASE);
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.FINALLY);
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.TRY);
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.CATCH);
		addEEnumLiteral(structuralFragmentTypeEEnum, StructuralFragmentType.SYNCHRONIZED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// Genuine-SiDiff-Model
		createGenuineSiDiffModelAnnotations();
		// IGNOREDIFF
		createIGNOREDIFFAnnotations();
		// AbsolutePosition
		createAbsolutePositionAnnotations();
		// INCOMING
		createINCOMINGAnnotations();
	}

  /**
	 * Initializes the annotations for <b>Genuine-SiDiff-Model</b>.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void createGenuineSiDiffModelAnnotations()
  {
		String source = "Genuine-SiDiff-Model";		
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
		   });											
	}

  /**
	 * Initializes the annotations for <b>IGNOREDIFF</b>.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void createIGNOREDIFFAnnotations()
  {
		String source = "IGNOREDIFF";			
		addAnnotation
		  (getJIdentifiableElement_Id(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getJClass_SubClasses(), 
		   source, 
		   new String[] {
		   });				
		addAnnotation
		  (getJField_AccessedBy(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getJMethod_CalledBy(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getJAdressableFragment_StartByte(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getJInterface_SubInterfaces(), 
		   source, 
		   new String[] {
		   });		
		addAnnotation
		  (getJInterface_ImplementingClasses(), 
		   source, 
		   new String[] {
		   });	
	}

  /**
	 * Initializes the annotations for <b>AbsolutePosition</b>.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void createAbsolutePositionAnnotations()
  {
		String source = "AbsolutePosition";					
		addAnnotation
		  (getJTemplate_GenericTypes(), 
		   source, 
		   new String[] {
		   });									
		addAnnotation
		  (getJTemplateWrapper_Bindings(), 
		   source, 
		   new String[] {
		   });
	}

  /**
	 * Initializes the annotations for <b>INCOMING</b>.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected void createINCOMINGAnnotations()
  {
		String source = "INCOMING";						
		addAnnotation
		  (getJField_AccessedBy(), 
		   source, 
		   new String[] {
		   });			
		addAnnotation
		  (getJMethod_CalledBy(), 
		   source, 
		   new String[] {
		   });					
	}

} //JavaModelPackageImpl
