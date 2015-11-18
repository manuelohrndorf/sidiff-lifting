package org.sidiff.patching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import org.sidiff.matching.modifieddetector.IModifiedDetector;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.arguments.ObjectArgumentWrapper;
import org.sidiff.patching.arguments.ValueArgumentWrapper;
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
import org.sidiff.patching.validation.IValidationError;
import org.sidiff.patching.validation.ValidationManager;
import org.silift.patching.settings.ExecutionMode;
import org.silift.patching.settings.PatchMode;
import org.silift.patching.settings.PatchingSettings;
import org.silift.patching.settings.PatchingSettings.ValidationMode;

/**
 * 
 * @author Dennis Koch, kehrer, dreuling
 */
public class PatchEngine {

	private Resource patchedResource;
	private EditingDomain patchedEditingDomain;
	private ExecutionMode executionMode;
	private PatchMode patchMode;

	private IModifiedDetector modifiedDetector;
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
	public PatchEngine(AsymmetricDifference difference, Resource patchedResource, PatchingSettings settings) {

		this.patchedResource = patchedResource;
		this.modifiedDetector = settings.getModifiedDetector();
		this.argumentManager = settings.getArgumentManager();
		this.transformationEngine = settings.getTransformationEngine();		
		this.executionMode = settings.getExecutionMode();
		this.patchMode = settings.getPatchMode();		
		
		// Init managers
		this.validationManager = new ValidationManager(settings.getValidationMode(), patchedResource);
		this.argumentManager.init(difference, patchedResource, settings.getScope(), patchMode, modifiedDetector);
		this.operationManager = new OperationManager(difference, argumentManager);
		
		// Init transformation engine
		this.transformationEngine.init(patchedResource, executionMode, settings.getScope());

		// Init report manager
		this.reportManager = new PatchReportManager(settings.getValidationMode());

		// Init patch interrupt handler
		this.patchInterruptHandler = settings.getInterruptHandler();
		
		this.validationChanged = false;
		
		LogUtil.log(LogEvent.NOTICE, settings.toString());
	}

	/**
	 * Runs over all OperationInvocations and checks which of them are to be
	 * executed and which are to be reverted. <br/>
	 * The execution and undo themselves are delegated to the private methods
	 * {@link #apply(OperationInvocation)} and
	 * {@link #revert(OperationInvocation)}, respectively.
	 * 
	 * @param applyConflictingOperationInvocations defines whether conflicting {@link OperationInvocation} shall be applied
	 *  A Operation Invocation is NON-CONFLICTING, iff:
	 *  - its execution has not failed
	 * 	- it has no unresolved arguments
	 *  - it has no modified arguments
	 * 
	 * @return
	 */
	public void applyPatch(boolean applyConflictingOperationInvocations) {
		
		// Start new patch application
		reportManager.startPatchApplication();

		// Initial validation (if needed)
		if (getValidationMode() == ValidationMode.MODEL_VALIDATION|| getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			reportManager.updateValidationEntries(validationErrors);
		}

		// Try to execute operations which are to apply
		for (OperationInvocation operationInvocation : operationManager.getOrderedOperations()) {
			OperationInvocationWrapper operationWrapper = operationManager.getStatusWrapper(operationInvocation);
			
			Boolean conflictingOperation = operationWrapper.getStatus() == OperationInvocationStatus.FAILED ||
					operationWrapper.hasModifiedInArguments() || operationWrapper.hasUnresolvedInArguments();
			Boolean ignoredOperation = operationWrapper.getStatus() == OperationInvocationStatus.IGNORED;

			if (!(operationWrapper.getStatus() == OperationInvocationStatus.PASSED) && 
					(applyConflictingOperationInvocations || !conflictingOperation) && !ignoredOperation) {

				apply(operationInvocation, false);
			}
		}

		// Final validation (if needed)
		if (getValidationMode() == ValidationMode.MODEL_VALIDATION || getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			reportManager.updateValidationEntries(validationErrors);
		}
		
		reportManager.finishPatchApplication();
	}
		

	/**
	 * Apply operation invocation. Note that this method tries to perform the
	 * operation execution on the command stack of the target editing domain.
	 * 
	 * @param operationInvocation Operation Invocation to apply
	 * @param singleOperation Boolean to define whether this operation invocation will be executed alone
	 * 
	 */
	public boolean apply(OperationInvocation operationInvocation, Boolean singleOperation) {

		final OperationInvocation op = operationInvocation;
		final ApplicationResult applicationResult = new ApplicationResult();
		
		boolean canceled = false;

		// Start new patch application (only if this execution is "alone")
		if(singleOperation){
			reportManager.startPatchApplication();
			
			// Initial validation (if needed)
			if (getValidationMode() == ValidationMode.MODEL_VALIDATION || getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
				Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
				reportManager.updateValidationEntries(validationErrors);
			}
		}
		
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
		if (getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			this.validationChanged = reportManager.updateValidationEntries(validationErrors);
		}
		
		
		if (applicationResult.success && (getValidationMode() == ValidationMode.ITERATIVE_VALIDATION)) {				
			if (this.validationChanged) {
				this.validationChanged = false;
				Collection<IValidationError> newErrors = reportManager.getLastReport()
						.getLastValidationEntry().getNewValidationErrors();
				Collection<IValidationError> validationErrors = newErrors;

				if (!newErrors.isEmpty() && (executionMode == ExecutionMode.INTERACTIVE)) {
					PatchInterruptOption option = patchInterruptHandler.getInterruptOption(false,
							operationInvocation, validationErrors);

					if (option != PatchInterruptOption.IGNORE) {
						reportManager.cancelPatchApplication();
						canceled = true;
						revert(operationInvocation);
						validationErrors = validationManager.validateTargetModel();
						reportManager.updateValidationEntries(validationErrors);
					}					
				}
			}
		}
		
		if(singleOperation){
			
			// Final validation (if needed)
			if (getValidationMode() == ValidationMode.MODEL_VALIDATION || getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
				Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
				reportManager.updateValidationEntries(validationErrors);
			}
			if(!canceled){
				reportManager.finishPatchApplication();
			}
		}
		
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

		boolean canceled = false;
		// Start new patch application
		reportManager.startPatchApplication();

		// Initial validation (if needed)
		if (getValidationMode() == ValidationMode.MODEL_VALIDATION || getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			reportManager.updateValidationEntries(validationErrors);
		}
		
		AbstractCommand command = new AbstractCommand() {

			@Override
			public void execute() {
				try {
					transformationEngine.undo(op);
					operationManager.getStatusWrapper(op).setReverted();
					
					revertResult.success = true;
				} catch (OperationNotUndoableException e) {
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
		if (getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			this.validationChanged = reportManager.updateValidationEntries(validationErrors);
		}
		
		
		if (revertResult.success && (getValidationMode() == ValidationMode.ITERATIVE_VALIDATION)) {				
			if (this.validationChanged) {
				this.validationChanged = false;
				Collection<IValidationError> newErrors = reportManager.getLastReport()
						.getLastValidationEntry().getNewValidationErrors();
				Collection<IValidationError> validationErrors = newErrors;

				if (!newErrors.isEmpty() && (executionMode == ExecutionMode.INTERACTIVE)) {
					PatchInterruptOption option = patchInterruptHandler.getInterruptOption(true,
							operationInvocation, validationErrors);

					if (option != PatchInterruptOption.IGNORE) {
						reportManager.cancelPatchApplication();
						canceled = true;
						apply(operationInvocation, true);
						validationErrors = validationManager.validateTargetModel();
						reportManager.updateValidationEntries(validationErrors);
					}					
				}
			}
		}
		
		
		// Final validation (if needed)
		if (getValidationMode() == ValidationMode.MODEL_VALIDATION || getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			reportManager.updateValidationEntries(validationErrors);
		}	
		
		if(!canceled){
			reportManager.finishPatchApplication();
		}
		
		return revertResult.success;
	}
	
	public void ignore(OperationInvocation op){
		operationManager.getStatusWrapper(op).setIgnored();
	}
	
	public void unignore(OperationInvocation op){
		operationManager.getStatusWrapper(op).setUnIgnored();
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

						ObjectArgumentWrapper argument = (ObjectArgumentWrapper) argumentManager.getArgument(nestedBinding);
						if (argument.isResolved()) {
							arguments.add(argument.getTargetObject());
							LogUtil.log(LogEvent.NOTICE, "\t argument(" + i + "): '" + argument.getTargetObject() + "'");
						} else {
							LogUtil.log(LogEvent.NOTICE, "\t argument(" + i + "): skipped");
						}
					}

				} else if (binding instanceof ObjectParameterBinding) {
					ObjectArgumentWrapper argument = (ObjectArgumentWrapper) argumentManager.getArgument((ObjectParameterBinding) binding);
					if (argument.isResolved()) {
						parameters.put(binding, argument.getTargetObject());
						LogUtil.log(LogEvent.NOTICE, "Binding objectParameter " + parameter.getName() + " to "
								+ argument.getTargetObject());
					} else {
						LogUtil.log(LogEvent.NOTICE, "Skipped objectParameter " + parameter.getName());
					}

				} else if (binding instanceof ValueParameterBinding) {
					ValueArgumentWrapper argument = (ValueArgumentWrapper) argumentManager.getArgument(binding);
					LogUtil.log(LogEvent.NOTICE, "Setting valueParameter " + parameter.getName() + " to "
							+ argument.getValue());
					parameters.put(binding, argument.getValue());
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
				// We can be sure that binding is an object parameter binding
				ObjectParameterBinding objectBinding = (ObjectParameterBinding) binding;
				EObject outTargetObject = (EObject) resultMap.get(objectBinding);
				argumentManager.addArgumentResolution(objectBinding, outTargetObject);
				
			}
		}
	}

	public ValidationMode getValidationMode() {
		return this.validationManager.getValidationMode();
	}

	public void setValidationMode(ValidationMode validationMode) {
		this.validationManager.setValidationMode(validationMode);
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

	public Resource getPatchedResource() {
		return patchedResource;
	}

	public void setPatchedEditingDomain(EditingDomain patchedEditingDomain) {
		this.patchedEditingDomain = patchedEditingDomain;
	}

}
