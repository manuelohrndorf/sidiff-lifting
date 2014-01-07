package org.sidiff.patching.report;

import java.util.Map;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;

public class OperationExecutionEntry extends ReportEntry {

	private OperationInvocation operationInvocation;
	private OperationExecutionKind kind;
	private Map<ParameterBinding, Object> inArgs;
	private Map<ParameterBinding, Object> outArgs;
	private Exception error;

	// skipped
	OperationExecutionEntry(OperationInvocation op, OperationExecutionKind kind) {
		super();

		this.operationInvocation = op;
		this.kind = kind;
	}

	// passed and modified
	OperationExecutionEntry(OperationInvocation op, OperationExecutionKind kind, Map<ParameterBinding, Object> inArgs, Map<ParameterBinding, Object> outArgs) {
		this(op, kind);

		this.inArgs = inArgs;
		this.outArgs = outArgs;
	}

	// failed
	OperationExecutionEntry(OperationInvocation op, OperationExecutionKind kind, Map<ParameterBinding, Object> inArgs,
			Exception error) {
		this(op, kind);
		
		this.inArgs = inArgs;
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

	public Map<ParameterBinding, Object> getInArgs() {
		return inArgs;
	}
	
	public Map<ParameterBinding, Object> getOutArgs() {
		return outArgs;
	}

	@Override
	public String toString() {
		return "ReportEntry [kind=" + kind + ", description=" + getDescription() + "]";
	}
}
