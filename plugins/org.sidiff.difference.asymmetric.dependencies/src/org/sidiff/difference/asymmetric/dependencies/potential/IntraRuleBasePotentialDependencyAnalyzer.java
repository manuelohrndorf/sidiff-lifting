package org.sidiff.difference.asymmetric.dependencies.potential;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinUnitAnalysis;
import org.sidiff.difference.asymmetric.dependencies.potential.util.EmbeddedRule;
import org.sidiff.difference.asymmetric.dependencies.potential.util.PotentialRuleDependencies;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;

/**
 * Calculates the potential intra-dependencies in a single rulebases. 
 * 
 * @author Manuel Ohrndorf
 */
public class IntraRuleBasePotentialDependencyAnalyzer extends RuleBasePotentialDependencyAnalyzer {

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
		
		super(transientPDs, nonTransientPDs);
		this.rulebase = rulebase;
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
		this(rulebase, false, true);
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
		
		// Get embedded multi-rule parts if rule A is an amalgamation unit
		EmbeddedRule embeddedRuleA = new EmbeddedRule(editRuleA); 

		/*
		 * In (1) we compare each rule with itself. E.g. a rule 'add package to package'. 
		 * (We also compare each rule in an multi-rule with itself.)
		 */
		for (Rule ruleA : rulesA) {
			// (1) Compare each rule in the Module with itself.
			findRuleDependencies(ruleA, editRuleA, embeddedRuleA, ruleA, editRuleA, embeddedRuleA);
		}
		
		for (RuleBaseItem item : rulebase.getItems()) {
			EditRule editRuleB = item.getEditRule();

			// Get embedded multi-rule parts if rule B is an amalgamation unit
			EmbeddedRule embeddedRuleB = new EmbeddedRule(editRuleB); 

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
					findRuleDependencies(ruleA, editRuleA, embeddedRuleA, ruleB, editRuleB, embeddedRuleB);
					// (3) Compare all old rules B with the new rule A
					findRuleDependencies(ruleB, editRuleB, embeddedRuleB, ruleA, editRuleA, embeddedRuleA);
				}
			}
		}
	}

	@Override
	protected PotentialRuleDependencies findRuleDependencies(
			Rule predecessor, EditRule predecessorEditRule, EmbeddedRule embeddedPredecessor, 
			Rule successor, EditRule successorEditRule, EmbeddedRule embeddedSuccessor) {

		PotentialRuleDependencies potDeps = super.findRuleDependencies(
				predecessor, predecessorEditRule, embeddedPredecessor,
				successor, successorEditRule, embeddedSuccessor);
		
		rulebase.getPotentialNodeDependencies().addAll(potDeps.getPotentialNodeDependencies());
		rulebase.getPotentialEdgeDependencies().addAll(potDeps.getPotentialEdgeDependencies());
		rulebase.getPotentialAttributeDependencies().addAll(potDeps.getPotentialAttributeDependencies());
		
		return potDeps;
	}

	@Override
	protected Set<EPackage> getImports() {
		return getImports(rulebase.getDocumentTypes());
	}
}
