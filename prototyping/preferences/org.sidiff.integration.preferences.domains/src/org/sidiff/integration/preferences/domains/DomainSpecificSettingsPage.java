package org.sidiff.integration.preferences.domains;

import org.sidiff.integration.preferences.AbstractPreferenceTabPage;

/**
 * 
 * Preference page for a specific domain.
 * @author Daniel Roedder, Robert Müller
 */
public class DomainSpecificSettingsPage extends AbstractPreferenceTabPage {

	public DomainSpecificSettingsPage(String title, String documentType) {
		super(documentType);
		super.noDefaultAndApplyButton();
		setTitle(title);
		setMessage(title + " - " + documentType);
	}
}
