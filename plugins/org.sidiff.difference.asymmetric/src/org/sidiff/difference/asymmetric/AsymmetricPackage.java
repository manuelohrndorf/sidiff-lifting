/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.sidiff.difference.asymmetric.AsymmetricFactory
 * @model kind="package"
 * @generated
 */
public interface AsymmetricPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "asymmetric";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.sidiff.org/difference/asymmetric/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "asymmetric";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AsymmetricPackage eINSTANCE = org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl <em>Difference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getAsymmetricDifference()
	 * @generated
	 */
	int ASYMMETRIC_DIFFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Operation Invocations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS = 0;

	/**
	 * The feature id for the '<em><b>Origin Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMMETRIC_DIFFERENCE__ORIGIN_MODEL = 1;

	/**
	 * The feature id for the '<em><b>Changed Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMMETRIC_DIFFERENCE__CHANGED_MODEL = 2;

	/**
	 * The feature id for the '<em><b>Dep Containers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS = 3;

	/**
	 * The feature id for the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS = 4;

	/**
	 * The feature id for the '<em><b>Executions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMMETRIC_DIFFERENCE__EXECUTIONS = 5;

	/**
	 * The feature id for the '<em><b>Symmetric Difference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE = 6;

	/**
	 * The feature id for the '<em><b>Uri Origin Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMMETRIC_DIFFERENCE__URI_ORIGIN_MODEL = 7;

	/**
	 * The feature id for the '<em><b>Uri Changed Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMMETRIC_DIFFERENCE__URI_CHANGED_MODEL = 8;

	/**
	 * The number of structural features of the '<em>Difference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMMETRIC_DIFFERENCE_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.ExecutionImpl <em>Execution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.ExecutionImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getExecution()
	 * @generated
	 */
	int EXECUTION = 9;

	/**
	 * The number of structural features of the '<em>Execution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXECUTION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl <em>Operation Invocation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.OperationInvocationImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getOperationInvocation()
	 * @generated
	 */
	int OPERATION_INVOCATION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_INVOCATION__NAME = EXECUTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Edit Rule Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_INVOCATION__EDIT_RULE_NAME = EXECUTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Apply</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_INVOCATION__APPLY = EXECUTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parameter Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_INVOCATION__PARAMETER_BINDINGS = EXECUTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_INVOCATION__OUTGOING = EXECUTION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_INVOCATION__INCOMING = EXECUTION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Change Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_INVOCATION__CHANGE_SET = EXECUTION_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Asymmetric Difference</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE = EXECUTION_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Operation Invocation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_INVOCATION_FEATURE_COUNT = EXECUTION_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.ParameterBindingImpl <em>Parameter Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.ParameterBindingImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getParameterBinding()
	 * @generated
	 */
	int PARAMETER_BINDING = 2;

	/**
	 * The feature id for the '<em><b>Formal Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_BINDING__FORMAL_NAME = 0;

	/**
	 * The number of structural features of the '<em>Parameter Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_BINDING_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.ObjectParameterBindingImpl <em>Object Parameter Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.ObjectParameterBindingImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getObjectParameterBinding()
	 * @generated
	 */
	int OBJECT_PARAMETER_BINDING = 3;

	/**
	 * The feature id for the '<em><b>Formal Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PARAMETER_BINDING__FORMAL_NAME = PARAMETER_BINDING__FORMAL_NAME;

	/**
	 * The feature id for the '<em><b>Actual A</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PARAMETER_BINDING__ACTUAL_A = PARAMETER_BINDING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Actual B</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PARAMETER_BINDING__ACTUAL_B = PARAMETER_BINDING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Outgoing</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PARAMETER_BINDING__OUTGOING = PARAMETER_BINDING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Incoming</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PARAMETER_BINDING__INCOMING = PARAMETER_BINDING_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Object Parameter Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PARAMETER_BINDING_FEATURE_COUNT = PARAMETER_BINDING_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.ValueParameterBindingImpl <em>Value Parameter Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.ValueParameterBindingImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getValueParameterBinding()
	 * @generated
	 */
	int VALUE_PARAMETER_BINDING = 4;

	/**
	 * The feature id for the '<em><b>Formal Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_PARAMETER_BINDING__FORMAL_NAME = PARAMETER_BINDING__FORMAL_NAME;

	/**
	 * The feature id for the '<em><b>Actual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_PARAMETER_BINDING__ACTUAL = PARAMETER_BINDING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Value Parameter Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_PARAMETER_BINDING_FEATURE_COUNT = PARAMETER_BINDING_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.DependencyContainerImpl <em>Dependency Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.DependencyContainerImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getDependencyContainer()
	 * @generated
	 */
	int DEPENDENCY_CONTAINER = 5;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_CONTAINER__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_CONTAINER__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_CONTAINER__DEPENDENCIES = 2;

	/**
	 * The number of structural features of the '<em>Dependency Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_CONTAINER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.DependencyImpl <em>Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.DependencyImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getDependency()
	 * @generated
	 */
	int DEPENDENCY = 14;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.ParameterMappingImpl <em>Parameter Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.ParameterMappingImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getParameterMapping()
	 * @generated
	 */
	int PARAMETER_MAPPING = 6;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_MAPPING__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_MAPPING__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Parameter Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.ParallelExecutionImpl <em>Parallel Execution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.ParallelExecutionImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getParallelExecution()
	 * @generated
	 */
	int PARALLEL_EXECUTION = 7;

	/**
	 * The feature id for the '<em><b>Sub Executions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_EXECUTION__SUB_EXECUTIONS = EXECUTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parallel Execution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARALLEL_EXECUTION_FEATURE_COUNT = EXECUTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.SequentialExecutionImpl <em>Sequential Execution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.SequentialExecutionImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getSequentialExecution()
	 * @generated
	 */
	int SEQUENTIAL_EXECUTION = 8;

	/**
	 * The feature id for the '<em><b>Sub Executions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_EXECUTION__SUB_EXECUTIONS = EXECUTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sequential Execution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENTIAL_EXECUTION_FEATURE_COUNT = EXECUTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.MultiParameterBindingImpl <em>Multi Parameter Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.MultiParameterBindingImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getMultiParameterBinding()
	 * @generated
	 */
	int MULTI_PARAMETER_BINDING = 10;

	/**
	 * The feature id for the '<em><b>Formal Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_PARAMETER_BINDING__FORMAL_NAME = PARAMETER_BINDING__FORMAL_NAME;

	/**
	 * The feature id for the '<em><b>Parameter Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_PARAMETER_BINDING__PARAMETER_BINDINGS = PARAMETER_BINDING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Multi Parameter Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_PARAMETER_BINDING_FEATURE_COUNT = PARAMETER_BINDING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__KIND = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__TARGET = 2;

	/**
	 * The number of structural features of the '<em>Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.NodeDependencyImpl <em>Node Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.NodeDependencyImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getNodeDependency()
	 * @generated
	 */
	int NODE_DEPENDENCY = 11;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DEPENDENCY__KIND = DEPENDENCY__KIND;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DEPENDENCY__SOURCE = DEPENDENCY__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DEPENDENCY__TARGET = DEPENDENCY__TARGET;

	/**
	 * The feature id for the '<em><b>Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DEPENDENCY__OBJECT = DEPENDENCY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Node Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_DEPENDENCY_FEATURE_COUNT = DEPENDENCY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.EdgeDependencyImpl <em>Edge Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.EdgeDependencyImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getEdgeDependency()
	 * @generated
	 */
	int EDGE_DEPENDENCY = 12;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DEPENDENCY__KIND = DEPENDENCY__KIND;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DEPENDENCY__SOURCE = DEPENDENCY__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DEPENDENCY__TARGET = DEPENDENCY__TARGET;

	/**
	 * The feature id for the '<em><b>Src Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DEPENDENCY__SRC_OBJECT = DEPENDENCY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tgt Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DEPENDENCY__TGT_OBJECT = DEPENDENCY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DEPENDENCY__TYPE = DEPENDENCY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Edge Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_DEPENDENCY_FEATURE_COUNT = DEPENDENCY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.impl.AttributeDependencyImpl <em>Attribute Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.impl.AttributeDependencyImpl
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getAttributeDependency()
	 * @generated
	 */
	int ATTRIBUTE_DEPENDENCY = 13;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEPENDENCY__KIND = DEPENDENCY__KIND;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEPENDENCY__SOURCE = DEPENDENCY__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEPENDENCY__TARGET = DEPENDENCY__TARGET;

	/**
	 * The feature id for the '<em><b>Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEPENDENCY__OBJECT = DEPENDENCY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEPENDENCY__TYPE = DEPENDENCY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Attribute Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DEPENDENCY_FEATURE_COUNT = DEPENDENCY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.sidiff.difference.asymmetric.DependencyKind <em>Dependency Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sidiff.difference.asymmetric.DependencyKind
	 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getDependencyKind()
	 * @generated
	 */
	int DEPENDENCY_KIND = 15;

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.AsymmetricDifference <em>Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Difference</em>'.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference
	 * @generated
	 */
	EClass getAsymmetricDifference();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getOperationInvocations <em>Operation Invocations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operation Invocations</em>'.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference#getOperationInvocations()
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	EReference getAsymmetricDifference_OperationInvocations();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getOriginModel <em>Origin Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Origin Model</em>'.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference#getOriginModel()
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	EAttribute getAsymmetricDifference_OriginModel();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getChangedModel <em>Changed Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Changed Model</em>'.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference#getChangedModel()
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	EAttribute getAsymmetricDifference_ChangedModel();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getDepContainers <em>Dep Containers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dep Containers</em>'.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference#getDepContainers()
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	EReference getAsymmetricDifference_DepContainers();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getParameterMappings <em>Parameter Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Mappings</em>'.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference#getParameterMappings()
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	EReference getAsymmetricDifference_ParameterMappings();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getExecutions <em>Executions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Executions</em>'.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference#getExecutions()
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	EReference getAsymmetricDifference_Executions();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getSymmetricDifference <em>Symmetric Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Symmetric Difference</em>'.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference#getSymmetricDifference()
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	EReference getAsymmetricDifference_SymmetricDifference();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getUriOriginModel <em>Uri Origin Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri Origin Model</em>'.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference#getUriOriginModel()
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	EAttribute getAsymmetricDifference_UriOriginModel();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getUriChangedModel <em>Uri Changed Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri Changed Model</em>'.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference#getUriChangedModel()
	 * @see #getAsymmetricDifference()
	 * @generated
	 */
	EAttribute getAsymmetricDifference_UriChangedModel();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.OperationInvocation <em>Operation Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Invocation</em>'.
	 * @see org.sidiff.difference.asymmetric.OperationInvocation
	 * @generated
	 */
	EClass getOperationInvocation();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.asymmetric.OperationInvocation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sidiff.difference.asymmetric.OperationInvocation#getName()
	 * @see #getOperationInvocation()
	 * @generated
	 */
	EAttribute getOperationInvocation_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.asymmetric.OperationInvocation#getEditRuleName <em>Edit Rule Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Edit Rule Name</em>'.
	 * @see org.sidiff.difference.asymmetric.OperationInvocation#getEditRuleName()
	 * @see #getOperationInvocation()
	 * @generated
	 */
	EAttribute getOperationInvocation_EditRuleName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.asymmetric.OperationInvocation#getParameterBindings <em>Parameter Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Bindings</em>'.
	 * @see org.sidiff.difference.asymmetric.OperationInvocation#getParameterBindings()
	 * @see #getOperationInvocation()
	 * @generated
	 */
	EReference getOperationInvocation_ParameterBindings();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.difference.asymmetric.OperationInvocation#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see org.sidiff.difference.asymmetric.OperationInvocation#getOutgoing()
	 * @see #getOperationInvocation()
	 * @generated
	 */
	EReference getOperationInvocation_Outgoing();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.difference.asymmetric.OperationInvocation#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming</em>'.
	 * @see org.sidiff.difference.asymmetric.OperationInvocation#getIncoming()
	 * @see #getOperationInvocation()
	 * @generated
	 */
	EReference getOperationInvocation_Incoming();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.asymmetric.OperationInvocation#isApply <em>Apply</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Apply</em>'.
	 * @see org.sidiff.difference.asymmetric.OperationInvocation#isApply()
	 * @see #getOperationInvocation()
	 * @generated
	 */
	EAttribute getOperationInvocation_Apply();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.OperationInvocation#getChangeSet <em>Change Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Change Set</em>'.
	 * @see org.sidiff.difference.asymmetric.OperationInvocation#getChangeSet()
	 * @see #getOperationInvocation()
	 * @generated
	 */
	EReference getOperationInvocation_ChangeSet();

	/**
	 * Returns the meta object for the container reference '{@link org.sidiff.difference.asymmetric.OperationInvocation#getAsymmetricDifference <em>Asymmetric Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Asymmetric Difference</em>'.
	 * @see org.sidiff.difference.asymmetric.OperationInvocation#getAsymmetricDifference()
	 * @see #getOperationInvocation()
	 * @generated
	 */
	EReference getOperationInvocation_AsymmetricDifference();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.ParameterBinding <em>Parameter Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Binding</em>'.
	 * @see org.sidiff.difference.asymmetric.ParameterBinding
	 * @generated
	 */
	EClass getParameterBinding();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.asymmetric.ParameterBinding#getFormalName <em>Formal Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Formal Name</em>'.
	 * @see org.sidiff.difference.asymmetric.ParameterBinding#getFormalName()
	 * @see #getParameterBinding()
	 * @generated
	 */
	EAttribute getParameterBinding_FormalName();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding <em>Object Parameter Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Parameter Binding</em>'.
	 * @see org.sidiff.difference.asymmetric.ObjectParameterBinding
	 * @generated
	 */
	EClass getObjectParameterBinding();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getActualA <em>Actual A</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Actual A</em>'.
	 * @see org.sidiff.difference.asymmetric.ObjectParameterBinding#getActualA()
	 * @see #getObjectParameterBinding()
	 * @generated
	 */
	EReference getObjectParameterBinding_ActualA();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getActualB <em>Actual B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Actual B</em>'.
	 * @see org.sidiff.difference.asymmetric.ObjectParameterBinding#getActualB()
	 * @see #getObjectParameterBinding()
	 * @generated
	 */
	EReference getObjectParameterBinding_ActualB();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing</em>'.
	 * @see org.sidiff.difference.asymmetric.ObjectParameterBinding#getOutgoing()
	 * @see #getObjectParameterBinding()
	 * @generated
	 */
	EReference getObjectParameterBinding_Outgoing();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Incoming</em>'.
	 * @see org.sidiff.difference.asymmetric.ObjectParameterBinding#getIncoming()
	 * @see #getObjectParameterBinding()
	 * @generated
	 */
	EReference getObjectParameterBinding_Incoming();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.ValueParameterBinding <em>Value Parameter Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Parameter Binding</em>'.
	 * @see org.sidiff.difference.asymmetric.ValueParameterBinding
	 * @generated
	 */
	EClass getValueParameterBinding();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.asymmetric.ValueParameterBinding#getActual <em>Actual</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Actual</em>'.
	 * @see org.sidiff.difference.asymmetric.ValueParameterBinding#getActual()
	 * @see #getValueParameterBinding()
	 * @generated
	 */
	EAttribute getValueParameterBinding_Actual();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.DependencyContainer <em>Dependency Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dependency Container</em>'.
	 * @see org.sidiff.difference.asymmetric.DependencyContainer
	 * @generated
	 */
	EClass getDependencyContainer();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.DependencyContainer#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.sidiff.difference.asymmetric.DependencyContainer#getSource()
	 * @see #getDependencyContainer()
	 * @generated
	 */
	EReference getDependencyContainer_Source();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.DependencyContainer#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.sidiff.difference.asymmetric.DependencyContainer#getTarget()
	 * @see #getDependencyContainer()
	 * @generated
	 */
	EReference getDependencyContainer_Target();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.asymmetric.DependencyContainer#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dependencies</em>'.
	 * @see org.sidiff.difference.asymmetric.DependencyContainer#getDependencies()
	 * @see #getDependencyContainer()
	 * @generated
	 */
	EReference getDependencyContainer_Dependencies();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.Dependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dependency</em>'.
	 * @see org.sidiff.difference.asymmetric.Dependency
	 * @generated
	 */
	EClass getDependency();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.Dependency#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.sidiff.difference.asymmetric.Dependency#getSource()
	 * @see #getDependency()
	 * @generated
	 */
	EReference getDependency_Source();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.Dependency#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.sidiff.difference.asymmetric.Dependency#getTarget()
	 * @see #getDependency()
	 * @generated
	 */
	EReference getDependency_Target();

	/**
	 * Returns the meta object for the attribute '{@link org.sidiff.difference.asymmetric.Dependency#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.sidiff.difference.asymmetric.Dependency#getKind()
	 * @see #getDependency()
	 * @generated
	 */
	EAttribute getDependency_Kind();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.ParameterMapping <em>Parameter Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Mapping</em>'.
	 * @see org.sidiff.difference.asymmetric.ParameterMapping
	 * @generated
	 */
	EClass getParameterMapping();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.ParameterMapping#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.sidiff.difference.asymmetric.ParameterMapping#getSource()
	 * @see #getParameterMapping()
	 * @generated
	 */
	EReference getParameterMapping_Source();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.ParameterMapping#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.sidiff.difference.asymmetric.ParameterMapping#getTarget()
	 * @see #getParameterMapping()
	 * @generated
	 */
	EReference getParameterMapping_Target();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.ParallelExecution <em>Parallel Execution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parallel Execution</em>'.
	 * @see org.sidiff.difference.asymmetric.ParallelExecution
	 * @generated
	 */
	EClass getParallelExecution();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.difference.asymmetric.ParallelExecution#getSubExecutions <em>Sub Executions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Executions</em>'.
	 * @see org.sidiff.difference.asymmetric.ParallelExecution#getSubExecutions()
	 * @see #getParallelExecution()
	 * @generated
	 */
	EReference getParallelExecution_SubExecutions();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.SequentialExecution <em>Sequential Execution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sequential Execution</em>'.
	 * @see org.sidiff.difference.asymmetric.SequentialExecution
	 * @generated
	 */
	EClass getSequentialExecution();

	/**
	 * Returns the meta object for the reference list '{@link org.sidiff.difference.asymmetric.SequentialExecution#getSubExecutions <em>Sub Executions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Executions</em>'.
	 * @see org.sidiff.difference.asymmetric.SequentialExecution#getSubExecutions()
	 * @see #getSequentialExecution()
	 * @generated
	 */
	EReference getSequentialExecution_SubExecutions();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.Execution <em>Execution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Execution</em>'.
	 * @see org.sidiff.difference.asymmetric.Execution
	 * @generated
	 */
	EClass getExecution();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.MultiParameterBinding <em>Multi Parameter Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Parameter Binding</em>'.
	 * @see org.sidiff.difference.asymmetric.MultiParameterBinding
	 * @generated
	 */
	EClass getMultiParameterBinding();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sidiff.difference.asymmetric.MultiParameterBinding#getParameterBindings <em>Parameter Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Bindings</em>'.
	 * @see org.sidiff.difference.asymmetric.MultiParameterBinding#getParameterBindings()
	 * @see #getMultiParameterBinding()
	 * @generated
	 */
	EReference getMultiParameterBinding_ParameterBindings();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.NodeDependency <em>Node Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Dependency</em>'.
	 * @see org.sidiff.difference.asymmetric.NodeDependency
	 * @generated
	 */
	EClass getNodeDependency();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.NodeDependency#getObject <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Object</em>'.
	 * @see org.sidiff.difference.asymmetric.NodeDependency#getObject()
	 * @see #getNodeDependency()
	 * @generated
	 */
	EReference getNodeDependency_Object();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.EdgeDependency <em>Edge Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge Dependency</em>'.
	 * @see org.sidiff.difference.asymmetric.EdgeDependency
	 * @generated
	 */
	EClass getEdgeDependency();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.EdgeDependency#getSrcObject <em>Src Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src Object</em>'.
	 * @see org.sidiff.difference.asymmetric.EdgeDependency#getSrcObject()
	 * @see #getEdgeDependency()
	 * @generated
	 */
	EReference getEdgeDependency_SrcObject();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.EdgeDependency#getTgtObject <em>Tgt Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Tgt Object</em>'.
	 * @see org.sidiff.difference.asymmetric.EdgeDependency#getTgtObject()
	 * @see #getEdgeDependency()
	 * @generated
	 */
	EReference getEdgeDependency_TgtObject();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.EdgeDependency#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.difference.asymmetric.EdgeDependency#getType()
	 * @see #getEdgeDependency()
	 * @generated
	 */
	EReference getEdgeDependency_Type();

	/**
	 * Returns the meta object for class '{@link org.sidiff.difference.asymmetric.AttributeDependency <em>Attribute Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Dependency</em>'.
	 * @see org.sidiff.difference.asymmetric.AttributeDependency
	 * @generated
	 */
	EClass getAttributeDependency();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.AttributeDependency#getObject <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Object</em>'.
	 * @see org.sidiff.difference.asymmetric.AttributeDependency#getObject()
	 * @see #getAttributeDependency()
	 * @generated
	 */
	EReference getAttributeDependency_Object();

	/**
	 * Returns the meta object for the reference '{@link org.sidiff.difference.asymmetric.AttributeDependency#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.sidiff.difference.asymmetric.AttributeDependency#getType()
	 * @see #getAttributeDependency()
	 * @generated
	 */
	EReference getAttributeDependency_Type();

	/**
	 * Returns the meta object for enum '{@link org.sidiff.difference.asymmetric.DependencyKind <em>Dependency Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Dependency Kind</em>'.
	 * @see org.sidiff.difference.asymmetric.DependencyKind
	 * @generated
	 */
	EEnum getDependencyKind();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AsymmetricFactory getAsymmetricFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl <em>Difference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricDifferenceImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getAsymmetricDifference()
		 * @generated
		 */
		EClass ASYMMETRIC_DIFFERENCE = eINSTANCE.getAsymmetricDifference();

		/**
		 * The meta object literal for the '<em><b>Operation Invocations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASYMMETRIC_DIFFERENCE__OPERATION_INVOCATIONS = eINSTANCE.getAsymmetricDifference_OperationInvocations();

		/**
		 * The meta object literal for the '<em><b>Origin Model</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASYMMETRIC_DIFFERENCE__ORIGIN_MODEL = eINSTANCE.getAsymmetricDifference_OriginModel();

		/**
		 * The meta object literal for the '<em><b>Changed Model</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASYMMETRIC_DIFFERENCE__CHANGED_MODEL = eINSTANCE.getAsymmetricDifference_ChangedModel();

		/**
		 * The meta object literal for the '<em><b>Dep Containers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASYMMETRIC_DIFFERENCE__DEP_CONTAINERS = eINSTANCE.getAsymmetricDifference_DepContainers();

		/**
		 * The meta object literal for the '<em><b>Parameter Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASYMMETRIC_DIFFERENCE__PARAMETER_MAPPINGS = eINSTANCE.getAsymmetricDifference_ParameterMappings();

		/**
		 * The meta object literal for the '<em><b>Executions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASYMMETRIC_DIFFERENCE__EXECUTIONS = eINSTANCE.getAsymmetricDifference_Executions();

		/**
		 * The meta object literal for the '<em><b>Symmetric Difference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASYMMETRIC_DIFFERENCE__SYMMETRIC_DIFFERENCE = eINSTANCE.getAsymmetricDifference_SymmetricDifference();

		/**
		 * The meta object literal for the '<em><b>Uri Origin Model</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASYMMETRIC_DIFFERENCE__URI_ORIGIN_MODEL = eINSTANCE.getAsymmetricDifference_UriOriginModel();

		/**
		 * The meta object literal for the '<em><b>Uri Changed Model</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASYMMETRIC_DIFFERENCE__URI_CHANGED_MODEL = eINSTANCE.getAsymmetricDifference_UriChangedModel();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.OperationInvocationImpl <em>Operation Invocation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.OperationInvocationImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getOperationInvocation()
		 * @generated
		 */
		EClass OPERATION_INVOCATION = eINSTANCE.getOperationInvocation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_INVOCATION__NAME = eINSTANCE.getOperationInvocation_Name();

		/**
		 * The meta object literal for the '<em><b>Edit Rule Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_INVOCATION__EDIT_RULE_NAME = eINSTANCE.getOperationInvocation_EditRuleName();

		/**
		 * The meta object literal for the '<em><b>Parameter Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_INVOCATION__PARAMETER_BINDINGS = eINSTANCE.getOperationInvocation_ParameterBindings();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_INVOCATION__OUTGOING = eINSTANCE.getOperationInvocation_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_INVOCATION__INCOMING = eINSTANCE.getOperationInvocation_Incoming();

		/**
		 * The meta object literal for the '<em><b>Apply</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_INVOCATION__APPLY = eINSTANCE.getOperationInvocation_Apply();

		/**
		 * The meta object literal for the '<em><b>Change Set</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_INVOCATION__CHANGE_SET = eINSTANCE.getOperationInvocation_ChangeSet();

		/**
		 * The meta object literal for the '<em><b>Asymmetric Difference</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_INVOCATION__ASYMMETRIC_DIFFERENCE = eINSTANCE.getOperationInvocation_AsymmetricDifference();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.ParameterBindingImpl <em>Parameter Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.ParameterBindingImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getParameterBinding()
		 * @generated
		 */
		EClass PARAMETER_BINDING = eINSTANCE.getParameterBinding();

		/**
		 * The meta object literal for the '<em><b>Formal Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_BINDING__FORMAL_NAME = eINSTANCE.getParameterBinding_FormalName();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.ObjectParameterBindingImpl <em>Object Parameter Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.ObjectParameterBindingImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getObjectParameterBinding()
		 * @generated
		 */
		EClass OBJECT_PARAMETER_BINDING = eINSTANCE.getObjectParameterBinding();

		/**
		 * The meta object literal for the '<em><b>Actual A</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_PARAMETER_BINDING__ACTUAL_A = eINSTANCE.getObjectParameterBinding_ActualA();

		/**
		 * The meta object literal for the '<em><b>Actual B</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_PARAMETER_BINDING__ACTUAL_B = eINSTANCE.getObjectParameterBinding_ActualB();

		/**
		 * The meta object literal for the '<em><b>Outgoing</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_PARAMETER_BINDING__OUTGOING = eINSTANCE.getObjectParameterBinding_Outgoing();

		/**
		 * The meta object literal for the '<em><b>Incoming</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_PARAMETER_BINDING__INCOMING = eINSTANCE.getObjectParameterBinding_Incoming();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.ValueParameterBindingImpl <em>Value Parameter Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.ValueParameterBindingImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getValueParameterBinding()
		 * @generated
		 */
		EClass VALUE_PARAMETER_BINDING = eINSTANCE.getValueParameterBinding();

		/**
		 * The meta object literal for the '<em><b>Actual</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_PARAMETER_BINDING__ACTUAL = eINSTANCE.getValueParameterBinding_Actual();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.DependencyContainerImpl <em>Dependency Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.DependencyContainerImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getDependencyContainer()
		 * @generated
		 */
		EClass DEPENDENCY_CONTAINER = eINSTANCE.getDependencyContainer();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCY_CONTAINER__SOURCE = eINSTANCE.getDependencyContainer_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCY_CONTAINER__TARGET = eINSTANCE.getDependencyContainer_Target();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCY_CONTAINER__DEPENDENCIES = eINSTANCE.getDependencyContainer_Dependencies();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.DependencyImpl <em>Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.DependencyImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getDependency()
		 * @generated
		 */
		EClass DEPENDENCY = eINSTANCE.getDependency();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCY__SOURCE = eINSTANCE.getDependency_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCY__TARGET = eINSTANCE.getDependency_Target();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPENDENCY__KIND = eINSTANCE.getDependency_Kind();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.ParameterMappingImpl <em>Parameter Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.ParameterMappingImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getParameterMapping()
		 * @generated
		 */
		EClass PARAMETER_MAPPING = eINSTANCE.getParameterMapping();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER_MAPPING__SOURCE = eINSTANCE.getParameterMapping_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER_MAPPING__TARGET = eINSTANCE.getParameterMapping_Target();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.ParallelExecutionImpl <em>Parallel Execution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.ParallelExecutionImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getParallelExecution()
		 * @generated
		 */
		EClass PARALLEL_EXECUTION = eINSTANCE.getParallelExecution();

		/**
		 * The meta object literal for the '<em><b>Sub Executions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARALLEL_EXECUTION__SUB_EXECUTIONS = eINSTANCE.getParallelExecution_SubExecutions();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.SequentialExecutionImpl <em>Sequential Execution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.SequentialExecutionImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getSequentialExecution()
		 * @generated
		 */
		EClass SEQUENTIAL_EXECUTION = eINSTANCE.getSequentialExecution();

		/**
		 * The meta object literal for the '<em><b>Sub Executions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEQUENTIAL_EXECUTION__SUB_EXECUTIONS = eINSTANCE.getSequentialExecution_SubExecutions();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.ExecutionImpl <em>Execution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.ExecutionImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getExecution()
		 * @generated
		 */
		EClass EXECUTION = eINSTANCE.getExecution();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.MultiParameterBindingImpl <em>Multi Parameter Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.MultiParameterBindingImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getMultiParameterBinding()
		 * @generated
		 */
		EClass MULTI_PARAMETER_BINDING = eINSTANCE.getMultiParameterBinding();

		/**
		 * The meta object literal for the '<em><b>Parameter Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULTI_PARAMETER_BINDING__PARAMETER_BINDINGS = eINSTANCE.getMultiParameterBinding_ParameterBindings();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.NodeDependencyImpl <em>Node Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.NodeDependencyImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getNodeDependency()
		 * @generated
		 */
		EClass NODE_DEPENDENCY = eINSTANCE.getNodeDependency();

		/**
		 * The meta object literal for the '<em><b>Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_DEPENDENCY__OBJECT = eINSTANCE.getNodeDependency_Object();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.EdgeDependencyImpl <em>Edge Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.EdgeDependencyImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getEdgeDependency()
		 * @generated
		 */
		EClass EDGE_DEPENDENCY = eINSTANCE.getEdgeDependency();

		/**
		 * The meta object literal for the '<em><b>Src Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_DEPENDENCY__SRC_OBJECT = eINSTANCE.getEdgeDependency_SrcObject();

		/**
		 * The meta object literal for the '<em><b>Tgt Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_DEPENDENCY__TGT_OBJECT = eINSTANCE.getEdgeDependency_TgtObject();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_DEPENDENCY__TYPE = eINSTANCE.getEdgeDependency_Type();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.impl.AttributeDependencyImpl <em>Attribute Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.impl.AttributeDependencyImpl
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getAttributeDependency()
		 * @generated
		 */
		EClass ATTRIBUTE_DEPENDENCY = eINSTANCE.getAttributeDependency();

		/**
		 * The meta object literal for the '<em><b>Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_DEPENDENCY__OBJECT = eINSTANCE.getAttributeDependency_Object();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_DEPENDENCY__TYPE = eINSTANCE.getAttributeDependency_Type();

		/**
		 * The meta object literal for the '{@link org.sidiff.difference.asymmetric.DependencyKind <em>Dependency Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sidiff.difference.asymmetric.DependencyKind
		 * @see org.sidiff.difference.asymmetric.impl.AsymmetricPackageImpl#getDependencyKind()
		 * @generated
		 */
		EEnum DEPENDENCY_KIND = eINSTANCE.getDependencyKind();

	}

} //AsymmetricPackage
