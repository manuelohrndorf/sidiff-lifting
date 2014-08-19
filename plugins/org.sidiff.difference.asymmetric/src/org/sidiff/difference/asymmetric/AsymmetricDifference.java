/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Difference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getOperationInvocations <em>Operation Invocations</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getOriginModel <em>Origin Model</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getChangedModel <em>Changed Model</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getDepContainers <em>Dep Containers</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getParameterMappings <em>Parameter Mappings</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getExecutions <em>Executions</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getSymmetricDifference <em>Symmetric Difference</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAsymmetricDifference()
 * @model
 * @generated
 */
public interface AsymmetricDifference extends EObject {
	/**
	 * Returns the value of the '<em><b>Operation Invocations</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.asymmetric.OperationInvocation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation Invocations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Invocations</em>' containment reference list.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAsymmetricDifference_OperationInvocations()
	 * @model containment="true"
	 * @generated
	 */
	EList<OperationInvocation> getOperationInvocations();

	/**
	 * Returns the value of the '<em><b>Origin Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Origin Model</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Origin Model</em>' attribute.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAsymmetricDifference_OriginModel()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Resource getOriginModel();

	/**
	 * Returns the value of the '<em><b>Changed Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Changed Model</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Changed Model</em>' attribute.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAsymmetricDifference_ChangedModel()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Resource getChangedModel();

	/**
	 * Returns the value of the '<em><b>Dep Containers</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.asymmetric.DependencyContainer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dep Containers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dep Containers</em>' containment reference list.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAsymmetricDifference_DepContainers()
	 * @model containment="true"
	 * @generated
	 */
	EList<DependencyContainer> getDepContainers();

	/**
	 * Returns the value of the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.asymmetric.ParameterMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Mappings</em>' containment reference list.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAsymmetricDifference_ParameterMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterMapping> getParameterMappings();

	/**
	 * Returns the value of the '<em><b>Executions</b></em>' containment reference list.
	 * The list contents are of type {@link org.sidiff.difference.asymmetric.Execution}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Executions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Executions</em>' containment reference list.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAsymmetricDifference_Executions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Execution> getExecutions();

	/**
	 * Returns the value of the '<em><b>Symmetric Difference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symmetric Difference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symmetric Difference</em>' reference.
	 * @see #setSymmetricDifference(SymmetricDifference)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getAsymmetricDifference_SymmetricDifference()
	 * @model
	 * @generated
	 */
	SymmetricDifference getSymmetricDifference();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.AsymmetricDifference#getSymmetricDifference <em>Symmetric Difference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symmetric Difference</em>' reference.
	 * @see #getSymmetricDifference()
	 * @generated
	 */
	void setSymmetricDifference(SymmetricDifference value);
	
	void initRuleBases();
	
	List<RuleBase> getRuleBases();

} // AsymmetricDifference
