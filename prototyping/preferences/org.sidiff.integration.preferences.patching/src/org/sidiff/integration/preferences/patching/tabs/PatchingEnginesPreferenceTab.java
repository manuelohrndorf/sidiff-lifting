package org.sidiff.integration.preferences.patching.tabs;

import java.util.List;

import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.integration.preferences.patching.valueconverters.SymbolicLinkHandlerValueConverter;
import org.sidiff.integration.preferences.tabs.IPreferenceTab;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

/**
 * 
 * Class for the patching settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class PatchingEnginesPreferenceTab implements IPreferenceTab {

	private IPreferenceField executionMode;
	private IPreferenceField patchMode;
	private IPreferenceField minReliability;
	private IPreferenceField useInteractivePatching;
	private IPreferenceField symbolicLinkHandlers;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		executionMode = PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_EXECUTION_MODE,
				"Execution Mode",
				ExecutionMode.class);
		list.add(executionMode);

		patchMode = PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_PATCH_MODE,
				"Patch Mode",
				PatchMode.class);
		list.add(patchMode);

		minReliability = PreferenceFieldFactory.createNumber(
				PatchingSettingsAdapter.KEY_MIN_RELIABILITY,
				"Minimum reliability", -1, Integer.MAX_VALUE);
		list.add(minReliability);

		useInteractivePatching = PreferenceFieldFactory.createCheckBox(
				PatchingSettingsAdapter.KEY_USE_INTERACTIVE_PATCHING,
				"Use interactive patching");
		list.add(useInteractivePatching);

		symbolicLinkHandlers = PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_SYMBOLIC_LINK_HANDLER,
				"Symbolic Link Handler",
				SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers(),
				new SymbolicLinkHandlerValueConverter());
		list.add(symbolicLinkHandlers);
	}
}
