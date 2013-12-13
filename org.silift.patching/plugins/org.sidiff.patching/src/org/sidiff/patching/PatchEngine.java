package org.sidiff.patching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.rulebase.Parameter;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.patching.exceptions.HenshinExecutionFailedException;
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.OperationNotUndoableException;
import org.sidiff.patching.exceptions.ParameterMissingException;
import org.sidiff.patching.exceptions.ParameterModifiedException;
import org.sidiff.patching.exceptions.PatchNotExecuteableException;
import org.sidiff.patching.report.PatchReport;
import org.sidiff.patching.report.PatchReport.Status;
import org.sidiff.patching.report.PatchReport.Type;
import org.sidiff.patching.report.ReportEntry;
import org.sidiff.patching.test.EMFValidationTestUnit;
import org.sidiff.patching.util.PatchUtil;

/**
 * 
 * @author Dennis Koch, kehrer, dreuling
 */
public class PatchEngine {

	private AsymmetricDifference difference;
	private IArgumentManager argumentManager;
	private List<OperationInvocation> orderedOperations;
	private Set<OperationInvocation> appliedOperations;
	private ITransformationEngine transformationEngine;
	private Resource patchedResource;
	private IValidationUnit testUnit;
	private ExecutionMode executionMode;
	private EditingDomain patchedEditingDomain;
	private PatchReport patchReport;

	private ValidationMode validationMode;
	private Collection<Diagnostic> initialErrors;
	private Collection<Diagnostic> previousErrors;
	private Collection<Diagnostic> currentErrors;

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
	 * @param correspondence
	 * @param transformationEngine
	 * @param executionMode
	 */
	public PatchEngine(AsymmetricDifference difference, Resource patchedResource, IArgumentManager correspondence,
			ITransformationEngine transformationEngine, ExecutionMode executionMode) {
		this.difference = difference;
		this.patchedResource = patchedResource;
		this.argumentManager = correspondence;
		this.transformationEngine = transformationEngine;

		this.orderedOperations = PatchUtil.getOrderdOperationInvocations(difference.getOperationInvocations());
		this.appliedOperations = new HashSet<OperationInvocation>();

		this.validationMode = ValidationMode.ITERATIVE;
		this.executionMode = executionMode;

		// initialize patch report
		this.patchReport = new PatchReport();

		this.argumentManager.init(difference, patchedResource);

		// Initialize all operationInvocations owning
		// modified parameters as "not applicable"
		for (OperationInvocation operationInvocation : orderedOperations) {
			if (hasModifiedParameters(operationInvocation)) {
				operationInvocation.setApply(false);
			}
		}

		this.transformationEngine.init(patchedResource, executionMode);
		// initialize test unit
		this.testUnit = new EMFValidationTestUnit();
	}

	// #################################### org.sidiff.patching.test
	// ####################################

	public void applyPatchOperationValidation() throws PatchNotExecuteableException {
		int initialErrors = getValidationErrorAmount(this.patchedResource);
		int previousErrors = initialErrors;
		for (OperationInvocation operationInvocation : orderedOperations) {
			ArrayList<ReportEntry> validationReports = new ArrayList<ReportEntry>();
			if (operationInvocation.isApply() && !(appliedOperations.contains(operationInvocation))) {
				try {
					apply(operationInvocation);
					appliedOperations.add(operationInvocation);
					int currentErrors = getValidationErrorAmount(this.patchedResource);
					if (previousErrors != currentErrors) {
						String messageStr = "Validation Errors: %1$s -> %2$s Operation: %3$s (Basemodel Errors: %4$s)";
						String message = String.format(messageStr, previousErrors, currentErrors, operationInvocation
								.getChangeSet().getName(), initialErrors);
						validationReports.add(new ReportEntry(Status.WARNING, Type.VALIDATION, message));
						patchReport.getIterativeValidationEntries().put(operationInvocation, validationReports);
					}
					previousErrors = currentErrors;
				} catch (OperationNotExecutableException e) {
					throw new PatchNotExecuteableException(e.getMessage() + " failed!");
				} catch (ParameterMissingException e) {
					throw new PatchNotExecuteableException(e.getMessage());
				}
			} else {
				LogUtil.log(LogEvent.NOTICE, "Skipping operation " + operationInvocation.getChangeSet().getName());
			}
		}
	}

	private int getValidationErrorAmount(Resource resource) {
		Collection<ReportEntry> validationReport = testUnit.test(resource);
		int amount = 0;
		for (ReportEntry reportEntry : validationReport) {
			Status status = reportEntry.getStatus();
			if (status == Status.WARNING || status == Status.FAILED) {
				amount++;
			}
		}
		return amount;
	}

	// ##################################################################################################

	/**
	 * apply operation invocation
	 * 
	 * @param operationInvocation
	 * @throws ParameterMissingException
	 * @throws OperationNotExecutableException
	 */
	private synchronized void apply(OperationInvocation operationInvocation) throws ParameterMissingException,
			OperationNotExecutableException {

		final OperationInvocation op = operationInvocation;
		final ArrayList<Exception> exceptions = new ArrayList<Exception>();

		AbstractCommand command = new AbstractCommand() {

			@Override
			public void execute() {
				Map<String, Object> parameters = getParameters(op.getParameterBindings());
				try {
					Map<String, Object> resultMap = transformationEngine.execute(op, parameters);
					// Exceptions do not set return values.
					// If they set null depending operations wont be executable
					// but UI will be ugly because of lacking a return name
					// Skip depending operations will be observed in
					// checkExecutable()
					setResult(op.getParameterBindings(), resultMap);
				} catch (ParameterMissingException | OperationNotExecutableException e) {
					exceptions.add(e);
				}
			}

			@Override
			public void redo() {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean canExecute() {
				// TODO Auto-generated method stub
				return true;
			}

		};

		if (patchedEditingDomain != null) {
			patchedEditingDomain.getCommandStack().execute(command);
		} else {
			command.execute();
		}

		for (Exception e : exceptions) {
			if (e instanceof ParameterMissingException) {
				throw (ParameterMissingException) e;
			}
			if (e instanceof OperationNotExecutableException) {
				throw (OperationNotExecutableException) e;
			}
		}
	}

	private synchronized void revert(OperationInvocation operationInvocation) throws OperationNotUndoableException {

		transformationEngine.undo(operationInvocation);
	}

	/**
	 * Finds parameter values and put them into a map with its formal name.
	 * 
	 * @param parameterBindings
	 * @return
	 */
	private Map<String, Object> getParameters(List<ParameterBinding> parameterBindings) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		for (ParameterBinding binding : parameterBindings) {
			Parameter parameter = binding.getFormalParameter();
			String parameterName = parameter.getName();

			if (parameter.getDirection() == ParameterDirection.IN) {
				if (binding instanceof MultiParameterBinding) {
					MultiParameterBinding multiBinding = (MultiParameterBinding) binding;
					LogUtil.log(LogEvent.NOTICE, "Binding listParameter " + parameterName + ":");
					List<EObject> arguments = new ArrayList<EObject>();
					parameters.put(parameterName, arguments);

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
						parameters.put(parameterName, argument.getTargetObject());
						LogUtil.log(LogEvent.NOTICE,
								"Binding objectParameter " + parameterName + " to " + argument.getTargetObject());
					} else {
						LogUtil.log(LogEvent.NOTICE, "Skipped objectParameter " + parameterName);
					}

				} else if (binding instanceof ValueParameterBinding) {
					ValueParameterBinding valueBinding = (ValueParameterBinding) binding;
					LogUtil.log(LogEvent.NOTICE,
							"Setting valueParameter " + parameterName + " to " + valueBinding.getActual());
					parameters.put(parameterName, valueBinding.getActual());
				}
			}
		}
		return parameters;
	}

	/**
	 * Sets the result object of an operation execution to the parameter mapping
	 * 
	 * @param parameterBindings
	 * @param resultMap
	 */
	private void setResult(EList<ParameterBinding> parameterBindings, Map<String, Object> resultMap) {
		for (ParameterBinding binding : parameterBindings) {
			if (binding.getFormalParameter().getDirection() == ParameterDirection.OUT) {
				String formalName = binding.getFormalName();

				if (binding instanceof ObjectParameterBinding) {
					ObjectParameterBinding objectBinding = (ObjectParameterBinding) binding;
					EObject outTargetObject = (EObject) resultMap.get(formalName);
					argumentManager.addArgumentResolution(objectBinding, outTargetObject);
				}
				if (binding instanceof MultiParameterBinding) {
					MultiParameterBinding multiBinding = (MultiParameterBinding) binding;
					assert (false) : "not yet implemented"; // FIXME
				}
			}
		}
	}

	/**
	 * Runs over all OperationInvocations and creates a report about failed and
	 * passed checks.
	 * 
	 * @return
	 */
	public PatchReport updatePatchReport() {
		checkParameter();

		checkModified();

		checkExecutable();

		if (validationMode == ValidationMode.FINAL || validationMode == ValidationMode.MANUAL) {
			patchReport.getValidationEntries().clear();
			Collection<ReportEntry> entries = testUnit.test(this.patchedResource);
			patchReport.getValidationEntries().addAll(entries);

		}

		return this.patchReport;
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

	/**
	 * Checks modification of parameters in a patch report
	 * 
	 * @param report
	 */
	private void checkModified() {

		for (OperationInvocation operationInvocation : orderedOperations) {
			if (operationInvocation.isApply()) {
				Collection<ReportEntry> entries = new ArrayList<ReportEntry>();
				for (ParameterBinding parameterBinding : operationInvocation.getParameterBindings()) {
					Parameter formalParameter = parameterBinding.getFormalParameter();
					if (formalParameter.getDirection() == ParameterDirection.IN) {
						if (parameterBinding instanceof ObjectParameterBinding) {
							ObjectParameterBinding binding = (ObjectParameterBinding) parameterBinding;
							ArgumentWrapper argument = argumentManager.getArgument(binding);
							if (argument.isResolved() && argumentManager.isModified(argument.getTargetObject())) {
								entries.add(new ReportEntry(Status.WARNING, Type.PARAMETER,
										new ParameterModifiedException(operationInvocation.getChangeSet().getName(),
												formalParameter.getName())));
							}
						}
					}
				}
				if (!entries.isEmpty())
					patchReport.getParameterEntries().get(operationInvocation).addAll(entries);
			}
		}
	}

	/**
	 * Checks availability of needed parameters
	 * 
	 * 
	 * @param operationInvocation
	 * 
	 * @throws ParameterMissingException
	 */
	private void checkParameter() {
		for (OperationInvocation operationInvocation : orderedOperations) {
			Collection<ReportEntry> entries = new ArrayList<ReportEntry>();
			if (operationInvocation.isApply()) {
				parameter: for (ParameterBinding parameterBinding : operationInvocation.getParameterBindings()) {
					Parameter formalParameter = parameterBinding.getFormalParameter();
					if (formalParameter.getDirection() == ParameterDirection.IN) {
						if (parameterBinding instanceof ObjectParameterBinding) {
							ObjectParameterBinding objectParameterBinding = (ObjectParameterBinding) parameterBinding;

							// Sorting out IN parameters depending on previous
							// operations
							for (ParameterMapping mapping : difference.getParameterMappings()) {
								if (mapping.getTarget().equals(objectParameterBinding)) {
									continue parameter;
								}
							}

							ObjectParameterBinding binding = (ObjectParameterBinding) parameterBinding;
							ArgumentWrapper argument = argumentManager.getArgument(binding);
							if (!argument.isResolved()) {
								entries.add(new ReportEntry(Status.FAILED, Type.PARAMETER,
										new ParameterMissingException(operationInvocation.getChangeSet().getName(),
												formalParameter.getName())));
							} else {
								entries.add(new ReportEntry(Status.PASSED, Type.PARAMETER, "ObjectParameter \""
										+ formalParameter.getName() + "\" is set!"));
							}
						}
					}
				}
			} else {
				entries.add(new ReportEntry(Status.SKIPPED, Type.PARAMETER, operationInvocation.getChangeSet()
						.getName()));
			}
			this.patchReport.getParameterEntries().put(operationInvocation, entries);
		}

	}

	/**
	 * Checks executability of henshin
	 * 
	 * 
	 * @param operationInvocation
	 * @throws HenshinExecutionFailedException
	 */
	private void checkExecutable() {
		AbstractCommand command = new AbstractCommand() {
			@Override
			public void execute() {

				// Executed operations must be stored to skip operations
				// depending on failed executions
				for (OperationInvocation operationInvocation : orderedOperations) {
					if (operationInvocation.isApply() && isOutgoingExecuted(operationInvocation, appliedOperations)) {
						if (!(appliedOperations.contains(operationInvocation))) {
							try {
								if (validationMode == ValidationMode.ITERATIVE) {
									initialErrors = testUnit.getErrors(testUnit.validate(patchedResource));
									previousErrors = initialErrors;
								}
								apply(operationInvocation);
								appliedOperations.add(operationInvocation);
								if (validationMode == ValidationMode.ITERATIVE) {
									currentErrors = testUnit.getErrors(testUnit.validate(patchedResource));

									if (currentErrors.size() > previousErrors.size()) {
										ArrayList<ReportEntry> entries = new ArrayList<ReportEntry>();
										for (Diagnostic d : currentErrors) {
											if (!previousErrors.contains(d)) {
												entries.add(new ReportEntry(Status.FAILED, Type.VALIDATION, d));
											}
										}
										if (!entries.isEmpty())
											patchReport.getIterativeValidationEntries().put(operationInvocation,
													entries);
									}
								}
								patchReport.getExecutionEntries().put(
										operationInvocation,
										new ReportEntry(Status.PASSED, Type.EXECUTION, operationInvocation
												.getChangeSet().getName()));

							} catch (OperationNotExecutableException | ParameterMissingException e) {
								patchReport.getExecutionEntries().put(operationInvocation,
										new ReportEntry(Status.FAILED, Type.EXECUTION, e));
							}
						} else {
							patchReport.getExecutionEntries().put(
									operationInvocation,
									new ReportEntry(Status.PASSED, Type.EXECUTION, operationInvocation.getChangeSet()
											.getName()));
						}
					} else {
						patchReport.getExecutionEntries().put(
								operationInvocation,
								new ReportEntry(Status.SKIPPED, Type.EXECUTION, operationInvocation.getChangeSet()
										.getName()));
					}
				}

				ListIterator<OperationInvocation> li = orderedOperations.listIterator(orderedOperations.size());
				while (li.hasPrevious()) {
					OperationInvocation operationInvocation = (OperationInvocation) li.previous();
					if (!operationInvocation.isApply() && appliedOperations.contains(operationInvocation)) {
						try {
							revert(operationInvocation);
							appliedOperations.remove(operationInvocation);
							patchReport.getExecutionEntries().put(
									operationInvocation,
									new ReportEntry(Status.SKIPPED, Type.EXECUTION, operationInvocation.getChangeSet()
											.getName()));
							patchReport.getIterativeValidationEntries().remove(operationInvocation);
						} catch (OperationNotUndoableException e) {
							e.printStackTrace();
						}

					}
				}
			}

			@Override
			public boolean canExecute() {
				return true;
			}

			@Override
			public void redo() {

			}

		};

		command.execute();
	}

	private synchronized boolean isOutgoingExecuted(OperationInvocation operationInvocation,
			Set<OperationInvocation> executed) {
		for (DependencyContainer dependency : operationInvocation.getOutgoing()) {
			OperationInvocation incomingOperation = dependency.getTarget();
			if (!executed.contains(incomingOperation)) {
				return false;
			}
		}
		return true;
	}

	public ValidationMode getValidationMode() {
		return this.validationMode;
	}

	public void setValidationMode(ValidationMode validationMode) {
		this.patchReport.getValidationEntries().clear();
		this.validationMode = validationMode;
	}

	public AsymmetricDifference getAsymmetricDifference() {
		return difference;
	}

	public IArgumentManager getArgumentManager() {
		return argumentManager;
	}

	public Collection<OperationInvocation> getOrderedOperationInvocations() {
		return orderedOperations;
	}

	public PatchReport getPatchReport() {
		return patchReport;
	}

	public Resource getPatchedResource() {
		return patchedResource;
	}

	public void setPatchedEditingdomain(EditingDomain patchedEditingDomain) {
		this.patchedEditingDomain = patchedEditingDomain;
	}

	// TODO

	// public class PatchResult {
	//
	// private Resource patchedResource;
	// private PatchReport report;
	//
	// public PatchResult(Resource resource, PatchReport report) {
	// this.patchedResource = resource;
	// this.report = report;
	// }
	//
	// public Resource getPatchedResource() {
	// return patchedResource;
	// }
	//
	// public PatchReport getReport() {
	// return report;
	// }
	//
	// }
}
