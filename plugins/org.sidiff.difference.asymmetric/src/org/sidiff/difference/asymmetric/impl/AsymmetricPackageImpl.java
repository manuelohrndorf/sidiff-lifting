/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.AttributeDependency;
import org.sidiff.difference.asymmetric.Dependency;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.DependencyKind;
import org.sidiff.difference.asymmetric.EdgeDependency;
import org.sidiff.difference.asymmetric.Execution;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.NodeDependency;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParallelExecution;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.difference.asymmetric.SequentialExecution;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.symmetric.SymmetricPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AsymmetricPackageImpl extends EPackageImpl implements AsymmetricPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass asymmetricDifferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationInvocationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass objectParameterBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueParameterBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependencyContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parallelExecutionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sequentialExecutionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass multiParameterBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass edgeDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dependencyKindEEnum = null;

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
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AsymmetricPackageImpl() {
		super(eNS_URI, AsymmetricFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link AsymmetricPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AsymmetricPackage init() {
		if (isInited) return (AsymmetricPackage)EPackage.Registry.INSTANCE.getEPackage(AsymmetricPackage.eNS_URI);

		// Obtain or create and register package
		AsymmetricPackageImpl theAsymmetricPackage = (AsymmetricPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AsymmetricPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AsymmetricPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		SymmetricPackage.eINSTANCE.eClass();
		HenshinPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theAsymmetricPackage.createPackageContents();

		// Initialize created meta-data
		theAsymmetricPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAsymmetricPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AsymmetricPackage.eNS_URI, theAsymmetricPackage);
		return theAsymmetricPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAsymmetricDifference() {
		return asymmetricDifferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAsymmetricDifference_OperationInvocations() {
		return (EReference)asymmetricDifferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAsymmetricDifference_OriginModel() {
		return (EAttribute)asymmetricDifferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAsymmetricDifference_ChangedModel() {
		return (EAttribute)asymmetricDifferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAsymmetricDifference_DepContainers() {
		return (EReference)asymmetricDifferenceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAsymmetricDifference_ParameterMappings() {
		return (EReference)asymmetricDifferenceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAsymmetricDifference_Executions() {
		return (EReference)asymmetricDifferenceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAsymmetricDifference_SymmetricDifference() {
		return (EReference)asymmetricDifferenceEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAsymmetricDifference_UriOriginModel() {
		return (EAttribute)asymmetricDifferenceEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAsymmetricDifference_UriChangedModel() {
		return (EAttribute)asymmetricDifferenceEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationInvocation() {
		return operationInvocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationInvocation_Name() {
		return (EAttribute)operationInvocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationInvocation_EditRuleName() {
		return (EAttribute)operationInvocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationInvocation_ParameterBindings() {
		return (EReference)operationInvocationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationInvocation_Outgoing() {
		return (EReference)operationInvocationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationInvocation_Incoming() {
		return (EReference)operationInvocationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationInvocation_Apply() {
		return (EAttribute)operationInvocationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationInvocation_ChangeSet() {
		return (EReference)operationInvocationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationInvocation_AsymmetricDifference() {
		return (EReference)operationInvocationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterBinding() {
		return parameterBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterBinding_FormalName() {
		return (EAttribute)parameterBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjectParameterBinding() {
		return objectParameterBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectParameterBinding_ActualA() {
		return (EReference)objectParameterBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectParameterBinding_ActualB() {
		return (EReference)objectParameterBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectParameterBinding_Outgoing() {
		return (EReference)objectParameterBindingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObjectParameterBinding_Incoming() {
		return (EReference)objectParameterBindingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueParameterBinding() {
		return valueParameterBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValueParameterBinding_Actual() {
		return (EAttribute)valueParameterBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDependencyContainer() {
		return dependencyContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependencyContainer_Source() {
		return (EReference)dependencyContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependencyContainer_Target() {
		return (EReference)dependencyContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependencyContainer_Dependencies() {
		return (EReference)dependencyContainerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDependency() {
		return dependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependency_Source() {
		return (EReference)dependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependency_Target() {
		return (EReference)dependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependency_Kind() {
		return (EAttribute)dependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterMapping() {
		return parameterMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterMapping_Source() {
		return (EReference)parameterMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterMapping_Target() {
		return (EReference)parameterMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParallelExecution() {
		return parallelExecutionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParallelExecution_SubExecutions() {
		return (EReference)parallelExecutionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSequentialExecution() {
		return sequentialExecutionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSequentialExecution_SubExecutions() {
		return (EReference)sequentialExecutionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecution() {
		return executionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMultiParameterBinding() {
		return multiParameterBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMultiParameterBinding_ParameterBindings() {
		return (EReference)multiParameterBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeDependency() {
		return nodeDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeDependency_Object() {
		return (EReference)nodeDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEdgeDependency() {
		return edgeDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeDependency_SrcObject() {
		return (EReference)edgeDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeDependency_TgtObject() {
		return (EReference)edgeDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdgeDependency_Type() {
		return (EReference)edgeDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeDependency() {
		return attributeDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeDependency_Object() {
		return (EReference)attributeDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeDependency_Type() {
		return (EReference)attributeDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDependencyKind() {
		return dependencyKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsymmetricFactory getAsymmetricFactory() {
		return (AsymmetricFactory)getEFactoryInstance();
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
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		asymmetricDifferenceEClass = createEClass(ASYMMETRIC_DIFFERENCE);
		createEReference(asymmetricDifferenceEClass, ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS);
		createEAttribute(asymmetricDifferenceEClass, ASYMMETRIC_DIFFERENCE__ORIGIN_MODEL);
		createEAttribute(asymmetricDifferenceEClass, ASYMMETRIC_DIFFERENCE__CHANGED_MODEL);
		createEReference(asymmetricDifferenceEClass, ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS);
		createEReference(asymmetricDifferenceEClass, ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS);
		createEReference(asymmetricDifferenceEClass, ASYMMETRIC_DIFFERENCE__EXECUTIONS);
		createEReference(asymmetricDifferenceEClass, ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE);
		createEAttribute(asymmetricDifferenceEClass, ASYMMETRIC_DIFFERENCE__URI_ORIGIN_MODEL);
		createEAttribute(asymmetricDifferenceEClass, ASYMMETRIC_DIFFERENCE__URI_CHANGED_MODEL);

		operationInvocationEClass = createEClass(OPERATION_INVOCATION);
		createEAttribute(operationInvocationEClass, OPERATION_INVOCATION__NAME);
		createEAttribute(operationInvocationEClass, OPERATION_INVOCATION__EDIT_RULE_NAME);
		createEAttribute(operationInvocationEClass, OPERATION_INVOCATION__APPLY);
		createEReference(operationInvocationEClass, OPERATION_INVOCATION__PARAMETER_BINDINGS);
		createEReference(operationInvocationEClass, OPERATION_INVOCATION__OUTGOING);
		createEReference(operationInvocationEClass, OPERATION_INVOCATION__INCOMING);
		createEReference(operationInvocationEClass, OPERATION_INVOCATION__CHANGE_SET);
		createEReference(operationInvocationEClass, OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE);

		parameterBindingEClass = createEClass(PARAMETER_BINDING);
		createEAttribute(parameterBindingEClass, PARAMETER_BINDING__FORMAL_NAME);

		objectParameterBindingEClass = createEClass(OBJECT_PARAMETER_BINDING);
		createEReference(objectParameterBindingEClass, OBJECT_PARAMETER_BINDING__ACTUAL_A);
		createEReference(objectParameterBindingEClass, OBJECT_PARAMETER_BINDING__ACTUAL_B);
		createEReference(objectParameterBindingEClass, OBJECT_PARAMETER_BINDING__OUTGOING);
		createEReference(objectParameterBindingEClass, OBJECT_PARAMETER_BINDING__INCOMING);

		valueParameterBindingEClass = createEClass(VALUE_PARAMETER_BINDING);
		createEAttribute(valueParameterBindingEClass, VALUE_PARAMETER_BINDING__ACTUAL);

		dependencyContainerEClass = createEClass(DEPENDENCY_CONTAINER);
		createEReference(dependencyContainerEClass, DEPENDENCY_CONTAINER__SOURCE);
		createEReference(dependencyContainerEClass, DEPENDENCY_CONTAINER__TARGET);
		createEReference(dependencyContainerEClass, DEPENDENCY_CONTAINER__DEPENDENCIES);

		parameterMappingEClass = createEClass(PARAMETER_MAPPING);
		createEReference(parameterMappingEClass, PARAMETER_MAPPING__SOURCE);
		createEReference(parameterMappingEClass, PARAMETER_MAPPING__TARGET);

		parallelExecutionEClass = createEClass(PARALLEL_EXECUTION);
		createEReference(parallelExecutionEClass, PARALLEL_EXECUTION__SUB_EXECUTIONS);

		sequentialExecutionEClass = createEClass(SEQUENTIAL_EXECUTION);
		createEReference(sequentialExecutionEClass, SEQUENTIAL_EXECUTION__SUB_EXECUTIONS);

		executionEClass = createEClass(EXECUTION);

		multiParameterBindingEClass = createEClass(MULTI_PARAMETER_BINDING);
		createEReference(multiParameterBindingEClass, MULTI_PARAMETER_BINDING__PARAMETER_BINDINGS);

		nodeDependencyEClass = createEClass(NODE_DEPENDENCY);
		createEReference(nodeDependencyEClass, NODE_DEPENDENCY__OBJECT);

		edgeDependencyEClass = createEClass(EDGE_DEPENDENCY);
		createEReference(edgeDependencyEClass, EDGE_DEPENDENCY__SRC_OBJECT);
		createEReference(edgeDependencyEClass, EDGE_DEPENDENCY__TGT_OBJECT);
		createEReference(edgeDependencyEClass, EDGE_DEPENDENCY__TYPE);

		attributeDependencyEClass = createEClass(ATTRIBUTE_DEPENDENCY);
		createEReference(attributeDependencyEClass, ATTRIBUTE_DEPENDENCY__OBJECT);
		createEReference(attributeDependencyEClass, ATTRIBUTE_DEPENDENCY__TYPE);

		dependencyEClass = createEClass(DEPENDENCY);
		createEAttribute(dependencyEClass, DEPENDENCY__KIND);
		createEReference(dependencyEClass, DEPENDENCY__SOURCE);
		createEReference(dependencyEClass, DEPENDENCY__TARGET);

		// Create enums
		dependencyKindEEnum = createEEnum(DEPENDENCY_KIND);
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
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		SymmetricPackage theSymmetricPackage = (SymmetricPackage)EPackage.Registry.INSTANCE.getEPackage(SymmetricPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		operationInvocationEClass.getESuperTypes().add(this.getExecution());
		objectParameterBindingEClass.getESuperTypes().add(this.getParameterBinding());
		valueParameterBindingEClass.getESuperTypes().add(this.getParameterBinding());
		parallelExecutionEClass.getESuperTypes().add(this.getExecution());
		sequentialExecutionEClass.getESuperTypes().add(this.getExecution());
		multiParameterBindingEClass.getESuperTypes().add(this.getParameterBinding());
		nodeDependencyEClass.getESuperTypes().add(this.getDependency());
		edgeDependencyEClass.getESuperTypes().add(this.getDependency());
		attributeDependencyEClass.getESuperTypes().add(this.getDependency());

		// Initialize classes and features; add operations and parameters
		initEClass(asymmetricDifferenceEClass, AsymmetricDifference.class, "AsymmetricDifference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAsymmetricDifference_OperationInvocations(), this.getOperationInvocation(), this.getOperationInvocation_AsymmetricDifference(), "operationInvocations", null, 0, -1, AsymmetricDifference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAsymmetricDifference_OriginModel(), ecorePackage.getEResource(), "originModel", null, 0, 1, AsymmetricDifference.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getAsymmetricDifference_ChangedModel(), ecorePackage.getEResource(), "changedModel", null, 0, 1, AsymmetricDifference.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getAsymmetricDifference_DepContainers(), this.getDependencyContainer(), null, "depContainers", null, 0, -1, AsymmetricDifference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAsymmetricDifference_ParameterMappings(), this.getParameterMapping(), null, "parameterMappings", null, 0, -1, AsymmetricDifference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAsymmetricDifference_Executions(), this.getExecution(), null, "executions", null, 0, -1, AsymmetricDifference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAsymmetricDifference_SymmetricDifference(), theSymmetricPackage.getSymmetricDifference(), null, "symmetricDifference", null, 0, 1, AsymmetricDifference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAsymmetricDifference_UriOriginModel(), theEcorePackage.getEString(), "uriOriginModel", null, 0, 1, AsymmetricDifference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAsymmetricDifference_UriChangedModel(), theEcorePackage.getEString(), "uriChangedModel", null, 0, 1, AsymmetricDifference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operationInvocationEClass, OperationInvocation.class, "OperationInvocation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOperationInvocation_Name(), theEcorePackage.getEString(), "name", null, 0, 1, OperationInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOperationInvocation_EditRuleName(), theEcorePackage.getEString(), "editRuleName", null, 0, 1, OperationInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOperationInvocation_Apply(), theEcorePackage.getEBoolean(), "apply", "true", 0, 1, OperationInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationInvocation_ParameterBindings(), this.getParameterBinding(), null, "parameterBindings", null, 0, -1, OperationInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationInvocation_Outgoing(), this.getDependencyContainer(), this.getDependencyContainer_Source(), "outgoing", null, 0, -1, OperationInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationInvocation_Incoming(), this.getDependencyContainer(), this.getDependencyContainer_Target(), "incoming", null, 0, -1, OperationInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationInvocation_ChangeSet(), theSymmetricPackage.getSemanticChangeSet(), null, "changeSet", null, 0, 1, OperationInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationInvocation_AsymmetricDifference(), this.getAsymmetricDifference(), this.getAsymmetricDifference_OperationInvocations(), "asymmetricDifference", null, 0, 1, OperationInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterBindingEClass, ParameterBinding.class, "ParameterBinding", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameterBinding_FormalName(), ecorePackage.getEString(), "formalName", null, 1, 1, ParameterBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(parameterBindingEClass, theEcorePackage.getEBoolean(), "isDefaultValue", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(objectParameterBindingEClass, ObjectParameterBinding.class, "ObjectParameterBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getObjectParameterBinding_ActualA(), ecorePackage.getEObject(), null, "actualA", null, 0, 1, ObjectParameterBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getObjectParameterBinding_ActualB(), ecorePackage.getEObject(), null, "actualB", null, 0, 1, ObjectParameterBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getObjectParameterBinding_Outgoing(), this.getParameterMapping(), this.getParameterMapping_Source(), "outgoing", null, 0, -1, ObjectParameterBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getObjectParameterBinding_Incoming(), this.getParameterMapping(), this.getParameterMapping_Target(), "incoming", null, 0, 1, ObjectParameterBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(objectParameterBindingEClass, ecorePackage.getEBoolean(), "isMappingTarget", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(valueParameterBindingEClass, ValueParameterBinding.class, "ValueParameterBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValueParameterBinding_Actual(), ecorePackage.getEString(), "actual", null, 0, 1, ValueParameterBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dependencyContainerEClass, DependencyContainer.class, "DependencyContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDependencyContainer_Source(), this.getOperationInvocation(), this.getOperationInvocation_Outgoing(), "source", null, 1, 1, DependencyContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDependencyContainer_Target(), this.getOperationInvocation(), this.getOperationInvocation_Incoming(), "target", null, 1, 1, DependencyContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDependencyContainer_Dependencies(), this.getDependency(), null, "dependencies", null, 0, -1, DependencyContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterMappingEClass, ParameterMapping.class, "ParameterMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameterMapping_Source(), this.getObjectParameterBinding(), this.getObjectParameterBinding_Outgoing(), "source", null, 1, 1, ParameterMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParameterMapping_Target(), this.getObjectParameterBinding(), this.getObjectParameterBinding_Incoming(), "target", null, 1, 1, ParameterMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parallelExecutionEClass, ParallelExecution.class, "ParallelExecution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParallelExecution_SubExecutions(), this.getExecution(), null, "subExecutions", null, 0, -1, ParallelExecution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sequentialExecutionEClass, SequentialExecution.class, "SequentialExecution", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSequentialExecution_SubExecutions(), this.getExecution(), null, "subExecutions", null, 0, -1, SequentialExecution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(executionEClass, Execution.class, "Execution", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(multiParameterBindingEClass, MultiParameterBinding.class, "MultiParameterBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMultiParameterBinding_ParameterBindings(), this.getParameterBinding(), null, "parameterBindings", null, 0, -1, MultiParameterBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeDependencyEClass, NodeDependency.class, "NodeDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeDependency_Object(), ecorePackage.getEObject(), null, "object", null, 0, 1, NodeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(edgeDependencyEClass, EdgeDependency.class, "EdgeDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEdgeDependency_SrcObject(), ecorePackage.getEObject(), null, "srcObject", null, 0, 1, EdgeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeDependency_TgtObject(), ecorePackage.getEObject(), null, "tgtObject", null, 0, 1, EdgeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdgeDependency_Type(), theEcorePackage.getEReference(), null, "type", null, 0, 1, EdgeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeDependencyEClass, AttributeDependency.class, "AttributeDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeDependency_Object(), ecorePackage.getEObject(), null, "object", null, 0, 1, AttributeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeDependency_Type(), theEcorePackage.getEAttribute(), null, "type", null, 0, 1, AttributeDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dependencyEClass, Dependency.class, "Dependency", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDependency_Kind(), this.getDependencyKind(), "kind", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDependency_Source(), this.getOperationInvocation(), null, "source", null, 1, 1, Dependency.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDependency_Target(), this.getOperationInvocation(), null, "target", null, 1, 1, Dependency.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(dependencyKindEEnum, DependencyKind.class, "DependencyKind");
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.USE_DELETE);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.CREATE_USE);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.DELETE_FORBID);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.FORBID_CREATE);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.CHANGE_USE);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.USE_CHANGE);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.CHANGE_FORBID);
		addEEnumLiteral(dependencyKindEEnum, DependencyKind.FORBID_CHANGE);

		// Create resource
		createResource(eNS_URI);
	}

} //AsymmetricPackageImpl
