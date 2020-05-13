package org.sidiff.editrule.rulebase.view.editrule;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialConflict;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.view.basic.BasicRuleBase;

/**
 * Basic implementation of {@link IEditRuleBase}.
 */
public class EditRuleBase extends BasicRuleBase implements IEditRuleBase {

	/**
	 * Mapping: src EditRule -> set of PotentialDependencies where EditRule is the source of
	 */
	private Map<EditRule, Set<PotentialDependency>> potDepIndex;

	/**
	 * Mapping: EditRule -> set of PotentialConflicts containing the EditRule
	 */
	private Map<EditRule, Set<PotentialConflict>> potConIndex;

	@Override
	public EditRule getEditRule(String name) {
		for (EditRule er : getActiveEditRules()) {
			if (er.getExecuteModule().getName().equals(name)) {
				return er;
			}
		}
		return null;
	}

	@Override
	public Set<PotentialDependency> getPotentialDependencies(EditRule editRule) {
		return getPotentialDependencyIndex().getOrDefault(editRule, Collections.emptySet());
	}

	@Override
	public Set<PotentialConflict> getPotentialConflicts(EditRule editRule) {
		return getPotentialConflictIndex().getOrDefault(editRule, Collections.emptySet());
	}

	@Override
	public Set<EditRule> getActiveEditRules() {
		Set<EditRule> items = new HashSet<>();
		for (EditRule er : getRuleBase().getEditRules()) {
			if (er.getRuleBaseItem().isActive()) {
				items.add(er);
			}
		}
		return items;
	}

	@Override
	public Set<Unit> getActiveEditRuleMainUnits() {
		Set<Unit> units = new HashSet<>();
		for (EditRule er : getActiveEditRules()) {
			units.add(er.getExecuteMainUnit());
		}
		return units;
	}

	@Override
	public void activateAllEditRules() {
		for (EditRule er : getRuleBase().getEditRules()) {
			er.getRuleBaseItem().setActive(true);
		}
	}

	@Override
	public void deactivateAllEditRules() {
		for (EditRule er : getRuleBase().getEditRules()) {
			er.getRuleBaseItem().setActive(false);
		}
	}

	/**
	 * Lazy builds an index for all potential Dependencies.
	 * 
	 * @return Index: Edit-Rule - source of -> Potential-Dependency
	 */
	private Map<EditRule, Set<PotentialDependency>> getPotentialDependencyIndex() {
		if (potDepIndex == null) {
			potDepIndex =
				Stream.of(getRuleBase().getPotentialNodeDependencies(),
						getRuleBase().getPotentialEdgeDependencies(),
						getRuleBase().getPotentialAttributeDependencies())
					.flatMap(Collection::stream)
					.collect(Collectors.groupingBy(PotentialDependency::getSourceRule, Collectors.toSet()));
		}
		return potDepIndex;
	}

	/**
	 * Lazy builds an index for all potential Conflicts.
	 * 
	 * @return Index: Edit-Rule -> Potential-Conflict
	 */
	private Map<EditRule, Set<PotentialConflict>> getPotentialConflictIndex() {
		if (potConIndex == null) {
			potConIndex =
				Stream.of(getRuleBase().getPotentialNodeConflicts(),
						getRuleBase().getPotentialEdgeConflicts(),
						getRuleBase().getPotentialAttributeConflicts(),
						getRuleBase().getPotentialDanglingEdgeConflicts())
					.flatMap(Collection::stream)
					.collect(Collectors.groupingBy(PotentialConflict::getSourceRule, Collectors.toSet()));
		}

		return potConIndex;
	}
}
