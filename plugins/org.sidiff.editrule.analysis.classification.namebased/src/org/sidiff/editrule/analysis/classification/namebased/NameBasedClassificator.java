package org.sidiff.editrule.analysis.classification.namebased;

import org.sidiff.common.collections.Classifier;
import org.sidiff.editrule.analysis.classification.IClassificator;
import org.sidiff.editrule.rulebase.EditRule;

public class NameBasedClassificator implements IClassificator, Classifier<String,EditRule> {

	public static final int ID = 0;
	public static final String NAME = "Name Based Classificator";
	public static final String KEY = "Namebased";
	
	
	public NameBasedClassificator() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getClassificatorId() {
		return ID;
	}
	
	@Override
	public String createClassification(EditRule rule) {
	
		return classify(rule);
	}
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getKey() {
		return KEY;
	}
	
	@Override
	public boolean canHandle(EditRule rule) {
		return true;
	}

	@Override
	public String classify(EditRule rule) {
		String ruleName = rule.getExecuteModule().getName().toUpperCase();
		
		if (ruleName.startsWith("CREATE") || ruleName.startsWith("ADD") || ruleName.startsWith("INSERT")) {
			return "INCREASE";
		} else if (ruleName.startsWith("DELETE") || ruleName.startsWith("REMOVE")) {
			return "REDUCE";
		} else if (ruleName.startsWith("MOVE") || ruleName.startsWith("CHANGE")) {
			return "STRUCTURAL CHANGE";
		} else if (ruleName.startsWith("SET") || ruleName.startsWith("UNSET")) {
			return "VALUE CHANGE";
		} else
			return "UNKNOWN";
	}

	

}