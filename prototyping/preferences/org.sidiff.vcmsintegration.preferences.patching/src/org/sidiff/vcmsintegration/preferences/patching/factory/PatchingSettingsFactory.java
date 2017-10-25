package org.sidiff.vcmsintegration.preferences.patching.factory;

import javax.sound.midi.Patch;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.difference.technical.IncrementalTechnicalDifferenceBuilder;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.patching.batch.arguments.BatchMatcherBasedArgumentManager;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.arguments.InteractiveArgumentManager;
import org.sidiff.patching.ui.arguments.InteractiveSymblArgumentManager;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.sidiff.vcmsintegration.preferences.difference.factory.DifferenceSettingsFactory;
import org.sidiff.vcmsintegration.preferences.exceptions.InvalidSettingsException;

/**
 * 
 * Factory class for {@link org.sidiff.patching.settings.PatchingSettings}
 * @author Daniel Roedder
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
	 * @see org.sidiff.vcmsintegration.preferences.difference.factory.DifferenceSettingsFactory#getFeatureLevel()
	 */
	@Override
	public String getFeatureLevel() {
		return "Patching";
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.difference.factory.DifferenceSettingsFactory#doSetFields(java.lang.String, org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void doSetFields(String documentType, IPreferenceStore store) {
		super.doSetFields(documentType, store);
		
		executionMode = ExecutionMode.valueOf(store.getString("executionMode"));
		
		patchMode = PatchMode.valueOf(store.getString("patchMode"));
		
		minReliability = store.getInt("minReliability");
		
		validationMode = ValidationMode.valueOf(store.getString("validationMode"));
		
		
		//FIXME Abfrage der TEngine nach Key ist noch nicht möglich!
		for (ITransformationEngine engine : TransformationEngineUtil.getAvailableTransformationEngines(documentType)) {
			if (engine.getKey().equals(store.getString(documentType.concat("transformationEngine")))) {
				transformationEngine = engine;
			}
		}
		
		
		modifiedDetector = ModifiedDetectorUtil.getModifiedDetector(store.getString(documentType.concat("modifiedDetector")));
		
		
		useInteractivePatching = store.getBoolean("useInteractivePatching");

		
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.difference.factory.DifferenceSettingsFactory#getSettings()
	 */
	@Override
	public PatchingSettings getSettings() throws InvalidSettingsException {
		
		PatchingSettings settings;
		
		if (matcherList.size() == 1) {
			
			if (technicalDifferenceBuilderList.size() == 1) {
				
						
				if (useSymbolicLinks) {
					if (useInteractivePatching) {
						settings = new PatchingSettings(scope, validate, matcherList.get(0), candidatesService,
								correspondencesService, technicalDifferenceBuilderList.get(0), symbolicLinkHandler,
								new InteractiveSymblArgumentManager(symbolicLinkHandler), new DialogPatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
					
					else {
						settings = new PatchingSettings(scope, validate, matcherList.get(0), candidatesService,
								correspondencesService, technicalDifferenceBuilderList.get(0), symbolicLinkHandler,
								new InteractiveSymblArgumentManager(symbolicLinkHandler), new BatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
				}
				else {
					if (useInteractivePatching) {
						settings = new PatchingSettings(scope, validate, matcherList.get(0), candidatesService,
								correspondencesService, technicalDifferenceBuilderList.get(0), symbolicLinkHandler,
								new InteractiveArgumentManager(matcherList.get(0)), new DialogPatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
					
					else {
						settings = new PatchingSettings(scope, validate, matcherList.get(0), candidatesService,
								correspondencesService, technicalDifferenceBuilderList.get(0), symbolicLinkHandler,
								new BatchMatcherBasedArgumentManager(matcherList.get(0)), new BatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
				}
			}
			
			else {
				if (useSymbolicLinks) {
					
					if (useInteractivePatching) {
						settings = new PatchingSettings(scope, validate, matcherList.get(0), candidatesService,
								correspondencesService, new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList), symbolicLinkHandler,
								new InteractiveSymblArgumentManager(symbolicLinkHandler), new DialogPatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
					
					else {
						settings = new PatchingSettings(scope, validate, matcherList.get(0), candidatesService,
								correspondencesService, new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList), symbolicLinkHandler,
								new InteractiveSymblArgumentManager(symbolicLinkHandler), new BatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
				}
				else {
					if (useInteractivePatching) {
						settings = new PatchingSettings(scope, validate, matcherList.get(0), candidatesService,
								correspondencesService, new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList), symbolicLinkHandler,
								new InteractiveArgumentManager(matcherList.get(0)), new DialogPatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
					
					else {
						settings = new PatchingSettings(scope, validate, matcherList.get(0), candidatesService,
								correspondencesService, new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList), symbolicLinkHandler,
								new BatchMatcherBasedArgumentManager(matcherList.get(0)), new BatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
				}
				
			}
			
			
		}
		
		else {
			
			if (technicalDifferenceBuilderList.size() == 1) {
				IncrementalMatcher incMatcher = new IncrementalMatcher(matcherList);
				if (useSymbolicLinks) {
					
					if (useInteractivePatching) {
						settings = new PatchingSettings(scope, validate, incMatcher, candidatesService,
								correspondencesService, technicalDifferenceBuilderList.get(0), symbolicLinkHandler,
								new InteractiveSymblArgumentManager(symbolicLinkHandler), new DialogPatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
					
					else {
						settings = new PatchingSettings(scope, validate, incMatcher, candidatesService,
								correspondencesService, technicalDifferenceBuilderList.get(0), symbolicLinkHandler,
								new InteractiveSymblArgumentManager(symbolicLinkHandler), new BatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
					
					
				}
				else {
					if (useInteractivePatching) {
						settings = new PatchingSettings(scope, validate, incMatcher, candidatesService,
								correspondencesService, technicalDifferenceBuilderList.get(0), symbolicLinkHandler,
								new InteractiveArgumentManager(matcherList.get(0)), new DialogPatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
					
					else {
						settings = new PatchingSettings(scope, validate, incMatcher, candidatesService,
								correspondencesService, technicalDifferenceBuilderList.get(0), symbolicLinkHandler,
								new BatchMatcherBasedArgumentManager(matcherList.get(0)), new BatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
				}
					
					
			}
			
			else {
				IncrementalMatcher incMatcher = new IncrementalMatcher(matcherList);
				
				if (useSymbolicLinks) {
					
					if (useInteractivePatching) {
						settings = new PatchingSettings(scope, validate, incMatcher, candidatesService,
								correspondencesService, new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList), symbolicLinkHandler,
								new InteractiveSymblArgumentManager(symbolicLinkHandler), new DialogPatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
					
					else {
						settings = new PatchingSettings(scope, validate, incMatcher, candidatesService,
								correspondencesService, new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList), symbolicLinkHandler,
								new InteractiveSymblArgumentManager(symbolicLinkHandler), new BatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
				}
				else {
					if (useInteractivePatching) {
						settings = new PatchingSettings(scope, validate, incMatcher, candidatesService,
								correspondencesService, new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList), symbolicLinkHandler,
								new InteractiveArgumentManager(matcherList.get(0)), new DialogPatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
					
					else {
						settings = new PatchingSettings(scope, validate, incMatcher, candidatesService,
								correspondencesService, new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilderList), symbolicLinkHandler,
								new BatchMatcherBasedArgumentManager(matcherList.get(0)), new BatchInterruptHandler(), transformationEngine, modifiedDetector, executionMode, patchMode,
								minReliability, validationMode);
						
						if (!settings.validateSettings()) throw new InvalidSettingsException();
						settings.setMergeImports(mergeImports);
						return settings;
					}
				}
			}
		}
		
		
	}

}
