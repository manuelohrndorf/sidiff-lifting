package org.sidiff.editrule.rulebase.project.runtime.util;

import java.util.Set;

import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.project.runtime.library.RuleBaseProjectLibrary;
import org.sidiff.editrule.rulebase.type.IEditRuleBase;

/**
 * Access all registered rulebase projects.
 */
public class RuleBaseProjectUtil {
	
	/**
	 * Resolve an EditRule by its name. We lookup in all rulebases which are
	 * suitable for the given document type.
	 * 
	 * @param documentType
	 *            The document (metamodel) type of the edit rule.
	 * @param editRuleName
	 *            The identifying name of the edit rule.
	 * @return The edit rule with given name.
	 */
	public static EditRule resolveEditRule(Set<String> documentTypes, String editRuleName) {
		
		for (IEditRuleBase iRulebase : RuleBaseProjectLibrary.getRuleBases(documentTypes, IEditRuleBase.TYPE)) {
			EditRule editRule = iRulebase.getEditRule(editRuleName);
			
			if (editRule != null) {
				return editRule;
			}
		}

		return null;
	}
}
