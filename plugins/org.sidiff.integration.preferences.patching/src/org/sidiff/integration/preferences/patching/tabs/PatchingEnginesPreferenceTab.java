package org.sidiff.integration.preferences.patching.tabs;

import java.util.List;

import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.integration.preferences.patching.valueconverters.SymbolicLinkHandlerValueConverter;
import org.sidiff.integration.preferences.patching.valueconverters.TransformationEngineValueConverter;
import org.sidiff.integration.preferences.tabs.AbstractPreferenceTab;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.PatchMode;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

/**
 * 
 * Class for the patching settings tab.
 * @author Daniel Roedder, Robert Müller
 */
public class PatchingEnginesPreferenceTab extends AbstractPreferenceTab {

	private IPreferenceField transformationEngineField;
	private IPreferenceField executionMode;
	private IPreferenceField patchMode;
	private IPreferenceField minReliability;
	private IPreferenceField symbolicLinkHandlers;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		transformationEngineField = PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_TRANSFORMATION_ENGINE,
				"Transformation Engine",
				TransformationEngineUtil.getAvailableTransformationEngines(ITransformationEngine.DEFAULT_DOCUMENT_TYPE),
				new TransformationEngineValueConverter());
		list.add(transformationEngineField);
		
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

		symbolicLinkHandlers = PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_SYMBOLIC_LINK_HANDLER,
				"Symbolic Link Handler",
				SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers(),
				new SymbolicLinkHandlerValueConverter(),
				true);
		list.add(symbolicLinkHandlers);
	}
}
