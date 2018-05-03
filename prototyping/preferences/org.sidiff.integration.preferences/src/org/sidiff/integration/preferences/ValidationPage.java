/**
 * @author Daniel Roedder
 */
package org.sidiff.integration.preferences;

import org.sidiff.integration.preferences.interfaces.IPreferenceTab;

/**
 * Preference subpage for validation settings.
 * @author Daniel Roedder, Robert Müller
 *
 */
public class ValidationPage extends AbstractPreferenceTabPage {

	public ValidationPage() {
		super(IPreferenceTab.TabPage.VALIDATION);
	}
}
