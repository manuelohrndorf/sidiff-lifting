package org.sidiff.difference.asymmetric.dependencies.potential;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.difference.asymmetric.dependencies.potential.util.EmbeddedRule;
import org.sidiff.difference.asymmetric.dependencies.potential.util.PotentialRuleDependencies;
import org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngine;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.PotentialAttributeDependency;
import org.sidiff.difference.rulebase.PotentialDependency;
import org.sidiff.difference.rulebase.PotentialEdgeDependency;
import org.sidiff.difference.rulebase.PotentialNodeDependency;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.extension.IRuleBase;

public class CrossOverPotentialDependencyAnalyzer extends PotentialDependencyAnalyzer {

	private Collection<String> documentTypes;
	private Map<EditRule, PotentialRuleDependencies> potDeps;

	// TODO(MO): Optimierung: Hashing von Node Typen je Regel!?

	/**
	 * When calculating actual dependencies on a given symmetric difference
	 * which consists of SemanticChangeSets, most likely this constructor will
	 * be the right choice. it is optimized as only cross over dependencies
	 * between EditRules which have been actually recognized will be computed.
	 * Please note that the documentType is given implicitly by the
	 * symmetricDifference in this case.
	 * 
	 * @param engine
	 *            The engine that has been used to lift the symmetric
	 *            difference.
	 */
	public CrossOverPotentialDependencyAnalyzer(RecognitionEngine engine) {
		// set documentTypes
		this.documentTypes = new HashSet<String>();
		for (IRuleBase rb : engine.getLiftingSettings().getRuleBases()) {
			documentTypes.addAll(rb.getDocumentTypes());
		}
		
		// Consider active rules only
		Set<EditRule> editRules = engine.getEditRule2SCS().keySet();

		// Calculate
		calculate(editRules);
	}

	public CrossOverPotentialDependencyAnalyzer(Collection<String> documentTypes, Set<EditRule> editRules) {
		// set documentTypes
		this.documentTypes = documentTypes;
		
		// Calculate
		calculate(editRules);
	}
	
	private void calculate(Set<EditRule> editRules) {
		potDeps = new HashMap<EditRule, PotentialRuleDependencies>();

		// Get embedded multi-rule nodes if the rule is an amalgamation unit
		Map<EditRule, EmbeddedRule> embeddedRules = new HashMap<EditRule, EmbeddedRule>();

		for (EditRule editRule : editRules) {
			EmbeddedRule embeddedRule = new EmbeddedRule(editRule); 
			
			if (!embeddedRule.isEmpty()) {
				embeddedRules.put(editRule, embeddedRule);
			}
		}

		// Calculate potential dependencies
		for (EditRule editRuleA : editRules) {
			RuleBase ruleBaseA = editRuleA.getRuleBaseItem().getRuleBase();
			List<Rule> rulesA = HenshinModuleAnalysis.getAllRules(editRuleA.getExecuteModule());

			for (EditRule editRuleB : editRules) {
				RuleBase ruleBaseB = editRuleB.getRuleBaseItem().getRuleBase();
				List<Rule> rulesB = HenshinModuleAnalysis.getAllRules(editRuleB.getExecuteModule());
				
				// If rules are not in the same rule base
				// (note that this implies "editRuleA != editRuleB")
				if (ruleBaseA != ruleBaseB) {
					for (Rule ruleA : rulesA) {
						for (Rule ruleB : rulesB) {
							// (1) Compare rule A with rules B
							findRuleDependencies(ruleA, editRuleA, embeddedRules.get(editRuleA), ruleB, editRuleB, embeddedRules.get(editRuleB));
						}
					}
				}
			}
		}
	}

	/**
	 * @return <code>true</code> if there are at least two rules from different rule bases; <code>false</code> otherwise.
	 */
	public boolean isNecessary() {

		/*
		 * If there are only matched edit rules from one rule base then
		 * potDeps.size() will be 0. (ruleBaseA != ruleBaseB) 
		 * 
		 * If there is at least one edit rule from another rule base then all
		 * matched edit rules of all rule bases are in potDeps map. Maybe with
		 * empty Set<PotentialRuleDependencies>.
		 */

		if (potDeps.size() == 0) {
			return false;
		}
		return true;
	}

	public Set<PotentialNodeDependency> getPotentialNodeDependencies(EditRule sourceEditRule) {
		if (potDeps.containsKey(sourceEditRule)){
			return potDeps.get(sourceEditRule).getPotentialNodeDependencies();
		} else {
			return Collections.emptySet();
		}
	}

	public Set<PotentialEdgeDependency> getPotentialEdgeDependencies(EditRule sourceEditRule) {		
		if (potDeps.containsKey(sourceEditRule)){
			return potDeps.get(sourceEditRule).getPotentialEdgeDependencies();
		} else {
			return Collections.emptySet();
		}
	}

	public Set<PotentialAttributeDependency> getPotentialAttributeDependencies(EditRule sourceEditRule) {		
		if (potDeps.containsKey(sourceEditRule)){
			return potDeps.get(sourceEditRule).getPotentialAttributeDependencies();
		} else {
			return Collections.emptySet();
		}
	}
	
	public Set<PotentialDependency> getPotentialDependencies(EditRule sourceEditRule) {		
		if (potDeps.containsKey(sourceEditRule)){
			return potDeps.get(sourceEditRule).getPotentialDependencies();
		} else {
			return Collections.emptySet();
		}
	}

	protected PotentialRuleDependencies findRuleDependencies(
			Rule predecessor, EditRule predecessorEditRule, EmbeddedRule embeddedPredecessor, 
			Rule successor, EditRule successorEditRule, EmbeddedRule embeddedSuccessor) {

		// Calculate dependencies
		PotentialRuleDependencies PotRuleDeps = super.findRuleDependencies(predecessor, predecessorEditRule,
				embeddedPredecessor, successor, successorEditRule, embeddedSuccessor);

		// Indexing the new dependencies
		if (!potDeps.containsKey(successorEditRule)) {
			potDeps.put(successorEditRule, PotRuleDeps);
		} else {
			potDeps.get(successorEditRule).add(PotRuleDeps);
		}

		return PotRuleDeps;
	}

	@Override
	protected Collection<String> getDocumentTypes() {
		return this.documentTypes;
	}
}
