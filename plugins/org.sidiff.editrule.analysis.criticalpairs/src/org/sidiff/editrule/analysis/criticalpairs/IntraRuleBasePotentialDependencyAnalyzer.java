package org.sidiff.editrule.analysis.criticalpairs;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinUnitAnalysis;
import org.sidiff.common.henshin.view.ActionGraph;
import org.sidiff.editrule.analysis.criticalpairs.util.PotentialRuleDependencies;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;

/**
 * Calculates the potential intra-dependencies in a single rulebases. 
 * 
 * @author Manuel Ohrndorf
 */
public class IntraRuleBasePotentialDependencyAnalyzer extends PotentialDependencyAnalyzer {

	/**
	 * The rulebase to process.
	 */
	private RuleBase rulebase;

	/**
	 * Calculates the potential intra-dependencies in a single rulebases.
	 * 
	 * @param rulebase
	 *            The rulebase to process.
	 * @param transientPDs
	 *            <code>true</code> to calculate transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 * @param nonTransientPDs
	 *            <code>true</code> to calculate non transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 */
	public IntraRuleBasePotentialDependencyAnalyzer(RuleBase rulebase, 
			boolean transientPDs, boolean nonTransientPDs) {

		super(getImports(rulebase.getDocumentTypes()), transientPDs, nonTransientPDs);
		this.rulebase = Objects.requireNonNull(rulebase, "rulebase is null");
	}

	/**
	 * Calculates the potential intra-dependencies in a single rulebases.
	 * (Non-transient dependencies only.)
	 * 
	 * @param rulebase
	 *            The rulebase to process.
	 * @see IntraRuleBasePotentialDependencyAnalyzer
	 * 		#IntraRuleBasePotentialDependencyAnalyzer(RuleBase, boolean, boolean)
	 */
	public IntraRuleBasePotentialDependencyAnalyzer(RuleBase rulebase) {
		this(rulebase, true, true);
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
	public void findDependencies(EditRule editRuleA) {
		List<Rule> rulesA = HenshinUnitAnalysis.getRules(editRuleA.getExecuteMainUnit());

		/*
		 * In (1) we compare each rule with itself. E.g. a rule 'add package to package'. 
		 * (We also compare each rule in an multi-rule with itself.)
		 */
		for (Rule ruleA : rulesA) {
			// (1) Compare each rule in the Module with itself.
			findRuleDependencies(getActionGraph(ruleA), editRuleA, getActionGraph(ruleA), editRuleA);
		}

		for (RuleBaseItem item : rulebase.getItems()) {
			EditRule editRuleB = item.getEditRule();

			/*
			 * Do not (cross) compare all rules in a Module with all other rules
			 * in this Module. E.g. a kernel- and a multi-rule. (Currently the
			 * only Modules with more than one rule are multi-rules.)
			 */
			if (editRuleA == editRuleB) {
				continue;
			}

			// Compare all rules in both transformation systems
			List<Rule> rulesB = HenshinUnitAnalysis.getRules(editRuleB.getExecuteMainUnit());

			for (Rule ruleA : rulesA) {
				for (Rule ruleB : rulesB) {
					// (2) Compare the new rule A with all old rules B
					findRuleDependencies(getActionGraph(ruleA), editRuleA, getActionGraph(ruleB), editRuleB);
					// (3) Compare all old rules B with the new rule A
					findRuleDependencies(getActionGraph(ruleB), editRuleB, getActionGraph(ruleA), editRuleA);
				}
			}
		}
	}

	@Override
	protected PotentialRuleDependencies findRuleDependencies(
			ActionGraph predecessor, EditRule predecessorEditRule, 
			ActionGraph successor, EditRule successorEditRule) {

		PotentialRuleDependencies potDeps = super.findRuleDependencies(
				predecessor, predecessorEditRule,
				successor, successorEditRule);

		rulebase.getPotentialNodeDependencies().addAll(potDeps.getPotentialNodeDependencies());
		rulebase.getPotentialEdgeDependencies().addAll(potDeps.getPotentialEdgeDependencies());
		rulebase.getPotentialDanglingEdgeDependencies().addAll(potDeps.getPotentialDanglingEdgeDependencies());
		rulebase.getPotentialAttributeDependencies().addAll(potDeps.getPotentialAttributeDependencies());

		return potDeps;
	}
}
