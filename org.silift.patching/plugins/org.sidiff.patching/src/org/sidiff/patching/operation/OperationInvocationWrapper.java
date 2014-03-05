package org.sidiff.patching.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.ecore.NameUtil;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.patching.arguments.ArgumentWrapper;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.arguments.ObjectArgumentWrapper;

/**
 * Encapsulates an operation invocation and keeps further information about the
 * execution state of the operation invocation.
 * 
 * @author kehrer
 */
public class OperationInvocationWrapper {

	/**
	 * The operation manager
	 */
	private OperationManager operationManager;

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

	/**
	 * The argument manager of this patch session.
	 */
	private IArgumentManager argumentManager;

	/**
	 * List of all actual arguments (each argument being encapsulated by an
	 * argument wrapper)
	 */
	private List<ArgumentWrapper> allActualArguments;

	OperationInvocationWrapper(OperationInvocation operationInvocation, OperationManager operationManager,
			IArgumentManager argumentManager) {
		this.operationInvocation = operationInvocation;
		this.operationManager = operationManager;
		this.argumentManager = argumentManager;

		// Extract arguments
		inArgs = new HashMap<ParameterBinding, Object>();
		for (ParameterBinding binding : operationInvocation.getInParameterBindings()) {
			inArgs.put(binding, null);
		}
		outArgs = new HashMap<ParameterBinding, Object>();
		for (ParameterBinding binding : operationInvocation.getOutParameterBindings()) {
			outArgs.put(binding, null);
		}

		// and now the actual ones
		allActualArguments = new ArrayList<ArgumentWrapper>(operationInvocation.getParameterBindings().size());
		for (ParameterBinding binding : operationInvocation.getParameterBindings()) {
			ArgumentWrapper wrapper = argumentManager.getArgument(binding);
			assert (wrapper != null);
			allActualArguments.add(wrapper);
		}
		allActualArguments = Collections.unmodifiableList(allActualArguments);

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

	/**
	 * Returns a list of all actual arguments (each argument being encapsulated
	 * by an argument wrapper).
	 * 
	 * @return
	 */
	public List<ArgumentWrapper> getAllActualArguments() {
		return allActualArguments;
	}

	/**
	 * Returns the actual argument (wrapped by an ArgumentWrapper) for the given
	 * parameter binding.
	 * 
	 * @param binding
	 * @return
	 */
	public ArgumentWrapper getActualArgument(ParameterBinding binding) {
		return argumentManager.getArgument(binding);
	}

	/**
	 * Returns the object which has been used as actual argument for the given
	 * parameter binding in terms of the last execution of the operation. Note
	 * that if binding is an OUT parameter, an object that has been created by
	 * the last execution of the operation is returned.
	 * 
	 * @param binding
	 * @return
	 */
	public Object getExecutionArgument(ParameterBinding binding) {
		if (binding.getFormalParameter().getDirection() == ParameterDirection.IN) {
			return inArgs.get(binding);
		} else {
			return outArgs.get(binding);
		}
	}

	/**
	 * 
	 * @return the argument manager for the current patch session.
	 */
	public IArgumentManager getArgumentManager() {
		return argumentManager;
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

	public List<OperationInvocationWrapper> getPredecessors() {
		List<OperationInvocation> operationPredecessors = operationInvocation.getPredecessors();
		List<OperationInvocationWrapper> wrapperPredecessors = new ArrayList<OperationInvocationWrapper>(
				operationPredecessors.size());
		for (OperationInvocation operation : operationPredecessors) {
			wrapperPredecessors.add(operationManager.getStatusWrapper(operation));
		}

		return Collections.unmodifiableList(wrapperPredecessors);
	}

	public String getText() {
		return operationManager.getOrderedOperationWrappers().indexOf(this) + ": "
				+ operationInvocation.getChangeSet().getName();
	}

	public boolean hasUnresolvedInArguments() {
		for (ArgumentWrapper argumentWrapper : allActualArguments) {
			if (argumentWrapper.getParameterBinding().getFormalParameter().getDirection() == ParameterDirection.IN) {
				if (!argumentWrapper.isResolved()) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasModifiedInArguments() {
		for (ArgumentWrapper argumentWrapper : allActualArguments) {
			if (argumentWrapper.getParameterBinding().getFormalParameter().getDirection() == ParameterDirection.IN) {
				if (argumentWrapper.isModified()) {
					return true;
				}
			}
		}

		return false;
	}

	public ArrayList<String> getChangedArguments() {
		ArrayList<String> arguments = new ArrayList<>();		
		for (ArgumentWrapper argumentWrapper : allActualArguments) {
			ParameterBinding binding = argumentWrapper.getParameterBinding();
			if (binding.getFormalParameter().getDirection() == ParameterDirection.IN) {				
				if (!argumentWrapper.isDefaultValue()) {					
					if(binding instanceof ValueParameterBinding){
						ValueParameterBinding valBinding = (ValueParameterBinding) binding;
						if (this.getStatus() == OperationInvocationStatus.PASSED) {
							Object actual = this.getExecutionArgument(valBinding);				
							if (actual != null) {
								arguments.add(actual.toString());
							}
						} else{
							arguments.add(valBinding.getActual());					
						}
					}
					else if (binding instanceof ObjectParameterBinding) {
						ObjectParameterBinding objBinding = (ObjectParameterBinding) binding;
						if (this.getStatus() == OperationInvocationStatus.PASSED) {
							EObject object = (EObject)this.getExecutionArgument(objBinding);
							arguments.add(NameUtil.getName(object));								
						} else {
							if (argumentWrapper.isResolved()) {
								EObject object = ((ObjectArgumentWrapper) argumentWrapper).getTargetObject();
								arguments.add(NameUtil.getName(object));								

							} else {
								EObject object = ((ObjectArgumentWrapper) argumentWrapper).getProxyObject();
								arguments.add(NameUtil.getName(object));								
							}
						}						
					}
				}
			}
		}

		return arguments;
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