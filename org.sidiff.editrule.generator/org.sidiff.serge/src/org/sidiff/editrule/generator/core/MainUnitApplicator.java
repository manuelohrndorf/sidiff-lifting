package org.sidiff.editrule.generator.core;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.editrule.generator.configuration.Configuration.OperationType;
import org.sidiff.editrule.generator.generators.actions.MainUnitGenerator;

public class MainUnitApplicator {

	public void applyOn(Map<OperationType, Set<Module>> allModules) {

		for (OperationType opType : allModules.keySet()) {
			Set<Module> currentSet = allModules.get(opType);
		
			if (currentSet != null) {
				for (Module module : currentSet) {
					MainUnitGenerator mainUnitGenerator = new MainUnitGenerator(module);
					mainUnitGenerator.generate();
				}
			}
		}
	}

}
