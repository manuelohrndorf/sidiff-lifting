package org.sidiff.editrule.analysis.criticalpairs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.henshin.view.ActionGraph;
import org.sidiff.editrule.analysis.criticalpairs.util.PotentialRuleConflicts;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialAttributeConflict;
import org.sidiff.editrule.rulebase.PotentialConflict;
import org.sidiff.editrule.rulebase.PotentialEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialNodeConflict;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.view.editrule.IEditRuleBase;

/**
 * Calculates the potential inter-conflicts between two or more rulebases. 
 * 
 * @author Manuel Ohrndorf, cpietsch
 */
public class InterRuleBasePotentialConflictAnalyzer extends RuleBasePotentialConflictAnalyzer {

	/**
	 * All document types of all rules in the rulebases.
	 */
	private Collection<String> documentTypes;
	
	/**
	 * Potential conflict index.
	 */
	private Map<EditRule, PotentialRuleConflicts> potCons;

	/**
	 * 
	 */
	public InterRuleBasePotentialConflictAnalyzer(
			Collection<? extends IEditRuleBase> rulebases) {
		
		super();

		// Initialize:
		this.documentTypes = new HashSet<String>();
		List<EditRule> editRules = new ArrayList<EditRule>();
		
		for (IEditRuleBase rb : rulebases) {
			documentTypes.addAll(rb.getDocumentTypes());

			// Consider active rules only:
			editRules.addAll(rb.getActiveEditRules());
		}

		// Calculate:
		calculate(editRules);
	}
	
	/**
	 * 
	 * @param documentTypes
	 * @param editRules
	 */
	public InterRuleBasePotentialConflictAnalyzer(
			Collection<String> documentTypes, Set<EditRule> editRules) {
		
		super();
		
		// Set document types:
		this.documentTypes = documentTypes;
		
		// Calculate:
		calculate(editRules);
	}
	
	/**
	 * Calculates the potential inter-conflicts between two or more rulebases.
	 * 
	 * @param editRules
	 *            All rules of various rulebases.
	 */
	private void calculate(Collection<EditRule> editRules) {
		potCons = new HashMap<EditRule, PotentialRuleConflicts>();

		// Calculate potential dependencies
		for (EditRule editRuleA : editRules) {
			RuleBase ruleBaseA = editRuleA.getRuleBaseItem().getRuleBase();
			List<Rule> rulesA = HenshinModuleAnalysis.getAllRules(editRuleA.getExecuteModule());

			for (EditRule editRuleB : editRules) {
				RuleBase ruleBaseB = editRuleB.getRuleBaseItem().getRuleBase();
				List<Rule> rulesB = HenshinModuleAnalysis.getAllRules(editRuleB.getExecuteModule());
				
				// If rules are not in the same rule base!
				// (note that this implies "editRuleA != editRuleB")
				if (ruleBaseA != ruleBaseB) {
					for (Rule ruleA : rulesA) {
						for (Rule ruleB : rulesB) {
							// (1) Compare rule A with rules B
							findRuleConflicts(
									getActionGraph(ruleA), editRuleA,
									getActionGraph(ruleB), editRuleB);
						}
					}
				}
			}
		}
	}

	/**
	 * @return <code>true</code> if there are at least two rules from different
	 *         rule bases; <code>false</code> otherwise.
	 */
	public boolean isNecessary() {

		/*
		 * If there are only matched edit rules from one rule base then
		 * potCons.size() will be 0. (ruleBaseA != ruleBaseB) 
		 * 
		 * If there is at least one edit rule from another rule base then all
		 * matched edit rules of all rule bases are in potCons map. Maybe with
		 * empty Set<PotentialRuleDependencies>.
		 */

		if (potCons.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the conflict.
	 * @return All potential node conflicts that are caused by the given edit rule.
	 */
	public Set<PotentialNodeConflict> getPotentialNodeConflicts(EditRule sourceEditRule) {
		if (potCons.containsKey(sourceEditRule)){
			return potCons.get(sourceEditRule).getPotentialNodeConflicts();
		} else {
			return Collections.emptySet();
		}
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the conflict.
	 * @return All potential edge conflicts that are caused by the given edit rule.
	 */
	public Set<PotentialEdgeConflict> getPotentialEdgeConflicts(EditRule sourceEditRule) {		
		if (potCons.containsKey(sourceEditRule)){
			return potCons.get(sourceEditRule).getPotentialEdgeConflicts();
		} else {
			return Collections.emptySet();
		}
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the conflict.
	 * @return All potential attribute conflicts that are caused by the given edit rule.
	 */
	public Set<PotentialAttributeConflict> getPotentialAttributeConflicts(EditRule sourceEditRule) {		
		if (potCons.containsKey(sourceEditRule)){
			return potCons.get(sourceEditRule).getPotentialAttributeConflicts();
		} else {
			return Collections.emptySet();
		}
	}
	
	/**
	 * @param sourceEditRule
	 *            The source or successor of the conflict.
	 * @return All potential conflicts that are caused by the given edit rule.
	 */
	public Set<PotentialConflict> getPotentialConflicts(EditRule sourceEditRule) {		
		if (potCons.containsKey(sourceEditRule)){
			return potCons.get(sourceEditRule).getPotentialConflicts();
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	protected PotentialRuleConflicts findRuleConflicts(
			ActionGraph predecessor, EditRule predecessorEditRule, 
			ActionGraph successor, EditRule successorEditRule) {

		// Calculate dependencies
		PotentialRuleConflicts PotRuleCons = super.findRuleConflicts(
				predecessor, predecessorEditRule, 
				successor, successorEditRule);

		// Indexing the new dependencies
		if (!potCons.containsKey(successorEditRule)) {
			potCons.put(successorEditRule, PotRuleCons);
		} else {
			potCons.get(successorEditRule).add(PotRuleCons);
		}

		return PotRuleCons;
	}

	@Override
	protected Set<EPackage> getImports() {
		return getImports(documentTypes);
	}
}
