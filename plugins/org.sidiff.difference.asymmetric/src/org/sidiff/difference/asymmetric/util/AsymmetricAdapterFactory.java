/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.AttributeDependency;
import org.sidiff.difference.asymmetric.Dependency;
import org.sidiff.difference.asymmetric.DependencyContainer;
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

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage
 * @generated
 */
public class AsymmetricAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AsymmetricPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsymmetricAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AsymmetricPackage.eINSTANCE;
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
	public boolean isFactoryForType(Object object) {
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
	protected AsymmetricSwitch<Adapter> modelSwitch =
		new AsymmetricSwitch<Adapter>() {
			@Override
			public Adapter caseAsymmetricDifference(AsymmetricDifference object) {
				return createAsymmetricDifferenceAdapter();
			}
			@Override
			public Adapter caseOperationInvocation(OperationInvocation object) {
				return createOperationInvocationAdapter();
			}
			@Override
			public Adapter caseParameterBinding(ParameterBinding object) {
				return createParameterBindingAdapter();
			}
			@Override
			public Adapter caseObjectParameterBinding(ObjectParameterBinding object) {
				return createObjectParameterBindingAdapter();
			}
			@Override
			public Adapter caseValueParameterBinding(ValueParameterBinding object) {
				return createValueParameterBindingAdapter();
			}
			@Override
			public Adapter caseDependencyContainer(DependencyContainer object) {
				return createDependencyContainerAdapter();
			}
			@Override
			public Adapter caseParameterMapping(ParameterMapping object) {
				return createParameterMappingAdapter();
			}
			@Override
			public Adapter caseParallelExecution(ParallelExecution object) {
				return createParallelExecutionAdapter();
			}
			@Override
			public Adapter caseSequentialExecution(SequentialExecution object) {
				return createSequentialExecutionAdapter();
			}
			@Override
			public Adapter caseExecution(Execution object) {
				return createExecutionAdapter();
			}
			@Override
			public Adapter caseMultiParameterBinding(MultiParameterBinding object) {
				return createMultiParameterBindingAdapter();
			}
			@Override
			public Adapter caseNodeDependency(NodeDependency object) {
				return createNodeDependencyAdapter();
			}
			@Override
			public Adapter caseEdgeDependency(EdgeDependency object) {
				return createEdgeDependencyAdapter();
			}
			@Override
			public Adapter caseAttributeDependency(AttributeDependency object) {
				return createAttributeDependencyAdapter();
			}
			@Override
			public Adapter caseDependency(Dependency object) {
				return createDependencyAdapter();
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
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.AsymmetricDifference <em>Difference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.AsymmetricDifference
	 * @generated
	 */
	public Adapter createAsymmetricDifferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.OperationInvocation <em>Operation Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.OperationInvocation
	 * @generated
	 */
	public Adapter createOperationInvocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.ParameterBinding <em>Parameter Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.ParameterBinding
	 * @generated
	 */
	public Adapter createParameterBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.ObjectParameterBinding <em>Object Parameter Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.ObjectParameterBinding
	 * @generated
	 */
	public Adapter createObjectParameterBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.ValueParameterBinding <em>Value Parameter Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.ValueParameterBinding
	 * @generated
	 */
	public Adapter createValueParameterBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.DependencyContainer <em>Dependency Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.DependencyContainer
	 * @generated
	 */
	public Adapter createDependencyContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.Dependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.Dependency
	 * @generated
	 */
	public Adapter createDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.ParameterMapping <em>Parameter Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.ParameterMapping
	 * @generated
	 */
	public Adapter createParameterMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.ParallelExecution <em>Parallel Execution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.ParallelExecution
	 * @generated
	 */
	public Adapter createParallelExecutionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.SequentialExecution <em>Sequential Execution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.SequentialExecution
	 * @generated
	 */
	public Adapter createSequentialExecutionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.Execution <em>Execution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.Execution
	 * @generated
	 */
	public Adapter createExecutionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.MultiParameterBinding <em>Multi Parameter Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.MultiParameterBinding
	 * @generated
	 */
	public Adapter createMultiParameterBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.NodeDependency <em>Node Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.NodeDependency
	 * @generated
	 */
	public Adapter createNodeDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.EdgeDependency <em>Edge Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.EdgeDependency
	 * @generated
	 */
	public Adapter createEdgeDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sidiff.difference.asymmetric.AttributeDependency <em>Attribute Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sidiff.difference.asymmetric.AttributeDependency
	 * @generated
	 */
	public Adapter createAttributeDependencyAdapter() {
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
	public Adapter createEObjectAdapter() {
		return null;
	}

} //AsymmetricAdapterFactory
