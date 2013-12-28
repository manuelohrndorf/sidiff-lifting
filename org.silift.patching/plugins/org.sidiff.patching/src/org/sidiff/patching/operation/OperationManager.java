package org.sidiff.patching.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;

/**
 * The operation manager is the central entity for the patch engine in order to
 * get information about operation invocations or their current execution state.
 * 
 * 
 * @author kehrer
 */
public class OperationManager {

	private Map<OperationInvocation, OperationInvocationWrapper> statusWrappers;

	public OperationManager(List<OperationInvocation> operationInvocations) {
		statusWrappers = new HashMap<OperationInvocation, OperationInvocationWrapper>();
		for (OperationInvocation op : operationInvocations) {
			statusWrappers.put(op, new OperationInvocationWrapper(op));
		}
	}

	/**
	 * Get the status wrapper for the given operation invocation.
	 * 
	 * @param op
	 * @return
	 */
	public OperationInvocationWrapper getStatusWrapper(OperationInvocation op) {
		return statusWrappers.get(op);
	}

	/**
	 * Return all operation invocations that have the given status.
	 * 
	 * @param status
	 * @return
	 */
	public List<OperationInvocation> getOperationInvocations(OperationInvocationStatus status) {
		List<OperationInvocation> res = new ArrayList<OperationInvocation>();
		for (OperationInvocation op : statusWrappers.keySet()) {
			OperationInvocationWrapper opWrapper = statusWrappers.get(op);
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
}
