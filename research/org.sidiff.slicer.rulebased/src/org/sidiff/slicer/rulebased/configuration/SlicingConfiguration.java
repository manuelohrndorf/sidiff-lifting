package org.sidiff.slicer.rulebased.configuration;

import org.sidiff.difference.lifting.api.settings.LiftingSettings;

public class SlicingConfiguration {

	private SlicingMode slicingMode = SlicingMode.BATCH;
	
	private boolean changePreserving = false;
	
	private LiftingSettings liftingSettings;
	
	public SlicingConfiguration(LiftingSettings liftingSettings) {
		this.liftingSettings = liftingSettings;
	}


	public SlicingMode getSlicingMode() {
		return slicingMode;
	}


	public void setSlicingMode(SlicingMode slicingMode) {
		this.slicingMode = slicingMode;
	}


	public boolean isChangePreserving() {
		return changePreserving;
	}


	public void setChangePreserving(boolean changePreserving) {
		this.changePreserving = changePreserving;
	}


	public LiftingSettings getLiftingSettings() {
		return liftingSettings;
	}
	public void setLiftingSettings(LiftingSettings liftingSettings) {
		this.liftingSettings = liftingSettings;
	}
	
	public enum SlicingMode {
		BATCH,
		INTERACTIVE,
	}
}
