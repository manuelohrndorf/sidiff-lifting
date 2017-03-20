package org.sidiff.patching.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.arguments.IArgumentManager;

/**
 * The operation manager is the central entity for the patch engine in order to
 * get information about operation invocations or their current execution state.
 * 
 * 
 * @author kehrer
 */
public class OperationManager {

	/**
	 * Mapping OperationInvocation <-> OperationInvocationWrapper
	 */
	private Map<OperationInvocation, OperationInvocationWrapper> operation2Wrapper;

	/**
	 * Unmodifiable list of ordered operation wrappers
	 */
	private List<OperationInvocationWrapper> orderedOperationWrappers;

	/**
	 * Unmodifiable list of ordered operations
	 */
	private List<OperationInvocation> orderedOperations;

	/**
	 * Used for sorting the operation invocations
	 */
	private int stage;

	public OperationManager(AsymmetricDifference editScript, IArgumentManager argumentManager) {
		stage = 0;
		operation2Wrapper = new HashMap<OperationInvocation, OperationInvocationWrapper>();

		List<OperationInvocation> unorderedOperations = new ArrayList<OperationInvocation>(editScript
				.getOperationInvocations().size());
		unorderedOperations.addAll(editScript.getOperationInvocations());

		// filter operations with isApply() == false
		for (Iterator<OperationInvocation> iterator = unorderedOperations.iterator(); iterator.hasNext();) {
			OperationInvocation op = iterator.next();
			if (!op.isApply()) {
				iterator.remove();
			}
		}

		// Sort
		orderedOperations = getOrderdOperationInvocations(unorderedOperations);

		// Create Operation Wrappers
		orderedOperationWrappers = new LinkedList<OperationInvocationWrapper>();
		for (OperationInvocation op : orderedOperations) {
			OperationInvocationWrapper wrapper = new OperationInvocationWrapper(op, this, argumentManager);
			operation2Wrapper.put(op, wrapper);
			orderedOperationWrappers.add(wrapper);
		}

		// make operationWrapper list unmodifiable
		orderedOperationWrappers = Collections.unmodifiableList(orderedOperationWrappers);
	}

	/**
	 * Get the status wrapper for the given operation invocation.
	 * 
	 * @param op
	 * @return
	 */
	public OperationInvocationWrapper getStatusWrapper(OperationInvocation op) {
		return operation2Wrapper.get(op);
	}

	/**
	 * Return all operation invocations that have the given status.
	 * 
	 * @param status
	 * @return
	 */
	public List<OperationInvocation> getOperationInvocations(OperationInvocationStatus status) {
		List<OperationInvocation> res = new ArrayList<OperationInvocation>();
		for (OperationInvocation op : operation2Wrapper.keySet()) {
			OperationInvocationWrapper opWrapper = operation2Wrapper.get(op);
			if (opWrapper.getStatus() == status) {
				res.add(op);
			}
		}

		return res;
	}

	public boolean allPredecessorsExecuted(OperationInvocation operationInvocation) {
		List<OperationInvocation> executed = getOperationInvocations(OperationInvocationStatus.PASSED);
		for (DependencyContainer dependency : operationInvocation.getOutgoing()) {
			OperationInvocation incomingOperation = dependency.getTarget();
			if (!executed.contains(incomingOperation)) {
				return false;
			}
		}

		return true;
	}

	public List<OperationInvocationWrapper> getOrderedOperationWrappers() {
		return orderedOperationWrappers;
	}

	public List<OperationInvocation> getOrderedOperations() {
		return orderedOperations;
	}

	/**
	 * Sorts OperationInvocations in a processable order (i.e. an order that is
	 * consistent with the partial order given by the sequential dependencies
	 * between operation invocations) and returns a flat list.
	 * 
	 * @return sorted list of OperationInvocation
	 */
	private List<OperationInvocation> getOrderdOperationInvocations(
			List<OperationInvocation> unorderdOperationInvocations) {
		List<OperationInvocation> operationInvocations = new ArrayList<OperationInvocation>();
		operationInvocations = sortDFS(unorderdOperationInvocations);
		return Collections.unmodifiableList(operationInvocations);
	}

	/**
	 * Sorts the OperationInvocations in dependency order using the depth-first
	 * search algorithm
	 * 
	 * @param unorderdOperationInvocations
	 */
	private List<OperationInvocation> sortDFS(List<OperationInvocation> unorderdOperationInvocations) {
		List<OperationInvocation> operationInvocations = new ArrayList<OperationInvocation>();
		for (OperationInvocation operationInvocation : unorderdOperationInvocations) {
			if (operationInvocation.getOutgoing().isEmpty()) {
				addIncomingOperations(operationInvocations, operationInvocation);
			}
		}
		return operationInvocations;
	}

	/**
	 * Internal utility method for sorting operation invocations
	 * 
	 * @param operationInvocations
	 * @param invocation
	 */
	private void addIncomingOperations(List<OperationInvocation> operationInvocations, OperationInvocation invocation) {
		if (!operationInvocations.contains(invocation) && invocation.isApply()) {
			stage++;
			for (OperationInvocation operationInvocation : invocation.getSuccessors()) {
				addIncomingOperations(operationInvocations, operationInvocation);
			}
			operationInvocations.add(0, invocation);
			LogUtil.log(LogEvent.DEBUG, "Stage: " + stage + " " + invocation.getChangeSet().getName());
			stage--;
		}
	}
}
