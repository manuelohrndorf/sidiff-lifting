/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.symmetric.SemanticChangeSet;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Operation Invocation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.OperationInvocation#getParameterBindings <em>Parameter Bindings</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.OperationInvocation#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.OperationInvocation#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.OperationInvocation#isApply <em>Apply</em>}</li>
 *   <li>{@link org.sidiff.difference.asymmetric.OperationInvocation#getChangeSet <em>Change Set</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getOperationInvocation()
 * @model
 * @generated
 */
public interface OperationInvocation extends Execution {
	/**
	 * Returns the value of the '<em><b>Parameter Bindings</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.sidiff.difference.asymmetric.ParameterBinding}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Bindings</em>' containment reference
	 * list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameter Bindings</em>' containment
	 *         reference list.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getOperationInvocation_ParameterBindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterBinding> getParameterBindings();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.difference.asymmetric.DependencyContainer}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.asymmetric.DependencyContainer#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getOperationInvocation_Outgoing()
	 * @see org.sidiff.difference.asymmetric.DependencyContainer#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<DependencyContainer> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.difference.asymmetric.DependencyContainer}.
	 * It is bidirectional and its opposite is '{@link org.sidiff.difference.asymmetric.DependencyContainer#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getOperationInvocation_Incoming()
	 * @see org.sidiff.difference.asymmetric.DependencyContainer#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<DependencyContainer> getIncoming();

	/**
	 * Returns the value of the '<em><b>Apply</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Apply</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Apply</em>' attribute.
	 * @see #setApply(boolean)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getOperationInvocation_Apply()
	 * @model default="true"
	 * @generated
	 */
	boolean isApply();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.OperationInvocation#isApply <em>Apply</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Apply</em>' attribute.
	 * @see #isApply()
	 * @generated
	 */
	void setApply(boolean value);

	/**
	 * Returns the value of the '<em><b>Change Set</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change Set</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Change Set</em>' reference.
	 * @see #setChangeSet(SemanticChangeSet)
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getOperationInvocation_ChangeSet()
	 * @model
	 * @generated
	 */
	SemanticChangeSet getChangeSet();

	/**
	 * Sets the value of the '{@link org.sidiff.difference.asymmetric.OperationInvocation#getChangeSet <em>Change Set</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Change Set</em>' reference.
	 * @see #getChangeSet()
	 * @generated
	 */
	void setChangeSet(SemanticChangeSet value);

	/**
	 * Return whether this is the invocation of a complex or an atomic edit
	 * operation.
	 * 
	 * @return
	 * @generated NOT
	 */
	boolean isComplex();

	/**
	 * Returns the direct predecessors of this operation invocation. In other
	 * words, the operation invocations to which this op has a direct sequential
	 * dependency.
	 * 
	 * @return
	 * @generated NOT
	 */
	List<OperationInvocation> getPredecessors();

	/**
	 * Returns the direct successors of this operation invocation. In other
	 * words, the operation invocations which have a direct sequential
	 * dependency to this op.
	 * 
	 * @return
	 * @generated NOT
	 */
	List<OperationInvocation> getSuccessors();

	/**
	 * Returns all predecessors of this operation invocation. In other words,
	 * the operation invocations to which this op has a sequential dependency,
	 * either directly or transitively.
	 * 
	 * @return
	 * @generated NOT
	 */
	List<OperationInvocation> getAllPredecessors();

	/**
	 * Returns all successors of this operation invocation. In other words, the
	 * operation invocations which have a sequential dependency to this op,
	 * either directly or transitively.
	 * 
	 * @return
	 * @generated NOT
	 */
	List<OperationInvocation> getAllSuccessors();
	
	/**
	 * Returns all parameter bindings for IN-Parameters.
	 * 
	 * @return
	 * @generated NOT
	 */
	List<ParameterBinding> getInParameterBindings();
	
	/**
	 * Returns all parameter bindings for OUT-Parameters.
	 * 
	 * @return
	 * @generated NOT
	 */
	List<ParameterBinding> getOutParameterBindings();

	EditRule resolveEditRule();
	
} // OperationInvocation
