package org.sidiff.patching.report;

public enum OperationExecutionKind {

	PASSED("Passed"),
	EXEC_FAILED("Execution failed"),
	WARNING("Warning"),
	REVERTED("Reverted"),
	REVERT_FAILED("Reverting failed");

	private final String name;

	private OperationExecutionKind(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
