package org.sidiff.patching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.rulebase.Parameter;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.patching.arguments.ArgumentWrapper;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.OperationNotUndoableException;
import org.sidiff.patching.exceptions.ParameterMissingException;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.interrupt.PatchInterruptOption;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.operation.OperationManager;
import org.sidiff.patching.report.PatchReportManager;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.util.PatchUtil;
import org.sidiff.patching.validation.IValidationError;
import org.sidiff.patching.validation.ValidationManager;
import org.sidiff.patching.validation.ValidationMode;
import org.silift.common.util.emf.Scope;

/**
 * 
 * @author Dennis Koch, kehrer, dreuling
 */
public class PatchEngine {

	public enum ExecutionMode {
		INTERACTIVE, BATCH
	}

	public enum PatchMode {
		PATCHING, MERGING
	}

	private AsymmetricDifference difference;
	private List<OperationInvocation> orderedOperations;
	private Resource patchedResource;
	private EditingDomain patchedEditingDomain;
	private Boolean reliabilitiesComputed;
	private ExecutionMode executionMode;
	private PatchMode patchMode;

	private ValidationManager validationManager;
	private OperationManager operationManager;
	private IArgumentManager argumentManager;
	private ITransformationEngine transformationEngine;
	private PatchReportManager reportManager;
	private IPatchInterruptHandler patchInterruptHandler;
	private Boolean validationChanged;

	/**
	 * The PatchEngine handles manipulations on target model.
	 * 
	 * @param difference
	 * @param targetResource
	 * @param argumentManager
	 * @param transformationEngine
	 * @param executionMode
	 * @param validationMode
	 * @param reliabilitiesComputed
	 * @param patchInterruptHandler
	 */
	public PatchEngine(AsymmetricDifference difference, Resource patchedResource, IArgumentManager argumentManager,
			ITransformationEngine transformationEngine, ExecutionMode executionMode, PatchMode patchMode,
			ValidationMode validationMode, Scope scope, Boolean reliabilitiesComputed,
			IPatchInterruptHandler patchInterruptHandler) {

		this.difference = difference;
		this.patchedResource = patchedResource;
		this.argumentManager = argumentManager;
		this.transformationEngine = transformationEngine;
		this.reliabilitiesComputed = reliabilitiesComputed;
		this.executionMode = executionMode;
		this.patchMode = patchMode;

		// Ordered set of operations to be executed
		this.orderedOperations = PatchUtil.getOrderdOperationInvocations(difference.getOperationInvocations());

		// Init managers
		this.validationManager = new ValidationManager(validationMode, patchedResource);
		this.operationManager = new OperationManager(orderedOperations);
		this.argumentManager.init(difference, patchedResource, scope, patchMode);

		// Initialize all operationInvocations owning
		// modified parameters as "not applicable"
		for (OperationInvocation operationInvocation : orderedOperations) {
			if (hasModifiedParameters(operationInvocation)) {
				operationInvocation.setApply(false);
			}
		}

		// Init transformation engine
		this.transformationEngine.init(patchedResource, executionMode, scope);

		// Init report manager
		this.reportManager = new PatchReportManager();

		// Init patch interrupt handler
		this.patchInterruptHandler = patchInterruptHandler;
		
		this.validationChanged = false;

	}

	/**
	 * Runs over all OperationInvocations and checks which of them are to be
	 * executed and which are to be reverted. <br/>
	 * The execution and undo themselves are delegated to the private methods
	 * {@link #apply(OperationInvocation)} and
	 * {@link #revert(OperationInvocation)}, respectively.
	 * 
	 * @return
	 */
	public void applyPatch() {
		// Start new patch application
		reportManager.startPatchApplication();

		// Initial validation (if needed)
		if (getValidationMode() != ValidationMode.MANUAL) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			reportManager.updateValidationEntries(validationErrors);
		}

		// Try to execute operations which are to apply
		for (OperationInvocation operationInvocation : orderedOperations) {
			OperationInvocationWrapper operationWrapper = operationManager.getStatusWrapper(operationInvocation);
			if (operationInvocation.isApply() && !(operationWrapper.getStatus() == OperationInvocationStatus.PASSED)) {

				boolean success = apply(operationInvocation);
				
				if (success && (getValidationMode() == ValidationMode.ITERATIVE)) {				
					if (this.validationChanged) {
						this.validationChanged = false;
						Collection<IValidationError> newErrors = reportManager.getLastReport()
								.getLastValidationEntry().getNewValidationErrors();
						Collection<IValidationError> validationErrors = newErrors;

						if (!newErrors.isEmpty() && (executionMode == ExecutionMode.INTERACTIVE)) {
							PatchInterruptOption option = patchInterruptHandler.getInterruptOption(false,
									operationInvocation, validationErrors);

							if (option != PatchInterruptOption.IGNORE) {
								operationInvocation.setApply(true);
								apply(operationInvocation);
								validationErrors = validationManager.validateTargetModel();
								reportManager.updateValidationEntries(validationErrors);
							}

							if (option == PatchInterruptOption.ABORT) {
								break;
							}
						}
					}
				}
			}
		}

		// Try to undo operations which are to revert
		ListIterator<OperationInvocation> li = orderedOperations.listIterator(orderedOperations.size());
		while (li.hasPrevious()) {
			OperationInvocation operationInvocation = (OperationInvocation) li.previous();
			OperationInvocationWrapper operationWrapper = operationManager.getStatusWrapper(operationInvocation);
			if (!operationInvocation.isApply()) {
				if (operationWrapper.getStatus() == OperationInvocationStatus.INIT) {
					operationWrapper.setSkipped();
					reportManager.operationSkipped(operationInvocation);
				} else if (operationWrapper.getStatus() == OperationInvocationStatus.FAILED
						|| operationWrapper.getStatus() == OperationInvocationStatus.SKIPPED) {
					// nothing to do
				} else {

					boolean success = revert(operationInvocation);

					if (success && (getValidationMode() == ValidationMode.ITERATIVE)) {
						if (this.validationChanged) {
							this.validationChanged = false;
							Collection<IValidationError> newErrors = reportManager.getLastReport()
									.getLastValidationEntry().getNewValidationErrors();
							Collection<IValidationError> validationErrors = newErrors;
							
							if (!newErrors.isEmpty() && (executionMode == ExecutionMode.INTERACTIVE)) {
								PatchInterruptOption option = patchInterruptHandler.getInterruptOption(true,
										operationInvocation, validationErrors);

								if (option != PatchInterruptOption.IGNORE) {
									operationInvocation.setApply(true);
									apply(operationInvocation);
									validationErrors = validationManager.validateTargetModel();
									reportManager.updateValidationEntries(validationErrors);
								}

								if (option == PatchInterruptOption.ABORT) {
									break;
								}
							}
						}
					}
				}
			}
		}

		// Final validation (if needed)
		if (getValidationMode() == ValidationMode.FINAL || getValidationMode() == ValidationMode.ITERATIVE) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			reportManager.updateValidationEntries(validationErrors);
		}
	}

	/**
	 * Apply operation invocation. Note that this method tries to perform the
	 * operation execution on the command stack of the target editing domain.
	 * 
	 * @param operationInvocation
	 */
	public boolean apply(OperationInvocation operationInvocation) {

		final OperationInvocation op = operationInvocation;
		final ApplicationResult applicationResult = new ApplicationResult();

		// Start new patch application
		reportManager.startPatchApplication();
		
		AbstractCommand command = new AbstractCommand() {

			@Override
			public void execute() {
				Map<ParameterBinding, Object> inArgs = getInArguments(op.getParameterBindings());
				try {
					Map<ParameterBinding, Object> outArgs = transformationEngine.execute(op, inArgs);
					setOutArguments(op.getParameterBindings(), outArgs);
					operationManager.getStatusWrapper(op).setPassed(inArgs, outArgs);					
					
					applicationResult.success = true;
					applicationResult.inArgs = inArgs;
					applicationResult.outArgs = outArgs;
					
					
					
				} catch (ParameterMissingException | OperationNotExecutableException e) {
					operationManager.getStatusWrapper(op).setFailed(inArgs, e);
					
					applicationResult.success = false;
					applicationResult.inArgs = inArgs;					
					applicationResult.error = e;
				}
			}

			@Override
			public void redo() {
				// not redoable here
			}

			@Override
			public boolean canExecute() {
				// always executable
				return true;
			}

		};

		if (patchedEditingDomain != null) {
			patchedEditingDomain.getCommandStack().execute(command);
		} else {
			command.execute();
		}
		
		if (applicationResult.success){
			reportManager.operationPassed(op, applicationResult.inArgs, applicationResult.outArgs);
		} else {
			reportManager.operationExecFailed(op, applicationResult.inArgs, applicationResult.error);
		}
		
		// Iterative validation (if needed)
		if (getValidationMode() == ValidationMode.ITERATIVE) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			this.validationChanged = reportManager.updateValidationEntries(validationErrors);
		}
		
		reportManager.finishPatchApplication();
		
		return applicationResult.success;
	}

	private class ApplicationResult {
		boolean success;
		Map<ParameterBinding, Object> inArgs;
		Map<ParameterBinding, Object> outArgs;
		Exception error;
	}
	
	/**
	 * Revert a specific operation invocation. Note that this method tries to
	 * perform the undo operation on the command stack of the target editing
	 * domain.
	 * 
	 * @param operationInvocation
	 */
	public boolean revert(OperationInvocation operationInvocation) {
		final OperationInvocation op = operationInvocation;
		final RevertResult revertResult = new RevertResult();

		// Start new patch application
		reportManager.startPatchApplication();
		
		AbstractCommand command = new AbstractCommand() {

			@Override
			public void execute() {
				try {
					transformationEngine.undo(op);
					operationManager.getStatusWrapper(op).setSkipped();
					
					revertResult.success = true;
				} catch (OperationNotUndoableException e) {
					op.setApply(true);
					
					revertResult.success = false;
					revertResult.error = e;
				}
			}

			@Override
			public void redo() {
				// not redoable here
			}

			@Override
			public boolean canExecute() {
				// always executable
				return true;
			}

		};

		if (patchedEditingDomain != null) {
			patchedEditingDomain.getCommandStack().execute(command);
		} else {
			command.execute();
		}
		
		if (revertResult.success){
			reportManager.operationReverted(op);
		} else {
			reportManager.operationRevertFailed(op, revertResult.error);
		}		
		
		// Iterative validation (if needed)
		if (getValidationMode() == ValidationMode.ITERATIVE) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			this.validationChanged = reportManager.updateValidationEntries(validationErrors);
		}
		
		reportManager.finishPatchApplication();
		
		return revertResult.success;
	}

	private class RevertResult {
		boolean success;		
		Exception error;
	}
	
	/**
	 * Finds parameter values and puts them into a map ParameterBinding ->
	 * Object.
	 * 
	 * @param parameterBindings
	 * @return
	 */
	private Map<ParameterBinding, Object> getInArguments(List<ParameterBinding> parameterBindings) {
		Map<ParameterBinding, Object> parameters = new HashMap<ParameterBinding, Object>();
		for (ParameterBinding binding : parameterBindings) {
			Parameter parameter = binding.getFormalParameter();

			if (parameter.getDirection() == ParameterDirection.IN) {
				if (binding instanceof MultiParameterBinding) {
					MultiParameterBinding multiBinding = (MultiParameterBinding) binding;
					LogUtil.log(LogEvent.NOTICE, "Binding listParameter " + parameter.getName() + ":");
					List<EObject> arguments = new ArrayList<EObject>();
					parameters.put(binding, arguments);

					// now fill the argument list
					for (int i = 0; i < multiBinding.getParameterBindings().size(); i++) {
						ParameterBinding nestedBinding = multiBinding.getParameterBindings().get(i);

						assert (nestedBinding instanceof ObjectParameterBinding) : "Currently we support only EObjects in a parameter list";

						ArgumentWrapper argument = argumentManager.getArgument((ObjectParameterBinding) nestedBinding);
						if (argument.isResolved()) {
							arguments.add(argument.getTargetObject());
							LogUtil.log(LogEvent.NOTICE, "\t argument(" + i + "): '" + argument.getTargetObject() + "'");
						} else {
							LogUtil.log(LogEvent.NOTICE, "\t argument(" + i + "): skipped");
						}
					}

				} else if (binding instanceof ObjectParameterBinding) {
					ArgumentWrapper argument = argumentManager.getArgument((ObjectParameterBinding) binding);
					if (argument.isResolved()) {
						parameters.put(binding, argument.getTargetObject());
						LogUtil.log(LogEvent.NOTICE, "Binding objectParameter " + parameter.getName() + " to "
								+ argument.getTargetObject());
					} else {
						LogUtil.log(LogEvent.NOTICE, "Skipped objectParameter " + parameter.getName());
					}

				} else if (binding instanceof ValueParameterBinding) {
					ValueParameterBinding valueBinding = (ValueParameterBinding) binding;
					LogUtil.log(LogEvent.NOTICE, "Setting valueParameter " + parameter.getName() + " to "
							+ valueBinding.getActual());
					parameters.put(binding, valueBinding.getActual());
				}
			}
		}
		return parameters;
	}

	/**
	 * Informs argumentManager about the resolution of output arguments after an
	 * operation has been executed.
	 * 
	 * @param parameterBindings
	 * @param resultMap
	 */
	private void setOutArguments(EList<ParameterBinding> parameterBindings, Map<ParameterBinding, Object> resultMap) {
		for (ParameterBinding binding : parameterBindings) {
			if (binding.getFormalParameter().getDirection() == ParameterDirection.OUT) {
				if (binding instanceof ObjectParameterBinding) {
					ObjectParameterBinding objectBinding = (ObjectParameterBinding) binding;
					EObject outTargetObject = (EObject) resultMap.get(objectBinding);
					argumentManager.addArgumentResolution(objectBinding, outTargetObject);
				}
			}
		}
	}

	/**
	 * Checks an operationInvocation for modified parameters.
	 * 
	 * @param operationInvocation
	 *            the operationInvocation to check
	 * @return if parameters have been modified
	 */
	private boolean hasModifiedParameters(OperationInvocation operationInvocation) {

		for (ParameterBinding parameterBinding : operationInvocation.getParameterBindings()) {
			Parameter formalParameter = parameterBinding.getFormalParameter();
			if (formalParameter.getDirection() == ParameterDirection.IN) {
				if (parameterBinding instanceof ObjectParameterBinding) {
					ObjectParameterBinding binding = (ObjectParameterBinding) parameterBinding;
					ArgumentWrapper argument = argumentManager.getArgument(binding);
					if (argument.isResolved() && argumentManager.isModified(argument.getTargetObject())) {
						return true;
					}
				}
			}
		}
		return false;

	}

	public ValidationMode getValidationMode() {
		return this.validationManager.getValidationMode();
	}

	public void setValidationMode(ValidationMode validationMode) {
		this.validationManager.setValidationMode(validationMode);
	}

	public AsymmetricDifference getAsymmetricDifference() {
		return difference;
	}

	public IArgumentManager getArgumentManager() {
		return argumentManager;
	}

	public OperationManager getOperationManager() {
		return operationManager;
	}

	public PatchReportManager getPatchReportManager() {
		return reportManager;
	}

	public Boolean getReliabilitiesComputed() {
		return reliabilitiesComputed;
	}

	public Collection<OperationInvocation> getOrderedOperationInvocations() {
		return orderedOperations;
	}

	public Resource getPatchedResource() {
		return patchedResource;
	}

	public void setPatchedEditingDomain(EditingDomain patchedEditingDomain) {
		this.patchedEditingDomain = patchedEditingDomain;
	}

}
