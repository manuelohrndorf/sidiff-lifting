package org.sidiff.slicer.rulebased.configuration;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.slicer.ISlicingConfiguration;

public class SlicingConfiguration implements ISlicingConfiguration{

	private Resource originModel;
	private LiftingSettings liftingSettings;
	
	public SlicingConfiguration(Resource originModel, LiftingSettings liftingSettings) {
		this.originModel = originModel;
		this.liftingSettings = liftingSettings;
	}
	
	public Resource getOriginModel() {
		return originModel;
	}

	public void setOriginModel(Resource originModel) {
		this.originModel = originModel;
	}

	public LiftingSettings getLiftingSettings() {
		return liftingSettings;
	}
	public void setLiftingSettings(LiftingSettings liftingSettings) {
		this.liftingSettings = liftingSettings;
	}
}
