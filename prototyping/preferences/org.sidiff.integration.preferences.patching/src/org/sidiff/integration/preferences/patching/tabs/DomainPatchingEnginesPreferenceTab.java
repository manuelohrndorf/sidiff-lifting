package org.sidiff.integration.preferences.patching.tabs;

import java.util.List;

import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;

/**
 * Class for the domain specific {@link org.sidiff.patching.settings.PatchingSettings}
 * @author Daniel Roedder, Robert Müller
 * 
 */
public class DomainPatchingEnginesPreferenceTab extends AbstractDomainPreferenceTab {

	private PreferenceField transformationEngineField;
	private PreferenceField modifiedDetectorField;

	@Override
	public void createPreferenceFields(List<PreferenceField> list) {
		transformationEngineField = RadioBoxPreferenceField.create(
				PatchingSettingsAdapter.KEY_TRANSFORMATION_ENGINE(getDocumentType()),
				"Transformation Engine",
				TransformationEngineUtil.getAvailableTransformationEngines(getDocumentType()),
				new IPreferenceValueConverter<ITransformationEngine>() {
					@Override
					public String getValue(ITransformationEngine value) {
						return value.getKey();
					}
					@Override
					public String getLabel(ITransformationEngine value) {
						return value.getName();
					}
				});
		list.add(transformationEngineField);

		modifiedDetectorField = RadioBoxPreferenceField.create(
				PatchingSettingsAdapter.KEY_MODIFIED_DETECTOR(getDocumentType()),
				"Modified Detector",
				ModifiedDetectorUtil.getAvailableModifiedDetectors(getDocumentType()),
				new IPreferenceValueConverter<IModifiedDetector>() {
					@Override
					public String getValue(IModifiedDetector value) {
						return value.getKey();
					}
					@Override
					public String getLabel(IModifiedDetector value) {
						return value.getName();
					}
				});
		list.add(modifiedDetectorField);
	}
}
