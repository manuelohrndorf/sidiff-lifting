package org.sidiff.integration.preferences.patching.settingsadapter;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.integration.preferences.interfaces.AbstractSettingsAdapter;
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
		patchingSettings.setTransformationEngine(transformationEngine);
		patchingSettings.setModifiedDetector(modifiedDetector);
		patchingSettings.setExecutionMode(executionMode);
		patchingSettings.setPatchMode(patchMode);
		patchingSettings.setMinReliability(minReliability);
		patchingSettings.setValidationMode(validationMode);
		patchingSettings.setInterruptHandler(createPatchInterruptHandler());
		patchingSettings.setArgumentManager(createArgumentManager(patchingSettings.getMatcher()));
	}

	@Override
	public void load(IPreferenceStore store) {
		for(String documentType : getDocumentTypes()) {
			// the first transformation engine is used
			if(transformationEngine == null) {
				transformationEngine = TransformationEngineUtil.getTransformationEngine(store.getString(KEY_TRANSFORMATION_ENGINE(documentType)));
			}
			// the first modified detector is used
			if(modifiedDetector == null) {
				modifiedDetector = ModifiedDetectorUtil.getModifiedDetector(store.getString(KEY_MODIFIED_DETECTOR(documentType)));
			}
		}
		executionMode = ExecutionMode.valueOf(store.getString(KEY_EXECUTION_MODE));
		patchMode = PatchMode.valueOf(store.getString(KEY_PATCH_MODE));
		minReliability = store.getInt(KEY_MIN_RELIABILITY);
		validationMode = ValidationMode.valueOf(store.getString(KEY_VALIDATION_MODE));
		useInteractivePatching = store.getBoolean(KEY_USE_INTERACTIVE_PATCHING);
		symbolicLinkHandler = SymbolicLinkHandlerUtil.getSymbolicLinkHandler(store.getString(KEY_SYMBOLIC_LINK_HANDLER));
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
		store.setDefault(KEY_MIN_RELIABILITY, 0);
		store.setDefault(KEY_VALIDATION_MODE, ValidationMode.MODEL_VALIDATION.name());
		store.setDefault(KEY_USE_INTERACTIVE_PATCHING, false);
		store.setDefault(KEY_SYMBOLIC_LINK_HANDLER, "NamedElementSymbolicLinkHandler");
	}
}
