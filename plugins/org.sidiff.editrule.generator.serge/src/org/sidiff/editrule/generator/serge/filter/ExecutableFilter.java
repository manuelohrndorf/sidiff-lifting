package org.sidiff.editrule.generator.serge.filter;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.serge.util.ModuleConsistencyChecker;
import org.sidiff.editrule.generator.serge.util.RuleExecutionChecker;
import org.sidiff.editrule.generator.types.OperationType;

/**
 * Filters edit rules that are not executable in principal. The question whether
 * an edit rule is executable is delegated to the {@link RuleExecutionChecker}
 * and {@link RuleConsistencyChecker}.
 * 
 * @author kehrer, mrindt
 */
public class ExecutableFilter {

	public void applyOn(Map<OperationType, Set<Module>> allModules) {

		for (OperationType opType : allModules.keySet()) {
			Set<Module> moduleSet = allModules.get(opType);
					
			Iterator<Module> moduleIterator = moduleSet.iterator();
			while (moduleIterator.hasNext()) {
				Module module = moduleIterator.next();
				Rule rule = HenshinModuleAnalysis.getAllRules(module).get(0);

				// Remove module if rule is not executable at all.
				RuleExecutionChecker executionChecker = new RuleExecutionChecker(rule);
				if (!executionChecker.isExecutable()) {
					LogUtil.log(LogEvent.NOTICE, "Not executable operation: " + module.getName());
					moduleIterator.remove();
				}
				// Remove module if rule is violating consistency
				ModuleConsistencyChecker consistencyChecker = new ModuleConsistencyChecker(module);
				if(consistencyChecker.isViolating()) {
					LogUtil.log(LogEvent.NOTICE, "Consistency violationg operation: " + module.getName());
					moduleIterator.remove();
				}
			}
		}
	}
	
}
