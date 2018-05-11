package org.sidiff.integration.preferences.patching.tabs;

import java.util.List;

import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.NumberPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.interfaces.IPreferenceTab;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

/**
 * 
 * Class for the patching settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class PatchingEnginesPreferenceTab implements IPreferenceTab {

	private PreferenceField executionMode;
	private PreferenceField patchMode;
	private PreferenceField minReliability;
	private PreferenceField useInteractivePatching;
	private PreferenceField symbolicLinkHandlers;

	@Override
	public void createPreferenceFields(List<PreferenceField> list) {
		executionMode = RadioBoxPreferenceField.create(
				PatchingSettingsAdapter.KEY_EXECUTION_MODE,
				"Execution Mode",
				ExecutionMode.class);
		list.add(executionMode);

		patchMode = RadioBoxPreferenceField.create(
				PatchingSettingsAdapter.KEY_PATCH_MODE,
				"Patch Mode",
				PatchMode.class);
		list.add(patchMode);

		minReliability = new NumberPreferenceField(
				PatchingSettingsAdapter.KEY_MIN_RELIABILITY,
				"Minimum reliability");
		list.add(minReliability);

		useInteractivePatching = new CheckBoxPreferenceField(
				PatchingSettingsAdapter.KEY_USE_INTERACTIVE_PATCHING,
				"Use interactive patching");
		list.add(useInteractivePatching);

		symbolicLinkHandlers = RadioBoxPreferenceField.create(
				PatchingSettingsAdapter.KEY_SYMBOLIC_LINK_HANDLER,
				"Symbolic Link Handler",
				SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers(),
				new IPreferenceValueConverter<ISymbolicLinkHandler>() {
					@Override
					public String getValue(ISymbolicLinkHandler value) {
						return value.getKey();
					}
					@Override
					public String getLabel(ISymbolicLinkHandler value) {
						return value.getName();
					}
				});
		list.add(symbolicLinkHandlers);
	}
}
