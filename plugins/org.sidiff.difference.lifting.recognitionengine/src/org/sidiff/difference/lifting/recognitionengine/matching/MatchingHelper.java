package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.collections.Pair;
import org.sidiff.common.henshin.ApplicationCondition;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.editrule.rulebase.EditRule;

public class MatchingHelper {

	//FIXME(TK): Usually we can have multiple NACs
	// (CP 2020-06-23): At the moment we support multiple NACs if they are contained in an AND Formula.
	// Furthermore the NAC must have a NestedCondition as child.
	public static Collection<ApplicationCondition> getNAC(EditRule editRule) {
		Collection<ApplicationCondition> nacs = new HashSet<ApplicationCondition>();
		for (Rule rule : HenshinModuleAnalysis.getAllRules(editRule.getExecuteModule())) {
			
			if(rule.getLhs().getFormula() instanceof And){
				And and = (And) rule.getLhs().getFormula();
				if(and.getLeft() instanceof Not) {
					Not not = (Not) and.getLeft();
					nacs.add(new ApplicationCondition((NestedCondition)not.getChild()));
				}
				if(and.getRight() instanceof Not) {
					Not not = (Not) and.getRight();
					nacs.add(new ApplicationCondition((NestedCondition) not.getChild()));
				}
			}else if (rule.getLhs().getFormula() instanceof Not) {
				Not not = (Not) rule.getLhs().getFormula();
				nacs.add(new ApplicationCondition((NestedCondition) not.getChild()));
			}
		}
		
		return nacs;
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
	public static Set<Pair<EObject,EObject>> getCartesianProduct(Set<EObject> set1, Set<EObject> set2) {
		Set<Pair<EObject,EObject>> res = new LinkedHashSet<>();
		for (EObject eObject1 : set1) {
			for (EObject eObject2 : set2) {
				res.add(Pair.of(eObject1, eObject2));
			}
		}
		return res;
	}
	
}
