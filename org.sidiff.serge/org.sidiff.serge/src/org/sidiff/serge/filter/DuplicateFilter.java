package org.sidiff.serge.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.util.RuleExecutionChecker;
import org.sidiff.serge.util.RuleSemanticsChecker;

/**
 * Filters duplicate edit rules. Basically we have two case where duplicate edit
 * rules may occur; add-set and remove-unset. The edit rules do the very same,
 * although from different point of context. In this case, we decide to keep the
 * set/unset Rules and remove the respective add/remove rules. TODO: Maybe this
 * choice should be a configuration option.
 * 
 * @author kehrer
 */
public class DuplicateFilter {

	public DuplicateFilter() {

	}

	/**
	 * Filters Modules with identical names.
	 * 
	 * @param allModules
	 */
	public void filterIdentical(Set<Set<Module>> allModules) {
		Set<String> moduleNames = new HashSet<String>();

		Iterator<Set<Module>> moduleSetIterator = allModules.iterator();
		while (moduleSetIterator.hasNext()) {
			Set<Module> moduleSet = moduleSetIterator.next();
			Iterator<Module> moduleIterator = moduleSet.iterator();
			while (moduleIterator.hasNext()) {
				Module module = moduleIterator.next();

				if (moduleNames.contains(module.getName())) {
					LogUtil.log(LogEvent.NOTICE, "Filter identical operation: " + module.getName());
					moduleIterator.remove();
				} else {
					moduleNames.add(module.getName());
				}
			}
		}
	}

	public void filterAddSet(Set<Module> addModules, Set<Module> setReferenceModules) {
		for (Module setModule : setReferenceModules) {
			Iterator<Module> addModulesIterator = addModules.iterator();
			while (addModulesIterator.hasNext()) {
				Module addModule = addModulesIterator.next();
				Rule addRule = HenshinModuleAnalysis.getAllRules(addModule).get(0);
				Rule setRule = HenshinModuleAnalysis.getAllRules(setModule).get(0);

				RuleSemanticsChecker checker = new RuleSemanticsChecker(addRule, setRule);
				if (checker.isEqual()) {
					LogUtil.log(LogEvent.NOTICE, "Filter duplicate: " + addModule.getName());
					addModulesIterator.remove();
					break;
				}
			}
		}
	}

	public void filterRemoveUnset(Set<Module> removeModules, Set<Module> unsetReferenceModules) {
		// TODO
	}
}
