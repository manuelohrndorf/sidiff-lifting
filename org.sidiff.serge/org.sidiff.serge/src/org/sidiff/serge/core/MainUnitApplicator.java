package org.sidiff.serge.core;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.serge.generators.actions.MainUnitGenerator;

public class MainUnitApplicator {

	public void applyOn(Set<Set<Module>> allModules) {

		Iterator<Set<Module>> setIterator = allModules.iterator();

		while (setIterator.hasNext()) {
			Set<Module> currentSet = setIterator.next();
			if (currentSet != null) {
				for (Module module : currentSet) {
					MainUnitGenerator mainUnitGenerator = new MainUnitGenerator(module);
					mainUnitGenerator.generate();
				}
			}
		}
	}

}
