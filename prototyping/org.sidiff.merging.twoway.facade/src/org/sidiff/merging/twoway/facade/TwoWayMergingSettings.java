package org.sidiff.merging.twoway.facade;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.patching.settings.ExecutionMode;

public class TwoWayMergingSettings extends LiftingSettings {
	
	private ExecutionMode executionMode;

	public TwoWayMergingSettings(String documentType){
		super(documentType);
		this.setCalculateEditRuleMatch(true);
		this.setExecutionMode(ExecutionMode.BATCH);
		this.setScope(Scope.RESOURCE);
	}

	public ExecutionMode getExecutionMode() {
		return executionMode;
	}

	public void setExecutionMode(ExecutionMode executionMode) {
		this.executionMode = executionMode;
	}
}
