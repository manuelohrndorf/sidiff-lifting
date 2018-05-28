package org.sidiff.integration.preferences.patching.tabs;

import java.util.List;

import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.integration.preferences.patching.valueconverters.ModifiedDetectedValueConverter;
import org.sidiff.integration.preferences.patching.valueconverters.TransformationEngineValueConverter;
import org.sidiff.integration.preferences.tabs.AbstractDomainPreferenceTab;
import org.sidiff.patching.transformation.TransformationEngineUtil;

/**
 * Class for the domain specific {@link org.sidiff.patching.api.settings.PatchingSettings}
 * @author Daniel Roedder, Robert Müller
 * 
 */
public class DomainPatchingEnginesPreferenceTab extends AbstractDomainPreferenceTab {

	private IPreferenceField transformationEngineField;
	private IPreferenceField modifiedDetectorField;

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		transformationEngineField = PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_TRANSFORMATION_ENGINE(getDocumentType()),
				"Transformation Engine",
				TransformationEngineUtil.getAvailableTransformationEngines(getDocumentType()),
				new TransformationEngineValueConverter());
		list.add(transformationEngineField);

		modifiedDetectorField = PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_MODIFIED_DETECTOR(getDocumentType()),
				"Modified Detector",
				ModifiedDetectorUtil.getAvailableModifiedDetectors(getDocumentType()),
				new ModifiedDetectedValueConverter(),
				true);
		list.add(modifiedDetectorField);
	}
}
