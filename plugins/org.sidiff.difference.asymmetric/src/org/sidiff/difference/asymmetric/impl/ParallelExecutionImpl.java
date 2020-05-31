/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.sidiff.difference.asymmetric.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.sidiff.difference.asymmetric.AsymmetricPackage;
import org.sidiff.difference.asymmetric.Execution;
import org.sidiff.difference.asymmetric.ParallelExecution;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parallel Execution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sidiff.difference.asymmetric.impl.ParallelExecutionImpl#getSubExecutions <em>Sub Executions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParallelExecutionImpl extends ExecutionImpl implements ParallelExecution {
	/**
	 * The cached value of the '{@link #getSubExecutions() <em>Sub Executions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubExecutions()
	 * @generated
	 * @ordered
	 */
	protected EList<Execution> subExecutions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParallelExecutionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AsymmetricPackage.Literals.PARALLEL_EXECUTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Execution> getSubExecutions() {
		if (subExecutions == null) {
			subExecutions = new EObjectResolvingEList<Execution>(Execution.class, this, AsymmetricPackage.PARALLEL_EXECUTION__SUB_EXECUTIONS);
		}
		return subExecutions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AsymmetricPackage.PARALLEL_EXECUTION__SUB_EXECUTIONS:
				return getSubExecutions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AsymmetricPackage.PARALLEL_EXECUTION__SUB_EXECUTIONS:
				getSubExecutions().clear();
				getSubExecutions().addAll((Collection<? extends Execution>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AsymmetricPackage.PARALLEL_EXECUTION__SUB_EXECUTIONS:
				getSubExecutions().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AsymmetricPackage.PARALLEL_EXECUTION__SUB_EXECUTIONS:
				return subExecutions != null && !subExecutions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ParallelExecutionImpl
