package org.sidiff.editrule.rulebase.view.editrule;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
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
	public Set<PotentialConflict> getPotentialConflicts(EditRule editRule) {
		Set<PotentialConflict> res = getPotentialConflictIndex().get(editRule);
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
	
	/**
	 * Lazy builds an index for all potential Conflicts.
	 * 
	 * @return Index: Edit-Rule -> Potential-Conflict
	 */
	private Map<EditRule, Set<PotentialConflict>> getPotentialConflictIndex() {
		if (potConIndex == null) {
			potConIndex = new HashMap<EditRule, Set<PotentialConflict>>();

			// Nodes
			for (PotentialConflict potDep : getRuleBase().getPotentialNodeConflicts()) {
				if (!potConIndex.containsKey(potDep.getEditRules().get(0))) {
					potConIndex.put(potDep.getEditRules().get(0), new HashSet<PotentialConflict>());
				}
				potConIndex.get(potDep.getEditRules().get(0)).add(potDep);
				if (!potConIndex.containsKey(potDep.getEditRules().get(1))) {
					potConIndex.put(potDep.getEditRules().get(1), new HashSet<PotentialConflict>());
				}
				potConIndex.get(potDep.getEditRules().get(1)).add(potDep);
			}

			// Edges
			for (PotentialConflict potDep : getRuleBase().getPotentialEdgeConflicts()) {
				if (!potConIndex.containsKey(potDep.getEditRules().get(0))) {
					potConIndex.put(potDep.getEditRules().get(0), new HashSet<PotentialConflict>());
				}
				potConIndex.get(potDep.getEditRules().get(0)).add(potDep);
				if (!potConIndex.containsKey(potDep.getEditRules().get(1))) {
					potConIndex.put(potDep.getEditRules().get(1), new HashSet<PotentialConflict>());
				}
				potConIndex.get(potDep.getEditRules().get(1)).add(potDep);
			}

			// Attributes
			for (PotentialConflict potDep : getRuleBase().getPotentialAttributeConflicts()) {
				if (!potConIndex.containsKey(potDep.getEditRules().get(0))) {
					potConIndex.put(potDep.getEditRules().get(0), new HashSet<PotentialConflict>());
				}
				potConIndex.get(potDep.getEditRules().get(0)).add(potDep);
				if (!potConIndex.containsKey(potDep.getEditRules().get(1))) {
					potConIndex.put(potDep.getEditRules().get(1), new HashSet<PotentialConflict>());
				}
				potConIndex.get(potDep.getEditRules().get(1)).add(potDep);
			}
		}

		return potConIndex;
	}
}
