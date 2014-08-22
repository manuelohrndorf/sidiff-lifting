package org.sidiff.editrule.generator.generators.actions;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

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
				generateParameter(lhsNode.getName(), lhsNode.getType());
			}

			for (Attribute attribute : lhsNode.getAttributes()) {
				generateParameter(attribute.getValue(), attribute.getType().getEAttributeType());
			}
		}

		// RHS nodes
		for (Node rhsNode : rule.getRhs().getNodes()) {
			if (rhsNode.getName() != null && !rhsNode.getName().equals("")) {
				generateParameter(rhsNode.getName(), rhsNode.getType());
			}

			for (Attribute attribute : rhsNode.getAttributes()) {
				generateParameter(attribute.getValue(), attribute.getType().getEAttributeType());
			}
		}
	}

	private void generateParameter(String name, EClassifier type) {
		if (rule.getParameter(name) != null && rule.getParameter(name).getType() == type) {
			return;
		}

		Parameter p = HenshinFactory.eINSTANCE.createParameter(name);
		p.setType(type);
		rule.getParameters().add(p);
	}
}
