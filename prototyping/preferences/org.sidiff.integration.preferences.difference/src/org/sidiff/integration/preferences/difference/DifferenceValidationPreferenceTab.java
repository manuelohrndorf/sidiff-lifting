package org.sidiff.integration.preferences.difference;

import org.sidiff.integration.preferences.AbstractValidationPreferenceTab;

/**
 * Class for the validation difference settings.
 * @author Daniel Roedder, Robert Müller
 *
 */
public class DifferenceValidationPreferenceTab extends AbstractValidationPreferenceTab {

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 2;
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Difference";
	}

	@Override
	protected void createPreferenceFields() {
		// tab is currently empty
	}
}
