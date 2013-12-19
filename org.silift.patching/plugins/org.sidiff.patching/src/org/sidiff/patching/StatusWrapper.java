package org.sidiff.patching;

import org.sidiff.difference.asymmetric.OperationInvocation;

public class StatusWrapper {

	private OperationInvocation operationInvocation;
	private Status status;
	private boolean undoable;
	
	public StatusWrapper(OperationInvocation operationInvocation){
		this.operationInvocation = operationInvocation;
	}
	
	
	public OperationInvocation getOperationInvocation() {
		return operationInvocation;
	}

	public void setOperationInvocation(OperationInvocation operationInvocation) {
		this.operationInvocation = operationInvocation;
	}

	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	public boolean isUndoable() {
		return undoable;
	}
	
	public void setUndoable(boolean undoable) {
		this.undoable = undoable;
	}
	
}