package org.sidiff.integration.preferences.difference.tabs;

import java.util.List;

import org.sidiff.integration.preferences.difference.settingsadapter.DifferenceSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.interfaces.IPreferenceTab;

/**
 * 
 * Class to create the tab for the difference settings.
 * @author Daniel Roedder, Robert Müller
 */
public class DifferenceEnginesPreferenceTab implements IPreferenceTab {

	private PreferenceField mergeImports;
	private PreferenceField unmergeImports;

	@Override
	public void createPreferenceFields(List<PreferenceField> list) {
		mergeImports = new CheckBoxPreferenceField(DifferenceSettingsAdapter.KEY_MERGE_IMPORTS, "Merge Imports");
		list.add(mergeImports);

		unmergeImports = new CheckBoxPreferenceField(DifferenceSettingsAdapter.KEY_UNMERGE_IMPORTS, "Unmerge Imports");
		list.add(unmergeImports);
	}
}
