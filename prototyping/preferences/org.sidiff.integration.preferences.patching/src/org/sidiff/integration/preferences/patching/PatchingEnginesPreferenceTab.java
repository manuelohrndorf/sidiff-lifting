package org.sidiff.integration.preferences.patching;

import org.sidiff.integration.preferences.AbstractEnginePreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.NumberPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
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
	 * The {@link CheckBoxPreferenceField} for the use interactive patching setting
	 */
	private CheckBoxPreferenceField useInteractivePatching;

	/**
	 * The {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField} for the symbolic link handlers
	 */
	private PreferenceField symbolicLinkHandlers;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Patching";
	}

	@Override
	protected void createPreferenceFields() {
		executionMode = RadioBoxPreferenceField.create(
				PatchingSettingsAdapter.KEY_EXECUTION_MODE,
				"Execution Mode",
				ExecutionMode.class);
		addField(executionMode);

		patchMode = RadioBoxPreferenceField.create(
				PatchingSettingsAdapter.KEY_PATCH_MODE,
				"Patch Mode",
				PatchMode.class);
		addField(patchMode);

		minReliability = new NumberPreferenceField(
				PatchingSettingsAdapter.KEY_MIN_RELIABILITY,
				"Minimum reliability");
		addField(minReliability);

		useInteractivePatching = new CheckBoxPreferenceField(
				PatchingSettingsAdapter.KEY_USE_INTERACTIVE_PATCHING,
				"Use interactive patching");
		addField(useInteractivePatching);

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
		addField(symbolicLinkHandlers);
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 3;
	}
}
