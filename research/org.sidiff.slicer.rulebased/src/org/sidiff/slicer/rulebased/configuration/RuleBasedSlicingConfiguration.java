package org.sidiff.slicer.rulebased.configuration;

import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.slicer.ISlicingConfiguration;

public class RuleBasedSlicingConfiguration implements ISlicingConfiguration {
	
	private UUIDResource emtpyResource;
	private UUIDResource completeResource;
	private LiftingSettings liftingSettings;
	
	public RuleBasedSlicingConfiguration() {
		
	}
	
	public RuleBasedSlicingConfiguration(UUIDResource emptyResource, UUIDResource completeResource, LiftingSettings liftingSettings) {
		this.emtpyResource = emptyResource;
		this.completeResource = completeResource;
		this.liftingSettings = liftingSettings;
	}

	public UUIDResource getEmtpyResource() {
		return emtpyResource;
	}
	public void setEmtpyResource(UUIDResource emtpyResource) {
		this.emtpyResource = emtpyResource;
	}
	public UUIDResource getCompleteResource() {
		return completeResource;
	}
	public void setCompleteResource(UUIDResource completeResource) {
		this.completeResource = completeResource;
	}
	public LiftingSettings getLiftingSettings() {
		return liftingSettings;
	}
	public void setLiftingSettings(LiftingSettings liftingSettings) {
		this.liftingSettings = liftingSettings;
	}
	
}
