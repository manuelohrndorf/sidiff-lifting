package org.sidiff.editrule.generator.serge.generators.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.editrule.generator.serge.core.InverseTracker;

public class RuleParameterGenerator {

	private Rule rule;

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
					InverseTracker.INSTANCE.addParameter(lhsNode, p);
				}
			}

			for (Attribute attribute : lhsNode.getAttributes()) {
				Parameter p=generateParameter(attribute.getValue(), attribute.getType().getEAttributeType());
				if (p != null){
					rule.getParameters().add(p);
					InverseTracker.INSTANCE.addParameter(attribute, p);
				}
			}
		}

		// RHS nodes
		for (Node rhsNode : rule.getRhs().getNodes()) {
			if (rhsNode.getName() != null && !rhsNode.getName().equals("")) {
				Parameter p=generateParameter(rhsNode.getName(), rhsNode.getType());
				if (p != null){
					rule.getParameters().add(p);
					InverseTracker.INSTANCE.addParameter(rhsNode, p);
				}
			}

			for (Attribute attribute : rhsNode.getAttributes()) {
				Parameter p=generateParameter(attribute.getValue(), attribute.getType().getEAttributeType());
				if (p != null){
					rule.getParameters().add(p);
					InverseTracker.INSTANCE.addParameter(attribute, p);
				}
			}
		}
	}

	private Parameter generateParameter(String name, EClassifier type) {
		if (rule.getParameter(name) != null && rule.getParameter(name).getType() == type) {
			return null;
		}

		Parameter p = HenshinFactory.eINSTANCE.createParameter(name);
		p.setType(type);
		return p;
	}
}
