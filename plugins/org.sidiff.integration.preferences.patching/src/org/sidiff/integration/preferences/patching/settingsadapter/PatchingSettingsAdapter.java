package org.sidiff.integration.preferences.patching.settingsadapter;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.ISettings;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.integration.preferences.patching.Activator;
import org.sidiff.integration.preferences.settingsadapter.AbstractSettingsAdapter;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.validation.ValidationMode;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

/**
 * 
 * @author Robert Müller
 *
 */
public class PatchingSettingsAdapter extends AbstractSettingsAdapter {

	public static String KEY_MODIFIED_DETECTOR(String documentType) {
		return "modifiedDetector[" + documentType + "]";
	}
	public static final String KEY_TRANSFORMATION_ENGINE = "transformationEngine";
	public static String KEY_TRANSFORMATION_ENGINE(String documentType) {
		return KEY_TRANSFORMATION_ENGINE + "[" + documentType + "]";
	}
	public static final String KEY_MIN_RELIABILITY = "minReliability";
	public static final String KEY_VALIDATION_MODE = "validationMode";
	public static final String KEY_SYMBOLIC_LINK_HANDLER = "symbolicLinkHandler";

	private ITransformationEngine transformationEngine;
	private IModifiedDetector modifiedDetector;
	private int minReliability;
	private ValidationMode validationMode;
	private ISymbolicLinkHandler symbolicLinkHandler;

	@Override
	public boolean canAdapt(ISettings settings) {
		return settings instanceof PatchingSettings;
	}

	@Override
	public void adapt(ISettings settings) {
		PatchingSettings patchingSettings = (PatchingSettings)settings;

		if(transformationEngine != null && isConsidered(PatchingSettingsItem.TRANSFORMATION_ENGINE)) {
			patchingSettings.setTransformationEngine(transformationEngine);
		}
		if(modifiedDetector != null && isConsidered(PatchingSettingsItem.MODIFIED_DETECTOR)) {
			patchingSettings.setModifiedDetector(modifiedDetector);
		}
		if(minReliability != -1 && isConsidered(PatchingSettingsItem.RELIABILITY)) {
			patchingSettings.setMinReliability(minReliability);
		}
		if(validationMode != null && isConsidered(PatchingSettingsItem.VALIDATION_MODE)) {
			patchingSettings.setValidationMode(validationMode);
		}
		if(isConsidered(PatchingSettingsItem.SYMBOLIC_LINK_HANDLER)) {
			patchingSettings.setSymbolicLinkHandler(symbolicLinkHandler);
		}
	}

	@Override
	public void load(IPreferenceStore store) {
		loadTransformationEngine(store);
		loadModifiedDetector(store);
		loadMinReliability(store);
		loadValidationMode(store);
		loadSymbolicLinkHandler(store);
	}

	protected void loadTransformationEngine(IPreferenceStore store) {
		transformationEngine = null;
		for(String documentType : getDocumentTypes()) {
			// the first transformation engine is used
			if(transformationEngine == null) {
				String key = store.getString(KEY_TRANSFORMATION_ENGINE(documentType));
				if(!key.isEmpty()) {
					transformationEngine = ITransformationEngine.MANAGER.getExtension(key).orElse(null);
					if(transformationEngine == null) {
						addWarning("Transformation Engine with key '" + key + "' was not found.");
					}
				}
			}
		}

		// get generic transformation engine if no domain specific one is selected
		if(transformationEngine == null) {
			String key = store.getString(KEY_TRANSFORMATION_ENGINE);
			if(!key.isEmpty()) {
				transformationEngine = ITransformationEngine.MANAGER.getExtension(key).orElse(null);
				if(transformationEngine == null) {
					addWarning("Transformation Engine with key '" + key + "' was not found.");
				}
			}
		}

		if(transformationEngine == null) {
			addError("No Transformation Engine was specified.");
		}
	}

	protected void loadModifiedDetector(IPreferenceStore store) {
		modifiedDetector = null;
		for(String documentType : getDocumentTypes()) {
			// the first modified detector is used
			if(modifiedDetector == null) {
				String key = store.getString(KEY_MODIFIED_DETECTOR(documentType));
				if(!key.isEmpty()) {
					modifiedDetector = IModifiedDetector.MANAGER.getExtension(key).orElse(null);
					if(modifiedDetector == null) {
						addWarning("Modified Detector with key '" + key + "' was not found.");
					}
				}
			}
		}
		// modified detector is allowed to be unset
	}

	protected void loadMinReliability(IPreferenceStore store) {
		String minReliabilityValue = store.getString(KEY_MIN_RELIABILITY);
		try {
			minReliability = Integer.parseInt(minReliabilityValue);
			if(minReliability < -1) {
				minReliability = -1;
			}
		} catch (NumberFormatException e) {
			minReliability = -1;
			addWarning("Invalid value for Minimum Reliability: '" + minReliabilityValue + "'", e);
		}
	}

	protected void loadValidationMode(IPreferenceStore store) {
		String validationModeValue = store.getString(KEY_VALIDATION_MODE);
		try {
			validationMode = ValidationMode.valueOf(validationModeValue);
		} catch (IllegalArgumentException e) {
			validationMode = null;
			addError("Invalid value for Validation Mode: '" + validationModeValue + "'", e);
		}
	}

	protected void loadSymbolicLinkHandler(IPreferenceStore store) {
		String symbolicLinkHandlerKey = store.getString(KEY_SYMBOLIC_LINK_HANDLER);
		if(symbolicLinkHandlerKey.isEmpty()) {
			// symbolic links are disabled
			symbolicLinkHandler = null;
		} else {
			symbolicLinkHandler = ISymbolicLinkHandler.MANAGER.getExtension(symbolicLinkHandlerKey).orElse(null);
			if(symbolicLinkHandler == null) {
				addError("Symbolic Link Handler with key '" + symbolicLinkHandlerKey + "' was not found.");
			}
		}
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_MODIFIED_DETECTOR("http://www.eclipse.org/emf/2002/Ecore"), "EcoreModifiedDetector");
		store.setDefault(KEY_TRANSFORMATION_ENGINE, "HenshinTransformationEngine");
		store.setDefault(KEY_MIN_RELIABILITY, -1);
		store.setDefault(KEY_VALIDATION_MODE, ValidationMode.MODEL_VALIDATION.name());
		store.setDefault(KEY_SYMBOLIC_LINK_HANDLER, "");
	}

	@Override
	protected BasicDiagnostic getDiagnosticGroup() {
		return new BasicDiagnostic(Activator.PLUGIN_ID, 0, "Patching settings", null);
	}
}
