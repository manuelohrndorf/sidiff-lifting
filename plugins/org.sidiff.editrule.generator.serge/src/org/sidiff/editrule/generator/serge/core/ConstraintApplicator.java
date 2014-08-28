package org.sidiff.editrule.generator.serge.core;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.editrule.generator.serge.generators.conditions.LowerBoundCheckGenerator;
import org.sidiff.editrule.generator.serge.generators.conditions.UpperBoundCheckGenerator;
import org.sidiff.editrule.generator.types.OperationType;

/**
 * Applies application conditions to all rules. The generation of the conditions
 * itself is delegetad to the respective condition generators.
 * 
 * @author kehrer
 */
public class ConstraintApplicator {

	public void applyOn(Map<OperationType, Set<Module>> allModules) {
		// TODO re-implementation of possible constraints:
		// 1. required nodes to ensure lowerbound multiplicities are matched
		// 2. forbid nodes to exclude upperbound multiplicity surpassing
		// 3. local name uniqueness
		// 4. global name uniqueness

		for (OperationType opType : allModules.keySet()) {
			Set<Module> currentSet = allModules.get(opType);
		
			if (currentSet != null) {
				for (Module module : currentSet) {
					for (Unit unit : module.getUnits()) {
						if (unit instanceof Rule) {
							LowerBoundCheckGenerator lbGenerator = new LowerBoundCheckGenerator((Rule) unit);
							lbGenerator.generate();

							UpperBoundCheckGenerator ubGenerator = new UpperBoundCheckGenerator((Rule) unit);
							ubGenerator.generate();
						}
					}
				}
			}
		}
	}

}
