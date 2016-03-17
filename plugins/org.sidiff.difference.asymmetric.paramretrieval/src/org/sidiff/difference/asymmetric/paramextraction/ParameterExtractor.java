package org.sidiff.difference.asymmetric.paramextraction;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.ParameterInfo;
import org.sidiff.common.henshin.ParameterInfo.ParameterDirection;
import org.sidiff.common.henshin.ParameterInfo.ParameterKind;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.RulebaseFactory;

/**
 * This class extracts the formal parameters from the executeMainUnit of an
 * EditRule and makes the parameters explicit. That is, for each external
 * parameter of the executeMainUnit, an instance of the class Parameter from the
 * Rulebase model is created and added to the EditRule.
 * 
 */
public class ParameterExtractor {

	private EditRule editRule;

	public ParameterExtractor(EditRule editRule) {
		super();
		this.editRule = editRule;
	}

	public void extractParameters() {
		// TODO: Handling if executeMainUnit is an AmalgamationUnit
		Unit erMainUnit = editRule.getExecuteMainUnit();

		LogUtil.log(LogEvent.DEBUG, "Extract parameters for " + erMainUnit.getName());
		
		for (Parameter formal : erMainUnit.getParameters()) {
			if (ParameterInfo.getParameterKind(formal).equals(ParameterKind.OBJECT)) {
				ParameterDirection direction = ParameterInfo.getParameterDirection(formal);

				// Assertions

				// Determine erNode
				Node erNode = null;
				if (direction.equals(ParameterDirection.IN)) {
					// ER node must be in LHS
					erNode = ParameterInfo.getInnermostIdentifiedNode(formal, true);
				} else if (direction.equals(ParameterDirection.OUT)) {
					// ER node must be in RHS
					erNode = ParameterInfo.getInnermostIdentifiedNode(formal, false);
				}

				assert (erNode != null) : "No identified node found for formal parameter " + formal;

				// End Assertions

				// Create Parameter and add to EditRule
				org.sidiff.editrule.rulebase.Parameter parameter = RulebaseFactory.eINSTANCE.createParameter();
				parameter.setName(formal.getName());
				parameter.setKind(org.sidiff.editrule.rulebase.ParameterKind.OBJECT);
				parameter.setType(ParameterInfo.getRealType(formal));
				if (direction.equals(ParameterDirection.IN)) {
					parameter.setDirection(org.sidiff.editrule.rulebase.ParameterDirection.IN);
				} else {
					parameter.setDirection(org.sidiff.editrule.rulebase.ParameterDirection.OUT);
				}
				editRule.getParameters().add(parameter);

			} else {

				org.sidiff.editrule.rulebase.Parameter parameter = RulebaseFactory.eINSTANCE.createParameter();
				ParameterDirection direction = ParameterInfo.getParameterDirection(formal);
				parameter.setName(formal.getName());
				parameter.setKind(org.sidiff.editrule.rulebase.ParameterKind.VALUE);
				parameter.setType(ParameterInfo.getRealType(formal));
				if (direction.equals(ParameterDirection.IN)) {
					parameter.setDirection(org.sidiff.editrule.rulebase.ParameterDirection.IN);
				} else {
					parameter.setDirection(org.sidiff.editrule.rulebase.ParameterDirection.OUT);
				}
				editRule.getParameters().add(parameter);
			}
		}
	}
}
