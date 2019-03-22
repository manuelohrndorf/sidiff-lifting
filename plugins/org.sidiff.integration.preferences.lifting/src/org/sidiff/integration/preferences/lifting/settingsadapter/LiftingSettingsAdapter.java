package org.sidiff.integration.preferences.lifting.settingsadapter;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.emf.settings.ISettings;
import org.sidiff.common.util.StringListSerializer;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.integration.preferences.lifting.Activator;
import org.sidiff.integration.preferences.lifting.valueconverters.LiftingRuleBaseValueConverter;
import org.sidiff.integration.preferences.settingsadapter.AbstractSettingsAdapter;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

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
	public boolean canAdapt(ISettings settings) {
		return settings instanceof LiftingSettings;
	}

	@Override
	public void adapt(ISettings settings) {
		LiftingSettings liftingSettings = (LiftingSettings)settings;

		if(isConsidered(LiftingSettingsItem.RULEBASES)) {
			liftingSettings.setRuleBases(ruleBases);
		}
		if(isConsidered(LiftingSettingsItem.RECOGNITION_RULE_SORTER)) {
			liftingSettings.setRrSorter(rrSorter);
		}
		if(recognitionEngineMode != null && isConsidered(LiftingSettingsItem.RECOGNITION_ENGINE_MODE)) {
			liftingSettings.setRecognitionEngineMode(recognitionEngineMode);
		}
		if(isConsidered(LiftingSettingsItem.CALCULATE_EDIT_RULE_MATCH)) {
			liftingSettings.setCalculateEditRuleMatch(calculateEditRuleMatch);
		}
		if(isConsidered(LiftingSettingsItem.SERIALIZE_EDIT_RULE_MATCH)) {
			liftingSettings.setSerializeEditRuleMatch(serializeEditRuleMatch);
		}
		if(isConsidered(LiftingSettingsItem.USE_THREAD_POOL)) {
			liftingSettings.setUseThreadPool(useThreadPool);
		}
		if(isConsidered(LiftingSettingsItem.NUMBER_OF_THREADS)) {
			liftingSettings.setNumberOfThreads(numberOfThreads);
		}
		if(isConsidered(LiftingSettingsItem.RULES_PER_THREAD)) {
			liftingSettings.setRulesPerThread(rulesPerThread);
		}
		if(isConsidered(LiftingSettingsItem.SORT_RECOGNITIONRULE_NODES)) {
			liftingSettings.setSortRecognitionRuleNodes(sortRecognitionRuleNodes);
		}
		if(isConsidered(LiftingSettingsItem.RULE_SET_REDUCTION)) {
			liftingSettings.setRuleSetReduction(rulesetReduction);
		}
		if(isConsidered(LiftingSettingsItem.BUILD_GRAPH_PER_RULE)) {
			liftingSettings.setBuildGraphPerRule(buildGraphPerRule);
		}
		if(isConsidered(LiftingSettingsItem.DETECT_SPLIT_JOINS)) {
			liftingSettings.setDetectSplitJoins(detectSplitJoins);
		}
	}

	@Override
	public void load(IPreferenceStore store) {
		loadRulebases(store);
		loadRecognitionRuleSorter(store);
		loadRecognitionEngineMode(store);
		calculateEditRuleMatch = store.getBoolean(KEY_CALCULATE_EDIT_RULE_MATCH);
		serializeEditRuleMatch = store.getBoolean(KEY_SERIALIZE_EDIT_RULE_MATCH);
		useThreadPool = store.getBoolean(KEY_USE_THREAD_POOL);
		loadNumberOfThreads(store);
		loadRulesPerThread(store);
		sortRecognitionRuleNodes = store.getBoolean(KEY_SORT_RECOGNITION_RULE_NODES);
		rulesetReduction = store.getBoolean(KEY_RULESET_REDUCTION);
		buildGraphPerRule = store.getBoolean(KEY_BUILD_GRAPH_PER_RULE);
		detectSplitJoins = store.getBoolean(KEY_DETECT_SPLIT_JOINS);
	}

	protected void loadRulebases(IPreferenceStore store) {
		ruleBases = new HashSet<ILiftingRuleBase>();
		final IPreferenceValueConverter<ILiftingRuleBase> valueConverter = new LiftingRuleBaseValueConverter();
		for(String documentType : getDocumentTypes()) {
			List<String> ruleBasesKeys = StringListSerializer.DEFAULT.deserialize(store.getString(KEY_RULE_BASES(documentType)));
			for(ILiftingRuleBase rbase : PipelineUtils.getAvailableRulebases(Collections.singleton(documentType))) {
				if(ruleBasesKeys.contains(valueConverter.getValue(rbase))) {
					ruleBases.add(rbase);
				}
			}
		}
		if(ruleBases.isEmpty()) {
			addError("No Rule Bases were specified.");
		}
	}

	protected void loadRecognitionRuleSorter(IPreferenceStore store) {
		rrSorter = null;
		for(String documentType : getDocumentTypes()) {
			// the first recognition rule sorter is used
			if(rrSorter == null) {
				String key = store.getString(KEY_RECOGNITION_RULE_SORTER(documentType));
				if(!key.isEmpty()) {
					rrSorter = IRecognitionRuleSorter.MANAGER.getExtension(key).orElse(null);
					if(rrSorter == null) {
						addWarning("Recognition Rule Sorter with key '" + key + "' was not found.");
					}
				}
			}
		}
		if(rrSorter == null) {
			rrSorter = IRecognitionRuleSorter.MANAGER.getDefaultExtension(getDocumentTypes()).orElse(null);
			if(rrSorter == null) {
				addError("No default Recognition Rule Sorter was found.");
			}
		}
	}

	protected void loadRecognitionEngineMode(IPreferenceStore store) {
		String recognitionEngineModeValue = store.getString(KEY_RECOGNITION_ENGINE_MODE);
		try {
			recognitionEngineMode = RecognitionEngineMode.valueOf(recognitionEngineModeValue);
		} catch (IllegalArgumentException e) {
			recognitionEngineMode = null;
			addWarning("Invalid value for Recognition Engine Mode: '" + recognitionEngineModeValue + "'", e);
		}
	}

	protected void loadNumberOfThreads(IPreferenceStore store) {
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
	}

	protected void loadRulesPerThread(IPreferenceStore store) {
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
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_RECOGNITION_RULE_SORTER("http://www.eclipse.org/emf/2002/Ecore"), "EcoreRRSorter");
		store.setDefault(KEY_RECOGNITION_RULE_SORTER("http://www.eclipse.org/uml2/5.0.0/UML"), "GenericRRSorter");

		store.setDefault(KEY_RULE_BASES("http://www.eclipse.org/emf/2002/Ecore"),
				"org.sidiff.ecore.editrules.atomic;org.sidiff.ecore.editrules.complex");
		store.setDefault(KEY_RULE_BASES("http://www.eclipse.org/uml2/5.0.0/UML"),
				"org.sidiff.uml2v4.editrules.atomic;org.sidiff.uml2v4.editrules.complex");

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
