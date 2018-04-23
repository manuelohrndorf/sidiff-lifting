package org.sidiff.integration.preferences.domains.patching;

import java.util.ArrayList;
import java.util.List;

import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.integration.preferences.domains.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
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
	 * List to hold all {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField}
	 */
	private List<PreferenceField> fieldList;
	
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

	/**
	 * @see org.sidiff.integration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();

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
		fieldList.add(transformationEngineField);

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
		fieldList.add(modifiedDetectorField);

		useInteractivePatching = new CheckBoxPreferenceField("useInteractivePatching", "Use interactive patching");
		fieldList.add(useInteractivePatching);
		return fieldList;
	}

	@Override
	public int getStepInPipeline() {
		return 3;
	}
}
