package org.sidiff.editrule.rulebase.project.runtime.util;

import java.util.HashSet;
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
	
	public static <R extends IBasicRuleBase> R resolveIEditRuleBase(EditRule editRule, Class<R> viewType){
		Set<String> documentTypes = editRule.getExecuteModule().getImports().stream().map(EPackage::getNsURI).collect(Collectors.toSet());
		for(R iEditRuleBase : RuleBaseProjectLibrary.getRuleBases(documentTypes, viewType)){
			if(iEditRuleBase.getRuleBase().getEditRules().contains(editRule)){
				return iEditRuleBase;
			}
		}
		return null;
//		forEach(ePackage -> documentTypes.add(ePackage.getNsURI()));
	}
}
