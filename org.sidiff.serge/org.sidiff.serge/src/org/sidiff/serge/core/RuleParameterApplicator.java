package org.sidiff.serge.core;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.serge.generators.actions.RuleParameterGenerator;

public class RuleParameterApplicator {

	public void applyOn(Set<Set<Module>> allModules) {

		Iterator<Set<Module>> setIterator = allModules.iterator();

		while (setIterator.hasNext()) {
			Set<Module> currentSet = setIterator.next();
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
