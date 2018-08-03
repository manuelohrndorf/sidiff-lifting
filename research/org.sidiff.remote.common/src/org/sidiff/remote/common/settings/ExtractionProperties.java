package org.sidiff.remote.common.settings;

import java.io.Serializable;
import java.util.List;

public class ExtractionProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3435023314280625862L;

	private SingleSelectionRemoteApplicationProperty<Boolean> mergeImports;
	
	private SingleSelectionRemoteApplicationProperty<Boolean> unmergeImports;
	
	private List<MultiSelectionRemoteApplicationProperty<String>> technicalDifferenceBuilderProperties;
	
	private List<SingleSelectionRemoteApplicationProperty<String>> recognitionRuleSorterProperties;
	
	private List<MultiSelectionRemoteApplicationProperty<String>> ruleBaseProperties;
	
	public ExtractionProperties(SingleSelectionRemoteApplicationProperty<Boolean> mergeImports,
			SingleSelectionRemoteApplicationProperty<Boolean> unmergeImports,
			List<MultiSelectionRemoteApplicationProperty<String>> technicalDifferenceBuilderProperties,
			List<SingleSelectionRemoteApplicationProperty<String>> recognitionRuleSorterProperties,
			List<MultiSelectionRemoteApplicationProperty<String>> ruleBaseProperties) {
		super();
		this.mergeImports = mergeImports;
		this.unmergeImports = unmergeImports;
		this.technicalDifferenceBuilderProperties = technicalDifferenceBuilderProperties;
		this.recognitionRuleSorterProperties = recognitionRuleSorterProperties;
		this.ruleBaseProperties = ruleBaseProperties;
	}

	public SingleSelectionRemoteApplicationProperty<Boolean> getMergeImports() {
		return mergeImports;
	}
	
	public SingleSelectionRemoteApplicationProperty<Boolean> getUnmergeImports() {
		return unmergeImports;
	}
	
	public List<MultiSelectionRemoteApplicationProperty<String>> getTechnicalDifferenceBuilderProperties() {
		return technicalDifferenceBuilderProperties;
	}
	
	public List<SingleSelectionRemoteApplicationProperty<String>> getRecognitionRuleSorterProperties() {
		return recognitionRuleSorterProperties;
	}
	
	public List<MultiSelectionRemoteApplicationProperty<String>> getRuleBaseProperties() {
		return ruleBaseProperties;
	}
}
