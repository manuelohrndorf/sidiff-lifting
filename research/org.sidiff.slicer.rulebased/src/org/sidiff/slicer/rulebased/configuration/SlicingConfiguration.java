package org.sidiff.slicer.rulebased.configuration;

import org.sidiff.difference.lifting.api.settings.LiftingSettings;

public class SlicingConfiguration {

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
