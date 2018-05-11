package org.sidiff.integration.preferences.patching.settingsadapter;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.integration.preferences.interfaces.AbstractSettingsAdapter;
import org.sidiff.integration.preferences.patching.Activator;
import org.sidiff.matcher.IMatcher;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.batch.arguments.BatchMatcherBasedArgumentManager;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.arguments.InteractiveArgumentManager;
import org.sidiff.patching.ui.arguments.InteractiveSymblArgumentManager;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

/**
 * 
 * @author Robert Müller
 *
 */
public class PatchingSettingsAdapter extends AbstractSettingsAdapter {

	public static String KEY_MODIFIED_DETECTOR(String documentType) {
		return "modifiedDetector[" + documentType + "]";
	}
	public static String KEY_TRANSFORMATION_ENGINE(String documentType) {
		return "transformationEngine[" + documentType + "]";
	}
	public static final String KEY_EXECUTION_MODE = "executionMode";
	public static final String KEY_PATCH_MODE = "patchMode";
	public static final String KEY_MIN_RELIABILITY = "minReliability";
	public static final String KEY_VALIDATION_MODE = "validationMode";
	public static final String KEY_USE_INTERACTIVE_PATCHING = "useInteractivePatching";
	public static final String KEY_SYMBOLIC_LINK_HANDLER = "symbolicLinkHandler";

	private ITransformationEngine transformationEngine;
	private IModifiedDetector modifiedDetector;
	private ExecutionMode executionMode;
	private PatchMode patchMode;
	private int minReliability;
	private ValidationMode validationMode;
	private boolean useInteractivePatching;
	private ISymbolicLinkHandler symbolicLinkHandler;

	@Override
	public boolean canAdapt(AbstractSettings settings) {
		return settings instanceof PatchingSettings;
	}

	@Override
	public void adapt(AbstractSettings settings) {
		PatchingSettings patchingSettings = (PatchingSettings)settings;
		if(transformationEngine != null) {
			patchingSettings.setTransformationEngine(transformationEngine);
		}
		if(modifiedDetector != null) {
			patchingSettings.setModifiedDetector(modifiedDetector);
		}
		if(executionMode != null) {
			patchingSettings.setExecutionMode(executionMode);
		}
		if(patchMode != null) {
			patchingSettings.setPatchMode(patchMode);
		}
		if(minReliability != -1) {
			patchingSettings.setMinReliability(minReliability);
		}
		if(validationMode != null) {
			patchingSettings.setValidationMode(validationMode);
		}

		patchingSettings.setInterruptHandler(createPatchInterruptHandler());

		if(patchingSettings.getMatcher() != null) {
			patchingSettings.setArgumentManager(createArgumentManager(patchingSettings.getMatcher()));
		} else {
			addError("Matcher is not specified. Cannot set ArgumentManager.");
		}
	}

	@Override
	public void load(IPreferenceStore store) {
		transformationEngine = null;
		modifiedDetector = null;
		for(String documentType : getDocumentTypes()) {
			// the first transformation engine is used
			if(transformationEngine == null) {
				String key = store.getString(KEY_TRANSFORMATION_ENGINE(documentType));
				transformationEngine = TransformationEngineUtil.getTransformationEngine(key);
				if(transformationEngine == null) {
					addWarning("Transformation Engine with key '" + key + "' was not found.");
				}
			}
			// the first modified detector is used
			if(modifiedDetector == null) {
				String key = store.getString(KEY_MODIFIED_DETECTOR(documentType));
				modifiedDetector = ModifiedDetectorUtil.getModifiedDetector(key);
				if(modifiedDetector == null) {
					addWarning("Modified Detector with key '" + key + "' was not found.");
				}
			}
		}
		if(transformationEngine == null) {
			addError("No Transformation Engine was specified.");
		}
		if(modifiedDetector == null) {
			addError("No Modified Detector was specified.");
		}

		String executionModeValue = store.getString(KEY_EXECUTION_MODE);
		try {
			executionMode = ExecutionMode.valueOf(executionModeValue);
		} catch (IllegalArgumentException e) {
			executionMode = null;
			addError("Invalid value for Execution Mode: '" + executionModeValue + "'", e);
		}

		String patchModeValue = store.getString(KEY_PATCH_MODE);
		try {
			patchMode = PatchMode.valueOf(patchModeValue);
		} catch (IllegalArgumentException e) {
			patchMode = null;
			addError("Invalid value for Patch Mode: '" + patchModeValue + "'", e);
		}

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

		String validationModeValue = store.getString(KEY_VALIDATION_MODE);
		try {
			validationMode = ValidationMode.valueOf(validationModeValue);
		} catch (IllegalArgumentException e) {
			validationMode = null;
			addError("Invalid value for Validation Mode: '" + validationModeValue + "'", e);
		}

		useInteractivePatching = store.getBoolean(KEY_USE_INTERACTIVE_PATCHING);

		String symbolicLinkHandlerKey = store.getString(KEY_SYMBOLIC_LINK_HANDLER);
		symbolicLinkHandler = SymbolicLinkHandlerUtil.getSymbolicLinkHandler(symbolicLinkHandlerKey);
		if(symbolicLinkHandler == null) {
			addError("Symbolic Link Handler with key '" + symbolicLinkHandlerKey + "' was not found.");
		}
	}

	private IPatchInterruptHandler createPatchInterruptHandler() {
		if(useInteractivePatching)
			return new DialogPatchInterruptHandler();
		return new BatchInterruptHandler();
	}

	private IArgumentManager createArgumentManager(IMatcher matcher) {
		if(symbolicLinkHandler != null)
			return new InteractiveSymblArgumentManager(symbolicLinkHandler);
		if(useInteractivePatching)
			return new InteractiveArgumentManager(matcher);
		return new BatchMatcherBasedArgumentManager(matcher);
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_MODIFIED_DETECTOR("http://www.eclipse.org/emf/2002/Ecore"),
				"org.sidiff.ecore.modifieddetector.EcoreModifiedDetector");
		store.setDefault(KEY_TRANSFORMATION_ENGINE("http://www.eclipse.org/emf/2002/Ecore"),
				"HenshinTransformationEngine");
		store.setDefault(KEY_TRANSFORMATION_ENGINE("http://www.eclipse.org/uml2/5.0.0/UML"),
				"HenshinTransformationEngine");
		store.setDefault(KEY_EXECUTION_MODE, ExecutionMode.INTERACTIVE.name());
		store.setDefault(KEY_PATCH_MODE, PatchMode.PATCHING.name());
		store.setDefault(KEY_MIN_RELIABILITY, -1);
		store.setDefault(KEY_VALIDATION_MODE, ValidationMode.MODEL_VALIDATION.name());
		store.setDefault(KEY_USE_INTERACTIVE_PATCHING, false);
		store.setDefault(KEY_SYMBOLIC_LINK_HANDLER, "NamedElementSymbolicLinkHandler");
	}

	@Override
	protected BasicDiagnostic getDiagnosticGroup() {
		return new BasicDiagnostic(Activator.PLUGIN_ID, 0, "Patching settings", null);
	}
}
