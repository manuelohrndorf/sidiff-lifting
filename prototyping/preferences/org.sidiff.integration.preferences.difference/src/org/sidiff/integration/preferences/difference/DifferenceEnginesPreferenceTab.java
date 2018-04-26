package org.sidiff.integration.preferences.difference;

import org.sidiff.integration.preferences.AbstractEnginePreferenceTab;
import org.sidiff.integration.preferences.difference.settingsadapter.DifferenceSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;

/**
 * 
 * Class to create the tab for the difference settings.
 * @author Daniel Roedder, Robert Müller
 */
public class DifferenceEnginesPreferenceTab extends AbstractEnginePreferenceTab {

	/**
	 * The {@link PreferenceField} for the merge imports setting
	 */
	private PreferenceField mergeImports;
	
	/**
	 * The {@link PreferenceField} for the unmerge imports setting
	 */
	private PreferenceField unmergeImports;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Difference";
	}

	@Override
	protected void createPreferenceFields() {
		mergeImports = new CheckBoxPreferenceField(DifferenceSettingsAdapter.KEY_MERGE_IMPORTS, "Merge Imports");
		addField(mergeImports);

		unmergeImports = new CheckBoxPreferenceField(DifferenceSettingsAdapter.KEY_UNMERGE_IMPORTS, "Unmerge Imports");
		addField(unmergeImports);
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 1;
	}
}
