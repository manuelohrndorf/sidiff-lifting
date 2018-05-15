package org.sidiff.integration.preferences.difference.tabs;

import java.util.Collections;
import java.util.List;

import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.util.TechnicalDifferenceUtils;
import org.sidiff.integration.preferences.difference.settingsadapter.DifferenceSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.tabs.IPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * 
 * Class to create the tab for the difference settings.
 * @author Daniel Roedder, Robert Müller
 */
public class DifferenceEnginesPreferenceTab implements IPreferenceTab {

	private IPreferenceField techDiffBuilderField;
	private IPreferenceField mergeImports;
	private IPreferenceField unmergeImports;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		techDiffBuilderField = PreferenceFieldFactory.createOrderedList(
				DifferenceSettingsAdapter.KEY_TECHNICAL_DIFFERENCE_BUILDERS,
				"Technical Difference Builders",
				TechnicalDifferenceUtils.getAvailableTechnicalDifferenceBuilders(
						Collections.singleton(EMFModelAccess.GENERIC_DOCUMENT_TYPE)),
				new IPreferenceValueConverter<ITechnicalDifferenceBuilder>() {
					@Override
					public String getValue(ITechnicalDifferenceBuilder value) {
						return value.getKey();
					}
					@Override
					public String getLabel(ITechnicalDifferenceBuilder value) {
						return value.getName();
					}
				});
		list.add(techDiffBuilderField);

		mergeImports = PreferenceFieldFactory.createCheckBox(
				DifferenceSettingsAdapter.KEY_MERGE_IMPORTS, "Merge Imports");
		list.add(mergeImports);

		unmergeImports = PreferenceFieldFactory.createCheckBox(
				DifferenceSettingsAdapter.KEY_UNMERGE_IMPORTS, "Unmerge Imports");
		list.add(unmergeImports);
	}
}
