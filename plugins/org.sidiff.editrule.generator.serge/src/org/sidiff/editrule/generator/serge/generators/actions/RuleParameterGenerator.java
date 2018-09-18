package org.sidiff.editrule.generator.serge.generators.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.editrule.generator.serge.core.InverseTracker;

public class RuleParameterGenerator {

	private Rule rule;
	
	private Map<Parameter, Node> parameterNodeMap = new HashMap<>();
	private Map<Parameter, Attribute> parameterAttributeMap = new HashMap<>();
	

	public RuleParameterGenerator(Rule rule) {
		super();
		this.rule = rule;
	}

	public void generate() {
		// LHS nodes
		for (Node lhsNode : rule.getLhs().getNodes()) {
			if (lhsNode.getName() != null && !lhsNode.getName().equals("")) {
				Parameter p=generateParameter(lhsNode.getName(), lhsNode.getType());
				if (p != null){
					rule.getParameters().add(p);
					InverseTracker.INSTANCE.addParameter(lhsNode, p);//MR 4.01.18: is this really needed?
					parameterNodeMap.put(p, lhsNode);				
				}
			}

			for (Attribute attribute : lhsNode.getAttributes()) {
				Parameter p=generateParameter(attribute.getValue(), attribute.getType().getEAttributeType());
				if (p != null){
					rule.getParameters().add(p);
					InverseTracker.INSTANCE.addParameter(attribute, p);//MR 4.01.18: is this really needed?
					parameterAttributeMap.put(p, attribute);	
				}
			}
		}

		// RHS nodes
		for (Node rhsNode : rule.getRhs().getNodes()) {
			if (rhsNode.getName() != null && !rhsNode.getName().equals("")) {
				Parameter p=generateParameter(rhsNode.getName(), rhsNode.getType());
				if (p != null){
					rule.getParameters().add(p);
					InverseTracker.INSTANCE.addParameter(rhsNode, p);//MR 4.01.18: is this really needed?
					parameterNodeMap.put(p, rhsNode);
				}
			}

			for (Attribute attribute : rhsNode.getAttributes()) {
				Parameter p=generateParameter(attribute.getValue(), attribute.getType().getEAttributeType());
				if (p != null){
					rule.getParameters().add(p);
					InverseTracker.INSTANCE.addParameter(attribute, p);//MR 4.01.18: is this really needed?
					parameterAttributeMap.put(p, attribute);	
				}
			}
		}
		
		setParameterKinds();
	}

	private Parameter generateParameter(String name, EClassifier type) {
		if (rule.getParameter(name) != null && rule.getParameter(name).getType() == type) {
			return null;
		}

		Parameter p = HenshinFactory.eINSTANCE.createParameter(name);
		p.setType(type);
		return p;
	}
	
	private void setParameterKinds() {
		
		// Nodes		
		for(Entry<Parameter, Node> entry: parameterNodeMap.entrySet()) {
			Node n = entry.getValue();
			
			// <<create>> Node --> ParameterKind.OUT
			if(HenshinRuleAnalysisUtilEx.isCreationNode(n)) {
				Parameter p = entry.getKey();
				p.setKind(ParameterKind.OUT);
			}
			// <<delete>> Node --> ParameterKind.IN
			if(HenshinRuleAnalysisUtilEx.isDeletionNode(n)) {
				Parameter p = entry.getKey();
				p.setKind(ParameterKind.IN);
			}
			// <<preserve>> Node --> ParameterKind.INOUT 
			if(HenshinRuleAnalysisUtilEx.isPreservedNode(n)) {
				Parameter p = entry.getKey();
				p.setKind(ParameterKind.IN);
			}
			
		}
		
		//Attributes		
		for(Entry<Parameter, Attribute> entry: parameterAttributeMap.entrySet()) {
			Attribute a = entry.getValue();
			
			// <<create>> Attribute --> ParameterKind.OUT
			if(HenshinRuleAnalysisUtilEx.isCreationAttribute(a)) {
				Parameter p = entry.getKey();
				p.setKind(ParameterKind.OUT);
			}
			// <<delete>> Attribute --> ParameterKind.IN
			if(!HenshinRuleAnalysisUtilEx.isCreationAttribute(a) && !HenshinRuleAnalysisUtilEx.isPreservedAttribute(a)) {
				Parameter p = entry.getKey();
				p.setKind(ParameterKind.IN);
			}
			// <<preserve>> Attribute --> ParameterKind.INOUT 
			if(HenshinRuleAnalysisUtilEx.isPreservedAttribute(a)) {
				Parameter p = entry.getKey();
				p.setKind(ParameterKind.INOUT);
			}
			
		}	
		
	}
}
