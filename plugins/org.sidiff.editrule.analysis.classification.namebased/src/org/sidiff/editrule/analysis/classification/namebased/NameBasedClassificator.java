package org.sidiff.editrule.analysis.classification.namebased;

import org.sidiff.editrule.analysis.classification.IClassificator;
import org.sidiff.editrule.rulebase.EditRule;

public class NameBasedClassificator implements IClassificator {

	@Override
	public String createClassification(EditRule rule) {
		String ruleName = rule.getExecuteModule().getName().toUpperCase();
		
		if (ruleName.startsWith("CREATE") || ruleName.startsWith("ADD") || ruleName.startsWith("INSERT")) {
			return "INCREASE";
		} else if (ruleName.startsWith("DELETE") || ruleName.startsWith("REMOVE")) {
			return "REDUCE";
		} else if (ruleName.startsWith("MOVE") || ruleName.startsWith("CHANGE")) {
			return "STRUCTURAL CHANGE";
		} else if (ruleName.startsWith("SET") || ruleName.startsWith("UNSET")) {
			return "VALUE CHANGE";
		}
		return "UNKNOWN";
	}

	@Override
	public String getName() {
		return "Name Based Classificator";
	}

	@Override
	public String getKey() {
		return "NameBasedClassificator";
	}

	@Override
	public boolean canHandle(EditRule rule) {
		return true;
	}
}