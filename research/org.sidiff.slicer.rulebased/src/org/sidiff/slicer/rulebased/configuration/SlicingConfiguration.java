package org.sidiff.slicer.rulebased.configuration;

import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.slicer.ISlicingConfiguration;

public class SlicingConfiguration implements ISlicingConfiguration{
	
	SlicingMode slicingMode;

	private LiftingSettings liftingSettings;
	
	public SlicingConfiguration(SlicingMode slicingMode, LiftingSettings liftingSettings) {
		this.slicingMode = slicingMode;
		this.liftingSettings = liftingSettings;
	}

	public SlicingMode getSlicingMode() {
		return slicingMode;
	}

	public void setSlicingMode(SlicingMode slicingMode) {
		this.slicingMode = slicingMode;
	}

	public LiftingSettings getLiftingSettings() {
		return liftingSettings;
	}
	public void setLiftingSettings(LiftingSettings liftingSettings) {
		this.liftingSettings = liftingSettings;
	}
}
