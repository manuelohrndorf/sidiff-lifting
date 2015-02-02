/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
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
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage
 * @generated
 */
public class AsymmetricSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AsymmetricPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsymmetricSwitch() {
		if (modelPackage == null) {
			modelPackage = AsymmetricPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
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
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE: {
				AsymmetricDifference asymmetricDifference = (AsymmetricDifference)theEObject;
				T result = caseAsymmetricDifference(asymmetricDifference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.OPERATION_INVOCATION: {
				OperationInvocation operationInvocation = (OperationInvocation)theEObject;
				T result = caseOperationInvocation(operationInvocation);
				if (result == null) result = caseExecution(operationInvocation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.PARAMETER_BINDING: {
				ParameterBinding parameterBinding = (ParameterBinding)theEObject;
				T result = caseParameterBinding(parameterBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING: {
				ObjectParameterBinding objectParameterBinding = (ObjectParameterBinding)theEObject;
				T result = caseObjectParameterBinding(objectParameterBinding);
				if (result == null) result = caseParameterBinding(objectParameterBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.VALUE_PARAMETER_BINDING: {
				ValueParameterBinding valueParameterBinding = (ValueParameterBinding)theEObject;
				T result = caseValueParameterBinding(valueParameterBinding);
				if (result == null) result = caseParameterBinding(valueParameterBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.DEPENDENCY_CONTAINER: {
				DependencyContainer dependencyContainer = (DependencyContainer)theEObject;
				T result = caseDependencyContainer(dependencyContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.PARAMETER_MAPPING: {
				ParameterMapping parameterMapping = (ParameterMapping)theEObject;
				T result = caseParameterMapping(parameterMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.PARALLEL_EXECUTION: {
				ParallelExecution parallelExecution = (ParallelExecution)theEObject;
				T result = caseParallelExecution(parallelExecution);
				if (result == null) result = caseExecution(parallelExecution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.SEQUENTIAL_EXECUTION: {
				SequentialExecution sequentialExecution = (SequentialExecution)theEObject;
				T result = caseSequentialExecution(sequentialExecution);
				if (result == null) result = caseExecution(sequentialExecution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.EXECUTION: {
				Execution execution = (Execution)theEObject;
				T result = caseExecution(execution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.MULTI_PARAMETER_BINDING: {
				MultiParameterBinding multiParameterBinding = (MultiParameterBinding)theEObject;
				T result = caseMultiParameterBinding(multiParameterBinding);
				if (result == null) result = caseParameterBinding(multiParameterBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.NODE_DEPENDENCY: {
				NodeDependency nodeDependency = (NodeDependency)theEObject;
				T result = caseNodeDependency(nodeDependency);
				if (result == null) result = caseDependency(nodeDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.EDGE_DEPENDENCY: {
				EdgeDependency edgeDependency = (EdgeDependency)theEObject;
				T result = caseEdgeDependency(edgeDependency);
				if (result == null) result = caseDependency(edgeDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.ATTRIBUTE_DEPENDENCY: {
				AttributeDependency attributeDependency = (AttributeDependency)theEObject;
				T result = caseAttributeDependency(attributeDependency);
				if (result == null) result = caseDependency(attributeDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AsymmetricPackage.DEPENDENCY: {
				Dependency dependency = (Dependency)theEObject;
				T result = caseDependency(dependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Difference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Difference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAsymmetricDifference(AsymmetricDifference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operation Invocation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operation Invocation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperationInvocation(OperationInvocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterBinding(ParameterBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Parameter Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Parameter Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectParameterBinding(ObjectParameterBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Parameter Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Parameter Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValueParameterBinding(ValueParameterBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dependency Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dependency Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDependencyContainer(DependencyContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDependency(Dependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterMapping(ParameterMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parallel Execution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parallel Execution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParallelExecution(ParallelExecution object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sequential Execution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequential Execution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSequentialExecution(SequentialExecution object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Execution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Execution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExecution(Execution object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multi Parameter Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multi Parameter Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultiParameterBinding(MultiParameterBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNodeDependency(NodeDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Edge Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Edge Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEdgeDependency(EdgeDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeDependency(AttributeDependency object) {
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
	public T defaultCase(EObject object) {
		return null;
	}

} //AsymmetricSwitch
