/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.sidiff.difference.asymmetric.*;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.AttributeDependency;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.DependencyKind;
import org.sidiff.difference.asymmetric.EdgeDependency;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.NodeDependency;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParallelExecution;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.difference.asymmetric.SequentialExecution;
import org.sidiff.difference.asymmetric.ValueParameterBinding;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AsymmetricFactoryImpl extends EFactoryImpl implements AsymmetricFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AsymmetricFactory init() {
		try {
			AsymmetricFactory theAsymmetricFactory = (AsymmetricFactory)EPackage.Registry.INSTANCE.getEFactory(AsymmetricPackage.eNS_URI);
			if (theAsymmetricFactory != null) {
				return theAsymmetricFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AsymmetricFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsymmetricFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case AsymmetricPackage.ASYMMETRIC_DIFFERENCE: return createAsymmetricDifference();
			case AsymmetricPackage.OPERATION_INVOCATION: return createOperationInvocation();
			case AsymmetricPackage.OBJECT_PARAMETER_BINDING: return createObjectParameterBinding();
			case AsymmetricPackage.VALUE_PARAMETER_BINDING: return createValueParameterBinding();
			case AsymmetricPackage.DEPENDENCY_CONTAINER: return createDependencyContainer();
			case AsymmetricPackage.PARAMETER_MAPPING: return createParameterMapping();
			case AsymmetricPackage.PARALLEL_EXECUTION: return createParallelExecution();
			case AsymmetricPackage.SEQUENTIAL_EXECUTION: return createSequentialExecution();
			case AsymmetricPackage.MULTI_PARAMETER_BINDING: return createMultiParameterBinding();
			case AsymmetricPackage.NODE_DEPENDENCY: return createNodeDependency();
			case AsymmetricPackage.EDGE_DEPENDENCY: return createEdgeDependency();
			case AsymmetricPackage.ATTRIBUTE_DEPENDENCY: return createAttributeDependency();
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
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case AsymmetricPackage.DEPENDENCY_KIND:
				return createDependencyKindFromString(eDataType, initialValue);
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
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case AsymmetricPackage.DEPENDENCY_KIND:
				return convertDependencyKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsymmetricDifference createAsymmetricDifference() {
		AsymmetricDifferenceImpl asymmetricDifference = new AsymmetricDifferenceImpl();
		return asymmetricDifference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationInvocation createOperationInvocation() {
		OperationInvocationImpl operationInvocation = new OperationInvocationImpl();
		return operationInvocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectParameterBinding createObjectParameterBinding() {
		ObjectParameterBindingImpl objectParameterBinding = new ObjectParameterBindingImpl();
		return objectParameterBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueParameterBinding createValueParameterBinding() {
		ValueParameterBindingImpl valueParameterBinding = new ValueParameterBindingImpl();
		return valueParameterBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependencyContainer createDependencyContainer() {
		DependencyContainerImpl dependencyContainer = new DependencyContainerImpl();
		return dependencyContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterMapping createParameterMapping() {
		ParameterMappingImpl parameterMapping = new ParameterMappingImpl();
		return parameterMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParallelExecution createParallelExecution() {
		ParallelExecutionImpl parallelExecution = new ParallelExecutionImpl();
		return parallelExecution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SequentialExecution createSequentialExecution() {
		SequentialExecutionImpl sequentialExecution = new SequentialExecutionImpl();
		return sequentialExecution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultiParameterBinding createMultiParameterBinding() {
		MultiParameterBindingImpl multiParameterBinding = new MultiParameterBindingImpl();
		return multiParameterBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeDependency createNodeDependency() {
		NodeDependencyImpl nodeDependency = new NodeDependencyImpl();
		return nodeDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgeDependency createEdgeDependency() {
		EdgeDependencyImpl edgeDependency = new EdgeDependencyImpl();
		return edgeDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeDependency createAttributeDependency() {
		AttributeDependencyImpl attributeDependency = new AttributeDependencyImpl();
		return attributeDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependencyKind createDependencyKindFromString(EDataType eDataType, String initialValue) {
		DependencyKind result = DependencyKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDependencyKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AsymmetricPackage getAsymmetricPackage() {
		return (AsymmetricPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AsymmetricPackage getPackage() {
		return AsymmetricPackage.eINSTANCE;
	}

} //AsymmetricFactoryImpl
