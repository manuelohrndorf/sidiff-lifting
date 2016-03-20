package org.sidiff.editrule.rulebase.type.editrule;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.type.basic.BasicRuleBase;

/**
 * Basic implementation of {@link IEditRuleBase}.
 */
public class EditRuleBase extends BasicRuleBase implements IEditRuleBase {
	
	/**
	 * Mapping: src EditRule -> set of PotentialDependencies where EditRule is the source of
	 */
	private Map<EditRule, Set<PotentialDependency>> potDepIndex;
	
	@Override
	public EditRule getEditRule(String name) {
		Set<EditRule> editRules = getActiveEditRules();
		for (EditRule er : editRules) {
			if (getRuleName(er.getExecuteMainUnit()).equals(name)) {
				return er;
			}
		}
		return null;
	}

	@Override
	public Set<PotentialDependency> getPotentialDependencies(EditRule editRule) {
		Set<PotentialDependency> res = getPotentialDependencyIndex().get(editRule);
		if (res != null) {
			return res;
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public Set<EditRule> getActiveEditRules() {
		Set<EditRule> items = new HashSet<EditRule>();
		
		for (EditRule er : getRuleBase().getEditRules()) {
			if (er.getRuleBaseItem().isActive()) {
				items.add(er);
			}
		}
		return items;
	}
	
	@Override
	public Set<Unit> getActiveEditRuleMainUnits() {
		Set<Unit> units = new HashSet<Unit>();

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
	 * @param unit
	 *            An (henshin) edit rule.
	 * @return The corresponding identifying name of the edit rule.
	 */
	private String getRuleName(Unit unit) {
		Module module = unit.getModule();
		return module.getName();
	}
	
	/**
	 * Lazy builds an index for all potential Dependencies.
	 * 
	 * @return Index: Edit-Rule - source of -> Potential-Dependency
	 */
	private Map<EditRule, Set<PotentialDependency>> getPotentialDependencyIndex() {
		if (potDepIndex == null) {
			potDepIndex = new HashMap<EditRule, Set<PotentialDependency>>();

			// Nodes
			for (PotentialDependency potDep : getRuleBase().getPotentialNodeDependencies()) {
				if (!potDepIndex.containsKey(potDep.getSourceRule())) {
					potDepIndex.put(potDep.getSourceRule(), new HashSet<PotentialDependency>());
				}
				potDepIndex.get(potDep.getSourceRule()).add(potDep);
			}

			// Edges
			for (PotentialDependency potDep : getRuleBase().getPotentialEdgeDependencies()) {
				if (!potDepIndex.containsKey(potDep.getSourceRule())) {
					potDepIndex.put(potDep.getSourceRule(), new HashSet<PotentialDependency>());
				}
				potDepIndex.get(potDep.getSourceRule()).add(potDep);
			}

			// Attributes
			for (PotentialDependency potDep : getRuleBase().getPotentialAttributeDependencies()) {
				if (!potDepIndex.containsKey(potDep.getSourceRule())) {
					potDepIndex.put(potDep.getSourceRule(), new HashSet<PotentialDependency>());
				}
				potDepIndex.get(potDep.getSourceRule()).add(potDep);
			}
		}

		return potDepIndex;
	}
}
