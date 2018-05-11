package org.sidiff.integration.preferences.lifting.settingsadapter;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterLibrary;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.integration.preferences.interfaces.AbstractSettingsAdapter;
import org.sidiff.integration.preferences.lifting.Activator;

/**
 * 
 * @author Robert Müller
 *
 */
public class LiftingSettingsAdapter extends AbstractSettingsAdapter {

	public static String KEY_RULE_BASES(String documentType) {
		return "ruleBases[" + documentType + "]";
	}
	public static String KEY_RECOGNITION_RULE_SORTER(String documentType) {
		return "recognitionRuleSorter[" + documentType + "]";
	}
	public static final String KEY_RECOGNITION_ENGINE_MODE = "recognitionEngineMode";
	public static final String KEY_CALCULATE_EDIT_RULE_MATCH = "calculateEditRuleMatch";
	public static final String KEY_SERIALIZE_EDIT_RULE_MATCH = "serializeEditRuleMatch";
	public static final String KEY_USE_THREAD_POOL = "useThreadPool";
	public static final String KEY_NUMBER_OF_THREADS = "numberOfThreads";
	public static final String KEY_RULES_PER_THREAD = "rulesPerThread";
	public static final String KEY_SORT_RECOGNITION_RULE_NODES = "sortRecognitionRuleNodes";
	public static final String KEY_RULESET_REDUCTION = "rulesetReduction";
	public static final String KEY_BUILD_GRAPH_PER_RULE = "buildGraphPerRule";
	public static final String KEY_DETECT_SPLIT_JOINS = "detectSplitJoins";

	private Set<ILiftingRuleBase> ruleBases;
	private IRecognitionRuleSorter rrSorter;
	private RecognitionEngineMode recognitionEngineMode;
	private boolean calculateEditRuleMatch;
	private boolean serializeEditRuleMatch;
	private boolean useThreadPool;
	private int numberOfThreads;
	private int rulesPerThread;
	private boolean sortRecognitionRuleNodes;
	private boolean rulesetReduction;
	private boolean buildGraphPerRule;
	private boolean detectSplitJoins;

	@Override
	public boolean canAdapt(AbstractSettings settings) {
		return settings instanceof LiftingSettings;
	}

	@Override
	public void adapt(AbstractSettings settings) {
		LiftingSettings liftingSettings = (LiftingSettings)settings;
		liftingSettings.setRuleBases(ruleBases);
		liftingSettings.setRrSorter(rrSorter);
		if(recognitionEngineMode != null) {
			liftingSettings.setRecognitionEngineMode(recognitionEngineMode);
		}
		liftingSettings.setCalculateEditRuleMatch(calculateEditRuleMatch);
		liftingSettings.setSerializeEditRuleMatch(serializeEditRuleMatch);
		liftingSettings.setUseThreadPool(useThreadPool);
		liftingSettings.setNumberOfThreads(numberOfThreads);
		liftingSettings.setRulesPerThread(rulesPerThread);
		liftingSettings.setSortRecognitionRuleNodes(sortRecognitionRuleNodes);
		liftingSettings.setRuleSetReduction(rulesetReduction);
		liftingSettings.setBuildGraphPerRule(buildGraphPerRule);
		liftingSettings.setDetectSplitJoins(detectSplitJoins);
	}

	@Override
	public void load(IPreferenceStore store) {
		ruleBases = new HashSet<ILiftingRuleBase>();
		rrSorter = null;
		for(String documentType : getDocumentTypes()) {
			for(ILiftingRuleBase rbase : PipelineUtils.getAvailableRulebases(documentType)) {
				if(store.getBoolean(KEY_RULE_BASES(documentType) + ":" + rbase.getName())) {
					ruleBases.add(rbase);
				}
			}

			// the first recognition rule sorter is used
			if(rrSorter == null) {
				String key = store.getString(KEY_RECOGNITION_RULE_SORTER(documentType));
				rrSorter = RecognitionRuleSorterLibrary.getRecognitionRuleSorter(key);
				if(rrSorter == null) {
					addWarning("Recognition Rule Sorter with key '" + key + "' was not found.");
				}
			}
		}
		if(rrSorter == null) {
			rrSorter = RecognitionRuleSorterLibrary.getDefaultRecognitionRuleSorter(getDocumentTypes());
		}

		String recognitionEngineModeValue = store.getString(KEY_RECOGNITION_ENGINE_MODE);
		try {
			recognitionEngineMode = RecognitionEngineMode.valueOf(recognitionEngineModeValue);
		} catch (IllegalArgumentException e) {
			recognitionEngineMode = null;
			addWarning("Invalid value for Recognition Engine Mode: '" + recognitionEngineModeValue + "'", e);
		}

		calculateEditRuleMatch = store.getBoolean(KEY_CALCULATE_EDIT_RULE_MATCH);
		serializeEditRuleMatch = store.getBoolean(KEY_SERIALIZE_EDIT_RULE_MATCH);
		useThreadPool = store.getBoolean(KEY_USE_THREAD_POOL);

		String numberOfThreadsValue = store.getString(KEY_NUMBER_OF_THREADS);
		try {
			numberOfThreads = Integer.parseInt(numberOfThreadsValue);
			if(numberOfThreads < 1) {
				numberOfThreads = 1;
			}
		} catch (NumberFormatException e) {
			numberOfThreads = 10;
			addWarning("Invalid value for Number of Threads: '" + numberOfThreadsValue + "'", e);
		}

		String rulesPerThreadValue = store.getString(KEY_RULES_PER_THREAD);
		try {
			rulesPerThread = Integer.parseInt(rulesPerThreadValue);
			if(rulesPerThread < 1) {
				rulesPerThread = 1;
			}
		} catch (NumberFormatException e) {
			rulesPerThread = 10;
			addWarning("Invalid value for Rules per Thread: '" + rulesPerThreadValue + "'", e);
		}

		sortRecognitionRuleNodes = store.getBoolean(KEY_SORT_RECOGNITION_RULE_NODES);
		rulesetReduction = store.getBoolean(KEY_RULESET_REDUCTION);
		buildGraphPerRule = store.getBoolean(KEY_BUILD_GRAPH_PER_RULE);
		detectSplitJoins = store.getBoolean(KEY_DETECT_SPLIT_JOINS);
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_RECOGNITION_RULE_SORTER("http://www.eclipse.org/emf/2002/Ecore"), "EcoreRRSorter");
		store.setDefault(KEY_RECOGNITION_RULE_SORTER("http://www.eclipse.org/uml2/5.0.0/UML"), "GenericRRSorter");
		// TODO: these are not correct as the rulebases have no good permanently saveable value
		store.setDefault(KEY_RULE_BASES("http://www.eclipse.org/emf/2002/Ecore") + ":" + "Ecore Atomic", true);
		store.setDefault(KEY_RULE_BASES("http://www.eclipse.org/uml2/5.0.0/UML") + ":" + "UML_2v4_Atomic", true);

		store.setDefault(KEY_RECOGNITION_ENGINE_MODE, RecognitionEngineMode.LIFTING_AND_POST_PROCESSING.name());
		store.setDefault(KEY_CALCULATE_EDIT_RULE_MATCH, true);
		store.setDefault(KEY_SERIALIZE_EDIT_RULE_MATCH, true);
		store.setDefault(KEY_USE_THREAD_POOL, true);
		store.setDefault(KEY_NUMBER_OF_THREADS, 10);
		store.setDefault(KEY_RULES_PER_THREAD, 10);
		store.setDefault(KEY_SORT_RECOGNITION_RULE_NODES, true);
		store.setDefault(KEY_RULESET_REDUCTION, true);
		store.setDefault(KEY_BUILD_GRAPH_PER_RULE, true);
		store.setDefault(KEY_DETECT_SPLIT_JOINS, false);
	}

	@Override
	protected BasicDiagnostic getDiagnosticGroup() {
		return new BasicDiagnostic(Activator.PLUGIN_ID, 0, "Lifting settings", null);
	}
}
