/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage
 * @generated
 */
public interface AsymmetricFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AsymmetricFactory eINSTANCE = org.sidiff.difference.asymmetric.impl.AsymmetricFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Difference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Difference</em>'.
	 * @generated
	 */
	AsymmetricDifference createAsymmetricDifference();

	/**
	 * Returns a new object of class '<em>Operation Invocation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Invocation</em>'.
	 * @generated
	 */
	OperationInvocation createOperationInvocation();

	/**
	 * Returns a new object of class '<em>Object Parameter Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Object Parameter Binding</em>'.
	 * @generated
	 */
	ObjectParameterBinding createObjectParameterBinding();

	/**
	 * Returns a new object of class '<em>Value Parameter Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Value Parameter Binding</em>'.
	 * @generated
	 */
	ValueParameterBinding createValueParameterBinding();

	/**
	 * Returns a new object of class '<em>Dependency Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dependency Container</em>'.
	 * @generated
	 */
	DependencyContainer createDependencyContainer();

	/**
	 * Returns a new object of class '<em>Parameter Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter Mapping</em>'.
	 * @generated
	 */
	ParameterMapping createParameterMapping();

	/**
	 * Returns a new object of class '<em>Parallel Execution</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parallel Execution</em>'.
	 * @generated
	 */
	ParallelExecution createParallelExecution();

	/**
	 * Returns a new object of class '<em>Sequential Execution</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sequential Execution</em>'.
	 * @generated
	 */
	SequentialExecution createSequentialExecution();

	/**
	 * Returns a new object of class '<em>Multi Parameter Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Multi Parameter Binding</em>'.
	 * @generated
	 */
	MultiParameterBinding createMultiParameterBinding();

	/**
	 * Returns a new object of class '<em>Node Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node Dependency</em>'.
	 * @generated
	 */
	NodeDependency createNodeDependency();

	/**
	 * Returns a new object of class '<em>Edge Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Edge Dependency</em>'.
	 * @generated
	 */
	EdgeDependency createEdgeDependency();

	/**
	 * Returns a new object of class '<em>Attribute Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Dependency</em>'.
	 * @generated
	 */
	AttributeDependency createAttributeDependency();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AsymmetricPackage getAsymmetricPackage();

} //AsymmetricFactory
