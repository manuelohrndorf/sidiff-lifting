package org.sidiff.patching.operation;

import java.util.HashMap;
import java.util.Map;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.rulebase.ParameterDirection;

/**
 * Encapsulates an operation invocation and keeps further information about the
 * execution state of the operation invocation.
 * 
 * @author kehrer
 */
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
	 * IN-Argumente (EObjects in target bzw. "primitive" values), die beim
	 * letzten erfolgreichen/erfolglosen Ausführen der OperationInvocation
	 * verwendet wurden.
	 */
	private Map<ParameterBinding, Object> inArgs;

	/**
	 * OUT-Argumente (EObjects), die beim letzten erfolgreichen Ausführen der
	 * OperationInvocation erzeugt wurden.
	 */
	private Map<ParameterBinding, Object> outArgs;

	OperationInvocationWrapper(OperationInvocation operationInvocation) {
		this.operationInvocation = operationInvocation;

		// Extract arguments
		inArgs = new HashMap<ParameterBinding, Object>();
		for (ParameterBinding binding : operationInvocation.getInParameterBindings()) {
			inArgs.put(binding, null);
		}
		outArgs = new HashMap<ParameterBinding, Object>();
		for (ParameterBinding binding : operationInvocation.getOutParameterBindings()) {
			outArgs.put(binding, null);
		}

		// set status
		status = OperationInvocationStatus.INIT;
	}

	public void setPassed(Map<ParameterBinding, Object> inArgs, Map<ParameterBinding, Object> outArgs) {

		copyArguments(inArgs, this.inArgs);
		copyArguments(outArgs, this.outArgs);
		status = OperationInvocationStatus.PASSED;
		executionError = null;
	}

	public void setSkipped() {
		cleanArguments(inArgs);
		cleanArguments(outArgs);
		status = OperationInvocationStatus.SKIPPED;
		executionError = null;
	}

	public void setFailed(Map<ParameterBinding, Object> inArgs, Exception executionError) {
		copyArguments(inArgs, this.inArgs);
		cleanArguments(outArgs);
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

	public Object getArgument(ParameterBinding binding) {
		if (binding.getFormalParameter().getDirection() == ParameterDirection.IN) {
			return getInArgument(binding);
		} else {
			return getOutArgument(binding);
		}
	}

	public Object getInArgument(ParameterBinding binding) {
		assert (status == OperationInvocationStatus.PASSED || status == OperationInvocationStatus.FAILED);

		return inArgs.get(binding);
	}

	public Object getOutArgument(ParameterBinding binding) {
		assert (status == OperationInvocationStatus.PASSED);

		return outArgs.get(binding);
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

	private void copyArguments(Map<ParameterBinding, Object> from, Map<ParameterBinding, Object> to) {
		for (ParameterBinding binding : from.keySet()) {
			to.put(binding, from.get(binding));
		}
	}

	private void cleanArguments(Map<ParameterBinding, Object> args) {
		for (ParameterBinding binding : args.keySet()) {
			args.put(binding, null);
		}
	}

}