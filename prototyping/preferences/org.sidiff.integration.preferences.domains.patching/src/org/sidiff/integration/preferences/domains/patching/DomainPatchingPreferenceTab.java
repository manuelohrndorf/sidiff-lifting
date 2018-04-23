package org.sidiff.integration.preferences.domains.patching;

import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.integration.preferences.domains.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;

/**
 * Abstract class for the domain specific {@link org.sidiff.patching.settings.PatchingSettings}
 * @author Daniel Roedder, Robert Müller
 * 
 */
public class DomainPatchingPreferenceTab extends AbstractDomainPreferenceTab {

	/**
	 * The {@link RadioBoxPreferenceField} for the transformation Engine setting
	 */
	private RadioBoxPreferenceField<?> transformationEngineField;
	
	/**
	 * The {@link RadioBoxPreferenceField} for the modified detector setting
	 */
	private RadioBoxPreferenceField<?> modifiedDetectorField;
	
	/**
	 * The {@link CheckBoxPreferenceField} for the use interactive patching setting
	 */
	private CheckBoxPreferenceField useInteractivePatching;
	
	/**
	 * @see org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Patching";
	}

	@Override
	protected void createPreferenceFields() {
		transformationEngineField = RadioBoxPreferenceField.create(
				getDocumentType() + "transformationEngine",
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
		addField(transformationEngineField);

		modifiedDetectorField = RadioBoxPreferenceField.create(
				getDocumentType() + "modifiedDetector",
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
		addField(modifiedDetectorField);

		useInteractivePatching = new CheckBoxPreferenceField("useInteractivePatching", "Use interactive patching");
		addField(useInteractivePatching);
	}

	@Override
	public int getStepInPipeline() {
		return 3;
	}
}
