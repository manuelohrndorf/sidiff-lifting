package org.sidiff.integration.preferences.patching.factory;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory;
import org.sidiff.integration.preferences.exceptions.InvalidSettingsException;
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

/**
 * 
 * Factory class for {@link org.sidiff.patching.settings.PatchingSettings}
 * @author Daniel Roedder, Robert Müller
 */
public class PatchingSettingsFactory extends DifferenceSettingsFactory {

	/**
	 * The {@link ExecutionMode} to be used
	 */
	protected ExecutionMode executionMode;

	/** 
	 * The {@link PatchMode} to be used
	 */
	protected PatchMode patchMode;

	/**
	 * Integer field for the minimum reliability value
	 */
	protected int minReliability;

	/**
	 * The {@link ValidationMode} to be used
	 */
	protected ValidationMode validationMode;

	/**
	 * The {@link ITransformationEngine} to be used
	 */
	protected ITransformationEngine transformationEngine;

	/**
	 * The {@link IModifiedDetector} to be used
	 */
	protected IModifiedDetector modifiedDetector;

	/**
	 * Boolean field for the use symbolic links setting
	 */
	protected boolean useSymbolicLinks;

	/**
	 * Boolean field for the use interactive patching setting
	 */
	protected boolean useInteractivePatching;

	/**
	 * Boolean field for the use interactive argument handling setting
	 */
	protected boolean useInteractiveArgumentHandling;

	/**
	 * @see org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory#getFeatureLevel()
	 */
	@Override
	public String getFeatureLevel() {
		return "Patching";
	}

	/**
	 * @see org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory#doSetFields(java.lang.String, org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void doSetFields(String documentType, IPreferenceStore store) {
		super.doSetFields(documentType, store);

		executionMode = ExecutionMode.valueOf(store.getString("executionMode"));

		patchMode = PatchMode.valueOf(store.getString("patchMode"));

		minReliability = store.getInt("minReliability");

		validationMode = ValidationMode.valueOf(store.getString("validationMode"));

		transformationEngine = TransformationEngineUtil.getTransformationEngine(store.getString(documentType + "transformationEngine"));

		modifiedDetector = ModifiedDetectorUtil.getModifiedDetector(store.getString(documentType + "modifiedDetector"));

		useInteractivePatching = store.getBoolean("useInteractivePatching");
	}

	/**
	 * @see org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory#getSettings()
	 */
	@Override
	public PatchingSettings getSettings() throws InvalidSettingsException {
		IMatcher matcher = createMatcher();
		PatchingSettings settings = new PatchingSettings(
				scope, validate, matcher, candidatesService,
				correspondencesService, createTechBuilder(), symbolicLinkHandler,
				createArgumentManager(matcher), createPatchInterruptHandler(),
				transformationEngine, modifiedDetector, executionMode, patchMode,
				minReliability, validationMode);
		if (!settings.validateSettings()) throw new InvalidSettingsException();
		settings.setMergeImports(mergeImports);
		return settings;
	}

	protected IPatchInterruptHandler createPatchInterruptHandler() {
		if(useInteractivePatching)
			return new DialogPatchInterruptHandler();
		return new BatchInterruptHandler();
	}

	protected IArgumentManager createArgumentManager(IMatcher matcher) {
		if(useSymbolicLinks)
			return new InteractiveSymblArgumentManager(symbolicLinkHandler);
		if(useInteractivePatching)
			return new InteractiveArgumentManager(matcher);
		return new BatchMatcherBasedArgumentManager(matcher);
	}
}
