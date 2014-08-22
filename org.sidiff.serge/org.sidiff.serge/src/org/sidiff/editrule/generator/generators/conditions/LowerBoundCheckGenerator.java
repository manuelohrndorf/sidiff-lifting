package org.sidiff.editrule.generator.generators.conditions;

import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinConditionUtil;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.FormulaCombineOperator;
import org.sidiff.common.henshin.NodePair;

public class LowerBoundCheckGenerator extends AbstractBoundCheckGenerator {

	public LowerBoundCheckGenerator(Rule editRule) {
		super(editRule);
	}

	public void generate() {
		for (NodePair preservedNode : src2Out.keySet()) {
			Set<EReference> outTypes = src2Out.get(preservedNode);
			for (EReference outType : outTypes) {
				int lb = outType.getLowerBound();				
				int balance = getBalance(preservedNode, outType);
				int preserved = getPreservedCount(preservedNode, outType);
			
				if ((lb > 0) && (balance < 0) && (preserved < lb)){
					int offset = ((lb - preserved) + balance) + 1;
					if (offset > 0){
						Formula formula = createBoundCheck(offset, preservedNode.getLhsNode(), outType, false);
						HenshinConditionUtil.addFormula(formula, editRule.getLhs(), FormulaCombineOperator.AND);
					}
				}
			}
		}
	}

}
