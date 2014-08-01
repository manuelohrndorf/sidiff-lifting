package org.sidiff.serge.ocl.constraintapplicator.store;

import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;

public class ConstraintModulePair {

	private Module module;
	private OCLExpressionPattern oclxpp;
	
	private Set<ConstraintModulePair> additonalModuleAffections;
	
	public ConstraintModulePair(Module m, OCLExpressionPattern oclxpp) {
		
		this.module = m;
		this.oclxpp = oclxpp;
		
	}
}
