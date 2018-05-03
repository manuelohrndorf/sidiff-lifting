package org.sidiff.integration.preferences.domains.patching;

import java.util.List;

import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.integration.preferences.domains.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.domains.patching.settingsadapter.DomainPatchingSettingsAdapter;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;

/**
 * Class for the domain specific {@link org.sidiff.patching.settings.PatchingSettings}
 * @author Daniel Roedder, Robert Müller
 * 
 */
public class DomainPatchingPreferenceTab extends AbstractDomainPreferenceTab {

	private RadioBoxPreferenceField<?> transformationEngineField;
	private RadioBoxPreferenceField<?> modifiedDetectorField;

	@Override
	public String getTitle() {
		return "Patching";
	}

	@Override
	public int getPosition() {
		return 40;
	}

	@Override
	public void createPreferenceFields(List<PreferenceField> list) {
		transformationEngineField = RadioBoxPreferenceField.create(
				DomainPatchingSettingsAdapter.KEY_TRANSFORMATION_ENGINE(getDocumentType()),
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
				DomainPatchingSettingsAdapter.KEY_MODIFIED_DETECTOR(getDocumentType()),
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
