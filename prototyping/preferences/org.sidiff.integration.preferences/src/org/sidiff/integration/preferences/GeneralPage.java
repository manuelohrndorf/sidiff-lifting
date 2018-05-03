/**
 * @author Daniel Roedder
 */
package org.sidiff.integration.preferences;

import org.sidiff.integration.preferences.interfaces.IPreferenceTab;

/**
 * Preference subpage for general settings.
 * @author Daniel Roedder, Robert Müller
 *
 */
public class GeneralPage extends AbstractPreferenceTabPage {

	public GeneralPage() {
		super(IPreferenceTab.TabPage.GENERAL);
	}
}
