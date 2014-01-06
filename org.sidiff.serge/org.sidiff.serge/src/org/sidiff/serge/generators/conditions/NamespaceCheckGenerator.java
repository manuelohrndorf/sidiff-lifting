package org.sidiff.serge.generators.conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.serge.core.LokalNamespace;

public class NamespaceCheckGenerator extends AbstractConditionGenerator {

	public static Set<EAttribute> globalNamespaces;
	public static Set<LokalNamespace> lokalNamespaces;

	public NamespaceCheckGenerator(Rule editRule) {
		super(editRule);
		// TODO Auto-generated constructor stub
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
