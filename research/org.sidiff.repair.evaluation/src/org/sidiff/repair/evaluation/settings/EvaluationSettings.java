package org.sidiff.repair.evaluation.settings;

import org.sidiff.difference.technical.api.settings.DifferenceSettings;

public class EvaluationSettings {
	
	private String history_name;
	
	private String[] fileFilters;
	
	private DifferenceSettings differenceSettings;
	

	public EvaluationSettings(String history_name, String[] fileFilters, DifferenceSettings differenceSettings) {
		super();
		this.history_name = history_name;
		this.fileFilters = fileFilters;
		this.differenceSettings = differenceSettings;
	}

	public String getHistory_name() {
		return history_name;
	}

	public String[] getFileFilters() {
		return fileFilters;
	}

	public DifferenceSettings getDifferenceSettings() {
		return differenceSettings;
	}
}
