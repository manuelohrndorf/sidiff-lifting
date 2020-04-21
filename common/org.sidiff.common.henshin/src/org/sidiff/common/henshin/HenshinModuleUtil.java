package org.sidiff.common.henshin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

public class HenshinModuleUtil {

	/**
	 * Creates a new Module that is an inverse of an input Module
	 * @param name
	 * 				the name for the new Module.
	 * @param description
	 * 				the description for the new Module.
	 * @param inputModule
	 * 				the Henshin Module from which an inverse should be created.
	 * @return the new Module.
	 */	
	public static Module createInverse(String name, String description, Module inputModule) {
		Module module = EcoreUtil.copy(inputModule);
		module.setName(name);
		module.setDescription(description);
		for(Unit unit : module.getUnits()) {
			if(unit instanceof Rule){
				
				Rule rule = (Rule)unit;
				Graph lhs = rule.getRhs();
				lhs.setName("LHS");
				Graph rhs = rule.getLhs();
				rhs.setName("RHS");
				rule.setLhs(lhs);
				rule.setRhs(rhs);
			
				for(Mapping mapping : rule.getMappings()) {
					Node origin = mapping.getImage();
					Graph orginGraph = mapping.getImage().getGraph();
					origin.setGraph(orginGraph);
				
					Node image = mapping.getOrigin();
					Graph imageGraph = mapping.getOrigin().getGraph();
					image.setGraph(imageGraph);
				
					mapping.setImage(image);
					mapping.setOrigin(origin);
				
				}

				// remove attributes under <<delete>> nodes and their ParameterMappings
				// and not used Parameters will be deleted automatically then.
				for (Node node : rule.getLhs().getNodes()) {
					List<ParameterMapping> removableMappings = new ArrayList<>();
					if (HenshinRuleAnalysisUtilEx.isDeletionNode(node)) {
						for (Attribute attribute : node.getAttributes()) {
							for (ParameterMapping pm : rule.getParameterMappings()) {
								if (pm.getTarget().getName().equals(attribute.getValue())
										|| pm.getSource().getName().equals(attribute.getValue())) {

									removableMappings.add(pm);
								}
							}
						}
						node.getAttributes().clear();
					}
					rule.getParameterMappings().removeAll(removableMappings);
				}
			}
	
		}
		return module;
	}
}
