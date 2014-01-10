package org.sidiff.serge.core;

import java.util.Set;

import org.eclipse.emf.henshin.model.Module;

public class ConstraintApplicator {

	public void applyOn(Set<Module> createModules) {
		// TODO re-implementation of possible constraints:
		//  1. required nodes to ensure lowerbound multiplicities are matched
		//  2. forbid nodes to exclude upperbound multiplicity surpassing
		//	3. local name uniqueness
		//  4. global name uniqueness
		
		// TODO also make sure: inverse creation must delete existing constraints.
	}

}
