package org.sidiff.merging.twoway.facade;

import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.silift.patching.settings.ExecutionMode;

public class TwoWayMergingSettings extends LiftingSettings {

	private ChangeKind changeKind;
	
	private ExecutionMode executionMode;

	public TwoWayMergingSettings(String documentType){
		super(documentType);
	}

	public ChangeKind getChangeKind() {
		return changeKind;
	}



	public void setChangeKind(ChangeKind changeKind) {
		this.changeKind = changeKind;
	}



	public ExecutionMode getExecutionMode() {
		return executionMode;
	}

	public void setExecutionMode(ExecutionMode executionMode) {
		this.executionMode = executionMode;
	}



	public enum ChangeKind {
		ADD,
		REMOVE
	}
}
