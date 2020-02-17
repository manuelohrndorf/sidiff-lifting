package org.sidiff.editrule.analysis.criticalpairs;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinUnitAnalysis;
import org.sidiff.common.henshin.view.ActionGraph;
import org.sidiff.editrule.analysis.criticalpairs.util.PotentialRuleConflicts;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;

/**
 * Calculates the potential intra-conflicts in a single rulebases. 
 * 
 * @author Manuel Ohrndorf, cpietsch
 */
public class IntraRuleBasePotentialConflictAnalyzer extends RuleBasePotentialConflictAnalyzer {

	/**
	 * The rulebase to process.
	 */
	private RuleBase rulebase;

	/**
	 * Calculates the potential intra-conflicts in a single rulebases.
	 * 
	 * @param rulebase
	 *            The rulebase to process.
	 */
	public IntraRuleBasePotentialConflictAnalyzer(RuleBase rulebase) {
		
		super();
		this.rulebase = rulebase;
	}

	/**
	 * Calculates all potential dependencies caused by adding edit rule A to the
	 * rulebase. The potential dependences are added directly to the rulebase.
	 * It is unimportant if edit rule A was already added to the rulebase or if
	 * it will be added later.
	 * 
	 * @param editRuleA
	 *            The new edit rule in the rulebase.
	 */
	public void findConflicts(EditRule editRuleA) {
		List<Rule> rulesA = HenshinUnitAnalysis.getRules(editRuleA.getExecuteMainUnit());

		/*
		 * In (1) we compare each rule with itself. E.g. a rule 'add package to package'. 
		 * (We also compare each rule in an multi-rule with itself.)
		 */
		for (Rule ruleA : rulesA) {
			// (1) Compare each rule in the Module with itself.
			findRuleConflicts(getActionGraph(ruleA), editRuleA, getActionGraph(ruleA), editRuleA);
		}
		
		for (RuleBaseItem item : rulebase.getItems()) {
			EditRule editRuleB = item.getEditRule();

			/*
			 * Do not (cross) compare all rules in a Module with all other rules
			 * in this Module. E.g. a kernel- and a multi-rule. (Currently the
			 * only Modules with more than one rule are multi-rules.)
			 */
			if (editRuleA == editRuleB)
				continue;

			// Compare all rules in both transformation systems
			List<Rule> rulesB = HenshinUnitAnalysis.getRules(editRuleB.getExecuteMainUnit());

			for (Rule ruleA : rulesA) {
				for (Rule ruleB : rulesB) {
					// (2) Compare the new rule A with all old rules B
					findRuleConflicts(getActionGraph(ruleA), editRuleA, getActionGraph(ruleB), editRuleB);
					// (3) Compare all old rules B with the new rule A
					findRuleConflicts(getActionGraph(ruleB), editRuleB, getActionGraph(ruleA), editRuleA);
				}
			}
		}
	}

	@Override
	protected PotentialRuleConflicts findRuleConflicts(
			ActionGraph predecessor, EditRule predecessorEditRule, 
			ActionGraph successor, EditRule successorEditRule) {

		PotentialRuleConflicts potConss = super.findRuleConflicts(
				predecessor, predecessorEditRule,
				successor, successorEditRule);
		
		rulebase.getPotentialNodeConflicts().addAll(potConss.getPotentialNodeConflicts());
		rulebase.getPotentialEdgeConflicts().addAll(potConss.getPotentialEdgeConflicts());
		rulebase.getPotentialAttributeConflicts().addAll(potConss.getPotentialAttributeConflicts());
		
		return potConss;
	}

	@Override
	protected Set<EPackage> getImports() {
		return getImports(rulebase.getDocumentTypes());
	}
}
