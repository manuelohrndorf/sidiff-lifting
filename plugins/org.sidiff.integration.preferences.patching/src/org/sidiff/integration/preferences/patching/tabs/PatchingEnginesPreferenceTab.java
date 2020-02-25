package org.sidiff.integration.preferences.patching.tabs;

import java.util.List;

import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.AbstractPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.ExtensionValueConverter;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

/**
 * 
 * Class for the patching settings tab.
 * @author Daniel Roedder
 * @author rmueller
 */
public class PatchingEnginesPreferenceTab extends AbstractPreferenceTab {

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		IPreferenceField transformationEngineField =
			PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_TRANSFORMATION_ENGINE,
				"Transformation Engine",
				ITransformationEngine.MANAGER.getGenericExtensions(),
				ExtensionValueConverter.getInstance());
		list.add(transformationEngineField);

		IPreferenceField minReliability =
			PreferenceFieldFactory.createNumber(
				PatchingSettingsAdapter.KEY_MIN_RELIABILITY,
				"Minimum reliability",
				-1,
				Integer.MAX_VALUE);
		list.add(minReliability);

		IPreferenceField symbolicLinkHandlers =
			PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_SYMBOLIC_LINK_HANDLER,
				"Symbolic Link Handler",
				ISymbolicLinkHandler.MANAGER.getSortedExtensions(),
				ExtensionValueConverter.getInstance(),
				true);
		list.add(symbolicLinkHandlers);
	}
}
