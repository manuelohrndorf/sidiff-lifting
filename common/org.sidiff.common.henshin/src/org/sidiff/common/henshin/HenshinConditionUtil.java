package org.sidiff.common.henshin;

import org.eclipse.emf.henshin.model.*;

public class HenshinConditionUtil {

	/**
	 * This method adds a new Formula to the given LHS. If the LHS already contains a Formula,
	 * a new container Formula will be created under the LHS where the old Formula and the new Formula will be combined.
	 * The old and the new Formula can be combined with AND, OR or XOR.
	 * @param new_formula
	 * 						the new Formula
	 * @param lhs
	 * 						the containing LHS.
	 * @param operator
	 * 						defines how existing Formulas shall be combined with the new one.
	 */
	public static void addFormula(Formula new_formula, Graph lhs, HenshinRuleAnalysisUtilEx.FormulaCombineOperator operator) {
		Formula existingFormula = lhs.getFormula();
		if(existingFormula != null) {
			BinaryFormula combiningFormula = operator.createFormula();
			combiningFormula.setLeft(existingFormula);
			combiningFormula.setRight(new_formula);
			lhs.setFormula(combiningFormula);
		} else {
			lhs.setFormula(new_formula);
		}
	}
}
