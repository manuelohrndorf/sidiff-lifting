package org.sidiff.editrule.analysis.criticalpairs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
 * @author Manuel Ohrndorf
 * @author cpietsch
 */
public class InterRuleBasePotentialConflictAnalyzer extends PotentialConflictAnalyzer {

	/**
	 * Potential conflict index.
	 */
	private Map<EditRule, PotentialRuleConflicts> potCons;

	public InterRuleBasePotentialConflictAnalyzer(
			Collection<? extends IEditRuleBase> ruleBases) {

		this(deriveDocTypes(ruleBases), deriveEditRules(ruleBases));
	}

	private static Collection<String> deriveDocTypes(Collection<? extends IEditRuleBase> ruleBases) {
		return ruleBases.stream()
				.map(IEditRuleBase::getDocumentTypes)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

	private static Set<EditRule> deriveEditRules(Collection<? extends IEditRuleBase> ruleBases) {
		return ruleBases.stream()
				.map(IEditRuleBase::getActiveEditRules)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

	public InterRuleBasePotentialConflictAnalyzer(
			Collection<String> documentTypes,
			Set<EditRule> editRules) {

		super(getImports(documentTypes));
		calculate(editRules);
	}
	
	/**
	 * Calculates the potential inter-conflicts between two or more rulebases.
	 * 
	 * @param editRules
	 *            All rules of various rulebases.
	 */
	private void calculate(Collection<EditRule> editRules) {
		potCons = new HashMap<>();

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
		return !potCons.isEmpty();
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the conflict.
	 * @return All potential node conflicts that are caused by the given edit rule.
	 */
	public Set<PotentialNodeConflict> getPotentialNodeConflicts(EditRule sourceEditRule) {
		PotentialRuleConflicts potentialConflicts = potCons.get(sourceEditRule);
		return potentialConflicts == null ? Collections.emptySet() : potentialConflicts.getPotentialNodeConflicts();
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the conflict.
	 * @return All potential edge conflicts that are caused by the given edit rule.
	 */
	public Set<PotentialEdgeConflict> getPotentialEdgeConflicts(EditRule sourceEditRule) {		
		PotentialRuleConflicts potentialConflicts = potCons.get(sourceEditRule);
		return potentialConflicts == null ? Collections.emptySet() : potentialConflicts.getPotentialEdgeConflicts();
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the conflict.
	 * @return All potential attribute conflicts that are caused by the given edit rule.
	 */
	public Set<PotentialAttributeConflict> getPotentialAttributeConflicts(EditRule sourceEditRule) {		
		PotentialRuleConflicts potentialConflicts = potCons.get(sourceEditRule);
		return potentialConflicts == null ? Collections.emptySet() : potentialConflicts.getPotentialAttributeConflicts();
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the conflict.
	 * @return All potential conflicts that are caused by the given edit rule.
	 */
	public Set<PotentialConflict> getPotentialConflicts(EditRule sourceEditRule) {		
		PotentialRuleConflicts potentialConflicts = potCons.get(sourceEditRule);
		return potentialConflicts == null ? Collections.emptySet() : potentialConflicts.getPotentialConflicts();
	}

	@Override
	protected PotentialRuleConflicts findRuleConflicts(
			ActionGraph predecessor, EditRule predecessorEditRule, 
			ActionGraph successor, EditRule successorEditRule) {

		// Calculate dependencies
		PotentialRuleConflicts potentialRuleConflicts = super.findRuleConflicts(
				predecessor, predecessorEditRule, 
				successor, successorEditRule);

		// Indexing the new dependencies
		if (!potCons.containsKey(successorEditRule)) {
			potCons.put(successorEditRule, potentialRuleConflicts);
		} else {
			potCons.get(successorEditRule).add(potentialRuleConflicts);
		}

		return potentialRuleConflicts;
	}
}
