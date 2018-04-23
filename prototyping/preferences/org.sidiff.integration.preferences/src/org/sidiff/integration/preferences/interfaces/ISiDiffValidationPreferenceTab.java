package org.sidiff.integration.preferences.interfaces;

/**
 * Interface for all Validation Preferences tabs.
 * All tabs of this interface are displayed in the validation section of the preferences.
 * @author Daniel Roedder, Robert Müller
 *
 */
public interface ISiDiffValidationPreferenceTab extends ISiDiffPreferenceTab {
	String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.validationPipelineStep";
}
