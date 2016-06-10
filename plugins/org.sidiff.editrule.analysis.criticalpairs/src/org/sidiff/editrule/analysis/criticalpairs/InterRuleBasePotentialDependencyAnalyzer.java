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
import org.sidiff.editrule.analysis.criticalpairs.util.PotentialRuleDependencies;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.view.editrule.IEditRuleBase;

/**
 * Calculates the potential inter-dependencies between two or more rulebases. 
 * 
 * @author Manuel Ohrndorf
 */
public class InterRuleBasePotentialDependencyAnalyzer extends RuleBasePotentialDependencyAnalyzer {

	/**
	 * All document types of all rules in the rulebases.
	 */
	private Collection<String> documentTypes;
	
	/**
	 * Potential dependency index.
	 */
	private Map<EditRule, PotentialRuleDependencies> potDeps;

	/**
	 * <p>
	 * Calculates the potential inter-dependencies between two or more rulebases.
	 * </p>
	 * 
	 * <p>
	 * When calculating actual dependencies on a given symmetric difference
	 * which consists of semantic change sets, most likely this constructor will
	 * be the right choice. It is optimized as only inter-dependencies between
	 * edit rules which have been actually recognized will be computed. Please
	 * note that the document type is given implicitly by the symmetric
	 * difference in this case.
	 * </p>
	 * 
	 * @param engine
	 *            The engine that has been used to lift the symmetric difference.
	 * @param transientPDs
	 *            <code>true</code> to calculate transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 * @param nonTransientPDs
	 *            <code>true</code> to calculate non transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 */
	public InterRuleBasePotentialDependencyAnalyzer(
			Collection<? extends IEditRuleBase> rulebases,
			boolean transientPDs, boolean nonTransientPDs) {
		
		super(transientPDs, nonTransientPDs);

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
	 * <p>
	 * Calculates the potential inter-dependencies between two or more rulebases.
	 * </p>
	 * 
	 * <p>
	 * When calculating actual dependencies on a given symmetric difference
	 * which consists of semantic change sets, most likely this constructor will
	 * be the right choice. It is optimized as only inter-dependencies between
	 * edit rules which have been actually recognized will be computed. Please
	 * note that the document type is given implicitly by the symmetric
	 * difference in this case.
	 * </p>
	 * 
	 * @param rulebases
	 *            The rulebases to analyze.
	 * 
	 * @see InterRuleBasePotentialDependencyAnalyzer
	 *      #InterRuleBasePotentialDependencyAnalyzer(Collection, Set, boolean, boolean)
	 */
	public InterRuleBasePotentialDependencyAnalyzer(Collection<? extends IEditRuleBase> rulebases) {
		this(rulebases, false, true);
	}
	
	/**
	 * Calculates the potential inter-dependencies between two or more rulebases.
	 * (Non-transient dependencies only.)
	 * 
	 * @param documentTypes
	 *            The document types of all rulebases.
	 * @param editRules
	 *            All rules of various rulebases.
	 *            
	 * @see InterRuleBasePotentialDependencyAnalyzer
	 * 		#InterRuleBasePotentialDependencyAnalyzer(Collection, Set, boolean, boolean)
	 */
	public InterRuleBasePotentialDependencyAnalyzer(
			Collection<String> documentTypes, Set<EditRule> editRules) {
		
		this(documentTypes, editRules, false, true);
	}

	/**
	 * Calculates the potential inter-dependencies between two or more rulebases.
	 * 
	 * @param documentTypes
	 *            The document types of all rulebases.
	 * @param editRules
	 *            All rules of various rulebases.
	 * @param transientPDs
	 *            <code>true</code> to calculate transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 * @param nonTransientPDs
	 *            <code>true</code> to calculate non transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 */
	public InterRuleBasePotentialDependencyAnalyzer(
			Collection<String> documentTypes, Set<EditRule> editRules,
			boolean transientPDs, boolean nonTransientPDs) {
		
		super(transientPDs, nonTransientPDs);
		
		// Set document types:
		this.documentTypes = documentTypes;
		
		// Calculate:
		calculate(editRules);
	}
	
	/**
	 * Calculates the potential inter-dependencies between two or more rulebases.
	 * 
	 * @param editRules
	 *            All rules of various rulebases.
	 */
	private void calculate(Collection<EditRule> editRules) {
		potDeps = new HashMap<EditRule, PotentialRuleDependencies>();

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
							findRuleDependencies(
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

	/**
	 * @param sourceEditRule
	 *            The source or successor of the dependency.
	 * @return All potential node dependencies that are caused by the given edit rule.
	 */
	public Set<PotentialNodeDependency> getPotentialNodeDependencies(EditRule sourceEditRule) {
		if (potDeps.containsKey(sourceEditRule)){
			return potDeps.get(sourceEditRule).getPotentialNodeDependencies();
		} else {
			return Collections.emptySet();
		}
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the dependency.
	 * @return All potential edge dependencies that are caused by the given edit rule.
	 */
	public Set<PotentialEdgeDependency> getPotentialEdgeDependencies(EditRule sourceEditRule) {		
		if (potDeps.containsKey(sourceEditRule)){
			return potDeps.get(sourceEditRule).getPotentialEdgeDependencies();
		} else {
			return Collections.emptySet();
		}
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the dependency.
	 * @return All potential attribute dependencies that are caused by the given edit rule.
	 */
	public Set<PotentialAttributeDependency> getPotentialAttributeDependencies(EditRule sourceEditRule) {		
		if (potDeps.containsKey(sourceEditRule)){
			return potDeps.get(sourceEditRule).getPotentialAttributeDependencies();
		} else {
			return Collections.emptySet();
		}
	}
	
	/**
	 * @param sourceEditRule
	 *            The source or successor of the dependency.
	 * @return All potential dependencies that are caused by the given edit rule.
	 */
	public Set<PotentialDependency> getPotentialDependencies(EditRule sourceEditRule) {		
		if (potDeps.containsKey(sourceEditRule)){
			return potDeps.get(sourceEditRule).getPotentialDependencies();
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	protected PotentialRuleDependencies findRuleDependencies(
			ActionGraph predecessor, EditRule predecessorEditRule, 
			ActionGraph successor, EditRule successorEditRule) {

		// Calculate dependencies
		PotentialRuleDependencies PotRuleDeps = super.findRuleDependencies(
				predecessor, predecessorEditRule, 
				successor, successorEditRule);

		// Indexing the new dependencies
		if (!potDeps.containsKey(successorEditRule)) {
			potDeps.put(successorEditRule, PotRuleDeps);
		} else {
			potDeps.get(successorEditRule).add(PotRuleDeps);
		}

		return PotRuleDeps;
	}

	@Override
	protected Set<EPackage> getImports() {
		return getImports(documentTypes);
	}
}
