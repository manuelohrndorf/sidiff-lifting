package org.sidiff.integration.preferences.patching.settingsadapter;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.integration.preferences.interfaces.ISettingsAdapter;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;

/**
 * 
 * @author Robert Müller
 *
 */
public class DomainPatchingSettingsAdapter implements ISettingsAdapter, ISettingsAdapter.DomainSpecific {

	public static String KEY_MODIFIED_DETECTOR(String documentType) {
		return "modifiedDetector[" + documentType + "]";
	}
	public static String KEY_TRANSFORMATION_ENGINE(String documentType) {
		return "transformationEngine[" + documentType + "]";
	}

	private String documentType;

	private ITransformationEngine transformationEngine;
	private IModifiedDetector modifiedDetector;

	@Override
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	@Override
	public boolean canAdapt(AbstractSettings settings) {
		return settings instanceof PatchingSettings;
	}

	@Override
	public void adapt(AbstractSettings settings) {
		PatchingSettings patchingSettings = (PatchingSettings)settings;
		patchingSettings.setTransformationEngine(transformationEngine);
		patchingSettings.setModifiedDetector(modifiedDetector);
	}

	@Override
	public void load(IPreferenceStore store) {
		transformationEngine = TransformationEngineUtil.getTransformationEngine(store.getString(KEY_MODIFIED_DETECTOR(documentType)));
		modifiedDetector = ModifiedDetectorUtil.getModifiedDetector(store.getString(KEY_TRANSFORMATION_ENGINE(documentType)));
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_MODIFIED_DETECTOR("http://www.eclipse.org/emf/2002/Ecore"),
				"org.sidiff.ecore.modifieddetector.EcoreModifiedDetector");
		store.setDefault(KEY_TRANSFORMATION_ENGINE("http://www.eclipse.org/emf/2002/Ecore"),
				"HenshinTransformationEngine");
		store.setDefault(KEY_TRANSFORMATION_ENGINE("http://www.eclipse.org/uml2/5.0.0/UML"),
				"HenshinTransformationEngine");
	}

	@Override
	public int getPosition() {
		return 41;
	}
}
