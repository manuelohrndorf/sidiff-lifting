package org.sidiff.remote.common.settings;

import java.io.Serializable;
import java.util.List;

public class ExtractionProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3435023314280625862L;

	private RemoteApplicationProperty<Boolean> mergeImports;
	
	private RemoteApplicationProperty<Boolean> unmergeImports;
	
	private List<RemoteApplicationProperty<String>> technicalDifferenceBuilderProperties;
	
	private List<RemoteApplicationProperty<String>> recognitionRuleSorterProperties;
	
	private List<RemoteApplicationProperty<String>> ruleBaseProperties;
	
	public ExtractionProperties(RemoteApplicationProperty<Boolean> mergeImports,
			RemoteApplicationProperty<Boolean> unmergeImports,
			List<RemoteApplicationProperty<String>> technicalDifferenceBuilderProperties,
			List<RemoteApplicationProperty<String>> recognitionRuleSorterProperties,
			List<RemoteApplicationProperty<String>> ruleBaseProperties) {
		super();
		this.mergeImports = mergeImports;
		this.unmergeImports = unmergeImports;
		this.technicalDifferenceBuilderProperties = technicalDifferenceBuilderProperties;
		this.recognitionRuleSorterProperties = recognitionRuleSorterProperties;
		this.ruleBaseProperties = ruleBaseProperties;
	}

	public RemoteApplicationProperty<Boolean> getMergeImports() {
		return mergeImports;
	}
	
	public RemoteApplicationProperty<Boolean> getUnmergeImports() {
		return unmergeImports;
	}
	
	public List<RemoteApplicationProperty<String>> getTechnicalDifferenceBuilderProperties() {
		return technicalDifferenceBuilderProperties;
	}
	
	public List<RemoteApplicationProperty<String>> getRecognitionRuleSorterProperties() {
		return recognitionRuleSorterProperties;
	}
	
	public List<RemoteApplicationProperty<String>> getRuleBaseProperties() {
		return ruleBaseProperties;
	}
}
