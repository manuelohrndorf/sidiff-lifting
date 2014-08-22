package org.sidiff.editrule.generator.util;

import java.util.List;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.NodeKindSelection;

/**
 * Checks whether a module is violating consistency constraints.
 * (e.g. if it still contains abstract <<create>> child nodes.
 * @author mrindt
 *
 */
public class ModuleConsistencyChecker {

	private Module module;
	
	
	public ModuleConsistencyChecker(Module module) {
		this.module = module;
	}
	
/**
 * This method is a consistency check and will remove all (original) modules, that still
 * contain abstract <<create>> child nodes.
 * @param allModules
 */
	public boolean isViolating() {
		List<Node> abstractNodesInOriginal =
				HenshinRuleAnalysisUtilEx
				.getChildNodesWithinAContainmentRelation(module, NodeKindSelection.CREATE, true);
		if(!abstractNodesInOriginal.isEmpty()) {
			return true;
		}	
		return false;
	}

	
}