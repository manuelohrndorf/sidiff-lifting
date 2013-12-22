package org.sidiff.patching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.Diagnostic;
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
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.OperationNotUndoableException;
import org.sidiff.patching.exceptions.ParameterMissingException;
import org.sidiff.patching.exceptions.PatchNotExecuteableException;
import org.sidiff.patching.report.PatchReport;
import org.sidiff.patching.report.PatchReport.Status;
import org.sidiff.patching.report.PatchReport.Type;
import org.sidiff.patching.report.ReportEntry;
import org.sidiff.patching.util.PatchUtil;

/**
 * 
 * @author Dennis Koch, kehrer, dreuling
 */
public class PatchEngine {

	private AsymmetricDifference difference;
	private Resource patchedResource;
	private Boolean reliabilitiesComputed;

	private List<OperationInvocation> orderedOperations;

	private ValidationManager validationManager;
	private OperationInvocationStatusManager statusManager;
	private IArgumentManager argumentManager;
	private ITransformationEngine transformationEngine;

	private EditingDomain patchedEditingDomain;

	public enum ValidationMode {
		ITERATIVE, FINAL, NO, MANUAL
	}

	public enum ExecutionMode {
		INTERACTIVE, BATCH
	}

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
		this.statusManager = new OperationInvocationStatusManager(orderedOperations);
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
	public void updatePatchReport() {

		// Try to execute operations which are to apply
		for (OperationInvocation operationInvocation : orderedOperations) {
			if (operationInvocation.isApply()
					&& !(statusManager.getStatusWrapper(operationInvocation).getStatus() == OperationInvocationStatus.PASSED)) {

				apply(operationInvocation);

				if (getValidationMode() == ValidationMode.ITERATIVE) {
					Collection<Diagnostic> newErrors = validationManager.performIncrementalValidation();
					if (!newErrors.isEmpty()) {
						// TODO: Fehler Dialog
						// Fehler anzeigen und Optionen zur Verfügung stellen:
						// Option 1.: ignorieren und continue
						// Option 2.: reverten und continue;
						// Option 3.: reverten und Patch-Vorgang abbrechen
					}
				}
			}
		}

		// Try to undo operations which are to revert
		ListIterator<OperationInvocation> li = orderedOperations.listIterator(orderedOperations.size());
		while (li.hasPrevious()) {
			OperationInvocation operationInvocation = (OperationInvocation) li.previous();
			OperationInvocationWrapper operationWrapper = statusManager.getStatusWrapper(operationInvocation);
			if (!operationInvocation.isApply() && operationWrapper.isUndoable(null)) {
				try {
					revert(operationInvocation);
				} catch (OperationNotUndoableException e) {
					// TODO: Throw Exception so that an Error Dialog can be
					// shown in the UI
					e.printStackTrace();
				}
			}
		}

		// Final validation (if desired)
		if (getValidationMode() == ValidationMode.FINAL) {
			this.validationManager.performCompleteValidation();
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

	public PatchReport createPatchReport() {
		PatchReport report = new PatchReport();

		for (OperationInvocation op : orderedOperations) {
			OperationInvocationWrapper opWrapper = getStatusManager().getStatusWrapper(op);
			assert (opWrapper.getStatus() != OperationInvocationStatus.INIT);

			ReportEntry entry = null;
			if (opWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				entry = new ReportEntry(Status.PASSED, Type.EXECUTION, op.getChangeSet().getName());
			} else if (opWrapper.getStatus() == OperationInvocationStatus.FAILED) {
				entry = new ReportEntry(Status.FAILED, Type.EXECUTION, opWrapper.getExecutionError());
			} else if (opWrapper.getStatus() == OperationInvocationStatus.SKIPPED) {
				entry = new ReportEntry(Status.SKIPPED, Type.EXECUTION, op.getChangeSet().getName());
			} else if (opWrapper.getStatus() == OperationInvocationStatus.SKIPPED_DEPENDENCY) {
				// TODO: Welche Operation von der wir abhängig sind wurde
				// geskipped
				entry = new ReportEntry(Status.SKIPPED, Type.EXECUTION, op.getChangeSet().getName());
			} else {
				assert (false) : "unknown state of an operation invocation";
			}

			report.addExecutionEntry(entry);
		}
		// TODO

		return report;
	}

	/**
	 * Apply operation invocation. Note that this method tries to perform the
	 * operation execution on the command stack of the target editing domain.
	 * 
	 * @param operationInvocation
	 */
	private void apply(OperationInvocation operationInvocation) {

		final OperationInvocation op = operationInvocation;

		AbstractCommand command = new AbstractCommand() {

			@Override
			public void execute() {
				Map<ParameterBinding, Object> parameters = getInArguments(op.getParameterBindings());
				try {
					Map<ParameterBinding, Object> outArgs = transformationEngine.execute(op, parameters);
					setOutArguments(op.getParameterBindings(), outArgs);
					statusManager.getStatusWrapper(op).setPassed(null);
				} catch (ParameterMissingException | OperationNotExecutableException e) {
					statusManager.getStatusWrapper(op).setFailed(null, e);
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
	}

	/**
	 * Revert a specific operation invocation. Note that this method tries to
	 * perform the undo operation on the command stack of the target editing
	 * domain.
	 * 
	 * @param operationInvocation
	 * @throws OperationNotUndoableException
	 */
	private void revert(OperationInvocation operationInvocation) throws OperationNotUndoableException {
		final OperationInvocation op = operationInvocation;
		final ArrayList<Exception> exceptions = new ArrayList<Exception>();

		AbstractCommand command = new AbstractCommand() {

			@Override
			public void execute() {
				try {
					transformationEngine.undo(op);
					statusManager.getStatusWrapper(op).setSkipped();
				} catch (OperationNotUndoableException e) {
					exceptions.add(e);
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

		for (Exception e : exceptions) {
			if (e instanceof OperationNotUndoableException) {
				throw (OperationNotUndoableException) e;
			}
		}
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

	public OperationInvocationStatusManager getStatusManager() {
		return statusManager;
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
