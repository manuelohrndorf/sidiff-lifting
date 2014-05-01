package org.sidiff.serge.core;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.generators.actions.RuleParameterGenerator;

public class RuleParameterApplicator {

	public void applyOn(Map<OperationType, Set<Module>> allModules) {

		for (OperationType opType : allModules.keySet()) {
			Set<Module> currentSet = allModules.get(opType);

			if (currentSet != null) {
				for (Module module : currentSet) {
					for (Unit unit : module.getUnits()) {
						if (unit instanceof Rule) {
							RuleParameterGenerator generator = new RuleParameterGenerator((Rule) unit);
							generator.generate();
						}
					}
				}
			}
		}
	}

}
