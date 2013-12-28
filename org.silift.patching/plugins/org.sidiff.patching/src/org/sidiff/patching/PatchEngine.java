package org.sidiff.patching;

import java.lang.reflect.Array;
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
import org.sidiff.patching.exceptions.PatchNotExecuteableException;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.operation.OperationManager;
import org.sidiff.patching.report.PatchReport;
import org.sidiff.patching.report.PatchReportManager;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.util.PatchUtil;
import org.sidiff.patching.validation.IValidationError;
import org.sidiff.patching.validation.ValidationMode;
import org.sidiff.patching.validation.ValidationManager;

/**
 * 
 * @author Dennis Koch, kehrer, dreuling
 */
public class PatchEngine {

	public enum ExecutionMode {
		INTERACTIVE, BATCH
	}

	private AsymmetricDifference difference;
	private List<OperationInvocation> orderedOperations;
	private Resource patchedResource;
	private EditingDomain patchedEditingDomain;
	private Boolean reliabilitiesComputed;

	private ValidationManager validationManager;
	private OperationManager operationManager;
	private IArgumentManager argumentManager;
	private ITransformationEngine transformationEngine;
	private PatchReportManager reportManager;

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
	 */
	public PatchEngine(AsymmetricDifference difference, Resource patchedResource, IArgumentManager argumentManager,
			ITransformationEngine transformationEngine, ExecutionMode executionMode, ValidationMode validationMode,
			Boolean reliabilitiesComputed) {

		this.difference = difference;
		this.patchedResource = patchedResource;
		this.argumentManager = argumentManager;
		this.transformationEngine = transformationEngine;
		this.reliabilitiesComputed = reliabilitiesComputed;

		// Ordered set of operations to be executed
		this.orderedOperations = PatchUtil.getOrderdOperationInvocations(difference.getOperationInvocations());

		// Init managers
		this.validationManager = new ValidationManager(validationMode, patchedResource);
		this.operationManager = new OperationManager(orderedOperations);
		this.argumentManager.init(difference, patchedResource);

		// Initialize all operationInvocations owning
		// modified parameters as "not applicable"
		for (OperationInvocation operationInvocation : orderedOperations) {
			if (hasModifiedParameters(operationInvocation)) {
				operationInvocation.setApply(false);
			}
		}

		// Init transformation engine
		this.transformationEngine.init(patchedResource, executionMode);

		// Init report manager
		this.reportManager = new PatchReportManager();
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

				if (!operationManager.allPredecessorsExecuted(operationInvocation)) {
					operationWrapper.setSkipped();
					reportManager.operationSkipped(operationInvocation);
				} else {

					boolean success = apply(operationInvocation);

					if (success && (getValidationMode() == ValidationMode.ITERATIVE)) {
						Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
						boolean validationChanged = reportManager.updateValidationEntries(validationErrors);
						if (validationChanged) {
							Collection<IValidationError> newErrors = reportManager.getLastReport()
									.getLastValidationEntry().getNewValidationErrors();

							if (!newErrors.isEmpty()) {
								// TODO: Fehler Dialog
								// Fehler anzeigen und Optionen zur Verfügung
								// stellen:
								// Option 1.: ignore and continue
								// Option 2.: revert and continue;
								// Option 3.: revert and exit current patch
								// application

								// Yet, we assume the selection of Option 1
								// -> do nothing
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
						Collection<IValidationError> validationErrors = validationManager.validateTargetModel();
						boolean validationChanged = reportManager.updateValidationEntries(validationErrors);
						if (validationChanged) {
							Collection<IValidationError> newErrors = reportManager.getLastReport()
									.getLastValidationEntry().getNewValidationErrors();

							if (!newErrors.isEmpty()) {
								// TODO: Fehler Dialog
								// Fehler anzeigen und Optionen zur Verfügung
								// stellen:
								// Option 1.: ignore and continue
								// Option 2.: revert and continue;
								// Option 3.: revert and exit current patch
								// application

								// Yet, we assume the selection of Option 1
								// -> do nothing
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

	// #################################### org.sidiff.patching.test
	// ####################################

	public void applyPatchOperationValidation() throws PatchNotExecuteableException {
		// int initialErrors = getValidationErrorAmount(this.patchedResource);
		// int previousErrors = initialErrors;
		// for (OperationInvocation operationInvocation : orderedOperations) {
		// ArrayList<ReportEntry> validationReports = new
		// ArrayList<ReportEntry>();
		// if (operationInvocation.isApply() &&
		// !(appliedOperations.contains(operationInvocation))) {
		// try {
		// apply(operationInvocation);
		// appliedOperations.add(operationInvocation);
		// int currentErrors = getValidationErrorAmount(this.patchedResource);
		// if (previousErrors != currentErrors) {
		// String messageStr =
		// "Validation Errors: %1$s -> %2$s Operation: %3$s (Basemodel Errors: %4$s)";
		// String message = String.format(messageStr, previousErrors,
		// currentErrors, operationInvocation
		// .getChangeSet().getName(), initialErrors);
		// validationReports.add(new ReportEntry(Status.WARNING,
		// Type.VALIDATION, message));
		// patchReport.getIterativeValidationEntries().put(operationInvocation,
		// validationReports);
		// }
		// previousErrors = currentErrors;
		// } catch (OperationNotExecutableException e) {
		// throw new PatchNotExecuteableException(e.getMessage() + " failed!");
		// } catch (ParameterMissingException e) {
		// throw new PatchNotExecuteableException(e.getMessage());
		// }
		// } else {
		// LogUtil.log(LogEvent.NOTICE, "Skipping operation " +
		// operationInvocation.getChangeSet().getName());
		// }
		// }
	}

	// private int getValidationErrorAmount(Resource resource) {
	// Collection<ReportEntry> validationReport = validator.test(resource);
	// int amount = 0;
	// for (ReportEntry reportEntry : validationReport) {
	// Status status = reportEntry.getStatus();
	// if (status == Status.WARNING || status == Status.FAILED) {
	// amount++;
	// }
	// }
	// return amount;
	// }

	// ##################################################################################################

	public PatchReport getPatchReport() {
		return reportManager.getLastReport();
	}

	public PatchReportManager getPatchReportManager() {
		return reportManager;
	}

	/**
	 * Apply operation invocation. Note that this method tries to perform the
	 * operation execution on the command stack of the target editing domain.
	 * 
	 * @param operationInvocation
	 */
	private boolean apply(OperationInvocation operationInvocation) {

		final OperationInvocation op = operationInvocation;
		final ArrayList<Boolean> success = new ArrayList<Boolean>();

		AbstractCommand command = new AbstractCommand() {

			@Override
			public void execute() {
				Map<ParameterBinding, Object> inArgs = getInArguments(op.getParameterBindings());
				try {
					Map<ParameterBinding, Object> outArgs = transformationEngine.execute(op, inArgs);
					setOutArguments(op.getParameterBindings(), outArgs);
					operationManager.getStatusWrapper(op).setPassed(inArgs);
					reportManager.operationPassed(op, inArgs);
					success.add(Boolean.TRUE);
				} catch (ParameterMissingException | OperationNotExecutableException e) {
					operationManager.getStatusWrapper(op).setFailed(inArgs, e);
					reportManager.operationFailed(op, inArgs, e);
					success.add(Boolean.FALSE);
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

		return success.get(0).booleanValue();
	}

	/**
	 * Revert a specific operation invocation. Note that this method tries to
	 * perform the undo operation on the command stack of the target editing
	 * domain.
	 * 
	 * @param operationInvocation
	 */
	private boolean revert(OperationInvocation operationInvocation) {
		final OperationInvocation op = operationInvocation;
		final ArrayList<Boolean> success = new ArrayList<Boolean>();

		AbstractCommand command = new AbstractCommand() {

			@Override
			public void execute() {
				try {
					transformationEngine.undo(op);
					operationManager.getStatusWrapper(op).setSkipped();
					reportManager.operationReverted(op);
					success.add(Boolean.TRUE);
				} catch (OperationNotUndoableException e) {
					op.setApply(true);
					reportManager.operationRevertFailed(op, e);
					success.add(Boolean.FALSE);
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

		return success.get(0).booleanValue();
	}

	/**
	 * Finds parameter values and put them into a map ParameterBinding ->
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
