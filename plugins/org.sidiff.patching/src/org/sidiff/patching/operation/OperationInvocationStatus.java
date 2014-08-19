package org.sidiff.patching.operation;

/**
 * Enumerates the possible states of an operation invocation.
 * 
 * @author kehrer, cpietsch
 */
public enum OperationInvocationStatus {

	INIT, PASSED, REVERTED, FAILED, IGNORED
}
