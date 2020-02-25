package org.sidiff.integration.preferences.patching.tabs;

import java.util.Collections;
import java.util.List;

import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;
import org.sidiff.integration.preferences.fieldeditors.PreferenceFieldFactory;
import org.sidiff.integration.preferences.patching.settingsadapter.PatchingSettingsAdapter;
import org.sidiff.integration.preferences.tabs.AbstractDomainPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.ExtensionValueConverter;
import org.sidiff.patching.transformation.ITransformationEngine;

/**
 * Class for the domain specific {@link org.sidiff.patching.api.settings.PatchingSettings}
 * @author Daniel Roedder
 * @author rmueller
 */
public class DomainPatchingEnginesPreferenceTab extends AbstractDomainPreferenceTab {

	@Override
	public void createPreferenceFields(List<IPreferenceField> list) {
		IPreferenceField transformationEngineField =
			PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_TRANSFORMATION_ENGINE(getDocumentType()),
				"Transformation Engine",
				ITransformationEngine.MANAGER.getExtensions(Collections.singleton(getDocumentType()), false),
				ExtensionValueConverter.getInstance());
		list.add(transformationEngineField);

		IPreferenceField modifiedDetectorField =
			PreferenceFieldFactory.createRadioBox(
				PatchingSettingsAdapter.KEY_MODIFIED_DETECTOR(getDocumentType()),
				"Modified Detector",
				IModifiedDetector.MANAGER.getExtensions(Collections.singleton(getDocumentType()), true),
				ExtensionValueConverter.getInstance(),
				true);
		list.add(modifiedDetectorField);
	}
}
