package org.sidiff.integration.preferences.domains.matching;

import org.sidiff.integration.preferences.domains.AbstractDomainPreferenceTab;

/**
 * 
 * Abstract class for the domain specific matching settings.
 * @author Daniel Roedder, Robert Müller
 */
public class DomainMatchingPreferenceTab extends AbstractDomainPreferenceTab {

	/**
	 * @see org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Matching";
	}

	@Override
	protected void createPreferenceFields() {
		// tab is empty
	}

	@Override
	public int getStepInPipeline() {
		return 0;
	}
}
