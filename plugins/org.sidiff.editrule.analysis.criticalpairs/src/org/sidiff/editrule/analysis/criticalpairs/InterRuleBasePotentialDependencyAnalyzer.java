package org.sidiff.editrule.analysis.criticalpairs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	private Set<String> documentTypes;

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
	 * @param ruleBases the rulebases
	 * @param transientPDs
	 *            <code>true</code> to calculate transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 * @param nonTransientPDs
	 *            <code>true</code> to calculate non transient potential dependencies; 
	 *            <code>false</code> otherwise.
	 */
	public InterRuleBasePotentialDependencyAnalyzer(
			Collection<? extends IEditRuleBase> ruleBases,
			boolean transientPDs, boolean nonTransientPDs) {

		this(deriveDocTypes(ruleBases), deriveEditRules(ruleBases), transientPDs, nonTransientPDs);
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
		this.documentTypes = new HashSet<>(documentTypes);

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
		potDeps = new HashMap<>();

		// Calculate potential dependencies
		for (EditRule editRuleA : editRules) {
			RuleBase ruleBaseA = editRuleA.getRuleBaseItem().getRuleBase();

			for (EditRule editRuleB : editRules) {
				RuleBase ruleBaseB = editRuleB.getRuleBaseItem().getRuleBase();
				
				// If rules are not in the same rule base!
				// (note that this implies "editRuleA != editRuleB")
				if (ruleBaseA != ruleBaseB) {
					for (Rule ruleA : HenshinModuleAnalysis.getAllRules(editRuleA.getExecuteModule())) {
						for (Rule ruleB : HenshinModuleAnalysis.getAllRules(editRuleB.getExecuteModule())) {
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
		return !potDeps.isEmpty();
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the dependency.
	 * @return All potential node dependencies that are caused by the given edit rule.
	 */
	public Set<PotentialNodeDependency> getPotentialNodeDependencies(EditRule sourceEditRule) {
		PotentialRuleDependencies dependencies = potDeps.get(sourceEditRule);
		return dependencies == null ? Collections.emptySet() : dependencies.getPotentialNodeDependencies();
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the dependency.
	 * @return All potential edge dependencies that are caused by the given edit rule.
	 */
	public Set<PotentialEdgeDependency> getPotentialEdgeDependencies(EditRule sourceEditRule) {		
		PotentialRuleDependencies dependencies = potDeps.get(sourceEditRule);
		return dependencies == null ? Collections.emptySet() : dependencies.getPotentialEdgeDependencies();
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the dependency.
	 * @return All potential attribute dependencies that are caused by the given edit rule.
	 */
	public Set<PotentialAttributeDependency> getPotentialAttributeDependencies(EditRule sourceEditRule) {		
		PotentialRuleDependencies dependencies = potDeps.get(sourceEditRule);
		return dependencies == null ? Collections.emptySet() : dependencies.getPotentialAttributeDependencies();
	}

	/**
	 * @param sourceEditRule
	 *            The source or successor of the dependency.
	 * @return All potential dependencies that are caused by the given edit rule.
	 */
	public Set<PotentialDependency> getPotentialDependencies(EditRule sourceEditRule) {		
		PotentialRuleDependencies dependencies = potDeps.get(sourceEditRule);
		return dependencies == null ? Collections.emptySet() : dependencies.getPotentialDependencies();
	}

	@Override
	protected PotentialRuleDependencies findRuleDependencies(
			ActionGraph predecessor, EditRule predecessorEditRule, 
			ActionGraph successor, EditRule successorEditRule) {

		// Calculate dependencies
		PotentialRuleDependencies potentialDependencies = super.findRuleDependencies(
				predecessor, predecessorEditRule, 
				successor, successorEditRule);

		// Indexing the new dependencies
		if (!potDeps.containsKey(successorEditRule)) {
			potDeps.put(successorEditRule, potentialDependencies);
		} else {
			potDeps.get(successorEditRule).add(potentialDependencies);
		}

		return potentialDependencies;
	}

	@Override
	protected Set<EPackage> getImports() {
		return getImports(documentTypes);
	}
}
