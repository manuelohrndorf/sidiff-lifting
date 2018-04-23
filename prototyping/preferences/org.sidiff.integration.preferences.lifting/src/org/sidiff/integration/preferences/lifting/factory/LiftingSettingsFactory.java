package org.sidiff.integration.preferences.lifting.factory;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterLibrary;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.integration.preferences.FeatureLevel;
import org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory;
import org.sidiff.integration.preferences.exceptions.InvalidSettingsException;

/**
 * 
 * Factory class for {@link org.sidiff.difference.lifting.api.settings.LiftingSettings}.
 * @author Daniel Roedder, cpietsch, Robert Müller
 */
public class LiftingSettingsFactory extends DifferenceSettingsFactory {

	/**
	 * The {@link Set} for the {@link ILiftingRuleBase}s
	 */
	protected Set<ILiftingRuleBase> ruleBases;

	/**
	 * The {@link Set} for all the {@link IRecognitionRuleSorter}s
	 */
	protected Set<IRecognitionRuleSorter> rrsorters;

	/**
	 * The {@link IRecognitionRuleSorter} to be used
	 */
	protected IRecognitionRuleSorter rrsorter;

	/**
	 * Boolean field for the calculate edit rule match setting
	 */
	protected boolean calculateEditRuleMatch;

	/**
	 * Boolean field for the serialize edit rule match setting
	 */
	protected boolean serializeEditRuleMatch;

	//Advanced Settings

	/**
	 * Boolean field for the use thread pool setting
	 */
	protected boolean useThreadPool;

	/**
	 * Integer field for the number of threads to be used
	 */
	protected int numberOfThreads;

	/**
	 * Integer field for the rules per thread setting
	 */
	protected int rulesPerThread;

	/**
	 * Boolean field for the sort recognition rule nodes setting
	 */
	protected boolean sortRecognitionRuleNodes;

	/**
	 * Boolean field for the ruleset reduction setting
	 */
	protected boolean rulesetReduction;

	/**
	 * Boolean setting for the build graph per rule setting
	 */
	protected boolean buildGraphPerRule;

	/**
	 * Boolean field for the detect split joins setting
	 */
	protected boolean detectSplitJoins;

	/**
	 * @see org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory#getFeatureLevel()
	 */
	@Override
	public FeatureLevel getFeatureLevel() {
		return FeatureLevel.LIFTING;
	}

	/**
	 * @see org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory#doSetFields(java.lang.String, org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void doSetFields(String documentType, IPreferenceStore store) {
		super.doSetFields(documentType, store);

		ruleBases = new HashSet<ILiftingRuleBase>();
		for (ILiftingRuleBase rbase : PipelineUtils.getAvailableRulebases(documentType)) {
			if (store.getBoolean(documentType + ":" + rbase.getName())) {
				ruleBases.add(rbase);
			}
		}

		Set<String> documentTypes = new HashSet<String>();
		rrsorters = PipelineUtils.getAvailableRecognitionRuleSorters(documentTypes);
		rrsorter = RecognitionRuleSorterLibrary.getDefaultRecognitionRuleSorter(documentTypes);
		for (IRecognitionRuleSorter sorter : rrsorters) {
			if (sorter.getKey().equals(store.getString(documentType.concat("recognitionRuleSorter")))) rrsorter = sorter;
		}
		
		calculateEditRuleMatch = store.getBoolean("calculateEditRuleMatch");
		serializeEditRuleMatch = store.getBoolean("serializeEditRuleMatch");

		//Advanced Settings
		useThreadPool = store.getBoolean("useThreadPool");
		numberOfThreads = store.getInt("numberOfThreads");
		rulesPerThread = store.getInt("rulesPerThread");
		sortRecognitionRuleNodes = store.getBoolean("sortRecognitionRuleNodes");
		rulesetReduction = store.getBoolean("rulesetReduction");
		buildGraphPerRule = store.getBoolean("buildGraphPerRule");
		detectSplitJoins = store.getBoolean("detectSplitJoins");
	}

	/**
	 * @see org.sidiff.integration.preferences.difference.factory.DifferenceSettingsFactory#getSettings()
	 */
	@Override
	public LiftingSettings getSettings() throws InvalidSettingsException {
		LiftingSettings settings = new LiftingSettings(
				scope, validate, createMatcher(), candidatesService,
				correspondencesService, createTechBuilder(), ruleBases,
				rrsorter, calculateEditRuleMatch, calculateEditRuleMatch);
		// TODO: why are the settings validated before the merge imports and advances settings are set?
		if (!settings.validateSettings()) throw new InvalidSettingsException();
		settings.setMergeImports(mergeImports);
		this.setAdvancedSettings(settings);
		return settings;
	}

	/**
	 * Sets the advanced settings fields in the specified settings object
	 * @param settings The settings object to be modified
	 */
	private void setAdvancedSettings(LiftingSettings settings) {
		settings.setUseThreadPool(useThreadPool);
		settings.setNumberOfThreads(numberOfThreads);
		settings.setRulesPerThread(rulesPerThread);
		settings.setSortRecognitionRuleNodes(sortRecognitionRuleNodes);
		settings.setRuleSetReduction(rulesetReduction);
		settings.setBuildGraphPerRule(buildGraphPerRule);
		settings.setDetectSplitJoins(detectSplitJoins);
	}
}
