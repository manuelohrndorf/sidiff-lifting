/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sequential Execution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.SequentialExecution#getSubExecutions <em>Sub Executions</em>}</li>
 * </ul>
 *
 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getSequentialExecution()
 * @model
 * @generated
 */
public interface SequentialExecution extends Execution {
	/**
	 * Returns the value of the '<em><b>Sub Executions</b></em>' reference list.
	 * The list contents are of type {@link org.sidiff.difference.asymmetric.Execution}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Executions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Executions</em>' reference list.
	 * @see org.sidiff.difference.asymmetric.AsymmetricPackage#getSequentialExecution_SubExecutions()
	 * @model
	 * @generated
	 */
	EList<Execution> getSubExecutions();

} // SequentialExecution
