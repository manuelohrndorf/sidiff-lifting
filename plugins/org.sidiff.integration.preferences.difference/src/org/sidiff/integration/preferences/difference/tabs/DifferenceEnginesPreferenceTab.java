package org.sidiff.integration.preferences.difference.tabs;

import java.util.List;

import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.integration.preferences.difference.settingsadapter.DifferenceSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.tabs.AbstractPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.ExtensionValueConverter;

/**
 * Class to create the tab for the difference settings.
 * @author Daniel Roedder
 * @author rmueller
 */
public class DifferenceEnginesPreferenceTab extends AbstractPreferenceTab {

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		IPreferenceField techDiffBuilderField =
			PreferenceFieldFactory.createOrderedList(
				DifferenceSettingsAdapter.KEY_TECHNICAL_DIFFERENCE_BUILDERS,
				"Technical Difference Builders",
				ITechnicalDifferenceBuilder.MANAGER.getGenericExtensions(),
				ExtensionValueConverter.getInstance());
		list.add(techDiffBuilderField);

		IPreferenceField mergeImports =
			PreferenceFieldFactory.createCheckBox(
				DifferenceSettingsAdapter.KEY_MERGE_IMPORTS,
				"Merge Imports");
		list.add(mergeImports);

		IPreferenceField unmergeImports =
			PreferenceFieldFactory.createCheckBox(
				DifferenceSettingsAdapter.KEY_UNMERGE_IMPORTS,
				"Unmerge Imports");
		list.add(unmergeImports);
	}
}
