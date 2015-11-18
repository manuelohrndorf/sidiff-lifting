package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.matching.conditions.ApplicationCondition;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.rulebase.EditRule;

public class MatchingHelper {

	//FIXME(TK): Usually we can have multiple NACs
	public static ApplicationCondition getNAC(EditRule editRule) {
		for (Rule rule : HenshinModuleAnalysis.getAllRules(editRule.getExecuteModule())) {
			if (rule.getLhs().getFormula() instanceof Not) {
				return new ApplicationCondition((NestedCondition) ((Not)rule.getLhs().getFormula()).getChild());
			}
		}
		
		return null;
	}

	/**
	 * Returns the cartesian product set1 x set2.<br/>
	 * Each pair (set1_element,set2_element) of the cartesian product is encoded
	 * as 2D-Array, set1_element is at index 0, set2_element at index 1.
	 * 
	 * @param set1
	 * @param set2
	 * @return The cartesian product set1 x set2
	 */
	public static Set<EObject[]> getCartesianProduct(Set<EObject> set1, Set<EObject> set2) {
		Set<EObject[]> res = new HashSet<EObject[]>();
		for (EObject eObject1 : set1) {
			for (EObject eObject2 : set2) {
				EObject[] tuple = new EObject[] { eObject1, eObject2 };
				res.add(tuple);
			}
		}

		return res;
	}
	
}
