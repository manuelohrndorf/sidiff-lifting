package org.sidiff.patching;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
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
	 * Argumente (EObjects in target), die beim letzten
	 * erfolgreichen/erfolglosen Ausführen der OperationInvocation verwendet
	 * wurden.
	 */
	private Map<ObjectParameterBinding, EObject> arguments;

	public OperationInvocationWrapper(OperationInvocation operationInvocation) {
		this.operationInvocation = operationInvocation;

		// Extract arguments
		arguments = new HashMap<ObjectParameterBinding, EObject>();
		for (ParameterBinding binding : operationInvocation.getParameterBindings()) {
			if (binding instanceof ObjectParameterBinding) {
				arguments.put((ObjectParameterBinding) binding, null);
			}
		}

		// set status
		status = OperationInvocationStatus.INIT;
	}

	public void setPassed(Map<ObjectParameterBinding, EObject> arguments) {
		copyArguments(arguments);
		status = OperationInvocationStatus.PASSED;
		executionError = null;
	}

	public void setSkippedDependency() {
		cleanArguments();
		status = OperationInvocationStatus.SKIPPED_DEPENDENCY;
		executionError = null;
	}

	public void setSkipped() {
		assert (!operationInvocation.isApply());
		cleanArguments();
		status = OperationInvocationStatus.SKIPPED;
		executionError = null;
	}

	public void setFailed(Map<ObjectParameterBinding, EObject> arguments, Exception executionError) {
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

	public Exception getExecutionError(){
		return executionError;
	}
	
	public boolean isUndoable(Map<ObjectParameterBinding, EObject> arguments) {
		if (status == OperationInvocationStatus.PASSED) {
			// Abgleich von arguments mit this.arguments
			assert (arguments.keySet().size() == this.arguments.keySet().size()) : "Programmierfehler!";
			
			for (ObjectParameterBinding binding : arguments.keySet()) {
				if (arguments.get(binding) != this.arguments.get(binding)){
					return false;
				}
			}
			
			// same arguments as for last invocation -> undoable 
			return true;
			
		} else {
			return false;
		}
	}

	private void copyArguments(Map<ObjectParameterBinding, EObject> arguments) {
//TODO wieder reinnehemen
//		for (ObjectParameterBinding binding : arguments.keySet()) {
//			this.arguments.put(binding, arguments.get(binding));
//		}
	}

	private void cleanArguments() {
//TODO wieder reinnehemen
//		for (ObjectParameterBinding binding : arguments.keySet()) {
//			this.arguments.put(binding, null);
//		}
	}

}