package org.sidiff.serge.core;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.serge.generators.conditions.LowerBoundCheckGenerator;
import org.sidiff.serge.generators.conditions.UpperBoundCheckGenerator;

public class ConstraintApplicator {

	public void applyOn(Set<Set<Module>> allModules) {
		// TODO re-implementation of possible constraints:
		// 1. required nodes to ensure lowerbound multiplicities are matched
		// 2. forbid nodes to exclude upperbound multiplicity surpassing
		// 3. local name uniqueness
		// 4. global name uniqueness

		Iterator<Set<Module>> setIterator = allModules.iterator();

		while (setIterator.hasNext()) {
			Set<Module> currentSet = setIterator.next();
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

		// TODO also make sure: inverse creation must delete existing
		// constraints.
		// TK: I think we don't have to consider that if constraints are
		// generated at the very end.
	}

}
