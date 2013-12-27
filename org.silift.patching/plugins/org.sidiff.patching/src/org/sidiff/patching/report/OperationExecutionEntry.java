package org.sidiff.patching.report;

import java.util.Map;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;

public class OperationExecutionEntry extends ReportEntry {

	public enum OperationExecutionKind {
		PASSED, SKIPPED, EXEC_FAILED, WARNING, REVERTED, REVERT_FAILED;
	}

	private OperationInvocation operationInvocation;
	private OperationExecutionKind kind;
	private Map<ParameterBinding, Object> invocationArguments;
	private Exception error;

	// skipped
	OperationExecutionEntry(OperationInvocation op, OperationExecutionKind kind) {
		super();

		this.operationInvocation = op;
		this.kind = kind;
	}

	// passed and modified
	OperationExecutionEntry(OperationInvocation op, OperationExecutionKind kind, Map<ParameterBinding, Object> args) {
		this(op, kind);

		this.invocationArguments = args;
	}

	// failed
	OperationExecutionEntry(OperationInvocation op, OperationExecutionKind kind, Map<ParameterBinding, Object> args,
			Exception error) {
		this(op, kind, args);
		this.error = error;
	}

	// revert failed
	OperationExecutionEntry(OperationInvocation op, OperationExecutionKind kind, Exception error) {
		this(op, kind);
		this.error = error;
	}

	public OperationExecutionKind getKind() {
		return kind;
	}

	@Override
	public String getDescription() {
		return operationInvocation.getChangeSet().getName();
	}

	public Exception getError() {
		return error;
	}

	public Map<ParameterBinding, Object> getInvocationArguments() {
		return invocationArguments;
	}

	@Override
	public String toString() {
		return "ReportEntry [kind=" + kind + ", description=" + getDescription() + "]";
	}
}
