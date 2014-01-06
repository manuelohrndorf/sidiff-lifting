package org.sidiff.common.henshin;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

public class HenshinModuleAnalysis {

	/**
	 * Get all rules of the module (including all rules of sub modules and including nested multi rules).
	 * 
	 * @param module
	 *            the module.
	 * @return all Rules contained by the module in an unmodifiable list.
	 */
	public static EList<Rule> getAllRules(Module module) {
		EList<Rule> rules = new BasicEList<Rule>();
		
		for (Unit unit : module.getUnits()) {
			if (unit instanceof Rule) {
				Rule rule = (Rule) unit;
				rules.add(rule);
				rules.addAll(rule.getAllMultiRules());
			}
		}
		
		for (Module subModule : module.getSubModules()) {
			rules.addAll(getAllRules(subModule));
		}
		
		return ECollections.unmodifiableEList(rules);
	}

}
