package org.sidiff.mutation.testdriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.rulebase.util.RuleBaseUtil;


public class EditRuleUtil {
	
	private static Map<String, List<EditRule>> editRules;
	
	public static Map<String, List<EditRule>> getAvailableEditRules(String docType) {
		if (editRules == null) {
			editRules = new HashMap<String, List<EditRule>>();
			Set<IRuleBase> rulebases = RuleBaseUtil.getAvailableRulebases(docType);
			for (IRuleBase rb : rulebases) {
				List<EditRule> rules = new ArrayList<EditRule>();
				rules.addAll(rb.getActiveEditRules());
				editRules.put(rb.getName().trim(), Collections.unmodifiableList(rules));
			}
		}

		return editRules;
	}

	public static EditRule getEditRule(String docType, String name) {
		for (String opKind : getAvailableEditRules(docType).keySet()) {
			for (EditRule er : getAvailableEditRules(docType).get(opKind)) {
				if (er.getExecuteModule().getName().equals(name)) {
					return er;
				}
			}
		}

		return null;
	}
}
