
package org.sidiff.difference.lifting.edit2recognition.util;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRules;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Testing rule properties.
 */
public class RuleChecker {
	
	/**
	 * Checks if any rule of this module uses derived references.
	 * 
	 * @param editModule
	 *            The module to test.
	 * @return <code>true</code> if any rule of this module uses a derived reference;
	 *         <code>false</code> otherwise.
	 */
	public static boolean checkDerivedReferences(Module editModule) {
		
		for (Rule rule : getRules(editModule)) {
			for (Edge edge : rule.getLhs().getEdges()) {
				if (edge.getType().isDerived()) {
					return true;
				}
			}
		}
		return false;
	}
}
