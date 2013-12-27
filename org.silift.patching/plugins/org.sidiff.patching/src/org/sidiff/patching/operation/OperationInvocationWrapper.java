package org.sidiff.patching.operation;

import java.util.HashMap;
import java.util.Map;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;

public class OperationInvocationWrapper {

	/**
	 * The operation invocation to be encapsulated.
	 */
	private OperationInvocation operationInvocation;

	/**
	 * Execution state.
	 */
	private OperationInvocationStatus status;

	/**
	 * Error in case of execution failure.
	 */
	private Exception executionError;

	/**
	 * Argumente (EObjects in target bzw. "primitive" values), die beim letzten
	 * erfolgreichen/erfolglosen Ausführen der OperationInvocation verwendet
	 * wurden.
	 */
	private Map<ParameterBinding, Object> arguments;

	OperationInvocationWrapper(OperationInvocation operationInvocation) {
		this.operationInvocation = operationInvocation;

		// Extract arguments
		arguments = new HashMap<ParameterBinding, Object>();
		for (ParameterBinding binding : operationInvocation.getParameterBindings()) {
			if (binding instanceof ParameterBinding) {
				arguments.put((ParameterBinding) binding, null);
			}
		}

		// set status
		status = OperationInvocationStatus.INIT;
	}

	public void setPassed(Map<ParameterBinding, Object> arguments) {
		copyArguments(arguments);
		status = OperationInvocationStatus.PASSED;
		executionError = null;
	}

	public void setSkipped() {
		assert (!operationInvocation.isApply());
		cleanArguments();
		status = OperationInvocationStatus.SKIPPED;
		executionError = null;
	}

	public void setFailed(Map<ParameterBinding, Object> arguments, Exception executionError) {
		copyArguments(arguments);
		status = OperationInvocationStatus.FAILED;
		this.executionError = executionError;
	}

	public OperationInvocation getOperationInvocation() {
		return operationInvocation;
	}

	public OperationInvocationStatus getStatus() {
		return status;
	}

	public Exception getExecutionError() {
		return executionError;
	}

	public Object getInvocationArgument(ParameterBinding binding){
		assert(status == OperationInvocationStatus.PASSED || status == OperationInvocationStatus.FAILED);
		return arguments.get(binding);
	}
	
	public boolean isUndoable(Map<ParameterBinding, Object> arguments) {
		// if (status == OperationInvocationStatus.PASSED) {
		// // Abgleich von arguments mit this.arguments
		// assert (arguments.keySet().size() == this.arguments.keySet().size());
		//
		// for (ParameterBinding binding : arguments.keySet()) {
		// if (arguments.get(binding) != this.arguments.get(binding)){
		// return false;
		// }
		// }
		//
		// // arguments of last invocation still existent -> undoable
		// return true;
		//
		// } else {
		// return false;
		// }
		return true;
	}

	private void copyArguments(Map<ParameterBinding, Object> arguments) {
		for (ParameterBinding binding : arguments.keySet()) {
			this.arguments.put(binding, arguments.get(binding));
		}
	}

	private void cleanArguments() {
		for (ParameterBinding binding : arguments.keySet()) {
			this.arguments.put(binding, null);
		}
	}

}