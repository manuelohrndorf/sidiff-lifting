package org.sidiff.editrule.analysis.classification;

import org.sidiff.common.collections.Classifier;
import org.sidiff.difference.rulebase.EditRule;

public class NameBasedClassificator implements IClassificator, Classifier<String,EditRule> {

	public static final int ID = 0;
	public static final String NAME = "Name Based Classificator";
	public static final String KEY = "Namesbased";
	
	
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
		String ruleName = rule.getExecuteModule().getName();
		
		if (ruleName.startsWith("CREATE") || ruleName.startsWith("Create") || ruleName.startsWith("ADD") || ruleName.startsWith("Add") || ruleName.startsWith("INSERT") || ruleName.startsWith("Insert")) {
			return "INCREASE";
		} else if (ruleName.startsWith("DELETE") || ruleName.startsWith("Delete") || ruleName.startsWith("REMOVE") || ruleName.startsWith("Remove")) {
			return "REDUCE";
		} else if (ruleName.startsWith("MOVE") || ruleName.startsWith("Move") || ruleName.startsWith("CHANGE") || ruleName.startsWith("Change")) {
			return "STRUCTURAL CHANGE";
		} else if (ruleName.startsWith("SET") || ruleName.startsWith("Set")) {
			return "VALUE CHANGE";
		} else
			return "UNKNOWN";
	}

	

}