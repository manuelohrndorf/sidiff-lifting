package org.sidiff.vcmsintegration.preferences.interfaces;

/**
 * Interface for orderable tabs
 * @author Daniel Roedder
 * 
 */
public interface ISiDiffOrderableTab {
	

	/**
	 * Method to get the step in the SiDiff pipeline for a given tab.
	 * @return The position in the SiDiff pipeline
	 */
	public int getStepInPipeline();

}
