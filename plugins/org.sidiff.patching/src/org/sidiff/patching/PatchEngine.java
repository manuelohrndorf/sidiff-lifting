package org.sidiff.patching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import org.sidiff.editrule.rulebase.Parameter;
import org.sidiff.editrule.rulebase.ParameterDirection;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.arguments.MultiArgumentWrapper;
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
import org.sidiff.patching.validation.ValidationMode;

/**
 * 
 * @author Dennis Koch, kehrer, dreuling
 */
public class PatchEngine {

	private Resource patchedResource;
	private EditingDomain patchedEditingDomain;
	private ExecutionMode executionMode;

	private ValidationManager validationManager;
	private OperationManager operationManager;
	private IArgumentManager argumentManager;
	private ITransformationEngine transformationEngine;
	private PatchReportManager reportManager;
	private IPatchInterruptHandler patchInterruptHandler;
	private boolean validationChanged;

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
	public PatchEngine(AsymmetricDifference difference, Resource patchedResource, IPatchEngineSettings settings) {
		Objects.requireNonNull(difference, "difference is null");
		Objects.requireNonNull(patchedResource, "patchedResource is null");
		Objects.requireNonNull(settings, "settings is null");

		// Get settings:
		this.patchedResource = patchedResource;
		this.argumentManager = settings.getArgumentManager();
		this.transformationEngine = settings.getTransformationEngine();		
		this.executionMode = settings.getExecutionMode();

		// Init managers
		this.validationManager = new ValidationManager(settings.getValidationMode(), patchedResource);
		this.argumentManager.init(difference, patchedResource, settings);
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
		if (getValidationMode() == ValidationMode.MODEL_VALIDATION
				|| getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			reportManager.updateValidationEntries(validationManager.validateTargetModel());
		}

		// Try to execute operations which are to apply
		for (OperationInvocation operationInvocation : operationManager.getOrderedOperations()) {
			OperationInvocationWrapper operationWrapper = operationManager.getStatusWrapper(operationInvocation);
			
			boolean conflictingOperation = operationWrapper.getStatus() == OperationInvocationStatus.FAILED
					|| operationWrapper.hasModifiedInArguments()
					|| operationWrapper.hasUnresolvedInArguments();

			if (operationWrapper.getStatus() != OperationInvocationStatus.PASSED
					&&  (applyConflictingOperationInvocations || !conflictingOperation)
					&& operationWrapper.getStatus() != OperationInvocationStatus.IGNORED) {
	
				apply(operationInvocation, false);
			}
		}

		// Final validation (if needed)
		if (getValidationMode() == ValidationMode.MODEL_VALIDATION
				|| getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			reportManager.updateValidationEntries(validationManager.validateTargetModel());
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
	public boolean apply(OperationInvocation operationInvocation, boolean singleOperation) {
		class ApplicationResult {
			boolean success;
			Map<ParameterBinding, Object> inArgs;
			Map<ParameterBinding, Object> outArgs;
			Exception error;
		}
		final ApplicationResult applicationResult = new ApplicationResult();
		
		boolean canceled = false;

		// Start new patch application (only if this execution is "alone")
		if(singleOperation){
			reportManager.startPatchApplication();
			
			// Initial validation (if needed)
			if (getValidationMode() == ValidationMode.MODEL_VALIDATION || getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
				reportManager.updateValidationEntries(validationManager.validateTargetModel());
			}
		}
		
		AbstractCommand command = new AbstractCommand() {
			@Override
			public void execute() {
				Map<ParameterBinding, Object> inArgs = getInArguments(operationInvocation.getParameterBindings());
				try {
					Map<ParameterBinding, Object> outArgs = transformationEngine.execute(operationInvocation, inArgs);
					setOutArguments(operationInvocation.getParameterBindings(), outArgs);
					operationManager.getStatusWrapper(operationInvocation).setPassed(inArgs, outArgs);					
					
					applicationResult.success = true;
					applicationResult.inArgs = inArgs;
					applicationResult.outArgs = outArgs;
				} catch (ParameterMissingException  e) {
					operationManager.getStatusWrapper(operationInvocation).setFailed(inArgs, e);
					
					applicationResult.success = false;
					applicationResult.inArgs = inArgs;					
					applicationResult.error = e;
				} catch (OperationNotExecutableException e){
					operationManager.getStatusWrapper(operationInvocation).setFailed(inArgs, e);
					
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
			reportManager.operationPassed(operationInvocation, applicationResult.inArgs, applicationResult.outArgs);
		} else {
			reportManager.operationExecFailed(operationInvocation, applicationResult.inArgs, applicationResult.error);
		}
		
		// Iterative validation (if needed)
		if (getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
			this.validationChanged = reportManager.updateValidationEntries(validationErrors);

			if (applicationResult.success && this.validationChanged) {
				this.validationChanged = false;
				Collection<IValidationError> newErrors = reportManager.getLastReport()
						.getLastValidationEntry().getNewValidationErrors();
				
				if (!newErrors.isEmpty() && (executionMode == ExecutionMode.INTERACTIVE)) {
					PatchInterruptOption option = patchInterruptHandler.getInterruptOption(false,
							operationInvocation, newErrors);
					
					if (option != PatchInterruptOption.IGNORE) {
						reportManager.cancelPatchApplication();
						canceled = true;
						revert(operationInvocation);
						reportManager.updateValidationEntries(validationManager.validateTargetModel());
					}					
				}
			}
		}
		
		if(singleOperation) {
			// Final validation (if needed)
			if (getValidationMode() == ValidationMode.MODEL_VALIDATION
					|| getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
				reportManager.updateValidationEntries(validationManager.validateTargetModel());
			}
			if(!canceled){
				reportManager.finishPatchApplication();
			}
		}
		
		return applicationResult.success;
	}

	/**
	 * Revert a specific operation invocation. Note that this method tries to
	 * perform the undo operation on the command stack of the target editing
	 * domain.
	 * 
	 * @param operationInvocation
	 */
	public boolean revert(OperationInvocation operationInvocation) {
		class RevertResult {
			boolean success;
			Exception error;
		}
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
					transformationEngine.undo(operationInvocation);
					operationManager.getStatusWrapper(operationInvocation).setReverted();
					
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
			reportManager.operationReverted(operationInvocation);
		} else {
			reportManager.operationRevertFailed(operationInvocation, revertResult.error);
		}
		
		// Iterative validation (if needed)
		if (getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			this.validationChanged = reportManager.updateValidationEntries(validationManager.validateTargetModel());

			if (revertResult.success && this.validationChanged) {				
				this.validationChanged = false;
				Collection<IValidationError> newErrors = reportManager.getLastReport()
						.getLastValidationEntry().getNewValidationErrors();
				
				if (!newErrors.isEmpty() && executionMode == ExecutionMode.INTERACTIVE) {
					PatchInterruptOption option = patchInterruptHandler.getInterruptOption(true,
							operationInvocation, newErrors);
					
					if (option != PatchInterruptOption.IGNORE) {
						reportManager.cancelPatchApplication();
						canceled = true;
						apply(operationInvocation, true);
						reportManager.updateValidationEntries(validationManager.validateTargetModel());
					}					
				}
			}
		}
		
		// Final validation (if needed)
		if (getValidationMode() == ValidationMode.MODEL_VALIDATION
				|| getValidationMode() == ValidationMode.ITERATIVE_VALIDATION) {
			reportManager.updateValidationEntries(validationManager.validateTargetModel());
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
	
	/**
	 * Finds parameter values and puts them into a map ParameterBinding ->
	 * Object.
	 * 
	 * @param parameterBindings
	 * @return
	 */
	private Map<ParameterBinding, Object> getInArguments(List<ParameterBinding> parameterBindings) {
		Map<ParameterBinding, Object> parameters = new HashMap<>();
		for (ParameterBinding binding : parameterBindings) {
			Parameter parameter = binding.getFormalParameter();
			if (parameter.getDirection() != ParameterDirection.IN) {
				continue;
			}

			if (binding instanceof MultiParameterBinding) {
				MultiParameterBinding multiBinding = (MultiParameterBinding) binding;
				LogUtil.log(LogEvent.NOTICE, "Binding multiParameter " + parameter.getName() + ":");
				MultiArgumentWrapper multiArgument = (MultiArgumentWrapper)argumentManager.getArgument(multiBinding);
				List<EObject> arguments = new ArrayList<>();
				for (ObjectArgumentWrapper argument : multiArgument.getNestedWrappers()) {
					if (argument.isResolved()) {
						arguments.add(argument.getTargetObject());
						LogUtil.log(LogEvent.NOTICE, "\t argument: '" + argument.getTargetObject() + "'");
					} else {
						LogUtil.log(LogEvent.NOTICE, "\t unresolved argument skipped");
					}
				}
				parameters.put(binding, arguments);
			} else if (binding instanceof ObjectParameterBinding) {
				ObjectArgumentWrapper argument = (ObjectArgumentWrapper) argumentManager.getArgument(binding);
				if (argument.isResolved()) {
					parameters.put(binding, argument.getTargetObject());
					LogUtil.log(LogEvent.NOTICE, "Binding objectParameter " + parameter.getName() + " to "
							+ argument.getTargetObject());
				} else {
					LogUtil.log(LogEvent.NOTICE, "Skipped unresolved objectParameter " + parameter.getName());
				}
			} else if (binding instanceof ValueParameterBinding) {
				ValueArgumentWrapper argument = (ValueArgumentWrapper) argumentManager.getArgument(binding);
				LogUtil.log(LogEvent.NOTICE, "Setting valueParameter " + parameter.getName() + " to "
						+ argument.getValue());
				parameters.put(binding, argument.getValue());
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
				// FIXME cpietsch 01.08.2016: There might be an error
				// during patch generation because the multi parameter binding
				// for output parameters is empty
				if(binding instanceof ObjectParameterBinding){
					ObjectParameterBinding objectBinding = (ObjectParameterBinding) binding;
					EObject outTargetObject = (EObject) resultMap.get(objectBinding);
					argumentManager.addArgumentResolution(objectBinding, outTargetObject);
				}else if(binding instanceof MultiParameterBinding){
					MultiParameterBinding multiBinding = (MultiParameterBinding) binding;
					for(ParameterBinding parameterBinding : multiBinding.getParameterBindings()){
						assert parameterBinding instanceof ObjectParameterBinding: "ObjectParameterBinding expected!";
						ObjectParameterBinding objectBinding = (ObjectParameterBinding) parameterBinding;
						EObject outTargetObject = (EObject) resultMap.get(objectBinding);
						argumentManager.addArgumentResolution(objectBinding, outTargetObject);
					}
				}
				
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
