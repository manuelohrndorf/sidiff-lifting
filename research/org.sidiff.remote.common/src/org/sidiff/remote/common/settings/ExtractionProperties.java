package org.sidiff.remote.common.settings;

import java.io.Serializable;
import java.util.List;

public class ExtractionProperties implements Serializable {

	public static final String MERGE_IMPORTS = "Merge Imports";
	public static final String UNMERGE_IMPORTS = "Unmerge Imports";
	public static final String TECHNICAL_DIFFERENCE_BUILDER = "Technical Difference Builder";
	public static final String RECOGNITION_RULE_SORTER = "Recognition Rule Sorter";
	public static final String RULE_BASE = "Rule Base";
	
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

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(mergeImports.toString()).append(", ");
		b.append(unmergeImports.toString()).append(", ");
		b.append(technicalDifferenceBuilderProperties.toString()).append(", ");
		b.append(recognitionRuleSorterProperties.toString()).append(", ");
		b.append(ruleBaseProperties.toString());
		return b.toString();
	}
}
