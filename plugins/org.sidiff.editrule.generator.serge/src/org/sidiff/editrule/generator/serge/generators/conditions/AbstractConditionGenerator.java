package org.sidiff.editrule.generator.serge.generators.conditions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;

public class AbstractConditionGenerator {
	
	protected Rule editRule;

	public AbstractConditionGenerator(Rule editRule) {
		super();
		this.editRule = editRule;
	}	
	
	protected List<Edge> getContainmentCreateEdges(){
		List<Edge> res = new ArrayList<Edge>();
		for (Edge edge : HenshinRuleAnalysisUtilEx.getRHSMinusLHSEdges(editRule)){
			if (edge.getType().isContainment()){
				res.add(edge);
			}
		}
		
		return res;
	}
	
}
