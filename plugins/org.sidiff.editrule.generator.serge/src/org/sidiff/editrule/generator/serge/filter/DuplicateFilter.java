package org.sidiff.editrule.generator.serge.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.serge.util.RuleSemanticsChecker;
import org.sidiff.editrule.generator.types.OperationType;

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
	public void filterIdenticalByName(Map<OperationType, Set<Module>> allModules) {
		Set<String> moduleNames = new HashSet<String>();

		for (OperationType opType : allModules.keySet()) {
			Set<Module> moduleSet = allModules.get(opType);
		
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
					LogUtil.log(LogEvent.NOTICE, "Filter duplicate (ADD/SET_REFERENCE): " + addModule.getName());
					addModulesIterator.remove();
					break;
				}
			}
		}
	}

	public void filterRemoveUnset(Set<Module> removeModules, Set<Module> unsetReferenceModules) {
		// TODO: implement this filter (TK)
	}
}
