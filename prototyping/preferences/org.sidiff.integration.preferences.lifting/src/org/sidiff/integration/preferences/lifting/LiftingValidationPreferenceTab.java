package org.sidiff.integration.preferences.lifting;

import org.sidiff.integration.preferences.AbstractValidationPreferenceTab;

/**
 * Class for the lifting validation settings
 * @author Daniel Roedder, Robert Müller
 *
 */
public class LiftingValidationPreferenceTab extends AbstractValidationPreferenceTab {

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 3;
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffValidationPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Lifting";
	}

	@Override
	protected void createPreferenceFields() {
		// tab is currently empty
	}
}
