package org.sidiff.editrule.rulebase.project.runtime.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.project.runtime.library.RuleBaseProjectLibrary;
import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;
import org.sidiff.editrule.rulebase.view.editrule.IEditRuleBase;

/**
 * Access all registered rulebase projects.
 */
public class RuleBaseProjectUtil {
	
	/**
	 * Resolve an EditRule by its name. We lookup in all rulebases which are
	 * suitable for the given document types.
	 * 
	 * @param documentTypes
	 *            The document (metamodel) types of the edit rule.
	 * @param editRuleName
	 *            The identifying name of the edit rule.
	 * @return The edit rule with given name.
	 * @throws NoSuchElementException if no edit rule is found
	 */
	public static EditRule resolveEditRule(Set<String> documentTypes, String editRuleName) throws NoSuchElementException {
		return RuleBaseProjectLibrary.getRuleBases(documentTypes, IEditRuleBase.TYPE)
			.stream()
			.map(rulebase -> rulebase.getEditRule(editRuleName))
			.filter(Objects::nonNull)
			.findFirst().orElseThrow(NoSuchElementException::new);
	}

	/**
	 * Resolves the RuleBase that contains the given EditRule.
	 * @param editRule the edit rule
	 * @param viewType the type of the rule base
	 * @return rulebase that contains the edit rule
	 * @throws NoSuchElementException if no rulebase is found
	 */
	public static <R extends IBasicRuleBase> R resolveIEditRuleBase(EditRule editRule, Class<R> viewType) {
		Set<String> documentTypes = editRule.getExecuteModule().getImports().stream().map(EPackage::getNsURI).collect(Collectors.toSet());
		return RuleBaseProjectLibrary.getRuleBases(documentTypes, viewType)
				.stream()
				.filter(editRuleBase -> editRuleBase.getRuleBase().getEditRules().contains(editRule))
				.findFirst().orElseThrow(NoSuchElementException::new);
	}
}
