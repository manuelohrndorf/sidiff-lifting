package org.sidiff.slicer.rulebased.configuration;

import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.slicer.ISlicingConfiguration;

public class SlicingConfiguration implements ISlicingConfiguration{

	private LiftingSettings liftingSettings;
	
	public SlicingConfiguration(LiftingSettings liftingSettings) {

		this.liftingSettings = liftingSettings;
	}

	public LiftingSettings getLiftingSettings() {
		return liftingSettings;
	}
	public void setLiftingSettings(LiftingSettings liftingSettings) {
		this.liftingSettings = liftingSettings;
	}
}
