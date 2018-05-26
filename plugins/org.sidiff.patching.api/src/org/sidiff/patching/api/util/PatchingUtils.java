package org.sidiff.patching.api.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.settings.ValidationMode;

public class PatchingUtils {

	/**
	 * Returns the argument manager identified by the given key.
	 * 
	 * @param key
	 * @return
	 */
	public static IArgumentManager getArgumentManager(String key) {
		IArgumentManager manager = null;
		
		for(IArgumentManager m : getAllAvailableArgumentManagers()){
			if(m.getKey().equals(key)){
				manager = m;
				break;
			}
		}
		
		return manager;
	}
	
	/**
	 * Get all argument managers from the extension registry.
	 * 
	 * @return
	 */
	public static List<IArgumentManager> getAllAvailableArgumentManagers() {
		List<IArgumentManager> availableManagers = new ArrayList<IArgumentManager>();
		
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				IArgumentManager.EXTENSION_POINT_ID)) {
			try {
				IArgumentManager manager = (IArgumentManager) configurationElement.createExecutableExtension(IArgumentManager.EXTENSION_POINT_ATTRIBUTE);
				
				if (!availableManagers.contains(manager)) {
					availableManagers.add(manager);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		availableManagers.sort(new Comparator<IArgumentManager>() {

			@Override
			public int compare(IArgumentManager t1, IArgumentManager t2) {
				return t1.getName().compareTo(t2.getName());
			}
		});
		
		return availableManagers;
	}

	/**
	 * Converts
	 * <pre>org.sidiff.patching.<u>api</u>.settings.PatchingSettings</pre>
	 * to
	 * <pre>org.sidiff.patching.settings.PatchingSettings</pre>
	 * for compatibility.
	 * @param apiSettings patching settings
	 * @return copy of the settings
	 */
	public static PatchingSettings convertSettingsCompat(org.sidiff.patching.api.settings.PatchingSettings apiSettings) {
		PatchingSettings settings = new PatchingSettings();
		settings.setArgumentManager(apiSettings.getArgumentManager());
		settings.setBuildGraphPerRule(apiSettings.isBuildGraphPerRule());
		settings.setCalculateEditRuleMatch(apiSettings.isCalculateEditRuleMatch());
		settings.setCandidatesService(apiSettings.getCandidatesService());
		settings.setComparator(apiSettings.getComparator());
		settings.setCorrespondencesService(apiSettings.getCorrespondencesService());
		settings.setDetectSplitJoins(apiSettings.isDetectSplitJoins());
		settings.setExecutionMode(ExecutionMode.valueOf(apiSettings.getExecutionMode().name()));
		settings.setImports(apiSettings.getImports());
		settings.setInterruptHandler(apiSettings.getInterruptHandler());
		settings.setMatcher(apiSettings.getMatcher());
		settings.setMergeImports(apiSettings.isEnabled_MergeImports());
		settings.setMinReliability(apiSettings.getMinReliability());
		settings.setModifiedDetector(apiSettings.getModifiedDetector());
		settings.setNumberOfThreads(apiSettings.getNumberOfThreads());
		settings.setPatchMode(PatchMode.valueOf(apiSettings.getPatchMode().name()));
		settings.setRecognitionEngine(apiSettings.getRecognitionEngine());
		settings.setRecognitionEngineMode(apiSettings.getRecognitionEngineMode());
		settings.setRrSorter(apiSettings.getRrSorter());
		settings.setRuleBases(apiSettings.getRuleBases());
		settings.setRuleSetReduction(apiSettings.isRuleSetReduction());
		settings.setRulesPerThread(apiSettings.getRulesPerThread());
		settings.setScope(apiSettings.getScope());
		settings.setSerializeEditRuleMatch(apiSettings.isSerializeEditRuleMatch());
		settings.setSimilaritiesCalculationService(apiSettings.getSimilaritiesCalculationService());
		settings.setSimilaritiesService(apiSettings.getSimilaritiesService());
		settings.setSortRecognitionRuleNodes(apiSettings.isSortRecognitionRuleNodes());
		settings.setSymbolicLinkHandler(apiSettings.getSymbolicLinkHandler());
		settings.setTechBuilder(apiSettings.getTechBuilder());
		settings.setTransformationEngine(apiSettings.getTransformationEngine());
		settings.setUnmergeImports(apiSettings.isEnabled_UnmergeImports());
		settings.setUseThreadPool(apiSettings.isUseThreadPool());
		settings.setValidate(apiSettings.isValidate());
		settings.setValidationMode(ValidationMode.valueOf(apiSettings.getValidationMode().name()));
		return settings;
	}
}
