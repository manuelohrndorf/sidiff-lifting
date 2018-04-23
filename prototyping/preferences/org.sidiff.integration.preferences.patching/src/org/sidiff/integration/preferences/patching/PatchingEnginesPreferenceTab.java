package org.sidiff.integration.preferences.patching;

import org.sidiff.integration.preferences.AbstractEnginePreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.NumberPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.valueconverters.EnumPreferenceValueConverter;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;

/**
 * 
 * Class for the patching settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class PatchingEnginesPreferenceTab extends AbstractEnginePreferenceTab {

	/**
	 * The {@link PreferenceField} for the execution Mode setting
	 */
	private PreferenceField executionMode;
	
	/**
	 * The {@link PreferenceField} for the patch mode setting
	 */
	private PreferenceField patchMode;
	
	/**
	 * The {@link PreferenceField} for the minimum reliability setting
	 */
	private PreferenceField minReliability;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Patching";
	}

	@Override
	protected void createPreferenceFields() {
		executionMode = RadioBoxPreferenceField.create("executionMode", "Execution Mode",
				ExecutionMode.values(), new EnumPreferenceValueConverter());
		addField(executionMode);

		patchMode = RadioBoxPreferenceField.create("patchMode", "Patch Mode",
				PatchMode.values(), new EnumPreferenceValueConverter());
		addField(patchMode);

		minReliability = new NumberPreferenceField("minReliability", "Minimum Reliability");
		addField(minReliability);
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 3;
	}
}
