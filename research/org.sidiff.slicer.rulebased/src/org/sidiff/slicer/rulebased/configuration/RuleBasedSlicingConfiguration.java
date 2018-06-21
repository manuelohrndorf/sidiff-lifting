package org.sidiff.slicer.rulebased.configuration;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.slicer.ISlicingConfiguration;

public class RuleBasedSlicingConfiguration implements ISlicingConfiguration {
	
	private UUIDResource emtpyResource;
	private UUIDResource completeResource;

	private Set<EObject> old_slicing_criteria;
	
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
	
	public Set<EObject> getOldSlicingCriteria() {
		if(this.old_slicing_criteria == null) {
			this.old_slicing_criteria = new HashSet<EObject>();
		}
		return old_slicing_criteria;
	}
	
	public void setOldSlicingCriteria(Set<EObject> old_slicing_criteria) {
		this.old_slicing_criteria = old_slicing_criteria;
	}
	
	public LiftingSettings getLiftingSettings() {
		return liftingSettings;
	}
	public void setLiftingSettings(LiftingSettings liftingSettings) {
		this.liftingSettings = liftingSettings;
	}

	
	
}
