package org.sidiff.integration.preferences.interfaces;

import java.util.Comparator;

/**
 * Interface for orderable tabs
 * @author Daniel Roedder, Robert Müller
 * 
 */
public interface ISiDiffOrderableTab {

	/**
	 * Method to get the step in the SiDiff pipeline for a given tab.
	 * @return The position in the SiDiff pipeline
	 */
	public int getStepInPipeline();

	Comparator<ISiDiffOrderableTab> COMPARATOR = new Comparator<ISiDiffOrderableTab>() {
		@Override
		public int compare(ISiDiffOrderableTab o1, ISiDiffOrderableTab o2) {
			return o1.getStepInPipeline() - o2.getStepInPipeline();
		}
	};
}
