package org.sidiff.serge.filter;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.util.RuleExecutionChecker;

/**
 * Filters edit rules that are not executable in principal. The question whether
 * an edit rule is executable is delegated to the {@link RuleExecutionChecker}.
 * 
 * @author kehrer
 */
public class ExecutionFilter {

	public void applyOn(Set<Set<Module>> allModules) {

		Iterator<Set<Module>> moduleSetIterator = allModules.iterator();
		while (moduleSetIterator.hasNext()) {
			Set<Module> moduleSet = moduleSetIterator.next();
			Iterator<Module> moduleIterator = moduleSet.iterator();
			while (moduleIterator.hasNext()) {
				Module module = moduleIterator.next();
				Rule rule = HenshinModuleAnalysis.getAllRules(module).get(0);

				// Remove module if rule is not executable at all.
				RuleExecutionChecker executionChecker = new RuleExecutionChecker(rule);
				if (!executionChecker.isExecutable()) {
					LogUtil.log(LogEvent.NOTICE, "Filter not executable operation : " + module.getName());
					moduleIterator.remove();
				}
			}
		}
	}
}
